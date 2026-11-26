package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTFramebufferBlit {
  public static final int GL_READ_FRAMEBUFFER_EXT = 36008;
  
  public static final int GL_DRAW_FRAMEBUFFER_EXT = 36009;
  
  public static final int GL_DRAW_FRAMEBUFFER_BINDING_EXT = 36006;
  
  public static final int GL_READ_FRAMEBUFFER_BINDING_EXT = 36010;
  
  static {
    GL.initialize();
  }
  
  protected EXTFramebufferBlit() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glBlitFramebufferEXT);
  }
  
  public static native void glBlitFramebufferEXT(@NativeType("GLint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLint") int paramInt8, @NativeType("GLbitfield") int paramInt9, @NativeType("GLenum") int paramInt10);
}
