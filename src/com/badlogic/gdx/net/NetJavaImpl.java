package com.badlogic.gdx.net;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NetJavaImpl {
  private final ThreadPoolExecutor executorService;
  
  final ObjectMap<Net.HttpRequest, HttpURLConnection> connections;
  
  final ObjectMap<Net.HttpRequest, Net.HttpResponseListener> listeners;
  
  static class HttpClientResponse implements Net.HttpResponse {
    private final HttpURLConnection connection;
    
    private HttpStatus status;
    
    public HttpClientResponse(HttpURLConnection connection) throws IOException {
      this.connection = connection;
      try {
        this.status = new HttpStatus(connection.getResponseCode());
      } catch (IOException e) {
        this.status = new HttpStatus(-1);
      } 
    }
    
    public byte[] getResult() {
      InputStream input = getInputStream();
      if (input == null)
        return StreamUtils.EMPTY_BYTES; 
      try {
        return StreamUtils.copyStreamToByteArray(input, this.connection.getContentLength());
      } catch (IOException e) {
        return StreamUtils.EMPTY_BYTES;
      } finally {
        StreamUtils.closeQuietly(input);
      } 
    }
    
    public String getResultAsString() {
      InputStream input = getInputStream();
      if (input == null)
        return ""; 
      try {
        return StreamUtils.copyStreamToString(input, this.connection.getContentLength(), "UTF8");
      } catch (IOException e) {
        return "";
      } finally {
        StreamUtils.closeQuietly(input);
      } 
    }
    
    public InputStream getResultAsStream() {
      return getInputStream();
    }
    
    public HttpStatus getStatus() {
      return this.status;
    }
    
    public String getHeader(String name) {
      return this.connection.getHeaderField(name);
    }
    
    public Map<String, List<String>> getHeaders() {
      return this.connection.getHeaderFields();
    }
    
    private InputStream getInputStream() {
      try {
        return this.connection.getInputStream();
      } catch (IOException e) {
        return this.connection.getErrorStream();
      } 
    }
  }
  
  public NetJavaImpl() {
    this(Integer.MAX_VALUE);
  }
  
  public NetJavaImpl(int maxThreads) {
    boolean isCachedPool = (maxThreads == Integer.MAX_VALUE);
    this.executorService = new ThreadPoolExecutor(isCachedPool ? 0 : maxThreads, maxThreads, 60L, TimeUnit.SECONDS, isCachedPool ? new SynchronousQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
          AtomicInteger threadID = new AtomicInteger();
          
          public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "NetThread" + this.threadID.getAndIncrement());
            thread.setDaemon(true);
            return thread;
          }
        });
    this.executorService.allowCoreThreadTimeOut(!isCachedPool);
    this.connections = new ObjectMap<>();
    this.listeners = new ObjectMap<>();
  }
  
  public void sendHttpRequest(final Net.HttpRequest httpRequest, final Net.HttpResponseListener httpResponseListener) {
    if (httpRequest.getUrl() == null) {
      httpResponseListener.failed(new GdxRuntimeException("can't process a HTTP request without URL set"));
      return;
    } 
    try {
      URL url;
      String method = httpRequest.getMethod();
      boolean doInput = !method.equalsIgnoreCase("HEAD");
      final boolean doingOutPut = (method.equalsIgnoreCase("POST") || 
        method.equalsIgnoreCase("PUT") || 
        method.equalsIgnoreCase("PATCH"));
      if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("HEAD")) {
        String queryString = "";
        String value = httpRequest.getContent();
        if (value != null && !"".equals(value))
          queryString = "?" + value; 
        url = new URL(httpRequest.getUrl() + queryString);
      } else
        url = new URL(httpRequest.getUrl()); 
      final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(doingOutPut);
      connection.setDoInput(doInput);
      connection.setRequestMethod(method);
      HttpURLConnection.setFollowRedirects(httpRequest.getFollowRedirects());
      putIntoConnectionsAndListeners(httpRequest, httpResponseListener, connection);
      for (Map.Entry<String, String> header : httpRequest.getHeaders().entrySet())
        connection.addRequestProperty(header.getKey(), header.getValue()); 
      connection.setConnectTimeout(httpRequest.getTimeOut());
      connection.setReadTimeout(httpRequest.getTimeOut());
      this.executorService.submit(new Runnable() {
            public void run() {
              try {
                if (doingOutPut) {
                  String contentAsString = httpRequest.getContent();
                  if (contentAsString != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF8");
                    try {
                      writer.write(contentAsString);
                    } finally {
                      StreamUtils.closeQuietly(writer);
                    } 
                  } else {
                    InputStream contentAsStream = httpRequest.getContentStream();
                    if (contentAsStream != null) {
                      OutputStream os = connection.getOutputStream();
                      try {
                        StreamUtils.copyStream(contentAsStream, os);
                      } finally {
                        StreamUtils.closeQuietly(os);
                      } 
                    } 
                  } 
                } 
                connection.connect();
                NetJavaImpl.HttpClientResponse clientResponse = new NetJavaImpl.HttpClientResponse(connection);
                try {
                  Net.HttpResponseListener listener = NetJavaImpl.this.getFromListeners(httpRequest);
                  if (listener != null)
                    listener.handleHttpResponse(clientResponse); 
                  NetJavaImpl.this.removeFromConnectionsAndListeners(httpRequest);
                } finally {
                  connection.disconnect();
                } 
              } catch (Exception e) {
                connection.disconnect();
                try {
                  httpResponseListener.failed(e);
                } finally {
                  NetJavaImpl.this.removeFromConnectionsAndListeners(httpRequest);
                } 
              } 
            }
          });
    } catch (Exception e) {
      try {
        httpResponseListener.failed(e);
      } finally {
        removeFromConnectionsAndListeners(httpRequest);
      } 
      return;
    } 
  }
  
  public void cancelHttpRequest(Net.HttpRequest httpRequest) {
    Net.HttpResponseListener httpResponseListener = getFromListeners(httpRequest);
    if (httpResponseListener != null) {
      httpResponseListener.cancelled();
      removeFromConnectionsAndListeners(httpRequest);
    } 
  }
  
  synchronized void removeFromConnectionsAndListeners(Net.HttpRequest httpRequest) {
    this.connections.remove(httpRequest);
    this.listeners.remove(httpRequest);
  }
  
  synchronized void putIntoConnectionsAndListeners(Net.HttpRequest httpRequest, Net.HttpResponseListener httpResponseListener, HttpURLConnection connection) {
    this.connections.put(httpRequest, connection);
    this.listeners.put(httpRequest, httpResponseListener);
  }
  
  synchronized Net.HttpResponseListener getFromListeners(Net.HttpRequest httpRequest) {
    Net.HttpResponseListener httpResponseListener = this.listeners.<Net.HttpRequest>get(httpRequest);
    return httpResponseListener;
  }
}
