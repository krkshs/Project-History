package org.lwjgl.system;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Struct extends Pointer.Default {
  protected static final int DEFAULT_PACK_ALIGNMENT = (Platform.get() == Platform.WINDOWS) ? 8 : 1073741824;
  
  protected static final int DEFAULT_ALIGN_AS = 0;
  
  private static final long CONTAINER;
  
  @Nullable
  private ByteBuffer container;
  
  static {
    Library.initialize();
    try {
      CONTAINER = UNSAFE.objectFieldOffset(Struct.class.getDeclaredField("container"));
    } catch (Throwable t) {
      throw new UnsupportedOperationException(t);
    } 
  }
  
  protected Struct(long address, @Nullable ByteBuffer container) {
    super(address);
    this.container = container;
  }
  
  public void clear() {
    MemoryUtil.memSet(address(), 0, (long)sizeof());
  }
  
  public void free() {
    MemoryUtil.nmemFree(address());
  }
  
  public boolean isNull(int memberOffset) {
    if (Checks.DEBUG)
      checkMemberOffset(memberOffset); 
    return (MemoryUtil.memGetAddress(address() + (long)memberOffset) == 0L);
  }
  
  protected static <T extends Struct> T wrap(Class<T> clazz, long address) {
    T struct;
    try {
      struct = (T)UNSAFE.allocateInstance(clazz);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(struct, ADDRESS, address);
    return struct;
  }
  
  protected static <T extends Struct> T wrap(Class<T> clazz, long address, ByteBuffer container) {
    T struct;
    try {
      struct = (T)UNSAFE.allocateInstance(clazz);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(struct, ADDRESS, address);
    UNSAFE.putObject(struct, CONTAINER, container);
    return struct;
  }
  
  <T extends Struct> T wrap(long address, int index, @Nullable ByteBuffer container) {
    T struct;
    try {
      struct = (T)UNSAFE.allocateInstance(getClass());
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(struct, ADDRESS, address + Integer.toUnsignedLong(index) * (long)sizeof());
    UNSAFE.putObject(struct, CONTAINER, container);
    return struct;
  }
  
  private void checkMemberOffset(int memberOffset) {
    if (memberOffset < 0 || sizeof() - memberOffset < POINTER_SIZE)
      throw new IllegalArgumentException("Invalid member offset."); 
  }
  
  protected static ByteBuffer __checkContainer(ByteBuffer container, int sizeof) {
    if (Checks.CHECKS)
      Checks.check((Buffer)container, sizeof); 
    return container;
  }
  
  private static long getBytes(int elements, int elementSize) {
    return ((long)elements & 0xFFFFFFFFL) * (long)elementSize;
  }
  
  protected static long __checkMalloc(int elements, int elementSize) {
    long bytes = ((long)elements & 0xFFFFFFFFL) * (long)elementSize;
    if (Checks.DEBUG) {
      if (elements < 0)
        throw new IllegalArgumentException("Invalid number of elements"); 
      if (BITS32 && 4294967295L < bytes)
        throw new IllegalArgumentException("The request allocation is too large"); 
    } 
    return bytes;
  }
  
  protected static ByteBuffer __create(int elements, int elementSize) {
    APIUtil.apiCheckAllocation(elements, getBytes(elements, elementSize), Integer.MAX_VALUE);
    return ByteBuffer.allocateDirect(elements * elementSize).order(ByteOrder.nativeOrder());
  }
  
  protected static class Member {
    final int size;
    
    final int alignment;
    
    final boolean forcedAlignment;
    
    int offset;
    
    Member(int size, int alignment, boolean forcedAlignment) {
      this.size = size;
      this.alignment = alignment;
      this.forcedAlignment = forcedAlignment;
    }
    
    public int getSize() {
      return this.size;
    }
    
    public int getAlignment() {
      return this.alignment;
    }
    
    public int getAlignment(int packAlignment) {
      return this.forcedAlignment ? this.alignment : Math.min(this.alignment, packAlignment);
    }
  }
  
  protected static class Layout extends Member {
    final Struct.Member[] members;
    
    Layout(int size, int alignment, boolean forceAlignment, Struct.Member[] members) {
      super(size, alignment, forceAlignment);
      this.members = members;
    }
    
    public int offsetof(int member) {
      return (this.members[member]).offset;
    }
  }
  
  protected static Member __padding(int size, boolean condition) {
    return __member(condition ? size : 0, 1);
  }
  
  protected static Member __member(int size) {
    return __member(size, size);
  }
  
  protected static Member __member(int size, int alignment) {
    return __member(size, alignment, false);
  }
  
  protected static Member __member(int size, int alignment, boolean forceAlignment) {
    return new Member(size, alignment, forceAlignment);
  }
  
  protected static Member __array(int size, int length) {
    return __array(size, size, length);
  }
  
  protected static Member __array(int size, int alignment, int length) {
    return new Member(size * length, alignment, false);
  }
  
  protected static Member __array(int size, int alignment, boolean forceAlignment, int length) {
    return new Member(size * length, alignment, forceAlignment);
  }
  
  protected static Layout __union(Member... members) {
    return __union(DEFAULT_PACK_ALIGNMENT, 0, members);
  }
  
  protected static Layout __union(int packAlignment, int alignas, Member... members) {
    List<Member> union = new ArrayList<>(members.length);
    int size = 0;
    int alignment = alignas;
    for (Member m : members) {
      size = Math.max(size, m.size);
      alignment = Math.max(alignment, m.getAlignment(packAlignment));
      m.offset = 0;
      union.add(m);
      if (m instanceof Layout)
        addNestedMembers(m, union, 0); 
    } 
    return new Layout(size, alignment, (alignas != 0), union.<Member>toArray(new Member[0]));
  }
  
  protected static Layout __struct(Member... members) {
    return __struct(DEFAULT_PACK_ALIGNMENT, 0, members);
  }
  
  protected static Layout __struct(int packAlignment, int alignas, Member... members) {
    List<Member> struct = new ArrayList<>(members.length);
    int size = 0;
    int alignment = alignas;
    for (Member m : members) {
      int memberAlignment = m.getAlignment(packAlignment);
      m.offset = align(size, memberAlignment);
      size = m.offset + m.size;
      alignment = Math.max(alignment, memberAlignment);
      struct.add(m);
      if (m instanceof Layout)
        addNestedMembers(m, struct, m.offset); 
    } 
    size = align(size, alignment);
    return new Layout(size, alignment, (alignas != 0), struct.<Member>toArray(new Member[0]));
  }
  
  private static void addNestedMembers(Member nested, List<Member> members, int offset) {
    Layout layout = (Layout)nested;
    for (Member m : layout.members) {
      m.offset += offset;
      members.add(m);
    } 
  }
  
  private static int align(int offset, int alignment) {
    return (offset - 1 | alignment - 1) + 1;
  }
  
  public abstract int sizeof();
}
