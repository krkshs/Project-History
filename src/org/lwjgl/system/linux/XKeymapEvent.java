package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XKeymapEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int KEY_VECTOR;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __array(1, 32));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    KEY_VECTOR = layout.offsetof(5);
  }
  
  public XKeymapEvent(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  public int type() {
    return ntype(address());
  }
  
  @NativeType("unsigned long")
  public long serial() {
    return nserial(address());
  }
  
  @NativeType("Bool")
  public boolean send_event() {
    return (nsend_event(address()) != 0);
  }
  
  @NativeType("Display *")
  public long display() {
    return ndisplay(address());
  }
  
  @NativeType("Window")
  public long window() {
    return nwindow(address());
  }
  
  @NativeType("char[32]")
  public ByteBuffer key_vector() {
    return nkey_vector(address());
  }
  
  @NativeType("char")
  public byte key_vector(int index) {
    return nkey_vector(address(), index);
  }
  
  public XKeymapEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XKeymapEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XKeymapEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XKeymapEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XKeymapEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XKeymapEvent key_vector(@NativeType("char[32]") ByteBuffer value) {
    nkey_vector(address(), value);
    return this;
  }
  
  public XKeymapEvent key_vector(int index, @NativeType("char") byte value) {
    nkey_vector(address(), index, value);
    return this;
  }
  
  public XKeymapEvent set(int type, long serial, boolean send_event, long display, long window, ByteBuffer key_vector) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    key_vector(key_vector);
    return this;
  }
  
  public XKeymapEvent set(XKeymapEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XKeymapEvent malloc() {
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XKeymapEvent calloc() {
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XKeymapEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XKeymapEvent create(long address) {
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, address);
  }
  
  @Nullable
  public static XKeymapEvent createSafe(long address) {
    return (address == 0L) ? null : XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XKeymapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XKeymapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XKeymapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XKeymapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XKeymapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XKeymapEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XKeymapEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XKeymapEvent mallocStack(MemoryStack stack) {
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XKeymapEvent callocStack(MemoryStack stack) {
    return XKeymapEvent.<XKeymapEvent>wrap(XKeymapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XKeymapEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XKeymapEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ntype(long struct) {
    return UNSAFE.getInt(null, struct + (long)TYPE);
  }
  
  public static long nserial(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)SERIAL);
  }
  
  public static int nsend_event(long struct) {
    return UNSAFE.getInt(null, struct + (long)SEND_EVENT);
  }
  
  public static long ndisplay(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)DISPLAY);
  }
  
  public static long nwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)WINDOW);
  }
  
  public static ByteBuffer nkey_vector(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)KEY_VECTOR, 32);
  }
  
  public static byte nkey_vector(long struct, int index) {
    return UNSAFE.getByte(null, struct + (long)KEY_VECTOR + Checks.check(index, 32) * 1L);
  }
  
  public static void ntype(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)TYPE, value);
  }
  
  public static void nserial(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)SERIAL, value);
  }
  
  public static void nsend_event(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)SEND_EVENT, value);
  }
  
  public static void ndisplay(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)DISPLAY, Checks.check(value));
  }
  
  public static void nwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)WINDOW, value);
  }
  
  public static void nkey_vector(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 32); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)KEY_VECTOR, (long)(value.remaining() * 1));
  }
  
  public static void nkey_vector(long struct, int index, byte value) {
    UNSAFE.putByte(null, struct + (long)KEY_VECTOR + Checks.check(index, 32) * 1L, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XKeymapEvent, Buffer> implements NativeResource {
    private static final XKeymapEvent ELEMENT_FACTORY = XKeymapEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XKeymapEvent.SIZEOF);
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
    
    protected XKeymapEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XKeymapEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XKeymapEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XKeymapEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XKeymapEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XKeymapEvent.nwindow(address());
    }
    
    @NativeType("char[32]")
    public ByteBuffer key_vector() {
      return XKeymapEvent.nkey_vector(address());
    }
    
    @NativeType("char")
    public byte key_vector(int index) {
      return XKeymapEvent.nkey_vector(address(), index);
    }
    
    public Buffer type(int value) {
      XKeymapEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XKeymapEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XKeymapEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XKeymapEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XKeymapEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer key_vector(@NativeType("char[32]") ByteBuffer value) {
      XKeymapEvent.nkey_vector(address(), value);
      return this;
    }
    
    public Buffer key_vector(int index, @NativeType("char") byte value) {
      XKeymapEvent.nkey_vector(address(), index, value);
      return this;
    }
  }
}
