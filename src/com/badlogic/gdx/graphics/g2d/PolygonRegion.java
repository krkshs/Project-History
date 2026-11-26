package com.badlogic.gdx.graphics.g2d;

public class PolygonRegion {
  final float[] textureCoords;
  
  final float[] vertices;
  
  final short[] triangles;
  
  final TextureRegion region;
  
  public PolygonRegion(TextureRegion region, float[] vertices, short[] triangles) {
    this.region = region;
    this.vertices = vertices;
    this.triangles = triangles;
    float[] textureCoords = this.textureCoords = new float[vertices.length];
    float u = region.u, v = region.v;
    float uvWidth = region.u2 - u;
    float uvHeight = region.v2 - v;
    int width = region.regionWidth;
    int height = region.regionHeight;
    for (int i = 0, n = vertices.length; i < n; i += 2) {
      textureCoords[i] = u + uvWidth * vertices[i] / (float)width;
      textureCoords[i + 1] = v + uvHeight * (1.0F - vertices[i + 1] / (float)height);
    } 
  }
  
  public float[] getVertices() {
    return this.vertices;
  }
  
  public short[] getTriangles() {
    return this.triangles;
  }
  
  public float[] getTextureCoords() {
    return this.textureCoords;
  }
  
  public TextureRegion getRegion() {
    return this.region;
  }
}
