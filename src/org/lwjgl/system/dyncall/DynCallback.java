package org.lwjgl.system.dyncall;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynCallback {
  public static final char DCB_SIGCHAR_CC_PREFIX = '_';
  
  public static final char DCB_SIGCHAR_CC_ELLIPSIS = 'e';
  
  public static final char DCB_SIGCHAR_CC_STDCALL = 's';
  
  public static final char DCB_SIGCHAR_CC_FASTCALL_GNU = 'f';
  
  public static final char DCB_SIGCHAR_CC_FASTCALL_MS = 'F';
  
  public static final char DCB_SIGCHAR_CC_THISCALL_MS = '+';
  
  static {
    Library.initialize();
  }
  
  protected DynCallback() {
    throw new UnsupportedOperationException();
  }
  
  @NativeType("DCCallback *")
  public static long dcbNewCallback(@NativeType("char const *") ByteBuffer signature, @NativeType("DCCallbackHandler *") long funcptr, @NativeType("void *") long userdata) {
    if (Checks.CHECKS) {
      Checks.checkNT1(signature);
      Checks.check(funcptr);
      Checks.check(userdata);
    } 
    return ndcbNewCallback(MemoryUtil.memAddress(signature), funcptr, userdata);
  }
  
  @NativeType("DCCallback *")
  public static long dcbNewCallback(@NativeType("char const *") CharSequence signature, @NativeType("DCCallbackHandler *") long funcptr, @NativeType("void *") long userdata) {
    if (Checks.CHECKS) {
      Checks.check(funcptr);
      Checks.check(userdata);
    } 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(signature, true);
      long signatureEncoded = stack.getPointerAddress();
      return ndcbNewCallback(signatureEncoded, funcptr, userdata);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void dcbInitCallback(@NativeType("DCCallback *") long pcb, @NativeType("char const *") ByteBuffer signature, @NativeType("DCCallbackHandler *") long handler, @NativeType("void *") long userdata) {
    if (Checks.CHECKS) {
      Checks.check(pcb);
      Checks.checkNT1(signature);
      Checks.check(handler);
      Checks.check(userdata);
    } 
    ndcbInitCallback(pcb, MemoryUtil.memAddress(signature), handler, userdata);
  }
  
  public static void dcbInitCallback(@NativeType("DCCallback *") long pcb, @NativeType("char const *") CharSequence signature, @NativeType("DCCallbackHandler *") long handler, @NativeType("void *") long userdata) {
    if (Checks.CHECKS) {
      Checks.check(pcb);
      Checks.check(handler);
      Checks.check(userdata);
    } 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(signature, true);
      long signatureEncoded = stack.getPointerAddress();
      ndcbInitCallback(pcb, signatureEncoded, handler, userdata);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void dcbFreeCallback(@NativeType("DCCallback *") long pcb) {
    if (Checks.CHECKS)
      Checks.check(pcb); 
    ndcbFreeCallback(pcb);
  }
  
  @NativeType("void *")
  public static long dcbGetUserData(@NativeType("DCCallback *") long pcb) {
    if (Checks.CHECKS)
      Checks.check(pcb); 
    return ndcbGetUserData(pcb);
  }
  
  @NativeType("DCbool")
  public static boolean dcbArgBool(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return (ndcbArgBool(args) != 0);
  }
  
  @NativeType("DCchar")
  public static byte dcbArgChar(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgChar(args);
  }
  
  @NativeType("DCshort")
  public static short dcbArgShort(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgShort(args);
  }
  
  @NativeType("DCint")
  public static int dcbArgInt(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgInt(args);
  }
  
  @NativeType("DClong")
  public static long dcbArgLong(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgLong(args);
  }
  
  @NativeType("DClonglong")
  public static long dcbArgLongLong(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgLongLong(args);
  }
  
  @NativeType("DCchar")
  public static byte dcbArgUChar(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgUChar(args);
  }
  
  @NativeType("DCshort")
  public static short dcbArgUShort(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgUShort(args);
  }
  
  @NativeType("DCint")
  public static int dcbArgUInt(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgUInt(args);
  }
  
  @NativeType("DClong")
  public static long dcbArgULong(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgULong(args);
  }
  
  @NativeType("DClonglong")
  public static long dcbArgULongLong(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgULongLong(args);
  }
  
  @NativeType("DCfloat")
  public static float dcbArgFloat(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgFloat(args);
  }
  
  @NativeType("DCdouble")
  public static double dcbArgDouble(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgDouble(args);
  }
  
  @NativeType("DCpointer")
  public static long dcbArgPointer(@NativeType("DCArgs *") long args) {
    if (Checks.CHECKS)
      Checks.check(args); 
    return ndcbArgPointer(args);
  }
  
  public static native long ndcbNewCallback(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void ndcbInitCallback(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void ndcbFreeCallback(long paramLong);
  
  public static native long ndcbGetUserData(long paramLong);
  
  public static native int ndcbArgBool(long paramLong);
  
  public static native byte ndcbArgChar(long paramLong);
  
  public static native short ndcbArgShort(long paramLong);
  
  public static native int ndcbArgInt(long paramLong);
  
  public static native long ndcbArgLong(long paramLong);
  
  public static native long ndcbArgLongLong(long paramLong);
  
  public static native byte ndcbArgUChar(long paramLong);
  
  public static native short ndcbArgUShort(long paramLong);
  
  public static native int ndcbArgUInt(long paramLong);
  
  public static native long ndcbArgULong(long paramLong);
  
  public static native long ndcbArgULongLong(long paramLong);
  
  public static native float ndcbArgFloat(long paramLong);
  
  public static native double ndcbArgDouble(long paramLong);
  
  public static native long ndcbArgPointer(long paramLong);
}
