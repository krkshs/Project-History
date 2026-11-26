package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class MapOver {
   public List<MapOver.Overlay> lOver;
   public int iOverSize = 0;
   public List<Image> overTile = new ArrayList<>();
   public List<Image> overMask = new ArrayList<>();

   public final void loadOverlay(String sFile) {
      new MapOver.Config();
      Json json = new Json();
      json.setElementType(MapOver.Config.class, "Overlay", MapOver.Overlay.class);
      MapOver.Config data = json.fromJson(
         MapOver.Config.class, FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "background/overlays/" + sFile).reader("UTF8")
      );
      this.lOver = new ArrayList<>();

      for (Object obj : data.Overlay) {
         this.lOver.add((MapOver.Overlay)obj);
      }

      this.iOverSize = this.lOver.size();
   }

   public final boolean loadOverlayImages() {
      int i = MapBG.loadMapBG_FileID++;
      if (i < this.iOverSize) {
         this.overTile
            .add(
               new Image(
                  ImageManager.loadTexture_RGB888("map/" + Game.map.getFile_ActiveMap_Path() + "background/overlays/" + this.lOver.get(i).Tile),
                  TextureFilter.Linear,
                  TextureWrap.Repeat
               )
            );
         return true;
      } else {
         return false;
      }
   }

   public final boolean loadOverlayImages_2() {
      int i = MapBG.loadMapBG_FileID++;
      if (i < this.iOverSize) {
         this.overMask
            .add(
               new Image(
                  ImageManager.loadTexture(
                     "map/"
                        + Game.map.getFile_ActiveMap_Path()
                        + "background/overlays/"
                        + (Game.highTextureSettings ? "high/" : "low/")
                        + this.lOver.get(i).Mask
                  ),
                  TextureFilter.Linear,
                  TextureWrap.Repeat
               )
            );
         this.lOver.get(i).u_maskScale = this.overMask.get(i).getWidth() / (this.overTile.get(i).getWidth() * this.lOver.get(i).Scale);
         this.lOver.get(i).u_maskScaleY = this.overMask.get(i).getHeight() / (this.overTile.get(i).getHeight() * this.lOver.get(i).Scale);
         return true;
      } else {
         return false;
      }
   }

   public void drawMapOverlay(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
      try {
         oSB.setShader(Renderer.shaderAlpha_Map);

         for (int i = 0; i < this.iOverSize; i++) {
            oSB.setColor(
               new Color(
                  1.0F,
                  1.0F,
                  1.0F,
                  (
                        this.lOver.get(i).Alpha
                           + (
                              Game.mapScale.getCurrentScale() < 1.0F
                                 ? this.lOver.get(i).AlphaScaleZoomOut * Game.mapScale.getCurrentScale()
                                 : this.lOver.get(i).AlphaScaleZoomOut
                           )
                           + Game.mapScale.getCurrentScale() * this.lOver.get(i).AlphaScale
                     )
                     * fAlpha
               )
            );
            Renderer.shaderAlpha_Map.setUniformf("u_maskScale", this.lOver.get(i).u_maskScale);
            Renderer.shaderAlpha_Map.setUniformf("u_maskScaleY", this.lOver.get(i).u_maskScaleY);
            Renderer.shaderAlpha_Map.setUniformf("u_extraColor", this.lOver.get(i).ExtraColor);
            this.overMask.get(i).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            this.overTile.get(i).draw(oSB, nPosX, nPosY, MapBG.iWidthOfMap, MapBG.iHeightOfMap);
            oSB.flush();
         }

         if (Game.mapBG.secondSideOfMap) {
            for (int i = 0; i < this.iOverSize; i++) {
               oSB.setColor(
                  new Color(
                     1.0F,
                     1.0F,
                     1.0F,
                     (
                           this.lOver.get(i).Alpha
                              + (
                                 Game.mapScale.getCurrentScale() < 1.0F
                                    ? this.lOver.get(i).AlphaScaleZoomOut * Game.mapScale.getCurrentScale()
                                    : this.lOver.get(i).AlphaScaleZoomOut
                              )
                              + Game.mapScale.getCurrentScale() * this.lOver.get(i).AlphaScale
                        )
                        * fAlpha
                  )
               );
               Renderer.shaderAlpha_Map.setUniformf("u_maskScale", this.lOver.get(i).u_maskScale);
               Renderer.shaderAlpha_Map.setUniformf("u_maskScaleY", this.lOver.get(i).u_maskScaleY);
               Renderer.shaderAlpha_Map.setUniformf("u_extraColor", this.lOver.get(i).ExtraColor);
               this.overMask.get(i).getTexture().bind(1);
               Gdx.gl.glActiveTexture(33984);
               this.overTile.get(i).draw(oSB, nPosX + Game.mapBG.getWidth(), nPosY, MapBG.iWidthOfMap, MapBG.iHeightOfMap);
               oSB.flush();
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setShader(Renderer.shaderDefault);
      oSB.setColor(Color.WHITE);
   }

   public void drawMapOverlaySea(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
      try {
         oSB.setShader(Renderer.shaderAlpha_MapSea);

         for (int i = 0; i < this.iOverSize; i++) {
            oSB.setColor(
               new Color(
                  1.0F,
                  1.0F,
                  1.0F,
                  (
                        this.lOver.get(i).Alpha
                           + (
                              Game.mapScale.getCurrentScale() < 1.0F
                                 ? this.lOver.get(i).AlphaScaleZoomOut * Game.mapScale.getCurrentScale()
                                 : this.lOver.get(i).AlphaScaleZoomOut
                           )
                           + Game.mapScale.getCurrentScale() * this.lOver.get(i).AlphaScale
                     )
                     * fAlpha
               )
            );
            Renderer.shaderAlpha_MapSea.setUniformf("u_maskScale", this.lOver.get(i).u_maskScale);
            Renderer.shaderAlpha_MapSea.setUniformf("u_maskScaleY", this.lOver.get(i).u_maskScaleY);
            Renderer.shaderAlpha_MapSea.setUniformf("u_extraColor", this.lOver.get(i).ExtraColor);
            this.overMask.get(i).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            this.overTile.get(i).draw(oSB, nPosX, nPosY, MapBG.iWidthOfMap, MapBG.iHeightOfMap);
            oSB.flush();
         }

         if (Game.mapBG.secondSideOfMap) {
            for (int i = 0; i < this.iOverSize; i++) {
               oSB.setColor(
                  new Color(
                     1.0F,
                     1.0F,
                     1.0F,
                     (
                           this.lOver.get(i).Alpha
                              + (
                                 Game.mapScale.getCurrentScale() < 1.0F
                                    ? this.lOver.get(i).AlphaScaleZoomOut * Game.mapScale.getCurrentScale()
                                    : this.lOver.get(i).AlphaScaleZoomOut
                              )
                              + Game.mapScale.getCurrentScale() * this.lOver.get(i).AlphaScale
                        )
                        * fAlpha
                  )
               );
               Renderer.shaderAlpha_MapSea.setUniformf("u_maskScale", this.lOver.get(i).u_maskScale);
               Renderer.shaderAlpha_MapSea.setUniformf("u_maskScaleY", this.lOver.get(i).u_maskScaleY);
               Renderer.shaderAlpha_MapSea.setUniformf("u_extraColor", this.lOver.get(i).ExtraColor);
               this.overMask.get(i).getTexture().bind(1);
               Gdx.gl.glActiveTexture(33984);
               this.overTile.get(i).draw(oSB, nPosX + Game.mapBG.getWidth(), nPosY, MapBG.iWidthOfMap, MapBG.iHeightOfMap);
               oSB.flush();
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setShader(Renderer.shaderDefault);
      oSB.setColor(Color.WHITE);
   }

   public static class Config {
      public String Age_of_History;
      public ArrayList Overlay;
   }

   public static class Overlay {
      public String Tile;
      public String Mask;
      public float Scale;
      public float Alpha;
      public float AlphaScale;
      public float AlphaScaleZoomOut;
      public float ExtraColor;
      public float u_maskScale;
      public float u_maskScaleY;
   }
}
