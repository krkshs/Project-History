package org.lwjgl.system;

final class MultiReleaseMemCopy {
  static {
    APIUtil.apiLog("Java 10 memcpy enabled");
  }
  
  static void copy(long src, long dst, long bytes) {
    if (bytes < 64L && ((int)src & 0x7) == 0 && ((int)dst & 0x7) == 0) {
      MemoryUtil.memCopyAligned(src, dst, (int)bytes & 0x3F);
    } else
      MemoryUtil.UNSAFE.copyMemory(src, dst, bytes); 
  }
}
