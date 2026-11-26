package com.badlogic.gdx.utils;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianInputStream extends FilterInputStream implements java.io.DataInput {
  private DataInputStream din;
  
  public LittleEndianInputStream(InputStream in) {
    super(in);
    this.din = new DataInputStream(in);
  }
  
  public void readFully(byte[] b) throws IOException {
    this.din.readFully(b);
  }
  
  public void readFully(byte[] b, int off, int len) throws IOException {
    this.din.readFully(b, off, len);
  }
  
  public int skipBytes(int n) throws IOException {
    return this.din.skipBytes(n);
  }
  
  public boolean readBoolean() throws IOException {
    return this.din.readBoolean();
  }
  
  public byte readByte() throws IOException {
    return this.din.readByte();
  }
  
  public int readUnsignedByte() throws IOException {
    return this.din.readUnsignedByte();
  }
  
  public short readShort() throws IOException {
    int low = this.din.read();
    int high = this.din.read();
    return (short)(high << 8 | low & 0xFF);
  }
  
  public int readUnsignedShort() throws IOException {
    int low = this.din.read();
    int high = this.din.read();
    return (high & 0xFF) << 8 | low & 0xFF;
  }
  
  public char readChar() throws IOException {
    return this.din.readChar();
  }
  
  public int readInt() throws IOException {
    int[] res = new int[4];
    for (int i = 3; i >= 0; i--)
      res[i] = this.din.read(); 
    return (res[0] & 0xFF) << 24 | (res[1] & 0xFF) << 16 | (res[2] & 0xFF) << 8 | res[3] & 0xFF;
  }
  
  public long readLong() throws IOException {
    int[] res = new int[8];
    for (int i = 7; i >= 0; i--)
      res[i] = this.din.read(); 
    return (long)(res[0] & 0xFF) << 56L | (long)(res[1] & 0xFF) << 48L | (long)(res[2] & 0xFF) << 40L | (long)(res[3] & 0xFF) << 32L | (long)(res[4] & 0xFF) << 24L | (long)(res[5] & 0xFF) << 16L | (long)(res[6] & 0xFF) << 8L | (long)(res[7] & 0xFF);
  }
  
  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readInt());
  }
  
  public double readDouble() throws IOException {
    return Double.longBitsToDouble(readLong());
  }
  
  public final String readLine() throws IOException {
    return this.din.readLine();
  }
  
  public String readUTF() throws IOException {
    return this.din.readUTF();
  }
}
