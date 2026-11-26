package com.badlogic.gdx.backends.lwjgl3.audio;

import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class JavaSoundAudioRecorder implements AudioRecorder {
  private TargetDataLine line;
  
  private byte[] buffer = new byte[4096];
  
  public JavaSoundAudioRecorder(int samplingRate, boolean isMono) {
    try {
      AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float)samplingRate, 16, isMono ? 1 : 2, isMono ? 2 : 4, (float)samplingRate, false);
      this.line = AudioSystem.getTargetDataLine(format);
      this.line.open(format, this.buffer.length);
      this.line.start();
    } catch (Exception ex) {
      throw new GdxRuntimeException("Error creating JavaSoundAudioRecorder.", ex);
    } 
  }
  
  public void read(short[] samples, int offset, int numSamples) {
    if (this.buffer.length < numSamples * 2)
      this.buffer = new byte[numSamples * 2]; 
    int toRead = numSamples * 2;
    int read = 0;
    while (read != toRead)
      read += this.line.read(this.buffer, read, toRead - read); 
    for (int i = 0, j = 0; i < numSamples * 2; i += 2, j++)
      samples[offset + j] = (short)(this.buffer[i + 1] << 8 | this.buffer[i] & 0xFF); 
  }
  
  public void dispose() {
    this.line.close();
  }
}
