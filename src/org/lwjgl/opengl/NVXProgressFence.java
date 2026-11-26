package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVXProgressFence {
  static {
    GL.initialize();
  }
  
  protected NVXProgressFence() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glCreateProgressFenceNVX, caps.glSignalSemaphoreui64NVX, caps.glWaitSemaphoreui64NVX, caps.glClientWaitSemaphoreui64NVX);
  }
  
  public static void glSignalSemaphoreui64NVX(@NativeType("GLuint") int signalGpu, @NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray) {
    if (Checks.CHECKS)
      Checks.check((Buffer)fenceValueArray, semaphoreArray.remaining()); 
    nglSignalSemaphoreui64NVX(signalGpu, semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
  }
  
  public static void glWaitSemaphoreui64NVX(@NativeType("GLuint") int waitGpu, @NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray) {
    if (Checks.CHECKS)
      Checks.check((Buffer)fenceValueArray, semaphoreArray.remaining()); 
    nglWaitSemaphoreui64NVX(waitGpu, semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
  }
  
  public static void glClientWaitSemaphoreui64NVX(@NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray) {
    if (Checks.CHECKS)
      Checks.check((Buffer)fenceValueArray, semaphoreArray.remaining()); 
    nglClientWaitSemaphoreui64NVX(semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
  }
  
  public static void glSignalSemaphoreui64NVX(@NativeType("GLuint") int signalGpu, @NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray) {
    long __functionAddress = (GL.getICD()).glSignalSemaphoreui64NVX;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(fenceValueArray, semaphoreArray.length);
    } 
    JNI.callPPV(signalGpu, semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
  }
  
  public static void glWaitSemaphoreui64NVX(@NativeType("GLuint") int waitGpu, @NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray) {
    long __functionAddress = (GL.getICD()).glWaitSemaphoreui64NVX;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(fenceValueArray, semaphoreArray.length);
    } 
    JNI.callPPV(waitGpu, semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
  }
  
  public static void glClientWaitSemaphoreui64NVX(@NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray) {
    long __functionAddress = (GL.getICD()).glClientWaitSemaphoreui64NVX;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(fenceValueArray, semaphoreArray.length);
    } 
    JNI.callPPV(semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
  }
  
  @NativeType("GLuint")
  public static native int glCreateProgressFenceNVX();
  
  public static native void nglSignalSemaphoreui64NVX(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void nglWaitSemaphoreui64NVX(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void nglClientWaitSemaphoreui64NVX(int paramInt, long paramLong1, long paramLong2);
}
