package org.lwjgl.system.dyncall;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynLoad {
  static {
    Library.initialize();
  }
  
  protected DynLoad() {
    throw new UnsupportedOperationException();
  }
  
  @NativeType("DLLib *")
  public static long dlLoadLibrary(@NativeType("char const *") ByteBuffer libpath) {
    if (Checks.CHECKS)
      Checks.checkNT1(libpath); 
    return ndlLoadLibrary(MemoryUtil.memAddress(libpath));
  }
  
  @NativeType("DLLib *")
  public static long dlLoadLibrary(@NativeType("char const *") CharSequence libpath) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(libpath, true);
      long libpathEncoded = stack.getPointerAddress();
      return ndlLoadLibrary(libpathEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void dlFreeLibrary(@NativeType("DLLib *") long pLib) {
    if (Checks.CHECKS)
      Checks.check(pLib); 
    ndlFreeLibrary(pLib);
  }
  
  @NativeType("void *")
  public static long dlFindSymbol(@NativeType("DLLib *") long pLib, @NativeType("char const *") ByteBuffer pSymbolName) {
    if (Checks.CHECKS) {
      Checks.check(pLib);
      Checks.checkNT1(pSymbolName);
    } 
    return ndlFindSymbol(pLib, MemoryUtil.memAddress(pSymbolName));
  }
  
  @NativeType("void *")
  public static long dlFindSymbol(@NativeType("DLLib *") long pLib, @NativeType("char const *") CharSequence pSymbolName) {
    if (Checks.CHECKS)
      Checks.check(pLib); 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(pSymbolName, true);
      long pSymbolNameEncoded = stack.getPointerAddress();
      return ndlFindSymbol(pLib, pSymbolNameEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static int dlGetLibraryPath(@NativeType("DLLib *") long pLib, @NativeType("char *") ByteBuffer sOut) {
    if (Checks.CHECKS)
      Checks.check(pLib); 
    return ndlGetLibraryPath(pLib, MemoryUtil.memAddress(sOut), sOut.remaining());
  }
  
  @NativeType("int")
  public static String dlGetLibraryPath(@NativeType("DLLib *") long pLib, int bufSize) {
    if (Checks.CHECKS)
      Checks.check(pLib); 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      ByteBuffer sOut = stack.malloc(bufSize);
      int __result = ndlGetLibraryPath(pLib, MemoryUtil.memAddress(sOut), bufSize);
      return MemoryUtil.memASCII(sOut, __result - 1);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("DLSyms *")
  public static long dlSymsInit(@NativeType("char const *") ByteBuffer libPath) {
    if (Checks.CHECKS)
      Checks.checkNT1(libPath); 
    return ndlSymsInit(MemoryUtil.memAddress(libPath));
  }
  
  @NativeType("DLSyms *")
  public static long dlSymsInit(@NativeType("char const *") CharSequence libPath) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(libPath, true);
      long libPathEncoded = stack.getPointerAddress();
      return ndlSymsInit(libPathEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void dlSymsCleanup(@NativeType("DLSyms *") long pSyms) {
    if (Checks.CHECKS)
      Checks.check(pSyms); 
    ndlSymsCleanup(pSyms);
  }
  
  public static int dlSymsCount(@NativeType("DLSyms *") long pSyms) {
    if (Checks.CHECKS)
      Checks.check(pSyms); 
    return ndlSymsCount(pSyms);
  }
  
  @Nullable
  @NativeType("char const *")
  public static String dlSymsName(@NativeType("DLSyms *") long pSyms, int index) {
    if (Checks.CHECKS)
      Checks.check(pSyms); 
    long __result = ndlSymsName(pSyms, index);
    return MemoryUtil.memASCIISafe(__result);
  }
  
  @Nullable
  @NativeType("char const *")
  public static String dlSymsNameFromValue(@NativeType("DLSyms *") long pSyms, @NativeType("void *") long value) {
    if (Checks.CHECKS) {
      Checks.check(pSyms);
      Checks.check(value);
    } 
    long __result = ndlSymsNameFromValue(pSyms, value);
    return MemoryUtil.memASCIISafe(__result);
  }
  
  public static native long ndlLoadLibrary(long paramLong);
  
  public static native void ndlFreeLibrary(long paramLong);
  
  public static native long ndlFindSymbol(long paramLong1, long paramLong2);
  
  public static native int ndlGetLibraryPath(long paramLong1, long paramLong2, int paramInt);
  
  public static native long ndlSymsInit(long paramLong);
  
  public static native void ndlSymsCleanup(long paramLong);
  
  public static native int ndlSymsCount(long paramLong);
  
  public static native long ndlSymsName(long paramLong, int paramInt);
  
  public static native long ndlSymsNameFromValue(long paramLong1, long paramLong2);
}
