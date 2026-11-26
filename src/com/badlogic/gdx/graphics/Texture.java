package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.HashMap;
import java.util.Map;

public class Texture extends GLTexture {
  private static AssetManager assetManager;
  
  static final Map<Application, Array<Texture>> managedTextures = new HashMap<>();
  
  TextureData data;
  
  public enum TextureFilter {
    Nearest(9728),
    Linear(9729),
    MipMap(9987),
    MipMapNearestNearest(9984),
    MipMapLinearNearest(9985),
    MipMapNearestLinear(9986),
    MipMapLinearLinear(9987);
    
    final int glEnum;
    
    TextureFilter(int glEnum) {
      this.glEnum = glEnum;
    }
    
    public boolean isMipMap() {
      return (this.glEnum != 9728 && this.glEnum != 9729);
    }
    
    public int getGLEnum() {
      return this.glEnum;
    }
  }
  
  public enum TextureWrap {
    MirroredRepeat(33648),
    ClampToEdge(33071),
    Repeat(10497);
    
    final int glEnum;
    
    TextureWrap(int glEnum) {
      this.glEnum = glEnum;
    }
    
    public int getGLEnum() {
      return this.glEnum;
    }
  }
  
  public Texture(String internalPath) {
    this(Gdx.files.internal(internalPath));
  }
  
  public Texture(FileHandle file) {
    this(file, null, false);
  }
  
  public Texture(FileHandle file, boolean useMipMaps) {
    this(file, null, useMipMaps);
  }
  
  public Texture(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
    this(TextureData.Factory.loadFromFile(file, format, useMipMaps));
  }
  
  public Texture(Pixmap pixmap) {
    this(new PixmapTextureData(pixmap, null, false, false));
  }
  
  public Texture(Pixmap pixmap, boolean useMipMaps) {
    this(new PixmapTextureData(pixmap, null, useMipMaps, false));
  }
  
  public Texture(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
    this(new PixmapTextureData(pixmap, format, useMipMaps, false));
  }
  
  public Texture(int width, int height, Pixmap.Format format) {
    this(new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
  }
  
  public Texture(TextureData data) {
    this(3553, Gdx.gl.glGenTexture(), data);
  }
  
  protected Texture(int glTarget, int glHandle, TextureData data) {
    super(glTarget, glHandle);
    load(data);
    if (data.isManaged())
      addManagedTexture(Gdx.app, this); 
  }
  
  public void load(TextureData data) {
    if (this.data != null && data.isManaged() != this.data.isManaged())
      throw new GdxRuntimeException("New data must have the same managed status as the old data"); 
    this.data = data;
    if (!data.isPrepared())
      data.prepare(); 
    bind();
    uploadImageData(3553, data);
    unsafeSetFilter(this.minFilter, this.magFilter, true);
    unsafeSetWrap(this.uWrap, this.vWrap, true);
    unsafeSetAnisotropicFilter(this.anisotropicFilterLevel, true);
    Gdx.gl.glBindTexture(this.glTarget, 0);
  }
  
  protected void reload() {
    if (!isManaged())
      throw new GdxRuntimeException("Tried to reload unmanaged Texture"); 
    this.glHandle = Gdx.gl.glGenTexture();
    load(this.data);
  }
  
  public void draw(Pixmap pixmap, int x, int y) {
    if (this.data.isManaged())
      throw new GdxRuntimeException("can't draw to a managed texture"); 
    bind();
    Gdx.gl.glTexSubImage2D(this.glTarget, 0, x, y, pixmap.getWidth(), pixmap.getHeight(), pixmap.getGLFormat(), pixmap.getGLType(), 
        pixmap.getPixels());
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
  
  public TextureData getTextureData() {
    return this.data;
  }
  
  public boolean isManaged() {
    return this.data.isManaged();
  }
  
  public void dispose() {
    if (this.glHandle == 0)
      return; 
    delete();
    if (this.data.isManaged() && managedTextures.get(Gdx.app) != null)
      managedTextures.get(Gdx.app).removeValue(this, true); 
  }
  
  public String toString() {
    if (this.data instanceof com.badlogic.gdx.graphics.glutils.FileTextureData)
      return this.data.toString(); 
    return super.toString();
  }
  
  private static void addManagedTexture(Application app, Texture texture) {
    Array<Texture> managedTextureArray = managedTextures.get(app);
    if (managedTextureArray == null)
      managedTextureArray = new Array<>(); 
    managedTextureArray.add(texture);
    managedTextures.put(app, managedTextureArray);
  }
  
  public static void clearAllTextures(Application app) {
    managedTextures.remove(app);
  }
  
  public static void invalidateAllTextures(Application app) {
    Array<Texture> managedTextureArray = managedTextures.get(app);
    if (managedTextureArray == null)
      return; 
    if (assetManager == null) {
      for (int i = 0; i < managedTextureArray.size; i++) {
        Texture texture = managedTextureArray.get(i);
        texture.reload();
      } 
    } else {
      assetManager.finishLoading();
      Array<Texture> textures = new Array<>(managedTextureArray);
      for (Array.ArrayIterator<Texture> arrayIterator = textures.iterator(); arrayIterator.hasNext(); ) {
        Texture texture = arrayIterator.next();
        String fileName = assetManager.<Texture>getAssetFileName(texture);
        if (fileName == null) {
          texture.reload();
          continue;
        } 
        final int refCount = assetManager.getReferenceCount(fileName);
        assetManager.setReferenceCount(fileName, 0);
        texture.glHandle = 0;
        TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
        params.textureData = texture.getTextureData();
        params.minFilter = texture.getMinFilter();
        params.magFilter = texture.getMagFilter();
        params.wrapU = texture.getUWrap();
        params.wrapV = texture.getVWrap();
        params.genMipMaps = texture.data.useMipMaps();
        params.texture = texture;
        params.loadedCallback = new AssetLoaderParameters.LoadedCallback() {
            public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
              assetManager.setReferenceCount(fileName, refCount);
            }
          };
        assetManager.unload(fileName);
        texture.glHandle = Gdx.gl.glGenTexture();
        assetManager.<Texture>load(fileName, Texture.class, params);
      } 
      managedTextureArray.clear();
      managedTextureArray.addAll(textures);
    } 
  }
  
  public static void setAssetManager(AssetManager manager) {
    assetManager = manager;
  }
  
  public static String getManagedStatus() {
    StringBuilder builder = new StringBuilder();
    builder.append("Managed textures/app: { ");
    for (Application app : managedTextures.keySet()) {
      builder.append(((Array)managedTextures.get(app)).size);
      builder.append(" ");
    } 
    builder.append("}");
    return builder.toString();
  }
  
  public static int getNumManagedTextures() {
    return ((Array)managedTextures.get(Gdx.app)).size;
  }
}
