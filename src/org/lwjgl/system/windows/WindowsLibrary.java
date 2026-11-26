package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.SharedLibrary;

public class WindowsLibrary extends SharedLibrary.Default {
  public static final long HINSTANCE;
  
  static {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      HINSTANCE = WinBase.GetModuleHandle(stack.UTF16(Library.JNI_LIBRARY_NAME));
      if (HINSTANCE == 0L)
        throw new RuntimeException("Failed to retrieve LWJGL module handle."); 
    } 
  }
  
  public WindowsLibrary(String name) {
    this(name, loadLibrary(name));
  }
  
  public WindowsLibrary(String name, long handle) {
    super(name, handle);
  }
  
  private static long loadLibrary(String name) {
    long handle;
    try (MemoryStack stack = MemoryStack.stackPush()) {
      handle = WinBase.LoadLibrary(stack.UTF16(name));
    } 
    if (handle == 0L)
      throw new UnsatisfiedLinkError("Failed to load library: " + name + " (error code = " + WinBase.getLastError() + ")"); 
    return handle;
  }
  
  public long getFunctionAddress(ByteBuffer functionName) {
    return WinBase.GetProcAddress(address(), functionName);
  }
  
  public void free() {
    if (!WinBase.FreeLibrary(address()))
      WindowsUtil.windowsThrowException("Failed to unload library: " + getName()); 
  }
}
