package com.badlogic.gdx;

public interface ApplicationListener {
  void create();
  
  void resize(int paramInt1, int paramInt2);
  
  void render();
  
  void pause();
  
  void resume();
  
  void dispose();
}
