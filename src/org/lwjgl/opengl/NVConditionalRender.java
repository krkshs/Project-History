package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConditionalRender {
  public static final int GL_QUERY_WAIT_NV = 36371;
  
  public static final int GL_QUERY_NO_WAIT_NV = 36372;
  
  public static final int GL_QUERY_BY_REGION_WAIT_NV = 36373;
  
  public static final int GL_QUERY_BY_REGION_NO_WAIT_NV = 36374;
  
  static {
    GL.initialize();
  }
  
  protected NVConditionalRender() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBeginConditionalRenderNV, caps.glEndConditionalRenderNV });
  }
  
  public static native void glBeginConditionalRenderNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glEndConditionalRenderNV();
}
