package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.SnapshotArray;

public class Group extends Actor implements Cullable {
  private static final Vector2 tmp = new Vector2();
  
  final SnapshotArray<Actor> children = new SnapshotArray<>(true, 4, Actor.class);
  
  private final Affine2 worldTransform = new Affine2();
  
  private final Matrix4 computedTransform = new Matrix4();
  
  private final Matrix4 oldTransform = new Matrix4();
  
  boolean transform = true;
  
  @Null
  private Rectangle cullingArea;
  
  public void act(float delta) {
    super.act(delta);
    Actor[] actors = this.children.begin();
    for (int i = 0, n = this.children.size; i < n; i++)
      actors[i].act(delta); 
    this.children.end();
  }
  
  public void draw(Batch batch, float parentAlpha) {
    if (this.transform)
      applyTransform(batch, computeTransform()); 
    drawChildren(batch, parentAlpha);
    if (this.transform)
      resetTransform(batch); 
  }
  
  protected void drawChildren(Batch batch, float parentAlpha) {
    parentAlpha *= this.color.a;
    SnapshotArray<Actor> children = this.children;
    Actor[] actors = children.begin();
    Rectangle cullingArea = this.cullingArea;
    if (cullingArea != null) {
      float cullLeft = cullingArea.x;
      float cullRight = cullLeft + cullingArea.width;
      float cullBottom = cullingArea.y;
      float cullTop = cullBottom + cullingArea.height;
      if (this.transform) {
        for (int i = 0, n = children.size; i < n; i++) {
          Actor child = actors[i];
          if (child.isVisible()) {
            float cx = child.x, cy = child.y;
            if (cx <= cullRight && cy <= cullTop && cx + child.width >= cullLeft && cy + child.height >= cullBottom)
              child.draw(batch, parentAlpha); 
          } 
        } 
      } else {
        float offsetX = this.x, offsetY = this.y;
        this.x = 0.0F;
        this.y = 0.0F;
        for (int i = 0, n = children.size; i < n; i++) {
          Actor child = actors[i];
          if (child.isVisible()) {
            float cx = child.x, cy = child.y;
            if (cx <= cullRight && cy <= cullTop && cx + child.width >= cullLeft && cy + child.height >= cullBottom) {
              child.x = cx + offsetX;
              child.y = cy + offsetY;
              child.draw(batch, parentAlpha);
              child.x = cx;
              child.y = cy;
            } 
          } 
        } 
        this.x = offsetX;
        this.y = offsetY;
      } 
    } else if (this.transform) {
      for (int i = 0, n = children.size; i < n; i++) {
        Actor child = actors[i];
        if (child.isVisible())
          child.draw(batch, parentAlpha); 
      } 
    } else {
      float offsetX = this.x, offsetY = this.y;
      this.x = 0.0F;
      this.y = 0.0F;
      for (int i = 0, n = children.size; i < n; i++) {
        Actor child = actors[i];
        if (child.isVisible()) {
          float cx = child.x, cy = child.y;
          child.x = cx + offsetX;
          child.y = cy + offsetY;
          child.draw(batch, parentAlpha);
          child.x = cx;
          child.y = cy;
        } 
      } 
      this.x = offsetX;
      this.y = offsetY;
    } 
    children.end();
  }
  
  public void drawDebug(ShapeRenderer shapes) {
    drawDebugBounds(shapes);
    if (this.transform)
      applyTransform(shapes, computeTransform()); 
    drawDebugChildren(shapes);
    if (this.transform)
      resetTransform(shapes); 
  }
  
  protected void drawDebugChildren(ShapeRenderer shapes) {
    SnapshotArray<Actor> children = this.children;
    Actor[] actors = children.begin();
    if (this.transform) {
      for (int i = 0, n = children.size; i < n; i++) {
        Actor child = actors[i];
        if (child.isVisible() && (
          child.getDebug() || child instanceof Group))
          child.drawDebug(shapes); 
      } 
      shapes.flush();
    } else {
      float offsetX = this.x, offsetY = this.y;
      this.x = 0.0F;
      this.y = 0.0F;
      for (int i = 0, n = children.size; i < n; i++) {
        Actor child = actors[i];
        if (child.isVisible() && (
          child.getDebug() || child instanceof Group)) {
          float cx = child.x, cy = child.y;
          child.x = cx + offsetX;
          child.y = cy + offsetY;
          child.drawDebug(shapes);
          child.x = cx;
          child.y = cy;
        } 
      } 
      this.x = offsetX;
      this.y = offsetY;
    } 
    children.end();
  }
  
