package com.badlogic.gdx.backends.lwjgl3;

public class Lwjgl3WindowAdapter implements Lwjgl3WindowListener {
  public void created(Lwjgl3Window window) {}
  
  public void iconified(boolean isIconified) {}
  
  public void maximized(boolean isMaximized) {}
  
  public void focusLost() {}
  
  public void focusGained() {}
  
  public boolean closeRequested() {
    return true;
  }
  
  public void filesDropped(String[] files) {}
  
  public void refreshRequested() {}
}
