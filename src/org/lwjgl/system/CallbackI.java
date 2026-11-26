package org.lwjgl.system;

public interface CallbackI extends Pointer {
  String getSignature();
  
  default long address() {
    return Callback.create(getSignature(), this);
  }
  
  public static interface P extends CallbackI {
    long callback(long param1Long);
  }
  
  public static interface D extends CallbackI {
    double callback(long param1Long);
  }
  
  public static interface F extends CallbackI {
    float callback(long param1Long);
  }
  
  public static interface N extends CallbackI {
    long callback(long param1Long);
  }
  
  public static interface J extends CallbackI {
    long callback(long param1Long);
  }
  
  public static interface I extends CallbackI {
    int callback(long param1Long);
  }
  
  public static interface S extends CallbackI {
    short callback(long param1Long);
  }
  
  public static interface B extends CallbackI {
    byte callback(long param1Long);
  }
  
  public static interface Z extends CallbackI {
    boolean callback(long param1Long);
  }
  
  public static interface V extends CallbackI {
    void callback(long param1Long);
  }
}
