package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBPointParameters {
  public static final int GL_POINT_SIZE_MIN_ARB = 33062;
  
  public static final int GL_POINT_SIZE_MAX_ARB = 33063;
  
  public static final int GL_POINT_FADE_THRESHOLD_SIZE_ARB = 33064;
  
  public static final int GL_POINT_DISTANCE_ATTENUATION_ARB = 33065;
  
  static {
    GL.initialize();
  }
  
  protected ARBPointParameters() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glPointParameterfARB, caps.glPointParameterfvARB });
  }
  
  public static void glPointParameterfvARB(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 3); 
    nglPointParameterfvARB(pname, MemoryUtil.memAddress(params));
  }
  
  public static void glPointParameterfvARB(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glPointParameterfvARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 3);
    } 
    JNI.callPV(pname, params, __functionAddress);
  }
  
  public static native void glPointParameterfARB(@NativeType("GLenum") int paramInt, @NativeType("GLfloat") float paramFloat);
  
  public static native void nglPointParameterfvARB(int paramInt, long paramLong);
}
