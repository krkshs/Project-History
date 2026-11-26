package aoc.kingdoms.lukasz.map.terrain;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadManager;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class TerrainManager {
   public List<Terrain> terrains = new ArrayList<>();
   public List<List<Image>> terrainImages = new ArrayList<>();
   public static int terrainSmallWidth = 0;
   public static int terrainSmallHeight = 0;

   public final void loadTerrains() {
      try {
         FileHandle fileList = FileManager.loadFile("game/terrain/TerrainTypes.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LoadManager.ConfigJson.class, "Data", Terrain.class);
         LoadManager.ConfigJson data = json.fromJson(LoadManager.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            this.terrains.add((Terrain)e);

            try {
               this.terrains.get(this.terrains.size() - 1).Name = Game.lang.get(this.terrains.get(this.terrains.size() - 1).Name);
            } catch (Exception var8) {
            }
         }

         int i = 0;

         for (int iSize = this.terrains.size(); i < iSize; i++) {
            this.terrains.get(i).Color[0] = this.terrains.get(i).Color[0] / 255.0F;
            this.terrains.get(i).Color[1] = this.terrains.get(i).Color[1] / 255.0F;
            this.terrains.get(i).Color[2] = this.terrains.get(i).Color[2] / 255.0F;
         }

         data = null;
         this.loadTerrainImages();
      } catch (GdxRuntimeException var9) {
         CFG.LOG(var9);
      }
   }

   private final void loadTerrainImages() {
      int i = 0;

      for (int iSize = this.terrains.size(); i < iSize; i++) {
         List<Image> nImages = new ArrayList<>();

         for (int a = 0; a < this.terrains.get(i).ImageFile.length; a++) {
            try {
               if (FileManager.loadFile("game/terrain/terrainImages/" + CFG.getRescouresPath_Short() + this.terrains.get(i).ImageFile[a]).exists()) {
                  nImages.add(
                     new Image(
                        ImageManager.loadTexture_RGB888("game/terrain/terrainImages/" + CFG.getRescouresPath_Short() + this.terrains.get(i).ImageFile[a])
                     )
                  );
               } else {
                  nImages.add(
                     new Image(
                        ImageManager.loadTexture_RGB888("game/terrain/terrainImages/" + CFG.getRescouresPath_Short_H() + this.terrains.get(i).ImageFile[a])
                     )
                  );
               }
            } catch (GdxRuntimeException var6) {
               nImages.add(new Image(ImageManager.loadTexture_RGB888("game/terrain/terrainImages/" + CFG.getRescouresPath_Short() + "notFound.png")));
            }
         }

         this.terrainImages.add(nImages);
      }

      float iconScale = (float)ImageManager.getImage(Images.terrainSmall).getHeight() / this.terrainImages.get(0).get(0).getHeight();
      terrainSmallWidth = (int)(this.terrainImages.get(0).get(0).getWidth() * iconScale);
      terrainSmallHeight = (int)(this.terrainImages.get(0).get(0).getHeight() * iconScale);
   }

   public int getBattleTerrain(int terrainID) {
      switch (this.terrains.get(terrainID).BattleOver) {
         case 0:
            return Images.battleOver;
         case 1:
            return Images.battleOver1;
         case 2:
         default:
            return Images.battleOver2;
         case 3:
            return Images.battleOver3;
      }
   }
}
