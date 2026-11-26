package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.province.Province;

public class AI_ConvertReligion {
   public static final void convertReligion(int civID) {
      if (Game.getCiv(civID).convertReligion.provincesSize > 0) {
         try {
            for (int i = 0; i < Game.getCiv(civID).convertReligion.provincesSize; i++) {
               buildProvince_AIConvertScore_Default(civID, Game.getCiv(civID).convertReligion.provinces.get(i));
            }

            while (Game.getCiv(civID).fGold > GameValues.religion.DEFAULT_CONVERSION_COST && Game.getCiv(civID).convertReligion.provincesSize > 0) {
               int bestProvinceID = 0;

               for (int i = 1; i < Game.getCiv(civID).convertReligion.provincesSize; i++) {
                  if (Game.getProvince(Game.getCiv(civID).convertReligion.provinces.get(bestProvinceID)).aiScore_CoresReligion
                     < Game.getProvince(Game.getCiv(civID).convertReligion.provinces.get(i)).aiScore_CoresReligion) {
                     bestProvinceID = i;
                  }
               }

               if (Game.getProvince(Game.getCiv(civID).convertReligion.provinces.get(bestProvinceID)).aiScore_CoresReligion < 0.0F) {
                  break;
               }

               if (!Game.getProvince(Game.getCiv(civID).convertReligion.provinces.get(bestProvinceID)).addReligionConversion()) {
                  Game.getCiv(civID).convertReligion.checkProvince(Game.getCiv(civID).convertReligion.provinces.get(bestProvinceID), civID);
                  return;
               }
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }
      }
   }

   public static final void buildProvince_AIConvertScore_Default(int civID, int nProvinceID) {
      if (Game.getProvince(nProvinceID).religionConversion != null) {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = -999999.0F;
      } else if (Game.getProvince(nProvinceID).getReligion() == Game.getCiv(civID).getReligionID()) {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = -999999.0F;
         Game.getCiv(civID).convertReligion.removeProvince(nProvinceID);
      } else {
         Game.getProvince(nProvinceID).aiScore_CoresReligion = Game.aiValuesConvert.BUILD_SCORE_MIN;
         Province var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesConvert.SCORE_PER_GROWTH_RATE * Game.getProvince(nProvinceID).getGrowthRateWithBonuses();
         var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesConvert.SCORE_PER_ECONOMY * Game.getProvince(nProvinceID).getEconomyWithBonuses();
         var10000 = Game.getProvince(nProvinceID);
         var10000.aiScore_CoresReligion = var10000.aiScore_CoresReligion
            + Game.aiValuesConvert.SCORE_DISTANCE_FROM_CAPITAL * Game.getProvince(nProvinceID).aiDistanceToCapital;
      }
   }
}
