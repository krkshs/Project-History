package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.CubemapLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.FacedCubemapData;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.HashMap;
import java.util.Map;

public class Cubemap extends GLTexture {
  private static AssetManager assetManager;
  
  static final Map<Application, Array<Cubemap>> managedCubemaps = new HashMap<>();
  
  protected CubemapData data;
  
  public enum CubemapSide {
    PositiveX(0, 34069, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F, 0.0F),
    NegativeX(1, 34070, 0.0F, -1.0F, 0.0F, -1.0F, 0.0F, 0.0F),
    PositiveY(2, 34071, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F),
    NegativeY(3, 34072, 0.0F, 0.0F, -1.0F, 0.0F, -1.0F, 0.0F),
    PositiveZ(4, 34073, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F),
    NegativeZ(5, 34074, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0F);
    
    public final int index;
    
    public final int glEnum;
    
    public final Vector3 up;
    
    public final Vector3 direction;
    
    CubemapSide(int index, int glEnum, float upX, float upY, float upZ, float directionX, float directionY, float directionZ) {
      this.index = index;
      this.glEnum = glEnum;
      this.up = new Vector3(upX, upY, upZ);
      this.direction = new Vector3(directionX, directionY, directionZ);
    }
    
    public int getGLEnum() {
      return this.glEnum;
    }
    
    public Vector3 getUp(Vector3 out) {
      return out.set(this.up);
    }
    
    public Vector3 getDirection(Vector3 out) {
      return out.set(this.direction);
    }
  }
  
  public Cubemap(CubemapData data) {
    super(34067);
    this.data = data;
    load(data);
    if (data.isManaged())
      addManagedCubemap(Gdx.app, this); 
  }
  
  public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ) {
    this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
  }
  
  public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ, boolean useMipMaps) {
    this(TextureData.Factory.loadFromFile(positiveX, useMipMaps), TextureData.Factory.loadFromFile(negativeX, useMipMaps), 
        TextureData.Factory.loadFromFile(positiveY, useMipMaps), TextureData.Factory.loadFromFile(negativeY, useMipMaps), 
        TextureData.Factory.loadFromFile(positiveZ, useMipMaps), TextureData.Factory.loadFromFile(negativeZ, useMipMaps));
  }
  
  public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ) {
    this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
  }
  
  public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ, boolean useMipMaps) {
    this((positiveX == null) ? null : new PixmapTextureData(positiveX, null, useMipMaps, false), (negativeX == null) ? null : new PixmapTextureData(negativeX, null, useMipMaps, false), (positiveY == null) ? null : new PixmapTextureData(positiveY, null, useMipMaps, false), (negativeY == null) ? null : new PixmapTextureData(negativeY, null, useMipMaps, false), (positiveZ == null) ? null : new PixmapTextureData(positiveZ, null, useMipMaps, false), (negativeZ == null) ? null : new PixmapTextureData(negativeZ, null, useMipMaps, false));
  }
  
  public Cubemap(int width, int height, int depth, Pixmap.Format format) {
    this(new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
  }
  
  public Cubemap(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
    this(new FacedCubemapData(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ));
  }
  
  public void load(CubemapData data) {
    if (!data.isPrepared())
      data.prepare(); 
    bind();
    unsafeSetFilter(this.minFilter, this.magFilter, true);
    unsafeSetWrap(this.uWrap, this.vWrap, true);
    unsafeSetAnisotropicFilter(this.anisotropicFilterLevel, true);
    data.consumeCubemapData();
    Gdx.gl.glBindTexture(this.glTarget, 0);
  }
  
  public CubemapData getCubemapData() {
    return this.data;
  }
  
  public boolean isManaged() {
    return this.data.isManaged();
  }
  
  protected void reload() {
    if (!isManaged())
      throw new GdxRuntimeException("Tried to reload an unmanaged Cubemap"); 
    this.glHandle = Gdx.gl.glGenTexture();
    load(this.data);
  }
  
  public int getWidth() {
    return this.data.getWidth();
  }
  
  public int getHeight() {
    return this.data.getHeight();
  }
  
  public int getDepth() {
    return 0;
  }
  
  public void dispose() {
    if (this.glHandle == 0)
      return; 
    delete();
    if (this.data.isManaged() && managedCubemaps.get(Gdx.app) != null)
      managedCubemaps.get(Gdx.app).removeValue(this, true); 
  }
  
  private static void addManagedCubemap(Application app, Cubemap cubemap) {
    Array<Cubemap> managedCubemapArray = managedCubemaps.get(app);
    if (managedCubemapArray == null)
      managedCubemapArray = new Array<>(); 
    managedCubemapArray.add(cubemap);
    managedCubemaps.put(app, managedCubemapArray);
  }
  
  public static void clearAllCubemaps(Application app) {
    managedCubemaps.remove(app);
  }
  
  public static void invalidateAllCubemaps(Application app) {
    Array<Cubemap> managedCubemapArray = managedCubemaps.get(app);
    if (managedCubemapArray == null)
      return; 
    if (assetManager == null) {
      for (int i = 0; i < managedCubemapArray.size; i++) {
        Cubemap cubemap = managedCubemapArray.get(i);
        cubemap.reload();
      } 
    } else {
      assetManager.finishLoading();
      Array<Cubemap> cubemaps = new Array<>(managedCubemapArray);
      for (Array.ArrayIterator<Cubemap> arrayIterator = cubemaps.iterator(); arrayIterator.hasNext(); ) {
        Cubemap cubemap = arrayIterator.next();
        String fileName = assetManager.<Cubemap>getAssetFileName(cubemap);
        if (fileName == null) {
          cubemap.reload();
          continue;
        } 
        final int refCount = assetManager.getReferenceCount(fileName);
        assetManager.setReferenceCount(fileName, 0);
        cubemap.glHandle = 0;
        CubemapLoader.CubemapParameter params = new CubemapLoader.CubemapParameter();
        params.cubemapData = cubemap.getCubemapData();
        params.minFilter = cubemap.getMinFilter();
        params.magFilter = cubemap.getMagFilter();
        params.wrapU = cubemap.getUWrap();
        params.wrapV = cubemap.getVWrap();
        params.cubemap = cubemap;
        params.loadedCallback = new AssetLoaderParameters.LoadedCallback() {
            public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
              assetManager.setReferenceCount(fileName, refCount);
            }
          };
        assetManager.unload(fileName);
        cubemap.glHandle = Gdx.gl.glGenTexture();
        assetManager.<Cubemap>load(fileName, Cubemap.class, params);
      } 
      managedCubemapArray.clear();
      managedCubemapArray.addAll(cubemaps);
    } 
  }
  
  public static void setAssetManager(AssetManager manager) {
    assetManager = manager;
  }
  
  public static String getManagedStatus() {
    StringBuilder builder = new StringBuilder();
    builder.append("Managed cubemap/app: { ");
    for (Application app : managedCubemaps.keySet()) {
      builder.append(((Array)managedCubemaps.get(app)).size);
      builder.append(" ");
    } 
    builder.append("}");
    return builder.toString();
  }
  
  public static int getNumManagedCubemaps() {
    return ((Array)managedCubemaps.get(Gdx.app)).size;
  }
}
