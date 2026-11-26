package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDirectStateAccess {
  public static final int GL_PROGRAM_MATRIX_EXT = 36397;
  
  public static final int GL_TRANSPOSE_PROGRAM_MATRIX_EXT = 36398;
  
  public static final int GL_PROGRAM_MATRIX_STACK_DEPTH_EXT = 36399;
  
  static {
    GL.initialize();
  }
  
  protected EXTDirectStateAccess() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { 
          caps.glClientAttribDefaultEXT, caps.glPushClientAttribDefaultEXT, caps.glMatrixLoadfEXT, caps.glMatrixLoaddEXT, caps.glMatrixMultfEXT, caps.glMatrixMultdEXT, caps.glMatrixLoadIdentityEXT, caps.glMatrixRotatefEXT, caps.glMatrixRotatedEXT, caps.glMatrixScalefEXT, 
          caps.glMatrixScaledEXT, caps.glMatrixTranslatefEXT, caps.glMatrixTranslatedEXT, caps.glMatrixOrthoEXT, caps.glMatrixFrustumEXT, caps.glMatrixPushEXT, caps.glMatrixPopEXT, caps.glTextureParameteriEXT, caps.glTextureParameterivEXT, caps.glTextureParameterfEXT, 
          caps.glTextureParameterfvEXT, caps.glTextureImage1DEXT, caps.glTextureImage2DEXT, caps.glTextureSubImage1DEXT, caps.glTextureSubImage2DEXT, caps.glCopyTextureImage1DEXT, caps.glCopyTextureImage2DEXT, caps.glCopyTextureSubImage1DEXT, caps.glCopyTextureSubImage2DEXT, caps.glGetTextureImageEXT, 
          caps.glGetTextureParameterfvEXT, caps.glGetTextureParameterivEXT, caps.glGetTextureLevelParameterfvEXT, caps.glGetTextureLevelParameterivEXT, 






          
          ext.contains("OpenGL12") ? caps.glTextureImage3DEXT : -1L, 
          ext.contains("OpenGL12") ? caps.glTextureSubImage3DEXT : -1L, ext.contains("OpenGL12") ? caps.glCopyTextureSubImage3DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glBindMultiTextureEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexCoordPointerEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexEnvfEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexEnvfvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexEnviEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexEnvivEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexGendEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexGendvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexGenfEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexGenfvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexGeniEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexGenivEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexEnvfvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexEnvivEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexGendvEXT : -1L, ext.contains("OpenGL13") ? caps.glGetMultiTexGenfvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexGenivEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexParameteriEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexParameterivEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexParameterfEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexParameterfvEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexImage2DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexSubImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexSubImage2DEXT : -1L, ext.contains("OpenGL13") ? caps.glCopyMultiTexImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCopyMultiTexImage2DEXT : -1L, ext.contains("OpenGL13") ? caps.glCopyMultiTexSubImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCopyMultiTexSubImage2DEXT : -1L, ext.contains("OpenGL13") ? caps.glGetMultiTexImageEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexParameterfvEXT : -1L, ext.contains("OpenGL13") ? caps.glGetMultiTexParameterivEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexLevelParameterfvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetMultiTexLevelParameterivEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMultiTexImage3DEXT : -1L, ext.contains("OpenGL13") ? caps.glMultiTexSubImage3DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCopyMultiTexSubImage3DEXT : -1L, ext.contains("OpenGL13") ? caps.glEnableClientStateIndexedEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glDisableClientStateIndexedEXT : -1L, ext.contains("OpenGL13") ? caps.glGetFloatIndexedvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetDoubleIndexedvEXT : -1L, ext.contains("OpenGL13") ? caps.glGetPointerIndexedvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glEnableIndexedEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glDisableIndexedEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glIsEnabledIndexedEXT : -1L, ext.contains("OpenGL13") ? caps.glGetIntegerIndexedvEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetBooleanIndexedvEXT : -1L, ext.contains("GL_ARB_vertex_program") ? caps.glNamedProgramStringEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glNamedProgramLocalParameter4dEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glNamedProgramLocalParameter4dvEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glNamedProgramLocalParameter4fEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glNamedProgramLocalParameter4fvEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glGetNamedProgramLocalParameterdvEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glGetNamedProgramLocalParameterfvEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glGetNamedProgramivEXT : -1L, 
          ext.contains("GL_ARB_vertex_program") ? caps.glGetNamedProgramStringEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedTextureImage3DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedTextureImage2DEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedTextureImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedTextureSubImage3DEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedTextureSubImage2DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedTextureSubImage1DEXT : -1L, ext.contains("OpenGL13") ? caps.glGetCompressedTextureImageEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedMultiTexImage3DEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedMultiTexImage2DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedMultiTexImage1DEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedMultiTexSubImage3DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glCompressedMultiTexSubImage2DEXT : -1L, ext.contains("OpenGL13") ? caps.glCompressedMultiTexSubImage1DEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glGetCompressedMultiTexImageEXT : -1L, ext.contains("OpenGL13") ? caps.glMatrixLoadTransposefEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMatrixLoadTransposedEXT : -1L, ext.contains("OpenGL13") ? caps.glMatrixMultTransposefEXT : -1L, 
          ext.contains("OpenGL13") ? caps.glMatrixMultTransposedEXT : -1L, ext.contains("OpenGL15") ? caps.glNamedBufferDataEXT : -1L, 
          ext.contains("OpenGL15") ? caps.glNamedBufferSubDataEXT : -1L, ext.contains("OpenGL15") ? caps.glMapNamedBufferEXT : -1L, 
          ext.contains("OpenGL15") ? caps.glUnmapNamedBufferEXT : -1L, ext.contains("OpenGL15") ? caps.glGetNamedBufferParameterivEXT : -1L, 
          ext.contains("OpenGL15") ? caps.glGetNamedBufferSubDataEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform1fEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform2fEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform3fEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform4fEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform1iEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform2iEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform3iEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform4iEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform1fvEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform2fvEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform3fvEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform4fvEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform1ivEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform2ivEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniform3ivEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniform4ivEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniformMatrix2fvEXT : -1L, 
          ext.contains("OpenGL20") ? caps.glProgramUniformMatrix3fvEXT : -1L, ext.contains("OpenGL20") ? caps.glProgramUniformMatrix4fvEXT : -1L, 
          ext.contains("OpenGL21") ? caps.glProgramUniformMatrix2x3fvEXT : -1L, ext.contains("OpenGL21") ? caps.glProgramUniformMatrix3x2fvEXT : -1L, 
          ext.contains("OpenGL21") ? caps.glProgramUniformMatrix2x4fvEXT : -1L, ext.contains("OpenGL21") ? caps.glProgramUniformMatrix4x2fvEXT : -1L, 
          ext.contains("OpenGL21") ? caps.glProgramUniformMatrix3x4fvEXT : -1L, ext.contains("OpenGL21") ? caps.glProgramUniformMatrix4x3fvEXT : -1L, 
          ext.contains("GL_EXT_texture_buffer_object") ? caps.glTextureBufferEXT : -1L, 
          ext.contains("GL_EXT_texture_buffer_object") ? caps.glMultiTexBufferEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glTextureParameterIivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glTextureParameterIuivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glGetTextureParameterIivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glGetTextureParameterIuivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glMultiTexParameterIivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glMultiTexParameterIuivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glGetMultiTexParameterIivEXT : -1L, 
          ext.contains("GL_EXT_texture_integer") ? caps.glGetMultiTexParameterIuivEXT : -1L, 
          ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform1uiEXT : -1L, ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform2uiEXT : -1L, 
          ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform3uiEXT : -1L, ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform4uiEXT : -1L, 
          ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform1uivEXT : -1L, ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform2uivEXT : -1L, 
          ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform3uivEXT : -1L, ext.contains("GL_EXT_gpu_shader4") ? caps.glProgramUniform4uivEXT : -1L, 
          ext.contains("GL_EXT_gpu_program_parameters") ? caps.glNamedProgramLocalParameters4fvEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParameterI4iEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParameterI4ivEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParametersI4ivEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParameterI4uiEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParameterI4uivEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glNamedProgramLocalParametersI4uivEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glGetNamedProgramLocalParameterIivEXT : -1L, 
          ext.contains("GL_NV_gpu_program4") ? caps.glGetNamedProgramLocalParameterIuivEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glNamedRenderbufferStorageEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGetNamedRenderbufferParameterivEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glNamedRenderbufferStorageMultisampleEXT : -1L, 
          ext.contains("GL_NV_framebuffer_multisample_coverage") ? caps.glNamedRenderbufferStorageMultisampleCoverageEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glCheckNamedFramebufferStatusEXT : -1L, ext.contains("OpenGL30") ? caps.glNamedFramebufferTexture1DEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glNamedFramebufferTexture2DEXT : -1L, ext.contains("OpenGL30") ? caps.glNamedFramebufferTexture3DEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glNamedFramebufferRenderbufferEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGetNamedFramebufferAttachmentParameterivEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGenerateTextureMipmapEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGenerateMultiTexMipmapEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glFramebufferDrawBufferEXT : -1L, ext.contains("OpenGL30") ? caps.glFramebufferDrawBuffersEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glFramebufferReadBufferEXT : -1L, ext.contains("OpenGL30") ? caps.glGetFramebufferParameterivEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glNamedCopyBufferSubDataEXT : -1L, (
          ext.contains("GL_EXT_geometry_shader4") || ext.contains("GL_NV_gpu_program4")) ? caps.glNamedFramebufferTextureEXT : -1L, (
          ext.contains("GL_EXT_geometry_shader4") || ext.contains("GL_NV_gpu_program4")) ? caps.glNamedFramebufferTextureLayerEXT : -1L, (
          ext.contains("GL_EXT_geometry_shader4") || ext.contains("GL_NV_gpu_program4")) ? caps.glNamedFramebufferTextureFaceEXT : -1L, 
          ext.contains("GL_NV_explicit_multisample") ? caps.glTextureRenderbufferEXT : -1L, 
          ext.contains("GL_NV_explicit_multisample") ? caps.glMultiTexRenderbufferEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArrayVertexOffsetEXT : -1L, ext.contains("OpenGL30") ? caps.glVertexArrayColorOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArrayEdgeFlagOffsetEXT : -1L, ext.contains("OpenGL30") ? caps.glVertexArrayIndexOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArrayNormalOffsetEXT : -1L, ext.contains("OpenGL30") ? caps.glVertexArrayTexCoordOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArrayMultiTexCoordOffsetEXT : -1L, ext.contains("OpenGL30") ? caps.glVertexArrayFogCoordOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArraySecondaryColorOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glVertexArrayVertexAttribOffsetEXT : -1L, ext.contains("OpenGL30") ? caps.glVertexArrayVertexAttribIOffsetEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glEnableVertexArrayEXT : -1L, ext.contains("OpenGL30") ? caps.glDisableVertexArrayEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glEnableVertexArrayAttribEXT : -1L, ext.contains("OpenGL30") ? caps.glDisableVertexArrayAttribEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGetVertexArrayIntegervEXT : -1L, ext.contains("OpenGL30") ? caps.glGetVertexArrayPointervEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glGetVertexArrayIntegeri_vEXT : -1L, ext.contains("OpenGL30") ? caps.glGetVertexArrayPointeri_vEXT : -1L, 
          ext.contains("OpenGL30") ? caps.glMapNamedBufferRangeEXT : -1L, ext.contains("OpenGL30") ? caps.glFlushMappedNamedBufferRangeEXT : -1L });
  }
  
  public static void glMatrixLoadfEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixLoadfEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixLoaddEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") DoubleBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixLoaddEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixMultfEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixMultfEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixMultdEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") DoubleBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixMultdEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glTextureParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 4); 
    nglTextureParameterivEXT(texture, target, pname, MemoryUtil.memAddress(param));
  }
  
  public static void glTextureParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 4); 
    nglTextureParameterfvEXT(texture, target, pname, MemoryUtil.memAddress(param));
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, pixels);
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, pixels);
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, pixels);
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") long pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, pixels);
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") DoubleBuffer pixels) {
    nglGetTextureImageEXT(texture, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetTextureParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureParameterfvEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetTextureParameterfEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetTextureParameterfvEXT(texture, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetTextureParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureParameterivEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetTextureParameteriEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetTextureParameterivEXT(texture, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetTextureLevelParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureLevelParameterfvEXT(texture, target, level, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetTextureLevelParameterfEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetTextureLevelParameterfvEXT(texture, target, level, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetTextureLevelParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureLevelParameterivEXT(texture, target, level, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetTextureLevelParameteriEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetTextureLevelParameterivEXT(texture, target, level, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, pixels);
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
    nglMultiTexCoordPointerEXT(texunit, size, type, stride, MemoryUtil.memAddress(pointer));
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer) {
    nglMultiTexCoordPointerEXT(texunit, size, type, stride, pointer);
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer) {
    nglMultiTexCoordPointerEXT(texunit, size, type, stride, MemoryUtil.memAddress(pointer));
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer) {
    nglMultiTexCoordPointerEXT(texunit, size, type, stride, MemoryUtil.memAddress(pointer));
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer) {
    nglMultiTexCoordPointerEXT(texunit, size, type, stride, MemoryUtil.memAddress(pointer));
  }
  
  public static void glMultiTexEnvfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexEnvfvEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glMultiTexEnvivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexEnvivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glMultiTexGendvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble const *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexGendvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glMultiTexGenfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexGenfvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glMultiTexGenivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexGenivEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glGetMultiTexEnvfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexEnvfvEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetMultiTexEnvfEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetMultiTexEnvfvEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexEnvivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexEnvivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexEnviEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexEnvivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexGendvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexGendvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static double glGetMultiTexGendEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      DoubleBuffer params = stack.callocDouble(1);
      nglGetMultiTexGendvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexGenfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexGenfvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetMultiTexGenfEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetMultiTexGenfvEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexGenivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexGenivEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexGeniEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexGenivEXT(texunit, coord, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMultiTexParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 4); 
    nglMultiTexParameterivEXT(texunit, target, pname, MemoryUtil.memAddress(param));
  }
  
  public static void glMultiTexParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 4); 
    nglMultiTexParameterfvEXT(texunit, target, pname, MemoryUtil.memAddress(param));
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, pixels);
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, pixels);
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, pixels);
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels);
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") long pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, pixels);
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") DoubleBuffer pixels) {
    nglGetMultiTexImageEXT(texunit, target, level, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetMultiTexParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexParameterfvEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetMultiTexParameterfEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetMultiTexParameterfvEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexParameterivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexParameteriEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexParameterivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexLevelParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexLevelParameterfvEXT(texunit, target, level, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetMultiTexLevelParameterfEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetMultiTexLevelParameterfvEXT(texunit, target, level, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexLevelParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexLevelParameterivEXT(texunit, target, level, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexLevelParameteriEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexLevelParameterivEXT(texunit, target, level, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") long pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, pixels);
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") long pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") ShortBuffer pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") FloatBuffer pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") DoubleBuffer pixels) {
    nglMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
  }
  
  public static void glGetFloatIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetFloatIndexedvEXT(target, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetFloatIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetFloatIndexedvEXT(target, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetDoubleIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetDoubleIndexedvEXT(target, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static double glGetDoubleIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      DoubleBuffer params = stack.callocDouble(1);
      nglGetDoubleIndexedvEXT(target, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetPointerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("void **") PointerBuffer params) {
    if (Checks.CHECKS)
      Checks.check((CustomBuffer)params, 1); 
    nglGetPointerIndexedvEXT(target, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static long glGetPointerIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      PointerBuffer params = stack.callocPointer(1);
      nglGetPointerIndexedvEXT(target, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetFloati_vEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetFloati_vEXT(pname, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetFloatiEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetFloati_vEXT(pname, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetDoublei_vEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLdouble *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetDoublei_vEXT(pname, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static double glGetDoubleiEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      DoubleBuffer params = stack.callocDouble(1);
      nglGetDoublei_vEXT(pname, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetPointeri_vEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("void **") PointerBuffer params) {
    if (Checks.CHECKS)
      Checks.check((CustomBuffer)params, 1); 
    nglGetPointeri_vEXT(pname, index, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static long glGetPointeriEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      PointerBuffer params = stack.callocPointer(1);
      nglGetPointeri_vEXT(pname, index, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glEnableIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    EXTDrawBuffers2.glEnableIndexedEXT(target, index);
  }
  
  public static void glDisableIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    EXTDrawBuffers2.glDisableIndexedEXT(target, index);
  }
  
  @NativeType("GLboolean")
  public static boolean glIsEnabledIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    return EXTDrawBuffers2.glIsEnabledIndexedEXT(target, index);
  }
  
  public static void nglGetIntegerIndexedvEXT(int target, int index, long data) {
    EXTDrawBuffers2.nglGetIntegerIndexedvEXT(target, index, data);
  }
  
  public static void glGetIntegerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer data) {
    EXTDrawBuffers2.glGetIntegerIndexedvEXT(target, index, data);
  }
  
  @NativeType("void")
  public static int glGetIntegerIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    return EXTDrawBuffers2.glGetIntegerIndexedEXT(target, index);
  }
  
  public static void nglGetBooleanIndexedvEXT(int target, int index, long data) {
    EXTDrawBuffers2.nglGetBooleanIndexedvEXT(target, index, data);
  }
  
  public static void glGetBooleanIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLboolean *") ByteBuffer data) {
    EXTDrawBuffers2.glGetBooleanIndexedvEXT(target, index, data);
  }
  
  @NativeType("void")
  public static boolean glGetBooleanIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    return EXTDrawBuffers2.glGetBooleanIndexedEXT(target, index);
  }
  
  public static void glNamedProgramStringEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer string) {
    nglNamedProgramStringEXT(program, target, format, string.remaining(), MemoryUtil.memAddress(string));
  }
  
  public static void glNamedProgramLocalParameter4dvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglNamedProgramLocalParameter4dvEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glNamedProgramLocalParameter4fvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglNamedProgramLocalParameter4fvEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedProgramLocalParameterdvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble *") DoubleBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglGetNamedProgramLocalParameterdvEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedProgramLocalParameterfvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat *") FloatBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglGetNamedProgramLocalParameterfvEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedProgramivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetNamedProgramivEXT(program, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetNamedProgramiEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetNamedProgramivEXT(program, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetNamedProgramStringEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("void *") ByteBuffer string) {
    if (Checks.CHECKS && 
      Checks.DEBUG)
      Checks.check((Buffer)string, glGetNamedProgramiEXT(program, target, 34343)); 
    nglGetNamedProgramStringEXT(program, target, pname, MemoryUtil.memAddress(string));
  }
  
  public static void glCompressedTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, imageSize, data);
  }
  
  public static void glCompressedTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedTextureImage2DEXT(texture, target, level, internalformat, width, height, border, imageSize, data);
  }
  
  public static void glCompressedTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureImage2DEXT(texture, target, level, internalformat, width, height, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedTextureImage1DEXT(texture, target, level, internalformat, width, border, imageSize, data);
  }
  
  public static void glCompressedTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureImage1DEXT(texture, target, level, internalformat, width, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
  }
  
  public static void glCompressedTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glCompressedTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, imageSize, data);
  }
  
  public static void glCompressedTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glCompressedTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedTextureSubImage1DEXT(texture, target, level, xoffset, width, format, imageSize, data);
  }
  
  public static void glCompressedTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedTextureSubImage1DEXT(texture, target, level, xoffset, width, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetCompressedTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer img) {
    if (Checks.CHECKS && 
      Checks.DEBUG)
      Checks.check((Buffer)img, glGetTextureLevelParameteriEXT(texture, target, level, 34464)); 
    nglGetCompressedTextureImageEXT(texture, target, level, MemoryUtil.memAddress(img));
  }
  
  public static void glGetCompressedTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") long img) {
    nglGetCompressedTextureImageEXT(texture, target, level, img);
  }
  
  public static void glCompressedMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, imageSize, data);
  }
  
  public static void glCompressedMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexImage3DEXT(texunit, target, level, internalformat, width, height, depth, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, imageSize, data);
  }
  
  public static void glCompressedMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLsizei") int imageSize, @Nullable @NativeType("void const *") long data) {
    nglCompressedMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, imageSize, data);
  }
  
  public static void glCompressedMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @Nullable @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, Checks.remainingSafe(data), MemoryUtil.memAddressSafe(data));
  }
  
  public static void glCompressedMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
  }
  
  public static void glCompressedMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glCompressedMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, imageSize, data);
  }
  
  public static void glCompressedMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glCompressedMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLsizei") int imageSize, @NativeType("void const *") long data) {
    nglCompressedMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, imageSize, data);
  }
  
  public static void glCompressedMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer data) {
    nglCompressedMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetCompressedMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer img) {
    if (Checks.CHECKS && 
      Checks.DEBUG)
      Checks.check((Buffer)img, glGetMultiTexLevelParameteriEXT(texunit, target, level, 34464)); 
    nglGetCompressedMultiTexImageEXT(texunit, target, level, MemoryUtil.memAddress(img));
  }
  
  public static void glGetCompressedMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") long img) {
    nglGetCompressedMultiTexImageEXT(texunit, target, level, img);
  }
  
  public static void glMatrixLoadTransposefEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixLoadTransposefEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixLoadTransposedEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") DoubleBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixLoadTransposedEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixMultTransposefEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixMultTransposefEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glMatrixMultTransposedEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") DoubleBuffer m) {
    if (Checks.CHECKS)
      Checks.check((Buffer)m, 16); 
    nglMatrixMultTransposedEXT(matrixMode, MemoryUtil.memAddress(m));
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLsizeiptr") long size, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, size, 0L, usage);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") ByteBuffer data, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, (long)data.remaining(), MemoryUtil.memAddress(data), usage);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") ShortBuffer data, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, Integer.toUnsignedLong(data.remaining()) << 1L, MemoryUtil.memAddress(data), usage);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") IntBuffer data, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data), usage);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") FloatBuffer data, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data), usage);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") DoubleBuffer data, @NativeType("GLenum") int usage) {
    nglNamedBufferDataEXT(buffer, Integer.toUnsignedLong(data.remaining()) << 3L, MemoryUtil.memAddress(data), usage);
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data) {
    nglNamedBufferSubDataEXT(buffer, offset, (long)data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ShortBuffer data) {
    nglNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1L, MemoryUtil.memAddress(data));
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data) {
    nglNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") FloatBuffer data) {
    nglNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") DoubleBuffer data) {
    nglNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3L, MemoryUtil.memAddress(data));
  }
  
  @Nullable
  @NativeType("void *")
  public static ByteBuffer glMapNamedBufferEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access) {
    long __result = nglMapNamedBufferEXT(buffer, access);
    return MemoryUtil.memByteBufferSafe(__result, glGetNamedBufferParameteriEXT(buffer, 34660));
  }
  
  @Nullable
  @NativeType("void *")
  public static ByteBuffer glMapNamedBufferEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, @Nullable ByteBuffer old_buffer) {
    long __result = nglMapNamedBufferEXT(buffer, access);
    int length = glGetNamedBufferParameteriEXT(buffer, 34660);
    return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
  }
  
  @Nullable
  @NativeType("void *")
  public static ByteBuffer glMapNamedBufferEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, long length, @Nullable ByteBuffer old_buffer) {
    long __result = nglMapNamedBufferEXT(buffer, access);
    return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
  }
  
  public static void glGetNamedBufferParameterivEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetNamedBufferParameterivEXT(buffer, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetNamedBufferParameteriEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetNamedBufferParameterivEXT(buffer, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ByteBuffer data) {
    nglGetNamedBufferSubDataEXT(buffer, offset, (long)data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ShortBuffer data) {
    nglGetNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1L, MemoryUtil.memAddress(data));
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") IntBuffer data) {
    nglGetNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") FloatBuffer data) {
    nglGetNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") DoubleBuffer data) {
    nglGetNamedBufferSubDataEXT(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3L, MemoryUtil.memAddress(data));
  }
  
  public static void glProgramUniform1fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniform1fvEXT(program, location, value.remaining(), MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniform2fvEXT(program, location, value.remaining() >> 1, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniform3fvEXT(program, location, value.remaining() / 3, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniform4fvEXT(program, location, value.remaining() >> 2, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform1ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
    nglProgramUniform1ivEXT(program, location, value.remaining(), MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform2ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
    nglProgramUniform2ivEXT(program, location, value.remaining() >> 1, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform3ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
    nglProgramUniform3ivEXT(program, location, value.remaining() / 3, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform4ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
    nglProgramUniform4ivEXT(program, location, value.remaining() >> 2, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix2fvEXT(program, location, value.remaining() >> 2, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix3fvEXT(program, location, value.remaining() / 9, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix4fvEXT(program, location, value.remaining() >> 4, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix2x3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix2x3fvEXT(program, location, value.remaining() / 6, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix3x2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix3x2fvEXT(program, location, value.remaining() / 6, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix2x4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix2x4fvEXT(program, location, value.remaining() >> 3, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix4x2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix4x2fvEXT(program, location, value.remaining() >> 3, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix3x4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix3x4fvEXT(program, location, value.remaining() / 12, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniformMatrix4x3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value) {
    nglProgramUniformMatrix4x3fvEXT(program, location, value.remaining() / 12, transpose, MemoryUtil.memAddress(value));
  }
  
  public static void glTextureParameterIivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglTextureParameterIivEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glTextureParameterIuivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglTextureParameterIuivEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glGetTextureParameterIivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureParameterIivEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetTextureParameterIiEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetTextureParameterIivEXT(texture, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetTextureParameterIuivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetTextureParameterIuivEXT(texture, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetTextureParameterIuiEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetTextureParameterIuivEXT(texture, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMultiTexParameterIivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexParameterIivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glMultiTexParameterIuivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglMultiTexParameterIuivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  public static void glGetMultiTexParameterIivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexParameterIivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexParameterIiEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexParameterIivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetMultiTexParameterIuivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetMultiTexParameterIuivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetMultiTexParameterIuiEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetMultiTexParameterIuivEXT(texunit, target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glProgramUniform1uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
    nglProgramUniform1uivEXT(program, location, value.remaining(), MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform2uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
    nglProgramUniform2uivEXT(program, location, value.remaining() >> 1, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform3uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
    nglProgramUniform3uivEXT(program, location, value.remaining() / 3, MemoryUtil.memAddress(value));
  }
  
  public static void glProgramUniform4uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
    nglProgramUniform4uivEXT(program, location, value.remaining() >> 2, MemoryUtil.memAddress(value));
  }
  
  public static void glNamedProgramLocalParameters4fvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer params) {
    nglNamedProgramLocalParameters4fvEXT(program, target, index, params.remaining() >> 2, MemoryUtil.memAddress(params));
  }
  
  public static void glNamedProgramLocalParameterI4ivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglNamedProgramLocalParameterI4ivEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glNamedProgramLocalParametersI4ivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer params) {
    nglNamedProgramLocalParametersI4ivEXT(program, target, index, params.remaining() >> 2, MemoryUtil.memAddress(params));
  }
  
  public static void glNamedProgramLocalParameterI4uivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglNamedProgramLocalParameterI4uivEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glNamedProgramLocalParametersI4uivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer params) {
    nglNamedProgramLocalParametersI4uivEXT(program, target, index, params.remaining() >> 2, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedProgramLocalParameterIivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglGetNamedProgramLocalParameterIivEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedProgramLocalParameterIuivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 4); 
    nglGetNamedProgramLocalParameterIuivEXT(program, target, index, MemoryUtil.memAddress(params));
  }
  
  public static void glGetNamedRenderbufferParameterivEXT(@NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetNamedRenderbufferParameterivEXT(renderbuffer, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetNamedRenderbufferParameteriEXT(@NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetNamedRenderbufferParameterivEXT(renderbuffer, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetNamedFramebufferAttachmentParameterivEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetNamedFramebufferAttachmentParameterivEXT(framebuffer, attachment, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetNamedFramebufferAttachmentParameteriEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetNamedFramebufferAttachmentParameterivEXT(framebuffer, attachment, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glFramebufferDrawBuffersEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") IntBuffer bufs) {
    nglFramebufferDrawBuffersEXT(framebuffer, bufs.remaining(), MemoryUtil.memAddress(bufs));
  }
  
  public static void glGetFramebufferParameterivEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 1); 
    nglGetFramebufferParameterivEXT(framebuffer, pname, MemoryUtil.memAddress(param));
  }
  
  @NativeType("void")
  public static int glGetFramebufferParameteriEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer param = stack.callocInt(1);
      nglGetFramebufferParameterivEXT(framebuffer, pname, MemoryUtil.memAddress(param));
      return param.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetVertexArrayIntegervEXT(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 1); 
    nglGetVertexArrayIntegervEXT(vaobj, pname, MemoryUtil.memAddress(param));
  }
  
  @NativeType("void")
  public static int glGetVertexArrayIntegerEXT(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer param = stack.callocInt(1);
      nglGetVertexArrayIntegervEXT(vaobj, pname, MemoryUtil.memAddress(param));
      return param.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetVertexArrayPointervEXT(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer param) {
    if (Checks.CHECKS)
      Checks.check((CustomBuffer)param, 1); 
    nglGetVertexArrayPointervEXT(vaobj, pname, MemoryUtil.memAddress(param));
  }
  
  @NativeType("void")
  public static long glGetVertexArrayPointerEXT(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      PointerBuffer param = stack.callocPointer(1);
      nglGetVertexArrayPointervEXT(vaobj, pname, MemoryUtil.memAddress(param));
      return param.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetVertexArrayIntegeri_vEXT(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
    if (Checks.CHECKS)
      Checks.check((Buffer)param, 1); 
    nglGetVertexArrayIntegeri_vEXT(vaobj, index, pname, MemoryUtil.memAddress(param));
  }
  
  @NativeType("void")
  public static int glGetVertexArrayIntegeriEXT(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer param = stack.callocInt(1);
      nglGetVertexArrayIntegeri_vEXT(vaobj, index, pname, MemoryUtil.memAddress(param));
      return param.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetVertexArrayPointeri_vEXT(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer param) {
    if (Checks.CHECKS)
      Checks.check((CustomBuffer)param, 1); 
    nglGetVertexArrayPointeri_vEXT(vaobj, index, pname, MemoryUtil.memAddress(param));
  }
  
  @NativeType("void")
  public static long glGetVertexArrayPointeriEXT(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      PointerBuffer param = stack.callocPointer(1);
      nglGetVertexArrayPointeri_vEXT(vaobj, index, pname, MemoryUtil.memAddress(param));
      return param.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @Nullable
  @NativeType("void *")
  public static ByteBuffer glMapNamedBufferRangeEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length, @NativeType("GLbitfield") int access) {
    long __result = nglMapNamedBufferRangeEXT(buffer, offset, length, access);
    return MemoryUtil.memByteBufferSafe(__result, (int)length);
  }
  
  @Nullable
  @NativeType("void *")
  public static ByteBuffer glMapNamedBufferRangeEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length, @NativeType("GLbitfield") int access, @Nullable ByteBuffer old_buffer) {
    long __result = nglMapNamedBufferRangeEXT(buffer, offset, length, access);
    return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
  }
  
  public static void glMatrixLoadfEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
    long __functionAddress = (GL.getICD()).glMatrixLoadfEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixLoaddEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") double[] m) {
    long __functionAddress = (GL.getICD()).glMatrixLoaddEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixMultfEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
    long __functionAddress = (GL.getICD()).glMatrixMultfEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixMultdEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") double[] m) {
    long __functionAddress = (GL.getICD()).glMatrixMultdEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glTextureParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] param) {
    long __functionAddress = (GL.getICD()).glTextureParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 4);
    } 
    JNI.callPV(texture, target, pname, param, __functionAddress);
  }
  
  public static void glTextureParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] param) {
    long __functionAddress = (GL.getICD()).glTextureParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 4);
    } 
    JNI.callPV(texture, target, pname, param, __functionAddress);
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage1DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage2DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glGetTextureImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glGetTextureImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glGetTextureImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetTextureImageEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glGetTextureImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetTextureParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glGetTextureParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glGetTextureLevelParameterfvEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureLevelParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, level, pname, params, __functionAddress);
  }
  
  public static void glGetTextureLevelParameterivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureLevelParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, level, pname, params, __functionAddress);
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glTextureSubImage3DEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glTextureSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") short[] pointer) {
    long __functionAddress = (GL.getICD()).glMultiTexCoordPointerEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, size, type, stride, pointer, __functionAddress);
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") int[] pointer) {
    long __functionAddress = (GL.getICD()).glMultiTexCoordPointerEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, size, type, stride, pointer, __functionAddress);
  }
  
  public static void glMultiTexCoordPointerEXT(@NativeType("GLenum") int texunit, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") float[] pointer) {
    long __functionAddress = (GL.getICD()).glMultiTexCoordPointerEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, size, type, stride, pointer, __functionAddress);
  }
  
  public static void glMultiTexEnvfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexEnvfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glMultiTexEnvivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexEnvivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glMultiTexGendvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble const *") double[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexGendvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glMultiTexGenfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexGenfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glMultiTexGenivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexGenivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexEnvfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexEnvfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexEnvivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexEnvivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexGendvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexGendvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexGenfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexGenfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexGenivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexGenivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, coord, pname, params, __functionAddress);
  }
  
  public static void glMultiTexParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] param) {
    long __functionAddress = (GL.getICD()).glMultiTexParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 4);
    } 
    JNI.callPV(texunit, target, pname, param, __functionAddress);
  }
  
  public static void glMultiTexParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] param) {
    long __functionAddress = (GL.getICD()).glMultiTexParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 4);
    } 
    JNI.callPV(texunit, target, pname, param, __functionAddress);
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage1DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLsizei") int width, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage1DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, width, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage2DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage2DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glGetMultiTexImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glGetMultiTexImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glGetMultiTexImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetMultiTexImageEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glGetMultiTexImageEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, format, type, pixels, __functionAddress);
  }
  
  public static void glGetMultiTexParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexLevelParameterfvEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexLevelParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, level, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexLevelParameterivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexLevelParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, level, pname, params, __functionAddress);
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLint") int border, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @Nullable @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") short[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") int[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") float[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glMultiTexSubImage3DEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLint") int xoffset, @NativeType("GLint") int yoffset, @NativeType("GLint") int zoffset, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLsizei") int depth, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void const *") double[] pixels) {
    long __functionAddress = (GL.getICD()).glMultiTexSubImage3DEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
  }
  
  public static void glGetFloatIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetFloatIndexedvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(target, index, params, __functionAddress);
  }
  
  public static void glGetDoubleIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble *") double[] params) {
    long __functionAddress = (GL.getICD()).glGetDoubleIndexedvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(target, index, params, __functionAddress);
  }
  
  public static void glGetFloati_vEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetFloati_vEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(pname, index, params, __functionAddress);
  }
  
  public static void glGetDoublei_vEXT(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLdouble *") double[] params) {
    long __functionAddress = (GL.getICD()).glGetDoublei_vEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(pname, index, params, __functionAddress);
  }
  
  public static void glGetIntegerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] data) {
    EXTDrawBuffers2.glGetIntegerIndexedvEXT(target, index, data);
  }
  
  public static void glNamedProgramLocalParameter4dvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParameter4dvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glNamedProgramLocalParameter4fvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParameter4fvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glGetNamedProgramLocalParameterdvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLdouble *") double[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedProgramLocalParameterdvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glGetNamedProgramLocalParameterfvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedProgramLocalParameterfvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glGetNamedProgramivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedProgramivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(program, target, pname, params, __functionAddress);
  }
  
  public static void glMatrixLoadTransposefEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
    long __functionAddress = (GL.getICD()).glMatrixLoadTransposefEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixLoadTransposedEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") double[] m) {
    long __functionAddress = (GL.getICD()).glMatrixLoadTransposedEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixMultTransposefEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
    long __functionAddress = (GL.getICD()).glMatrixMultTransposefEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glMatrixMultTransposedEXT(@NativeType("GLenum") int matrixMode, @NativeType("GLdouble const *") double[] m) {
    long __functionAddress = (GL.getICD()).glMatrixMultTransposedEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(m, 16);
    } 
    JNI.callPV(matrixMode, m, __functionAddress);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") short[] data, @NativeType("GLenum") int usage) {
    long __functionAddress = (GL.getICD()).glNamedBufferDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 1L, data, usage, __functionAddress);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") int[] data, @NativeType("GLenum") int usage) {
    long __functionAddress = (GL.getICD()).glNamedBufferDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2L, data, usage, __functionAddress);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") float[] data, @NativeType("GLenum") int usage) {
    long __functionAddress = (GL.getICD()).glNamedBufferDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2L, data, usage, __functionAddress);
  }
  
  public static void glNamedBufferDataEXT(@NativeType("GLuint") int buffer, @NativeType("void const *") double[] data, @NativeType("GLenum") int usage) {
    long __functionAddress = (GL.getICD()).glNamedBufferDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 3L, data, usage, __functionAddress);
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data) {
    long __functionAddress = (GL.getICD()).glNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 1L, data, __functionAddress);
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data) {
    long __functionAddress = (GL.getICD()).glNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data) {
    long __functionAddress = (GL.getICD()).glNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data) {
    long __functionAddress = (GL.getICD()).glNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3L, data, __functionAddress);
  }
  
  public static void glGetNamedBufferParameterivEXT(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedBufferParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(buffer, pname, params, __functionAddress);
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") short[] data) {
    long __functionAddress = (GL.getICD()).glGetNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 1L, data, __functionAddress);
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") int[] data) {
    long __functionAddress = (GL.getICD()).glGetNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") float[] data) {
    long __functionAddress = (GL.getICD()).glGetNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glGetNamedBufferSubDataEXT(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") double[] data) {
    long __functionAddress = (GL.getICD()).glGetNamedBufferSubDataEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3L, data, __functionAddress);
  }
  
  public static void glProgramUniform1fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform1fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length, value, __functionAddress);
  }
  
  public static void glProgramUniform2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform2fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 1, value, __functionAddress);
  }
  
  public static void glProgramUniform3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform3fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 3, value, __functionAddress);
  }
  
  public static void glProgramUniform4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 2, value, __functionAddress);
  }
  
  public static void glProgramUniform1ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform1ivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length, value, __functionAddress);
  }
  
  public static void glProgramUniform2ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform2ivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 1, value, __functionAddress);
  }
  
  public static void glProgramUniform3ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform3ivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 3, value, __functionAddress);
  }
  
  public static void glProgramUniform4ivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform4ivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 2, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix2fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 2, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix3fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 9, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 4, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix2x3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix2x3fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 6, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix3x2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix3x2fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 6, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix2x4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix2x4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 3, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix4x2fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix4x2fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 3, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix3x4fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix3x4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 12, transpose, value, __functionAddress);
  }
  
  public static void glProgramUniformMatrix4x3fvEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniformMatrix4x3fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 12, transpose, value, __functionAddress);
  }
  
  public static void glTextureParameterIivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glTextureParameterIivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glTextureParameterIuivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glTextureParameterIuivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glGetTextureParameterIivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureParameterIivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glGetTextureParameterIuivEXT(@NativeType("GLuint") int texture, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetTextureParameterIuivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texture, target, pname, params, __functionAddress);
  }
  
  public static void glMultiTexParameterIivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexParameterIivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glMultiTexParameterIuivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glMultiTexParameterIuivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexParameterIivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexParameterIivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glGetMultiTexParameterIuivEXT(@NativeType("GLenum") int texunit, @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetMultiTexParameterIuivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(texunit, target, pname, params, __functionAddress);
  }
  
  public static void glProgramUniform1uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform1uivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length, value, __functionAddress);
  }
  
  public static void glProgramUniform2uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform2uivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 1, value, __functionAddress);
  }
  
  public static void glProgramUniform3uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform3uivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length / 3, value, __functionAddress);
  }
  
  public static void glProgramUniform4uivEXT(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
    long __functionAddress = (GL.getICD()).glProgramUniform4uivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, value.length >> 2, value, __functionAddress);
  }
  
  public static void glNamedProgramLocalParameters4fvEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParameters4fvEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, target, index, params.length >> 2, params, __functionAddress);
  }
  
  public static void glNamedProgramLocalParameterI4ivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParameterI4ivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glNamedProgramLocalParametersI4ivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParametersI4ivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, target, index, params.length >> 2, params, __functionAddress);
  }
  
  public static void glNamedProgramLocalParameterI4uivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParameterI4uivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glNamedProgramLocalParametersI4uivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint const *") int[] params) {
    long __functionAddress = (GL.getICD()).glNamedProgramLocalParametersI4uivEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, target, index, params.length >> 2, params, __functionAddress);
  }
  
  public static void glGetNamedProgramLocalParameterIivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedProgramLocalParameterIivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glGetNamedProgramLocalParameterIuivEXT(@NativeType("GLuint") int program, @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedProgramLocalParameterIuivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 4);
    } 
    JNI.callPV(program, target, index, params, __functionAddress);
  }
  
  public static void glGetNamedRenderbufferParameterivEXT(@NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedRenderbufferParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(renderbuffer, pname, params, __functionAddress);
  }
  
  public static void glGetNamedFramebufferAttachmentParameterivEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetNamedFramebufferAttachmentParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(framebuffer, attachment, pname, params, __functionAddress);
  }
  
  public static void glFramebufferDrawBuffersEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int[] bufs) {
    long __functionAddress = (GL.getICD()).glFramebufferDrawBuffersEXT;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(framebuffer, bufs.length, bufs, __functionAddress);
  }
  
  public static void glGetFramebufferParameterivEXT(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
    long __functionAddress = (GL.getICD()).glGetFramebufferParameterivEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 1);
    } 
    JNI.callPV(framebuffer, pname, param, __functionAddress);
  }
  
  public static void glGetVertexArrayIntegervEXT(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
    long __functionAddress = (GL.getICD()).glGetVertexArrayIntegervEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 1);
    } 
    JNI.callPV(vaobj, pname, param, __functionAddress);
  }
  
  public static void glGetVertexArrayIntegeri_vEXT(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
    long __functionAddress = (GL.getICD()).glGetVertexArrayIntegeri_vEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(param, 1);
    } 
    JNI.callPV(vaobj, index, pname, param, __functionAddress);
  }
  
  public static native void glClientAttribDefaultEXT(@NativeType("GLbitfield") int paramInt);
  
  public static native void glPushClientAttribDefaultEXT(@NativeType("GLbitfield") int paramInt);
  
  public static native void nglMatrixLoadfEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixLoaddEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixMultfEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixMultdEXT(int paramInt, long paramLong);
  
  public static native void glMatrixLoadIdentityEXT(@NativeType("GLenum") int paramInt);
  
  public static native void glMatrixRotatefEXT(@NativeType("GLenum") int paramInt, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4);
  
  public static native void glMatrixRotatedEXT(@NativeType("GLenum") int paramInt, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3, @NativeType("GLdouble") double paramDouble4);
  
  public static native void glMatrixScalefEXT(@NativeType("GLenum") int paramInt, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3);
  
  public static native void glMatrixScaledEXT(@NativeType("GLenum") int paramInt, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3);
  
  public static native void glMatrixTranslatefEXT(@NativeType("GLenum") int paramInt, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3);
  
  public static native void glMatrixTranslatedEXT(@NativeType("GLenum") int paramInt, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3);
  
  public static native void glMatrixOrthoEXT(@NativeType("GLenum") int paramInt, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3, @NativeType("GLdouble") double paramDouble4, @NativeType("GLdouble") double paramDouble5, @NativeType("GLdouble") double paramDouble6);
  
  public static native void glMatrixFrustumEXT(@NativeType("GLenum") int paramInt, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3, @NativeType("GLdouble") double paramDouble4, @NativeType("GLdouble") double paramDouble5, @NativeType("GLdouble") double paramDouble6);
  
  public static native void glMatrixPushEXT(@NativeType("GLenum") int paramInt);
  
  public static native void glMatrixPopEXT(@NativeType("GLenum") int paramInt);
  
  public static native void glTextureParameteriEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void nglTextureParameterivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glTextureParameterfEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLfloat") float paramFloat);
  
  public static native void nglTextureParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglTextureImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong);
  
  public static native void nglTextureImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglTextureSubImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglTextureSubImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void glCopyTextureImage1DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLint") int paramInt8);
  
  public static native void glCopyTextureImage2DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLsizei") int paramInt8, @NativeType("GLint") int paramInt9);
  
  public static native void glCopyTextureSubImage1DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7);
  
  public static native void glCopyTextureSubImage2DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLsizei") int paramInt8, @NativeType("GLsizei") int paramInt9);
  
  public static native void nglGetTextureImageEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static native void nglGetTextureParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetTextureParameterivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetTextureLevelParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetTextureLevelParameterivEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglTextureImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, long paramLong);
  
  public static native void nglTextureSubImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, long paramLong);
  
  public static native void glCopyTextureSubImage3DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLint") int paramInt8, @NativeType("GLsizei") int paramInt9, @NativeType("GLsizei") int paramInt10);
  
  public static native void glBindMultiTextureEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void nglMultiTexCoordPointerEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glMultiTexEnvfEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLfloat") float paramFloat);
  
  public static native void nglMultiTexEnvfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexEnviEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void nglMultiTexEnvivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexGendEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLdouble") double paramDouble);
  
  public static native void nglMultiTexGendvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexGenfEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLfloat") float paramFloat);
  
  public static native void nglMultiTexGenfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexGeniEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void nglMultiTexGenivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexEnvfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexEnvivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexGendvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexGenfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexGenivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexParameteriEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void nglMultiTexParameterivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glMultiTexParameterfEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLfloat") float paramFloat);
  
  public static native void nglMultiTexParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMultiTexImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong);
  
  public static native void nglMultiTexImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglMultiTexSubImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglMultiTexSubImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void glCopyMultiTexImage1DEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLint") int paramInt8);
  
  public static native void glCopyMultiTexImage2DEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7, @NativeType("GLsizei") int paramInt8, @NativeType("GLint") int paramInt9);
  
  public static native void glCopyMultiTexSubImage1DEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLsizei") int paramInt7);
  
  public static native void glCopyMultiTexSubImage2DEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLsizei") int paramInt8, @NativeType("GLsizei") int paramInt9);
  
  public static native void nglGetMultiTexImageEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static native void nglGetMultiTexParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexParameterivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexLevelParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetMultiTexLevelParameterivEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglMultiTexImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, long paramLong);
  
  public static native void nglMultiTexSubImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, long paramLong);
  
  public static native void glCopyMultiTexSubImage3DEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLint") int paramInt8, @NativeType("GLsizei") int paramInt9, @NativeType("GLsizei") int paramInt10);
  
  public static native void glEnableClientStateIndexedEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glDisableClientStateIndexedEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glEnableClientStateiEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glDisableClientStateiEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void nglGetFloatIndexedvEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetDoubleIndexedvEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetPointerIndexedvEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetFloati_vEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetDoublei_vEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetPointeri_vEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglNamedProgramStringEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glNamedProgramLocalParameter4dEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLdouble") double paramDouble1, @NativeType("GLdouble") double paramDouble2, @NativeType("GLdouble") double paramDouble3, @NativeType("GLdouble") double paramDouble4);
  
  public static native void nglNamedProgramLocalParameter4dvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glNamedProgramLocalParameter4fEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4);
  
  public static native void nglNamedProgramLocalParameter4fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetNamedProgramLocalParameterdvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetNamedProgramLocalParameterfvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetNamedProgramivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetNamedProgramStringEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglCompressedTextureImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglCompressedTextureImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong);
  
  public static native void nglCompressedTextureImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglCompressedTextureSubImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, long paramLong);
  
  public static native void nglCompressedTextureSubImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglCompressedTextureSubImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglGetCompressedTextureImageEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglCompressedMultiTexImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglCompressedMultiTexImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, long paramLong);
  
  public static native void nglCompressedMultiTexImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglCompressedMultiTexSubImage3DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, long paramLong);
  
  public static native void nglCompressedMultiTexSubImage2DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, long paramLong);
  
  public static native void nglCompressedMultiTexSubImage1DEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglGetCompressedMultiTexImageEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMatrixLoadTransposefEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixLoadTransposedEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixMultTransposefEXT(int paramInt, long paramLong);
  
  public static native void nglMatrixMultTransposedEXT(int paramInt, long paramLong);
  
  public static native void nglNamedBufferDataEXT(int paramInt1, long paramLong1, long paramLong2, int paramInt2);
  
  public static native void nglNamedBufferSubDataEXT(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native long nglMapNamedBufferEXT(int paramInt1, int paramInt2);
  
  @NativeType("GLboolean")
  public static native boolean glUnmapNamedBufferEXT(@NativeType("GLuint") int paramInt);
  
  public static native void nglGetNamedBufferParameterivEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetNamedBufferSubDataEXT(int paramInt, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void glProgramUniform1fEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLfloat") float paramFloat);
  
  public static native void glProgramUniform2fEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2);
  
  public static native void glProgramUniform3fEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3);
  
  public static native void glProgramUniform4fEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4);
  
  public static native void glProgramUniform1iEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3);
  
  public static native void glProgramUniform2iEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void glProgramUniform3iEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5);
  
  public static native void glProgramUniform4iEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6);
  
  public static native void nglProgramUniform1fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform2fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform3fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform4fvEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform1ivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform2ivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform3ivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform4ivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniformMatrix2fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix3fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix4fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix2x3fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix3x2fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix2x4fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix4x2fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix3x4fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void nglProgramUniformMatrix4x3fvEXT(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong);
  
  public static native void glTextureBufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4);
  
  public static native void glMultiTexBufferEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4);
  
  public static native void nglTextureParameterIivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglTextureParameterIuivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetTextureParameterIivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetTextureParameterIuivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMultiTexParameterIivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMultiTexParameterIuivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexParameterIivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetMultiTexParameterIuivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glProgramUniform1uiEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void glProgramUniform2uiEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4);
  
  public static native void glProgramUniform3uiEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLuint") int paramInt5);
  
  public static native void glProgramUniform4uiEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLuint") int paramInt5, @NativeType("GLuint") int paramInt6);
  
  public static native void nglProgramUniform1uivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform2uivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform3uivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglProgramUniform4uivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglNamedProgramLocalParameters4fvEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glNamedProgramLocalParameterI4iEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7);
  
  public static native void nglNamedProgramLocalParameterI4ivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglNamedProgramLocalParametersI4ivEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glNamedProgramLocalParameterI4uiEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLuint") int paramInt5, @NativeType("GLuint") int paramInt6, @NativeType("GLuint") int paramInt7);
  
  public static native void nglNamedProgramLocalParameterI4uivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglNamedProgramLocalParametersI4uivEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetNamedProgramLocalParameterIivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetNamedProgramLocalParameterIuivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glNamedRenderbufferStorageEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLsizei") int paramInt4);
  
  public static native void nglGetNamedRenderbufferParameterivEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glNamedRenderbufferStorageMultisampleEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLsizei") int paramInt5);
  
  public static native void glNamedRenderbufferStorageMultisampleCoverageEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLsizei") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLsizei") int paramInt6);
  
  @NativeType("GLenum")
  public static native int glCheckNamedFramebufferStatusEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glNamedFramebufferTexture1DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLint") int paramInt5);
  
  public static native void glNamedFramebufferTexture2DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLint") int paramInt5);
  
  public static native void glNamedFramebufferTexture3DEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6);
  
  public static native void glNamedFramebufferRenderbufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLuint") int paramInt4);
  
  public static native void nglGetNamedFramebufferAttachmentParameterivEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glGenerateTextureMipmapEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glGenerateMultiTexMipmapEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glFramebufferDrawBufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void nglFramebufferDrawBuffersEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glFramebufferReadBufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void nglGetFramebufferParameterivEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glNamedCopyBufferSubDataEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLintptr") long paramLong1, @NativeType("GLintptr") long paramLong2, @NativeType("GLsizeiptr") long paramLong3);
  
  public static native void glNamedFramebufferTextureEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4);
  
  public static native void glNamedFramebufferTextureLayerEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5);
  
  public static native void glNamedFramebufferTextureFaceEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLenum") int paramInt5);
  
  public static native void glTextureRenderbufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void glMultiTexRenderbufferEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void glVertexArrayVertexOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayColorOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayEdgeFlagOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLsizei") int paramInt3, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayIndexOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayNormalOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayTexCoordOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayMultiTexCoordOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLenum") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayFogCoordOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArraySecondaryColorOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayVertexAttribOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLenum") int paramInt5, @NativeType("GLboolean") boolean paramBoolean, @NativeType("GLsizei") int paramInt6, @NativeType("GLintptr") long paramLong);
  
  public static native void glVertexArrayVertexAttribIOffsetEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLenum") int paramInt5, @NativeType("GLsizei") int paramInt6, @NativeType("GLintptr") long paramLong);
  
  public static native void glEnableVertexArrayEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glDisableVertexArrayEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2);
  
  public static native void glEnableVertexArrayAttribEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glDisableVertexArrayAttribEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void nglGetVertexArrayIntegervEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetVertexArrayPointervEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetVertexArrayIntegeri_vEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetVertexArrayPointeri_vEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native long nglMapNamedBufferRangeEXT(int paramInt1, long paramLong1, long paramLong2, int paramInt2);
  
  public static native void glFlushMappedNamedBufferRangeEXT(@NativeType("GLuint") int paramInt, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2);
}
