package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitmapFont implements Disposable {
  private static final int LOG2_PAGE_SIZE = 9;
  
  private static final int PAGE_SIZE = 512;
  
  private static final int PAGES = 128;
  
  final BitmapFontData data;
  
  Array<TextureRegion> regions;
  
  private final BitmapFontCache cache;
  
  private boolean flipped;
  
  boolean integer;
  
  private boolean ownsTexture;
  
  public BitmapFont() {
    this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), false, true);
  }
  
  public BitmapFont(boolean flip) {
    this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), flip, true);
  }
  
  public BitmapFont(FileHandle fontFile, TextureRegion region) {
    this(fontFile, region, false);
  }
  
  public BitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
    this(new BitmapFontData(fontFile, flip), region, true);
  }
  
  public BitmapFont(FileHandle fontFile) {
    this(fontFile, false);
  }
  
  public BitmapFont(FileHandle fontFile, boolean flip) {
    this(new BitmapFontData(fontFile, flip), (TextureRegion)null, true);
  }
  
  public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
    this(fontFile, imageFile, flip, true);
  }
  
  public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip, boolean integer) {
    this(new BitmapFontData(fontFile, flip), new TextureRegion(new Texture(imageFile, false)), integer);
    this.ownsTexture = true;
  }
  
  public BitmapFont(BitmapFontData data, TextureRegion region, boolean integer) {
    this(data, (region != null) ? Array.<TextureRegion>with(region) : null, integer);
  }
  
  public BitmapFont(BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
    this.flipped = data.flipped;
    this.data = data;
    this.integer = integer;
    if (pageRegions == null || pageRegions.size == 0) {
      if (data.imagePaths == null)
        throw new IllegalArgumentException("If no regions are specified, the font data must have an images path."); 
      int n = data.imagePaths.length;
      this.regions = new Array<>(n);
      for (int i = 0; i < n; i++) {
        FileHandle file;
        if (data.fontFile == null) {
          file = Gdx.files.internal(data.imagePaths[i]);
        } else
          file = Gdx.files.getFileHandle(data.imagePaths[i], data.fontFile.type()); 
        this.regions.add(new TextureRegion(new Texture(file, false)));
      } 
      this.ownsTexture = true;
    } else {
      this.regions = pageRegions;
      this.ownsTexture = false;
    } 
    this.cache = newFontCache();
    load(data);
  }
  
  protected void load(BitmapFontData data) {
    for (Glyph[] page : data.glyphs) {
      if (page != null)
        for (Glyph glyph : page) {
          if (glyph != null)
            data.setGlyphRegion(glyph, this.regions.get(glyph.page)); 
        }  
    } 
    if (data.missingGlyph != null)
      data.setGlyphRegion(data.missingGlyph, this.regions.get(data.missingGlyph.page)); 
  }
  
  public GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
    this.cache.clear();
    GlyphLayout layout = this.cache.addText(str, x, y);
    this.cache.draw(batch);
    return layout;
  }
  
  public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
    this.cache.clear();
    GlyphLayout layout = this.cache.addText(str, x, y, targetWidth, halign, wrap);
    this.cache.draw(batch);
    return layout;
  }
  
  public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
    this.cache.clear();
    GlyphLayout layout = this.cache.addText(str, x, y, start, end, targetWidth, halign, wrap);
    this.cache.draw(batch);
    return layout;
  }
  
  public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap, String truncate) {
    this.cache.clear();
    GlyphLayout layout = this.cache.addText(str, x, y, start, end, targetWidth, halign, wrap, truncate);
    this.cache.draw(batch);
    return layout;
  }
  
  public void draw(Batch batch, GlyphLayout layout, float x, float y) {
    this.cache.clear();
    this.cache.addText(layout, x, y);
    this.cache.draw(batch);
  }
  
  public Color getColor() {
    return this.cache.getColor();
  }
  
  public void setColor(Color color) {
    this.cache.getColor().set(color);
  }
  
  public void setColor(float r, float g, float b, float a) {
    this.cache.getColor().set(r, g, b, a);
  }
  
  public float getScaleX() {
    return this.data.scaleX;
  }
  
  public float getScaleY() {
    return this.data.scaleY;
  }
  
  public TextureRegion getRegion() {
    return this.regions.first();
  }
  
  public Array<TextureRegion> getRegions() {
    return this.regions;
  }
  
  public TextureRegion getRegion(int index) {
    return this.regions.get(index);
  }
  
  public float getLineHeight() {
    return this.data.lineHeight;
  }
  
  public float getSpaceXadvance() {
    return this.data.spaceXadvance;
  }
  
  public float getXHeight() {
    return this.data.xHeight;
  }
  
  public float getCapHeight() {
    return this.data.capHeight;
  }
  
  public float getAscent() {
    return this.data.ascent;
  }
  
  public float getDescent() {
    return this.data.descent;
  }
  
  public boolean isFlipped() {
    return this.flipped;
  }
  
  public void dispose() {
    if (this.ownsTexture)
      for (int i = 0; i < this.regions.size; i++)
        this.regions.get(i).getTexture().dispose();  
  }
  
  public void setFixedWidthGlyphs(CharSequence glyphs) {
    BitmapFontData data = this.data;
    int maxAdvance = 0;
    for (int i = 0, j = glyphs.length(); i < j; i++) {
      Glyph g = data.getGlyph(glyphs.charAt(i));
      if (g != null && g.xadvance > maxAdvance)
        maxAdvance = g.xadvance; 
    } 
    for (int index = 0, end = glyphs.length(); index < end; index++) {
      Glyph g = data.getGlyph(glyphs.charAt(index));
      if (g != null) {
        g.xoffset += (maxAdvance - g.xadvance) / 2;
        g.xadvance = maxAdvance;
        g.kerning = null;
        g.fixedWidth = true;
      } 
    } 
  }
  
  public void setUseIntegerPositions(boolean integer) {
    this.integer = integer;
    this.cache.setUseIntegerPositions(integer);
  }
  
  public boolean usesIntegerPositions() {
    return this.integer;
  }
  
  public BitmapFontCache getCache() {
    return this.cache;
  }
  
  public BitmapFontData getData() {
    return this.data;
  }
  
  public boolean ownsTexture() {
    return this.ownsTexture;
  }
  
  public void setOwnsTexture(boolean ownsTexture) {
    this.ownsTexture = ownsTexture;
  }
  
  public BitmapFontCache newFontCache() {
    return new BitmapFontCache(this, this.integer);
  }
  
  public String toString() {
    return (this.data.name != null) ? this.data.name : super.toString();
  }
  
  public static class Glyph {
    public int id;
    
    public int srcX;
    
    public int srcY;
    
    public int width;
    
    public int height;
    
    public float u;
    
    public float v;
    
    public float u2;
    
    public float v2;
    
    public int xoffset;
    
    public int yoffset;
    
    public int xadvance;
    
    public byte[][] kerning;
    
    public boolean fixedWidth;
    
    public int page = 0;
    
    public int getKerning(char ch) {
      if (this.kerning != null) {
        byte[] page = this.kerning[ch >>> 9];
        if (page != null)
          return page[ch & 0x1FF]; 
      } 
      return 0;
    }
    
    public void setKerning(int ch, int value) {
      if (this.kerning == null)
        this.kerning = new byte[128][]; 
      byte[] page = this.kerning[ch >>> 9];
      if (page == null)
        this.kerning[ch >>> 9] = page = new byte[512]; 
      page[ch & 0x1FF] = (byte)value;
    }
    
    public String toString() {
      return Character.toString((char)this.id);
    }
  }
  
  static int indexOf(CharSequence text, char ch, int start) {
    int n = text.length();
    for (; start < n; start++) {
      if (text.charAt(start) == ch)
        return start; 
    } 
    return n;
  }
  
  public static class BitmapFontData {
    public String name;
    
    public String[] imagePaths;
    
    public FileHandle fontFile;
    
    public boolean flipped;
    
    public float padTop;
    
    public float padRight;
    
    public float padBottom;
    
    public float padLeft;
    
    public float lineHeight;
    
    public float capHeight = 1.0F;
    
    public float ascent;
    
    public float descent;
    
    public float down;
    
    public float blankLineScale = 1.0F;
    
    public float scaleX = 1.0F;
    
    public float scaleY = 1.0F;
    
    public boolean markupEnabled;
    
    public float cursorX;
    
    public final BitmapFont.Glyph[][] glyphs = new BitmapFont.Glyph[128][];
    
    public BitmapFont.Glyph missingGlyph;
    
    public float spaceXadvance;
    
    public float xHeight = 1.0F;
    
    public char[] breakChars;
    
    public char[] xChars = new char[] { 
        'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 
        'v', 'w', 'z' };
    
    public char[] capChars = new char[] { 
        'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 
        'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z' };
    
    public BitmapFontData() {}
    
    public BitmapFontData(FileHandle fontFile, boolean flip) {
      this.fontFile = fontFile;
      this.flipped = flip;
      load(fontFile, flip);
    }
    
    public void load(FileHandle fontFile, boolean flip) {
      if (this.imagePaths != null)
        throw new IllegalStateException("Already loaded."); 
      this.name = fontFile.nameWithoutExtension();
      BufferedReader reader = new BufferedReader(new InputStreamReader(fontFile.read()), 512);
      try {
        String line = reader.readLine();
        if (line == null)
          throw new GdxRuntimeException("File is empty."); 
        line = line.substring(line.indexOf("padding=") + 8);
        String[] padding = line.substring(0, line.indexOf(' ')).split(",", 4);
        if (padding.length != 4)
          throw new GdxRuntimeException("Invalid padding."); 
        this.padTop = (float)Integer.parseInt(padding[0]);
        this.padRight = (float)Integer.parseInt(padding[1]);
        this.padBottom = (float)Integer.parseInt(padding[2]);
        this.padLeft = (float)Integer.parseInt(padding[3]);
        float padY = this.padTop + this.padBottom;
        line = reader.readLine();
        if (line == null)
          throw new GdxRuntimeException("Missing common header."); 
        String[] common = line.split(" ", 9);
        if (common.length < 3)
          throw new GdxRuntimeException("Invalid common header."); 
        if (!common[1].startsWith("lineHeight="))
          throw new GdxRuntimeException("Missing: lineHeight"); 
        this.lineHeight = (float)Integer.parseInt(common[1].substring(11));
        if (!common[2].startsWith("base="))
          throw new GdxRuntimeException("Missing: base"); 
        float baseLine = (float)Integer.parseInt(common[2].substring(5));
        int pageCount = 1;
        if (common.length >= 6 && common[5] != null && common[5].startsWith("pages="))
          try {
            pageCount = Math.max(1, Integer.parseInt(common[5].substring(6)));
          } catch (NumberFormatException e) {} 
        this.imagePaths = new String[pageCount];
        for (int p = 0; p < pageCount; p++) {
          line = reader.readLine();
          if (line == null)
            throw new GdxRuntimeException("Missing additional page definitions."); 
          Matcher matcher = Pattern.compile(".*id=(\\d+)").matcher(line);
          if (matcher.find()) {
            String id = matcher.group(1);
            try {
              int pageID = Integer.parseInt(id);
              if (pageID != p)
                throw new GdxRuntimeException("Page IDs must be indices starting at 0: " + id); 
            } catch (NumberFormatException ex) {
              throw new GdxRuntimeException("Invalid page id: " + id, ex);
            } 
          } 
          matcher = Pattern.compile(".*file=\"?([^\"]+)\"?").matcher(line);
          if (!matcher.find())
            throw new GdxRuntimeException("Missing: file"); 
          String fileName = matcher.group(1);
          this.imagePaths[p] = fontFile.parent().child(fileName).path().replaceAll("\\\\", "/");
        } 
        this.descent = 0.0F;
        while (true) {
          line = reader.readLine();
          if (line == null || 
            line.startsWith("kernings ") || 
            line.startsWith("metrics "))
            break; 
          if (!line.startsWith("char "))
            continue; 
          BitmapFont.Glyph glyph = new BitmapFont.Glyph();
          StringTokenizer tokens = new StringTokenizer(line, " =");
          tokens.nextToken();
          tokens.nextToken();
          int ch = Integer.parseInt(tokens.nextToken());
          if (ch <= 0) {
            this.missingGlyph = glyph;
          } else if (ch <= 65535) {
            setGlyph(ch, glyph);
          } else
            continue; 
          glyph.id = ch;
          tokens.nextToken();
          glyph.srcX = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          glyph.srcY = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          glyph.width = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          glyph.height = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          glyph.xoffset = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          if (flip) {
            glyph.yoffset = Integer.parseInt(tokens.nextToken());
          } else
            glyph.yoffset = -(glyph.height + Integer.parseInt(tokens.nextToken())); 
          tokens.nextToken();
          glyph.xadvance = Integer.parseInt(tokens.nextToken());
          if (tokens.hasMoreTokens())
            tokens.nextToken(); 
          if (tokens.hasMoreTokens())
            try {
              glyph.page = Integer.parseInt(tokens.nextToken());
            } catch (NumberFormatException e) {} 
          if (glyph.width > 0 && glyph.height > 0)
            this.descent = Math.min(baseLine + (float)glyph.yoffset, this.descent); 
        } 
        this.descent += this.padBottom;
        while (true) {
          line = reader.readLine();
          if (line == null || 
            !line.startsWith("kerning "))
            break; 
          StringTokenizer tokens = new StringTokenizer(line, " =");
          tokens.nextToken();
          tokens.nextToken();
          int first = Integer.parseInt(tokens.nextToken());
          tokens.nextToken();
          int second = Integer.parseInt(tokens.nextToken());
          if (first < 0 || first > 65535 || second < 0 || second > 65535)
            continue; 
          BitmapFont.Glyph glyph = getGlyph((char)first);
          tokens.nextToken();
          int amount = Integer.parseInt(tokens.nextToken());
          if (glyph != null)
            glyph.setKerning(second, amount); 
        } 
        boolean hasMetricsOverride = false;
        float overrideAscent = 0.0F;
        float overrideDescent = 0.0F;
        float overrideDown = 0.0F;
        float overrideCapHeight = 0.0F;
        float overrideLineHeight = 0.0F;
        float overrideSpaceXAdvance = 0.0F;
        float overrideXHeight = 0.0F;
        if (line != null && line.startsWith("metrics ")) {
          hasMetricsOverride = true;
          StringTokenizer tokens = new StringTokenizer(line, " =");
          tokens.nextToken();
          tokens.nextToken();
          overrideAscent = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideDescent = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideDown = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideCapHeight = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideLineHeight = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideSpaceXAdvance = Float.parseFloat(tokens.nextToken());
          tokens.nextToken();
          overrideXHeight = Float.parseFloat(tokens.nextToken());
        } 
        BitmapFont.Glyph spaceGlyph = getGlyph(' ');
        if (spaceGlyph == null) {
          spaceGlyph = new BitmapFont.Glyph();
          spaceGlyph.id = 32;
          BitmapFont.Glyph xadvanceGlyph = getGlyph('l');
          if (xadvanceGlyph == null)
            xadvanceGlyph = getFirstGlyph(); 
          spaceGlyph.xadvance = xadvanceGlyph.xadvance;
          setGlyph(32, spaceGlyph);
        } 
        if (spaceGlyph.width == 0) {
          spaceGlyph.width = (int)(this.padLeft + (float)spaceGlyph.xadvance + this.padRight);
          spaceGlyph.xoffset = (int)-this.padLeft;
        } 
        this.spaceXadvance = (float)spaceGlyph.xadvance;
        BitmapFont.Glyph xGlyph = null;
        for (char xChar : this.xChars) {
          xGlyph = getGlyph(xChar);
          if (xGlyph != null)
            break; 
        } 
        if (xGlyph == null)
          xGlyph = getFirstGlyph(); 
        this.xHeight = (float)xGlyph.height - padY;
        BitmapFont.Glyph capGlyph = null;
        for (char capChar : this.capChars) {
          capGlyph = getGlyph(capChar);
          if (capGlyph != null)
            break; 
        } 
        if (capGlyph == null) {
          for (BitmapFont.Glyph[] page : this.glyphs) {
            if (page != null)
              for (BitmapFont.Glyph glyph : page) {
                if (glyph != null && glyph.height != 0 && glyph.width != 0)
                  this.capHeight = Math.max(this.capHeight, (float)glyph.height); 
              }  
          } 
        } else
          this.capHeight = (float)capGlyph.height; 
        this.capHeight -= padY;
        this.ascent = baseLine - this.capHeight;
        this.down = -this.lineHeight;
        if (flip) {
          this.ascent = -this.ascent;
          this.down = -this.down;
        } 
        if (hasMetricsOverride) {
          this.ascent = overrideAscent;
          this.descent = overrideDescent;
          this.down = overrideDown;
          this.capHeight = overrideCapHeight;
          this.lineHeight = overrideLineHeight;
          this.spaceXadvance = overrideSpaceXAdvance;
          this.xHeight = overrideXHeight;
        } 
      } catch (Exception ex) {
        throw new GdxRuntimeException("Error loading font file: " + fontFile, ex);
      } finally {
        StreamUtils.closeQuietly(reader);
      } 
    }
    
    public void setGlyphRegion(BitmapFont.Glyph glyph, TextureRegion region) {
      Texture texture = region.getTexture();
      float invTexWidth = 1.0F / (float)texture.getWidth();
      float invTexHeight = 1.0F / (float)texture.getHeight();
      float offsetX = 0.0F, offsetY = 0.0F;
      float u = region.u;
      float v = region.v;
      float regionWidth = (float)region.getRegionWidth();
      float regionHeight = (float)region.getRegionHeight();
      if (region instanceof TextureAtlas.AtlasRegion) {
        TextureAtlas.AtlasRegion atlasRegion = (TextureAtlas.AtlasRegion)region;
        offsetX = atlasRegion.offsetX;
        offsetY = (float)(atlasRegion.originalHeight - atlasRegion.packedHeight) - atlasRegion.offsetY;
      } 
      float x = (float)glyph.srcX;
      float x2 = (float)(glyph.srcX + glyph.width);
      float y = (float)glyph.srcY;
      float y2 = (float)(glyph.srcY + glyph.height);
      if (offsetX > 0.0F) {
        x -= offsetX;
        if (x < 0.0F) {
          glyph.width = (int)((float)glyph.width + x);
          glyph.xoffset = (int)((float)glyph.xoffset - x);
          x = 0.0F;
        } 
        x2 -= offsetX;
        if (x2 > regionWidth) {
          glyph.width = (int)((float)glyph.width - (x2 - regionWidth));
          x2 = regionWidth;
        } 
      } 
      if (offsetY > 0.0F) {
        y -= offsetY;
        if (y < 0.0F) {
          glyph.height = (int)((float)glyph.height + y);
          if (glyph.height < 0)
            glyph.height = 0; 
          y = 0.0F;
        } 
        y2 -= offsetY;
        if (y2 > regionHeight) {
          float amount = y2 - regionHeight;
          glyph.height = (int)((float)glyph.height - amount);
          glyph.yoffset = (int)((float)glyph.yoffset + amount);
          y2 = regionHeight;
        } 
      } 
      glyph.u = u + x * invTexWidth;
      glyph.u2 = u + x2 * invTexWidth;
      if (this.flipped) {
        glyph.v = v + y * invTexHeight;
        glyph.v2 = v + y2 * invTexHeight;
      } else {
        glyph.v2 = v + y * invTexHeight;
        glyph.v = v + y2 * invTexHeight;
      } 
    }
    
    public void setLineHeight(float height) {
      this.lineHeight = height * this.scaleY;
      this.down = this.flipped ? this.lineHeight : -this.lineHeight;
    }
    
    public void setGlyph(int ch, BitmapFont.Glyph glyph) {
      BitmapFont.Glyph[] page = this.glyphs[ch / 512];
      if (page == null)
        this.glyphs[ch / 512] = page = new BitmapFont.Glyph[512]; 
      page[ch & 0x1FF] = glyph;
    }
    
    public BitmapFont.Glyph getFirstGlyph() {
      for (BitmapFont.Glyph[] page : this.glyphs) {
        if (page != null) {
          BitmapFont.Glyph[] arrayOfGlyph;
          int i, j;
          for (arrayOfGlyph = page, i = arrayOfGlyph.length, j = 0; j < i; ) {
            BitmapFont.Glyph glyph = arrayOfGlyph[j];
            if (glyph == null || glyph.height == 0 || glyph.width == 0) {
              j++;
              continue;
            } 
            return glyph;
          } 
        } 
      } 
      throw new GdxRuntimeException("No glyphs found.");
    }
    
    public boolean hasGlyph(char ch) {
      if (this.missingGlyph != null)
        return true; 
      return (getGlyph(ch) != null);
    }
    
    public BitmapFont.Glyph getGlyph(char ch) {
      BitmapFont.Glyph[] page = this.glyphs[ch / 512];
      if (page != null)
        return page[ch & 0x1FF]; 
      return null;
    }
    
    public void getGlyphs(GlyphLayout.GlyphRun run, CharSequence str, int start, int end, BitmapFont.Glyph lastGlyph) {
      int max = end - start;
      if (max == 0)
        return; 
      boolean markupEnabled = this.markupEnabled;
      float scaleX = this.scaleX;
      Array<BitmapFont.Glyph> glyphs = run.glyphs;
      FloatArray xAdvances = run.xAdvances;
      glyphs.ensureCapacity(max);
      run.xAdvances.ensureCapacity(max + 1);
      while (true) {
        while (true) {
          char ch = str.charAt(start++);
          if (ch == '\r')
            break; 
          BitmapFont.Glyph glyph = getGlyph(ch);
          if (glyph == null) {
            if (this.missingGlyph == null)
              break; 
            glyph = this.missingGlyph;
          } 
          glyphs.add(glyph);
          xAdvances.add((lastGlyph == null) ? (glyph.fixedWidth ? 0.0F : ((float)-glyph.xoffset * scaleX - this.padLeft)) : (
              
              (float)(lastGlyph.xadvance + lastGlyph.getKerning(ch)) * scaleX));
          lastGlyph = glyph;
          if (markupEnabled && ch == '[' && start < end && str.charAt(start) == '[')
            start++; 
          break;
        } 
        if (start >= end) {
          if (lastGlyph != null) {
            float lastGlyphWidth = lastGlyph.fixedWidth ? ((float)lastGlyph.xadvance * scaleX) : ((float)(lastGlyph.width + lastGlyph.xoffset) * scaleX - this.padRight);
            xAdvances.add(lastGlyphWidth);
          } 
          return;
        } 
      } 
    }
    
    public int getWrapIndex(Array<BitmapFont.Glyph> glyphs, int start) {
      int i = start - 1;
      Object[] glyphsItems = glyphs.items;
      char ch = (char)((BitmapFont.Glyph)glyphsItems[i]).id;
      if (isWhitespace(ch))
        return i; 
      if (isBreakChar(ch))
        i--; 
      for (; i > 0; i--) {
        ch = (char)((BitmapFont.Glyph)glyphsItems[i]).id;
        if (isWhitespace(ch) || isBreakChar(ch))
          return i + 1; 
      } 
      return 0;
    }
    
    public boolean isBreakChar(char c) {
      if (this.breakChars == null)
        return false; 
      for (char br : this.breakChars) {
        if (c == br)
          return true; 
      } 
      return false;
    }
    
    public boolean isWhitespace(char c) {
      switch (c) {
        case '\t':
        case '\n':
        case '\r':
        case ' ':
          return true;
      } 
      return false;
    }
    
    public String getImagePath(int index) {
      return this.imagePaths[index];
    }
    
    public String[] getImagePaths() {
      return this.imagePaths;
    }
    
    public FileHandle getFontFile() {
      return this.fontFile;
    }
    
    public void setScale(float scaleX, float scaleY) {
      if (scaleX == 0.0F)
        throw new IllegalArgumentException("scaleX cannot be 0."); 
      if (scaleY == 0.0F)
        throw new IllegalArgumentException("scaleY cannot be 0."); 
      float x = scaleX / this.scaleX;
      float y = scaleY / this.scaleY;
      this.lineHeight *= y;
      this.spaceXadvance *= x;
      this.xHeight *= y;
      this.capHeight *= y;
      this.ascent *= y;
      this.descent *= y;
      this.down *= y;
      this.padLeft *= x;
      this.padRight *= x;
      this.padTop *= y;
      this.padBottom *= y;
      this.scaleX = scaleX;
      this.scaleY = scaleY;
    }
    
    public void setScale(float scaleXY) {
      setScale(scaleXY, scaleXY);
    }
    
    public void scale(float amount) {
      setScale(this.scaleX + amount, this.scaleY + amount);
    }
    
    public String toString() {
      return (this.name != null) ? this.name : super.toString();
    }
  }
}
