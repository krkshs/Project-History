package org.lwjgl.system;

final class MemoryAccessJNI {
  static {
    Library.initialize();
  }
  
  private MemoryAccessJNI() {
    throw new UnsupportedOperationException();
  }
  
  static final long malloc = malloc();
  
  static final long calloc = calloc();
  
  static final long realloc = realloc();
  
  static final long free = free();
  
  static final long aligned_alloc = aligned_alloc();
  
  static final long aligned_free = aligned_free();
  
  @NativeType("int8_t")
  static byte getByte(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetByte(ptr);
  }
  
  @NativeType("int16_t")
  static short getShort(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetShort(ptr);
  }
  
  @NativeType("int32_t")
  static int getInt(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetInt(ptr);
  }
  
  @NativeType("int64_t")
  static long getLong(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetLong(ptr);
  }
  
  static float getFloat(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetFloat(ptr);
  }
  
  static double getDouble(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetDouble(ptr);
  }
  
  @NativeType("intptr_t")
  static long getAddress(@NativeType("void *") long ptr) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    return ngetAddress(ptr);
  }
  
  static void putByte(@NativeType("void *") long ptr, @NativeType("int8_t") byte value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputByte(ptr, value);
  }
  
  static void putShort(@NativeType("void *") long ptr, @NativeType("int16_t") short value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputShort(ptr, value);
  }
  
  static void putInt(@NativeType("void *") long ptr, @NativeType("int32_t") int value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputInt(ptr, value);
  }
  
  static void putLong(@NativeType("void *") long ptr, @NativeType("int64_t") long value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputLong(ptr, value);
  }
  
  static void putFloat(@NativeType("void *") long ptr, float value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputFloat(ptr, value);
  }
  
  static void putDouble(@NativeType("void *") long ptr, double value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputDouble(ptr, value);
  }
  
  static void putAddress(@NativeType("void *") long ptr, @NativeType("intptr_t") long value) {
    if (Checks.CHECKS)
      Checks.check(ptr); 
    nputAddress(ptr, value);
  }
  
  static native int getPointerSize();
  
  @NativeType("void * (*) (size_t)")
  private static native long malloc();
  
  @NativeType("void * (*) (size_t, size_t)")
  private static native long calloc();
  
  @NativeType("void * (*) (void *, size_t)")
  private static native long realloc();
  
  @NativeType("void (*) (void *)")
  private static native long free();
  
  @NativeType("void * (*) (size_t, size_t)")
  private static native long aligned_alloc();
  
  @NativeType("void (*) (void *)")
  private static native long aligned_free();
  
  static native byte ngetByte(long paramLong);
  
  static native short ngetShort(long paramLong);
  
  static native int ngetInt(long paramLong);
  
  static native long ngetLong(long paramLong);
  
  static native float ngetFloat(long paramLong);
  
  static native double ngetDouble(long paramLong);
  
  static native long ngetAddress(long paramLong);
  
  static native void nputByte(long paramLong, byte paramByte);
  
  static native void nputShort(long paramLong, short paramShort);
  
  static native void nputInt(long paramLong, int paramInt);
  
  static native void nputLong(long paramLong1, long paramLong2);
  
  static native void nputFloat(long paramLong, float paramFloat);
  
  static native void nputDouble(long paramLong, double paramDouble);
  
  static native void nputAddress(long paramLong1, long paramLong2);
}
