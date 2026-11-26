package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class Map {
   public int iActiveMapID = 0;
   public List<Map_Data> lMaps;
   public static Map.DrawProvincesFlags drawProvincesFlags = new Map.DrawProvincesFlags() {
      @Override
      public void drawCity_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
      }
   };

   public Map() {
      new Map.Config();
      Json json = new Json();
      json.setElementType(Map.Config.class, "Map", Map.Maps.class);
      Map.Config data = json.fromJson(Map.Config.class, FileManager.loadFile("map/Maps.json").reader("UTF8"));
      this.lMaps = new ArrayList<>();

      for (Object obj : data.Map) {
         Map.Maps tempMapFolder = (Map.Maps)obj;
         if (FileManager.loadFile("map/" + tempMapFolder.Folder + "/" + "Config.json").exists()) {
            this.lMaps.add(new Map_Data(tempMapFolder.Folder));
         }
      }

      if (CFG.isDesktop()) {
         for (int i = 0; i < SteamManager.modsFoldersSize; i++) {
            FileHandle[] files;
            if (FileManager.IS_MAC) {
               files = Gdx.files.external(SteamManager.modsFolders.get(i) + "map/").list();
            } else {
               files = Gdx.files.internal(SteamManager.modsFolders.get(i) + "map/").list();
            }

            for (FileHandle file : files) {
               boolean addMap = true;
               if (file.name().indexOf("jar") >= 0 || file.name().indexOf("txt") >= 0 || file.name().indexOf("json") >= 0) {
                  addMap = false;
               }

               if (addMap) {
                  for (int a = 0; a < this.lMaps.size(); a++) {
                     if (this.lMaps.get(a).Folder.equals(file.name())) {
                        addMap = false;
                        break;
                     }
                  }
               }

               if (addMap && FileManager.loadFile("map/" + file.name() + "/" + "Config.json").exists()) {
                  this.lMaps.add(new Map_Data(file.name()));
               }
            }
         }

         for (int i = 0; i < SteamManager.itemsInstalledSize; i++) {
            FileHandle[] files = Gdx.files.absolute(SteamManager.itemsInstalled.get(i).getFolder() + "/" + "map/").list();

            for (FileHandle file : files) {
               boolean addMapx = true;
               if (file.name().indexOf("jar") >= 0 || file.name().indexOf("txt") >= 0 || file.name().indexOf("json") >= 0) {
                  addMapx = false;
               }

               if (addMapx) {
                  for (int ax = 0; ax < this.lMaps.size(); ax++) {
                     if (this.lMaps.get(ax).Folder.equals(file.name())) {
                        addMapx = false;
                        break;
                     }
                  }
               }

               if (addMapx && FileManager.loadFile("map/" + file.name() + "/" + "Config.json").exists()) {
                  this.lMaps.add(new Map_Data(file.name()));
               }
            }
         }
      }

      this.setActiveMapID(this.iActiveMapID);
   }

   public final void updateWorldMap() {
      Game.mapBG.updateWorldMap();
      Game.mapCoords.updateWorldMap();
   }

   public final void update() {
      Game.mapScale.update();
      Game.mapScroll.update();
      Game.mapEdgeMove.updateMoveMap();
      Game.mapCoords.updateMapPosition();
      Game.updateInView_CordsXY();
   }

   public final void drawMap(SpriteBatch oSB) {
      if (Game.mapBG.requestToDisposeMinimap) {
         Game.mapBG.disposeMinimapOfCivilizations_Real();
      }

      Game.mapBG.drawMinimapTexture_Generate(oSB);
      Game.mapBG.drawMap(oSB, Game.mapCoords.getPosX(), Game.mapCoords.getPosY());
   }

   public final void drawMapBorder(SpriteBatch oSB) {
      Game.mapBG.drawMapBorder(oSB, Game.mapCoords.getPosX(), Game.mapCoords.getPosY());
   }

   public final String getFile_ActiveMap_Path() {
      return this.lMaps.get(this.iActiveMapID).Folder + "/";
   }

   public final String getFile_Map_Path(int nMapID) {
      return this.lMaps.get(nMapID).Folder + "/";
   }

   public final boolean isWorldMap(int i) {
      return this.lMaps.get(i).mapData.WorldMap;
   }

   public final int getActiveMapID() {
      return this.iActiveMapID;
   }

   public final void setActiveMapID(int nActiveMapID) {
      if (this.iActiveMapID != nActiveMapID) {
         this.iActiveMapID = nActiveMapID;
         this.updateWorldMap();
      }

      Game.DRAW_CITIES_MIN_SCALE = this.lMaps.get(this.iActiveMapID).mapData.DRAW_CITIES_MIN_SCALE;
      Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE = this.lMaps.get(this.iActiveMapID).mapData.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE;
      Game.DRAW_INNER_BORDERS = this.lMaps.get(this.iActiveMapID).mapData.DRAW_INNER_BORDERS;
      Game.DRAW_OCCUPIED_PROVINCES_MIN_SCALE = this.lMaps.get(this.iActiveMapID).mapData.DRAW_OCCUPIED_PROVINCES_MIN_SCALE;
      Game.DRAW_ARMY_MIN_SCALE = this.lMaps.get(this.iActiveMapID).mapData.DRAW_ARMY_MIN_SCALE;
      Game.DRAW_OCCUPIED_SCALE = this.lMaps.get(this.iActiveMapID).mapData.DRAW_OCCUPIED_SCALE;
      TechnologyTree.MapResearchCost = this.lMaps.get(this.iActiveMapID).mapData.ResearchCost;
   }

   public final Map_Data getActiveMap_MapData() {
      return this.lMaps.get(this.getActiveMapID());
   }

   public final boolean getMapWorldMap(int i) {
      return this.lMaps.get(i).mapData.WorldMap;
   }

   public void dispose() {
      try {
         for (int i = 0; i < Game.mapBG.gameMap.size(); i++) {
            Game.mapBG.gameMap.get(i).getTexture().dispose();
         }
      } catch (Exception var8) {
      }

      try {
         for (int i = 0; i < Game.mapBG.gameMap2.size(); i++) {
            Game.mapBG.gameMap2.get(i).getTexture().dispose();
         }
      } catch (Exception var7) {
      }

      try {
         for (int i = 0; i < Game.mapOver.overMask.size(); i++) {
            Game.mapOver.overMask.get(i).getTexture().dispose();
         }
      } catch (Exception var6) {
      }

      try {
         for (int i = 0; i < Game.mapOver.overTile.size(); i++) {
            Game.mapOver.overTile.get(i).getTexture().dispose();
         }
      } catch (Exception var5) {
      }

      try {
         for (int i = 0; i < Game.mapOverSea.overMask.size(); i++) {
            Game.mapOverSea.overMask.get(i).getTexture().dispose();
         }
      } catch (Exception var4) {
      }

      try {
         for (int i = 0; i < Game.mapOverSea.overTile.size(); i++) {
            Game.mapOverSea.overTile.get(i).getTexture().dispose();
         }
      } catch (Exception var3) {
      }

      try {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).getProvinceBG().getTexture().dispose();
         }
      } catch (Exception var2) {
      }
   }

   public static void updateDrawProvincesFlags() {
      if (Game.settingsManager.SETTINGS_PROVINCE_FLAGS == 0) {
         drawProvincesFlags = new Map.DrawProvincesFlags() {
            @Override
            public void drawCity_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
            }
         };
      } else if (Game.settingsManager.SETTINGS_PROVINCE_FLAGS == 1) {
         drawProvincesFlags = new Map.DrawProvincesFlags() {
            @Override
            public void drawCity_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
               Map.drawCityName_Capital_CivFlag_Low(oSB, nProvinceID, nScale, fAlpha, nPosX, nPosY);
            }
         };
      } else {
         drawProvincesFlags = new Map.DrawProvincesFlags() {
            @Override
            public void drawCity_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
               Map.drawCityName_Capital_CivFlag(oSB, nProvinceID, nScale, fAlpha, nPosX, nPosY);
            }
         };
      }
   }

   public static final void drawCityName_Capital_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F * fAlpha));
      oSB.setShader(Renderer.shaderAlpha);
      Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getFlag().getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID == 4) {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver_l).getWidth() / 2;
         ImageManager.getImage(Images.flagCapitalMask_l).draw(oSB, nPosX, nPosY);
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         ImageManager.getImage(Images.flagCapitalOver_l).draw(oSB, nPosX, nPosY);
      } else if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID > 1) {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver).getWidth() / 2;
         ImageManager.getImage(Images.flagCapitalMask).draw(oSB, nPosX, nPosY);
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         ImageManager.getImage(Images.flagCapitalOver).draw(oSB, nPosX, nPosY);
      } else {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver_s).getWidth() / 2;
         ImageManager.getImage(Images.flagCapitalMask_s).draw(oSB, nPosX, nPosY);
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         ImageManager.getImage(Images.flagCapitalOver_s).draw(oSB, nPosX, nPosY);
      }
   }

   public static final void drawCityName_Capital_CivFlag_Low(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, int nPosX, int nPosY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F * fAlpha));
      if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID == 4) {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver_l_Low).getWidth() / 2;
         Game.getCiv(Game.getProvince(nProvinceID).getCivID())
            .getFlag()
            .draw(
               oSB,
               nPosX,
               nPosY,
               ImageManager.getImage(Images.flagCapitalOver_l_Low).getWidth(),
               ImageManager.getImage(Images.flagCapitalOver_l_Low).getHeight()
            );
         ImageManager.getImage(Images.flagCapitalOver_l_Low).draw(oSB, nPosX, nPosY);
      } else if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID > 1) {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver_Low).getWidth() / 2;
         Game.getCiv(Game.getProvince(nProvinceID).getCivID())
            .getFlag()
            .draw(
               oSB, nPosX, nPosY, ImageManager.getImage(Images.flagCapitalOver_Low).getWidth(), ImageManager.getImage(Images.flagCapitalOver_Low).getHeight()
            );
         ImageManager.getImage(Images.flagCapitalOver_Low).draw(oSB, nPosX, nPosY);
      } else {
         nPosX -= ImageManager.getImage(Images.flagCapitalOver_s_Low).getWidth() / 2;
         Game.getCiv(Game.getProvince(nProvinceID).getCivID())
            .getFlag()
            .draw(
               oSB,
               nPosX,
               nPosY,
               ImageManager.getImage(Images.flagCapitalOver_s_Low).getWidth(),
               ImageManager.getImage(Images.flagCapitalOver_s_Low).getHeight()
            );
         ImageManager.getImage(Images.flagCapitalOver_s_Low).draw(oSB, nPosX, nPosY);
      }
   }

   public static class Config {
      private String Age_of_History;
      private ArrayList Map;
   }

   public interface DrawProvincesFlags {
      void drawCity_CivFlag(SpriteBatch var1, int var2, float var3, float var4, int var5, int var6);
   }

   public static class Maps {
      private String Folder;
   }
}
