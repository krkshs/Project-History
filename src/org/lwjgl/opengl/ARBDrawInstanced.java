package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBDrawInstanced {
  static {
    GL.initialize();
  }
  
  protected ARBDrawInstanced() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDrawArraysInstancedARB, caps.glDrawElementsInstancedARB });
  }
  
  public static void glDrawElementsInstancedARB(@NativeType("GLenum") int mode, @NativeType("GLsizei") int count, @NativeType("GLenum") int type, @NativeType("void const *") long indices, @NativeType("GLsizei") int primcount) {
    nglDrawElementsInstancedARB(mode, count, type, indices, primcount);
  }
  
  public static void glDrawElementsInstancedARB(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount) {
    nglDrawElementsInstancedARB(mode, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices), primcount);
  }
  
  public static void glDrawElementsInstancedARB(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount) {
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5121, MemoryUtil.memAddress(indices), primcount);
  }
  
  public static void glDrawElementsInstancedARB(@NativeType("GLenum") int mode, @NativeType("void const *") ShortBuffer indices, @NativeType("GLsizei") int primcount) {
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5123, MemoryUtil.memAddress(indices), primcount);
  }
  
  public static void glDrawElementsInstancedARB(@NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLsizei") int primcount) {
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5125, MemoryUtil.memAddress(indices), primcount);
  }
  
  public static native void glDrawArraysInstancedARB(@NativeType("GLenum") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4);
  
  public static native void nglDrawElementsInstancedARB(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4);
}
