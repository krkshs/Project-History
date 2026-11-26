package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class LawsManager {
   public static List<Image> lawsImages = new ArrayList<>();
   public static List<LawsManager.Law> laws = new ArrayList<>();
   public static int iLawsSize = 0;

   public static final void updateCivBonuses(int i, int j, int iCivID, float mod) {
      if (j < laws.get(i).ImageID.length) {
         if (laws.get(i).TaxEfficiency != null) {
            CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
            var10000.TaxEfficiency = var10000.TaxEfficiency + laws.get(i).TaxEfficiency[j] * mod;
         }

         if (laws.get(i).ProvinceMaintenance != null) {
            CivilizationBonuses var4 = Game.getCiv(iCivID).civBonuses;
            var4.ProvinceMaintenance = var4.ProvinceMaintenance + laws.get(i).ProvinceMaintenance[j] * mod;
         }

         if (laws.get(i).BuildingsMaintenanceCost != null) {
            CivilizationBonuses var5 = Game.getCiv(iCivID).civBonuses;
            var5.BuildingsMaintenanceCost = var5.BuildingsMaintenanceCost + laws.get(i).BuildingsMaintenanceCost[j] * mod;
         }

         if (laws.get(i).ConstructionTime != null) {
            CivilizationBonuses var6 = Game.getCiv(iCivID).civBonuses;
            var6.ConstructionTime = var6.ConstructionTime + laws.get(i).ConstructionTime[j] * mod;
         }

         if (laws.get(i).ConstructionCost != null) {
            CivilizationBonuses var7 = Game.getCiv(iCivID).civBonuses;
            var7.ConstructionCost = var7.ConstructionCost + laws.get(i).ConstructionCost[j] * mod;
         }

         if (laws.get(i).AdministrationBuildingsCost != null) {
            CivilizationBonuses var8 = Game.getCiv(iCivID).civBonuses;
            var8.AdministrationBuildingsCost = var8.AdministrationBuildingsCost + laws.get(i).AdministrationBuildingsCost[j] * mod;
         }

         if (laws.get(i).MilitaryBuildingsCost != null) {
            CivilizationBonuses var9 = Game.getCiv(iCivID).civBonuses;
            var9.MilitaryBuildingsCost = var9.MilitaryBuildingsCost + laws.get(i).MilitaryBuildingsCost[j] * mod;
         }

         if (laws.get(i).EconomyBuildingsCost != null) {
            CivilizationBonuses var10 = Game.getCiv(iCivID).civBonuses;
            var10.EconomyBuildingsCost = var10.EconomyBuildingsCost + laws.get(i).EconomyBuildingsCost[j] * mod;
         }

         if (laws.get(i).WonderConstructionCost != null) {
            CivilizationBonuses var11 = Game.getCiv(iCivID).civBonuses;
            var11.WonderConstructionCost = var11.WonderConstructionCost + laws.get(i).WonderConstructionCost[j] * mod;
         }

         if (laws.get(i).MaxManpower != null) {
            CivilizationBonuses var12 = Game.getCiv(iCivID).civBonuses;
            var12.MaxManpower = var12.MaxManpower + laws.get(i).MaxManpower[j] * mod;
            Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
         }

         if (laws.get(i).MaxManpower_Percentage != null) {
            CivilizationBonuses var13 = Game.getCiv(iCivID).civBonuses;
            var13.MaxManpower_Percentage = var13.MaxManpower_Percentage + laws.get(i).MaxManpower_Percentage[j] * mod;
            Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
         }

         if (laws.get(i).ManpowerRecoverySpeed != null) {
            CivilizationBonuses var14 = Game.getCiv(iCivID).civBonuses;
            var14.ManpowerRecoverySpeed = var14.ManpowerRecoverySpeed + laws.get(i).ManpowerRecoverySpeed[j] * mod;
            Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
         }

         if (laws.get(i).ArmyMoraleRecovery != null) {
            CivilizationBonuses var15 = Game.getCiv(iCivID).civBonuses;
            var15.ArmyMoraleRecovery = var15.ArmyMoraleRecovery + laws.get(i).ArmyMoraleRecovery[j] * mod;
         }

         if (laws.get(i).WarScoreCost != null) {
            CivilizationBonuses var16 = Game.getCiv(iCivID).civBonuses;
            var16.WarScoreCost = var16.WarScoreCost + laws.get(i).WarScoreCost[j] * mod;
         }

         if (laws.get(i).Research != null) {
            CivilizationBonuses var17 = Game.getCiv(iCivID).civBonuses;
            var17.Research = var17.Research + laws.get(i).Research[j] * mod;
            Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (laws.get(i).ResearchPoints != null) {
            CivilizationBonuses var18 = Game.getCiv(iCivID).civBonuses;
            var18.ResearchPoints = var18.ResearchPoints + laws.get(i).ResearchPoints[j] * mod;
            Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         }

         if (laws.get(i).Devastation != null) {
            CivilizationBonuses var19 = Game.getCiv(iCivID).civBonuses;
            var19.Devastation = var19.Devastation + laws.get(i).Devastation[j] * mod;
         }

         if (laws.get(i).BuildingSlot != null) {
            CivilizationBonuses var20 = Game.getCiv(iCivID).civBonuses;
            var20.BuildingSlot = (int)(var20.BuildingSlot + laws.get(i).BuildingSlot[j] * mod);
            Game.getCiv(iCivID).updateBuildingLimit();
         }

         if (laws.get(i).MaxInfrastructure != null) {
            CivilizationBonuses var21 = Game.getCiv(iCivID).civBonuses;
            var21.MaxInfrastructure = (int)(var21.MaxInfrastructure + laws.get(i).MaxInfrastructure[j] * mod);
            Game.getCiv(iCivID).updateInfrastructureMax();
         }

         if (laws.get(i).MonthlyIncome != null) {
            CivilizationBonuses var22 = Game.getCiv(iCivID).civBonuses;
            var22.MonthlyIncome = var22.MonthlyIncome + laws.get(i).MonthlyIncome[j] * mod;
         }

         if (laws.get(i).Gold != null) {
            Civilization var23 = Game.getCiv(iCivID);
            var23.fGold = var23.fGold + laws.get(i).Gold[j] * mod;
         }

         if (laws.get(i).MonthlyLegacy != null) {
            CivilizationBonuses var24 = Game.getCiv(iCivID).civBonuses;
            var24.MonthlyLegacy = var24.MonthlyLegacy + laws.get(i).MonthlyLegacy[j] * mod;
            Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
         }

         if (laws.get(i).MonthlyLegacy_Percentage != null) {
            CivilizationBonuses var25 = Game.getCiv(iCivID).civBonuses;
            var25.MonthlyLegacy_Percentage = var25.MonthlyLegacy_Percentage + laws.get(i).MonthlyLegacy_Percentage[j] * mod;
            Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
         }

         if (laws.get(i).GrowthRate != null) {
            CivilizationBonuses var26 = Game.getCiv(iCivID).civBonuses;
            var26.GrowthRate = var26.GrowthRate + laws.get(i).GrowthRate[j] * mod;
            Game.getCiv(iCivID).updateResearchPerMonth();
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (laws.get(i).IncomeProduction != null) {
            CivilizationBonuses var27 = Game.getCiv(iCivID).civBonuses;
            var27.IncomeProduction = var27.IncomeProduction + laws.get(i).IncomeProduction[j] * mod;
         }

         if (laws.get(i).IncomeEconomy != null) {
            CivilizationBonuses var28 = Game.getCiv(iCivID).civBonuses;
            var28.IncomeEconomy = var28.IncomeEconomy + laws.get(i).IncomeEconomy[j] * mod;
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (laws.get(i).IncomeTaxation != null) {
            CivilizationBonuses var29 = Game.getCiv(iCivID).civBonuses;
            var29.IncomeTaxation = var29.IncomeTaxation + laws.get(i).IncomeTaxation[j] * mod;
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (laws.get(i).ProductionEfficiency != null) {
            CivilizationBonuses var30 = Game.getCiv(iCivID).civBonuses;
            var30.ProductionEfficiency = var30.ProductionEfficiency + laws.get(i).ProductionEfficiency[j] * mod;
         }

         if (laws.get(i).IncreaseManpowerCost != null) {
            CivilizationBonuses var31 = Game.getCiv(iCivID).civBonuses;
            var31.IncreaseManpowerCost = var31.IncreaseManpowerCost + laws.get(i).IncreaseManpowerCost[j] * mod;
         }

         if (laws.get(i).InvestInEconomyCost != null) {
            CivilizationBonuses var32 = Game.getCiv(iCivID).civBonuses;
            var32.InvestInEconomyCost = var32.InvestInEconomyCost + laws.get(i).InvestInEconomyCost[j] * mod;
         }

         if (laws.get(i).IncreaseTaxEfficiencyCost != null) {
            CivilizationBonuses var33 = Game.getCiv(iCivID).civBonuses;
            var33.IncreaseTaxEfficiencyCost = var33.IncreaseTaxEfficiencyCost + laws.get(i).IncreaseTaxEfficiencyCost[j] * mod;
         }

         if (laws.get(i).IncreaseGrowthRateCost != null) {
            CivilizationBonuses var34 = Game.getCiv(iCivID).civBonuses;
            var34.IncreaseGrowthRateCost = var34.IncreaseGrowthRateCost + laws.get(i).IncreaseGrowthRateCost[j] * mod;
         }

         if (laws.get(i).DevelopInfrastructureCost != null) {
            CivilizationBonuses var35 = Game.getCiv(iCivID).civBonuses;
            var35.DevelopInfrastructureCost = var35.DevelopInfrastructureCost + laws.get(i).DevelopInfrastructureCost[j] * mod;
         }

         if (laws.get(i).GeneralAttack != null) {
            CivilizationBonuses var36 = Game.getCiv(iCivID).civBonuses;
            var36.GeneralAttack = (int)(var36.GeneralAttack + laws.get(i).GeneralAttack[j] * mod);
         }

         if (laws.get(i).GeneralDefense != null) {
            CivilizationBonuses var37 = Game.getCiv(iCivID).civBonuses;
            var37.GeneralDefense = (int)(var37.GeneralDefense + laws.get(i).GeneralDefense[j] * mod);
         }

         if (laws.get(i).UnitsAttack != null) {
            CivilizationBonuses var38 = Game.getCiv(iCivID).civBonuses;
            var38.UnitsAttack = (int)(var38.UnitsAttack + laws.get(i).UnitsAttack[j] * mod);
         }

         if (laws.get(i).UnitsDefense != null) {
            CivilizationBonuses var39 = Game.getCiv(iCivID).civBonuses;
            var39.UnitsDefense = (int)(var39.UnitsDefense + laws.get(i).UnitsDefense[j] * mod);
         }

         if (laws.get(i).MaxMorale != null) {
            CivilizationBonuses var40 = Game.getCiv(iCivID).civBonuses;
            var40.MaxMorale = var40.MaxMorale + laws.get(i).MaxMorale[j] * mod;
         }

         if (laws.get(i).ArmyMovementSpeed != null) {
            CivilizationBonuses var41 = Game.getCiv(iCivID).civBonuses;
            var41.ArmyMovementSpeed = var41.ArmyMovementSpeed + laws.get(i).ArmyMovementSpeed[j] * mod;
         }

         if (laws.get(i).SiegeEffectiveness != null) {
            CivilizationBonuses var42 = Game.getCiv(iCivID).civBonuses;
            var42.SiegeEffectiveness = var42.SiegeEffectiveness + laws.get(i).SiegeEffectiveness[j] * mod;
         }

         if (laws.get(i).ImproveRelationsModifier != null) {
            CivilizationBonuses var43 = Game.getCiv(iCivID).civBonuses;
            var43.ImproveRelationsModifier = var43.ImproveRelationsModifier + laws.get(i).ImproveRelationsModifier[j] * mod;
         }

         if (laws.get(i).IncomeFromVassals != null) {
            CivilizationBonuses var44 = Game.getCiv(iCivID).civBonuses;
            var44.IncomeFromVassals = var44.IncomeFromVassals + laws.get(i).IncomeFromVassals[j] * mod;
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         }

         if (laws.get(i).DiplomacyPoints != null) {
            CivilizationBonuses var45 = Game.getCiv(iCivID).civBonuses;
            var45.DiplomacyPoints = var45.DiplomacyPoints + laws.get(i).DiplomacyPoints[j] * mod;
            Game.getCiv(iCivID).updateDiplomacyPerMonth();
         }

         if (laws.get(i).LoanInterest != null) {
            CivilizationBonuses var46 = Game.getCiv(iCivID).civBonuses;
            var46.LoanInterest = var46.LoanInterest + laws.get(i).LoanInterest[j] * mod;
         }

         if (laws.get(i).MaxNumberOfLoans != null) {
            CivilizationBonuses var47 = Game.getCiv(iCivID).civBonuses;
            var47.MaxNumberOfLoans = (int)(var47.MaxNumberOfLoans + laws.get(i).MaxNumberOfLoans[j] * mod);
         }

         if (laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
            CivilizationBonuses var48 = Game.getCiv(iCivID).civBonuses;
            var48.MaximumLevelOfTheMilitaryAcademyForGenerals = (int)(
               var48.MaximumLevelOfTheMilitaryAcademyForGenerals + laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j] * mod
            );
         }

         if (laws.get(i).MaximumLevelOfTheMilitaryAcademy != null) {
            CivilizationBonuses var49 = Game.getCiv(iCivID).civBonuses;
            var49.MaximumLevelOfTheMilitaryAcademy = (int)(var49.MaximumLevelOfTheMilitaryAcademy + laws.get(i).MaximumLevelOfTheMilitaryAcademy[j] * mod);
         }

         if (laws.get(i).MaximumLevelOfTheSupremeCourt != null) {
            CivilizationBonuses var50 = Game.getCiv(iCivID).civBonuses;
            var50.MaximumLevelOfTheSupremeCourt = (int)(var50.MaximumLevelOfTheSupremeCourt + laws.get(i).MaximumLevelOfTheSupremeCourt[j] * mod);
         }

         if (laws.get(i).MaximumLevelOfCapitalCity != null) {
            CivilizationBonuses var51 = Game.getCiv(iCivID).civBonuses;
            var51.MaximumLevelOfCapitalCity = (int)(var51.MaximumLevelOfCapitalCity + laws.get(i).MaximumLevelOfCapitalCity[j] * mod);
         }

         if (laws.get(i).RecruitmentTime != null) {
            CivilizationBonuses var52 = Game.getCiv(iCivID).civBonuses;
            var52.RecruitmentTime = var52.RecruitmentTime + laws.get(i).RecruitmentTime[j] * mod;
         }

         if (laws.get(i).CoreCost != null) {
            CivilizationBonuses var53 = Game.getCiv(iCivID).civBonuses;
            var53.CoreCost = var53.CoreCost + laws.get(i).CoreCost[j] * mod;
         }

         if (laws.get(i).ReligionCost != null) {
            CivilizationBonuses var54 = Game.getCiv(iCivID).civBonuses;
            var54.ReligionCost = var54.ReligionCost + laws.get(i).ReligionCost[j] * mod;
         }

         if (laws.get(i).RecruitArmyCost != null) {
            CivilizationBonuses var55 = Game.getCiv(iCivID).civBonuses;
            var55.RecruitArmyCost = var55.RecruitArmyCost + laws.get(i).RecruitArmyCost[j] * mod;
         }

         if (laws.get(i).RecruitArmyFirstLineCost != null) {
            CivilizationBonuses var56 = Game.getCiv(iCivID).civBonuses;
            var56.RecruitArmyFirstLineCost = var56.RecruitArmyFirstLineCost + laws.get(i).RecruitArmyFirstLineCost[j] * mod;
         }

         if (laws.get(i).RecruitArmySecondLineCost != null) {
            CivilizationBonuses var57 = Game.getCiv(iCivID).civBonuses;
            var57.RecruitArmySecondLineCost = var57.RecruitArmySecondLineCost + laws.get(i).RecruitArmySecondLineCost[j] * mod;
         }

         if (laws.get(i).ArmyMaintenance != null) {
            CivilizationBonuses var58 = Game.getCiv(iCivID).civBonuses;
            var58.ArmyMaintenance = var58.ArmyMaintenance + laws.get(i).ArmyMaintenance[j] * mod;
            Game.gameThread.addCivUpdateArmyMaintenance(iCivID);
         }

         if (laws.get(i).MaxNumOfAlliances != null) {
            CivilizationBonuses var59 = Game.getCiv(iCivID).civBonuses;
            var59.MaxNumOfAlliances = (int)(var59.MaxNumOfAlliances + laws.get(i).MaxNumOfAlliances[j] * mod);
         }

         if (laws.get(i).AdvisorMaxLevel != null) {
            CivilizationBonuses var60 = Game.getCiv(iCivID).civBonuses;
            var60.AdvisorMaxLevel = (int)(var60.AdvisorMaxLevel + laws.get(i).AdvisorMaxLevel[j] * mod);
         }

         if (laws.get(i).AdvisorPoolSize != null) {
            CivilizationBonuses var61 = Game.getCiv(iCivID).civBonuses;
            var61.AdvisorPoolSize = (int)(var61.AdvisorPoolSize + laws.get(i).AdvisorPoolSize[j] * mod);
         }

         if (laws.get(i).AdvisorCost != null) {
            CivilizationBonuses var62 = Game.getCiv(iCivID).civBonuses;
            var62.AdvisorCost = var62.AdvisorCost + laws.get(i).AdvisorCost[j] * mod;
         }

         if (laws.get(i).GeneralCost != null) {
            CivilizationBonuses var63 = Game.getCiv(iCivID).civBonuses;
            var63.GeneralCost = var63.GeneralCost + laws.get(i).GeneralCost[j] * mod;
         }

         if (laws.get(i).AggressiveExpansion != null) {
            CivilizationBonuses var64 = Game.getCiv(iCivID).civBonuses;
            var64.AggressiveExpansion = var64.AggressiveExpansion + laws.get(i).AggressiveExpansion[j] * mod;
         }

         if (laws.get(i).DiseaseDeathRate != null) {
            CivilizationBonuses var65 = Game.getCiv(iCivID).civBonuses;
            var65.DiseaseDeathRate = var65.DiseaseDeathRate + laws.get(i).DiseaseDeathRate[j] * mod;
         }

         if (laws.get(i).ManpowerRecoveryFromADisbandedArmy != null) {
            CivilizationBonuses var66 = Game.getCiv(iCivID).civBonuses;
            var66.ManpowerRecoveryFromADisbandedArmy = var66.ManpowerRecoveryFromADisbandedArmy + laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] * mod;
         }

         if (laws.get(i).BattleWidth != null) {
            CivilizationBonuses var67 = Game.getCiv(iCivID).civBonuses;
            var67.BattleWidth = (int)(var67.BattleWidth + laws.get(i).BattleWidth[j] * mod);
         }

         if (laws.get(i).AllCharactersLifeExpectancy != null) {
            CivilizationBonuses var68 = Game.getCiv(iCivID).civBonuses;
            var68.AllCharactersLifeExpectancy = (int)(var68.AllCharactersLifeExpectancy + laws.get(i).AllCharactersLifeExpectancy[j] * mod);
         }

         if (laws.get(i).UnlocksColonization != null) {
            Game.getCiv(iCivID).canColonize = laws.get(i).UnlocksColonization[j];
         }

         if (laws.get(i).Discipline != null) {
            CivilizationBonuses var69 = Game.getCiv(iCivID).civBonuses;
            var69.Discipline = var69.Discipline + laws.get(i).Discipline[j] * mod;
         }

         if (laws.get(i).MaximumAmountOfGold != null) {
            CivilizationBonuses var70 = Game.getCiv(iCivID).civBonuses;
            var70.MaximumAmountOfGold = var70.MaximumAmountOfGold + laws.get(i).MaximumAmountOfGold[j] * mod;
         }

         if (laws.get(i).MaximumAmountOfGold_Percentage != null) {
            CivilizationBonuses var71 = Game.getCiv(iCivID).civBonuses;
            var71.MaximumAmountOfGold_Percentage = var71.MaximumAmountOfGold_Percentage + laws.get(i).MaximumAmountOfGold_Percentage[j] * mod;
         }

         if (laws.get(i).Loot != null) {
            CivilizationBonuses var72 = Game.getCiv(iCivID).civBonuses;
            var72.Loot = var72.Loot + laws.get(i).Loot[j] * mod;
         }

         if (laws.get(i).RegimentsLimit != null) {
            CivilizationBonuses var73 = Game.getCiv(iCivID).civBonuses;
            var73.RegimentsLimit = (int)(var73.RegimentsLimit + laws.get(i).RegimentsLimit[j] * mod);
            Game.getCiv(iCivID).updateRegimentsLimit();
         }

         Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
      }
   }

   public static final void loadLaws() {
      try {
         FileHandle fileList = FileManager.loadFile("game/laws/Laws.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LawsManager.ConfigLawData.class, "Law", LawsManager.Law.class);
         LawsManager.ConfigLawData data = json.fromJson(LawsManager.ConfigLawData.class, fileContent);

         for (Object e : data.Law) {
            laws.add((LawsManager.Law)e);
         }
      } catch (GdxRuntimeException var6) {
         CFG.exceptionStack(var6);
      }

      iLawsSize = laws.size();
      loadLawsImages();
   }

   public static final void loadLawsImages() {
      FileHandle tempFileT = FileManager.loadFile("game/laws/lawsImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/laws/lawsImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            lawsImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/laws/lawsImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         } else {
            lawsImages.add(
               new Image(
                  ImageManager.loadTexture_RGB888("game/laws/lawsImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static MenuElement_Hover getHover(int i, int j) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(laws.get(i).Title), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.law, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get(laws.get(i).Law[j]), "", Images.law, CFG.FONT_BOLD, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_LEFT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Empty());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text_Desc(
            Game.lang.get(laws.get(i).LawDesc != null ? laws.get(i).LawDesc[j] : laws.get(i).Law[j] + ".d"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (laws.get(i).RequiredTechID[j] >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RequiredTechnology") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + TechnologyTree.lTechnology.get(laws.get(i).RequiredTechID[j]).Name, CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RequiredGovernmentID != null) {
         try {
            if (laws.get(i).RequiredGovernmentID[j] >= 0) {
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Government") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.ideologiesManager.getIdeology(laws.get(i).RequiredGovernmentID[j]).Name, CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.government, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }
      }

      if (laws.get(i).ConstructionCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ConstructionCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).ConstructionCost[j] * 100.0F, 10) + "%",
               Images.construction,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AdministrationBuildingsCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AdministrationBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).AdministrationBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MilitaryBuildingsCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MilitaryBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).MilitaryBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).EconomyBuildingsCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("EconomyBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).EconomyBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ConstructionTime != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ConstructionTime") + ": ",
               "" + CFG.getPrecision2(laws.get(i).ConstructionTime[j] * 100.0F, 10) + "%",
               Images.buildTime,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).WonderConstructionCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("WonderConstructionCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).WonderConstructionCost[j] * 100.0F, 10) + "%",
               Images.mapModesWonders,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncomeEconomy != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncomeEconomy") + ": ",
               (laws.get(i).IncomeEconomy[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeEconomy[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncomeTaxation != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncomeTaxation") + ": ",
               (laws.get(i).IncomeTaxation[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeTaxation[j] * 100.0F, 10) + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).TaxEfficiency != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TaxEfficiency") + ": ",
               (laws.get(i).TaxEfficiency[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).TaxEfficiency[j], 10) + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ProvinceMaintenance != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ProvinceMaintenances") + ": ",
               (laws.get(i).ProvinceMaintenance[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ProvinceMaintenance[j], 10) + "%",
               Images.provinces,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).BuildingsMaintenanceCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("BuildingsMaintenanceCost") + ": ",
               (laws.get(i).BuildingsMaintenanceCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).BuildingsMaintenanceCost[j] * 100.0F, 10) + "%",
               Images.buildings,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ManpowerRecoverySpeed != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ManpowerRecoverySpeed") + ": ",
               (laws.get(i).ManpowerRecoverySpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ManpowerRecoverySpeed[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ArmyMoraleRecovery != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyMoraleRecovery") + ": ",
               (laws.get(i).ArmyMoraleRecovery[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMoraleRecovery[j] * 100.0F, 10) + "%",
               Images.morale,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).WarScoreCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("WarScoreCost") + ": ",
               (laws.get(i).WarScoreCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).WarScoreCost[j] * 100.0F, 10) + "%",
               Images.victoryPoints,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ReinforcementSpeed != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ReinforcementSpeed") + ": ",
               (laws.get(i).ReinforcementSpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ReinforcementSpeed[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxManpower != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumManpower") + ": ",
               (laws.get(i).MaxManpower[j] > 0 ? "+" : "") + CFG.getNumberWithSpaces("" + laws.get(i).MaxManpower[j]),
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxManpower_Percentage != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumManpower") + ": ",
               (laws.get(i).MaxManpower_Percentage[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaxManpower_Percentage[j] * 100.0F, 100) + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).Research != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Research") + ": ",
               (laws.get(i).Research[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Research[j], 10) + "%",
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ResearchPoints != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ResearchPerMonth") + ": ",
               (laws.get(i).ResearchPoints[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ResearchPoints[j], 100),
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).BuildingSlot != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AdditionalBuildingsInProvince") + ": ",
               (laws.get(i).BuildingSlot[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).BuildingSlot[j], 1),
               Images.build,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxInfrastructure != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumInfrastructureLevel") + ": ",
               (laws.get(i).MaxInfrastructure[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxInfrastructure[j], 1),
               Images.infrastructureUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).Devastation != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Devastation") + ": ",
               (laws.get(i).Devastation[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Devastation[j] * 100.0F, 10) + "%",
               Images.devastation,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).GrowthRate != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("GrowthRate") + ": ",
               (laws.get(i).GrowthRate[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).GrowthRate[j], 10) + "%",
               Images.populationGrowth,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MonthlyIncome != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncome") + ": ",
               (laws.get(i).MonthlyIncome[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyIncome[j], 100),
               Images.goldPositive,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).Gold != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Gold") + ": ",
               (laws.get(i).Gold[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Gold[j], 100),
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MonthlyLegacy != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyLegacy") + ": ",
               (laws.get(i).MonthlyLegacy[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyLegacy[j], 100),
               Images.legacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MonthlyLegacy_Percentage != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyLegacy") + ": ",
               (laws.get(i).MonthlyLegacy_Percentage[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyLegacy_Percentage[j] * 100.0F, 100) + "%",
               Images.legacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncomeProduction != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncomeProduction") + ": ",
               (laws.get(i).IncomeProduction[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeProduction[j], 10) + "%",
               Images.goods,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ProductionEfficiency != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ProductionEfficiency") + ": ",
               (laws.get(i).ProductionEfficiency[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ProductionEfficiency[j], 10) + "%",
               Images.goods,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).InvestInEconomyCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("InvestInEconomyCost") + ": ",
               (laws.get(i).InvestInEconomyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).InvestInEconomyCost[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_ECONOMY_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncreaseManpowerCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseManpowerCost") + ": ",
               (laws.get(i).IncreaseManpowerCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseManpowerCost[j], 10) + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncreaseTaxEfficiencyCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseTaxEfficiencyCost") + ": ",
               (laws.get(i).IncreaseTaxEfficiencyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseTaxEfficiencyCost[j] * 100.0F, 10) + "%",
               Images.taxUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncreaseGrowthRateCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseGrowthRateCost") + ": ",
               (laws.get(i).IncreaseGrowthRateCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseGrowthRateCost[j] * 100.0F, 10) + "%",
               Images.populationUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).DevelopInfrastructureCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DevelopInfrastructureCost") + ": ",
               (laws.get(i).DevelopInfrastructureCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DevelopInfrastructureCost[j] * 100.0F, 10) + "%",
               Images.infrastructure,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).GeneralAttack != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("GeneralsAttack") + ": ",
               (laws.get(i).GeneralAttack[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).GeneralAttack[j], 1),
               Images.attack,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).GeneralDefense != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("GeneralsDefense") + ": ",
               (laws.get(i).GeneralDefense[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).GeneralDefense[j], 1),
               Images.defense,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).UnitsAttack != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("UnitsAttack") + ": ",
               (laws.get(i).UnitsAttack[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).UnitsAttack[j], 1),
               Images.attack,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).UnitsDefense != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("UnitsDefense") + ": ",
               (laws.get(i).UnitsDefense[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).UnitsDefense[j], 1),
               Images.defense,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxMorale != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaxMorale") + ": ",
               (laws.get(i).MaxMorale[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaxMorale[j] * 100.0F, 10) + "%",
               Images.morale,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ArmyMovementSpeed != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyMovementSpeed") + ": ",
               (laws.get(i).ArmyMovementSpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMovementSpeed[j], 10) + "%",
               Images.movementSpeed,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).SiegeEffectiveness != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("SiegeEffectiveness") + ": ",
               (laws.get(i).SiegeEffectiveness[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).SiegeEffectiveness[j] * 100.0F, 10) + "%",
               Images.siege,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ImproveRelationsModifier != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ImproveRelationsModifier") + ": ",
               (laws.get(i).ImproveRelationsModifier[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ImproveRelationsModifier[j], 10) + "%",
               Images.relations,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).IncomeFromVassals != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncomeFromVassals") + ": ",
               (laws.get(i).IncomeFromVassals[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeFromVassals[j] * 100.0F, 10) + "%",
               Images.vassal,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).LoanInterest != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("LoanInterest") + ": ",
               (laws.get(i).LoanInterest[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).LoanInterest[j], 10) + "%",
               Images.loan,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).DiplomacyPoints != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DiplomacyPoints") + ": ",
               (laws.get(i).DiplomacyPoints[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DiplomacyPoints[j] * 100.0F, 10) + "%",
               Images.diplomacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RecruitmentTime != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("RecruitmentTime") + ": ",
               (laws.get(i).RecruitmentTime[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitmentTime[j], 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RecruitArmyCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmyCost[j], 10) + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RecruitArmyFirstLineCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("FirstLineArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmyFirstLineCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmyFirstLineCost[j], 10) + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RecruitArmySecondLineCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("SecondLineArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmySecondLineCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmySecondLineCost[j], 10) + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ArmyMaintenance != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyMaintenance") + ": ",
               (laws.get(i).ArmyMaintenance[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMaintenance[j], 10) + "%",
               Images.armyMaintenance,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).CoreCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("CoreConstruction") + ": ",
               (laws.get(i).CoreCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).CoreCost[j], 10) + "%",
               Images.core,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ReligionCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ReligionConversionCost") + ": ",
               (laws.get(i).ReligionCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ReligionCost[j], 10) + "%",
               Images.religion,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxNumOfAlliances != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaxNumOfAlliances") + ": ",
               (laws.get(i).MaxNumOfAlliances[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxNumOfAlliances[j], 1),
               Images.alliance,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AdvisorMaxLevel != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumAdvisorSkillLevel") + ": ",
               (laws.get(i).AdvisorMaxLevel[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).AdvisorMaxLevel[j], 1),
               Images.skill,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AdvisorPoolSize != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AdvisorPool") + ": ",
               (laws.get(i).AdvisorPoolSize[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).AdvisorPoolSize[j], 1),
               Images.council,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaxNumberOfLoans != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumNumberOfLoans") + ": ",
               (laws.get(i).MaxNumberOfLoans[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxNumberOfLoans[j], 1),
               Images.loan,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumLevelOfTheMilitaryAcademyForGenerals") + ": ",
               (laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j] >= 0 ? "+" : "")
                  + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j], 1),
               Images.general,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumLevelOfTheMilitaryAcademy != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumLevelOfTheMilitaryAcademy") + ": ",
               (laws.get(i).MaximumLevelOfTheMilitaryAcademy[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheMilitaryAcademy[j], 1),
               Game_Calendar.IMG_MANPOWER,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumLevelOfTheSupremeCourt != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumLevelOfTheSupremeCourt") + ": ",
               (laws.get(i).MaximumLevelOfTheSupremeCourt[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheSupremeCourt[j], 1),
               Images.stability,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumLevelOfCapitalCity != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumLevelOfCapitalCity") + ": ",
               (laws.get(i).MaximumLevelOfCapitalCity[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfCapitalCity[j], 1),
               Images.capital,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AggressiveExpansion != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AggressiveExpansion") + ": ",
               (laws.get(i).AggressiveExpansion[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).AggressiveExpansion[j], 10) + "%",
               Images.aggressiveExpansion,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).DiseaseDeathRate != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DiseasesDeathRate") + ": ",
               (laws.get(i).DiseaseDeathRate[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DiseaseDeathRate[j] * 100.0F, 10) + "%",
               Images.disease,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).ManpowerRecoveryFromADisbandedArmy != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ",
               (laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] * 100.0F, 10)
                  + "%",
               Game_Calendar.IMG_MANPOWER_DISBAND,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AdvisorCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AdvisorCost") + ": ",
               (laws.get(i).AdvisorCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).AdvisorCost[j] * 100.0F, 10) + "%",
               Images.council,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).GeneralCost != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("GeneralCost") + ": ",
               (laws.get(i).GeneralCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).GeneralCost[j] * 100.0F, 10) + "%",
               Images.general,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).Discipline != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Discipline") + ": ",
               (laws.get(i).Discipline[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Discipline[j] * 100.0F, 10) + "%",
               Images.discipline,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumAmountOfGold != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumAmountOfGold") + ": ",
               (laws.get(i).MaximumAmountOfGold[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaximumAmountOfGold[j], 10),
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).MaximumAmountOfGold_Percentage != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumAmountOfGold") + ": ",
               (laws.get(i).MaximumAmountOfGold_Percentage[j] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(laws.get(i).MaximumAmountOfGold_Percentage[j] * 100.0F, 100)
                  + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).Loot != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Loot") + ": ",
               (laws.get(i).Loot[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Loot[j] * 100.0F, 10) + "%",
               Images.loot,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).BattleWidth != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("BattleWidth") + ": ",
               (laws.get(i).BattleWidth[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).BattleWidth[j], 1),
               Images.battleWidth,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).RegimentsLimit != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("RegimentsLimit") + ": ",
               (laws.get(i).RegimentsLimit[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).RegimentsLimit[j], 1),
               Images.regimentsLimit,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).AllCharactersLifeExpectancy != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("AllCharactersLifeExpectancy") + ": ",
               (laws.get(i).AllCharactersLifeExpectancy[j] > 0 ? "+" : "") + Game.lang.get("YearsX", laws.get(i).AllCharactersLifeExpectancy[j]),
               Images.council,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (laws.get(i).UnlocksColonization != null) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               "",
               laws.get(i).UnlocksColonization[j] ? Game.lang.get("ColonizationAllowed") : Game.lang.get("NoColonizationAllowed"),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LegacyPoints") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.legacy, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (CFG.debugMode) {
         nData.add(new MenuElement_HoverElement_Type_Button_TextBonus("ID: ", "" + i, Images.law, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Button_TextBonus("ID2: ", "" + j, Images.law, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      return new MenuElement_Hover(nElements);
   }

   public static List<MenuElement> getLawBonuses(int i, int j, int paddingLeft, int menuWidth) {
      List<MenuElement> menuElements = new ArrayList<>();
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      int statH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      if (laws.get(i).ConstructionCost != null && laws.get(i).ConstructionCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).ConstructionCost[j] * 100.0F, 10) + "%",
               Images.construction,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AdministrationBuildingsCost != null && laws.get(i).AdministrationBuildingsCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AdministrationBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).AdministrationBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MilitaryBuildingsCost != null && laws.get(i).MilitaryBuildingsCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MilitaryBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).MilitaryBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).EconomyBuildingsCost != null && laws.get(i).EconomyBuildingsCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("EconomyBuildingsCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).EconomyBuildingsCost[j] * 100.0F, 10) + "%",
               Images.construction,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ConstructionTime != null && laws.get(i).ConstructionTime[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionTime") + ": ",
               "" + CFG.getPrecision2(laws.get(i).ConstructionTime[j] * 100.0F, 10) + "%",
               Images.buildTime,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).WonderConstructionCost != null && laws.get(i).WonderConstructionCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("WonderConstructionCost") + ": ",
               "" + CFG.getPrecision2(laws.get(i).WonderConstructionCost[j] * 100.0F, 10) + "%",
               Images.mapModesWonders,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncomeEconomy != null && laws.get(i).IncomeEconomy[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyIncomeEconomy") + ": ",
               (laws.get(i).IncomeEconomy[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeEconomy[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_ECONOMY,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncomeTaxation != null && laws.get(i).IncomeTaxation[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncomeTaxation") + ": ",
               (laws.get(i).IncomeTaxation[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeTaxation[j] * 100.0F, 10) + "%",
               Images.tax,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).TaxEfficiency != null && laws.get(i).TaxEfficiency[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("TaxEfficiency") + ": ",
               (laws.get(i).TaxEfficiency[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).TaxEfficiency[j], 10) + "%",
               Images.tax,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ProvinceMaintenance != null && laws.get(i).ProvinceMaintenance[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProvinceMaintenances") + ": ",
               (laws.get(i).ProvinceMaintenance[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ProvinceMaintenance[j], 10) + "%",
               Images.provinces,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).BuildingsMaintenanceCost != null && laws.get(i).BuildingsMaintenanceCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("BuildingsMaintenanceCost") + ": ",
               (laws.get(i).BuildingsMaintenanceCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).BuildingsMaintenanceCost[j] * 100.0F, 10) + "%",
               Images.buildings,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ManpowerRecoverySpeed != null && laws.get(i).ManpowerRecoverySpeed[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ManpowerRecoverySpeed") + ": ",
               (laws.get(i).ManpowerRecoverySpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ManpowerRecoverySpeed[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ArmyMoraleRecovery != null && laws.get(i).ArmyMoraleRecovery[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyMoraleRecovery") + ": ",
               (laws.get(i).ArmyMoraleRecovery[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMoraleRecovery[j] * 100.0F, 10) + "%",
               Images.morale,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).WarScoreCost != null && laws.get(i).WarScoreCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("WarScoreCost") + ": ",
               (laws.get(i).WarScoreCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).WarScoreCost[j] * 100.0F, 10) + "%",
               Images.victoryPoints,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ReinforcementSpeed != null && laws.get(i).ReinforcementSpeed[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ReinforcementSpeed") + ": ",
               (laws.get(i).ReinforcementSpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ReinforcementSpeed[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxManpower != null && laws.get(i).MaxManpower[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumManpower") + ": ",
               (laws.get(i).MaxManpower[j] > 0 ? "+" : "") + CFG.getNumberWithSpaces("" + laws.get(i).MaxManpower[j]),
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxManpower_Percentage != null && laws.get(i).MaxManpower_Percentage[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumManpower") + ": ",
               (laws.get(i).MaxManpower_Percentage[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaxManpower_Percentage[j] * 100.0F, 100) + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).Research != null && laws.get(i).Research[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Research") + ": ",
               (laws.get(i).Research[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Research[j], 10) + "%",
               Game_Calendar.IMG_TECHNOLOGY,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ResearchPoints != null && laws.get(i).ResearchPoints[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ResearchPerMonth") + ": ",
               (laws.get(i).ResearchPoints[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ResearchPoints[j], 100),
               Game_Calendar.IMG_TECHNOLOGY,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).BuildingSlot != null && laws.get(i).BuildingSlot[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AdditionalBuildingsInProvince") + ": ",
               (laws.get(i).BuildingSlot[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).BuildingSlot[j], 1),
               Images.build,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxInfrastructure != null && laws.get(i).MaxInfrastructure[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumInfrastructureLevel") + ": ",
               (laws.get(i).MaxInfrastructure[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxInfrastructure[j], 1),
               Images.infrastructureUp,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).Devastation != null && laws.get(i).Devastation[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Devastation") + ": ",
               (laws.get(i).Devastation[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Devastation[j] * 100.0F, 10) + "%",
               Images.devastation,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).GrowthRate != null && laws.get(i).GrowthRate[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GrowthRate") + ": ",
               (laws.get(i).GrowthRate[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).GrowthRate[j], 10) + "%",
               Images.populationGrowth,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MonthlyIncome != null && laws.get(i).MonthlyIncome[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyIncome") + ": ",
               (laws.get(i).MonthlyIncome[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyIncome[j], 100),
               Images.goldPositive,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).Gold != null && laws.get(i).Gold[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Gold") + ": ",
               (laws.get(i).Gold[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Gold[j], 100),
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MonthlyLegacy != null && laws.get(i).MonthlyLegacy[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyLegacy") + ": ",
               (laws.get(i).MonthlyLegacy[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyLegacy[j], 100),
               Images.legacy,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MonthlyLegacy_Percentage != null && laws.get(i).MonthlyLegacy_Percentage[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyLegacy") + ": ",
               (laws.get(i).MonthlyLegacy_Percentage[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MonthlyLegacy_Percentage[j] * 100.0F, 100) + "%",
               Images.legacy,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncomeProduction != null && laws.get(i).IncomeProduction[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncomeProduction") + ": ",
               (laws.get(i).IncomeProduction[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeProduction[j], 10) + "%",
               Images.goods,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ProductionEfficiency != null && laws.get(i).ProductionEfficiency[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProductionEfficiency") + ": ",
               (laws.get(i).ProductionEfficiency[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ProductionEfficiency[j], 10) + "%",
               Images.goods,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).InvestInEconomyCost != null && laws.get(i).InvestInEconomyCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("InvestInEconomyCost") + ": ",
               (laws.get(i).InvestInEconomyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).InvestInEconomyCost[j] * 100.0F, 10) + "%",
               Game_Calendar.IMG_ECONOMY_UP,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncreaseManpowerCost != null && laws.get(i).IncreaseManpowerCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseManpowerCost") + ": ",
               (laws.get(i).IncreaseManpowerCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseManpowerCost[j], 10) + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncreaseTaxEfficiencyCost != null && laws.get(i).IncreaseTaxEfficiencyCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseTaxEfficiencyCost") + ": ",
               (laws.get(i).IncreaseTaxEfficiencyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseTaxEfficiencyCost[j] * 100.0F, 10) + "%",
               Images.taxUp,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncreaseGrowthRateCost != null && laws.get(i).IncreaseGrowthRateCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseGrowthRateCost") + ": ",
               (laws.get(i).IncreaseGrowthRateCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncreaseGrowthRateCost[j] * 100.0F, 10) + "%",
               Images.populationUp,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).DevelopInfrastructureCost != null && laws.get(i).DevelopInfrastructureCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DevelopInfrastructureCost") + ": ",
               (laws.get(i).DevelopInfrastructureCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DevelopInfrastructureCost[j] * 100.0F, 10) + "%",
               Images.infrastructure,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).GeneralAttack != null && laws.get(i).GeneralAttack[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GeneralsAttack") + ": ",
               (laws.get(i).GeneralAttack[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).GeneralAttack[j], 1),
               Images.attack,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).GeneralDefense != null && laws.get(i).GeneralDefense[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GeneralsDefense") + ": ",
               (laws.get(i).GeneralDefense[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).GeneralDefense[j], 1),
               Images.defense,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).UnitsAttack != null && laws.get(i).UnitsAttack[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("UnitsAttack") + ": ",
               (laws.get(i).UnitsAttack[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).UnitsAttack[j], 1),
               Images.attack,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).UnitsDefense != null && laws.get(i).UnitsDefense[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("UnitsDefense") + ": ",
               (laws.get(i).UnitsDefense[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).UnitsDefense[j], 1),
               Images.defense,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxMorale != null && laws.get(i).MaxMorale[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaxMorale") + ": ",
               (laws.get(i).MaxMorale[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaxMorale[j] * 100.0F, 10) + "%",
               Images.morale,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ArmyMovementSpeed != null && laws.get(i).ArmyMovementSpeed[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyMovementSpeed") + ": ",
               (laws.get(i).ArmyMovementSpeed[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMovementSpeed[j], 10) + "%",
               Images.movementSpeed,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).SiegeEffectiveness != null && laws.get(i).SiegeEffectiveness[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("SiegeEffectiveness") + ": ",
               (laws.get(i).SiegeEffectiveness[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).SiegeEffectiveness[j] * 100.0F, 10) + "%",
               Images.siege,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ImproveRelationsModifier != null && laws.get(i).ImproveRelationsModifier[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ImproveRelationsModifier") + ": ",
               (laws.get(i).ImproveRelationsModifier[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ImproveRelationsModifier[j], 10) + "%",
               Images.relations,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).IncomeFromVassals != null && laws.get(i).IncomeFromVassals[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncomeFromVassals") + ": ",
               (laws.get(i).IncomeFromVassals[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).IncomeFromVassals[j] * 100.0F, 10) + "%",
               Images.vassal,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).LoanInterest != null && laws.get(i).LoanInterest[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LoanInterest") + ": ",
               (laws.get(i).LoanInterest[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).LoanInterest[j], 10) + "%",
               Images.loan,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).DiplomacyPoints != null && laws.get(i).DiplomacyPoints[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DiplomacyPoints") + ": ",
               (laws.get(i).DiplomacyPoints[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DiplomacyPoints[j] * 100.0F, 10) + "%",
               Images.diplomacy,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).RecruitmentTime != null && laws.get(i).RecruitmentTime[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("RecruitmentTime") + ": ",
               (laws.get(i).RecruitmentTime[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitmentTime[j], 10) + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).RecruitArmyCost != null && laws.get(i).RecruitArmyCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmyCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmyCost[j], 10) + "%",
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).RecruitArmyFirstLineCost != null && laws.get(i).RecruitArmyFirstLineCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("FirstLineArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmyFirstLineCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmyFirstLineCost[j], 10) + "%",
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).RecruitArmySecondLineCost != null && laws.get(i).RecruitArmySecondLineCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("SecondLineArmyRecruitmentCost") + ": ",
               (laws.get(i).RecruitArmySecondLineCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).RecruitArmySecondLineCost[j], 10) + "%",
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ArmyMaintenance != null && laws.get(i).ArmyMaintenance[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyMaintenance") + ": ",
               (laws.get(i).ArmyMaintenance[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ArmyMaintenance[j], 10) + "%",
               Images.armyMaintenance,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).CoreCost != null && laws.get(i).CoreCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("CoreConstruction") + ": ",
               (laws.get(i).CoreCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).CoreCost[j], 10) + "%",
               Images.core,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ReligionCost != null && laws.get(i).ReligionCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ReligionConversionCost") + ": ",
               (laws.get(i).ReligionCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).ReligionCost[j], 10) + "%",
               Images.religion,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxNumOfAlliances != null && laws.get(i).MaxNumOfAlliances[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaxNumOfAlliances") + ": ",
               (laws.get(i).MaxNumOfAlliances[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxNumOfAlliances[j], 1),
               Images.alliance,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AdvisorMaxLevel != null && laws.get(i).AdvisorMaxLevel[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumAdvisorSkillLevel") + ": ",
               (laws.get(i).AdvisorMaxLevel[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).AdvisorMaxLevel[j], 1),
               Images.skill,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AdvisorPoolSize != null && laws.get(i).AdvisorPoolSize[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AdvisorPool") + ": ",
               (laws.get(i).AdvisorPoolSize[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).AdvisorPoolSize[j], 1),
               Images.council,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaxNumberOfLoans != null && laws.get(i).MaxNumberOfLoans[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumNumberOfLoans") + ": ",
               (laws.get(i).MaxNumberOfLoans[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaxNumberOfLoans[j], 1),
               Images.loan,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals != null && laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumLevelOfTheMilitaryAcademyForGenerals") + ": ",
               (laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j] >= 0 ? "+" : "")
                  + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheMilitaryAcademyForGenerals[j], 1),
               Images.general,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumLevelOfTheMilitaryAcademy != null && laws.get(i).MaximumLevelOfTheMilitaryAcademy[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumLevelOfTheMilitaryAcademy") + ": ",
               (laws.get(i).MaximumLevelOfTheMilitaryAcademy[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheMilitaryAcademy[j], 1),
               Game_Calendar.IMG_MANPOWER,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumLevelOfTheSupremeCourt != null && laws.get(i).MaximumLevelOfTheSupremeCourt[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumLevelOfTheSupremeCourt") + ": ",
               (laws.get(i).MaximumLevelOfTheSupremeCourt[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfTheSupremeCourt[j], 1),
               Images.stability,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumLevelOfCapitalCity != null && laws.get(i).MaximumLevelOfCapitalCity[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumLevelOfCapitalCity") + ": ",
               (laws.get(i).MaximumLevelOfCapitalCity[j] >= 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).MaximumLevelOfCapitalCity[j], 1),
               Images.capital,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AggressiveExpansion != null && laws.get(i).AggressiveExpansion[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AggressiveExpansion") + ": ",
               (laws.get(i).AggressiveExpansion[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).AggressiveExpansion[j], 10) + "%",
               Images.aggressiveExpansion,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).DiseaseDeathRate != null && laws.get(i).DiseaseDeathRate[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DiseasesDeathRate") + ": ",
               (laws.get(i).DiseaseDeathRate[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).DiseaseDeathRate[j] * 100.0F, 10) + "%",
               Images.disease,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).ManpowerRecoveryFromADisbandedArmy != null && laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ",
               (laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(laws.get(i).ManpowerRecoveryFromADisbandedArmy[j] * 100.0F, 10)
                  + "%",
               Game_Calendar.IMG_MANPOWER_DISBAND,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AdvisorCost != null && laws.get(i).AdvisorCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AdvisorCost") + ": ",
               (laws.get(i).AdvisorCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).AdvisorCost[j] * 100.0F, 10) + "%",
               Images.council,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).GeneralCost != null && laws.get(i).GeneralCost[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GeneralCost") + ": ",
               (laws.get(i).GeneralCost[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).GeneralCost[j] * 100.0F, 10) + "%",
               Images.general,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).Discipline != null && laws.get(i).Discipline[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Discipline") + ": ",
               (laws.get(i).Discipline[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Discipline[j] * 100.0F, 10) + "%",
               Images.discipline,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumAmountOfGold != null && laws.get(i).MaximumAmountOfGold[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumAmountOfGold") + ": ",
               (laws.get(i).MaximumAmountOfGold[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).MaximumAmountOfGold[j], 10),
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).MaximumAmountOfGold_Percentage != null && laws.get(i).MaximumAmountOfGold_Percentage[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumAmountOfGold") + ": ",
               (laws.get(i).MaximumAmountOfGold_Percentage[j] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(laws.get(i).MaximumAmountOfGold_Percentage[j] * 100.0F, 100)
                  + "%",
               Images.gold,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).Loot != null && laws.get(i).Loot[j] != 0.0F) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Loot") + ": ",
               (laws.get(i).Loot[j] > 0.0F ? "+" : "") + CFG.getPrecision2(laws.get(i).Loot[j] * 100.0F, 10) + "%",
               Images.loot,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).BattleWidth != null && laws.get(i).BattleWidth[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("BattleWidth") + ": ",
               (laws.get(i).BattleWidth[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).BattleWidth[j], 1),
               Images.battleWidth,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).RegimentsLimit != null && laws.get(i).RegimentsLimit[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("RegimentsLimit") + ": ",
               (laws.get(i).RegimentsLimit[j] > 0 ? "+" : "") + CFG.getPrecision2((float)laws.get(i).RegimentsLimit[j], 1),
               Images.regimentsLimit,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).AllCharactersLifeExpectancy != null && laws.get(i).AllCharactersLifeExpectancy[j] != 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AllCharactersLifeExpectancy") + ": ",
               (laws.get(i).AllCharactersLifeExpectancy[j] > 0 ? "+" : "") + Game.lang.get("YearsX", laws.get(i).AllCharactersLifeExpectancy[j]),
               Images.council,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      if (laws.get(i).UnlocksColonization != null) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               "",
               laws.get(i).UnlocksColonization[j] ? Game.lang.get("ColonizationAllowed") : Game.lang.get("NoColonizationAllowed"),
               Images.population,
               paddingLeft,
               0,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            )
         );
      }

      return menuElements;
   }

   public static final boolean adoptReform(int iCivID, int lawID, int lawID2) {
      if (Game.getCiv(iCivID).fLegacy < GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS) {
         return false;
      } else if (Game.getCiv(iCivID).fGold < GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD) {
         return false;
      } else if (Game.getCiv(iCivID).laws.get(lawID) == lawID2) {
         return false;
      } else if (laws.get(lawID).RequiredTechID[lawID2] >= 0 && !Game.getCiv(iCivID).getTechResearched(laws.get(lawID).RequiredTechID[lawID2])) {
         return false;
      } else if (laws.get(lawID).RequiredGovernmentID != null
         && laws.get(lawID).RequiredGovernmentID[lawID2] >= 0
         && laws.get(lawID).RequiredGovernmentID[lawID2] != Game.getCiv(iCivID).getIdeologyID()) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iCivID);
         var10000.fGold = var10000.fGold - GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD;
         var10000 = Game.getCiv(iCivID);
         var10000.fLegacy = var10000.fLegacy - GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS;
         updateCivBonuses(lawID, Game.getCiv(iCivID).laws.get(lawID), iCivID, -1.0F);

         try {
            Game.getCiv(iCivID).adoptReform(lawID, lawID2);
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }

         updateCivBonuses(lawID, Game.getCiv(iCivID).laws.get(lawID), iCivID, 1.0F);
         return true;
      }
   }

   public static final int getAvailableLaws(int iCivID, int lawID) {
      int out = 0;
      int i = 0;

      for (int iSize = laws.get(lawID).RequiredTechID.length; i < iSize; i++) {
         if (laws.get(lawID).RequiredTechID[i] < 0 || Game.getCiv(iCivID).getTechResearched(laws.get(lawID).RequiredTechID[i])) {
            if (laws.get(lawID).RequiredGovernmentID == null) {
               out++;
            } else {
               try {
                  if (laws.get(lawID).RequiredGovernmentID[i] < 0 || laws.get(lawID).RequiredGovernmentID[i] == Game.getCiv(iCivID).getIdeologyID()) {
                     out++;
                  }
               } catch (Exception var6) {
                  CFG.exceptionStack(var6);
               }
            }
         }
      }

      return out;
   }

   public static class ConfigLawData {
      public String Age_of_History;
      public ArrayList Law;
   }

   public static class Law {
      public int[] ImageID;
      public String Title;
      public String[] Law;
      public String[] LawDesc;
      public int[] RequiredTechID;
      public int[] RequiredGovernmentID;
      public float[] MonthlyIncome;
      public float[] MonthlyLegacy;
      public float[] MonthlyLegacy_Percentage;
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
      public float[] MaxManpower_Percentage;
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
      public float[] IncomeTaxation;
      public float[] IncomeEconomy;
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
      public boolean[] UnlocksColonization;
   }
}
