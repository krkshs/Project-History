package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Map_Data {
   public String Folder;
   public Image Icon;
   public Map_Data.MapData mapData = new Map_Data.MapData();

   public Map_Data(String MAP_TAG) {
      this.Folder = MAP_TAG;
      Json json = new Json();
      FileHandle file = FileManager.loadFile("map/" + MAP_TAG + "/" + (Game.highTextueSettings() ? "Config.json" : "ConfigLow.json"));
      this.mapData = json.fromJson(Map_Data.MapData.class, file);

      try {
         for (int i = 0; i < 3; i++) {
            this.mapData.BackgroundColor[i] = this.mapData.BackgroundColor[i] / 255.0F;
            this.mapData.BackgroundColor_ZoomIn[i] = this.mapData.BackgroundColor_ZoomIn[i] / 255.0F;
            this.mapData.BackgroundColor_ZoomOut[i] = this.mapData.BackgroundColor_ZoomOut[i] / 255.0F;
            this.mapData.WastelandColor[i] = this.mapData.WastelandColor[i] / 255.0F;
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      this.Icon = new Image(ImageManager.loadTexture("map/" + MAP_TAG + "/" + "icon.png"));
   }

   public static class MapData {
      public String Name;
      public String Author;
      public int NumOfProvinces;
      public int DefaultMapScale;
      public int ExtraMapScale;
      public boolean WorldMap;
      public int BackgroundSize_X;
      public int BackgroundSize_Y;
      public float BackgroundScale;
      public boolean BackgroundZoomOut_Enable;
      public int BackgroundZoomOut_Size_X;
      public int BackgroundZoomOut_Size_Y;
      public float BackgroundZoomOut_Scale;
      public int BackgroundZoomOut_AnimationDuration;
      public float[] BackgroundColor;
      public float[] BackgroundColor_ZoomIn;
      public float[] BackgroundColor_ZoomOut;
      public float SeaOverAlpha;
      public float[] WastelandColor;
      public float DistanceKm;
      public float ResearchCost = 1.0F;
      public float UnitsRecruitCost = 1.0F;
      public float UnitsMaintenanceCost = 1.0F;
      public float BuildingsCost = 1.0F;
      public float BuildingsMaintenanceCost = 1.0F;
      public float BuildingsConstructionTime = 1.0F;
      public String DefaultScenario;
      public String Wiki;
      public float DRAW_CITIES_MIN_SCALE = 1.3F;
      public float DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE = 0.6F;
      public float DRAW_INNER_BORDERS = 0.6F;
      public float DRAW_OCCUPIED_PROVINCES_MIN_SCALE = 0.15F;
      public float DRAW_ARMY_MIN_SCALE = 0.5F;
      public float DRAW_OCCUPIED_SCALE;
   }
}
