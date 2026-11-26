package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBInstancedArrays {
  public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ARB = 35070;
  
  static {
    GL.initialize();
  }
  
  protected ARBInstancedArrays() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glVertexAttribDivisorARB });
  }
  
  public static native void glVertexAttribDivisorARB(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glVertexArrayVertexAttribDivisorEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3);
}
