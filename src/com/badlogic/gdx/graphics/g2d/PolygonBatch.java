package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Texture;

public interface PolygonBatch extends Batch {
  void draw(PolygonRegion paramPolygonRegion, float paramFloat1, float paramFloat2);
  
  void draw(PolygonRegion paramPolygonRegion, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void draw(PolygonRegion paramPolygonRegion, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);
  
  void draw(Texture paramTexture, float[] paramArrayOffloat, int paramInt1, int paramInt2, short[] paramArrayOfshort, int paramInt3, int paramInt4);
}
