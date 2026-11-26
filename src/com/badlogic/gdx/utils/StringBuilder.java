package com.badlogic.gdx.utils;

import java.io.IOException;
import java.util.Arrays;

public class StringBuilder implements Appendable, CharSequence {
  static final int INITIAL_CAPACITY = 16;
  
  public char[] chars;
  
  public int length;
  
  private static final char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  
  public static int numChars(int value, int radix) {
    int result = (value < 0) ? 2 : 1;
    while ((value /= radix) != 0)
      result++; 
    return result;
  }
  
  public static int numChars(long value, int radix) {
    int result = (value < 0L) ? 2 : 1;
    while ((value /= (long)radix) != 0L)
      result++; 
    return result;
  }
  
  final char[] getValue() {
    return this.chars;
  }
  
  public StringBuilder() {
    this.chars = new char[16];
  }
  
  public StringBuilder(int capacity) {
    if (capacity < 0)
      throw new NegativeArraySizeException(); 
    this.chars = new char[capacity];
  }
  
  public StringBuilder(CharSequence seq) {
    this(seq.toString());
  }
  
  public StringBuilder(StringBuilder builder) {
    this.length = builder.length;
    this.chars = new char[this.length + 16];
    System.arraycopy(builder.chars, 0, this.chars, 0, this.length);
  }
  
  public StringBuilder(String string) {
    this.length = string.length();
    this.chars = new char[this.length + 16];
    string.getChars(0, this.length, this.chars, 0);
  }
  
  private void enlargeBuffer(int min) {
    int newSize = (this.chars.length >> 1) + this.chars.length + 2;
    char[] newData = new char[(min > newSize) ? min : newSize];
    System.arraycopy(this.chars, 0, newData, 0, this.length);
    this.chars = newData;
  }
  
  final void appendNull() {
    int newSize = this.length + 4;
    if (newSize > this.chars.length)
      enlargeBuffer(newSize); 
    this.chars[this.length++] = 'n';
    this.chars[this.length++] = 'u';
    this.chars[this.length++] = 'l';
    this.chars[this.length++] = 'l';
  }
  
  final void append0(char[] value) {
    int newSize = this.length + value.length;
    if (newSize > this.chars.length)
      enlargeBuffer(newSize); 
    System.arraycopy(value, 0, this.chars, this.length, value.length);
    this.length = newSize;
  }
  
  final void append0(char[] value, int offset, int length) {
    if (offset > value.length || offset < 0)
      throw new ArrayIndexOutOfBoundsException("Offset out of bounds: " + offset); 
    if (length < 0 || value.length - offset < length)
      throw new ArrayIndexOutOfBoundsException("Length out of bounds: " + length); 
    int newSize = this.length + length;
    if (newSize > this.chars.length)
      enlargeBuffer(newSize); 
    System.arraycopy(value, offset, this.chars, this.length, length);
    this.length = newSize;
  }
  
  final void append0(char ch) {
    if (this.length == this.chars.length)
      enlargeBuffer(this.length + 1); 
    this.chars[this.length++] = ch;
  }
  
  final void append0(String string) {
    if (string == null) {
      appendNull();
      return;
    } 
    int adding = string.length();
    int newSize = this.length + adding;
    if (newSize > this.chars.length)
      enlargeBuffer(newSize); 
    string.getChars(0, adding, this.chars, this.length);
    this.length = newSize;
  }
  
  final void append0(CharSequence s, int start, int end) {
    if (s == null)
      s = "null"; 
    if (start < 0 || end < 0 || start > end || end > s.length())
      throw new IndexOutOfBoundsException(); 
    append0(s.subSequence(start, end).toString());
  }
  
  public int capacity() {
    return this.chars.length;
  }
  
  public char charAt(int index) {
    if (index < 0 || index >= this.length)
      throw new StringIndexOutOfBoundsException(index); 
    return this.chars[index];
  }
  
  final void delete0(int start, int end) {
    if (start >= 0) {
      if (end > this.length)
        end = this.length; 
      if (end == start)
        return; 
      if (end > start) {
        int count = this.length - end;
        if (count >= 0)
          System.arraycopy(this.chars, end, this.chars, start, count); 
        this.length -= end - start;
        return;
      } 
    } 
    throw new StringIndexOutOfBoundsException();
  }
  
