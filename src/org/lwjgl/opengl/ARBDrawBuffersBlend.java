package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBDrawBuffersBlend {
  static {
    GL.initialize();
  }
  
  protected ARBDrawBuffersBlend() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBlendEquationiARB, caps.glBlendEquationSeparateiARB, caps.glBlendFunciARB, caps.glBlendFuncSeparateiARB });
  }
  
  public static native void glBlendEquationiARB(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glBlendEquationSeparateiARB(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3);
  
  public static native void glBlendFunciARB(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3);
  
  public static native void glBlendFuncSeparateiARB(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLenum") int paramInt5);
}
