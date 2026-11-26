package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct GLFWvidmode")
public class GLFWVidMode extends Struct {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int WIDTH;
  
  public static final int HEIGHT;
  
  public static final int REDBITS;
  
  public static final int GREENBITS;
  
  public static final int BLUEBITS;
  
  public static final int REFRESHRATE;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    WIDTH = layout.offsetof(0);
    HEIGHT = layout.offsetof(1);
    REDBITS = layout.offsetof(2);
    GREENBITS = layout.offsetof(3);
    BLUEBITS = layout.offsetof(4);
    REFRESHRATE = layout.offsetof(5);
  }
  
  public GLFWVidMode(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  public int width() {
    return nwidth(address());
  }
  
  public int height() {
    return nheight(address());
  }
  
  public int redBits() {
    return nredBits(address());
  }
  
  public int greenBits() {
    return ngreenBits(address());
  }
  
  public int blueBits() {
    return nblueBits(address());
  }
  
  public int refreshRate() {
    return nrefreshRate(address());
  }
  
  public static GLFWVidMode create(long address) {
    return GLFWVidMode.<GLFWVidMode>wrap(GLFWVidMode.class, address);
  }
  
  @Nullable
  public static GLFWVidMode createSafe(long address) {
    return (address == 0L) ? null : GLFWVidMode.<GLFWVidMode>wrap(GLFWVidMode.class, address);
  }
  
  public static Buffer create(long address, int capacity) {
    return GLFWVidMode.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : GLFWVidMode.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static int nwidth(long struct) {
    return UNSAFE.getInt(null, struct + (long)WIDTH);
  }
  
  public static int nheight(long struct) {
    return UNSAFE.getInt(null, struct + (long)HEIGHT);
  }
  
  public static int nredBits(long struct) {
    return UNSAFE.getInt(null, struct + (long)REDBITS);
  }
  
  public static int ngreenBits(long struct) {
    return UNSAFE.getInt(null, struct + (long)GREENBITS);
  }
  
  public static int nblueBits(long struct) {
    return UNSAFE.getInt(null, struct + (long)BLUEBITS);
  }
  
  public static int nrefreshRate(long struct) {
    return UNSAFE.getInt(null, struct + (long)REFRESHRATE);
  }
  
  public static class Buffer extends StructBuffer<GLFWVidMode, Buffer> {
    private static final GLFWVidMode ELEMENT_FACTORY = GLFWVidMode.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / GLFWVidMode.SIZEOF);
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
    
    protected GLFWVidMode getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int width() {
      return GLFWVidMode.nwidth(address());
    }
    
    public int height() {
      return GLFWVidMode.nheight(address());
    }
    
    public int redBits() {
      return GLFWVidMode.nredBits(address());
    }
    
    public int greenBits() {
      return GLFWVidMode.ngreenBits(address());
    }
    
    public int blueBits() {
      return GLFWVidMode.nblueBits(address());
    }
    
    public int refreshRate() {
      return GLFWVidMode.nrefreshRate(address());
    }
  }
}
