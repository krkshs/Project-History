package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.Disposable;
import java.nio.FloatBuffer;

public interface InstanceData extends Disposable {
  int getNumInstances();
  
  int getNumMaxInstances();
  
  VertexAttributes getAttributes();
  
  void setInstanceData(float[] paramArrayOffloat, int paramInt1, int paramInt2);
  
  void updateInstanceData(int paramInt1, float[] paramArrayOffloat, int paramInt2, int paramInt3);
  
  void setInstanceData(FloatBuffer paramFloatBuffer, int paramInt);
  
  void updateInstanceData(int paramInt1, FloatBuffer paramFloatBuffer, int paramInt2, int paramInt3);
  
  FloatBuffer getBuffer();
  
  void bind(ShaderProgram paramShaderProgram);
  
  void bind(ShaderProgram paramShaderProgram, int[] paramArrayOfint);
  
  void unbind(ShaderProgram paramShaderProgram);
  
  void unbind(ShaderProgram paramShaderProgram, int[] paramArrayOfint);
  
  void invalidate();
  
  void dispose();
}
