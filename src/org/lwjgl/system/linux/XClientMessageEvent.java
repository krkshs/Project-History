package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.CLongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XClientMessageEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int MESSAGE_TYPE;
  
  public static final int FORMAT;
  
  public static final int DATA;
  
  public static final int DATA_B;
  
  public static final int DATA_S;
  
  public static final int DATA_L;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(4), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(POINTER_SIZE), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(4), 
          __struct(new Struct.Member[] { __array(1, 20), 
              __array(2, 10), 
              __array(CLONG_SIZE, 5) }) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    MESSAGE_TYPE = layout.offsetof(5);
    FORMAT = layout.offsetof(6);
    DATA = layout.offsetof(7);
    DATA_B = layout.offsetof(8);
    DATA_S = layout.offsetof(9);
    DATA_L = layout.offsetof(10);
  }
  
  public XClientMessageEvent(ByteBuffer container) {
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
  
  @NativeType("Atom")
  public long message_type() {
    return nmessage_type(address());
  }
  
  public int format() {
    return nformat(address());
  }
  
  @NativeType("char[20]")
  public ByteBuffer data_b() {
    return ndata_b(address());
  }
  
  @NativeType("char")
  public byte data_b(int index) {
    return ndata_b(address(), index);
  }
  
  @NativeType("short[10]")
  public ShortBuffer data_s() {
    return ndata_s(address());
  }
  
  public short data_s(int index) {
    return ndata_s(address(), index);
  }
  
  @NativeType("long[5]")
  public CLongBuffer data_l() {
    return ndata_l(address());
  }
  
  public long data_l(int index) {
    return ndata_l(address(), index);
  }
  
  public XClientMessageEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XClientMessageEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XClientMessageEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XClientMessageEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XClientMessageEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XClientMessageEvent message_type(@NativeType("Atom") long value) {
    nmessage_type(address(), value);
    return this;
  }
  
  public XClientMessageEvent format(int value) {
    nformat(address(), value);
    return this;
  }
  
  public XClientMessageEvent data_b(@NativeType("char[20]") ByteBuffer value) {
    ndata_b(address(), value);
    return this;
  }
  
  public XClientMessageEvent data_b(int index, @NativeType("char") byte value) {
    ndata_b(address(), index, value);
    return this;
  }
  
  public XClientMessageEvent data_s(@NativeType("short[10]") ShortBuffer value) {
    ndata_s(address(), value);
    return this;
  }
  
  public XClientMessageEvent data_s(int index, short value) {
    ndata_s(address(), index, value);
    return this;
  }
  
  public XClientMessageEvent data_l(@NativeType("long[5]") CLongBuffer value) {
    ndata_l(address(), value);
    return this;
  }
  
  public XClientMessageEvent data_l(int index, long value) {
    ndata_l(address(), index, value);
    return this;
  }
  
  public XClientMessageEvent set(int type, long serial, boolean send_event, long display, long window, long message_type, int format, ByteBuffer data_b, ShortBuffer data_s, CLongBuffer data_l) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    message_type(message_type);
    format(format);
    data_b(data_b);
    data_s(data_s);
    data_l(data_l);
    return this;
  }
  
  public XClientMessageEvent set(XClientMessageEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XClientMessageEvent malloc() {
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XClientMessageEvent calloc() {
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XClientMessageEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XClientMessageEvent create(long address) {
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, address);
  }
  
  @Nullable
  public static XClientMessageEvent createSafe(long address) {
    return (address == 0L) ? null : XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XClientMessageEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XClientMessageEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XClientMessageEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XClientMessageEvent mallocStack(MemoryStack stack) {
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XClientMessageEvent callocStack(MemoryStack stack) {
    return XClientMessageEvent.<XClientMessageEvent>wrap(XClientMessageEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XClientMessageEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nmessage_type(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)MESSAGE_TYPE);
  }
  
  public static int nformat(long struct) {
    return UNSAFE.getInt(null, struct + (long)FORMAT);
  }
  
  public static ByteBuffer ndata_b(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)DATA_B, 20);
  }
  
  public static byte ndata_b(long struct, int index) {
    return UNSAFE.getByte(null, struct + (long)DATA_B + Checks.check(index, 20) * 1L);
  }
  
  public static ShortBuffer ndata_s(long struct) {
    return MemoryUtil.memShortBuffer(struct + (long)DATA_S, 10);
  }
  
  public static short ndata_s(long struct, int index) {
    return UNSAFE.getShort(null, struct + (long)DATA_S + Checks.check(index, 10) * 2L);
  }
  
  public static CLongBuffer ndata_l(long struct) {
    return MemoryUtil.memCLongBuffer(struct + (long)DATA_L, 5);
  }
  
  public static long ndata_l(long struct, int index) {
    return MemoryUtil.memGetCLong(struct + (long)DATA_L + Checks.check(index, 5) * (long)CLONG_SIZE);
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
  
  public static void nmessage_type(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)MESSAGE_TYPE, value);
  }
  
  public static void nformat(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)FORMAT, value);
  }
  
  public static void ndata_b(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 20); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)DATA_B, (long)(value.remaining() * 1));
  }
  
  public static void ndata_b(long struct, int index, byte value) {
    UNSAFE.putByte(null, struct + (long)DATA_B + Checks.check(index, 20) * 1L, value);
  }
  
  public static void ndata_s(long struct, ShortBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 10); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)DATA_S, (long)(value.remaining() * 2));
  }
  
  public static void ndata_s(long struct, int index, short value) {
    UNSAFE.putShort(null, struct + (long)DATA_S + Checks.check(index, 10) * 2L, value);
  }
  
  public static void ndata_l(long struct, CLongBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 5); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)DATA_L, (long)(value.remaining() * CLONG_SIZE));
  }
  
  public static void ndata_l(long struct, int index, long value) {
    MemoryUtil.memPutCLong(struct + (long)DATA_L + Checks.check(index, 5) * (long)CLONG_SIZE, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XClientMessageEvent, Buffer> implements NativeResource {
    private static final XClientMessageEvent ELEMENT_FACTORY = XClientMessageEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XClientMessageEvent.SIZEOF);
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
    
    protected XClientMessageEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XClientMessageEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XClientMessageEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XClientMessageEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XClientMessageEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XClientMessageEvent.nwindow(address());
    }
    
    @NativeType("Atom")
    public long message_type() {
      return XClientMessageEvent.nmessage_type(address());
    }
    
    public int format() {
      return XClientMessageEvent.nformat(address());
    }
    
    @NativeType("char[20]")
    public ByteBuffer data_b() {
      return XClientMessageEvent.ndata_b(address());
    }
    
    @NativeType("char")
    public byte data_b(int index) {
      return XClientMessageEvent.ndata_b(address(), index);
    }
    
    @NativeType("short[10]")
    public ShortBuffer data_s() {
      return XClientMessageEvent.ndata_s(address());
    }
    
    public short data_s(int index) {
      return XClientMessageEvent.ndata_s(address(), index);
    }
    
    @NativeType("long[5]")
    public CLongBuffer data_l() {
      return XClientMessageEvent.ndata_l(address());
    }
    
    public long data_l(int index) {
      return XClientMessageEvent.ndata_l(address(), index);
    }
    
    public Buffer type(int value) {
      XClientMessageEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XClientMessageEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XClientMessageEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XClientMessageEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XClientMessageEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer message_type(@NativeType("Atom") long value) {
      XClientMessageEvent.nmessage_type(address(), value);
      return this;
    }
    
    public Buffer format(int value) {
      XClientMessageEvent.nformat(address(), value);
      return this;
    }
    
    public Buffer data_b(@NativeType("char[20]") ByteBuffer value) {
      XClientMessageEvent.ndata_b(address(), value);
      return this;
    }
    
    public Buffer data_b(int index, @NativeType("char") byte value) {
      XClientMessageEvent.ndata_b(address(), index, value);
      return this;
    }
    
    public Buffer data_s(@NativeType("short[10]") ShortBuffer value) {
      XClientMessageEvent.ndata_s(address(), value);
      return this;
    }
    
    public Buffer data_s(int index, short value) {
      XClientMessageEvent.ndata_s(address(), index, value);
      return this;
    }
    
    public Buffer data_l(@NativeType("long[5]") CLongBuffer value) {
      XClientMessageEvent.ndata_l(address(), value);
      return this;
    }
    
    public Buffer data_l(int index, long value) {
      XClientMessageEvent.ndata_l(address(), index, value);
      return this;
    }
  }
}
