package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTFramebufferMultisample {
  public static final int GL_RENDERBUFFER_SAMPLES_EXT = 36011;
  
  public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_EXT = 36182;
  
  public static final int GL_MAX_SAMPLES_EXT = 36183;
  
  static {
    GL.initialize();
  }
  
  protected EXTFramebufferMultisample() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glRenderbufferStorageMultisampleEXT });
  }
  
  public static native void glRenderbufferStorageMultisampleEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLsizei") int paramInt5);
}
