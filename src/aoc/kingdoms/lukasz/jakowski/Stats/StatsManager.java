package aoc.kingdoms.lukasz.jakowski.Stats;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadManager;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class StatsManager {
   public Stats civStats = new Stats("neu");

   public void loadStats(String tag, boolean increaseGames) {
      try {
         if (FileManager.loadFile("statistics/" + tag + ".json").exists()) {
            try {
               FileHandle fileList = FileManager.loadFile("statistics/" + tag + ".json");
               Json json = new Json();
               this.civStats = json.fromJson(Stats.class, fileList);
               if (increaseGames) {
                  this.civStats.ga++;
               }

               return;
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      this.civStats = new Stats(tag);
   }

   public void saveStats() {
      if (Game.player.iCivID != 0 && !Game.SPECTATOR_MODE && !this.civStats.tg.equals("neu")) {
         this.civStats.li = Math.max(this.civStats.li, (int)Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth);
         this.civStats.lp = Math.max(this.civStats.lp, Game.getCiv(Game.player.iCivID).getNumOfProvinces());
         Json json = SaveManager.getJson();
         json.setElementType(LoadManager.ConfigJson.class, "Data", Stats.class);
         FileHandle file = FileManager.getSaveType("statistics/" + this.civStats.tg + ".json");
         file.writeString(json.toJson(this.civStats), false);
         this.saveStatsList();
      }
   }

   private final void saveStatsList() {
      String tList = "";
      if (FileManager.loadFile("statistics/AoH.txt").exists()) {
         FileHandle file2 = FileManager.loadFile("statistics/AoH.txt");
         String tempTags = file2.readString();
         tList = tempTags;
         String[] tSplited = tempTags.split(";");
         boolean add = true;
         int i = 0;

         for (int iSize = tSplited.length; i < iSize; i++) {
            if (tSplited[i].equals(this.civStats.tg)) {
               add = false;
               break;
            }
         }

         if (add) {
            tList = tempTags + this.civStats.tg + ";";
         }
      } else {
         tList = tList + this.civStats.tg + ";";
      }

      FileHandle fileSave = FileManager.getSaveType("statistics/AoH.txt");
      fileSave.writeString(tList, false);
   }

   public final List<Stats> loadAllStats() {
      List<Stats> out = new ArrayList<>();
      if (FileManager.loadFile("statistics/AoH.txt").exists()) {
         FileHandle file2 = FileManager.loadFile("statistics/AoH.txt");
         String tempTags = file2.readString();
         String[] tSplited = tempTags.split(";");
         int i = 0;

         for (int iSize = tSplited.length; i < iSize; i++) {
            if (FileManager.loadFile("statistics/" + tSplited[i] + ".json").exists()) {
               try {
                  FileHandle fileList = FileManager.loadFile("statistics/" + tSplited[i] + ".json");
                  Json json = new Json();
                  Stats loadedStats = json.fromJson(Stats.class, fileList);
                  if (loadedStats != null) {
                     out.add(loadedStats);
                  }
               } catch (Exception var10) {
                  CFG.exceptionStack(var10);
               }
            }
         }
      }

      return out;
   }
}
