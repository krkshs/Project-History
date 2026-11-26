package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import java.util.List;

public class AI_BuildManpower {
   public static final boolean buildBuilding(int civID, int limitOfBuildings, List<AI_Build.Building> tList) {
      int aiScore = AI_Build.getBuildingsAIScore(civID, tList);
      int bestBuildingID = AI_Build.getBuildingsAIScore_BestID(civID, tList, aiScore);
      updateScore(civID, tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID);

      while (limitOfBuildings-- > 0) {
         int bestProvinceID = 0;

         for (int i = 1; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
            if (Game.getProvince(Game.getCiv(civID).getProvinceID(bestProvinceID)).aiBuildScore
               < Game.getProvince(Game.getCiv(civID).getProvinceID(i)).aiBuildScore) {
               bestProvinceID = i;
            } else if (Game.getProvince(Game.getCiv(civID).getProvinceID(bestProvinceID)).aiBuildScore
                  == Game.getProvince(Game.getCiv(civID).getProvinceID(i)).aiBuildScore
               && Game.oR.nextInt(100) < 50) {
               bestProvinceID = i;
            }
         }

         bestProvinceID = Game.getCiv(civID).getProvinceID(bestProvinceID);
         if (!(Game.getProvince(bestProvinceID).aiBuildScore > 0.0F)) {
            return true;
         }

         Game.getProvince(bestProvinceID).aiBuildScore = -999999.0F;
         if (!Game.getProvince(bestProvinceID).addBuildingConstruction(tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID)) {
            return true;
         }

         if (tList.size() > 1 && Game.oR.nextInt(100) < Game.aiValuesBuild.BUILD_CHANGE_BUILDING_CHANCE) {
            int bestBuildingID_2 = AI_Build.getBuildingsAIScore_BestID(civID, tList, aiScore);
            if (bestBuildingID != bestBuildingID_2) {
               bestBuildingID = bestBuildingID_2;
               updateScore(civID, tList.get(bestBuildingID_2).building, tList.get(bestBuildingID_2).buildingID);
            }
         }

         if (BuildingsManager.buildings.get(tList.get(bestBuildingID).building).MaintenanceCost != null
            && BuildingsManager.buildings.get(tList.get(bestBuildingID).building).MaintenanceCost[tList.get(bestBuildingID).buildingID] > 0.0F
            && AI_Build.balance_StopBuildingConstruction(civID)) {
            return true;
         }
      }

      return true;
   }

   public static void updateScore(int civID, int building, int buildingID) {
      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         int nProvinceID = Game.getCiv(civID).getProvinceID(i);
         if (!Game.getProvince(nProvinceID).buildingBuilt(building, buildingID) && !Game.getProvince(nProvinceID).isUnderConstruction(building, buildingID)) {
            AI_Build.buildProvince_AIBuildScore_Default(civID, nProvinceID);
            AI_Build.buildProvince_AIBuildScore_GrowthRateManpower(nProvinceID);
            if (BuildingsManager.buildings.get(building).MaintenanceCost != null && BuildingsManager.buildings.get(building).MaintenanceCost[buildingID] > 0.0F
               )
             {
               AI_Build.buildProvince_AIBuildScore_ProvinceMaintenance(nProvinceID);
            }
         } else {
            Game.getProvince(nProvinceID).aiBuildScore = -999999.0F;
         }
      }
   }
}
