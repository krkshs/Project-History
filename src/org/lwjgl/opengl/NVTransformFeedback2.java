package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVTransformFeedback2 {
  public static final int GL_TRANSFORM_FEEDBACK_NV = 36386;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED_NV = 36387;
  
  public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE_NV = 36388;
  
  public static final int GL_TRANSFORM_FEEDBACK_BINDING_NV = 36389;
  
  static {
    GL.initialize();
  }
  
  protected NVTransformFeedback2() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glBindTransformFeedbackNV, caps.glDeleteTransformFeedbacksNV, caps.glGenTransformFeedbacksNV, caps.glIsTransformFeedbackNV, caps.glPauseTransformFeedbackNV, caps.glResumeTransformFeedbackNV, caps.glDrawTransformFeedbackNV });
  }
  
  public static void glDeleteTransformFeedbacksNV(@NativeType("GLuint const *") IntBuffer ids) {
    nglDeleteTransformFeedbacksNV(ids.remaining(), MemoryUtil.memAddress(ids));
  }
  
  public static void glDeleteTransformFeedbacksNV(@NativeType("GLuint const *") int id) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer ids = stack.ints(id);
      nglDeleteTransformFeedbacksNV(1, MemoryUtil.memAddress(ids));
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGenTransformFeedbacksNV(@NativeType("GLuint *") IntBuffer ids) {
    if (Checks.CHECKS)
      Checks.check((Buffer)ids, 1); 
    nglGenTransformFeedbacksNV(ids.remaining(), MemoryUtil.memAddress(ids));
  }
  
  @NativeType("void")
  public static int glGenTransformFeedbacksNV() {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer ids = stack.callocInt(1);
      nglGenTransformFeedbacksNV(1, MemoryUtil.memAddress(ids));
      return ids.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glDeleteTransformFeedbacksNV(@NativeType("GLuint const *") int[] ids) {
    long __functionAddress = (GL.getICD()).glDeleteTransformFeedbacksNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(ids.length, ids, __functionAddress);
  }
  
  public static void glGenTransformFeedbacksNV(@NativeType("GLuint *") int[] ids) {
    long __functionAddress = (GL.getICD()).glGenTransformFeedbacksNV;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(ids, 1);
    } 
    JNI.callPV(ids.length, ids, __functionAddress);
  }
  
  public static native void glBindTransformFeedbackNV(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void nglDeleteTransformFeedbacksNV(int paramInt, long paramLong);
  
  public static native void nglGenTransformFeedbacksNV(int paramInt, long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsTransformFeedbackNV(@NativeType("GLuint") int paramInt);
  
  public static native void glPauseTransformFeedbackNV();
  
  public static native void glResumeTransformFeedbackNV();
  
  public static native void glDrawTransformFeedbackNV(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
}
