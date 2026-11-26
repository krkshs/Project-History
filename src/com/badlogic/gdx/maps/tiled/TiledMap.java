package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class TiledMap extends Map {
  private TiledMapTileSets tilesets;
  
  private Array<? extends Disposable> ownedResources;
  
  public TiledMapTileSets getTileSets() {
    return this.tilesets;
  }
  
  public TiledMap() {
    this.tilesets = new TiledMapTileSets();
  }
  
  public void setOwnedResources(Array<? extends Disposable> resources) {
    this.ownedResources = resources;
  }
  
  public void dispose() {
    if (this.ownedResources != null)
      for (Array.ArrayIterator<? extends Disposable> arrayIterator = this.ownedResources.iterator(); arrayIterator.hasNext(); ) {
        Disposable resource = arrayIterator.next();
        resource.dispose();
      }  
  }
}
