package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBOcclusionQuery {
  public static final int GL_SAMPLES_PASSED_ARB = 35092;
  
  public static final int GL_QUERY_COUNTER_BITS_ARB = 34916;
  
  public static final int GL_CURRENT_QUERY_ARB = 34917;
  
  public static final int GL_QUERY_RESULT_ARB = 34918;
  
  public static final int GL_QUERY_RESULT_AVAILABLE_ARB = 34919;
  
  static {
    GL.initialize();
  }
  
  protected ARBOcclusionQuery() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glGenQueriesARB, caps.glDeleteQueriesARB, caps.glIsQueryARB, caps.glBeginQueryARB, caps.glEndQueryARB, caps.glGetQueryivARB, caps.glGetQueryObjectivARB, caps.glGetQueryObjectuivARB });
  }
  
  public static void glGenQueriesARB(@NativeType("GLuint *") IntBuffer ids) {
    nglGenQueriesARB(ids.remaining(), MemoryUtil.memAddress(ids));
  }
  
  @NativeType("void")
  public static int glGenQueriesARB() {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer ids = stack.callocInt(1);
      nglGenQueriesARB(1, MemoryUtil.memAddress(ids));
      return ids.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glDeleteQueriesARB(@NativeType("GLuint const *") IntBuffer ids) {
    nglDeleteQueriesARB(ids.remaining(), MemoryUtil.memAddress(ids));
  }
  
  public static void glDeleteQueriesARB(@NativeType("GLuint const *") int id) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer ids = stack.ints(id);
      nglDeleteQueriesARB(1, MemoryUtil.memAddress(ids));
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetQueryivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetQueryivARB(target, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetQueryiARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetQueryivARB(target, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetQueryObjectivARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetQueryObjectivARB(id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetQueryObjectiARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetQueryObjectivARB(id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetQueryObjectuivARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
    if (Checks.CHECKS)
      Checks.check((Buffer)params, 1); 
    nglGetQueryObjectuivARB(id, pname, MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetQueryObjectuiARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetQueryObjectuivARB(id, pname, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGenQueriesARB(@NativeType("GLuint *") int[] ids) {
    long __functionAddress = (GL.getICD()).glGenQueriesARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(ids.length, ids, __functionAddress);
  }
  
  public static void glDeleteQueriesARB(@NativeType("GLuint const *") int[] ids) {
    long __functionAddress = (GL.getICD()).glDeleteQueriesARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(ids.length, ids, __functionAddress);
  }
  
  public static void glGetQueryivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetQueryivARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(target, pname, params, __functionAddress);
  }
  
  public static void glGetQueryObjectivARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetQueryObjectivARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(id, pname, params, __functionAddress);
  }
  
  public static void glGetQueryObjectuivARB(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetQueryObjectuivARB;
    if (Checks.CHECKS) {
      Checks.check(__functionAddress);
      Checks.check(params, 1);
    } 
    JNI.callPV(id, pname, params, __functionAddress);
  }
  
  public static native void nglGenQueriesARB(int paramInt, long paramLong);
  
  public static native void nglDeleteQueriesARB(int paramInt, long paramLong);
  
  @NativeType("GLboolean")
  public static native boolean glIsQueryARB(@NativeType("GLuint") int paramInt);
  
  public static native void glBeginQueryARB(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glEndQueryARB(@NativeType("GLenum") int paramInt);
  
  public static native void nglGetQueryivARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetQueryObjectivARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetQueryObjectuivARB(int paramInt1, int paramInt2, long paramLong);
}
