package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationGeneralsPool;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class GeneralManager {
   public Image noGeneral;
   public List<Integer> generalsImagesSize = new ArrayList<>();
   private ArrayList<List<String>> generalNames = new ArrayList<>();
   private ArrayList<List<String>> generalSurnames = new ArrayList<>();

   public static int getRecruitGoldCost(int iCivID) {
      return (int)(
         GameValues.generals.RECRUIT_GENERAL_GOLD_COST
            * Math.max(0.0F, 1.0F + Game.getCiv(iCivID).civBonuses.GeneralCost + GameValues.civRank.CIV_RANK_GENERAL_COST[Game.getCiv(iCivID).iCivRankID])
      );
   }

   public static int getRecruitLegacyCost(int iCivID) {
      return (int)(
         GameValues.generals.RECRUIT_GENERAL_LEGACY_COST
            * Math.max(0.0F, 1.0F + Game.getCiv(iCivID).civBonuses.GeneralCost + GameValues.civRank.CIV_RANK_GENERAL_COST[Game.getCiv(iCivID).iCivRankID])
      );
   }

   public static boolean recruitGeneral_AI(int iCivID, int provinceID, int armyID) {
      try {
         if (Game.getCiv(iCivID).fGold < getRecruitGoldCost(iCivID)) {
            return false;
         }

         if (Game.getCiv(iCivID).fLegacy < getRecruitLegacyCost(iCivID)) {
            return false;
         }

         if (Game.getProvince(provinceID).getArmy(armyID).civID != iCivID) {
            return false;
         }

         if (Game.getProvince(provinceID).getArmy(armyID).armyGeneral != null) {
            return false;
         }

         Game.getProvince(provinceID).getArmy(armyID).setArmyGeneral(CivilizationGeneralsPool.getGeneral_Random(iCivID));
         Civilization var10000 = Game.getCiv(iCivID);
         var10000.fGold = var10000.fGold - getRecruitGoldCost(iCivID);
         var10000 = Game.getCiv(iCivID);
         var10000.fLegacy = var10000.fLegacy - getRecruitLegacyCost(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return true;
   }

   public final void loadGenerals() {
      this.noGeneral = new Image(
         ImageManager.loadTexture_RGB888("game/generals/generals/" + CFG.getRescouresPath_Short() + "noGeneral.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );

      for (int i = 0; i < RulersManager.iGroupsSize; i++) {
         int numOfGenerals = 0;

         try {
            FileHandle tempFileT = FileManager.loadFile("game/generals/generals/" + CFG.getRescouresPath_Short() + i + "/numOfGenerals.txt");
            numOfGenerals = Integer.parseInt(tempFileT.readString());
         } catch (GdxRuntimeException var7) {
            CFG.exceptionStack(var7);
         }

         this.generalsImagesSize.add(numOfGenerals);
      }

      for (int i = 0; i < RulersManager.iGroupsSize; i++) {
         FileHandle tempFileT = FileManager.loadFile("game/randomNames/names/" + RulersManager.groups.get(i) + ".txt");
         String[] tNames = tempFileT.readString().split(";");
         List<String> tLNames = new ArrayList<>();
         int j = 0;

         for (int jSize = tNames.length; j < jSize; j++) {
            tLNames.add(tNames[j]);
         }

         this.generalNames.add(tLNames);
         tNames = null;
      }

      for (int i = 0; i < RulersManager.iGroupsSize; i++) {
         FileHandle tempFileT = FileManager.loadFile("game/randomNames/surnames/" + RulersManager.groups.get(i) + ".txt");
         String[] tNames = tempFileT.readString().split(";");
         List<String> tLNames = new ArrayList<>();
         int j = 0;

         for (int jSize = tNames.length; j < jSize; j++) {
            tLNames.add(tNames[j]);
         }

         this.generalSurnames.add(tLNames);
         tNames = null;
      }
   }

   public static String getGeneralsImgPath() {
      if (Game_Calendar.currentYear >= GameValues.generals.MODERN_WORLD_GENERALS_SINCE_YEAR) {
         return "generals3/";
      } else {
         return !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
               && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG
            ? "generals/"
            : "generals2/";
      }
   }

   public static String getGeneralsImgPath(int iCivID) {
      if (Game_Calendar.currentYear >= GameValues.generals.MODERN_WORLD_GENERALS_SINCE_YEAR) {
         return "generals3/";
      } else {
         return (
                  Game.ideologiesManager.getIdeology(Game.getCiv(iCivID).getIdeologyID()).KingsImages
                     || !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
               )
               && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG
            ? "generals/"
            : "generals2/";
      }
   }

   public String getGeneralRandomName(int iCivID) {
      String sCivTAG = Game.getCiv(iCivID).realTag;
      if (FileManager.loadFile("game/randomNames/names/" + sCivTAG + ".txt").exists()) {
         FileHandle fileList = FileManager.loadFile("game/randomNames/names/" + sCivTAG + ".txt");
         String[] tSplit = fileList.readString().split(";");
         if (tSplit.length > 0) {
            return tSplit[Game.oR.nextInt(tSplit.length)];
         }
      } else if (FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt").exists()) {
         FileHandle fileList = FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt");
         sCivTAG = fileList.readString();
         if (FileManager.loadFile("game/randomNames/names/" + sCivTAG + ".txt").exists()) {
            FileHandle fileList2 = FileManager.loadFile("game/randomNames/names/" + sCivTAG + ".txt");
            String[] tSplit = fileList2.readString().split(";");
            if (tSplit.length > 0) {
               return tSplit[Game.oR.nextInt(tSplit.length)];
            }
         }
      }

      try {
         return this.generalNames.get(Game.getCiv(iCivID).iGroupID).get(Game.oR.nextInt(this.generalNames.get(Game.getCiv(iCivID).iGroupID).size()));
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
         return this.generalNames.get(0).get(Game.oR.nextInt(this.generalNames.get(0).size()));
      }
   }

   public String getGeneralRandomSurname(int iCivID) {
      String sCivTAG = Game.getCiv(iCivID).realTag;
      if (FileManager.loadFile("game/randomNames/surnames/" + sCivTAG + ".txt").exists()) {
         FileHandle fileList = FileManager.loadFile("game/randomNames/surnames/" + sCivTAG + ".txt");
         String[] tSplit = fileList.readString().split(";");
         return tSplit.length > 0 ? tSplit[Game.oR.nextInt(tSplit.length)] : "";
      } else {
         if (FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt").exists()) {
            FileHandle fileList = FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt");
            sCivTAG = fileList.readString();
            if (FileManager.loadFile("game/randomNames/surnames/" + sCivTAG + ".txt").exists()) {
               FileHandle fileList2 = FileManager.loadFile("game/randomNames/surnames/" + sCivTAG + ".txt");
               String[] tSplit = fileList2.readString().split(";");
               if (tSplit.length > 0) {
                  return tSplit[Game.oR.nextInt(tSplit.length)];
               }
            }
         }

         try {
            return this.generalSurnames.get(Game.getCiv(iCivID).iGroupID).get(Game.oR.nextInt(this.generalSurnames.get(Game.getCiv(iCivID).iGroupID).size()));
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
            return this.generalSurnames.get(0).get(Game.oR.nextInt(this.generalSurnames.get(0).size()));
         }
      }
   }
}
