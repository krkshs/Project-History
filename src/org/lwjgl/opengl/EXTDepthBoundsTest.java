package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class EXTDepthBoundsTest {
  public static final int GL_DEPTH_BOUNDS_TEST_EXT = 34960;
  
  public static final int GL_DEPTH_BOUNDS_EXT = 34961;
  
  static {
    GL.initialize();
  }
  
  protected EXTDepthBoundsTest() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDepthBoundsEXT });
  }
  
  public static native void glDepthBoundsEXT(double paramDouble1, double paramDouble2);
}
