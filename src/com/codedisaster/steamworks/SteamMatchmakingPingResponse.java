package com.codedisaster.steamworks;

public abstract class SteamMatchmakingPingResponse extends SteamInterface {
  private static native long createProxy(SteamMatchmakingPingResponse paramSteamMatchmakingPingResponse);
  
  public abstract void serverFailedToRespond();
  
  public abstract void serverResponded(SteamMatchmakingGameServerItem paramSteamMatchmakingGameServerItem);
  
  protected SteamMatchmakingPingResponse() {
    super(-1L);
    this.callback = createProxy(this);
  }
}
