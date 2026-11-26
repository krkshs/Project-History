package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDrawBuffers2 {
  static {
    GL.initialize();
  }
  
  protected EXTDrawBuffers2() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glColorMaskIndexedEXT, caps.glGetBooleanIndexedvEXT, caps.glGetIntegerIndexedvEXT, caps.glEnableIndexedEXT, caps.glDisableIndexedEXT, caps.glIsEnabledIndexedEXT });
  }
  
  public static void glGetBooleanIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLboolean *") ByteBuffer data) {
    if (Checks.CHECKS)
      Checks.check((Buffer)data, 1); 
    nglGetBooleanIndexedvEXT(target, index, MemoryUtil.memAddress(data));
  }
  
  @NativeType("void")
  public static boolean glGetBooleanIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      ByteBuffer data = stack.calloc(1);
      nglGetBooleanIndexedvEXT(target, index, MemoryUtil.memAddress(data));
      return (data.get(0) != 0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetIntegerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer data) {
    if (Checks.CHECKS)
      Checks.check((Buffer)data, 1); 
    nglGetIntegerIndexedvEXT(target, index, MemoryUtil.memAddress(data));
  }
  
  @NativeType("void")
  public static int glGetIntegerIndexedEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer data = stack.callocInt(1);
      nglGetIntegerIndexedvEXT(target, index, MemoryUtil.memAddress(data));
      return data.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetIntegerIndexedvEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] data) {
    long __functionAddress = (GL.getICD()).glGetIntegerIndexedvEXT;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(data, 1);
    } 
    JNI.callPV(target, index, data, __functionAddress);
  }
  
  public static native void glColorMaskIndexedEXT(@NativeType("GLuint") int paramInt, @NativeType("GLboolean") boolean paramBoolean1, @NativeType("GLboolean") boolean paramBoolean2, @NativeType("GLboolean") boolean paramBoolean3, @NativeType("GLboolean") boolean paramBoolean4);
  
  public static native void nglGetBooleanIndexedvEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetIntegerIndexedvEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static native void glEnableIndexedEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glDisableIndexedEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  @NativeType("GLboolean")
  public static native boolean glIsEnabledIndexedEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
}
