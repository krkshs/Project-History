package com.badlogic.gdx.maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public interface ImageResolver {
  TextureRegion getImage(String paramString);
  
  public static class DirectImageResolver implements ImageResolver {
    private final ObjectMap<String, Texture> images;
    
    public DirectImageResolver(ObjectMap<String, Texture> images) {
      this.images = images;
    }
    
    public TextureRegion getImage(String name) {
      return new TextureRegion(this.images.<String>get(name));
    }
  }
  
  public static class AssetManagerImageResolver implements ImageResolver {
    private final AssetManager assetManager;
    
    public AssetManagerImageResolver(AssetManager assetManager) {
      this.assetManager = assetManager;
    }
    
    public TextureRegion getImage(String name) {
      return new TextureRegion(this.assetManager.<Texture>get(name, Texture.class));
    }
  }
  
  public static class TextureAtlasImageResolver implements ImageResolver {
    private final TextureAtlas atlas;
    
    public TextureAtlasImageResolver(TextureAtlas atlas) {
      this.atlas = atlas;
    }
    
    public TextureRegion getImage(String name) {
      return this.atlas.findRegion(name);
    }
  }
}
