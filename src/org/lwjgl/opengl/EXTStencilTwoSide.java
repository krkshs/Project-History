package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTStencilTwoSide {
  public static final int GL_STENCIL_TEST_TWO_SIDE_EXT = 35088;
  
  public static final int GL_ACTIVE_STENCIL_FACE_EXT = 35089;
  
  static {
    GL.initialize();
  }
  
  protected EXTStencilTwoSide() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glActiveStencilFaceEXT });
  }
  
  public static native void glActiveStencilFaceEXT(@NativeType("GLenum") int paramInt);
}
