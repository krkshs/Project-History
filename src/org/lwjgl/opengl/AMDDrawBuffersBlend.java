package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDDrawBuffersBlend {
  static {
    GL.initialize();
  }
  
  protected AMDDrawBuffersBlend() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBlendFuncIndexedAMD, caps.glBlendFuncSeparateIndexedAMD, caps.glBlendEquationIndexedAMD, caps.glBlendEquationSeparateIndexedAMD });
  }
  
  public static native void glBlendFuncIndexedAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3);
  
  public static native void glBlendFuncSeparateIndexedAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLenum") int paramInt5);
  
  public static native void glBlendEquationIndexedAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glBlendEquationSeparateIndexedAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3);
}
