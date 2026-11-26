package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public abstract class BaseShaderProvider implements ShaderProvider {
  protected Array<Shader> shaders = new Array<>();
  
  public Shader getShader(Renderable renderable) {
    Shader suggestedShader = renderable.shader;
    if (suggestedShader != null && suggestedShader.canRender(renderable))
      return suggestedShader; 
    for (Array.ArrayIterator<Shader> arrayIterator = this.shaders.iterator(); arrayIterator.hasNext(); ) {
      Shader shader1 = arrayIterator.next();
      if (shader1.canRender(renderable))
        return shader1; 
    } 
    Shader shader = createShader(renderable);
    if (!shader.canRender(renderable))
      throw new GdxRuntimeException("unable to provide a shader for this renderable"); 
    shader.init();
    this.shaders.add(shader);
    return shader;
  }
  
  protected abstract Shader createShader(Renderable paramRenderable);
  
  public void dispose() {
    for (Array.ArrayIterator<Shader> arrayIterator = this.shaders.iterator(); arrayIterator.hasNext(); ) {
      Shader shader = arrayIterator.next();
      shader.dispose();
    } 
    this.shaders.clear();
  }
}
