package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.XmlReader;

public class AtlasTmxMapLoader extends BaseTmxMapLoader<AtlasTmxMapLoader.AtlasTiledMapLoaderParameters> {
  public static class AtlasTiledMapLoaderParameters extends BaseTmxMapLoader.Parameters {
    public boolean forceTextureFilters = false;
  }
  
  protected static interface AtlasResolver extends ImageResolver {
    TextureAtlas getAtlas();
    
    public static class DirectAtlasResolver implements AtlasResolver {
      private final TextureAtlas atlas;
      
      public DirectAtlasResolver(TextureAtlas atlas) {
        this.atlas = atlas;
      }
      
      public TextureAtlas getAtlas() {
        return this.atlas;
      }
      
      public TextureRegion getImage(String name) {
        return this.atlas.findRegion(name);
      }
    }
    
    public static class AssetManagerAtlasResolver implements AtlasResolver {
      private final AssetManager assetManager;
      
      private final String atlasName;
      
      public AssetManagerAtlasResolver(AssetManager assetManager, String atlasName) {
        this.assetManager = assetManager;
        this.atlasName = atlasName;
      }
      
      public TextureAtlas getAtlas() {
        return this.assetManager.<TextureAtlas>get(this.atlasName, TextureAtlas.class);
      }
      
      public TextureRegion getImage(String name) {
        return getAtlas().findRegion(name);
      }
    }
  }
  
  protected Array<Texture> trackedTextures = new Array<>();
  
  protected AtlasResolver atlasResolver;
  
  public AtlasTmxMapLoader() {
    super(new InternalFileHandleResolver());
  }
  
  public AtlasTmxMapLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  public TiledMap load(String fileName) {
    return load(fileName, new AtlasTiledMapLoaderParameters());
  }
  
  public TiledMap load(String fileName, AtlasTiledMapLoaderParameters parameter) {
    FileHandle tmxFile = resolve(fileName);
    this.root = this.xml.parse(tmxFile);
    FileHandle atlasFileHandle = getAtlasFileHandle(tmxFile);
    TextureAtlas atlas = new TextureAtlas(atlasFileHandle);
    this.atlasResolver = new AtlasResolver.DirectAtlasResolver(atlas);
    TiledMap map = loadTiledMap(tmxFile, parameter, this.atlasResolver);
    map.setOwnedResources(new Array<>(new TextureAtlas[] { atlas }));
    setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
    return map;
  }
  
  public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {
    FileHandle atlasHandle = getAtlasFileHandle(tmxFile);
    this.atlasResolver = new AtlasResolver.AssetManagerAtlasResolver(manager, atlasHandle.path());
    this.map = loadTiledMap(tmxFile, parameter, this.atlasResolver);
  }
  
  public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, AtlasTiledMapLoaderParameters parameter) {
    if (parameter != null)
      setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter); 
    return this.map;
  }
  
  protected Array<AssetDescriptor> getDependencyAssetDescriptors(FileHandle tmxFile, TextureLoader.TextureParameter textureParameter) {
    Array<AssetDescriptor> descriptors = new Array<>();
    FileHandle atlasFileHandle = getAtlasFileHandle(tmxFile);
    if (atlasFileHandle != null)
      descriptors.add(new AssetDescriptor<>(atlasFileHandle, TextureAtlas.class)); 
    return descriptors;
  }
  
  protected void addStaticTiles(FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, XmlReader.Element element, Array<XmlReader.Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin, String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {
    TextureAtlas atlas = this.atlasResolver.getAtlas();
    String regionsName = name;
    for (ObjectSet.ObjectSetIterator<Texture> objectSetIterator = atlas.getTextures().iterator(); objectSetIterator.hasNext(); ) {
      Texture texture = objectSetIterator.next();
      this.trackedTextures.add(texture);
    } 
    MapProperties props = tileSet.getProperties();
    props.put("imagesource", imageSource);
    props.put("imagewidth", Integer.valueOf(imageWidth));
    props.put("imageheight", Integer.valueOf(imageHeight));
    props.put("tilewidth", Integer.valueOf(tilewidth));
    props.put("tileheight", Integer.valueOf(tileheight));
    props.put("margin", Integer.valueOf(margin));
    props.put("spacing", Integer.valueOf(spacing));
    if (imageSource != null && imageSource.length() > 0) {
      int lastgid = firstgid + imageWidth / tilewidth * imageHeight / tileheight - 1;
      for (Array.ArrayIterator<TextureAtlas.AtlasRegion> arrayIterator1 = atlas.findRegions(regionsName).iterator(); arrayIterator1.hasNext(); ) {
        TextureAtlas.AtlasRegion region = arrayIterator1.next();
        if (region != null) {
          int tileId = firstgid + region.index;
          if (tileId >= firstgid && tileId <= lastgid)
            addStaticTiledMapTile(tileSet, region, tileId, (float)offsetX, (float)offsetY); 
        } 
      } 
    } 
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator = tileElements.iterator(); arrayIterator.hasNext(); ) {
      XmlReader.Element tileElement = arrayIterator.next();
      int tileId = firstgid + tileElement.getIntAttribute("id", 0);
      TiledMapTile tile = tileSet.getTile(tileId);
      if (tile == null) {
        XmlReader.Element imageElement = tileElement.getChildByName("image");
        if (imageElement != null) {
          String regionName = imageElement.getAttribute("source");
          regionName = regionName.substring(0, regionName.lastIndexOf('.'));
          TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
          if (region == null)
            throw new GdxRuntimeException("Tileset atlasRegion not found: " + regionName); 
          addStaticTiledMapTile(tileSet, region, tileId, (float)offsetX, (float)offsetY);
        } 
      } 
    } 
  }
  
  protected FileHandle getAtlasFileHandle(FileHandle tmxFile) {
    XmlReader.Element properties = this.root.getChildByName("properties");
    String atlasFilePath = null;
    if (properties != null)
      for (Array.ArrayIterator<XmlReader.Element> arrayIterator = properties.getChildrenByName("property").iterator(); arrayIterator.hasNext(); ) {
        XmlReader.Element property = arrayIterator.next();
        String name = property.getAttribute("name");
        if (name.startsWith("atlas")) {
          atlasFilePath = property.getAttribute("value");
          break;
        } 
      }  
    if (atlasFilePath == null)
      throw new GdxRuntimeException("The map is missing the 'atlas' property"); 
    FileHandle fileHandle = getRelativeFileHandle(tmxFile, atlasFilePath);
    if (!fileHandle.exists())
      throw new GdxRuntimeException("The 'atlas' file could not be found: '" + atlasFilePath + "'"); 
    return fileHandle;
  }
  
  protected void setTextureFilters(Texture.TextureFilter min, Texture.TextureFilter mag) {
    for (Array.ArrayIterator<Texture> arrayIterator = this.trackedTextures.iterator(); arrayIterator.hasNext(); ) {
      Texture texture = arrayIterator.next();
      texture.setFilter(min, mag);
    } 
    this.trackedTextures.clear();
  }
}
