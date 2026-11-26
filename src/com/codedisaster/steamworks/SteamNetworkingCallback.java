package com.codedisaster.steamworks;

public interface SteamNetworkingCallback {
  void onP2PSessionConnectFail(SteamID paramSteamID, SteamNetworking.P2PSessionError paramP2PSessionError);
  
  void onP2PSessionRequest(SteamID paramSteamID);
}
