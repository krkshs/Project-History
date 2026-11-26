package org.lwjgl.system;

import javax.annotation.Nullable;
import org.lwjgl.system.dyncall.DynLoad;

public interface SharedLibrary extends FunctionProvider, NativeResource, Pointer {
  String getName();
  
  @Nullable
  String getPath();
  
  public static abstract class Default extends Pointer.Default implements SharedLibrary {
    private final String name;
    
    protected Default(String name, long handle) {
      super(handle);
      this.name = name;
    }
    
    public String getName() {
      return this.name;
    }
    
    @Nullable
    public String getPath() {
      String path = DynLoad.dlGetLibraryPath(address(), 256);
      return path.isEmpty() ? null : path;
    }
  }
  
  public static abstract class Delegate implements SharedLibrary {
    protected final SharedLibrary library;
    
    protected Delegate(SharedLibrary library) {
      this.library = library;
    }
    
    public String getName() {
      return this.library.getName();
    }
    
    @Nullable
    public String getPath() {
      return this.library.getPath();
    }
    
    public long address() {
      return this.library.address();
    }
    
    public void free() {
      this.library.free();
    }
  }
}
