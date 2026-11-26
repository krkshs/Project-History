package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class EXTShaderFramebufferFetchNonCoherent {
  static {
    GL.initialize();
  }
  
  protected EXTShaderFramebufferFetchNonCoherent() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glFramebufferFetchBarrierEXT });
  }
  
  public static native void glFramebufferFetchBarrierEXT();
}
