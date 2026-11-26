package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBColorBufferFloat {
  public static final int GL_RGBA_FLOAT_MODE_ARB = 34848;
  
  public static final int GL_CLAMP_VERTEX_COLOR_ARB = 35098;
  
  public static final int GL_CLAMP_FRAGMENT_COLOR_ARB = 35099;
  
  public static final int GL_CLAMP_READ_COLOR_ARB = 35100;
  
  public static final int GL_FIXED_ONLY_ARB = 35101;
  
  static {
    GL.initialize();
  }
  
  protected ARBColorBufferFloat() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glClampColorARB });
  }
  
  public static native void glClampColorARB(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2);
}
