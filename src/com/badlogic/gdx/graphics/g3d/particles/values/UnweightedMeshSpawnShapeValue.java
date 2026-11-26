package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class UnweightedMeshSpawnShapeValue extends MeshSpawnShapeValue {
  private float[] vertices;
  
  private short[] indices;
  
  private int positionOffset;
  
  private int vertexSize;
  
  private int vertexCount;
  
  private int triangleCount;
  
  public UnweightedMeshSpawnShapeValue(UnweightedMeshSpawnShapeValue value) {
    super(value);
    load(value);
  }
  
  public UnweightedMeshSpawnShapeValue() {}
  
  public void setMesh(Mesh mesh, Model model) {
    super.setMesh(mesh, model);
    this.vertexSize = mesh.getVertexSize() / 4;
    this.positionOffset = (mesh.getVertexAttribute(1)).offset / 4;
    int indicesCount = mesh.getNumIndices();
    if (indicesCount > 0) {
      this.indices = new short[indicesCount];
      mesh.getIndices(this.indices);
      this.triangleCount = this.indices.length / 3;
    } else
      this.indices = null; 
    this.vertexCount = mesh.getNumVertices();
    this.vertices = new float[this.vertexCount * this.vertexSize];
    mesh.getVertices(this.vertices);
  }
  
  public void spawnAux(Vector3 vector, float percent) {
    if (this.indices == null) {
      int triangleIndex = MathUtils.random(this.vertexCount - 3) * this.vertexSize;
      int p1Offset = triangleIndex + this.positionOffset, p2Offset = p1Offset + this.vertexSize, p3Offset = p2Offset + this.vertexSize;
      float x1 = this.vertices[p1Offset], y1 = this.vertices[p1Offset + 1], z1 = this.vertices[p1Offset + 2], x2 = this.vertices[p2Offset], y2 = this.vertices[p2Offset + 1], z2 = this.vertices[p2Offset + 2], x3 = this.vertices[p3Offset], y3 = this.vertices[p3Offset + 1], z3 = this.vertices[p3Offset + 2];
      MeshSpawnShapeValue.Triangle.pick(x1, y1, z1, x2, y2, z2, x3, y3, z3, vector);
    } else {
      int triangleIndex = MathUtils.random(this.triangleCount - 1) * 3;
      int p1Offset = this.indices[triangleIndex] * this.vertexSize + this.positionOffset, p2Offset = this.indices[triangleIndex + 1] * this.vertexSize + this.positionOffset;
      int p3Offset = this.indices[triangleIndex + 2] * this.vertexSize + this.positionOffset;
      float x1 = this.vertices[p1Offset], y1 = this.vertices[p1Offset + 1], z1 = this.vertices[p1Offset + 2], x2 = this.vertices[p2Offset], y2 = this.vertices[p2Offset + 1], z2 = this.vertices[p2Offset + 2], x3 = this.vertices[p3Offset], y3 = this.vertices[p3Offset + 1], z3 = this.vertices[p3Offset + 2];
      MeshSpawnShapeValue.Triangle.pick(x1, y1, z1, x2, y2, z2, x3, y3, z3, vector);
    } 
  }
  
  public SpawnShapeValue copy() {
    return new UnweightedMeshSpawnShapeValue(this);
  }
}
