package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.List;

public class AI_BuildCapitalBuilding {
   public static final boolean buildBuilding(int civID, int limitOfBuildings) {
      if (Game.oR.nextInt(100) < Game.aiValuesBuild.BUILD_CHANGE_UPGRADE_CAPITAL_BUILDING_CHANCE) {
         upgradeCapitalBuilding(civID);
      }

      List<AI_Build.Building> tList = AI_Build.getUnlockedBuildings(civID, AI_Build.capitalBuildings);

      for (int i = tList.size() - 1; i >= 0; i--) {
         if (Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).buildingBuilt(tList.get(i).building, tList.get(i).buildingID)) {
            tList.remove(i);
         }
      }

      if (!tList.isEmpty()) {
         int aiScore = AI_Build.getBuildingsAIScore(civID, tList);
         int bestBuildingID = AI_Build.getBuildingsAIScore_BestID(civID, tList, aiScore);
         return !Game.getProvince(Game.getCiv(civID).getCapitalProvinceID())
               .addBuildingConstruction(tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID)
            ? true
            : true;
      } else {
         upgradeCapitalBuilding(civID);
         return false;
      }
   }

   public static final void upgradeCapitalBuilding(int civID) {
      int typeID = chooseBuildingType();
      switch (typeID) {
         case 0:
            if (Game.getCiv(civID).getCapitalLevel() < Game.getCapital_MaxLvl(civID) && Game.getCiv(civID).fGold > Game.getCapital_Cost(civID)) {
               Game.getCiv(civID).upgradeCapitalCity();
            }
            break;
         case 1:
            if (Game.getCiv(civID).getMilitaryAcademyLevel() < Game.getMilitaryAcademy_MaxLvl(civID)
               && Game.getCiv(civID).fGold > Game.getMilitaryAcademy_Cost(civID)) {
               Game.getCiv(civID).upgradeMilitaryAcademy();
            }
            break;
         case 2:
            if (Game.getCiv(civID).getMilitaryAcademyForGeneralsLevel() < Game.getMilitaryAcademyForGenerals_MaxLvl(civID)
               && Game.getCiv(civID).fGold > Game.getMilitaryAcademyForGenerals_Cost(civID)) {
               Game.getCiv(civID).upgradeMilitaryAcademyForGenerals();
            }
            break;
         case 3:
            if (Game.getCiv(civID).getSupremeCourtLevel() < Game.getSupremeCourt_MaxLvl(civID) && Game.getCiv(civID).fGold > Game.getSupremeCourt_Cost(civID)) {
               Game.getCiv(civID).upgradeSupremeCourt();
            }
      }
   }

   public static int chooseBuildingType() {
      int randomValue = Game.oR.nextInt(Game.aiValuesBuild.BUILD_SCORE_CAPITAL_TOTAL);
      int score = 0;

      for (int i = 0; i < Game.aiValuesBuild.BUILD_SCORE_CAPITAL.length; i++) {
         score += Game.aiValuesBuild.BUILD_SCORE_CAPITAL[i];
         if (randomValue < score) {
            return i;
         }
      }

      return 0;
   }
}
