package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WinBase {
  public static final int FALSE = 0;
  
  public static final int TRUE = 1;
  
  static {
    Library.initialize();
  }
  
  protected WinBase() {
    throw new UnsupportedOperationException();
  }
  
  @NativeType("HMODULE")
  public static long GetModuleHandle(@Nullable @NativeType("LPCTSTR") ByteBuffer moduleName) {
    if (Checks.CHECKS)
      Checks.checkNT2Safe(moduleName); 
    return nGetModuleHandle(MemoryUtil.memAddressSafe(moduleName));
  }
  
  @NativeType("HMODULE")
  public static long GetModuleHandle(@Nullable @NativeType("LPCTSTR") CharSequence moduleName) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nUTF16Safe(moduleName, true);
      long moduleNameEncoded = (moduleName == null) ? 0L : stack.getPointerAddress();
      return nGetModuleHandle(moduleNameEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("HMODULE")
  public static long LoadLibrary(@NativeType("LPCTSTR") ByteBuffer name) {
    if (Checks.CHECKS)
      Checks.checkNT2(name); 
    return nLoadLibrary(MemoryUtil.memAddress(name));
  }
  
  @NativeType("HMODULE")
  public static long LoadLibrary(@NativeType("LPCTSTR") CharSequence name) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nUTF16(name, true);
      long nameEncoded = stack.getPointerAddress();
      return nLoadLibrary(nameEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("FARPROC")
  public static long GetProcAddress(@NativeType("HMODULE") long handle, @NativeType("LPCSTR") ByteBuffer name) {
    if (Checks.CHECKS) {
      Checks.check(handle);
      Checks.checkNT1(name);
    } 
    return nGetProcAddress(handle, MemoryUtil.memAddress(name));
  }
  
  @NativeType("FARPROC")
  public static long GetProcAddress(@NativeType("HMODULE") long handle, @NativeType("LPCSTR") CharSequence name) {
    if (Checks.CHECKS)
      Checks.check(handle); 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(name, true);
      long nameEncoded = stack.getPointerAddress();
      return nGetProcAddress(handle, nameEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("BOOL")
  public static boolean FreeLibrary(@NativeType("HMODULE") long handle) {
    if (Checks.CHECKS)
      Checks.check(handle); 
    return (nFreeLibrary(handle) != 0);
  }
  
  @NativeType("DWORD")
  public static native int GetLastError();
  
  @NativeType("DWORD")
  public static native int getLastError();
  
  public static native long nGetModuleHandle(long paramLong);
  
  public static native long nLoadLibrary(long paramLong);
  
  public static native long nGetProcAddress(long paramLong1, long paramLong2);
  
  public static native int nFreeLibrary(long paramLong);
}
