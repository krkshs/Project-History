package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTextureStorage {
  public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 37167;
  
  static {
    GL.initialize();
  }
  
  protected ARBTextureStorage() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glTexStorage1D, caps.glTexStorage2D, caps.glTexStorage3D, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glTextureStorage1DEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glTextureStorage2DEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glTextureStorage3DEXT : -1L });
  }
  
  public static void glTexStorage1D(@NativeType("GLenum") int target, @NativeType("GLsizei") int levels, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width) {
    GL42C.glTexStorage1D(target, levels, internalformat, width);
  }
  
  public static void glTexStorage2D(@NativeType("GLenum") int target, @NativeType("GLsizei") int levels, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height) {
    GL42C.glTexStorage2D(target, levels, internalformat, width, height);
  }
  
  public static void glTexStorage3D(@NativeType("GLenum") int target, @NativeType("GLsizei") int levels, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth) {
    GL42C.glTexStorage3D(target, levels, internalformat, width, height, depth);
  }
  
  public static native void glTextureStorage1DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5);
  
  public static native void glTextureStorage2DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6);
  
  public static native void glTextureStorage3DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7);
}
