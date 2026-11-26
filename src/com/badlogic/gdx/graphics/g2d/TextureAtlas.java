package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class TextureAtlas implements Disposable {
  private final ObjectSet<Texture> textures = new ObjectSet<>(4);
  
  private final Array<AtlasRegion> regions = new Array<>();
  
  public TextureAtlas() {}
  
  public TextureAtlas(String internalPackFile) {
    this(Gdx.files.internal(internalPackFile));
  }
  
  public TextureAtlas(FileHandle packFile) {
    this(packFile, packFile.parent());
  }
  
  public TextureAtlas(FileHandle packFile, boolean flip) {
    this(packFile, packFile.parent(), flip);
  }
  
  public TextureAtlas(FileHandle packFile, FileHandle imagesDir) {
    this(packFile, imagesDir, false);
  }
  
  public TextureAtlas(FileHandle packFile, FileHandle imagesDir, boolean flip) {
    this(new TextureAtlasData(packFile, imagesDir, flip));
  }
  
  public TextureAtlas(TextureAtlasData data) {
    load(data);
  }
  
  public void load(TextureAtlasData data) {
    this.textures.ensureCapacity(data.pages.size);
    for (Array.ArrayIterator<TextureAtlasData.Page> arrayIterator2 = data.pages.iterator(); arrayIterator2.hasNext(); ) {
      TextureAtlasData.Page page = arrayIterator2.next();
      if (page.texture == null)
        page.texture = new Texture(page.textureFile, page.format, page.useMipMaps); 
      page.texture.setFilter(page.minFilter, page.magFilter);
      page.texture.setWrap(page.uWrap, page.vWrap);
      this.textures.add(page.texture);
    } 
    this.regions.ensureCapacity(data.regions.size);
    for (Array.ArrayIterator<TextureAtlasData.Page> arrayIterator1 = (Array.ArrayIterator)data.regions.iterator(); arrayIterator1.hasNext(); ) {
      TextureAtlasData.Region region = (TextureAtlasData.Region)arrayIterator1.next();
      AtlasRegion atlasRegion = new AtlasRegion(region.page.texture, region.left, region.top, region.rotate ? region.height : region.width, region.rotate ? region.width : region.height);
      atlasRegion.index = region.index;
      atlasRegion.name = region.name;
      atlasRegion.offsetX = region.offsetX;
      atlasRegion.offsetY = region.offsetY;
      atlasRegion.originalHeight = region.originalHeight;
      atlasRegion.originalWidth = region.originalWidth;
      atlasRegion.rotate = region.rotate;
      atlasRegion.degrees = region.degrees;
      atlasRegion.names = region.names;
      atlasRegion.values = region.values;
      if (region.flip)
        atlasRegion.flip(false, true); 
      this.regions.add(atlasRegion);
    } 
  }
  
  public AtlasRegion addRegion(String name, Texture texture, int x, int y, int width, int height) {
    this.textures.add(texture);
    AtlasRegion region = new AtlasRegion(texture, x, y, width, height);
    region.name = name;
    this.regions.add(region);
    return region;
  }
  
  public AtlasRegion addRegion(String name, TextureRegion textureRegion) {
    this.textures.add(textureRegion.texture);
    AtlasRegion region = new AtlasRegion(textureRegion);
    region.name = name;
    this.regions.add(region);
    return region;
  }
  
  public Array<AtlasRegion> getRegions() {
    return this.regions;
  }
  
  @Null
  public AtlasRegion findRegion(String name) {
    for (int i = 0, n = this.regions.size; i < n; i++) {
      if (((AtlasRegion)this.regions.get(i)).name.equals(name))
        return this.regions.get(i); 
    } 
    return null;
  }
  
  @Null
  public AtlasRegion findRegion(String name, int index) {
    for (int i = 0, n = this.regions.size; i < n; ) {
      AtlasRegion region = this.regions.get(i);
      if (!region.name.equals(name) || 
        region.index != index) {
        i++;
        continue;
      } 
      return region;
    } 
    return null;
  }
  
  public Array<AtlasRegion> findRegions(String name) {
    Array<AtlasRegion> matched = new Array<>(AtlasRegion.class);
    for (int i = 0, n = this.regions.size; i < n; i++) {
      AtlasRegion region = this.regions.get(i);
      if (region.name.equals(name))
        matched.add(new AtlasRegion(region)); 
    } 
    return matched;
  }
  
  public Array<Sprite> createSprites() {
    Array<Sprite> sprites = new Array(true, this.regions.size, Sprite.class);
    for (int i = 0, n = this.regions.size; i < n; i++)
      sprites.add(newSprite(this.regions.get(i))); 
    return sprites;
  }
  
  @Null
  public Sprite createSprite(String name) {
    for (int i = 0, n = this.regions.size; i < n; i++) {
      if (((AtlasRegion)this.regions.get(i)).name.equals(name))
        return newSprite(this.regions.get(i)); 
    } 
    return null;
  }
  
  @Null
  public Sprite createSprite(String name, int index) {
    for (int i = 0, n = this.regions.size; i < n; ) {
      AtlasRegion region = this.regions.get(i);
      if (region.index != index || 
        !region.name.equals(name)) {
        i++;
        continue;
      } 
      return newSprite(this.regions.get(i));
    } 
    return null;
  }
  
  public Array<Sprite> createSprites(String name) {
    Array<Sprite> matched = new Array<>(Sprite.class);
    for (int i = 0, n = this.regions.size; i < n; i++) {
      AtlasRegion region = this.regions.get(i);
      if (region.name.equals(name))
        matched.add(newSprite(region)); 
    } 
    return matched;
  }
  
  private Sprite newSprite(AtlasRegion region) {
    if (region.packedWidth == region.originalWidth && region.packedHeight == region.originalHeight) {
      if (region.rotate) {
        Sprite sprite = new Sprite(region);
        sprite.setBounds(0.0F, 0.0F, (float)region.getRegionHeight(), (float)region.getRegionWidth());
        sprite.rotate90(true);
        return sprite;
      } 
      return new Sprite(region);
    } 
    return new AtlasSprite(region);
  }
  
  @Null
  public NinePatch createPatch(String name) {
    for (int i = 0, n = this.regions.size; i < n; i++) {
      AtlasRegion region = this.regions.get(i);
      if (region.name.equals(name)) {
        int[] splits = region.findValue("split");
        if (splits == null)
          throw new IllegalArgumentException("Region does not have ninepatch splits: " + name); 
        NinePatch patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
        int[] pads = region.findValue("pad");
        if (pads != null)
          patch.setPadding((float)pads[0], (float)pads[1], (float)pads[2], (float)pads[3]); 
        return patch;
      } 
    } 
    return null;
  }
  
  public ObjectSet<Texture> getTextures() {
    return this.textures;
  }
  
  public void dispose() {
    for (ObjectSet.ObjectSetIterator<Texture> objectSetIterator = this.textures.iterator(); objectSetIterator.hasNext(); ) {
      Texture texture = objectSetIterator.next();
      texture.dispose();
    } 
    this.textures.clear(0);
  }
  
  public static class TextureAtlasData {
    final Array<Page> pages = new Array<>();
    
    final Array<Region> regions = new Array<>();
    
    public TextureAtlasData() {}
    
    public TextureAtlasData(FileHandle packFile, FileHandle imagesDir, boolean flip) {
      load(packFile, imagesDir, flip);
    }
    
    public void load(FileHandle packFile, FileHandle imagesDir, boolean flip) {
      final String[] entry = new String[5];
      ObjectMap<String, Field<Page>> pageFields = new ObjectMap<>(15, 0.99F);
      pageFields.put("size", new Field<Page>() {
            public void parse(TextureAtlas.TextureAtlasData.Page page) {
              page.width = (float)Integer.parseInt(entry[1]);
              page.height = (float)Integer.parseInt(entry[2]);
            }
          });
      pageFields.put("format", new Field<Page>() {
            public void parse(TextureAtlas.TextureAtlasData.Page page) {
              page.format = Pixmap.Format.valueOf(entry[1]);
            }
          });
      pageFields.put("filter", new Field<Page>() {
            public void parse(TextureAtlas.TextureAtlasData.Page page) {
              page.minFilter = Texture.TextureFilter.valueOf(entry[1]);
              page.magFilter = Texture.TextureFilter.valueOf(entry[2]);
              page.useMipMaps = page.minFilter.isMipMap();
            }
          });
      pageFields.put("repeat", new Field<Page>() {
            public void parse(TextureAtlas.TextureAtlasData.Page page) {
              if (entry[1].indexOf('x') != -1)
                page.uWrap = Texture.TextureWrap.Repeat; 
              if (entry[1].indexOf('y') != -1)
                page.vWrap = Texture.TextureWrap.Repeat; 
            }
          });
      pageFields.put("pma", new Field<Page>() {
            public void parse(TextureAtlas.TextureAtlasData.Page page) {
              page.pma = entry[1].equals("true");
            }
          });
      final boolean[] hasIndexes = { false };
      ObjectMap<String, Field<Region>> regionFields = new ObjectMap<>(127, 0.99F);
      regionFields.put("xy", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.left = Integer.parseInt(entry[1]);
              region.top = Integer.parseInt(entry[2]);
            }
          });
      regionFields.put("size", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.width = Integer.parseInt(entry[1]);
              region.height = Integer.parseInt(entry[2]);
            }
          });
      regionFields.put("bounds", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.left = Integer.parseInt(entry[1]);
              region.top = Integer.parseInt(entry[2]);
              region.width = Integer.parseInt(entry[3]);
              region.height = Integer.parseInt(entry[4]);
            }
          });
      regionFields.put("offset", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.offsetX = (float)Integer.parseInt(entry[1]);
              region.offsetY = (float)Integer.parseInt(entry[2]);
            }
          });
      regionFields.put("orig", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.originalWidth = Integer.parseInt(entry[1]);
              region.originalHeight = Integer.parseInt(entry[2]);
            }
          });
      regionFields.put("offsets", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.offsetX = (float)Integer.parseInt(entry[1]);
              region.offsetY = (float)Integer.parseInt(entry[2]);
              region.originalWidth = Integer.parseInt(entry[3]);
              region.originalHeight = Integer.parseInt(entry[4]);
            }
          });
      regionFields.put("rotate", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              String value = entry[1];
              if (value.equals("true")) {
                region.degrees = 90;
              } else if (!value.equals("false")) {
                region.degrees = Integer.parseInt(value);
              } 
              region.rotate = (region.degrees == 90);
            }
          });
      regionFields.put("index", new Field<Region>() {
            public void parse(TextureAtlas.TextureAtlasData.Region region) {
              region.index = Integer.parseInt(entry[1]);
              if (region.index != -1)
                hasIndexes[0] = true; 
            }
          });
      BufferedReader reader = new BufferedReader(new InputStreamReader(packFile.read()), 1024);
      try {
        String line = reader.readLine();
        while (line != null && line.trim().length() == 0)
          line = reader.readLine(); 
        while (line != null && line.trim().length() != 0 && 
          readEntry(entry, line) != 0)
          line = reader.readLine(); 
        Page page = null;
        Array<Object> names = null, values = null;
        while (line != null) {
          if (line.trim().length() == 0) {
            page = null;
            line = reader.readLine();
            continue;
          } 
          if (page == null) {
            page = new Page();
            page.textureFile = imagesDir.child(line);
            while (readEntry(entry, line = reader.readLine()) != 0) {
              Field<Page> field = pageFields.<String>get(entry[0]);
              if (field != null)
                field.parse(page); 
            } 
            this.pages.add(page);
            continue;
          } 
          Region region = new Region();
          region.page = page;
          region.name = line.trim();
          if (flip)
            region.flip = true; 
          while (true) {
            int count = readEntry(entry, line = reader.readLine());
            if (count == 0)
              break; 
            Field<Region> field = regionFields.<String>get(entry[0]);
            if (field != null) {
              field.parse(region);
              continue;
            } 
            if (names == null) {
              names = new Array(8);
              values = new Array(8);
            } 
            names.add(entry[0]);
            int[] entryValues = new int[count];
            for (int i = 0; i < count; i++) {
              try {
                entryValues[i] = Integer.parseInt(entry[i + 1]);
              } catch (NumberFormatException e) {}
            } 
            values.add(entryValues);
          } 
          if (region.originalWidth == 0 && region.originalHeight == 0) {
            region.originalWidth = region.width;
            region.originalHeight = region.height;
          } 
          if (names != null && names.size > 0) {
            region.names = names.<String>toArray(String.class);
            region.values = (int[][])values.<int>toArray((Class)int[].class);
            names.clear();
            values.clear();
          } 
          this.regions.add(region);
        } 
      } catch (Exception ex) {
        throw new GdxRuntimeException("Error reading texture atlas file: " + packFile, ex);
      } finally {
        StreamUtils.closeQuietly(reader);
      } 
      if (hasIndexes[0])
        this.regions.sort(new Comparator<Region>() {
              public int compare(TextureAtlas.TextureAtlasData.Region region1, TextureAtlas.TextureAtlasData.Region region2) {
                int i1 = region1.index;
                if (i1 == -1)
                  i1 = Integer.MAX_VALUE; 
                int i2 = region2.index;
                if (i2 == -1)
                  i2 = Integer.MAX_VALUE; 
                return i1 - i2;
              }
            }); 
    }
    
    public Array<Page> getPages() {
      return this.pages;
    }
    
    public Array<Region> getRegions() {
      return this.regions;
    }
    
    private static int readEntry(String[] entry, @Null String line) throws IOException {
      if (line == null)
        return 0; 
      line = line.trim();
      if (line.length() == 0)
        return 0; 
      int colon = line.indexOf(':');
      if (colon == -1)
        return 0; 
      entry[0] = line.substring(0, colon).trim();
      for (int i = 1, lastMatch = colon + 1;; i++) {
        int comma = line.indexOf(',', lastMatch);
        if (comma == -1) {
          entry[i] = line.substring(lastMatch).trim();
          return i;
        } 
        entry[i] = line.substring(lastMatch, comma).trim();
        lastMatch = comma + 1;
        if (i == 4)
          return 4; 
      } 
    }
    
    private static interface Field<T> {
      void parse(T param2T);
    }
    
    public static class Page {
      @Null
      public FileHandle textureFile;
      
      @Null
      public Texture texture;
      
      public float width;
      
      public float height;
      
      public boolean useMipMaps;
      
      public Pixmap.Format format = Pixmap.Format.RGBA8888;
      
      public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
      
      public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
      
      public Texture.TextureWrap uWrap = Texture.TextureWrap.ClampToEdge;
      
      public Texture.TextureWrap vWrap = Texture.TextureWrap.ClampToEdge;
      
      public boolean pma;
    }
    
    public static class Region {
      public TextureAtlas.TextureAtlasData.Page page;
      
      public String name;
      
      public int left;
      
      public int top;
      
      public int width;
      
      public int height;
      
      public float offsetX;
      
      public float offsetY;
      
      public int originalWidth;
      
      public int originalHeight;
      
      public int degrees;
      
      public boolean rotate;
      
      public int index = -1;
      
      @Null
      public String[] names;
      
      @Null
      public int[][] values;
      
      public boolean flip;
      
      @Null
      public int[] findValue(String name) {
        if (this.names != null)
          for (int i = 0, n = this.names.length; i < n; i++) {
            if (name.equals(this.names[i]))
              return this.values[i]; 
          }  
        return null;
      }
    }
  }
  
  public static class AtlasRegion extends TextureRegion {
    public int index = -1;
    
    public String name;
    
    public float offsetX;
    
    public float offsetY;
    
    public int packedWidth;
    
    public int packedHeight;
    
    public int originalWidth;
    
    public int originalHeight;
    
    public boolean rotate;
    
    public int degrees;
    
    @Null
    public String[] names;
    
    @Null
    public int[][] values;
    
    public AtlasRegion(Texture texture, int x, int y, int width, int height) {
      super(texture, x, y, width, height);
      this.originalWidth = width;
      this.originalHeight = height;
      this.packedWidth = width;
      this.packedHeight = height;
    }
    
    public AtlasRegion(AtlasRegion region) {
      setRegion(region);
      this.index = region.index;
      this.name = region.name;
      this.offsetX = region.offsetX;
      this.offsetY = region.offsetY;
      this.packedWidth = region.packedWidth;
      this.packedHeight = region.packedHeight;
      this.originalWidth = region.originalWidth;
      this.originalHeight = region.originalHeight;
      this.rotate = region.rotate;
      this.degrees = region.degrees;
      this.names = region.names;
      this.values = region.values;
    }
    
    public AtlasRegion(TextureRegion region) {
      setRegion(region);
      this.packedWidth = region.getRegionWidth();
      this.packedHeight = region.getRegionHeight();
      this.originalWidth = this.packedWidth;
      this.originalHeight = this.packedHeight;
    }
    
    public void flip(boolean x, boolean y) {
      super.flip(x, y);
      if (x)
        this.offsetX = (float)this.originalWidth - this.offsetX - getRotatedPackedWidth(); 
      if (y)
        this.offsetY = (float)this.originalHeight - this.offsetY - getRotatedPackedHeight(); 
    }
    
    public float getRotatedPackedWidth() {
      return this.rotate ? (float)this.packedHeight : (float)this.packedWidth;
    }
    
    public float getRotatedPackedHeight() {
      return this.rotate ? (float)this.packedWidth : (float)this.packedHeight;
    }
    
    @Null
    public int[] findValue(String name) {
      if (this.names != null)
        for (int i = 0, n = this.names.length; i < n; i++) {
          if (name.equals(this.names[i]))
            return this.values[i]; 
        }  
      return null;
    }
    
    public String toString() {
      return this.name;
    }
  }
  
  public static class AtlasSprite extends Sprite {
    final TextureAtlas.AtlasRegion region;
    
    float originalOffsetX;
    
    float originalOffsetY;
    
    public AtlasSprite(TextureAtlas.AtlasRegion region) {
      this.region = new TextureAtlas.AtlasRegion(region);
      this.originalOffsetX = region.offsetX;
      this.originalOffsetY = region.offsetY;
      setRegion(region);
      setOrigin((float)region.originalWidth / 2.0F, (float)region.originalHeight / 2.0F);
      int width = region.getRegionWidth();
      int height = region.getRegionHeight();
      if (region.rotate) {
        super.rotate90(true);
        super.setBounds(region.offsetX, region.offsetY, (float)height, (float)width);
      } else
        super.setBounds(region.offsetX, region.offsetY, (float)width, (float)height); 
      setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public AtlasSprite(AtlasSprite sprite) {
      this.region = sprite.region;
      this.originalOffsetX = sprite.originalOffsetX;
      this.originalOffsetY = sprite.originalOffsetY;
      set(sprite);
    }
    
    public void setPosition(float x, float y) {
      super.setPosition(x + this.region.offsetX, y + this.region.offsetY);
    }
    
    public void setX(float x) {
      super.setX(x + this.region.offsetX);
    }
    
    public void setY(float y) {
      super.setY(y + this.region.offsetY);
    }
    
    public void setBounds(float x, float y, float width, float height) {
      float widthRatio = width / (float)this.region.originalWidth;
      float heightRatio = height / (float)this.region.originalHeight;
      this.region.offsetX = this.originalOffsetX * widthRatio;
      this.region.offsetY = this.originalOffsetY * heightRatio;
      int packedWidth = this.region.rotate ? this.region.packedHeight : this.region.packedWidth;
      int packedHeight = this.region.rotate ? this.region.packedWidth : this.region.packedHeight;
      super.setBounds(x + this.region.offsetX, y + this.region.offsetY, (float)packedWidth * widthRatio, (float)packedHeight * heightRatio);
    }
    
    public void setSize(float width, float height) {
      setBounds(getX(), getY(), width, height);
    }
    
    public void setOrigin(float originX, float originY) {
      super.setOrigin(originX - this.region.offsetX, originY - this.region.offsetY);
    }
    
    public void setOriginCenter() {
      super.setOrigin(this.width / 2.0F - this.region.offsetX, this.height / 2.0F - this.region.offsetY);
    }
    
    public void flip(boolean x, boolean y) {
      if (this.region.rotate) {
        super.flip(y, x);
      } else
        super.flip(x, y); 
      float oldOriginX = getOriginX();
      float oldOriginY = getOriginY();
      float oldOffsetX = this.region.offsetX;
      float oldOffsetY = this.region.offsetY;
      float widthRatio = getWidthRatio();
      float heightRatio = getHeightRatio();
      this.region.offsetX = this.originalOffsetX;
      this.region.offsetY = this.originalOffsetY;
      this.region.flip(x, y);
      this.originalOffsetX = this.region.offsetX;
      this.originalOffsetY = this.region.offsetY;
      this.region.offsetX *= widthRatio;
      this.region.offsetY *= heightRatio;
      translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
      setOrigin(oldOriginX, oldOriginY);
    }
    
    public void rotate90(boolean clockwise) {
      super.rotate90(clockwise);
      float oldOriginX = getOriginX();
      float oldOriginY = getOriginY();
      float oldOffsetX = this.region.offsetX;
      float oldOffsetY = this.region.offsetY;
      float widthRatio = getWidthRatio();
      float heightRatio = getHeightRatio();
      if (clockwise) {
        this.region.offsetX = oldOffsetY;
        this.region.offsetY = (float)this.region.originalHeight * heightRatio - oldOffsetX - (float)this.region.packedWidth * widthRatio;
      } else {
        this.region.offsetX = (float)this.region.originalWidth * widthRatio - oldOffsetY - (float)this.region.packedHeight * heightRatio;
        this.region.offsetY = oldOffsetX;
      } 
      translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
      setOrigin(oldOriginX, oldOriginY);
    }
    
    public float getX() {
      return super.getX() - this.region.offsetX;
    }
    
    public float getY() {
      return super.getY() - this.region.offsetY;
    }
    
    public float getOriginX() {
      return super.getOriginX() + this.region.offsetX;
    }
    
    public float getOriginY() {
      return super.getOriginY() + this.region.offsetY;
    }
    
    public float getWidth() {
      return super.getWidth() / this.region.getRotatedPackedWidth() * (float)this.region.originalWidth;
    }
    
    public float getHeight() {
      return super.getHeight() / this.region.getRotatedPackedHeight() * (float)this.region.originalHeight;
    }
    
    public float getWidthRatio() {
      return super.getWidth() / this.region.getRotatedPackedWidth();
    }
    
    public float getHeightRatio() {
      return super.getHeight() / this.region.getRotatedPackedHeight();
    }
    
    public TextureAtlas.AtlasRegion getAtlasRegion() {
      return this.region;
    }
    
    public String toString() {
      return this.region.toString();
    }
  }
}
