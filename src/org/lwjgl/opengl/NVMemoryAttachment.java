package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVMemoryAttachment {
  public static final int GL_ATTACHED_MEMORY_OBJECT_NV = 38308;
  
  public static final int GL_ATTACHED_MEMORY_OFFSET_NV = 38309;
  
  public static final int GL_MEMORY_ATTACHABLE_ALIGNMENT_NV = 38310;
  
  public static final int GL_MEMORY_ATTACHABLE_SIZE_NV = 38311;
  
  public static final int GL_MEMORY_ATTACHABLE_NV = 38312;
  
  public static final int GL_DETACHED_MEMORY_INCARNATION_NV = 38313;
  
  public static final int GL_DETACHED_TEXTURES_NV = 38314;
  
  public static final int GL_DETACHED_BUFFERS_NV = 38315;
  
  public static final int GL_MAX_DETACHED_TEXTURES_NV = 38316;
  
  public static final int GL_MAX_DETACHED_BUFFERS_NV = 38317;
  
  static {
    GL.initialize();
  }
  
  protected NVMemoryAttachment() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glGetMemoryObjectDetachedResourcesuivNV, caps.glResetMemoryObjectParameterNV, caps.glTexAttachMemoryNV, caps.glBufferAttachMemoryNV, 
          
          caps.hasDSA(ext) ? caps.glTextureAttachMemoryNV : -1L, caps.hasDSA(ext) ? caps.glNamedBufferAttachMemoryNV : -1L });
  }
  
  public static void glGetMemoryObjectDetachedResourcesuivNV(@NativeType("GLuint") int memory, @NativeType("GLenum") int pname, @NativeType("GLint") int first, @NativeType("GLuint *") IntBuffer params) {
    nglGetMemoryObjectDetachedResourcesuivNV(memory, pname, first, params.remaining(), MemoryUtil.memAddress(params));
  }
  
  public static void glGetMemoryObjectDetachedResourcesuivNV(@NativeType("GLuint") int memory, @NativeType("GLenum") int pname, @NativeType("GLint") int first, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMemoryObjectDetachedResourcesuivNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(memory, pname, first, params.length, params, __functionAddress);
  }
  
  public static native void nglGetMemoryObjectDetachedResourcesuivNV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glResetMemoryObjectParameterNV(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glTexAttachMemoryNV(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint64") long paramLong);
  
  public static native void glBufferAttachMemoryNV(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint64") long paramLong);
  
  public static native void glTextureAttachMemoryNV(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint64") long paramLong);
  
  public static native void glNamedBufferAttachMemoryNV(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint64") long paramLong);
}
