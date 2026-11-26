package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBCLEvent {
  public static final int GL_SYNC_CL_EVENT_ARB = 33344;
  
  public static final int GL_SYNC_CL_EVENT_COMPLETE_ARB = 33345;
  
  static {
    GL.initialize();
  }
  
  protected ARBCLEvent() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glCreateSyncFromCLeventARB });
  }
  
  @NativeType("GLsync")
  public static long glCreateSyncFromCLeventARB(@NativeType("cl_context") long context, @NativeType("cl_event") long event, @NativeType("GLbitfield") int flags) {
    if (Checks.CHECKS) {
      Checks.check(context);
      Checks.check(event);
    } 
    return nglCreateSyncFromCLeventARB(context, event, flags);
  }
  
  public static native long nglCreateSyncFromCLeventARB(long paramLong1, long paramLong2, int paramInt);
}
