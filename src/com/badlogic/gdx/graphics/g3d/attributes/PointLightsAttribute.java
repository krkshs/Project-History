package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Array;

public class PointLightsAttribute extends Attribute {
  public static final String Alias = "pointLights";
  
  public static final long Type = register("pointLights");
  
  public final Array<PointLight> lights;
  
  public static final boolean is(long mask) {
    return ((mask & Type) == mask);
  }
  
  public PointLightsAttribute() {
    super(Type);
    this.lights = new Array<>(1);
  }
  
  public PointLightsAttribute(PointLightsAttribute copyFrom) {
    this();
    this.lights.addAll(copyFrom.lights);
  }
  
  public PointLightsAttribute copy() {
    return new PointLightsAttribute(this);
  }
  
  public int hashCode() {
    int result = super.hashCode();
    for (Array.ArrayIterator<PointLight> arrayIterator = this.lights.iterator(); arrayIterator.hasNext(); ) {
      PointLight light = arrayIterator.next();
      result = 1231 * result + ((light == null) ? 0 : light.hashCode());
    } 
    return result;
  }
  
  public int compareTo(Attribute o) {
    if (this.type != o.type)
      return (this.type < o.type) ? -1 : 1; 
    return 0;
  }
}
