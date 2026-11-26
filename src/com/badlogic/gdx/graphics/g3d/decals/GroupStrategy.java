package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

public interface GroupStrategy {
  ShaderProgram getGroupShader(int paramInt);
  
  int decideGroup(Decal paramDecal);
  
  void beforeGroup(int paramInt, Array<Decal> paramArray);
  
  void afterGroup(int paramInt);
  
  void beforeGroups();
  
  void afterGroups();
}
