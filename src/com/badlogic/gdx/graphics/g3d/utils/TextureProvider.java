package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public interface TextureProvider {
  Texture load(String paramString);
  
  public static class FileTextureProvider implements TextureProvider {
    private Texture.TextureFilter minFilter;
    
    private Texture.TextureFilter magFilter;
    
    private Texture.TextureWrap uWrap;
    
    private Texture.TextureWrap vWrap;
    
    private boolean useMipMaps;
    
    public FileTextureProvider() {
      this.minFilter = this.magFilter = Texture.TextureFilter.Linear;
      this.uWrap = this.vWrap = Texture.TextureWrap.Repeat;
      this.useMipMaps = false;
    }
    
    public FileTextureProvider(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, Texture.TextureWrap uWrap, Texture.TextureWrap vWrap, boolean useMipMaps) {
      this.minFilter = minFilter;
      this.magFilter = magFilter;
      this.uWrap = uWrap;
      this.vWrap = vWrap;
      this.useMipMaps = useMipMaps;
    }
    
    public Texture load(String fileName) {
      Texture result = new Texture(Gdx.files.internal(fileName), this.useMipMaps);
      result.setFilter(this.minFilter, this.magFilter);
      result.setWrap(this.uWrap, this.vWrap);
      return result;
    }
  }
  
  public static class AssetTextureProvider implements TextureProvider {
    public final AssetManager assetManager;
    
    public AssetTextureProvider(AssetManager assetManager) {
      this.assetManager = assetManager;
    }
    
    public Texture load(String fileName) {
      return this.assetManager.<Texture>get(fileName, Texture.class);
    }
  }
}
