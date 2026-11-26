package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class ParticleEffect implements Disposable, ResourceData.Configurable {
  private Array<ParticleController> controllers;
  
  private BoundingBox bounds;
  
  public ParticleEffect() {
    this.controllers = new Array<>(true, 3, ParticleController.class);
  }
  
  public ParticleEffect(ParticleEffect effect) {
    this.controllers = new Array<>(true, effect.controllers.size);
    for (int i = 0, n = effect.controllers.size; i < n; i++)
      this.controllers.add(effect.controllers.get(i).copy()); 
  }
  
  public ParticleEffect(ParticleController... emitters) {
    this.controllers = new Array<>(emitters);
  }
  
  public void init() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).init(); 
  }
  
  public void start() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).start(); 
  }
  
  public void end() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).end(); 
  }
  
  public void reset() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).reset(); 
  }
  
  public void update() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).update(); 
  }
  
  public void update(float deltaTime) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).update(deltaTime); 
  }
  
  public void draw() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).draw(); 
  }
  
  public boolean isComplete() {
    for (int i = 0, n = this.controllers.size; i < n; i++) {
      if (!this.controllers.get(i).isComplete())
        return false; 
    } 
    return true;
  }
  
  public void setTransform(Matrix4 transform) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).setTransform(transform); 
  }
  
  public void rotate(Quaternion rotation) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).rotate(rotation); 
  }
  
  public void rotate(Vector3 axis, float angle) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).rotate(axis, angle); 
  }
  
  public void translate(Vector3 translation) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).translate(translation); 
  }
  
  public void scale(float scaleX, float scaleY, float scaleZ) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).scale(scaleX, scaleY, scaleZ); 
  }
  
  public void scale(Vector3 scale) {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).scale(scale.x, scale.y, scale.z); 
  }
  
  public Array<ParticleController> getControllers() {
    return this.controllers;
  }
  
  public ParticleController findController(String name) {
    for (int i = 0, n = this.controllers.size; i < n; i++) {
      ParticleController emitter = this.controllers.get(i);
      if (emitter.name.equals(name))
        return emitter; 
    } 
    return null;
  }
  
  public void dispose() {
    for (int i = 0, n = this.controllers.size; i < n; i++)
      this.controllers.get(i).dispose(); 
  }
  
  public BoundingBox getBoundingBox() {
    if (this.bounds == null)
      this.bounds = new BoundingBox(); 
    BoundingBox bounds = this.bounds;
    bounds.inf();
    for (Array.ArrayIterator<ParticleController> arrayIterator = this.controllers.iterator(); arrayIterator.hasNext(); ) {
      ParticleController emitter = arrayIterator.next();
      bounds.ext(emitter.getBoundingBox());
    } 
    return bounds;
  }
  
  public void setBatch(Array<ParticleBatch<?>> batches) {
    for (Array.ArrayIterator<ParticleController> arrayIterator = this.controllers.iterator(); arrayIterator.hasNext(); ) {
      ParticleController controller = arrayIterator.next();
      for (Array.ArrayIterator<ParticleBatch<?>> arrayIterator1 = batches.iterator(); arrayIterator1.hasNext(); ) {
        ParticleBatch<?> batch = arrayIterator1.next();
        if (controller.renderer.setBatch(batch))
          break; 
      } 
    } 
  }
  
  public ParticleEffect copy() {
    return new ParticleEffect(this);
  }
  
  public void save(AssetManager assetManager, ResourceData data) {
    for (Array.ArrayIterator<ParticleController> arrayIterator = this.controllers.iterator(); arrayIterator.hasNext(); ) {
      ParticleController controller = arrayIterator.next();
      controller.save(assetManager, data);
    } 
  }
  
  public void load(AssetManager assetManager, ResourceData data) {
    int i = 0;
    for (Array.ArrayIterator<ParticleController> arrayIterator = this.controllers.iterator(); arrayIterator.hasNext(); ) {
      ParticleController controller = arrayIterator.next();
      controller.load(assetManager, data);
    } 
  }
}
