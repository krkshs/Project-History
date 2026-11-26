package com.codedisaster.steamworks;

public abstract class SteamMatchmakingRulesResponse extends SteamInterface {
  private static native long createProxy(SteamMatchmakingRulesResponse paramSteamMatchmakingRulesResponse);
  
  public abstract void rulesRefreshComplete();
  
  public abstract void rulesFailedToRespond();
  
  public abstract void rulesResponded(String paramString1, String paramString2);
  
  protected SteamMatchmakingRulesResponse() {
    super(-1L);
    this.callback = createProxy(this);
  }
}
