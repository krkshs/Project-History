package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDPerformanceMonitor {
  public static final int GL_COUNTER_TYPE_AMD = 35776;
  
  public static final int GL_COUNTER_RANGE_AMD = 35777;
  
  public static final int GL_UNSIGNED_INT64_AMD = 35778;
  
  public static final int GL_PERCENTAGE_AMD = 35779;
  
  public static final int GL_PERFMON_RESULT_AVAILABLE_AMD = 35780;
  
  public static final int GL_PERFMON_RESULT_SIZE_AMD = 35781;
  
  public static final int GL_PERFMON_RESULT_AMD = 35782;
  
  static {
    GL.initialize();
  }
  
  protected AMDPerformanceMonitor() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { 
          caps.glGetPerfMonitorGroupsAMD, caps.glGetPerfMonitorCountersAMD, caps.glGetPerfMonitorGroupStringAMD, caps.glGetPerfMonitorCounterStringAMD, caps.glGetPerfMonitorCounterInfoAMD, caps.glGenPerfMonitorsAMD, caps.glDeletePerfMonitorsAMD, caps.glSelectPerfMonitorCountersAMD, caps.glBeginPerfMonitorAMD, caps.glEndPerfMonitorAMD, 
          caps.glGetPerfMonitorCounterDataAMD });
  }
  
  public static void glGetPerfMonitorGroupsAMD(@Nullable @NativeType("GLint *") IntBuffer numGroups, @Nullable @NativeType("GLuint *") IntBuffer groups) {
    if (Checks.CHECKS)
      Checks.checkSafe((Buffer)numGroups, 1); 
    nglGetPerfMonitorGroupsAMD(MemoryUtil.memAddressSafe(numGroups), Checks.remainingSafe(groups), MemoryUtil.memAddressSafe(groups));
  }
  
  public static void glGetPerfMonitorCountersAMD(@NativeType("GLuint") int group, @NativeType("GLint *") IntBuffer numCounters, @NativeType("GLint *") IntBuffer maxActiveCounters, @NativeType("GLuint *") IntBuffer counters) {
    if (Checks.CHECKS) {
      Checks.check((Buffer)numCounters, 1);
      Checks.check((Buffer)maxActiveCounters, 1);
    } 
    nglGetPerfMonitorCountersAMD(group, MemoryUtil.memAddress(numCounters), MemoryUtil.memAddress(maxActiveCounters), counters.remaining(), MemoryUtil.memAddress(counters));
  }
  
  public static void glGetPerfMonitorGroupStringAMD(@NativeType("GLuint") int group, @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer groupString) {
    if (Checks.CHECKS)
      Checks.check((Buffer)length, 1); 
    nglGetPerfMonitorGroupStringAMD(group, groupString.remaining(), MemoryUtil.memAddress(length), MemoryUtil.memAddress(groupString));
  }
  
  public static void glGetPerfMonitorCounterStringAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @Nullable @NativeType("GLsizei *") IntBuffer length, @Nullable @NativeType("GLchar *") ByteBuffer counterString) {
    if (Checks.CHECKS)
      Checks.checkSafe((Buffer)length, 1); 
    nglGetPerfMonitorCounterStringAMD(group, counter, Checks.remainingSafe(counterString), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddressSafe(counterString));
  }
  
  public static void glGetPerfMonitorCounterInfoAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @NativeType("GLenum") int pname, @NativeType("void *") ByteBuffer data) {
    if (Checks.CHECKS)
      Checks.check((Buffer)data, 4); 
    nglGetPerfMonitorCounterInfoAMD(group, counter, pname, MemoryUtil.memAddress(data));
  }
  
  public static void glGetPerfMonitorCounterInfoAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @NativeType("GLenum") int pname, @NativeType("void *") IntBuffer data) {
    if (Checks.CHECKS)
      Checks.check((Buffer)data, 1); 
    nglGetPerfMonitorCounterInfoAMD(group, counter, pname, MemoryUtil.memAddress(data));
  }
  
  public static void glGetPerfMonitorCounterInfoAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @NativeType("GLenum") int pname, @NativeType("void *") FloatBuffer data) {
    if (Checks.CHECKS)
      Checks.check((Buffer)data, 1); 
    nglGetPerfMonitorCounterInfoAMD(group, counter, pname, MemoryUtil.memAddress(data));
  }
  
  public static void glGenPerfMonitorsAMD(@NativeType("GLuint *") IntBuffer monitors) {
    nglGenPerfMonitorsAMD(monitors.remaining(), MemoryUtil.memAddress(monitors));
  }
  
  @NativeType("void")
  public static int glGenPerfMonitorsAMD() {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer monitors = stack.callocInt(1);
      nglGenPerfMonitorsAMD(1, MemoryUtil.memAddress(monitors));
      return monitors.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glDeletePerfMonitorsAMD(@NativeType("GLuint *") IntBuffer monitors) {
    nglDeletePerfMonitorsAMD(monitors.remaining(), MemoryUtil.memAddress(monitors));
  }
  
  public static void glDeletePerfMonitorsAMD(@NativeType("GLuint *") int monitor) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer monitors = stack.ints(monitor);
      nglDeletePerfMonitorsAMD(1, MemoryUtil.memAddress(monitors));
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glSelectPerfMonitorCountersAMD(@NativeType("GLuint") int monitor, @NativeType("GLboolean") boolean enable, @NativeType("GLuint") int group, @NativeType("GLuint *") IntBuffer counterList) {
    nglSelectPerfMonitorCountersAMD(monitor, enable, group, counterList.remaining(), MemoryUtil.memAddress(counterList));
  }
  
  public static void glGetPerfMonitorCounterDataAMD(@NativeType("GLuint") int monitor, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer data, @Nullable @NativeType("GLint *") IntBuffer bytesWritten) {
    if (Checks.CHECKS)
      Checks.checkSafe((Buffer)bytesWritten, 1); 
    nglGetPerfMonitorCounterDataAMD(monitor, pname, data.remaining(), MemoryUtil.memAddress(data), MemoryUtil.memAddressSafe(bytesWritten));
  }
  
  public static void glGetPerfMonitorGroupsAMD(@Nullable @NativeType("GLint *") int[] numGroups, @Nullable @NativeType("GLuint *") int[] groups) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorGroupsAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.checkSafe(numGroups, 1);
    } 
    JNI.callPPV(numGroups, Checks.lengthSafe(groups), groups, __functionAddress);
  }
  
  public static void glGetPerfMonitorCountersAMD(@NativeType("GLuint") int group, @NativeType("GLint *") int[] numCounters, @NativeType("GLint *") int[] maxActiveCounters, @NativeType("GLuint *") int[] counters) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorCountersAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(numCounters, 1);
      Checks.check(maxActiveCounters, 1);
    } 
    JNI.callPPPV(group, numCounters, maxActiveCounters, counters.length, counters, __functionAddress);
  }
  
  public static void glGetPerfMonitorGroupStringAMD(@NativeType("GLuint") int group, @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer groupString) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorGroupStringAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(length, 1);
    } 
    JNI.callPPV(group, groupString.remaining(), length, MemoryUtil.memAddress(groupString), __functionAddress);
  }
  
  public static void glGetPerfMonitorCounterStringAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @Nullable @NativeType("GLsizei *") int[] length, @Nullable @NativeType("GLchar *") ByteBuffer counterString) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorCounterStringAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.checkSafe(length, 1);
    } 
    JNI.callPPV(group, counter, Checks.remainingSafe(counterString), length, MemoryUtil.memAddressSafe(counterString), __functionAddress);
  }
  
  public static void glGetPerfMonitorCounterInfoAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @NativeType("GLenum") int pname, @NativeType("void *") int[] data) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorCounterInfoAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(data, 1);
    } 
    JNI.callPV(group, counter, pname, data, __functionAddress);
  }
  
  public static void glGetPerfMonitorCounterInfoAMD(@NativeType("GLuint") int group, @NativeType("GLuint") int counter, @NativeType("GLenum") int pname, @NativeType("void *") float[] data) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorCounterInfoAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(data, 1);
    } 
    JNI.callPV(group, counter, pname, data, __functionAddress);
  }
  
  public static void glGenPerfMonitorsAMD(@NativeType("GLuint *") int[] monitors) {
    long __functionAddress = (GL.getICD()).glGenPerfMonitorsAMD;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(monitors.length, monitors, __functionAddress);
  }
  
  public static void glDeletePerfMonitorsAMD(@NativeType("GLuint *") int[] monitors) {
    long __functionAddress = (GL.getICD()).glDeletePerfMonitorsAMD;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(monitors.length, monitors, __functionAddress);
  }
  
  public static void glSelectPerfMonitorCountersAMD(@NativeType("GLuint") int monitor, @NativeType("GLboolean") boolean enable, @NativeType("GLuint") int group, @NativeType("GLuint *") int[] counterList) {
    long __functionAddress = (GL.getICD()).glSelectPerfMonitorCountersAMD;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(monitor, enable, group, counterList.length, counterList, __functionAddress);
  }
  
  public static void glGetPerfMonitorCounterDataAMD(@NativeType("GLuint") int monitor, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] data, @Nullable @NativeType("GLint *") int[] bytesWritten) {
    long __functionAddress = (GL.getICD()).glGetPerfMonitorCounterDataAMD;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.checkSafe(bytesWritten, 1);
    } 
    JNI.callPPV(monitor, pname, data.length, data, bytesWritten, __functionAddress);
  }
  
  public static native void nglGetPerfMonitorGroupsAMD(long paramLong1, int paramInt, long paramLong2);
  
  public static native void nglGetPerfMonitorCountersAMD(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
  
  public static native void nglGetPerfMonitorGroupStringAMD(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static native void nglGetPerfMonitorCounterStringAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
  
  public static native void nglGetPerfMonitorCounterInfoAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGenPerfMonitorsAMD(int paramInt, long paramLong);
  
  public static native void nglDeletePerfMonitorsAMD(int paramInt, long paramLong);
  
  public static native void nglSelectPerfMonitorCountersAMD(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, long paramLong);
  
  public static native void glBeginPerfMonitorAMD(@NativeType("GLuint") int paramInt);
  
  public static native void glEndPerfMonitorAMD(@NativeType("GLuint") int paramInt);
  
  public static native void nglGetPerfMonitorCounterDataAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);
}
