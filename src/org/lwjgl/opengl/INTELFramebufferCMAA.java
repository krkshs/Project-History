package org.lwjgl.opengl;

import org.lwjgl.system.Checks;

public class INTELFramebufferCMAA {
  static {
    GL.initialize();
  }
  
  protected INTELFramebufferCMAA() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glApplyFramebufferAttachmentCMAAINTEL });
  }
  
  public static native void glApplyFramebufferAttachmentCMAAINTEL();
}
