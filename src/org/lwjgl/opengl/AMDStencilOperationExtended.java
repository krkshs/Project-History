package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDStencilOperationExtended {
  public static final int GL_SET_AMD = 34634;
  
  public static final int GL_REPLACE_VALUE_AMD = 34635;
  
  public static final int GL_STENCIL_OP_VALUE_AMD = 34636;
  
  public static final int GL_STENCIL_BACK_OP_VALUE_AMD = 34637;
  
  static {
    GL.initialize();
  }
  
  protected AMDStencilOperationExtended() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glStencilOpValueAMD });
  }
  
  public static native void glStencilOpValueAMD(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
}
