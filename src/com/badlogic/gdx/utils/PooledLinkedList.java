package com.badlogic.gdx.utils;

public class PooledLinkedList<T> {
  private Item<T> head;
  
  private Item<T> tail;
  
  private Item<T> iter;
  
  private Item<T> curr;
  
  static final class Item<T> {
    public T payload;
    
    public Item<T> next;
    
    public Item<T> prev;
  }
  
  private int size = 0;
  
  private final Pool<Item<T>> pool;
  
  public PooledLinkedList(int maxPoolSize) {
    this.pool = new Pool<Item<T>>(16, maxPoolSize) {
        protected PooledLinkedList.Item<T> newObject() {
          return new PooledLinkedList.Item<>();
        }
      };
  }
  
  public void add(T object) {
    Item<T> item = this.pool.obtain();
    item.payload = object;
    item.next = null;
    item.prev = null;
    if (this.head == null) {
      this.head = item;
      this.tail = item;
      this.size++;
      return;
    } 
    item.prev = this.tail;
    this.tail.next = item;
    this.tail = item;
    this.size++;
  }
  
  public void addFirst(T object) {
    Item<T> item = this.pool.obtain();
    item.payload = object;
    item.next = this.head;
    item.prev = null;
    if (this.head != null) {
      this.head.prev = item;
    } else
      this.tail = item; 
    this.head = item;
    this.size++;
  }
  
  public int size() {
    return this.size;
  }
  
  public void iter() {
    this.iter = this.head;
  }
  
  public void iterReverse() {
    this.iter = this.tail;
  }
  
  @Null
  public T next() {
    if (this.iter == null)
      return null; 
    T payload = this.iter.payload;
    this.curr = this.iter;
    this.iter = this.iter.next;
    return payload;
  }
  
  @Null
  public T previous() {
    if (this.iter == null)
      return null; 
    T payload = this.iter.payload;
    this.curr = this.iter;
    this.iter = this.iter.prev;
    return payload;
  }
  
  public void remove() {
    if (this.curr == null)
      return; 
    this.size--;
    Item<T> c = this.curr;
    Item<T> n = this.curr.next;
    Item<T> p = this.curr.prev;
    this.pool.free(this.curr);
    this.curr = null;
    if (this.size == 0) {
      this.head = null;
      this.tail = null;
      return;
    } 
    if (c == this.head) {
      n.prev = null;
      this.head = n;
      return;
    } 
    if (c == this.tail) {
      p.next = null;
      this.tail = p;
      return;
    } 
    p.next = n;
    n.prev = p;
  }
  
  @Null
  public T removeLast() {
    if (this.tail == null)
      return null; 
    T payload = this.tail.payload;
    this.size--;
    Item<T> p = this.tail.prev;
    this.pool.free(this.tail);
    if (this.size == 0) {
      this.head = null;
      this.tail = null;
    } else {
      this.tail = p;
      this.tail.next = null;
    } 
    return payload;
  }
  
  public void clear() {
    iter();
    T v = null;
    while ((v = next()) != null)
      remove(); 
  }
}
