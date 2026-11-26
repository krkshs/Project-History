package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class ARBTextureBarrier {
  static {
    GL.initialize();
  }
  
  protected ARBTextureBarrier() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glTextureBarrier });
  }
  
  public static void glTextureBarrier() {
    GL45C.glTextureBarrier();
  }
}
