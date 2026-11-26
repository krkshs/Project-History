package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pools;
import java.util.Iterator;

public class Selection<T> implements Disableable, Iterable<T> {
  @Null
  private Actor actor;
  
  final OrderedSet<T> selected = new OrderedSet<>();
  
  private final OrderedSet<T> old = new OrderedSet<>();
  
  boolean isDisabled;
  
  private boolean toggle;
  
  boolean multiple;
  
  boolean required;
  
  private boolean programmaticChangeEvents = true;
  
  @Null
  T lastSelected;
  
  public void setActor(@Null Actor actor) {
    this.actor = actor;
  }
  
  public void choose(T item) {
    if (item == null)
      throw new IllegalArgumentException("item cannot be null."); 
    if (this.isDisabled)
      return; 
    snapshot();
    try {
      if ((this.toggle || UIUtils.ctrl()) && this.selected.contains(item)) {
        if (this.required && this.selected.size == 1)
          return; 
        this.selected.remove(item);
        this.lastSelected = null;
      } else {
        boolean modified = false;
        if (!this.multiple || (!this.toggle && !UIUtils.ctrl())) {
          if (this.selected.size == 1 && this.selected.contains(item))
            return; 
          modified = (this.selected.size > 0);
          this.selected.clear(8);
        } 
        if (!this.selected.add(item) && !modified)
          return; 
        this.lastSelected = item;
      } 
      if (fireChangeEvent()) {
        revert();
      } else
        changed(); 
    } finally {
      cleanup();
    } 
  }
  
  @Deprecated
  public boolean hasItems() {
    return (this.selected.size > 0);
  }
  
  public boolean notEmpty() {
    return (this.selected.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.selected.size == 0);
  }
  
  public int size() {
    return this.selected.size;
  }
  
  public OrderedSet<T> items() {
    return this.selected;
  }
  
  @Null
  public T first() {
    return (this.selected.size == 0) ? null : this.selected.first();
  }
  
  void snapshot() {
    this.old.clear(this.selected.size);
    this.old.addAll(this.selected);
  }
  
  void revert() {
    this.selected.clear(this.old.size);
    this.selected.addAll(this.old);
  }
  
  void cleanup() {
    this.old.clear(32);
  }
  
  public void set(T item) {
    if (item == null)
      throw new IllegalArgumentException("item cannot be null."); 
    if (this.selected.size == 1 && this.selected.first() == item)
      return; 
    snapshot();
    this.selected.clear(8);
    this.selected.add(item);
    if (this.programmaticChangeEvents && fireChangeEvent()) {
      revert();
    } else {
      this.lastSelected = item;
      changed();
    } 
    cleanup();
  }
  
  public void setAll(Array<T> items) {
    boolean added = false;
    snapshot();
    this.lastSelected = null;
    this.selected.clear(items.size);
    for (int i = 0, n = items.size; i < n; i++) {
      T item = items.get(i);
      if (item == null)
        throw new IllegalArgumentException("item cannot be null."); 
      if (this.selected.add(item))
        added = true; 
    } 
    if (added)
      if (this.programmaticChangeEvents && fireChangeEvent()) {
        revert();
      } else if (items.size > 0) {
        this.lastSelected = items.peek();
        changed();
      }  
    cleanup();
  }
  
  public void add(T item) {
    if (item == null)
      throw new IllegalArgumentException("item cannot be null."); 
    if (!this.selected.add(item))
      return; 
    if (this.programmaticChangeEvents && fireChangeEvent()) {
      this.selected.remove(item);
    } else {
      this.lastSelected = item;
      changed();
    } 
  }
  
  public void addAll(Array<T> items) {
    boolean added = false;
    snapshot();
    for (int i = 0, n = items.size; i < n; i++) {
      T item = items.get(i);
      if (item == null)
        throw new IllegalArgumentException("item cannot be null."); 
      if (this.selected.add(item))
        added = true; 
    } 
    if (added)
      if (this.programmaticChangeEvents && fireChangeEvent()) {
        revert();
      } else {
        this.lastSelected = items.peek();
        changed();
      }  
    cleanup();
  }
  
  public void remove(T item) {
    if (item == null)
      throw new IllegalArgumentException("item cannot be null."); 
    if (!this.selected.remove(item))
      return; 
    if (this.programmaticChangeEvents && fireChangeEvent()) {
      this.selected.add(item);
    } else {
      this.lastSelected = null;
      changed();
    } 
  }
  
  public void removeAll(Array<T> items) {
    boolean removed = false;
    snapshot();
    for (int i = 0, n = items.size; i < n; i++) {
      T item = items.get(i);
      if (item == null)
        throw new IllegalArgumentException("item cannot be null."); 
      if (this.selected.remove(item))
        removed = true; 
    } 
    if (removed)
      if (this.programmaticChangeEvents && fireChangeEvent()) {
        revert();
      } else {
        this.lastSelected = null;
        changed();
      }  
    cleanup();
  }
  
  public void clear() {
    if (this.selected.size == 0)
      return; 
    snapshot();
    this.selected.clear(8);
    if (this.programmaticChangeEvents && fireChangeEvent()) {
      revert();
    } else {
      this.lastSelected = null;
      changed();
    } 
    cleanup();
  }
  
  protected void changed() {}
  
  public boolean fireChangeEvent() {
    if (this.actor == null)
      return false; 
    ChangeListener.ChangeEvent changeEvent = Pools.<ChangeListener.ChangeEvent>obtain(ChangeListener.ChangeEvent.class);
    try {
      return this.actor.fire(changeEvent);
    } finally {
      Pools.free(changeEvent);
    } 
  }
  
  public boolean contains(@Null T item) {
    if (item == null)
      return false; 
    return this.selected.contains(item);
  }
  
  @Null
  public T getLastSelected() {
    if (this.lastSelected != null)
      return this.lastSelected; 
    if (this.selected.size > 0)
      return this.selected.first(); 
    return null;
  }
  
  public Iterator<T> iterator() {
    return this.selected.iterator();
  }
  
  public Array<T> toArray() {
    return this.selected.iterator().toArray();
  }
  
  public Array<T> toArray(Array<T> array) {
    return this.selected.iterator().toArray(array);
  }
  
  public void setDisabled(boolean isDisabled) {
    this.isDisabled = isDisabled;
  }
  
  public boolean isDisabled() {
    return this.isDisabled;
  }
  
  public boolean getToggle() {
    return this.toggle;
  }
  
  public void setToggle(boolean toggle) {
    this.toggle = toggle;
  }
  
  public boolean getMultiple() {
    return this.multiple;
  }
  
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }
  
  public boolean getRequired() {
    return this.required;
  }
  
  public void setRequired(boolean required) {
    this.required = required;
  }
  
  public void setProgrammaticChangeEvents(boolean programmaticChangeEvents) {
    this.programmaticChangeEvents = programmaticChangeEvents;
  }
  
  public String toString() {
    return this.selected.toString();
  }
}
