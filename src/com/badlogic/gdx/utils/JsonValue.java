package com.badlogic.gdx.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonValue implements Iterable<JsonValue> {
  private ValueType type;
  
  private String stringValue;
  
  private double doubleValue;
  
  private long longValue;
  
  public String name;
  
  public JsonValue child;
  
  public JsonValue parent;
  
  public JsonValue next;
  
  public JsonValue prev;
  
  public int size;
  
  public JsonValue(ValueType type) {
    this.type = type;
  }
  
  public JsonValue(@Null String value) {
    set(value);
  }
  
  public JsonValue(double value) {
    set(value, (String)null);
  }
  
  public JsonValue(long value) {
    set(value, (String)null);
  }
  
  public JsonValue(double value, String stringValue) {
    set(value, stringValue);
  }
  
  public JsonValue(long value, String stringValue) {
    set(value, stringValue);
  }
  
  public JsonValue(boolean value) {
    set(value);
  }
  
  @Null
  public JsonValue get(int index) {
    JsonValue current = this.child;
    while (current != null && index > 0) {
      index--;
      current = current.next;
    } 
    return current;
  }
  
  @Null
  public JsonValue get(String name) {
    JsonValue current = this.child;
    while (current != null && (current.name == null || !current.name.equalsIgnoreCase(name)))
      current = current.next; 
    return current;
  }
  
  public boolean has(String name) {
    return (get(name) != null);
  }
  
  public JsonValue require(int index) {
    JsonValue current = this.child;
    while (current != null && index > 0) {
      index--;
      current = current.next;
    } 
    if (current == null)
      throw new IllegalArgumentException("Child not found with index: " + index); 
    return current;
  }
  
  public JsonValue require(String name) {
    JsonValue current = this.child;
    while (current != null && (current.name == null || !current.name.equalsIgnoreCase(name)))
      current = current.next; 
    if (current == null)
      throw new IllegalArgumentException("Child not found with name: " + name); 
    return current;
  }
  
  @Null
  public JsonValue remove(int index) {
    JsonValue child = get(index);
    if (child == null)
      return null; 
    if (child.prev == null) {
      this.child = child.next;
      if (this.child != null)
        this.child.prev = null; 
    } else {
      child.prev.next = child.next;
      if (child.next != null)
        child.next.prev = child.prev; 
    } 
    this.size--;
    return child;
  }
  
  @Null
  public JsonValue remove(String name) {
    JsonValue child = get(name);
    if (child == null)
      return null; 
    if (child.prev == null) {
      this.child = child.next;
      if (this.child != null)
        this.child.prev = null; 
    } else {
      child.prev.next = child.next;
      if (child.next != null)
        child.next.prev = child.prev; 
    } 
    this.size--;
    return child;
  }
  
  public void remove() {
    if (this.parent == null)
      throw new IllegalStateException(); 
    if (this.prev == null) {
      this.parent.child = this.next;
      if (this.parent.child != null)
        this.parent.child.prev = null; 
    } else {
      this.prev.next = this.next;
      if (this.next != null)
        this.next.prev = this.prev; 
    } 
    this.parent.size--;
  }
  
  public boolean notEmpty() {
    return (this.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.size == 0);
  }
  
  @Deprecated
  public int size() {
    return this.size;
  }
  
  @Null
  public String asString() {
    switch (this.type) {
      case stringValue:
        return this.stringValue;
      case doubleValue:
        return (this.stringValue != null) ? this.stringValue : Double.toString(this.doubleValue);
      case longValue:
        return (this.stringValue != null) ? this.stringValue : Long.toString(this.longValue);
      case booleanValue:
        return (this.longValue != 0L) ? "true" : "false";
      case nullValue:
        return null;
    } 
    throw new IllegalStateException("Value cannot be converted to string: " + this.type);
  }
  
  public float asFloat() {
    switch (this.type) {
      case stringValue:
        return Float.parseFloat(this.stringValue);
      case doubleValue:
        return (float)this.doubleValue;
      case longValue:
        return (float)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1.0F : 0.0F;
    } 
    throw new IllegalStateException("Value cannot be converted to float: " + this.type);
  }
  
  public double asDouble() {
    switch (this.type) {
      case stringValue:
        return Double.parseDouble(this.stringValue);
      case doubleValue:
        return this.doubleValue;
      case longValue:
        return (double)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1.0D : 0.0D;
    } 
    throw new IllegalStateException("Value cannot be converted to double: " + this.type);
  }
  
  public long asLong() {
    switch (this.type) {
      case stringValue:
        return Long.parseLong(this.stringValue);
      case doubleValue:
        return (long)this.doubleValue;
      case longValue:
        return this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1L : 0L;
    } 
    throw new IllegalStateException("Value cannot be converted to long: " + this.type);
  }
  
  public int asInt() {
    switch (this.type) {
      case stringValue:
        return Integer.parseInt(this.stringValue);
      case doubleValue:
        return (int)this.doubleValue;
      case longValue:
        return (int)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1 : 0;
    } 
    throw new IllegalStateException("Value cannot be converted to int: " + this.type);
  }
  
  public boolean asBoolean() {
    switch (this.type) {
      case stringValue:
        return this.stringValue.equalsIgnoreCase("true");
      case doubleValue:
        return (this.doubleValue != 0.0D);
      case longValue:
        return (this.longValue != 0L);
      case booleanValue:
        return (this.longValue != 0L);
    } 
    throw new IllegalStateException("Value cannot be converted to boolean: " + this.type);
  }
  
  public byte asByte() {
    switch (this.type) {
      case stringValue:
        return Byte.parseByte(this.stringValue);
      case doubleValue:
        return (byte)(int)this.doubleValue;
      case longValue:
        return (byte)(int)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1 : 0;
    } 
    throw new IllegalStateException("Value cannot be converted to byte: " + this.type);
  }
  
  public short asShort() {
    switch (this.type) {
      case stringValue:
        return Short.parseShort(this.stringValue);
      case doubleValue:
        return (short)(int)this.doubleValue;
      case longValue:
        return (short)(int)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? 1 : 0;
    } 
    throw new IllegalStateException("Value cannot be converted to short: " + this.type);
  }
  
  public char asChar() {
    switch (this.type) {
      case stringValue:
        return (this.stringValue.length() == 0) ? '\000' : this.stringValue.charAt(0);
      case doubleValue:
        return (char)(int)this.doubleValue;
      case longValue:
        return (char)(int)this.longValue;
      case booleanValue:
        return (this.longValue != 0L) ? '\001' : '\000';
    } 
    throw new IllegalStateException("Value cannot be converted to char: " + this.type);
  }
  
  public String[] asStringArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    String[] array = new String[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      String v;
      switch (value.type) {
        case stringValue:
          v = value.stringValue;
          break;
        case doubleValue:
          v = (this.stringValue != null) ? this.stringValue : Double.toString(value.doubleValue);
          break;
        case longValue:
          v = (this.stringValue != null) ? this.stringValue : Long.toString(value.longValue);
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? "true" : "false";
          break;
        case nullValue:
          v = null;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to string: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public float[] asFloatArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    float[] array = new float[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      float v;
      switch (value.type) {
        case stringValue:
          v = Float.parseFloat(value.stringValue);
          break;
        case doubleValue:
          v = (float)value.doubleValue;
          break;
        case longValue:
          v = (float)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1.0F : 0.0F;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to float: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public double[] asDoubleArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    double[] array = new double[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      double v;
      switch (value.type) {
        case stringValue:
          v = Double.parseDouble(value.stringValue);
          break;
        case doubleValue:
          v = value.doubleValue;
          break;
        case longValue:
          v = (double)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1.0D : 0.0D;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to double: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public long[] asLongArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    long[] array = new long[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      long v;
      switch (value.type) {
        case stringValue:
          v = Long.parseLong(value.stringValue);
          break;
        case doubleValue:
          v = (long)value.doubleValue;
          break;
        case longValue:
          v = value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1L : 0L;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to long: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public int[] asIntArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    int[] array = new int[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      int v;
      switch (value.type) {
        case stringValue:
          v = Integer.parseInt(value.stringValue);
          break;
        case doubleValue:
          v = (int)value.doubleValue;
          break;
        case longValue:
          v = (int)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1 : 0;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to int: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public boolean[] asBooleanArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    boolean[] array = new boolean[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      boolean v;
      switch (value.type) {
        case stringValue:
          v = Boolean.parseBoolean(value.stringValue);
          break;
        case doubleValue:
          v = (value.doubleValue == 0.0D);
          break;
        case longValue:
          v = (value.longValue == 0L);
          break;
        case booleanValue:
          v = (value.longValue != 0L);
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to boolean: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public byte[] asByteArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    byte[] array = new byte[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      byte v;
      switch (value.type) {
        case stringValue:
          v = Byte.parseByte(value.stringValue);
          break;
        case doubleValue:
          v = (byte)(int)value.doubleValue;
          break;
        case longValue:
          v = (byte)(int)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1 : 0;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to byte: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public short[] asShortArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    short[] array = new short[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      short v;
      switch (value.type) {
        case stringValue:
          v = Short.parseShort(value.stringValue);
          break;
        case doubleValue:
          v = (short)(int)value.doubleValue;
          break;
        case longValue:
          v = (short)(int)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? 1 : 0;
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to short: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public char[] asCharArray() {
    if (this.type != ValueType.array)
      throw new IllegalStateException("Value is not an array: " + this.type); 
    char[] array = new char[this.size];
    int i = 0;
    for (JsonValue value = this.child; value != null; value = value.next, i++) {
      char v;
      switch (value.type) {
        case stringValue:
          v = (value.stringValue.length() == 0) ? '\000' : value.stringValue.charAt(0);
          break;
        case doubleValue:
          v = (char)(int)value.doubleValue;
          break;
        case longValue:
          v = (char)(int)value.longValue;
          break;
        case booleanValue:
          v = (value.longValue != 0L) ? '\001' : '\000';
          break;
        default:
          throw new IllegalStateException("Value cannot be converted to char: " + value.type);
      } 
      array[i] = v;
    } 
    return array;
  }
  
  public boolean hasChild(String name) {
    return (getChild(name) != null);
  }
  
  @Null
  public JsonValue getChild(String name) {
    JsonValue child = get(name);
    return (child == null) ? null : child.child;
  }
  
  public String getString(String name, @Null String defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asString();
  }
  
  public float getFloat(String name, float defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asFloat();
  }
  
  public double getDouble(String name, double defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asDouble();
  }
  
  public long getLong(String name, long defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asLong();
  }
  
  public int getInt(String name, int defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asInt();
  }
  
  public boolean getBoolean(String name, boolean defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asBoolean();
  }
  
  public byte getByte(String name, byte defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asByte();
  }
  
  public short getShort(String name, short defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asShort();
  }
  
  public char getChar(String name, char defaultValue) {
    JsonValue child = get(name);
    return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asChar();
  }
  
  public String getString(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asString();
  }
  
  public float getFloat(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asFloat();
  }
  
  public double getDouble(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asDouble();
  }
  
  public long getLong(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asLong();
  }
  
  public int getInt(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asInt();
  }
  
  public boolean getBoolean(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asBoolean();
  }
  
  public byte getByte(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asByte();
  }
  
  public short getShort(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asShort();
  }
  
  public char getChar(String name) {
    JsonValue child = get(name);
    if (child == null)
      throw new IllegalArgumentException("Named value not found: " + name); 
    return child.asChar();
  }
  
  public String getString(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asString();
  }
  
  public float getFloat(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asFloat();
  }
  
  public double getDouble(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asDouble();
  }
  
  public long getLong(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asLong();
  }
  
  public int getInt(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asInt();
  }
  
  public boolean getBoolean(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asBoolean();
  }
  
  public byte getByte(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asByte();
  }
  
  public short getShort(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asShort();
  }
  
  public char getChar(int index) {
    JsonValue child = get(index);
    if (child == null)
      throw new IllegalArgumentException("Indexed value not found: " + this.name); 
    return child.asChar();
  }
  
  public ValueType type() {
    return this.type;
  }
  
  public void setType(ValueType type) {
    if (type == null)
      throw new IllegalArgumentException("type cannot be null."); 
    this.type = type;
  }
  
  public boolean isArray() {
    return (this.type == ValueType.array);
  }
  
  public boolean isObject() {
    return (this.type == ValueType.object);
  }
  
  public boolean isString() {
    return (this.type == ValueType.stringValue);
  }
  
  public boolean isNumber() {
    return (this.type == ValueType.doubleValue || this.type == ValueType.longValue);
  }
  
  public boolean isDouble() {
    return (this.type == ValueType.doubleValue);
  }
  
  public boolean isLong() {
    return (this.type == ValueType.longValue);
  }
  
  public boolean isBoolean() {
    return (this.type == ValueType.booleanValue);
  }
  
  public boolean isNull() {
    return (this.type == ValueType.nullValue);
  }
  
  public boolean isValue() {
    switch (this.type) {
      case stringValue:
      case doubleValue:
      case longValue:
      case booleanValue:
      case nullValue:
        return true;
    } 
    return false;
  }
  
  @Null
  public String name() {
    return this.name;
  }
  
  public void setName(@Null String name) {
    this.name = name;
  }
  
  @Null
  public JsonValue parent() {
    return this.parent;
  }
  
  @Null
  public JsonValue child() {
    return this.child;
  }
  
  public void addChild(String name, JsonValue value) {
    if (name == null)
      throw new IllegalArgumentException("name cannot be null."); 
    value.name = name;
    addChild(value);
  }
  
  public void addChild(JsonValue value) {
    value.parent = this;
    this.size++;
    JsonValue current = this.child;
    if (current == null) {
      this.child = value;
    } else
      while (true) {
        if (current.next == null) {
          current.next = value;
          value.prev = current;
          return;
        } 
        current = current.next;
      }  
  }
  
  @Null
  public JsonValue next() {
    return this.next;
  }
  
  public void setNext(@Null JsonValue next) {
    this.next = next;
  }
  
  @Null
  public JsonValue prev() {
    return this.prev;
  }
  
  public void setPrev(@Null JsonValue prev) {
    this.prev = prev;
  }
  
  public void set(@Null String value) {
    this.stringValue = value;
    this.type = (value == null) ? ValueType.nullValue : ValueType.stringValue;
  }
  
  public void set(double value, @Null String stringValue) {
    this.doubleValue = value;
    this.longValue = (long)value;
    this.stringValue = stringValue;
    this.type = ValueType.doubleValue;
  }
  
  public void set(long value, @Null String stringValue) {
    this.longValue = value;
    this.doubleValue = (double)value;
    this.stringValue = stringValue;
    this.type = ValueType.longValue;
  }
  
  public void set(boolean value) {
    this.longValue = value ? 1L : 0L;
    this.type = ValueType.booleanValue;
  }
  
  public String toJson(JsonWriter.OutputType outputType) {
    if (isValue())
      return asString(); 
    StringBuilder buffer = new StringBuilder(512);
    json(this, buffer, outputType);
    return buffer.toString();
  }
  
  private void json(JsonValue object, StringBuilder buffer, JsonWriter.OutputType outputType) {
    if (object.isObject()) {
      if (object.child == null) {
        buffer.append("{}");
      } else {
        int start = buffer.length();
        buffer.append('{');
        int i = 0;
        for (JsonValue child = object.child; child != null; child = child.next) {
          buffer.append(outputType.quoteName(child.name));
          buffer.append(':');
          json(child, buffer, outputType);
          if (child.next != null)
            buffer.append(','); 
        } 
        buffer.append('}');
      } 
    } else if (object.isArray()) {
      if (object.child == null) {
        buffer.append("[]");
      } else {
        int start = buffer.length();
        buffer.append('[');
        for (JsonValue child = object.child; child != null; child = child.next) {
          json(child, buffer, outputType);
          if (child.next != null)
            buffer.append(','); 
        } 
        buffer.append(']');
      } 
    } else if (object.isString()) {
      buffer.append(outputType.quoteValue(object.asString()));
    } else if (object.isDouble()) {
      double doubleValue = object.asDouble();
      long longValue = object.asLong();
      buffer.append((doubleValue == (double)longValue) ? (double)longValue : doubleValue);
    } else if (object.isLong()) {
      buffer.append(object.asLong());
    } else if (object.isBoolean()) {
      buffer.append(object.asBoolean());
    } else if (object.isNull()) {
      buffer.append("null");
    } else
      throw new SerializationException("Unknown object type: " + object); 
  }
  
  public JsonIterator iterator() {
    return new JsonIterator();
  }
  
  public String toString() {
    if (isValue())
      return (this.name == null) ? asString() : (this.name + ": " + asString()); 
    return ((this.name == null) ? "" : (this.name + ": ")) + prettyPrint(JsonWriter.OutputType.minimal, 0);
  }
  
  public String trace() {
    String trace;
    if (this.parent == null) {
      if (this.type == ValueType.array)
        return "[]"; 
      if (this.type == ValueType.object)
        return "{}"; 
      return "";
    } 
    if (this.parent.type == ValueType.array) {
      trace = "[]";
      int i = 0;
      for (JsonValue child = this.parent.child; child != null; child = child.next, i++) {
        if (child == this) {
          trace = "[" + i + "]";
          break;
        } 
      } 
    } else if (this.name.indexOf('.') != -1) {
      trace = ".\"" + this.name.replace("\"", "\\\"") + "\"";
    } else
      trace = '.' + this.name; 
    return this.parent.trace() + trace;
  }
  
  public String prettyPrint(JsonWriter.OutputType outputType, int singleLineColumns) {
    PrettyPrintSettings settings = new PrettyPrintSettings();
    settings.outputType = outputType;
    settings.singleLineColumns = singleLineColumns;
    return prettyPrint(settings);
  }
  
  public String prettyPrint(PrettyPrintSettings settings) {
    StringBuilder buffer = new StringBuilder(512);
    prettyPrint(this, buffer, 0, settings);
    return buffer.toString();
  }
  
  private void prettyPrint(JsonValue object, StringBuilder buffer, int indent, PrettyPrintSettings settings) {
    JsonWriter.OutputType outputType = settings.outputType;
    if (object.isObject()) {
      if (object.child == null) {
        buffer.append("{}");
        return;
      } 
      boolean newLines = !isFlat(object);
      int start = buffer.length();
      label103: while (true) {
        buffer.append(newLines ? "{\n" : "{ ");
        int i = 0;
        for (JsonValue child = object.child; child != null; child = child.next) {
          if (newLines)
            indent(indent, buffer); 
          buffer.append(outputType.quoteName(child.name));
          buffer.append(": ");
          prettyPrint(child, buffer, indent + 1, settings);
          if ((!newLines || outputType != JsonWriter.OutputType.minimal) && child.next != null)
            buffer.append(','); 
          buffer.append(newLines ? '\n' : ' ');
          if (!newLines && buffer.length() - start > settings.singleLineColumns) {
            buffer.setLength(start);
            newLines = true;
            continue label103;
          } 
        } 
        break;
      } 
      if (newLines)
        indent(indent - 1, buffer); 
      buffer.append('}');
      return;
    } 
    if (object.isArray()) {
      if (object.child == null) {
        buffer.append("[]");
        return;
      } 
      boolean newLines = !isFlat(object);
      boolean wrap = (settings.wrapNumericArrays || !isNumeric(object));
      int start = buffer.length();
      label104: while (true) {
        buffer.append(newLines ? "[\n" : "[ ");
        for (JsonValue child = object.child; child != null; child = child.next) {
          if (newLines)
            indent(indent, buffer); 
          prettyPrint(child, buffer, indent + 1, settings);
          if ((!newLines || outputType != JsonWriter.OutputType.minimal) && child.next != null)
            buffer.append(','); 
          buffer.append(newLines ? '\n' : ' ');
          if (wrap && !newLines && buffer.length() - start > settings.singleLineColumns) {
            buffer.setLength(start);
            newLines = true;
            continue label104;
          } 
        } 
        break;
      } 
      if (newLines)
        indent(indent - 1, buffer); 
      buffer.append(']');
      return;
    } 
    if (object.isString()) {
      buffer.append(outputType.quoteValue(object.asString()));
      return;
    } 
    if (object.isDouble()) {
      double doubleValue = object.asDouble();
      long longValue = object.asLong();
      buffer.append((doubleValue == (double)longValue) ? (double)longValue : doubleValue);
      return;
    } 
    if (object.isLong()) {
      buffer.append(object.asLong());
      return;
    } 
    if (object.isBoolean()) {
      buffer.append(object.asBoolean());
      return;
    } 
    if (object.isNull()) {
      buffer.append("null");
      return;
    } 
    throw new SerializationException("Unknown object type: " + object);
  }
  
  public void prettyPrint(JsonWriter.OutputType outputType, Writer writer) throws IOException {
    PrettyPrintSettings settings = new PrettyPrintSettings();
    settings.outputType = outputType;
    prettyPrint(this, writer, 0, settings);
  }
  
  private void prettyPrint(JsonValue object, Writer writer, int indent, PrettyPrintSettings settings) throws IOException {
    JsonWriter.OutputType outputType = settings.outputType;
    if (object.isObject()) {
      if (object.child == null) {
        writer.append("{}");
      } else {
        boolean newLines = (!isFlat(object) || object.size > 6);
        writer.append(newLines ? "{\n" : "{ ");
        int i = 0;
        for (JsonValue child = object.child; child != null; child = child.next) {
          if (newLines)
            indent(indent, writer); 
          writer.append(outputType.quoteName(child.name));
          writer.append(": ");
          prettyPrint(child, writer, indent + 1, settings);
          if ((!newLines || outputType != JsonWriter.OutputType.minimal) && child.next != null)
            writer.append(','); 
          writer.append(newLines ? '\n' : ' ');
        } 
        if (newLines)
          indent(indent - 1, writer); 
        writer.append('}');
      } 
    } else if (object.isArray()) {
      if (object.child == null) {
        writer.append("[]");
      } else {
        boolean newLines = !isFlat(object);
        writer.append(newLines ? "[\n" : "[ ");
        int i = 0;
        for (JsonValue child = object.child; child != null; child = child.next) {
          if (newLines)
            indent(indent, writer); 
          prettyPrint(child, writer, indent + 1, settings);
          if ((!newLines || outputType != JsonWriter.OutputType.minimal) && child.next != null)
            writer.append(','); 
          writer.append(newLines ? '\n' : ' ');
        } 
        if (newLines)
          indent(indent - 1, writer); 
        writer.append(']');
      } 
    } else if (object.isString()) {
      writer.append(outputType.quoteValue(object.asString()));
    } else if (object.isDouble()) {
      double doubleValue = object.asDouble();
      long longValue = object.asLong();
      writer.append(Double.toString((doubleValue == (double)longValue) ? (double)longValue : doubleValue));
    } else if (object.isLong()) {
      writer.append(Long.toString(object.asLong()));
    } else if (object.isBoolean()) {
      writer.append(Boolean.toString(object.asBoolean()));
    } else if (object.isNull()) {
      writer.append("null");
    } else
      throw new SerializationException("Unknown object type: " + object); 
  }
  
  private static boolean isFlat(JsonValue object) {
    for (JsonValue child = object.child; child != null; child = child.next) {
      if (child.isObject() || child.isArray())
        return false; 
    } 
    return true;
  }
  
  private static boolean isNumeric(JsonValue object) {
    for (JsonValue child = object.child; child != null; child = child.next) {
      if (!child.isNumber())
        return false; 
    } 
    return true;
  }
  
  private static void indent(int count, StringBuilder buffer) {
    for (int i = 0; i < count; i++)
      buffer.append('\t'); 
  }
  
  private static void indent(int count, Writer buffer) throws IOException {
    for (int i = 0; i < count; i++)
      buffer.append('\t'); 
  }
  
  public class JsonIterator implements Iterator<JsonValue>, Iterable<JsonValue> {
    JsonValue entry = JsonValue.this.child;
    
    JsonValue current;
    
    public boolean hasNext() {
      return (this.entry != null);
    }
    
    public JsonValue next() {
      this.current = this.entry;
      if (this.current == null)
        throw new NoSuchElementException(); 
      this.entry = this.current.next;
      return this.current;
    }
    
    public void remove() {
      if (this.current.prev == null) {
        JsonValue.this.child = this.current.next;
        if (JsonValue.this.child != null)
          JsonValue.this.child.prev = null; 
      } else {
        this.current.prev.next = this.current.next;
        if (this.current.next != null)
          this.current.next.prev = this.current.prev; 
      } 
      JsonValue.this.size--;
    }
    
    public Iterator<JsonValue> iterator() {
      return this;
    }
  }
  
  public enum ValueType {
    object, array, stringValue, doubleValue, longValue, booleanValue, nullValue;
  }
  
  public static class PrettyPrintSettings {
    public JsonWriter.OutputType outputType;
    
    public int singleLineColumns;
    
    public boolean wrapNumericArrays;
  }
}
