package com.badlogic.gdx.net;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.net.InetSocketAddress;

public class NetJavaServerSocketImpl implements ServerSocket {
  private Net.Protocol protocol;
  
  private java.net.ServerSocket server;
  
  public NetJavaServerSocketImpl(Net.Protocol protocol, int port, ServerSocketHints hints) {
    this(protocol, null, port, hints);
  }
  
  public NetJavaServerSocketImpl(Net.Protocol protocol, String hostname, int port, ServerSocketHints hints) {
    this.protocol = protocol;
    try {
      InetSocketAddress address;
      this.server = new java.net.ServerSocket();
      if (hints != null) {
        this.server.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency, hints.performancePrefBandwidth);
        this.server.setReuseAddress(hints.reuseAddress);
        this.server.setSoTimeout(hints.acceptTimeout);
        this.server.setReceiveBufferSize(hints.receiveBufferSize);
      } 
      if (hostname != null) {
        address = new InetSocketAddress(hostname, port);
      } else
        address = new InetSocketAddress(port); 
      if (hints != null) {
        this.server.bind(address, hints.backlog);
      } else
        this.server.bind(address); 
    } catch (Exception e) {
      throw new GdxRuntimeException("Cannot create a server socket at port " + port + ".", e);
    } 
  }
  
  public Net.Protocol getProtocol() {
    return this.protocol;
  }
  
  public Socket accept(SocketHints hints) {
    try {
      return new NetJavaSocketImpl(this.server.accept(), hints);
    } catch (Exception e) {
      throw new GdxRuntimeException("Error accepting socket.", e);
    } 
  }
  
  public void dispose() {
    if (this.server != null)
      try {
        this.server.close();
        this.server = null;
      } catch (Exception e) {
        throw new GdxRuntimeException("Error closing server.", e);
      }  
  }
}
