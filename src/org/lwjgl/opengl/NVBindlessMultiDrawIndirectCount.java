package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessMultiDrawIndirectCount {
  static {
    GL.initialize();
  }
  
  protected NVBindlessMultiDrawIndirectCount() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glMultiDrawArraysIndirectBindlessCountNV, caps.glMultiDrawElementsIndirectBindlessCountNV });
  }
  
  public static void glMultiDrawArraysIndirectBindlessCountNV(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indirect, @NativeType("GLintptr") long drawCount, @NativeType("GLsizei") int maxDrawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    if (Checks.CHECKS)
      Checks.check((Buffer)indirect, maxDrawCount * ((stride == 0) ? (16 + vertexBufferCount * 24) : stride)); 
    nglMultiDrawArraysIndirectBindlessCountNV(mode, MemoryUtil.memAddress(indirect), drawCount, maxDrawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawArraysIndirectBindlessCountNV(@NativeType("GLenum") int mode, @NativeType("void const *") long indirect, @NativeType("GLintptr") long drawCount, @NativeType("GLsizei") int maxDrawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    nglMultiDrawArraysIndirectBindlessCountNV(mode, indirect, drawCount, maxDrawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawElementsIndirectBindlessCountNV(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indirect, @NativeType("GLintptr") long drawCount, @NativeType("GLsizei") int maxDrawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    if (Checks.CHECKS)
      Checks.check((Buffer)indirect, maxDrawCount * ((stride == 0) ? ((vertexBufferCount + 2) * 24) : stride)); 
    nglMultiDrawElementsIndirectBindlessCountNV(mode, type, MemoryUtil.memAddress(indirect), drawCount, maxDrawCount, stride, vertexBufferCount);
  }
  
  public static void glMultiDrawElementsIndirectBindlessCountNV(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") long indirect, @NativeType("GLintptr") long drawCount, @NativeType("GLsizei") int maxDrawCount, @NativeType("GLsizei") int stride, @NativeType("GLint") int vertexBufferCount) {
    nglMultiDrawElementsIndirectBindlessCountNV(mode, type, indirect, drawCount, maxDrawCount, stride, vertexBufferCount);
  }
  
  public static native void nglMultiDrawArraysIndirectBindlessCountNV(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, int paramInt4);
  
  public static native void nglMultiDrawElementsIndirectBindlessCountNV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, int paramInt5);
}
