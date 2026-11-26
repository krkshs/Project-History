package com.badlogic.gdx.math;

import java.util.Random;

public class RandomXS128 extends Random {
  private static final double NORM_DOUBLE = 1.1102230246251565E-16D;
  
  private static final double NORM_FLOAT = 5.960464477539063E-8D;
  
  private long seed0;
  
  private long seed1;
  
  public RandomXS128() {
    setSeed(new Random().nextLong());
  }
  
  public RandomXS128(long seed) {
    setSeed(seed);
  }
  
  public RandomXS128(long seed0, long seed1) {
    setState(seed0, seed1);
  }
  
  public long nextLong() {
    long s1 = this.seed0;
    long s0 = this.seed1;
    this.seed0 = s0;
    s1 ^= s1 << 23L;
    return (this.seed1 = s1 ^ s0 ^ s1 >>> 17L ^ s0 >>> 26L) + s0;
  }
  
  protected final int next(int bits) {
    return (int)(nextLong() & (1L << bits) - 1L);
  }
  
  public int nextInt() {
    return (int)nextLong();
  }
  
  public int nextInt(int n) {
    return (int)nextLong((long)n);
  }
  
  public long nextLong(long n) {
    if (n <= 0L)
      throw new IllegalArgumentException("n must be positive"); 
    while (true) {
      long bits = nextLong() >>> 1L;
      long value = bits % n;
      if (bits - value + n - 1L >= 0L)
        return value; 
    } 
  }
  
  public double nextDouble() {
    return (double)(nextLong() >>> 11L) * 1.1102230246251565E-16D;
  }
  
  public float nextFloat() {
    return (float)((double)(nextLong() >>> 40L) * 5.960464477539063E-8D);
  }
  
  public boolean nextBoolean() {
    return ((nextLong() & 0x1L) != 0L);
  }
  
  public void nextBytes(byte[] bytes) {
    int n = 0;
    int i = bytes.length;
    while (i != 0) {
      n = (i < 8) ? i : 8;
      for (long bits = nextLong(); n-- != 0; bits >>= 8L)
        bytes[--i] = (byte)(int)bits; 
    } 
  }
  
  public void setSeed(long seed) {
    long seed0 = murmurHash3((seed == 0L) ? Long.MIN_VALUE : seed);
    setState(seed0, murmurHash3(seed0));
  }
  
  public void setState(long seed0, long seed1) {
    this.seed0 = seed0;
    this.seed1 = seed1;
  }
  
  public long getState(int seed) {
    return (seed == 0) ? this.seed0 : this.seed1;
  }
  
  private static final long murmurHash3(long x) {
    x ^= x >>> 33L;
    x *= -49064778989728563L;
    x ^= x >>> 33L;
    x *= -4265267296055464877L;
    x ^= x >>> 33L;
    return x;
  }
}
