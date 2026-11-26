package aoc.kingdoms.lukasz.map.advisors;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class AdvisorSerializer implements Serializer<Advisor> {
   public void write(Json json, Advisor advisor, Class knownType) {
      json.writeObjectStart();
      if (advisor.TaxEfficiency != 0.0F) {
         json.writeValue("TaxEfficiency", advisor.TaxEfficiency);
      }

      if (advisor.ProvinceMaintenance != 0.0F) {
         json.writeValue("ProvinceMaintenance", advisor.ProvinceMaintenance);
      }

      if (advisor.GrowthRate != 0.0F) {
         json.writeValue("GrowthRate", advisor.GrowthRate);
      }

      if (advisor.ProductionEfficiency != 0.0F) {
         json.writeValue("ProductionEfficiency", advisor.ProductionEfficiency);
      }

      if (advisor.IncomeProduction != 0.0F) {
         json.writeValue("IncomeProduction", advisor.IncomeProduction);
      }

      if (advisor.MonthlyLegacy != 0.0F) {
         json.writeValue("MonthlyLegacy", advisor.MonthlyLegacy);
      }

      if (advisor.MaxManpower != 0.0F) {
         json.writeValue("MaxManpower", advisor.MaxManpower);
      }

      if (advisor.ArmyMaintenance != 0.0F) {
         json.writeValue("ArmyMaintenance", advisor.ArmyMaintenance);
      }

      if (advisor.RecruitmentTime != 0.0F) {
         json.writeValue("RecruitmentTime", advisor.RecruitmentTime);
      }

      if (advisor.RecruitArmyCost != 0.0F) {
         json.writeValue("RecruitArmyCost", advisor.RecruitArmyCost);
      }

      if (advisor.Research != 0.0F) {
         json.writeValue("Research", advisor.Research);
      }

      if (advisor.ConstructionCost != 0.0F) {
         json.writeValue("ConstructionCost", advisor.ConstructionCost);
      }

      if (advisor.AdministrationBuildingsCost != 0.0F) {
         json.writeValue("AdministrationBuildingsCost", advisor.AdministrationBuildingsCost);
      }

      if (advisor.MilitaryBuildingsCost != 0.0F) {
         json.writeValue("MilitaryBuildingsCost", advisor.MilitaryBuildingsCost);
      }

      if (advisor.EconomyBuildingsCost != 0.0F) {
         json.writeValue("EconomyBuildingsCost", advisor.EconomyBuildingsCost);
      }

      if (advisor.ConstructionTime != 0.0F) {
         json.writeValue("ConstructionTime", advisor.ConstructionTime);
      }

      if (advisor.InvestInEconomyCost != 0.0F) {
         json.writeValue("InvestInEconomyCost", advisor.InvestInEconomyCost);
      }

      if (advisor.IncreaseManpowerCost != 0.0F) {
         json.writeValue("IncreaseManpowerCost", advisor.IncreaseManpowerCost);
      }

      if (advisor.IncreaseTaxEfficiencyCost != 0.0F) {
         json.writeValue("IncreaseTaxEfficiencyCost", advisor.IncreaseTaxEfficiencyCost);
      }

      if (advisor.IncreaseGrowthRateCost != 0.0F) {
         json.writeValue("IncreaseGrowthRateCost", advisor.IncreaseGrowthRateCost);
      }

      if (advisor.DevelopInfrastructureCost != 0.0F) {
         json.writeValue("DevelopInfrastructureCost", advisor.DevelopInfrastructureCost);
      }

      if (advisor.GeneralAttack != 0.0F) {
         json.writeValue("GeneralAttack", advisor.GeneralAttack);
      }

      if (advisor.GeneralDefense != 0.0F) {
         json.writeValue("GeneralDefense", advisor.GeneralDefense);
      }

      if (advisor.UnitsAttack != 0.0F) {
         json.writeValue("UnitsAttack", advisor.UnitsAttack);
      }

      if (advisor.UnitsDefense != 0.0F) {
         json.writeValue("UnitsDefense", advisor.UnitsDefense);
      }

      if (advisor.MaxMorale != 0.0F) {
         json.writeValue("MaxMorale", advisor.MaxMorale);
      }

      if (advisor.ArmyMovementSpeed != 0.0F) {
         json.writeValue("ArmyMovementSpeed", advisor.ArmyMovementSpeed);
      }

      if (advisor.SiegeEffectiveness != 0.0F) {
         json.writeValue("SiegeEffectiveness", advisor.SiegeEffectiveness);
      }

      if (advisor.ImproveRelationsModifier != 0.0F) {
         json.writeValue("ImproveRelationsModifier", advisor.ImproveRelationsModifier);
      }

      if (advisor.LoanInterest != 0.0F) {
         json.writeValue("LoanInterest", advisor.LoanInterest);
      }

      if (advisor.CoreCost != 0.0F) {
         json.writeValue("CoreCost", advisor.CoreCost);
      }

      if (advisor.ReligionCost != 0.0F) {
         json.writeValue("ReligionCost", advisor.ReligionCost);
      }

      if (advisor.RegimentsLimit != 0) {
         json.writeValue("RegimentsLimit", advisor.RegimentsLimit);
      }

      json.writeObjectEnd();
   }

   public Advisor read(Json json, JsonValue jsonData, Class type) {
      Advisor advisor = new Advisor();
      advisor.sName = jsonData.getString("sName", null);
      advisor.iYearOfBirth = jsonData.getInt("iYearOfBirth", 0);
      advisor.iMonthOfBirth = jsonData.getInt("iMonthOfBirth", 0);
      advisor.iDayOfBirth = jsonData.getInt("iDayOfBirth", 0);
      advisor.sIMG = jsonData.getString("sIMG", null);
      advisor.imageID = jsonData.getInt("imageID", 0);
      advisor.iLevel = jsonData.getInt("iLevel", 1);
      advisor.TaxEfficiency = jsonData.getFloat("TaxEfficiency", 0.0F);
      advisor.ProvinceMaintenance = jsonData.getFloat("ProvinceMaintenance", 0.0F);
      advisor.GrowthRate = jsonData.getFloat("GrowthRate", 0.0F);
      advisor.ProductionEfficiency = jsonData.getFloat("ProductionEfficiency", 0.0F);
      advisor.IncomeProduction = jsonData.getFloat("IncomeProduction", 0.0F);
      advisor.MonthlyLegacy = jsonData.getFloat("MonthlyLegacy", 0.0F);
      advisor.MaxManpower = jsonData.getFloat("MaxManpower", 0.0F);
      advisor.ArmyMaintenance = jsonData.getFloat("ArmyMaintenance", 0.0F);
      advisor.RecruitmentTime = jsonData.getFloat("RecruitmentTime", 0.0F);
      advisor.RecruitArmyCost = jsonData.getFloat("RecruitArmyCost", 0.0F);
      advisor.Research = jsonData.getFloat("Research", 0.0F);
      advisor.ConstructionCost = jsonData.getFloat("ConstructionCost", 0.0F);
      advisor.AdministrationBuildingsCost = jsonData.getFloat("AdministrationBuildingsCost", 0.0F);
      advisor.MilitaryBuildingsCost = jsonData.getFloat("MilitaryBuildingsCost", 0.0F);
      advisor.EconomyBuildingsCost = jsonData.getFloat("EconomyBuildingsCost", 0.0F);
      advisor.ConstructionTime = jsonData.getFloat("ConstructionTime", 0.0F);
      advisor.InvestInEconomyCost = jsonData.getFloat("InvestInEconomyCost", 0.0F);
      advisor.IncreaseManpowerCost = jsonData.getFloat("IncreaseManpowerCost", 0.0F);
      advisor.IncreaseTaxEfficiencyCost = jsonData.getFloat("IncreaseTaxEfficiencyCost", 0.0F);
      advisor.IncreaseGrowthRateCost = jsonData.getFloat("IncreaseGrowthRateCost", 0.0F);
      advisor.DevelopInfrastructureCost = jsonData.getFloat("DevelopInfrastructureCost", 0.0F);
      advisor.GeneralAttack = jsonData.getFloat("GeneralAttack", 0.0F);
      advisor.GeneralDefense = jsonData.getFloat("GeneralDefense", 0.0F);
      advisor.UnitsAttack = jsonData.getFloat("UnitsAttack", 0.0F);
      advisor.UnitsDefense = jsonData.getFloat("UnitsDefense", 0.0F);
      advisor.MaxMorale = jsonData.getFloat("MaxMorale", 0.0F);
      advisor.ArmyMovementSpeed = jsonData.getFloat("ArmyMovementSpeed", 0.0F);
      advisor.SiegeEffectiveness = jsonData.getFloat("SiegeEffectiveness", 0.0F);
      advisor.ImproveRelationsModifier = jsonData.getFloat("ImproveRelationsModifier", 0.0F);
      advisor.LoanInterest = jsonData.getFloat("LoanInterest", 0.0F);
      advisor.CoreCost = jsonData.getFloat("CoreCost", 0.0F);
      advisor.ReligionCost = jsonData.getFloat("ReligionCost", 0.0F);
      advisor.RegimentsLimit = jsonData.getInt("RegimentsLimit", 0);
      return advisor;
   }
}
