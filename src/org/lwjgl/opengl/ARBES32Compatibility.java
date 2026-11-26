package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBES32Compatibility {
  public static final int GL_PRIMITIVE_BOUNDING_BOX_ARB = 37566;
  
  public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE_ARB = 37761;
  
  public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY_ARB = 37762;
  
  static {
    GL.initialize();
  }
  
  protected ARBES32Compatibility() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glPrimitiveBoundingBoxARB });
  }
  
  public static native void glPrimitiveBoundingBoxARB(@NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4, @NativeType("GLfloat") float paramFloat5, @NativeType("GLfloat") float paramFloat6, @NativeType("GLfloat") float paramFloat7, @NativeType("GLfloat") float paramFloat8);
}
