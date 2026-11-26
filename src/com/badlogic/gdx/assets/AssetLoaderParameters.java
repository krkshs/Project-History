package com.badlogic.gdx.assets;

public class AssetLoaderParameters<T> {
  public LoadedCallback loadedCallback;
  
  public static interface LoadedCallback {
    void finishedLoading(AssetManager param1AssetManager, String param1String, Class param1Class);
  }
}
