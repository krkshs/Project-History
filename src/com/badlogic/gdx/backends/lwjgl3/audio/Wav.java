package com.badlogic.gdx.backends.lwjgl3.audio;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;

public class Wav {
  public static class Music extends OpenALMusic {
    private Wav.WavInputStream input;
    
    public Music(OpenALLwjgl3Audio audio, FileHandle file) {
      super(audio, file);
      this.input = new Wav.WavInputStream(file);
      if (audio.noDevice)
        return; 
      setup(this.input.channels, this.input.sampleRate);
    }
    
    public int read(byte[] buffer) {
      if (this.input == null) {
        this.input = new Wav.WavInputStream(this.file);
        setup(this.input.channels, this.input.sampleRate);
      } 
      try {
        return this.input.read(buffer);
      } catch (IOException ex) {
        throw new GdxRuntimeException("Error reading WAV file: " + this.file, ex);
      } 
    }
    
    public void reset() {
      StreamUtils.closeQuietly(this.input);
      this.input = null;
    }
  }
  
  public static class Sound extends OpenALSound {
    public Sound(OpenALLwjgl3Audio audio, FileHandle file) {
      super(audio);
      if (audio.noDevice)
        return; 
      Wav.WavInputStream input = null;
      try {
        input = new Wav.WavInputStream(file);
        setup(StreamUtils.copyStreamToByteArray(input, input.dataRemaining), input.channels, input.sampleRate);
      } catch (IOException ex) {
        throw new GdxRuntimeException("Error reading WAV file: " + file, ex);
      } finally {
        StreamUtils.closeQuietly(input);
      } 
    }
  }
  
  private static class WavInputStream extends FilterInputStream {
    int channels;
    
    int sampleRate;
    
    int dataRemaining;
    
    WavInputStream(FileHandle file) {
      super(file.read());
      try {
        if (read() != 82 || read() != 73 || read() != 70 || read() != 70)
          throw new GdxRuntimeException("RIFF header not found: " + file); 
        skipFully(4);
        if (read() != 87 || read() != 65 || read() != 86 || read() != 69)
          throw new GdxRuntimeException("Invalid wave file header: " + file); 
        int fmtChunkLength = seekToChunk('f', 'm', 't', ' ');
        int type = read() & 0xFF | (read() & 0xFF) << 8;
        if (type != 1)
          throw new GdxRuntimeException("WAV files must be PCM: " + type); 
        this.channels = read() & 0xFF | (read() & 0xFF) << 8;
        if (this.channels != 1 && this.channels != 2)
          throw new GdxRuntimeException("WAV files must have 1 or 2 channels: " + this.channels); 
        this.sampleRate = read() & 0xFF | (read() & 0xFF) << 8 | (read() & 0xFF) << 16 | (read() & 0xFF) << 24;
        skipFully(6);
        int bitsPerSample = read() & 0xFF | (read() & 0xFF) << 8;
        if (bitsPerSample != 16)
          throw new GdxRuntimeException("WAV files must have 16 bits per sample: " + bitsPerSample); 
        skipFully(fmtChunkLength - 16);
        this.dataRemaining = seekToChunk('d', 'a', 't', 'a');
      } catch (Throwable ex) {
        StreamUtils.closeQuietly(this);
        throw new GdxRuntimeException("Error reading WAV file: " + file, ex);
      } 
    }
    
    private int seekToChunk(char c1, char c2, char c3, char c4) throws IOException {
      while (true) {
        boolean found = (read() == c1);
        found &= (read() == c2) ? true : false;
        found &= (read() == c3) ? true : false;
        found &= (read() == c4) ? true : false;
        int chunkLength = read() & 0xFF | (read() & 0xFF) << 8 | (read() & 0xFF) << 16 | (read() & 0xFF) << 24;
        if (chunkLength == -1)
          throw new IOException("Chunk not found: " + c1 + c2 + c3 + c4); 
        if (found)
          return chunkLength; 
        skipFully(chunkLength);
      } 
    }
    
    private void skipFully(int count) throws IOException {
      while (count > 0) {
        long skipped = this.in.skip((long)count);
        if (skipped <= 0L)
          throw new EOFException("Unable to skip."); 
        count = (int)((long)count - skipped);
      } 
    }
    
    public int read(byte[] buffer) throws IOException {
      if (this.dataRemaining == 0)
        return -1; 
      int offset = 0;
      while (true) {
        int length = Math.min(read(buffer, offset, buffer.length - offset), this.dataRemaining);
        if (length == -1) {
          if (offset > 0)
            return offset; 
          return -1;
        } 
        offset += length;
        this.dataRemaining -= length;
        if (offset >= buffer.length)
          return offset; 
      } 
    }
  }
}
