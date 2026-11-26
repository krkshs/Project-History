package org.lwjgl.system;

import java.util.Objects;

public final class CheckIntrinsics {
  static {
    APIUtil.apiLog("Java 9 check intrinsics enabled");
  }
  
  public static int checkIndex(int index, int length) {
    return Objects.checkIndex(index, length);
  }
  
  public static int checkFromToIndex(int fromIndex, int toIndex, int length) {
    return Objects.checkFromToIndex(fromIndex, toIndex, length);
  }
  
  public static int checkFromIndexSize(int fromIndex, int size, int length) {
    return Objects.checkFromIndexSize(fromIndex, size, length);
  }
}
