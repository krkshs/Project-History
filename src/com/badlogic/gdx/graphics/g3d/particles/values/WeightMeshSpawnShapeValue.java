package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.CumulativeDistribution;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class WeightMeshSpawnShapeValue extends MeshSpawnShapeValue {
  private CumulativeDistribution<MeshSpawnShapeValue.Triangle> distribution;
  
  public WeightMeshSpawnShapeValue(WeightMeshSpawnShapeValue value) {
    super(value);
    this.distribution = new CumulativeDistribution<>();
    load(value);
  }
  
  public WeightMeshSpawnShapeValue() {
    this.distribution = new CumulativeDistribution<>();
  }
  
  public void init() {
    calculateWeights();
  }
  
  public void calculateWeights() {
    this.distribution.clear();
    VertexAttributes attributes = this.mesh.getVertexAttributes();
    int indicesCount = this.mesh.getNumIndices();
    int vertexCount = this.mesh.getNumVertices();
    int vertexSize = (short)(attributes.vertexSize / 4), positionOffset = (short)((attributes.findByUsage(1)).offset / 4);
    float[] vertices = new float[vertexCount * vertexSize];
    this.mesh.getVertices(vertices);
    if (indicesCount > 0) {
      short[] indices = new short[indicesCount];
      this.mesh.getIndices(indices);
      for (int i = 0; i < indicesCount; i += 3) {
        int p1Offset = indices[i] * vertexSize + positionOffset, p2Offset = indices[i + 1] * vertexSize + positionOffset, p3Offset = indices[i + 2] * vertexSize + positionOffset;
        float x1 = vertices[p1Offset], y1 = vertices[p1Offset + 1], z1 = vertices[p1Offset + 2], x2 = vertices[p2Offset], y2 = vertices[p2Offset + 1], z2 = vertices[p2Offset + 2], x3 = vertices[p3Offset], y3 = vertices[p3Offset + 1], z3 = vertices[p3Offset + 2];
        float area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0F);
        this.distribution.add(new MeshSpawnShapeValue.Triangle(x1, y1, z1, x2, y2, z2, x3, y3, z3), area);
      } 
    } else
      for (int i = 0; i < vertexCount; i += vertexSize) {
        int p1Offset = i + positionOffset, p2Offset = p1Offset + vertexSize, p3Offset = p2Offset + vertexSize;
        float x1 = vertices[p1Offset], y1 = vertices[p1Offset + 1], z1 = vertices[p1Offset + 2], x2 = vertices[p2Offset], y2 = vertices[p2Offset + 1], z2 = vertices[p2Offset + 2], x3 = vertices[p3Offset], y3 = vertices[p3Offset + 1], z3 = vertices[p3Offset + 2];
        float area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0F);
        this.distribution.add(new MeshSpawnShapeValue.Triangle(x1, y1, z1, x2, y2, z2, x3, y3, z3), area);
      }  
    this.distribution.generateNormalized();
  }
  
  public void spawnAux(Vector3 vector, float percent) {
    MeshSpawnShapeValue.Triangle t = this.distribution.value();
    float a = MathUtils.random(), b = MathUtils.random();
    vector.set(t.x1 + a * (t.x2 - t.x1) + b * (t.x3 - t.x1), t.y1 + a * (t.y2 - t.y1) + b * (t.y3 - t.y1), t.z1 + a * (t.z2 - t.z1) + b * (t.z3 - t.z1));
  }
  
  public SpawnShapeValue copy() {
    return new WeightMeshSpawnShapeValue(this);
  }
}
