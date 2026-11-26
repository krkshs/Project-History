package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class ArraySelection<T> extends Selection<T> {
  private Array<T> array;
  
  private boolean rangeSelect = true;
  
  private T rangeStart;
  
  public ArraySelection(Array<T> array) {
    this.array = array;
  }
  
  public void choose(T item) {
    if (item == null)
      throw new IllegalArgumentException("item cannot be null."); 
    if (this.isDisabled)
      return; 
    if (!this.rangeSelect || !this.multiple) {
      super.choose(item);
      return;
    } 
    if (this.selected.size > 0 && UIUtils.shift()) {
      int rangeStartIndex = (this.rangeStart == null) ? -1 : this.array.indexOf(this.rangeStart, false);
      if (rangeStartIndex != -1) {
        T oldRangeStart = this.rangeStart;
        snapshot();
        int start = rangeStartIndex, end = this.array.indexOf(item, false);
        if (start > end) {
          int temp = end;
          end = start;
          start = temp;
        } 
        if (!UIUtils.ctrl())
          this.selected.clear(8); 
        for (int i = start; i <= end; i++)
          this.selected.add(this.array.get(i)); 
        if (fireChangeEvent()) {
          revert();
        } else
          changed(); 
        this.rangeStart = oldRangeStart;
        cleanup();
        return;
      } 
    } 
    super.choose(item);
    this.rangeStart = item;
  }
  
  protected void changed() {
    this.rangeStart = null;
  }
  
  public boolean getRangeSelect() {
    return this.rangeSelect;
  }
  
  public void setRangeSelect(boolean rangeSelect) {
    this.rangeSelect = rangeSelect;
  }
  
  public void validate() {
    Array<T> array = this.array;
    if (array.size == 0) {
      clear();
      return;
    } 
    boolean changed = false;
    for (Iterator<T> iter = items().iterator(); iter.hasNext(); ) {
      T selected = iter.next();
      if (!array.contains(selected, false)) {
        iter.remove();
        changed = true;
      } 
    } 
    if (this.required && this.selected.size == 0) {
      set(array.first());
    } else if (changed) {
      changed();
    } 
  }
}
