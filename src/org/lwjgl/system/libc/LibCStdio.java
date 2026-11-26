package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCStdio {
  static {
    Library.initialize();
  }
  
  protected LibCStdio() {
    throw new UnsupportedOperationException();
  }
  
  public static final long sscanf = sscanf();
  
  public static int vsscanf(@NativeType("char const *") ByteBuffer buffer, @NativeType("char const *") ByteBuffer format, @NativeType("va_list") long vlist) {
    if (Checks.CHECKS) {
      Checks.checkNT1(buffer);
      Checks.checkNT1(format);
      Checks.check(vlist);
    } 
    return nvsscanf(MemoryUtil.memAddress(buffer), MemoryUtil.memAddress(format), vlist);
  }
  
  public static int vsscanf(@NativeType("char const *") CharSequence buffer, @NativeType("char const *") CharSequence format, @NativeType("va_list") long vlist) {
    if (Checks.CHECKS)
      Checks.check(vlist); 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(buffer, true);
      long bufferEncoded = stack.getPointerAddress();
      stack.nASCII(format, true);
      long formatEncoded = stack.getPointerAddress();
      return nvsscanf(bufferEncoded, formatEncoded, vlist);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static final long sprintf = sprintf();
  
  public static final long snprintf = snprintf();
  
  public static int vsnprintf(@Nullable @NativeType("char *") ByteBuffer buffer, @NativeType("char const *") ByteBuffer format, @NativeType("va_list") long vlist) {
    if (Checks.CHECKS) {
      Checks.checkNT1(format);
      Checks.check(vlist);
    } 
    return nvsnprintf(MemoryUtil.memAddressSafe(buffer), (long)Checks.remainingSafe(buffer), MemoryUtil.memAddress(format), vlist);
  }
  
  public static int vsnprintf(@Nullable @NativeType("char *") ByteBuffer buffer, @NativeType("char const *") CharSequence format, @NativeType("va_list") long vlist) {
    if (Checks.CHECKS)
      Checks.check(vlist); 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(format, true);
      long formatEncoded = stack.getPointerAddress();
      return nvsnprintf(MemoryUtil.memAddressSafe(buffer), (long)Checks.remainingSafe(buffer), formatEncoded, vlist);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("void *")
  private static native long sscanf();
  
  public static native int nvsscanf(long paramLong1, long paramLong2, long paramLong3);
  
  @NativeType("void *")
  private static native long sprintf();
  
  @NativeType("void *")
  private static native long snprintf();
  
  public static native int nvsnprintf(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
}
