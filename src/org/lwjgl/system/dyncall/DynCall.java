package org.lwjgl.system.dyncall;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynCall {
  public static final int DC_CALL_C_DEFAULT = 0;
  
  public static final int DC_CALL_C_ELLIPSIS = 100;
  
  public static final int DC_CALL_C_ELLIPSIS_VARARGS = 101;
  
  public static final int DC_CALL_C_X86_CDECL = 1;
  
  public static final int DC_CALL_C_X86_WIN32_STD = 2;
  
  public static final int DC_CALL_C_X86_WIN32_FAST_MS = 3;
  
  public static final int DC_CALL_C_X86_WIN32_FAST_GNU = 4;
  
  public static final int DC_CALL_C_X86_WIN32_THIS_MS = 5;
  
  public static final int DC_CALL_C_X86_WIN32_THIS_GNU = 6;
  
  public static final int DC_CALL_C_X64_WIN64 = 7;
  
  public static final int DC_CALL_C_X64_SYSV = 8;
  
  public static final int DC_CALL_C_PPC32_DARWIN = 9;
  
  public static final int DC_CALL_C_PPC32_OSX = 9;
  
  public static final int DC_CALL_C_ARM_ARM_EABI = 10;
  
  public static final int DC_CALL_C_ARM_THUMB_EABI = 11;
  
  public static final int DC_CALL_C_ARM_ARMHF = 30;
  
  public static final int DC_CALL_C_MIPS32_EABI = 12;
  
  public static final int DC_CALL_C_PPC32_SYSV = 13;
  
  public static final int DC_CALL_C_PPC32_LINUX = 13;
  
  public static final int DC_CALL_C_ARM_ARM = 14;
  
  public static final int DC_CALL_C_ARM_THUMB = 15;
  
  public static final int DC_CALL_C_MIPS32_O32 = 16;
  
  public static final int DC_CALL_C_MIPS64_N32 = 17;
  
  public static final int DC_CALL_C_MIPS64_N64 = 18;
  
  public static final int DC_CALL_C_X86_PLAN9 = 19;
  
  public static final int DC_CALL_C_SPARC32 = 20;
  
  public static final int DC_CALL_C_SPARC64 = 21;
  
  public static final int DC_CALL_C_ARM64 = 22;
  
  public static final int DC_CALL_C_PPC64 = 23;
  
  public static final int DC_CALL_C_PPC64_LINUX = 23;
  
  public static final int DC_CALL_SYS_DEFAULT = 200;
  
  public static final int DC_CALL_SYS_X86_INT80H_LINUX = 201;
  
  public static final int DC_CALL_SYS_X86_INT80H_BSD = 202;
  
  public static final int DC_CALL_SYS_PPC32 = 210;
  
  public static final int DC_CALL_SYS_PPC64 = 211;
  
  public static final int DC_ERROR_NONE = 0;
  
  public static final int DC_ERROR_UNSUPPORTED_MODE = -1;
  
  public static final int DC_TRUE = 1;
  
  public static final int DC_FALSE = 0;
  
  public static final char DC_SIGCHAR_VOID = 'v';
  
  public static final char DC_SIGCHAR_BOOL = 'B';
  
  public static final char DC_SIGCHAR_CHAR = 'c';
  
  public static final char DC_SIGCHAR_UCHAR = 'C';
  
  public static final char DC_SIGCHAR_SHORT = 's';
  
  public static final char DC_SIGCHAR_USHORT = 'S';
  
  public static final char DC_SIGCHAR_INT = 'i';
  
  public static final char DC_SIGCHAR_UINT = 'I';
  
  public static final char DC_SIGCHAR_LONG = 'j';
  
  public static final char DC_SIGCHAR_ULONG = 'J';
  
  public static final char DC_SIGCHAR_LONGLONG = 'l';
  
  public static final char DC_SIGCHAR_ULONGLONG = 'L';
  
  public static final char DC_SIGCHAR_FLOAT = 'f';
  
  public static final char DC_SIGCHAR_DOUBLE = 'd';
  
  public static final char DC_SIGCHAR_POINTER = 'p';
  
  public static final char DC_SIGCHAR_STRING = 'Z';
  
  public static final char DC_SIGCHAR_STRUCT = 'T';
  
  public static final char DC_SIGCHAR_ENDARG = ')';
  
  static {
    Library.initialize();
  }
  
  protected DynCall() {
    throw new UnsupportedOperationException();
  }
  
  public static void dcFree(@NativeType("DCCallVM *") long vm) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcFree(vm);
  }
  
  public static void dcReset(@NativeType("DCCallVM *") long vm) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcReset(vm);
  }
  
  public static void dcMode(@NativeType("DCCallVM *") long vm, @NativeType("DCint") int mode) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcMode(vm, mode);
  }
  
  public static void dcArgBool(@NativeType("DCCallVM *") long vm, @NativeType("DCbool") boolean value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgBool(vm, value ? 1 : 0);
  }
  
  public static void dcArgChar(@NativeType("DCCallVM *") long vm, @NativeType("DCchar") byte value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgChar(vm, value);
  }
  
  public static void dcArgShort(@NativeType("DCCallVM *") long vm, @NativeType("DCshort") short value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgShort(vm, value);
  }
  
  public static void dcArgInt(@NativeType("DCCallVM *") long vm, @NativeType("DCint") int value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgInt(vm, value);
  }
  
  public static void dcArgLong(@NativeType("DCCallVM *") long vm, @NativeType("DClong") long value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgLong(vm, value);
  }
  
  public static void dcArgLongLong(@NativeType("DCCallVM *") long vm, @NativeType("DClonglong") long value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgLongLong(vm, value);
  }
  
  public static void dcArgFloat(@NativeType("DCCallVM *") long vm, @NativeType("DCfloat") float value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgFloat(vm, value);
  }
  
  public static void dcArgDouble(@NativeType("DCCallVM *") long vm, @NativeType("DCdouble") double value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgDouble(vm, value);
  }
  
  public static void dcArgPointer(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long value) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    ndcArgPointer(vm, value);
  }
  
  public static void dcArgStruct(@NativeType("DCCallVM *") long vm, @NativeType("DCstruct *") long s, @NativeType("DCpointer") long value) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(s);
      Checks.check(value);
    } 
    ndcArgStruct(vm, s, value);
  }
  
  @NativeType("DCvoid")
  public static void dcCallVoid(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    ndcCallVoid(vm, funcptr);
  }
  
  @NativeType("DCbool")
  public static boolean dcCallBool(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return (ndcCallBool(vm, funcptr) != 0);
  }
  
  @NativeType("DCchar")
  public static byte dcCallChar(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallChar(vm, funcptr);
  }
  
  @NativeType("DCshort")
  public static short dcCallShort(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallShort(vm, funcptr);
  }
  
  @NativeType("DCint")
  public static int dcCallInt(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallInt(vm, funcptr);
  }
  
  @NativeType("DClong")
  public static long dcCallLong(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallLong(vm, funcptr);
  }
  
  @NativeType("DClonglong")
  public static long dcCallLongLong(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallLongLong(vm, funcptr);
  }
  
  @NativeType("DCfloat")
  public static float dcCallFloat(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallFloat(vm, funcptr);
  }
  
  @NativeType("DCdouble")
  public static double dcCallDouble(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallDouble(vm, funcptr);
  }
  
  @NativeType("DCpointer")
  public static long dcCallPointer(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
    } 
    return ndcCallPointer(vm, funcptr);
  }
  
  public static void dcCallStruct(@NativeType("DCCallVM *") long vm, @NativeType("DCpointer") long funcptr, @NativeType("DCstruct *") long s, @NativeType("DCpointer") long returnValue) {
    if (Checks.CHECKS) {
      Checks.check(vm);
      Checks.check(funcptr);
      Checks.check(s);
      Checks.check(returnValue);
    } 
    ndcCallStruct(vm, funcptr, s, returnValue);
  }
  
  @NativeType("DCint")
  public static int dcGetError(@NativeType("DCCallVM *") long vm) {
    if (Checks.CHECKS)
      Checks.check(vm); 
    return ndcGetError(vm);
  }
  
  public static void dcStructField(@NativeType("DCstruct *") long s, @NativeType("DCint") int type, @NativeType("DCint") int alignment, @NativeType("DCsize") long arrayLength) {
    if (Checks.CHECKS)
      Checks.check(s); 
    ndcStructField(s, type, alignment, arrayLength);
  }
  
  public static void dcSubStruct(@NativeType("DCstruct *") long s, @NativeType("DCsize") long fieldCount, @NativeType("DCint") int alignment, @NativeType("DCsize") long arrayLength) {
    if (Checks.CHECKS)
      Checks.check(s); 
    ndcSubStruct(s, fieldCount, alignment, arrayLength);
  }
  
  public static void dcCloseStruct(@NativeType("DCstruct *") long s) {
    if (Checks.CHECKS)
      Checks.check(s); 
    ndcCloseStruct(s);
  }
  
  @NativeType("DCsize")
  public static long dcStructSize(@NativeType("DCstruct *") long s) {
    if (Checks.CHECKS)
      Checks.check(s); 
    return ndcStructSize(s);
  }
  
  @NativeType("DCsize")
  public static long dcStructAlignment(@NativeType("DCstruct *") long s) {
    if (Checks.CHECKS)
      Checks.check(s); 
    return ndcStructAlignment(s);
  }
  
  public static void dcFreeStruct(@NativeType("DCstruct *") long s) {
    if (Checks.CHECKS)
      Checks.check(s); 
    ndcFreeStruct(s);
  }
  
  @NativeType("DCstruct *")
  public static long dcDefineStruct(@NativeType("char const *") ByteBuffer signature) {
    if (Checks.CHECKS)
      Checks.checkNT1(signature); 
    return ndcDefineStruct(MemoryUtil.memAddress(signature));
  }
  
  @NativeType("DCstruct *")
  public static long dcDefineStruct(@NativeType("char const *") CharSequence signature) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(signature, true);
      long signatureEncoded = stack.getPointerAddress();
      return ndcDefineStruct(signatureEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("DCCallVM *")
  public static native long dcNewCallVM(@NativeType("DCsize") long paramLong);
  
  public static native void ndcFree(long paramLong);
  
  public static native void ndcReset(long paramLong);
  
  public static native void ndcMode(long paramLong, int paramInt);
  
  public static native void ndcArgBool(long paramLong, int paramInt);
  
  public static native void ndcArgChar(long paramLong, byte paramByte);
  
  public static native void ndcArgShort(long paramLong, short paramShort);
  
  public static native void ndcArgInt(long paramLong, int paramInt);
  
  public static native void ndcArgLong(long paramLong1, long paramLong2);
  
  public static native void ndcArgLongLong(long paramLong1, long paramLong2);
  
  public static native void ndcArgFloat(long paramLong, float paramFloat);
  
  public static native void ndcArgDouble(long paramLong, double paramDouble);
  
  public static native void ndcArgPointer(long paramLong1, long paramLong2);
  
  public static native void ndcArgStruct(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void ndcCallVoid(long paramLong1, long paramLong2);
  
  public static native int ndcCallBool(long paramLong1, long paramLong2);
  
  public static native byte ndcCallChar(long paramLong1, long paramLong2);
  
  public static native short ndcCallShort(long paramLong1, long paramLong2);
  
  public static native int ndcCallInt(long paramLong1, long paramLong2);
  
  public static native long ndcCallLong(long paramLong1, long paramLong2);
  
  public static native long ndcCallLongLong(long paramLong1, long paramLong2);
  
  public static native float ndcCallFloat(long paramLong1, long paramLong2);
  
  public static native double ndcCallDouble(long paramLong1, long paramLong2);
  
  public static native long ndcCallPointer(long paramLong1, long paramLong2);
  
  public static native void ndcCallStruct(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int ndcGetError(long paramLong);
  
  @NativeType("DCstruct *")
  public static native long dcNewStruct(@NativeType("DCsize") long paramLong, @NativeType("DCint") int paramInt);
  
  public static native void ndcStructField(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void ndcSubStruct(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native void ndcCloseStruct(long paramLong);
  
  public static native long ndcStructSize(long paramLong);
  
  public static native long ndcStructAlignment(long paramLong);
  
  public static native void ndcFreeStruct(long paramLong);
  
  public static native long ndcDefineStruct(long paramLong);
}
