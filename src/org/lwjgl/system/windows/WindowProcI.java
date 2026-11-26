package org.lwjgl.system.windows;

import org.lwjgl.system.Callback;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("WNDPROC")
public interface WindowProcI extends CallbackI.P {
  public static final String SIGNATURE = Callback.__stdcall("(pipp)p");
  
  default String getSignature() {
    return SIGNATURE;
  }
  
  default long callback(long args) {
    return invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  @NativeType("LRESULT")
  long invoke(@NativeType("HWND") long paramLong1, @NativeType("UINT") int paramInt, @NativeType("WPARAM") long paramLong2, @NativeType("LPARAM") long paramLong3);
}
