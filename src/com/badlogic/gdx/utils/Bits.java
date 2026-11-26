package com.badlogic.gdx.utils;

import java.util.Arrays;

public class Bits {
  long[] bits = new long[] { 0L };
  
  public Bits() {}
  
  public Bits(int nbits) {
    checkCapacity(nbits >>> 6);
  }
  
  public boolean get(int index) {
    int word = index >>> 6;
    if (word >= this.bits.length)
      return false; 
    return ((this.bits[word] & 1L << (index & 0x3F)) != 0L);
  }
  
  public boolean getAndClear(int index) {
    int word = index >>> 6;
    if (word >= this.bits.length)
      return false; 
    long oldBits = this.bits[word];
    this.bits[word] = this.bits[word] & (1L << (index & 0x3F) ^ 0xFFFFFFFFFFFFFFFFL);
    return (this.bits[word] != oldBits);
  }
  
  public boolean getAndSet(int index) {
    int word = index >>> 6;
    checkCapacity(word);
    long oldBits = this.bits[word];
    this.bits[word] = this.bits[word] | 1L << (index & 0x3F);
    return (this.bits[word] == oldBits);
  }
  
  public void set(int index) {
    int word = index >>> 6;
    checkCapacity(word);
    this.bits[word] = this.bits[word] | 1L << (index & 0x3F);
  }
  
  public void flip(int index) {
    int word = index >>> 6;
    checkCapacity(word);
    this.bits[word] = this.bits[word] ^ 1L << (index & 0x3F);
  }
  
  private void checkCapacity(int len) {
    if (len >= this.bits.length) {
      long[] newBits = new long[len + 1];
      System.arraycopy(this.bits, 0, newBits, 0, this.bits.length);
      this.bits = newBits;
    } 
  }
  
  public void clear(int index) {
    int word = index >>> 6;
    if (word >= this.bits.length)
      return; 
    this.bits[word] = this.bits[word] & (1L << (index & 0x3F) ^ 0xFFFFFFFFFFFFFFFFL);
  }
  
  public void clear() {
    Arrays.fill(this.bits, 0L);
  }
  
  public int numBits() {
    return this.bits.length << 6;
  }
  
  public int length() {
    long[] bits = this.bits;
    for (int word = bits.length - 1; word >= 0; word--) {
      long bitsAtWord = bits[word];
      if (bitsAtWord != 0L)
        for (int bit = 63; bit >= 0; bit--) {
          if ((bitsAtWord & 1L << (bit & 0x3F)) != 0L)
            return (word << 6) + bit + 1; 
        }  
    } 
    return 0;
  }
  
  public boolean notEmpty() {
    return !isEmpty();
  }
  
  public boolean isEmpty() {
    long[] bits = this.bits;
    int length = bits.length;
    for (int i = 0; i < length; i++) {
      if (bits[i] != 0L)
        return false; 
    } 
    return true;
  }
  
  public int nextSetBit(int fromIndex) {
    long[] bits = this.bits;
    int word = fromIndex >>> 6;
    int bitsLength = bits.length;
    if (word >= bitsLength)
      return -1; 
    long bitsAtWord = bits[word];
    if (bitsAtWord != 0L)
      for (int i = fromIndex & 0x3F; i < 64; i++) {
        if ((bitsAtWord & 1L << (i & 0x3F)) != 0L)
          return (word << 6) + i; 
      }  
    for (; ++word < bitsLength; word++) {
      if (word != 0) {
        bitsAtWord = bits[word];
        if (bitsAtWord != 0L)
          for (int i = 0; i < 64; i++) {
            if ((bitsAtWord & 1L << (i & 0x3F)) != 0L)
              return (word << 6) + i; 
          }  
      } 
    } 
    return -1;
  }
  
  public int nextClearBit(int fromIndex) {
    long[] bits = this.bits;
    int word = fromIndex >>> 6;
    int bitsLength = bits.length;
    if (word >= bitsLength)
      return bits.length << 6; 
    long bitsAtWord = bits[word];
    for (int i = fromIndex & 0x3F; i < 64; i++) {
      if ((bitsAtWord & 1L << (i & 0x3F)) == 0L)
        return (word << 6) + i; 
    } 
    for (; ++word < bitsLength; word++) {
      if (word == 0)
        return word << 6; 
      bitsAtWord = bits[word];
      for (int j = 0; j < 64; j++) {
        if ((bitsAtWord & 1L << (j & 0x3F)) == 0L)
          return (word << 6) + j; 
      } 
    } 
    return bits.length << 6;
  }
  
  public void and(Bits other) {
    int commonWords = Math.min(this.bits.length, other.bits.length);
    for (int i = 0; commonWords > i; i++)
      this.bits[i] = this.bits[i] & other.bits[i]; 
    if (this.bits.length > commonWords)
      for (int j = commonWords, s = this.bits.length; s > j; j++)
        this.bits[j] = 0L;  
  }
  
  public void andNot(Bits other) {
    for (int i = 0, j = this.bits.length, k = other.bits.length; i < j && i < k; i++)
      this.bits[i] = this.bits[i] & (other.bits[i] ^ 0xFFFFFFFFFFFFFFFFL); 
  }
  
  public void or(Bits other) {
    int commonWords = Math.min(this.bits.length, other.bits.length);
    for (int i = 0; commonWords > i; i++)
      this.bits[i] = this.bits[i] | other.bits[i]; 
    if (commonWords < other.bits.length) {
      checkCapacity(other.bits.length);
      for (int j = commonWords, s = other.bits.length; s > j; j++)
        this.bits[j] = other.bits[j]; 
    } 
  }
  
  public void xor(Bits other) {
    int commonWords = Math.min(this.bits.length, other.bits.length);
    for (int i = 0; commonWords > i; i++)
      this.bits[i] = this.bits[i] ^ other.bits[i]; 
    if (commonWords < other.bits.length) {
      checkCapacity(other.bits.length);
      for (int j = commonWords, s = other.bits.length; s > j; j++)
        this.bits[j] = other.bits[j]; 
    } 
  }
  
  public boolean intersects(Bits other) {
    long[] bits = this.bits;
    long[] otherBits = other.bits;
    for (int i = Math.min(bits.length, otherBits.length) - 1; i >= 0; i--) {
      if ((bits[i] & otherBits[i]) != 0L)
        return true; 
    } 
    return false;
  }
  
  public boolean containsAll(Bits other) {
    long[] bits = this.bits;
    long[] otherBits = other.bits;
    int otherBitsLength = otherBits.length;
    int bitsLength = bits.length;
    for (int j = bitsLength; j < otherBitsLength; j++) {
      if (otherBits[j] != 0L)
        return false; 
    } 
    for (int i = Math.min(bitsLength, otherBitsLength) - 1; i >= 0; i--) {
      if ((bits[i] & otherBits[i]) != otherBits[i])
        return false; 
    } 
    return true;
  }
  
  public int hashCode() {
    int word = length() >>> 6;
    int hash = 0;
    for (int i = 0; word >= i; i++)
      hash = 127 * hash + (int)(this.bits[i] ^ this.bits[i] >>> 32L); 
    return hash;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    Bits other = (Bits)obj;
    long[] otherBits = other.bits;
    int commonWords = Math.min(this.bits.length, otherBits.length);
    for (int i = 0; commonWords > i; i++) {
      if (this.bits[i] != otherBits[i])
        return false; 
    } 
    if (this.bits.length == otherBits.length)
      return true; 
    return (length() == other.length());
  }
}
