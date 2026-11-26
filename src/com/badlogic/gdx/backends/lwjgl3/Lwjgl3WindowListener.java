package com.badlogic.gdx.backends.lwjgl3;

public interface Lwjgl3WindowListener {
  void created(Lwjgl3Window paramLwjgl3Window);
  
  void iconified(boolean paramBoolean);
  
  void maximized(boolean paramBoolean);
  
  void focusLost();
  
  void focusGained();
  
  boolean closeRequested();
  
  void filesDropped(String[] paramArrayOfString);
  
  void refreshRequested();
}
