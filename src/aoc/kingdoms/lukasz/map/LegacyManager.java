package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class LegacyManager {
   public static List<String> legacyGroups = new ArrayList<>();
   public static int iLegacyGroupsSize = 0;
   public static List<Image> legacyImages = new ArrayList<>();
   public static List<LegacyManager.Legacy> legacies = new ArrayList<>();
   public static int iLegaciesSize = 0;
   public static int minLegacyCost = 999666;
   public static List<Integer> numOfLegaciesInGroup = new ArrayList<>();

   public static final void updateCivBonuses(int i, int level, int iCivID) {
      updateCivBonuses(i, level, iCivID, false);
   }

   public static final void updateCivBonuses(int i, int level, int iCivID, boolean load) {
      if (legacies.get(i).TaxEfficiency != null) {
         CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.TaxEfficiency = var10000.TaxEfficiency + legacies.get(i).TaxEfficiency[level];
         if (level > 0 && !load) {
            var10000 = Game.getCiv(iCivID).civBonuses;
            var10000.TaxEfficiency = var10000.TaxEfficiency - legacies.get(i).TaxEfficiency[level - 1];
         }
      }

      if (legacies.get(i).ProvinceMaintenance != null) {
         CivilizationBonuses var5 = Game.getCiv(iCivID).civBonuses;
         var5.ProvinceMaintenance = var5.ProvinceMaintenance + legacies.get(i).ProvinceMaintenance[level];
         if (level > 0 && !load) {
            var5 = Game.getCiv(iCivID).civBonuses;
            var5.ProvinceMaintenance = var5.ProvinceMaintenance - legacies.get(i).ProvinceMaintenance[level - 1];
         }
      }

      if (legacies.get(i).BuildingsMaintenanceCost != null) {
         CivilizationBonuses var7 = Game.getCiv(iCivID).civBonuses;
         var7.BuildingsMaintenanceCost = var7.BuildingsMaintenanceCost + legacies.get(i).BuildingsMaintenanceCost[level];
         if (level > 0 && !load) {
            var7 = Game.getCiv(iCivID).civBonuses;
            var7.BuildingsMaintenanceCost = var7.BuildingsMaintenanceCost - legacies.get(i).BuildingsMaintenanceCost[level - 1];
         }
      }

      if (legacies.get(i).ConstructionTime != null) {
         CivilizationBonuses var9 = Game.getCiv(iCivID).civBonuses;
         var9.ConstructionTime = var9.ConstructionTime + legacies.get(i).ConstructionTime[level];
         if (level > 0 && !load) {
            var9 = Game.getCiv(iCivID).civBonuses;
            var9.ConstructionTime = var9.ConstructionTime - legacies.get(i).ConstructionTime[level - 1];
         }
      }

      if (legacies.get(i).ConstructionCost != null) {
         CivilizationBonuses var11 = Game.getCiv(iCivID).civBonuses;
         var11.ConstructionCost = var11.ConstructionCost + legacies.get(i).ConstructionCost[level];
         if (level > 0 && !load) {
            var11 = Game.getCiv(iCivID).civBonuses;
            var11.ConstructionCost = var11.ConstructionCost - legacies.get(i).ConstructionCost[level - 1];
         }
      }

      if (legacies.get(i).AdministrationBuildingsCost != null) {
         CivilizationBonuses var13 = Game.getCiv(iCivID).civBonuses;
         var13.AdministrationBuildingsCost = var13.AdministrationBuildingsCost + legacies.get(i).AdministrationBuildingsCost[level];
         if (level > 0 && !load) {
            var13 = Game.getCiv(iCivID).civBonuses;
            var13.AdministrationBuildingsCost = var13.AdministrationBuildingsCost - legacies.get(i).AdministrationBuildingsCost[level - 1];
         }
      }

      if (legacies.get(i).MilitaryBuildingsCost != null) {
         CivilizationBonuses var15 = Game.getCiv(iCivID).civBonuses;
         var15.MilitaryBuildingsCost = var15.MilitaryBuildingsCost + legacies.get(i).MilitaryBuildingsCost[level];
         if (level > 0 && !load) {
            var15 = Game.getCiv(iCivID).civBonuses;
            var15.MilitaryBuildingsCost = var15.MilitaryBuildingsCost - legacies.get(i).MilitaryBuildingsCost[level - 1];
         }
      }

      if (legacies.get(i).EconomyBuildingsCost != null) {
         CivilizationBonuses var17 = Game.getCiv(iCivID).civBonuses;
         var17.EconomyBuildingsCost = var17.EconomyBuildingsCost + legacies.get(i).EconomyBuildingsCost[level];
         if (level > 0 && !load) {
            var17 = Game.getCiv(iCivID).civBonuses;
            var17.EconomyBuildingsCost = var17.EconomyBuildingsCost - legacies.get(i).EconomyBuildingsCost[level - 1];
         }
      }

      if (legacies.get(i).WonderConstructionCost != null) {
         CivilizationBonuses var19 = Game.getCiv(iCivID).civBonuses;
         var19.WonderConstructionCost = var19.WonderConstructionCost + legacies.get(i).WonderConstructionCost[level];
         if (level > 0 && !load) {
            var19 = Game.getCiv(iCivID).civBonuses;
            var19.WonderConstructionCost = var19.WonderConstructionCost - legacies.get(i).WonderConstructionCost[level - 1];
         }
      }

      if (legacies.get(i).MaxManpower != null) {
         CivilizationBonuses var21 = Game.getCiv(iCivID).civBonuses;
         var21.MaxManpower = var21.MaxManpower + legacies.get(i).MaxManpower[level];
         if (level > 0 && !load) {
            var21 = Game.getCiv(iCivID).civBonuses;
            var21.MaxManpower = var21.MaxManpower - legacies.get(i).MaxManpower[level - 1];
         }

         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (legacies.get(i).ManpowerRecoverySpeed != null) {
         CivilizationBonuses var23 = Game.getCiv(iCivID).civBonuses;
         var23.ManpowerRecoverySpeed = var23.ManpowerRecoverySpeed + legacies.get(i).ManpowerRecoverySpeed[level];
         if (level > 0 && !load) {
            var23 = Game.getCiv(iCivID).civBonuses;
            var23.ManpowerRecoverySpeed = var23.ManpowerRecoverySpeed - legacies.get(i).ManpowerRecoverySpeed[level - 1];
         }

         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (legacies.get(i).Research != null) {
         CivilizationBonuses var25 = Game.getCiv(iCivID).civBonuses;
         var25.Research = var25.Research + legacies.get(i).Research[level];
         if (level > 0 && !load) {
            var25 = Game.getCiv(iCivID).civBonuses;
            var25.Research = var25.Research - legacies.get(i).Research[level - 1];
         }

         Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (legacies.get(i).ResearchPoints != null) {
         CivilizationBonuses var27 = Game.getCiv(iCivID).civBonuses;
         var27.ResearchPoints = var27.ResearchPoints + legacies.get(i).ResearchPoints[level];
         if (level > 0 && !load) {
            var27 = Game.getCiv(iCivID).civBonuses;
            var27.ResearchPoints = var27.ResearchPoints - legacies.get(i).ResearchPoints[level - 1];
         }

         Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
      }

      if (legacies.get(i).Devastation != null) {
         CivilizationBonuses var29 = Game.getCiv(iCivID).civBonuses;
         var29.Devastation = var29.Devastation + legacies.get(i).Devastation[level];
         if (level > 0 && !load) {
            var29 = Game.getCiv(iCivID).civBonuses;
            var29.Devastation = var29.Devastation - legacies.get(i).Devastation[level - 1];
         }
      }

      if (legacies.get(i).BuildingSlot != null) {
         CivilizationBonuses var31 = Game.getCiv(iCivID).civBonuses;
         var31.BuildingSlot = var31.BuildingSlot + legacies.get(i).BuildingSlot[level];
         if (level > 0 && !load) {
            var31 = Game.getCiv(iCivID).civBonuses;
            var31.BuildingSlot = var31.BuildingSlot - legacies.get(i).BuildingSlot[level - 1];
         }

         Game.getCiv(iCivID).updateBuildingLimit();
      }

      if (legacies.get(i).MaxInfrastructure != null) {
         CivilizationBonuses var33 = Game.getCiv(iCivID).civBonuses;
         var33.MaxInfrastructure = var33.MaxInfrastructure + legacies.get(i).MaxInfrastructure[level];
         if (level > 0 && !load) {
            var33 = Game.getCiv(iCivID).civBonuses;
            var33.MaxInfrastructure = var33.MaxInfrastructure - legacies.get(i).MaxInfrastructure[level - 1];
         }

         Game.getCiv(iCivID).updateInfrastructureMax();
      }

      if (legacies.get(i).GrowthRate != null) {
         CivilizationBonuses var35 = Game.getCiv(iCivID).civBonuses;
         var35.GrowthRate = var35.GrowthRate + legacies.get(i).GrowthRate[level];
         if (level > 0 && !load) {
            var35 = Game.getCiv(iCivID).civBonuses;
            var35.GrowthRate = var35.GrowthRate - legacies.get(i).GrowthRate[level - 1];
         }

         Game.getCiv(iCivID).updateResearchPerMonth();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (legacies.get(i).IncomeProduction != null) {
         CivilizationBonuses var37 = Game.getCiv(iCivID).civBonuses;
         var37.IncomeProduction = var37.IncomeProduction + legacies.get(i).IncomeProduction[level];
         if (level > 0 && !load) {
            var37 = Game.getCiv(iCivID).civBonuses;
            var37.IncomeProduction = var37.IncomeProduction - legacies.get(i).IncomeProduction[level - 1];
         }
      }

      if (legacies.get(i).ProductionEfficiency != null) {
         CivilizationBonuses var39 = Game.getCiv(iCivID).civBonuses;
         var39.ProductionEfficiency = var39.ProductionEfficiency + legacies.get(i).ProductionEfficiency[level];
         if (level > 0 && !load) {
            var39 = Game.getCiv(iCivID).civBonuses;
            var39.ProductionEfficiency = var39.ProductionEfficiency - legacies.get(i).ProductionEfficiency[level - 1];
         }
      }

      if (legacies.get(i).InvestInEconomyCost != null) {
         CivilizationBonuses var41 = Game.getCiv(iCivID).civBonuses;
         var41.InvestInEconomyCost = var41.InvestInEconomyCost + legacies.get(i).InvestInEconomyCost[level];
         if (level > 0 && !load) {
            var41 = Game.getCiv(iCivID).civBonuses;
            var41.InvestInEconomyCost = var41.InvestInEconomyCost - legacies.get(i).InvestInEconomyCost[level - 1];
         }
      }

      if (legacies.get(i).IncreaseTaxEfficiencyCost != null) {
         CivilizationBonuses var43 = Game.getCiv(iCivID).civBonuses;
         var43.IncreaseTaxEfficiencyCost = var43.IncreaseTaxEfficiencyCost + legacies.get(i).IncreaseTaxEfficiencyCost[level];
         if (level > 0 && !load) {
            var43 = Game.getCiv(iCivID).civBonuses;
            var43.IncreaseTaxEfficiencyCost = var43.IncreaseTaxEfficiencyCost - legacies.get(i).IncreaseTaxEfficiencyCost[level - 1];
         }
      }

      if (legacies.get(i).IncreaseGrowthRateCost != null) {
         CivilizationBonuses var45 = Game.getCiv(iCivID).civBonuses;
         var45.IncreaseGrowthRateCost = var45.IncreaseGrowthRateCost + legacies.get(i).IncreaseGrowthRateCost[level];
         if (level > 0 && !load) {
            var45 = Game.getCiv(iCivID).civBonuses;
            var45.IncreaseGrowthRateCost = var45.IncreaseGrowthRateCost - legacies.get(i).IncreaseGrowthRateCost[level - 1];
         }
      }

      if (legacies.get(i).DevelopInfrastructureCost != null) {
         CivilizationBonuses var47 = Game.getCiv(iCivID).civBonuses;
         var47.DevelopInfrastructureCost = var47.DevelopInfrastructureCost + legacies.get(i).DevelopInfrastructureCost[level];
         if (level > 0 && !load) {
            var47 = Game.getCiv(iCivID).civBonuses;
            var47.DevelopInfrastructureCost = var47.DevelopInfrastructureCost - legacies.get(i).DevelopInfrastructureCost[level - 1];
         }
      }

      if (legacies.get(i).GeneralAttack != null) {
         CivilizationBonuses var49 = Game.getCiv(iCivID).civBonuses;
         var49.GeneralAttack = var49.GeneralAttack + legacies.get(i).GeneralAttack[level];
         if (level > 0 && !load) {
            var49 = Game.getCiv(iCivID).civBonuses;
            var49.GeneralAttack = var49.GeneralAttack - legacies.get(i).GeneralAttack[level - 1];
         }
      }

      if (legacies.get(i).GeneralDefense != null) {
         CivilizationBonuses var51 = Game.getCiv(iCivID).civBonuses;
         var51.GeneralDefense = var51.GeneralDefense + legacies.get(i).GeneralDefense[level];
         if (level > 0 && !load) {
            var51 = Game.getCiv(iCivID).civBonuses;
            var51.GeneralDefense = var51.GeneralDefense - legacies.get(i).GeneralDefense[level - 1];
         }
      }

      if (legacies.get(i).UnitsAttack != null) {
         CivilizationBonuses var53 = Game.getCiv(iCivID).civBonuses;
         var53.UnitsAttack = var53.UnitsAttack + legacies.get(i).UnitsAttack[level];
         if (level > 0 && !load) {
            var53 = Game.getCiv(iCivID).civBonuses;
            var53.UnitsAttack = var53.UnitsAttack - legacies.get(i).UnitsAttack[level - 1];
         }
      }

      if (legacies.get(i).UnitsDefense != null) {
         CivilizationBonuses var55 = Game.getCiv(iCivID).civBonuses;
         var55.UnitsDefense = var55.UnitsDefense + legacies.get(i).UnitsDefense[level];
         if (level > 0 && !load) {
            var55 = Game.getCiv(iCivID).civBonuses;
            var55.UnitsDefense = var55.UnitsDefense - legacies.get(i).UnitsDefense[level - 1];
         }
      }

      if (legacies.get(i).MaxMorale != null) {
         CivilizationBonuses var57 = Game.getCiv(iCivID).civBonuses;
         var57.MaxMorale = var57.MaxMorale + legacies.get(i).MaxMorale[level];
         if (level > 0 && !load) {
            var57 = Game.getCiv(iCivID).civBonuses;
            var57.MaxMorale = var57.MaxMorale - legacies.get(i).MaxMorale[level - 1];
         }
      }

      if (legacies.get(i).ArmyMovementSpeed != null) {
         CivilizationBonuses var59 = Game.getCiv(iCivID).civBonuses;
         var59.ArmyMovementSpeed = var59.ArmyMovementSpeed + legacies.get(i).ArmyMovementSpeed[level];
         if (level > 0 && !load) {
            var59 = Game.getCiv(iCivID).civBonuses;
            var59.ArmyMovementSpeed = var59.ArmyMovementSpeed - legacies.get(i).ArmyMovementSpeed[level - 1];
         }
      }

      if (legacies.get(i).SiegeEffectiveness != null) {
         CivilizationBonuses var61 = Game.getCiv(iCivID).civBonuses;
         var61.SiegeEffectiveness = var61.SiegeEffectiveness + legacies.get(i).SiegeEffectiveness[level];
         if (level > 0 && !load) {
            var61 = Game.getCiv(iCivID).civBonuses;
            var61.SiegeEffectiveness = var61.SiegeEffectiveness - legacies.get(i).SiegeEffectiveness[level - 1];
         }
      }

      if (legacies.get(i).ImproveRelationsModifier != null) {
         CivilizationBonuses var63 = Game.getCiv(iCivID).civBonuses;
         var63.ImproveRelationsModifier = var63.ImproveRelationsModifier + legacies.get(i).ImproveRelationsModifier[level];
         if (level > 0 && !load) {
            var63 = Game.getCiv(iCivID).civBonuses;
            var63.ImproveRelationsModifier = var63.ImproveRelationsModifier - legacies.get(i).ImproveRelationsModifier[level - 1];
         }
      }

      if (legacies.get(i).IncomeFromVassals != null) {
         CivilizationBonuses var65 = Game.getCiv(iCivID).civBonuses;
         var65.IncomeFromVassals = var65.IncomeFromVassals + legacies.get(i).IncomeFromVassals[level];
         if (level > 0 && !load) {
            var65 = Game.getCiv(iCivID).civBonuses;
            var65.IncomeFromVassals = var65.IncomeFromVassals - legacies.get(i).IncomeFromVassals[level - 1];
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (legacies.get(i).DiplomacyPoints != null) {
         CivilizationBonuses var67 = Game.getCiv(iCivID).civBonuses;
         var67.DiplomacyPoints = var67.DiplomacyPoints + legacies.get(i).DiplomacyPoints[level];
         if (level > 0 && !load) {
            var67 = Game.getCiv(iCivID).civBonuses;
            var67.DiplomacyPoints = var67.DiplomacyPoints - legacies.get(i).DiplomacyPoints[level - 1];
         }

         Game.getCiv(iCivID).updateDiplomacyPerMonth();
      }

      if (legacies.get(i).LoanInterest != null) {
         CivilizationBonuses var69 = Game.getCiv(iCivID).civBonuses;
         var69.LoanInterest = var69.LoanInterest + legacies.get(i).LoanInterest[level];
         if (level > 0 && !load) {
            var69 = Game.getCiv(iCivID).civBonuses;
            var69.LoanInterest = var69.LoanInterest - legacies.get(i).LoanInterest[level - 1];
         }
      }

      if (legacies.get(i).MaxNumberOfLoans != null) {
         CivilizationBonuses var71 = Game.getCiv(iCivID).civBonuses;
         var71.MaxNumberOfLoans = var71.MaxNumberOfLoans + legacies.get(i).MaxNumberOfLoans[level];
         if (level > 0 && !load) {
            var71 = Game.getCiv(iCivID).civBonuses;
            var71.MaxNumberOfLoans = var71.MaxNumberOfLoans - legacies.get(i).MaxNumberOfLoans[level - 1];
         }
      }

      if (legacies.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
         CivilizationBonuses var73 = Game.getCiv(iCivID).civBonuses;
         var73.MaximumLevelOfTheMilitaryAcademyForGenerals = var73.MaximumLevelOfTheMilitaryAcademyForGenerals
            + legacies.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[level];
         if (level > 0 && !load) {
            var73 = Game.getCiv(iCivID).civBonuses;
            var73.MaximumLevelOfTheMilitaryAcademyForGenerals = var73.MaximumLevelOfTheMilitaryAcademyForGenerals
               - legacies.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[level - 1];
         }
      }

      if (legacies.get(i).MaximumLevelOfTheMilitaryAcademy != null) {
         CivilizationBonuses var75 = Game.getCiv(iCivID).civBonuses;
         var75.MaximumLevelOfTheMilitaryAcademy = var75.MaximumLevelOfTheMilitaryAcademy + legacies.get(i).MaximumLevelOfTheMilitaryAcademy[level];
         if (level > 0 && !load) {
            var75 = Game.getCiv(iCivID).civBonuses;
            var75.MaximumLevelOfTheMilitaryAcademy = var75.MaximumLevelOfTheMilitaryAcademy - legacies.get(i).MaximumLevelOfTheMilitaryAcademy[level - 1];
         }
      }

      if (legacies.get(i).MaximumLevelOfTheSupremeCourt != null) {
         CivilizationBonuses var77 = Game.getCiv(iCivID).civBonuses;
         var77.MaximumLevelOfTheSupremeCourt = var77.MaximumLevelOfTheSupremeCourt + legacies.get(i).MaximumLevelOfTheSupremeCourt[level];
         if (level > 0 && !load) {
            var77 = Game.getCiv(iCivID).civBonuses;
            var77.MaximumLevelOfTheSupremeCourt = var77.MaximumLevelOfTheSupremeCourt - legacies.get(i).MaximumLevelOfTheSupremeCourt[level - 1];
         }
      }

      if (legacies.get(i).MaximumLevelOfCapitalCity != null) {
         CivilizationBonuses var79 = Game.getCiv(iCivID).civBonuses;
         var79.MaximumLevelOfCapitalCity = var79.MaximumLevelOfCapitalCity + legacies.get(i).MaximumLevelOfCapitalCity[level];
         if (level > 0 && !load) {
            var79 = Game.getCiv(iCivID).civBonuses;
            var79.MaximumLevelOfCapitalCity = var79.MaximumLevelOfCapitalCity - legacies.get(i).MaximumLevelOfCapitalCity[level - 1];
         }
      }

      if (legacies.get(i).RecruitmentTime != null) {
         CivilizationBonuses var81 = Game.getCiv(iCivID).civBonuses;
         var81.RecruitmentTime = var81.RecruitmentTime + legacies.get(i).RecruitmentTime[level];
         if (level > 0 && !load) {
            var81 = Game.getCiv(iCivID).civBonuses;
            var81.RecruitmentTime = var81.RecruitmentTime - legacies.get(i).RecruitmentTime[level - 1];
         }
      }

      if (legacies.get(i).MaxNumOfAlliances != null) {
         CivilizationBonuses var83 = Game.getCiv(iCivID).civBonuses;
         var83.MaxNumOfAlliances = var83.MaxNumOfAlliances + legacies.get(i).MaxNumOfAlliances[level];
         if (level > 0 && !load) {
            var83 = Game.getCiv(iCivID).civBonuses;
            var83.MaxNumOfAlliances = var83.MaxNumOfAlliances - legacies.get(i).MaxNumOfAlliances[level - 1];
         }
      }

      if (legacies.get(i).AdvisorMaxLevel != null) {
         CivilizationBonuses var85 = Game.getCiv(iCivID).civBonuses;
         var85.AdvisorMaxLevel = var85.AdvisorMaxLevel + legacies.get(i).AdvisorMaxLevel[level];
         if (level > 0 && !load) {
            var85 = Game.getCiv(iCivID).civBonuses;
            var85.AdvisorMaxLevel = var85.AdvisorMaxLevel - legacies.get(i).AdvisorMaxLevel[level - 1];
         }
      }

      if (legacies.get(i).AdvisorPoolSize != null) {
         CivilizationBonuses var87 = Game.getCiv(iCivID).civBonuses;
         var87.AdvisorPoolSize = var87.AdvisorPoolSize + legacies.get(i).AdvisorPoolSize[level];
         if (level > 0 && !load) {
            var87 = Game.getCiv(iCivID).civBonuses;
            var87.AdvisorPoolSize = var87.AdvisorPoolSize - legacies.get(i).AdvisorPoolSize[level - 1];
         }
      }

      if (legacies.get(i).AdvisorCost != null) {
         CivilizationBonuses var89 = Game.getCiv(iCivID).civBonuses;
         var89.AdvisorCost = var89.AdvisorCost + legacies.get(i).AdvisorCost[level];
         if (level > 0 && !load) {
            var89 = Game.getCiv(iCivID).civBonuses;
            var89.AdvisorCost = var89.AdvisorCost - legacies.get(i).AdvisorCost[level - 1];
         }
      }

      if (legacies.get(i).GeneralCost != null) {
         CivilizationBonuses var91 = Game.getCiv(iCivID).civBonuses;
         var91.GeneralCost = var91.GeneralCost + legacies.get(i).GeneralCost[level];
         if (level > 0 && !load) {
            var91 = Game.getCiv(iCivID).civBonuses;
            var91.GeneralCost = var91.GeneralCost - legacies.get(i).GeneralCost[level - 1];
         }
      }

      if (legacies.get(i).AggressiveExpansion != null) {
         CivilizationBonuses var93 = Game.getCiv(iCivID).civBonuses;
         var93.AggressiveExpansion = var93.AggressiveExpansion + legacies.get(i).AggressiveExpansion[level];
         if (level > 0 && !load) {
            var93 = Game.getCiv(iCivID).civBonuses;
            var93.AggressiveExpansion = var93.AggressiveExpansion - legacies.get(i).AggressiveExpansion[level - 1];
         }
      }

      if (legacies.get(i).DiseaseDeathRate != null) {
         CivilizationBonuses var95 = Game.getCiv(iCivID).civBonuses;
         var95.DiseaseDeathRate = var95.DiseaseDeathRate + legacies.get(i).DiseaseDeathRate[level];
         if (level > 0 && !load) {
            var95 = Game.getCiv(iCivID).civBonuses;
            var95.DiseaseDeathRate = var95.DiseaseDeathRate - legacies.get(i).DiseaseDeathRate[level - 1];
         }
      }

      if (legacies.get(i).Discipline != null) {
         CivilizationBonuses var97 = Game.getCiv(iCivID).civBonuses;
         var97.Discipline = var97.Discipline + legacies.get(i).Discipline[level];
         if (level > 0 && !load) {
            var97 = Game.getCiv(iCivID).civBonuses;
            var97.Discipline = var97.Discipline - legacies.get(i).Discipline[level - 1];
         }
      }

      if (legacies.get(i).MaximumAmountOfGold != null) {
         CivilizationBonuses var99 = Game.getCiv(iCivID).civBonuses;
         var99.MaximumAmountOfGold = var99.MaximumAmountOfGold + legacies.get(i).MaximumAmountOfGold[level];
         if (level > 0 && !load) {
            var99 = Game.getCiv(iCivID).civBonuses;
            var99.MaximumAmountOfGold = var99.MaximumAmountOfGold - legacies.get(i).MaximumAmountOfGold[level - 1];
         }
      }

      if (legacies.get(i).Loot != null) {
         CivilizationBonuses var101 = Game.getCiv(iCivID).civBonuses;
         var101.Loot = var101.Loot + legacies.get(i).Loot[level];
         if (level > 0 && !load) {
            var101 = Game.getCiv(iCivID).civBonuses;
            var101.Loot = var101.Loot - legacies.get(i).Loot[level - 1];
         }
      }

      if (legacies.get(i).ManpowerRecoveryFromADisbandedArmy != null) {
         CivilizationBonuses var103 = Game.getCiv(iCivID).civBonuses;
         var103.ManpowerRecoveryFromADisbandedArmy = var103.ManpowerRecoveryFromADisbandedArmy + legacies.get(i).ManpowerRecoveryFromADisbandedArmy[level];
         if (level > 0 && !load) {
            var103 = Game.getCiv(iCivID).civBonuses;
            var103.ManpowerRecoveryFromADisbandedArmy = var103.ManpowerRecoveryFromADisbandedArmy
               - legacies.get(i).ManpowerRecoveryFromADisbandedArmy[level - 1];
         }
      }

      if (legacies.get(i).BattleWidth != null) {
         CivilizationBonuses var105 = Game.getCiv(iCivID).civBonuses;
         var105.BattleWidth = var105.BattleWidth + legacies.get(i).BattleWidth[level];
         if (level > 0 && !load) {
            var105 = Game.getCiv(iCivID).civBonuses;
            var105.BattleWidth = var105.BattleWidth - legacies.get(i).BattleWidth[level - 1];
         }
      }

      if (legacies.get(i).RegimentsLimit != null) {
         CivilizationBonuses var107 = Game.getCiv(iCivID).civBonuses;
         var107.RegimentsLimit = var107.RegimentsLimit + legacies.get(i).RegimentsLimit[level];
         if (level > 0 && !load) {
            var107 = Game.getCiv(iCivID).civBonuses;
            var107.RegimentsLimit = var107.RegimentsLimit - legacies.get(i).RegimentsLimit[level - 1];
         }
      }

      if (legacies.get(i).AllCharactersLifeExpectancy != null) {
         CivilizationBonuses var109 = Game.getCiv(iCivID).civBonuses;
         var109.AllCharactersLifeExpectancy = var109.AllCharactersLifeExpectancy + legacies.get(i).AllCharactersLifeExpectancy[level];
         if (level > 0 && !load) {
            var109 = Game.getCiv(iCivID).civBonuses;
            var109.AllCharactersLifeExpectancy = var109.AllCharactersLifeExpectancy - legacies.get(i).AllCharactersLifeExpectancy[level - 1];
         }
      }

      Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
   }

   public static final void loadLegacies() {
      FileHandle tempFileT = FileManager.loadFile("game/legacies/LegaciesGroups.txt");
      String[] tGroups = tempFileT.readString().split(";");

      for (int i = 0; i < tGroups.length; i++) {
         legacyGroups.add(Game.lang.get(tGroups[i]));
      }

      iLegacyGroupsSize = legacyGroups.size();

      try {
         FileHandle fileList = FileManager.loadFile("game/legacies/Legacies.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LegacyManager.ConfigLegacyData.class, "Legacy", LegacyManager.Legacy.class);
         LegacyManager.ConfigLegacyData data = json.fromJson(LegacyManager.ConfigLegacyData.class, fileContent);

         for (Object e : data.Legacy) {
            legacies.add((LegacyManager.Legacy)e);
         }
      } catch (GdxRuntimeException var8) {
         CFG.exceptionStack(var8);
      }

      iLegaciesSize = legacies.size();

      for (int i = 0; i < iLegacyGroupsSize; i++) {
         numOfLegaciesInGroup.add(0);
      }

      for (int i = 0; i < iLegaciesSize; i++) {
         numOfLegaciesInGroup.set(legacies.get(i).GroupID, numOfLegaciesInGroup.get(legacies.get(i).GroupID) + 1);
      }

      for (int i = 0; i < iLegaciesSize; i++) {
         for (int j = legacies.get(i).CostLegacy.length - 1; j >= 0; j--) {
            if (legacies.get(i).CostLegacy[j] < minLegacyCost) {
               minLegacyCost = legacies.get(i).CostLegacy[j];
            }
         }
      }

      loadLegacyImages();
   }

   public static final void loadLegacyImages() {
      FileHandle tempFileT = FileManager.loadFile("game/legacies/legaciesImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/legacies/legaciesImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            legacyImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/legacies/legaciesImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         } else {
            legacyImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/legacies/legaciesImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static class ConfigLegacyData {
      public String Age_of_History;
      public ArrayList Legacy;
   }

   public static class Legacy {
      public String Name;
      public int ImageID;
      public int GroupID;
      public int[] CostLegacy;
      public int[] AI;
      public float[] ConstructionCost;
      public float[] AdministrationBuildingsCost;
      public float[] MilitaryBuildingsCost;
      public float[] EconomyBuildingsCost;
      public float[] WonderConstructionCost;
      public float[] ConstructionTime;
      public float[] TaxEfficiency;
      public float[] ProvinceMaintenance;
      public float[] BuildingsMaintenanceCost;
      public float[] ManpowerRecoverySpeed;
      public int[] MaxManpower;
      public float[] Research;
      public float[] ResearchPoints;
      public int[] BuildingSlot;
      public int[] MaxInfrastructure;
      public float[] Devastation;
      public float[] GrowthRate;
      public float[] IncomeProduction;
      public float[] ProductionEfficiency;
      public float[] InvestInEconomyCost;
      public float[] IncreaseTaxEfficiencyCost;
      public float[] IncreaseGrowthRateCost;
      public float[] DevelopInfrastructureCost;
      public int[] GeneralAttack;
      public int[] GeneralDefense;
      public int[] UnitsAttack;
      public int[] UnitsDefense;
      public float[] MaxMorale;
      public float[] ArmyMovementSpeed;
      public float[] SiegeEffectiveness;
      public float[] ImproveRelationsModifier;
      public float[] IncomeFromVassals;
      public float[] LoanInterest;
      public float[] DiplomacyPoints;
      public float[] RecruitmentTime;
      public int[] MaxNumOfAlliances;
      public int[] AdvisorMaxLevel;
      public int[] AdvisorPoolSize;
      public int[] MaxNumberOfLoans;
      public int[] MaximumLevelOfTheMilitaryAcademyForGenerals;
      public int[] MaximumLevelOfTheMilitaryAcademy;
      public int[] MaximumLevelOfTheSupremeCourt;
      public int[] MaximumLevelOfCapitalCity;
      public float[] AggressiveExpansion;
      public float[] DiseaseDeathRate;
      public float[] AdvisorCost;
      public float[] GeneralCost;
      public int[] BattleWidth;
      public float[] Discipline;
      public float[] ManpowerRecoveryFromADisbandedArmy;
      public float[] MaximumAmountOfGold;
      public float[] Loot;
      public int[] AllCharactersLifeExpectancy;
      public int[] RegimentsLimit;
   }
}
