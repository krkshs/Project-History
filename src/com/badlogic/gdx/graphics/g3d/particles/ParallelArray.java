package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ArrayReflection;

public class ParallelArray {
  Array<Channel> arrays;
  
  public int capacity;
  
  public int size;
  
  public static class ChannelDescriptor {
    public int id;
    
    public Class<?> type;
    
    public int count;
    
    public ChannelDescriptor(int id, Class<?> type, int count) {
      this.id = id;
      this.type = type;
      this.count = count;
    }
  }
  
  public abstract class Channel {
    public int id;
    
    public Object data;
    
    public int strideSize;
    
    public Channel(int id, Object data, int strideSize) {
      this.id = id;
      this.strideSize = strideSize;
      this.data = data;
    }
    
    public abstract void add(int param1Int, Object... param1VarArgs);
    
    public abstract void swap(int param1Int1, int param1Int2);
    
    protected abstract void setCapacity(int param1Int);
  }
  
  public static interface ChannelInitializer<T extends Channel> {
    void init(T param1T);
  }
  
  public class FloatChannel extends Channel {
    public float[] data;
    
    public FloatChannel(int id, int strideSize, int size) {
      super(id, new float[size * strideSize], strideSize);
      this.data = (float[])super.data;
    }
    
    public void add(int index, Object... objects) {
      for (int i = this.strideSize * ParallelArray.this.size, c = i + this.strideSize, k = 0; i < c; i++, k++)
        this.data[i] = (Float)objects[k]; 
    }
    
    public void swap(int i, int k) {
      i = this.strideSize * i;
      k = this.strideSize * k;
      for (int c = i + this.strideSize; i < c; i++, k++) {
        float t = this.data[i];
        this.data[i] = this.data[k];
        this.data[k] = t;
      } 
    }
    
    public void setCapacity(int requiredCapacity) {
      float[] newData = new float[this.strideSize * requiredCapacity];
      System.arraycopy(this.data, 0, newData, 0, Math.min(this.data.length, newData.length));
      this.data = newData;
    }
  }
  
  public class IntChannel extends Channel {
    public int[] data;
    
    public IntChannel(int id, int strideSize, int size) {
      super(id, new int[size * strideSize], strideSize);
      this.data = (int[])super.data;
    }
    
    public void add(int index, Object... objects) {
      for (int i = this.strideSize * ParallelArray.this.size, c = i + this.strideSize, k = 0; i < c; i++, k++)
        this.data[i] = (Integer)objects[k]; 
    }
    
    public void swap(int i, int k) {
      i = this.strideSize * i;
      k = this.strideSize * k;
      for (int c = i + this.strideSize; i < c; i++, k++) {
        int t = this.data[i];
        this.data[i] = this.data[k];
        this.data[k] = t;
      } 
    }
    
    public void setCapacity(int requiredCapacity) {
      int[] newData = new int[this.strideSize * requiredCapacity];
      System.arraycopy(this.data, 0, newData, 0, Math.min(this.data.length, newData.length));
      this.data = newData;
    }
  }
  
  public class ObjectChannel<T> extends Channel {
    Class<T> componentType;
    
    public T[] data;
    
    public ObjectChannel(int id, int strideSize, int size, Class<T> type) {
      super(id, ArrayReflection.newInstance(type, size * strideSize), strideSize);
      this.componentType = type;
      this.data = (T[])super.data;
    }
    
    public void add(int index, Object... objects) {
      for (int i = this.strideSize * ParallelArray.this.size, c = i + this.strideSize, k = 0; i < c; i++, k++)
        this.data[i] = (T)objects[k]; 
    }
    
    public void swap(int i, int k) {
      i = this.strideSize * i;
      k = this.strideSize * k;
      for (int c = i + this.strideSize; i < c; i++, k++) {
        T t = this.data[i];
        this.data[i] = this.data[k];
        this.data[k] = t;
      } 
    }
    
    public void setCapacity(int requiredCapacity) {
      T[] newData = (T[])ArrayReflection.newInstance(this.componentType, this.strideSize * requiredCapacity);
      System.arraycopy(this.data, 0, newData, 0, Math.min(this.data.length, newData.length));
      this.data = newData;
    }
  }
  
  public ParallelArray(int capacity) {
    this.arrays = new Array<>(false, 2, Channel.class);
    this.capacity = capacity;
    this.size = 0;
  }
  
  public <T extends Channel> T addChannel(ChannelDescriptor channelDescriptor) {
    return addChannel(channelDescriptor, null);
  }
  
  public <T extends Channel> T addChannel(ChannelDescriptor channelDescriptor, ChannelInitializer<T> initializer) {
    T channel = getChannel(channelDescriptor);
    if (channel == null) {
      channel = allocateChannel(channelDescriptor);
      if (initializer != null)
        initializer.init(channel); 
      this.arrays.add(channel);
    } 
    return channel;
  }
  
  private <T extends Channel> T allocateChannel(ChannelDescriptor channelDescriptor) {
    if (channelDescriptor.type == float.class)
      return (T)new FloatChannel(channelDescriptor.id, channelDescriptor.count, this.capacity); 
    if (channelDescriptor.type == int.class)
      return (T)new IntChannel(channelDescriptor.id, channelDescriptor.count, this.capacity); 
    return (T)new ObjectChannel(channelDescriptor.id, channelDescriptor.count, this.capacity, channelDescriptor.type);
  }
  
  public <T> void removeArray(int id) {
    this.arrays.removeIndex(findIndex(id));
  }
  
  private int findIndex(int id) {
    for (int i = 0; i < this.arrays.size; i++) {
      Channel array = ((Channel[])this.arrays.items)[i];
      if (array.id == id)
        return i; 
    } 
    return -1;
  }
  
  public void addElement(Object... values) {
    if (this.size == this.capacity)
      throw new GdxRuntimeException("Capacity reached, cannot add other elements"); 
    int k = 0;
    for (Array.ArrayIterator<Channel> arrayIterator = this.arrays.iterator(); arrayIterator.hasNext(); ) {
      Channel strideArray = arrayIterator.next();
      strideArray.add(k, values);
      k += strideArray.strideSize;
    } 
    this.size++;
  }
  
  public void removeElement(int index) {
    int last = this.size - 1;
    for (Array.ArrayIterator<Channel> arrayIterator = this.arrays.iterator(); arrayIterator.hasNext(); ) {
      Channel strideArray = arrayIterator.next();
      strideArray.swap(index, last);
    } 
    this.size = last;
  }
  
  public <T extends Channel> T getChannel(ChannelDescriptor descriptor) {
    for (Array.ArrayIterator<Channel> arrayIterator = this.arrays.iterator(); arrayIterator.hasNext(); ) {
      Channel array = arrayIterator.next();
      if (array.id == descriptor.id)
        return (T)array; 
    } 
    return null;
  }
  
  public void clear() {
    this.arrays.clear();
    this.size = 0;
  }
  
  public void setCapacity(int requiredCapacity) {
    if (this.capacity != requiredCapacity) {
      for (Array.ArrayIterator<Channel> arrayIterator = this.arrays.iterator(); arrayIterator.hasNext(); ) {
        Channel channel = arrayIterator.next();
        channel.setCapacity(requiredCapacity);
      } 
      this.capacity = requiredCapacity;
    } 
  }
}
