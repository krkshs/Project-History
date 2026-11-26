package com.badlogic.gdx.backends.lwjgl3.audio;

import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class OpenALAudioDevice implements AudioDevice {
  private static final int bytesPerSample = 2;
  
  private final OpenALLwjgl3Audio audio;
  
  private final int channels;
  
  private IntBuffer buffers;
  
  private int sourceID = -1;
  
  private int format;
  
  private int sampleRate;
  
  private boolean isPlaying;
  
  private float volume = 1.0F;
  
  private float renderedSeconds;
  
  private float secondsPerBuffer;
  
  private byte[] bytes;
  
  private final int bufferSize;
  
  private final int bufferCount;
  
  private final ByteBuffer tempBuffer;
  
  public OpenALAudioDevice(OpenALLwjgl3Audio audio, int sampleRate, boolean isMono, int bufferSize, int bufferCount) {
    this.audio = audio;
    this.channels = isMono ? 1 : 2;
    this.bufferSize = bufferSize;
    this.bufferCount = bufferCount;
    this.format = (this.channels > 1) ? 4355 : 4353;
    this.sampleRate = sampleRate;
    this.secondsPerBuffer = (float)bufferSize / 2.0F / (float)this.channels / (float)sampleRate;
    this.tempBuffer = BufferUtils.createByteBuffer(bufferSize);
  }
  
  public void writeSamples(short[] samples, int offset, int numSamples) {
    if (this.bytes == null || this.bytes.length < numSamples * 2)
      this.bytes = new byte[numSamples * 2]; 
    int end = Math.min(offset + numSamples, samples.length);
    for (int i = offset, ii = 0; i < end; i++) {
      short sample = samples[i];
      this.bytes[ii++] = (byte)(sample & 0xFF);
      this.bytes[ii++] = (byte)(sample >> 8 & 0xFF);
    } 
    writeSamples(this.bytes, 0, numSamples * 2);
  }
  
  public void writeSamples(float[] samples, int offset, int numSamples) {
    if (this.bytes == null || this.bytes.length < numSamples * 2)
      this.bytes = new byte[numSamples * 2]; 
    int end = Math.min(offset + numSamples, samples.length);
    for (int i = offset, ii = 0; i < end; i++) {
      float floatSample = samples[i];
      floatSample = MathUtils.clamp(floatSample, -1.0F, 1.0F);
      int intSample = (int)(floatSample * 32767.0F);
      this.bytes[ii++] = (byte)(intSample & 0xFF);
      this.bytes[ii++] = (byte)(intSample >> 8 & 0xFF);
    } 
    writeSamples(this.bytes, 0, numSamples * 2);
  }
  
  public void writeSamples(byte[] data, int offset, int length) {
    if (length < 0)
      throw new IllegalArgumentException("length cannot be < 0."); 
    if (this.sourceID == -1) {
      this.sourceID = this.audio.obtainSource(true);
      if (this.sourceID == -1)
        return; 
      if (this.buffers == null) {
        this.buffers = BufferUtils.createIntBuffer(this.bufferCount);
        AL10.alGenBuffers(this.buffers);
        if (AL10.alGetError() != 0)
          throw new GdxRuntimeException("Unabe to allocate audio buffers."); 
      } 
      AL10.alSourcei(this.sourceID, 4103, 0);
      AL10.alSourcef(this.sourceID, 4106, this.volume);
      int queuedBuffers = 0;
      for (int j = 0; j < this.bufferCount; j++) {
        int bufferID = this.buffers.get(j);
        int written = Math.min(this.bufferSize, length);
        this.tempBuffer.clear();
        this.tempBuffer.put(data, offset, written).flip();
        AL10.alBufferData(bufferID, this.format, this.tempBuffer, this.sampleRate);
        AL10.alSourceQueueBuffers(this.sourceID, bufferID);
        length -= written;
        offset += written;
        queuedBuffers++;
      } 
      this.tempBuffer.clear().flip();
      for (int i = queuedBuffers; i < this.bufferCount; i++) {
        int bufferID = this.buffers.get(i);
        AL10.alBufferData(bufferID, this.format, this.tempBuffer, this.sampleRate);
        AL10.alSourceQueueBuffers(this.sourceID, bufferID);
      } 
      AL10.alSourcePlay(this.sourceID);
      this.isPlaying = true;
    } 
    while (length > 0) {
      int written = fillBuffer(data, offset, length);
      length -= written;
      offset += written;
    } 
  }
  
  private int fillBuffer(byte[] data, int offset, int length) {
    int written = Math.min(this.bufferSize, length);
    while (true) {
      int buffers = AL10.alGetSourcei(this.sourceID, 4118);
      if (buffers-- > 0) {
        int bufferID = AL10.alSourceUnqueueBuffers(this.sourceID);
        if (bufferID != 40963) {
          this.renderedSeconds += this.secondsPerBuffer;
          this.tempBuffer.clear();
          this.tempBuffer.put(data, offset, written).flip();
          AL10.alBufferData(bufferID, this.format, this.tempBuffer, this.sampleRate);
          AL10.alSourceQueueBuffers(this.sourceID, bufferID);
          break;
        } 
      } 
      try {
        Thread.sleep((long)(1000.0F * this.secondsPerBuffer));
      } catch (InterruptedException e) {}
    } 
    if (!this.isPlaying || AL10.alGetSourcei(this.sourceID, 4112) != 4114) {
      AL10.alSourcePlay(this.sourceID);
      this.isPlaying = true;
    } 
    return written;
  }
  
  public void stop() {
    if (this.sourceID == -1)
      return; 
    this.audio.freeSource(this.sourceID);
    this.sourceID = -1;
    this.renderedSeconds = 0.0F;
    this.isPlaying = false;
  }
  
  public boolean isPlaying() {
    if (this.sourceID == -1)
      return false; 
    return this.isPlaying;
  }
  
  public void setVolume(float volume) {
    this.volume = volume;
    if (this.sourceID != -1)
      AL10.alSourcef(this.sourceID, 4106, volume); 
  }
  
  public float getPosition() {
    if (this.sourceID == -1)
      return 0.0F; 
    return this.renderedSeconds + AL10.alGetSourcef(this.sourceID, 4132);
  }
  
  public void setPosition(float position) {
    this.renderedSeconds = position;
  }
  
  public int getChannels() {
    return (this.format == 4355) ? 2 : 1;
  }
  
  public int getRate() {
    return this.sampleRate;
  }
  
  public void dispose() {
    if (this.buffers == null)
      return; 
    if (this.sourceID != -1) {
      this.audio.freeSource(this.sourceID);
      this.sourceID = -1;
    } 
    AL10.alDeleteBuffers(this.buffers);
    this.buffers = null;
  }
  
  public boolean isMono() {
    return (this.channels == 1);
  }
  
  public int getLatency() {
    return (int)(this.secondsPerBuffer * (float)this.bufferCount * 1000.0F);
  }
}
