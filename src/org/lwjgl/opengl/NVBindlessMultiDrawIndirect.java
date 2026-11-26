package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessMultiDrawIndirect {
  static {
    GL.initialize();
  }
  
  protected NVBindlessMultiDrawIndirect() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glMultiDrawArraysIndirectBindlessNV, caps.glMultiDrawElementsIndirectBindlessNV });
  }
  
  public static void glMultiDrawArraysIndirectBindlessNV(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indirect, @NativeType("GLsizei") int drawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    if (Checks.CHECKS)
      Checks.check((Buffer)indirect, drawCount * ((stride == 0) ? (16 + vertexBufferCount * 24) : stride)); 
    nglMultiDrawArraysIndirectBindlessNV(mode, MemoryUtil.memAddress(indirect), drawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawArraysIndirectBindlessNV(@NativeType("GLenum") int mode, @NativeType("void const *") long indirect, @NativeType("GLsizei") int drawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    nglMultiDrawArraysIndirectBindlessNV(mode, indirect, drawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawElementsIndirectBindlessNV(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indirect, @NativeType("GLsizei") int drawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    if (Checks.CHECKS)
      Checks.check((Buffer)indirect, drawCount * ((stride == 0) ? ((vertexBufferCount + 2) * 24) : stride)); 
    nglMultiDrawElementsIndirectBindlessNV(mode, type, MemoryUtil.memAddress(indirect), drawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawElementsIndirectBindlessNV(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") long indirect, @NativeType("GLsizei") int drawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    nglMultiDrawElementsIndirectBindlessNV(mode, type, indirect, drawCount, stride, vertexBufferCount);
  }
  
  public static native void nglMultiDrawArraysIndirectBindlessNV(int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4);
  
  public static native void nglMultiDrawElementsIndirectBindlessNV(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, int paramInt5);
}
