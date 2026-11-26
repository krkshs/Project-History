package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVVertexArrayRange {
  public static final int GL_VERTEX_ARRAY_RANGE_NV = 34077;
  
  public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_NV = 34078;
  
  public static final int GL_VERTEX_ARRAY_RANGE_VALID_NV = 34079;
  
  public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_NV = 34080;
  
  public static final int GL_VERTEX_ARRAY_RANGE_POINTER_NV = 34081;
  
  static {
    GL.initialize();
  }
  
  protected NVVertexArrayRange() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glVertexArrayRangeNV, caps.glFlushVertexArrayRangeNV });
  }
  
  public static void glVertexArrayRangeNV(@NativeType("void *") ByteBuffer pointer) {
    nglVertexArrayRangeNV(pointer.remaining(), MemoryUtil.memAddress(pointer));
  }
  
  public static native void nglVertexArrayRangeNV(int paramInt, long paramLong);
  
  public static native void glFlushVertexArrayRangeNV();
}
