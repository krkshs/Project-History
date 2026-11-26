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

public class XMapEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int EVENT;
  
  public static final int WINDOW;
  
  public static final int OVERRIDE_REDIRECT;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    EVENT = layout.offsetof(4);
    WINDOW = layout.offsetof(5);
    OVERRIDE_REDIRECT = layout.offsetof(6);
  }
  
  public XMapEvent(ByteBuffer container) {
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
  public long event() {
    return nevent(address());
  }
  
  @NativeType("Window")
  public long window() {
    return nwindow(address());
  }
  
  public int override_redirect() {
    return noverride_redirect(address());
  }
  
  public XMapEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XMapEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XMapEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XMapEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XMapEvent event(@NativeType("Window") long value) {
    nevent(address(), value);
    return this;
  }
  
  public XMapEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XMapEvent override_redirect(int value) {
    noverride_redirect(address(), value);
    return this;
  }
  
  public XMapEvent set(int type, long serial, boolean send_event, long display, long event, long window, int override_redirect) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    event(event);
    window(window);
    override_redirect(override_redirect);
    return this;
  }
  
  public XMapEvent set(XMapEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XMapEvent malloc() {
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XMapEvent calloc() {
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XMapEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XMapEvent create(long address) {
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, address);
  }
  
  @Nullable
  public static XMapEvent createSafe(long address) {
    return (address == 0L) ? null : XMapEvent.<XMapEvent>wrap(XMapEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XMapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XMapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XMapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XMapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XMapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XMapEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XMapEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XMapEvent mallocStack(MemoryStack stack) {
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XMapEvent callocStack(MemoryStack stack) {
    return XMapEvent.<XMapEvent>wrap(XMapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XMapEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XMapEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nevent(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)EVENT);
  }
  
  public static long nwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)WINDOW);
  }
  
  public static int noverride_redirect(long struct) {
    return UNSAFE.getInt(null, struct + (long)OVERRIDE_REDIRECT);
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
  
  public static void nevent(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)EVENT, value);
  }
  
  public static void nwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)WINDOW, value);
  }
  
  public static void noverride_redirect(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)OVERRIDE_REDIRECT, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XMapEvent, Buffer> implements NativeResource {
    private static final XMapEvent ELEMENT_FACTORY = XMapEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XMapEvent.SIZEOF);
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
    
    protected XMapEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XMapEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XMapEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XMapEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XMapEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long event() {
      return XMapEvent.nevent(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XMapEvent.nwindow(address());
    }
    
    public int override_redirect() {
      return XMapEvent.noverride_redirect(address());
    }
    
    public Buffer type(int value) {
      XMapEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XMapEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XMapEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XMapEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer event(@NativeType("Window") long value) {
      XMapEvent.nevent(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XMapEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer override_redirect(int value) {
      XMapEvent.noverride_redirect(address(), value);
      return this;
    }
  }
}
