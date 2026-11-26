package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTextureBufferRange {
  public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
  
  public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
  
  public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
  
  static {
    GL.initialize();
  }
  
  protected ARBTextureBufferRange() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(caps.glTexBufferRange, 
        ext.contains("GL_EXT_direct_state_access") ? caps.glTextureBufferRangeEXT : -1L);
  }
  
  public static void glTexBufferRange(@NativeType("GLenum") int target, @NativeType("GLenum") int internalformat, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long size) {
    GL43C.glTexBufferRange(target, internalformat, buffer, offset, size);
  }
  
  public static native void glTextureBufferRangeEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2);
}
