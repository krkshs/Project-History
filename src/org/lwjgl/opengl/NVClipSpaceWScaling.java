package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVClipSpaceWScaling {
  public static final int GL_VIEWPORT_POSITION_W_SCALE_NV = 37756;
  
  public static final int GL_VIEWPORT_POSITION_W_SCALE_X_COEFF = 37757;
  
  public static final int GL_VIEWPORT_POSITION_W_SCALE_Y_COEFF = 37758;
  
  static {
    GL.initialize();
  }
  
  protected NVClipSpaceWScaling() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glViewportPositionWScaleNV });
  }
  
  public static native void glViewportPositionWScaleNV(@NativeType("GLuint") int paramInt, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2);
}
