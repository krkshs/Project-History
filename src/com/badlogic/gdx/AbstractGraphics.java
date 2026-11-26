package com.badlogic.gdx;

public abstract class AbstractGraphics implements Graphics {
  public float getRawDeltaTime() {
    return getDeltaTime();
  }
  
  public float getDensity() {
    return getPpiX() / 160.0F;
  }
  
  public float getBackBufferScale() {
    return (float)getBackBufferWidth() / (float)getWidth();
  }
}
