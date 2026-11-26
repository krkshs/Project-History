package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTGPUProgramParameters {
  static {
    GL.initialize();
  }
  
  protected EXTGPUProgramParameters() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glProgramEnvParameters4fvEXT, caps.glProgramLocalParameters4fvEXT);
  }
  
  public static void glProgramEnvParameters4fvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer params) {
    nglProgramEnvParameters4fvEXT(target, index, params.remaining() >> 2, MemoryUtil.memAddress(params));
  }
  
  public static void glProgramLocalParameters4fvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer params) {
    nglProgramLocalParameters4fvEXT(target, index, params.remaining() >> 2, MemoryUtil.memAddress(params));
  }
  
  public static void glProgramEnvParameters4fvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glProgramEnvParameters4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, index, params.length >> 2, params, __functionAddress);
  }
  
  public static void glProgramLocalParameters4fvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glProgramLocalParameters4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, index, params.length >> 2, params, __functionAddress);
  }
  
  public static native void nglProgramEnvParameters4fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramLocalParameters4fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
}
