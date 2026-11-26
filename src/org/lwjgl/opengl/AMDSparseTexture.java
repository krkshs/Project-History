package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDSparseTexture {
  public static final int GL_TEXTURE_STORAGE_SPARSE_BIT_AMD = 1;
  
  public static final int GL_VIRTUAL_PAGE_SIZE_X_AMD = 37269;
  
  public static final int GL_VIRTUAL_PAGE_SIZE_Y_AMD = 37270;
  
  public static final int GL_VIRTUAL_PAGE_SIZE_Z_AMD = 37271;
  
  public static final int GL_MAX_SPARSE_TEXTURE_SIZE_AMD = 37272;
  
  public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_AMD = 37273;
  
  public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS = 37274;
  
  public static final int GL_MIN_SPARSE_LEVEL_AMD = 37275;
  
  public static final int GL_MIN_LOD_WARNING_AMD = 37276;
  
  static {
    GL.initialize();
  }
  
  protected AMDSparseTexture() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glTexStorageSparseAMD, caps.glTextureStorageSparseAMD });
  }
  
  public static native void glTexStorageSparseAMD(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLbitfield") int paramInt7);
  
  public static native void glTextureStorageSparseAMD(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLbitfield") int paramInt8);
}
