package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConservativeRasterPreSnapTriangles {
  public static final int GL_CONSERVATIVE_RASTER_MODE_NV = 38221;
  
  public static final int GL_CONSERVATIVE_RASTER_MODE_POST_SNAP_NV = 38222;
  
  public static final int GL_CONSERVATIVE_RASTER_MODE_PRE_SNAP_TRIANGLES_NV = 38223;
  
  static {
    GL.initialize();
  }
  
  protected NVConservativeRasterPreSnapTriangles() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glConservativeRasterParameteriNV });
  }
  
  public static native void glConservativeRasterParameteriNV(@NativeType("GLenum") int paramInt1, @NativeType("GLint") int paramInt2);
}
