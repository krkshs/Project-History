package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTextureStorageMultisample {
  static {
    GL.initialize();
  }
  
  protected ARBTextureStorageMultisample() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(caps.glTexStorage2DMultisample, caps.glTexStorage3DMultisample, 
        
        ext.contains("GL_EXT_direct_state_access") ? caps.glTextureStorage2DMultisampleEXT : -1L, 
        ext.contains("GL_EXT_direct_state_access") ? caps.glTextureStorage3DMultisampleEXT : -1L);
  }
  
  public static void glTexStorage2DMultisample(@NativeType("GLenum") int target, @NativeType("GLsizei") int samples, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLboolean") boolean fixedsamplelocations) {
    GL43C.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
  }
  
  public static void glTexStorage3DMultisample(@NativeType("GLenum") int target, @NativeType("GLsizei") int samples, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLboolean") boolean fixedsamplelocations) {
    GL43C.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
  }
  
  public static native void glTextureStorage2DMultisampleEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLboolean") boolean paramBoolean);
  
  public static native void glTextureStorage3DMultisampleEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLboolean") boolean paramBoolean);
}
