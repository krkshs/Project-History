package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSeparateShaderObjects {
  public static final int GL_ACTIVE_PROGRAM_EXT = 35725;
  
  static {
    GL.initialize();
  }
  
  protected EXTSeparateShaderObjects() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glUseShaderProgramEXT, caps.glActiveProgramEXT, caps.glCreateShaderProgramEXT });
  }
  
  @NativeType("GLuint")
  public static int glCreateShaderProgramEXT(@NativeType("GLenum") int type, @NativeType("GLchar const *") ByteBuffer string) {
    if (Checks.CHECKS)
      Checks.checkNT1(string); 
    return nglCreateShaderProgramEXT(type, MemoryUtil.memAddress(string));
  }
  
  @NativeType("GLuint")
  public static int glCreateShaderProgramEXT(@NativeType("GLenum") int type, @NativeType("GLchar const *") CharSequence string) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      stack.nUTF8(string, true);
      long stringEncoded = stack.getPointerAddress();
      return nglCreateShaderProgramEXT(type, stringEncoded);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static native void glUseShaderProgramEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLuint") int paramInt2);
  
  public static native void glActiveProgramEXT(@NativeType("GLuint") int paramInt);
  
  public static native int nglCreateShaderProgramEXT(int paramInt, long paramLong);
}