  final void deleteCharAt0(int location) {
    if (0 > location || location >= this.length)
      throw new StringIndexOutOfBoundsException(location); 
    int count = this.length - location - 1;
    if (count > 0)
      System.arraycopy(this.chars, location + 1, this.chars, location, count); 
    this.length--;
  }
  
  public void ensureCapacity(int min) {
    if (min > this.chars.length) {
      int twice = (this.chars.length << 1) + 2;
      enlargeBuffer((twice > min) ? twice : min);
    } 
  }
  
  public void getChars(int start, int end, char[] dest, int destStart) {
    if (start > this.length || end > this.length || start > end)
      throw new StringIndexOutOfBoundsException(); 
    System.arraycopy(this.chars, start, dest, destStart, end - start);
  }
  
  final void insert0(int index, char[] value) {
    if (0 > index || index > this.length)
      throw new StringIndexOutOfBoundsException(index); 
    if (value.length != 0) {
      move(value.length, index);
      System.arraycopy(value, 0, value, index, value.length);
      this.length += value.length;
    } 
  }
  
  final void insert0(int index, char[] value, int start, int length) {
    if (0 <= index && index <= length) {
      if (start >= 0 && 0 <= length && length <= value.length - start) {
        if (length != 0) {
          move(length, index);
          System.arraycopy(value, start, this.chars, index, length);
          this.length += length;
        } 
        return;
      } 
      throw new StringIndexOutOfBoundsException("offset " + start + ", length " + length + ", char[].length " + value.length);
    } 
    throw new StringIndexOutOfBoundsException(index);
  }
  
  final void insert0(int index, char ch) {
    if (0 > index || index > this.length)
      throw new ArrayIndexOutOfBoundsException(index); 
    move(1, index);
    this.chars[index] = ch;
    this.length++;
  }
  
  final void insert0(int index, String string) {
    if (0 <= index && index <= this.length) {
      if (string == null)
        string = "null"; 
      int min = string.length();
      if (min != 0) {
        move(min, index);
        string.getChars(0, min, this.chars, index);
        this.length += min;
      } 
    } else
      throw new StringIndexOutOfBoundsException(index); 
  }
  
  final void insert0(int index, CharSequence s, int start, int end) {
    if (s == null)
      s = "null"; 
    if (index < 0 || index > this.length || start < 0 || end < 0 || start > end || end > s.length())
      throw new IndexOutOfBoundsException(); 
    insert0(index, s.subSequence(start, end).toString());
  }
  
  public int length() {
    return this.length;
  }
  
  private void move(int size, int index) {
    if (this.chars.length - this.length >= size) {
      System.arraycopy(this.chars, index, this.chars, index + size, this.length - index);
      return;
    } 
    int a = this.length + size, b = (this.chars.length << 1) + 2;
    int newSize = (a > b) ? a : b;
    char[] newData = new char[newSize];
    System.arraycopy(this.chars, 0, newData, 0, index);
    System.arraycopy(this.chars, index, newData, index + size, this.length - index);
    this.chars = newData;
  }
  
  final void replace0(int start, int end, String string) {
    if (start >= 0) {
      if (end > this.length)
        end = this.length; 
      if (end > start) {
        int stringLength = string.length();
        int diff = end - start - stringLength;
        if (diff > 0) {
          System.arraycopy(this.chars, end, this.chars, start + stringLength, this.length - end);
        } else if (diff < 0) {
          move(-diff, end);
        } 
        string.getChars(0, stringLength, this.chars, start);
        this.length -= diff;
        return;
      } 
      if (start == end) {
        if (string == null)
          throw new NullPointerException(); 
        insert0(start, string);
        return;
      } 
    } 
    throw new StringIndexOutOfBoundsException();
  }
  
