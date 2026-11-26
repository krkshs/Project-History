package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Colonization.AI_ColonizationTribal;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_IncreaseTaxEfficiency;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_InvestInEconomy;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_Build {
   public static int minCostOfBuilding = 9999999;
   public static float averageCostOfBuilding = 0.0F;
   public static float averageCostOfBuilding_Capital = 0.0F;
   public static List<AI_Build.Building> research = new ArrayList<>();
   public static List<AI_Build.Building> gold = new ArrayList<>();
   public static List<AI_Build.Building> legacy = new ArrayList<>();
   public static List<AI_Build.Building> manpower = new ArrayList<>();
   public static List<AI_Build.Building> defensive = new ArrayList<>();
   public static List<AI_Build.Building> recruitArmyCost = new ArrayList<>();
   public static List<AI_Build.Building> taxEfficiency = new ArrayList<>();
   public static List<AI_Build.Building> growthRate = new ArrayList<>();
   public static List<AI_Build.Building> economy = new ArrayList<>();
   public static List<AI_Build.Building> productionEfficiency = new ArrayList<>();
   public static List<AI_Build.Building> provinceMaintenance = new ArrayList<>();
   public static List<AI_Build.Building> constructionCost = new ArrayList<>();
   public static List<AI_Build.Building> rest = new ArrayList<>();
   public static List<AI_Build.Building> capitalBuildings = new ArrayList<>();
   public static List<AI_Build.Building> resourceBuildings = new ArrayList<>();

   public static final void build(int civID) {
      try {
         if (Game.getCiv(civID).getNumOfProvinces() > 0 && Game.getCiv(civID).fGold > averageCostOfBuilding) {
            int i = 0;

            while (
               i < Game.aiValuesBuild.CHOOSE_BUILD_TYPE_LIMIT && build(civID, chooseBuildingType(civID)) && !(Game.getCiv(civID).fGold < averageCostOfBuilding)
            ) {
               i++;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final int getLimitOfBuildings(int typeID) {
      return Game.aiValuesBuild.BUILD_LIMIT[typeID];
   }

   public static final boolean build(int civID, int typeID) {
      switch (typeID) {
         case 0:
            return AI_BuildResearch.buildResearchBuilding(civID, getLimitOfBuildings(typeID));
         case 1:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, gold);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildGold.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 2:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, legacy);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildLegacy.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 3:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, taxEfficiency);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildTaxEfficiency.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 4:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, provinceMaintenance);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildProvinceMaintenance.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 5:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, manpower);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildManpower.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 6:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, defensive);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildDefensive.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 7:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, recruitArmyCost);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildRecruitArmyCost.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 8:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, growthRate);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildGrowthRate.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 9:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, economy);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildEconomy.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 10:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, productionEfficiency);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildProductionEfficiency.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 11:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, constructionCost);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildConstructionCost.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 12:
            List<AI_Build.Building> tList = getUnlockedBuildings(civID, rest);
            if (tList.isEmpty()) {
               return build_NoBuildings(civID, typeID);
            }

            return AI_BuildRest.buildBuilding(civID, getLimitOfBuildings(typeID), tList);
         case 13:
            return AI_BuildCapitalBuilding.buildBuilding(civID, getLimitOfBuildings(typeID));
         case 14:
            return AI_BuildResource.buildBuilding(civID, getLimitOfBuildings(typeID));
         case 15:
            return (
                     !GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS
                        || Game_Calendar.TURN_ID < GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS_MIN_TURN_ID
                  )
                  && !Game.getCiv(civID).canColonize
               ? build(civID, Game.oR.nextInt(15))
               : AI_ColonizationTribal.colonize(civID);
         default:
            return false;
      }
   }

   public static final boolean build_NoBuildings(int civID, int typeID) {
      int rand = Game.oR.nextInt(100);
      if (rand >= Game.aiValuesBuild.BUILD_NO_BUILDINGS_NEXT_TYPE) {
         if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_INVEST_ECONOMY) {
            AI_InvestInEconomy.investInEconomy(civID, Game.aiValuesBuild.INVEST_IN_ECONOMY_MIN_LEFT_GOLD);
         } else if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_TAX_EFFICIENCY) {
            AI_IncreaseTaxEfficiency.increaseTaxEfficiency(civID, Game.aiValuesBuild.INCREASE_TAX_EFFICIENCY_MIN_LEFT_GOLD);
         } else if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_PRODUCTION_BUILDING) {
            return AI_BuildResource.buildBuilding(civID, getLimitOfBuildings(typeID));
         }
      }

      return true;
   }

   public static int chooseBuildingType(int civID) {
      int randomValue = Game.oR.nextInt(Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_SCORE_TOTAL);
      int score = 0;

      for (int i = 0; i < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_SCORE.length; i++) {
         score += Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_SCORE[i];
         if (randomValue < score) {
            return i;
         }
      }

      return 0;
   }

   public static final void buildProvince_AIBuildScore_Default(int civID, int nProvinceID) {
      Province province = Game.getProvince(nProvinceID);
      if (province.getBuildingsLimit_FreeSlots() <= 0) {
         province.aiBuildScore = -999999.0F;
      } else {
         province.aiBuildScore = Game.aiValuesBuild.BUILD_SCORE_MIN;
         if (Game.getCiv(civID).getCapitalProvinceID() >= 0
            && province.getContinent() == Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getContinent()) {
            province.aiBuildScore = province.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_SAME_CONTINENT_AS_CAPITAL;
         }

         if (Game.terrainManager.terrains.get(province.getTerrainID()).BuildCost != 1.0) {
            province.aiBuildScore = province.aiBuildScore
               + Game.aiValuesBuild.BUILD_SCORE_TERRAIN_CONSTRUCTION_COST * (Game.terrainManager.terrains.get(province.getTerrainID()).BuildCost - 1.0F);
         }

         province.aiBuildScore = province.aiBuildScore
            + Game.aiValuesBuild.BUILD_SCORE_CONSTRUCTION_COST
               * (province.provBonuses.ConstructionCost + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_COST_PER_LVL);
         province.aiBuildScore = province.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_PER_GROWTH_RATE * province.getGrowthRateWithBonuses();
         province.aiBuildScore = province.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_PER_INFRASTRUCTURE * province.getInfrastructure();
         province.aiBuildScore = province.aiBuildScore
            * (1.0F - Game.aiValuesBuild.BUILD_SCORE_CONSTRUCTED_BUILDINGS_MODIFIER * ((float)province.iBuildingsSize / province.iBuildingsLimit));
         if (!province.haveACore) {
            province.aiBuildScore = province.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_NON_CORE;
         }

         if (province.getReligion() != Game.getCiv(civID).getReligionID()) {
            province.aiBuildScore = province.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_DIFFERENT_RELIGION;
         }
      }
   }

   public static final void buildProvince_AIBuildScore_ProvinceMaintenance(int nProvinceID) {
      if (Game.getProvince(nProvinceID).provBonuses.ProvinceMaintenance < 0.0F) {
         Province var10000 = Game.getProvince(nProvinceID);
         var10000.aiBuildScore = var10000.aiBuildScore
            + Math.abs(Game.aiValuesBuild.BUILD_SCORE_PER_PROVINCE_MAINTENANCE_REDUCTION * Game.getProvince(nProvinceID).provBonuses.ProvinceMaintenance);
      }
   }

   public static final void buildProvince_AIBuildScore_DistanceToCapital(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_DISTANCE_FROM_CAPITAL * Game.getProvince(nProvinceID).aiDistanceToCapital;
   }

   public static final void buildProvince_AIBuildScore_TaxEfficiency(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore
         + Game.aiValuesBuild.BUILD_SCORE_PER_TAX_EFFICIENCY * Game.getProvince(nProvinceID).getTaxEfficiencyWithBonuses();
   }

   public static final void buildProvince_AIBuildScore_Economy(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_PER_ECONOMY * Game.getProvince(nProvinceID).getEconomyWithBonuses();
   }

   public static final void buildProvince_AIBuildScore_Manpower(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_PER_MANPOWER * Game.getProvince(nProvinceID).getManpower();
   }

   public static final void buildProvince_AIBuildScore_ResourcePrice(int nProvinceID) {
      if (Game.getProvince(nProvinceID).getResourceID() >= 0) {
         Province var10000 = Game.getProvince(nProvinceID);
         var10000.aiBuildScore = var10000.aiBuildScore
            + Game.aiValuesBuild.BUILD_SCORE_PER_PRICE_OF_RESOURCE * ResourcesManager.getPrice(Game.getProvince(nProvinceID).getResourceID());
      }
   }

   public static final void buildProvince_AIBuildScore_GrowthRateResearch(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore
         + Game.aiValuesBuild.BUILD_SCORE_RESEARCH_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
   }

   public static final void buildProvince_AIBuildScore_GrowthRateManpower(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore
         + Game.aiValuesBuild.BUILD_SCORE_MANPOWER_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
   }

   public static final void buildProvince_AIBuildScore_GrowthRateTaxEfficiency(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore
         + Game.aiValuesBuild.BUILD_SCORE_TAX_EFFICIENCY_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
   }

   public static final void buildProvince_AIBuildScore_ProvinceValue(int nProvinceID) {
      Province var10000 = Game.getProvince(nProvinceID);
      var10000.aiBuildScore = var10000.aiBuildScore + Game.aiValuesBuild.BUILD_SCORE_PER_PROVINCE_VALUE * Game.getProvince(nProvinceID).fProvinceValue;
   }

   public static int getBuildingsAIScore(int civID, List<AI_Build.Building> tList) {
      int out = 0;

      for (int i = tList.size() - 1; i >= 0; i--) {
         out += BuildingsManager.buildings.get(tList.get(i).building).AI[tList.get(i).buildingID];
      }

      return out;
   }

   public static int getBuildingsAIScore_BestID(int civID, List<AI_Build.Building> tList, int aiScore) {
      int rand = Game.oR.nextInt(aiScore);
      int i = 0;

      for (int currentScore = 0; i < tList.size(); i++) {
         currentScore += BuildingsManager.buildings.get(tList.get(i).building).AI[tList.get(i).buildingID];
         if (currentScore >= rand) {
            return i;
         }
      }

      return 0;
   }

   public static boolean balance_StopBuildingConstruction(int civID) {
      return Game.getCiv(civID).getBalance() + getBuildingsMaintenanceCost_UnderConstruction(civID) < Game.aiValuesBuild.BUILD_MIN_BALANCE;
   }

   public static boolean balance_StopBuildingConstruction_Research(int civID) {
      return Game.getCiv(civID).getBalance() + getBuildingsMaintenanceCost_UnderConstruction(civID) < Game.aiValuesBuild.BUILD_RESEARCH_MIN_BALANCE;
   }

   public static float getBuildingsMaintenanceCost_UnderConstruction(int civID) {
      float out = 0.0F;

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (Game.getProvince(i).iBuildingsConstructionSize > 0) {
            out += Game.getProvince(i).getBuildingsConstruction_MaintenanceCosts();
         }
      }

      return -out;
   }

   public static List<AI_Build.Building> getUnlockedBuildings(int civID, List<AI_Build.Building> tList) {
      List<AI_Build.Building> out = new ArrayList<>();
      Civilization civ = Game.getCiv(civID);

      for (int i = tList.size() - 1; i >= 0; i--) {
         AI_Build.Building building = tList.get(i);
         if (civ.isBuildingResearched(building.building, building.buildingID)
            && (
               BuildingsManager.buildings.get(building.building).RequiredReligionID < 0
                  || BuildingsManager.buildings.get(building.building).RequiredReligionID == civ.getReligionID()
            )
            && (
               BuildingsManager.buildings.get(building.building).RequiredGovernmentID < 0
                  || BuildingsManager.buildings.get(building.building).RequiredGovernmentID == civ.getIdeologyID()
            )) {
            out.add(building);
         }
      }

      return out;
   }

   public static final void initBuildings() {
      try {
         int numOfBuildings = 0;
         int numOfBuildingsCapital = 0;

         for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
            for (int j = 0; j < BuildingsManager.buildings.get(i).Name.length; j++) {
               resourceBuildings.add(new AI_Build.Building(i, j));
            }
         }

         for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
            for (int j = 0; j < BuildingsManager.buildings.get(i).Name.length; j++) {
               boolean added = false;
               if (BuildingsManager.buildings.get(i).GroupID == 3) {
                  numOfBuildingsCapital++;
                  averageCostOfBuilding_Capital = averageCostOfBuilding_Capital + BuildingsManager.buildings.get(i).CostGold[j];
                  capitalBuildings.add(new AI_Build.Building(i, j));
               } else {
                  if (minCostOfBuilding > BuildingsManager.buildings.get(i).CostGold[j]) {
                     minCostOfBuilding = (int)BuildingsManager.buildings.get(i).CostGold[j];
                  }

                  averageCostOfBuilding = averageCostOfBuilding + BuildingsManager.buildings.get(i).CostGold[j];
                  numOfBuildings++;
                  if (BuildingsManager.buildings.get(i).MonthlyIncome != null && BuildingsManager.buildings.get(i).MonthlyIncome[j] > 0.0F) {
                     added = true;
                     gold.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).TaxEfficiency != null && BuildingsManager.buildings.get(i).TaxEfficiency[j] > 0.0F) {
                     added = true;
                     taxEfficiency.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).LocalTaxEfficiency != null && BuildingsManager.buildings.get(i).LocalTaxEfficiency[j] > 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int a = taxEfficiency.size() - 1; a >= 0; a--) {
                        if (taxEfficiency.get(a).building == i && taxEfficiency.get(a).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        taxEfficiency.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).MonthlyLegacy != null && BuildingsManager.buildings.get(i).MonthlyLegacy[j] > 0.0F) {
                     added = true;
                     legacy.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).DefenseBonus != null && BuildingsManager.buildings.get(i).DefenseBonus[j] > 0) {
                     added = true;
                     defensive.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).FortLevel != null && BuildingsManager.buildings.get(i).FortLevel[j] > 0) {
                     added = true;
                     boolean innerAdd = true;

                     for (int ax = defensive.size() - 1; ax >= 0; ax--) {
                        if (defensive.get(ax).building == i && defensive.get(ax).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        defensive.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).FortDefense != null && BuildingsManager.buildings.get(i).FortDefense[j] > 0) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axx = defensive.size() - 1; axx >= 0; axx--) {
                        if (defensive.get(axx).building == i && defensive.get(axx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        defensive.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).MaximumManpower != null && BuildingsManager.buildings.get(i).MaximumManpower[j] > 0) {
                     added = true;
                     manpower.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).LocalManpower != null && BuildingsManager.buildings.get(i).LocalManpower[j] > 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxx = manpower.size() - 1; axxx >= 0; axxx--) {
                        if (manpower.get(axxx).building == i && manpower.get(axxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        manpower.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).RecruitArmyCostInProvince != null
                     && BuildingsManager.buildings.get(i).RecruitArmyCostInProvince[j] < 0.0F) {
                     added = true;
                     recruitArmyCost.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).LocalGrowthRate != null && BuildingsManager.buildings.get(i).LocalGrowthRate[j] > 0.0F) {
                     added = true;
                     growthRate.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).ConstructionCost != null && BuildingsManager.buildings.get(i).ConstructionCost[j] < 0) {
                     added = true;
                     constructionCost.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).InvestInEconomyCost != null && BuildingsManager.buildings.get(i).InvestInEconomyCost[j] < 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxx = constructionCost.size() - 1; axxxx >= 0; axxxx--) {
                        if (constructionCost.get(axxxx).building == i && constructionCost.get(axxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        constructionCost.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).IncreaseManpowerCost != null && BuildingsManager.buildings.get(i).IncreaseManpowerCost[j] < 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxxx = constructionCost.size() - 1; axxxxx >= 0; axxxxx--) {
                        if (constructionCost.get(axxxxx).building == i && constructionCost.get(axxxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        constructionCost.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).IncreaseTaxEfficiencyCost != null
                     && BuildingsManager.buildings.get(i).IncreaseTaxEfficiencyCost[j] < 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxxxx = constructionCost.size() - 1; axxxxxx >= 0; axxxxxx--) {
                        if (constructionCost.get(axxxxxx).building == i && constructionCost.get(axxxxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        constructionCost.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).DevelopInfrastructureCost != null
                     && BuildingsManager.buildings.get(i).DevelopInfrastructureCost[j] < 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxxxxx = constructionCost.size() - 1; axxxxxxx >= 0; axxxxxxx--) {
                        if (constructionCost.get(axxxxxxx).building == i && constructionCost.get(axxxxxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        constructionCost.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).IncreaseGrowthRateCost != null && BuildingsManager.buildings.get(i).IncreaseGrowthRateCost[j] < 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxxxxxx = constructionCost.size() - 1; axxxxxxxx >= 0; axxxxxxxx--) {
                        if (constructionCost.get(axxxxxxxx).building == i && constructionCost.get(axxxxxxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        constructionCost.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (BuildingsManager.buildings.get(i).ProvinceMaintenance != null && BuildingsManager.buildings.get(i).ProvinceMaintenance[j] < 0.0F) {
                     added = true;
                     provinceMaintenance.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).Economy != null && BuildingsManager.buildings.get(i).Economy[j] > 0.0F) {
                     added = true;
                     economy.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).ResearchPoints != null && BuildingsManager.buildings.get(i).ResearchPoints[j] > 0.0F) {
                     added = true;
                     research.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).ProductionEfficiency != null && BuildingsManager.buildings.get(i).ProductionEfficiency[j] > 0.0F) {
                     added = true;
                     productionEfficiency.add(new AI_Build.Building(i, j));
                  }

                  if (BuildingsManager.buildings.get(i).IncomeProduction != null && BuildingsManager.buildings.get(i).IncomeProduction[j] > 0.0F) {
                     added = true;
                     boolean innerAdd = true;

                     for (int axxxxxxxxx = productionEfficiency.size() - 1; axxxxxxxxx >= 0; axxxxxxxxx--) {
                        if (productionEfficiency.get(axxxxxxxxx).building == i && productionEfficiency.get(axxxxxxxxx).buildingID == j) {
                           innerAdd = false;
                           break;
                        }
                     }

                     if (innerAdd) {
                        productionEfficiency.add(new AI_Build.Building(i, j));
                     }
                  }

                  if (!added) {
                     rest.add(new AI_Build.Building(i, j));
                  }
               }
            }
         }

         averageCostOfBuilding /= numOfBuildings;
         averageCostOfBuilding_Capital /= numOfBuildingsCapital;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void logBuilding(String text, List<AI_Build.Building> tList) {
      for (int i = 0; i < tList.size(); i++) {
         CFG.LOG(text + ": " + BuildingsManager.buildings.get(tList.get(i).building).Name[tList.get(i).buildingID]);
      }
   }

   public static final void logBuildings() {
      logBuilding("Gold", gold);
      logBuilding("Legacy", legacy);
      logBuilding("Research", research);
      logBuilding("Manpower", manpower);
      logBuilding("Defensive", defensive);
      logBuilding("RecruitArmyCost", recruitArmyCost);
      logBuilding("TaxEfficiency", taxEfficiency);
      logBuilding("GrowthRate", growthRate);
      logBuilding("Economy", economy);
      logBuilding("ProductionEfficiency", productionEfficiency);
      logBuilding("ProvinceMaintenance", provinceMaintenance);
      logBuilding("ConstructionCost", constructionCost);
      logBuilding("Rest", rest);
      CFG.LOG("minCostOfBuilding: " + minCostOfBuilding);
      CFG.LOG("averageCostOfBuilding: " + averageCostOfBuilding);
      CFG.LOG("averageCostOfBuilding_Capital: " + averageCostOfBuilding_Capital);
   }

   public static class Building {
      public int building;
      public int buildingID;

      public Building(int building, int buildingID) {
         this.building = building;
         this.buildingID = buildingID;
      }
   }
}
