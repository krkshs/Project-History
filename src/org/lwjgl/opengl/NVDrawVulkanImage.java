package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVDrawVulkanImage {
  static {
    GL.initialize();
  }
  
  protected NVDrawVulkanImage() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDrawVkImageNV, caps.glGetVkProcAddrNV, caps.glWaitVkSemaphoreNV, caps.glSignalVkSemaphoreNV, caps.glSignalVkFenceNV });
  }
  
  @NativeType("VULKANPROCNV")
  public static long glGetVkProcAddrNV(@NativeType("GLchar const *") ByteBuffer name) {
    if (Checks.CHECKS)
      Checks.checkNT1(name); 
    return nglGetVkProcAddrNV(MemoryUtil.memAddress(name));
  }
  
  @NativeType("VULKANPROCNV")
  public static long glGetVkProcAddrNV(@NativeType("GLchar const *") CharSequence name) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nASCII(name, true);
      long nameEncoded = stack.getPointerAddress();
      return nglGetVkProcAddrNV(nameEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static native void glDrawVkImageNV(@NativeType("GLuint64") long paramLong, @NativeType("GLuint") int paramInt, @NativeType("GLfloat") float paramFloat1, @NativeType("GLfloat") float paramFloat2, @NativeType("GLfloat") float paramFloat3, @NativeType("GLfloat") float paramFloat4, @NativeType("GLfloat") float paramFloat5, @NativeType("GLfloat") float paramFloat6, @NativeType("GLfloat") float paramFloat7, @NativeType("GLfloat") float paramFloat8, @NativeType("GLfloat") float paramFloat9);
  
  public static native long nglGetVkProcAddrNV(long paramLong);
  
  public static native void glWaitVkSemaphoreNV(@NativeType("GLuint64") long paramLong);
  
  public static native void glSignalVkSemaphoreNV(@NativeType("GLuint64") long paramLong);
  
  public static native void glSignalVkFenceNV(@NativeType("GLuint64") long paramLong);
}
