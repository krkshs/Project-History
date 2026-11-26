package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Json {
  private static final boolean debug = false;
  
  private JsonWriter writer;
  
  private String typeName = "class";
  
  private boolean usePrototypes = true;
  
  private JsonWriter.OutputType outputType;
  
  private boolean quoteLongValues;
  
  private boolean ignoreUnknownFields;
  
  private boolean ignoreDeprecated;
  
  private boolean readDeprecated;
  
  private boolean enumNames = true;
  
  private boolean sortFields;
  
  private Serializer defaultSerializer;
  
  private final ObjectMap<Class, OrderedMap<String, FieldMetadata>> typeToFields = new ObjectMap<>();
  
  private final ObjectMap<String, Class> tagToClass = new ObjectMap<>();
  
  private final ObjectMap<Class, String> classToTag = new ObjectMap<>();
  
  private final ObjectMap<Class, Serializer> classToSerializer = new ObjectMap<>();
  
  private final ObjectMap<Class, Object[]> classToDefaultValues = new ObjectMap<>();
  
  private final Object[] equals1 = new Object[] { null }, equals2 = new Object[] { null };
  
  public Json() {
    this.outputType = JsonWriter.OutputType.minimal;
  }
  
  public Json(JsonWriter.OutputType outputType) {
    this.outputType = outputType;
  }
  
  public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
    this.ignoreUnknownFields = ignoreUnknownFields;
  }
  
  public boolean getIgnoreUnknownFields() {
    return this.ignoreUnknownFields;
  }
  
  public void setIgnoreDeprecated(boolean ignoreDeprecated) {
    this.ignoreDeprecated = ignoreDeprecated;
  }
  
  public void setReadDeprecated(boolean readDeprecated) {
    this.readDeprecated = readDeprecated;
  }
  
  public void setOutputType(JsonWriter.OutputType outputType) {
    this.outputType = outputType;
  }
  
  public void setQuoteLongValues(boolean quoteLongValues) {
    this.quoteLongValues = quoteLongValues;
  }
  
  public void setEnumNames(boolean enumNames) {
    this.enumNames = enumNames;
  }
  
  public void addClassTag(String tag, Class type) {
    this.tagToClass.put(tag, type);
    this.classToTag.put(type, tag);
  }
  
  @Null
  public Class getClass(String tag) {
    return this.tagToClass.<String>get(tag);
  }
  
  @Null
  public String getTag(Class type) {
    return this.classToTag.<Class<?>>get(type);
  }
  
  public void setTypeName(@Null String typeName) {
    this.typeName = typeName;
  }
  
  public void setDefaultSerializer(@Null Serializer defaultSerializer) {
    this.defaultSerializer = defaultSerializer;
  }
  
  public <T> void setSerializer(Class<T> type, Serializer<T> serializer) {
    this.classToSerializer.put(type, serializer);
  }
  
  public <T> Serializer<T> getSerializer(Class<T> type) {
    return this.classToSerializer.<Class<T>>get(type);
  }
  
  public void setUsePrototypes(boolean usePrototypes) {
    this.usePrototypes = usePrototypes;
  }
  
  public void setElementType(Class type, String fieldName, Class elementType) {
    FieldMetadata metadata = getFields(type).get(fieldName);
    if (metadata == null)
      throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")"); 
    metadata.elementType = elementType;
  }
  
  public void setDeprecated(Class type, String fieldName, boolean deprecated) {
    FieldMetadata metadata = getFields(type).get(fieldName);
    if (metadata == null)
      throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")"); 
    metadata.deprecated = deprecated;
  }
  
  public void setSortFields(boolean sortFields) {
    this.sortFields = sortFields;
  }
  
  private OrderedMap<String, FieldMetadata> getFields(Class<Object> type) {
    OrderedMap<String, FieldMetadata> fields = this.typeToFields.<Class<?>>get(type);
    if (fields != null)
      return fields; 
    Array<Class<?>> classHierarchy = new Array<>();
    Class<Object> nextClass = type;
    while (nextClass != Object.class) {
      classHierarchy.add(nextClass);
      nextClass = (Class)nextClass.getSuperclass();
    } 
    ArrayList<Field> allFields = new ArrayList<>();
    for (int i = classHierarchy.size - 1; i >= 0; i--)
      java.util.Collections.<Field>addAll(allFields, ClassReflection.getDeclaredFields(classHierarchy.get(i))); 
    OrderedMap<String, FieldMetadata> nameToField = new OrderedMap<>(allFields.size());
    for (int j = 0, n = allFields.size(); j < n; j++) {
      Field field = allFields.get(j);
      if (!field.isTransient() && 
        !field.isStatic() && 
        !field.isSynthetic()) {
        if (!field.isAccessible())
          try {
            field.setAccessible(true);
          } catch (AccessControlException ex) {} 
        nameToField.put(field.getName(), new FieldMetadata(field));
      } 
    } 
    if (this.sortFields)
      nameToField.keys.sort(); 
    this.typeToFields.put(type, nameToField);
    return nameToField;
  }
  
  public String toJson(@Null Object object) {
    return toJson(object, (object == null) ? null : object.getClass(), (Class)null);
  }
  
  public String toJson(@Null Object object, @Null Class knownType) {
    return toJson(object, knownType, (Class)null);
  }
  
  public String toJson(@Null Object object, @Null Class knownType, @Null Class elementType) {
    StringWriter buffer = new StringWriter();
    toJson(object, knownType, elementType, buffer);
    return buffer.toString();
  }
  
  public void toJson(@Null Object object, FileHandle file) {
    toJson(object, (object == null) ? null : object.getClass(), null, file);
  }
  
  public void toJson(@Null Object object, @Null Class knownType, FileHandle file) {
    toJson(object, knownType, null, file);
  }
  
  public void toJson(@Null Object object, @Null Class knownType, @Null Class elementType, FileHandle file) {
    Writer writer = null;
    try {
      writer = file.writer(false, "UTF-8");
      toJson(object, knownType, elementType, writer);
    } catch (Exception ex) {
      throw new SerializationException("Error writing file: " + file, ex);
    } finally {
      StreamUtils.closeQuietly(writer);
    } 
  }
  
  public void toJson(@Null Object object, Writer writer) {
    toJson(object, (object == null) ? null : object.getClass(), null, writer);
  }
  
  public void toJson(@Null Object object, @Null Class knownType, Writer writer) {
    toJson(object, knownType, null, writer);
  }
  
  public void toJson(@Null Object object, @Null Class knownType, @Null Class elementType, Writer writer) {
    setWriter(writer);
    try {
      writeValue(object, knownType, elementType);
    } finally {
      StreamUtils.closeQuietly(this.writer);
      this.writer = null;
    } 
  }
  
  public void setWriter(Writer writer) {
    if (!(writer instanceof JsonWriter))
      writer = new JsonWriter(writer); 
    this.writer = (JsonWriter)writer;
    this.writer.setOutputType(this.outputType);
    this.writer.setQuoteLongValues(this.quoteLongValues);
  }
  
  public JsonWriter getWriter() {
    return this.writer;
  }
  
  public void writeFields(Object object) {
    Class<?> type = object.getClass();
    Object[] defaultValues = getDefaultValues(type);
    OrderedMap<String, FieldMetadata> fields = getFields(type);
    int defaultIndex = 0;
    Array<String> fieldNames = fields.orderedKeys();
    for (int i = 0, n = fieldNames.size; i < n; i++) {
      FieldMetadata metadata = fields.get(fieldNames.get(i));
      if (this.ignoreDeprecated && metadata.deprecated)
        continue; 
      Field field = metadata.field;
      try {
        Object value = field.get(object);
        if (defaultValues != null) {
          Object defaultValue = defaultValues[defaultIndex++];
          if (value == null && defaultValue == null)
            continue; 
          if (value != null && defaultValue != null) {
            if (value.equals(defaultValue))
              continue; 
            if (value.getClass().isArray() && defaultValue.getClass().isArray()) {
              this.equals1[0] = value;
              this.equals2[0] = defaultValue;
              if (Arrays.deepEquals(this.equals1, this.equals2))
                continue; 
            } 
          } 
        } 
        this.writer.name(field.getName());
        writeValue(value, field.getType(), metadata.elementType);
      } catch (ReflectionException ex) {
        throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex);
      } catch (SerializationException ex) {
        ex.addTrace(field + " (" + type.getName() + ")");
        throw ex;
      } catch (Exception runtimeEx) {
        SerializationException ex = new SerializationException(runtimeEx);
        ex.addTrace(field + " (" + type.getName() + ")");
        throw ex;
      } 
    } 
  }
  
  @Null
  private Object[] getDefaultValues(Class type) {
    Object object;
    if (!this.usePrototypes)
      return null; 
    if (this.classToDefaultValues.containsKey(type))
      return this.classToDefaultValues.<Class<?>>get(type); 
    try {
      object = newInstance(type);
    } catch (Exception ex) {
      this.classToDefaultValues.put(type, null);
      return null;
    } 
    OrderedMap<String, FieldMetadata> fields = getFields(type);
    Object[] values = new Object[fields.size];
    this.classToDefaultValues.put(type, values);
    int defaultIndex = 0;
    Array<String> fieldNames = fields.orderedKeys();
    for (int i = 0, n = fieldNames.size; i < n; i++) {
      FieldMetadata metadata = fields.get(fieldNames.get(i));
      if (!this.ignoreDeprecated || !metadata.deprecated) {
        Field field = metadata.field;
        try {
          values[defaultIndex++] = field.get(object);
        } catch (ReflectionException ex) {
          throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex);
        } catch (SerializationException ex) {
          ex.addTrace(field + " (" + type.getName() + ")");
          throw ex;
        } catch (RuntimeException runtimeEx) {
          SerializationException ex = new SerializationException(runtimeEx);
          ex.addTrace(field + " (" + type.getName() + ")");
          throw ex;
        } 
      } 
    } 
    return values;
  }
  
  public void writeField(Object object, String name) {
    writeField(object, name, name, null);
  }
  
  public void writeField(Object object, String name, @Null Class elementType) {
    writeField(object, name, name, elementType);
  }
  
  public void writeField(Object object, String fieldName, String jsonName) {
    writeField(object, fieldName, jsonName, null);
  }
  
  public void writeField(Object object, String fieldName, String jsonName, @Null Class elementType) {
    Class<?> type = object.getClass();
    FieldMetadata metadata = getFields(type).get(fieldName);
    if (metadata == null)
      throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")"); 
    Field field = metadata.field;
    if (elementType == null)
      elementType = metadata.elementType; 
    try {
      this.writer.name(jsonName);
      writeValue(field.get(object), field.getType(), elementType);
    } catch (ReflectionException ex) {
      throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex);
    } catch (SerializationException ex) {
      ex.addTrace(field + " (" + type.getName() + ")");
      throw ex;
    } catch (Exception runtimeEx) {
      SerializationException ex = new SerializationException(runtimeEx);
      ex.addTrace(field + " (" + type.getName() + ")");
      throw ex;
    } 
  }
  
  public void writeValue(String name, @Null Object value) {
    try {
      this.writer.name(name);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    if (value == null) {
      writeValue(value, null, null);
    } else
      writeValue(value, value.getClass(), null); 
  }
  
  public void writeValue(String name, @Null Object value, @Null Class knownType) {
    try {
      this.writer.name(name);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    writeValue(value, knownType, null);
  }
  
  public void writeValue(String name, @Null Object value, @Null Class knownType, @Null Class elementType) {
    try {
      this.writer.name(name);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    writeValue(value, knownType, elementType);
  }
  
  public void writeValue(@Null Object value) {
    if (value == null) {
      writeValue(value, null, null);
    } else
      writeValue(value, value.getClass(), null); 
  }
  
  public void writeValue(@Null Object value, @Null Class knownType) {
    writeValue(value, knownType, null);
  }
  
  public void writeValue(@Null Object value, @Null Class<ObjectMap> knownType, @Null Class<?> elementType) {
    try {
      Class<ObjectIntMap> clazz7;
      Class<ObjectFloatMap> clazz6;
      Class<ObjectSet> clazz5;
      Class<IntMap> clazz4;
      Class<LongMap> clazz3;
      Class<IntSet> clazz2;
      Class<ArrayMap> clazz1;
      Class<HashMap> clazz;
      if (value == null) {
        this.writer.value(null);
        return;
      } 
      if ((knownType != null && knownType.isPrimitive()) || knownType == String.class || knownType == Integer.class || knownType == Boolean.class || knownType == Float.class || knownType == Long.class || knownType == Double.class || knownType == Short.class || knownType == Byte.class || knownType == Character.class) {
        this.writer.value(value);
        return;
      } 
      Class<?> actualType = value.getClass();
      if (actualType.isPrimitive() || actualType == String.class || actualType == Integer.class || actualType == Boolean.class || actualType == Float.class || actualType == Long.class || actualType == Double.class || actualType == Short.class || actualType == Byte.class || actualType == Character.class) {
        writeObjectStart(actualType, null);
        writeValue("value", value);
        writeObjectEnd();
        return;
      } 
      if (value instanceof Serializable) {
        writeObjectStart(actualType, knownType);
        ((Serializable)value).write(this);
        writeObjectEnd();
        return;
      } 
      Serializer serializer = this.classToSerializer.<Class<?>>get(actualType);
      if (serializer != null) {
        serializer.write(this, value, knownType);
        return;
      } 
      if (value instanceof Array) {
        if (knownType != null && actualType != knownType && actualType != Array.class)
          throw new SerializationException("Serialization of an Array other than the known type is not supported.\nKnown type: " + knownType + "\nActual type: " + actualType); 
        writeArrayStart();
        Array array = (Array)value;
        for (int i = 0, n = array.size; i < n; i++)
          writeValue(array.get(i), elementType, null); 
        writeArrayEnd();
        return;
      } 
      if (value instanceof Queue) {
        if (knownType != null && actualType != knownType && actualType != Queue.class)
          throw new SerializationException("Serialization of a Queue other than the known type is not supported.\nKnown type: " + knownType + "\nActual type: " + actualType); 
        writeArrayStart();
        Queue queue = (Queue)value;
        for (int i = 0, n = queue.size; i < n; i++)
          writeValue(queue.get(i), elementType, null); 
        writeArrayEnd();
        return;
      } 
      if (value instanceof Collection) {
        if (this.typeName != null && actualType != ArrayList.class && (knownType == null || knownType != actualType)) {
          writeObjectStart(actualType, knownType);
          writeArrayStart("items");
          for (Object item : (Iterable<Object>)value)
            writeValue(item, elementType, null); 
          writeArrayEnd();
          writeObjectEnd();
        } else {
          writeArrayStart();
          for (Object item : (Iterable<Object>)value)
            writeValue(item, elementType, null); 
          writeArrayEnd();
        } 
        return;
      } 
      if (actualType.isArray()) {
        if (elementType == null)
          elementType = actualType.getComponentType(); 
        int length = ArrayReflection.getLength(value);
        writeArrayStart();
        for (int i = 0; i < length; i++)
          writeValue(ArrayReflection.get(value, i), elementType, null); 
        writeArrayEnd();
        return;
      } 
      if (value instanceof ObjectMap) {
        if (knownType == null)
          knownType = ObjectMap.class; 
        writeObjectStart(actualType, knownType);
        for (ObjectMap.Entries<ObjectMap.Entry> entries = ((ObjectMap)value).entries().iterator(); entries.hasNext(); ) {
          ObjectMap.Entry entry = entries.next();
          this.writer.name(convertToString(entry.key));
          writeValue(entry.value, elementType, null);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof ObjectIntMap) {
        if (knownType == null)
          clazz7 = ObjectIntMap.class; 
        writeObjectStart(actualType, clazz7);
        for (ObjectIntMap.Entries<ObjectIntMap.Entry> entries = ((ObjectIntMap)value).entries().iterator(); entries.hasNext(); ) {
          ObjectIntMap.Entry entry = entries.next();
          this.writer.name(convertToString(entry.key));
          writeValue(Integer.valueOf(entry.value), Integer.class);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof ObjectFloatMap) {
        if (clazz7 == null)
          clazz6 = ObjectFloatMap.class; 
        writeObjectStart(actualType, clazz6);
        for (ObjectFloatMap.Entries<ObjectFloatMap.Entry> entries = ((ObjectFloatMap)value).entries().iterator(); entries.hasNext(); ) {
          ObjectFloatMap.Entry entry = entries.next();
          this.writer.name(convertToString(entry.key));
          writeValue(Float.valueOf(entry.value), Float.class);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof ObjectSet) {
        if (clazz6 == null)
          clazz5 = ObjectSet.class; 
        writeObjectStart(actualType, clazz5);
        this.writer.name("values");
        writeArrayStart();
        for (ObjectSet.ObjectSetIterator objectSetIterator = ((ObjectSet)value).iterator(); objectSetIterator.hasNext(); ) {
          Object entry = objectSetIterator.next();
          writeValue(entry, elementType, null);
        } 
        writeArrayEnd();
        writeObjectEnd();
        return;
      } 
      if (value instanceof IntMap) {
        if (clazz5 == null)
          clazz4 = IntMap.class; 
        writeObjectStart(actualType, clazz4);
        for (IntMap.Entry entry : (Iterable<IntMap.Entry>)((IntMap)value).entries()) {
          this.writer.name(String.valueOf(entry.key));
          writeValue(entry.value, elementType, null);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof LongMap) {
        if (clazz4 == null)
          clazz3 = LongMap.class; 
        writeObjectStart(actualType, clazz3);
        for (LongMap.Entry entry : (Iterable<LongMap.Entry>)((LongMap)value).entries()) {
          this.writer.name(String.valueOf(entry.key));
          writeValue(entry.value, elementType, null);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof IntSet) {
        if (clazz3 == null)
          clazz2 = IntSet.class; 
        writeObjectStart(actualType, clazz2);
        this.writer.name("values");
        writeArrayStart();
        for (IntSet.IntSetIterator iter = ((IntSet)value).iterator(); iter.hasNext;)
          writeValue(Integer.valueOf(iter.next()), Integer.class, null); 
        writeArrayEnd();
        writeObjectEnd();
        return;
      } 
      if (value instanceof ArrayMap) {
        if (clazz2 == null)
          clazz1 = ArrayMap.class; 
        writeObjectStart(actualType, clazz1);
        ArrayMap map = (ArrayMap)value;
        for (int i = 0, n = map.size; i < n; i++) {
          this.writer.name(convertToString(map.keys[i]));
          writeValue(map.values[i], elementType, null);
        } 
        writeObjectEnd();
        return;
      } 
      if (value instanceof Map) {
        if (clazz1 == null)
          clazz = HashMap.class; 
        writeObjectStart(actualType, clazz);
        for (Map.Entry entry : (Iterable<Map.Entry>)((Map)value).entrySet()) {
          this.writer.name(convertToString(entry.getKey()));
          writeValue(entry.getValue(), elementType, null);
        } 
        writeObjectEnd();
        return;
      } 
      if (ClassReflection.isAssignableFrom(Enum.class, actualType)) {
        if (this.typeName != null && (clazz == null || clazz != actualType)) {
          if (actualType.getEnumConstants() == null)
            actualType = actualType.getSuperclass(); 
          writeObjectStart(actualType, null);
          this.writer.name("value");
          this.writer.value(convertToString((Enum)value));
          writeObjectEnd();
        } else
          this.writer.value(convertToString((Enum)value)); 
        return;
      } 
      writeObjectStart(actualType, clazz);
      writeFields(value);
      writeObjectEnd();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeObjectStart(String name) {
    try {
      this.writer.name(name);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    writeObjectStart();
  }
  
  public void writeObjectStart(String name, Class actualType, @Null Class knownType) {
    try {
      this.writer.name(name);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    writeObjectStart(actualType, knownType);
  }
  
  public void writeObjectStart() {
    try {
      this.writer.object();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeObjectStart(Class actualType, @Null Class knownType) {
    try {
      this.writer.object();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
    if (knownType == null || knownType != actualType)
      writeType(actualType); 
  }
  
  public void writeObjectEnd() {
    try {
      this.writer.pop();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeArrayStart(String name) {
    try {
      this.writer.name(name);
      this.writer.array();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeArrayStart() {
    try {
      this.writer.array();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeArrayEnd() {
    try {
      this.writer.pop();
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  public void writeType(Class type) {
    if (this.typeName == null)
      return; 
    String className = getTag(type);
    if (className == null)
      className = type.getName(); 
    try {
      this.writer.set(this.typeName, className);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    } 
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Reader reader) {
    return readValue(type, null, new JsonReader().parse(reader));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Class elementType, Reader reader) {
    return readValue(type, elementType, new JsonReader().parse(reader));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, InputStream input) {
    return readValue(type, null, new JsonReader().parse(input));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Class elementType, InputStream input) {
    return readValue(type, elementType, new JsonReader().parse(input));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, FileHandle file) {
    try {
      return readValue(type, null, new JsonReader().parse(file));
    } catch (Exception ex) {
      throw new SerializationException("Error reading file: " + file, ex);
    } 
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Class elementType, FileHandle file) {
    try {
      return readValue(type, elementType, new JsonReader().parse(file));
    } catch (Exception ex) {
      throw new SerializationException("Error reading file: " + file, ex);
    } 
  }
  
  @Null
  public <T> T fromJson(Class<T> type, char[] data, int offset, int length) {
    return readValue(type, null, new JsonReader().parse(data, offset, length));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Class elementType, char[] data, int offset, int length) {
    return readValue(type, elementType, new JsonReader().parse(data, offset, length));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, String json) {
    return readValue(type, null, new JsonReader().parse(json));
  }
  
  @Null
  public <T> T fromJson(Class<T> type, Class elementType, String json) {
    return readValue(type, elementType, new JsonReader().parse(json));
  }
  
  public void readField(Object object, String name, JsonValue jsonData) {
    readField(object, name, name, null, jsonData);
  }
  
  public void readField(Object object, String name, @Null Class elementType, JsonValue jsonData) {
    readField(object, name, name, elementType, jsonData);
  }
  
  public void readField(Object object, String fieldName, String jsonName, JsonValue jsonData) {
    readField(object, fieldName, jsonName, null, jsonData);
  }
  
  public void readField(Object object, String fieldName, String jsonName, @Null Class elementType, JsonValue jsonMap) {
    Class<?> type = object.getClass();
    FieldMetadata metadata = getFields(type).get(fieldName);
    if (metadata == null)
      throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")"); 
    Field field = metadata.field;
    if (elementType == null)
      elementType = metadata.elementType; 
    readField(object, field, jsonName, elementType, jsonMap);
  }
  
  public void readField(@Null Object object, Field field, String jsonName, @Null Class elementType, JsonValue jsonMap) {
    JsonValue jsonValue = jsonMap.get(jsonName);
    if (jsonValue == null)
      return; 
    try {
      field.set(object, readValue(field.getType(), elementType, jsonValue));
    } catch (ReflectionException ex) {
      throw new SerializationException("Error accessing field: " + 
          field.getName() + " (" + field.getDeclaringClass().getName() + ")", ex);
    } catch (SerializationException ex) {
      ex.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + ")");
      throw ex;
    } catch (RuntimeException runtimeEx) {
      SerializationException ex = new SerializationException(runtimeEx);
      ex.addTrace(jsonValue.trace());
      ex.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + ")");
      throw ex;
    } 
  }
  
  public void readFields(Object object, JsonValue jsonMap) {
    Class<?> type = object.getClass();
    OrderedMap<String, FieldMetadata> fields = getFields(type);
    for (JsonValue child = jsonMap.child; child != null; child = child.next) {
      FieldMetadata metadata = fields.get(child.name().replace(" ", "_"));
      if (metadata == null) {
        if (!child.name.equals(this.typeName) && 
          !this.ignoreUnknownFields && !ignoreUnknownField(type, child.name)) {
          SerializationException ex = new SerializationException("Field not found: " + child.name + " (" + 
              type.getName() + ")");
          ex.addTrace(child.trace());
          throw ex;
        } 
      } else if (!this.ignoreDeprecated || this.readDeprecated || !metadata.deprecated) {
        Field field = metadata.field;
        try {
          field.set(object, readValue(field.getType(), metadata.elementType, child));
        } catch (ReflectionException ex) {
          throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex);
        } catch (SerializationException ex) {
          ex.addTrace(field.getName() + " (" + type.getName() + ")");
          throw ex;
        } catch (RuntimeException runtimeEx) {
          SerializationException ex = new SerializationException(runtimeEx);
          ex.addTrace(child.trace());
          ex.addTrace(field.getName() + " (" + type.getName() + ")");
          throw ex;
        } 
      } 
    } 
  }
  
  protected boolean ignoreUnknownField(Class type, String fieldName) {
    return false;
  }
  
  @Null
  public <T> T readValue(String name, @Null Class<T> type, JsonValue jsonMap) {
    return readValue(type, null, jsonMap.get(name));
  }
  
  @Null
  public <T> T readValue(String name, @Null Class<T> type, T defaultValue, JsonValue jsonMap) {
    JsonValue jsonValue = jsonMap.get(name);
    if (jsonValue == null)
      return defaultValue; 
    return readValue(type, null, jsonValue);
  }
  
  @Null
  public <T> T readValue(String name, @Null Class<T> type, @Null Class elementType, JsonValue jsonMap) {
    return readValue(type, elementType, jsonMap.get(name));
  }
  
  @Null
  public <T> T readValue(String name, @Null Class<T> type, @Null Class elementType, T defaultValue, JsonValue jsonMap) {
    JsonValue jsonValue = jsonMap.get(name);
    return readValue(type, elementType, defaultValue, jsonValue);
  }
  
  @Null
  public <T> T readValue(@Null Class<T> type, @Null Class elementType, T defaultValue, JsonValue jsonData) {
    if (jsonData == null)
      return defaultValue; 
    return readValue(type, elementType, jsonData);
  }
  
  @Null
  public <T> T readValue(@Null Class<T> type, JsonValue jsonData) {
    return readValue(type, null, jsonData);
  }
  
  @Null
  public <T> T readValue(@Null Class<T> type, @Null Class<?> elementType, JsonValue jsonData) {
    Class<Array> clazz;
    if (jsonData == null)
      return null; 
    if (jsonData.isObject()) {
      String className = (this.typeName == null) ? null : jsonData.getString(this.typeName, null);
      if (className != null) {
        type = getClass(className);
        if (type == null)
          try {
            type = ClassReflection.forName(className);
          } catch (ReflectionException ex) {
            throw new SerializationException(ex);
          }  
      } 
      if (type == null) {
        if (this.defaultSerializer != null)
          return this.defaultSerializer.read(this, jsonData, type); 
        return (T)jsonData;
      } 
      if (this.typeName != null && ClassReflection.isAssignableFrom(Collection.class, type)) {
        jsonData = jsonData.get("items");
        if (jsonData == null)
          throw new SerializationException("Unable to convert object to collection: " + jsonData + " (" + 
              type.getName() + ")"); 
      } else {
        Serializer<T> serializer = this.classToSerializer.<Class<T>>get(type);
        if (serializer != null)
          return serializer.read(this, jsonData, type); 
        if (type == String.class || type == Integer.class || type == Boolean.class || type == Float.class || type == Long.class || type == Double.class || type == Short.class || type == Byte.class || type == Character.class || 
          
          ClassReflection.isAssignableFrom(Enum.class, type))
          return readValue("value", type, jsonData); 
        Object object = newInstance(type);
        if (object instanceof Serializable) {
          ((Serializable)object).read(this, jsonData);
          return (T)object;
        } 
        if (object instanceof ObjectMap) {
          ObjectMap result = (ObjectMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(child.name, readValue(elementType, null, child)); 
          return (T)result;
        } 
        if (object instanceof ObjectIntMap) {
          ObjectIntMap<String> result = (ObjectIntMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(child.name, readValue(Integer.class, null, child)); 
          return (T)result;
        } 
        if (object instanceof ObjectFloatMap) {
          ObjectFloatMap<String> result = (ObjectFloatMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(child.name, readValue(Float.class, null, child)); 
          return (T)result;
        } 
        if (object instanceof ObjectSet) {
          ObjectSet result = (ObjectSet)object;
          for (JsonValue child = jsonData.getChild("values"); child != null; child = child.next)
            result.add(readValue(elementType, null, child)); 
          return (T)result;
        } 
        if (object instanceof IntMap) {
          IntMap result = (IntMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(Integer.parseInt(child.name), readValue(elementType, null, child)); 
          return (T)result;
        } 
        if (object instanceof LongMap) {
          LongMap result = (LongMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(Long.parseLong(child.name), readValue(elementType, null, child)); 
          return (T)result;
        } 
        if (object instanceof IntSet) {
          IntSet result = (IntSet)object;
          for (JsonValue child = jsonData.getChild("values"); child != null; child = child.next)
            result.add(child.asInt()); 
          return (T)result;
        } 
        if (object instanceof ArrayMap) {
          ArrayMap result = (ArrayMap)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next)
            result.put(child.name, readValue(elementType, null, child)); 
          return (T)result;
        } 
        if (object instanceof Map) {
          Map result = (Map)object;
          for (JsonValue child = jsonData.child; child != null; child = child.next) {
            if (!child.name.equals(this.typeName))
              result.put(child.name, readValue(elementType, null, child)); 
          } 
          return (T)result;
        } 
        readFields(object, jsonData);
        return (T)object;
      } 
    } 
    if (type != null) {
      Serializer<T> serializer = this.classToSerializer.<Class<T>>get(type);
      if (serializer != null)
        return serializer.read(this, jsonData, type); 
      if (ClassReflection.isAssignableFrom(Serializable.class, type)) {
        Object object = newInstance(type);
        ((Serializable)object).read(this, jsonData);
        return (T)object;
      } 
    } 
    if (jsonData.isArray()) {
      if (type == null || type == Object.class)
        clazz = Array.class; 
      if (ClassReflection.isAssignableFrom(Array.class, clazz)) {
        Array result = (clazz == Array.class) ? new Array() : (Array)newInstance(clazz);
        for (JsonValue child = jsonData.child; child != null; child = child.next)
          result.add(readValue(elementType, null, child)); 
        return (T)result;
      } 
      if (ClassReflection.isAssignableFrom(Queue.class, clazz)) {
        Queue result = (clazz == Queue.class) ? new Queue() : (Queue)newInstance(clazz);
        for (JsonValue child = jsonData.child; child != null; child = child.next)
          result.addLast(readValue(elementType, null, child)); 
        return (T)result;
      } 
      if (ClassReflection.isAssignableFrom(Collection.class, clazz)) {
        Collection result = clazz.isInterface() ? new ArrayList() : (Collection)newInstance(clazz);
        for (JsonValue child = jsonData.child; child != null; child = child.next)
          result.add(readValue(elementType, null, child)); 
        return (T)result;
      } 
      if (clazz.isArray()) {
        Class<?> componentType = clazz.getComponentType();
        if (elementType == null)
          elementType = componentType; 
        Object result = ArrayReflection.newInstance(componentType, jsonData.size);
        int i = 0;
        for (JsonValue child = jsonData.child; child != null; child = child.next)
          ArrayReflection.set(result, i++, readValue(elementType, null, child)); 
        return (T)result;
      } 
      throw new SerializationException("Unable to convert value to required type: " + jsonData + " (" + clazz.getName() + ")");
    } 
    if (jsonData.isNumber()) {
      try {
        if (clazz == null || clazz == float.class || clazz == Float.class)
          return (T)jsonData.asFloat(); 
        if (clazz == int.class || clazz == Integer.class)
          return (T)jsonData.asInt(); 
        if (clazz == long.class || clazz == Long.class)
          return (T)jsonData.asLong(); 
        if (clazz == double.class || clazz == Double.class)
          return (T)jsonData.asDouble(); 
        if (clazz == String.class)
          return (T)jsonData.asString(); 
        if (clazz == short.class || clazz == Short.class)
          return (T)jsonData.asShort(); 
        if (clazz == byte.class || clazz == Byte.class)
          return (T)jsonData.asByte(); 
      } catch (NumberFormatException e) {}
      jsonData = new JsonValue(jsonData.asString());
    } 
    if (jsonData.isBoolean()) {
      try {
        if (clazz == null || clazz == boolean.class || clazz == Boolean.class)
          return (T)jsonData.asBoolean(); 
      } catch (NumberFormatException e) {}
      jsonData = new JsonValue(jsonData.asString());
    } 
    if (jsonData.isString()) {
      String string = jsonData.asString();
      if (clazz == null || clazz == String.class)
        return (T)string; 
      try {
        if (clazz == int.class || clazz == Integer.class)
          return (T)Integer.valueOf(string); 
        if (clazz == float.class || clazz == Float.class)
          return (T)Float.valueOf(string); 
        if (clazz == long.class || clazz == Long.class)
          return (T)Long.valueOf(string); 
        if (clazz == double.class || clazz == Double.class)
          return (T)Double.valueOf(string); 
        if (clazz == short.class || clazz == Short.class)
          return (T)Short.valueOf(string); 
        if (clazz == byte.class || clazz == Byte.class)
          return (T)Byte.valueOf(string); 
      } catch (NumberFormatException e) {}
      if (clazz == boolean.class || clazz == Boolean.class)
        return (T)Boolean.valueOf(string); 
      if (clazz == char.class || clazz == Character.class)
        return (T)string.charAt(0); 
      if (ClassReflection.isAssignableFrom(Enum.class, clazz)) {
        Enum[] constants = (Enum[])clazz.getEnumConstants();
        for (int i = 0, n = constants.length; i < n; i++) {
          Enum e = constants[i];
          if (string.equals(convertToString(e)))
            return (T)e; 
        } 
      } 
      if (clazz == CharSequence.class)
        return (T)string; 
      throw new SerializationException("Unable to convert value to required type: " + jsonData + " (" + clazz.getName() + ")");
    } 
    return null;
  }
  
  public void copyFields(Object from, Object to) {
    OrderedMap<String, FieldMetadata> toFields = getFields(to.getClass());
    for (ObjectMap.Entries<String, FieldMetadata> entries = getFields(from.getClass()).iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<String, FieldMetadata> entry = (ObjectMap.Entry<String, FieldMetadata>)entries.next();
      FieldMetadata toField = toFields.get(entry.key);
      Field fromField = ((FieldMetadata)entry.value).field;
      if (toField == null)
        throw new SerializationException("To object is missing field: " + (String)entry.key); 
      try {
        toField.field.set(to, fromField.get(from));
      } catch (ReflectionException ex) {
        throw new SerializationException("Error copying field: " + fromField.getName(), ex);
      } 
    } 
  }
  
  private String convertToString(Enum e) {
    return this.enumNames ? e.name() : e.toString();
  }
  
  private String convertToString(Object object) {
    if (object instanceof Enum)
      return convertToString((Enum)object); 
    if (object instanceof Class)
      return ((Class)object).getName(); 
    return String.valueOf(object);
  }
  
  protected Object newInstance(Class type) {
    try {
      return ClassReflection.newInstance(type);
    } catch (Exception ex) {
      try {
        Constructor constructor = ClassReflection.getDeclaredConstructor(type, new Class<?>[0]);
        constructor.setAccessible(true);
        return constructor.newInstance();
      } catch (SecurityException e) {
      
      } catch (ReflectionException ignored) {
        if (ClassReflection.isAssignableFrom(Enum.class, type)) {
          if (type.getEnumConstants() == null)
            type = type.getSuperclass(); 
          return type.getEnumConstants()[0];
        } 
        if (type.isArray())
          throw new SerializationException("Encountered JSON object when expected array of type: " + type.getName(), ex); 
        if (ClassReflection.isMemberClass(type) && !ClassReflection.isStaticClass(type))
          throw new SerializationException("Class cannot be created (non-static member class): " + type.getName(), ex); 
        throw new SerializationException("Class cannot be created (missing no-arg constructor): " + type.getName(), ex);
      } catch (Exception privateConstructorException) {
        ex = privateConstructorException;
      } 
      throw new SerializationException("Error constructing instance of class: " + type.getName(), ex);
    } 
  }
  
  public String prettyPrint(@Null Object object) {
    return prettyPrint(object, 0);
  }
  
  public String prettyPrint(String json) {
    return prettyPrint(json, 0);
  }
  
  public String prettyPrint(@Null Object object, int singleLineColumns) {
    return prettyPrint(toJson(object), singleLineColumns);
  }
  
  public String prettyPrint(String json, int singleLineColumns) {
    return new JsonReader().parse(json).prettyPrint(this.outputType, singleLineColumns);
  }
  
  public String prettyPrint(@Null Object object, JsonValue.PrettyPrintSettings settings) {
    return prettyPrint(toJson(object), settings);
  }
  
  public String prettyPrint(String json, JsonValue.PrettyPrintSettings settings) {
    return new JsonReader().parse(json).prettyPrint(settings);
  }
  
  private static class FieldMetadata {
    final Field field;
    
    Class elementType;
    
    boolean deprecated;
    
    public FieldMetadata(Field field) {
      this.field = field;
      int index = (ClassReflection.isAssignableFrom(ObjectMap.class, field.getType()) || 
        ClassReflection.isAssignableFrom(Map.class, field.getType())) ? 1 : 0;
      this.elementType = field.getElementType(index);
      this.deprecated = field.isAnnotationPresent(Deprecated.class);
    }
  }
  
  public static interface Serializer<T> {
    void write(Json param1Json, T param1T, Class param1Class);
    
    T read(Json param1Json, JsonValue param1JsonValue, Class param1Class);
  }
  
  public static abstract class ReadOnlySerializer<T> implements Serializer<T> {
    public void write(Json json, T object, Class knownType) {}
    
    public abstract T read(Json param1Json, JsonValue param1JsonValue, Class param1Class);
  }
  
  public static interface Serializable {
    void write(Json param1Json);
    
    void read(Json param1Json, JsonValue param1JsonValue);
  }
}
