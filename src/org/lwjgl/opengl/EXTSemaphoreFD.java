package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTSemaphoreFD {
  public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 38278;
  
  static {
    GL.initialize();
  }
  
  protected EXTSemaphoreFD() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glImportSemaphoreFdEXT });
  }
  
  public static native void glImportSemaphoreFdEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3);
}
