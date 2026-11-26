package com.badlogic.gdx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

public final class PropertiesUtils {
  private static final int NONE = 0;
  
  private static final int SLASH = 1;
  
  private static final int UNICODE = 2;
  
  private static final int CONTINUE = 3;
  
  private static final int KEY_DONE = 4;
  
  private static final int IGNORE = 5;
  
  private static final String LINE_SEPARATOR = "\n";
  
  public static void load(ObjectMap<String, String> properties, Reader reader) throws IOException {
    if (properties == null)
      throw new NullPointerException("properties cannot be null"); 
    if (reader == null)
      throw new NullPointerException("reader cannot be null"); 
    int mode = 0, unicode = 0, count = 0;
    char[] buf = new char[40];
    int offset = 0, keyLength = -1;
    boolean firstChar = true;
    BufferedReader br = new BufferedReader(reader);
    label104: while (true) {
      int intVal = br.read();
      if (intVal == -1)
        break; 
      char nextChar = (char)intVal;
      if (offset == buf.length) {
        char[] newBuf = new char[buf.length * 2];
        System.arraycopy(buf, 0, newBuf, 0, offset);
        buf = newBuf;
      } 
      if (mode == 2) {
        int digit = Character.digit(nextChar, 16);
        if (digit >= 0) {
          unicode = (unicode << 4) + digit;
          if (++count < 4)
            continue; 
        } else if (count <= 4) {
          throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
        } 
        mode = 0;
        buf[offset++] = (char)unicode;
        if (nextChar != '\n')
          continue; 
      } 
      if (mode == 1) {
        mode = 0;
        switch (nextChar) {
          case '\r':
            mode = 3;
            continue;
          case '\n':
            mode = 5;
            continue;
          case 'b':
            nextChar = '\b';
            break;
          case 'f':
            nextChar = '\f';
            break;
          case 'n':
            nextChar = '\n';
            break;
          case 'r':
            nextChar = '\r';
            break;
          case 't':
            nextChar = '\t';
            break;
          case 'u':
            mode = 2;
            unicode = count = 0;
            continue;
        } 
      } else {
        switch (nextChar) {
          case '!':
          case '#':
            if (firstChar)
              while (true) {
                intVal = br.read();
                if (intVal == -1)
                  continue label104; 
                nextChar = (char)intVal;
                if (nextChar != '\r') {
                  if (nextChar == '\n')
                    continue label104; 
                  continue;
                } 
                continue label104;
              }  
            break;
          case '\n':
            if (mode == 3) {
              mode = 5;
              continue;
            } 
          case '\r':
            mode = 0;
            firstChar = true;
            if (offset > 0 || (offset == 0 && keyLength == 0)) {
              if (keyLength == -1)
                keyLength = offset; 
              String temp = new String(buf, 0, offset);
              properties.put(temp.substring(0, keyLength), temp.substring(keyLength));
            } 
            keyLength = -1;
            offset = 0;
            continue;
          case '\\':
            if (mode == 4)
              keyLength = offset; 
            mode = 1;
            continue;
          case ':':
          case '=':
            if (keyLength == -1) {
              mode = 0;
              keyLength = offset;
              continue;
            } 
            break;
        } 
        if (Character.isSpace(nextChar)) {
          if (mode == 3)
            mode = 5; 
          if (offset == 0 || offset == keyLength || mode == 5)
            continue; 
          if (keyLength == -1) {
            mode = 4;
            continue;
          } 
        } 
        if (mode == 5 || mode == 3)
          mode = 0; 
      } 
      firstChar = false;
      if (mode == 4) {
        keyLength = offset;
        mode = 0;
      } 
      buf[offset++] = nextChar;
    } 
    if (mode == 2 && count <= 4)
      throw new IllegalArgumentException("Invalid Unicode sequence: expected format \\uxxxx"); 
    if (keyLength == -1 && offset > 0)
      keyLength = offset; 
    if (keyLength >= 0) {
      String temp = new String(buf, 0, offset);
      String key = temp.substring(0, keyLength);
      String value = temp.substring(keyLength);
      if (mode == 1)
        value = value + "\000"; 
      properties.put(key, value);
    } 
  }
  
  public static void store(ObjectMap<String, String> properties, Writer writer, String comment) throws IOException {
    storeImpl(properties, writer, comment, false);
  }
  
  private static void storeImpl(ObjectMap<String, String> properties, Writer writer, String comment, boolean escapeUnicode) throws IOException {
    if (comment != null)
      writeComment(writer, comment); 
    writer.write("#");
    writer.write(new Date().toString());
    writer.write("\n");
    StringBuilder sb = new StringBuilder(200);
    for (ObjectMap.Entries<ObjectMap.Entry<String, String>> entries = properties.entries().iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<String, String> entry = entries.next();
      dumpString(sb, (String)entry.key, true, escapeUnicode);
      sb.append('=');
      dumpString(sb, (String)entry.value, false, escapeUnicode);
      writer.write("\n");
      writer.write(sb.toString());
      sb.setLength(0);
    } 
    writer.flush();
  }
  
  private static void dumpString(StringBuilder outBuffer, String string, boolean escapeSpace, boolean escapeUnicode) {
    int len = string.length();
    for (int i = 0; i < len; i++) {
      char ch = string.charAt(i);
      if (ch > '=' && ch < '\u007F') {
        outBuffer.append((ch == '\\') ? "\\\\" : ch);
      } else
        switch (ch) {
          case ' ':
            if (i == 0 || escapeSpace) {
              outBuffer.append("\\ ");
            } else
              outBuffer.append(ch); 
            break;
          case '\n':
            outBuffer.append("\\n");
            break;
          case '\r':
            outBuffer.append("\\r");
            break;
          case '\t':
            outBuffer.append("\\t");
            break;
          case '\f':
            outBuffer.append("\\f");
            break;
          case '!':
          case '#':
          case ':':
          case '=':
            outBuffer.append('\\').append(ch);
            break;
          default:
            if (((ch < ' ' || ch > '~')) & escapeUnicode) {
              String hex = Integer.toHexString(ch);
              outBuffer.append("\\u");
              for (int j = 0; j < 4 - hex.length(); j++)
                outBuffer.append('0'); 
              outBuffer.append(hex);
            } else
              outBuffer.append(ch); 
            break;
        }  
    } 
  }
  
  private static void writeComment(Writer writer, String comment) throws IOException {
    writer.write("#");
    int len = comment.length();
    int curIndex = 0;
    int lastIndex = 0;
    while (curIndex < len) {
      char c = comment.charAt(curIndex);
      if (c > 'ÿ' || c == '\n' || c == '\r') {
        if (lastIndex != curIndex)
          writer.write(comment.substring(lastIndex, curIndex)); 
        if (c > 'ÿ') {
          String hex = Integer.toHexString(c);
          writer.write("\\u");
          for (int j = 0; j < 4 - hex.length(); j++)
            writer.write(48); 
          writer.write(hex);
        } else {
          writer.write("\n");
          if (c == '\r' && curIndex != len - 1 && comment.charAt(curIndex + 1) == '\n')
            curIndex++; 
          if (curIndex == len - 1 || (comment.charAt(curIndex + 1) != '#' && comment.charAt(curIndex + 1) != '!'))
            writer.write("#"); 
        } 
        lastIndex = curIndex + 1;
      } 
      curIndex++;
    } 
    if (lastIndex != curIndex)
      writer.write(comment.substring(lastIndex, curIndex)); 
    writer.write("\n");
  }
}
