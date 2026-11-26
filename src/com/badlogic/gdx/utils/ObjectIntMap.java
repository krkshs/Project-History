package com.badlogic.gdx.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectIntMap<K> implements Iterable<ObjectIntMap.Entry<K>> {
  public int size;
  
  K[] keyTable;
  
  int[] valueTable;
  
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
  
  public ObjectIntMap() {
    this(51, 0.8F);
  }
  
  public ObjectIntMap(int initialCapacity) {
    this(initialCapacity, 0.8F);
  }
  
  public ObjectIntMap(int initialCapacity, float loadFactor) {
    if (loadFactor <= 0.0F || loadFactor >= 1.0F)
      throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + loadFactor); 
    this.loadFactor = loadFactor;
    int tableSize = ObjectSet.tableSize(initialCapacity, loadFactor);
    this.threshold = (int)((float)tableSize * loadFactor);
    this.mask = tableSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    this.keyTable = (K[])new Object[tableSize];
    this.valueTable = new int[tableSize];
  }
  
  public ObjectIntMap(ObjectIntMap<? extends K> map) {
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
  
  public void put(K key, int value) {
    int i = locateKey(key);
    if (i >= 0) {
      this.valueTable[i] = value;
      return;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = value;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
  }
  
  public int put(K key, int value, int defaultValue) {
    int i = locateKey(key);
    if (i >= 0) {
      int oldValue = this.valueTable[i];
      this.valueTable[i] = value;
      return oldValue;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = value;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return defaultValue;
  }
  
  public void putAll(ObjectIntMap<? extends K> map) {
    ensureCapacity(map.size);
    K[] keyTable = map.keyTable;
    int[] valueTable = map.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null)
        put(key, valueTable[i]); 
    } 
  }
  
  private void putResize(K key, int value) {
    K[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      if (keyTable[i] == null) {
        keyTable[i] = key;
        this.valueTable[i] = value;
        return;
      } 
    } 
  }
  
  public int get(K key, int defaultValue) {
    int i = locateKey(key);
    return (i < 0) ? defaultValue : this.valueTable[i];
  }
  
  public int getAndIncrement(K key, int defaultValue, int increment) {
    int i = locateKey(key);
    if (i >= 0) {
      int oldValue = this.valueTable[i];
      this.valueTable[i] = this.valueTable[i] + increment;
      return oldValue;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = defaultValue + increment;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return defaultValue;
  }
  
  public int remove(K key, int defaultValue) {
    int i = locateKey(key);
    if (i < 0)
      return defaultValue; 
    K[] keyTable = this.keyTable;
    int[] valueTable = this.valueTable;
    int oldValue = valueTable[i];
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
  }
  
  public boolean containsValue(int value) {
    K[] keyTable = this.keyTable;
    int[] valueTable = this.valueTable;
    for (int i = valueTable.length - 1; i >= 0; i--) {
      if (keyTable[i] != null && valueTable[i] == value)
        return true; 
    } 
    return false;
  }
  
  public boolean containsKey(K key) {
    return (locateKey(key) >= 0);
  }
  
  @Null
  public K findKey(int value) {
    K[] keyTable = this.keyTable;
    int[] valueTable = this.valueTable;
    for (int i = valueTable.length - 1; i >= 0; i--) {
      K key = keyTable[i];
      if (key != null && valueTable[i] == value)
        return key; 
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
    int[] oldValueTable = this.valueTable;
    this.keyTable = (K[])new Object[newSize];
    this.valueTable = new int[newSize];
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
    int[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null)
        h += key.hashCode() + valueTable[i]; 
    } 
    return h;
  }
  
  public boolean equals(Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof ObjectIntMap))
      return false; 
    ObjectIntMap other = (ObjectIntMap)obj;
    if (other.size != this.size)
      return false; 
    K[] keyTable = this.keyTable;
    int[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null) {
        int otherValue = other.get(key, 0);
        if (otherValue == 0 && !other.containsKey(key))
          return false; 
        if (otherValue != valueTable[i])
          return false; 
      } 
    } 
    return true;
  }
  
  public String toString(String separator) {
    return toString(separator, false);
  }
  
  public String toString() {
    return toString(", ", true);
  }
  
  private String toString(String separator, boolean braces) {
    if (this.size == 0)
      return braces ? "{}" : ""; 
    java.lang.StringBuilder buffer = new java.lang.StringBuilder(32);
    if (braces)
      buffer.append('{'); 
    K[] keyTable = this.keyTable;
    int[] valueTable = this.valueTable;
    int i = keyTable.length;
    while (i-- > 0) {
      K key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append(key);
      buffer.append('=');
      buffer.append(valueTable[i]);
      break;
    } 
    while (i-- > 0) {
      K key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append(separator);
      buffer.append(key);
      buffer.append('=');
      buffer.append(valueTable[i]);
    } 
    if (braces)
      buffer.append('}'); 
    return buffer.toString();
  }
  
  public Entries<K> iterator() {
    return entries();
  }
  
  public Entries<K> entries() {
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
  
  public Values values() {
    if (Collections.allocateIterators)
      return new Values(this); 
    if (this.values1 == null) {
      this.values1 = new Values(this);
      this.values2 = new Values(this);
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
  
  public static class Entry<K> {
    public K key;
    
    public int value;
    
    public String toString() {
      return new java.lang.StringBuilder().append(this.key).append("=").append(this.value).toString();
    }
  }
  
  private static class MapIterator<K> {
    public boolean hasNext;
    
    final ObjectIntMap<K> map;
    
    int nextIndex;
    
    int currentIndex;
    
    boolean valid = true;
    
    public MapIterator(ObjectIntMap<K> map) {
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
      int[] valueTable = this.map.valueTable;
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
      this.map.size--;
      if (i != this.currentIndex)
        this.nextIndex--; 
      this.currentIndex = -1;
    }
  }
  
  public static class Entries<K> extends MapIterator<K> implements Iterable<Entry<K>>, Iterator<Entry<K>> {
    ObjectIntMap.Entry<K> entry = new ObjectIntMap.Entry<>();
    
    public Entries(ObjectIntMap<K> map) {
      super(map);
    }
    
    public ObjectIntMap.Entry<K> next() {
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
    
    public Entries<K> iterator() {
      return this;
    }
  }
  
  public static class Values extends MapIterator<Object> {
    public Values(ObjectIntMap<?> map) {
      super(map);
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    public int next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      int value = this.map.valueTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return value;
    }
    
    public Values iterator() {
      return this;
    }
    
    public IntArray toArray() {
      IntArray array = new IntArray(true, this.map.size);
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
    
    public IntArray toArray(IntArray array) {
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
  }
  
  public static class Keys<K> extends MapIterator<K> implements Iterable<K>, Iterator<K> {
    public Keys(ObjectIntMap<K> map) {
      super(map);
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
