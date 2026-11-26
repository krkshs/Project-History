package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBBindlessTexture {
  public static final int GL_UNSIGNED_INT64_ARB = 5135;
  
  static {
    GL.initialize();
  }
  
  protected ARBBindlessTexture() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { 
          caps.glGetTextureHandleARB, caps.glGetTextureSamplerHandleARB, caps.glMakeTextureHandleResidentARB, caps.glMakeTextureHandleNonResidentARB, caps.glGetImageHandleARB, caps.glMakeImageHandleResidentARB, caps.glMakeImageHandleNonResidentARB, caps.glUniformHandleui64ARB, caps.glUniformHandleui64vARB, caps.glProgramUniformHandleui64ARB, 
          caps.glProgramUniformHandleui64vARB, caps.glIsTextureHandleResidentARB, caps.glIsImageHandleResidentARB, caps.glVertexAttribL1ui64ARB, caps.glVertexAttribL1ui64vARB, caps.glGetVertexAttribLui64vARB });
  }
  
  public static void glUniformHandleui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
    nglUniformHandleui64vARB(location, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glProgramUniformHandleui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
    nglProgramUniformHandleui64vARB(program, location, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glVertexAttribL1ui64vARB(@NativeType("GLuint") int index, @NativeType("GLuint64 const *") LongBuffer v) {
    if (Checks.CHECKS)
      Checks.check((Buffer)v, 1); 
    nglVertexAttribL1ui64vARB(index, MemoryUtil.memAddress(v));
  }
  
  public static void glGetVertexAttribLui64vARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetVertexAttribLui64vARB(index, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static long glGetVertexAttribLui64ARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      LongBuffer params = stack.callocLong(1);
      nglGetVertexAttribLui64vARB(index, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glUniformHandleui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
    long __functionAddress = (GL.getICD()).glUniformHandleui64vARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(location, values.length, values, __functionAddress);
  }
  
  public static void glProgramUniformHandleui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
    long __functionAddress = (GL.getICD()).glProgramUniformHandleui64vARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, values.length, values, __functionAddress);
  }
  
  public static void glVertexAttribL1ui64vARB(@NativeType("GLuint") int index, @NativeType("GLuint64 const *") long[] v) {
    long __functionAddress = (GL.getICD()).glVertexAttribL1ui64vARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(v, 1);
    } 
    JNI.callPV(index, v, __functionAddress);
  }
  
  public static void glGetVertexAttribLui64vARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params) {
    long __functionAddress = (GL.getICD()).glGetVertexAttribLui64vARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(index, pname, params, __functionAddress);
  }
  
  @NativeType("GLuint64")
  public static native long glGetTextureHandleARB(@NativeType("GLuint") int paramInt);
  
  @NativeType("GLuint64")
  public static native long glGetTextureSamplerHandleARB(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glMakeTextureHandleResidentARB(@NativeType("GLuint64") long paramLong);
  
  public static native void glMakeTextureHandleNonResidentARB(@NativeType("GLuint64") long paramLong);
  
  @NativeType("GLuint64")
  public static native long glGetImageHandleARB(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLboolean") boolean paramBoolean, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4);
  
  public static native void glMakeImageHandleResidentARB(@NativeType("GLuint64") long paramLong, @NativeType("GLenum") int paramInt);
  
  public static native void glMakeImageHandleNonResidentARB(@NativeType("GLuint64") long paramLong);
  
  public static native void glUniformHandleui64ARB(@NativeType("GLint") int paramInt, @NativeType("GLuint64") long paramLong);
  
  public static native void nglUniformHandleui64vARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glProgramUniformHandleui64ARB(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint64") long paramLong);
  
  public static native void nglProgramUniformHandleui64vARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsTextureHandleResidentARB(@NativeType("GLuint64") long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsImageHandleResidentARB(@NativeType("GLuint64") long paramLong);
  
  public static native void glVertexAttribL1ui64ARB(@NativeType("GLuint") int paramInt, @NativeType("GLuint64") long paramLong);
  
  public static native void nglVertexAttribL1ui64vARB(int paramInt, long paramLong);
  
  public static native void nglGetVertexAttribLui64vARB(int paramInt1, int paramInt2, long paramLong);
}