  final void reverse0() {
    if (this.length < 2)
      return; 
    int end = this.length - 1;
    char frontHigh = this.chars[0];
    char endLow = this.chars[end];
    boolean allowFrontSur = true, allowEndSur = true;
    for (int i = 0, mid = this.length / 2; i < mid; i++, end--) {
      char frontLow = this.chars[i + 1];
      char endHigh = this.chars[end - 1];
      boolean surAtFront = (allowFrontSur && frontLow >= '?' && frontLow <= '?' && frontHigh >= '?' && frontHigh <= '?');
      if (surAtFront && this.length < 3)
        return; 
      boolean surAtEnd = (allowEndSur && endHigh >= '?' && endHigh <= '?' && endLow >= '?' && endLow <= '?');
      allowFrontSur = allowEndSur = true;
      if (surAtFront == surAtEnd) {
        if (surAtFront) {
          this.chars[end] = frontLow;
          this.chars[end - 1] = frontHigh;
          this.chars[i] = endHigh;
          this.chars[i + 1] = endLow;
          frontHigh = this.chars[i + 2];
          endLow = this.chars[end - 2];
          i++;
          end--;
        } else {
          this.chars[end] = frontHigh;
          this.chars[i] = endLow;
          frontHigh = frontLow;
          endLow = endHigh;
        } 
      } else if (surAtFront) {
        this.chars[end] = frontLow;
        this.chars[i] = endLow;
        endLow = endHigh;
        allowFrontSur = false;
      } else {
        this.chars[end] = frontHigh;
        this.chars[i] = endHigh;
        frontHigh = frontLow;
        allowEndSur = false;
      } 
    } 
    if ((this.length & 0x1) == 1 && (!allowFrontSur || !allowEndSur))
      this.chars[end] = allowFrontSur ? endLow : frontHigh; 
  }
  
  public void setCharAt(int index, char ch) {
    if (0 > index || index >= this.length)
      throw new StringIndexOutOfBoundsException(index); 
    this.chars[index] = ch;
  }
  
  public void setLength(int newLength) {
    if (newLength < 0)
      throw new StringIndexOutOfBoundsException(newLength); 
    if (newLength > this.chars.length) {
      enlargeBuffer(newLength);
    } else if (this.length < newLength) {
      Arrays.fill(this.chars, this.length, newLength, '\000');
    } 
    this.length = newLength;
  }
  
  public String substring(int start) {
    if (0 <= start && start <= this.length) {
      if (start == this.length)
        return ""; 
      return new String(this.chars, start, this.length - start);
    } 
    throw new StringIndexOutOfBoundsException(start);
  }
  
  public String substring(int start, int end) {
    if (0 <= start && start <= end && end <= this.length) {
      if (start == end)
        return ""; 
      return new String(this.chars, start, end - start);
    } 
    throw new StringIndexOutOfBoundsException();
  }
  
  public String toString() {
    if (this.length == 0)
      return ""; 
    return new String(this.chars, 0, this.length);
  }
  
  public CharSequence subSequence(int start, int end) {
    return substring(start, end);
  }
  
  public int indexOf(String string) {
    return indexOf(string, 0);
  }
  
  public int indexOf(String subString, int start) {
    if (start < 0)
      start = 0; 
    int subCount = subString.length();
    if (subCount == 0)
      return (start < this.length || start == 0) ? start : this.length; 
    int maxIndex = this.length - subCount;
    if (start > maxIndex)
      return -1; 
    char firstChar = subString.charAt(0);
    while (true) {
      int i = start;
      boolean found = false;
      for (; i <= maxIndex; i++) {
        if (this.chars[i] == firstChar) {
          found = true;
          break;
        } 
      } 
      if (!found)
        return -1; 
      int o1 = i, o2 = 0;
      while (++o2 < subCount && this.chars[++o1] == subString.charAt(o2));
      if (o2 == subCount)
        return i; 
      start = i + 1;
    } 
  }
  
  public int indexOfIgnoreCase(String subString, int start) {
    if (start < 0)
      start = 0; 
    int subCount = subString.length();
    if (subCount == 0)
      return (start < this.length || start == 0) ? start : this.length; 
    int maxIndex = this.length - subCount;
    if (start > maxIndex)
      return -1; 
    char firstUpper = Character.toUpperCase(subString.charAt(0));
    char firstLower = Character.toLowerCase(firstUpper);
    while (true) {
      int i = start;
      boolean found = false;
      for (; i <= maxIndex; i++) {
        char c = this.chars[i];
        if (c == firstUpper || c == firstLower) {
          found = true;
          break;
        } 
      } 
      if (!found)
        return -1; 
      int o1 = i, o2 = 0;
      while (++o2 < subCount) {
        char c = this.chars[++o1];
        char upper = Character.toUpperCase(subString.charAt(o2));
        if (c != upper && c != Character.toLowerCase(upper))
          break; 
      } 
      if (o2 == subCount)
        return i; 
      start = i + 1;
    } 
  }
  
