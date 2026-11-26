package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Lwjgl3Preferences implements Preferences {
  private final Properties properties = new Properties();
  
  private final FileHandle file;
  
  public Lwjgl3Preferences(String name, String directory) {
    this(new Lwjgl3FileHandle(new File(directory, name), Files.FileType.External));
  }
  
  public Lwjgl3Preferences(FileHandle file) {
    this.file = file;
    if (!file.exists())
      return; 
    InputStream in = null;
    try {
      in = new BufferedInputStream(file.read());
      this.properties.loadFromXML(in);
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      StreamUtils.closeQuietly(in);
    } 
  }
  
  public Preferences putBoolean(String key, boolean val) {
    this.properties.put(key, Boolean.toString(val));
    return this;
  }
  
  public Preferences putInteger(String key, int val) {
    this.properties.put(key, Integer.toString(val));
    return this;
  }
  
  public Preferences putLong(String key, long val) {
    this.properties.put(key, Long.toString(val));
    return this;
  }
  
  public Preferences putFloat(String key, float val) {
    this.properties.put(key, Float.toString(val));
    return this;
  }
  
  public Preferences putString(String key, String val) {
    this.properties.put(key, val);
    return this;
  }
  
  public Preferences put(Map<String, ?> vals) {
    for (Map.Entry<String, ?> val : vals.entrySet()) {
      if (val.getValue() instanceof Boolean)
        putBoolean(val.getKey(), (Boolean)val.getValue()); 
      if (val.getValue() instanceof Integer)
        putInteger(val.getKey(), (Integer)val.getValue()); 
      if (val.getValue() instanceof Long)
        putLong(val.getKey(), (Long)val.getValue()); 
      if (val.getValue() instanceof String)
        putString(val.getKey(), (String)val.getValue()); 
      if (val.getValue() instanceof Float)
        putFloat(val.getKey(), (Float)val.getValue()); 
    } 
    return this;
  }
  
  public boolean getBoolean(String key) {
    return getBoolean(key, false);
  }
  
  public int getInteger(String key) {
    return getInteger(key, 0);
  }
  
  public long getLong(String key) {
    return getLong(key, 0L);
  }
  
  public float getFloat(String key) {
    return getFloat(key, 0.0F);
  }
  
  public String getString(String key) {
    return getString(key, "");
  }
  
  public boolean getBoolean(String key, boolean defValue) {
    return Boolean.parseBoolean(this.properties.getProperty(key, Boolean.toString(defValue)));
  }
  
  public int getInteger(String key, int defValue) {
    return Integer.parseInt(this.properties.getProperty(key, Integer.toString(defValue)));
  }
  
  public long getLong(String key, long defValue) {
    return Long.parseLong(this.properties.getProperty(key, Long.toString(defValue)));
  }
  
  public float getFloat(String key, float defValue) {
    return Float.parseFloat(this.properties.getProperty(key, Float.toString(defValue)));
  }
  
  public String getString(String key, String defValue) {
    return this.properties.getProperty(key, defValue);
  }
  
  public Map<String, ?> get() {
    Map<String, Object> map = new HashMap<>();
    for (Map.Entry<Object, Object> val : this.properties.entrySet()) {
      if (val.getValue() instanceof Boolean)
        map.put((String)val.getKey(), Boolean.valueOf(Boolean.parseBoolean((String)val.getValue()))); 
      if (val.getValue() instanceof Integer)
        map.put((String)val.getKey(), Integer.valueOf(Integer.parseInt((String)val.getValue()))); 
      if (val.getValue() instanceof Long)
        map.put((String)val.getKey(), Long.valueOf(Long.parseLong((String)val.getValue()))); 
      if (val.getValue() instanceof String)
        map.put((String)val.getKey(), val.getValue()); 
      if (val.getValue() instanceof Float)
        map.put((String)val.getKey(), Float.valueOf(Float.parseFloat((String)val.getValue()))); 
    } 
    return map;
  }
  
  public boolean contains(String key) {
    return this.properties.containsKey(key);
  }
  
  public void clear() {
    this.properties.clear();
  }
  
  public void flush() {
    OutputStream out = null;
    try {
      out = new BufferedOutputStream(this.file.write(false));
      this.properties.storeToXML(out, null);
    } catch (Exception ex) {
      throw new GdxRuntimeException("Error writing preferences: " + this.file, ex);
    } finally {
      StreamUtils.closeQuietly(out);
    } 
  }
  
  public void remove(String key) {
    this.properties.remove(key);
  }
}
