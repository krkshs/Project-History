package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTBlendEquationSeparate {
  public static final int GL_BLEND_EQUATION_RGB_EXT = 32777;
  
  public static final int GL_BLEND_EQUATION_ALPHA_EXT = 34877;
  
  static {
    GL.initialize();
  }
  
  protected EXTBlendEquationSeparate() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBlendEquationSeparateEXT });
  }
  
  public static native void glBlendEquationSeparateEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2);
}
