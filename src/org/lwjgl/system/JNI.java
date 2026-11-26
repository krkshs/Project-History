package org.lwjgl.system;

import javax.annotation.Nullable;

public final class JNI {
  public static native byte invokePB(byte paramByte, long paramLong1, long paramLong2);
  
  public static native double invokeD(long paramLong);
  
  public static native double invokeD(int paramInt, long paramLong);
  
  public static native double invokePD(long paramLong1, long paramLong2);
  
  public static native double invokePD(long paramLong1, int paramInt, long paramLong2);
  
  public static native double invokePPD(long paramLong1, long paramLong2, long paramLong3);
  
  public static native float invokeF(int paramInt, long paramLong);
  
  public static native float invokePF(long paramLong1, long paramLong2);
  
  public static native float invokePF(long paramLong1, int paramInt, long paramLong2);
  
  public static native float invokePF(long paramLong1, float paramFloat1, float paramFloat2, long paramLong2);
  
  public static native float invokePPF(long paramLong1, long paramLong2, long paramLong3);
  
  public static native float invokePPF(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native float invokePPF(long paramLong1, float paramFloat, long paramLong2, int paramInt, long paramLong3);
  
  public static native int invokeI(long paramLong);
  
  public static native int invokeI(int paramInt, long paramLong);
  
  public static native int invokeI(boolean paramBoolean, long paramLong);
  
  public static native int invokeI(int paramInt1, int paramInt2, long paramLong);
  
  public static native int invokeI(int paramInt, short paramShort, long paramLong);
  
  public static native int invokeI(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native int invokePI(long paramLong1, long paramLong2);
  
  public static native int invokePI(int paramInt, long paramLong1, long paramLong2);
  
  public static native int invokePI(long paramLong1, int paramInt, long paramLong2);
  
  public static native int invokePI(long paramLong1, short paramShort, long paramLong2);
  
  public static native int invokePI(short paramShort, long paramLong1, long paramLong2);
  
  public static native int invokePI(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native int invokePI(short paramShort, long paramLong1, byte paramByte, long paramLong2);
  
  public static native int invokePI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native int invokePJI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int invokePNI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int invokePPI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int invokePPI(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native int invokePPI(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native int invokePPI(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native int invokePPI(long paramLong1, long paramLong2, short paramShort, long paramLong3);
  
  public static native int invokePPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native int invokePPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, long paramLong3);
  
  public static native int invokePPI(int paramInt1, long paramLong1, int paramInt2, long paramLong2, int paramInt3, boolean paramBoolean, long paramLong3);
  
  public static native int invokePPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2, long paramLong3);
  
  public static native int invokePPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2, int paramInt5, long paramLong3);
  
  public static native int invokePPJI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, int paramInt3, long paramLong4);
  
  public static native int invokePPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3, int paramInt4, long paramLong4);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int invokePNNPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5);
  
