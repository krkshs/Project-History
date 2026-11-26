package com.badlogic.gdx.assets;

import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.CubemapLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class AssetManager implements Disposable {
  final ObjectMap<Class, ObjectMap<String, RefCountedContainer>> assets = new ObjectMap<>();
  
  final ObjectMap<String, Class> assetTypes = new ObjectMap<>();
  
  final ObjectMap<String, Array<String>> assetDependencies = new ObjectMap<>();
  
  final ObjectSet<String> injected = new ObjectSet<>();
  
  final ObjectMap<Class, ObjectMap<String, AssetLoader>> loaders = new ObjectMap<>();
  
  final Array<AssetDescriptor> loadQueue = new Array<>();
  
  final AsyncExecutor executor;
  
  final Array<AssetLoadingTask> tasks = new Array<>();
  
  AssetErrorListener listener;
  
  int loaded;
  
  int toLoad;
  
  int peakTasks;
  
  final FileHandleResolver resolver;
  
  Logger log = new Logger("AssetManager", 0);
  
  public AssetManager() {
    this(new InternalFileHandleResolver());
  }
  
  public AssetManager(FileHandleResolver resolver) {
    this(resolver, true);
  }
  
  public AssetManager(FileHandleResolver resolver, boolean defaultLoaders) {
    this.resolver = resolver;
    if (defaultLoaders) {
      setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
      setLoader(Music.class, new MusicLoader(resolver));
      setLoader(Pixmap.class, new PixmapLoader(resolver));
      setLoader(Sound.class, new SoundLoader(resolver));
      setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
      setLoader(Texture.class, new TextureLoader(resolver));
      setLoader(Skin.class, new SkinLoader(resolver));
      setLoader(ParticleEffect.class, new ParticleEffectLoader(resolver));
      setLoader(com.badlogic.gdx.graphics.g3d.particles.ParticleEffect.class, new com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader(resolver));
      setLoader(PolygonRegion.class, new PolygonRegionLoader(resolver));
      setLoader(I18NBundle.class, new I18NBundleLoader(resolver));
      setLoader(Model.class, ".g3dj", new G3dModelLoader(new JsonReader(), resolver));
      setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), resolver));
      setLoader(Model.class, ".obj", new ObjLoader(resolver));
      setLoader(ShaderProgram.class, new ShaderProgramLoader(resolver));
      setLoader(Cubemap.class, new CubemapLoader(resolver));
    } 
    this.executor = new AsyncExecutor(1, "AssetManager");
  }
  
  public FileHandleResolver getFileHandleResolver() {
    return this.resolver;
  }
  
  public synchronized <T> T get(String fileName) {
    return get(fileName, true);
  }
  
  public synchronized <T> T get(String fileName, Class<T> type) {
    return get(fileName, type, true);
  }
  
  @Null
  public synchronized <T> T get(String fileName, boolean required) {
    Class<T> type = this.assetTypes.<String>get(fileName);
    if (type != null) {
      ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<T>>get(type);
      if (assetsByType != null) {
        RefCountedContainer assetContainer = assetsByType.<String>get(fileName);
        if (assetContainer != null)
          return (T)assetContainer.object; 
      } 
    } 
    if (required)
      throw new GdxRuntimeException("Asset not loaded: " + fileName); 
    return null;
  }
  
  @Null
  public synchronized <T> T get(String fileName, Class<T> type, boolean required) {
    ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<T>>get(type);
    if (assetsByType != null) {
      RefCountedContainer assetContainer = assetsByType.<String>get(fileName);
      if (assetContainer != null)
        return (T)assetContainer.object; 
    } 
    if (required)
      throw new GdxRuntimeException("Asset not loaded: " + fileName); 
    return null;
  }
  
  public synchronized <T> T get(AssetDescriptor<T> assetDescriptor) {
    return get(assetDescriptor.fileName, assetDescriptor.type, true);
  }
  
  public synchronized <T> Array<T> getAll(Class<T> type, Array<T> out) {
    ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<T>>get(type);
    if (assetsByType != null)
      for (ObjectMap.Values<RefCountedContainer> values = assetsByType.values().iterator(); values.hasNext(); ) {
        RefCountedContainer assetRef = values.next();
        out.add((T)assetRef.object);
      }  
    return out;
  }
  
  public synchronized boolean contains(String fileName) {
    if (this.tasks.size > 0 && ((AssetLoadingTask)this.tasks.first()).assetDesc.fileName.equals(fileName))
      return true; 
    for (int i = 0; i < this.loadQueue.size; i++) {
      if (((AssetDescriptor)this.loadQueue.get(i)).fileName.equals(fileName))
        return true; 
    } 
    return isLoaded(fileName);
  }
  
  public synchronized boolean contains(String fileName, Class type) {
    if (this.tasks.size > 0) {
      AssetDescriptor assetDesc = ((AssetLoadingTask)this.tasks.first()).assetDesc;
      if (assetDesc.type == type && assetDesc.fileName.equals(fileName))
        return true; 
    } 
    for (int i = 0; i < this.loadQueue.size; i++) {
      AssetDescriptor assetDesc = this.loadQueue.get(i);
      if (assetDesc.type == type && assetDesc.fileName.equals(fileName))
        return true; 
    } 
    return isLoaded(fileName, type);
  }
  
  public synchronized void unload(String fileName) {
    fileName = fileName.replace('\\', '/');
    if (this.tasks.size > 0) {
      AssetLoadingTask currentTask = this.tasks.first();
      if (currentTask.assetDesc.fileName.equals(fileName)) {
        this.log.info("Unload (from tasks): " + fileName);
        currentTask.cancel = true;
        currentTask.unload();
        return;
      } 
    } 
    Class<?> type = this.assetTypes.<String>get(fileName);
    int foundIndex = -1;
    for (int i = 0; i < this.loadQueue.size; i++) {
      if (((AssetDescriptor)this.loadQueue.get(i)).fileName.equals(fileName)) {
        foundIndex = i;
        break;
      } 
    } 
    if (foundIndex != -1) {
      this.toLoad--;
      AssetDescriptor desc = this.loadQueue.removeIndex(foundIndex);
      this.log.info("Unload (from queue): " + fileName);
      if (type != null && desc.params != null && desc.params.loadedCallback != null)
        desc.params.loadedCallback.finishedLoading(this, desc.fileName, desc.type); 
      return;
    } 
    if (type == null)
      throw new GdxRuntimeException("Asset not loaded: " + fileName); 
    RefCountedContainer assetRef = (RefCountedContainer)this.assets.<Class<?>>get(type).get(fileName);
    assetRef.refCount--;
    if (assetRef.refCount <= 0) {
      this.log.info("Unload (dispose): " + fileName);
      if (assetRef.object instanceof Disposable)
        ((Disposable)assetRef.object).dispose(); 
      this.assetTypes.remove(fileName);
      this.assets.<Class<?>>get(type).remove(fileName);
    } else
      this.log.info("Unload (decrement): " + fileName); 
    Array<String> dependencies = this.assetDependencies.<String>get(fileName);
    if (dependencies != null)
      for (Array.ArrayIterator<String> arrayIterator = dependencies.iterator(); arrayIterator.hasNext(); ) {
        String dependency = arrayIterator.next();
        if (isLoaded(dependency))
          unload(dependency); 
      }  
    if (assetRef.refCount <= 0)
      this.assetDependencies.remove(fileName); 
  }
  
  public synchronized <T> boolean containsAsset(T asset) {
    ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<?>>get(asset.getClass());
    if (assetsByType == null)
      return false; 
    for (ObjectMap.Values<RefCountedContainer> values = assetsByType.values().iterator(); values.hasNext(); ) {
      RefCountedContainer assetRef = values.next();
      if (assetRef.object == asset || asset.equals(assetRef.object))
        return true; 
    } 
    return false;
  }
  
  public synchronized <T> String getAssetFileName(T asset) {
    for (ObjectMap.Keys<Class<?>> keys = this.assets.keys().iterator(); keys.hasNext(); ) {
      Class<?> assetType = keys.next();
      ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<?>>get(assetType);
      for (ObjectMap.Entries<String, RefCountedContainer> entries = assetsByType.iterator(); entries.hasNext(); ) {
        ObjectMap.Entry<String, RefCountedContainer> entry = (ObjectMap.Entry<String, RefCountedContainer>)entries.next();
        Object object = ((RefCountedContainer)entry.value).object;
        if (object == asset || asset.equals(object))
          return (String)entry.key; 
      } 
    } 
    return null;
  }
  
  public synchronized boolean isLoaded(AssetDescriptor assetDesc) {
    return isLoaded(assetDesc.fileName);
  }
  
  public synchronized boolean isLoaded(String fileName) {
    if (fileName == null)
      return false; 
    return this.assetTypes.containsKey(fileName);
  }
  
  public synchronized boolean isLoaded(String fileName, Class type) {
    ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<?>>get(type);
    if (assetsByType == null)
      return false; 
    return (assetsByType.<String>get(fileName) != null);
  }
  
  public <T> AssetLoader getLoader(Class<T> type) {
    return getLoader(type, null);
  }
  
  public <T> AssetLoader getLoader(Class<T> type, String fileName) {
    ObjectMap<String, AssetLoader> loaders = this.loaders.<Class<T>>get(type);
    if (loaders == null || loaders.size < 1)
      return null; 
    if (fileName == null)
      return loaders.<String>get(""); 
    AssetLoader result = null;
    int length = -1;
    for (ObjectMap.Entries<ObjectMap.Entry<String, AssetLoader>> entries = loaders.entries().iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<String, AssetLoader> entry = entries.next();
      if (((String)entry.key).length() > length && fileName.endsWith((String)entry.key)) {
        result = (AssetLoader)entry.value;
        length = ((String)entry.key).length();
      } 
    } 
    return result;
  }
  
  public synchronized <T> void load(String fileName, Class<T> type) {
    load(fileName, type, null);
  }
  
  public synchronized <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
    AssetLoader loader = getLoader(type, fileName);
    if (loader == null)
      throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(type)); 
    if (this.loadQueue.size == 0) {
      this.loaded = 0;
      this.toLoad = 0;
      this.peakTasks = 0;
    } 
    for (int j = 0; j < this.loadQueue.size; j++) {
      AssetDescriptor desc = this.loadQueue.get(j);
      if (desc.fileName.equals(fileName) && !desc.type.equals(type))
        throw new GdxRuntimeException("Asset with name '" + fileName + "' already in preload queue, but has different type (expected: " + 
            
            ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(desc.type) + ")"); 
    } 
    for (int i = 0; i < this.tasks.size; i++) {
      AssetDescriptor desc = ((AssetLoadingTask)this.tasks.get(i)).assetDesc;
      if (desc.fileName.equals(fileName) && !desc.type.equals(type))
        throw new GdxRuntimeException("Asset with name '" + fileName + "' already in task list, but has different type (expected: " + 
            
            ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(desc.type) + ")"); 
    } 
    Class otherType = this.assetTypes.<String>get(fileName);
    if (otherType != null && !otherType.equals(type))
      throw new GdxRuntimeException("Asset with name '" + fileName + "' already loaded, but has different type (expected: " + 
          ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(otherType) + ")"); 
    this.toLoad++;
    AssetDescriptor<T> assetDesc = new AssetDescriptor<>(fileName, type, parameter);
    this.loadQueue.add(assetDesc);
    this.log.debug("Queued: " + assetDesc);
  }
  
  public synchronized void load(AssetDescriptor desc) {
    load(desc.fileName, desc.type, desc.params);
  }
  
  public synchronized boolean update() {
    try {
      if (this.tasks.size == 0) {
        while (this.loadQueue.size != 0 && this.tasks.size == 0)
          nextTask(); 
        if (this.tasks.size == 0)
          return true; 
      } 
      return (updateTask() && this.loadQueue.size == 0 && this.tasks.size == 0);
    } catch (Throwable t) {
      handleTaskError(t);
      return (this.loadQueue.size == 0);
    } 
  }
  
  public boolean update(int millis) {
    long endTime = TimeUtils.millis() + (long)millis;
    while (true) {
      boolean done = update();
      if (done || TimeUtils.millis() > endTime)
        return done; 
      ThreadUtils.yield();
    } 
  }
  
  public synchronized boolean isFinished() {
    return (this.loadQueue.size == 0 && this.tasks.size == 0);
  }
  
  public void finishLoading() {
    this.log.debug("Waiting for loading to complete...");
    while (!update())
      ThreadUtils.yield(); 
    this.log.debug("Loading complete.");
  }
  
  public <T> T finishLoadingAsset(AssetDescriptor assetDesc) {
    return finishLoadingAsset(assetDesc.fileName);
  }
  
  public <T> T finishLoadingAsset(String fileName) {
    this.log.debug("Waiting for asset to be loaded: " + fileName);
    while (true) {
      synchronized (this) {
        Class<T> type = this.assetTypes.<String>get(fileName);
        if (type != null) {
          ObjectMap<String, RefCountedContainer> assetsByType = this.assets.<Class<T>>get(type);
          if (assetsByType != null) {
            RefCountedContainer assetContainer = assetsByType.<String>get(fileName);
            if (assetContainer != null) {
              this.log.debug("Asset loaded: " + fileName);
              return (T)assetContainer.object;
            } 
          } 
        } 
        update();
      } 
      ThreadUtils.yield();
    } 
  }
  
  synchronized void injectDependencies(String parentAssetFilename, Array<AssetDescriptor> dependendAssetDescs) {
    ObjectSet<String> injected = this.injected;
    for (Array.ArrayIterator<AssetDescriptor> arrayIterator = dependendAssetDescs.iterator(); arrayIterator.hasNext(); ) {
      AssetDescriptor desc = arrayIterator.next();
      if (injected.contains(desc.fileName))
        continue; 
      injected.add(desc.fileName);
      injectDependency(parentAssetFilename, desc);
    } 
    injected.clear(32);
  }
  
  private synchronized void injectDependency(String parentAssetFilename, AssetDescriptor dependendAssetDesc) {
    Array<String> dependencies = this.assetDependencies.<String>get(parentAssetFilename);
    if (dependencies == null) {
      dependencies = new Array<>();
      this.assetDependencies.put(parentAssetFilename, dependencies);
    } 
    dependencies.add(dependendAssetDesc.fileName);
    if (isLoaded(dependendAssetDesc.fileName)) {
      this.log.debug("Dependency already loaded: " + dependendAssetDesc);
      Class<?> type = this.assetTypes.<String>get(dependendAssetDesc.fileName);
      RefCountedContainer assetRef = (RefCountedContainer)this.assets.<Class<?>>get(type).get(dependendAssetDesc.fileName);
      assetRef.refCount++;
      incrementRefCountedDependencies(dependendAssetDesc.fileName);
    } else {
      this.log.info("Loading dependency: " + dependendAssetDesc);
      addTask(dependendAssetDesc);
    } 
  }
  
  private void nextTask() {
    AssetDescriptor assetDesc = this.loadQueue.removeIndex(0);
    if (isLoaded(assetDesc.fileName)) {
      this.log.debug("Already loaded: " + assetDesc);
      Class<?> type = this.assetTypes.<String>get(assetDesc.fileName);
      RefCountedContainer assetRef = (RefCountedContainer)this.assets.<Class<?>>get(type).get(assetDesc.fileName);
      assetRef.refCount++;
      incrementRefCountedDependencies(assetDesc.fileName);
      if (assetDesc.params != null && assetDesc.params.loadedCallback != null)
        assetDesc.params.loadedCallback.finishedLoading(this, assetDesc.fileName, assetDesc.type); 
      this.loaded++;
    } else {
      this.log.info("Loading: " + assetDesc);
      addTask(assetDesc);
    } 
  }
  
  private void addTask(AssetDescriptor assetDesc) {
    AssetLoader loader = getLoader(assetDesc.type, assetDesc.fileName);
    if (loader == null)
      throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(assetDesc.type)); 
    this.tasks.add(new AssetLoadingTask(this, assetDesc, loader, this.executor));
    this.peakTasks++;
  }
  
  protected <T> void addAsset(String fileName, Class<T> type, T asset) {
    this.assetTypes.put(fileName, type);
    ObjectMap<String, RefCountedContainer> typeToAssets = this.assets.<Class<T>>get(type);
    if (typeToAssets == null) {
      typeToAssets = new ObjectMap<>();
      this.assets.put(type, typeToAssets);
    } 
    RefCountedContainer assetRef = new RefCountedContainer();
    assetRef.object = asset;
    typeToAssets.put(fileName, assetRef);
  }
  
  private boolean updateTask() {
    AssetLoadingTask task = this.tasks.peek();
    boolean complete = true;
    try {
      complete = (task.cancel || task.update());
    } catch (RuntimeException ex) {
      task.cancel = true;
      taskFailed(task.assetDesc, ex);
    } 
    if (complete) {
      if (this.tasks.size == 1) {
        this.loaded++;
        this.peakTasks = 0;
      } 
      this.tasks.pop();
      if (task.cancel)
        return true; 
      addAsset(task.assetDesc.fileName, task.assetDesc.type, task.asset);
      if (task.assetDesc.params != null && task.assetDesc.params.loadedCallback != null)
        task.assetDesc.params.loadedCallback.finishedLoading(this, task.assetDesc.fileName, task.assetDesc.type); 
      long endTime = TimeUtils.nanoTime();
      this.log.debug("Loaded: " + ((float)(endTime - task.startTime) / 1000000.0F) + "ms " + task.assetDesc);
      return true;
    } 
    return false;
  }
  
  protected void taskFailed(AssetDescriptor assetDesc, RuntimeException ex) {
    throw ex;
  }
  
  private void incrementRefCountedDependencies(String parent) {
    Array<String> dependencies = this.assetDependencies.<String>get(parent);
    if (dependencies == null)
      return; 
    for (Array.ArrayIterator<String> arrayIterator = dependencies.iterator(); arrayIterator.hasNext(); ) {
      String dependency = arrayIterator.next();
      Class<?> type = this.assetTypes.<String>get(dependency);
      RefCountedContainer assetRef = (RefCountedContainer)this.assets.<Class<?>>get(type).get(dependency);
      assetRef.refCount++;
      incrementRefCountedDependencies(dependency);
    } 
  }
  
  private void handleTaskError(Throwable t) {
    this.log.error("Error loading asset.", t);
    if (this.tasks.isEmpty())
      throw new GdxRuntimeException(t); 
    AssetLoadingTask task = this.tasks.pop();
    AssetDescriptor assetDesc = task.assetDesc;
    if (task.dependenciesLoaded && task.dependencies != null)
      for (Array.ArrayIterator<AssetDescriptor> arrayIterator = task.dependencies.iterator(); arrayIterator.hasNext(); ) {
        AssetDescriptor desc = arrayIterator.next();
        unload(desc.fileName);
      }  
    this.tasks.clear();
    if (this.listener != null) {
      this.listener.error(assetDesc, t);
    } else
      throw new GdxRuntimeException(t); 
  }
  
  public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
    setLoader(type, null, loader);
  }
  
  public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, String suffix, AssetLoader<T, P> loader) {
    if (type == null)
      throw new IllegalArgumentException("type cannot be null."); 
    if (loader == null)
      throw new IllegalArgumentException("loader cannot be null."); 
    this.log.debug("Loader set: " + ClassReflection.getSimpleName(type) + " -> " + ClassReflection.getSimpleName(loader.getClass()));
    ObjectMap<String, AssetLoader> loaders = this.loaders.<Class<T>>get(type);
    if (loaders == null)
      this.loaders.put(type, loaders = new ObjectMap<>()); 
    loaders.put((suffix == null) ? "" : suffix, loader);
  }
  
  public synchronized int getLoadedAssets() {
    return this.assetTypes.size;
  }
  
  public synchronized int getQueuedAssets() {
    return this.loadQueue.size + this.tasks.size;
  }
  
  public synchronized float getProgress() {
    if (this.toLoad == 0)
      return 1.0F; 
    float fractionalLoaded = (float)this.loaded;
    if (this.peakTasks > 0)
      fractionalLoaded += (float)(this.peakTasks - this.tasks.size) / (float)this.peakTasks; 
    return Math.min(1.0F, fractionalLoaded / (float)this.toLoad);
  }
  
  public synchronized void setErrorListener(AssetErrorListener listener) {
    this.listener = listener;
  }
  
  public synchronized void dispose() {
    this.log.debug("Disposing.");
    clear();
    this.executor.dispose();
  }
  
  public synchronized void clear() {
    this.loadQueue.clear();
    while (!update());
    ObjectIntMap<String> dependencyCount = new ObjectIntMap<>();
    while (this.assetTypes.size > 0) {
      dependencyCount.clear();
      Array<String> assets = this.assetTypes.keys().toArray();
      for (Array.ArrayIterator<String> arrayIterator2 = assets.iterator(); arrayIterator2.hasNext(); ) {
        String asset = arrayIterator2.next();
        Array<String> dependencies = this.assetDependencies.<String>get(asset);
        if (dependencies == null)
          continue; 
        for (Array.ArrayIterator<String> arrayIterator = dependencies.iterator(); arrayIterator.hasNext(); ) {
          String dependency = arrayIterator.next();
          dependencyCount.getAndIncrement(dependency, 0, 1);
        } 
      } 
      for (Array.ArrayIterator<String> arrayIterator1 = assets.iterator(); arrayIterator1.hasNext(); ) {
        String asset = arrayIterator1.next();
        if (dependencyCount.get(asset, 0) == 0)
          unload(asset); 
      } 
    } 
    this.assets.clear();
    this.assetTypes.clear();
    this.assetDependencies.clear();
    this.loaded = 0;
    this.toLoad = 0;
    this.peakTasks = 0;
    this.loadQueue.clear();
    this.tasks.clear();
  }
  
  public Logger getLogger() {
    return this.log;
  }
  
  public void setLogger(Logger logger) {
    this.log = logger;
  }
  
  public synchronized int getReferenceCount(String fileName) {
    Class type = this.assetTypes.<String>get(fileName);
    if (type == null)
      throw new GdxRuntimeException("Asset not loaded: " + fileName); 
    return ((RefCountedContainer)((ObjectMap)this.assets.get(type)).get(fileName)).refCount;
  }
  
  public synchronized void setReferenceCount(String fileName, int refCount) {
    Class type = this.assetTypes.<String>get(fileName);
    if (type == null)
      throw new GdxRuntimeException("Asset not loaded: " + fileName); 
    ((RefCountedContainer)((ObjectMap)this.assets.get(type)).get(fileName)).refCount = refCount;
  }
  
  public synchronized String getDiagnostics() {
    StringBuilder buffer = new StringBuilder(256);
    for (ObjectMap.Entries<String, Class> entries = this.assetTypes.iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<String, Class<?>> entry = (ObjectMap.Entry<String, Class<?>>)entries.next();
      String fileName = (String)entry.key;
      Class type = (Class)entry.value;
      if (buffer.length() > 0)
        buffer.append('\n'); 
      buffer.append(fileName);
      buffer.append(", ");
      buffer.append(ClassReflection.getSimpleName(type));
      buffer.append(", refs: ");
      buffer.append(((RefCountedContainer)((ObjectMap)this.assets.get(type)).get(fileName)).refCount);
      Array<String> dependencies = this.assetDependencies.<String>get(fileName);
      if (dependencies != null) {
        buffer.append(", deps: [");
        for (Array.ArrayIterator<String> arrayIterator = dependencies.iterator(); arrayIterator.hasNext(); ) {
          String dep = arrayIterator.next();
          buffer.append(dep);
          buffer.append(',');
        } 
        buffer.append(']');
      } 
    } 
    return buffer.toString();
  }
  
  public synchronized Array<String> getAssetNames() {
    return this.assetTypes.keys().toArray();
  }
  
  public synchronized Array<String> getDependencies(String fileName) {
    return this.assetDependencies.<String>get(fileName);
  }
  
  public synchronized Class getAssetType(String fileName) {
    return this.assetTypes.<String>get(fileName);
  }
  
  static class RefCountedContainer {
    Object object;
    
    int refCount = 1;
  }
}
