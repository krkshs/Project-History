package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;

public class RepeatablePolygonSprite {
  private TextureRegion region;
  
  private float density;
  
  private boolean dirty = true;
  
  private Array<float[]> parts = new Array<>();
  
  private Array<float[]> vertices = new Array<>();
  
  private Array<short[]> indices = new Array<>();
  
  private int cols;
  
  private int rows;
  
  private float gridWidth;
  
  private float gridHeight;
  
  public float x = 0.0F;
  
  public float y = 0.0F;
  
  private Color color = Color.WHITE;
  
  private Vector2 offset = new Vector2();
  
  public void setPolygon(TextureRegion region, float[] vertices) {
    setPolygon(region, vertices, -1.0F);
  }
  
  public void setPolygon(TextureRegion region, float[] vertices, float density) {
    this.region = region;
    vertices = offset(vertices);
    Polygon polygon = new Polygon(vertices);
    Polygon tmpPoly = new Polygon();
    Polygon intersectionPoly = new Polygon();
    EarClippingTriangulator triangulator = new EarClippingTriangulator();
    Rectangle boundRect = polygon.getBoundingRectangle();
    if (density == -1.0F)
      density = boundRect.getWidth() / (float)region.getRegionWidth(); 
    float regionAspectRatio = (float)region.getRegionHeight() / (float)region.getRegionWidth();
    this.cols = (int)Math.ceil((double)density);
    this.gridWidth = boundRect.getWidth() / density;
    this.gridHeight = regionAspectRatio * this.gridWidth;
    this.rows = (int)Math.ceil((double)(boundRect.getHeight() / this.gridHeight));
    for (int col = 0; col < this.cols; col++) {
      for (int row = 0; row < this.rows; row++) {
        float[] verts = new float[8];
        int idx = 0;
        verts[idx++] = (float)col * this.gridWidth;
        verts[idx++] = (float)row * this.gridHeight;
        verts[idx++] = (float)col * this.gridWidth;
        verts[idx++] = (float)(row + 1) * this.gridHeight;
        verts[idx++] = (float)(col + 1) * this.gridWidth;
        verts[idx++] = (float)(row + 1) * this.gridHeight;
        verts[idx++] = (float)(col + 1) * this.gridWidth;
        verts[idx] = (float)row * this.gridHeight;
        tmpPoly.setVertices(verts);
        Intersector.intersectPolygons(polygon, tmpPoly, intersectionPoly);
        verts = intersectionPoly.getVertices();
        if (verts.length > 0) {
          this.parts.add(snapToGrid(verts));
          ShortArray arr = triangulator.computeTriangles(verts);
          this.indices.add(arr.toArray());
        } else
          this.parts.add(null); 
      } 
    } 
    buildVertices();
  }
  
  private float[] snapToGrid(float[] vertices) {
    for (int i = 0; i < vertices.length; i += 2) {
      float numX = vertices[i] / this.gridWidth % 1.0F;
      float numY = vertices[i + 1] / this.gridHeight % 1.0F;
      if (numX > 0.99F || numX < 0.01F)
        vertices[i] = this.gridWidth * (float)Math.round(vertices[i] / this.gridWidth); 
      if (numY > 0.99F || numY < 0.01F)
        vertices[i + 1] = this.gridHeight * (float)Math.round(vertices[i + 1] / this.gridHeight); 
    } 
    return vertices;
  }
  
  private float[] offset(float[] vertices) {
    this.offset.set(vertices[0], vertices[1]);
    for (int j = 0; j < vertices.length - 1; j += 2) {
      if (this.offset.x > vertices[j])
        this.offset.x = vertices[j]; 
      if (this.offset.y > vertices[j + 1])
        this.offset.y = vertices[j + 1]; 
    } 
    for (int i = 0; i < vertices.length; i += 2) {
      vertices[i] = vertices[i] - this.offset.x;
      vertices[i + 1] = vertices[i + 1] - this.offset.y;
    } 
    return vertices;
  }
  
  private void buildVertices() {
    this.vertices.clear();
    for (int i = 0; i < this.parts.size; i++) {
      float[] verts = this.parts.get(i);
      if (verts != null) {
        float[] fullVerts = new float[5 * verts.length / 2];
        int idx = 0;
        int col = i / this.rows;
        int row = i % this.rows;
        for (int j = 0; j < verts.length; j += 2) {
          fullVerts[idx++] = verts[j] + this.offset.x + this.x;
          fullVerts[idx++] = verts[j + 1] + this.offset.y + this.y;
          fullVerts[idx++] = this.color.toFloatBits();
          float u = verts[j] % this.gridWidth / this.gridWidth;
          float v = verts[j + 1] % this.gridHeight / this.gridHeight;
          if (verts[j] == (float)col * this.gridWidth)
            u = 0.0F; 
          if (verts[j] == (float)(col + 1) * this.gridWidth)
            u = 1.0F; 
          if (verts[j + 1] == (float)row * this.gridHeight)
            v = 0.0F; 
          if (verts[j + 1] == (float)(row + 1) * this.gridHeight)
            v = 1.0F; 
          u = this.region.getU() + (this.region.getU2() - this.region.getU()) * u;
          v = this.region.getV() + (this.region.getV2() - this.region.getV()) * v;
          fullVerts[idx++] = u;
          fullVerts[idx++] = v;
        } 
        this.vertices.add(fullVerts);
      } 
    } 
    this.dirty = false;
  }
  
  public void draw(PolygonSpriteBatch batch) {
    if (this.dirty)
      buildVertices(); 
    for (int i = 0; i < this.vertices.size; i++)
      batch.draw(this.region.getTexture(), this.vertices.get(i), 0, ((float[])this.vertices.get(i)).length, this.indices.get(i), 0, ((short[])this.indices.get(i)).length); 
  }
  
  public void setColor(Color color) {
    this.color = color;
    this.dirty = true;
  }
  
  public void setPosition(float x, float y) {
    this.x = x;
    this.y = y;
    this.dirty = true;
  }
}
