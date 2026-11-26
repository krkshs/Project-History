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

public class XGenericEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int EXTENSION;
  
  public static final int EVTYPE;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    EXTENSION = layout.offsetof(4);
    EVTYPE = layout.offsetof(5);
  }
  
  public XGenericEvent(ByteBuffer container) {
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
  
  public int extension() {
    return nextension(address());
  }
  
  public int evtype() {
    return nevtype(address());
  }
  
  public XGenericEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XGenericEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XGenericEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XGenericEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XGenericEvent extension(int value) {
    nextension(address(), value);
    return this;
  }
  
  public XGenericEvent evtype(int value) {
    nevtype(address(), value);
    return this;
  }
  
  public XGenericEvent set(int type, long serial, boolean send_event, long display, int extension, int evtype) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    extension(extension);
    evtype(evtype);
    return this;
  }
  
  public XGenericEvent set(XGenericEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XGenericEvent malloc() {
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XGenericEvent calloc() {
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XGenericEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XGenericEvent create(long address) {
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, address);
  }
  
  @Nullable
  public static XGenericEvent createSafe(long address) {
    return (address == 0L) ? null : XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XGenericEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XGenericEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XGenericEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XGenericEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XGenericEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XGenericEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XGenericEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XGenericEvent mallocStack(MemoryStack stack) {
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XGenericEvent callocStack(MemoryStack stack) {
    return XGenericEvent.<XGenericEvent>wrap(XGenericEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XGenericEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XGenericEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static int nextension(long struct) {
    return UNSAFE.getInt(null, struct + (long)EXTENSION);
  }
  
  public static int nevtype(long struct) {
    return UNSAFE.getInt(null, struct + (long)EVTYPE);
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
  
  public static void nextension(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)EXTENSION, value);
  }
  
  public static void nevtype(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)EVTYPE, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XGenericEvent, Buffer> implements NativeResource {
    private static final XGenericEvent ELEMENT_FACTORY = XGenericEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XGenericEvent.SIZEOF);
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
    
    protected XGenericEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XGenericEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XGenericEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XGenericEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XGenericEvent.ndisplay(address());
    }
    
    public int extension() {
      return XGenericEvent.nextension(address());
    }
    
    public int evtype() {
      return XGenericEvent.nevtype(address());
    }
    
    public Buffer type(int value) {
      XGenericEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XGenericEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XGenericEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XGenericEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer extension(int value) {
      XGenericEvent.nextension(address(), value);
      return this;
    }
    
    public Buffer evtype(int value) {
      XGenericEvent.nevtype(address(), value);
      return this;
    }
  }
}
