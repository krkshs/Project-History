/*
 * Decompiled with CFR 0.152.
 */
package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildCapitalBuilding;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildConstructionCost;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildDefensive;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildEconomy;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildGold;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildGrowthRate;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildLegacy;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildManpower;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildProductionEfficiency;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildProvinceMaintenance;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildRecruitArmyCost;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildResearch;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildResource;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildRest;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildTaxEfficiency;
import aoc.kingdoms.lukasz.jakowski.AI.Colonization.AI_ColonizationTribal;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_IncreaseTaxEfficiency;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_InvestInEconomy;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_Build {
    public static int minCostOfBuilding = 9999999;
    public static float averageCostOfBuilding = 0.0f;
    public static float averageCostOfBuilding_Capital = 0.0f;
    public static List<Building> research = new ArrayList<Building>();
    public static List<Building> gold = new ArrayList<Building>();
    public static List<Building> legacy = new ArrayList<Building>();
    public static List<Building> manpower = new ArrayList<Building>();
    public static List<Building> defensive = new ArrayList<Building>();
    public static List<Building> recruitArmyCost = new ArrayList<Building>();
    public static List<Building> taxEfficiency = new ArrayList<Building>();
    public static List<Building> growthRate = new ArrayList<Building>();
    public static List<Building> economy = new ArrayList<Building>();
    public static List<Building> productionEfficiency = new ArrayList<Building>();
    public static List<Building> provinceMaintenance = new ArrayList<Building>();
    public static List<Building> constructionCost = new ArrayList<Building>();
    public static List<Building> rest = new ArrayList<Building>();
    public static List<Building> capitalBuildings = new ArrayList<Building>();
    public static List<Building> resourceBuildings = new ArrayList<Building>();

    public static final void build(int civID) {
        try {
            if (Game.getCiv(civID).getNumOfProvinces() > 0 && Game.getCiv((int)civID).fGold > averageCostOfBuilding) {
                for (int i = 0; i < Game.aiValuesBuild.CHOOSE_BUILD_TYPE_LIMIT && AI_Build.build(civID, AI_Build.chooseBuildingType(civID)) && !(Game.getCiv((int)civID).fGold < averageCostOfBuilding); ++i) {
                }
            }
        }
        catch (Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    public static final int getLimitOfBuildings(int typeID) {
        return Game.aiValuesBuild.BUILD_LIMIT[typeID];
    }

    public static final boolean build(int civID, int typeID) {
        switch (typeID) {
            case 0: {
                return AI_BuildResearch.buildResearchBuilding(civID, AI_Build.getLimitOfBuildings(typeID));
            }
            case 1: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, gold);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildGold.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 2: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, legacy);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildLegacy.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 3: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, taxEfficiency);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildTaxEfficiency.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 4: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, provinceMaintenance);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildProvinceMaintenance.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 5: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, manpower);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildManpower.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 6: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, defensive);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildDefensive.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 7: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, recruitArmyCost);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildRecruitArmyCost.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 8: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, growthRate);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildGrowthRate.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 9: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, economy);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildEconomy.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 10: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, productionEfficiency);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildProductionEfficiency.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 11: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, constructionCost);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildConstructionCost.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 12: {
                List<Building> tList = AI_Build.getUnlockedBuildings(civID, rest);
                if (tList.isEmpty()) {
                    return AI_Build.build_NoBuildings(civID, typeID);
                }
                return AI_BuildRest.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID), tList);
            }
            case 13: {
                return AI_BuildCapitalBuilding.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID));
            }
            case 14: {
                return AI_BuildResource.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID));
            }
            case 15: {
                if (GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS && Game_Calendar.TURN_ID >= GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS_MIN_TURN_ID || Game.getCiv((int)civID).canColonize) {
                    return AI_ColonizationTribal.colonize(civID);
                }
                return AI_Build.build(civID, Game.oR.nextInt(15));
            }
        }
        return false;
    }

    public static final boolean build_NoBuildings(int civID, int typeID) {
        int rand = Game.oR.nextInt(100);
        if (rand >= Game.aiValuesBuild.BUILD_NO_BUILDINGS_NEXT_TYPE) {
            if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_INVEST_ECONOMY) {
                AI_InvestInEconomy.investInEconomy(civID, Game.aiValuesBuild.INVEST_IN_ECONOMY_MIN_LEFT_GOLD);
            } else if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_TAX_EFFICIENCY) {
                AI_IncreaseTaxEfficiency.increaseTaxEfficiency(civID, Game.aiValuesBuild.INCREASE_TAX_EFFICIENCY_MIN_LEFT_GOLD);
            } else if (rand < Game.aiValuesBuild.BUILD_NO_BUILDINGS_PRODUCTION_BUILDING) {
                return AI_BuildResource.buildBuilding(civID, AI_Build.getLimitOfBuildings(typeID));
            }
        }
        return true;
    }

    public static int chooseBuildingType(int civID) {
        int randomValue = Game.oR.nextInt(Game.ideologiesManager.getIdeology((int)Game.getCiv((int)civID).getIdeologyID()).AI_BUILD_SCORE_TOTAL);
        int score = 0;
        for (int i = 0; i < Game.ideologiesManager.getIdeology((int)Game.getCiv((int)civID).getIdeologyID()).AI_BUILD_SCORE.length; ++i) {
            if (randomValue >= (score += Game.ideologiesManager.getIdeology((int)Game.getCiv((int)civID).getIdeologyID()).AI_BUILD_SCORE[i])) continue;
            return i;
        }
        return 0;
    }

    public static final void buildProvince_AIBuildScore_Default(int civID, int nProvinceID) {
        Province province = Game.getProvince(nProvinceID);
        if (province.getBuildingsLimit_FreeSlots() <= 0) {
            province.aiBuildScore = -999999.0f;
        } else {
            province.aiBuildScore = Game.aiValuesBuild.BUILD_SCORE_MIN;
            if (Game.getCiv(civID).getCapitalProvinceID() >= 0 && province.getContinent() == Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getContinent()) {
                province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_SAME_CONTINENT_AS_CAPITAL;
            }
            if ((double)Game.terrainManager.terrains.get((int)province.getTerrainID()).BuildCost != 1.0) {
                province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_TERRAIN_CONSTRUCTION_COST * (Game.terrainManager.terrains.get((int)province.getTerrainID()).BuildCost - 1.0f);
            }
            province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_CONSTRUCTION_COST * (province.provBonuses.ConstructionCost + (float)province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_COST_PER_LVL);
            province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_GROWTH_RATE * province.getGrowthRateWithBonuses();
            province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_INFRASTRUCTURE * (float)province.getInfrastructure();
            province.aiBuildScore *= 1.0f - Game.aiValuesBuild.BUILD_SCORE_CONSTRUCTED_BUILDINGS_MODIFIER * ((float)province.iBuildingsSize / (float)province.iBuildingsLimit);
            if (!province.haveACore) {
                province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_NON_CORE;
            }
            if (province.getReligion() != Game.getCiv(civID).getReligionID()) {
                province.aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_DIFFERENT_RELIGION;
            }
        }
    }

    public static final void buildProvince_AIBuildScore_ProvinceMaintenance(int nProvinceID) {
        if (Game.getProvince((int)nProvinceID).provBonuses.ProvinceMaintenance < 0.0f) {
            Game.getProvince((int)nProvinceID).aiBuildScore += Math.abs(Game.aiValuesBuild.BUILD_SCORE_PER_PROVINCE_MAINTENANCE_REDUCTION * Game.getProvince((int)nProvinceID).provBonuses.ProvinceMaintenance);
        }
    }

    public static final void buildProvince_AIBuildScore_DistanceToCapital(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_DISTANCE_FROM_CAPITAL * Game.getProvince((int)nProvinceID).aiDistanceToCapital;
    }

    public static final void buildProvince_AIBuildScore_TaxEfficiency(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_TAX_EFFICIENCY * Game.getProvince(nProvinceID).getTaxEfficiencyWithBonuses();
    }

    public static final void buildProvince_AIBuildScore_Economy(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_ECONOMY * Game.getProvince(nProvinceID).getEconomyWithBonuses();
    }

    public static final void buildProvince_AIBuildScore_Manpower(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_MANPOWER * Game.getProvince(nProvinceID).getManpower();
    }

    public static final void buildProvince_AIBuildScore_ResourcePrice(int nProvinceID) {
        if (Game.getProvince(nProvinceID).getResourceID() >= 0) {
            Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_PRICE_OF_RESOURCE * ResourcesManager.getPrice(Game.getProvince(nProvinceID).getResourceID());
        }
    }

    public static final void buildProvince_AIBuildScore_GrowthRateResearch(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_RESEARCH_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
    }

    public static final void buildProvince_AIBuildScore_GrowthRateManpower(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_MANPOWER_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
    }

    public static final void buildProvince_AIBuildScore_GrowthRateTaxEfficiency(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_TAX_EFFICIENCY_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
    }

    public static final void buildProvince_AIBuildScore_ProvinceValue(int nProvinceID) {
        Game.getProvince((int)nProvinceID).aiBuildScore += Game.aiValuesBuild.BUILD_SCORE_PER_PROVINCE_VALUE * Game.getProvince((int)nProvinceID).fProvinceValue;
    }

    public static int getBuildingsAIScore(int civID, List<Building> tList) {
        int out = 0;
        for (int i = tList.size() - 1; i >= 0; --i) {
            out += BuildingsManager.buildings.get((int)tList.get((int)i).building).AI[tList.get((int)i).buildingID];
        }
        return out;
    }

    public static int getBuildingsAIScore_BestID(int civID, List<Building> tList, int aiScore) {
        int rand = Game.oR.nextInt(aiScore);
        int currentScore = 0;
        for (int i = 0; i < tList.size(); ++i) {
            if ((currentScore += BuildingsManager.buildings.get((int)tList.get((int)i).building).AI[tList.get((int)i).buildingID]) < rand) continue;
            return i;
        }
        return 0;
    }

    public static boolean balance_StopBuildingConstruction(int civID) {
        return Game.getCiv(civID).getBalance() + AI_Build.getBuildingsMaintenanceCost_UnderConstruction(civID) < Game.aiValuesBuild.BUILD_MIN_BALANCE;
    }

    public static boolean balance_StopBuildingConstruction_Research(int civID) {
        return Game.getCiv(civID).getBalance() + AI_Build.getBuildingsMaintenanceCost_UnderConstruction(civID) < Game.aiValuesBuild.BUILD_RESEARCH_MIN_BALANCE;
    }

    public static float getBuildingsMaintenanceCost_UnderConstruction(int civID) {
        float out = 0.0f;
        for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); ++i) {
            if (Game.getProvince((int)i).iBuildingsConstructionSize <= 0) continue;
            out += Game.getProvince(i).getBuildingsConstruction_MaintenanceCosts();
        }
        return -out;
    }

    public static List<Building> getUnlockedBuildings(int civID, List<Building> tList) {
        ArrayList<Building> out = new ArrayList<Building>();
        Civilization civ = Game.getCiv(civID);
        for (int i = tList.size() - 1; i >= 0; --i) {
            Building building = tList.get(i);
            if (!civ.isBuildingResearched(building.building, building.buildingID) || BuildingsManager.buildings.get((int)building.building).RequiredReligionID >= 0 && BuildingsManager.buildings.get((int)building.building).RequiredReligionID != civ.getReligionID() || BuildingsManager.buildings.get((int)building.building).RequiredGovernmentID >= 0 && BuildingsManager.buildings.get((int)building.building).RequiredGovernmentID != civ.getIdeologyID()) continue;
            out.add(building);
        }
        return out;
    }

    public static final void initBuildings() {
        try {
            int j;
            int i;
            int numOfBuildings = 0;
            int numOfBuildingsCapital = 0;
            for (i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; ++i) {
                for (j = 0; j < BuildingsManager.buildings.get((int)i).Name.length; ++j) {
                    resourceBuildings.add(new Building(i, j));
                }
            }
            for (i = 0; i < BuildingsManager.buildingsSize; ++i) {
                for (j = 0; j < BuildingsManager.buildings.get((int)i).Name.length; ++j) {
                    int a;
                    boolean innerAdd;
                    boolean added = false;
                    if (BuildingsManager.buildings.get((int)i).GroupID == 3) {
                        ++numOfBuildingsCapital;
                        averageCostOfBuilding_Capital += BuildingsManager.buildings.get((int)i).CostGold[j];
                        capitalBuildings.add(new Building(i, j));
                        continue;
                    }
                    if ((float)minCostOfBuilding > BuildingsManager.buildings.get((int)i).CostGold[j]) {
                        minCostOfBuilding = (int)BuildingsManager.buildings.get((int)i).CostGold[j];
                    }
                    averageCostOfBuilding += BuildingsManager.buildings.get((int)i).CostGold[j];
                    ++numOfBuildings;
                    if (BuildingsManager.buildings.get((int)i).MonthlyIncome != null && BuildingsManager.buildings.get((int)i).MonthlyIncome[j] > 0.0f) {
                        added = true;
                        gold.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).TaxEfficiency != null && BuildingsManager.buildings.get((int)i).TaxEfficiency[j] > 0.0f) {
                        added = true;
                        taxEfficiency.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).LocalTaxEfficiency != null && BuildingsManager.buildings.get((int)i).LocalTaxEfficiency[j] > 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = taxEfficiency.size() - 1; a >= 0; --a) {
                            if (AI_Build.taxEfficiency.get((int)a).building != i || AI_Build.taxEfficiency.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            taxEfficiency.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).MonthlyLegacy != null && BuildingsManager.buildings.get((int)i).MonthlyLegacy[j] > 0.0f) {
                        added = true;
                        legacy.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).DefenseBonus != null && BuildingsManager.buildings.get((int)i).DefenseBonus[j] > 0) {
                        added = true;
                        defensive.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).FortLevel != null && BuildingsManager.buildings.get((int)i).FortLevel[j] > 0) {
                        added = true;
                        innerAdd = true;
                        for (a = defensive.size() - 1; a >= 0; --a) {
                            if (AI_Build.defensive.get((int)a).building != i || AI_Build.defensive.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            defensive.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).FortDefense != null && BuildingsManager.buildings.get((int)i).FortDefense[j] > 0) {
                        added = true;
                        innerAdd = true;
                        for (a = defensive.size() - 1; a >= 0; --a) {
                            if (AI_Build.defensive.get((int)a).building != i || AI_Build.defensive.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            defensive.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).MaximumManpower != null && BuildingsManager.buildings.get((int)i).MaximumManpower[j] > 0) {
                        added = true;
                        manpower.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).LocalManpower != null && BuildingsManager.buildings.get((int)i).LocalManpower[j] > 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = manpower.size() - 1; a >= 0; --a) {
                            if (AI_Build.manpower.get((int)a).building != i || AI_Build.manpower.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            manpower.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).RecruitArmyCostInProvince != null && BuildingsManager.buildings.get((int)i).RecruitArmyCostInProvince[j] < 0.0f) {
                        added = true;
                        recruitArmyCost.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).LocalGrowthRate != null && BuildingsManager.buildings.get((int)i).LocalGrowthRate[j] > 0.0f) {
                        added = true;
                        growthRate.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).ConstructionCost != null && BuildingsManager.buildings.get((int)i).ConstructionCost[j] < 0) {
                        added = true;
                        constructionCost.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).InvestInEconomyCost != null && BuildingsManager.buildings.get((int)i).InvestInEconomyCost[j] < 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = constructionCost.size() - 1; a >= 0; --a) {
                            if (AI_Build.constructionCost.get((int)a).building != i || AI_Build.constructionCost.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            constructionCost.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).IncreaseManpowerCost != null && BuildingsManager.buildings.get((int)i).IncreaseManpowerCost[j] < 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = constructionCost.size() - 1; a >= 0; --a) {
                            if (AI_Build.constructionCost.get((int)a).building != i || AI_Build.constructionCost.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            constructionCost.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).IncreaseTaxEfficiencyCost != null && BuildingsManager.buildings.get((int)i).IncreaseTaxEfficiencyCost[j] < 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = constructionCost.size() - 1; a >= 0; --a) {
                            if (AI_Build.constructionCost.get((int)a).building != i || AI_Build.constructionCost.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            constructionCost.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).DevelopInfrastructureCost != null && BuildingsManager.buildings.get((int)i).DevelopInfrastructureCost[j] < 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = constructionCost.size() - 1; a >= 0; --a) {
                            if (AI_Build.constructionCost.get((int)a).building != i || AI_Build.constructionCost.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            constructionCost.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).IncreaseGrowthRateCost != null && BuildingsManager.buildings.get((int)i).IncreaseGrowthRateCost[j] < 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = constructionCost.size() - 1; a >= 0; --a) {
                            if (AI_Build.constructionCost.get((int)a).building != i || AI_Build.constructionCost.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            constructionCost.add(new Building(i, j));
                        }
                    }
                    if (BuildingsManager.buildings.get((int)i).ProvinceMaintenance != null && BuildingsManager.buildings.get((int)i).ProvinceMaintenance[j] < 0.0f) {
                        added = true;
                        provinceMaintenance.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).Economy != null && BuildingsManager.buildings.get((int)i).Economy[j] > 0.0f) {
                        added = true;
                        economy.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).ResearchPoints != null && BuildingsManager.buildings.get((int)i).ResearchPoints[j] > 0.0f) {
                        added = true;
                        research.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).ProductionEfficiency != null && BuildingsManager.buildings.get((int)i).ProductionEfficiency[j] > 0.0f) {
                        added = true;
                        productionEfficiency.add(new Building(i, j));
                    }
                    if (BuildingsManager.buildings.get((int)i).IncomeProduction != null && BuildingsManager.buildings.get((int)i).IncomeProduction[j] > 0.0f) {
                        added = true;
                        innerAdd = true;
                        for (a = productionEfficiency.size() - 1; a >= 0; --a) {
                            if (AI_Build.productionEfficiency.get((int)a).building != i || AI_Build.productionEfficiency.get((int)a).buildingID != j) continue;
                            innerAdd = false;
                            break;
                        }
                        if (innerAdd) {
                            productionEfficiency.add(new Building(i, j));
                        }
                    }
                    if (added) continue;
                    rest.add(new Building(i, j));
                }
            }
            averageCostOfBuilding /= (float)numOfBuildings;
            averageCostOfBuilding_Capital /= (float)numOfBuildingsCapital;
        }
        catch (Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    public static final void logBuilding(String text, List<Building> tList) {
        for (int i = 0; i < tList.size(); ++i) {
            CFG.LOG(text + ": " + BuildingsManager.buildings.get((int)tList.get((int)i).building).Name[tList.get((int)i).buildingID]);
        }
    }

    public static final void logBuildings() {
        AI_Build.logBuilding("Gold", gold);
        AI_Build.logBuilding("Legacy", legacy);
        AI_Build.logBuilding("Research", research);
        AI_Build.logBuilding("Manpower", manpower);
        AI_Build.logBuilding("Defensive", defensive);
        AI_Build.logBuilding("RecruitArmyCost", recruitArmyCost);
        AI_Build.logBuilding("TaxEfficiency", taxEfficiency);
        AI_Build.logBuilding("GrowthRate", growthRate);
        AI_Build.logBuilding("Economy", economy);
        AI_Build.logBuilding("ProductionEfficiency", productionEfficiency);
        AI_Build.logBuilding("ProvinceMaintenance", provinceMaintenance);
        AI_Build.logBuilding("ConstructionCost", constructionCost);
        AI_Build.logBuilding("Rest", rest);
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
