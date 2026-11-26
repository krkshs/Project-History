package com.badlogic.gdx.backends.lwjgl3.audio;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.ByteArrayOutputStream;

public class Ogg {
  public static class Music extends OpenALMusic {
    private OggInputStream input;
    
    private OggInputStream previousInput;
    
    public Music(OpenALLwjgl3Audio audio, FileHandle file) {
      super(audio, file);
      if (audio.noDevice)
        return; 
      this.input = new OggInputStream(file.read());
      setup(this.input.getChannels(), this.input.getSampleRate());
    }
    
    public int read(byte[] buffer) {
      if (this.input == null) {
        this.input = new OggInputStream(this.file.read(), this.previousInput);
        setup(this.input.getChannels(), this.input.getSampleRate());
        this.previousInput = null;
      } 
      return this.input.read(buffer);
    }
    
    public void reset() {
      StreamUtils.closeQuietly(this.input);
      this.previousInput = null;
      this.input = null;
    }
    
    protected void loop() {
      StreamUtils.closeQuietly(this.input);
      this.previousInput = this.input;
      this.input = null;
    }
  }
  
  public static class Sound extends OpenALSound {
    public Sound(OpenALLwjgl3Audio audio, FileHandle file) {
      super(audio);
      if (audio.noDevice)
        return; 
      OggInputStream input = null;
      try {
        input = new OggInputStream(file.read());
        ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[2048];
        while (!input.atEnd()) {
          int length = input.read(buffer);
          if (length == -1)
            break; 
          output.write(buffer, 0, length);
        } 
        setup(output.toByteArray(), input.getChannels(), input.getSampleRate());
      } finally {
        StreamUtils.closeQuietly(input);
      } 
    }
  }
}
