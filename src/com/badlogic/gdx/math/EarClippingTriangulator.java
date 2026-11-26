package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

public class EarClippingTriangulator {
  private static final int CONCAVE = -1;
  
  private static final int CONVEX = 1;
  
  private final ShortArray indicesArray = new ShortArray();
  
  private short[] indices;
  
  private float[] vertices;
  
  private int vertexCount;
  
  private final IntArray vertexTypes = new IntArray();
  
  private final ShortArray triangles = new ShortArray();
  
  public ShortArray computeTriangles(FloatArray vertices) {
    return computeTriangles(vertices.items, 0, vertices.size);
  }
  
  public ShortArray computeTriangles(float[] vertices) {
    return computeTriangles(vertices, 0, vertices.length);
  }
  
  public ShortArray computeTriangles(float[] vertices, int offset, int count) {
    this.vertices = vertices;
    int vertexCount = this.vertexCount = count / 2;
    int vertexOffset = offset / 2;
    ShortArray indicesArray = this.indicesArray;
    indicesArray.clear();
    indicesArray.ensureCapacity(vertexCount);
    indicesArray.size = vertexCount;
    short[] indices = this.indices = indicesArray.items;
    if (GeometryUtils.isClockwise(vertices, offset, count)) {
      for (short s = 0; s < vertexCount; s = (short)(s + 1))
        indices[s] = (short)(vertexOffset + s); 
    } else
      for (int j = 0, k = vertexCount - 1; j < vertexCount; j++)
        indices[j] = (short)(vertexOffset + k - j);  
    IntArray vertexTypes = this.vertexTypes;
    vertexTypes.clear();
    vertexTypes.ensureCapacity(vertexCount);
    for (int i = 0, n = vertexCount; i < n; i++)
      vertexTypes.add(classifyVertex(i)); 
    ShortArray triangles = this.triangles;
    triangles.clear();
    triangles.ensureCapacity(Math.max(0, vertexCount - 2) * 3);
    triangulate();
    return triangles;
  }
  
  private void triangulate() {
    int[] vertexTypes = this.vertexTypes.items;
    while (this.vertexCount > 3) {
      int earTipIndex = findEarTip();
      cutEarTip(earTipIndex);
      int previousIndex = previousIndex(earTipIndex);
      int nextIndex = (earTipIndex == this.vertexCount) ? 0 : earTipIndex;
      vertexTypes[previousIndex] = classifyVertex(previousIndex);
      vertexTypes[nextIndex] = classifyVertex(nextIndex);
    } 
    if (this.vertexCount == 3) {
      ShortArray triangles = this.triangles;
      short[] indices = this.indices;
      triangles.add(indices[0]);
      triangles.add(indices[1]);
      triangles.add(indices[2]);
    } 
  }
  
  private int classifyVertex(int index) {
    short[] indices = this.indices;
    int previous = indices[previousIndex(index)] * 2;
    int current = indices[index] * 2;
    int next = indices[nextIndex(index)] * 2;
    float[] vertices = this.vertices;
    return computeSpannedAreaSign(vertices[previous], vertices[previous + 1], vertices[current], vertices[current + 1], vertices[next], vertices[next + 1]);
  }
  
  private int findEarTip() {
    int vertexCount = this.vertexCount;
    for (int i = 0; i < vertexCount; i++) {
      if (isEarTip(i))
        return i; 
    } 
    int[] vertexTypes = this.vertexTypes.items;
    for (int j = 0; j < vertexCount; j++) {
      if (vertexTypes[j] != -1)
        return j; 
    } 
    return 0;
  }
  
  private boolean isEarTip(int earTipIndex) {
    int[] vertexTypes = this.vertexTypes.items;
    if (vertexTypes[earTipIndex] == -1)
      return false; 
    int previousIndex = previousIndex(earTipIndex);
    int nextIndex = nextIndex(earTipIndex);
    short[] indices = this.indices;
    int p1 = indices[previousIndex] * 2;
    int p2 = indices[earTipIndex] * 2;
    int p3 = indices[nextIndex] * 2;
    float[] vertices = this.vertices;
    float p1x = vertices[p1], p1y = vertices[p1 + 1];
    float p2x = vertices[p2], p2y = vertices[p2 + 1];
    float p3x = vertices[p3], p3y = vertices[p3 + 1];
    for (int i = nextIndex(nextIndex); i != previousIndex; i = nextIndex(i)) {
      if (vertexTypes[i] != 1) {
        int v = indices[i] * 2;
        float vx = vertices[v];
        float vy = vertices[v + 1];
        if (computeSpannedAreaSign(p3x, p3y, p1x, p1y, vx, vy) >= 0 && 
          computeSpannedAreaSign(p1x, p1y, p2x, p2y, vx, vy) >= 0 && 
          computeSpannedAreaSign(p2x, p2y, p3x, p3y, vx, vy) >= 0)
          return false; 
      } 
    } 
    return true;
  }
  
  private void cutEarTip(int earTipIndex) {
    short[] indices = this.indices;
    ShortArray triangles = this.triangles;
    triangles.add(indices[previousIndex(earTipIndex)]);
    triangles.add(indices[earTipIndex]);
    triangles.add(indices[nextIndex(earTipIndex)]);
    this.indicesArray.removeIndex(earTipIndex);
    this.vertexTypes.removeIndex(earTipIndex);
    this.vertexCount--;
  }
  
  private int previousIndex(int index) {
    return ((index == 0) ? this.vertexCount : index) - 1;
  }
  
  private int nextIndex(int index) {
    return (index + 1) % this.vertexCount;
  }
  
  private static int computeSpannedAreaSign(float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
    float area = p1x * (p3y - p2y);
    area += p2x * (p1y - p3y);
    area += p3x * (p2y - p1y);
    return (int)Math.signum(area);
  }
}
