package com.badlogic.gdx;

public interface InputProcessor {
  boolean keyDown(int paramInt);
  
  boolean keyUp(int paramInt);
  
  boolean keyTyped(char paramChar);
  
  boolean touchDown(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean touchUp(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean touchDragged(int paramInt1, int paramInt2, int paramInt3);
  
  boolean mouseMoved(int paramInt1, int paramInt2);
  
  boolean scrolled(float paramFloat1, float paramFloat2);
}
