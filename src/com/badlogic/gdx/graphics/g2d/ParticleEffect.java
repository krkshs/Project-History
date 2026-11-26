package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

public class ParticleEffect implements Disposable {
  private final Array<ParticleEmitter> emitters;
  
  private BoundingBox bounds;
  
  private boolean ownsTexture;
  
  protected float xSizeScale = 1.0F;
  
  protected float ySizeScale = 1.0F;
  
  protected float motionScale = 1.0F;
  
  public ParticleEffect() {
    this.emitters = new Array<>(8);
  }
  
  public ParticleEffect(ParticleEffect effect) {
    this.emitters = new Array<>(true, effect.emitters.size);
    for (int i = 0, n = effect.emitters.size; i < n; i++)
      this.emitters.add(newEmitter(effect.emitters.get(i))); 
  }
  
  public void start() {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).start(); 
  }
  
  public void reset() {
    reset(true);
  }
  
  public void reset(boolean resetScaling) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).reset(); 
    if (resetScaling && (this.xSizeScale != 1.0F || this.ySizeScale != 1.0F || this.motionScale != 1.0F)) {
      scaleEffect(1.0F / this.xSizeScale, 1.0F / this.ySizeScale, 1.0F / this.motionScale);
      this.xSizeScale = this.ySizeScale = this.motionScale = 1.0F;
    } 
  }
  
  public void update(float delta) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).update(delta); 
  }
  
  public void draw(Batch spriteBatch) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).draw(spriteBatch); 
  }
  
  public void draw(Batch spriteBatch, float delta) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).draw(spriteBatch, delta); 
  }
  
  public void allowCompletion() {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).allowCompletion(); 
  }
  
  public boolean isComplete() {
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      if (!emitter.isComplete())
        return false; 
    } 
    return true;
  }
  
  public void setDuration(int duration) {
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      emitter.setContinuous(false);
      emitter.duration = (float)duration;
      emitter.durationTimer = 0.0F;
    } 
  }
  
  public void setPosition(float x, float y) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).setPosition(x, y); 
  }
  
  public void setFlip(boolean flipX, boolean flipY) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).setFlip(flipX, flipY); 
  }
  
  public void flipY() {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).flipY(); 
  }
  
  public Array<ParticleEmitter> getEmitters() {
    return this.emitters;
  }
  
  public ParticleEmitter findEmitter(String name) {
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      if (emitter.getName().equals(name))
        return emitter; 
    } 
    return null;
  }
  
  public void preAllocateParticles() {
    for (Array.ArrayIterator<ParticleEmitter> arrayIterator = this.emitters.iterator(); arrayIterator.hasNext(); ) {
      ParticleEmitter emitter = arrayIterator.next();
      emitter.preAllocateParticles();
    } 
  }
  
  public void save(Writer output) throws IOException {
    int index = 0;
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      if (index++ > 0)
        output.write("\n"); 
      emitter.save(output);
    } 
  }
  
  public void load(FileHandle effectFile, FileHandle imagesDir) {
    loadEmitters(effectFile);
    loadEmitterImages(imagesDir);
  }
  
  public void load(FileHandle effectFile, TextureAtlas atlas) {
    load(effectFile, atlas, null);
  }
  
  public void load(FileHandle effectFile, TextureAtlas atlas, String atlasPrefix) {
    loadEmitters(effectFile);
    loadEmitterImages(atlas, atlasPrefix);
  }
  
  public void loadEmitters(FileHandle effectFile) {
    InputStream input = effectFile.read();
    this.emitters.clear();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(input), 512);
      do {
        ParticleEmitter emitter = newEmitter(reader);
        this.emitters.add(emitter);
      } while (reader.readLine() != null);
    } catch (IOException ex) {
      throw new GdxRuntimeException("Error loading effect: " + effectFile, ex);
    } finally {
      StreamUtils.closeQuietly(reader);
    } 
  }
  
  public void loadEmitterImages(TextureAtlas atlas) {
    loadEmitterImages(atlas, null);
  }
  
  public void loadEmitterImages(TextureAtlas atlas, String atlasPrefix) {
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      if ((emitter.getImagePaths()).size != 0) {
        Array<Sprite> sprites = new Array<>();
        for (Array.ArrayIterator<String> arrayIterator = emitter.getImagePaths().iterator(); arrayIterator.hasNext(); ) {
          String imagePath = arrayIterator.next();
          String imageName = new File(imagePath.replace('\\', '/')).getName();
          int lastDotIndex = imageName.lastIndexOf('.');
          if (lastDotIndex != -1)
            imageName = imageName.substring(0, lastDotIndex); 
          if (atlasPrefix != null)
            imageName = atlasPrefix + imageName; 
          Sprite sprite = atlas.createSprite(imageName);
          if (sprite == null)
            throw new IllegalArgumentException("SpriteSheet missing image: " + imageName); 
          sprites.add(sprite);
        } 
        emitter.setSprites(sprites);
      } 
    } 
  }
  
  public void loadEmitterImages(FileHandle imagesDir) {
    this.ownsTexture = true;
    ObjectMap<String, Sprite> loadedSprites = new ObjectMap<>(this.emitters.size);
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      if ((emitter.getImagePaths()).size != 0) {
        Array<Sprite> sprites = new Array<>();
        for (Array.ArrayIterator<String> arrayIterator = emitter.getImagePaths().iterator(); arrayIterator.hasNext(); ) {
          String imagePath = arrayIterator.next();
          String imageName = new File(imagePath.replace('\\', '/')).getName();
          Sprite sprite = loadedSprites.<String>get(imageName);
          if (sprite == null) {
            sprite = new Sprite(loadTexture(imagesDir.child(imageName)));
            loadedSprites.put(imageName, sprite);
          } 
          sprites.add(sprite);
        } 
        emitter.setSprites(sprites);
      } 
    } 
  }
  
  protected ParticleEmitter newEmitter(BufferedReader reader) throws IOException {
    return new ParticleEmitter(reader);
  }
  
  protected ParticleEmitter newEmitter(ParticleEmitter emitter) {
    return new ParticleEmitter(emitter);
  }
  
  protected Texture loadTexture(FileHandle file) {
    return new Texture(file, false);
  }
  
  public void dispose() {
    if (!this.ownsTexture)
      return; 
    for (int i = 0, n = this.emitters.size; i < n; i++) {
      ParticleEmitter emitter = this.emitters.get(i);
      for (Array.ArrayIterator<Sprite> arrayIterator = emitter.getSprites().iterator(); arrayIterator.hasNext(); ) {
        Sprite sprite = arrayIterator.next();
        sprite.getTexture().dispose();
      } 
    } 
  }
  
  public BoundingBox getBoundingBox() {
    if (this.bounds == null)
      this.bounds = new BoundingBox(); 
    BoundingBox bounds = this.bounds;
    bounds.inf();
    for (Array.ArrayIterator<ParticleEmitter> arrayIterator = this.emitters.iterator(); arrayIterator.hasNext(); ) {
      ParticleEmitter emitter = arrayIterator.next();
      bounds.ext(emitter.getBoundingBox());
    } 
    return bounds;
  }
  
  public void scaleEffect(float scaleFactor) {
    scaleEffect(scaleFactor, scaleFactor, scaleFactor);
  }
  
  public void scaleEffect(float scaleFactor, float motionScaleFactor) {
    scaleEffect(scaleFactor, scaleFactor, motionScaleFactor);
  }
  
  public void scaleEffect(float xSizeScaleFactor, float ySizeScaleFactor, float motionScaleFactor) {
    this.xSizeScale *= xSizeScaleFactor;
    this.ySizeScale *= ySizeScaleFactor;
    this.motionScale *= motionScaleFactor;
    for (Array.ArrayIterator<ParticleEmitter> arrayIterator = this.emitters.iterator(); arrayIterator.hasNext(); ) {
      ParticleEmitter particleEmitter = arrayIterator.next();
      particleEmitter.scaleSize(xSizeScaleFactor, ySizeScaleFactor);
      particleEmitter.scaleMotion(motionScaleFactor);
    } 
  }
  
  public void setEmittersCleanUpBlendFunction(boolean cleanUpBlendFunction) {
    for (int i = 0, n = this.emitters.size; i < n; i++)
      this.emitters.get(i).setCleansUpBlendFunction(cleanUpBlendFunction); 
  }
}
