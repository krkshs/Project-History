package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTMemoryObjectFD {
  public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 38278;
  
  static {
    GL.initialize();
  }
  
  protected EXTMemoryObjectFD() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glImportMemoryFdEXT });
  }
  
  public static native void glImportMemoryFdEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint64") long paramLong, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3);
}
