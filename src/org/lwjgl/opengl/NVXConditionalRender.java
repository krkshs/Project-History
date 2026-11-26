package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVXConditionalRender {
  static {
    GL.initialize();
  }
  
  protected NVXConditionalRender() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBeginConditionalRenderNVX, caps.glEndConditionalRenderNVX });
  }
  
  public static native void glBeginConditionalRenderNVX(@NativeType("GLuint") int paramInt);
  
  public static native void glEndConditionalRenderNVX();
}
