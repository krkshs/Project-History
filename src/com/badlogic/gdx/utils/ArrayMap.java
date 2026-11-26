package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayMap<K, V> implements Iterable<ObjectMap.Entry<K, V>> {
  public K[] keys;
  
  public V[] values;
  
  public int size;
  
  public boolean ordered;
  
  private transient Entries entries1;
  
  private transient Entries entries2;
  
  private transient Values values1;
  
  private transient Values values2;
  
  private transient Keys keys1;
  
  private transient Keys keys2;
  
  public ArrayMap() {
    this(true, 16);
  }
  
  public ArrayMap(int capacity) {
    this(true, capacity);
  }
  
  public ArrayMap(boolean ordered, int capacity) {
    this.ordered = ordered;
    this.keys = (K[])new Object[capacity];
    this.values = (V[])new Object[capacity];
  }
  
  public ArrayMap(boolean ordered, int capacity, Class keyArrayType, Class valueArrayType) {
    this.ordered = ordered;
    this.keys = (K[])ArrayReflection.newInstance(keyArrayType, capacity);
    this.values = (V[])ArrayReflection.newInstance(valueArrayType, capacity);
  }
  
  public ArrayMap(Class keyArrayType, Class valueArrayType) {
    this(false, 16, keyArrayType, valueArrayType);
  }
  
  public ArrayMap(ArrayMap array) {
    this(array.ordered, array.size, array.keys.getClass().getComponentType(), array.values.getClass().getComponentType());
    this.size = array.size;
    System.arraycopy(array.keys, 0, this.keys, 0, this.size);
    System.arraycopy(array.values, 0, this.values, 0, this.size);
  }
  
  public int put(K key, V value) {
    int index = indexOfKey(key);
    if (index == -1) {
      if (this.size == this.keys.length)
        resize(Math.max(8, (int)((float)this.size * 1.75F))); 
      index = this.size++;
    } 
    this.keys[index] = key;
    this.values[index] = value;
    return index;
  }
  
  public int put(K key, V value, int index) {
    int existingIndex = indexOfKey(key);
    if (existingIndex != -1) {
      removeIndex(existingIndex);
    } else if (this.size == this.keys.length) {
      resize(Math.max(8, (int)((float)this.size * 1.75F)));
    } 
    System.arraycopy(this.keys, index, this.keys, index + 1, this.size - index);
    System.arraycopy(this.values, index, this.values, index + 1, this.size - index);
    this.keys[index] = key;
    this.values[index] = value;
    this.size++;
    return index;
  }
  
  public void putAll(ArrayMap<? extends K, ? extends V> map) {
    putAll(map, 0, map.size);
  }
  
  public void putAll(ArrayMap<? extends K, ? extends V> map, int offset, int length) {
    if (offset + length > map.size)
      throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + map.size); 
    int sizeNeeded = this.size + length - offset;
    if (sizeNeeded >= this.keys.length)
      resize(Math.max(8, (int)((float)sizeNeeded * 1.75F))); 
    System.arraycopy(map.keys, offset, this.keys, this.size, length);
    System.arraycopy(map.values, offset, this.values, this.size, length);
    this.size += length;
  }
  
  @Null
  public V get(K key) {
    return get(key, null);
  }
  
  @Null
  public V get(K key, @Null V defaultValue) {
    Object[] keys = this.keys;
    int i = this.size - 1;
    if (key == null) {
      for (; i >= 0; i--) {
        if (keys[i] == key)
          return this.values[i]; 
      } 
    } else
      for (; i >= 0; i--) {
        if (key.equals(keys[i]))
          return this.values[i]; 
      }  
    return defaultValue;
  }
  
  @Null
  public K getKey(V value, boolean identity) {
    Object[] values = this.values;
    int i = this.size - 1;
    if (identity || value == null) {
      for (; i >= 0; i--) {
        if (values[i] == value)
          return this.keys[i]; 
      } 
    } else
      for (; i >= 0; i--) {
        if (value.equals(values[i]))
          return this.keys[i]; 
      }  
    return null;
  }
  
  public K getKeyAt(int index) {
    if (index >= this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    return this.keys[index];
  }
  
  public V getValueAt(int index) {
    if (index >= this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    return this.values[index];
  }
  
  public K firstKey() {
    if (this.size == 0)
      throw new IllegalStateException("Map is empty."); 
    return this.keys[0];
  }
  
  public V firstValue() {
    if (this.size == 0)
      throw new IllegalStateException("Map is empty."); 
    return this.values[0];
  }
  
  public void setKey(int index, K key) {
    if (index >= this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    this.keys[index] = key;
  }
  
  public void setValue(int index, V value) {
    if (index >= this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    this.values[index] = value;
  }
  
  public void insert(int index, K key, V value) {
    if (index > this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    if (this.size == this.keys.length)
      resize(Math.max(8, (int)((float)this.size * 1.75F))); 
    if (this.ordered) {
      System.arraycopy(this.keys, index, this.keys, index + 1, this.size - index);
      System.arraycopy(this.values, index, this.values, index + 1, this.size - index);
    } else {
      this.keys[this.size] = this.keys[index];
      this.values[this.size] = this.values[index];
    } 
    this.size++;
    this.keys[index] = key;
    this.values[index] = value;
  }
  
  public boolean containsKey(K key) {
    K[] keys = this.keys;
    int i = this.size - 1;
    if (key == null) {
      while (i >= 0) {
        if (keys[i--] == key)
          return true; 
      } 
    } else
      while (i >= 0) {
        if (key.equals(keys[i--]))
          return true; 
      }  
    return false;
  }
  
  public boolean containsValue(V value, boolean identity) {
    V[] values = this.values;
    int i = this.size - 1;
    if (identity || value == null) {
      while (i >= 0) {
        if (values[i--] == value)
          return true; 
      } 
    } else
      while (i >= 0) {
        if (value.equals(values[i--]))
          return true; 
      }  
    return false;
  }
  
  public int indexOfKey(K key) {
    Object[] keys = this.keys;
    if (key == null) {
      for (int i = 0, n = this.size; i < n; i++) {
        if (keys[i] == key)
          return i; 
      } 
    } else
      for (int i = 0, n = this.size; i < n; i++) {
        if (key.equals(keys[i]))
          return i; 
      }  
    return -1;
  }
  
  public int indexOfValue(V value, boolean identity) {
    Object[] values = this.values;
    if (identity || value == null) {
      for (int i = 0, n = this.size; i < n; i++) {
        if (values[i] == value)
          return i; 
      } 
    } else
      for (int i = 0, n = this.size; i < n; i++) {
        if (value.equals(values[i]))
          return i; 
      }  
    return -1;
  }
  
  @Null
  public V removeKey(K key) {
    Object[] keys = this.keys;
    if (key == null) {
      for (int i = 0, n = this.size; i < n; i++) {
        if (keys[i] == key) {
          V value = this.values[i];
          removeIndex(i);
          return value;
        } 
      } 
    } else
      for (int i = 0, n = this.size; i < n; i++) {
        if (key.equals(keys[i])) {
          V value = this.values[i];
          removeIndex(i);
          return value;
        } 
      }  
    return null;
  }
  
  public boolean removeValue(V value, boolean identity) {
    Object[] values = this.values;
    if (identity || value == null) {
      for (int i = 0, n = this.size; i < n; i++) {
        if (values[i] == value) {
          removeIndex(i);
          return true;
        } 
      } 
    } else
      for (int i = 0, n = this.size; i < n; i++) {
        if (value.equals(values[i])) {
          removeIndex(i);
          return true;
        } 
      }  
    return false;
  }
  
  public void removeIndex(int index) {
    if (index >= this.size)
      throw new IndexOutOfBoundsException(String.valueOf(index)); 
    Object[] keys = this.keys;
    this.size--;
    if (this.ordered) {
      System.arraycopy(keys, index + 1, keys, index, this.size - index);
      System.arraycopy(this.values, index + 1, this.values, index, this.size - index);
    } else {
      keys[index] = keys[this.size];
      this.values[index] = this.values[this.size];
    } 
    keys[this.size] = null;
    this.values[this.size] = null;
  }
  
  public boolean notEmpty() {
    return (this.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.size == 0);
  }
  
  public K peekKey() {
    return this.keys[this.size - 1];
  }
  
  public V peekValue() {
    return this.values[this.size - 1];
  }
  
  public void clear(int maximumCapacity) {
    if (this.keys.length <= maximumCapacity) {
      clear();
      return;
    } 
    this.size = 0;
    resize(maximumCapacity);
  }
  
  public void clear() {
    Arrays.fill(this.keys, 0, this.size, null);
    Arrays.fill(this.values, 0, this.size, null);
    this.size = 0;
  }
  
  public void shrink() {
    if (this.keys.length == this.size)
      return; 
    resize(this.size);
  }
  
  public void ensureCapacity(int additionalCapacity) {
    if (additionalCapacity < 0)
      throw new IllegalArgumentException("additionalCapacity must be >= 0: " + additionalCapacity); 
    int sizeNeeded = this.size + additionalCapacity;
    if (sizeNeeded > this.keys.length)
      resize(Math.max(Math.max(8, sizeNeeded), (int)((float)this.size * 1.75F))); 
  }
  
  protected void resize(int newSize) {
    K[] newKeys = (K[])ArrayReflection.newInstance(this.keys.getClass().getComponentType(), newSize);
    System.arraycopy(this.keys, 0, newKeys, 0, Math.min(this.size, newKeys.length));
    this.keys = newKeys;
    V[] newValues = (V[])ArrayReflection.newInstance(this.values.getClass().getComponentType(), newSize);
    System.arraycopy(this.values, 0, newValues, 0, Math.min(this.size, newValues.length));
    this.values = newValues;
  }
  
  public void reverse() {
    for (int i = 0, lastIndex = this.size - 1, n = this.size / 2; i < n; i++) {
      int ii = lastIndex - i;
      K tempKey = this.keys[i];
      this.keys[i] = this.keys[ii];
      this.keys[ii] = tempKey;
      V tempValue = this.values[i];
      this.values[i] = this.values[ii];
      this.values[ii] = tempValue;
    } 
  }
  
  public void shuffle() {
    for (int i = this.size - 1; i >= 0; i--) {
      int ii = MathUtils.random(i);
      K tempKey = this.keys[i];
      this.keys[i] = this.keys[ii];
      this.keys[ii] = tempKey;
      V tempValue = this.values[i];
      this.values[i] = this.values[ii];
      this.values[ii] = tempValue;
    } 
  }
  
  public void truncate(int newSize) {
    if (this.size <= newSize)
      return; 
    for (int i = newSize; i < this.size; i++) {
      this.keys[i] = null;
      this.values[i] = null;
    } 
    this.size = newSize;
  }
  
  public int hashCode() {
    K[] keys = this.keys;
    V[] values = this.values;
    int h = 0;
    for (int i = 0, n = this.size; i < n; i++) {
      K key = keys[i];
      V value = values[i];
      if (key != null)
        h += key.hashCode() * 31; 
      if (value != null)
        h += value.hashCode(); 
    } 
    return h;
  }
  
  public boolean equals(Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof ArrayMap))
      return false; 
    ArrayMap other = (ArrayMap)obj;
    if (other.size != this.size)
      return false; 
    K[] keys = this.keys;
    V[] values = this.values;
    for (int i = 0, n = this.size; i < n; i++) {
      K key = keys[i];
      V value = values[i];
      if (value == null) {
        if (other.get(key, (V)ObjectMap.dummy) != null)
          return false; 
      } else if (!value.equals(other.get(key))) {
        return false;
      } 
    } 
    return true;
  }
  
  public boolean equalsIdentity(Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof ArrayMap))
      return false; 
    ArrayMap other = (ArrayMap)obj;
    if (other.size != this.size)
      return false; 
    K[] keys = this.keys;
    V[] values = this.values;
    for (int i = 0, n = this.size; i < n; i++) {
      if (values[i] != other.get(keys[i], (V)ObjectMap.dummy))
        return false; 
    } 
    return true;
  }
  
  public String toString() {
    if (this.size == 0)
      return "{}"; 
    K[] keys = this.keys;
    V[] values = this.values;
    StringBuilder buffer = new StringBuilder(32);
    buffer.append('{');
    buffer.append(keys[0]);
    buffer.append('=');
    buffer.append(values[0]);
    for (int i = 1; i < this.size; i++) {
      buffer.append(", ");
      buffer.append(keys[i]);
      buffer.append('=');
      buffer.append(values[i]);
    } 
    buffer.append('}');
    return buffer.toString();
  }
  
  public Iterator<ObjectMap.Entry<K, V>> iterator() {
    return entries();
  }
  
  public Entries<K, V> entries() {
    if (Collections.allocateIterators)
      return new Entries<>(this); 
    if (this.entries1 == null) {
      this.entries1 = new Entries<>(this);
      this.entries2 = new Entries<>(this);
    } 
    if (!this.entries1.valid) {
      this.entries1.index = 0;
      this.entries1.valid = true;
      this.entries2.valid = false;
      return this.entries1;
    } 
    this.entries2.index = 0;
    this.entries2.valid = true;
    this.entries1.valid = false;
    return this.entries2;
  }
  
  public Values<V> values() {
    if (Collections.allocateIterators)
      return new Values<>((ArrayMap)this); 
    if (this.values1 == null) {
      this.values1 = new Values<>((ArrayMap)this);
      this.values2 = new Values<>((ArrayMap)this);
    } 
    if (!this.values1.valid) {
      this.values1.index = 0;
      this.values1.valid = true;
      this.values2.valid = false;
      return this.values1;
    } 
    this.values2.index = 0;
    this.values2.valid = true;
    this.values1.valid = false;
    return this.values2;
  }
  
  public Keys<K> keys() {
    if (Collections.allocateIterators)
      return new Keys<>((ArrayMap)this); 
    if (this.keys1 == null) {
      this.keys1 = new Keys<>((ArrayMap)this);
      this.keys2 = new Keys<>((ArrayMap)this);
    } 
    if (!this.keys1.valid) {
      this.keys1.index = 0;
      this.keys1.valid = true;
      this.keys2.valid = false;
      return this.keys1;
    } 
    this.keys2.index = 0;
    this.keys2.valid = true;
    this.keys1.valid = false;
    return this.keys2;
  }
  
  public static class Entries<K, V> implements Iterable<ObjectMap.Entry<K, V>>, Iterator<ObjectMap.Entry<K, V>> {
    private final ArrayMap<K, V> map;
    
    ObjectMap.Entry<K, V> entry = new ObjectMap.Entry<>();
    
    int index;
    
    boolean valid = true;
    
    public Entries(ArrayMap<K, V> map) {
      this.map = map;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return (this.index < this.map.size);
    }
    
    public Iterator<ObjectMap.Entry<K, V>> iterator() {
      return this;
    }
    
    public ObjectMap.Entry<K, V> next() {
      if (this.index >= this.map.size)
        throw new NoSuchElementException(String.valueOf(this.index)); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      this.entry.key = this.map.keys[this.index];
      this.entry.value = this.map.values[this.index++];
      return this.entry;
    }
    
    public void remove() {
      this.index--;
      this.map.removeIndex(this.index);
    }
    
    public void reset() {
      this.index = 0;
    }
  }
  
  public static class Values<V> implements Iterable<V>, Iterator<V> {
    private final ArrayMap<Object, V> map;
    
    int index;
    
    boolean valid = true;
    
    public Values(ArrayMap<Object, V> map) {
      this.map = map;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return (this.index < this.map.size);
    }
    
    public Iterator<V> iterator() {
      return this;
    }
    
    public V next() {
      if (this.index >= this.map.size)
        throw new NoSuchElementException(String.valueOf(this.index)); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.map.values[this.index++];
    }
    
    public void remove() {
      this.index--;
      this.map.removeIndex(this.index);
    }
    
    public void reset() {
      this.index = 0;
    }
    
    public Array<V> toArray() {
      return new Array<>(true, this.map.values, this.index, this.map.size - this.index);
    }
    
    public Array<V> toArray(Array array) {
      array.addAll(this.map.values, this.index, this.map.size - this.index);
      return array;
    }
  }
  
  public static class Keys<K> implements Iterable<K>, Iterator<K> {
    private final ArrayMap<K, Object> map;
    
    int index;
    
    boolean valid = true;
    
    public Keys(ArrayMap<K, Object> map) {
      this.map = map;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return (this.index < this.map.size);
    }
    
    public Iterator<K> iterator() {
      return this;
    }
    
    public K next() {
      if (this.index >= this.map.size)
        throw new NoSuchElementException(String.valueOf(this.index)); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.map.keys[this.index++];
    }
    
    public void remove() {
      this.index--;
      this.map.removeIndex(this.index);
    }
    
    public void reset() {
      this.index = 0;
    }
    
    public Array<K> toArray() {
      return new Array<>(true, this.map.keys, this.index, this.map.size - this.index);
    }
    
    public Array<K> toArray(Array array) {
      array.addAll(this.map.keys, this.index, this.map.size - this.index);
      return array;
    }
  }
}
