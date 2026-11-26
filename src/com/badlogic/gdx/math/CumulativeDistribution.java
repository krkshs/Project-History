package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;

public class CumulativeDistribution<T> {
  public class CumulativeValue {
    public T value;
    
    public float frequency;
    
    public float interval;
    
    public CumulativeValue(T value, float frequency, float interval) {
      this.value = value;
      this.frequency = frequency;
      this.interval = interval;
    }
  }
  
  private Array<CumulativeValue> values = new Array<>(false, 10, CumulativeValue.class);
  
  public void add(T value, float intervalSize) {
    this.values.add(new CumulativeValue(value, 0.0F, intervalSize));
  }
  
  public void add(T value) {
    this.values.add(new CumulativeValue(value, 0.0F, 0.0F));
  }
  
  public void generate() {
    float sum = 0.0F;
    for (int i = 0; i < this.values.size; i++) {
      sum += (((CumulativeValue[])this.values.items)[i]).interval;
      (((CumulativeValue[])this.values.items)[i]).frequency = sum;
    } 
  }
  
  public void generateNormalized() {
    float sum = 0.0F;
    for (int i = 0; i < this.values.size; i++)
      sum += (((CumulativeValue[])this.values.items)[i]).interval; 
    float intervalSum = 0.0F;
    for (int j = 0; j < this.values.size; j++) {
      intervalSum += (((CumulativeValue[])this.values.items)[j]).interval / sum;
      (((CumulativeValue[])this.values.items)[j]).frequency = intervalSum;
    } 
  }
  
  public void generateUniform() {
    float freq = 1.0F / (float)this.values.size;
    for (int i = 0; i < this.values.size; i++) {
      (((CumulativeValue[])this.values.items)[i]).interval = freq;
      (((CumulativeValue[])this.values.items)[i]).frequency = (float)(i + 1) * freq;
    } 
  }
  
  public T value(float probability) {
    CumulativeValue value = null;
    int imax = this.values.size - 1, imin = 0;
    while (imin <= imax) {
      int imid = imin + (imax - imin) / 2;
      value = ((CumulativeValue[])this.values.items)[imid];
      if (probability < value.frequency) {
        imax = imid - 1;
        continue;
      } 
      if (probability > value.frequency) {
        imin = imid + 1;
        continue;
      } 
      break;
    } 
    return (((CumulativeValue[])this.values.items)[imin]).value;
  }
  
  public T value() {
    return value(MathUtils.random());
  }
  
  public int size() {
    return this.values.size;
  }
  
  public float getInterval(int index) {
    return (((CumulativeValue[])this.values.items)[index]).interval;
  }
  
  public T getValue(int index) {
    return (((CumulativeValue[])this.values.items)[index]).value;
  }
  
  public void setInterval(T obj, float intervalSize) {
    for (Array.ArrayIterator<CumulativeValue> arrayIterator = this.values.iterator(); arrayIterator.hasNext(); ) {
      CumulativeValue value = arrayIterator.next();
      if (value.value == obj) {
        value.interval = intervalSize;
        return;
      } 
    } 
  }
  
  public void setInterval(int index, float intervalSize) {
    (((CumulativeValue[])this.values.items)[index]).interval = intervalSize;
  }
  
  public void clear() {
    this.values.clear();
  }
}
