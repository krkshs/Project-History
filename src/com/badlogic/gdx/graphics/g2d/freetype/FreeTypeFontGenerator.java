package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import java.nio.ByteBuffer;

public class FreeTypeFontGenerator implements Disposable {
  public static final String DEFAULT_CHARS = "\000ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$€-%+=#_&~* ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
  
  public static final int NO_MAXIMUM = -1;
  
  private static int maxTextureSize = 1024;
  
  final FreeType.Library library;
  
  final FreeType.Face face;
  
  final String name;
  
  boolean bitmapped = false;
  
  private int pixelWidth;
  
  private int pixelHeight;
  
  public FreeTypeFontGenerator(FileHandle fontFile) {
    this(fontFile, 0);
  }
  
  public FreeTypeFontGenerator(FileHandle fontFile, int faceIndex) {
    this.name = fontFile.nameWithoutExtension();
    this.library = FreeType.initFreeType();
    this.face = this.library.newFace(fontFile, faceIndex);
    if (checkForBitmapFont())
      return; 
    setPixelSizes(0, 15);
  }
  
  private int getLoadingFlags(FreeTypeFontParameter parameter) {
    int loadingFlags = FreeType.FT_LOAD_DEFAULT;
    switch (parameter.hinting) {
      case None:
        loadingFlags |= FreeType.FT_LOAD_NO_HINTING;
        break;
      case Slight:
        loadingFlags |= FreeType.FT_LOAD_TARGET_LIGHT;
        break;
      case Medium:
        loadingFlags |= FreeType.FT_LOAD_TARGET_NORMAL;
        break;
      case Full:
        loadingFlags |= FreeType.FT_LOAD_TARGET_MONO;
        break;
      case AutoSlight:
        loadingFlags |= FreeType.FT_LOAD_FORCE_AUTOHINT | FreeType.FT_LOAD_TARGET_LIGHT;
        break;
      case AutoMedium:
        loadingFlags |= FreeType.FT_LOAD_FORCE_AUTOHINT | FreeType.FT_LOAD_TARGET_NORMAL;
        break;
      case AutoFull:
        loadingFlags |= FreeType.FT_LOAD_FORCE_AUTOHINT | FreeType.FT_LOAD_TARGET_MONO;
        break;
    } 
    return loadingFlags;
  }
  
  private boolean loadChar(int c) {
    return loadChar(c, FreeType.FT_LOAD_DEFAULT | FreeType.FT_LOAD_FORCE_AUTOHINT);
  }
  
  private boolean loadChar(int c, int flags) {
    return this.face.loadChar(c, flags);
  }
  
  private boolean checkForBitmapFont() {
    int faceFlags = this.face.getFaceFlags();
    if ((faceFlags & FreeType.FT_FACE_FLAG_FIXED_SIZES) == FreeType.FT_FACE_FLAG_FIXED_SIZES && (faceFlags & FreeType.FT_FACE_FLAG_HORIZONTAL) == FreeType.FT_FACE_FLAG_HORIZONTAL)
      if (loadChar(32)) {
        FreeType.GlyphSlot slot = this.face.getGlyph();
        if (slot.getFormat() == 1651078259)
          this.bitmapped = true; 
      }  
    return this.bitmapped;
  }
  
  public BitmapFont generateFont(FreeTypeFontParameter parameter) {
    return generateFont(parameter, new FreeTypeBitmapFontData());
  }
  
  public BitmapFont generateFont(FreeTypeFontParameter parameter, FreeTypeBitmapFontData data) {
    boolean updateTextureRegions = (data.regions == null && parameter.packer != null);
    if (updateTextureRegions)
      data.regions = new Array<>(); 
    generateData(parameter, data);
    if (updateTextureRegions)
      parameter.packer.updateTextureRegions(data.regions, parameter.minFilter, parameter.magFilter, parameter.genMipMaps); 
    if (data.regions.isEmpty())
      throw new GdxRuntimeException("Unable to create a font with no texture regions."); 
    BitmapFont font = newBitmapFont(data, data.regions, true);
    font.setOwnsTexture((parameter.packer == null));
    return font;
  }
  
