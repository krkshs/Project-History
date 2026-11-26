package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDDebugOutput {
  public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_AMD = 37187;
  
  public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_AMD = 37188;
  
  public static final int GL_DEBUG_LOGGED_MESSAGES_AMD = 37189;
  
  public static final int GL_DEBUG_SEVERITY_HIGH_AMD = 37190;
  
  public static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 37191;
  
  public static final int GL_DEBUG_SEVERITY_LOW_AMD = 37192;
  
  public static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 37193;
  
  public static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 37194;
  
  public static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 37195;
  
  public static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 37196;
  
  public static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 37197;
  
  public static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 37198;
  
  public static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 37199;
  
  public static final int GL_DEBUG_CATEGORY_OTHER_AMD = 37200;
  
  static {
    GL.initialize();
  }
  
  protected AMDDebugOutput() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDebugMessageEnableAMD, caps.glDebugMessageInsertAMD, caps.glDebugMessageCallbackAMD, caps.glGetDebugMessageLogAMD });
  }
  
  public static void glDebugMessageEnableAMD(@NativeType("GLenum") int category, @NativeType("GLenum") int severity, @Nullable @NativeType("GLuint const *") IntBuffer ids, @NativeType("GLboolean") boolean enabled) {
    nglDebugMessageEnableAMD(category, severity, Checks.remainingSafe(ids), MemoryUtil.memAddressSafe(ids), enabled);
  }
  
  public static void glDebugMessageEnableAMD(@NativeType("GLenum") int category, @NativeType("GLenum") int severity, @NativeType("GLuint const *") int id, @NativeType("GLboolean") boolean enabled) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer ids = stack.ints(id);
      nglDebugMessageEnableAMD(category, severity, 1, MemoryUtil.memAddress(ids), enabled);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glDebugMessageInsertAMD(@NativeType("GLenum") int category, @NativeType("GLenum") int severity, @NativeType("GLuint") int id, @NativeType("GLchar const *") ByteBuffer buf) {
    nglDebugMessageInsertAMD(category, severity, id, buf.remaining(), MemoryUtil.memAddress(buf));
  }
  
  public static void glDebugMessageInsertAMD(@NativeType("GLenum") int category, @NativeType("GLenum") int severity, @NativeType("GLuint") int id, @NativeType("GLchar const *") CharSequence buf) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      int bufEncodedLength = stack.nUTF8(buf, false);
      long bufEncoded = stack.getPointerAddress();
      nglDebugMessageInsertAMD(category, severity, id, bufEncodedLength, bufEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glDebugMessageCallbackAMD(@Nullable @NativeType("GLDEBUGPROCAMD") GLDebugMessageAMDCallbackI callback, @NativeType("void *") long userParam) {
    nglDebugMessageCallbackAMD(MemoryUtil.memAddressSafe(callback), userParam);
  }
  
  @NativeType("GLuint")
  public static int glGetDebugMessageLogAMD(@NativeType("GLuint") int count, @Nullable @NativeType("GLenum *") IntBuffer categories, @Nullable @NativeType("GLuint *") IntBuffer severities, @Nullable @NativeType("GLuint *") IntBuffer ids, @Nullable @NativeType("GLsizei *") IntBuffer lengths, @Nullable @NativeType("GLchar *") ByteBuffer messageLog) {
    if (Checks.CHECKS) {
      Checks.checkSafe((Buffer)categories, count);
      Checks.checkSafe((Buffer)severities, count);
      Checks.checkSafe((Buffer)ids, count);
      Checks.checkSafe((Buffer)lengths, count);
    } 
    return nglGetDebugMessageLogAMD(count, Checks.remainingSafe(messageLog), MemoryUtil.memAddressSafe(categories), MemoryUtil.memAddressSafe(severities), MemoryUtil.memAddressSafe(ids), MemoryUtil.memAddressSafe(lengths), MemoryUtil.memAddressSafe(messageLog));
  }
  
  public static void glDebugMessageEnableAMD(@NativeType("GLenum") int category, @NativeType("GLenum") int severity, @Nullable @NativeType("GLuint const *") int[] ids, @NativeType("GLboolean") boolean enabled) {
    long __functionAddress = (GL.getICD()).glDebugMessageEnableAMD;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(category, severity, Checks.lengthSafe(ids), ids, enabled, __functionAddress);
  }
  
  @NativeType("GLuint")
  public static int glGetDebugMessageLogAMD(@NativeType("GLuint") int count, @Nullable @NativeType("GLenum *") int[] categories, @Nullable @NativeType("GLuint *") int[] severities, @Nullable @NativeType("GLuint *") int[] ids, @Nullable @NativeType("GLsizei *") int[] lengths, @Nullable @NativeType("GLchar *") ByteBuffer messageLog) {
    long __functionAddress = (GL.getICD()).glGetDebugMessageLogAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.checkSafe(categories, count);
      Checks.checkSafe(severities, count);
      Checks.checkSafe(ids, count);
      Checks.checkSafe(lengths, count);
    } 
    return JNI.callPPPPPI(count, Checks.remainingSafe(messageLog), categories, severities, ids, lengths, MemoryUtil.memAddressSafe(messageLog), __functionAddress);
  }
  
  public static native void nglDebugMessageEnableAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong, boolean paramBoolean);
  
  public static native void nglDebugMessageInsertAMD(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglDebugMessageCallbackAMD(long paramLong1, long paramLong2);
  
  public static native int nglGetDebugMessageLogAMD(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
}
