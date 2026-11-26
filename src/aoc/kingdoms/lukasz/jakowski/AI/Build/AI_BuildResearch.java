package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import java.util.List;

public class AI_BuildResearch {
   public static final boolean buildResearchBuilding(int civID, int limitOfBuildings) {
      Civilization civ = Game.getCiv(civID);
      if (civ.fResearchPerMonth >= TechnologyTree.getMaxResearch(civID)) {
         if (Game.oR.nextInt(100) < Game.aiValuesBuild.BUILD_MAX_RESEARCH_UPGRADE_CAPITAL_CHANCE
            && civ.getCapitalLevel() < Game.getCapital_MaxLvl(civID)
            && civ.fGold > Game.getCapital_Cost(civID)) {
            civ.upgradeCapitalCity();
         }

         return false;
      } else {
         float currentResearch = civ.fResearchPerMonth;

         for (int i = 0; i < civ.getNumOfProvinces(); i++) {
            for (int j = 0; j < Game.getProvince(civ.getProvinceID(i)).iBuildingsConstructionSize; j++) {
               if (BuildingsManager.buildings.get(Game.getProvince(civ.getProvinceID(i)).buildingsConstruction.get(j).getBuilding()).ResearchPoints != null) {
                  currentResearch += BuildingsManager.buildings.get(Game.getProvince(civ.getProvinceID(i)).buildingsConstruction.get(j).getBuilding()).ResearchPoints[Game.getProvince(
                        civ.getProvinceID(i)
                     )
                     .buildingsConstruction
                     .get(j)
                     .getBuildingID()];
               }
            }
         }

         float maxResearch = TechnologyTree.getMaxResearch(civID);
         if (currentResearch >= maxResearch) {
            return true;
         } else {
            float researchBudgetLeft = (Game.getCiv(civID).fTotalIncomePerMonth + civ.civBonuses.MonthlyIncome)
                  * Game.aiValuesBuild.MAX_RESEARCH_EXPENSES_PERC_OF_INCOME
               - Game.getResearchCost(civID, currentResearch);
            if (researchBudgetLeft < 0.0F) {
               return true;
            } else {
               List<AI_Build.Building> tList = AI_Build.getUnlockedBuildings(civID, AI_Build.research);
               if (tList.isEmpty()) {
                  return false;
               } else {
                  int aiScore = AI_Build.getBuildingsAIScore(civID, tList);
                  int bestBuildingID = AI_Build.getBuildingsAIScore_BestID(civID, tList, aiScore);

                  for (int i = 0; i < civ.getNumOfProvinces(); i++) {
                     int nProvinceID = civ.getProvinceID(i);
                     if (!Game.getProvince(nProvinceID).buildingBuilt(tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID)
                        && !Game.getProvince(nProvinceID).isUnderConstruction(tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID)) {
                        AI_Build.buildProvince_AIBuildScore_Default(civID, nProvinceID);
                        if (Game.getProvince(nProvinceID).aiBuildScore > 0.0F) {
                           AI_Build.buildProvince_AIBuildScore_ProvinceMaintenance(nProvinceID);
                           AI_Build.buildProvince_AIBuildScore_DistanceToCapital(nProvinceID);
                           AI_Build.buildProvince_AIBuildScore_GrowthRateResearch(nProvinceID);
                        }
                     } else {
                        Game.getProvince(nProvinceID).aiBuildScore = -999999.0F;
                     }
                  }

                  while (limitOfBuildings-- > 0) {
                     int bestProvinceID = 0;

                     for (int ix = 1; ix < civ.getNumOfProvinces(); ix++) {
                        if (Game.getProvince(civ.getProvinceID(bestProvinceID)).aiBuildScore < Game.getProvince(civ.getProvinceID(ix)).aiBuildScore) {
                           bestProvinceID = ix;
                        } else if (Game.getProvince(civ.getProvinceID(bestProvinceID)).aiBuildScore == Game.getProvince(civ.getProvinceID(ix)).aiBuildScore
                           && Game.oR.nextInt(100) < 50) {
                           bestProvinceID = ix;
                        }
                     }

                     bestProvinceID = civ.getProvinceID(bestProvinceID);
                     if (!(Game.getProvince(bestProvinceID).aiBuildScore > 0.0F)) {
                        return true;
                     }

                     Game.getProvince(bestProvinceID).aiBuildScore = -999999.0F;
                     if (!Game.getProvince(bestProvinceID).addBuildingConstruction(tList.get(bestBuildingID).building, tList.get(bestBuildingID).buildingID)) {
                        return true;
                     }

                     if (BuildingsManager.buildings.get(tList.get(bestBuildingID).building).ResearchPoints != null) {
                        currentResearch += BuildingsManager.buildings.get(tList.get(bestBuildingID).building).ResearchPoints[tList.get(bestBuildingID).buildingID];
                        if (currentResearch >= maxResearch) {
                           return true;
                        }

                        researchBudgetLeft -= Game.getResearchCost(
                           civID, BuildingsManager.buildings.get(tList.get(bestBuildingID).building).ResearchPoints[tList.get(bestBuildingID).buildingID]
                        );
                        if (researchBudgetLeft < 0.0F) {
                           return true;
                        }
                     }

                     if (BuildingsManager.buildings.get(tList.get(bestBuildingID).building).MaintenanceCost != null
                        && BuildingsManager.buildings.get(tList.get(bestBuildingID).building).MaintenanceCost[tList.get(bestBuildingID).buildingID] > 0.0F
                        && AI_Build.balance_StopBuildingConstruction_Research(civID)) {
                        return true;
                     }
                  }

                  return true;
               }
            }
         }
      }
   }
}
