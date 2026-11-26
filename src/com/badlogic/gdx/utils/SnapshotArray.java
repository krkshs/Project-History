package com.badlogic.gdx.utils;

import java.util.Comparator;

public class SnapshotArray<T> extends Array<T> {
  private T[] snapshot;
  
  private T[] recycled;
  
  private int snapshots;
  
  public SnapshotArray() {}
  
  public SnapshotArray(Array array) {
    super(array);
  }
  
  public SnapshotArray(boolean ordered, int capacity, Class arrayType) {
    super(ordered, capacity, arrayType);
  }
  
  public SnapshotArray(boolean ordered, int capacity) {
    super(ordered, capacity);
  }
  
  public SnapshotArray(boolean ordered, T[] array, int startIndex, int count) {
    super(ordered, array, startIndex, count);
  }
  
  public SnapshotArray(Class arrayType) {
    super(arrayType);
  }
  
  public SnapshotArray(int capacity) {
    super(capacity);
  }
  
  public SnapshotArray(T[] array) {
    super(array);
  }
  
  public T[] begin() {
    modified();
    this.snapshot = this.items;
    this.snapshots++;
    return this.items;
  }
  
  public void end() {
    this.snapshots = Math.max(0, this.snapshots - 1);
    if (this.snapshot == null)
      return; 
    if (this.snapshot != this.items && this.snapshots == 0) {
      this.recycled = this.snapshot;
      for (int i = 0, n = this.recycled.length; i < n; i++)
        this.recycled[i] = null; 
    } 
    this.snapshot = null;
  }
  
  private void modified() {
    if (this.snapshot == null || this.snapshot != this.items)
      return; 
    if (this.recycled != null && this.recycled.length >= this.size) {
      System.arraycopy(this.items, 0, this.recycled, 0, this.size);
      this.items = this.recycled;
      this.recycled = null;
    } else
      resize(this.items.length); 
  }
  
  public void set(int index, T value) {
    modified();
    super.set(index, value);
  }
  
  public void insert(int index, T value) {
    modified();
    super.insert(index, value);
  }
  
  public void insertRange(int index, int count) {
    modified();
    super.insertRange(index, count);
  }
  
  public void swap(int first, int second) {
    modified();
    super.swap(first, second);
  }
  
  public boolean removeValue(T value, boolean identity) {
    modified();
    return super.removeValue(value, identity);
  }
  
  public T removeIndex(int index) {
    modified();
    return super.removeIndex(index);
  }
  
  public void removeRange(int start, int end) {
    modified();
    super.removeRange(start, end);
  }
  
  public boolean removeAll(Array<? extends T> array, boolean identity) {
    modified();
    return super.removeAll(array, identity);
  }
  
  public T pop() {
    modified();
    return super.pop();
  }
  
  public void clear() {
    modified();
    super.clear();
  }
  
  public void sort() {
    modified();
    super.sort();
  }
  
  public void sort(Comparator<? super T> comparator) {
    modified();
    super.sort(comparator);
  }
  
  public void reverse() {
    modified();
    super.reverse();
  }
  
  public void shuffle() {
    modified();
    super.shuffle();
  }
  
  public void truncate(int newSize) {
    modified();
    super.truncate(newSize);
  }
  
  public T[] setSize(int newSize) {
    modified();
    return super.setSize(newSize);
  }
  
  public static <T> SnapshotArray<T> with(T... array) {
    return new SnapshotArray<>(array);
  }
}