  protected Matrix4 computeTransform() {
    Affine2 worldTransform = this.worldTransform;
    float originX = this.originX, originY = this.originY;
    worldTransform.setToTrnRotScl(this.x + originX, this.y + originY, this.rotation, this.scaleX, this.scaleY);
    if (originX != 0.0F || originY != 0.0F)
      worldTransform.translate(-originX, -originY); 
    Group parentGroup = this.parent;
    while (parentGroup != null && 
      !parentGroup.transform)
      parentGroup = parentGroup.parent; 
    if (parentGroup != null)
      worldTransform.preMul(parentGroup.worldTransform); 
    this.computedTransform.set(worldTransform);
    return this.computedTransform;
  }
  
  protected void applyTransform(Batch batch, Matrix4 transform) {
    this.oldTransform.set(batch.getTransformMatrix());
    batch.setTransformMatrix(transform);
  }
  
  protected void resetTransform(Batch batch) {
    batch.setTransformMatrix(this.oldTransform);
  }
  
  protected void applyTransform(ShapeRenderer shapes, Matrix4 transform) {
    this.oldTransform.set(shapes.getTransformMatrix());
    shapes.setTransformMatrix(transform);
    shapes.flush();
  }
  
  protected void resetTransform(ShapeRenderer shapes) {
    shapes.setTransformMatrix(this.oldTransform);
  }
  
  public void setCullingArea(@Null Rectangle cullingArea) {
    this.cullingArea = cullingArea;
  }
  
  @Null
  public Rectangle getCullingArea() {
    return this.cullingArea;
  }
  
  @Null
  public Actor hit(float x, float y, boolean touchable) {
    if (touchable && getTouchable() == Touchable.disabled)
      return null; 
    if (!isVisible())
      return null; 
    Vector2 point = tmp;
    Actor[] childrenArray = (Actor[])this.children.items;
    for (int i = this.children.size - 1; i >= 0; i--) {
      Actor child = childrenArray[i];
      child.parentToLocalCoordinates(point.set(x, y));
      Actor hit = child.hit(point.x, point.y, touchable);
      if (hit != null)
        return hit; 
    } 
    return super.hit(x, y, touchable);
  }
  
  protected void childrenChanged() {}
  
  public void addActor(Actor actor) {
    if (actor.parent != null) {
      if (actor.parent == this)
        return; 
      actor.parent.removeActor(actor, false);
    } 
    this.children.add(actor);
    actor.setParent(this);
    actor.setStage(getStage());
    childrenChanged();
  }
  
  public void addActorAt(int index, Actor actor) {
    if (actor.parent != null) {
      if (actor.parent == this)
        return; 
      actor.parent.removeActor(actor, false);
    } 
    if (index >= this.children.size) {
      this.children.add(actor);
    } else
      this.children.insert(index, actor); 
    actor.setParent(this);
    actor.setStage(getStage());
    childrenChanged();
  }
  
  public void addActorBefore(Actor actorBefore, Actor actor) {
    if (actor.parent != null) {
      if (actor.parent == this)
        return; 
      actor.parent.removeActor(actor, false);
    } 
    int index = this.children.indexOf(actorBefore, true);
    this.children.insert(index, actor);
    actor.setParent(this);
    actor.setStage(getStage());
    childrenChanged();
  }
  
  public void addActorAfter(Actor actorAfter, Actor actor) {
    if (actor.parent != null) {
      if (actor.parent == this)
        return; 
      actor.parent.removeActor(actor, false);
    } 
    int index = this.children.indexOf(actorAfter, true);
    if (index == this.children.size || index == -1) {
      this.children.add(actor);
    } else
      this.children.insert(index + 1, actor); 
    actor.setParent(this);
    actor.setStage(getStage());
    childrenChanged();
  }
  
  public boolean removeActor(Actor actor) {
    return removeActor(actor, true);
  }
  
  public boolean removeActor(Actor actor, boolean unfocus) {
    int index = this.children.indexOf(actor, true);
    if (index == -1)
      return false; 
    removeActorAt(index, unfocus);
    return true;
  }
  
