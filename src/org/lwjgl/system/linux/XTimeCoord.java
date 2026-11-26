package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XTimeCoord extends Struct {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TIME;
  
  public static final int X;
  
  public static final int Y;
  
  static {
    Struct.Layout layout = __struct(
        __member(CLONG_SIZE), 
        __member(2), 
        __member(2));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TIME = layout.offsetof(0);
    X = layout.offsetof(1);
    Y = layout.offsetof(2);
  }
  
  public XTimeCoord(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("Time")
  public long time() {
    return ntime(address());
  }
  
  public short x() {
    return nx(address());
  }
  
  public short y() {
    return ny(address());
  }
  
  public static XTimeCoord create(long address) {
    return XTimeCoord.<XTimeCoord>wrap(XTimeCoord.class, address);
  }
  
  @Nullable
  public static XTimeCoord createSafe(long address) {
    return (address == 0L) ? null : XTimeCoord.<XTimeCoord>wrap(XTimeCoord.class, address);
  }
  
  public static Buffer create(long address, int capacity) {
    return XTimeCoord.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XTimeCoord.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static long ntime(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)TIME);
  }
  
  public static short nx(long struct) {
    return UNSAFE.getShort(null, struct + (long)X);
  }
  
  public static short ny(long struct) {
    return UNSAFE.getShort(null, struct + (long)Y);
  }
  
  public static class Buffer extends StructBuffer<XTimeCoord, Buffer> {
    private static final XTimeCoord ELEMENT_FACTORY = XTimeCoord.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XTimeCoord.SIZEOF);
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
    
    protected XTimeCoord getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("Time")
    public long time() {
      return XTimeCoord.ntime(address());
    }
    
    public short x() {
      return XTimeCoord.nx(address());
    }
    
    public short y() {
      return XTimeCoord.ny(address());
    }
  }
}
