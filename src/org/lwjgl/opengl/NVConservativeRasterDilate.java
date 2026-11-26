package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConservativeRasterDilate {
  public static final int GL_CONSERVATIVE_RASTER_DILATE_NV = 37753;
  
  public static final int GL_CONSERVATIVE_RASTER_DILATE_RANGE_NV = 37754;
  
  public static final int GL_CONSERVATIVE_RASTER_DILATE_GRANULARITY_NV = 37755;
  
  static {
    GL.initialize();
  }
  
  protected NVConservativeRasterDilate() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glConservativeRasterParameterfNV });
  }
  
  public static native void glConservativeRasterParameterfNV(@NativeType("GLenum") int paramInt, @NativeType("GLfloat") float paramFloat);
}
