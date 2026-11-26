package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVScissorExclusive {
  public static final int GL_SCISSOR_TEST_EXCLUSIVE_NV = 38229;
  
  public static final int GL_SCISSOR_BOX_EXCLUSIVE_NV = 38230;
  
  static {
    GL.initialize();
  }
  
  protected NVScissorExclusive() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glScissorExclusiveArrayvNV, caps.glScissorExclusiveNV });
  }
  
  public static void glScissorExclusiveArrayvNV(@NativeType("GLuint") int first, @NativeType("GLint const *") IntBuffer v) {
    nglScissorExclusiveArrayvNV(first, v.remaining() >> 2, MemoryUtil.memAddress(v));
  }
  
  public static void glScissorExclusiveArrayvNV(@NativeType("GLuint") int first, @NativeType("GLint const *") int[] v) {
    long __functionAddress = (GL.getICD()).glScissorExclusiveArrayvNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(first, v.length >> 2, v, __functionAddress);
  }
  
  public static native void nglScissorExclusiveArrayvNV(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glScissorExclusiveNV(@NativeType("GLint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4);
}
