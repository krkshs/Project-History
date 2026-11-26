package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

public class TmxMapLoader extends BaseTmxMapLoader<TmxMapLoader.Parameters> {
  public static class Parameters extends BaseTmxMapLoader.Parameters {}
  
  public TmxMapLoader() {
    super(new InternalFileHandleResolver());
  }
  
  public TmxMapLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  public TiledMap load(String fileName) {
    return load(fileName, new Parameters());
  }
  
  public TiledMap load(String fileName, Parameters parameter) {
    FileHandle tmxFile = resolve(fileName);
    this.root = this.xml.parse(tmxFile);
    ObjectMap<String, Texture> textures = new ObjectMap<>();
    Array<FileHandle> textureFiles = getDependencyFileHandles(tmxFile);
    for (Array.ArrayIterator<FileHandle> arrayIterator = textureFiles.iterator(); arrayIterator.hasNext(); ) {
      FileHandle textureFile = arrayIterator.next();
      Texture texture = new Texture(textureFile, parameter.generateMipMaps);
      texture.setFilter(parameter.textureMinFilter, parameter.textureMagFilter);
      textures.put(textureFile.path(), texture);
    } 
    TiledMap map = loadTiledMap(tmxFile, parameter, new ImageResolver.DirectImageResolver(textures));
    map.setOwnedResources(textures.values().toArray());
    return map;
  }
  
  public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
    this.map = loadTiledMap(tmxFile, parameter, new ImageResolver.AssetManagerImageResolver(manager));
  }
  
  public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
    return this.map;
  }
  
  protected Array<AssetDescriptor> getDependencyAssetDescriptors(FileHandle tmxFile, TextureLoader.TextureParameter textureParameter) {
    Array<AssetDescriptor> descriptors = new Array<>();
    Array<FileHandle> fileHandles = getDependencyFileHandles(tmxFile);
    for (Array.ArrayIterator<FileHandle> arrayIterator = fileHandles.iterator(); arrayIterator.hasNext(); ) {
      FileHandle handle = arrayIterator.next();
      descriptors.add(new AssetDescriptor<>(handle, Texture.class, textureParameter));
    } 
    return descriptors;
  }
  
  protected Array<FileHandle> getDependencyFileHandles(FileHandle tmxFile) {
    Array<FileHandle> fileHandles = new Array<>();
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator2 = this.root.getChildrenByName("tileset").iterator(); arrayIterator2.hasNext(); ) {
      XmlReader.Element tileset = arrayIterator2.next();
      String source = tileset.getAttribute("source", null);
      if (source != null) {
        FileHandle tsxFile = getRelativeFileHandle(tmxFile, source);
        tileset = this.xml.parse(tsxFile);
        XmlReader.Element element = tileset.getChildByName("image");
        if (element != null) {
          String imageSource = tileset.getChildByName("image").getAttribute("source");
          FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
          fileHandles.add(image);
          continue;
        } 
        for (Array.ArrayIterator<XmlReader.Element> arrayIterator3 = tileset.getChildrenByName("tile").iterator(); arrayIterator3.hasNext(); ) {
          XmlReader.Element tile = arrayIterator3.next();
          String imageSource = tile.getChildByName("image").getAttribute("source");
          FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
          fileHandles.add(image);
        } 
        continue;
      } 
      XmlReader.Element imageElement = tileset.getChildByName("image");
      if (imageElement != null) {
        String imageSource = tileset.getChildByName("image").getAttribute("source");
        FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
        fileHandles.add(image);
        continue;
      } 
      for (Array.ArrayIterator<XmlReader.Element> arrayIterator = tileset.getChildrenByName("tile").iterator(); arrayIterator.hasNext(); ) {
        XmlReader.Element tile = arrayIterator.next();
        String imageSource = tile.getChildByName("image").getAttribute("source");
        FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
        fileHandles.add(image);
      } 
    } 
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator1 = this.root.getChildrenByName("imagelayer").iterator(); arrayIterator1.hasNext(); ) {
      XmlReader.Element imageLayer = arrayIterator1.next();
      XmlReader.Element image = imageLayer.getChildByName("image");
      String source = image.getAttribute("source", null);
      if (source != null) {
        FileHandle handle = getRelativeFileHandle(tmxFile, source);
        fileHandles.add(handle);
      } 
    } 
    return fileHandles;
  }
  
  protected void addStaticTiles(FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, XmlReader.Element element, Array<XmlReader.Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin, String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {
    MapProperties props = tileSet.getProperties();
    if (image != null) {
      TextureRegion texture = imageResolver.getImage(image.path());
      props.put("imagesource", imageSource);
      props.put("imagewidth", Integer.valueOf(imageWidth));
      props.put("imageheight", Integer.valueOf(imageHeight));
      props.put("tilewidth", Integer.valueOf(tilewidth));
      props.put("tileheight", Integer.valueOf(tileheight));
      props.put("margin", Integer.valueOf(margin));
      props.put("spacing", Integer.valueOf(spacing));
      int stopWidth = texture.getRegionWidth() - tilewidth;
      int stopHeight = texture.getRegionHeight() - tileheight;
      int id = firstgid;
      for (int y = margin; y <= stopHeight; y += tileheight + spacing) {
        for (int x = margin; x <= stopWidth; x += tilewidth + spacing) {
          TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
          int tileId = id++;
          addStaticTiledMapTile(tileSet, tileRegion, tileId, (float)offsetX, (float)offsetY);
        } 
      } 
    } else
      for (Array.ArrayIterator<XmlReader.Element> arrayIterator = tileElements.iterator(); arrayIterator.hasNext(); ) {
        XmlReader.Element tileElement = arrayIterator.next();
        XmlReader.Element imageElement = tileElement.getChildByName("image");
        if (imageElement != null) {
          imageSource = imageElement.getAttribute("source");
          if (source != null) {
            image = getRelativeFileHandle(getRelativeFileHandle(tmxFile, source), imageSource);
          } else
            image = getRelativeFileHandle(tmxFile, imageSource); 
        } 
        TextureRegion texture = imageResolver.getImage(image.path());
        int tileId = firstgid + tileElement.getIntAttribute("id");
        addStaticTiledMapTile(tileSet, texture, tileId, (float)offsetX, (float)offsetY);
      }  
  }
}
