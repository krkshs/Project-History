package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceBonuses;

public class BonusesManager {
   public static void initAndBuildProvinceBonuses() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         Game.getProvince(i).provBonuses = new ProvinceBonuses();
      }

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (!Game.getProvince(i).getSeaProvince()) {
            for (int j = 0; j < Game.getProvince(i).iBuildingsSize; j++) {
               updateBuildingBonuses(i, Game.getProvince(i).getBuildings(j).getBuilding(), Game.getProvince(i).getBuildings(j).getBuildingID(), 1);
            }
         }
      }

      for (int ix = 1; ix < Game.getCivsSize(); ix++) {
         if (Game.getCiv(ix).getNumOfProvinces() > 0 && Game.getCiv(ix).getCapitalProvinceID() >= 0) {
            Game.getProvince(Game.getCiv(ix).getCapitalProvinceID()).setIsCapital(true, true);
         }
      }
   }

   public static void updateBuildingBonuses(int iProvinceID, int building, int buildingID, int mod) {
      Province province = Game.getProvince(iProvinceID);
      if (!BuildingsManager.buildings.get(building).UniqueCapitalBuilding || Game.getCiv(province.getCivID()).getCapitalProvinceID() == iProvinceID) {
         if (BuildingsManager.buildings.get(building).MonthlyLegacy != null) {
            province.provBonuses.MonthlyLegacy = province.provBonuses.MonthlyLegacy + BuildingsManager.buildings.get(building).MonthlyLegacy[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).MonthlyLegacy[buildingID] != 0.0F) {
               Game.gameThread.addCivUpdateLegacyPerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).DefenseBonus != null) {
            province.provBonuses.DefenseBonus = province.provBonuses.DefenseBonus + BuildingsManager.buildings.get(building).DefenseBonus[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).FortLevel != null) {
            province.provBonuses.FortLevel = province.provBonuses.FortLevel + BuildingsManager.buildings.get(building).FortLevel[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).FortDefense != null) {
            province.provBonuses.FortDefense = province.provBonuses.FortDefense + BuildingsManager.buildings.get(building).FortDefense[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).MaximumManpower != null) {
            province.provBonuses.MaximumManpower = province.provBonuses.MaximumManpower
               + BuildingsManager.buildings.get(building).MaximumManpower[buildingID] * mod;
            Game.gameThreadTurns.addCivUpdateMaxManpower(province.getCivID());
         }

         if (BuildingsManager.buildings.get(building).LocalManpower != null) {
            province.provBonuses.LocalManpower = (int)(
               province.provBonuses.LocalManpower + BuildingsManager.buildings.get(building).LocalManpower[buildingID] * mod
            );
            Game.gameThreadTurns.addCivUpdateMaxManpower(province.getCivID());
         }

         if (BuildingsManager.buildings.get(building).RecruitArmyCostInProvince != null) {
            province.provBonuses.RecruitArmyCostInProvince = province.provBonuses.RecruitArmyCostInProvince
               + BuildingsManager.buildings.get(building).RecruitArmyCostInProvince[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).LocalGrowthRate != null) {
            province.provBonuses.LocalGrowthRate = province.provBonuses.LocalGrowthRate
               + BuildingsManager.buildings.get(building).LocalGrowthRate[buildingID] * mod;
            province.updateInfrastructureMax();
            if (province.haveResearchBuilding()) {
               Game.getCiv(province.getCivID()).updateResearchPerMonth();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).InvestInEconomyCost != null) {
            province.provBonuses.InvestInEconomyCost = province.provBonuses.InvestInEconomyCost
               + BuildingsManager.buildings.get(building).InvestInEconomyCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).ConstructionTimeBonus != null) {
            province.provBonuses.ConstructionTimeBonus = province.provBonuses.ConstructionTimeBonus
               + BuildingsManager.buildings.get(building).ConstructionTimeBonus[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).ConstructionCost != null) {
            province.provBonuses.ConstructionCost = province.provBonuses.ConstructionCost
               + BuildingsManager.buildings.get(building).ConstructionCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).IncreaseGrowthRateCost != null) {
            province.provBonuses.IncreaseGrowthRateCost = province.provBonuses.IncreaseGrowthRateCost
               + BuildingsManager.buildings.get(building).IncreaseGrowthRateCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).IncreaseManpowerCost != null) {
            province.provBonuses.IncreaseManpowerCost = province.provBonuses.IncreaseManpowerCost
               + BuildingsManager.buildings.get(building).IncreaseManpowerCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).IncreaseTaxEfficiencyCost != null) {
            province.provBonuses.IncreaseTaxEfficiencyCost = province.provBonuses.IncreaseTaxEfficiencyCost
               + BuildingsManager.buildings.get(building).IncreaseTaxEfficiencyCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).DevelopInfrastructureCost != null) {
            province.provBonuses.DevelopInfrastructureCost = province.provBonuses.DevelopInfrastructureCost
               + BuildingsManager.buildings.get(building).DevelopInfrastructureCost[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).ProvinceMaintenance != null) {
            province.provBonuses.ProvinceMaintenance = province.provBonuses.ProvinceMaintenance
               + BuildingsManager.buildings.get(building).ProvinceMaintenance[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).ProvinceMaintenance[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).MaintenanceCost != null) {
            province.provBonuses.MaintenanceCost = province.provBonuses.MaintenanceCost
               + BuildingsManager.buildings.get(building).MaintenanceCost[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).MaintenanceCost[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).MonthlyIncome != null) {
            province.provBonuses.MonthlyIncome = province.provBonuses.MonthlyIncome + BuildingsManager.buildings.get(building).MonthlyIncome[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).MonthlyIncome[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).MaxInfrastructure != null) {
            province.provBonuses.MaxInfrastructure = province.provBonuses.MaxInfrastructure
               + BuildingsManager.buildings.get(building).MaxInfrastructure[buildingID] * mod;
            province.updateInfrastructureMax();
         }

         if (BuildingsManager.buildings.get(building).BuildingSlots != null) {
            province.provBonuses.BuildingSlots = province.provBonuses.BuildingSlots + BuildingsManager.buildings.get(building).BuildingSlots[buildingID] * mod;
            province.updateBuildingLimit();
         }

         if (BuildingsManager.buildings.get(building).LocalTaxEfficiency != null) {
            province.provBonuses.LocalTaxEfficiency = province.provBonuses.LocalTaxEfficiency
               + BuildingsManager.buildings.get(building).LocalTaxEfficiency[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).LocalTaxEfficiency[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).ArmyMovementSpeed != null) {
            province.provBonuses.ArmyMovementSpeed = province.provBonuses.ArmyMovementSpeed
               + BuildingsManager.buildings.get(building).ArmyMovementSpeed[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).DiseaseDeathRate != null) {
            province.provBonuses.DiseaseDeathRate = province.provBonuses.DiseaseDeathRate
               + BuildingsManager.buildings.get(building).DiseaseDeathRate[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).CasualtiesNuclearAttacks != null) {
            province.provBonuses.CasualtiesNuclearAttacks = province.provBonuses.CasualtiesNuclearAttacks
               + BuildingsManager.buildings.get(building).CasualtiesNuclearAttacks[buildingID] * mod;
         }

         if (BuildingsManager.buildings.get(building).Economy != null) {
            province.provBonuses.Economy = province.provBonuses.Economy + BuildingsManager.buildings.get(building).Economy[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).Economy[buildingID] != 0.0F) {
               province.updateBuildingLimit();
               province.updateInfrastructureMax();
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).ResearchPoints != null) {
            province.provBonuses.ResearchPoints = province.provBonuses.ResearchPoints
               + BuildingsManager.buildings.get(building).ResearchPoints[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).ResearchPoints[buildingID] != 0.0F) {
               Game.gameThread.addCivUpdateResearchPerMonth(province.getCivID());
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).ProductionEfficiency != null) {
            province.provBonuses.ProductionEfficiency = province.provBonuses.ProductionEfficiency
               + BuildingsManager.buildings.get(building).ProductionEfficiency[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).ProductionEfficiency[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).IncomeProduction != null) {
            province.provBonuses.IncomeProduction = province.provBonuses.IncomeProduction
               + BuildingsManager.buildings.get(building).IncomeProduction[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).IncomeProduction[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }

         if (BuildingsManager.buildings.get(building).TaxEfficiency != null) {
            CivilizationBonuses var10000 = Game.getCiv(province.getCivID()).civBonuses;
            var10000.TaxEfficiency = var10000.TaxEfficiency + BuildingsManager.buildings.get(building).TaxEfficiency[buildingID] * mod;
            if (BuildingsManager.buildings.get(building).TaxEfficiency[buildingID] != 0.0F) {
               province.updateProvinceIncome();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(province.getCivID());
            }
         }
      }
   }
}
