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

public class Continents {
   public static final int OCEAN_ID = 0;
   public static final int OCEAN_ID_LOW_QUALITY = -4;
   public List<Continents.Continent> lContinents;
   public int iContinentsSize;

   public final void loadMapContinents() {
      this.lContinents = new ArrayList<>();

      try {
         FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "Continents.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LoadManager.ConfigJson.class, "Data", Continents.Continent.class);
         LoadManager.ConfigJson data = json.fromJson(LoadManager.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            this.lContinents.add((Continents.Continent)e);
         }

         this.iContinentsSize = this.lContinents.size();

         for (int i = 0; i < this.iContinentsSize; i++) {
            this.lContinents.get(i).sName = Game.lang.get(this.lContinents.get(i).sName);
         }
      } catch (GdxRuntimeException var7) {
         CFG.LOG(var7);
      }
   }

   public static class Continent {
      public String sName;
      public int iR;
      public int iG;
      public int iB;
      public boolean prioritizeColonization = false;
   }
}
