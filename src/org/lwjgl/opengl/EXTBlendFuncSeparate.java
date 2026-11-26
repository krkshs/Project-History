package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTBlendFuncSeparate {
  public static final int GL_BLEND_DST_RGB_EXT = 32968;
  
  public static final int GL_BLEND_SRC_RGB_EXT = 32969;
  
  public static final int GL_BLEND_DST_ALPHA_EXT = 32970;
  
  public static final int GL_BLEND_SRC_ALPHA_EXT = 32971;
  
  static {
    GL.initialize();
  }
  
  protected EXTBlendFuncSeparate() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glBlendFuncSeparateEXT);
  }
  
  public static native void glBlendFuncSeparateEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLenum") int paramInt4);
}
