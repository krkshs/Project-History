package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class FreeType {
  private static class Pointer {
    long address;
    
    Pointer(long address) {
      this.address = address;
    }
  }
  
  public static class Library extends Pointer implements Disposable {
    LongMap<ByteBuffer> fontData = new LongMap<>();
    
    Library(long address) {
      super(address);
    }
    
    public void dispose() {
      doneFreeType(this.address);
      for (ByteBuffer buffer : this.fontData.values()) {
        if (BufferUtils.isUnsafeByteBuffer(buffer))
          BufferUtils.disposeUnsafeByteBuffer(buffer); 
      } 
    }
    
    private static native void doneFreeType(long param1Long);
    
    public FreeType.Face newFace(FileHandle fontFile, int faceIndex) {
      ByteBuffer buffer = null;
      try {
        buffer = fontFile.map();
      } catch (GdxRuntimeException e) {}
      if (buffer == null) {
        InputStream input = fontFile.read();
        try {
          int fileSize = (int)fontFile.length();
          if (fileSize == 0) {
            byte[] data = StreamUtils.copyStreamToByteArray(input, 16384);
            buffer = BufferUtils.newUnsafeByteBuffer(data.length);
            BufferUtils.copy(data, 0, buffer, data.length);
          } else {
            buffer = BufferUtils.newUnsafeByteBuffer(fileSize);
            StreamUtils.copyStream(input, buffer);
          } 
        } catch (IOException ex) {
          throw new GdxRuntimeException(ex);
        } finally {
          StreamUtils.closeQuietly(input);
        } 
      } 
      return newMemoryFace(buffer, faceIndex);
    }
    
    public FreeType.Face newMemoryFace(byte[] data, int dataSize, int faceIndex) {
      ByteBuffer buffer = BufferUtils.newUnsafeByteBuffer(data.length);
      BufferUtils.copy(data, 0, buffer, data.length);
      return newMemoryFace(buffer, faceIndex);
    }
    
    public FreeType.Face newMemoryFace(ByteBuffer buffer, int faceIndex) {
      long face = newMemoryFace(this.address, buffer, buffer.remaining(), faceIndex);
      if (face == 0L) {
        if (BufferUtils.isUnsafeByteBuffer(buffer))
          BufferUtils.disposeUnsafeByteBuffer(buffer); 
        throw new GdxRuntimeException("Couldn't load font, FreeType error code: " + FreeType.getLastErrorCode());
      } 
      this.fontData.put(face, buffer);
      return new FreeType.Face(face, this);
    }
    
    private static native long newMemoryFace(long param1Long, ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2);
    
    public FreeType.Stroker createStroker() {
      long stroker = strokerNew(this.address);
      if (stroker == 0L)
        throw new GdxRuntimeException("Couldn't create FreeType stroker, FreeType error code: " + FreeType.getLastErrorCode()); 
      return new FreeType.Stroker(stroker);
    }
    
    private static native long strokerNew(long param1Long);
  }
  
  public static class Face extends Pointer implements Disposable {
    FreeType.Library library;
    
    public Face(long address, FreeType.Library library) {
      super(address);
      this.library = library;
    }
    
    public void dispose() {
      doneFace(this.address);
      ByteBuffer buffer = this.library.fontData.get(this.address);
      if (buffer != null) {
        this.library.fontData.remove(this.address);
        if (BufferUtils.isUnsafeByteBuffer(buffer))
          BufferUtils.disposeUnsafeByteBuffer(buffer); 
      } 
    }
    
    private static native void doneFace(long param1Long);
    
    public int getFaceFlags() {
      return getFaceFlags(this.address);
    }
    
    private static native int getFaceFlags(long param1Long);
    
    public int getStyleFlags() {
      return getStyleFlags(this.address);
    }
    
    private static native int getStyleFlags(long param1Long);
    
    public int getNumGlyphs() {
      return getNumGlyphs(this.address);
    }
    
    private static native int getNumGlyphs(long param1Long);
    
    public int getAscender() {
      return getAscender(this.address);
    }
    
    private static native int getAscender(long param1Long);
    
    public int getDescender() {
      return getDescender(this.address);
    }
    
    private static native int getDescender(long param1Long);
    
    public int getHeight() {
      return getHeight(this.address);
    }
    
    private static native int getHeight(long param1Long);
    
    public int getMaxAdvanceWidth() {
      return getMaxAdvanceWidth(this.address);
    }
    
    private static native int getMaxAdvanceWidth(long param1Long);
    
    public int getMaxAdvanceHeight() {
      return getMaxAdvanceHeight(this.address);
    }
    
    private static native int getMaxAdvanceHeight(long param1Long);
    
    public int getUnderlinePosition() {
      return getUnderlinePosition(this.address);
    }
    
    private static native int getUnderlinePosition(long param1Long);
    
    public int getUnderlineThickness() {
      return getUnderlineThickness(this.address);
    }
    
    private static native int getUnderlineThickness(long param1Long);
    
    public boolean selectSize(int strikeIndex) {
      return selectSize(this.address, strikeIndex);
    }
    
    private static native boolean selectSize(long param1Long, int param1Int);
    
    public boolean setCharSize(int charWidth, int charHeight, int horzResolution, int vertResolution) {
      return setCharSize(this.address, charWidth, charHeight, horzResolution, vertResolution);
    }
    
    private static native boolean setCharSize(long param1Long, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
    
    public boolean setPixelSizes(int pixelWidth, int pixelHeight) {
      return setPixelSizes(this.address, pixelWidth, pixelHeight);
    }
    
    private static native boolean setPixelSizes(long param1Long, int param1Int1, int param1Int2);
    
    public boolean loadGlyph(int glyphIndex, int loadFlags) {
      return loadGlyph(this.address, glyphIndex, loadFlags);
    }
    
    private static native boolean loadGlyph(long param1Long, int param1Int1, int param1Int2);
    
    public boolean loadChar(int charCode, int loadFlags) {
      return loadChar(this.address, charCode, loadFlags);
    }
    
    private static native boolean loadChar(long param1Long, int param1Int1, int param1Int2);
    
    public FreeType.GlyphSlot getGlyph() {
      return new FreeType.GlyphSlot(getGlyph(this.address));
    }
    
    private static native long getGlyph(long param1Long);
    
    public FreeType.Size getSize() {
      return new FreeType.Size(getSize(this.address));
    }
    
    private static native long getSize(long param1Long);
    
    public boolean hasKerning() {
      return hasKerning(this.address);
    }
    
    private static native boolean hasKerning(long param1Long);
    
    public int getKerning(int leftGlyph, int rightGlyph, int kernMode) {
      return getKerning(this.address, leftGlyph, rightGlyph, kernMode);
    }
    
    private static native int getKerning(long param1Long, int param1Int1, int param1Int2, int param1Int3);
    
    public int getCharIndex(int charCode) {
      return getCharIndex(this.address, charCode);
    }
    
    private static native int getCharIndex(long param1Long, int param1Int);
  }
  
  public static class Size extends Pointer {
    Size(long address) {
      super(address);
    }
    
    public FreeType.SizeMetrics getMetrics() {
      return new FreeType.SizeMetrics(getMetrics(this.address));
    }
    
    private static native long getMetrics(long param1Long);
  }
  
  public static class SizeMetrics extends Pointer {
    SizeMetrics(long address) {
      super(address);
    }
    
    public int getXppem() {
      return getXppem(this.address);
    }
    
    private static native int getXppem(long param1Long);
    
    public int getYppem() {
      return getYppem(this.address);
    }
    
    private static native int getYppem(long param1Long);
    
    public int getXScale() {
      return getXscale(this.address);
    }
    
    private static native int getXscale(long param1Long);
    
    public int getYscale() {
      return getYscale(this.address);
    }
    
    private static native int getYscale(long param1Long);
    
    public int getAscender() {
      return getAscender(this.address);
    }
    
    private static native int getAscender(long param1Long);
    
    public int getDescender() {
      return getDescender(this.address);
    }
    
    private static native int getDescender(long param1Long);
    
    public int getHeight() {
      return getHeight(this.address);
    }
    
    private static native int getHeight(long param1Long);
    
    public int getMaxAdvance() {
      return getMaxAdvance(this.address);
    }
    
    private static native int getMaxAdvance(long param1Long);
  }
  
  public static class GlyphSlot extends Pointer {
    GlyphSlot(long address) {
      super(address);
    }
    
    public FreeType.GlyphMetrics getMetrics() {
      return new FreeType.GlyphMetrics(getMetrics(this.address));
    }
    
    private static native long getMetrics(long param1Long);
    
    public int getLinearHoriAdvance() {
      return getLinearHoriAdvance(this.address);
    }
    
    private static native int getLinearHoriAdvance(long param1Long);
    
    public int getLinearVertAdvance() {
      return getLinearVertAdvance(this.address);
    }
    
    private static native int getLinearVertAdvance(long param1Long);
    
    public int getAdvanceX() {
      return getAdvanceX(this.address);
    }
    
    private static native int getAdvanceX(long param1Long);
    
    public int getAdvanceY() {
      return getAdvanceY(this.address);
    }
    
    private static native int getAdvanceY(long param1Long);
    
    public int getFormat() {
      return getFormat(this.address);
    }
    
    private static native int getFormat(long param1Long);
    
    public FreeType.Bitmap getBitmap() {
      return new FreeType.Bitmap(getBitmap(this.address));
    }
    
    private static native long getBitmap(long param1Long);
    
    public int getBitmapLeft() {
      return getBitmapLeft(this.address);
    }
    
    private static native int getBitmapLeft(long param1Long);
    
    public int getBitmapTop() {
      return getBitmapTop(this.address);
    }
    
    private static native int getBitmapTop(long param1Long);
    
    public boolean renderGlyph(int renderMode) {
      return renderGlyph(this.address, renderMode);
    }
    
    private static native boolean renderGlyph(long param1Long, int param1Int);
    
    public FreeType.Glyph getGlyph() {
      long glyph = getGlyph(this.address);
      if (glyph == 0L)
        throw new GdxRuntimeException("Couldn't get glyph, FreeType error code: " + FreeType.getLastErrorCode()); 
      return new FreeType.Glyph(glyph);
    }
    
    private static native long getGlyph(long param1Long);
  }
  
  public static class Glyph extends Pointer implements Disposable {
    private boolean rendered;
    
    Glyph(long address) {
      super(address);
    }
    
    public void dispose() {
      done(this.address);
    }
    
    private static native void done(long param1Long);
    
    public void strokeBorder(FreeType.Stroker stroker, boolean inside) {
      this.address = strokeBorder(this.address, stroker.address, inside);
    }
    
    private static native long strokeBorder(long param1Long1, long param1Long2, boolean param1Boolean);
    
    public void toBitmap(int renderMode) {
      long bitmap = toBitmap(this.address, renderMode);
      if (bitmap == 0L)
        throw new GdxRuntimeException("Couldn't render glyph, FreeType error code: " + FreeType.getLastErrorCode()); 
      this.address = bitmap;
      this.rendered = true;
    }
    
    private static native long toBitmap(long param1Long, int param1Int);
    
    public FreeType.Bitmap getBitmap() {
      if (!this.rendered)
        throw new GdxRuntimeException("Glyph is not yet rendered"); 
      return new FreeType.Bitmap(getBitmap(this.address));
    }
    
    private static native long getBitmap(long param1Long);
    
    public int getLeft() {
      if (!this.rendered)
        throw new GdxRuntimeException("Glyph is not yet rendered"); 
      return getLeft(this.address);
    }
    
    private static native int getLeft(long param1Long);
    
    public int getTop() {
      if (!this.rendered)
        throw new GdxRuntimeException("Glyph is not yet rendered"); 
      return getTop(this.address);
    }
    
    private static native int getTop(long param1Long);
  }
  
  public static class Bitmap extends Pointer {
    Bitmap(long address) {
      super(address);
    }
    
    public int getRows() {
      return getRows(this.address);
    }
    
    private static native int getRows(long param1Long);
    
    public int getWidth() {
      return getWidth(this.address);
    }
    
    private static native int getWidth(long param1Long);
    
    public int getPitch() {
      return getPitch(this.address);
    }
    
    private static native int getPitch(long param1Long);
    
    public ByteBuffer getBuffer() {
      if (getRows() == 0)
        return BufferUtils.newByteBuffer(1); 
      return getBuffer(this.address);
    }
    
    private static native ByteBuffer getBuffer(long param1Long);
    
    public Pixmap getPixmap(Pixmap.Format format, Color color, float gamma) {
      Pixmap pixmap;
      int width = getWidth(), rows = getRows();
      ByteBuffer src = getBuffer();
      int pixelMode = getPixelMode();
      int rowBytes = Math.abs(getPitch());
      if (color == Color.WHITE && pixelMode == FreeType.FT_PIXEL_MODE_GRAY && rowBytes == width && gamma == 1.0F) {
        pixmap = new Pixmap(width, rows, Pixmap.Format.Alpha);
        BufferUtils.copy(src, pixmap.getPixels(), pixmap.getPixels().capacity());
      } else {
        pixmap = new Pixmap(width, rows, Pixmap.Format.RGBA8888);
        int rgba = Color.rgba8888(color);
        byte[] srcRow = new byte[rowBytes];
        int[] dstRow = new int[width];
        IntBuffer dst = pixmap.getPixels().asIntBuffer();
        if (pixelMode == FreeType.FT_PIXEL_MODE_MONO) {
          for (int y = 0; y < rows; y++) {
            src.get(srcRow);
            for (int i = 0, x = 0; x < width; i++, x += 8) {
              byte b = srcRow[i];
              for (int ii = 0, n = Math.min(8, width - x); ii < n; ii++) {
                if ((b & 1 << 7 - ii) != 0) {
                  dstRow[x + ii] = rgba;
                } else
                  dstRow[x + ii] = 0; 
              } 
            } 
            dst.put(dstRow);
          } 
        } else {
          int rgb = rgba & 0xFFFFFF00;
          int a = rgba & 0xFF;
          for (int y = 0; y < rows; y++) {
            src.get(srcRow);
            for (int x = 0; x < width; x++) {
              int alpha = srcRow[x] & 0xFF;
              if (alpha == 0) {
                dstRow[x] = rgb;
              } else if (alpha == 255) {
                dstRow[x] = rgb | a;
              } else
                dstRow[x] = rgb | (int)((float)a * (float)Math.pow((double)((float)alpha / 255.0F), (double)gamma)); 
            } 
            dst.put(dstRow);
          } 
        } 
      } 
      Pixmap converted = pixmap;
      if (format != pixmap.getFormat()) {
        converted = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), format);
        converted.setBlending(Pixmap.Blending.None);
        converted.drawPixmap(pixmap, 0, 0);
        converted.setBlending(Pixmap.Blending.SourceOver);
        pixmap.dispose();
      } 
      return converted;
    }
    
    public int getNumGray() {
      return getNumGray(this.address);
    }
    
    private static native int getNumGray(long param1Long);
    
    public int getPixelMode() {
      return getPixelMode(this.address);
    }
    
    private static native int getPixelMode(long param1Long);
  }
  
  public static class GlyphMetrics extends Pointer {
    GlyphMetrics(long address) {
      super(address);
    }
    
    public int getWidth() {
      return getWidth(this.address);
    }
    
    private static native int getWidth(long param1Long);
    
    public int getHeight() {
      return getHeight(this.address);
    }
    
    private static native int getHeight(long param1Long);
    
    public int getHoriBearingX() {
      return getHoriBearingX(this.address);
    }
    
    private static native int getHoriBearingX(long param1Long);
    
    public int getHoriBearingY() {
      return getHoriBearingY(this.address);
    }
    
    private static native int getHoriBearingY(long param1Long);
    
    public int getHoriAdvance() {
      return getHoriAdvance(this.address);
    }
    
    private static native int getHoriAdvance(long param1Long);
    
    public int getVertBearingX() {
      return getVertBearingX(this.address);
    }
    
    private static native int getVertBearingX(long param1Long);
    
    public int getVertBearingY() {
      return getVertBearingY(this.address);
    }
    
    private static native int getVertBearingY(long param1Long);
    
    public int getVertAdvance() {
      return getVertAdvance(this.address);
    }
    
    private static native int getVertAdvance(long param1Long);
  }
  
  public static class Stroker extends Pointer implements Disposable {
    Stroker(long address) {
      super(address);
    }
    
    public void set(int radius, int lineCap, int lineJoin, int miterLimit) {
      set(this.address, radius, lineCap, lineJoin, miterLimit);
    }
    
    private static native void set(long param1Long, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
    
    public void dispose() {
      done(this.address);
    }
    
    private static native void done(long param1Long);
  }
  
  public static int FT_PIXEL_MODE_NONE = 0;
  
  public static int FT_PIXEL_MODE_MONO = 1;
  
  public static int FT_PIXEL_MODE_GRAY = 2;
  
  public static int FT_PIXEL_MODE_GRAY2 = 3;
  
  public static int FT_PIXEL_MODE_GRAY4 = 4;
  
  public static int FT_PIXEL_MODE_LCD = 5;
  
  public static int FT_PIXEL_MODE_LCD_V = 6;
  
  static native int getLastErrorCode();
  
  private static int encode(char a, char b, char c, char d) {
    return a << 24 | b << 16 | c << 8 | d;
  }
  
  public static int FT_ENCODING_NONE = 0;
  
  public static int FT_ENCODING_MS_SYMBOL = encode('s', 'y', 'm', 'b');
  
  public static int FT_ENCODING_UNICODE = encode('u', 'n', 'i', 'c');
  
  public static int FT_ENCODING_SJIS = encode('s', 'j', 'i', 's');
  
  public static int FT_ENCODING_GB2312 = encode('g', 'b', ' ', ' ');
  
  public static int FT_ENCODING_BIG5 = encode('b', 'i', 'g', '5');
  
  public static int FT_ENCODING_WANSUNG = encode('w', 'a', 'n', 's');
  
  public static int FT_ENCODING_JOHAB = encode('j', 'o', 'h', 'a');
  
  public static int FT_ENCODING_ADOBE_STANDARD = encode('A', 'D', 'O', 'B');
  
  public static int FT_ENCODING_ADOBE_EXPERT = encode('A', 'D', 'B', 'E');
  
  public static int FT_ENCODING_ADOBE_CUSTOM = encode('A', 'D', 'B', 'C');
  
  public static int FT_ENCODING_ADOBE_LATIN_1 = encode('l', 'a', 't', '1');
  
  public static int FT_ENCODING_OLD_LATIN_2 = encode('l', 'a', 't', '2');
  
  public static int FT_ENCODING_APPLE_ROMAN = encode('a', 'r', 'm', 'n');
  
  public static int FT_FACE_FLAG_SCALABLE = 1;
  
  public static int FT_FACE_FLAG_FIXED_SIZES = 2;
  
  public static int FT_FACE_FLAG_FIXED_WIDTH = 4;
  
  public static int FT_FACE_FLAG_SFNT = 8;
  
  public static int FT_FACE_FLAG_HORIZONTAL = 16;
  
  public static int FT_FACE_FLAG_VERTICAL = 32;
  
  public static int FT_FACE_FLAG_KERNING = 64;
  
  public static int FT_FACE_FLAG_FAST_GLYPHS = 128;
  
  public static int FT_FACE_FLAG_MULTIPLE_MASTERS = 256;
  
  public static int FT_FACE_FLAG_GLYPH_NAMES = 512;
  
  public static int FT_FACE_FLAG_EXTERNAL_STREAM = 1024;
  
  public static int FT_FACE_FLAG_HINTER = 2048;
  
  public static int FT_FACE_FLAG_CID_KEYED = 4096;
  
  public static int FT_FACE_FLAG_TRICKY = 8192;
  
  public static int FT_STYLE_FLAG_ITALIC = 1;
  
  public static int FT_STYLE_FLAG_BOLD = 2;
  
  public static int FT_LOAD_DEFAULT = 0;
  
  public static int FT_LOAD_NO_SCALE = 1;
  
  public static int FT_LOAD_NO_HINTING = 2;
  
  public static int FT_LOAD_RENDER = 4;
  
  public static int FT_LOAD_NO_BITMAP = 8;
  
  public static int FT_LOAD_VERTICAL_LAYOUT = 16;
  
  public static int FT_LOAD_FORCE_AUTOHINT = 32;
  
  public static int FT_LOAD_CROP_BITMAP = 64;
  
  public static int FT_LOAD_PEDANTIC = 128;
  
  public static int FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = 512;
  
  public static int FT_LOAD_NO_RECURSE = 1024;
  
  public static int FT_LOAD_IGNORE_TRANSFORM = 2048;
  
  public static int FT_LOAD_MONOCHROME = 4096;
  
  public static int FT_LOAD_LINEAR_DESIGN = 8192;
  
  public static int FT_LOAD_NO_AUTOHINT = 32768;
  
  public static int FT_LOAD_TARGET_NORMAL = 0;
  
  public static int FT_LOAD_TARGET_LIGHT = 65536;
  
  public static int FT_LOAD_TARGET_MONO = 131072;
  
  public static int FT_LOAD_TARGET_LCD = 196608;
  
  public static int FT_LOAD_TARGET_LCD_V = 262144;
  
  public static int FT_RENDER_MODE_NORMAL = 0;
  
  public static int FT_RENDER_MODE_LIGHT = 1;
  
  public static int FT_RENDER_MODE_MONO = 2;
  
  public static int FT_RENDER_MODE_LCD = 3;
  
  public static int FT_RENDER_MODE_LCD_V = 4;
  
  public static int FT_RENDER_MODE_MAX = 5;
  
  public static int FT_KERNING_DEFAULT = 0;
  
  public static int FT_KERNING_UNFITTED = 1;
  
  public static int FT_KERNING_UNSCALED = 2;
  
  public static int FT_STROKER_LINECAP_BUTT = 0;
  
  public static int FT_STROKER_LINECAP_ROUND = 1;
  
  public static int FT_STROKER_LINECAP_SQUARE = 2;
  
  public static int FT_STROKER_LINEJOIN_ROUND = 0;
  
  public static int FT_STROKER_LINEJOIN_BEVEL = 1;
  
  public static int FT_STROKER_LINEJOIN_MITER_VARIABLE = 2;
  
  public static int FT_STROKER_LINEJOIN_MITER = FT_STROKER_LINEJOIN_MITER_VARIABLE;
  
  public static int FT_STROKER_LINEJOIN_MITER_FIXED = 3;
  
  public static Library initFreeType() {
    new SharedLibraryLoader().load("gdx-freetype");
    long address = initFreeTypeJni();
    if (address == 0L)
      throw new GdxRuntimeException("Couldn't initialize FreeType library, FreeType error code: " + getLastErrorCode()); 
    return new Library(address);
  }
  
  private static native long initFreeTypeJni();
  
  public static int toInt(int value) {
    return (value + 63 & 0xFFFFFFC0) >> 6;
  }
}
