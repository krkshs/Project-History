package org.lwjgl.system.macosx;

import org.lwjgl.system.Library;
import org.lwjgl.system.SharedLibrary;

public final class LibSystem {
  private LibSystem() {
    throw new UnsupportedOperationException();
  }
  
  private static final SharedLibrary SYSTEM = Library.loadNative(LibSystem.class, "org.lwjgl", "System");
  
  public static SharedLibrary getLibrary() {
    return SYSTEM;
  }
}
