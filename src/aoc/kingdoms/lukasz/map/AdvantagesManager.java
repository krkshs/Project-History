package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
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

public class AdvantagesManager {
   public static List<String> advantagesGroups = new ArrayList<>();
   public static int iAdvantagesGroupsSize = 0;
   public static List<Image> advantagesImages = new ArrayList<>();
   public static List<AdvantagesManager.Advantage> advantages = new ArrayList<>();
   public static int iAdvantagesSize = 0;

   public static final boolean unlockAdvantage(int iCivID, int iAdvantageID, int iLevel) {
      if (Game.getCiv(iCivID).getAdvantagePoints() > 0
         && !Game.getCiv(iCivID).haveAdvantage(iAdvantageID, iLevel)
         && Game.getCiv(iCivID).canUnlockAdvantage(iAdvantageID, iLevel)) {
         Game.getCiv(iCivID).setAdvantagePoints(Math.max(0, Game.getCiv(iCivID).getAdvantagePoints() - 1));
         Game.getCiv(iCivID).addAdvantage(iAdvantageID, iLevel);
         updateCivBonuses(iAdvantageID, iLevel, iCivID);
         return true;
      } else {
         return false;
      }
   }

   public static final boolean unlockAdvantage(int iCivID, int iAdvantageID) {
      int iLevel = Game.getCiv(iCivID).getAdvantageLvl(iAdvantageID) + 1;
      if (Game.getCiv(iCivID).getAdvantagePoints() > 0
         && !Game.getCiv(iCivID).haveAdvantage(iAdvantageID, iLevel)
         && Game.getCiv(iCivID).canUnlockAdvantage(iAdvantageID, iLevel)) {
         Game.getCiv(iCivID).setAdvantagePoints(Math.max(0, Game.getCiv(iCivID).getAdvantagePoints() - 1));
         Game.getCiv(iCivID).addAdvantage(iAdvantageID, iLevel);
         updateCivBonuses(iAdvantageID, iLevel, iCivID);
         return true;
      } else {
         return false;
      }
   }

   public static final void updateCivBonuses(int i, int j, int iCivID) {
      updateCivBonuses(i, j, iCivID, false);
   }

