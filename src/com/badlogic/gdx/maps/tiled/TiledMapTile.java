package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;

public interface TiledMapTile {
  int getId();
  
  void setId(int paramInt);
  
  BlendMode getBlendMode();
  
  void setBlendMode(BlendMode paramBlendMode);
  
  TextureRegion getTextureRegion();
  
  void setTextureRegion(TextureRegion paramTextureRegion);
  
  float getOffsetX();
  
  void setOffsetX(float paramFloat);
  
  float getOffsetY();
  
  void setOffsetY(float paramFloat);
  
  MapProperties getProperties();
  
  MapObjects getObjects();
  
  public enum BlendMode {
    NONE, ALPHA;
  }
}
