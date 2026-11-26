package com.badlogic.gdx.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectMap<K, V> implements Iterable<ObjectMap.Entry<K, V>> {
  static final Object dummy = new Object();
  
  public int size;
  
  K[] keyTable;
  
  V[] valueTable;
  
  float loadFactor;
  
  int threshold;
  
  protected int shift;
  
  protected int mask;
  
  transient Entries entries1;
  
  transient Entries entries2;
  
  transient Values values1;
  
  transient Values values2;
  
  transient Keys keys1;
  
  transient Keys keys2;
  
  public ObjectMap() {
    this(51, 0.8F);
  }
  
  public ObjectMap(int initialCapacity) {
    this(initialCapacity, 0.8F);
  }
  
  public ObjectMap(int initialCapacity, float loadFactor) {
    if (loadFactor <= 0.0F || loadFactor >= 1.0F)
      throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + loadFactor); 
    this.loadFactor = loadFactor;
    int tableSize = ObjectSet.tableSize(initialCapacity, loadFactor);
    this.threshold = (int)((float)tableSize * loadFactor);
    this.mask = tableSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    this.keyTable = (K[])new Object[tableSize];
    this.valueTable = (V[])new Object[tableSize];
  }
  
  public ObjectMap(ObjectMap<? extends K, ? extends V> map) {
    this((int)((float)map.keyTable.length * map.loadFactor), map.loadFactor);
    System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
    System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
    this.size = map.size;
  }
  
  protected int place(K item) {
    return (int)((long)item.hashCode() * -7046029254386353131L >>> this.shift);
  }
  
  int locateKey(K key) {
    if (key == null)
      throw new IllegalArgumentException("key cannot be null."); 
    K[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      K other = keyTable[i];
      if (other == null)
        return -(i + 1); 
      if (other.equals(key))
        return i; 
    } 
  }
  
  @Null
  public V put(K key, @Null V value) {
    int i = locateKey(key);
    if (i >= 0) {
      V oldValue = this.valueTable[i];
      this.valueTable[i] = value;
      return oldValue;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = value;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return null;
  }
  
  public void putAll(ObjectMap<? extends K, ? extends V> map) {
    ensureCapacity(map.size);
    K[] keyTable = map.keyTable;
    V[] valueTable = map.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null)
        put(key, valueTable[i]); 
    } 
  }
  
  private void putResize(K key, @Null V value) {
    K[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      if (keyTable[i] == null) {
        keyTable[i] = key;
        this.valueTable[i] = value;
        return;
      } 
    } 
  }
  
  @Null
  public <T extends K> V get(T key) {
    int i = locateKey((K)key);
    return (i < 0) ? null : this.valueTable[i];
  }
  
  public V get(K key, @Null V defaultValue) {
    int i = locateKey(key);
    return (i < 0) ? defaultValue : this.valueTable[i];
  }
  
  @Null
  public V remove(K key) {
    int i = locateKey(key);
    if (i < 0)
      return null; 
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    V oldValue = valueTable[i];
    int mask = this.mask, next = i + 1 & mask;
    while ((key = keyTable[next]) != null) {
      int placement = place(key);
      if ((next - placement & mask) > (i - placement & mask)) {
        keyTable[i] = key;
        valueTable[i] = valueTable[next];
        i = next;
      } 
      next = next + 1 & mask;
    } 
    keyTable[i] = null;
    valueTable[i] = null;
    this.size--;
    return oldValue;
  }
  
  public boolean notEmpty() {
    return (this.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.size == 0);
  }
  
  public void shrink(int maximumCapacity) {
    if (maximumCapacity < 0)
      throw new IllegalArgumentException("maximumCapacity must be >= 0: " + maximumCapacity); 
    int tableSize = ObjectSet.tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length > tableSize)
      resize(tableSize); 
  }
  
  public void clear(int maximumCapacity) {
    int tableSize = ObjectSet.tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length <= tableSize) {
      clear();
      return;
    } 
    this.size = 0;
    resize(tableSize);
  }
  
  public void clear() {
    if (this.size == 0)
      return; 
    this.size = 0;
    Arrays.fill(this.keyTable, null);
    Arrays.fill(this.valueTable, null);
  }
  
  public boolean containsValue(@Null Object value, boolean identity) {
    V[] valueTable = this.valueTable;
    if (value == null) {
      K[] keyTable = this.keyTable;
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (keyTable[i] != null && valueTable[i] == null)
          return true; 
      } 
    } else if (identity) {
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (valueTable[i] == value)
          return true; 
      } 
    } else
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (value.equals(valueTable[i]))
          return true; 
      }  
    return false;
  }
  
  public boolean containsKey(K key) {
    return (locateKey(key) >= 0);
  }
  
  @Null
  public K findKey(@Null Object value, boolean identity) {
    V[] valueTable = this.valueTable;
    if (value == null) {
      K[] keyTable = this.keyTable;
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (keyTable[i] != null && valueTable[i] == null)
          return keyTable[i]; 
      } 
    } else if (identity) {
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (valueTable[i] == value)
          return this.keyTable[i]; 
      } 
    } else
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (value.equals(valueTable[i]))
          return this.keyTable[i]; 
      }  
    return null;
  }
  
  public void ensureCapacity(int additionalCapacity) {
    int tableSize = ObjectSet.tableSize(this.size + additionalCapacity, this.loadFactor);
    if (this.keyTable.length < tableSize)
      resize(tableSize); 
  }
  
  final void resize(int newSize) {
    int oldCapacity = this.keyTable.length;
    this.threshold = (int)((float)newSize * this.loadFactor);
    this.mask = newSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    K[] oldKeyTable = this.keyTable;
    V[] oldValueTable = this.valueTable;
    this.keyTable = (K[])new Object[newSize];
    this.valueTable = (V[])new Object[newSize];
    if (this.size > 0)
      for (int i = 0; i < oldCapacity; i++) {
        K key = oldKeyTable[i];
        if (key != null)
          putResize(key, oldValueTable[i]); 
      }  
  }
  
  public int hashCode() {
    int h = this.size;
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null) {
        h += key.hashCode();
        V value = valueTable[i];
        if (value != null)
          h += value.hashCode(); 
      } 
    } 
    return h;
  }
  
  public boolean equals(Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof ObjectMap))
      return false; 
    ObjectMap other = (ObjectMap)obj;
    if (other.size != this.size)
      return false; 
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null) {
        V value = valueTable[i];
        if (value == null) {
          if (other.get(key, (V)dummy) != null)
            return false; 
        } else if (!value.equals(other.get(key))) {
          return false;
        } 
      } 
    } 
    return true;
  }
  
  public boolean equalsIdentity(@Null Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof ObjectMap))
      return false; 
    ObjectMap other = (ObjectMap)obj;
    if (other.size != this.size)
      return false; 
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null && valueTable[i] != other.get(key, (V)dummy))
        return false; 
    } 
    return true;
  }
  
  public String toString(String separator) {
    return toString(separator, false);
  }
  
  public String toString() {
    return toString(", ", true);
  }
  
  protected String toString(String separator, boolean braces) {
    if (this.size == 0)
      return braces ? "{}" : ""; 
    java.lang.StringBuilder buffer = new java.lang.StringBuilder(32);
    if (braces)
      buffer.append('{'); 
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    int i = keyTable.length;
    while (i-- > 0) {
      K key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append((key == this) ? "(this)" : key);
      buffer.append('=');
      V value = valueTable[i];
      buffer.append((value == this) ? "(this)" : value);
      break;
    } 
    while (i-- > 0) {
      K key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append(separator);
      buffer.append((key == this) ? "(this)" : key);
      buffer.append('=');
      V value = valueTable[i];
      buffer.append((value == this) ? "(this)" : value);
    } 
    if (braces)
      buffer.append('}'); 
    return buffer.toString();
  }
  
  public Entries<K, V> iterator() {
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
      this.entries1.reset();
      this.entries1.valid = true;
      this.entries2.valid = false;
      return this.entries1;
    } 
    this.entries2.reset();
    this.entries2.valid = true;
    this.entries1.valid = false;
    return this.entries2;
  }
  
  public Values<V> values() {
    if (Collections.allocateIterators)
      return new Values<>(this); 
    if (this.values1 == null) {
      this.values1 = new Values<>(this);
      this.values2 = new Values<>(this);
    } 
    if (!this.values1.valid) {
      this.values1.reset();
      this.values1.valid = true;
      this.values2.valid = false;
      return this.values1;
    } 
    this.values2.reset();
    this.values2.valid = true;
    this.values1.valid = false;
    return this.values2;
  }
  
  public Keys<K> keys() {
    if (Collections.allocateIterators)
      return new Keys<>(this); 
    if (this.keys1 == null) {
      this.keys1 = new Keys<>(this);
      this.keys2 = new Keys<>(this);
    } 
    if (!this.keys1.valid) {
      this.keys1.reset();
      this.keys1.valid = true;
      this.keys2.valid = false;
      return this.keys1;
    } 
    this.keys2.reset();
    this.keys2.valid = true;
    this.keys1.valid = false;
    return this.keys2;
  }
  
  public static class Entry<K, V> {
    public K key;
    
    @Null
    public V value;
    
    public String toString() {
      return new java.lang.StringBuilder().append(this.key).append("=").append(this.value).toString();
    }
  }
  
  private static abstract class MapIterator<K, V, I> implements Iterable<I>, Iterator<I> {
    public boolean hasNext;
    
    final ObjectMap<K, V> map;
    
    int nextIndex;
    
    int currentIndex;
    
    boolean valid = true;
    
    public MapIterator(ObjectMap<K, V> map) {
      this.map = map;
      reset();
    }
    
    public void reset() {
      this.currentIndex = -1;
      this.nextIndex = -1;
      findNextIndex();
    }
    
    void findNextIndex() {
      K[] keyTable = this.map.keyTable;
      for (int n = keyTable.length; ++this.nextIndex < n;) {
        if (keyTable[this.nextIndex] != null) {
          this.hasNext = true;
          return;
        } 
      } 
      this.hasNext = false;
    }
    
    public void remove() {
      int i = this.currentIndex;
      if (i < 0)
        throw new IllegalStateException("next must be called before remove."); 
      K[] keyTable = this.map.keyTable;
      V[] valueTable = this.map.valueTable;
      int mask = this.map.mask, next = i + 1 & mask;
      K key;
      while ((key = keyTable[next]) != null) {
        int placement = this.map.place(key);
        if ((next - placement & mask) > (i - placement & mask)) {
          keyTable[i] = key;
          valueTable[i] = valueTable[next];
          i = next;
        } 
        next = next + 1 & mask;
      } 
      keyTable[i] = null;
      valueTable[i] = null;
      this.map.size--;
      if (i != this.currentIndex)
        this.nextIndex--; 
      this.currentIndex = -1;
    }
  }
  
  public static class Entries<K, V> extends MapIterator<K, V, Entry<K, V>> {
    ObjectMap.Entry<K, V> entry = new ObjectMap.Entry<>();
    
    public Entries(ObjectMap<K, V> map) {
      super(map);
    }
    
    public ObjectMap.Entry<K, V> next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      K[] keyTable = this.map.keyTable;
      this.entry.key = keyTable[this.nextIndex];
      this.entry.value = this.map.valueTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return this.entry;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    public Entries<K, V> iterator() {
      return this;
    }
  }
  
  public static class Values<V> extends MapIterator<Object, V, V> {
    public Values(ObjectMap<?, V> map) {
      super((ObjectMap)map);
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    @Null
    public V next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      V value = this.map.valueTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return value;
    }
    
    public Values<V> iterator() {
      return this;
    }
    
    public Array<V> toArray() {
      return toArray(new Array<>(true, this.map.size));
    }
    
    public Array<V> toArray(Array<V> array) {
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
  }
  
  public static class Keys<K> extends MapIterator<K, Object, K> {
    public Keys(ObjectMap<K, ?> map) {
      super((ObjectMap)map);
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    public K next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      K key = this.map.keyTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return key;
    }
    
    public Keys<K> iterator() {
      return this;
    }
    
    public Array<K> toArray() {
      return toArray(new Array<>(true, this.map.size));
    }
    
    public Array<K> toArray(Array<K> array) {
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
  }
}
