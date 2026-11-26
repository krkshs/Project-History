package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class OVRMultiview {
  public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_NUM_VIEWS_OVR = 38448;
  
  public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_BASE_VIEW_INDEX_OVR = 38450;
  
  public static final int GL_MAX_VIEWS_OVR = 38449;
  
  public static final int GL_FRAMEBUFFER_INCOMPLETE_VIEW_TARGETS_OVR = 38451;
  
  static {
    GL.initialize();
  }
  
  protected OVRMultiview() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { caps.glFramebufferTextureMultiviewOVR, 
          caps.hasDSA(ext) ? caps.glNamedFramebufferTextureMultiviewOVR : -1L });
  }
  
  public static native void glFramebufferTextureMultiviewOVR(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLsizei") int paramInt6);
  
  public static native void glNamedFramebufferTextureMultiviewOVR(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLsizei") int paramInt6);
}
