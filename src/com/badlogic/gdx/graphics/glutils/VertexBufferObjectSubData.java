package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class VertexBufferObjectSubData implements VertexData {
  final VertexAttributes attributes;
  
  final FloatBuffer buffer;
  
  final ByteBuffer byteBuffer;
  
  int bufferHandle;
  
  final boolean isDirect;
  
  final boolean isStatic;
  
  final int usage;
  
  boolean isDirty = false;
  
  boolean isBound = false;
  
  public VertexBufferObjectSubData(boolean isStatic, int numVertices, VertexAttribute... attributes) {
    this(isStatic, numVertices, new VertexAttributes(attributes));
  }
  
  public VertexBufferObjectSubData(boolean isStatic, int numVertices, VertexAttributes attributes) {
    this.isStatic = isStatic;
    this.attributes = attributes;
    this.byteBuffer = BufferUtils.newByteBuffer(this.attributes.vertexSize * numVertices);
    this.isDirect = true;
    this.usage = isStatic ? 35044 : 35048;
    this.buffer = this.byteBuffer.asFloatBuffer();
    this.bufferHandle = createBufferObject();
    this.buffer.flip();
    this.byteBuffer.flip();
  }
  
  private int createBufferObject() {
    int result = Gdx.gl20.glGenBuffer();
    Gdx.gl20.glBindBuffer(34962, result);
    Gdx.gl20.glBufferData(34962, this.byteBuffer.capacity(), null, this.usage);
    Gdx.gl20.glBindBuffer(34962, 0);
    return result;
  }
  
  public VertexAttributes getAttributes() {
    return this.attributes;
  }
  
  public int getNumVertices() {
    return this.buffer.limit() * 4 / this.attributes.vertexSize;
  }
  
  public int getNumMaxVertices() {
    return this.byteBuffer.capacity() / this.attributes.vertexSize;
  }
  
  public FloatBuffer getBuffer() {
    this.isDirty = true;
    return this.buffer;
  }
  
  private void bufferChanged() {
    if (this.isBound) {
      Gdx.gl20.glBufferSubData(34962, 0, this.byteBuffer.limit(), this.byteBuffer);
      this.isDirty = false;
    } 
  }
  
  public void setVertices(float[] vertices, int offset, int count) {
    this.isDirty = true;
    if (this.isDirect) {
      BufferUtils.copy(vertices, this.byteBuffer, count, offset);
      this.buffer.position(0);
      this.buffer.limit(count);
    } else {
      this.buffer.clear();
      this.buffer.put(vertices, offset, count);
      this.buffer.flip();
      this.byteBuffer.position(0);
      this.byteBuffer.limit(this.buffer.limit() << 2);
    } 
    bufferChanged();
  }
  
  public void updateVertices(int targetOffset, float[] vertices, int sourceOffset, int count) {
    this.isDirty = true;
    if (this.isDirect) {
      int pos = this.byteBuffer.position();
      this.byteBuffer.position(targetOffset * 4);
      BufferUtils.copy(vertices, sourceOffset, count, this.byteBuffer);
      this.byteBuffer.position(pos);
    } else
      throw new GdxRuntimeException("Buffer must be allocated direct."); 
    bufferChanged();
  }
  
  public void bind(ShaderProgram shader) {
    bind(shader, null);
  }
  
  public void bind(ShaderProgram shader, int[] locations) {
    GL20 gl = Gdx.gl20;
    gl.glBindBuffer(34962, this.bufferHandle);
    if (this.isDirty) {
      this.byteBuffer.limit(this.buffer.limit() * 4);
      gl.glBufferData(34962, this.byteBuffer.limit(), this.byteBuffer, this.usage);
      this.isDirty = false;
    } 
    int numAttributes = this.attributes.size();
    if (locations == null) {
      for (int i = 0; i < numAttributes; i++) {
        VertexAttribute attribute = this.attributes.get(i);
        int location = shader.getAttributeLocation(attribute.alias);
        if (location >= 0) {
          shader.enableVertexAttribute(location);
          shader.setVertexAttribute(location, attribute.numComponents, attribute.type, attribute.normalized, this.attributes.vertexSize, attribute.offset);
        } 
      } 
    } else
      for (int i = 0; i < numAttributes; i++) {
        VertexAttribute attribute = this.attributes.get(i);
        int location = locations[i];
        if (location >= 0) {
          shader.enableVertexAttribute(location);
          shader.setVertexAttribute(location, attribute.numComponents, attribute.type, attribute.normalized, this.attributes.vertexSize, attribute.offset);
        } 
      }  
    this.isBound = true;
  }
  
  public void unbind(ShaderProgram shader) {
    unbind(shader, null);
  }
  
  public void unbind(ShaderProgram shader, int[] locations) {
    GL20 gl = Gdx.gl20;
    int numAttributes = this.attributes.size();
    if (locations == null) {
      for (int i = 0; i < numAttributes; i++)
        shader.disableVertexAttribute((this.attributes.get(i)).alias); 
    } else
      for (int i = 0; i < numAttributes; i++) {
        int location = locations[i];
        if (location >= 0)
          shader.disableVertexAttribute(location); 
      }  
    gl.glBindBuffer(34962, 0);
    this.isBound = false;
  }
  
  public void invalidate() {
    this.bufferHandle = createBufferObject();
    this.isDirty = true;
  }
  
  public void dispose() {
    GL20 gl = Gdx.gl20;
    gl.glBindBuffer(34962, 0);
    gl.glDeleteBuffer(this.bufferHandle);
    this.bufferHandle = 0;
  }
  
  public int getBufferHandle() {
    return this.bufferHandle;
  }
}
