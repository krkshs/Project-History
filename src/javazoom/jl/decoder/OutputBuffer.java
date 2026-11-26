package javazoom.jl.decoder;

public class OutputBuffer {
  public static final int BUFFERSIZE = 2304;
  
  private static final int MAXCHANNELS = 2;
  
  private Float replayGainScale;
  
  private int channels;
  
  private byte[] buffer;
  
  private int[] channelPointer;
  
  private boolean isBigEndian;
  
  public OutputBuffer(int channels, boolean isBigEndian) {
    this.channels = channels;
    this.isBigEndian = isBigEndian;
    this.buffer = new byte[2304 * channels];
    this.channelPointer = new int[channels];
    reset();
  }
  
  private void append(int channel, short value) {
    byte firstByte, secondByte;
    if (this.isBigEndian) {
      firstByte = (byte)(value >>> 8 & 0xFF);
      secondByte = (byte)(value & 0xFF);
    } else {
      firstByte = (byte)(value & 0xFF);
      secondByte = (byte)(value >>> 8 & 0xFF);
    } 
    this.buffer[this.channelPointer[channel]] = firstByte;
    this.buffer[this.channelPointer[channel] + 1] = secondByte;
    this.channelPointer[channel] = this.channelPointer[channel] + this.channels * 2;
  }
  
  public void appendSamples(int channel, float[] f) {
    if (this.replayGainScale != null) {
      for (int i = 0; i < 32; ) {
        short s = clip(f[i++] * this.replayGainScale);
        append(channel, s);
      } 
    } else
      for (int i = 0; i < 32; ) {
        short s = clip(f[i++]);
        append(channel, s);
      }  
  }
  
  public byte[] getBuffer() {
    return this.buffer;
  }
  
  public int reset() {
    try {
      int index = this.channels - 1;
      return this.channelPointer[index] - index * 2;
    } finally {
      for (int i = 0; i < this.channels; i++)
        this.channelPointer[i] = i * 2; 
    } 
  }
  
  public void setReplayGainScale(Float replayGainScale) {
    this.replayGainScale = replayGainScale;
  }
  
  public boolean isStereo() {
    return (this.channelPointer[1] == 2);
  }
  
  private final short clip(float sample) {
    return (sample > 32767.0F) ? Short.MAX_VALUE : ((sample < -32768.0F) ? Short.MIN_VALUE : (short)(int)sample);
  }
}
