package org.lwjgl.opengl;

import org.lwjgl.system.Callback;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLDEBUGPROCAMD")
public interface GLDebugMessageAMDCallbackI extends CallbackI.V {
  public static final String SIGNATURE = Callback.__stdcall("(iiiipp)v");
  
  default String getSignature() {
    return SIGNATURE;
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(@NativeType("GLuint") int paramInt1, @NativeType("GLenum") int paramInt2, @NativeType("GLenum") int paramInt3, @NativeType("GLsizei") int paramInt4, @NativeType("GLchar const *") long paramLong1, @NativeType("void *") long paramLong2);
}
