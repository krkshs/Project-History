package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTX11SyncObject {
  public static final int GL_SYNC_X11_FENCE_EXT = 37089;
  
  static {
    GL.initialize();
  }
  
  protected EXTX11SyncObject() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(caps.glImportSyncEXT);
  }
  
  @NativeType("GLsync")
  public static native long glImportSyncEXT(@NativeType("GLenum") int paramInt1, @NativeType("GLintptr") long paramLong, @NativeType("GLbitfield") int paramInt2);
}
