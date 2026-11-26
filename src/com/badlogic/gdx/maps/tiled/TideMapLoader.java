package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TideMapLoader extends SynchronousAssetLoader<TiledMap, TideMapLoader.Parameters> {
  public static class Parameters extends AssetLoaderParameters<TiledMap> {}
  
  private XmlReader xml = new XmlReader();
  
  private XmlReader.Element root;
  
  public TideMapLoader() {
    super(new InternalFileHandleResolver());
  }
  
  public TideMapLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  public TiledMap load(String fileName) {
    try {
      FileHandle tideFile = resolve(fileName);
      this.root = this.xml.parse(tideFile);
      ObjectMap<String, Texture> textures = new ObjectMap<>();
      for (Array.ArrayIterator<FileHandle> arrayIterator = loadTileSheets(this.root, tideFile).iterator(); arrayIterator.hasNext(); ) {
        FileHandle textureFile = arrayIterator.next();
        textures.put(textureFile.path(), new Texture(textureFile));
      } 
      ImageResolver.DirectImageResolver imageResolver = new ImageResolver.DirectImageResolver(textures);
      TiledMap map = loadMap(this.root, tideFile, imageResolver);
      map.setOwnedResources(textures.values().toArray());
      return map;
    } catch (IOException e) {
      throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
    } 
  }
  
  public TiledMap load(AssetManager assetManager, String fileName, FileHandle tideFile, Parameters parameter) {
    try {
      return loadMap(this.root, tideFile, new ImageResolver.AssetManagerImageResolver(assetManager));
    } catch (Exception e) {
      throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
    } 
  }
  
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {
    Array<AssetDescriptor> dependencies = new Array<>();
    try {
      this.root = this.xml.parse(tmxFile);
      for (Array.ArrayIterator<FileHandle> arrayIterator = loadTileSheets(this.root, tmxFile).iterator(); arrayIterator.hasNext(); ) {
        FileHandle image = arrayIterator.next();
        dependencies.add(new AssetDescriptor<>(image.path(), Texture.class));
      } 
      return dependencies;
    } catch (IOException e) {
      throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
    } 
  }
  
  private TiledMap loadMap(XmlReader.Element root, FileHandle tmxFile, ImageResolver imageResolver) {
    TiledMap map = new TiledMap();
    XmlReader.Element properties = root.getChildByName("Properties");
    if (properties != null)
      loadProperties(map.getProperties(), properties); 
    XmlReader.Element tilesheets = root.getChildByName("TileSheets");
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator1 = tilesheets.getChildrenByName("TileSheet").iterator(); arrayIterator1.hasNext(); ) {
      XmlReader.Element tilesheet = arrayIterator1.next();
      loadTileSheet(map, tilesheet, tmxFile, imageResolver);
    } 
    XmlReader.Element layers = root.getChildByName("Layers");
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator2 = layers.getChildrenByName("Layer").iterator(); arrayIterator2.hasNext(); ) {
      XmlReader.Element layer = arrayIterator2.next();
      loadLayer(map, layer);
    } 
    return map;
  }
  
  private Array<FileHandle> loadTileSheets(XmlReader.Element root, FileHandle tideFile) throws IOException {
    Array<FileHandle> images = new Array<>();
    XmlReader.Element tilesheets = root.getChildByName("TileSheets");
    for (Array.ArrayIterator<XmlReader.Element> arrayIterator = tilesheets.getChildrenByName("TileSheet").iterator(); arrayIterator.hasNext(); ) {
      XmlReader.Element tileset = arrayIterator.next();
      XmlReader.Element imageSource = tileset.getChildByName("ImageSource");
      FileHandle image = getRelativeFileHandle(tideFile, imageSource.getText());
      images.add(image);
    } 
    return images;
  }
  
  private void loadTileSheet(TiledMap map, XmlReader.Element element, FileHandle tideFile, ImageResolver imageResolver) {
    if (element.getName().equals("TileSheet")) {
      String id = element.getAttribute("Id");
      String description = element.getChildByName("Description").getText();
      String imageSource = element.getChildByName("ImageSource").getText();
      XmlReader.Element alignment = element.getChildByName("Alignment");
      String sheetSize = alignment.getAttribute("SheetSize");
      String tileSize = alignment.getAttribute("TileSize");
      String margin = alignment.getAttribute("Margin");
      String spacing = alignment.getAttribute("Spacing");
      String[] sheetSizeParts = sheetSize.split(" x ");
      int sheetSizeX = Integer.parseInt(sheetSizeParts[0]);
      int sheetSizeY = Integer.parseInt(sheetSizeParts[1]);
      String[] tileSizeParts = tileSize.split(" x ");
      int tileSizeX = Integer.parseInt(tileSizeParts[0]);
      int tileSizeY = Integer.parseInt(tileSizeParts[1]);
      String[] marginParts = margin.split(" x ");
      int marginX = Integer.parseInt(marginParts[0]);
      int marginY = Integer.parseInt(marginParts[1]);
      String[] spacingParts = margin.split(" x ");
      int spacingX = Integer.parseInt(spacingParts[0]);
      int spacingY = Integer.parseInt(spacingParts[1]);
      FileHandle image = getRelativeFileHandle(tideFile, imageSource);
      TextureRegion texture = imageResolver.getImage(image.path());
      TiledMapTileSets tilesets = map.getTileSets();
      int firstgid = 1;
      for (TiledMapTileSet tiledMapTileSet : (Iterable<TiledMapTileSet>)tilesets)
        firstgid += tiledMapTileSet.size(); 
      TiledMapTileSet tileset = new TiledMapTileSet();
      tileset.setName(id);
      tileset.getProperties().put("firstgid", Integer.valueOf(firstgid));
      int gid = firstgid;
      int stopWidth = texture.getRegionWidth() - tileSizeX;
      int stopHeight = texture.getRegionHeight() - tileSizeY;
      for (int y = marginY; y <= stopHeight; y += tileSizeY + spacingY) {
        for (int x = marginX; x <= stopWidth; x += tileSizeX + spacingX) {
          TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(texture, x, y, tileSizeX, tileSizeY));
          tile.setId(gid);
          tileset.putTile(gid++, tile);
        } 
      } 
      XmlReader.Element properties = element.getChildByName("Properties");
      if (properties != null)
        loadProperties(tileset.getProperties(), properties); 
      tilesets.addTileSet(tileset);
    } 
  }
  
  private void loadLayer(TiledMap map, XmlReader.Element element) {
    if (element.getName().equals("Layer")) {
      String id = element.getAttribute("Id");
      String visible = element.getAttribute("Visible");
      XmlReader.Element dimensions = element.getChildByName("Dimensions");
      String layerSize = dimensions.getAttribute("LayerSize");
      String tileSize = dimensions.getAttribute("TileSize");
      String[] layerSizeParts = layerSize.split(" x ");
      int layerSizeX = Integer.parseInt(layerSizeParts[0]);
      int layerSizeY = Integer.parseInt(layerSizeParts[1]);
      String[] tileSizeParts = tileSize.split(" x ");
      int tileSizeX = Integer.parseInt(tileSizeParts[0]);
      int tileSizeY = Integer.parseInt(tileSizeParts[1]);
      TiledMapTileLayer layer = new TiledMapTileLayer(layerSizeX, layerSizeY, tileSizeX, tileSizeY);
      layer.setName(id);
      layer.setVisible(visible.equalsIgnoreCase("True"));
      XmlReader.Element tileArray = element.getChildByName("TileArray");
      Array<XmlReader.Element> rows = tileArray.getChildrenByName("Row");
      TiledMapTileSets tilesets = map.getTileSets();
      TiledMapTileSet currentTileSet = null;
      int firstgid = 0;
      for (int row = 0, rowCount = rows.size; row < rowCount; row++) {
        XmlReader.Element currentRow = rows.get(row);
        int y = rowCount - 1 - row;
        int x = 0;
        for (int child = 0, childCount = currentRow.getChildCount(); child < childCount; child++) {
          XmlReader.Element currentChild = currentRow.getChild(child);
          String name = currentChild.getName();
          if (name.equals("TileSheet")) {
            currentTileSet = tilesets.getTileSet(currentChild.getAttribute("Ref"));
            firstgid = currentTileSet.getProperties().<Integer>get("firstgid", Integer.class);
          } else if (name.equals("Null")) {
            x += currentChild.getIntAttribute("Count");
          } else if (name.equals("Static")) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(currentTileSet.getTile(firstgid + currentChild.getIntAttribute("Index")));
            layer.setCell(x++, y, cell);
          } else if (name.equals("Animated")) {
            int interval = currentChild.getInt("Interval");
            XmlReader.Element frames = currentChild.getChildByName("Frames");
            Array<StaticTiledMapTile> frameTiles = new Array<>();
            for (int frameChild = 0, frameChildCount = frames.getChildCount(); frameChild < frameChildCount; frameChild++) {
              XmlReader.Element frame = frames.getChild(frameChild);
              String frameName = frame.getName();
              if (frameName.equals("TileSheet")) {
                currentTileSet = tilesets.getTileSet(frame.getAttribute("Ref"));
                firstgid = currentTileSet.getProperties().<Integer>get("firstgid", Integer.class);
              } else if (frameName.equals("Static")) {
                frameTiles.add((StaticTiledMapTile)currentTileSet.getTile(firstgid + frame.getIntAttribute("Index")));
              } 
            } 
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new AnimatedTiledMapTile((float)interval / 1000.0F, frameTiles));
            layer.setCell(x++, y, cell);
          } 
        } 
      } 
      XmlReader.Element properties = element.getChildByName("Properties");
      if (properties != null)
        loadProperties(layer.getProperties(), properties); 
      map.getLayers().add(layer);
    } 
  }
  
  private void loadProperties(MapProperties properties, XmlReader.Element element) {
    if (element.getName().equals("Properties"))
      for (Array.ArrayIterator<XmlReader.Element> arrayIterator = element.getChildrenByName("Property").iterator(); arrayIterator.hasNext(); ) {
        XmlReader.Element property = arrayIterator.next();
        String key = property.getAttribute("Key", null);
        String type = property.getAttribute("Type", null);
        String value = property.getText();
        if (type.equals("Int32")) {
          properties.put(key, Integer.valueOf(Integer.parseInt(value)));
          continue;
        } 
        if (type.equals("String")) {
          properties.put(key, value);
          continue;
        } 
        if (type.equals("Boolean")) {
          properties.put(key, Boolean.valueOf(value.equalsIgnoreCase("true")));
          continue;
        } 
        properties.put(key, value);
      }  
  }
  
  private static FileHandle getRelativeFileHandle(FileHandle file, String path) {
    StringTokenizer tokenizer = new StringTokenizer(path, "\\/");
    FileHandle result = file.parent();
    while (tokenizer.hasMoreElements()) {
      String token = tokenizer.nextToken();
      if (token.equals("..")) {
        result = result.parent();
        continue;
      } 
      result = result.child(token);
    } 
    return result;
  }
}