  public boolean contains(String subString) {
    return (indexOf(subString, 0) != -1);
  }
  
  public boolean containsIgnoreCase(String subString) {
    return (indexOfIgnoreCase(subString, 0) != -1);
  }
  
  public int lastIndexOf(String string) {
    return lastIndexOf(string, this.length);
  }
  
  public int lastIndexOf(String subString, int start) {
    int subCount = subString.length();
    if (subCount <= this.length && start >= 0) {
      if (subCount > 0) {
        if (start > this.length - subCount)
          start = this.length - subCount; 
        char firstChar = subString.charAt(0);
        while (true) {
          int i = start;
          boolean found = false;
          for (; i >= 0; i--) {
            if (this.chars[i] == firstChar) {
              found = true;
              break;
            } 
          } 
          if (!found)
            return -1; 
          int o1 = i, o2 = 0;
          while (++o2 < subCount && this.chars[++o1] == subString.charAt(o2));
          if (o2 == subCount)
            return i; 
          start = i - 1;
        } 
      } 
      return (start < this.length) ? start : this.length;
    } 
    return -1;
  }
  
  public void trimToSize() {
    if (this.length < this.chars.length) {
      char[] newValue = new char[this.length];
      System.arraycopy(this.chars, 0, newValue, 0, this.length);
      this.chars = newValue;
    } 
  }
  
  public int codePointAt(int index) {
    if (index < 0 || index >= this.length)
      throw new StringIndexOutOfBoundsException(index); 
    return Character.codePointAt(this.chars, index, this.length);
  }
  
  public int codePointBefore(int index) {
    if (index < 1 || index > this.length)
      throw new StringIndexOutOfBoundsException(index); 
    return Character.codePointBefore(this.chars, index);
  }
  
  public int codePointCount(int beginIndex, int endIndex) {
    if (beginIndex < 0 || endIndex > this.length || beginIndex > endIndex)
      throw new StringIndexOutOfBoundsException(); 
    return Character.codePointCount(this.chars, beginIndex, endIndex - beginIndex);
  }
  
  public int offsetByCodePoints(int index, int codePointOffset) {
    return Character.offsetByCodePoints(this.chars, 0, this.length, index, codePointOffset);
  }
  
  public StringBuilder append(boolean b) {
    append0(b ? "true" : "false");
    return this;
  }
  
  public StringBuilder append(char c) {
    append0(c);
    return this;
  }
  
  public StringBuilder append(int value) {
    return append(value, 0);
  }
  
  public StringBuilder append(int value, int minLength) {
    return append(value, minLength, '0');
  }
  
  public StringBuilder append(int value, int minLength, char prefix) {
    if (value == Integer.MIN_VALUE) {
      append0("-2147483648");
      return this;
    } 
    if (value < 0) {
      append0('-');
      value = -value;
    } 
    if (minLength > 1)
      for (int j = minLength - numChars(value, 10); j > 0; j--)
        append(prefix);  
    if (value >= 10000) {
      if (value >= 1000000000)
        append0(digits[(int)((long)value % 10000000000L / 1000000000L)]); 
      if (value >= 100000000)
        append0(digits[value % 1000000000 / 100000000]); 
      if (value >= 10000000)
        append0(digits[value % 100000000 / 10000000]); 
      if (value >= 1000000)
        append0(digits[value % 10000000 / 1000000]); 
      if (value >= 100000)
        append0(digits[value % 1000000 / 100000]); 
      append0(digits[value % 100000 / 10000]);
    } 
    if (value >= 1000)
      append0(digits[value % 10000 / 1000]); 
    if (value >= 100)
      append0(digits[value % 1000 / 100]); 
    if (value >= 10)
      append0(digits[value % 100 / 10]); 
    append0(digits[value % 10]);
    return this;
  }
  
  public StringBuilder append(long value) {
    return append(value, 0);
  }
  
