package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVDrawTexture {
  static {
    GL.initialize();
  }
  
  protected NVDrawTexture() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDrawTextureNV });
  }
  
  public static native void glDrawTextureNV(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4, @NativeType("GLfloat") float paramFloat5, @NativeType("GLfloat") float paramFloat6, @NativeType("GLfloat") float paramFloat7, @NativeType("GLfloat") float paramFloat8, @NativeType("GLfloat") float paramFloat9);
}
