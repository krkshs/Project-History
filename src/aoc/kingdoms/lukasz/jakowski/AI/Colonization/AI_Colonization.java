package aoc.kingdoms.lukasz.jakowski.AI.Colonization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.ColonizationManager;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_Colonization {
   public static List<Integer> provinces = new ArrayList<>();
   public static List<Integer> provincesAll = new ArrayList<>();

   public static void initData() {
      provinces.clear();
      provincesAll.clear();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() == 0 && Game.getProvince(i).getWasteland() < 0 && !Game.getProvince(i).getSeaProvince()) {
            if (Game.getProvince(i).accessToMainSea) {
               provinces.add(i);
            }

            provincesAll.add(i);
         }
      }
   }

   public static void removeProvinceID(int id) {
      for (int i = provinces.size() - 1; i >= 0; i--) {
         if (provinces.get(i) == id) {
            provinces.remove(i);
            return;
         }
      }
   }

   public static void removeProvinceID_All(int id) {
      for (int i = provincesAll.size() - 1; i >= 0; i--) {
         if (provincesAll.get(i) == id) {
            provincesAll.remove(i);
            return;
         }
      }
   }

   public static void updateColonize() {
      try {
         if (!provincesAll.isEmpty()) {
            int i;
            for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_COLONIZE;
               i < Game.player.iCivID;
               i += GameValues.gameUpdateAI.GAME_UPDATE_AI_COLONIZE
            ) {
               if (Game.getCiv(i).getNumOfProvinces() > 0
                  && Game.getCiv(i).canColonize
                  && Game.getCiv(i).iCivRankPosition < Game.aiValues.COLONIZE_TOP_CIVS
                  && !Game.getCiv(i).diplomacy.isAtWar()
                  && Game.getCiv(i).fGold > Game.aiValues.COLONIZE_MIN_GOLD
                  && Game.getCiv(i).fManpower
                     > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * Game.aiValues.COLONIZE_MIN_MANPOWER_X_REGIMENTS) {
                  colonize(i);
               }
            }

            if (i == Game.player.iCivID) {
               i += GameValues.gameUpdateAI.GAME_UPDATE_AI_COLONIZE;
            }

            for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_COLONIZE) {
               if (Game.getCiv(i).getNumOfProvinces() > 0
                  && Game.getCiv(i).canColonize
                  && Game.getCiv(i).iCivRankPosition < Game.aiValues.COLONIZE_TOP_CIVS
                  && !Game.getCiv(i).diplomacy.isAtWar()
                  && Game.getCiv(i).fGold > Game.aiValues.COLONIZE_MIN_GOLD
                  && Game.getCiv(i).fManpower
                     > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * Game.aiValues.COLONIZE_MIN_MANPOWER_X_REGIMENTS) {
                  colonize(i);
               }
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void colonize(int civID) {
      List<Integer> neighProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvincesSize(); j++) {
            if (Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).getCivID() == 0
               && Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).getWasteland() < 0) {
               float modifier = Game.continents
                        .lContinents
                        .get(Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).getContinent())
                     .prioritizeColonization
                  ? Game.aiValues.COLONIZE_SCORE_PRIORITIZE_CONTINENT_MODIFIER
                  : 1.0F;
               if (!neighProvinces.contains(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j))) {
                  neighProvinces.add(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j));
                  Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).aiInvestScore = (
                        Game.aiValues.COLONIZE_SCORE_PER_NEIGH_PROVINCE
                           + Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).getGrowthRateWithBonuses()
                     )
                     * modifier;
               } else {
                  Province var10000 = Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j));
                  var10000.aiInvestScore = var10000.aiInvestScore + Game.aiValues.COLONIZE_SCORE_PER_NEIGH_PROVINCE * modifier;
               }
            }
         }
      }

      if (neighProvinces.isEmpty()) {
         for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
            int provinceID = Game.getCiv(civID).getProvinceID(i);

            for (int jx = 0; jx < Game.getProvince(provinceID).getNeighboringProvincesSize(); jx++) {
               int neighborProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(jx);
               float modifier = Game.continents.lContinents.get(Game.getProvince(neighborProvinceID).getContinent()).prioritizeColonization
                  ? Game.aiValues.COLONIZE_SCORE_PRIORITIZE_CONTINENT_MODIFIER
                  : 1.0F;
               if (Game.getProvince(neighborProvinceID).getCivID() == 0 && Game.getProvince(neighborProvinceID).getWasteland() < 0) {
                  if (!neighProvinces.contains(neighborProvinceID)) {
                     neighProvinces.add(neighborProvinceID);
                     Game.getProvince(neighborProvinceID).aiInvestScore = (
                           Game.aiValues.COLONIZE_SCORE_PER_NEIGH_PROVINCE + Game.getProvince(neighborProvinceID).getGrowthRateWithBonuses()
                        )
                        * modifier;
                  } else {
                     Province var17 = Game.getProvince(neighborProvinceID);
                     var17.aiInvestScore = var17.aiInvestScore + Game.aiValues.COLONIZE_SCORE_PER_NEIGH_PROVINCE * modifier;
                  }
               }
            }
         }
      }

      if (!neighProvinces.isEmpty()) {
         for (int a = 0; a < Game.aiValues.COLONIZE_PROVINCES_LIMIT && neighProvinces.size() > 0; a++) {
            int bestID = 0;

            for (int i = neighProvinces.size() - 1; i > 0; i--) {
               if (Game.getProvince(neighProvinces.get(bestID)).aiInvestScore < Game.getProvince(neighProvinces.get(i)).aiInvestScore) {
                  bestID = i;
               }
            }

            if (!aiColonize(civID, neighProvinces.get(bestID))) {
               neighProvinces.clear();
               return;
            }

            neighProvinces.remove(bestID);
         }

         neighProvinces.clear();
      } else {
         if (Game.getCiv(civID).haveAccessSea && !provinces.isEmpty()) {
            for (int ix = provinces.size() - 1; ix > 0; ix--) {
               Game.getProvince(provinces.get(ix)).aiInvestScore = Game.getProvince(provinces.get(ix)).aiInvestScore
                  * Math.max(0.05F, 1.0F - Game.getDistanceFromProvinceToProvince(Game.getCiv(civID).getCapitalProvinceID(), provinces.get(ix)));
            }

            for (int a = 0; a < Game.aiValues.COLONIZE_PROVINCES_LIMIT_SEA && provinces.size() > 0; a++) {
               int bestID = 0;

               for (int ix = provinces.size() - 1; ix > 0; ix--) {
                  if (Game.getProvince(provinces.get(bestID)).aiInvestScore < Game.getProvince(provinces.get(ix)).aiInvestScore) {
                     bestID = ix;
                  }
               }

               if (!aiColonize(civID, provinces.get(bestID))) {
                  return;
               }
            }
         }
      }
   }

   public static boolean aiColonize(int civID, int provinceID) {
      if (Game.getCiv(civID).fManpower < Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
         return false;
      } else {
         if (Game.getProvince(provinceID).getCivID() == 0 && Game.getProvince(provinceID).getWasteland() < 0 && !Game.getProvince(provinceID).getSeaProvince()) {
            int pop = (int)Math.max(
               (double)Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE,
               Math.min(
                  Game.getCiv(civID).fManpower,
                  (double)(
                     Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * (1 + Game.oR.nextInt(Game.aiValues.COLONIZE_X_REGIMENTS_RANDOM))
                  )
               )
            );
            if (ColonizationManager.establishSettlement(civID, provinceID, pop)) {
               Game.getCiv(civID).fGold -= pop / 100;
               Game.getCiv(civID).setManpower(Game.getCiv(civID).fManpower - pop);
               removeProvinceID(provinceID);
               removeProvinceID_All(provinceID);
               if (Game.getCiv(civID).fManpower < Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
                  return false;
               }

               return true;
            }
         }

         return false;
      }
   }
}
