package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBSparseBuffer {
  public static final int GL_SPARSE_STORAGE_BIT_ARB = 1024;
  
  public static final int GL_SPARSE_BUFFER_PAGE_SIZE_ARB = 33528;
  
  static {
    GL.initialize();
  }
  
  protected ARBSparseBuffer() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glBufferPageCommitmentARB });
  }
  
  public static native void glBufferPageCommitmentARB(@NativeType("GLenum") int paramInt, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glNamedBufferPageCommitmentEXT(@NativeType("GLuint") int paramInt, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glNamedBufferPageCommitmentARB(@NativeType("GLuint") int paramInt, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2, @NativeType("GLboolean") boolean paramBoolean);
}
