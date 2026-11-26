package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTTransformFeedback {
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_EXT = 35982;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_EXT = 35972;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_EXT = 35973;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_EXT = 35983;
  
  public static final int GL_INTERLEAVED_ATTRIBS_EXT = 35980;
  
  public static final int GL_SEPARATE_ATTRIBS_EXT = 35981;
  
  public static final int GL_PRIMITIVES_GENERATED_EXT = 35975;
  
  public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_EXT = 35976;
  
  public static final int GL_RASTERIZER_DISCARD_EXT = 35977;
  
  public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_EXT = 35978;
  
  public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_EXT = 35979;
  
  public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_EXT = 35968;
  
  public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_EXT = 35971;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_EXT = 35967;
  
  public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH_EXT = 35958;
  
  static {
    GL.initialize();
  }
  
  protected EXTTransformFeedback() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBindBufferRangeEXT, caps.glBindBufferOffsetEXT, caps.glBindBufferBaseEXT, caps.glBeginTransformFeedbackEXT, caps.glEndTransformFeedbackEXT, caps.glTransformFeedbackVaryingsEXT, caps.glGetTransformFeedbackVaryingEXT, caps.glGetIntegerIndexedvEXT, caps.glGetBooleanIndexedvEXT });
  }
  
  public static void glTransformFeedbackVaryingsEXT(@NativeType("GLuint") int program, @NativeType("GLchar const * const *") PointerBuffer varyings, @NativeType("GLenum") int bufferMode) {
    nglTransformFeedbackVaryingsEXT(program, varyings.remaining(), MemoryUtil.memAddress(varyings), bufferMode);
  }
  
  public static void glTransformFeedbackVaryingsEXT(@NativeType("GLuint") int program, @NativeType("GLchar const * const *") CharSequence[] varyings, @NativeType("GLenum") int bufferMode) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      long varyingsAddress = APIUtil.apiArray(stack, MemoryUtil::memASCII, varyings);
      nglTransformFeedbackVaryingsEXT(program, varyings.length, varyingsAddress, bufferMode);
      APIUtil.apiArrayFree(varyingsAddress, varyings.length);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glTransformFeedbackVaryingsEXT(@NativeType("GLuint") int program, @NativeType("GLchar const * const *") CharSequence varying, @NativeType("GLenum") int bufferMode) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      long varyingsAddress = APIUtil.apiArray(stack, MemoryUtil::memASCII, new CharSequence[] { varying });
      nglTransformFeedbackVaryingsEXT(program, 1, varyingsAddress, bufferMode);
      APIUtil.apiArrayFree(varyingsAddress, 1);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetTransformFeedbackVaryingEXT(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @Nullable @NativeType("GLsizei *") IntBuffer length, @NativeType("GLsizei *") IntBuffer size, @NativeType("GLenum *") IntBuffer type, @NativeType("GLchar *") ByteBuffer name) {
    if (Checks.CHECKS) {
      Checks.checkSafe((Buffer)length, 1);
      Checks.check((Buffer)size, 1);
      Checks.check((Buffer)type, 1);
    } 
    nglGetTransformFeedbackVaryingEXT(program, index, name.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name));
  }
  
  @NativeType("void")
  public static String glGetTransformFeedbackVaryingEXT(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLsizei") int bufSize, @NativeType("GLsizei *") IntBuffer size, @NativeType("GLenum *") IntBuffer type) {
    if (Checks.CHECKS) {
      Checks.check((Buffer)size, 1);
      Checks.check((Buffer)type, 1);
    } 
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer length = stack.ints(0);
      ByteBuffer name = stack.malloc(bufSize);
      nglGetTransformFeedbackVaryingEXT(program, index, bufSize, MemoryUtil.memAddress(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name));
      return MemoryUtil.memASCII(name, length.get(0));
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  @NativeType("void")
  public static String glGetTransformFeedbackVaryingEXT(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLsizei *") IntBuffer size, @NativeType("GLenum *") IntBuffer type) {
    return glGetTransformFeedbackVaryingEXT(program, index, (GL.getCapabilities()).OpenGL20 ? 
        GL20.glGetProgrami(program, 35958) : 
        ARBShaderObjects.glGetObjectParameteriARB(program, 35958), size, type);
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
  
  public static void glGetTransformFeedbackVaryingEXT(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLsizei *") int[] size, @NativeType("GLenum *") int[] type, @NativeType("GLchar *") ByteBuffer name) {
    long __functionAddress = (GL.getICD()).glGetTransformFeedbackVaryingEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.checkSafe(length, 1);
      Checks.check(size, 1);
      Checks.check(type, 1);
    } 
    JNI.callPPPPV(program, index, name.remaining(), length, size, type, MemoryUtil.memAddress(name), __functionAddress);
  }
  
  public static void glGetIntegerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] data) {
    EXTDrawBuffers2.glGetIntegerIndexedvEXT(target, index, data);
  }
  
  public static native void glBindBufferRangeEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLintptr") long paramLong1, @NativeType("GLsizeiptr") long paramLong2);
  
  public static native void glBindBufferOffsetEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLintptr") long paramLong);
  
  public static native void glBindBufferBaseEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3);
  
  public static native void glBeginTransformFeedbackEXT(@NativeType("GLenum") int paramInt);
  
  public static native void glEndTransformFeedbackEXT();
  
  public static native void nglTransformFeedbackVaryingsEXT(int paramInt1, int paramInt2, long paramLong, int paramInt3);
  
  public static native void nglGetTransformFeedbackVaryingEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4);
}
