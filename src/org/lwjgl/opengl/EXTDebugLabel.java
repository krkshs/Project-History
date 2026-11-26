package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDebugLabel {
  public static final int GL_BUFFER_OBJECT_EXT = 37201;
  
  public static final int GL_SHADER_OBJECT_EXT = 35656;
  
  public static final int GL_PROGRAM_OBJECT_EXT = 35648;
  
  public static final int GL_VERTEX_ARRAY_OBJECT_EXT = 37204;
  
  public static final int GL_QUERY_OBJECT_EXT = 37203;
  
  public static final int GL_PROGRAM_PIPELINE_OBJECT_EXT = 35407;
  
  static {
    GL.initialize();
  }
  
  protected EXTDebugLabel() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glLabelObjectEXT, caps.glGetObjectLabelEXT });
  }
  
  public static void glLabelObjectEXT(@NativeType("GLenum") int type, @NativeType("GLuint") int object, @NativeType("GLchar const *") ByteBuffer label) {
    nglLabelObjectEXT(type, object, label.remaining(), MemoryUtil.memAddress(label));
  }
  
  public static void glLabelObjectEXT(@NativeType("GLenum") int type, @NativeType("GLuint") int object, @NativeType("GLchar const *") CharSequence label) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      int labelEncodedLength = stack.nUTF8(label, false);
      long labelEncoded = stack.getPointerAddress();
      nglLabelObjectEXT(type, object, labelEncodedLength, labelEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetObjectLabelEXT(@NativeType("GLenum") int type, @NativeType("GLuint") int object, @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer label) {
    if (Checks.CHECKS)
      Checks.check((Buffer)length, 1); 
    nglGetObjectLabelEXT(type, object, label.remaining(), MemoryUtil.memAddress(length), MemoryUtil.memAddress(label));
  }
  
  @NativeType("void")
  public static String glGetObjectLabelEXT(@NativeType("GLenum") int type, @NativeType("GLuint") int object, @NativeType("GLsizei") int bufSize) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer length = stack.ints(0);
      ByteBuffer label = stack.malloc(bufSize);
      nglGetObjectLabelEXT(type, object, bufSize, MemoryUtil.memAddress(length), MemoryUtil.memAddress(label));
      return MemoryUtil.memUTF8(label, length.get(0));
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetObjectLabelEXT(@NativeType("GLenum") int type, @NativeType("GLuint") int object, @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer label) {
    long __functionAddress = (GL.getICD()).glGetObjectLabelEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(length, 1);
    } 
    JNI.callPPV(type, object, label.remaining(), length, MemoryUtil.memAddress(label), __functionAddress);
  }
  
  public static native void nglLabelObjectEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetObjectLabelEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
}
