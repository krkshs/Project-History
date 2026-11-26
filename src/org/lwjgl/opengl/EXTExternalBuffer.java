package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTExternalBuffer {
  static {
    GL.initialize();
  }
  
  protected EXTExternalBuffer() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glBufferStorageExternalEXT, 
          caps.hasDSA(ext) ? caps.glNamedBufferStorageExternalEXT : -1L });
  }
  
  public static void glBufferStorageExternalEXT(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long size, @NativeType("GLeglClientBufferEXT") long clientBuffer, @NativeType("GLbitfield") int flags) {
    if (Checks.CHECKS)
      Checks.check(clientBuffer); 
    nglBufferStorageExternalEXT(target, offset, size, clientBuffer, flags);
  }
  
  public static void glNamedBufferStorageExternalEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long size, @NativeType("GLeglClientBufferEXT") long clientBuffer, @NativeType("GLbitfield") int flags) {
    if (Checks.CHECKS)
      Checks.check(clientBuffer); 
    nglNamedBufferStorageExternalEXT(buffer, offset, size, clientBuffer, flags);
  }
  
  public static native void nglBufferStorageExternalEXT(int paramInt1, long paramLong1, long paramLong2, long paramLong3, int paramInt2);
  
  public static native void nglNamedBufferStorageExternalEXT(int paramInt1, long paramLong1, long paramLong2, long paramLong3, int paramInt2);
}
