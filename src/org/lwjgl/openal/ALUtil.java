package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.system.MemoryUtil;

public final class ALUtil {
  @Nullable
  public static List<String> getStringList(long deviceHandle, int token) {
    long __result = ALC10.nalcGetString(deviceHandle, token);
    if (__result == 0L)
      return null; 
    ByteBuffer buffer = MemoryUtil.memByteBuffer(__result, Integer.MAX_VALUE);
    List<String> strings = new ArrayList<>();
    int offset = 0;
    while (true) {
      if (buffer.get() == 0) {
        int limit = buffer.position() - 1;
        if (limit == offset)
          break; 
        strings.add(MemoryUtil.memUTF8(buffer, limit - offset, offset));
        offset = buffer.position();
      } 
    } 
    return strings;
  }
}
