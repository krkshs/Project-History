package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class GeographicalRegions {
   public List<GeographicalRegions.GeographicalRegion> lGeographicalRegions;
   public int iGeographicalRegionsSize;

   public final void loadMapGeographicalRegions() {
      this.lGeographicalRegions = new ArrayList<>();

      try {
         FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "GeographicalRegions.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LoadManager.ConfigJson.class, "Data", GeographicalRegions.GeographicalRegion.class);
         LoadManager.ConfigJson data = json.fromJson(LoadManager.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            this.lGeographicalRegions.add((GeographicalRegions.GeographicalRegion)e);
         }

         this.iGeographicalRegionsSize = this.lGeographicalRegions.size();

         for (int i = 0; i < this.iGeographicalRegionsSize; i++) {
            this.lGeographicalRegions.get(i).sName = Game.lang.get(this.lGeographicalRegions.get(i).sName);
         }
      } catch (GdxRuntimeException var7) {
         CFG.LOG(var7);
      }
   }

   public static class GeographicalRegion {
      public String sName;
      public int iR;
      public int iG;
      public int iB;
   }
}
