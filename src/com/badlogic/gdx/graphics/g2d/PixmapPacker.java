package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PixmapPacker implements Disposable {
  boolean packToTexture;
  
  boolean disposed;
  
  int pageWidth;
  
  int pageHeight;
  
  Pixmap.Format pageFormat;
  
  int padding;
  
  boolean duplicateBorder;
  
  boolean stripWhitespaceX;
  
  boolean stripWhitespaceY;
  
  int alphaThreshold;
  
  Color transparentColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
  
  final Array<Page> pages = new Array<>();
  
  PackStrategy packStrategy;
  
  static Pattern indexPattern = Pattern.compile("(.+)_(\\d+)$");
  
  public PixmapPacker(int pageWidth, int pageHeight, Pixmap.Format pageFormat, int padding, boolean duplicateBorder) {
    this(pageWidth, pageHeight, pageFormat, padding, duplicateBorder, false, false, new GuillotineStrategy());
  }
  
  public PixmapPacker(int pageWidth, int pageHeight, Pixmap.Format pageFormat, int padding, boolean duplicateBorder, PackStrategy packStrategy) {
    this(pageWidth, pageHeight, pageFormat, padding, duplicateBorder, false, false, packStrategy);
  }
  
  public PixmapPacker(int pageWidth, int pageHeight, Pixmap.Format pageFormat, int padding, boolean duplicateBorder, boolean stripWhitespaceX, boolean stripWhitespaceY, PackStrategy packStrategy) {
    this.pageWidth = pageWidth;
    this.pageHeight = pageHeight;
    this.pageFormat = pageFormat;
    this.padding = padding;
    this.duplicateBorder = duplicateBorder;
    this.stripWhitespaceX = stripWhitespaceX;
    this.stripWhitespaceY = stripWhitespaceY;
    this.packStrategy = packStrategy;
  }
  
  public void sort(Array<Pixmap> images) {
    this.packStrategy.sort(images);
  }
  
  public synchronized Rectangle pack(Pixmap image) {
    return pack(null, image);
  }
  
  public synchronized Rectangle pack(String name, Pixmap image) {
    PixmapPackerRectangle rect;
    if (this.disposed)
      return null; 
    if (name != null && getRect(name) != null)
      throw new GdxRuntimeException("Pixmap has already been packed with name: " + name); 
    Pixmap pixmapToDispose = null;
    if (name != null && name.endsWith(".9")) {
      rect = new PixmapPackerRectangle(0, 0, image.getWidth() - 2, image.getHeight() - 2);
      pixmapToDispose = new Pixmap(image.getWidth() - 2, image.getHeight() - 2, image.getFormat());
      pixmapToDispose.setBlending(Pixmap.Blending.None);
      rect.splits = getSplits(image);
      rect.pads = getPads(image, rect.splits);
      pixmapToDispose.drawPixmap(image, 0, 0, 1, 1, image.getWidth() - 1, image.getHeight() - 1);
      image = pixmapToDispose;
      name = name.split("\\.")[0];
    } else if (this.stripWhitespaceX || this.stripWhitespaceY) {
      int originalWidth = image.getWidth();
      int originalHeight = image.getHeight();
      int top = 0;
      int bottom = image.getHeight();
      if (this.stripWhitespaceY) {
        int y;
        label99: for (y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getPixel(x, y);
            int alpha = pixel & 0xFF;
            if (alpha > this.alphaThreshold)
              break label99; 
          } 
          top++;
        } 
        for (y = image.getHeight(); --y >= top; ) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getPixel(x, y);
            int alpha = pixel & 0xFF;
            if (alpha > this.alphaThreshold)
              // goto line number 208 
          } 
          bottom--;
        } 
      } 
      int left = 0;
      int right = image.getWidth();
      if (this.stripWhitespaceX) {
        int x;
        label100: for (x = 0; x < image.getWidth(); x++) {
          for (int y = top; y < bottom; y++) {
            int pixel = image.getPixel(x, y);
            int alpha = pixel & 0xFF;
            if (alpha > this.alphaThreshold)
              break label100; 
          } 
          left++;
        } 
        for (x = image.getWidth(); --x >= left; ) {
          for (int y = top; y < bottom; y++) {
            int pixel = image.getPixel(x, y);
            int alpha = pixel & 0xFF;
            if (alpha > this.alphaThreshold)
              // goto line number 231 
          } 
          right--;
        } 
      } 
      int newWidth = right - left;
      int newHeight = bottom - top;
      pixmapToDispose = new Pixmap(newWidth, newHeight, image.getFormat());
      pixmapToDispose.setBlending(Pixmap.Blending.None);
      pixmapToDispose.drawPixmap(image, 0, 0, left, top, newWidth, newHeight);
      image = pixmapToDispose;
      rect = new PixmapPackerRectangle(0, 0, newWidth, newHeight, left, top, originalWidth, originalHeight);
    } else
      rect = new PixmapPackerRectangle(0, 0, image.getWidth(), image.getHeight()); 
    if (rect.getWidth() > (float)this.pageWidth || rect.getHeight() > (float)this.pageHeight) {
      if (name == null)
        throw new GdxRuntimeException("Page size too small for pixmap."); 
      throw new GdxRuntimeException("Page size too small for pixmap: " + name);
    } 
    Page page = this.packStrategy.pack(this, name, rect);
    if (name != null) {
      page.rects.put(name, rect);
      page.addedRects.add(name);
    } 
    int rectX = (int)rect.x, rectY = (int)rect.y, rectWidth = (int)rect.width, rectHeight = (int)rect.height;
    if (this.packToTexture && !this.duplicateBorder && page.texture != null && !page.dirty) {
      page.texture.bind();
      Gdx.gl.glTexSubImage2D(page.texture.glTarget, 0, rectX, rectY, rectWidth, rectHeight, image.getGLFormat(), 
          image.getGLType(), image.getPixels());
    } else
      page.dirty = true; 
    page.image.drawPixmap(image, rectX, rectY);
    if (this.duplicateBorder) {
      int imageWidth = image.getWidth(), imageHeight = image.getHeight();
      page.image.drawPixmap(image, 0, 0, 1, 1, rectX - 1, rectY - 1, 1, 1);
      page.image.drawPixmap(image, imageWidth - 1, 0, 1, 1, rectX + rectWidth, rectY - 1, 1, 1);
      page.image.drawPixmap(image, 0, imageHeight - 1, 1, 1, rectX - 1, rectY + rectHeight, 1, 1);
      page.image.drawPixmap(image, imageWidth - 1, imageHeight - 1, 1, 1, rectX + rectWidth, rectY + rectHeight, 1, 1);
      page.image.drawPixmap(image, 0, 0, imageWidth, 1, rectX, rectY - 1, rectWidth, 1);
      page.image.drawPixmap(image, 0, imageHeight - 1, imageWidth, 1, rectX, rectY + rectHeight, rectWidth, 1);
      page.image.drawPixmap(image, 0, 0, 1, imageHeight, rectX - 1, rectY, 1, rectHeight);
      page.image.drawPixmap(image, imageWidth - 1, 0, 1, imageHeight, rectX + rectWidth, rectY, 1, rectHeight);
    } 
    if (pixmapToDispose != null)
      pixmapToDispose.dispose(); 
    return rect;
  }
  
  public Array<Page> getPages() {
    return this.pages;
  }
  
  public synchronized Rectangle getRect(String name) {
    for (Array.ArrayIterator<Page> arrayIterator = this.pages.iterator(); arrayIterator.hasNext(); ) {
      Page page = arrayIterator.next();
      Rectangle rect = page.rects.get(name);
      if (rect != null)
        return rect; 
    } 
    return null;
  }
  
  public synchronized Page getPage(String name) {
    for (Array.ArrayIterator<Page> arrayIterator = this.pages.iterator(); arrayIterator.hasNext(); ) {
      Page page = arrayIterator.next();
      Rectangle rect = page.rects.get(name);
      if (rect != null)
        return page; 
    } 
    return null;
  }
  
  public synchronized int getPageIndex(String name) {
    for (int i = 0; i < this.pages.size; i++) {
      Rectangle rect = ((Page)this.pages.get(i)).rects.get(name);
      if (rect != null)
        return i; 
    } 
    return -1;
  }
  
  public synchronized void dispose() {
    for (Array.ArrayIterator<Page> arrayIterator = this.pages.iterator(); arrayIterator.hasNext(); ) {
      Page page = arrayIterator.next();
      if (page.texture == null)
        page.image.dispose(); 
    } 
    this.disposed = true;
  }
  
  public synchronized TextureAtlas generateTextureAtlas(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps) {
    TextureAtlas atlas = new TextureAtlas();
    updateTextureAtlas(atlas, minFilter, magFilter, useMipMaps);
    return atlas;
  }
  
  public synchronized void updateTextureAtlas(TextureAtlas atlas, Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps) {
    updateTextureAtlas(atlas, minFilter, magFilter, useMipMaps, true);
  }
  
  public synchronized void updateTextureAtlas(TextureAtlas atlas, Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps, boolean useIndexes) {
    updatePageTextures(minFilter, magFilter, useMipMaps);
    for (Array.ArrayIterator<Page> arrayIterator = this.pages.iterator(); arrayIterator.hasNext(); ) {
      Page page = arrayIterator.next();
      if (page.addedRects.size > 0) {
        for (Array.ArrayIterator<String> arrayIterator1 = page.addedRects.iterator(); arrayIterator1.hasNext(); ) {
          String name = arrayIterator1.next();
          PixmapPackerRectangle rect = page.rects.get(name);
          TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(page.texture, (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
          if (rect.splits != null) {
            region.names = new String[] { "split", "pad" };
            region.values = new int[][] { rect.splits, rect.pads };
          } 
          int imageIndex = -1;
          String imageName = name;
          if (useIndexes) {
            Matcher matcher = indexPattern.matcher(imageName);
            if (matcher.matches()) {
              imageName = matcher.group(1);
              imageIndex = Integer.parseInt(matcher.group(2));
            } 
          } 
          region.name = imageName;
          region.index = imageIndex;
          region.offsetX = (float)rect.offsetX;
          region.offsetY = (float)(int)((float)rect.originalHeight - rect.height - (float)rect.offsetY);
          region.originalWidth = rect.originalWidth;
          region.originalHeight = rect.originalHeight;
          atlas.getRegions().add(region);
        } 
        page.addedRects.clear();
        atlas.getTextures().add(page.texture);
      } 
    } 
  }
  
  public synchronized void updateTextureRegions(Array<TextureRegion> regions, Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps) {
    updatePageTextures(minFilter, magFilter, useMipMaps);
    while (regions.size < this.pages.size)
      regions.add(new TextureRegion(((Page)this.pages.get(regions.size)).texture)); 
  }
  
  public synchronized void updatePageTextures(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps) {
    for (Array.ArrayIterator<Page> arrayIterator = this.pages.iterator(); arrayIterator.hasNext(); ) {
      Page page = arrayIterator.next();
      page.updateTexture(minFilter, magFilter, useMipMaps);
    } 
  }
  
  public int getPageWidth() {
    return this.pageWidth;
  }
  
  public void setPageWidth(int pageWidth) {
    this.pageWidth = pageWidth;
  }
  
  public int getPageHeight() {
    return this.pageHeight;
  }
  
  public void setPageHeight(int pageHeight) {
    this.pageHeight = pageHeight;
  }
  
  public Pixmap.Format getPageFormat() {
    return this.pageFormat;
  }
  
  public void setPageFormat(Pixmap.Format pageFormat) {
    this.pageFormat = pageFormat;
  }
  
  public int getPadding() {
    return this.padding;
  }
  
  public void setPadding(int padding) {
    this.padding = padding;
  }
  
  public boolean getDuplicateBorder() {
    return this.duplicateBorder;
  }
  
  public void setDuplicateBorder(boolean duplicateBorder) {
    this.duplicateBorder = duplicateBorder;
  }
  
  public boolean getPackToTexture() {
    return this.packToTexture;
  }
  
  public void setPackToTexture(boolean packToTexture) {
    this.packToTexture = packToTexture;
  }
  
  public static class Page {
    OrderedMap<String, PixmapPacker.PixmapPackerRectangle> rects = new OrderedMap<>();
    
    Pixmap image;
    
    Texture texture;
    
    final Array<String> addedRects = new Array<>();
    
    boolean dirty;
    
    public Page(PixmapPacker packer) {
      this.image = new Pixmap(packer.pageWidth, packer.pageHeight, packer.pageFormat);
      this.image.setBlending(Pixmap.Blending.None);
      this.image.setColor(packer.getTransparentColor());
      this.image.fill();
    }
    
    public Pixmap getPixmap() {
      return this.image;
    }
    
    public OrderedMap<String, PixmapPacker.PixmapPackerRectangle> getRects() {
      return this.rects;
    }
    
    public Texture getTexture() {
      return this.texture;
    }
    
    public boolean updateTexture(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter, boolean useMipMaps) {
      if (this.texture != null) {
        if (!this.dirty)
          return false; 
        this.texture.load(this.texture.getTextureData());
      } else {
        this.texture = new Texture(new PixmapTextureData(this.image, this.image.getFormat(), useMipMaps, false, true)) {
            public void dispose() {
              super.dispose();
              Page.this.image.dispose();
            }
          };
        this.texture.setFilter(minFilter, magFilter);
      } 
      this.dirty = false;
      return true;
    }
  }
  
  public static interface PackStrategy {
    void sort(Array<Pixmap> param1Array);
    
    PixmapPacker.Page pack(PixmapPacker param1PixmapPacker, String param1String, Rectangle param1Rectangle);
  }
  
  public static class GuillotineStrategy implements PackStrategy {
    Comparator<Pixmap> comparator;
    
    public void sort(Array<Pixmap> pixmaps) {
      if (this.comparator == null)
        this.comparator = new Comparator<Pixmap>() {
            public int compare(Pixmap o1, Pixmap o2) {
              return Math.max(o1.getWidth(), o1.getHeight()) - Math.max(o2.getWidth(), o2.getHeight());
            }
          }; 
      pixmaps.sort(this.comparator);
    }
    
    public PixmapPacker.Page pack(PixmapPacker packer, String name, Rectangle rect) {
      GuillotinePage page;
      if (packer.pages.size == 0) {
        page = new GuillotinePage(packer);
        packer.pages.add(page);
      } else
        page = (GuillotinePage)packer.pages.peek(); 
      int padding = packer.padding;
      rect.width += (float)padding;
      rect.height += (float)padding;
      Node node = insert(page.root, rect);
      if (node == null) {
        page = new GuillotinePage(packer);
        packer.pages.add(page);
        node = insert(page.root, rect);
      } 
      node.full = true;
      rect.set(node.rect.x, node.rect.y, node.rect.width - (float)padding, node.rect.height - (float)padding);
      return page;
    }
    
    private Node insert(Node node, Rectangle rect) {
      if (!node.full && node.leftChild != null && node.rightChild != null) {
        Node newNode = insert(node.leftChild, rect);
        if (newNode == null)
          newNode = insert(node.rightChild, rect); 
        return newNode;
      } 
      if (node.full)
        return null; 
      if (node.rect.width == rect.width && node.rect.height == rect.height)
        return node; 
      if (node.rect.width < rect.width || node.rect.height < rect.height)
        return null; 
      node.leftChild = new Node();
      node.rightChild = new Node();
      int deltaWidth = (int)node.rect.width - (int)rect.width;
      int deltaHeight = (int)node.rect.height - (int)rect.height;
      if (deltaWidth > deltaHeight) {
        node.leftChild.rect.x = node.rect.x;
        node.leftChild.rect.y = node.rect.y;
        node.leftChild.rect.width = rect.width;
        node.leftChild.rect.height = node.rect.height;
        node.rect.x += rect.width;
        node.rightChild.rect.y = node.rect.y;
        node.rect.width -= rect.width;
        node.rightChild.rect.height = node.rect.height;
      } else {
        node.leftChild.rect.x = node.rect.x;
        node.leftChild.rect.y = node.rect.y;
        node.leftChild.rect.width = node.rect.width;
        node.leftChild.rect.height = rect.height;
        node.rightChild.rect.x = node.rect.x;
        node.rect.y += rect.height;
        node.rightChild.rect.width = node.rect.width;
        node.rect.height -= rect.height;
      } 
      return insert(node.leftChild, rect);
    }
    
    static final class Node {
      public Node leftChild;
      
      public Node rightChild;
      
      public final Rectangle rect = new Rectangle();
      
      public boolean full;
    }
    
    static class GuillotinePage extends PixmapPacker.Page {
      PixmapPacker.GuillotineStrategy.Node root;
      
      public GuillotinePage(PixmapPacker packer) {
        super(packer);
        this.root = new PixmapPacker.GuillotineStrategy.Node();
        this.root.rect.x = (float)packer.padding;
        this.root.rect.y = (float)packer.padding;
        this.root.rect.width = (float)(packer.pageWidth - packer.padding * 2);
        this.root.rect.height = (float)(packer.pageHeight - packer.padding * 2);
      }
    }
  }
  
  public static class SkylineStrategy implements PackStrategy {
    Comparator<Pixmap> comparator;
    
    public void sort(Array<Pixmap> images) {
      if (this.comparator == null)
        this.comparator = new Comparator<Pixmap>() {
            public int compare(Pixmap o1, Pixmap o2) {
              return o1.getHeight() - o2.getHeight();
            }
          }; 
      images.sort(this.comparator);
    }
    
    public PixmapPacker.Page pack(PixmapPacker packer, String name, Rectangle rect) {
      int padding = packer.padding;
      int pageWidth = packer.pageWidth - padding * 2, pageHeight = packer.pageHeight - padding * 2;
      int rectWidth = (int)rect.width + padding, rectHeight = (int)rect.height + padding;
      for (int i = 0, n = packer.pages.size; i < n; i++) {
        SkylinePage skylinePage = (SkylinePage)packer.pages.get(i);
        SkylinePage.Row bestRow = null;
        for (int ii = 0, nn = skylinePage.rows.size - 1; ii < nn; ii++) {
          SkylinePage.Row row1 = skylinePage.rows.get(ii);
          if (row1.x + rectWidth < pageWidth && 
            row1.y + rectHeight < pageHeight && 
            rectHeight <= row1.height && (
            bestRow == null || row1.height < bestRow.height))
            bestRow = row1; 
        } 
        if (bestRow == null) {
          SkylinePage.Row row1 = skylinePage.rows.peek();
          if (row1.y + rectHeight >= pageHeight)
            continue; 
          if (row1.x + rectWidth < pageWidth) {
            row1.height = Math.max(row1.height, rectHeight);
            bestRow = row1;
          } else if (row1.y + row1.height + rectHeight < pageHeight) {
            bestRow = new SkylinePage.Row();
            row1.y += row1.height;
            bestRow.height = rectHeight;
            skylinePage.rows.add(bestRow);
          } 
        } 
        if (bestRow != null) {
          rect.x = (float)bestRow.x;
          rect.y = (float)bestRow.y;
          bestRow.x += rectWidth;
          return skylinePage;
        } 
      } 
      SkylinePage page = new SkylinePage(packer);
      packer.pages.add(page);
      SkylinePage.Row row = new SkylinePage.Row();
      row.x = padding + rectWidth;
      row.y = padding;
      row.height = rectHeight;
      page.rows.add(row);
      rect.x = (float)padding;
      rect.y = (float)padding;
      return page;
    }
    
    static class SkylinePage extends PixmapPacker.Page {
      Array<Row> rows = new Array<>();
      
      public SkylinePage(PixmapPacker packer) {
        super(packer);
      }
      
      static class Row {
        int x;
        
        int y;
        
        int height;
      }
    }
  }
  
  public Color getTransparentColor() {
    return this.transparentColor;
  }
  
  public void setTransparentColor(Color color) {
    this.transparentColor.set(color);
  }
  
  private int[] getSplits(Pixmap raster) {
    int startX = getSplitPoint(raster, 1, 0, true, true);
    int endX = getSplitPoint(raster, startX, 0, false, true);
    int startY = getSplitPoint(raster, 0, 1, true, false);
    int endY = getSplitPoint(raster, 0, startY, false, false);
    getSplitPoint(raster, endX + 1, 0, true, true);
    getSplitPoint(raster, 0, endY + 1, true, false);
    if (startX == 0 && endX == 0 && startY == 0 && endY == 0)
      return null; 
    if (startX != 0) {
      startX--;
      endX = raster.getWidth() - 2 - (endX - 1);
    } else
      endX = raster.getWidth() - 2; 
    if (startY != 0) {
      startY--;
      endY = raster.getHeight() - 2 - (endY - 1);
    } else
      endY = raster.getHeight() - 2; 
    return new int[] { startX, endX, startY, endY };
  }
  
  private int[] getPads(Pixmap raster, int[] splits) {
    int bottom = raster.getHeight() - 1;
    int right = raster.getWidth() - 1;
    int startX = getSplitPoint(raster, 1, bottom, true, true);
    int startY = getSplitPoint(raster, right, 1, true, false);
    int endX = 0;
    int endY = 0;
    if (startX != 0)
      endX = getSplitPoint(raster, startX + 1, bottom, false, true); 
    if (startY != 0)
      endY = getSplitPoint(raster, right, startY + 1, false, false); 
    getSplitPoint(raster, endX + 1, bottom, true, true);
    getSplitPoint(raster, right, endY + 1, true, false);
    if (startX == 0 && endX == 0 && startY == 0 && endY == 0)
      return null; 
    if (startX == 0 && endX == 0) {
      startX = -1;
      endX = -1;
    } else if (startX > 0) {
      startX--;
      endX = raster.getWidth() - 2 - (endX - 1);
    } else
      endX = raster.getWidth() - 2; 
    if (startY == 0 && endY == 0) {
      startY = -1;
      endY = -1;
    } else if (startY > 0) {
      startY--;
      endY = raster.getHeight() - 2 - (endY - 1);
    } else
      endY = raster.getHeight() - 2; 
    int[] pads = { startX, endX, startY, endY };
    if (splits != null && Arrays.equals(pads, splits))
      return null; 
    return pads;
  }
  
  private Color c = new Color();
  
  private int getSplitPoint(Pixmap raster, int startX, int startY, boolean startPoint, boolean xAxis) {
    int[] rgba = new int[4];
    int next = xAxis ? startX : startY;
    int end = xAxis ? raster.getWidth() : raster.getHeight();
    int breakA = startPoint ? 255 : 0;
    int x = startX;
    int y = startY;
    while (next != end) {
      if (xAxis) {
        x = next;
      } else
        y = next; 
      int colint = raster.getPixel(x, y);
      this.c.set(colint);
      rgba[0] = (int)(this.c.r * 255.0F);
      rgba[1] = (int)(this.c.g * 255.0F);
      rgba[2] = (int)(this.c.b * 255.0F);
      rgba[3] = (int)(this.c.a * 255.0F);
      if (rgba[3] == breakA)
        return next; 
      if (!startPoint && (rgba[0] != 0 || rgba[1] != 0 || rgba[2] != 0 || rgba[3] != 255))
        System.out.println(x + "  " + y + " " + rgba + " "); 
      next++;
    } 
    return 0;
  }
  
  public static class PixmapPackerRectangle extends Rectangle {
    int[] splits;
    
    int[] pads;
    
    int offsetX;
    
    int offsetY;
    
    int originalWidth;
    
    int originalHeight;
    
    PixmapPackerRectangle(int x, int y, int width, int height) {
      super((float)x, (float)y, (float)width, (float)height);
      this.offsetX = 0;
      this.offsetY = 0;
      this.originalWidth = width;
      this.originalHeight = height;
    }
    
    PixmapPackerRectangle(int x, int y, int width, int height, int left, int top, int originalWidth, int originalHeight) {
      super((float)x, (float)y, (float)width, (float)height);
      this.offsetX = left;
      this.offsetY = top;
      this.originalWidth = originalWidth;
      this.originalHeight = originalHeight;
    }
  }
}
