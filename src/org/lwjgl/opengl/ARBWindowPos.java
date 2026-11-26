package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBWindowPos {
  static {
    GL.initialize();
  }
  
  protected ARBWindowPos() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { 
          caps.glWindowPos2iARB, caps.glWindowPos2sARB, caps.glWindowPos2fARB, caps.glWindowPos2dARB, caps.glWindowPos2ivARB, caps.glWindowPos2svARB, caps.glWindowPos2fvARB, caps.glWindowPos2dvARB, caps.glWindowPos3iARB, caps.glWindowPos3sARB, 
          caps.glWindowPos3fARB, caps.glWindowPos3dARB, caps.glWindowPos3ivARB, caps.glWindowPos3svARB, caps.glWindowPos3fvARB, caps.glWindowPos3dvARB });
  }
  
  public static void glWindowPos2ivARB(@NativeType("GLint const *") IntBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 2); 
    nglWindowPos2ivARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos2svARB(@NativeType("GLshort const *") ShortBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 2); 
    nglWindowPos2svARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos2fvARB(@NativeType("GLfloat const *") FloatBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 2); 
    nglWindowPos2fvARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos2dvARB(@NativeType("GLdouble const *") DoubleBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 2); 
    nglWindowPos2dvARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos3ivARB(@NativeType("GLint const *") IntBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 3); 
    nglWindowPos3ivARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos3svARB(@NativeType("GLshort const *") ShortBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 3); 
    nglWindowPos3svARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos3fvARB(@NativeType("GLfloat const *") FloatBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 3); 
    nglWindowPos3fvARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos3dvARB(@NativeType("GLdouble const *") DoubleBuffer p) {
    if (Checks.CHECKS)
      Checks.check((Buffer)p, 3); 
    nglWindowPos3dvARB(MemoryUtil.memAddress(p));
  }
  
  public static void glWindowPos2ivARB(@NativeType("GLint const *") int[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos2ivARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 2);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos2svARB(@NativeType("GLshort const *") short[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos2svARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 2);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos2fvARB(@NativeType("GLfloat const *") float[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos2fvARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 2);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos2dvARB(@NativeType("GLdouble const *") double[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos2dvARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 2);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos3ivARB(@NativeType("GLint const *") int[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos3ivARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 3);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos3svARB(@NativeType("GLshort const *") short[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos3svARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 3);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos3fvARB(@NativeType("GLfloat const *") float[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos3fvARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 3);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static void glWindowPos3dvARB(@NativeType("GLdouble const *") double[] p) {
    long __functionAddress = (GL.getICD()).glWindowPos3dvARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(p, 3);
    } 
    JNI.callPV(p, __functionAddress);
  }
  
  public static native void glWindowPos2iARB(@NativeType("GLint") int paramInt1, @NativeType("GLint") int paramInt2);
  
  public static native void glWindowPos2sARB(@NativeType("GLshort") short paramShort1, @NativeType("GLshort") short paramShort2);
  
  public static native void glWindowPos2fARB(@NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2);
  
  public static native void glWindowPos2dARB(@NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2);
  
  public static native void nglWindowPos2ivARB(long paramLong);
  
  public static native void nglWindowPos2svARB(long paramLong);
  
  public static native void nglWindowPos2fvARB(long paramLong);
  
  public static native void nglWindowPos2dvARB(long paramLong);
  
  public static native void glWindowPos3iARB(@NativeType("GLint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3);
  
  public static native void glWindowPos3sARB(@NativeType("GLshort") short paramShort1, @NativeType("GLshort") short paramShort2, @NativeType("GLshort") short paramShort3);
  
  public static native void glWindowPos3fARB(@NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3);
  
  public static native void glWindowPos3dARB(@NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3);
  
  public static native void nglWindowPos3ivARB(long paramLong);
  
  public static native void nglWindowPos3svARB(long paramLong);
  
  public static native void nglWindowPos3fvARB(long paramLong);
  
  public static native void nglWindowPos3dvARB(long paramLong);
}
