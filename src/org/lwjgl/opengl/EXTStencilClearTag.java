package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTStencilClearTag {
  public static final int GL_STENCIL_TAG_BITS_EXT = 35058;
  
  public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 35059;
  
  static {
    GL.initialize();
  }
  
  protected EXTStencilClearTag() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glStencilClearTagEXT });
  }
  
  public static native void glStencilClearTagEXT(@NativeType("GLsizei") int paramInt1, @NativeType("GLuint") int paramInt2);
}
