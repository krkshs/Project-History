package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTBlendColor {
  public static final int GL_CONSTANT_COLOR_EXT = 32769;
  
  public static final int GL_ONE_MINUS_CONSTANT_COLOR_EXT = 32770;
  
  public static final int GL_CONSTANT_ALPHA_EXT = 32771;
  
  public static final int GL_ONE_MINUS_CONSTANT_ALPHA_EXT = 32772;
  
  public static final int GL_BLEND_COLOR_EXT = 32773;
  
  static {
    GL.initialize();
  }
  
  protected EXTBlendColor() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBlendColorEXT });
  }
  
  public static native void glBlendColorEXT(@NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4);
}
