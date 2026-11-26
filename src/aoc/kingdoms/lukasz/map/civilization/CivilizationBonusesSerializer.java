package aoc.kingdoms.lukasz.map.civilization;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivilizationBonusesSerializer implements Serializer<CivilizationBonuses> {
   public void write(Json json, CivilizationBonuses bonuses, Class knownType) {
      json.writeObjectStart();
      if (bonuses.TempTurnID != 0 && bonuses.TempTurnID != 1000000) {
         json.writeValue("TempTurnID", bonuses.TempTurnID);
      }

      if (bonuses.MonthlyIncome != 0.0F) {
         json.writeValue("MonthlyIncome", bonuses.MonthlyIncome);
      }

      if (bonuses.TaxEfficiency != 0.0F) {
         json.writeValue("TaxEfficiency", bonuses.TaxEfficiency);
      }

      if (bonuses.ProvinceMaintenance != 0.0F) {
         json.writeValue("ProvinceMaintenance", bonuses.ProvinceMaintenance);
      }

      if (bonuses.BuildingsMaintenanceCost != 0.0F) {
         json.writeValue("BuildingsMaintenanceCost", bonuses.BuildingsMaintenanceCost);
      }

      if (bonuses.GrowthRate != 0.0F) {
         json.writeValue("GrowthRate", bonuses.GrowthRate);
      }

      if (bonuses.MaintenanceCost != 0.0F) {
         json.writeValue("MaintenanceCost", bonuses.MaintenanceCost);
      }

      if (bonuses.ProductionEfficiency != 0.0F) {
         json.writeValue("ProductionEfficiency", bonuses.ProductionEfficiency);
      }

      if (bonuses.IncomeProduction != 0.0F) {
         json.writeValue("IncomeProduction", bonuses.IncomeProduction);
      }

      if (bonuses.IncomeTaxation != 0.0F) {
         json.writeValue("IncomeTaxation", bonuses.IncomeTaxation);
      }

      if (bonuses.IncomeEconomy != 0.0F) {
         json.writeValue("IncomeEconomy", bonuses.IncomeEconomy);
      }

      if (bonuses.MonthlyLegacy != 0.0F) {
         json.writeValue("MonthlyLegacy", bonuses.MonthlyLegacy);
      }

      if (bonuses.MonthlyLegacy_Percentage != 0.0F) {
         json.writeValue("MonthlyLegacy_Percentage", bonuses.MonthlyLegacy_Percentage);
      }

      if (bonuses.MaxManpower != 0.0F) {
         json.writeValue("MaxManpower", bonuses.MaxManpower);
      }

      if (bonuses.MaxManpower_Percentage != 0.0F) {
         json.writeValue("MaxManpower_Percentage", bonuses.MaxManpower_Percentage);
      }

      if (bonuses.ManpowerRecoverySpeed != 0.0F) {
         json.writeValue("ManpowerRecoverySpeed", bonuses.ManpowerRecoverySpeed);
      }

      if (bonuses.ReinforcementSpeed != 0.0F) {
         json.writeValue("ReinforcementSpeed", bonuses.ReinforcementSpeed);
      }

      if (bonuses.ArmyMoraleRecovery != 0.0F) {
         json.writeValue("ArmyMoraleRecovery", bonuses.ArmyMoraleRecovery);
      }

      if (bonuses.WarScoreCost != 0.0F) {
         json.writeValue("WarScoreCost", bonuses.WarScoreCost);
      }

      if (bonuses.ArmyMaintenance != 0.0F) {
         json.writeValue("ArmyMaintenance", bonuses.ArmyMaintenance);
      }

      if (bonuses.RecruitmentTime != 0.0F) {
         json.writeValue("RecruitmentTime", bonuses.RecruitmentTime);
      }

      if (bonuses.RecruitArmyCost != 0.0F) {
         json.writeValue("RecruitArmyCost", bonuses.RecruitArmyCost);
      }

      if (bonuses.RecruitArmyFirstLineCost != 0.0F) {
         json.writeValue("RecruitArmyFirstLineCost", bonuses.RecruitArmyFirstLineCost);
      }

      if (bonuses.RecruitArmySecondLineCost != 0.0F) {
         json.writeValue("RecruitArmySecondLineCost", bonuses.RecruitArmySecondLineCost);
      }

      if (bonuses.Research != 0.0F) {
         json.writeValue("Research", bonuses.Research);
      }

      if (bonuses.ResearchPoints != 0.0F) {
         json.writeValue("ResearchPoints", bonuses.ResearchPoints);
      }

      if (bonuses.TechnologyCost != 0.0F) {
         json.writeValue("TechnologyCost", bonuses.TechnologyCost);
      }

      if (bonuses.ConstructionCost != 0.0F) {
         json.writeValue("ConstructionCost", bonuses.ConstructionCost);
      }

      if (bonuses.AdministrationBuildingsCost != 0.0F) {
         json.writeValue("AdministrationBuildingsCost", bonuses.AdministrationBuildingsCost);
      }

      if (bonuses.MilitaryBuildingsCost != 0.0F) {
         json.writeValue("MilitaryBuildingsCost", bonuses.MilitaryBuildingsCost);
      }

      if (bonuses.EconomyBuildingsCost != 0.0F) {
         json.writeValue("EconomyBuildingsCost", bonuses.EconomyBuildingsCost);
      }

      if (bonuses.WonderConstructionCost != 0.0F) {
         json.writeValue("WonderConstructionCost", bonuses.WonderConstructionCost);
      }

      if (bonuses.ConstructionTime != 0.0F) {
         json.writeValue("ConstructionTime", bonuses.ConstructionTime);
      }

      if (bonuses.BuildingSlot != 0) {
         json.writeValue("BuildingSlot", bonuses.BuildingSlot);
      }

      if (bonuses.MaxInfrastructure != 0) {
         json.writeValue("MaxInfrastructure", bonuses.MaxInfrastructure);
      }

      if (bonuses.InvestInEconomyCost != 0.0F) {
         json.writeValue("InvestInEconomyCost", bonuses.InvestInEconomyCost);
      }

      if (bonuses.IncreaseManpowerCost != 0.0F) {
         json.writeValue("IncreaseManpowerCost", bonuses.IncreaseManpowerCost);
      }

      if (bonuses.IncreaseTaxEfficiencyCost != 0.0F) {
         json.writeValue("IncreaseTaxEfficiencyCost", bonuses.IncreaseTaxEfficiencyCost);
      }

      if (bonuses.DevelopInfrastructureCost != 0.0F) {
         json.writeValue("DevelopInfrastructureCost", bonuses.DevelopInfrastructureCost);
      }

      if (bonuses.IncreaseGrowthRateCost != 0.0F) {
         json.writeValue("IncreaseGrowthRateCost", bonuses.IncreaseGrowthRateCost);
      }

      if (bonuses.GeneralAttack != 0) {
         json.writeValue("GeneralAttack", bonuses.GeneralAttack);
      }

      if (bonuses.GeneralDefense != 0) {
         json.writeValue("GeneralDefense", bonuses.GeneralDefense);
      }

      if (bonuses.UnitsAttack != 0) {
         json.writeValue("UnitsAttack", bonuses.UnitsAttack);
      }

      if (bonuses.UnitsDefense != 0) {
         json.writeValue("UnitsDefense", bonuses.UnitsDefense);
      }

      if (bonuses.MaxMorale != 0.0F) {
         json.writeValue("MaxMorale", bonuses.MaxMorale);
      }

      if (bonuses.ArmyMovementSpeed != 0.0F) {
         json.writeValue("ArmyMovementSpeed", bonuses.ArmyMovementSpeed);
      }

      if (bonuses.SiegeEffectiveness != 0.0F) {
         json.writeValue("SiegeEffectiveness", bonuses.SiegeEffectiveness);
      }

      if (bonuses.ImproveRelationsModifier != 0.0F) {
         json.writeValue("ImproveRelationsModifier", bonuses.ImproveRelationsModifier);
      }

      if (bonuses.IncomeFromVassals != 0.0F) {
         json.writeValue("IncomeFromVassals", bonuses.IncomeFromVassals);
      }

      if (bonuses.LoanInterest != 0.0F) {
         json.writeValue("LoanInterest", bonuses.LoanInterest);
      }

      if (bonuses.AggressiveExpansion != 0.0F) {
         json.writeValue("AggressiveExpansion", bonuses.AggressiveExpansion);
      }

      if (bonuses.MaxNumOfAlliances != 0) {
         json.writeValue("MaxNumOfAlliances", bonuses.MaxNumOfAlliances);
      }

      if (bonuses.RevolutionaryRisk != 0.0F) {
         json.writeValue("RevolutionaryRisk", bonuses.RevolutionaryRisk);
      }

      if (bonuses.CoreCost != 0.0F) {
         json.writeValue("CoreCost", bonuses.CoreCost);
      }

      if (bonuses.ReligionCost != 0.0F) {
         json.writeValue("ReligionCost", bonuses.ReligionCost);
      }

      if (bonuses.AdvisorCost != 0.0F) {
         json.writeValue("AdvisorCost", bonuses.AdvisorCost);
      }

      if (bonuses.GeneralCost != 0.0F) {
         json.writeValue("GeneralCost", bonuses.GeneralCost);
      }

      if (bonuses.AdvisorMaxLevel != 0) {
         json.writeValue("AdvisorMaxLevel", bonuses.AdvisorMaxLevel);
      }

      if (bonuses.AdvisorPoolSize != 0) {
         json.writeValue("AdvisorPoolSize", bonuses.AdvisorPoolSize);
      }

      if (bonuses.MaxNumberOfLoans != 0) {
         json.writeValue("MaxNumberOfLoans", bonuses.MaxNumberOfLoans);
      }

      if (bonuses.DiseaseDeathRate != 0.0F) {
         json.writeValue("DiseaseDeathRate", bonuses.DiseaseDeathRate);
      }

      if (bonuses.DiplomacyPoints != 0.0F) {
         json.writeValue("DiplomacyPoints", bonuses.DiplomacyPoints);
      }

      if (bonuses.Devastation != 0.0F) {
         json.writeValue("Devastation", bonuses.Devastation);
      }

      if (bonuses.MaximumLevelOfTheMilitaryAcademyForGenerals != 0) {
         json.writeValue("MaximumLevelOfTheMilitaryAcademyForGenerals", bonuses.MaximumLevelOfTheMilitaryAcademyForGenerals);
      }

      if (bonuses.MaximumLevelOfTheMilitaryAcademy != 0) {
         json.writeValue("MaximumLevelOfTheMilitaryAcademy", bonuses.MaximumLevelOfTheMilitaryAcademy);
      }

      if (bonuses.MaximumLevelOfTheSupremeCourt != 0) {
         json.writeValue("MaximumLevelOfTheSupremeCourt", bonuses.MaximumLevelOfTheSupremeCourt);
      }

      if (bonuses.MaximumLevelOfNuclearReactor != 0) {
         json.writeValue("MaximumLevelOfNuclearReactor", bonuses.MaximumLevelOfNuclearReactor);
      }

      if (bonuses.MaximumLevelOfCapitalCity != 0) {
         json.writeValue("MaximumLevelOfCapitalCity", bonuses.MaximumLevelOfCapitalCity);
      }

      if (bonuses.BattleWidth != 0) {
         json.writeValue("BattleWidth", bonuses.BattleWidth);
      }

      if (bonuses.Discipline != 0.0F) {
         json.writeValue("Discipline", bonuses.Discipline);
      }

      if (bonuses.ManpowerRecoveryFromADisbandedArmy != 0.0F) {
         json.writeValue("ManpowerRecoveryFromADisbandedArmy", bonuses.ManpowerRecoveryFromADisbandedArmy);
      }

      if (bonuses.MaximumAmountOfGold != 0.0F) {
         json.writeValue("MaximumAmountOfGold", bonuses.MaximumAmountOfGold);
      }

      if (bonuses.MaximumAmountOfGold_Percentage != 0.0F) {
         json.writeValue("MaximumAmountOfGold_Percentage", bonuses.MaximumAmountOfGold_Percentage);
      }

      if (bonuses.Loot != 0.0F) {
         json.writeValue("Loot", bonuses.Loot);
      }

      if (bonuses.AllCharactersLifeExpectancy != 0) {
         json.writeValue("AllCharactersLifeExpectancy", bonuses.AllCharactersLifeExpectancy);
      }

      if (bonuses.RegimentsLimit != 0) {
         json.writeValue("RegimentsLimit", bonuses.RegimentsLimit);
      }

      if (bonuses.Inflation != 0.0F) {
         json.writeValue("Inflation", bonuses.Inflation);
      }

      if (bonuses.Corruption != 0.0F) {
         json.writeValue("Corruption", bonuses.Corruption);
      }

      json.writeObjectEnd();
   }

   public CivilizationBonuses read(Json json, JsonValue jsonData, Class type) {
      CivilizationBonuses bonuses = new CivilizationBonuses();
      bonuses.TempTurnID = jsonData.getInt("TempTurnID", 0);
      bonuses.MonthlyIncome = jsonData.getFloat("MonthlyIncome", 0.0F);
      bonuses.TaxEfficiency = jsonData.getFloat("TaxEfficiency", 0.0F);
      bonuses.ProvinceMaintenance = jsonData.getFloat("ProvinceMaintenance", 0.0F);
      bonuses.BuildingsMaintenanceCost = jsonData.getFloat("BuildingsMaintenanceCost", 0.0F);
      bonuses.GrowthRate = jsonData.getFloat("GrowthRate", 0.0F);
      bonuses.MaintenanceCost = jsonData.getFloat("MaintenanceCost", 0.0F);
      bonuses.ProductionEfficiency = jsonData.getFloat("ProductionEfficiency", 0.0F);
      bonuses.IncomeProduction = jsonData.getFloat("IncomeProduction", 0.0F);
      bonuses.IncomeTaxation = jsonData.getFloat("IncomeTaxation", 0.0F);
      bonuses.IncomeEconomy = jsonData.getFloat("IncomeEconomy", 0.0F);
      bonuses.MonthlyLegacy = jsonData.getFloat("MonthlyLegacy", 0.0F);
      bonuses.MonthlyLegacy_Percentage = jsonData.getFloat("MonthlyLegacy_Percentage", 0.0F);
      bonuses.MaxManpower = jsonData.getFloat("MaxManpower", 0.0F);
      bonuses.MaxManpower_Percentage = jsonData.getFloat("MaxManpower_Percentage", 0.0F);
      bonuses.ManpowerRecoverySpeed = jsonData.getFloat("ManpowerRecoverySpeed", 0.0F);
      bonuses.ReinforcementSpeed = jsonData.getFloat("ReinforcementSpeed", 0.0F);
      bonuses.ArmyMoraleRecovery = jsonData.getFloat("ArmyMoraleRecovery", 0.0F);
      bonuses.WarScoreCost = jsonData.getFloat("WarScoreCost", 0.0F);
      bonuses.ArmyMaintenance = jsonData.getFloat("ArmyMaintenance", 0.0F);
      bonuses.RecruitmentTime = jsonData.getFloat("RecruitmentTime", 0.0F);
      bonuses.RecruitArmyCost = jsonData.getFloat("RecruitArmyCost", 0.0F);
      bonuses.RecruitArmyFirstLineCost = jsonData.getFloat("RecruitArmyFirstLineCost", 0.0F);
      bonuses.RecruitArmySecondLineCost = jsonData.getFloat("RecruitArmySecondLineCost", 0.0F);
      bonuses.Research = jsonData.getFloat("Research", 0.0F);
      bonuses.ResearchPoints = jsonData.getFloat("ResearchPoints", 0.0F);
      bonuses.TechnologyCost = jsonData.getFloat("TechnologyCost", 0.0F);
      bonuses.ConstructionCost = jsonData.getFloat("ConstructionCost", 0.0F);
      bonuses.AdministrationBuildingsCost = jsonData.getFloat("AdministrationBuildingsCost", 0.0F);
      bonuses.MilitaryBuildingsCost = jsonData.getFloat("MilitaryBuildingsCost", 0.0F);
      bonuses.EconomyBuildingsCost = jsonData.getFloat("EconomyBuildingsCost", 0.0F);
      bonuses.WonderConstructionCost = jsonData.getFloat("WonderConstructionCost", 0.0F);
      bonuses.ConstructionTime = jsonData.getFloat("ConstructionTime", 0.0F);
      bonuses.BuildingSlot = jsonData.getInt("BuildingSlot", 0);
      bonuses.MaxInfrastructure = jsonData.getInt("MaxInfrastructure", 0);
      bonuses.InvestInEconomyCost = jsonData.getFloat("InvestInEconomyCost", 0.0F);
      bonuses.IncreaseManpowerCost = jsonData.getFloat("IncreaseManpowerCost", 0.0F);
      bonuses.IncreaseTaxEfficiencyCost = jsonData.getFloat("IncreaseTaxEfficiencyCost", 0.0F);
      bonuses.DevelopInfrastructureCost = jsonData.getFloat("DevelopInfrastructureCost", 0.0F);
      bonuses.IncreaseGrowthRateCost = jsonData.getFloat("IncreaseGrowthRateCost", 0.0F);
      bonuses.GeneralAttack = jsonData.getInt("GeneralAttack", 0);
      bonuses.GeneralDefense = jsonData.getInt("GeneralDefense", 0);
      bonuses.UnitsAttack = jsonData.getInt("UnitsAttack", 0);
      bonuses.UnitsDefense = jsonData.getInt("UnitsDefense", 0);
      bonuses.MaxMorale = jsonData.getFloat("MaxMorale", 0.0F);
      bonuses.ArmyMovementSpeed = jsonData.getFloat("ArmyMovementSpeed", 0.0F);
      bonuses.SiegeEffectiveness = jsonData.getFloat("SiegeEffectiveness", 0.0F);
      bonuses.ImproveRelationsModifier = jsonData.getFloat("ImproveRelationsModifier", 0.0F);
      bonuses.IncomeFromVassals = jsonData.getFloat("IncomeFromVassals", 0.0F);
      bonuses.LoanInterest = jsonData.getFloat("LoanInterest", 0.0F);
      bonuses.AggressiveExpansion = jsonData.getFloat("AggressiveExpansion", 0.0F);
      bonuses.MaxNumOfAlliances = jsonData.getInt("MaxNumOfAlliances", 0);
      bonuses.RevolutionaryRisk = jsonData.getFloat("RevolutionaryRisk", 0.0F);
      bonuses.CoreCost = jsonData.getFloat("CoreCost", 0.0F);
      bonuses.ReligionCost = jsonData.getFloat("ReligionCost", 0.0F);
      bonuses.AdvisorCost = jsonData.getFloat("AdvisorCost", 0.0F);
      bonuses.GeneralCost = jsonData.getFloat("GeneralCost", 0.0F);
      bonuses.AdvisorMaxLevel = jsonData.getInt("AdvisorMaxLevel", 0);
      bonuses.AdvisorPoolSize = jsonData.getInt("AdvisorPoolSize", 0);
      bonuses.MaxNumberOfLoans = jsonData.getInt("MaxNumberOfLoans", 0);
      bonuses.DiseaseDeathRate = jsonData.getFloat("DiseaseDeathRate", 0.0F);
      bonuses.DiplomacyPoints = jsonData.getFloat("DiplomacyPoints", 0.0F);
      bonuses.Devastation = jsonData.getFloat("Devastation", 0.0F);
      bonuses.MaximumLevelOfTheMilitaryAcademyForGenerals = jsonData.getInt("MaximumLevelOfTheMilitaryAcademyForGenerals", 0);
      bonuses.MaximumLevelOfTheMilitaryAcademy = jsonData.getInt("MaximumLevelOfTheMilitaryAcademy", 0);
      bonuses.MaximumLevelOfTheSupremeCourt = jsonData.getInt("MaximumLevelOfTheSupremeCourt", 0);
      bonuses.MaximumLevelOfNuclearReactor = jsonData.getInt("MaximumLevelOfNuclearReactor", 0);
      bonuses.MaximumLevelOfCapitalCity = jsonData.getInt("MaximumLevelOfCapitalCity", 0);
      bonuses.BattleWidth = jsonData.getInt("BattleWidth", 0);
      bonuses.Discipline = jsonData.getFloat("Discipline", 0.0F);
      bonuses.ManpowerRecoveryFromADisbandedArmy = jsonData.getFloat("ManpowerRecoveryFromADisbandedArmy", 0.0F);
      bonuses.MaximumAmountOfGold = jsonData.getFloat("MaximumAmountOfGold", 0.0F);
      bonuses.MaximumAmountOfGold_Percentage = jsonData.getFloat("MaximumAmountOfGold_Percentage", 0.0F);
      bonuses.Loot = jsonData.getFloat("Loot", 0.0F);
      bonuses.AllCharactersLifeExpectancy = jsonData.getInt("AllCharactersLifeExpectancy", 0);
      bonuses.RegimentsLimit = jsonData.getInt("RegimentsLimit", 0);
      bonuses.Inflation = jsonData.getFloat("Inflation", 0.0F);
      bonuses.Corruption = jsonData.getFloat("Corruption", 0.0F);
      return bonuses;
   }
}
