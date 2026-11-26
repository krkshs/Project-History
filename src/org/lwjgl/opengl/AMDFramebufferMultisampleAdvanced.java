package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDFramebufferMultisampleAdvanced {
  public static final int GL_RENDERBUFFER_STORAGE_SAMPLES_AMD = 37298;
  
  public static final int GL_MAX_COLOR_FRAMEBUFFER_SAMPLES_AMD = 37299;
  
  public static final int GL_MAX_COLOR_FRAMEBUFFER_STORAGE_SAMPLES_AMD = 37300;
  
  public static final int GL_MAX_DEPTH_STENCIL_FRAMEBUFFER_SAMPLES_AMD = 37301;
  
  public static final int GL_NUM_SUPPORTED_MULTISAMPLE_MODES_AMD = 37302;
  
  public static final int GL_SUPPORTED_MULTISAMPLE_MODES_AMD = 37303;
  
  static {
    GL.initialize();
  }
  
  protected AMDFramebufferMultisampleAdvanced() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glRenderbufferStorageMultisampleAdvancedAMD, caps.glNamedRenderbufferStorageMultisampleAdvancedAMD);
  }
  
  public static native void glRenderbufferStorageMultisampleAdvancedAMD(@NativeType("GLenum") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6);
  
  public static native void glNamedRenderbufferStorageMultisampleAdvancedAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6);
}
