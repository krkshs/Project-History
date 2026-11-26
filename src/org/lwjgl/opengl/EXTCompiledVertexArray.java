package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTCompiledVertexArray {
  public static final int GL_ARRAY_ELEMENT_LOCK_FIRST_EXT = 33192;
  
  public static final int GL_ARRAY_ELEMENT_LOCK_COUNT_EXT = 33193;
  
  static {
    GL.initialize();
  }
  
  protected EXTCompiledVertexArray() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glLockArraysEXT, caps.glUnlockArraysEXT });
  }
  
  public static native void glLockArraysEXT(@NativeType("GLint") int paramInt1, @NativeType("GLsizei") int paramInt2);
  
  public static native void glUnlockArraysEXT();
}
