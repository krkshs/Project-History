package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessTexture {
  static {
    GL.initialize();
  }
  
  protected NVBindlessTexture() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { 
          caps.glGetTextureHandleNV, caps.glGetTextureSamplerHandleNV, caps.glMakeTextureHandleResidentNV, caps.glMakeTextureHandleNonResidentNV, caps.glGetImageHandleNV, caps.glMakeImageHandleResidentNV, caps.glMakeImageHandleNonResidentNV, caps.glUniformHandleui64NV, caps.glUniformHandleui64vNV, caps.glProgramUniformHandleui64NV, 
          caps.glProgramUniformHandleui64vNV, caps.glIsTextureHandleResidentNV, caps.glIsImageHandleResidentNV });
  }
  
  public static void glUniformHandleui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
    nglUniformHandleui64vNV(location, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glProgramUniformHandleui64vNV(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
    nglProgramUniformHandleui64vNV(program, location, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glUniformHandleui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
    long __functionAddress = (GL.getICD()).glUniformHandleui64vNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(location, values.length, values, __functionAddress);
  }
  
  public static void glProgramUniformHandleui64vNV(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
    long __functionAddress = (GL.getICD()).glProgramUniformHandleui64vNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, values.length, values, __functionAddress);
  }
  
  @NativeType("GLuint64")
  public static native long glGetTextureHandleNV(@NativeType("GLuint") int paramInt);
  
  @NativeType("GLuint64")
  public static native long glGetTextureSamplerHandleNV(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glMakeTextureHandleResidentNV(@NativeType("GLuint64") long paramLong);
  
  public static native void glMakeTextureHandleNonResidentNV(@NativeType("GLuint64") long paramLong);
  
  @NativeType("GLuint64")
  public static native long glGetImageHandleNV(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLboolean") boolean paramBoolean, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4);
  
  public static native void glMakeImageHandleResidentNV(@NativeType("GLuint64") long paramLong, @NativeType("GLenum") int paramInt);
  
  public static native void glMakeImageHandleNonResidentNV(@NativeType("GLuint64") long paramLong);
  
  public static native void glUniformHandleui64NV(@NativeType("GLint") int paramInt, @NativeType("GLuint64") long paramLong);
  
  public static native void nglUniformHandleui64vNV(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glProgramUniformHandleui64NV(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint64") long paramLong);
  
  public static native void nglProgramUniformHandleui64vNV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsTextureHandleResidentNV(@NativeType("GLuint64") long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsImageHandleResidentNV(@NativeType("GLuint64") long paramLong);
}
