package aoc.kingdoms.lukasz.map.army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class ArmyManager {
   public static List<ArmyManager.Data_UnitTypes> lUnitsTypes = new ArrayList<>();
   public static int iUnitsTypesSize;
   public static ArrayList<ArrayList<ArmyManager.Data_Army>> lArmy = new ArrayList<>();
   public static List<Integer> lArmySize = new ArrayList<>();
   public static List<Image> armyImages = new ArrayList<>();
   public static final int FIRST_LINE_MIDDLE = 0;
   public static final int FIRST_LINE_FLANK = 1;
   public static final int SECOND_LINE = 2;
   public static float averageArmyCost = 0.0F;

   public static int getRecruitmentCost(int iCivID, int iProvinceID, int iUnitID, int iArmyID) {
      return getRecruitmentCost(iCivID, iProvinceID, iUnitID, iArmyID, getRecruitmentCost_Regiments(iCivID));
   }

   public static int getRecruitmentCost_Regiments(int iCivID) {
      return Game.getCiv(iCivID).getArmyRegimentSize() + Game.getCiv(iCivID).iArmyRecruitSize_Total;
   }

   public static int getRecruitmentCost(int iCivID, int iProvinceID, int iUnitID, int iArmyID, int numOfRegiments) {
      return (int)(
         (numOfRegiments >= Game.getCiv(iCivID).iRegimentsLimit ? GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER : 1.0F)
            * Math.max(
               1.0F,
               lArmy.get(iUnitID).get(iArmyID).Cost
                  * (
                     1.0F
                        + (
                              (iProvinceID >= 0 ? Game.getProvince(iProvinceID).provBonuses.RecruitArmyCostInProvince : 0.0F)
                                 + Game.getCiv(iCivID).civBonuses.RecruitArmyCost
                                 + (
                                    lUnitsTypes.get(iUnitID).Line == 2
                                       ? Game.getCiv(iCivID).civBonuses.RecruitArmySecondLineCost
                                       : Game.getCiv(iCivID).civBonuses.RecruitArmyFirstLineCost
                                 )
                           )
                           / 100.0F
                  )
            )
      );
   }

   public static int getRecruitmentTime(int iCivID, int iProvinceID, int iUnitID, int iArmyID) {
      return (int)Math.max(
         1.0F,
         lArmy.get(iUnitID).get(iArmyID).RecruitmentTime
            * (
               1.0F
                  + (
                     iProvinceID >= 0
                        ? Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_RECRUITMENT_TIME_PER_LVL
                        : 0.0F
                  )
                  + Game.getCiv(iCivID).getWarWeariness() * GameValues.warWeariness.WW_RECRUITMENT_TIME_PER_POINT
                  + Game.getCiv(iCivID).civBonuses.RecruitmentTime / 100.0F
            )
      );
   }

   public static final void loadUnits() {
      try {
         FileHandle fileList = FileManager.loadFile("game/units/Units.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(ArmyManager.ConfigUnitsData.class, "Army", ArmyManager.Data_UnitTypes.class);
         ArmyManager.ConfigUnitsData data = json.fromJson(ArmyManager.ConfigUnitsData.class, fileContent);

         for (Object e : data.Army) {
            lUnitsTypes.add((ArmyManager.Data_UnitTypes)e);
         }

         data = null;
      } catch (GdxRuntimeException var7) {
         CFG.exceptionStack(var7);
      }

      iUnitsTypesSize = lUnitsTypes.size();

      try {
         loadArmies();
         loadArmyImages();
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadArmies() {
      for (int i = 0; i < iUnitsTypesSize; i++) {
         try {
            lArmy.add(new ArrayList<>());
            FileHandle fileList = FileManager.loadFile("game/units/" + lUnitsTypes.get(i).File);
            String fileContent = fileList.readString();
            Json json = new Json();
            json.setElementType(ArmyManager.ConfigUnitsData.class, "Army", ArmyManager.Data_Army.class);
            ArmyManager.ConfigUnitsData data = json.fromJson(ArmyManager.ConfigUnitsData.class, fileContent);

            for (Object e : data.Army) {
               ArmyManager.Data_Army dataArmy = (ArmyManager.Data_Army)e;
               dataArmy.MaintenanceCost = dataArmy.MaintenanceCost * Game.map.getActiveMap_MapData().mapData.UnitsMaintenanceCost;
               dataArmy.Cost = (int)(dataArmy.Cost * Game.map.getActiveMap_MapData().mapData.UnitsRecruitCost);
               lArmy.get(i).add(dataArmy);
            }

            lArmySize.add(lArmy.get(i).size());
            data = null;
         } catch (GdxRuntimeException var8) {
            CFG.exceptionStack(var8);
         }
      }

      int tempNumOfUnits = 0;

      for (int i = 0; i < iUnitsTypesSize; i++) {
         for (int j = 0; j < lArmySize.get(i); j++) {
            lArmy.get(i).get(j).Name = Game.lang.get(lArmy.get(i).get(j).Name);
            averageArmyCost = averageArmyCost + lArmy.get(i).get(j).Cost;
            tempNumOfUnits++;
         }
      }

      averageArmyCost /= tempNumOfUnits;
   }

   public static final void loadArmyImages() {
      FileHandle tempFileT = FileManager.loadFile("game/units/unitsImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/units/unitsImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            armyImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/units/unitsImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         } else {
            armyImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/units/unitsImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static boolean armyCanBeUpgraded(int iCivID, int unitTypeID, int armyID) {
      if (armyID + 1 >= lArmySize.get(unitTypeID)) {
         return false;
      } else {
         try {
            if (Game.getCiv(iCivID).getTechResearched(lArmy.get(unitTypeID).get(armyID + 1).RequiredTechID)) {
               return true;
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }

         return false;
      }
   }

   public static int getUpgradeMaxArmyID(int iCivID, int unitTypeID, int armyID) {
      for (int i = lArmySize.get(unitTypeID) - 1; i >= armyID; i--) {
         if (Game.getCiv(iCivID).getTechResearched(lArmy.get(unitTypeID).get(i).RequiredTechID)) {
            return i;
         }
      }

      return armyID;
   }

   public static final int upgradeAllArmies(int iCivID) {
      int out = 0;

      for (int i = 0; i < Game.getCiv(iCivID).iArmyPositionSize; i++) {
         int nProvinceID = Game.getCiv(iCivID).getArmyPosition(i);
         if (nProvinceID >= 0) {
            for (int j = Game.getProvince(nProvinceID).getArmySize() - 1; j >= 0; j--) {
               if (Game.getProvince(nProvinceID).getArmy(j).civID == iCivID) {
                  for (int k = Game.getProvince(nProvinceID).getArmy(j).iArmyRegimentSize - 1; k >= 0; k--) {
                     if (armyCanBeUpgraded(
                        iCivID,
                        Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).uID,
                        Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).aID
                     )) {
                        int maxUpgrade = getUpgradeMaxArmyID(
                           iCivID,
                           Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).uID,
                           Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).aID
                        );
                        if (Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).aID < maxUpgrade) {
                           Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).aID = maxUpgrade;
                           Civilization var10000 = Game.getCiv(iCivID);
                           var10000.fGold = var10000.fGold - getUpgradeRegimentCost(iCivID);
                           out++;
                        }
                     }
                  }
               }
            }
         }
      }

      return out;
   }

   public static final void upgradeDivisionArmy(int iCivID, int unitTypeID, int armyID, int iProvinceID, int iArmyID) {
      if (iCivID == Game.getProvince(iProvinceID).getArmy(iArmyID).civID) {
         int maxUpgrade = getUpgradeMaxArmyID(iCivID, unitTypeID, armyID);

         for (int i = Game.getProvince(iProvinceID).getArmy(iArmyID).iArmyRegimentSize - 1; i >= 0; i--) {
            if (Game.getProvince(iProvinceID).getArmy(iArmyID).lArmyRegiment.get(i).uID == unitTypeID
               && Game.getProvince(iProvinceID).getArmy(iArmyID).lArmyRegiment.get(i).aID < maxUpgrade) {
               Game.getProvince(iProvinceID).getArmy(iArmyID).lArmyRegiment.get(i).aID = maxUpgrade;
               Civilization var10000 = Game.getCiv(iCivID);
               var10000.fGold = var10000.fGold - getUpgradeRegimentCost(iCivID);
            }
         }
      }
   }

   public static float getUpgradeRegimentCost(int iCivID) {
      return GameValues.army.UPGRADE_REGIMENT_COST;
   }

   public static int getRegimentsAvailableToUpgrade(int iCivID) {
      int out = 0;

      for (int i = 0; i < Game.getCiv(iCivID).iArmyPositionSize; i++) {
         int nProvinceID = Game.getCiv(iCivID).getArmyPosition(i);
         if (nProvinceID >= 0) {
            for (int j = Game.getProvince(nProvinceID).getArmySize() - 1; j >= 0; j--) {
               if (Game.getProvince(nProvinceID).getArmy(j).civID == iCivID) {
                  for (int k = Game.getProvince(nProvinceID).getArmy(j).iArmyRegimentSize - 1; k >= 0; k--) {
                     if (armyCanBeUpgraded(
                        iCivID,
                        Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).uID,
                        Game.getProvince(nProvinceID).getArmy(j).lArmyRegiment.get(k).aID
                     )) {
                        out++;
                     }
                  }
               }
            }
         }
      }

      return out;
   }

   public static class ConfigUnitsData {
      public String Age_of_History;
      public ArrayList Army;
   }

   public static class Data_Army {
      public String Name;
      public int ImageID;
      public int UnitLevel;
      private int Attack;
      private int Defense;
      public float MovementSpeed;
      public int AttackRange;
      public float SiegeProgress;
      public int RequiredTechID;
      public int Cost;
      public float MaintenanceCost;
      public int RecruitmentTime;
      public boolean isSettler = false;
      public boolean SiegeUnit = false;

      public int getAttack() {
         return this.Attack;
      }

      public int getDefense() {
         return this.Defense;
      }

      public int getAttack(int iCivID) {
         return Math.max(
            1,
            this.Attack + Game.getCiv(iCivID).civBonuses.UnitsAttack + GameValues.battleTactics.BATTLE_TACTICS_ATTACK[Game.getCiv(iCivID).getBattleTacticsID()]
         );
      }

      public int getDefense(int iCivID) {
         return Math.max(
            1,
            this.Defense
               + Game.getCiv(iCivID).civBonuses.UnitsDefense
               + GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[Game.getCiv(iCivID).getBattleTacticsID()]
         );
      }
   }

   public static class Data_UnitTypes {
      public String File;
      public int ID;
      public int Line;
   }
}
