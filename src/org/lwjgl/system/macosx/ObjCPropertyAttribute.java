package org.lwjgl.system.macosx;

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

@NativeType("struct objc_property_attribute_t")
public class ObjCPropertyAttribute extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int NAME;
  
  public static final int VALUE;
  
  static {
    Struct.Layout layout = __struct(
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    NAME = layout.offsetof(0);
    VALUE = layout.offsetof(1);
  }
  
  public ObjCPropertyAttribute(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("char *")
  public ByteBuffer name() {
    return nname(address());
  }
  
  @NativeType("char *")
  public String nameString() {
    return nnameString(address());
  }
  
  @NativeType("char *")
  public ByteBuffer value() {
    return nvalue(address());
  }
  
  @NativeType("char *")
  public String valueString() {
    return nvalueString(address());
  }
  
  public ObjCPropertyAttribute name(@NativeType("char *") ByteBuffer value) {
    nname(address(), value);
    return this;
  }
  
  public ObjCPropertyAttribute value(@NativeType("char *") ByteBuffer value) {
    nvalue(address(), value);
    return this;
  }
  
  public ObjCPropertyAttribute set(ByteBuffer name, ByteBuffer value) {
    name(name);
    value(value);
    return this;
  }
  
  public ObjCPropertyAttribute set(ObjCPropertyAttribute src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static ObjCPropertyAttribute malloc() {
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static ObjCPropertyAttribute calloc() {
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static ObjCPropertyAttribute create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, MemoryUtil.memAddress(container), container);
  }
  
  public static ObjCPropertyAttribute create(long address) {
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, address);
  }
  
  @Nullable
  public static ObjCPropertyAttribute createSafe(long address) {
    return (address == 0L) ? null : ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static ObjCPropertyAttribute mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static ObjCPropertyAttribute callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static ObjCPropertyAttribute mallocStack(MemoryStack stack) {
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static ObjCPropertyAttribute callocStack(MemoryStack stack) {
    return ObjCPropertyAttribute.<ObjCPropertyAttribute>wrap(ObjCPropertyAttribute.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return ObjCPropertyAttribute.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static ByteBuffer nname(long struct) {
    return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(struct + (long)NAME));
  }
  
  public static String nnameString(long struct) {
    return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(struct + (long)NAME));
  }
  
  public static ByteBuffer nvalue(long struct) {
    return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(struct + (long)VALUE));
  }
  
  public static String nvalueString(long struct) {
    return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(struct + (long)VALUE));
  }
  
  public static void nname(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkNT1(value); 
    MemoryUtil.memPutAddress(struct + (long)NAME, MemoryUtil.memAddress(value));
  }
  
  public static void nvalue(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkNT1(value); 
    MemoryUtil.memPutAddress(struct + (long)VALUE, MemoryUtil.memAddress(value));
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)NAME));
    Checks.check(MemoryUtil.memGetAddress(struct + (long)VALUE));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<ObjCPropertyAttribute, Buffer> implements NativeResource {
    private static final ObjCPropertyAttribute ELEMENT_FACTORY = ObjCPropertyAttribute.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / ObjCPropertyAttribute.SIZEOF);
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
    
    protected ObjCPropertyAttribute getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("char *")
    public ByteBuffer name() {
      return ObjCPropertyAttribute.nname(address());
    }
    
    @NativeType("char *")
    public String nameString() {
      return ObjCPropertyAttribute.nnameString(address());
    }
    
    @NativeType("char *")
    public ByteBuffer value() {
      return ObjCPropertyAttribute.nvalue(address());
    }
    
    @NativeType("char *")
    public String valueString() {
      return ObjCPropertyAttribute.nvalueString(address());
    }
    
    public Buffer name(@NativeType("char *") ByteBuffer value) {
      ObjCPropertyAttribute.nname(address(), value);
      return this;
    }
    
    public Buffer value(@NativeType("char *") ByteBuffer value) {
      ObjCPropertyAttribute.nvalue(address(), value);
      return this;
    }
  }
}
