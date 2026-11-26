package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.province.Province;

public class AI_Cores {
   public static final void createCore(int civID) {
      if (Game.getCiv(civID).civilizationCores.provincesSize > 0) {
         try {
            for (int i = 0; i < Game.getCiv(civID).civilizationCores.provincesSize; i++) {
               buildProvince_AICoresScore_Default(civID, Game.getCiv(civID).civilizationCores.provinces.get(i));
            }

            while (Game.getCiv(civID).fGold > GameValues.core.CORE_CREATION_COST_DEFAULT && Game.getCiv(civID).civilizationCores.provincesSize > 0) {
               int bestProvinceID = 0;

               for (int i = 1; i < Game.getCiv(civID).civilizationCores.provincesSize; i++) {
                  if (Game.getProvince(Game.getCiv(civID).civilizationCores.provinces.get(bestProvinceID)).aiScore_CoresReligion
                     < Game.getProvince(Game.getCiv(civID).civilizationCores.provinces.get(i)).aiScore_CoresReligion) {
                     bestProvinceID = i;
                  }
               }

               if (Game.getProvince(Game.getCiv(civID).civilizationCores.provinces.get(bestProvinceID)).aiScore_CoresReligion < 0.0F) {
                  break;
               }

               if (!Game.getProvince(Game.getCiv(civID).civilizationCores.provinces.get(bestProvinceID)).addCoreCreation()) {
                  Game.getCiv(civID).civilizationCores.checkProvince(Game.getCiv(civID).civilizationCores.provinces.get(bestProvinceID), civID);
                  return;
               }
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }
      }
   }

   public static final void buildProvince_AICoresScore_Default(int civID, int nProvinceID) {
      if (Game.getProvince(nProvinceID).coreCreation != null) {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = -999999.0F;
      } else if (Game.getProvince(nProvinceID).haveACore(civID)) {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = -999999.0F;
         Game.getCiv(civID).civilizationCores.removeProvince(nProvinceID);
      } else {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = Game.aiValuesCores.BUILD_SCORE_MIN;
         Province var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesCores.SCORE_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
         var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesCores.SCORE_PER_ECONOMY * Game.getProvince(nProvinceID).getEconomyWithBonuses();
         var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesCores.SCORE_DISTANCE_FROM_CAPITAL * Game.getProvince(nProvinceID).aiDistanceToCapital;
      }
   }
}
