package org.lwjgl.system;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.jni.JNINativeInterface;

public final class ThreadLocalUtil {
  private static final long JNI_NATIVE_INTERFACE = getThreadJNIEnv();
  
  private static final long FUNCTION_MISSING_ABORT = getFunctionMissingAbort();
  
  private static final long SIZE_OF_JNI_NATIVE_INTERFACE;
  
  static {
    int reservedCount, jniCallCount;
    int JNI_VERSION = JNINativeInterface.GetVersion();
    switch (JNI_VERSION) {
      case 65537:
        reservedCount = 12;
        break;
      default:
        reservedCount = 4;
        break;
    } 
    switch (JNI_VERSION) {
      case 65537:
        jniCallCount = 208;
        break;
      case 65538:
        jniCallCount = 225;
        break;
      case 65540:
        jniCallCount = 228;
        break;
      case 65542:
      case 65544:
        jniCallCount = 229;
        break;
      case 589824:
      case 655360:
        jniCallCount = 230;
        break;
      default:
        jniCallCount = 230;
        APIUtil.DEBUG_STREAM.println("[LWJGL] [ThreadLocalUtil] Unsupported JNI version detected, this may result in a crash. Please inform LWJGL developers.");
        break;
    } 
    SIZE_OF_JNI_NATIVE_INTERFACE = Integer.toUnsignedLong(reservedCount + jniCallCount) * (long)Pointer.POINTER_SIZE;
  }
  
  public static void setEnv(long capabilities, int index) {
    if (index < 0 || 3 < index)
      throw new IndexOutOfBoundsException(); 
    long env = getThreadJNIEnv();
    if (capabilities == 0L) {
      if (env != JNI_NATIVE_INTERFACE) {
        setThreadJNIEnv(JNI_NATIVE_INTERFACE);
        MemoryUtil.nmemFree(env);
      } 
    } else {
      if (env == JNI_NATIVE_INTERFACE) {
        long newEnv = MemoryUtil.nmemAllocChecked(SIZE_OF_JNI_NATIVE_INTERFACE);
        MemoryUtil.memCopy(env, newEnv, SIZE_OF_JNI_NATIVE_INTERFACE);
        setThreadJNIEnv(env = newEnv);
      } 
      MemoryUtil.memPutAddress(env + Integer.toUnsignedLong(index) * (long)Pointer.POINTER_SIZE, capabilities);
    } 
  }
  
  private static List<Field> getFieldsFromCapabilities(Class<?> capabilitiesClass) {
    List<Field> fields = new ArrayList<>();
    for (Field field : capabilitiesClass.getFields()) {
      if (field.getType() == long.class)
        fields.add(field); 
    } 
    return fields;
  }
  
  public static void setFunctionMissingAddresses(@Nullable Class<?> capabilitiesClass, int index) {
    if (capabilitiesClass == null) {
      long missingCaps = MemoryUtil.memGetAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(index) * (long)Pointer.POINTER_SIZE);
      if (missingCaps != 0L) {
        MemoryUtil.getAllocator().free(missingCaps);
        MemoryUtil.memPutAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(index) * (long)Pointer.POINTER_SIZE, 0L);
      } 
    } else {
      int functionCount = getFieldsFromCapabilities(capabilitiesClass).size();
      long missingCaps = MemoryUtil.getAllocator().malloc(Integer.toUnsignedLong(functionCount) * (long)Pointer.POINTER_SIZE);
      for (int i = 0; i < functionCount; i++)
        MemoryUtil.memPutAddress(missingCaps + Integer.toUnsignedLong(i) * (long)Pointer.POINTER_SIZE, FUNCTION_MISSING_ABORT); 
      MemoryUtil.memPutAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(index) * (long)Pointer.POINTER_SIZE, missingCaps);
    } 
  }
  
  public static PointerBuffer getAddressesFromCapabilities(Object caps) {
    List<Field> fields = getFieldsFromCapabilities(caps.getClass());
    PointerBuffer addresses = BufferUtils.createPointerBuffer(fields.size());
    try {
      for (int i = 0; i < fields.size(); i++) {
        long a = fields.get(i).getLong(caps);
        addresses.put(i, (a != 0L) ? a : FUNCTION_MISSING_ABORT);
      } 
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } 
    return addresses;
  }
  
  public static boolean areCapabilitiesDifferent(PointerBuffer ref, PointerBuffer caps) {
    for (int i = 0; i < ref.remaining(); i++) {
      if (ref.get(i) != caps.get(i) && caps.get(i) != 0L)
        return true; 
    } 
    return false;
  }
  
  private static native long getThreadJNIEnv();
  
  private static native void setThreadJNIEnv(long paramLong);
  
  private static native long getFunctionMissingAbort();
}
