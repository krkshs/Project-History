package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class DISPLAY_DEVICE extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int CB;
  
  public static final int DEVICENAME;
  
  public static final int DEVICESTRING;
  
  public static final int STATEFLAGS;
  
  public static final int DEVICEID;
  
  public static final int DEVICEKEY;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(4), 
          __array(2, 32), 
          __array(2, 128), 
          __member(4), 
          __array(2, 128), 
          __array(2, 128) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    CB = layout.offsetof(0);
    DEVICENAME = layout.offsetof(1);
    DEVICESTRING = layout.offsetof(2);
    STATEFLAGS = layout.offsetof(3);
    DEVICEID = layout.offsetof(4);
    DEVICEKEY = layout.offsetof(5);
  }
  
  public DISPLAY_DEVICE(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("DWORD")
  public int cb() {
    return ncb(address());
  }
  
  @NativeType("TCHAR[32]")
  public ByteBuffer DeviceName() {
    return nDeviceName(address());
  }
  
  @NativeType("TCHAR[32]")
  public String DeviceNameString() {
    return nDeviceNameString(address());
  }
  
  @NativeType("TCHAR[128]")
  public ByteBuffer DeviceString() {
    return nDeviceString(address());
  }
  
  @NativeType("TCHAR[128]")
  public String DeviceStringString() {
    return nDeviceStringString(address());
  }
  
  @NativeType("DWORD")
  public int StateFlags() {
    return nStateFlags(address());
  }
  
  @NativeType("TCHAR[128]")
  public ByteBuffer DeviceID() {
    return nDeviceID(address());
  }
  
  @NativeType("TCHAR[128]")
  public String DeviceIDString() {
    return nDeviceIDString(address());
  }
  
  @NativeType("TCHAR[128]")
  public ByteBuffer DeviceKey() {
    return nDeviceKey(address());
  }
  
  @NativeType("TCHAR[128]")
  public String DeviceKeyString() {
    return nDeviceKeyString(address());
  }
  
  public DISPLAY_DEVICE cb(@NativeType("DWORD") int value) {
    ncb(address(), value);
    return this;
  }
  
  public DISPLAY_DEVICE set(DISPLAY_DEVICE src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static DISPLAY_DEVICE malloc() {
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static DISPLAY_DEVICE calloc() {
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static DISPLAY_DEVICE create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, MemoryUtil.memAddress(container), container);
  }
  
  public static DISPLAY_DEVICE create(long address) {
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, address);
  }
  
  @Nullable
  public static DISPLAY_DEVICE createSafe(long address) {
    return (address == 0L) ? null : DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static DISPLAY_DEVICE mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static DISPLAY_DEVICE callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static DISPLAY_DEVICE mallocStack(MemoryStack stack) {
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static DISPLAY_DEVICE callocStack(MemoryStack stack) {
    return DISPLAY_DEVICE.<DISPLAY_DEVICE>wrap(DISPLAY_DEVICE.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return DISPLAY_DEVICE.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ncb(long struct) {
    return UNSAFE.getInt(null, struct + (long)CB);
  }
  
  public static ByteBuffer nDeviceName(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)DEVICENAME, 64);
  }
  
  public static String nDeviceNameString(long struct) {
    return MemoryUtil.memUTF16(struct + (long)DEVICENAME);
  }
  
  public static ByteBuffer nDeviceString(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)DEVICESTRING, 256);
  }
  
  public static String nDeviceStringString(long struct) {
    return MemoryUtil.memUTF16(struct + (long)DEVICESTRING);
  }
  
  public static int nStateFlags(long struct) {
    return UNSAFE.getInt(null, struct + (long)STATEFLAGS);
  }
  
  public static ByteBuffer nDeviceID(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)DEVICEID, 256);
  }
  
  public static String nDeviceIDString(long struct) {
    return MemoryUtil.memUTF16(struct + (long)DEVICEID);
  }
  
  public static ByteBuffer nDeviceKey(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)DEVICEKEY, 256);
  }
  
  public static String nDeviceKeyString(long struct) {
    return MemoryUtil.memUTF16(struct + (long)DEVICEKEY);
  }
  
  public static void ncb(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CB, value);
  }
  
  public static class Buffer extends StructBuffer<DISPLAY_DEVICE, Buffer> implements NativeResource {
    private static final DISPLAY_DEVICE ELEMENT_FACTORY = DISPLAY_DEVICE.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / DISPLAY_DEVICE.SIZEOF);
    }
    
    public Buffer(long address, int cap) {
      super(address, null, -1, 0, cap, cap);
    }
    
    Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
      super(address, container, mark, pos, lim, cap);
    }
    
    protected Buffer self() {
      return this;
    }
    
    protected DISPLAY_DEVICE getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("DWORD")
    public int cb() {
      return DISPLAY_DEVICE.ncb(address());
    }
    
    @NativeType("TCHAR[32]")
    public ByteBuffer DeviceName() {
      return DISPLAY_DEVICE.nDeviceName(address());
    }
    
    @NativeType("TCHAR[32]")
    public String DeviceNameString() {
      return DISPLAY_DEVICE.nDeviceNameString(address());
    }
    
    @NativeType("TCHAR[128]")
    public ByteBuffer DeviceString() {
      return DISPLAY_DEVICE.nDeviceString(address());
    }
    
    @NativeType("TCHAR[128]")
    public String DeviceStringString() {
      return DISPLAY_DEVICE.nDeviceStringString(address());
    }
    
    @NativeType("DWORD")
    public int StateFlags() {
      return DISPLAY_DEVICE.nStateFlags(address());
    }
    
    @NativeType("TCHAR[128]")
    public ByteBuffer DeviceID() {
      return DISPLAY_DEVICE.nDeviceID(address());
    }
    
    @NativeType("TCHAR[128]")
    public String DeviceIDString() {
      return DISPLAY_DEVICE.nDeviceIDString(address());
    }
    
    @NativeType("TCHAR[128]")
    public ByteBuffer DeviceKey() {
      return DISPLAY_DEVICE.nDeviceKey(address());
    }
    
    @NativeType("TCHAR[128]")
    public String DeviceKeyString() {
      return DISPLAY_DEVICE.nDeviceKeyString(address());
    }
    
    public Buffer cb(@NativeType("DWORD") int value) {
      DISPLAY_DEVICE.ncb(address(), value);
      return this;
    }
  }
}
