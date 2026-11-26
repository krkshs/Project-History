package org.lwjgl.system;

import java.nio.charset.StandardCharsets;

final class MultiReleaseTextDecoding {
  static {
    APIUtil.apiLog("Java 9 text decoding enabled");
  }
  
  static String decodeUTF8(long source, int length) {
    if (length <= 0)
      return ""; 
    byte[] bytes = (length <= MemoryUtil.ARRAY_TLC_SIZE) ? MemoryUtil.ARRAY_TLC_BYTE.get() : new byte[length];
    MemoryUtil.memByteBuffer(source, length).get(bytes, 0, length);
    return new String(bytes, 0, length, StandardCharsets.UTF_8);
  }
}
