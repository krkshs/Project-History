package org.lwjgl.system.libc;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCString {
  static {
    Library.initialize();
  }
  
  protected LibCString() {
    throw new UnsupportedOperationException();
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") ByteBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, (long)dest.remaining());
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") ShortBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 1L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") IntBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") LongBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") FloatBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") DoubleBuffer dest, int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") ByteBuffer dest, @NativeType("void const *") ByteBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining());
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") ShortBuffer dest, @NativeType("void const *") ShortBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 1L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") IntBuffer dest, @NativeType("void const *") IntBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") LongBuffer dest, @NativeType("void const *") LongBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") FloatBuffer dest, @NativeType("void const *") FloatBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") DoubleBuffer dest, @NativeType("void const *") DoubleBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") ByteBuffer dest, @NativeType("void const *") ByteBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining());
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") ShortBuffer dest, @NativeType("void const *") ShortBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 1L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") IntBuffer dest, @NativeType("void const *") IntBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") LongBuffer dest, @NativeType("void const *") LongBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") FloatBuffer dest, @NativeType("void const *") FloatBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") DoubleBuffer dest, @NativeType("void const *") DoubleBuffer src) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dest, src.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") byte[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 0L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") short[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 1L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") int[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") long[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 3L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") float[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memset(@NativeType("void *") double[] dest, int c) {
    return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 3L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") byte[] dest, @NativeType("void const *") byte[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 0L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") short[] dest, @NativeType("void const *") short[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 1L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") int[] dest, @NativeType("void const *") int[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") long[] dest, @NativeType("void const *") long[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 3L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") float[] dest, @NativeType("void const *") float[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memcpy(@NativeType("void *") double[] dest, @NativeType("void const *") double[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 3L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") byte[] dest, @NativeType("void const *") byte[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 0L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") short[] dest, @NativeType("void const *") short[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 1L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") int[] dest, @NativeType("void const *") int[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") long[] dest, @NativeType("void const *") long[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 3L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") float[] dest, @NativeType("void const *") float[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 2L);
  }
  
  @NativeType("void *")
  public static long memmove(@NativeType("void *") double[] dest, @NativeType("void const *") double[] src) {
    if (Checks.CHECKS)
      Checks.check(dest, src.length); 
    return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 3L);
  }
  
  @NativeType("void *")
  public static <T extends org.lwjgl.system.CustomBuffer<T>> long memset(@NativeType("void *") T dest, @NativeType("int") int c) {
    return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) * (long)dest.sizeof());
  }
  
  @NativeType("void *")
  public static <T extends org.lwjgl.system.CustomBuffer<T>> long memcpy(@NativeType("void *") T dest, @NativeType("void const *") T src) {
    if (Checks.CHECKS)
      Checks.check(src, dest.remaining()); 
    return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining() * (long)src.sizeof());
  }
  
  @NativeType("void *")
  public static <T extends org.lwjgl.system.CustomBuffer<T>> long memmove(@NativeType("void *") T dest, @NativeType("void const *") T src) {
    if (Checks.CHECKS)
      Checks.check(src, dest.remaining()); 
    return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining() * (long)src.sizeof());
  }
  
  public static native long nmemset(long paramLong1, int paramInt, long paramLong2);
  
  public static native long nmemcpy(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long nmemmove(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long nmemset(byte[] paramArrayOfbyte, int paramInt, long paramLong);
  
  public static native long nmemset(short[] paramArrayOfshort, int paramInt, long paramLong);
  
  public static native long nmemset(int[] paramArrayOfint, int paramInt, long paramLong);
  
  public static native long nmemset(long[] paramArrayOflong, int paramInt, long paramLong);
  
  public static native long nmemset(float[] paramArrayOffloat, int paramInt, long paramLong);
  
  public static native long nmemset(double[] paramArrayOfdouble, int paramInt, long paramLong);
  
  public static native long nmemcpy(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, long paramLong);
  
  public static native long nmemcpy(short[] paramArrayOfshort1, short[] paramArrayOfshort2, long paramLong);
  
  public static native long nmemcpy(int[] paramArrayOfint1, int[] paramArrayOfint2, long paramLong);
  
  public static native long nmemcpy(long[] paramArrayOflong1, long[] paramArrayOflong2, long paramLong);
  
  public static native long nmemcpy(float[] paramArrayOffloat1, float[] paramArrayOffloat2, long paramLong);
  
  public static native long nmemcpy(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, long paramLong);
  
  public static native long nmemmove(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, long paramLong);
  
  public static native long nmemmove(short[] paramArrayOfshort1, short[] paramArrayOfshort2, long paramLong);
  
  public static native long nmemmove(int[] paramArrayOfint1, int[] paramArrayOfint2, long paramLong);
  
  public static native long nmemmove(long[] paramArrayOflong1, long[] paramArrayOflong2, long paramLong);
  
  public static native long nmemmove(float[] paramArrayOffloat1, float[] paramArrayOffloat2, long paramLong);
  
  public static native long nmemmove(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, long paramLong);
}