  public StringBuilder append(long value, int minLength) {
    return append(value, minLength, '0');
  }
  
  public StringBuilder append(long value, int minLength, char prefix) {
    if (value == Long.MIN_VALUE) {
      append0("-9223372036854775808");
      return this;
    } 
    if (value < 0L) {
      append0('-');
      value = -value;
    } 
    if (minLength > 1)
      for (int j = minLength - numChars(value, 10); j > 0; j--)
        append(prefix);  
    if (value >= 10000L) {
      if (value >= 1000000000000000000L)
        append0(digits[(int)((double)value % 1.0E19D / 1.0E18D)]); 
      if (value >= 100000000000000000L)
        append0(digits[(int)(value % 1000000000000000000L / 100000000000000000L)]); 
      if (value >= 10000000000000000L)
        append0(digits[(int)(value % 100000000000000000L / 10000000000000000L)]); 
      if (value >= 1000000000000000L)
        append0(digits[(int)(value % 10000000000000000L / 1000000000000000L)]); 
      if (value >= 100000000000000L)
        append0(digits[(int)(value % 1000000000000000L / 100000000000000L)]); 
      if (value >= 10000000000000L)
        append0(digits[(int)(value % 100000000000000L / 10000000000000L)]); 
      if (value >= 1000000000000L)
        append0(digits[(int)(value % 10000000000000L / 1000000000000L)]); 
      if (value >= 100000000000L)
        append0(digits[(int)(value % 1000000000000L / 100000000000L)]); 
      if (value >= 10000000000L)
        append0(digits[(int)(value % 100000000000L / 10000000000L)]); 
      if (value >= 1000000000L)
        append0(digits[(int)(value % 10000000000L / 1000000000L)]); 
      if (value >= 100000000L)
        append0(digits[(int)(value % 1000000000L / 100000000L)]); 
      if (value >= 10000000L)
        append0(digits[(int)(value % 100000000L / 10000000L)]); 
      if (value >= 1000000L)
        append0(digits[(int)(value % 10000000L / 1000000L)]); 
      if (value >= 100000L)
        append0(digits[(int)(value % 1000000L / 100000L)]); 
      append0(digits[(int)(value % 100000L / 10000L)]);
    } 
    if (value >= 1000L)
      append0(digits[(int)(value % 10000L / 1000L)]); 
    if (value >= 100L)
      append0(digits[(int)(value % 1000L / 100L)]); 
    if (value >= 10L)
      append0(digits[(int)(value % 100L / 10L)]); 
    append0(digits[(int)(value % 10L)]);
    return this;
  }
  
  public StringBuilder append(float f) {
    append0(Float.toString(f));
    return this;
  }
  
  public StringBuilder append(double d) {
    append0(Double.toString(d));
    return this;
  }
  
  public StringBuilder append(Object obj) {
    if (obj == null) {
      appendNull();
    } else
      append0(obj.toString()); 
    return this;
  }
  
  public StringBuilder append(String str) {
    append0(str);
    return this;
  }
  
  public StringBuilder appendLine(String str) {
    append0(str);
    append0('\n');
    return this;
  }
  
  public StringBuilder append(char[] ch) {
    append0(ch);
    return this;
  }
  
  public StringBuilder append(char[] str, int offset, int len) {
    append0(str, offset, len);
    return this;
  }
  
  public StringBuilder append(CharSequence csq) {
    if (csq == null) {
      appendNull();
    } else if (csq instanceof StringBuilder) {
      StringBuilder builder = (StringBuilder)csq;
      append0(builder.chars, 0, builder.length);
    } else
      append0(csq.toString()); 
    return this;
  }
  
  public StringBuilder append(StringBuilder builder) {
    if (builder == null) {
      appendNull();
    } else
      append0(builder.chars, 0, builder.length); 
    return this;
  }
  
  public StringBuilder append(CharSequence csq, int start, int end) {
    append0(csq, start, end);
    return this;
  }
  
  public StringBuilder append(StringBuilder builder, int start, int end) {
    if (builder == null) {
      appendNull();
    } else
      append0(builder.chars, start, end); 
    return this;
  }
  
  public StringBuilder appendCodePoint(int codePoint) {
    append0(Character.toChars(codePoint));
    return this;
  }
  
