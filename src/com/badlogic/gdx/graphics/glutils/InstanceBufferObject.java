package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class InstanceBufferObject implements InstanceData {
  private VertexAttributes attributes;
  
  private FloatBuffer buffer;
  
  private ByteBuffer byteBuffer;
  
  private boolean ownsBuffer;
  
  private int bufferHandle;
  
  private int usage;
  
  boolean isDirty = false;
  
  boolean isBound = false;
  
  public InstanceBufferObject(boolean isStatic, int numVertices, VertexAttribute... attributes) {
    this(isStatic, numVertices, new VertexAttributes(attributes));
  }
  
  public InstanceBufferObject(boolean isStatic, int numVertices, VertexAttributes instanceAttributes) {
    if (Gdx.gl30 == null)
      throw new GdxRuntimeException("InstanceBufferObject requires a device running with GLES 3.0 compatibilty"); 
    this.bufferHandle = Gdx.gl20.glGenBuffer();
    ByteBuffer data = BufferUtils.newUnsafeByteBuffer(instanceAttributes.vertexSize * numVertices);
    data.limit(0);
    setBuffer(data, true, instanceAttributes);
    setUsage(isStatic ? 35044 : 35048);
  }
  
  public VertexAttributes getAttributes() {
    return this.attributes;
  }
  
  public int getNumInstances() {
    return this.buffer.limit() * 4 / this.attributes.vertexSize;
  }
  
  public int getNumMaxInstances() {
    return this.byteBuffer.capacity() / this.attributes.vertexSize;
  }
  
  public FloatBuffer getBuffer() {
    this.isDirty = true;
    return this.buffer;
  }
  
  protected void setBuffer(Buffer data, boolean ownsBuffer, VertexAttributes value) {
    if (this.isBound)
      throw new GdxRuntimeException("Cannot change attributes while VBO is bound"); 
    if (this.ownsBuffer && this.byteBuffer != null)
      BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer); 
    this.attributes = value;
    if (data instanceof ByteBuffer) {
      this.byteBuffer = (ByteBuffer)data;
    } else
      throw new GdxRuntimeException("Only ByteBuffer is currently supported"); 
    this.ownsBuffer = ownsBuffer;
    int l = this.byteBuffer.limit();
    this.byteBuffer.limit(this.byteBuffer.capacity());
    this.buffer = this.byteBuffer.asFloatBuffer();
    this.byteBuffer.limit(l);
    this.buffer.limit(l / 4);
  }
  
  private void bufferChanged() {
    if (this.isBound) {
      Gdx.gl20.glBufferData(34962, this.byteBuffer.limit(), null, this.usage);
      Gdx.gl20.glBufferData(34962, this.byteBuffer.limit(), this.byteBuffer, this.usage);
      this.isDirty = false;
    } 
  }
  
  public void setInstanceData(float[] data, int offset, int count) {
    this.isDirty = true;
    BufferUtils.copy(data, this.byteBuffer, count, offset);
    this.buffer.position(0);
    this.buffer.limit(count);
    bufferChanged();
  }
  
  public void setInstanceData(FloatBuffer data, int count) {
    this.isDirty = true;
    BufferUtils.copy(data, this.byteBuffer, count);
    this.buffer.position(0);
    this.buffer.limit(count);
    bufferChanged();
  }
  
  public void updateInstanceData(int targetOffset, float[] data, int sourceOffset, int count) {
    this.isDirty = true;
    int pos = this.byteBuffer.position();
    this.byteBuffer.position(targetOffset * 4);
    BufferUtils.copy(data, sourceOffset, count, this.byteBuffer);
    this.byteBuffer.position(pos);
    this.buffer.position(0);
    bufferChanged();
  }
  
  public void updateInstanceData(int targetOffset, FloatBuffer data, int sourceOffset, int count) {
    this.isDirty = true;
    int pos = this.byteBuffer.position();
    this.byteBuffer.position(targetOffset * 4);
    data.position(sourceOffset * 4);
    BufferUtils.copy(data, this.byteBuffer, count);
    this.byteBuffer.position(pos);
    this.buffer.position(0);
    bufferChanged();
  }
  
  protected int getUsage() {
    return this.usage;
  }
  
  protected void setUsage(int value) {
    if (this.isBound)
      throw new GdxRuntimeException("Cannot change usage while VBO is bound"); 
    this.usage = value;
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
          int unitOffset = attribute.unit;
          shader.enableVertexAttribute(location + unitOffset);
          shader.setVertexAttribute(location + unitOffset, attribute.numComponents, attribute.type, attribute.normalized, this.attributes.vertexSize, attribute.offset);
          Gdx.gl30.glVertexAttribDivisor(location + unitOffset, 1);
        } 
      } 
    } else
      for (int i = 0; i < numAttributes; i++) {
        VertexAttribute attribute = this.attributes.get(i);
        int location = locations[i];
        if (location >= 0) {
          int unitOffset = attribute.unit;
          shader.enableVertexAttribute(location + unitOffset);
          shader.setVertexAttribute(location + unitOffset, attribute.numComponents, attribute.type, attribute.normalized, this.attributes.vertexSize, attribute.offset);
          Gdx.gl30.glVertexAttribDivisor(location + unitOffset, 1);
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
      for (int i = 0; i < numAttributes; i++) {
        VertexAttribute attribute = this.attributes.get(i);
        int location = shader.getAttributeLocation(attribute.alias);
        if (location >= 0) {
          int unitOffset = attribute.unit;
          shader.disableVertexAttribute(location + unitOffset);
        } 
      } 
    } else
      for (int i = 0; i < numAttributes; i++) {
        VertexAttribute attribute = this.attributes.get(i);
        int location = locations[i];
        if (location >= 0) {
          int unitOffset = attribute.unit;
          shader.disableVertexAttribute(location + unitOffset);
        } 
      }  
    gl.glBindBuffer(34962, 0);
    this.isBound = false;
  }
  
  public void invalidate() {
    this.bufferHandle = Gdx.gl20.glGenBuffer();
    this.isDirty = true;
  }
  
  public void dispose() {
    GL20 gl = Gdx.gl20;
    gl.glBindBuffer(34962, 0);
    gl.glDeleteBuffer(this.bufferHandle);
    this.bufferHandle = 0;
    if (this.ownsBuffer)
      BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer); 
  }
}
