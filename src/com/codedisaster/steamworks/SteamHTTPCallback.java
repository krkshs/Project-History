package com.codedisaster.steamworks;

public interface SteamHTTPCallback {
  void onHTTPRequestCompleted(SteamHTTPRequestHandle paramSteamHTTPRequestHandle, long paramLong, boolean paramBoolean, SteamHTTP.HTTPStatusCode paramHTTPStatusCode, int paramInt);
  
  void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle paramSteamHTTPRequestHandle, long paramLong);
  
  void onHTTPRequestDataReceived(SteamHTTPRequestHandle paramSteamHTTPRequestHandle, long paramLong, int paramInt1, int paramInt2);
}
