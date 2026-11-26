package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVTextureMultisample {
  public static final int GL_TEXTURE_COVERAGE_SAMPLES_NV = 36933;
  
  public static final int GL_TEXTURE_COLOR_SAMPLES_NV = 36934;
  
  static {
    GL.initialize();
  }
  
  protected NVTextureMultisample() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glTexImage2DMultisampleCoverageNV, caps.glTexImage3DMultisampleCoverageNV, caps.glTextureImage2DMultisampleNV, caps.glTextureImage3DMultisampleNV, caps.glTextureImage2DMultisampleCoverageNV, caps.glTextureImage3DMultisampleCoverageNV });
  }
  
  public static native void glTexImage2DMultisampleCoverageNV(@NativeType("GLenum") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTexImage3DMultisampleCoverageNV(@NativeType("GLenum") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTextureImage2DMultisampleNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTextureImage3DMultisampleNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTextureImage2DMultisampleCoverageNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTextureImage3DMultisampleCoverageNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLsizei") int paramInt8, @NativeType("GLboolean") boolean paramBoolean);
}
