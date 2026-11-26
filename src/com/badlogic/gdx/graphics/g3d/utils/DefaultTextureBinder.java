package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.IntBuffer;

public final class DefaultTextureBinder implements TextureBinder {
  public static final int ROUNDROBIN = 0;
  
  public static final int LRU = 1;
  
  public static final int MAX_GLES_UNITS = 32;
  
  private final int offset;
  
  private final int count;
  
  private final GLTexture[] textures;
  
  private int[] unitsLRU;
  
  private final int method;
  
  private boolean reused;
  
  private int reuseCount = 0;
  
  private int bindCount = 0;
  
  public DefaultTextureBinder(int method) {
    this(method, 0);
  }
  
  public DefaultTextureBinder(int method, int offset) {
    this(method, offset, -1);
  }
  
  public DefaultTextureBinder(int method, int offset, int count) {
    int max = Math.min(getMaxTextureUnits(), 32);
    if (count < 0)
      count = max - offset; 
    if (offset < 0 || count < 0 || offset + count > max)
      throw new GdxRuntimeException("Illegal arguments"); 
    this.method = method;
    this.offset = offset;
    this.count = count;
    this.textures = new GLTexture[count];
    this.unitsLRU = (method == 1) ? new int[count] : null;
  }
  
  private static int getMaxTextureUnits() {
    IntBuffer buffer = BufferUtils.newIntBuffer(16);
    Gdx.gl.glGetIntegerv(34930, buffer);
    return buffer.get(0);
  }
  
  public void begin() {
    for (int i = 0; i < this.count; i++) {
      this.textures[i] = null;
      if (this.unitsLRU != null)
        this.unitsLRU[i] = i; 
    } 
  }
  
  public void end() {
    Gdx.gl.glActiveTexture(33984);
  }
  
  public final int bind(TextureDescriptor textureDesc) {
    return bindTexture(textureDesc, false);
  }
  
  private final TextureDescriptor tempDesc = new TextureDescriptor();
  
  public final int bind(GLTexture texture) {
    this.tempDesc.set(texture, null, null, null, null);
    return bindTexture(this.tempDesc, false);
  }
  
  private final int bindTexture(TextureDescriptor textureDesc, boolean rebind) {
    int idx, result;
    T t = textureDesc.texture;
    this.reused = false;
    switch (this.method) {
      case 0:
        result = this.offset + (idx = bindTextureRoundRobin((GLTexture)t));
        break;
      case 1:
        result = this.offset + (idx = bindTextureLRU((GLTexture)t));
        break;
      default:
        return -1;
    } 
    if (this.reused) {
      this.reuseCount++;
      if (rebind) {
        t.bind(result);
      } else
        Gdx.gl.glActiveTexture(33984 + result); 
    } else
      this.bindCount++; 
    t.unsafeSetWrap(textureDesc.uWrap, textureDesc.vWrap);
    t.unsafeSetFilter(textureDesc.minFilter, textureDesc.magFilter);
    return result;
  }
  
  private int currentTexture = 0;
  
  private final int bindTextureRoundRobin(GLTexture texture) {
    for (int i = 0; i < this.count; i++) {
      int idx = (this.currentTexture + i) % this.count;
      if (this.textures[idx] == texture) {
        this.reused = true;
        return idx;
      } 
    } 
    this.currentTexture = (this.currentTexture + 1) % this.count;
    this.textures[this.currentTexture] = texture;
    texture.bind(this.offset + this.currentTexture);
    return this.currentTexture;
  }
  
  private final int bindTextureLRU(GLTexture texture) {
    int i;
    for (i = 0; i < this.count; i++) {
      int j = this.unitsLRU[i];
      if (this.textures[j] == texture) {
        this.reused = true;
        break;
      } 
      if (this.textures[j] == null)
        break; 
    } 
    if (i >= this.count)
      i = this.count - 1; 
    int idx = this.unitsLRU[i];
    while (i > 0) {
      this.unitsLRU[i] = this.unitsLRU[i - 1];
      i--;
    } 
    this.unitsLRU[0] = idx;
    if (!this.reused) {
      this.textures[idx] = texture;
      texture.bind(this.offset + idx);
    } 
    return idx;
  }
  
  public final int getBindCount() {
    return this.bindCount;
  }
  
  public final int getReuseCount() {
    return this.reuseCount;
  }
  
  public final void resetCounts() {
    this.bindCount = this.reuseCount = 0;
  }
}
