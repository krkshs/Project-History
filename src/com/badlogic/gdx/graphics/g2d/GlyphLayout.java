package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class GlyphLayout implements Pool.Poolable {
  private static final Pool<GlyphRun> glyphRunPool = Pools.<GlyphRun>get(GlyphRun.class);
  
  private static final Pool<Color> colorPool = Pools.<Color>get(Color.class);
  
  private static final Array<Color> colorStack = new Array<>(4);
  
  private static final float epsilon = 1.0E-4F;
  
  public final Array<GlyphRun> runs = new Array<>(1);
  
  public float width;
  
  public float height;
  
  public GlyphLayout() {}
  
  public GlyphLayout(BitmapFont font, CharSequence str) {
    setText(font, str);
  }
  
  public GlyphLayout(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
    setText(font, str, color, targetWidth, halign, wrap);
  }
  
  public GlyphLayout(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign, boolean wrap, String truncate) {
    setText(font, str, start, end, color, targetWidth, halign, wrap, truncate);
  }
  
  public void setText(BitmapFont font, CharSequence str) {
    setText(font, str, 0, str.length(), font.getColor(), 0.0F, 8, false, null);
  }
  
  public void setText(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
    setText(font, str, 0, str.length(), color, targetWidth, halign, wrap, null);
  }
  
  public void setText(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign, boolean wrap, @Null String truncate) {
    Array<GlyphRun> runs = this.runs;
    glyphRunPool.freeAll(runs);
    runs.clear();
    BitmapFont.BitmapFontData fontData = font.data;
    if (start == end) {
      this.width = 0.0F;
      this.height = fontData.capHeight;
      return;
    } 
    if (truncate != null) {
      wrap = true;
    } else if (targetWidth <= fontData.spaceXadvance * 3.0F) {
      wrap = false;
    } 
    Color nextColor = color;
    boolean markupEnabled = fontData.markupEnabled;
    if (markupEnabled) {
      for (int j = 1, n = colorStack.size; j < n; j++)
        colorPool.free(colorStack.get(j)); 
      colorStack.clear();
      colorStack.add(color);
    } 
    float x = 0.0F, y = 0.0F, down = fontData.down;
    BitmapFont.Glyph lastGlyph = null;
    int runStart = start;
    label161: while (true) {
      int runEnd = -1;
      boolean newline = false;
      if (start == end) {
        if (runStart == end)
          break; 
        runEnd = end;
      } else
        switch (str.charAt(start++)) {
          case '\n':
            runEnd = start - 1;
            newline = true;
            break;
          case '[':
            if (markupEnabled) {
              int length = parseColorMarkup(str, start, end, colorPool);
              if (length >= 0) {
                runEnd = start - 1;
                start += length + 1;
                nextColor = colorStack.peek();
                break;
              } 
              if (length == -2) {
                start++;
                continue;
              } 
            } 
            break;
        }  
      if (runEnd != -1) {
        if (runEnd != runStart) {
          GlyphRun run = glyphRunPool.obtain();
          run.color.set(color);
          fontData.getGlyphs(run, str, runStart, runEnd, lastGlyph);
          if (run.glyphs.size == 0) {
            glyphRunPool.free(run);
          } else {
            if (lastGlyph != null)
              x -= lastGlyph.fixedWidth ? ((float)lastGlyph.xadvance * fontData.scaleX) : ((float)(lastGlyph.width + lastGlyph.xoffset) * fontData.scaleX - fontData.padRight); 
            lastGlyph = run.glyphs.peek();
            run.x = x;
            run.y = y;
            if (newline || runEnd == end)
              adjustLastGlyph(fontData, run); 
            runs.add(run);
            int n = run.xAdvances.size;
            float[] xAdvances = run.xAdvances.items;
            if (!wrap || n == 0) {
              if (markupEnabled)
                for (int j = 0; j < n; j++)
                  x += xAdvances[j];  
            } else {
              x += xAdvances[0] + xAdvances[1];
              for (int j = 2; j < n; j++) {
                BitmapFont.Glyph glyph = run.glyphs.get(j - 1);
                float glyphWidth = (float)(glyph.width + glyph.xoffset) * fontData.scaleX - fontData.padRight;
                if (x + glyphWidth - 1.0E-4F <= targetWidth) {
                  x += xAdvances[j];
                } else {
                  GlyphRun next;
                  if (truncate != null) {
                    truncate(fontData, run, targetWidth, truncate, j, glyphRunPool);
                    break label161;
                  } 
                  y += down;
                  lastGlyph = null;
                  int wrapIndex = fontData.getWrapIndex(run.glyphs, j);
                  if ((wrapIndex == 0 && run.x == 0.0F) || wrapIndex >= run.glyphs.size)
                    wrapIndex = j - 1; 
                  if (wrapIndex == 0) {
                    next = run;
                    for (int glyphCount = run.glyphs.size; wrapIndex < glyphCount && 
                      fontData.isWhitespace((char)((BitmapFont.Glyph)run.glyphs.get(wrapIndex)).id); wrapIndex++);
                    if (wrapIndex > 0) {
                      run.glyphs.removeRange(0, wrapIndex - 1);
                      run.xAdvances.removeRange(1, wrapIndex);
                    } 
                    xAdvances[0] = (float)-((BitmapFont.Glyph)run.glyphs.first()).xoffset * fontData.scaleX - fontData.padLeft;
                    if (runs.size > 1) {
                      GlyphRun previous = runs.get(runs.size - 2);
                      int lastIndex = previous.glyphs.size - 1;
                      for (; lastIndex > 0 && 
                        fontData.isWhitespace((char)((BitmapFont.Glyph)previous.glyphs.get(lastIndex)).id); lastIndex--);
                      previous.glyphs.truncate(lastIndex + 1);
                      previous.xAdvances.truncate(lastIndex + 2);
                      adjustLastGlyph(fontData, previous);
                    } 
                  } else {
                    next = wrap(fontData, run, wrapIndex, j);
                    if (next == null) {
                      x = 0.0F;
                      break;
                    } 
                    runs.add(next);
                  } 
                  n = next.xAdvances.size;
                  xAdvances = next.xAdvances.items;
                  x = xAdvances[0];
                  if (n > 1)
                    x += xAdvances[1]; 
                  next.x = 0.0F;
                  next.y = y;
                  j = 1;
                  run = next;
                } 
              } 
            } 
          } 
        } 
        if (newline) {
          x = 0.0F;
          if (runEnd == runStart) {
            y += down * fontData.blankLineScale;
          } else
            y += down; 
          lastGlyph = null;
        } 
        runStart = start;
        color = nextColor;
      } 
    } 
    this.height = fontData.capHeight + Math.abs(y);
    float width = 0.0F;
    Object[] runsItems = runs.items;
    int runsSize = runs.size;
    for (int i = 0; i < runsSize; i++) {
      GlyphRun run = (GlyphRun)runsItems[i];
      float[] xAdvances = run.xAdvances.items;
      float runWidth = xAdvances[0], max = 0.0F;
      Object[] glyphs = run.glyphs.items;
      for (int ii = 0, nn = run.glyphs.size; ii < nn; ) {
        BitmapFont.Glyph glyph = (BitmapFont.Glyph)glyphs[ii];
        float glyphWidth = (float)(glyph.width + glyph.xoffset) * fontData.scaleX - fontData.padRight;
        max = Math.max(max, runWidth + glyphWidth);
        ii++;
        runWidth += xAdvances[ii];
      } 
      run.width = Math.max(runWidth, max);
      width = Math.max(width, run.x + run.width);
    } 
    this.width = width;
    if ((halign & 0x8) == 0) {
      boolean center = ((halign & 0x1) != 0);
      float lineWidth = 0.0F;
      int j = Integer.MIN_VALUE;
      int lineStart = 0;
      for (int k = 0; k < runsSize; k++) {
        GlyphRun run = (GlyphRun)runsItems[k];
        if (run.y != j) {
          float f1 = run.y;
          float f2 = targetWidth - lineWidth;
          if (center)
            f2 /= 2.0F; 
          while (lineStart < k)
            ((GlyphRun)runsItems[lineStart++]).x += f2; 
          lineWidth = run.x + run.width;
        } else
          lineWidth = Math.max(lineWidth, run.x + run.width); 
      } 
      float shift = targetWidth - lineWidth;
      if (center)
        shift /= 2.0F; 
      while (lineStart < runsSize)
        ((GlyphRun)runsItems[lineStart++]).x += shift; 
    } 
  }
  
  private void truncate(BitmapFont.BitmapFontData fontData, GlyphRun run, float targetWidth, String truncate, int widthIndex, Pool<GlyphRun> glyphRunPool) {
    GlyphRun truncateRun = glyphRunPool.obtain();
    fontData.getGlyphs(truncateRun, truncate, 0, truncate.length(), null);
    float truncateWidth = 0.0F;
    if (truncateRun.xAdvances.size > 0) {
      adjustLastGlyph(fontData, truncateRun);
      float[] arrayOfFloat = truncateRun.xAdvances.items;
      for (int i = 1, n = truncateRun.xAdvances.size; i < n; i++)
        truncateWidth += arrayOfFloat[i]; 
    } 
    targetWidth -= truncateWidth;
    int count = 0;
    float width = run.x;
    float[] xAdvances = run.xAdvances.items;
    while (count < run.xAdvances.size) {
      float xAdvance = xAdvances[count];
      width += xAdvance;
      if (width > targetWidth)
        break; 
      count++;
    } 
    if (count > 1) {
      run.glyphs.truncate(count - 1);
      run.xAdvances.truncate(count);
      adjustLastGlyph(fontData, run);
      if (truncateRun.xAdvances.size > 0)
        run.xAdvances.addAll(truncateRun.xAdvances, 1, truncateRun.xAdvances.size - 1); 
    } else {
      run.glyphs.clear();
      run.xAdvances.clear();
      run.xAdvances.addAll(truncateRun.xAdvances);
    } 
    run.glyphs.addAll(truncateRun.glyphs);
    glyphRunPool.free(truncateRun);
  }
  
  private GlyphRun wrap(BitmapFont.BitmapFontData fontData, GlyphRun first, int wrapIndex, int widthIndex) {
    Array<BitmapFont.Glyph> glyphs2 = first.glyphs;
    int glyphCount = first.glyphs.size;
    FloatArray xAdvances2 = first.xAdvances;
    int firstEnd = wrapIndex;
    for (; firstEnd > 0 && 
      fontData.isWhitespace((char)((BitmapFont.Glyph)glyphs2.get(firstEnd - 1)).id); firstEnd--);
    int secondStart = wrapIndex;
    for (; secondStart < glyphCount && 
      fontData.isWhitespace((char)((BitmapFont.Glyph)glyphs2.get(secondStart)).id); secondStart++);
    GlyphRun second = null;
    if (secondStart < glyphCount) {
      second = glyphRunPool.obtain();
      second.color.set(first.color);
      Array<BitmapFont.Glyph> glyphs1 = second.glyphs;
      glyphs1.addAll(glyphs2, 0, firstEnd);
      glyphs2.removeRange(0, secondStart - 1);
      first.glyphs = glyphs1;
      second.glyphs = glyphs2;
      FloatArray xAdvances1 = second.xAdvances;
      xAdvances1.addAll(xAdvances2, 0, firstEnd + 1);
      xAdvances2.removeRange(1, secondStart);
      xAdvances2.items[0] = (float)-((BitmapFont.Glyph)glyphs2.first()).xoffset * fontData.scaleX - fontData.padLeft;
      first.xAdvances = xAdvances1;
      second.xAdvances = xAdvances2;
    } else {
      glyphs2.truncate(firstEnd);
      xAdvances2.truncate(firstEnd + 1);
    } 
    if (firstEnd == 0) {
      glyphRunPool.free(first);
      this.runs.pop();
    } else
      adjustLastGlyph(fontData, first); 
    return second;
  }
  
  private void adjustLastGlyph(BitmapFont.BitmapFontData fontData, GlyphRun run) {
    BitmapFont.Glyph last = run.glyphs.peek();
    if (last.fixedWidth)
      return; 
    float width = (float)(last.width + last.xoffset) * fontData.scaleX - fontData.padRight;
    run.xAdvances.items[run.xAdvances.size - 1] = width;
  }
  
  private int parseColorMarkup(CharSequence str, int start, int end, Pool<Color> colorPool) {
    int colorInt;
    if (start == end)
      return -1; 
    switch (str.charAt(start)) {
      case '#':
        colorInt = 0;
        for (i = start + 1; i < end; i++) {
          char ch = str.charAt(i);
          if (ch == ']') {
            if (i < start + 2 || i > start + 9)
              break; 
            if (i - start <= 7) {
              for (int ii = 0, nn = 9 - (i - start); ii < nn; ii++)
                colorInt <<= 4; 
              colorInt |= 0xFF;
            } 
            Color color = colorPool.obtain();
            colorStack.add(color);
            Color.rgba8888ToColor(color, colorInt);
            return i - start;
          } 
          if (ch >= '0' && ch <= '9') {
            colorInt = colorInt * 16 + ch - 48;
          } else if (ch >= 'a' && ch <= 'f') {
            colorInt = colorInt * 16 + ch - 87;
          } else if (ch >= 'A' && ch <= 'F') {
            colorInt = colorInt * 16 + ch - 55;
          } else
            break; 
        } 
        return -1;
      case '[':
        return -2;
      case ']':
        if (colorStack.size > 1)
          colorPool.free(colorStack.pop()); 
        return 0;
    } 
    int colorStart = start;
    for (int i = start + 1; i < end; ) {
      char ch = str.charAt(i);
      if (ch != ']') {
        i++;
        continue;
      } 
      Color namedColor = Colors.get(str.subSequence(colorStart, i).toString());
      if (namedColor == null)
        return -1; 
      Color color = colorPool.obtain();
      colorStack.add(color);
      color.set(namedColor);
      return i - start;
    } 
    return -1;
  }
  
  public void reset() {
    Pools.<GlyphRun>get(GlyphRun.class).freeAll(this.runs);
    this.runs.clear();
    this.width = 0.0F;
    this.height = 0.0F;
  }
  
  public String toString() {
    if (this.runs.size == 0)
      return ""; 
    StringBuilder buffer = new StringBuilder(128);
    buffer.append(this.width);
    buffer.append('x');
    buffer.append(this.height);
    buffer.append('\n');
    for (int i = 0, n = this.runs.size; i < n; i++) {
      buffer.append(this.runs.get(i).toString());
      buffer.append('\n');
    } 
    buffer.setLength(buffer.length() - 1);
    return buffer.toString();
  }
  
  public static class GlyphRun implements Pool.Poolable {
    public Array<BitmapFont.Glyph> glyphs = new Array<>();
    
    public FloatArray xAdvances = new FloatArray();
    
    public float x;
    
    public float y;
    
    public float width;
    
    public final Color color = new Color();
    
    public void reset() {
      this.glyphs.clear();
      this.xAdvances.clear();
      this.width = 0.0F;
    }
    
    public String toString() {
      StringBuilder buffer = new StringBuilder(this.glyphs.size + 32);
      Array<BitmapFont.Glyph> glyphs = this.glyphs;
      for (int i = 0, n = glyphs.size; i < n; i++) {
        BitmapFont.Glyph g = glyphs.get(i);
        buffer.append((char)g.id);
      } 
      buffer.append(", #");
      buffer.append(this.color);
      buffer.append(", ");
      buffer.append(this.x);
      buffer.append(", ");
      buffer.append(this.y);
      buffer.append(", ");
      buffer.append(this.width);
      return buffer.toString();
    }
  }
}
