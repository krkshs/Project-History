package org.lwjgl.system;

import java.util.Arrays;
import javax.annotation.Nullable;

final class StackWalkUtil {
  static StackTraceElement[] stackWalkArray(Object[] a) {
    return (StackTraceElement[])a;
  }
  
  static Object stackWalkGetMethod(Class<?> after) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (int i = 3; i < stackTrace.length; i++) {
      if (!stackTrace[i].getClassName().startsWith(after.getName()))
        return stackTrace[i]; 
    } 
    throw new IllegalStateException();
  }
  
  private static boolean isSameMethod(StackTraceElement a, StackTraceElement b) {
    return isSameMethod(a, b, b.getMethodName());
  }
  
  private static boolean isSameMethod(StackTraceElement a, StackTraceElement b, String methodName) {
    return (a.getMethodName().equals(methodName) && 
      a.getClassName().equals(b.getClassName()) && 
      a.getFileName().equals(b.getFileName()));
  }
  
  private static boolean isAutoCloseable(StackTraceElement element, StackTraceElement pushed) {
    if (isSameMethod(element, pushed, "$closeResource"))
      return true; 
    if ("closeFinally".equals(element.getMethodName()) && "AutoCloseable.kt".equals(element.getFileName()))
      return true; 
    return false;
  }
  
  @Nullable
  static Object stackWalkCheckPop(Class<?> after, Object pushedObj) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (int i = 3; i < stackTrace.length; ) {
      StackTraceElement element = stackTrace[i];
      if (element.getClassName().startsWith(after.getName())) {
        i++;
        continue;
      } 
      StackTraceElement pushed = (StackTraceElement)pushedObj;
      if (isSameMethod(element, pushed))
        return null; 
      if (isAutoCloseable(element, pushed) && i + 1 < stackTrace.length) {
        element = stackTrace[i + 1];
        if (isSameMethod(pushed, stackTrace[i + 1]))
          return null; 
      } 
      return element;
    } 
    throw new IllegalStateException();
  }
  
  static Object[] stackWalkGetTrace() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    int i = 3;
    for (; i < stackTrace.length && 
      stackTrace[i].getClassName().startsWith("org.lwjgl.system.Memory"); i++);
    return Arrays.copyOfRange(stackTrace, i, stackTrace.length);
  }
}
