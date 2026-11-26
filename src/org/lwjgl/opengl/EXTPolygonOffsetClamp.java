package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTPolygonOffsetClamp {
  public static final int GL_POLYGON_OFFSET_CLAMP_EXT = 36379;
  
  static {
    GL.initialize();
  }
  
  protected EXTPolygonOffsetClamp() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glPolygonOffsetClampEXT });
  }
  
  public static native void glPolygonOffsetClampEXT(@NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3);
}
