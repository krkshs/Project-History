package com.badlogic.gdx;

import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Net {
  void sendHttpRequest(HttpRequest paramHttpRequest, @Null HttpResponseListener paramHttpResponseListener);
  
  void cancelHttpRequest(HttpRequest paramHttpRequest);
  
  ServerSocket newServerSocket(Protocol paramProtocol, String paramString, int paramInt, ServerSocketHints paramServerSocketHints);
  
  ServerSocket newServerSocket(Protocol paramProtocol, int paramInt, ServerSocketHints paramServerSocketHints);
  
  Socket newClientSocket(Protocol paramProtocol, String paramString, int paramInt, SocketHints paramSocketHints);
  
  boolean openURI(String paramString);
  
  public static interface HttpResponse {
    byte[] getResult();
    
    String getResultAsString();
    
    InputStream getResultAsStream();
    
    HttpStatus getStatus();
    
    String getHeader(String param1String);
    
    Map<String, List<String>> getHeaders();
  }
  
  public static interface HttpMethods {
    public static final String HEAD = "HEAD";
    
    public static final String GET = "GET";
    
    public static final String POST = "POST";
    
    public static final String PUT = "PUT";
    
    public static final String PATCH = "PATCH";
    
    public static final String DELETE = "DELETE";
  }
  
  public static class HttpRequest implements Pool.Poolable {
    private String httpMethod;
    
    private String url;
    
    private Map<String, String> headers;
    
    private int timeOut = 0;
    
    private String content;
    
    private InputStream contentStream;
    
    private long contentLength;
    
    private boolean followRedirects = true;
    
    private boolean includeCredentials = false;
    
    public HttpRequest() {
      this.headers = new HashMap<>();
    }
    
    public HttpRequest(String httpMethod) {
      this();
      this.httpMethod = httpMethod;
    }
    
    public void setUrl(String url) {
      this.url = url;
    }
    
    public void setHeader(String name, String value) {
      this.headers.put(name, value);
    }
    
    public void setContent(String content) {
      this.content = content;
    }
    
    public void setContent(InputStream contentStream, long contentLength) {
      this.contentStream = contentStream;
      this.contentLength = contentLength;
    }
    
    public void setTimeOut(int timeOut) {
      this.timeOut = timeOut;
    }
    
    public void setFollowRedirects(boolean followRedirects) throws IllegalArgumentException {
      if (followRedirects || Gdx.app.getType() != Application.ApplicationType.WebGL) {
        this.followRedirects = followRedirects;
      } else
        throw new IllegalArgumentException("Following redirects can't be disabled using the GWT/WebGL backend!"); 
    }
    
    public void setIncludeCredentials(boolean includeCredentials) {
      this.includeCredentials = includeCredentials;
    }
    
    public void setMethod(String httpMethod) {
      this.httpMethod = httpMethod;
    }
    
    public int getTimeOut() {
      return this.timeOut;
    }
    
    public String getMethod() {
      return this.httpMethod;
    }
    
    public String getUrl() {
      return this.url;
    }
    
    public String getContent() {
      return this.content;
    }
    
    public InputStream getContentStream() {
      return this.contentStream;
    }
    
    public long getContentLength() {
      return this.contentLength;
    }
    
    public Map<String, String> getHeaders() {
      return this.headers;
    }
    
    public boolean getFollowRedirects() {
      return this.followRedirects;
    }
    
    public boolean getIncludeCredentials() {
      return this.includeCredentials;
    }
    
    public void reset() {
      this.httpMethod = null;
      this.url = null;
      this.headers.clear();
      this.timeOut = 0;
      this.content = null;
      this.contentStream = null;
      this.contentLength = 0L;
      this.followRedirects = true;
    }
  }
  
  public static interface HttpResponseListener {
    void handleHttpResponse(Net.HttpResponse param1HttpResponse);
    
    void failed(Throwable param1Throwable);
    
    void cancelled();
  }
  
  public enum Protocol {
    TCP;
  }
}
