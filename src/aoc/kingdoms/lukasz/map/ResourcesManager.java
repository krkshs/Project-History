package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_Goods;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class ResourcesManager {
   public static List<ResourcesManager.Resources> lResources = new ArrayList<>();
   public static int iResourcesSize = 0;
   public static List<Image> resourceImages = new ArrayList<>();
   public static List<Float> priceChangePerc = new ArrayList<>();
   public static List<ResourcesManager.PriceChange> priceChanges = new ArrayList<>();
   public static float maxPrice = 0.01F;
   public static List<Boolean> uniqueGoods = new ArrayList<>();
   public static List<Integer> worldResourcesProduced = new ArrayList<>();
   public static List<Integer> worldResources_LargestProducer = new ArrayList<>();
   public static List<Integer> worldResources_LargestProducer_Amount = new ArrayList<>();
   public static int lastUpdateYear = 0;

   public static float getPrice(int iResourceID) {
      return lResources.get(iResourceID).Price * priceChangePerc.get(iResourceID);
   }

   public static float getPrice_Default(int iResourceID) {
      return lResources.get(iResourceID).Price;
   }

   public static float getMonthlyIncome(int iProvinceID) {
      return getMonthlyIncome(iProvinceID, Game.getProvince(iProvinceID).getResourceID());
   }

   public static float getMonthlyIncome(int iProvinceID, int iResourceID) {
      return getMonthlyIncome(iProvinceID, iResourceID, getProductionEfficiency(iProvinceID));
   }

   public static float getMonthlyIncome(int iProvinceID, int iResourceID, float productionEfficiency) {
      if (iResourceID < 0) {
         return 0.0F;
      } else {
         return lResources.get(iResourceID).RequiredTechID >= 0
               && !Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getTechResearched(lResources.get(iResourceID).RequiredTechID)
            ? 0.0F
            : getPrice(iResourceID)
               * productionEfficiency
               * Math.max(
                  0.01F,
                  1.0F
                     - Game.getProvince(iProvinceID).getDevastation()
                     + (
                        Game.getProvince(iProvinceID).getReligion() == Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getReligionID()
                           ? 0.0F
                           : GameValues.religion.BASE_INCOME_DIFFERENT_RELIGION
                     )
                     + (Game.getProvince(iProvinceID).haveACore ? 0.0F : GameValues.core.BASE_INCOME_NON_CORE)
                     - Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getCorruption()
                     + (
                           Game.getCiv(Game.getProvince(iProvinceID).getCivID()).civBonuses.IncomeProduction
                              + Game.getProvince(iProvinceID).provBonuses.IncomeProduction
                        )
                        / 100.0F
               );
      }
   }

   public static float getProductionEfficiency(int iProvinceID) {
      return (GameValues.production.BASE_PRODUCTION_EFFICIENCY + getProductionEfficiency_FromEconomy(iProvinceID))
         * Math.max(
            0.01F,
            1.0F
               + (
                     Game.getProvince(iProvinceID).provBonuses.ProductionEfficiency
                        + Game.getCiv(Game.getProvince(iProvinceID).getCivID()).civBonuses.ProductionEfficiency
                  )
                  / 100.0F
               + GameValues.infrastructure.INFRASTRUCTURE_PRODUCTION_EFFICIENCY_PER_LVL * Game.getProvince(iProvinceID).getInfrastructure()
         );
   }

   public static float getProductionEfficiency_FromEconomy(int iProvinceID) {
      return Game.getProvince(iProvinceID).getEconomyWithBonuses() * GameValues.production.PRODUCTION_EFFICIENCY_PER_ECONOMY;
   }

   public static float getProductionEfficiency_FromEconomy(float fEconomy) {
      return fEconomy * GameValues.production.PRODUCTION_EFFICIENCY_PER_ECONOMY;
   }

   public static float getProducedGoods(int iProvinceID) {
      if (Game.getProvince(iProvinceID).getResourceID() < 0) {
         return 0.0F;
      } else {
         return lResources.get(Game.getProvince(iProvinceID).getResourceID()).RequiredTechID >= 0
               && !Game.getCiv(Game.getProvince(iProvinceID).getCivID())
                  .getTechResearched(lResources.get(Game.getProvince(iProvinceID).getResourceID()).RequiredTechID)
            ? 0.0F
            : Math.max(
               0.0F,
               (
                     GameValues.production.BASE_PRODUCTION
                        + GameValues.production.GOODS_PRODUCED_PER_ECONOMY
                           * Game.getProvince(iProvinceID).getEconomyWithBonuses()
                           * getProductionEfficiency(iProvinceID)
                  )
                  * (1.0F + Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getWarWeariness() * GameValues.warWeariness.WW_GOODS_PRODUCTION_PER_POINT)
            );
      }
   }

   public static float getProducedGoods_ResourceCiv(int civID, int resourceID) {
      float out = 0.0F;
      Civilization civ = Game.getCiv(civID);

      for (int i = 0; i < civ.getNumOfProvinces(); i++) {
         if (Game.getProvince(civ.getProvinceID(i)).getResourceID() == resourceID) {
            out += getProducedGoods(civ.getProvinceID(i));
         }
      }

      return (int)out;
   }

   public static int getLargestGoodsProducedByCiv(int civID) {
      List<Float> goodsProduced = new ArrayList<>();

      for (int i = 0; i < iResourcesSize; i++) {
         goodsProduced.add(0.0F);
      }

      int bestResourceID = -1;

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getResourceID() >= 0) {
            goodsProduced.set(
               Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getResourceID(),
               goodsProduced.get(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getResourceID()) + getProducedGoods(Game.getCiv(civID).getProvinceID(i))
            );
            bestResourceID = Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getResourceID();
         }
      }

      if (bestResourceID >= 0) {
         for (int ix = 0; ix < iResourcesSize; ix++) {
            if (goodsProduced.get(bestResourceID) < goodsProduced.get(ix)) {
               bestResourceID = ix;
            }
         }
      }

      goodsProduced.clear();
      return bestResourceID;
   }

   public static final void resetPriceChangePerc() {
      for (int i = 0; i < lResources.size(); i++) {
         priceChangePerc.set(i, 1.0F);
      }

      priceChanges.clear();
   }

   public static final void updatePriceChanges() {
      try {
         if (Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_CHANGE_PRICE_EXPIRED == 0) {
            for (int i = priceChanges.size() - 1; i >= 0; i--) {
               if (priceChanges.get(i).expiresTurnID <= Game_Calendar.TURN_ID) {
                  if (updatePriceChanges_NumOfPriceChanges(priceChanges.get(i).resourceID) == 1) {
                     priceChangePerc.set(priceChanges.get(i).resourceID, 1.0F);
                  } else {
                     priceChangePerc.set(
                        priceChanges.get(i).resourceID, Math.max(0.01F, priceChangePerc.get(priceChanges.get(i).resourceID) - priceChanges.get(i).priceChange)
                     );
                  }

                  priceChanges.remove(i);
               }
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static int updatePriceChanges_NumOfPriceChanges(int resourceID) {
      int out = 0;

      for (int i = priceChanges.size() - 1; i >= 0; i--) {
         if (priceChanges.get(i).resourceID == resourceID) {
            out++;
         }
      }

      return out;
   }

   public static final void setPriceChangePerc(int resourceID, float newPriceChangePerc, int expiresTurnID) {
      float newPricePerc = Math.max(0.01F, priceChangePerc.get(resourceID) + newPriceChangePerc);
      float changeInPrice = newPricePerc - priceChangePerc.get(resourceID);
      if (Math.abs(changeInPrice) >= 0.01F) {
         priceChangePerc.set(resourceID, newPricePerc);
         priceChanges.add(new ResourcesManager.PriceChange(resourceID, changeInPrice, expiresTurnID));
         updateIncomeOfCivsWithResource(resourceID);
      }
   }

   public static void updateIncomeOfCivsWithResource(int iResourceID) {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         for (int j = 0; j < Game.getCiv(i).getNumOfProvinces(); j++) {
            if (Game.getProvince(Game.getCiv(i).getProvinceID(j)).getResourceID() == iResourceID) {
               Game.gameThread.addCivUpdateTotalIncomePerMonth(i);
               break;
            }
         }
      }
   }

   public static final void initUniqueCivsGoods() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).iUniqueResources = 0;
      }

      updateUniqueCivsGoods();
   }

   public static final void updateUniqueCivsGoods() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            updateUniqueCivGoods(i);
         }
      }
   }

   public static final void updateUniqueCivGoods(int iCivID) {
      try {
         for (int i = 0; i < iResourcesSize; i++) {
            uniqueGoods.set(i, false);
         }

         for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
            if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getResourceID() >= 0) {
               uniqueGoods.set(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getResourceID(), true);
            }
         }

         Game.getCiv(iCivID).iUniqueResources = 0;

         for (int ix = 0; ix < iResourcesSize; ix++) {
            if (uniqueGoods.get(ix)) {
               Game.getCiv(iCivID).iUniqueResources++;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void updateWorldResourcesProduced_NewYear() {
      if (Game_Calendar.currentYear != lastUpdateYear) {
         updateWorldResourcesProduced(false);
         if (Game.menuManager.getVisibleInGame_Goods() && InGame_Goods.inGoodsView) {
            Game.addSimpleTask(new Game.SimpleTask("rebuildGoods") {
               @Override
               public void update() {
                  if (Game.menuManager.getVisibleInGame_Goods() && InGame_Goods.inGoods) {
                     Game.menuManager.rebuildInGame_Goods(true);
                     Game.menuManager.setVisibleInGame_Goods(true);
                     InGame_Goods.lTime = 0L;
                  }
               }
            });
         }
      }
   }

   private static final void updateCivBonuses(int i, int iCivID, int mod) {
      if (lResources.get(i).MonthlyIncome != 0.0F) {
         CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.MonthlyIncome = var10000.MonthlyIncome + lResources.get(i).MonthlyIncome * mod;
      }

      if (lResources.get(i).TaxEfficiency != 0.0F) {
         CivilizationBonuses var3 = Game.getCiv(iCivID).civBonuses;
         var3.TaxEfficiency = var3.TaxEfficiency + lResources.get(i).TaxEfficiency * mod;
      }

      if (lResources.get(i).IncomeProduction != 0.0F) {
         CivilizationBonuses var4 = Game.getCiv(iCivID).civBonuses;
         var4.IncomeProduction = var4.IncomeProduction + lResources.get(i).IncomeProduction * mod;
      }

      if (lResources.get(i).ProductionEfficiency != 0.0F) {
         CivilizationBonuses var5 = Game.getCiv(iCivID).civBonuses;
         var5.ProductionEfficiency = var5.ProductionEfficiency + lResources.get(i).ProductionEfficiency * mod;
      }

      if (lResources.get(i).ProvinceMaintenance != 0.0F) {
         CivilizationBonuses var6 = Game.getCiv(iCivID).civBonuses;
         var6.ProvinceMaintenance = var6.ProvinceMaintenance + lResources.get(i).ProvinceMaintenance * mod;
      }

      if (lResources.get(i).GrowthRate != 0.0F) {
         CivilizationBonuses var7 = Game.getCiv(iCivID).civBonuses;
         var7.GrowthRate = var7.GrowthRate + lResources.get(i).GrowthRate * mod;
         Game.getCiv(iCivID).updateResearchPerMonth();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (lResources.get(i).MonthlyLegacy != 0.0F) {
         Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
      }

      CivilizationBonuses var8 = Game.getCiv(iCivID).civBonuses;
      var8.MonthlyLegacy = var8.MonthlyLegacy + lResources.get(i).MonthlyLegacy * mod;
      if (lResources.get(i).MaxManpower != 0.0F) {
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (lResources.get(i).MaxManpower != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.MaxManpower = var8.MaxManpower + lResources.get(i).MaxManpower * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (lResources.get(i).ManpowerRecoverySpeed != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ManpowerRecoverySpeed = var8.ManpowerRecoverySpeed + lResources.get(i).ManpowerRecoverySpeed * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      if (lResources.get(i).ArmyMaintenance != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ArmyMaintenance = var8.ArmyMaintenance + lResources.get(i).ArmyMaintenance * mod;
         Game.gameThread.addCivUpdateArmyMaintenance(iCivID);
      }

      if (lResources.get(i).RecruitmentTime != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RecruitmentTime = var8.RecruitmentTime + lResources.get(i).RecruitmentTime * mod;
      }

      if (lResources.get(i).RecruitArmyCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RecruitArmyCost = var8.RecruitArmyCost + lResources.get(i).RecruitArmyCost * mod;
      }

      if (lResources.get(i).RecruitArmyFirstLineCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RecruitArmyFirstLineCost = var8.RecruitArmyFirstLineCost + lResources.get(i).RecruitArmyFirstLineCost * mod;
      }

      if (lResources.get(i).RecruitArmySecondLineCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RecruitArmySecondLineCost = var8.RecruitArmySecondLineCost + lResources.get(i).RecruitArmySecondLineCost * mod;
      }

      if (lResources.get(i).ResearchPoints != 0.0F) {
         Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      if (lResources.get(i).ResearchPoints != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ResearchPoints = var8.ResearchPoints + lResources.get(i).ResearchPoints * mod;
      }

      if (lResources.get(i).TechnologyCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.TechnologyCost = var8.TechnologyCost + lResources.get(i).TechnologyCost * mod;
      }

      if (lResources.get(i).ConstructionCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ConstructionCost = var8.ConstructionCost + lResources.get(i).ConstructionCost * mod;
      }

      if (lResources.get(i).AdministrationBuildingsCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.AdministrationBuildingsCost = var8.AdministrationBuildingsCost + lResources.get(i).AdministrationBuildingsCost * mod;
      }

      if (lResources.get(i).MilitaryBuildingsCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.MilitaryBuildingsCost = var8.MilitaryBuildingsCost + lResources.get(i).MilitaryBuildingsCost * mod;
      }

      if (lResources.get(i).EconomyBuildingsCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.EconomyBuildingsCost = var8.EconomyBuildingsCost + lResources.get(i).EconomyBuildingsCost * mod;
      }

      if (lResources.get(i).ConstructionTime != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ConstructionTime = var8.ConstructionTime + lResources.get(i).ConstructionTime * mod;
      }

      if (lResources.get(i).InvestInEconomyCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.InvestInEconomyCost = var8.InvestInEconomyCost + lResources.get(i).InvestInEconomyCost * mod;
      }

      if (lResources.get(i).IncreaseManpowerCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.IncreaseManpowerCost = var8.IncreaseManpowerCost + lResources.get(i).IncreaseManpowerCost * mod;
      }

      if (lResources.get(i).GeneralAttack != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.GeneralAttack = var8.GeneralAttack + lResources.get(i).GeneralAttack * mod;
      }

      if (lResources.get(i).GeneralDefense != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.GeneralDefense = var8.GeneralDefense + lResources.get(i).GeneralDefense * mod;
      }

      if (lResources.get(i).UnitsAttack != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.UnitsAttack = var8.UnitsAttack + lResources.get(i).UnitsAttack * mod;
      }

      if (lResources.get(i).UnitsDefense != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.UnitsDefense = var8.UnitsDefense + lResources.get(i).UnitsDefense * mod;
      }

      if (lResources.get(i).GeneralDefense != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.MaxMorale = var8.MaxMorale + lResources.get(i).MaxMorale * mod;
      }

      if (lResources.get(i).ArmyMovementSpeed != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ArmyMovementSpeed = var8.ArmyMovementSpeed + lResources.get(i).ArmyMovementSpeed * mod;
      }

      if (lResources.get(i).SiegeEffectiveness != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.SiegeEffectiveness = var8.SiegeEffectiveness + lResources.get(i).SiegeEffectiveness * mod;
      }

      if (lResources.get(i).ImproveRelationsModifier != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.ImproveRelationsModifier = var8.ImproveRelationsModifier + lResources.get(i).ImproveRelationsModifier * mod;
      }

      if (lResources.get(i).IncomeFromVassals != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.IncomeFromVassals = var8.IncomeFromVassals + lResources.get(i).IncomeFromVassals * mod;
      }

      if (lResources.get(i).LoanInterest != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.LoanInterest = var8.LoanInterest + lResources.get(i).LoanInterest * mod;
      }

      if (lResources.get(i).AggressiveExpansion != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.AggressiveExpansion = var8.AggressiveExpansion + lResources.get(i).AggressiveExpansion * mod;
      }

      if (lResources.get(i).RevolutionaryRisk != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RevolutionaryRisk = var8.RevolutionaryRisk + lResources.get(i).RevolutionaryRisk * mod;
      }

      if (lResources.get(i).CoreCost != 0.0F) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.CoreCost = var8.CoreCost + lResources.get(i).CoreCost * mod;
      }

      if (lResources.get(i).BattleWidth != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.BattleWidth = var8.BattleWidth + lResources.get(i).BattleWidth * mod;
      }

      if (lResources.get(i).RegimentsLimit != 0) {
         var8 = Game.getCiv(iCivID).civBonuses;
         var8.RegimentsLimit = var8.RegimentsLimit + lResources.get(i).RegimentsLimit * mod;
      }

      Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
   }

   public static void updateWorldResourcesProduced(boolean init) {
      try {
         List<Boolean> playerLargest = new ArrayList<>();
         if (worldResources_LargestProducer.size() > 0) {
            for (int j = 0; j < iResourcesSize; j++) {
               playerLargest.add(worldResources_LargestProducer.get(j) == Game.player.iCivID);
            }
         } else {
            for (int j = 0; j < iResourcesSize; j++) {
               playerLargest.add(false);
            }
         }

         if (!init) {
            try {
               int i = 0;

               for (int iSize = Math.max(iResourcesSize, worldResources_LargestProducer.size()); i < iSize; i++) {
                  if (worldResources_LargestProducer.get(i) > 0
                     && Game.getCiv(worldResources_LargestProducer.get(i)).getTechResearched(lResources.get(i).RequiredTechID)) {
                     updateCivBonuses(i, worldResources_LargestProducer.get(i), -1);
                  }
               }
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }

            for (int i = 0; i < Game.getCivsSize(); i++) {
               Game.getCiv(i).resetGoodsProduced();
               Game.getCiv(i).largestProducerNum = 0;
            }
         } else {
            for (int i = 0; i < Game.getCivsSize(); i++) {
               Game.getCiv(i).initGoodsProduced();
               Game.getCiv(i).largestProducerNum = 0;
            }
         }

         for (int i = 0; i < iResourcesSize; i++) {
            worldResourcesProduced.set(i, 0);
         }

         worldResources_LargestProducer.clear();
         worldResources_LargestProducer_Amount.clear();
         lastUpdateYear = Game_Calendar.currentYear;

         for (int i = 0; i < Game.getCivsSize(); i++) {
            List<Integer> tList = new ArrayList<>();

            for (int j = 0; j < iResourcesSize; j++) {
               tList.add(0);
            }
         }

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (!Game.getProvince(i).getSeaProvince() && Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getResourceID() >= 0) {
               int goodsProduced = (int)(getProducedGoods(i) * 100.0F);
               worldResourcesProduced.set(Game.getProvince(i).getResourceID(), worldResourcesProduced.get(Game.getProvince(i).getResourceID()) + goodsProduced);
               Game.getCiv(Game.getProvince(i).getCivID()).addGoodsProduced(Game.getProvince(i).getResourceID(), goodsProduced);
            }
         }

         for (int ix = 0; ix < iResourcesSize; ix++) {
            worldResourcesProduced.set(ix, worldResourcesProduced.get(ix));
         }

         for (int ix = 0; ix < iResourcesSize; ix++) {
            int largestProducer = 0;

            for (int j = 1; j < Game.getCivsSize(); j++) {
               if (Game.getCiv(largestProducer).getGoodsProduced(ix) < Game.getCiv(j).getGoodsProduced(ix)) {
                  largestProducer = j;
               }
            }

            if (Game.getCiv(largestProducer).getTechResearched(lResources.get(ix).RequiredTechID)) {
               worldResources_LargestProducer.add(largestProducer);
               worldResources_LargestProducer_Amount.add(Game.getCiv(largestProducer).getGoodsProduced(ix));
            } else {
               worldResources_LargestProducer.add(0);
               worldResources_LargestProducer_Amount.add(Game.getCiv(largestProducer).getGoodsProduced(ix));
            }
         }

         if (!init) {
            for (int jx = 0; jx < iResourcesSize; jx++) {
               if (playerLargest.get(jx)) {
                  if (worldResources_LargestProducer.get(jx) != Game.player.iCivID) {
                     Game.player
                        .addNotification(
                           new Notification(
                              Notification.Notification_Type.NO_LONGER_LARGEST_PRODUCER,
                              Game.lang.get("WeAreNoLongerTheLargestProducerOf") + ": " + lResources.get(jx).Name,
                              jx,
                              Game_Calendar.TURN_ID,
                              Notification.Notification_BG.RED
                           )
                        );
                  }
               } else if (worldResources_LargestProducer.get(jx) == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.LARGEST_PRODUCER,
                           Game.lang.get("WeAreTheLargestProducerOf") + ": " + lResources.get(jx).Name,
                           jx,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.GREEN
                        )
                     );
               }
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      try {
         int ix = 0;

         for (int iSizex = Math.max(iResourcesSize, worldResources_LargestProducer.size()); ix < iSizex; ix++) {
            if (worldResources_LargestProducer.get(ix) > 0
               && Game.getCiv(worldResources_LargestProducer.get(ix)).getTechResearched(lResources.get(ix).RequiredTechID)) {
               updateCivBonuses(ix, worldResources_LargestProducer.get(ix), 1);
               Game.getCiv(worldResources_LargestProducer.get(ix)).largestProducerNum++;
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static final void loadResources() {
      try {
         FileHandle fileList = FileManager.loadFile("game/resources/Resources.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(ResourcesManager.ConfigResourcesData.class, "Resources", ResourcesManager.Resources.class);
         ResourcesManager.ConfigResourcesData data = json.fromJson(ResourcesManager.ConfigResourcesData.class, fileContent);
         int id = 0;

         for (Object e : data.Resources) {
            lResources.add((ResourcesManager.Resources)e);
            lResources.get(id).Name = Game.lang.get(lResources.get(id).Name);
            lResources.get(id).Color[0] = lResources.get(id).Color[0] / 255.0F;
            lResources.get(id).Color[1] = lResources.get(id).Color[1] / 255.0F;
            lResources.get(id).Color[2] = lResources.get(id).Color[2] / 255.0F;
            worldResourcesProduced.add(0);
            priceChangePerc.add(1.0F);
            maxPrice = Math.max(maxPrice, lResources.get(id).Price);
            id++;
         }

         data = null;
      } catch (GdxRuntimeException var7) {
         CFG.exceptionStack(var7);
      }

      iResourcesSize = lResources.size();

      for (int i = 0; i < iResourcesSize; i++) {
         uniqueGoods.add(false);
      }

      loadResourcesImages();

      for (int r = 0; r < iResourcesSize; r++) {
         for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
            if (BuildingsManager.buildings.get(i).RequiredResource == r) {
               lResources.get(r).uniqueBuildingID = i;
               break;
            }
         }
      }
   }

   public static final void loadResourcesImages() {
      FileHandle tempFileT = FileManager.loadFile("game/resources/resourcesImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/resources/resourcesImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            resourceImages.add(
               new Image(
                  ImageManager.loadTexture("game/resources/resourcesImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         } else {
            resourceImages.add(
               new Image(
                  ImageManager.loadTexture("game/resources/resourcesImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static String getResourceName(int iID) {
      return iID >= 0 ? lResources.get(iID).Name : Game.lang.get("None");
   }

   public static String getResourceGroupName(int i) {
      switch (i) {
         case 0:
            return Game.lang.get("Food");
         case 1:
            return Game.lang.get("Commodities");
         case 2:
            return Game.lang.get("LuxuryCommodities");
         case 3:
            return Game.lang.get("Luxury");
         case 4:
            return Game.lang.get("ProductionResources");
         default:
            return Game.lang.get("Commodities");
      }
   }

   public static boolean hasResource(int civID, int resourceID) {
      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getResourceID() == resourceID) {
            return true;
         }
      }

      return false;
   }

   public static class ConfigResourcesData {
      public String Age_of_History;
      public ArrayList Resources;
   }

   public static class PriceChange {
      public int resourceID;
      public float priceChange;
      public int expiresTurnID;

      public PriceChange(int resourceID, float priceChange, int expiresTurnID) {
         this.resourceID = resourceID;
         this.priceChange = priceChange;
         this.expiresTurnID = expiresTurnID;
      }
   }

   public static class Resources {
      public String Name;
      public int ID;
      public int ImageID;
      public int GroupID;
      public int uniqueBuildingID = -1;
      public float[] Color;
      public float Price;
      public int RequiredTechID;
      public float MonthlyIncome;
      public float IncomeProduction;
      public float ProductionEfficiency;
      public float TaxEfficiency;
      public float ProvinceMaintenance;
      public float GrowthRate;
      public float MonthlyLegacy;
      public float MaxManpower;
      public float ManpowerRecoverySpeed;
      public float ArmyMaintenance;
      public float RecruitmentTime;
      public float RecruitArmyCost;
      public float RecruitArmyFirstLineCost;
      public float RecruitArmySecondLineCost;
      public float ResearchPoints;
      public float TechnologyCost;
      public float ConstructionCost;
      public float AdministrationBuildingsCost;
      public float MilitaryBuildingsCost;
      public float EconomyBuildingsCost;
      public float ConstructionTime;
      public float InvestInEconomyCost;
      public float IncreaseManpowerCost;
      public int GeneralAttack;
      public int GeneralDefense;
      public int UnitsAttack;
      public int UnitsDefense;
      public float MaxMorale;
      public float ArmyMovementSpeed;
      public float SiegeEffectiveness;
      public float ImproveRelationsModifier;
      public float IncomeFromVassals;
      public float LoanInterest;
      public float AggressiveExpansion;
      public float RevolutionaryRisk;
      public float CoreCost;
      public int BattleWidth;
      public int RegimentsLimit;
   }
}
