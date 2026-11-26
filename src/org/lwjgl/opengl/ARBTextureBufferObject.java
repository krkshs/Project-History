package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTextureBufferObject {
  public static final int GL_TEXTURE_BUFFER_ARB = 35882;
  
  public static final int GL_MAX_TEXTURE_BUFFER_SIZE_ARB = 35883;
  
  public static final int GL_TEXTURE_BINDING_BUFFER_ARB = 35884;
  
  public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_ARB = 35885;
  
  public static final int GL_TEXTURE_BUFFER_FORMAT_ARB = 35886;
  
  static {
    GL.initialize();
  }
  
  protected ARBTextureBufferObject() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glTexBufferARB });
  }
  
  public static native void glTexBufferARB(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3);
}
