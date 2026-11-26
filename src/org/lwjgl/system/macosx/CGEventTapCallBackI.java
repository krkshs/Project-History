package org.lwjgl.system.macosx;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("CGEventRef (*) (CGEventTapProxy, CGEventType, CGEventRef, void *)")
public interface CGEventTapCallBackI extends CallbackI.P {
  public static final String SIGNATURE = "(pipp)p";
  
  default String getSignature() {
    return "(pipp)p";
  }
  
  default long callback(long args) {
    return invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  @NativeType("CGEventRef")
  long invoke(@NativeType("CGEventTapProxy") long paramLong1, @NativeType("CGEventType") int paramInt, @NativeType("CGEventRef") long paramLong2, @NativeType("void *") long paramLong3);
}