   public static final void updateCivBonuses(int i, int j, int iCivID, boolean isLoadingGame) {
      if (j < advantages.get(i).ImageID.length) {
         if (advantages.get(i).TaxEfficiency != null) {
            CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
            var10000.TaxEfficiency = var10000.TaxEfficiency + advantages.get(i).TaxEfficiency[j];
         }

         if (advantages.get(i).ProvinceMaintenance != null) {
            CivilizationBonuses var4 = Game.getCiv(iCivID).civBonuses;
            var4.ProvinceMaintenance = var4.ProvinceMaintenance + advantages.get(i).ProvinceMaintenance[j];
         }

         if (advantages.get(i).BuildingsMaintenanceCost != null) {
            CivilizationBonuses var5 = Game.getCiv(iCivID).civBonuses;
            var5.BuildingsMaintenanceCost = var5.BuildingsMaintenanceCost + advantages.get(i).BuildingsMaintenanceCost[j];
         }

         if (advantages.get(i).ConstructionTime != null) {
            CivilizationBonuses var6 = Game.getCiv(iCivID).civBonuses;
            var6.ConstructionTime = var6.ConstructionTime + advantages.get(i).ConstructionTime[j];
         }

         if (advantages.get(i).ConstructionCost != null) {
            CivilizationBonuses var7 = Game.getCiv(iCivID).civBonuses;
            var7.ConstructionCost = var7.ConstructionCost + advantages.get(i).ConstructionCost[j];
         }

         if (advantages.get(i).AdministrationBuildingsCost != null) {
            CivilizationBonuses var8 = Game.getCiv(iCivID).civBonuses;
            var8.AdministrationBuildingsCost = var8.AdministrationBuildingsCost + advantages.get(i).AdministrationBuildingsCost[j];
         }

         if (advantages.get(i).MilitaryBuildingsCost != null) {
            CivilizationBonuses var9 = Game.getCiv(iCivID).civBonuses;
            var9.MilitaryBuildingsCost = var9.MilitaryBuildingsCost + advantages.get(i).MilitaryBuildingsCost[j];
         }

         if (advantages.get(i).EconomyBuildingsCost != null) {
            CivilizationBonuses var10 = Game.getCiv(iCivID).civBonuses;
            var10.EconomyBuildingsCost = var10.EconomyBuildingsCost + advantages.get(i).EconomyBuildingsCost[j];
         }

         if (advantages.get(i).WonderConstructionCost != null) {
            CivilizationBonuses var11 = Game.getCiv(iCivID).civBonuses;
            var11.WonderConstructionCost = var11.WonderConstructionCost + advantages.get(i).WonderConstructionCost[j];
         }

         if (advantages.get(i).MaxManpower != null) {
            CivilizationBonuses var12 = Game.getCiv(iCivID).civBonuses;
            var12.MaxManpower = var12.MaxManpower + advantages.get(i).MaxManpower[j];
            Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
         }

         if (advantages.get(i).ManpowerRecoverySpeed != null) {
            CivilizationBonuses var13 = Game.getCiv(iCivID).civBonuses;
            var13.ManpowerRecoverySpeed = var13.ManpowerRecoverySpeed + advantages.get(i).ManpowerRecoverySpeed[j];
            Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
         }

         if (advantages.get(i).ArmyMoraleRecovery != null) {
            CivilizationBonuses var14 = Game.getCiv(iCivID).civBonuses;
            var14.ArmyMoraleRecovery = var14.ArmyMoraleRecovery + advantages.get(i).ArmyMoraleRecovery[j];
         }

         if (advantages.get(i).WarScoreCost != null) {
            CivilizationBonuses var15 = Game.getCiv(iCivID).civBonuses;
            var15.WarScoreCost = var15.WarScoreCost + advantages.get(i).WarScoreCost[j];
         }

         if (advantages.get(i).Research != null) {
            CivilizationBonuses var16 = Game.getCiv(iCivID).civBonuses;
            var16.Research = var16.Research + advantages.get(i).Research[j];
            Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (advantages.get(i).ResearchPoints != null) {
            CivilizationBonuses var17 = Game.getCiv(iCivID).civBonuses;
            var17.ResearchPoints = var17.ResearchPoints + advantages.get(i).ResearchPoints[j];
            Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         }

         if (advantages.get(i).Devastation != null) {
            CivilizationBonuses var18 = Game.getCiv(iCivID).civBonuses;
            var18.Devastation = var18.Devastation + advantages.get(i).Devastation[j];
         }

         if (advantages.get(i).BuildingSlot != null) {
            CivilizationBonuses var19 = Game.getCiv(iCivID).civBonuses;
            var19.BuildingSlot = var19.BuildingSlot + advantages.get(i).BuildingSlot[j];
            Game.getCiv(iCivID).updateBuildingLimit();
         }

         if (advantages.get(i).MaxInfrastructure != null) {
            CivilizationBonuses var20 = Game.getCiv(iCivID).civBonuses;
            var20.MaxInfrastructure = var20.MaxInfrastructure + advantages.get(i).MaxInfrastructure[j];
            Game.getCiv(iCivID).updateInfrastructureMax();
         }

         if (advantages.get(i).MonthlyIncome != null) {
            CivilizationBonuses var21 = Game.getCiv(iCivID).civBonuses;
            var21.MonthlyIncome = var21.MonthlyIncome + advantages.get(i).MonthlyIncome[j];
         }

         if (advantages.get(i).Gold != null && !isLoadingGame) {
            Civilization var22 = Game.getCiv(iCivID);
            var22.fGold = var22.fGold + advantages.get(i).Gold[j];
         }

         if (advantages.get(i).MonthlyLegacy != null) {
            CivilizationBonuses var23 = Game.getCiv(iCivID).civBonuses;
            var23.MonthlyLegacy = var23.MonthlyLegacy + advantages.get(i).MonthlyLegacy[j];
            Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
         }

         if (advantages.get(i).GrowthRate != null) {
            CivilizationBonuses var24 = Game.getCiv(iCivID).civBonuses;
            var24.GrowthRate = var24.GrowthRate + advantages.get(i).GrowthRate[j];
            Game.getCiv(iCivID).updateResearchPerMonth();
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (advantages.get(i).IncomeProduction != null) {
            CivilizationBonuses var25 = Game.getCiv(iCivID).civBonuses;
            var25.IncomeProduction = var25.IncomeProduction + advantages.get(i).IncomeProduction[j];
         }

         if (advantages.get(i).ProductionEfficiency != null) {
            CivilizationBonuses var26 = Game.getCiv(iCivID).civBonuses;
            var26.ProductionEfficiency = var26.ProductionEfficiency + advantages.get(i).ProductionEfficiency[j];
         }

         if (advantages.get(i).IncreaseManpowerCost != null) {
            CivilizationBonuses var27 = Game.getCiv(iCivID).civBonuses;
            var27.IncreaseManpowerCost = var27.IncreaseManpowerCost + advantages.get(i).IncreaseManpowerCost[j];
         }

         if (advantages.get(i).InvestInEconomyCost != null) {
            CivilizationBonuses var28 = Game.getCiv(iCivID).civBonuses;
            var28.InvestInEconomyCost = var28.InvestInEconomyCost + advantages.get(i).InvestInEconomyCost[j];
         }

         if (advantages.get(i).IncreaseTaxEfficiencyCost != null) {
            CivilizationBonuses var29 = Game.getCiv(iCivID).civBonuses;
            var29.IncreaseTaxEfficiencyCost = var29.IncreaseTaxEfficiencyCost + advantages.get(i).IncreaseTaxEfficiencyCost[j];
         }

         if (advantages.get(i).IncreaseGrowthRateCost != null) {
            CivilizationBonuses var30 = Game.getCiv(iCivID).civBonuses;
            var30.IncreaseGrowthRateCost = var30.IncreaseGrowthRateCost + advantages.get(i).IncreaseGrowthRateCost[j];
         }

         if (advantages.get(i).DevelopInfrastructureCost != null) {
            CivilizationBonuses var31 = Game.getCiv(iCivID).civBonuses;
            var31.DevelopInfrastructureCost = var31.DevelopInfrastructureCost + advantages.get(i).DevelopInfrastructureCost[j];
         }

         if (advantages.get(i).GeneralAttack != null) {
            CivilizationBonuses var32 = Game.getCiv(iCivID).civBonuses;
            var32.GeneralAttack = var32.GeneralAttack + advantages.get(i).GeneralAttack[j];
         }

         if (advantages.get(i).GeneralDefense != null) {
            CivilizationBonuses var33 = Game.getCiv(iCivID).civBonuses;
            var33.GeneralDefense = var33.GeneralDefense + advantages.get(i).GeneralDefense[j];
         }

         if (advantages.get(i).UnitsAttack != null) {
            CivilizationBonuses var34 = Game.getCiv(iCivID).civBonuses;
            var34.UnitsAttack = var34.UnitsAttack + advantages.get(i).UnitsAttack[j];
         }

         if (advantages.get(i).UnitsDefense != null) {
            CivilizationBonuses var35 = Game.getCiv(iCivID).civBonuses;
            var35.UnitsDefense = var35.UnitsDefense + advantages.get(i).UnitsDefense[j];
         }

         if (advantages.get(i).MaxMorale != null) {
            CivilizationBonuses var36 = Game.getCiv(iCivID).civBonuses;
            var36.MaxMorale = var36.MaxMorale + advantages.get(i).MaxMorale[j];
         }

         if (advantages.get(i).ArmyMovementSpeed != null) {
            CivilizationBonuses var37 = Game.getCiv(iCivID).civBonuses;
            var37.ArmyMovementSpeed = var37.ArmyMovementSpeed + advantages.get(i).ArmyMovementSpeed[j];
         }

         if (advantages.get(i).SiegeEffectiveness != null) {
            CivilizationBonuses var38 = Game.getCiv(iCivID).civBonuses;
            var38.SiegeEffectiveness = var38.SiegeEffectiveness + advantages.get(i).SiegeEffectiveness[j];
         }

         if (advantages.get(i).ImproveRelationsModifier != null) {
            CivilizationBonuses var39 = Game.getCiv(iCivID).civBonuses;
            var39.ImproveRelationsModifier = var39.ImproveRelationsModifier + advantages.get(i).ImproveRelationsModifier[j];
         }

         if (advantages.get(i).IncomeFromVassals != null) {
            CivilizationBonuses var40 = Game.getCiv(iCivID).civBonuses;
            var40.IncomeFromVassals = var40.IncomeFromVassals + advantages.get(i).IncomeFromVassals[j];
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (advantages.get(i).DiplomacyPoints != null) {
            CivilizationBonuses var41 = Game.getCiv(iCivID).civBonuses;
            var41.DiplomacyPoints = var41.DiplomacyPoints + advantages.get(i).DiplomacyPoints[j];
            Game.getCiv(iCivID).updateDiplomacyPerMonth();
         }

         if (advantages.get(i).LoanInterest != null) {
            CivilizationBonuses var42 = Game.getCiv(iCivID).civBonuses;
            var42.LoanInterest = var42.LoanInterest + advantages.get(i).LoanInterest[j];
         }

         if (advantages.get(i).MaxNumberOfLoans != null) {
            CivilizationBonuses var43 = Game.getCiv(iCivID).civBonuses;
            var43.MaxNumberOfLoans = var43.MaxNumberOfLoans + advantages.get(i).MaxNumberOfLoans[j];
         }

         if (advantages.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
            CivilizationBonuses var44 = Game.getCiv(iCivID).civBonuses;
            var44.MaximumLevelOfTheMilitaryAcademyForGenerals = var44.MaximumLevelOfTheMilitaryAcademyForGenerals
               + advantages.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j];
         }

         if (advantages.get(i).MaximumLevelOfTheMilitaryAcademy != null) {
            CivilizationBonuses var45 = Game.getCiv(iCivID).civBonuses;
            var45.MaximumLevelOfTheMilitaryAcademy = var45.MaximumLevelOfTheMilitaryAcademy + advantages.get(i).MaximumLevelOfTheMilitaryAcademy[j];
         }

         if (advantages.get(i).MaximumLevelOfTheSupremeCourt != null) {
            CivilizationBonuses var46 = Game.getCiv(iCivID).civBonuses;
            var46.MaximumLevelOfTheSupremeCourt = var46.MaximumLevelOfTheSupremeCourt + advantages.get(i).MaximumLevelOfTheSupremeCourt[j];
         }

         if (advantages.get(i).MaximumLevelOfCapitalCity != null) {
            CivilizationBonuses var47 = Game.getCiv(iCivID).civBonuses;
            var47.MaximumLevelOfCapitalCity = var47.MaximumLevelOfCapitalCity + advantages.get(i).MaximumLevelOfCapitalCity[j];
         }

         if (advantages.get(i).RecruitmentTime != null) {
            CivilizationBonuses var48 = Game.getCiv(iCivID).civBonuses;
            var48.RecruitmentTime = var48.RecruitmentTime + advantages.get(i).RecruitmentTime[j];
         }

         if (advantages.get(i).CoreCost != null) {
            CivilizationBonuses var49 = Game.getCiv(iCivID).civBonuses;
            var49.CoreCost = var49.CoreCost + advantages.get(i).CoreCost[j];
         }

         if (advantages.get(i).ReligionCost != null) {
            CivilizationBonuses var50 = Game.getCiv(iCivID).civBonuses;
            var50.ReligionCost = var50.ReligionCost + advantages.get(i).ReligionCost[j];
         }

         if (advantages.get(i).RecruitArmyCost != null) {
            CivilizationBonuses var51 = Game.getCiv(iCivID).civBonuses;
            var51.RecruitArmyCost = var51.RecruitArmyCost + advantages.get(i).RecruitArmyCost[j];
         }

         if (advantages.get(i).RecruitArmyFirstLineCost != null) {
            CivilizationBonuses var52 = Game.getCiv(iCivID).civBonuses;
            var52.RecruitArmyFirstLineCost = var52.RecruitArmyFirstLineCost + advantages.get(i).RecruitArmyFirstLineCost[j];
         }

         if (advantages.get(i).RecruitArmySecondLineCost != null) {
            CivilizationBonuses var53 = Game.getCiv(iCivID).civBonuses;
            var53.RecruitArmySecondLineCost = var53.RecruitArmySecondLineCost + advantages.get(i).RecruitArmySecondLineCost[j];
         }

         if (advantages.get(i).ArmyMaintenance != null) {
            CivilizationBonuses var54 = Game.getCiv(iCivID).civBonuses;
            var54.ArmyMaintenance = var54.ArmyMaintenance + advantages.get(i).ArmyMaintenance[j];
            Game.gameThread.addCivUpdateArmyMaintenance(iCivID);
         }

         if (advantages.get(i).MaxNumOfAlliances != null) {
            CivilizationBonuses var55 = Game.getCiv(iCivID).civBonuses;
            var55.MaxNumOfAlliances = var55.MaxNumOfAlliances + advantages.get(i).MaxNumOfAlliances[j];
         }

         if (advantages.get(i).AdvisorMaxLevel != null) {
            CivilizationBonuses var56 = Game.getCiv(iCivID).civBonuses;
            var56.AdvisorMaxLevel = var56.AdvisorMaxLevel + advantages.get(i).AdvisorMaxLevel[j];
         }

         if (advantages.get(i).AdvisorPoolSize != null) {
            CivilizationBonuses var57 = Game.getCiv(iCivID).civBonuses;
            var57.AdvisorPoolSize = var57.AdvisorPoolSize + advantages.get(i).AdvisorPoolSize[j];
         }

         if (advantages.get(i).AdvisorCost != null) {
            CivilizationBonuses var58 = Game.getCiv(iCivID).civBonuses;
            var58.AdvisorCost = var58.AdvisorCost + advantages.get(i).AdvisorCost[j];
         }

         if (advantages.get(i).GeneralCost != null) {
            CivilizationBonuses var59 = Game.getCiv(iCivID).civBonuses;
            var59.GeneralCost = var59.GeneralCost + advantages.get(i).GeneralCost[j];
         }

         if (advantages.get(i).AggressiveExpansion != null) {
            CivilizationBonuses var60 = Game.getCiv(iCivID).civBonuses;
            var60.AggressiveExpansion = var60.AggressiveExpansion + advantages.get(i).AggressiveExpansion[j];
         }

         if (advantages.get(i).DiseaseDeathRate != null) {
            CivilizationBonuses var61 = Game.getCiv(iCivID).civBonuses;
            var61.DiseaseDeathRate = var61.DiseaseDeathRate + advantages.get(i).DiseaseDeathRate[j];
         }

         if (advantages.get(i).ManpowerRecoveryFromADisbandedArmy != null) {
            CivilizationBonuses var62 = Game.getCiv(iCivID).civBonuses;
            var62.ManpowerRecoveryFromADisbandedArmy = var62.ManpowerRecoveryFromADisbandedArmy + advantages.get(i).ManpowerRecoveryFromADisbandedArmy[j];
         }

         if (advantages.get(i).BattleWidth != null) {
            CivilizationBonuses var63 = Game.getCiv(iCivID).civBonuses;
            var63.BattleWidth = var63.BattleWidth + advantages.get(i).BattleWidth[j];
         }

         if (advantages.get(i).AllCharactersLifeExpectancy != null) {
            CivilizationBonuses var64 = Game.getCiv(iCivID).civBonuses;
            var64.AllCharactersLifeExpectancy = var64.AllCharactersLifeExpectancy + advantages.get(i).AllCharactersLifeExpectancy[j];
         }

         if (advantages.get(i).Discipline != null) {
            CivilizationBonuses var65 = Game.getCiv(iCivID).civBonuses;
            var65.Discipline = var65.Discipline + advantages.get(i).Discipline[j];
         }

         if (advantages.get(i).MaximumAmountOfGold != null) {
            CivilizationBonuses var66 = Game.getCiv(iCivID).civBonuses;
            var66.MaximumAmountOfGold = var66.MaximumAmountOfGold + advantages.get(i).MaximumAmountOfGold[j];
         }

         if (advantages.get(i).MaximumAmountOfGold_Percentage != null) {
            CivilizationBonuses var67 = Game.getCiv(iCivID).civBonuses;
            var67.MaximumAmountOfGold_Percentage = var67.MaximumAmountOfGold_Percentage + advantages.get(i).MaximumAmountOfGold_Percentage[j];
         }

         if (advantages.get(i).Loot != null) {
            CivilizationBonuses var68 = Game.getCiv(iCivID).civBonuses;
            var68.Loot = var68.Loot + advantages.get(i).Loot[j];
         }

         if (advantages.get(i).RegimentsLimit != null) {
            CivilizationBonuses var69 = Game.getCiv(iCivID).civBonuses;
            var69.RegimentsLimit = var69.RegimentsLimit + advantages.get(i).RegimentsLimit[j];
            Game.getCiv(iCivID).updateRegimentsLimit();
         }

         Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
      }
   }

   public static final void loadAdvantages() {
      FileHandle tempFileT = FileManager.loadFile("game/advantages/AdvantagesGroups.txt");
      String[] tGroups = tempFileT.readString().split(";");

      for (int i = 0; i < tGroups.length; i++) {
         advantagesGroups.add(Game.lang.get(tGroups[i]));
      }

      iAdvantagesGroupsSize = advantagesGroups.size();

      try {
         FileHandle fileList = FileManager.loadFile("game/advantages/Advantages.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(AdvantagesManager.ConfigAdvantageData.class, "Advantage", AdvantagesManager.Advantage.class);
         AdvantagesManager.ConfigAdvantageData data = json.fromJson(AdvantagesManager.ConfigAdvantageData.class, fileContent);

         for (Object e : data.Advantage) {
            advantages.add((AdvantagesManager.Advantage)e);
         }
      } catch (GdxRuntimeException var8) {
         CFG.exceptionStack(var8);
      }

      iAdvantagesSize = advantages.size();
      loadAdvantagesImages();
   }

   public static final void loadAdvantagesImages() {
      FileHandle tempFileT = FileManager.loadFile("game/advantages/advantagesImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/advantages/advantagesImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            advantagesImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/advantages/advantagesImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         } else {
            advantagesImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/advantages/advantagesImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static final void initRandomAdvantages() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         List<Integer> addR = getRandomAdvantages();
         int j = 0;

         for (int jSize = addR.size(); j < jSize; j++) {
            Game.getCiv(i).addAdvantage(addR.get(j), 0);
            updateCivBonuses(addR.get(j), 0, i);
         }
      }
   }

   public static final void initAdvantagePoints() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         Game.getCiv(i)
            .setAdvantagePoints(
               GameValues.advantages.ADVANTAGE_POINTS_START_GAME
                  + (
                     GameValues.advantages.ADVANTAGE_POINTS_START_GAME_RANDOM > 0
                        ? Game.oR.nextInt(GameValues.advantages.ADVANTAGE_POINTS_START_GAME_RANDOM)
                        : 0
                  )
            );
      }
   }

   private static final List<Integer> getRandomAdvantages() {
      List<Integer> out = new ArrayList<>();
      int iStack = 0;
      int tNum = GameValues.advantages.ADVANTAGES_RANDOM_INIT;
      if (GameValues.advantages.ADVANTAGES_RANDOM_INIT_RANDOM > 1) {
         tNum += Game.oR.nextInt(GameValues.advantages.ADVANTAGES_RANDOM_INIT_RANDOM);
      }

      while (iStack++ < 49 && out.size() < tNum) {
         int iR = Game.oR.nextInt(iAdvantagesSize);
         if (!out.contains(iR)) {
            out.add(iR);
         }
      }

      return out;
   }

   public static String getAdvantageName(int i) {
      if (advantages.get(i).ConstructionCost != null) {
         return Game.lang.get("ConstructionCost");
      } else if (advantages.get(i).AdministrationBuildingsCost != null) {
         return Game.lang.get("AdministrationBuildingsCost");
      } else if (advantages.get(i).MilitaryBuildingsCost != null) {
         return Game.lang.get("MilitaryBuildingsCost");
      } else if (advantages.get(i).EconomyBuildingsCost != null) {
         return Game.lang.get("EconomyBuildingsCost");
      } else if (advantages.get(i).ConstructionTime != null) {
         return Game.lang.get("ConstructionTime");
      } else if (advantages.get(i).WonderConstructionCost != null) {
         return Game.lang.get("WonderConstructionCost");
      } else if (advantages.get(i).TaxEfficiency != null) {
         return Game.lang.get("TaxEfficiency");
      } else if (advantages.get(i).ProvinceMaintenance != null) {
         return Game.lang.get("ProvinceMaintenance");
      } else if (advantages.get(i).BuildingsMaintenanceCost != null) {
         return Game.lang.get("BuildingsMaintenanceCost");
      } else if (advantages.get(i).ManpowerRecoverySpeed != null) {
         return Game.lang.get("ManpowerRecoverySpeed");
      } else if (advantages.get(i).ArmyMoraleRecovery != null) {
         return Game.lang.get("ArmyMoraleRecovery");
      } else if (advantages.get(i).WarScoreCost != null) {
         return Game.lang.get("WarScoreCost");
      } else if (advantages.get(i).ReinforcementSpeed != null) {
         return Game.lang.get("ReinforcementSpeed");
      } else if (advantages.get(i).MaxManpower != null) {
         return Game.lang.get("MaximumManpower");
      } else if (advantages.get(i).Research != null) {
         return Game.lang.get("Research");
      } else if (advantages.get(i).ResearchPoints != null) {
         return Game.lang.get("ResearchPerMonth");
      } else if (advantages.get(i).BuildingSlot != null) {
         return Game.lang.get("AdditionalBuildingsInProvince");
      } else if (advantages.get(i).MaxInfrastructure != null) {
         return Game.lang.get("MaximumInfrastructureLevel");
      } else if (advantages.get(i).Devastation != null) {
         return Game.lang.get("Devastation");
      } else if (advantages.get(i).GrowthRate != null) {
         return Game.lang.get("GrowthRate");
      } else if (advantages.get(i).MonthlyIncome != null) {
         return Game.lang.get("MonthlyIncome");
      } else if (advantages.get(i).Gold != null) {
         return Game.lang.get("Gold");
      } else if (advantages.get(i).MonthlyLegacy != null) {
         return Game.lang.get("MonthlyLegacy");
      } else if (advantages.get(i).IncomeProduction != null) {
         return Game.lang.get("IncomeProduction");
      } else if (advantages.get(i).ProductionEfficiency != null) {
         return Game.lang.get("ProductionEfficiency");
      } else if (advantages.get(i).InvestInEconomyCost != null) {
         return Game.lang.get("InvestInEconomyCost");
      } else if (advantages.get(i).IncreaseManpowerCost != null) {
         return Game.lang.get("IncreaseManpowerCost");
      } else if (advantages.get(i).IncreaseTaxEfficiencyCost != null) {
         return Game.lang.get("IncreaseTaxEfficiencyCost");
      } else if (advantages.get(i).IncreaseGrowthRateCost != null) {
         return Game.lang.get("IncreaseGrowthRateCost");
      } else if (advantages.get(i).DevelopInfrastructureCost != null) {
         return Game.lang.get("DevelopInfrastructureCost");
      } else if (advantages.get(i).GeneralAttack != null) {
         return Game.lang.get("GeneralsAttack");
      } else if (advantages.get(i).GeneralDefense != null) {
         return Game.lang.get("GeneralsDefense");
      } else if (advantages.get(i).UnitsAttack != null) {
         return Game.lang.get("UnitsAttack");
      } else if (advantages.get(i).UnitsDefense != null) {
         return Game.lang.get("UnitsDefense");
      } else if (advantages.get(i).MaxMorale != null) {
         return Game.lang.get("MaxMorale");
      } else if (advantages.get(i).ArmyMovementSpeed != null) {
         return Game.lang.get("ArmyMovementSpeed");
      } else if (advantages.get(i).SiegeEffectiveness != null) {
         return Game.lang.get("SiegeEffectiveness");
      } else if (advantages.get(i).ImproveRelationsModifier != null) {
         return Game.lang.get("ImproveRelationsModifier");
      } else if (advantages.get(i).IncomeFromVassals != null) {
         return Game.lang.get("IncomeFromVassals");
      } else if (advantages.get(i).LoanInterest != null) {
         return Game.lang.get("LoanInterest");
      } else if (advantages.get(i).DiplomacyPoints != null) {
         return Game.lang.get("DiplomacyPoints");
      } else if (advantages.get(i).RecruitmentTime != null) {
         return Game.lang.get("RecruitmentTime");
      } else if (advantages.get(i).RecruitArmyCost != null) {
         return Game.lang.get("ArmyRecruitmentCost");
      } else if (advantages.get(i).RecruitArmyFirstLineCost != null) {
         return Game.lang.get("FirstLineArmyRecruitmentCost");
      } else if (advantages.get(i).RecruitArmySecondLineCost != null) {
         return Game.lang.get("SecondLineArmyRecruitmentCost");
      } else if (advantages.get(i).ArmyMaintenance != null) {
         return Game.lang.get("ArmyMaintenance");
      } else if (advantages.get(i).CoreCost != null) {
         return Game.lang.get("CoreConstruction");
      } else if (advantages.get(i).ReligionCost != null) {
         return Game.lang.get("ReligionConversionCost");
      } else if (advantages.get(i).MaxNumOfAlliances != null) {
         return Game.lang.get("MaxNumOfAlliances");
      } else if (advantages.get(i).AdvisorMaxLevel != null) {
         return Game.lang.get("MaximumAdvisorSkillLevel");
      } else if (advantages.get(i).AdvisorPoolSize != null) {
         return Game.lang.get("AdvisorPool");
      } else if (advantages.get(i).MaxNumberOfLoans != null) {
         return Game.lang.get("MaximumNumberOfLoans");
      } else if (advantages.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
         return Game.lang.get("MaximumLevelOfTheMilitaryAcademyForGenerals");
      } else if (advantages.get(i).MaximumLevelOfTheMilitaryAcademy != null) {
         return Game.lang.get("MaximumLevelOfTheMilitaryAcademy");
      } else if (advantages.get(i).MaximumLevelOfTheSupremeCourt != null) {
         return Game.lang.get("MaximumLevelOfTheSupremeCourt");
      } else if (advantages.get(i).MaximumLevelOfCapitalCity != null) {
         return Game.lang.get("MaximumLevelOfCapitalCity");
      } else if (advantages.get(i).AggressiveExpansion != null) {
         return Game.lang.get("AggressiveExpansion");
      } else if (advantages.get(i).DiseaseDeathRate != null) {
         return Game.lang.get("DiseasesDeathRate");
      } else if (advantages.get(i).ManpowerRecoveryFromADisbandedArmy != null) {
         return Game.lang.get("ManpowerRecoveryFromADisbandedArmy");
      } else if (advantages.get(i).AdvisorCost != null) {
         return Game.lang.get("AdvisorCost");
      } else if (advantages.get(i).GeneralCost != null) {
         return Game.lang.get("GeneralCost");
      } else if (advantages.get(i).Discipline != null) {
         return Game.lang.get("Discipline");
      } else if (advantages.get(i).MaximumAmountOfGold != null) {
         return Game.lang.get("MaximumAmountOfGold");
      } else if (advantages.get(i).MaximumAmountOfGold_Percentage != null) {
         return Game.lang.get("MaximumAmountOfGold");
      } else if (advantages.get(i).Loot != null) {
         return Game.lang.get("Loot");
      } else if (advantages.get(i).BattleWidth != null) {
         return Game.lang.get("BattleWidth");
      } else if (advantages.get(i).RegimentsLimit != null) {
         return Game.lang.get("RegimentsLimit");
      } else {
         return advantages.get(i).AllCharactersLifeExpectancy != null ? Game.lang.get("AllCharactersLifeExpectancy") : "--";
      }
   }

   public static class Advantage {
      public int[] ImageID;
      public int GroupID;
      public int RequiredTechID;
      public int AI;
      public float[] MonthlyIncome;
      public float[] MonthlyLegacy;
      public float[] Gold;
      public float[] ConstructionCost;
      public float[] AdministrationBuildingsCost;
      public float[] MilitaryBuildingsCost;
      public float[] EconomyBuildingsCost;
      public float[] ConstructionTime;
      public float[] WonderConstructionCost;
      public float[] TaxEfficiency;
      public float[] ProvinceMaintenance;
      public float[] BuildingsMaintenanceCost;
      public float[] ArmyMaintenance;
      public int[] MaxManpower;
      public float[] ManpowerRecoverySpeed;
      public float[] ReinforcementSpeed;
      public float[] ArmyMovementSpeed;
      public float[] Research;
      public float[] ResearchPoints;
      public float[] MaxMorale;
      public float[] ArmyMoraleRecovery;
      public float[] WarScoreCost;
      public int[] BuildingSlot;
      public int[] MaxInfrastructure;
      public float[] Devastation;
      public float[] GrowthRate;
      public float[] IncomeProduction;
      public float[] ProductionEfficiency;
      public float[] InvestInEconomyCost;
      public float[] IncreaseManpowerCost;
      public float[] IncreaseTaxEfficiencyCost;
      public float[] IncreaseGrowthRateCost;
      public float[] DevelopInfrastructureCost;
      public int[] GeneralAttack;
      public int[] GeneralDefense;
      public int[] UnitsAttack;
      public int[] UnitsDefense;
      public float[] SiegeEffectiveness;
      public float[] ImproveRelationsModifier;
      public float[] IncomeFromVassals;
      public float[] LoanInterest;
      public float[] DiplomacyPoints;
      public float[] CoreCost;
      public float[] ReligionCost;
      public float[] RecruitmentTime;
      public float[] RecruitArmyCost;
      public float[] RecruitArmyFirstLineCost;
      public float[] RecruitArmySecondLineCost;
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
      public float[] MaximumAmountOfGold_Percentage;
      public float[] Loot;
      public int[] AllCharactersLifeExpectancy;
      public int[] RegimentsLimit;
   }

   public static class ConfigAdvantageData {
      public String Age_of_History;
      public ArrayList Advantage;
   }
}
