package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.province.ProvinceBonuses;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class WondersManager {
   public static List<WondersManager.Wonders> wonders = new ArrayList<>();
   public static int wondersSize = 0;
   public static List<Image> wonderImages = new ArrayList<>();

   public static final void loadWonders() {
      try {
         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "Wonders.json").exists()) {
            FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "Wonders.json");
            String fileContent = fileList.readString();
            Json json = new Json();
            json.setElementType(WondersManager.ConfigWondersData.class, "Wonders", WondersManager.Wonders.class);
            WondersManager.ConfigWondersData data = json.fromJson(WondersManager.ConfigWondersData.class, fileContent);
            int id = 0;

            for (Object e : data.Wonders) {
               wonders.add((WondersManager.Wonders)e);
            }
         }
      } catch (GdxRuntimeException var7) {
         CFG.exceptionStack(var7);
      }

      wondersSize = wonders.size();

      for (int i = 0; i < wondersSize; i++) {
         wonders.get(i).Name = Game.lang.get(wonders.get(i).Name);
      }

      loadWondersImages();
   }

   public static final void loadWondersImages() {
      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "wondersImages/" + "numOfImages.txt").exists()) {
         FileHandle tempFileT = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "wondersImages/" + "numOfImages.txt");
         int numOfImages = Integer.parseInt(tempFileT.readString());

         for (int i = 0; i < numOfImages; i++) {
            if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "wondersImages/" + CFG.getRescouresPath_Short() + i + ".png")
               .exists()) {
               wonderImages.add(
                  new Image(
                     ImageManager.loadTexture_RGB888(
                        "map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "wondersImages/" + CFG.getRescouresPath_Short() + i + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else {
               wonderImages.add(
                  new Image(
                     ImageManager.loadTexture_RGB888(
                        "map/" + Game.map.getFile_ActiveMap_Path() + "wonders/" + "wondersImages/" + CFG.getRescouresPath_Short_H() + i + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            }
         }
      }
   }

   public static final void initProvinceWonders() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         Game.getProvince(i).wonderID = -1;
         Game.getProvince(i).setWonderBuilt(false);
      }

      int i = 0;

      while (i < wondersSize) {
         Game.getProvince(wonders.get(i).ProvinceID).wonderID = i++;
      }
   }

   public static final void updateCivBonuses(int iCivID, int iWonderID, int mod) {
      CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MonthlyIncome = var10000.MonthlyIncome + wonders.get(iWonderID).MonthlyIncome * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.TaxEfficiency = var10000.TaxEfficiency + wonders.get(iWonderID).ProductionEfficiency * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ProductionEfficiency = var10000.ProductionEfficiency + wonders.get(iWonderID).ProductionEfficiency * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ProvinceMaintenance = var10000.ProvinceMaintenance + wonders.get(iWonderID).ProvinceMaintenance * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.BuildingsMaintenanceCost = var10000.BuildingsMaintenanceCost + wonders.get(iWonderID).BuildingsMaintenanceCost * mod;
      if (wonders.get(iWonderID).GrowthRate != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.GrowthRate = var10000.GrowthRate + wonders.get(iWonderID).GrowthRate * mod;
         Game.getCiv(iCivID).updateResearchPerMonth();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (wonders.get(iWonderID).MonthlyLegacy != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.MonthlyLegacy = var10000.MonthlyLegacy + wonders.get(iWonderID).MonthlyLegacy * mod;
         Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
      }

      if (wonders.get(iWonderID).MaxManpower != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.MaxManpower = var10000.MaxManpower + wonders.get(iWonderID).MaxManpower * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (wonders.get(iWonderID).ManpowerRecoverySpeed != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.ManpowerRecoverySpeed = var10000.ManpowerRecoverySpeed + wonders.get(iWonderID).ManpowerRecoverySpeed * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (wonders.get(iWonderID).ArmyMaintenance != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.ArmyMaintenance = var10000.ArmyMaintenance + wonders.get(iWonderID).ArmyMaintenance * mod;
         Game.gameThread.addCivUpdateArmyMaintenance(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitmentTime = var10000.RecruitmentTime + wonders.get(iWonderID).RecruitmentTime * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitArmyCost = var10000.RecruitArmyCost + wonders.get(iWonderID).RecruitArmyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitArmyFirstLineCost = var10000.RecruitArmyFirstLineCost + wonders.get(iWonderID).RecruitArmyFirstLineCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitArmySecondLineCost = var10000.RecruitArmySecondLineCost + wonders.get(iWonderID).RecruitArmySecondLineCost * mod;
      if (wonders.get(iWonderID).ResearchPoints != 0.0F) {
         Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ResearchPoints = var10000.ResearchPoints + wonders.get(iWonderID).ResearchPoints * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.TechnologyCost = var10000.TechnologyCost + wonders.get(iWonderID).TechnologyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ConstructionCost = var10000.ConstructionCost + wonders.get(iWonderID).ConstructionCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AdministrationBuildingsCost = var10000.AdministrationBuildingsCost + wonders.get(iWonderID).AdministrationBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MilitaryBuildingsCost = var10000.MilitaryBuildingsCost + wonders.get(iWonderID).MilitaryBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.EconomyBuildingsCost = var10000.EconomyBuildingsCost + wonders.get(iWonderID).EconomyBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ConstructionTime = var10000.ConstructionTime + wonders.get(iWonderID).ConstructionTimeBonus * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.DevelopInfrastructureCost = var10000.DevelopInfrastructureCost + wonders.get(iWonderID).DevelopInfrastructureCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseTaxEfficiencyCost = var10000.IncreaseTaxEfficiencyCost + wonders.get(iWonderID).IncreaseTaxEfficiencyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseGrowthRateCost = var10000.IncreaseGrowthRateCost + wonders.get(iWonderID).IncreaseGrowthRateCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.InvestInEconomyCost = var10000.InvestInEconomyCost + wonders.get(iWonderID).InvestInEconomyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseManpowerCost = var10000.IncreaseManpowerCost + wonders.get(iWonderID).IncreaseManpowerCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.GeneralAttack = var10000.GeneralAttack + wonders.get(iWonderID).GeneralAttack * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.GeneralDefense = var10000.GeneralDefense + wonders.get(iWonderID).GeneralDefense * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.UnitsAttack = var10000.UnitsAttack + wonders.get(iWonderID).UnitsAttack * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.UnitsDefense = var10000.UnitsDefense + wonders.get(iWonderID).UnitsDefense * mod;
      if (wonders.get(iWonderID).MaxInfrastructure != 0) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.MaxInfrastructure = var10000.MaxInfrastructure + wonders.get(iWonderID).MaxInfrastructure * mod;
         Game.getCiv(iCivID).updateInfrastructureMax();
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AdvisorMaxLevel = var10000.AdvisorMaxLevel + wonders.get(iWonderID).AdvisorMaxLevel * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AdvisorPoolSize = var10000.AdvisorPoolSize + wonders.get(iWonderID).AdvisorPoolSize * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MaxMorale = var10000.MaxMorale + wonders.get(iWonderID).MaxMorale * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ArmyMovementSpeed = var10000.ArmyMovementSpeed + wonders.get(iWonderID).ArmyMovementSpeed * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.SiegeEffectiveness = var10000.SiegeEffectiveness + wonders.get(iWonderID).SiegeEffectiveness * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ImproveRelationsModifier = var10000.ImproveRelationsModifier + wonders.get(iWonderID).ImproveRelationsModifier * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncomeFromVassals = var10000.IncomeFromVassals + wonders.get(iWonderID).IncomeFromVassals * mod;
      if (wonders.get(iWonderID).DiplomacyPoints != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.DiplomacyPoints = var10000.DiplomacyPoints + wonders.get(iWonderID).DiplomacyPoints * mod;
         Game.getCiv(iCivID).updateDiplomacyPerMonth();
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.LoanInterest = var10000.LoanInterest + wonders.get(iWonderID).LoanInterest * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MaxNumberOfLoans = var10000.MaxNumberOfLoans + wonders.get(iWonderID).MaxNumberOfLoans * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AggressiveExpansion = var10000.AggressiveExpansion + wonders.get(iWonderID).AggressiveExpansion * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RevolutionaryRisk = var10000.RevolutionaryRisk + wonders.get(iWonderID).RevolutionaryRisk * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.CoreCost = var10000.CoreCost + wonders.get(iWonderID).CoreCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.DiseaseDeathRate = var10000.DiseaseDeathRate + wonders.get(iWonderID).DiseaseDeathRate * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AllCharactersLifeExpectancy = var10000.AllCharactersLifeExpectancy + wonders.get(iWonderID).AllCharactersLifeExpectancy * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.BattleWidth = var10000.BattleWidth + wonders.get(iWonderID).BattleWidth * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.Discipline = var10000.Discipline + wonders.get(iWonderID).Discipline * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MaximumAmountOfGold = var10000.MaximumAmountOfGold + wonders.get(iWonderID).MaximumAmountOfGold * mod;
      if (wonders.get(iWonderID).RegimentsLimit != 0) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.RegimentsLimit = var10000.RegimentsLimit + wonders.get(iWonderID).RegimentsLimit * mod;
         Game.getCiv(iCivID).updateRegimentsLimit();
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ManpowerRecoveryFromADisbandedArmy = var10000.ManpowerRecoveryFromADisbandedArmy
         + wonders.get(iWonderID).ManpowerRecoveryFromADisbandedArmy * mod;
      Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
   }

   public static final void updateProvinceBonuses(int iProvinceID, int iWonderID, int mod) {
      ProvinceBonuses var10000 = Game.getProvince(iProvinceID).provBonuses;
      var10000.LocalTaxEfficiency = var10000.LocalTaxEfficiency + wonders.get(iWonderID).LocalTaxEfficiency * mod;
      var10000 = Game.getProvince(iProvinceID).provBonuses;
      var10000.Economy = var10000.Economy + wonders.get(iWonderID).Economy * mod;
      var10000 = Game.getProvince(iProvinceID).provBonuses;
      var10000.DefenseBonus = var10000.DefenseBonus + wonders.get(iWonderID).DefenseBonus * mod;
      var10000 = Game.getProvince(iProvinceID).provBonuses;
      var10000.FortLevel = var10000.FortLevel + wonders.get(iWonderID).FortLevel * mod;
      var10000 = Game.getProvince(iProvinceID).provBonuses;
      var10000.FortDefense = var10000.FortDefense + wonders.get(iWonderID).FortDefense * mod;
   }

   public static final float getWonderConstructionCost(int iProvinceID, int iWonderID) {
      return Math.max(
         1.0F,
         wonders.get(iWonderID).CostGold
            * (
               1.0F
                  + Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_WONDER_CONSTRUCTION_COST_PER_LVL
                  + Game.getCiv(Game.getProvince(iProvinceID).getCivID()).civBonuses.WonderConstructionCost
            )
      );
   }

   public static class ConfigWondersData {
      public String Age_of_History;
      public ArrayList Wonders;
   }

   public static class Wonders {
      public String Name;
      public int ImageID;
      public int ProvinceID;
      public int CostGold;
      public float MaintenanceCost;
      public int ConstructionTime;
      public float Legacy;
      public int AI;
      public String Wiki;
      public float MonthlyIncome;
      public float TaxEfficiency;
      public float ProvinceMaintenance;
      public float BuildingsMaintenanceCost;
      public float GrowthRate;
      public float ProductionEfficiency;
      public float MonthlyLegacy;
      public float MaxManpower;
      public float ManpowerRecoverySpeed;
      public float ArmyMaintenance;
      public float RecruitmentTime;
      public float RecruitArmyCost;
      public float RecruitArmyFirstLineCost;
      public float RecruitArmySecondLineCost;
      public float Research;
      public float ResearchPoints;
      public float TechnologyCost;
      public float ConstructionCost;
      public float AdministrationBuildingsCost;
      public float MilitaryBuildingsCost;
      public float EconomyBuildingsCost;
      public float ConstructionTimeBonus;
      public int BuildingSlot;
      public float InvestInEconomyCost;
      public float IncreaseTaxEfficiencyCost;
      public float IncreaseGrowthRateCost;
      public float IncreaseManpowerCost;
      public float DevelopInfrastructureCost;
      public int GeneralAttack;
      public int GeneralDefense;
      public int UnitsAttack;
      public int UnitsDefense;
      public int MaxInfrastructure;
      public int AdvisorMaxLevel;
      public int AdvisorPoolSize;
      public float MaxMorale;
      public float ArmyMovementSpeed;
      public float SiegeEffectiveness;
      public float ImproveRelationsModifier;
      public float IncomeFromVassals;
      public float DiplomacyPoints;
      public float LoanInterest;
      public int MaxNumberOfLoans;
      public float AggressiveExpansion;
      public float RevolutionaryRisk;
      public float CoreCost;
      public float DiseaseDeathRate;
      public int AllCharactersLifeExpectancy;
      public int RegimentsLimit;
      public int BattleWidth;
      public float Discipline;
      public float ManpowerRecoveryFromADisbandedArmy;
      public float MaximumAmountOfGold;
      public float LocalTaxEfficiency;
      public float Economy;
      public int DefenseBonus;
      public int FortLevel;
      public int FortDefense;
   }
}
