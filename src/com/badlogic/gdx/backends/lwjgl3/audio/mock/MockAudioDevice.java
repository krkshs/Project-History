package com.badlogic.gdx.backends.lwjgl3.audio.mock;

import com.badlogic.gdx.audio.AudioDevice;

public class MockAudioDevice implements AudioDevice {
  public boolean isMono() {
    return false;
  }
  
  public void writeSamples(short[] samples, int offset, int numSamples) {}
  
  public void writeSamples(float[] samples, int offset, int numSamples) {}
  
  public int getLatency() {
    return 0;
  }
  
  public void dispose() {}
  
  public void setVolume(float volume) {}
}
