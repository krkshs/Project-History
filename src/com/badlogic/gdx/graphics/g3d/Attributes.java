package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;
import java.util.Comparator;
import java.util.Iterator;

public class Attributes implements Iterable<Attribute>, Comparator<Attribute>, Comparable<Attributes> {
  protected long mask;
  
  protected final Array<Attribute> attributes = new Array<>();
  
  protected boolean sorted = true;
  
  public final void sort() {
    if (!this.sorted) {
      this.attributes.sort(this);
      this.sorted = true;
    } 
  }
  
  public final long getMask() {
    return this.mask;
  }
  
  public final Attribute get(long type) {
    if (has(type))
      for (int i = 0; i < this.attributes.size; i++) {
        if (((Attribute)this.attributes.get(i)).type == type)
          return this.attributes.get(i); 
      }  
    return null;
  }
  
  public final <T extends Attribute> T get(Class<T> clazz, long type) {
    return (T)get(type);
  }
  
  public final Array<Attribute> get(Array<Attribute> out, long type) {
    for (int i = 0; i < this.attributes.size; i++) {
      if ((((Attribute)this.attributes.get(i)).type & type) != 0L)
        out.add(this.attributes.get(i)); 
    } 
    return out;
  }
  
  public void clear() {
    this.mask = 0L;
    this.attributes.clear();
  }
  
  public int size() {
    return this.attributes.size;
  }
  
  private final void enable(long mask) {
    this.mask |= mask;
  }
  
  private final void disable(long mask) {
    this.mask &= mask ^ 0xFFFFFFFFFFFFFFFFL;
  }
  
  public final void set(Attribute attribute) {
    int idx = indexOf(attribute.type);
    if (idx < 0) {
      enable(attribute.type);
      this.attributes.add(attribute);
      this.sorted = false;
    } else
      this.attributes.set(idx, attribute); 
    sort();
  }
  
  public final void set(Attribute attribute1, Attribute attribute2) {
    set(attribute1);
    set(attribute2);
  }
  
  public final void set(Attribute attribute1, Attribute attribute2, Attribute attribute3) {
    set(attribute1);
    set(attribute2);
    set(attribute3);
  }
  
  public final void set(Attribute attribute1, Attribute attribute2, Attribute attribute3, Attribute attribute4) {
    set(attribute1);
    set(attribute2);
    set(attribute3);
    set(attribute4);
  }
  
  public final void set(Attribute... attributes) {
    for (Attribute attr : attributes)
      set(attr); 
  }
  
  public final void set(Iterable<Attribute> attributes) {
    for (Attribute attr : attributes)
      set(attr); 
  }
  
  public final void remove(long mask) {
    for (int i = this.attributes.size - 1; i >= 0; i--) {
      long type = ((Attribute)this.attributes.get(i)).type;
      if ((mask & type) == type) {
        this.attributes.removeIndex(i);
        disable(type);
        this.sorted = false;
      } 
    } 
    sort();
  }
  
  public final boolean has(long type) {
    return (type != 0L && (this.mask & type) == type);
  }
  
  protected int indexOf(long type) {
    if (has(type))
      for (int i = 0; i < this.attributes.size; i++) {
        if (((Attribute)this.attributes.get(i)).type == type)
          return i; 
      }  
    return -1;
  }
  
  public final boolean same(Attributes other, boolean compareValues) {
    if (other == this)
      return true; 
    if (other == null || this.mask != other.mask)
      return false; 
    if (!compareValues)
      return true; 
    sort();
    other.sort();
    for (int i = 0; i < this.attributes.size; i++) {
      if (!this.attributes.get(i).equals(other.attributes.get(i)))
        return false; 
    } 
    return true;
  }
  
  public final boolean same(Attributes other) {
    return same(other, false);
  }
  
  public final int compare(Attribute arg0, Attribute arg1) {
    return (int)(arg0.type - arg1.type);
  }
  
  public final Iterator<Attribute> iterator() {
    return this.attributes.iterator();
  }
  
  public int attributesHash() {
    sort();
    int n = this.attributes.size;
    long result = 71L + this.mask;
    int m = 1;
    for (int i = 0; i < n; i++)
      result += this.mask * (long)this.attributes.get(i).hashCode() * (long)(m = m * 7 & 0xFFFF); 
    return (int)(result ^ result >> 32L);
  }
  
  public int hashCode() {
    return attributesHash();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof Attributes))
      return false; 
    if (other == this)
      return true; 
    return same((Attributes)other, true);
  }
  
  public int compareTo(Attributes other) {
    if (other == this)
      return 0; 
    if (this.mask != other.mask)
      return (this.mask < other.mask) ? -1 : 1; 
    sort();
    other.sort();
    for (int i = 0; i < this.attributes.size; i++) {
      int c = this.attributes.get(i).compareTo(other.attributes.get(i));
      if (c != 0)
        return (c < 0) ? -1 : ((c > 0) ? 1 : 0); 
    } 
    return 0;
  }
}
