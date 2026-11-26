package org.lwjgl.opengl;

import org.lwjgl.system.Callback;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLDEBUGPROC")
public interface GLDebugMessageCallbackI extends CallbackI.V {
  public static final String SIGNATURE = Callback.__stdcall("(iiiiipp)v");
  
  default String getSignature() {
    return SIGNATURE;
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(@NativeType("GLenum") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLenum") int paramInt4, @NativeType("GLsizei") int paramInt5, @NativeType("GLchar const *") long paramLong1, @NativeType("void const *") long paramLong2);
}
