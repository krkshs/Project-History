package org.lwjgl.system;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class StackWalkUtil {
  static {
    APIUtil.apiLog("Java 9 stack walker enabled");
  }
  
  static StackTraceElement[] stackWalkArray(Object[] a) {
    return Arrays.<StackWalker.StackFrame>stream((StackWalker.StackFrame[])a)
      .map(StackWalker.StackFrame::toStackTraceElement)
      .toArray(StackTraceElement[]::new);
  }
  
  static Object stackWalkGetMethod(Class<?> after) {
    return StackWalker.getInstance()
      .walk(s -> 
        s.skip(2L)
        .filter(f -> !f.getClassName().startsWith(after.getName()))
        .findFirst())
      
      .orElseThrow(IllegalStateException::new);
  }
  
  private static boolean isSameMethod(StackWalker.StackFrame a, StackWalker.StackFrame b) {
    return isSameMethod(a, b, b.getMethodName());
  }
  
  private static boolean isSameMethod(StackWalker.StackFrame a, StackWalker.StackFrame b, String methodName) {
    return (a.getMethodName().equals(methodName) && 
      a.getClassName().equals(b.getClassName()) && 
      a.getFileName().equals(b.getFileName()));
  }
  
  private static boolean isAutoCloseable(StackWalker.StackFrame element, StackWalker.StackFrame pushed) {
    if (isSameMethod(element, pushed, "$closeResource"))
      return true; 
    if ("closeFinally".equals(element.getMethodName()) && "AutoCloseable.kt".equals(element.getFileName()))
      return true; 
    return false;
  }
  
  static Object stackWalkCheckPop(Class<?> after, Object pushedObj) {
    return StackWalker.getInstance().walk(s -> {
          Iterator<StackWalker.StackFrame> iter = s.skip(2L)
            .dropWhile(f -> f.getClassName().startsWith(after.getName()))
            .iterator();
          if (iter.hasNext()) {
            StackWalker.StackFrame element = iter.next();
            StackWalker.StackFrame pushed = (StackWalker.StackFrame)pushedObj;
            if (isSameMethod(element, pushed))
              return null; 
            if (isAutoCloseable(element, pushed) && iter.hasNext()) {
              element = iter.next();
              if (isSameMethod(pushed, element))
                return null; 
            } 
            return element;
          } 
          throw new IllegalStateException();
        });
  }
  
  static Object[] stackWalkGetTrace() {
    return StackWalker.getInstance()
      .walk(s -> 

        
        s.skip(2L).dropWhile(f -> f.getClassName().startsWith("org.lwjgl.system.Memory")).toArray(StackWalker.StackFrame[]::new));
  }
}
