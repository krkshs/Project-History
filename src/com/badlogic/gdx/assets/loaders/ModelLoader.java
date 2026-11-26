package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;

public abstract class ModelLoader<P extends ModelLoader.ModelParameters> extends AsynchronousAssetLoader<Model, P> {
  public ModelLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  protected Array<ObjectMap.Entry<String, ModelData>> items = new Array<>();
  
  protected ModelParameters defaultParameters = new ModelParameters();
  
  public ModelData loadModelData(FileHandle fileHandle) {
    return loadModelData(fileHandle, null);
  }
  
  public Model loadModel(FileHandle fileHandle, TextureProvider textureProvider, P parameters) {
    ModelData data = loadModelData(fileHandle, parameters);
    return (data == null) ? null : new Model(data, textureProvider);
  }
  
  public Model loadModel(FileHandle fileHandle, P parameters) {
    return loadModel(fileHandle, new TextureProvider.FileTextureProvider(), parameters);
  }
  
  public Model loadModel(FileHandle fileHandle, TextureProvider textureProvider) {
    return loadModel(fileHandle, textureProvider, null);
  }
  
  public Model loadModel(FileHandle fileHandle) {
    return loadModel(fileHandle, new TextureProvider.FileTextureProvider(), null);
  }
  
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, P parameters) {
    Array<AssetDescriptor> deps = new Array<>();
    ModelData data = loadModelData(file, parameters);
    if (data == null)
      return deps; 
    ObjectMap.Entry<String, ModelData> item = new ObjectMap.Entry<>();
    item.key = fileName;
    item.value = data;
    synchronized (this.items) {
      this.items.add(item);
    } 
    TextureLoader.TextureParameter textureParameter = (parameters != null) ? parameters.textureParameter : this.defaultParameters.textureParameter;
    for (Array.ArrayIterator<ModelMaterial> arrayIterator = data.materials.iterator(); arrayIterator.hasNext(); ) {
      ModelMaterial modelMaterial = arrayIterator.next();
      if (modelMaterial.textures != null)
        for (Array.ArrayIterator<ModelTexture> arrayIterator1 = modelMaterial.textures.iterator(); arrayIterator1.hasNext(); ) {
          ModelTexture modelTexture = arrayIterator1.next();
          deps.add(new AssetDescriptor<>(modelTexture.fileName, Texture.class, textureParameter));
        }  
    } 
    return deps;
  }
  
  public void loadAsync(AssetManager manager, String fileName, FileHandle file, P parameters) {}
  
  public Model loadSync(AssetManager manager, String fileName, FileHandle file, P parameters) {
    ModelData data = null;
    synchronized (this.items) {
      for (int i = 0; i < this.items.size; i++) {
        if (((String)((ObjectMap.Entry)this.items.get(i)).key).equals(fileName)) {
          data = (ModelData)((ObjectMap.Entry)this.items.get(i)).value;
          this.items.removeIndex(i);
        } 
      } 
    } 
    if (data == null)
      return null; 
    Model result = new Model(data, new TextureProvider.AssetTextureProvider(manager));
    Iterator<Disposable> disposables = result.getManagedDisposables().iterator();
    while (disposables.hasNext()) {
      Disposable disposable = disposables.next();
      if (disposable instanceof Texture)
        disposables.remove(); 
    } 
    return result;
  }
  
  public abstract ModelData loadModelData(FileHandle paramFileHandle, P paramP);
  
  public static class ModelParameters extends AssetLoaderParameters<Model> {
    public TextureLoader.TextureParameter textureParameter;
    
    public ModelParameters() {
      this.textureParameter = new TextureLoader.TextureParameter();
      this.textureParameter.minFilter = this.textureParameter.magFilter = Texture.TextureFilter.Linear;
      this.textureParameter.wrapU = this.textureParameter.wrapV = Texture.TextureWrap.Repeat;
    }
  }
}