  public Actor removeActorAt(int index, boolean unfocus) {
    Actor actor = this.children.removeIndex(index);
    if (unfocus) {
      Stage stage = getStage();
      if (stage != null)
        stage.unfocus(actor); 
    } 
    actor.setParent(null);
    actor.setStage(null);
    childrenChanged();
    return actor;
  }
  
  public void clearChildren() {
    clearChildren(true);
  }
  
  public void clearChildren(boolean unfocus) {
    Actor[] actors = this.children.begin();
    for (int i = 0, n = this.children.size; i < n; i++) {
      Actor child = actors[i];
      if (unfocus) {
        Stage stage = getStage();
        if (stage != null)
          stage.unfocus(child); 
      } 
      child.setStage(null);
      child.setParent(null);
    } 
    this.children.end();
    this.children.clear();
    childrenChanged();
  }
  
  public void clear() {
    super.clear();
    clearChildren(true);
  }
  
  public void clear(boolean unfocus) {
    super.clear();
    clearChildren(unfocus);
  }
  
  @Null
  public <T extends Actor> T findActor(String name) {
    Array<Actor> children = this.children;
    for (int j = 0, k = children.size; j < k; j++) {
      if (name.equals(children.get(j).getName()))
        return (T)children.get(j); 
    } 
    for (int i = 0, n = children.size; i < n; i++) {
      Actor child = children.get(i);
      if (child instanceof Group) {
        Actor actor = ((Group)child).<Actor>findActor(name);
        if (actor != null)
          return (T)actor; 
      } 
    } 
    return null;
  }
  
  protected void setStage(Stage stage) {
    super.setStage(stage);
    Actor[] childrenArray = (Actor[])this.children.items;
    for (int i = 0, n = this.children.size; i < n; i++)
      childrenArray[i].setStage(stage); 
  }
  
  public boolean swapActor(int first, int second) {
    int maxIndex = this.children.size;
    if (first < 0 || first >= maxIndex)
      return false; 
    if (second < 0 || second >= maxIndex)
      return false; 
    this.children.swap(first, second);
    return true;
  }
  
  public boolean swapActor(Actor first, Actor second) {
    int firstIndex = this.children.indexOf(first, true);
    int secondIndex = this.children.indexOf(second, true);
    if (firstIndex == -1 || secondIndex == -1)
      return false; 
    this.children.swap(firstIndex, secondIndex);
    return true;
  }
  
  public Actor getChild(int index) {
    return this.children.get(index);
  }
  
  public SnapshotArray<Actor> getChildren() {
    return this.children;
  }
  
  public boolean hasChildren() {
    return (this.children.size > 0);
  }
  
  public void setTransform(boolean transform) {
    this.transform = transform;
  }
  
  public boolean isTransform() {
    return this.transform;
  }
  
  public Vector2 localToDescendantCoordinates(Actor descendant, Vector2 localCoords) {
    Group parent = descendant.parent;
    if (parent == null)
      throw new IllegalArgumentException("Child is not a descendant: " + descendant); 
    if (parent != this)
      localToDescendantCoordinates(parent, localCoords); 
    descendant.parentToLocalCoordinates(localCoords);
    return localCoords;
  }
  
  public void setDebug(boolean enabled, boolean recursively) {
    setDebug(enabled);
    if (recursively)
      for (Array.ArrayIterator<Actor> arrayIterator = this.children.iterator(); arrayIterator.hasNext(); ) {
        Actor child = arrayIterator.next();
        if (child instanceof Group) {
          ((Group)child).setDebug(enabled, recursively);
          continue;
        } 
        child.setDebug(enabled);
      }  
  }
  
  public Group debugAll() {
    setDebug(true, true);
    return this;
  }
  
  public String toString() {
    StringBuilder buffer = new StringBuilder(128);
    toString(buffer, 1);
    buffer.setLength(buffer.length() - 1);
    return buffer.toString();
  }
  
  void toString(StringBuilder buffer, int indent) {
    buffer.append(super.toString());
    buffer.append('\n');
    Actor[] actors = this.children.begin();
    for (int i = 0, n = this.children.size; i < n; i++) {
      for (int ii = 0; ii < indent; ii++)
        buffer.append("|  "); 
      Actor actor = actors[i];
      if (actor instanceof Group) {
        ((Group)actor).toString(buffer, indent + 1);
      } else {
        buffer.append(actor);
        buffer.append('\n');
      } 
    } 
    this.children.end();
  }
}
