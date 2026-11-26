package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.utils.Disposable;
import java.nio.ShortBuffer;

public interface IndexData extends Disposable {
  int getNumIndices();
  
  int getNumMaxIndices();
  
  void setIndices(short[] paramArrayOfshort, int paramInt1, int paramInt2);
  
  void setIndices(ShortBuffer paramShortBuffer);
  
  void updateIndices(int paramInt1, short[] paramArrayOfshort, int paramInt2, int paramInt3);
  
  ShortBuffer getBuffer();
  
  void bind();
  
  void unbind();
  
  void invalidate();
  
  void dispose();
}
