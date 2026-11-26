package org.lwjgl.system.macosx;

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

public class CGEventTapInformation extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int EVENTTAPID;
  
  public static final int TAPPOINT;
  
  public static final int OPTIONS;
  
  public static final int EVENTSOFINTEREST;
  
  public static final int TAPPINGPROCESS;
  
  public static final int PROCESSBEINGTAPPED;
  
  public static final int ENABLED;
  
  public static final int MINUSECLATENCY;
  
  public static final int AVGUSECLATENCY;
  
  public static final int MAXUSECLATENCY;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(4), 
          __member(4), 
          __member(4), 
          __member(8), 
          __member(POINTER_SIZE), 
          __member(POINTER_SIZE), 
          __member(1), 
          __member(4), 
          __member(4), 
          __member(4) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    EVENTTAPID = layout.offsetof(0);
    TAPPOINT = layout.offsetof(1);
    OPTIONS = layout.offsetof(2);
    EVENTSOFINTEREST = layout.offsetof(3);
    TAPPINGPROCESS = layout.offsetof(4);
    PROCESSBEINGTAPPED = layout.offsetof(5);
    ENABLED = layout.offsetof(6);
    MINUSECLATENCY = layout.offsetof(7);
    AVGUSECLATENCY = layout.offsetof(8);
    MAXUSECLATENCY = layout.offsetof(9);
  }
  
  public CGEventTapInformation(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("uint32_t")
  public int eventTapID() {
    return neventTapID(address());
  }
  
  @NativeType("CGEventTapLocation")
  public int tapPoint() {
    return ntapPoint(address());
  }
  
  @NativeType("CGEventTapOptions")
  public int options() {
    return noptions(address());
  }
  
  @NativeType("CGEventMask")
  public long eventsOfInterest() {
    return neventsOfInterest(address());
  }
  
  @NativeType("pid_t")
  public long tappingProcess() {
    return ntappingProcess(address());
  }
  
  @NativeType("pid_t")
  public long processBeingTapped() {
    return nprocessBeingTapped(address());
  }
  
  @NativeType("bool")
  public boolean enabled() {
    return nenabled(address());
  }
  
  public float minUsecLatency() {
    return nminUsecLatency(address());
  }
  
  public float avgUsecLatency() {
    return navgUsecLatency(address());
  }
  
  public float maxUsecLatency() {
    return nmaxUsecLatency(address());
  }
  
  public static CGEventTapInformation malloc() {
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static CGEventTapInformation calloc() {
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static CGEventTapInformation create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, MemoryUtil.memAddress(container), container);
  }
  
  public static CGEventTapInformation create(long address) {
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, address);
  }
  
  @Nullable
  public static CGEventTapInformation createSafe(long address) {
    return (address == 0L) ? null : CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : CGEventTapInformation.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static CGEventTapInformation mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static CGEventTapInformation callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static CGEventTapInformation mallocStack(MemoryStack stack) {
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static CGEventTapInformation callocStack(MemoryStack stack) {
    return CGEventTapInformation.<CGEventTapInformation>wrap(CGEventTapInformation.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return CGEventTapInformation.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int neventTapID(long struct) {
    return UNSAFE.getInt(null, struct + (long)EVENTTAPID);
  }
  
  public static int ntapPoint(long struct) {
    return UNSAFE.getInt(null, struct + (long)TAPPOINT);
  }
  
  public static int noptions(long struct) {
    return UNSAFE.getInt(null, struct + (long)OPTIONS);
  }
  
  public static long neventsOfInterest(long struct) {
    return UNSAFE.getLong(null, struct + (long)EVENTSOFINTEREST);
  }
  
  public static long ntappingProcess(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)TAPPINGPROCESS);
  }
  
  public static long nprocessBeingTapped(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)PROCESSBEINGTAPPED);
  }
  
  public static boolean nenabled(long struct) {
    return (UNSAFE.getByte(null, struct + (long)ENABLED) != 0);
  }
  
  public static float nminUsecLatency(long struct) {
    return UNSAFE.getFloat(null, struct + (long)MINUSECLATENCY);
  }
  
  public static float navgUsecLatency(long struct) {
    return UNSAFE.getFloat(null, struct + (long)AVGUSECLATENCY);
  }
  
  public static float nmaxUsecLatency(long struct) {
    return UNSAFE.getFloat(null, struct + (long)MAXUSECLATENCY);
  }
  
  public static class Buffer extends StructBuffer<CGEventTapInformation, Buffer> implements NativeResource {
    private static final CGEventTapInformation ELEMENT_FACTORY = CGEventTapInformation.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / CGEventTapInformation.SIZEOF);
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
    
    protected CGEventTapInformation getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("uint32_t")
    public int eventTapID() {
      return CGEventTapInformation.neventTapID(address());
    }
    
    @NativeType("CGEventTapLocation")
    public int tapPoint() {
      return CGEventTapInformation.ntapPoint(address());
    }
    
    @NativeType("CGEventTapOptions")
    public int options() {
      return CGEventTapInformation.noptions(address());
    }
    
    @NativeType("CGEventMask")
    public long eventsOfInterest() {
      return CGEventTapInformation.neventsOfInterest(address());
    }
    
    @NativeType("pid_t")
    public long tappingProcess() {
      return CGEventTapInformation.ntappingProcess(address());
    }
    
    @NativeType("pid_t")
    public long processBeingTapped() {
      return CGEventTapInformation.nprocessBeingTapped(address());
    }
    
    @NativeType("bool")
    public boolean enabled() {
      return CGEventTapInformation.nenabled(address());
    }
    
    public float minUsecLatency() {
      return CGEventTapInformation.nminUsecLatency(address());
    }
    
    public float avgUsecLatency() {
      return CGEventTapInformation.navgUsecLatency(address());
    }
    
    public float maxUsecLatency() {
      return CGEventTapInformation.nmaxUsecLatency(address());
    }
  }
}
