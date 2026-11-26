package com.badlogic.gdx.utils;

public class GdxNativesLoader {
  public static boolean disableNativesLoading = false;
  
  private static boolean nativesLoaded;
  
  public static synchronized void load() {
    if (nativesLoaded)
      return; 
    nativesLoaded = true;
    if (disableNativesLoading)
      return; 
    new SharedLibraryLoader().load("gdx");
  }
}
