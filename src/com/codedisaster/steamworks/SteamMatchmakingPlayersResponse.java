package com.codedisaster.steamworks;

public abstract class SteamMatchmakingPlayersResponse extends SteamInterface {
  private static native long createProxy(SteamMatchmakingPlayersResponse paramSteamMatchmakingPlayersResponse);
  
  public abstract void playersRefreshComplete();
  
  public abstract void playersFailedToRespond();
  
  public abstract void addPlayerToList(String paramString, int paramInt, float paramFloat);
  
  protected SteamMatchmakingPlayersResponse() {
    super(-1L);
    this.callback = createProxy(this);
  }
}
