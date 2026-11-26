package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTProvokingVertex {
  public static final int GL_FIRST_VERTEX_CONVENTION_EXT = 36429;
  
  public static final int GL_LAST_VERTEX_CONVENTION_EXT = 36430;
  
  public static final int GL_PROVOKING_VERTEX_EXT = 36431;
  
  public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION_EXT = 36428;
  
  static {
    GL.initialize();
  }
  
  protected EXTProvokingVertex() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glProvokingVertexEXT });
  }
  
  public static native void glProvokingVertexEXT(@NativeType("GLenum") int paramInt);
}
