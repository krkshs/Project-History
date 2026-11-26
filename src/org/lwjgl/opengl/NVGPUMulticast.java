package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVGPUMulticast {
  public static final int GL_PER_GPU_STORAGE_BIT_NV = 2048;
  
  public static final int GL_MULTICAST_GPUS_NV = 37562;
  
  public static final int GL_RENDER_GPU_MASK_NV = 38232;
  
  public static final int GL_PER_GPU_STORAGE_NV = 38216;
  
  public static final int GL_MULTICAST_PROGRAMMABLE_SAMPLE_LOCATION_NV = 38217;
  
  static {
    GL.initialize();
  }
  
  protected NVGPUMulticast() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { 
          caps.glRenderGpuMaskNV, caps.glMulticastBufferSubDataNV, caps.glMulticastCopyBufferSubDataNV, caps.glMulticastCopyImageSubDataNV, caps.glMulticastBlitFramebufferNV, caps.glMulticastFramebufferSampleLocationsfvNV, caps.glMulticastBarrierNV, caps.glMulticastWaitSyncNV, caps.glMulticastGetQueryObjectivNV, caps.glMulticastGetQueryObjectuivNV, 
          caps.glMulticastGetQueryObjecti64vNV, caps.glMulticastGetQueryObjectui64vNV });
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data) {
    nglMulticastBufferSubDataNV(gpuMask, buffer, offset, (long)data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ShortBuffer data) {
    nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1L, MemoryUtil.memAddress(data));
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data) {
    nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") FloatBuffer data) {
    nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2L, MemoryUtil.memAddress(data));
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") DoubleBuffer data) {
    nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3L, MemoryUtil.memAddress(data));
  }
  
  public static void glMulticastFramebufferSampleLocationsfvNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int framebuffer, @NativeType("GLuint") int start, @NativeType("GLfloat const *") FloatBuffer v) {
    nglMulticastFramebufferSampleLocationsfvNV(gpu, framebuffer, start, v.remaining() >> 1, MemoryUtil.memAddress(v));
  }
  
  public static void glMulticastGetQueryObjectivNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglMulticastGetQueryObjectivNV(gpu, id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glMulticastGetQueryObjectiNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglMulticastGetQueryObjectivNV(gpu, id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMulticastGetQueryObjectuivNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglMulticastGetQueryObjectuivNV(gpu, id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glMulticastGetQueryObjectuiNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglMulticastGetQueryObjectuivNV(gpu, id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMulticastGetQueryObjecti64vNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglMulticastGetQueryObjecti64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static long glMulticastGetQueryObjecti64NV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      LongBuffer params = stack.callocLong(1);
      nglMulticastGetQueryObjecti64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMulticastGetQueryObjectui64vNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglMulticastGetQueryObjectui64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static long glMulticastGetQueryObjectui64NV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      LongBuffer params = stack.callocLong(1);
      nglMulticastGetQueryObjectui64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data) {
    long __functionAddress = (GL.getICD()).glMulticastBufferSubDataNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 1L, data, __functionAddress);
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data) {
    long __functionAddress = (GL.getICD()).glMulticastBufferSubDataNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data) {
    long __functionAddress = (GL.getICD()).glMulticastBufferSubDataNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 2L, data, __functionAddress);
  }
  
  public static void glMulticastBufferSubDataNV(@NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data) {
    long __functionAddress = (GL.getICD()).glMulticastBufferSubDataNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 3L, data, __functionAddress);
  }
  
  public static void glMulticastFramebufferSampleLocationsfvNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int framebuffer, @NativeType("GLuint") int start, @NativeType("GLfloat const *") float[] v) {
    long __functionAddress = (GL.getICD()).glMulticastFramebufferSampleLocationsfvNV;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(gpu, framebuffer, start, v.length >> 1, v, __functionAddress);
  }
  
  public static void glMulticastGetQueryObjectivNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glMulticastGetQueryObjectivNV;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(gpu, id, pname, params, __functionAddress);
  }
  
  public static void glMulticastGetQueryObjectuivNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glMulticastGetQueryObjectuivNV;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(gpu, id, pname, params, __functionAddress);
  }
  
  public static void glMulticastGetQueryObjecti64vNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
    long __functionAddress = (GL.getICD()).glMulticastGetQueryObjecti64vNV;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(gpu, id, pname, params, __functionAddress);
  }
  
  public static void glMulticastGetQueryObjectui64vNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params) {
    long __functionAddress = (GL.getICD()).glMulticastGetQueryObjectui64vNV;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(gpu, id, pname, params, __functionAddress);
  }
  
  public static native void glRenderGpuMaskNV(@NativeType("GLbitfield") int paramInt);
  
  public static native void nglMulticastBufferSubDataNV(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);
  
  public static native void glMulticastCopyBufferSubDataNV(@NativeType("GLuint") int paramInt1, @NativeType("GLbitfield") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLintptr") long paramLong1, @NativeType("GLintptr") long paramLong2, @NativeType("GLsizeiptr") long paramLong3);
  
  public static native void glMulticastCopyImageSubDataNV(@NativeType("GLuint") int paramInt1, @NativeType("GLbitfield") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLint") int paramInt8, @NativeType("GLuint") int paramInt9, @NativeType("GLenum") int paramInt10, @NativeType("GLint") int paramInt11, @NativeType("GLint") int paramInt12, @NativeType("GLint") int paramInt13, @NativeType("GLint") int paramInt14, @NativeType("GLsizei") int paramInt15, @NativeType("GLsizei") int paramInt16, @NativeType("GLsizei") int paramInt17);
  
  public static native void glMulticastBlitFramebufferNV(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLint") int paramInt3, @NativeType("GLint") int paramInt4, @NativeType("GLint") int paramInt5, @NativeType("GLint") int paramInt6, @NativeType("GLint") int paramInt7, @NativeType("GLint") int paramInt8, @NativeType("GLint") int paramInt9, @NativeType("GLint") int paramInt10, @NativeType("GLbitfield") int paramInt11, @NativeType("GLenum") int paramInt12);
  
  public static native void nglMulticastFramebufferSampleLocationsfvNV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void glMulticastBarrierNV();
  
  public static native void glMulticastWaitSyncNV(@NativeType("GLuint") int paramInt1, @NativeType("GLbitfield") int paramInt2);
  
  public static native void nglMulticastGetQueryObjectivNV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMulticastGetQueryObjectuivNV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMulticastGetQueryObjecti64vNV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglMulticastGetQueryObjectui64vNV(int paramInt1, int paramInt2, int paramInt3, long paramLong);
}
