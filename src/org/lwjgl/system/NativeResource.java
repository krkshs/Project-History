package org.lwjgl.system;

public interface NativeResource extends AutoCloseable {
  void free();
  
  default void close() {
    free();
  }
}
