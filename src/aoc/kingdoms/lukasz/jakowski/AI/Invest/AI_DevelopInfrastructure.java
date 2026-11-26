package aoc.kingdoms.lukasz.jakowski.AI.Invest;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_DevelopInfrastructure {
   public static final void developInfrastructure(int iCivID, float minGold) {
      int rand = Game.oR.nextInt(100);
      if (rand < Game.aiValuesBuild.DEVELOP_INFRASTRUCTURE_SCORE_RANDOM) {
         for (int i = Math.min(Game.aiValues.INVEST_LIMIT_PER_TURN_RANDOM_LIMIT, 1 + Game.getCiv(iCivID).getNumOfProvinces() / 2); i >= 0; i--) {
            int randomProvince = Game.aiManager.getRandomProvince(iCivID);
            if (!Game.getProvince(randomProvince).addDevelopInfrastructure()) {
               return;
            }

            if (minGold >= Game.getCiv(iCivID).fGold) {
               return;
            }
         }
      } else {
         if (rand < Game.aiValuesBuild.DEVELOP_INFRASTRUCTURE_SCORE_GROWTH_RATE) {
            buildScore_InvestInEconomy_GrowthRate(iCivID);
         } else if (rand < Game.aiValuesBuild.DEVELOP_INFRASTRUCTURE_SCORE_DISTANCE) {
            buildScore_InvestInEconomy_Distance(iCivID);
         } else {
            buildScore_Economy(iCivID);
         }

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

            for (int a = 1 + Game.oR.nextInt(Game.aiValuesBuild.DEVELOP_INFRASTRUCTURE_RANDOM); a >= 0; a--) {
               if (!Game.getProvince(Game.getCiv(iCivID).getProvinceID(investInProvinceID)).addDevelopInfrastructure()) {
                  if (Game.getCiv(iCivID).fGold < Game.getDevelopInfrastructureCost(Game.getCiv(iCivID).getProvinceID(investInProvinceID))) {
                     tInvested.clear();
                     return;
                  }

                  if (Game.getCiv(iCivID).fLegacy < Game.getDevelopInfrastructureCostLegacy(Game.getCiv(iCivID).getProvinceID(investInProvinceID))) {
                     tInvested.clear();
                     return;
                  }
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
      }
   }

   public static final void buildScore_InvestInEconomy_GrowthRate(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_GrowthRate_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_GrowthRate_ProvinceID(int provinceID) {
      if (Game.getProvince(provinceID).getInfrastructure() >= Game.getProvince(provinceID).iInfrastructureMax) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         Game.getProvince(provinceID).aiInvestScore = Game.aiValues.SCORE_BASE
            + Game.aiValues.SCORE_INVEST_ECONOMY_GROWTH_RATE
               * (Game.getProvince(provinceID).getGrowthRateWithBonuses() / Game.getProvince(provinceID).getEconomyWithBonuses());
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * AI_InvestInEconomy.buildScore_CoreReligion(provinceID);
      }
   }

   public static final void buildScore_InvestInEconomy_Distance(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_InvestInEconomy_Distance_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_InvestInEconomy_Distance_ProvinceID(int provinceID) {
      if (Game.getProvince(provinceID).getInfrastructure() >= Game.getProvince(provinceID).iInfrastructureMax) {
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
         var10000.aiInvestScore = var10000.aiInvestScore * AI_InvestInEconomy.buildScore_CoreReligion(provinceID);
      }
   }

   public static final void buildScore_Economy(int iCivID) {
      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         buildScore_Economy_ProvinceID(Game.getCiv(iCivID).getProvinceID(i));
      }
   }

   public static final void buildScore_Economy_ProvinceID(int provinceID) {
      if (Game.getProvince(provinceID).getInfrastructure() >= Game.getProvince(provinceID).iInfrastructureMax) {
         Game.getProvince(provinceID).aiInvestScore = 0.0F;
      } else {
         Game.getProvince(provinceID).aiInvestScore = Game.aiValues.SCORE_BASE
            + Game.aiValues.SCORE_DEVELOP_INFRASTRUCTURE_ECONOMY
               * Game.getProvince(provinceID).getEconomyWithBonuses()
               * (0.25F + 0.75F * (1.0F - (float)Game.getProvince(provinceID).getInfrastructure() / Game.getProvince(provinceID).iInfrastructureMax));
         Province var10000 = Game.getProvince(provinceID);
         var10000.aiInvestScore = var10000.aiInvestScore * AI_InvestInEconomy.buildScore_CoreReligion(provinceID);
      }
   }
}
