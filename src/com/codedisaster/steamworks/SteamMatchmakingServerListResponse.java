package com.codedisaster.steamworks;

public abstract class SteamMatchmakingServerListResponse extends SteamInterface {
  public enum Response {
    ServerResponded, ServerFailedToRespond, NoServersListedOnMasterServer;
    
    private static final Response[] values = values();
    
    static Response byOrdinal(int ordinal) {
      return values[ordinal];
    }
  }
  
  protected SteamMatchmakingServerListResponse() {
    super(-1L);
    this.callback = createProxy(this);
  }
  
  void serverResponded(long request, int server) {
    serverResponded(new SteamServerListRequest(request), server);
  }
  
  void serverFailedToRespond(long request, int server) {
    serverFailedToRespond(new SteamServerListRequest(request), server);
  }
  
  void refreshComplete(long request, int response) {
    refreshComplete(new SteamServerListRequest(request), Response.byOrdinal(response));
  }
  
  public abstract void serverResponded(SteamServerListRequest paramSteamServerListRequest, int paramInt);
  
  public abstract void serverFailedToRespond(SteamServerListRequest paramSteamServerListRequest, int paramInt);
  
  public abstract void refreshComplete(SteamServerListRequest paramSteamServerListRequest, Response paramResponse);
  
  private static native long createProxy(SteamMatchmakingServerListResponse paramSteamMatchmakingServerListResponse);
}
