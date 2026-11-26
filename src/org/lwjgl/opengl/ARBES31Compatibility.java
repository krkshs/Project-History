package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBES31Compatibility {
  static {
    GL.initialize();
  }
  
  protected ARBES31Compatibility() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glMemoryBarrierByRegion });
  }
  
  public static void glMemoryBarrierByRegion(@NativeType("GLbitfield") int barriers) {
    GL45C.glMemoryBarrierByRegion(barriers);
  }
}
