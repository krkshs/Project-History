package com.codedisaster.steamworks;

public interface SteamScreenshotsCallback {
  void onScreenshotReady(SteamScreenshotHandle paramSteamScreenshotHandle, SteamResult paramSteamResult);
  
  void onScreenshotRequested();
}
