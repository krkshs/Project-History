package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class GREMEDYFrameTerminator {
  static {
    GL.initialize();
  }
  
  protected GREMEDYFrameTerminator() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glFrameTerminatorGREMEDY });
  }
  
  public static native void glFrameTerminatorGREMEDY();
}
