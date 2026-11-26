package aoc.kingdoms.lukasz.jakowski.AI.Invest;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Loan;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_InvestInEconomy {
   public static final float SCORE_CANT_INVEST = 0.0F;

   public static final void investInEconomy(int iCivID, float minGold) {
      if (!(Game.getCiv(iCivID).fLegacy < GameValues.economy.INVEST_COST_LEGACY * 4.0F)) {
         int rand = Game.oR.nextInt(100);
         if (rand >= Game.aiValuesBuild.INVEST_IN_ECONOMY_SCORE_RANDOM
            && Game.getCiv(iCivID).getNumOfProvinces() >= Game.aiValues.INVEST_RANDOM_IF_PROVINCES_BELOW) {
            if (rand < Game.aiValuesBuild.INVEST_IN_ECONOMY_SCORE_GROWTH_RATE) {
               buildScore_InvestInEconomy_GrowthRate(iCivID);
            } else if (rand < Game.aiValuesBuild.INVEST_IN_ECONOMY_SCORE_DISTANCE) {
               buildScore_InvestInEconomy_Distance(iCivID);
            } else if (rand < Game.aiValuesBuild.INVEST_IN_ECONOMY_SCORE_PAYOFF) {
               buildScore_InvestInEconomy_PayOff(iCivID);
            } else {
               buildScore_InvestInEconomy(iCivID);
            }

            AI_Loan.takeLoan_Chance(iCivID, Game.aiValues.TAKE_LOAN_CHANCE_INVEST_IN_ECONOMY);
            List<Integer> tInvested = new ArrayList<>();

            for (int i = Game.aiValues.INVEST_LIMIT_PER_TURN; i > 0; i--) {
               int investInProvinceID = -1;

               int checkInvestID;
               for (checkInvestID = 0; checkInvestID < Game.getCiv(iCivID).getNumOfProvinces(); checkInvestID++) {
                  if (!tInvested.contains(Game.getCiv(iCivID).getProvinceID(checkInvestID))
                     && Game.getProvince(Game.getCiv(iCivID).getProvinceID(checkInvestID)).aiInvestScore > 0.0F) {
                     investInProvinceID = checkInvestID;
                     break;
                  }
               }

               if (investInProvinceID < 0) {
                  tInvested.clear();
                  if (Game.oR.nextInt(100) < Game.aiValuesBuild.INVEST_IN_ECONOMY_MAX_VALUE_INCREASE_GROWTH_RATE_CHANCE) {
                     AI_GrowthRate.increaseGrowthRate(iCivID, minGold);
                  } else {
                     AI_IncreaseManpower.increaseManpower(iCivID, minGold);
                  }

                  return;
               }

               for (; checkInvestID < Game.getCiv(iCivID).getNumOfProvinces(); checkInvestID++) {
                  if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(checkInvestID)).aiInvestScore > 0.0F
                     && Game.getProvince(Game.getCiv(iCivID).getProvinceID(investInProvinceID)).aiInvestScore
                        < Game.getProvince(Game.getCiv(iCivID).getProvinceID(checkInvestID)).aiInvestScore
                     && !tInvested.contains(Game.getCiv(iCivID).getProvinceID(checkInvestID))) {
                     investInProvinceID = checkInvestID;
                  }
               }

               for (int a = 1 + Game.oR.nextInt(Game.aiValuesBuild.INVEST_IN_ECONOMY_RANDOM); a >= 0; a--) {
                  if (!Game.getProvince(Game.getCiv(iCivID).getProvinceID(investInProvinceID)).addInvestInProvince()
                     && (
                        Game.getCiv(iCivID).fGold < Game.getInvestCost(Game.getCiv(iCivID).getProvinceID(investInProvinceID))
                           || Game.getCiv(iCivID).fLegacy < Game.getInvestCost_Legacy(Game.getCiv(iCivID).getProvinceID(investInProvinceID))
                     )) {
                     tInvested.clear();
                     return;
                  }
               }

               if (minGold >= Game.getCiv(iCivID).fGold) {
                  tInvested.clear();
                  return;
               }

               tInvested.add(Game.getCiv(iCivID).getProvinceID(investInProvinceID));
               if (tInvested.size() == Game.getCiv(iCivID).getNumOfProvinces()) {
                  tInvested.clear();
                  return;
               }
            }
         } else {
            for (int i = Math.min(Game.aiValues.INVEST_LIMIT_PER_TURN_RANDOM_LIMIT, 1 + Game.getCiv(iCivID).getNumOfProvinces() / 2); i >= 0; i--) {
               int randomProvince = Game.aiManager.getRandomProvince(iCivID);
               if (!Game.getProvince(randomProvince).addInvestInProvince()) {
                  return;
               }

               if (minGold >= Game.getCiv(iCivID).fGold) {
                  return;
               }
            }
         }
      }
   }

   public static float buildScore_CoreReligion(int provinceID) {
      return 1.0F
         + (Game.getProvince(provinceID).haveACore ? Game.aiValues.SCORE_INVEST_NON_CORE : 0.0F)
         + (
            Game.getProvince(provinceID).getReligion() == Game.getCiv(Game.getProvince(provinceID).getReligion()).getReligionID()
               ? 0.0F
               : Game.aiValues.SCORE_INVEST_DIFFERENT_RELIGION
         );
   }

   public static final void buildScore_InvestInEconomy(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_ProvinceID(int provinceID) {
      if (Game.canInvestInEconomy(provinceID)) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         Game.getProvince(provinceID).aiInvestScore = Game.aiValues.SCORE_BASE
            + Game.getProvince(provinceID).getGrowthRate() / 10.0F
            + (
                  (
                        Game.getProvince(provinceID).getResourceID() >= 0
                              && Game.getCiv(Game.getProvince(provinceID).getCivID())
                                 .getTechResearched(ResourcesManager.lResources.get(Game.getProvince(provinceID).getResourceID()).RequiredTechID)
                           ? Game.aiValues.SCORE_INVEST_ECONOMY_RESOURCE_MIN
                              + Game.aiValues.SCORE_INVEST_ECONOMY_RESOURCE_PRICE * ResourcesManager.getPrice(Game.getProvince(provinceID).getResourceID())
                           : 0.0F
                     )
                     + Game.aiValues.SCORE_INVEST_ECONOMY_PER_INFRASTRUCTURE * Game.getProvince(provinceID).getInfrastructure()
               )
               / Game.getProvince(provinceID).getEconomyWithBonuses();
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * buildScore_CoreReligion(provinceID);
      }
   }

   public static final void buildScore_InvestInEconomy_Distance(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_Distance_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_Distance_ProvinceID(int provinceID) {
      if (Game.canInvestInEconomy(provinceID)) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         Game.getProvince(provinceID).aiInvestScore = Game.aiValues.SCORE_BASE
            + Game.aiValues.SCORE_INVEST_ECONOMY_DISTANCE
               * (
                  Game.aiValues.SCORE_INVEST_ECONOMY_DISTANCE_W_GR
                     * (Game.getProvince(provinceID).getGrowthRateWithBonuses() / Game.getProvince(provinceID).getEconomyWithBonuses())
                     * (Game.aiValues.SCORE_INVEST_ECONOMY_DISTANCE_W_DIS * (1.0F - Game.getProvince(provinceID).aiDistanceToCapital))
               );
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * buildScore_CoreReligion(provinceID);
      }
   }

   public static final void buildScore_InvestInEconomy_GrowthRate(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_GrowthRate_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_GrowthRate_ProvinceID(int provinceID) {
      if (Game.canInvestInEconomy(provinceID)) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         Game.getProvince(provinceID).aiInvestScore = Game.aiValues.SCORE_BASE
            + Game.aiValues.SCORE_INVEST_ECONOMY_GROWTH_RATE
               * (Game.getProvince(provinceID).getGrowthRateWithBonuses() / Game.getProvince(provinceID).getEconomyWithBonuses());
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * buildScore_CoreReligion(provinceID);
      }
   }

   public static final void buildScore_InvestInEconomy_PayOff(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_PayOff_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_PayOff_ProvinceID(int provinceID) {
      if (Game.canInvestInEconomy(provinceID)) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         float tIncomeProduction = (
               ResourcesManager.getMonthlyIncome(
                     provinceID,
                     Game.getProvince(provinceID).getResourceID(),
                     ResourcesManager.getProductionEfficiency(provinceID)
                        + ResourcesManager.getProductionEfficiency_FromEconomy(Game.getInvestEconomyGrowth(provinceID))
                  )
                  - ResourcesManager.getMonthlyIncome(
                     provinceID, Game.getProvince(provinceID).getResourceID(), ResourcesManager.getProductionEfficiency(provinceID)
                  )
            )
            / 100.0F;
         Game.getProvince(provinceID).aiInvestScore = 100000.0F
            - Game.getInvestCost(provinceID) / (Game.getIncomeFromEconomy_Invest(provinceID) + tIncomeProduction);
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * buildScore_CoreReligion(provinceID);
      }
   }
}
