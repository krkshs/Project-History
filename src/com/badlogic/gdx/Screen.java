package com.badlogic.gdx;

public interface Screen {
  void show();
  
  void render(float paramFloat);
  
  void resize(int paramInt1, int paramInt2);
  
  void pause();
  
  void resume();
  
  void hide();
  
  void dispose();
}
