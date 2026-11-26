package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVCopyImage {
  static {
    GL.initialize();
  }
  
  protected NVCopyImage() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glCopyImageSubDataNV });
  }
  
  public static native void glCopyImageSubDataNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLuint") int paramInt7, @NativeType("GLenum") int paramInt8, @NativeType("GLint") int paramInt9, @NativeType("GLint") int paramInt10, @NativeType("GLint") int paramInt11, @NativeType("GLint") int paramInt12, @NativeType("GLsizei") int paramInt13, @NativeType("GLsizei") int paramInt14, @NativeType("GLsizei") int paramInt15);
}