  protected BitmapFont newBitmapFont(BitmapFont.BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
    return new BitmapFont(data, pageRegions, integer);
  }
  
  public int scaleForPixelHeight(int height) {
    setPixelSizes(0, height);
    FreeType.SizeMetrics fontMetrics = this.face.getSize().getMetrics();
    int ascent = FreeType.toInt(fontMetrics.getAscender());
    int descent = FreeType.toInt(fontMetrics.getDescender());
    return height * height / (ascent - descent);
  }
  
  public int scaleForPixelWidth(int width, int numChars) {
    FreeType.SizeMetrics fontMetrics = this.face.getSize().getMetrics();
    int advance = FreeType.toInt(fontMetrics.getMaxAdvance());
    int ascent = FreeType.toInt(fontMetrics.getAscender());
    int descent = FreeType.toInt(fontMetrics.getDescender());
    int unscaledHeight = ascent - descent;
    int height = unscaledHeight * width / (advance * numChars);
    setPixelSizes(0, height);
    return height;
  }
  
  public int scaleToFitSquare(int width, int height, int numChars) {
    return Math.min(scaleForPixelHeight(height), scaleForPixelWidth(width, numChars));
  }
  
  public class GlyphAndBitmap {
    public BitmapFont.Glyph glyph;
    
    @Null
    public FreeType.Bitmap bitmap;
  }
  
  @Null
  public GlyphAndBitmap generateGlyphAndBitmap(int c, int size, boolean flip) {
    FreeType.Bitmap bitmap;
    setPixelSizes(0, size);
    FreeType.SizeMetrics fontMetrics = this.face.getSize().getMetrics();
    int baseline = FreeType.toInt(fontMetrics.getAscender());
    if (this.face.getCharIndex(c) == 0)
      return null; 
    if (!loadChar(c))
      throw new GdxRuntimeException("Unable to load character!"); 
    FreeType.GlyphSlot slot = this.face.getGlyph();
    if (this.bitmapped) {
      bitmap = slot.getBitmap();
    } else if (!slot.renderGlyph(FreeType.FT_RENDER_MODE_NORMAL)) {
      bitmap = null;
    } else
      bitmap = slot.getBitmap(); 
    FreeType.GlyphMetrics metrics = slot.getMetrics();
    BitmapFont.Glyph glyph = new BitmapFont.Glyph();
    if (bitmap != null) {
      glyph.width = bitmap.getWidth();
      glyph.height = bitmap.getRows();
    } else {
      glyph.width = 0;
      glyph.height = 0;
    } 
    glyph.xoffset = slot.getBitmapLeft();
    glyph.yoffset = flip ? (-slot.getBitmapTop() + baseline) : (-(glyph.height - slot.getBitmapTop()) - baseline);
    glyph.xadvance = FreeType.toInt(metrics.getHoriAdvance());
    glyph.srcX = 0;
    glyph.srcY = 0;
    glyph.id = c;
    GlyphAndBitmap result = new GlyphAndBitmap();
    result.glyph = glyph;
    result.bitmap = bitmap;
    return result;
  }
  
  public FreeTypeBitmapFontData generateData(int size) {
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = size;
    return generateData(parameter);
  }
  
