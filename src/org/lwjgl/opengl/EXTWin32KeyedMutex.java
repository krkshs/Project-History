package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTWin32KeyedMutex {
  static {
    GL.initialize();
  }
  
  protected EXTWin32KeyedMutex() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glAcquireKeyedMutexWin32EXT, caps.glReleaseKeyedMutexWin32EXT });
  }
  
  @NativeType("GLboolean")
  public static native boolean glAcquireKeyedMutexWin32EXT(@NativeType("GLuint") int paramInt1, @NativeType("GLuint64") long paramLong, @NativeType("GLuint") int paramInt2);
  
  @NativeType("GLboolean")
  public static native boolean glReleaseKeyedMutexWin32EXT(@NativeType("GLuint") int paramInt, @NativeType("GLuint64") long paramLong);
}
