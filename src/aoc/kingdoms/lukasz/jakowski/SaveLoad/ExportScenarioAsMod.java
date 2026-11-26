package aoc.kingdoms.lukasz.jakowski.SaveLoad;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;

public class ExportScenarioAsMod {
   public static void exportScenario(String tag) {
      try {
         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Details.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Details.json"));
         } catch (Exception var28) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Data.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Data.json"));
         } catch (Exception var27) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "DataProvinces.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "DataProvinces.json")
            );
         } catch (Exception var26) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "DataProvinces.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "DataProvinces.json")
            );
         } catch (Exception var25) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Cores.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Cores.json"));
         } catch (Exception var24) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Religions.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Religions.json"));
         } catch (Exception var23) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Characters.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Characters.json")
            );
         } catch (Exception var22) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Armies.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Armies.json"));
         } catch (Exception var21) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Buildings.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Buildings.json"));
         } catch (Exception var20) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Wasteland.txt");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Wasteland.txt"));
         } catch (Exception var19) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "preview.png");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "preview.png"));
         } catch (Exception var18) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "preview.png");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/logo.png"));
         } catch (Exception var17) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "previewSpecial.png");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "previewSpecial.png")
            );
         } catch (Exception var16) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Desc.txt");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Desc.txt"));
         } catch (Exception var15) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Relations.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Relations.json"));
         } catch (Exception var14) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Alliances.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Alliances.json"));
         } catch (Exception var13) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Defensive.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Defensive.json"));
         } catch (Exception var12) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Truces.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Truces.json"));
         } catch (Exception var11) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "NonAggression.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "NonAggression.json")
            );
         } catch (Exception var10) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "MilitaryAccess.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "MilitaryAccess.json")
            );
         } catch (Exception var9) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Guarantee.json");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "Guarantee.json"));
         } catch (Exception var8) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "AlliancesSpecial.json");
            file.copyTo(
               FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "AlliancesSpecial.json")
            );
         } catch (Exception var7) {
         }

         try {
            FileHandle file = FileManager.loadFile("modsExamples/mod.txt");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/mod.txt"));
         } catch (Exception var6) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "descriptions/");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "descriptions/"));
         } catch (Exception var5) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "events/");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "events/"));
         } catch (Exception var4) {
         }

         try {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "missions/");
            file.copyTo(FileManager.getSaveType("mods/" + tag + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + tag + "/" + "missions/"));
         } catch (Exception var3) {
         }
      } catch (Exception var29) {
         Game.menuManager.addToast_Error(Game.lang.get("Error"), Images.x);
         CFG.exceptionStack(var29);
      }
   }
}
