package com.badlogic.gdx.backends.lwjgl3.audio.mock;

import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl3.audio.Lwjgl3Audio;
import com.badlogic.gdx.files.FileHandle;

public class MockAudio implements Lwjgl3Audio {
  public AudioDevice newAudioDevice(int samplingRate, boolean isMono) {
    return new MockAudioDevice();
  }
  
  public AudioRecorder newAudioRecorder(int samplingRate, boolean isMono) {
    return new MockAudioRecorder();
  }
  
  public Sound newSound(FileHandle fileHandle) {
    return new MockSound();
  }
  
  public Music newMusic(FileHandle file) {
    return new MockMusic();
  }
  
  public void update() {}
  
  public void dispose() {}
}