  public StringBuilder delete(int start, int end) {
    delete0(start, end);
    return this;
  }
  
  public StringBuilder deleteCharAt(int index) {
    deleteCharAt0(index);
    return this;
  }
  
  public void clear() {
    this.length = 0;
  }
  
  public StringBuilder insert(int offset, boolean b) {
    insert0(offset, b ? "true" : "false");
    return this;
  }
  
  public StringBuilder insert(int offset, char c) {
    insert0(offset, c);
    return this;
  }
  
  public StringBuilder insert(int offset, int i) {
    insert0(offset, Integer.toString(i));
    return this;
  }
  
  public StringBuilder insert(int offset, long l) {
    insert0(offset, Long.toString(l));
    return this;
  }
  
  public StringBuilder insert(int offset, float f) {
    insert0(offset, Float.toString(f));
    return this;
  }
  
  public StringBuilder insert(int offset, double d) {
    insert0(offset, Double.toString(d));
    return this;
  }
  
  public StringBuilder insert(int offset, Object obj) {
    insert0(offset, (obj == null) ? "null" : obj.toString());
    return this;
  }
  
  public StringBuilder insert(int offset, String str) {
    insert0(offset, str);
    return this;
  }
  
  public StringBuilder insert(int offset, char[] ch) {
    insert0(offset, ch);
    return this;
  }
  
  public StringBuilder insert(int offset, char[] str, int strOffset, int strLen) {
    insert0(offset, str, strOffset, strLen);
    return this;
  }
  
  public StringBuilder insert(int offset, CharSequence s) {
    insert0(offset, (s == null) ? "null" : s.toString());
    return this;
  }
  
  public StringBuilder insert(int offset, CharSequence s, int start, int end) {
    insert0(offset, s, start, end);
    return this;
  }
  
  public StringBuilder replace(int start, int end, String str) {
    replace0(start, end, str);
    return this;
  }
  
  public StringBuilder replace(String find, String replace) {
    int findLength = find.length(), replaceLength = replace.length();
    int index = 0;
    while (true) {
      index = indexOf(find, index);
      if (index == -1)
        break; 
      replace0(index, index + findLength, replace);
      index += replaceLength;
    } 
    return this;
  }
  
  public StringBuilder replace(char find, String replace) {
    int replaceLength = replace.length();
    int index = 0;
    while (true) {
      if (index == this.length)
        return this; 
      if (this.chars[index] == find) {
        replace0(index, index + 1, replace);
        index += replaceLength;
        continue;
      } 
      index++;
    } 
  }
  
  public StringBuilder reverse() {
    reverse0();
    return this;
  }
  
  public boolean isEmpty() {
    return (this.length == 0);
  }
  
  public boolean notEmpty() {
    return (this.length != 0);
  }
  
  public int hashCode() {
    int result = 31 + this.length;
    for (int index = 0; index < this.length; index++)
      result = 31 * result + this.chars[index]; 
    return result;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    StringBuilder other = (StringBuilder)obj;
    int length = this.length;
    if (length != other.length)
      return false; 
    char[] chars = this.chars, chars2 = other.chars;
    for (int i = 0; i < length; i++) {
      if (chars[i] != chars2[i])
        return false; 
    } 
    return true;
  }
  
  public boolean equalsIgnoreCase(@Null StringBuilder other) {
    if (this == other)
      return true; 
    if (other == null)
      return false; 
    int length = this.length;
    if (length != other.length)
      return false; 
    char[] chars = this.chars, chars2 = other.chars;
    for (int i = 0; i < length; i++) {
      char c = chars[i];
      char upper = Character.toUpperCase(chars2[i]);
      if (c != upper && c != Character.toLowerCase(upper))
        return false; 
    } 
    return true;
  }
  
  public boolean equalsIgnoreCase(@Null String other) {
    if (other == null)
      return false; 
    int length = this.length;
    if (length != other.length())
      return false; 
    char[] chars = this.chars;
    for (int i = 0; i < length; i++) {
      char c = chars[i];
      char upper = Character.toUpperCase(other.charAt(i));
      if (c != upper && c != Character.toLowerCase(upper))
        return false; 
    } 
    return true;
  }
}
