package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MtlLoader {
  public Array<ModelMaterial> materials = new Array<>();
  
  public void load(FileHandle file) {
    String curMatName = "default";
    Color difcolor = Color.WHITE;
    Color speccolor = Color.WHITE;
    float opacity = 1.0F;
    float shininess = 0.0F;
    String texFilename = null;
    if (file == null || !file.exists())
      return; 
    BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 4096);
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.length() > 0 && line.charAt(0) == '\t')
          line = line.substring(1).trim(); 
        String[] tokens = line.split("\\s+");
        if (tokens[0].length() == 0)
          continue; 
        if (tokens[0].charAt(0) == '#')
          continue; 
        String key = tokens[0].toLowerCase();
        if (key.equals("newmtl")) {
          ModelMaterial modelMaterial = new ModelMaterial();
          modelMaterial.id = curMatName;
          modelMaterial.diffuse = new Color(difcolor);
          modelMaterial.specular = new Color(speccolor);
          modelMaterial.opacity = opacity;
          modelMaterial.shininess = shininess;
          if (texFilename != null) {
            ModelTexture tex = new ModelTexture();
            tex.usage = 2;
            tex.fileName = new String(texFilename);
            if (modelMaterial.textures == null)
              modelMaterial.textures = new Array<>(1); 
            modelMaterial.textures.add(tex);
          } 
          this.materials.add(modelMaterial);
          if (tokens.length > 1) {
            curMatName = tokens[1];
            curMatName = curMatName.replace('.', '_');
          } else
            curMatName = "default"; 
          difcolor = Color.WHITE;
          speccolor = Color.WHITE;
          opacity = 1.0F;
          shininess = 0.0F;
          continue;
        } 
        if (key.equals("kd") || key.equals("ks")) {
          float r = Float.parseFloat(tokens[1]);
          float g = Float.parseFloat(tokens[2]);
          float b = Float.parseFloat(tokens[3]);
          float a = 1.0F;
          if (tokens.length > 4)
            a = Float.parseFloat(tokens[4]); 
          if (tokens[0].toLowerCase().equals("kd")) {
            difcolor = new Color();
            difcolor.set(r, g, b, a);
            continue;
          } 
          speccolor = new Color();
          speccolor.set(r, g, b, a);
          continue;
        } 
        if (key.equals("tr") || key.equals("d")) {
          opacity = Float.parseFloat(tokens[1]);
          continue;
        } 
        if (key.equals("ns")) {
          shininess = Float.parseFloat(tokens[1]);
          continue;
        } 
        if (key.equals("map_kd"))
          texFilename = file.parent().child(tokens[1]).path(); 
      } 
      reader.close();
    } catch (IOException e) {
      return;
    } 
    ModelMaterial mat = new ModelMaterial();
    mat.id = curMatName;
    mat.diffuse = new Color(difcolor);
    mat.specular = new Color(speccolor);
    mat.opacity = opacity;
    mat.shininess = shininess;
    if (texFilename != null) {
      ModelTexture tex = new ModelTexture();
      tex.usage = 2;
      tex.fileName = new String(texFilename);
      if (mat.textures == null)
        mat.textures = new Array<>(1); 
      mat.textures.add(tex);
    } 
    this.materials.add(mat);
  }
  
  public ModelMaterial getMaterial(String name) {
    for (Array.ArrayIterator<ModelMaterial> arrayIterator = this.materials.iterator(); arrayIterator.hasNext(); ) {
      ModelMaterial m = arrayIterator.next();
      if (m.id.equals(name))
        return m; 
    } 
    ModelMaterial mat = new ModelMaterial();
    mat.id = name;
    mat.diffuse = new Color(Color.WHITE);
    this.materials.add(mat);
    return mat;
  }
}
