package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

public interface AudioDevice extends Disposable {
  boolean isMono();
  
  void writeSamples(short[] paramArrayOfshort, int paramInt1, int paramInt2);
  
  void writeSamples(float[] paramArrayOffloat, int paramInt1, int paramInt2);
  
  int getLatency();
  
  void dispose();
  
  void setVolume(float paramFloat);
}
