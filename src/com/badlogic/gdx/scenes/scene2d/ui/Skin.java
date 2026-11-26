package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class Skin implements Disposable {
  ObjectMap<Class, ObjectMap<String, Object>> resources = new ObjectMap<>();
  
  TextureAtlas atlas;
  
  float scale = 1.0F;
  
  private final ObjectMap<String, Class> jsonClassTags = new ObjectMap<>(defaultTagClasses.length);
  
  public Skin() {
    for (Class c : defaultTagClasses)
      this.jsonClassTags.put(c.getSimpleName(), c); 
  }
  
  public Skin(FileHandle skinFile) {
    for (Class c : defaultTagClasses)
      this.jsonClassTags.put(c.getSimpleName(), c); 
    FileHandle atlasFile = skinFile.sibling(skinFile.nameWithoutExtension() + ".atlas");
    if (atlasFile.exists()) {
      this.atlas = new TextureAtlas(atlasFile);
      addRegions(this.atlas);
    } 
    load(skinFile);
  }
  
  public Skin(FileHandle skinFile, TextureAtlas atlas) {
    for (Class c : defaultTagClasses)
      this.jsonClassTags.put(c.getSimpleName(), c); 
    this.atlas = atlas;
    addRegions(atlas);
    load(skinFile);
  }
  
  public Skin(TextureAtlas atlas) {
    for (Class c : defaultTagClasses)
      this.jsonClassTags.put(c.getSimpleName(), c); 
    this.atlas = atlas;
    addRegions(atlas);
  }
  
  public void load(FileHandle skinFile) {
    try {
      getJsonLoader(skinFile).<Skin>fromJson(Skin.class, skinFile);
    } catch (SerializationException ex) {
      throw new SerializationException("Error reading file: " + skinFile, ex);
    } 
  }
  
  public void addRegions(TextureAtlas atlas) {
    Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
    for (int i = 0, n = regions.size; i < n; i++) {
      TextureAtlas.AtlasRegion region = regions.get(i);
      String name = region.name;
      if (region.index != -1)
        name = name + "_" + region.index; 
      add(name, region, TextureRegion.class);
    } 
  }
  
  public void add(String name, Object resource) {
    add(name, resource, resource.getClass());
  }
  
  public void add(String name, Object resource, Class type) {
    if (name == null)
      throw new IllegalArgumentException("name cannot be null."); 
    if (resource == null)
      throw new IllegalArgumentException("resource cannot be null."); 
    ObjectMap<String, Object> typeResources = this.resources.<Class<?>>get(type);
    if (typeResources == null) {
      typeResources = new ObjectMap<>((type == TextureRegion.class || type == Drawable.class || type == Sprite.class) ? 256 : 64);
      this.resources.put(type, typeResources);
    } 
    typeResources.put(name, resource);
  }
  
  public void remove(String name, Class type) {
    if (name == null)
      throw new IllegalArgumentException("name cannot be null."); 
    ObjectMap<String, Object> typeResources = this.resources.<Class<?>>get(type);
    typeResources.remove(name);
  }
  
  public <T> T get(Class<T> type) {
    return get("default", type);
  }
  
  public <T> T get(String name, Class<T> type) {
    if (name == null)
      throw new IllegalArgumentException("name cannot be null."); 
    if (type == null)
      throw new IllegalArgumentException("type cannot be null."); 
    if (type == Drawable.class)
      return (T)getDrawable(name); 
    if (type == TextureRegion.class)
      return (T)getRegion(name); 
    if (type == NinePatch.class)
      return (T)getPatch(name); 
    if (type == Sprite.class)
      return (T)getSprite(name); 
    ObjectMap<String, Object> typeResources = this.resources.<Class<T>>get(type);
    if (typeResources == null)
      throw new GdxRuntimeException("No " + type.getName() + " registered with name: " + name); 
    Object resource = typeResources.<String>get(name);
    if (resource == null)
      throw new GdxRuntimeException("No " + type.getName() + " registered with name: " + name); 
    return (T)resource;
  }
  
  @Null
  public <T> T optional(String name, Class<T> type) {
    if (name == null)
      throw new IllegalArgumentException("name cannot be null."); 
    if (type == null)
      throw new IllegalArgumentException("type cannot be null."); 
    ObjectMap<String, Object> typeResources = this.resources.<Class<T>>get(type);
    if (typeResources == null)
      return null; 
    return (T)typeResources.<String>get(name);
  }
  
  public boolean has(String name, Class type) {
    ObjectMap<String, Object> typeResources = this.resources.<Class<?>>get(type);
    if (typeResources == null)
      return false; 
    return typeResources.containsKey(name);
  }
  
  @Null
  public <T> ObjectMap<String, T> getAll(Class<T> type) {
    return (ObjectMap<String, T>)this.resources.<Class<T>>get(type);
  }
  
  public Color getColor(String name) {
    return get(name, Color.class);
  }
  
  public BitmapFont getFont(String name) {
    return get(name, BitmapFont.class);
  }
  
  public TextureRegion getRegion(String name) {
    TextureRegion region = optional(name, TextureRegion.class);
    if (region != null)
      return region; 
    Texture texture = optional(name, Texture.class);
    if (texture == null)
      throw new GdxRuntimeException("No TextureRegion or Texture registered with name: " + name); 
    region = new TextureRegion(texture);
    add(name, region, TextureRegion.class);
    return region;
  }
  
  @Null
  public Array<TextureRegion> getRegions(String regionName) {
    Array<TextureRegion> regions = null;
    int i = 0;
    TextureRegion region = optional(regionName + "_" + i++, TextureRegion.class);
    if (region != null) {
      regions = new Array<>();
      while (region != null) {
        regions.add(region);
        region = optional(regionName + "_" + i++, TextureRegion.class);
      } 
    } 
    return regions;
  }
  
  public TiledDrawable getTiledDrawable(String name) {
    TiledDrawable tiled = optional(name, TiledDrawable.class);
    if (tiled != null)
      return tiled; 
    tiled = new TiledDrawable(getRegion(name));
    tiled.setName(name);
    if (this.scale != 1.0F) {
      scale(tiled);
      tiled.setScale(this.scale);
    } 
    add(name, tiled, TiledDrawable.class);
    return tiled;
  }
  
  public NinePatch getPatch(String name) {
    NinePatch patch = optional(name, NinePatch.class);
    if (patch != null)
      return patch; 
    try {
      TextureRegion region = getRegion(name);
      if (region instanceof TextureAtlas.AtlasRegion) {
        int[] splits = ((TextureAtlas.AtlasRegion)region).findValue("split");
        if (splits != null) {
          patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
          int[] pads = ((TextureAtlas.AtlasRegion)region).findValue("pad");
          if (pads != null)
            patch.setPadding((float)pads[0], (float)pads[1], (float)pads[2], (float)pads[3]); 
        } 
      } 
      if (patch == null)
        patch = new NinePatch(region); 
      if (this.scale != 1.0F)
        patch.scale(this.scale, this.scale); 
      add(name, patch, NinePatch.class);
      return patch;
    } catch (GdxRuntimeException ex) {
      throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
    } 
  }
  
  public Sprite getSprite(String name) {
    Sprite sprite = optional(name, Sprite.class);
    if (sprite != null)
      return sprite; 
    try {
      TextureRegion textureRegion = getRegion(name);
      if (textureRegion instanceof TextureAtlas.AtlasRegion) {
        TextureAtlas.AtlasRegion region = (TextureAtlas.AtlasRegion)textureRegion;
        if (region.rotate || region.packedWidth != region.originalWidth || region.packedHeight != region.originalHeight)
          sprite = new TextureAtlas.AtlasSprite(region); 
      } 
      if (sprite == null)
        sprite = new Sprite(textureRegion); 
      if (this.scale != 1.0F)
        sprite.setSize(sprite.getWidth() * this.scale, sprite.getHeight() * this.scale); 
      add(name, sprite, Sprite.class);
      return sprite;
    } catch (GdxRuntimeException ex) {
      throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
    } 
  }
  
  public Drawable getDrawable(String name) {
    Drawable drawable = optional(name, Drawable.class);
    if (drawable != null)
      return drawable; 
    try {
      TextureRegion textureRegion = getRegion(name);
      if (textureRegion instanceof TextureAtlas.AtlasRegion) {
        TextureAtlas.AtlasRegion region = (TextureAtlas.AtlasRegion)textureRegion;
        if (region.findValue("split") != null) {
          drawable = new NinePatchDrawable(getPatch(name));
        } else if (region.rotate || region.packedWidth != region.originalWidth || region.packedHeight != region.originalHeight) {
          drawable = new SpriteDrawable(getSprite(name));
        } 
      } 
      if (drawable == null) {
        drawable = new TextureRegionDrawable(textureRegion);
        if (this.scale != 1.0F)
          scale(drawable); 
      } 
    } catch (GdxRuntimeException e) {}
    if (drawable == null) {
      NinePatch patch = optional(name, NinePatch.class);
      if (patch != null) {
        drawable = new NinePatchDrawable(patch);
      } else {
        Sprite sprite = optional(name, Sprite.class);
        if (sprite != null) {
          drawable = new SpriteDrawable(sprite);
        } else
          throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + name); 
      } 
    } 
    if (drawable instanceof BaseDrawable)
      ((BaseDrawable)drawable).setName(name); 
    add(name, drawable, Drawable.class);
    return drawable;
  }
  
  @Null
  public String find(Object resource) {
    if (resource == null)
      throw new IllegalArgumentException("style cannot be null."); 
    ObjectMap<String, Object> typeResources = this.resources.<Class<?>>get(resource.getClass());
    if (typeResources == null)
      return null; 
    return typeResources.findKey(resource, true);
  }
  
  public Drawable newDrawable(String name) {
    return newDrawable(getDrawable(name));
  }
  
  public Drawable newDrawable(String name, float r, float g, float b, float a) {
    return newDrawable(getDrawable(name), new Color(r, g, b, a));
  }
  
  public Drawable newDrawable(String name, Color tint) {
    return newDrawable(getDrawable(name), tint);
  }
  
  public Drawable newDrawable(Drawable drawable) {
    if (drawable instanceof TiledDrawable)
      return new TiledDrawable((TiledDrawable)drawable); 
    if (drawable instanceof TextureRegionDrawable)
      return new TextureRegionDrawable((TextureRegionDrawable)drawable); 
    if (drawable instanceof NinePatchDrawable)
      return new NinePatchDrawable((NinePatchDrawable)drawable); 
    if (drawable instanceof SpriteDrawable)
      return new SpriteDrawable((SpriteDrawable)drawable); 
    throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
  }
  
  public Drawable newDrawable(Drawable drawable, float r, float g, float b, float a) {
    return newDrawable(drawable, new Color(r, g, b, a));
  }
  
  public Drawable newDrawable(Drawable drawable, Color tint) {
    Drawable newDrawable;
    if (drawable instanceof TextureRegionDrawable) {
      newDrawable = ((TextureRegionDrawable)drawable).tint(tint);
    } else if (drawable instanceof NinePatchDrawable) {
      newDrawable = ((NinePatchDrawable)drawable).tint(tint);
    } else if (drawable instanceof SpriteDrawable) {
      newDrawable = ((SpriteDrawable)drawable).tint(tint);
    } else
      throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass()); 
    if (newDrawable instanceof BaseDrawable) {
      BaseDrawable named = (BaseDrawable)newDrawable;
      if (drawable instanceof BaseDrawable) {
        named.setName(((BaseDrawable)drawable).getName() + " (" + tint + ")");
      } else
        named.setName(" (" + tint + ")"); 
    } 
    return newDrawable;
  }
  
  public void scale(Drawable drawble) {
    drawble.setLeftWidth(drawble.getLeftWidth() * this.scale);
    drawble.setRightWidth(drawble.getRightWidth() * this.scale);
    drawble.setBottomHeight(drawble.getBottomHeight() * this.scale);
    drawble.setTopHeight(drawble.getTopHeight() * this.scale);
    drawble.setMinWidth(drawble.getMinWidth() * this.scale);
    drawble.setMinHeight(drawble.getMinHeight() * this.scale);
  }
  
  public void setScale(float scale) {
    this.scale = scale;
  }
  
  public void setEnabled(Actor actor, boolean enabled) {
    Method method = findMethod(actor.getClass(), "getStyle");
    if (method == null)
      return; 
    try {
      style = method.invoke(actor, new Object[0]);
    } catch (Exception ignored) {
      return;
    } 
    String name = find(style);
    if (name == null)
      return; 
    name = name.replace("-disabled", "") + (enabled ? "" : "-disabled");
    Object style = get(name, style.getClass());
    method = findMethod(actor.getClass(), "setStyle");
    if (method == null)
      return; 
    try {
      method.invoke(actor, new Object[] { style });
    } catch (Exception e) {}
  }
  
  @Null
  public TextureAtlas getAtlas() {
    return this.atlas;
  }
  
  public void dispose() {
    if (this.atlas != null)
      this.atlas.dispose(); 
    for (ObjectMap.Values<ObjectMap<String, Object>> values = this.resources.values().iterator(); values.hasNext(); ) {
      ObjectMap<String, Object> entry = values.next();
      for (ObjectMap.Values values1 = entry.values().iterator(); values1.hasNext(); ) {
        Object resource = values1.next();
        if (resource instanceof Disposable)
          ((Disposable)resource).dispose(); 
      } 
    } 
  }
  
  protected Json getJsonLoader(final FileHandle skinFile) {
    final Skin skin = this;
    Json json = new Json() {
        private static final String parentFieldName = "parent";
        
        public <T> T readValue(Class<T> type, Class elementType, JsonValue jsonData) {
          if (jsonData != null && jsonData.isString() && !ClassReflection.isAssignableFrom(CharSequence.class, type))
            return Skin.this.<T>get(jsonData.asString(), type); 
          return super.<T>readValue(type, elementType, jsonData);
        }
        
        protected boolean ignoreUnknownField(Class type, String fieldName) {
          return fieldName.equals("parent");
        }
        
        public void readFields(Object object, JsonValue jsonMap) {
          if (jsonMap.has("parent")) {
            String parentName = readValue("parent", String.class, jsonMap);
            Class<?> parentType = object.getClass();
            while (true) {
              try {
                copyFields(Skin.this.get(parentName, parentType), object);
                break;
              } catch (GdxRuntimeException ex) {
                parentType = parentType.getSuperclass();
                if (parentType == Object.class) {
                  SerializationException se = new SerializationException("Unable to find parent resource with name: " + parentName);
                  se.addTrace(jsonMap.child.trace());
                  throw se;
                } 
              } 
            } 
          } 
          super.readFields(object, jsonMap);
        }
      };
    json.setTypeName(null);
    json.setUsePrototypes(false);
    json.<Skin>setSerializer(Skin.class, new Json.ReadOnlySerializer<Skin>() {
          public Skin read(Json json, JsonValue typeToValueMap, Class ignored) {
            for (JsonValue valueMap = typeToValueMap.child; valueMap != null; valueMap = valueMap.next) {
              try {
                Class type = json.getClass(valueMap.name());
                if (type == null)
                  type = ClassReflection.forName(valueMap.name()); 
                readNamedObjects(json, type, valueMap);
              } catch (ReflectionException ex) {
                throw new SerializationException(ex);
              } 
            } 
            return skin;
          }
          
          private void readNamedObjects(Json json, Class type, JsonValue valueMap) {
            Class<Drawable> addType = (type == Skin.TintedDrawable.class) ? Drawable.class : type;
            for (JsonValue valueEntry = valueMap.child; valueEntry != null; valueEntry = valueEntry.next) {
              Object object = json.readValue(type, valueEntry);
              if (object != null)
                try {
                  Skin.this.add(valueEntry.name, object, addType);
                  if (addType != Drawable.class && ClassReflection.isAssignableFrom(Drawable.class, addType))
                    Skin.this.add(valueEntry.name, object, Drawable.class); 
                } catch (Exception ex) {
                  throw new SerializationException("Error reading " + 
                      ClassReflection.getSimpleName(type) + ": " + valueEntry.name, ex);
                }  
            } 
          }
        });
    json.<BitmapFont>setSerializer(BitmapFont.class, new Json.ReadOnlySerializer<BitmapFont>() {
          public BitmapFont read(Json json, JsonValue jsonData, Class type) {
            String path = json.<String>readValue("file", String.class, jsonData);
            int scaledSize = (Integer)json.<int>readValue("scaledSize", int.class, -1, jsonData);
            Boolean flip = json.<Boolean>readValue("flip", Boolean.class, false, jsonData);
            Boolean markupEnabled = json.<Boolean>readValue("markupEnabled", Boolean.class, false, jsonData);
            FileHandle fontFile = skinFile.parent().child(path);
            if (!fontFile.exists())
              fontFile = Gdx.files.internal(path); 
            if (!fontFile.exists())
              throw new SerializationException("Font file not found: " + fontFile); 
            String regionName = fontFile.nameWithoutExtension();
            try {
              BitmapFont font;
              Array<TextureRegion> regions = skin.getRegions(regionName);
              if (regions != null) {
                font = new BitmapFont(new BitmapFont.BitmapFontData(fontFile, flip), regions, true);
              } else {
                TextureRegion region = skin.<TextureRegion>optional(regionName, TextureRegion.class);
                if (region != null) {
                  font = new BitmapFont(fontFile, region, flip);
                } else {
                  FileHandle imageFile = fontFile.parent().child(regionName + ".png");
                  if (imageFile.exists()) {
                    font = new BitmapFont(fontFile, imageFile, flip);
                  } else
                    font = new BitmapFont(fontFile, flip); 
                } 
              } 
              (font.getData()).markupEnabled = markupEnabled;
              if (scaledSize != -1)
                font.getData().setScale((float)scaledSize / font.getCapHeight()); 
              return font;
            } catch (RuntimeException ex) {
              throw new SerializationException("Error loading bitmap font: " + fontFile, ex);
            } 
          }
        });
    json.<Color>setSerializer(Color.class, new Json.ReadOnlySerializer<Color>() {
          public Color read(Json json, JsonValue jsonData, Class type) {
            if (jsonData.isString())
              return Skin.this.<Color>get(jsonData.asString(), Color.class); 
            String hex = json.<String>readValue("hex", String.class, (String)null, jsonData);
            if (hex != null)
              return Color.valueOf(hex); 
            float r = (Float)json.<float>readValue("r", float.class, 0.0F, jsonData);
            float g = (Float)json.<float>readValue("g", float.class, 0.0F, jsonData);
            float b = (Float)json.<float>readValue("b", float.class, 0.0F, jsonData);
            float a = (Float)json.<float>readValue("a", float.class, 1.0F, jsonData);
            return new Color(r, g, b, a);
          }
        });
    json.<TintedDrawable>setSerializer(TintedDrawable.class, new Json.ReadOnlySerializer() {
          public Object read(Json json, JsonValue jsonData, Class type) {
            String name = json.<String>readValue("name", String.class, jsonData);
            Color color = json.<Color>readValue("color", Color.class, jsonData);
            if (color == null)
              throw new SerializationException("TintedDrawable missing color: " + jsonData); 
            Drawable drawable = Skin.this.newDrawable(name, color);
            if (drawable instanceof BaseDrawable) {
              BaseDrawable named = (BaseDrawable)drawable;
              named.setName(jsonData.name + " (" + name + ", " + color + ")");
            } 
            return drawable;
          }
        });
    for (ObjectMap.Entries<String, Class> entries = this.jsonClassTags.iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<String, Class<?>> entry = (ObjectMap.Entry<String, Class<?>>)entries.next();
      json.addClassTag((String)entry.key, (Class)entry.value);
    } 
    return json;
  }
  
  public ObjectMap<String, Class> getJsonClassTags() {
    return this.jsonClassTags;
  }
  
  private static final Class[] defaultTagClasses = new Class<?>[] { 
      BitmapFont.class, Color.class, TintedDrawable.class, NinePatchDrawable.class, SpriteDrawable.class, TextureRegionDrawable.class, TiledDrawable.class, Button.ButtonStyle.class, CheckBox.CheckBoxStyle.class, ImageButton.ImageButtonStyle.class, 
      ImageTextButton.ImageTextButtonStyle.class, Label.LabelStyle.class, List.ListStyle.class, ProgressBar.ProgressBarStyle.class, ScrollPane.ScrollPaneStyle.class, SelectBox.SelectBoxStyle.class, Slider.SliderStyle.class, SplitPane.SplitPaneStyle.class, TextButton.TextButtonStyle.class, TextField.TextFieldStyle.class, 
      TextTooltip.TextTooltipStyle.class, Touchpad.TouchpadStyle.class, Tree.TreeStyle.class, Window.WindowStyle.class };
  
  @Null
  private static Method findMethod(Class type, String name) {
    Method[] methods = ClassReflection.getMethods(type);
    for (int i = 0, n = methods.length; i < n; i++) {
      Method method = methods[i];
      if (method.getName().equals(name))
        return method; 
    } 
    return null;
  }
  
  public static class TintedDrawable {
    public String name;
    
    public Color color;
  }
}
