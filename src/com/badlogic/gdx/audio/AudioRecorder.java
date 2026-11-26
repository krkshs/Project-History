package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

public interface AudioRecorder extends Disposable {
  void read(short[] paramArrayOfshort, int paramInt1, int paramInt2);
  
  void dispose();
}
