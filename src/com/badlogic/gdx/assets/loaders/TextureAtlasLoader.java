package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class TextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {
  TextureAtlas.TextureAtlasData data;
  
  public TextureAtlasLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  public TextureAtlas load(AssetManager assetManager, String fileName, FileHandle file, TextureAtlasParameter parameter) {
    for (Array.ArrayIterator<TextureAtlas.TextureAtlasData.Page> arrayIterator = this.data.getPages().iterator(); arrayIterator.hasNext(); ) {
      TextureAtlas.TextureAtlasData.Page page = arrayIterator.next();
      Texture texture = assetManager.<Texture>get(page.textureFile.path().replaceAll("\\\\", "/"), Texture.class);
      page.texture = texture;
    } 
    TextureAtlas atlas = new TextureAtlas(this.data);
    this.data = null;
    return atlas;
  }
  
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle atlasFile, TextureAtlasParameter parameter) {
    FileHandle imgDir = atlasFile.parent();
    if (parameter != null) {
      this.data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, parameter.flip);
    } else
      this.data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, false); 
    Array<AssetDescriptor> dependencies = new Array<>();
    for (Array.ArrayIterator<TextureAtlas.TextureAtlasData.Page> arrayIterator = this.data.getPages().iterator(); arrayIterator.hasNext(); ) {
      TextureAtlas.TextureAtlasData.Page page = arrayIterator.next();
      TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
      params.format = page.format;
      params.genMipMaps = page.useMipMaps;
      params.minFilter = page.minFilter;
      params.magFilter = page.magFilter;
      dependencies.add(new AssetDescriptor<>(page.textureFile, Texture.class, params));
    } 
    return dependencies;
  }
  
  public static class TextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {
    public boolean flip = false;
    
    public TextureAtlasParameter() {}
    
    public TextureAtlasParameter(boolean flip) {
      this.flip = flip;
    }
  }
}
