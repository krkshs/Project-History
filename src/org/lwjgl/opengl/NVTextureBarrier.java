package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class NVTextureBarrier {
  static {
    GL.initialize();
  }
  
  protected NVTextureBarrier() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glTextureBarrierNV });
  }
  
  public static native void glTextureBarrierNV();
}
