package com.badlogic.gdx.backends.lwjgl3.audio.mock;

import com.badlogic.gdx.audio.Sound;

public class MockSound implements Sound {
  public long play() {
    return 0L;
  }
  
  public long play(float volume) {
    return 0L;
  }
  
  public long play(float volume, float pitch, float pan) {
    return 0L;
  }
  
  public long loop() {
    return 0L;
  }
  
  public long loop(float volume) {
    return 0L;
  }
  
  public long loop(float volume, float pitch, float pan) {
    return 0L;
  }
  
  public void stop() {}
  
  public void pause() {}
  
  public void resume() {}
  
  public void dispose() {}
  
  public void stop(long soundId) {}
  
  public void pause(long soundId) {}
  
  public void resume(long soundId) {}
  
  public void setLooping(long soundId, boolean looping) {}
  
  public void setPitch(long soundId, float pitch) {}
  
  public void setVolume(long soundId, float volume) {}
  
  public void setPan(long soundId, float pan, float volume) {}
}