  public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter) {
    return generateData(parameter, new FreeTypeBitmapFontData());
  }
  
  void setPixelSizes(int pixelWidth, int pixelHeight) {
    this.pixelWidth = pixelWidth;
    this.pixelHeight = pixelHeight;
    if (!this.bitmapped && !this.face.setPixelSizes(pixelWidth, pixelHeight))
      throw new GdxRuntimeException("Couldn't set size for font"); 
  }
  
  public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter, FreeTypeBitmapFontData data) {
    data.name = this.name + "-" + parameter.size;
    char[] characters = parameter.characters.toCharArray();
    int charactersLength = characters.length;
    boolean incremental = parameter.incremental;
    int flags = getLoadingFlags(parameter);
    setPixelSizes(0, parameter.size);
    FreeType.SizeMetrics fontMetrics = this.face.getSize().getMetrics();
    data.flipped = parameter.flip;
    data.ascent = (float)FreeType.toInt(fontMetrics.getAscender());
    data.descent = (float)FreeType.toInt(fontMetrics.getDescender());
    data.lineHeight = (float)FreeType.toInt(fontMetrics.getHeight());
    float baseLine = data.ascent;
    if (this.bitmapped && data.lineHeight == 0.0F)
      for (int c = 32; c < 32 + this.face.getNumGlyphs(); c++) {
        if (loadChar(c, flags)) {
          int lh = FreeType.toInt(this.face.getGlyph().getMetrics().getHeight());
          data.lineHeight = ((float)lh > data.lineHeight) ? (float)lh : data.lineHeight;
        } 
      }  
    data.lineHeight += (float)parameter.spaceY;
    if (loadChar(32, flags) || loadChar(108, flags)) {
      data.spaceXadvance = (float)FreeType.toInt(this.face.getGlyph().getMetrics().getHoriAdvance());
    } else
      data.spaceXadvance = (float)this.face.getMaxAdvanceWidth(); 
    char[] arrayOfChar2;
    int k, n;
    for (arrayOfChar2 = data.xChars, k = arrayOfChar2.length, n = 0; n < k; ) {
      char xChar = arrayOfChar2[n];
      if (!loadChar(xChar, flags)) {
        n++;
        continue;
      } 
      data.xHeight = (float)FreeType.toInt(this.face.getGlyph().getMetrics().getHeight());
      break;
    } 
    if (data.xHeight == 0.0F)
      throw new GdxRuntimeException("No x-height character found in font"); 
    char[] arrayOfChar1;
    int j, m;
    for (arrayOfChar1 = data.capChars, j = arrayOfChar1.length, m = 0; m < j; ) {
      char capChar = arrayOfChar1[m];
      if (!loadChar(capChar, flags)) {
        m++;
        continue;
      } 
      data.capHeight = (float)(FreeType.toInt(this.face.getGlyph().getMetrics().getHeight()) + Math.abs(parameter.shadowOffsetY));
      break;
    } 
    if (!this.bitmapped && data.capHeight == 1.0F)
      throw new GdxRuntimeException("No cap character found in font"); 
    data.ascent -= data.capHeight;
    data.down = -data.lineHeight;
    if (parameter.flip) {
      data.ascent = -data.ascent;
      data.down = -data.down;
    } 
    boolean ownsAtlas = false;
    PixmapPacker packer = parameter.packer;
    if (packer == null) {
      int size;
      PixmapPacker.PackStrategy packStrategy;
      if (incremental) {
        size = maxTextureSize;
        packStrategy = new PixmapPacker.GuillotineStrategy();
      } else {
        int maxGlyphHeight = (int)Math.ceil((double)data.lineHeight);
        size = MathUtils.nextPowerOfTwo((int)Math.sqrt((double)(maxGlyphHeight * maxGlyphHeight * charactersLength)));
        if (maxTextureSize > 0)
          size = Math.min(size, maxTextureSize); 
        packStrategy = new PixmapPacker.SkylineStrategy();
      } 
      ownsAtlas = true;
      packer = new PixmapPacker(size, size, Pixmap.Format.RGBA8888, 1, false, packStrategy);
      packer.setTransparentColor(parameter.color);
      (packer.getTransparentColor()).a = 0.0F;
      if (parameter.borderWidth > 0.0F) {
        packer.setTransparentColor(parameter.borderColor);
        (packer.getTransparentColor()).a = 0.0F;
      } 
    } 
    if (incremental)
      data.glyphs = new Array<>(charactersLength + 32); 
    FreeType.Stroker stroker = null;
    if (parameter.borderWidth > 0.0F) {
      stroker = this.library.createStroker();
      stroker.set((int)(parameter.borderWidth * 64.0F), parameter.borderStraight ? FreeType.FT_STROKER_LINECAP_BUTT : FreeType.FT_STROKER_LINECAP_ROUND, parameter.borderStraight ? FreeType.FT_STROKER_LINEJOIN_MITER_FIXED : FreeType.FT_STROKER_LINEJOIN_ROUND, 0);
    } 
    int[] heights = new int[charactersLength];
    for (int i = 0; i < charactersLength; i++) {
      char c = characters[i];
      int height = loadChar(c, flags) ? FreeType.toInt(this.face.getGlyph().getMetrics().getHeight()) : 0;
      heights[i] = height;
      if (c == '\000') {
        BitmapFont.Glyph missingGlyph = createGlyph('\000', data, parameter, stroker, baseLine, packer);
        if (missingGlyph != null && missingGlyph.width != 0 && missingGlyph.height != 0) {
          data.setGlyph(0, missingGlyph);
          data.missingGlyph = missingGlyph;
          if (incremental)
            data.glyphs.add(missingGlyph); 
        } 
      } 
    } 
    int heightsCount = heights.length;
    while (heightsCount > 0) {
      int best = 0, maxHeight = heights[0];
      for (int i1 = 1; i1 < heightsCount; i1++) {
        int height = heights[i1];
        if (height > maxHeight) {
          maxHeight = height;
          best = i1;
        } 
      } 
      char c = characters[best];
      if (data.getGlyph(c) == null) {
        BitmapFont.Glyph glyph = createGlyph(c, data, parameter, stroker, baseLine, packer);
        if (glyph != null) {
          data.setGlyph(c, glyph);
          if (incremental)
            data.glyphs.add(glyph); 
        } 
      } 
      heightsCount--;
      heights[best] = heights[heightsCount];
      char tmpChar = characters[best];
      characters[best] = characters[heightsCount];
      characters[heightsCount] = tmpChar;
    } 
    if (stroker != null && !incremental)
      stroker.dispose(); 
    if (incremental) {
      data.generator = this;
      data.parameter = parameter;
      data.stroker = stroker;
      data.packer = packer;
    } 
    parameter.kerning &= this.face.hasKerning();
    if (parameter.kerning)
      for (int i1 = 0; i1 < charactersLength; i1++) {
        char firstChar = characters[i1];
        BitmapFont.Glyph first = data.getGlyph(firstChar);
        if (first != null) {
          int firstIndex = this.face.getCharIndex(firstChar);
          for (int ii = i1; ii < charactersLength; ii++) {
            char secondChar = characters[ii];
            BitmapFont.Glyph second = data.getGlyph(secondChar);
            if (second != null) {
              int secondIndex = this.face.getCharIndex(secondChar);
              int kerning = this.face.getKerning(firstIndex, secondIndex, 0);
              if (kerning != 0)
                first.setKerning(secondChar, FreeType.toInt(kerning)); 
              kerning = this.face.getKerning(secondIndex, firstIndex, 0);
              if (kerning != 0)
                second.setKerning(firstChar, FreeType.toInt(kerning)); 
            } 
          } 
        } 
      }  
    if (ownsAtlas) {
      data.regions = new Array<>();
      packer.updateTextureRegions(data.regions, parameter.minFilter, parameter.magFilter, parameter.genMipMaps);
    } 
    BitmapFont.Glyph spaceGlyph = data.getGlyph(' ');
    if (spaceGlyph == null) {
      spaceGlyph = new BitmapFont.Glyph();
      spaceGlyph.xadvance = (int)data.spaceXadvance + parameter.spaceX;
      spaceGlyph.id = 32;
      data.setGlyph(32, spaceGlyph);
    } 
    if (spaceGlyph.width == 0)
      spaceGlyph.width = (int)((float)spaceGlyph.xadvance + data.padRight); 
    return data;
  }
  
  @Null
  protected BitmapFont.Glyph createGlyph(char c, FreeTypeBitmapFontData data, FreeTypeFontParameter parameter, FreeType.Stroker stroker, float baseLine, PixmapPacker packer) {
    boolean missing = (this.face.getCharIndex(c) == 0 && c != '\000');
    if (missing)
      return null; 
    if (!loadChar(c, getLoadingFlags(parameter)))
      return null; 
    FreeType.GlyphSlot slot = this.face.getGlyph();
    FreeType.Glyph mainGlyph = slot.getGlyph();
    try {
      mainGlyph.toBitmap(parameter.mono ? FreeType.FT_RENDER_MODE_MONO : FreeType.FT_RENDER_MODE_NORMAL);
    } catch (GdxRuntimeException e) {
      mainGlyph.dispose();
      Gdx.app.log("FreeTypeFontGenerator", "Couldn't render char: " + c);
      return null;
    } 
    FreeType.Bitmap mainBitmap = mainGlyph.getBitmap();
    Pixmap mainPixmap = mainBitmap.getPixmap(Pixmap.Format.RGBA8888, parameter.color, parameter.gamma);
    if (mainBitmap.getWidth() != 0 && mainBitmap.getRows() != 0) {
      int offsetX = 0, offsetY = 0;
      if (parameter.borderWidth > 0.0F) {
        int top = mainGlyph.getTop(), left = mainGlyph.getLeft();
        FreeType.Glyph borderGlyph = slot.getGlyph();
        borderGlyph.strokeBorder(stroker, false);
        borderGlyph.toBitmap(parameter.mono ? FreeType.FT_RENDER_MODE_MONO : FreeType.FT_RENDER_MODE_NORMAL);
        offsetX = left - borderGlyph.getLeft();
        offsetY = -(top - borderGlyph.getTop());
        FreeType.Bitmap borderBitmap = borderGlyph.getBitmap();
        Pixmap borderPixmap = borderBitmap.getPixmap(Pixmap.Format.RGBA8888, parameter.borderColor, parameter.borderGamma);
        for (int i = 0, n = parameter.renderCount; i < n; i++)
          borderPixmap.drawPixmap(mainPixmap, offsetX, offsetY); 
        mainPixmap.dispose();
        mainGlyph.dispose();
        mainPixmap = borderPixmap;
        mainGlyph = borderGlyph;
      } 
      if (parameter.shadowOffsetX != 0 || parameter.shadowOffsetY != 0) {
        int mainW = mainPixmap.getWidth(), mainH = mainPixmap.getHeight();
        int shadowOffsetX = Math.max(parameter.shadowOffsetX, 0), shadowOffsetY = Math.max(parameter.shadowOffsetY, 0);
        int shadowW = mainW + Math.abs(parameter.shadowOffsetX), shadowH = mainH + Math.abs(parameter.shadowOffsetY);
        Pixmap shadowPixmap = new Pixmap(shadowW, shadowH, mainPixmap.getFormat());
        Color shadowColor = parameter.shadowColor;
        float a = shadowColor.a;
        if (a != 0.0F) {
          byte r = (byte)(int)(shadowColor.r * 255.0F), g = (byte)(int)(shadowColor.g * 255.0F), b = (byte)(int)(shadowColor.b * 255.0F);
          ByteBuffer mainPixels = mainPixmap.getPixels();
          ByteBuffer shadowPixels = shadowPixmap.getPixels();
          for (int y = 0; y < mainH; y++) {
            int shadowRow = shadowW * (y + shadowOffsetY) + shadowOffsetX;
            for (int x = 0; x < mainW; x++) {
              int mainPixel = (mainW * y + x) * 4;
              byte mainA = mainPixels.get(mainPixel + 3);
              if (mainA != 0) {
                int shadowPixel = (shadowRow + x) * 4;
                shadowPixels.put(shadowPixel, r);
                shadowPixels.put(shadowPixel + 1, g);
                shadowPixels.put(shadowPixel + 2, b);
                shadowPixels.put(shadowPixel + 3, (byte)(int)((float)(mainA & 0xFF) * a));
              } 
            } 
          } 
        } 
        for (int i = 0, n = parameter.renderCount; i < n; i++)
          shadowPixmap.drawPixmap(mainPixmap, Math.max(-parameter.shadowOffsetX, 0), Math.max(-parameter.shadowOffsetY, 0)); 
        mainPixmap.dispose();
        mainPixmap = shadowPixmap;
      } else if (parameter.borderWidth == 0.0F) {
        for (int i = 0, n = parameter.renderCount - 1; i < n; i++)
          mainPixmap.drawPixmap(mainPixmap, 0, 0); 
      } 
      if (parameter.padTop > 0 || parameter.padLeft > 0 || parameter.padBottom > 0 || parameter.padRight > 0) {
        Pixmap padPixmap = new Pixmap(mainPixmap.getWidth() + parameter.padLeft + parameter.padRight, 
            mainPixmap.getHeight() + parameter.padTop + parameter.padBottom, mainPixmap.getFormat());
        padPixmap.setBlending(Pixmap.Blending.None);
        padPixmap.drawPixmap(mainPixmap, parameter.padLeft, parameter.padTop);
        mainPixmap.dispose();
        mainPixmap = padPixmap;
      } 
    } 
    FreeType.GlyphMetrics metrics = slot.getMetrics();
    BitmapFont.Glyph glyph = new BitmapFont.Glyph();
    glyph.id = c;
    glyph.width = mainPixmap.getWidth();
    glyph.height = mainPixmap.getHeight();
    glyph.xoffset = mainGlyph.getLeft();
    if (parameter.flip) {
      glyph.yoffset = -mainGlyph.getTop() + (int)baseLine;
    } else
      glyph.yoffset = -(glyph.height - mainGlyph.getTop()) - (int)baseLine; 
    glyph.xadvance = FreeType.toInt(metrics.getHoriAdvance()) + (int)parameter.borderWidth + parameter.spaceX;
    if (this.bitmapped) {
      mainPixmap.setColor(Color.CLEAR);
      mainPixmap.fill();
      ByteBuffer buf = mainBitmap.getBuffer();
      int whiteIntBits = Color.WHITE.toIntBits();
      int clearIntBits = Color.CLEAR.toIntBits();
      for (int h = 0; h < glyph.height; h++) {
        int idx = h * mainBitmap.getPitch();
        for (int w = 0; w < glyph.width + glyph.xoffset; w++) {
          int bit = buf.get(idx + w / 8) >>> 7 - w % 8 & 0x1;
          mainPixmap.drawPixel(w, h, (bit == 1) ? whiteIntBits : clearIntBits);
        } 
      } 
    } 
    Rectangle rect = packer.pack(mainPixmap);
    glyph.page = (packer.getPages()).size - 1;
    glyph.srcX = (int)rect.x;
    glyph.srcY = (int)rect.y;
    if (parameter.incremental && data.regions != null && data.regions.size <= glyph.page)
      packer.updateTextureRegions(data.regions, parameter.minFilter, parameter.magFilter, parameter.genMipMaps); 
    mainPixmap.dispose();
    mainGlyph.dispose();
    return glyph;
  }
  
  public String toString() {
    return this.name;
  }
  
  public void dispose() {
    this.face.dispose();
    this.library.dispose();
  }
  
  public static void setMaxTextureSize(int texSize) {
    maxTextureSize = texSize;
  }
  
  public static int getMaxTextureSize() {
    return maxTextureSize;
  }
  
  public static class FreeTypeBitmapFontData extends BitmapFont.BitmapFontData implements Disposable {
    public Array<TextureRegion> regions;
    
    FreeTypeFontGenerator generator;
    
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    
    FreeType.Stroker stroker;
    
    PixmapPacker packer;
    
    Array<BitmapFont.Glyph> glyphs;
    
    private boolean dirty;
    
    public BitmapFont.Glyph getGlyph(char ch) {
      BitmapFont.Glyph glyph = super.getGlyph(ch);
      if (glyph == null && this.generator != null) {
        this.generator.setPixelSizes(0, this.parameter.size);
        float baseline = ((this.flipped ? -this.ascent : this.ascent) + this.capHeight) / this.scaleY;
        glyph = this.generator.createGlyph(ch, this, this.parameter, this.stroker, baseline, this.packer);
        if (glyph == null)
          return this.missingGlyph; 
        setGlyphRegion(glyph, this.regions.get(glyph.page));
        setGlyph(ch, glyph);
        this.glyphs.add(glyph);
        this.dirty = true;
        FreeType.Face face = this.generator.face;
        if (this.parameter.kerning) {
          int glyphIndex = face.getCharIndex(ch);
          for (int i = 0, n = this.glyphs.size; i < n; i++) {
            BitmapFont.Glyph other = this.glyphs.get(i);
            int otherIndex = face.getCharIndex(other.id);
            int kerning = face.getKerning(glyphIndex, otherIndex, 0);
            if (kerning != 0)
              glyph.setKerning(other.id, FreeType.toInt(kerning)); 
            kerning = face.getKerning(otherIndex, glyphIndex, 0);
            if (kerning != 0)
              other.setKerning(ch, FreeType.toInt(kerning)); 
          } 
        } 
      } 
      return glyph;
    }
    
    public void getGlyphs(GlyphLayout.GlyphRun run, CharSequence str, int start, int end, BitmapFont.Glyph lastGlyph) {
      if (this.packer != null)
        this.packer.setPackToTexture(true); 
      super.getGlyphs(run, str, start, end, lastGlyph);
      if (this.dirty) {
        this.dirty = false;
        this.packer.updateTextureRegions(this.regions, this.parameter.minFilter, this.parameter.magFilter, this.parameter.genMipMaps);
      } 
    }
    
    public void dispose() {
      if (this.stroker != null)
        this.stroker.dispose(); 
      if (this.packer != null)
        this.packer.dispose(); 
    }
  }
  
  public enum Hinting {
    None, Slight, Medium, Full, AutoSlight, AutoMedium, AutoFull;
  }
  
  public static class FreeTypeFontParameter {
    public int size = 16;
    
    public boolean mono;
    
    public FreeTypeFontGenerator.Hinting hinting = FreeTypeFontGenerator.Hinting.AutoMedium;
    
    public Color color = Color.WHITE;
    
    public float gamma = 1.8F;
    
    public int renderCount = 2;
    
    public float borderWidth = 0.0F;
    
    public Color borderColor = Color.BLACK;
    
    public boolean borderStraight = false;
    
    public float borderGamma = 1.8F;
    
    public int shadowOffsetX = 0;
    
    public int shadowOffsetY = 0;
    
    public Color shadowColor = new Color(0.0F, 0.0F, 0.0F, 0.75F);
    
    public int spaceX;
    
    public int spaceY;
    
    public int padTop;
    
    public int padLeft;
    
    public int padBottom;
    
    public int padRight;
    
    public String characters = "\000ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$€-%+=#_&~* ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
    
    public boolean kerning = true;
    
    public PixmapPacker packer = null;
    
    public boolean flip = false;
    
    public boolean genMipMaps = false;
    
    public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
    
    public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
    
    public boolean incremental;
  }
}
