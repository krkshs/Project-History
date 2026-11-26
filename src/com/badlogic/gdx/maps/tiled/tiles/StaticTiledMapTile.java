package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class StaticTiledMapTile implements TiledMapTile {
  private int id;
  
  private TiledMapTile.BlendMode blendMode = TiledMapTile.BlendMode.ALPHA;
  
  private MapProperties properties;
  
  private MapObjects objects;
  
  private TextureRegion textureRegion;
  
  private float offsetX;
  
  private float offsetY;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public TiledMapTile.BlendMode getBlendMode() {
    return this.blendMode;
  }
  
  public void setBlendMode(TiledMapTile.BlendMode blendMode) {
    this.blendMode = blendMode;
  }
  
  public MapProperties getProperties() {
    if (this.properties == null)
      this.properties = new MapProperties(); 
    return this.properties;
  }
  
  public MapObjects getObjects() {
    if (this.objects == null)
      this.objects = new MapObjects(); 
    return this.objects;
  }
  
  public TextureRegion getTextureRegion() {
    return this.textureRegion;
  }
  
  public void setTextureRegion(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
  }
  
  public float getOffsetX() {
    return this.offsetX;
  }
  
  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }
  
  public float getOffsetY() {
    return this.offsetY;
  }
  
  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }
  
  public StaticTiledMapTile(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
  }
  
  public StaticTiledMapTile(StaticTiledMapTile copy) {
    if (copy.properties != null)
      getProperties().putAll(copy.properties); 
    this.objects = copy.objects;
    this.textureRegion = copy.textureRegion;
    this.id = copy.id;
  }
}
