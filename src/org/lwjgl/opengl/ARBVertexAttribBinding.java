package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBVertexAttribBinding {
  public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
  
  public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
  
  public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
  
  public static final int GL_VERTEX_BINDING_OFFSET = 33495;
  
  public static final int GL_VERTEX_BINDING_STRIDE = 33496;
  
  public static final int GL_VERTEX_BINDING_BUFFER = 36687;
  
  public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
  
  public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;
  
  static {
    GL.initialize();
  }
  
  protected ARBVertexAttribBinding() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { 
          caps.glBindVertexBuffer, caps.glVertexAttribFormat, caps.glVertexAttribIFormat, caps.glVertexAttribLFormat, caps.glVertexAttribBinding, caps.glVertexBindingDivisor, 
          
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayBindVertexBufferEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayVertexAttribFormatEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayVertexAttribIFormatEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayVertexAttribLFormatEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayVertexAttribBindingEXT : -1L, 
          ext.contains("GL_EXT_direct_state_access") ? caps.glVertexArrayVertexBindingDivisorEXT : -1L });
  }
  
  public static void glBindVertexBuffer(@NativeType("GLuint") int bindingindex, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizei") int stride) {
    GL43C.glBindVertexBuffer(bindingindex, buffer, offset, stride);
  }
  
  public static void glVertexAttribFormat(@NativeType("GLuint") int attribindex, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLboolean") boolean normalized, @NativeType("GLuint") int relativeoffset) {
    GL43C.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
  }
  
  public static void glVertexAttribIFormat(@NativeType("GLuint") int attribindex, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLuint") int relativeoffset) {
    GL43C.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
  }
  
  public static void glVertexAttribLFormat(@NativeType("GLuint") int attribindex, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLuint") int relativeoffset) {
    GL43C.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
  }
  
  public static void glVertexAttribBinding(@NativeType("GLuint") int attribindex, @NativeType("GLuint") int bindingindex) {
    GL43C.glVertexAttribBinding(attribindex, bindingindex);
  }
  
  public static void glVertexBindingDivisor(@NativeType("GLuint") int bindingindex, @NativeType("GLuint") int divisor) {
    GL43C.glVertexBindingDivisor(bindingindex, divisor);
  }
  
  public static native void glVertexArrayBindVertexBufferEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLintptr") long paramLong, @NativeType("GLsizei") int paramInt4);
  
  public static native void glVertexArrayVertexAttribFormatEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLboolean") boolean paramBoolean, @NativeType("GLuint") int paramInt5);
  
  public static native void glVertexArrayVertexAttribIFormatEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLuint") int paramInt5);
  
  public static native void glVertexArrayVertexAttribLFormatEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLuint") int paramInt5);
  
  public static native void glVertexArrayVertexAttribBindingEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void glVertexArrayVertexBindingDivisorEXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3);
}