  public static native int invokePPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, int paramInt3, long paramLong5);
  
  public static native int invokePPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int invokePJJJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6);
  
  public static native int invokePPPPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int invokePPPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt1, long paramLong4, int paramInt2, int paramInt3, long paramLong5, long paramLong6);
  
  public static native int invokePPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int invokePPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, int paramInt, long paramLong7);
  
  public static native int invokePNNPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int invokePPPPPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5, int paramInt3, long paramLong6, int paramInt4, long paramLong7, int paramInt5, long paramLong8);
  
  public static native int invokePPPPPPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long invokeJ(long paramLong);
  
  public static native long invokePJ(long paramLong1, long paramLong2);
  
  public static native long invokePJ(long paramLong1, int paramInt, long paramLong2);
  
  public static native long invokePJ(long paramLong1, float paramFloat1, int paramInt1, float paramFloat2, int paramInt2, long paramLong2);
  
  public static native long invokePPJ(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long invokePPJ(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native long invokePN(long paramLong1, long paramLong2);
  
  public static native long invokePN(long paramLong1, int paramInt, long paramLong2);
  
  public static native long invokePNPN(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native long invokePNPNPN(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokeP(long paramLong);
  
  public static native long invokeP(int paramInt, long paramLong);
  
  public static native long invokeP(short paramShort, long paramLong);
  
  public static native long invokeP(boolean paramBoolean, long paramLong);
  
  public static native long invokeP(int paramInt1, int paramInt2, long paramLong);
  
  public static native long invokePP(long paramLong1, long paramLong2);
  
  public static native long invokePP(int paramInt, long paramLong1, long paramLong2);
  
  public static native long invokePP(long paramLong1, byte paramByte, long paramLong2);
  
  public static native long invokePP(long paramLong1, double paramDouble, long paramLong2);
  
  public static native long invokePP(long paramLong1, int paramInt, long paramLong2);
  
  public static native long invokePP(short paramShort, long paramLong1, long paramLong2);
  
  public static native long invokePP(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native long invokePP(int paramInt, short paramShort, long paramLong1, long paramLong2);
  
  public static native long invokePP(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native long invokePP(long paramLong1, short paramShort, boolean paramBoolean, long paramLong2);
  
  public static native long invokePP(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native long invokePP(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native long invokeJP(short paramShort1, short paramShort2, short paramShort3, byte paramByte, int paramInt, long paramLong1, long paramLong2);
  
  public static native long invokePP(long paramLong1, int paramInt1, byte paramByte, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, long paramLong2);
  
  public static native long invokePP(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2);
  
  public static native long invokePJP(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long invokePJP(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native long invokePJP(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native long invokePPP(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, byte paramByte, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native long invokePPP(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, int paramInt, byte paramByte, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, long paramLong2, boolean paramBoolean1, boolean paramBoolean2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, long paramLong3);
  
  public static native long invokePPP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2, long paramLong3);
  
  public static native long invokePPP(long paramLong1, short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong2, long paramLong3);
  
  public static native long invokePPP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, long paramLong2, long paramLong3);
  
  public static native long invokePJJP(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPJP(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native long invokePPPP(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4);
  
  public static native long invokeJPPP(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, int paramInt3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, int paramInt3, long paramLong4);
  
  public static native long invokePPPP(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, int paramInt4, long paramLong3, long paramLong4);
  
  public static native long invokePPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, int paramInt3, int paramInt4, long paramLong4);
  
  public static native long invokePPJPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePPPJP(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native long invokePJPPP(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long invokePJPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, int paramInt2, long paramLong5);
  
  public static native long invokePPPJP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt1, long paramLong4, int paramInt2, int paramInt3, long paramLong5);
  
  public static native long invokePPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, int paramInt3, long paramLong5);
  
  public static native long invokePPPPP(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, int paramInt4, long paramLong4, long paramLong5);
  
  public static native long invokePJPJPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokePNNNPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokePPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokePPPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokePPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6);
  
  public static native long invokePPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6);
  
  public static native long invokePPJPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long invokePPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, int paramInt2, int paramInt3, long paramLong6);
  
  public static native long invokePPJJPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, int paramInt, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, long paramLong5, int paramInt2, long paramLong6, int paramInt3, int paramInt4, long paramLong7);
  
  public static native long invokePPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, int paramInt3, int paramInt4, long paramLong7);
  
  public static native long invokePPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native long invokePPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, long paramLong7, int paramInt3, long paramLong8);
  
  public static native long invokePPPPJJPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, long paramLong5, int paramInt2, long paramLong6, int paramInt3, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long invokePPPPPJJPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, long paramLong7, int paramInt3, long paramLong8, long paramLong9);
  
  public static native long invokePPPPPJPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, long paramLong7, int paramInt3, long paramLong8, long paramLong9);
  
  public static native long invokePPPPPJPPP(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt2, int paramInt3, long paramLong6, int paramInt4, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long invokePPPPPJPPP(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt2, int paramInt3, long paramLong6, int paramInt4, int paramInt5, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long invokePPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt1, long paramLong8, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong9);
  
  public static native long invokePPPPPPPPP(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5, long paramLong6, int paramInt3, long paramLong7, long paramLong8, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong9);
  
  public static native long invokePPPPPJJJPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, long paramLong7, long paramLong8, int paramInt2, long paramLong9, long paramLong10);
  
  public static native long invokePPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, long paramLong5, long paramLong6, long paramLong7, long paramLong8, int paramInt2, long paramLong9, long paramLong10);
  
  public static native long invokePPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt1, long paramLong8, int paramInt2, long paramLong9, int paramInt3, long paramLong10);
  
  public static native long invokePPPPPJPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, int paramInt3, long paramLong7, int paramInt4, int paramInt5, long paramLong8, long paramLong9, long paramLong10);
  
  public static native long invokePPPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11);
  
  public static native long invokePPPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt1, long paramLong8, int paramInt2, long paramLong9, long paramLong10, int paramInt3, long paramLong11);
  
  public static native long invokePPPPPJPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, int paramInt3, long paramLong7, long paramLong8, int paramInt4, int paramInt5, long paramLong9, long paramLong10, long paramLong11, long paramLong12);
  
  public static native long invokePPPPPJJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, long paramLong6, int paramInt2, long paramLong7, int paramInt3, long paramLong8, long paramLong9, int paramInt4, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14);
  
  public static native short invokeS(long paramLong);
  
  public static native short invokeS(int paramInt, long paramLong);
  
  public static native short invokeS(int paramInt, short paramShort, long paramLong);
  
  public static native short invokeS(short paramShort, byte paramByte, long paramLong);
  
  public static native short invokeS(short paramShort, boolean paramBoolean, long paramLong);
  
  public static native short invokeS(short paramShort1, short paramShort2, boolean paramBoolean, long paramLong);
  
  public static native short invokeS(short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong);
  
  public static native short invokePS(long paramLong1, long paramLong2);
  
  public static native short invokePS(long paramLong1, short paramShort, long paramLong2);
  
  public static native short invokeJS(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native short invokePS(byte paramByte, long paramLong1, boolean paramBoolean, long paramLong2);
  
  public static native short invokePS(int paramInt, long paramLong1, short paramShort, long paramLong2);
  
  public static native short invokePS(long paramLong1, int paramInt, short paramShort, long paramLong2);
  
  public static native short invokePS(short paramShort1, long paramLong1, short paramShort2, long paramLong2);
  
  public static native short invokeJS(short paramShort1, short paramShort2, int paramInt, long paramLong1, long paramLong2);
  
  public static native short invokeJS(int paramInt1, boolean paramBoolean, short paramShort, int paramInt2, long paramLong1, long paramLong2);
  
  public static native short invokePS(long paramLong1, short paramShort1, short paramShort2, int paramInt1, int paramInt2, long paramLong2);
  
  public static native short invokePS(long paramLong1, short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong2);
  
  public static native short invokePPS(long paramLong1, long paramLong2, short paramShort, long paramLong3);
  
  public static native short invokeJPS(short paramShort1, boolean paramBoolean, short paramShort2, int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native short invokeJPS(short paramShort1, short paramShort2, short paramShort3, boolean paramBoolean, int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native short invokeJPS(short paramShort1, short paramShort2, boolean paramBoolean, short paramShort3, int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native short invokePJPS(long paramLong1, long paramLong2, byte paramByte, long paramLong3, long paramLong4);
  
  public static native short invokePPPS(long paramLong1, long paramLong2, long paramLong3, short paramShort, float paramFloat, long paramLong4);
  
  public static native void invokeV(long paramLong);
  
  public static native void invokeV(double paramDouble, long paramLong);
  
  public static native void invokeV(float paramFloat, long paramLong);
  
  public static native void invokeV(int paramInt, long paramLong);
  
  public static native void invokeV(short paramShort, long paramLong);
  
  public static native void invokeV(byte paramByte, int paramInt, long paramLong);
  
  public static native void invokeV(byte paramByte, boolean paramBoolean, long paramLong);
  
  public static native void invokeV(int paramInt, float paramFloat, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, long paramLong);
  
  public static native void invokeV(int paramInt, short paramShort, long paramLong);
  
  public static native void invokeV(short paramShort, int paramInt, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, long paramLong);
  
  public static native void invokeV(short paramShort, boolean paramBoolean, long paramLong);
  
  public static native void invokeV(byte paramByte, short paramShort, int paramInt, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, double paramDouble, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, float paramFloat, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void invokeV(short paramShort, int paramInt1, int paramInt2, long paramLong);
  
  public static native void invokeV(byte paramByte, short paramShort, int paramInt1, int paramInt2, long paramLong);
  
  public static native void invokeV(byte paramByte, short paramShort1, short paramShort2, int paramInt, long paramLong);
  
  public static native void invokeV(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, int paramInt, boolean paramBoolean, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, short paramShort3, int paramInt, long paramLong);
  
  public static native void invokeV(byte paramByte1, short paramShort, byte paramByte2, int paramInt1, int paramInt2, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native void invokeV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, int paramInt, float paramFloat, byte paramByte, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, short paramShort3, int paramInt, boolean paramBoolean, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, int paramInt, boolean paramBoolean, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, float paramFloat, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6, byte paramByte7, byte paramByte8, byte paramByte9, long paramLong);
  
  public static native void invokeV(short paramShort1, short paramShort2, byte paramByte1, short paramShort3, short paramShort4, short paramShort5, short paramShort6, byte paramByte2, short paramShort7, short paramShort8, short paramShort9, short paramShort10, short paramShort11, short paramShort12, long paramLong);
  
  public static native void invokePV(long paramLong1, long paramLong2);
  
  public static native void invokeJV(long paramLong1, int paramInt, long paramLong2);
  
  public static native void invokePV(byte paramByte, long paramLong1, long paramLong2);
  
  public static native void invokePV(int paramInt, long paramLong1, long paramLong2);
  
  public static native void invokePV(long paramLong1, float paramFloat, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort, long paramLong2);
  
  public static native void invokePV(long paramLong1, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(short paramShort, long paramLong1, long paramLong2);
  
  public static native void invokeJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void invokePV(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void invokePV(long paramLong1, double paramDouble1, double paramDouble2, long paramLong2);
  
  public static native void invokePV(long paramLong1, float paramFloat1, float paramFloat2, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt, double paramDouble, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt, short paramShort, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(short paramShort, int paramInt, long paramLong1, long paramLong2);
  
  public static native void invokePV(short paramShort, long paramLong1, int paramInt, long paramLong2);
  
  public static native void invokePV(short paramShort1, long paramLong1, short paramShort2, long paramLong2);
  
  public static native void invokePV(short paramShort1, short paramShort2, long paramLong1, long paramLong2);
  
  public static native void invokePV(byte paramByte, long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void invokePV(long paramLong1, byte paramByte, short paramShort, int paramInt, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void invokePV(short paramShort1, short paramShort2, byte paramByte, long paramLong1, long paramLong2);
  
  public static native void invokePV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void invokePV(long paramLong1, byte paramByte, short paramShort1, short paramShort2, int paramInt, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, int paramInt, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(long paramLong1, byte paramByte1, short paramShort, byte paramByte2, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void invokePV(long paramLong1, byte paramByte, short paramShort1, int paramInt1, int paramInt2, short paramShort2, long paramLong2);
  
  public static native void invokePV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, int paramInt, short paramShort2, short paramShort3, byte paramByte, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, short paramShort3, int paramInt, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, long paramLong2);
  
  public static native void invokePV(short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong1, short paramShort5, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, int paramInt, boolean paramBoolean, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, short paramShort3, boolean paramBoolean1, boolean paramBoolean2, short paramShort4, int paramInt, long paramLong2);
  
  public static native void invokePV(short paramShort1, byte paramByte, short paramShort2, short paramShort3, short paramShort4, short paramShort5, short paramShort6, short paramShort7, long paramLong1, long paramLong2);
  
  public static native void invokePV(short paramShort1, short paramShort2, byte paramByte, short paramShort3, short paramShort4, short paramShort5, short paramShort6, long paramLong1, short paramShort7, long paramLong2);
  
  public static native void invokePV(short paramShort1, short paramShort2, byte paramByte1, byte paramByte2, short paramShort3, short paramShort4, short paramShort5, short paramShort6, long paramLong1, short paramShort7, long paramLong2);
  
  public static native void invokePV(long paramLong1, short paramShort1, short paramShort2, byte paramByte1, short paramShort3, short paramShort4, short paramShort5, short paramShort6, byte paramByte2, short paramShort7, short paramShort8, short paramShort9, short paramShort10, short paramShort11, short paramShort12, long paramLong2);
  
  public static native void invokePJV(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void invokePJV(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native void invokePJV(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native void invokePPV(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, float paramFloat, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3);
  
  public static native void invokePPV(short paramShort, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, float paramFloat, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native void invokePPV(short paramShort1, short paramShort2, byte paramByte, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void invokePPV(long paramLong1, byte paramByte, long paramLong2, int paramInt1, int paramInt2, short paramShort, long paramLong3);
  
  public static native void invokePPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong3);
  
  public static native void invokePNPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePJJV(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePJPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native void invokePPPV(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native void invokeJJJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, int paramInt, long paramLong3, short paramShort, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean1, boolean paramBoolean2, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, short paramShort, int paramInt, long paramLong3, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, boolean paramBoolean1, boolean paramBoolean2, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, boolean paramBoolean, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4);
  
  public static native void invokePPPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, int paramInt4, boolean paramBoolean, long paramLong4);
  
  public static native void invokePNPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void invokePPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void invokePPPPV(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void invokePPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native void invokePPPPV(long paramLong1, long paramLong2, short paramShort, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void invokePPPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native void invokePPPPPV(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native void invokePPPPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, int paramInt3, long paramLong5, int paramInt4, boolean paramBoolean, long paramLong6);
  
  public static native void invokePPPPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native void invokePPPPPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native boolean invokeZ(long paramLong);
  
  public static native boolean invokeZ(int paramInt, long paramLong);
  
  public static native boolean invokePZ(long paramLong1, long paramLong2);
  
  public static native boolean invokePZ(long paramLong1, int paramInt, long paramLong2);
  
  public static native boolean invokePZ(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native boolean invokeJZ(short paramShort1, boolean paramBoolean, short paramShort2, int paramInt, long paramLong1, long paramLong2);
  
  public static native boolean invokePPZ(long paramLong1, long paramLong2, long paramLong3);
  
  public static native boolean invokePPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native boolean invokePJPZ(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native boolean invokePPPZ(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native boolean invokePPPZ(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4);
  
  public static native boolean invokePPPZ(long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean, int paramInt, long paramLong4);
  
  public static native boolean invokePPPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native boolean invokePPPPZ(long paramLong1, long paramLong2, long paramLong3, byte paramByte, long paramLong4, long paramLong5);
  
  public static native boolean invokePPPPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6);
  
  public static native boolean invokePPPPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, boolean paramBoolean, int paramInt, long paramLong6);
  
  public static native float callF(long paramLong);
  
  public static native float callF(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native float callPF(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native float callPPPF(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callI(long paramLong);
  
  public static native int callI(int paramInt, long paramLong);
  
  public static native int callI(int paramInt, float paramFloat, long paramLong);
  
  public static native int callI(int paramInt1, int paramInt2, long paramLong);
  
  public static native int callI(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native int callJI(long paramLong1, long paramLong2);
  
  public static native int callPI(long paramLong1, long paramLong2);
  
  public static native int callJI(int paramInt, long paramLong1, long paramLong2);
  
  public static native int callJI(long paramLong1, float paramFloat, long paramLong2);
  
  public static native int callJI(long paramLong1, int paramInt, long paramLong2);
  
  public static native int callPI(int paramInt, long paramLong1, long paramLong2);
  
  public static native int callPI(long paramLong1, float paramFloat, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt, long paramLong2);
  
  public static native int callPI(long paramLong1, boolean paramBoolean, long paramLong2);
  
  public static native int callJI(long paramLong1, float paramFloat1, float paramFloat2, long paramLong2);
  
  public static native int callJI(long paramLong1, int paramInt, boolean paramBoolean, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native int callPI(int paramInt1, long paramLong1, int paramInt2, long paramLong2);
  
  public static native int callPI(long paramLong1, float paramFloat1, float paramFloat2, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt, float paramFloat, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native int callJI(long paramLong1, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native int callPI(int paramInt1, long paramLong1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2);
  
  public static native int callPI(int paramInt1, long paramLong1, int paramInt2, int paramInt3, float paramFloat, int paramInt4, long paramLong2);
  
  public static native int callPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2);
  
  public static native int callPI(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat, long paramLong2);
  
  public static native int callJJI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callJPI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callPJI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callJJI(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callJPI(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native int callJPI(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native int callPJI(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native int callPJI(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native int callPPI(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, byte paramByte, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, float paramFloat, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native int callPPI(long paramLong1, short paramShort, long paramLong2, long paramLong3);
  
  public static native int callJPI(long paramLong1, int paramInt, long paramLong2, float paramFloat, long paramLong3);
  
  public static native int callJPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native int callJPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native int callPJI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native int callPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callPPI(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native int callJPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, long paramLong3);
  
  public static native int callJPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3);
  
  public static native int callPPI(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3);
  
  public static native int callPPI(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3);
  
  public static native int callJJI(long paramLong1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong2, long paramLong3);
  
  public static native int callPPI(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2, long paramLong3);
  
  public static native int callPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat, long paramLong3);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, long paramLong3);
  
  public static native int callJJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPJI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callJJPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native int callJPJI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native int callPJPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPJPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int callPPJI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPJI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int callPPJI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native int callPPNI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, byte paramByte, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native int callPPPI(long paramLong1, short paramShort, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callJPJI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4);
  
  public static native int callJPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callPPJI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4);
  
  public static native int callJPJI(long paramLong1, int paramInt1, float paramFloat, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native int callJPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, long paramLong4);
  
  public static native int callPPJI(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, boolean paramBoolean, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong3, long paramLong4);
  
  public static native int callPPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong2, long paramLong3, long paramLong4);
  
  public static native int callJPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPJJJI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPNPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callJPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, byte paramByte, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, short paramShort, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callJPPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callJPPJI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, long paramLong3, boolean paramBoolean, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPPPPI(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19, long paramLong3, long paramLong4, long paramLong5);
  
  public static native int callPJPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPJJJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6);
  
  public static native int callPJPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, byte paramByte, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, short paramShort, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callJJPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPJPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPJPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, int paramInt2, long paramLong6);
  
  public static native int callJPPPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPJPPJI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5, int paramInt3, long paramLong6);
  
  public static native int callPPPPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native int callPJJJJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPJPPPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPJPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPI(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6, long paramLong7, long paramLong8);
  
  public static native int callPPPPPPPI(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, int paramInt2, long paramLong6, long paramLong7, long paramLong8);
  
  public static native int callPPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, int paramInt, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callPPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, long paramLong5, long paramLong6, int paramInt2, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callJPPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10);
  
  public static native int callPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt2, long paramLong8, long paramLong9, long paramLong10);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, int paramInt, long paramLong11, long paramLong12, long paramLong13);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, int paramInt2, long paramLong11, long paramLong12, long paramLong13);
  
  public static native long callJ(long paramLong);
  
  public static native long callJ(int paramInt, long paramLong);
  
  public static native long callJ(int paramInt1, int paramInt2, long paramLong);
  
  public static native long callJ(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, long paramLong);
  
  public static native long callJJ(long paramLong1, long paramLong2);
  
  public static native long callPJ(long paramLong1, long paramLong2);
  
  public static native long callPJ(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native long callPPJ(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long callPPJ(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native long callPN(long paramLong1, long paramLong2);
  
  public static native long callP(long paramLong);
  
  public static native long callP(int paramInt, long paramLong);
  
  public static native long callP(int paramInt1, int paramInt2, long paramLong);
  
  public static native long callP(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native long callPP(long paramLong1, long paramLong2);
  
  public static native long callPP(int paramInt, long paramLong1, long paramLong2);
  
  public static native long callPP(long paramLong1, int paramInt, long paramLong2);
  
  public static native long callPP(int paramInt1, long paramLong1, int paramInt2, long paramLong2);
  
  public static native long callPP(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native long callPNP(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long callPPP(long paramLong1, long paramLong2, long paramLong3);
  
  public static native long callPPP(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native long callPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native long callPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native long callPPP(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native long callPPP(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3);
  
  public static native long callPPP(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3);
  
  public static native long callPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3);
  
  public static native long callPPNP(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long callPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long callPJPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native long callPJPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native long callPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native long callPPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native long callPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native long callPPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4);
  
  public static native long callPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4);
  
  public static native long callPJPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, long paramLong4);
  
  public static native long callPPJPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPPNPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native long callPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long callPPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native long callPJPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long callPPPJPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long callPPPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long callPPPPPPP(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native long callPPPPPPPP(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong3, long paramLong4, long paramLong5, int paramInt6, long paramLong6, long paramLong7, long paramLong8);
  
  public static native long callPJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long callPPJPPPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, int paramInt2, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
  
  public static native long callPJPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11);
  
  public static native long callPPJPPPPPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt2, long paramLong8, long paramLong9, long paramLong10, long paramLong11);
  
  public static native short callS(int paramInt, long paramLong);
  
  public static native short callPS(long paramLong1, long paramLong2);
  
  public static native short callPS(long paramLong1, short paramShort, long paramLong2);
  
  public static native short callPS(short paramShort, long paramLong1, long paramLong2);
  
  public static native short callPS(short paramShort1, long paramLong1, short paramShort2, long paramLong2);
  
  public static native short callPPS(long paramLong1, long paramLong2, long paramLong3);
  
  public static native short callPPS(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native short callPPS(long paramLong1, long paramLong2, short paramShort, long paramLong3);
  
  public static native short callPPS(long paramLong1, short paramShort, long paramLong2, long paramLong3);
  
  public static native short callPPS(short paramShort, long paramLong1, long paramLong2, long paramLong3);
  
  public static native short callPJS(long paramLong1, long paramLong2, short paramShort1, short paramShort2, long paramLong3);
  
  public static native short callPPS(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native short callPPS(long paramLong1, short paramShort1, short paramShort2, long paramLong2, int paramInt, long paramLong3);
  
  public static native short callPPPS(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native short callPPPS(long paramLong1, long paramLong2, short paramShort, long paramLong3, long paramLong4);
  
  public static native short callPPPS(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native short callPPPS(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3, long paramLong4);
  
  public static native short callPPPS(long paramLong1, short paramShort1, short paramShort2, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native short callPPPS(short paramShort1, long paramLong1, short paramShort2, short paramShort3, long paramLong2, short paramShort4, long paramLong3, long paramLong4);
  
  public static native short callPPPPS(long paramLong1, short paramShort, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, long paramLong2, short paramShort1, long paramLong3, short paramShort2, long paramLong4, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, short paramShort1, short paramShort2, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, long paramLong2, short paramShort1, long paramLong3, short paramShort2, long paramLong4, short paramShort3, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, short paramShort1, short paramShort2, long paramLong2, short paramShort3, long paramLong3, long paramLong4, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, long paramLong2, short paramShort1, long paramLong3, short paramShort2, long paramLong4, short paramShort3, short paramShort4, short paramShort5, long paramLong5);
  
  public static native short callPPPPS(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3, short paramShort3, long paramLong4, short paramShort4, short paramShort5, short paramShort6, long paramLong5);
  
  public static native short callPPPPPS(long paramLong1, short paramShort, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native short callPPPPPS(long paramLong1, long paramLong2, long paramLong3, short paramShort1, long paramLong4, short paramShort2, long paramLong5, short paramShort3, long paramLong6);
  
  public static native short callPPPPPS(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3, long paramLong4, short paramShort3, long paramLong5, long paramLong6);
  
  public static native short callPPPPPS(short paramShort1, long paramLong1, short paramShort2, long paramLong2, long paramLong3, long paramLong4, short paramShort3, long paramLong5, long paramLong6);
  
  public static native short callPPPPPS(long paramLong1, long paramLong2, short paramShort1, long paramLong3, short paramShort2, long paramLong4, short paramShort3, long paramLong5, short paramShort4, long paramLong6);
  
  public static native short callPPPPPS(long paramLong1, short paramShort1, short paramShort2, short paramShort3, long paramLong2, short paramShort4, short paramShort5, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native short callPPPPPS(long paramLong1, short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong2, short paramShort5, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native short callPPPPPPPS(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native short callPPPPPPPS(long paramLong1, long paramLong2, short paramShort1, long paramLong3, short paramShort2, long paramLong4, short paramShort3, long paramLong5, short paramShort4, long paramLong6, short paramShort5, long paramLong7, short paramShort6, long paramLong8);
  
  public static native short callPPPPPPPPPS(long paramLong1, short paramShort1, long paramLong2, short paramShort2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10);
  
  public static native void callV(long paramLong);
  
  public static native void callV(byte paramByte, long paramLong);
  
  public static native void callV(double paramDouble, long paramLong);
  
  public static native void callV(float paramFloat, long paramLong);
  
  public static native void callV(int paramInt, long paramLong);
  
  public static native void callV(short paramShort, long paramLong);
  
  public static native void callV(boolean paramBoolean, long paramLong);
  
  public static native void callV(double paramDouble1, double paramDouble2, long paramLong);
  
  public static native void callV(float paramFloat1, float paramFloat2, long paramLong);
  
  public static native void callV(float paramFloat, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt, double paramDouble, long paramLong);
  
  public static native void callV(int paramInt, float paramFloat, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, long paramLong);
  
  public static native void callV(int paramInt, short paramShort, long paramLong);
  
  public static native void callV(int paramInt, boolean paramBoolean, long paramLong);
  
  public static native void callV(short paramShort1, short paramShort2, long paramLong);
  
  public static native void callV(byte paramByte1, byte paramByte2, byte paramByte3, long paramLong);
  
  public static native void callV(double paramDouble1, double paramDouble2, double paramDouble3, long paramLong);
  
  public static native void callV(float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native void callV(int paramInt, double paramDouble1, double paramDouble2, long paramLong);
  
  public static native void callV(int paramInt, float paramFloat1, float paramFloat2, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, double paramDouble, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, short paramShort, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt, short paramShort1, short paramShort2, long paramLong);
  
  public static native void callV(short paramShort1, short paramShort2, short paramShort3, long paramLong);
  
  public static native void callV(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, long paramLong);
  
  public static native void callV(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong);
  
  public static native void callV(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong);
  
  public static native void callV(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, long paramLong);
  
  public static native void callV(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat, int paramInt3, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, double paramDouble, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, float paramFloat, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, long paramLong);
  
  public static native void callV(int paramInt, short paramShort1, short paramShort2, short paramShort3, long paramLong);
  
  public static native void callV(short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong);
  
  public static native void callV(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, long paramLong);
  
  public static native void callV(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, long paramLong);
  
  public static native void callV(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong);
  
  public static native void callV(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, long paramLong);
  
  public static native void callV(int paramInt, short paramShort1, short paramShort2, short paramShort3, short paramShort4, long paramLong);
  
  public static native void callV(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, long paramLong);
  
  public static native void callV(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, long paramLong);
  
  public static native void callV(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3, double paramDouble4, long paramLong);
  
  public static native void callV(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3, float paramFloat4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, long paramLong);
  
  public static native void callV(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6, long paramLong);
  
  public static native void callV(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, long paramLong);
  
  public static native void callV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, long paramLong);
  
  public static native void callJV(long paramLong1, long paramLong2);
  
  public static native void callPV(long paramLong1, long paramLong2);
  
  public static native void callJV(int paramInt, long paramLong1, long paramLong2);
  
  public static native void callJV(long paramLong1, int paramInt, long paramLong2);
  
  public static native void callPV(int paramInt, long paramLong1, long paramLong2);
  
  public static native void callPV(long paramLong1, float paramFloat, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2);
  
  public static native void callPV(long paramLong1, float paramFloat1, float paramFloat2, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt, short paramShort, long paramLong2);
  
  public static native void callJV(int paramInt1, long paramLong1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void callNV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void callPV(int paramInt1, float paramFloat, long paramLong1, int paramInt2, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, boolean paramBoolean, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, long paramLong1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void callPV(long paramLong1, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, boolean paramBoolean, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void callPV(int paramInt1, long paramLong1, int paramInt2, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void callPV(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, boolean paramBoolean, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, int paramInt5, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, long paramLong2);
  
  public static native void callPV(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, int paramInt6, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, int paramInt5, int paramInt6, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong1, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6, long paramLong1, long paramLong2);
  
  public static native void callJV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, int paramInt7, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat, long paramLong2);
  
  public static native void callPV(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double paramDouble3, double paramDouble4, int paramInt4, int paramInt5, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float paramFloat3, float paramFloat4, int paramInt4, int paramInt5, long paramLong1, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong1, long paramLong2);
  
  public static native void callJV(long paramLong1, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, long paramLong1, long paramLong2);
  
  public static native void callPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, long paramLong2);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, long paramLong1, long paramLong2);
  
  public static native void callJPV(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPJV(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callJJV(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPJV(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native void callPJV(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native void callPPV(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native void callPPV(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native void callJJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callJPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callJPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native void callPJV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native void callPJV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void callPJV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void callPPV(int paramInt, long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3);
  
  public static native void callPPV(long paramLong1, int paramInt, float paramFloat, long paramLong2, long paramLong3);
  
  public static native void callPPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native void callPPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3);
  
  public static native void callPJV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, long paramLong3);
  
  public static native void callPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, int paramInt3, long paramLong3);
  
  public static native void callPPV(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, long paramLong3);
  
  public static native void callPPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, long paramLong3);
  
  public static native void callPPV(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, int paramInt5, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, int paramInt5, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, int paramInt6, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, float paramFloat1, float paramFloat2, int paramInt5, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong2, long paramLong3);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, long paramLong2, int paramInt6, int paramInt7, float paramFloat, long paramLong3);
  
  public static native void callPJJV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPNV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callJJJV(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPJJV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, float paramFloat, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3, long paramLong4);
  
  public static native void callJJJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPJJV(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, long paramLong4);
  
  public static native void callPJJV(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, long paramLong1, long paramLong2, long paramLong3, int paramInt2, long paramLong4);
  
  public static native void callPPPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, long paramLong4);
  
  public static native void callPJPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, long paramLong3, long paramLong4);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, long paramLong4);
  
  public static native void callPPJV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, int paramInt3, long paramLong3, long paramLong4);
  
  public static native void callPJJV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, int paramInt3, int paramInt4, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, long paramLong3, long paramLong4);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2, long paramLong3, long paramLong4);
  
  public static native void callPPPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong3, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, long paramLong4);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPJPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPPPNV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callJJJJV(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPJJJV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5);
  
  public static native void callJJJJV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5);
  
  public static native void callPJPPV(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(int paramInt1, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, long paramLong5);
  
  public static native void callPPPPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, int paramInt3, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt3, long paramLong5);
  
  public static native void callPJJPV(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2, int paramInt3, long paramLong4, int paramInt4, long paramLong5);
  
  public static native void callPJPPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, long paramLong3, int paramInt4, long paramLong4, long paramLong5);
  
  public static native void callPPPPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong2, int paramInt5, long paramLong3, int paramInt6, long paramLong4, long paramLong5);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native void callPJJJJV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, int paramInt2, long paramLong6);
  
  public static native void callPPPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native void callPJJJJV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5, int paramInt3, long paramLong6);
  
  public static native void callPJPPPV(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3, int paramInt3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native void callPPPPPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, long paramLong3, int paramInt5, long paramLong4, int paramInt6, long paramLong5, long paramLong6);
  
  public static native void callPPPPPPV(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
  
  public static native void callPPPPPPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
  
  public static native void callPPJJJJJJV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
  
  public static native void callPJJJJJJJJJJJV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, int paramInt1, int paramInt2, int paramInt3, long paramLong13);
  
  public static native boolean callZ(long paramLong);
  
  public static native boolean callZ(int paramInt, long paramLong);
  
  public static native boolean callZ(boolean paramBoolean, long paramLong);
  
  public static native boolean callZ(int paramInt1, int paramInt2, long paramLong);
  
  public static native boolean callZ(int paramInt, float paramFloat1, float paramFloat2, long paramLong);
  
  public static native boolean callZ(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, long paramLong);
  
  public static native boolean callJZ(long paramLong1, long paramLong2);
  
  public static native boolean callPZ(long paramLong1, long paramLong2);
  
  public static native boolean callJZ(int paramInt, long paramLong1, long paramLong2);
  
  public static native boolean callPZ(int paramInt, long paramLong1, long paramLong2);
  
  public static native boolean callPZ(long paramLong1, int paramInt, long paramLong2);
  
  public static native boolean callPZ(boolean paramBoolean, long paramLong1, long paramLong2);
  
  public static native boolean callJZ(int paramInt1, long paramLong1, int paramInt2, long paramLong2);
  
  public static native boolean callPZ(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native boolean callPZ(int paramInt1, long paramLong1, int paramInt2, long paramLong2);
  
  public static native boolean callPZ(int paramInt, float paramFloat1, float paramFloat2, long paramLong1, long paramLong2);
  
  public static native boolean callPPZ(long paramLong1, long paramLong2, long paramLong3);
  
  public static native boolean callJPZ(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native boolean callPPZ(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native boolean callPPZ(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  public static native boolean callPPZ(long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  public static native boolean callPPZ(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3);
  
  public static native boolean callPPZ(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3);
  
  public static native boolean callJPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native boolean callPPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public static native boolean callPPPPZ(int paramInt1, int paramInt2, int paramInt3, float paramFloat, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
  
  public static native boolean callPPJPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native boolean callPPPPPZ(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
  
  public static native byte invokePB(byte paramByte, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int invokePI(short paramShort, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int invokePI(@Nullable float[] paramArrayOffloat, short paramShort, long paramLong);
  
  public static native int invokePI(short paramShort, @Nullable float[] paramArrayOffloat, byte paramByte, long paramLong);
  
  public static native int invokePI(short paramShort, @Nullable int[] paramArrayOfint, byte paramByte, long paramLong);
  
  public static native int invokePI(short paramShort, @Nullable short[] paramArrayOfshort, byte paramByte, long paramLong);
  
  public static native int invokePPI(int paramInt, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int invokePPI(long paramLong1, @Nullable float[] paramArrayOffloat, short paramShort, long paramLong2);
  
  public static native int invokePPI(int paramInt1, @Nullable int[] paramArrayOfint1, int paramInt2, @Nullable int[] paramArrayOfint2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native int invokePPI(int paramInt1, @Nullable short[] paramArrayOfshort1, int paramInt2, @Nullable short[] paramArrayOfshort2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int invokePPPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native int invokePNNPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong4, long paramLong5);
  
  public static native int invokePPPPPPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, @Nullable int[] paramArrayOfint5, long paramLong3);
  
  public static native long invokePP(@Nullable double[] paramArrayOfdouble, int paramInt, long paramLong);
  
  public static native long invokePP(@Nullable float[] paramArrayOffloat, int paramInt, long paramLong);
  
  public static native long invokePP(@Nullable int[] paramArrayOfint, int paramInt, long paramLong);
  
  public static native long invokePP(@Nullable long[] paramArrayOflong, int paramInt, long paramLong);
  
  public static native long invokePP(@Nullable short[] paramArrayOfshort, int paramInt, long paramLong);
  
  public static native long invokePPP(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native short invokePS(byte paramByte, @Nullable short[] paramArrayOfshort, boolean paramBoolean, long paramLong);
  
  public static native short invokePS(short paramShort1, @Nullable short[] paramArrayOfshort, short paramShort2, long paramLong);
  
  public static native short invokePPPS(@Nullable short[] paramArrayOfshort, long paramLong1, long paramLong2, short paramShort, float paramFloat, long paramLong3);
  
  public static native void invokePV(byte paramByte, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void invokePV(int paramInt, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void invokePV(int paramInt, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void invokePV(int paramInt, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void invokePV(short paramShort1, short paramShort2, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void invokePV(short paramShort1, @Nullable double[] paramArrayOfdouble, short paramShort2, long paramLong);
  
  public static native void invokePV(short paramShort1, @Nullable float[] paramArrayOffloat, short paramShort2, long paramLong);
  
  public static native void invokePV(short paramShort1, @Nullable int[] paramArrayOfint, short paramShort2, long paramLong);
  
  public static native void invokePV(short paramShort1, @Nullable long[] paramArrayOflong, short paramShort2, long paramLong);
  
  public static native void invokePV(short paramShort1, @Nullable short[] paramArrayOfshort, short paramShort2, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable float[] paramArrayOffloat, int paramInt3, int paramInt4, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, int paramInt3, int paramInt4, long paramLong);
  
  public static native void invokePV(int paramInt1, int paramInt2, @Nullable short[] paramArrayOfshort, int paramInt3, int paramInt4, long paramLong);
  
  public static native void invokePPV(long paramLong1, @Nullable float[] paramArrayOffloat, int paramInt, long paramLong2);
  
  public static native void invokePPV(long paramLong1, @Nullable int[] paramArrayOfint, int paramInt, long paramLong2);
  
  public static native void invokePPV(long paramLong1, @Nullable short[] paramArrayOfshort, int paramInt, long paramLong2);
  
  public static native void invokePPV(short paramShort, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong);
  
  public static native void invokePPV(long paramLong1, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void invokePPV(long paramLong1, int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong, long paramLong2);
  
  public static native void invokePPV(long paramLong1, short paramShort1, @Nullable double[] paramArrayOfdouble, short paramShort2, long paramLong2);
  
  public static native void invokePPV(long paramLong1, short paramShort1, @Nullable float[] paramArrayOffloat, short paramShort2, long paramLong2);
  
  public static native void invokePPV(long paramLong1, short paramShort1, @Nullable int[] paramArrayOfint, short paramShort2, long paramLong2);
  
  public static native void invokePPV(long paramLong1, short paramShort1, @Nullable long[] paramArrayOflong, short paramShort2, long paramLong2);
  
  public static native void invokePPV(long paramLong1, short paramShort1, @Nullable short[] paramArrayOfshort, short paramShort2, long paramLong2);
  
  public static native void invokePNPV(long paramLong1, long paramLong2, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native void invokePPPV(long paramLong1, @Nullable double[] paramArrayOfdouble1, @Nullable double[] paramArrayOfdouble2, long paramLong2);
  
  public static native void invokePPPV(long paramLong1, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong2);
  
  public static native void invokePPPV(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void invokePPPV(@Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong);
  
  public static native void invokePPPV(int paramInt, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, @Nullable float[] paramArrayOffloat3, long paramLong);
  
  public static native void invokePPPV(int paramInt1, int paramInt2, @Nullable double[] paramArrayOfdouble1, @Nullable double[] paramArrayOfdouble2, @Nullable double[] paramArrayOfdouble3, long paramLong);
  
  public static native void invokePPPV(int paramInt1, int paramInt2, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, @Nullable float[] paramArrayOffloat3, long paramLong);
  
  public static native void invokePPPV(int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong1, @Nullable long[] paramArrayOflong2, @Nullable long[] paramArrayOflong3, long paramLong);
  
  public static native void invokePPPV(@Nullable float[] paramArrayOffloat, int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void invokePPPV(@Nullable float[] paramArrayOffloat, boolean paramBoolean, int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void invokePNPPV(long paramLong1, long paramLong2, long paramLong3, @Nullable short[] paramArrayOfshort, long paramLong4);
  
  public static native void invokePPPPPV(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, long paramLong2);
  
  public static native void invokePPPPPV(long paramLong1, int paramInt, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4, long paramLong5);
  
  public static native void invokePPPPPV(int paramInt1, long paramLong1, int paramInt2, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong2, int paramInt3, long paramLong3, int paramInt4, boolean paramBoolean, long paramLong4);
  
  public static native void invokePPPPPV(int paramInt1, @Nullable int[] paramArrayOfint1, int paramInt2, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong1, int paramInt3, @Nullable int[] paramArrayOfint2, int paramInt4, boolean paramBoolean, long paramLong2);
  
  public static native void invokePPPPPV(int paramInt1, @Nullable short[] paramArrayOfshort1, int paramInt2, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong1, int paramInt3, @Nullable short[] paramArrayOfshort2, int paramInt4, boolean paramBoolean, long paramLong2);
  
  public static native boolean invokePPZ(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int callPI(@Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int callPI(int paramInt, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int callPI(@Nullable int[] paramArrayOfint, int paramInt, long paramLong);
  
  public static native int callPI(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int callPI(int paramInt1, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native int callPI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native int callPPI(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int callPPI(long paramLong1, @Nullable long[] paramArrayOflong, long paramLong2);
  
  public static native int callPPI(@Nullable int[] paramArrayOfint, long paramLong1, long paramLong2);
  
  public static native int callPPI(int paramInt, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int callPPI(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int callPPI(long paramLong1, int paramInt, @Nullable long[] paramArrayOflong, long paramLong2);
  
  public static native int callPPI(long paramLong1, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native int callPJPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPPPI(long paramLong1, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPPI(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native int callPPPI(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native int callPPPI(long paramLong1, @Nullable long[] paramArrayOflong1, @Nullable long[] paramArrayOflong2, long paramLong2);
  
  public static native int callPPPI(@Nullable int[] paramArrayOfint1, long paramLong1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native int callPJPI(long paramLong1, int paramInt, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPJPI(long paramLong1, long paramLong2, int paramInt, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable double[] paramArrayOfdouble, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable float[] paramArrayOffloat, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native int callPPPI(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint, @Nullable long[] paramArrayOflong, long paramLong2);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPPPI(long paramLong1, long paramLong2, int paramInt, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, int paramInt, long paramLong3);
  
  public static native int callPPJI(long paramLong1, int paramInt1, @Nullable long[] paramArrayOflong, int paramInt2, long paramLong2, long paramLong3);
  
  public static native int callPPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native int callPPPI(long paramLong1, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPNPI(long paramLong1, long paramLong2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native int callPPPPI(long paramLong1, int paramInt, long paramLong2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt, long paramLong2, @Nullable long[] paramArrayOflong1, @Nullable long[] paramArrayOflong2, long paramLong3);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, long paramLong2, long paramLong3, int paramInt, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, @Nullable int[] paramArrayOfint1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native int callPPPPI(long paramLong1, @Nullable long[] paramArrayOflong, int paramInt, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native int callPJPPI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, int paramInt2, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3, long paramLong4);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native int callPPPPI(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native int callPPPPI(int paramInt1, @Nullable int[] paramArrayOfint1, @Nullable long[] paramArrayOflong1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19, @Nullable int[] paramArrayOfint2, @Nullable long[] paramArrayOflong2, long paramLong);
  
  public static native int callPJPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable long[] paramArrayOflong, long paramLong5);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong3);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native int callPPPPPI(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong2, long paramLong3);
  
  public static native int callPJPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, @Nullable long[] paramArrayOflong, long paramLong5);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4, long paramLong5);
  
  public static native int callPPPPPI(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4, long paramLong5);
  
  public static native int callPPPPPI(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable float[] paramArrayOffloat, int paramInt, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong2);
  
  public static native int callPPPPPI(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, long paramLong1, long paramLong2);
  
  public static native int callPJPPJI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4, int paramInt3, long paramLong5);
  
  public static native int callPJPPJI(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, @Nullable long[] paramArrayOflong, long paramLong4, int paramInt3, long paramLong5);
  
  public static native int callPJJJJPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, @Nullable int[] paramArrayOfint, long paramLong6);
  
  public static native int callPPPPPPI(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, @Nullable int[] paramArrayOfint5, long paramLong2);
  
  public static native int callPPPPPPI(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, @Nullable int[] paramArrayOfint5, long paramLong1, long paramLong2);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, @Nullable int[] paramArrayOfint, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, @Nullable float[] paramArrayOffloat, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, @Nullable double[] paramArrayOfdouble, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, @Nullable float[] paramArrayOffloat, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, @Nullable short[] paramArrayOfshort, int paramInt2, long paramLong5, long paramLong6, long paramLong7);
  
  public static native int callPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable double[] paramArrayOfdouble, int paramInt2, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable float[] paramArrayOffloat, int paramInt2, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable short[] paramArrayOfshort, int paramInt2, long paramLong7, long paramLong8, long paramLong9);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, @Nullable double[] paramArrayOfdouble, int paramInt2, long paramLong10, long paramLong11, long paramLong12);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, @Nullable float[] paramArrayOffloat, int paramInt2, long paramLong10, long paramLong11, long paramLong12);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong10, long paramLong11, long paramLong12);
  
  public static native int callPPPPPPPPPPPPI(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, @Nullable short[] paramArrayOfshort, int paramInt2, long paramLong10, long paramLong11, long paramLong12);
  
  public static native long callPP(@Nullable int[] paramArrayOfint, long paramLong);
  
  public static native long callPPP(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native long callPPP(int paramInt, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native long callPPP(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native long callPPP(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native long callPPP(long paramLong1, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native long callPPPP(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPPPP(long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native long callPJPP(long paramLong1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPPPP(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native long callPPPP(long paramLong1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPJPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPPJPP(long paramLong1, long paramLong2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPNPP(long paramLong1, long paramLong2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, @Nullable long[] paramArrayOflong, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, int paramInt, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native long callPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, int paramInt, long paramLong3, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPPPP(long paramLong1, long paramLong2, long paramLong3, int paramInt, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPJPPP(long paramLong1, long paramLong2, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong3);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint, long paramLong5);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, @Nullable double[] paramArrayOfdouble, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong4);
  
  public static native long callPJPPPP(long paramLong1, long paramLong2, long paramLong3, @Nullable short[] paramArrayOfshort, @Nullable int[] paramArrayOfint, long paramLong4);
  
  public static native long callPPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint, long paramLong5);
  
  public static native long callPJPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, @Nullable int[] paramArrayOfint, long paramLong6);
  
  public static native long callPJPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint, long paramLong5);
  
  public static native long callPJPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong5);
  
  public static native long callPJPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable short[] paramArrayOfshort, @Nullable int[] paramArrayOfint, long paramLong5);
  
  public static native long callPPPJPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, @Nullable int[] paramArrayOfint, long paramLong6);
  
  public static native long callPPPPPPP(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong5);
  
  public static native long callPPPPPPPP(int paramInt1, @Nullable int[] paramArrayOfint1, @Nullable long[] paramArrayOflong1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2, long paramLong3, int paramInt6, @Nullable int[] paramArrayOfint2, @Nullable long[] paramArrayOflong2, long paramLong4);
  
  public static native long callPJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, @Nullable int[] paramArrayOfint, long paramLong8);
  
  public static native long callPJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint, long paramLong7);
  
  public static native long callPJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong7);
  
  public static native long callPJPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, @Nullable short[] paramArrayOfshort, @Nullable int[] paramArrayOfint, long paramLong7);
  
  public static native long callPPJPPPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, int paramInt2, long paramLong6, long paramLong7, @Nullable int[] paramArrayOfint, long paramLong8);
  
  public static native long callPJPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, @Nullable int[] paramArrayOfint, long paramLong10);
  
  public static native long callPJPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, @Nullable float[] paramArrayOffloat, @Nullable int[] paramArrayOfint, long paramLong9);
  
  public static native long callPJPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong9);
  
  public static native long callPJPPPPPPPPP(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, @Nullable short[] paramArrayOfshort, @Nullable int[] paramArrayOfint, long paramLong9);
  
  public static native long callPPJPPPPPPPP(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, int paramInt2, long paramLong8, long paramLong9, @Nullable int[] paramArrayOfint, long paramLong10);
  
  public static native void callPV(@Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(@Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(@Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(@Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPV(int paramInt, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, boolean paramBoolean, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, boolean paramBoolean, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, boolean paramBoolean, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, int paramInt3, long paramLong);
  
  public static native void callPV(int paramInt1, @Nullable int[] paramArrayOfint, int paramInt2, int paramInt3, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, boolean paramBoolean, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, int paramInt3, int paramInt4, long paramLong);
  
  public static native void callPV(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint, boolean paramBoolean, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double paramDouble3, double paramDouble4, int paramInt4, int paramInt5, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float paramFloat3, float paramFloat4, int paramInt4, int paramInt5, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, @Nullable double[] paramArrayOfdouble, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, @Nullable int[] paramArrayOfint, long paramLong);
  
  public static native void callPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, @Nullable short[] paramArrayOfshort, long paramLong);
  
  public static native void callPPV(long paramLong1, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(@Nullable double[] paramArrayOfdouble1, @Nullable double[] paramArrayOfdouble2, long paramLong);
  
  public static native void callPPV(@Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, long paramLong);
  
  public static native void callPPV(@Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(@Nullable short[] paramArrayOfshort1, @Nullable short[] paramArrayOfshort2, long paramLong);
  
  public static native void callPPV(int paramInt, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(int paramInt, @Nullable int[] paramArrayOfint, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPPV(int paramInt, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(int paramInt, @Nullable int[] paramArrayOfint, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPPV(@Nullable int[] paramArrayOfint1, int paramInt, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong1, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, @Nullable long[] paramArrayOflong, long paramLong);
  
  public static native void callPPV(int paramInt1, long paramLong1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(int paramInt1, long paramLong1, @Nullable double[] paramArrayOfdouble, int paramInt2, long paramLong2);
  
  public static native void callPPV(int paramInt1, long paramLong1, @Nullable float[] paramArrayOffloat, int paramInt2, long paramLong2);
  
  public static native void callPPV(int paramInt1, long paramLong1, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong2);
  
  public static native void callPPV(int paramInt1, long paramLong1, @Nullable long[] paramArrayOflong, int paramInt2, long paramLong2);
  
  public static native void callPPV(int paramInt1, long paramLong1, @Nullable short[] paramArrayOfshort, int paramInt2, long paramLong2);
  
  public static native void callPPV(int paramInt1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, int paramInt2, long paramLong);
  
  public static native void callPPV(int paramInt1, @Nullable long[] paramArrayOflong, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong1, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, @Nullable int[] paramArrayOfint, int paramInt3, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, int paramInt3, @Nullable int[] paramArrayOfint2, long paramLong);
  
  public static native void callPPV(int paramInt1, @Nullable int[] paramArrayOfint, int paramInt2, long paramLong1, int paramInt3, long paramLong2);
  
  public static native void callPPV(int paramInt1, @Nullable int[] paramArrayOfint1, int paramInt2, @Nullable int[] paramArrayOfint2, int paramInt3, long paramLong);
  
  public static native void callPPV(int paramInt1, @Nullable int[] paramArrayOfint, long paramLong1, int paramInt2, int paramInt3, long paramLong2);
  
  public static native void callPPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong1, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, @Nullable short[] paramArrayOfshort, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong1, int paramInt3, int paramInt4, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, int paramInt5, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, int paramInt6, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, int paramInt6, @Nullable short[] paramArrayOfshort, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, float paramFloat1, float paramFloat2, int paramInt5, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPPV(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, @Nullable float[] paramArrayOffloat, long paramLong2);
  
  public static native void callPJPV(long paramLong1, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native void callPPPV(long paramLong1, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, @Nullable double[] paramArrayOfdouble, long paramLong3);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, @Nullable float[] paramArrayOffloat, long paramLong3);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native void callPPPV(int paramInt, long paramLong1, long paramLong2, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native void callPPPV(long paramLong1, int paramInt, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native void callPPPV(long paramLong1, int paramInt, @Nullable long[] paramArrayOflong, long paramLong2, long paramLong3);
  
  public static native void callPPPV(long paramLong1, long paramLong2, int paramInt, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, @Nullable double[] paramArrayOfdouble, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, @Nullable float[] paramArrayOffloat, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, long paramLong1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void callPPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong1, long paramLong2);
  
  public static native void callPPPV(int paramInt1, long paramLong1, int paramInt2, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native void callPPPV(int paramInt1, long paramLong1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void callPPPV(int paramInt1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, int paramInt2, @Nullable int[] paramArrayOfint3, long paramLong);
  
  public static native void callPPPV(long paramLong1, int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void callPPPV(long paramLong1, int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong1, @Nullable long[] paramArrayOflong2, long paramLong2);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable double[] paramArrayOfdouble, long paramLong3);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable float[] paramArrayOffloat, long paramLong3);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native void callPJPV(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native void callPPJV(long paramLong1, int paramInt1, @Nullable long[] paramArrayOflong, int paramInt2, long paramLong2, int paramInt3, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint1, long paramLong1, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void callPPPV(int paramInt1, int paramInt2, @Nullable int[] paramArrayOfint1, int paramInt3, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong);
  
  public static native void callPPPV(int paramInt1, @Nullable int[] paramArrayOfint1, int paramInt2, long paramLong1, int paramInt3, @Nullable int[] paramArrayOfint2, long paramLong2);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, @Nullable float[] paramArrayOffloat, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, @Nullable short[] paramArrayOfshort, long paramLong3);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint1, int paramInt5, @Nullable int[] paramArrayOfint2, @Nullable float[] paramArrayOffloat, long paramLong);
  
  public static native void callPPPV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable int[] paramArrayOfint1, int paramInt5, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong);
  
  public static native void callPPPV(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, @Nullable int[] paramArrayOfint, long paramLong2, long paramLong3);
  
  public static native void callPJPPV(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native void callPPPPV(long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint, long paramLong3, long paramLong4);
  
  public static native void callPPPPV(@Nullable long[] paramArrayOflong, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, int paramInt, long paramLong);
  
  public static native void callPPPPV(int paramInt1, long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, int paramInt2, long paramLong2);
  
  public static native void callPPPPV(long paramLong1, int paramInt1, int paramInt2, @Nullable long[] paramArrayOflong1, @Nullable long[] paramArrayOflong2, @Nullable long[] paramArrayOflong3, long paramLong2);
  
  public static native void callPPPPV(int paramInt1, int paramInt2, int paramInt3, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, long paramLong1, long paramLong2);
  
  public static native void callPPPPV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, int paramInt3, long paramLong3);
  
  public static native void callPJPPV(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, @Nullable long[] paramArrayOflong, int paramInt4, @Nullable int[] paramArrayOfint, long paramLong3);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable double[] paramArrayOfdouble, long paramLong5);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable float[] paramArrayOffloat, long paramLong5);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable int[] paramArrayOfint, long paramLong5);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable long[] paramArrayOflong, long paramLong5);
  
  public static native void callPJJJPV(long paramLong1, long paramLong2, long paramLong3, long paramLong4, @Nullable short[] paramArrayOfshort, long paramLong5);
  
  public static native void callPPPPPV(int paramInt1, int paramInt2, long paramLong1, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, long paramLong2);
  
  public static native void callPPPPPV(long paramLong1, int paramInt1, @Nullable long[] paramArrayOflong, int paramInt2, int paramInt3, int paramInt4, long paramLong2, int paramInt5, long paramLong3, int paramInt6, long paramLong4, long paramLong5);
  
  public static native void callPPPPPPPV(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2, @Nullable int[] paramArrayOfint1, @Nullable int[] paramArrayOfint2, @Nullable int[] paramArrayOfint3, @Nullable int[] paramArrayOfint4, @Nullable long[] paramArrayOflong, long paramLong3);
  
  public static native boolean callPPZ(int paramInt, @Nullable int[] paramArrayOfint, long paramLong1, long paramLong2);
  
  public static native boolean callPPPPZ(int paramInt1, int paramInt2, int paramInt3, float paramFloat, @Nullable float[] paramArrayOffloat1, @Nullable float[] paramArrayOffloat2, @Nullable float[] paramArrayOffloat3, @Nullable float[] paramArrayOffloat4, long paramLong);
  
  static {
    Library.initialize();
  }
}
