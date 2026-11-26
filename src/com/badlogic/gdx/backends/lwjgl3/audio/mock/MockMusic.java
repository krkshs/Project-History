package com.badlogic.gdx.backends.lwjgl3.audio.mock;

import com.badlogic.gdx.audio.Music;

public class MockMusic implements Music {
  public void play() {}
  
  public void pause() {}
  
  public void stop() {}
  
  public boolean isPlaying() {
    return false;
  }
  
  public void setLooping(boolean isLooping) {}
  
  public boolean isLooping() {
    return false;
  }
  
  public void setVolume(float volume) {}
  
  public float getVolume() {
    return 0.0F;
  }
  
  public void setPan(float pan, float volume) {}
  
  public void setPosition(float position) {}
  
  public float getPosition() {
    return 0.0F;
  }
  
  public void dispose() {}
  
  public void setOnCompletionListener(Music.OnCompletionListener listener) {}
}
