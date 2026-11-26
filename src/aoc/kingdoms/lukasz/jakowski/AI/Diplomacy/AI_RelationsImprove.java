package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_RelationsImprove {
   public static void updateRelations_Improve() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateRelations_Improve(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateRelations_Improve(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void updateRelations_Improve(int civID) {
      Civilization civ = Game.getCiv(civID);
      if (civ.diplomacy.iImprovingRelationsSize > 0) {
         if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_CLEAR_IMPROVE_RELATIONS_CHANCE) {
            civ.diplomacy.clearImproveRelations_AI(civID);
         }

         if (civ.diplomacy.iImprovingRelationsSize >= Game.aiValuesDiplomacy.AI_LIMIT_IMPROVING_RELATIONS) {
            return;
         }
      }

      for (int i = 0; i < civ.diplomacy.iVassalsSize; i++) {
         if (civ.diplomacy.getRelation(civ.diplomacy.lVassals.get(i).c) < Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_WITH_VASSAL_IF_RELATIONS_BELOW) {
            addImproveRelations(civID, civ.diplomacy.lVassals.get(i).c);
         }
      }

      List<Integer> civs = new ArrayList<>();
      if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_NEIGHBORS_OF_NEIGHBORS) {
         for (int ix = 0; ix < civ.civNeighbors.civsSize; ix++) {
            Civilization civJ = Game.getCiv(civ.civNeighbors.civs.get(ix).civID);

            for (int j = 0; j < civJ.civNeighbors.civsSize; j++) {
               if (Game.getCiv(civJ.civNeighbors.civs.get(j).civID).getNumOfProvinces() > 0
                  && !civs.contains(civJ.civNeighbors.civs.get(j))
                  && civJ.civNeighbors.civs.get(j).civID != civID
                  && canBeAdded(civID, civJ.civNeighbors.civs.get(j).civID)) {
                  civs.add(civJ.civNeighbors.civs.get(j).civID);
               }
            }
         }

         if (!civs.isEmpty()) {
            for (int a = Math.min(civs.size(), Game.aiValuesDiplomacy.AI_LIMIT_IMPROVING_RELATIONS - civ.diplomacy.iImprovingRelationsSize) - 1; a >= 0; a--) {
               int rand = Game.oR.nextInt(civs.size());
               addImproveRelations(civID, civs.get(rand));
               civs.remove(rand);
            }

            return;
         }
      }

      int type = Game.oR.nextInt(3);
      float randDistanceScore = Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_DISTANCE_SCORE
         + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_DISTANCE_SCORE_RANDOM) / 100.0F;
      float randSecondScore = Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_SECOND_SCORE
         + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_SECOND_SCORE_RANDOM) / 100.0F;
      switch (type) {
         case 0:
            for (int ix = 1; ix < civID; ix++) {
               buildScore(civID, ix, randDistanceScore, randSecondScore);
            }

            for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
               buildScore(civID, ix, randDistanceScore, randSecondScore);
            }
            break;
         case 1:
            for (int ix = 1; ix < civID; ix++) {
               buildScoreManpower(civID, ix, randDistanceScore, randSecondScore);
            }

            for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
               buildScoreManpower(civID, ix, randDistanceScore, randSecondScore);
            }
            break;
         case 2:
            for (int ix = 1; ix < civID; ix++) {
               buildScoreNumOfProvinces(civID, ix, randDistanceScore, randSecondScore);
            }

            for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
               buildScoreNumOfProvinces(civID, ix, randDistanceScore, randSecondScore);
            }
      }

      civ.aiScore = 9.999877E7F;

      for (int a = (Game.aiValuesDiplomacy.AI_LIMIT_IMPROVING_RELATIONS - civ.diplomacy.iImprovingRelationsSize) * 2 - 1; a >= 0; a--) {
         int bestID = 1;

         for (int ix = 2; ix < Game.getCivsSize(); ix++) {
            if (Game.getCiv(bestID).aiScore > Game.getCiv(ix).aiScore
               && !civ.diplomacy.isRival(ix)
               && !civ.diplomacy.isDamagingRelations(ix)
               && !civ.diplomacy.isImprovingRelations(ix)
               && civ.diplomacy.getRelation(ix) < GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_MAX) {
               bestID = ix;
            }
         }

         if (bestID == civID) {
            break;
         }

         if (!civ.diplomacy.isRival(bestID) && !civ.diplomacy.isDamagingRelations(bestID) && !civ.diplomacy.isImprovingRelations(bestID)) {
            if (Game.getCiv(bestID).getNumOfProvinces() <= 0) {
               break;
            }

            civs.add(bestID);
            Game.getCiv(bestID).aiScore = 9.999877E7F;
         } else {
            if (Game.getCiv(bestID).aiScore == 9.999832E7F) {
               break;
            }

            Game.getCiv(bestID).aiScore = 9.999832E7F;
         }
      }

      if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_BEST_CIVS_RANDOMLY) {
         for (int a = Math.min(civs.size(), Game.aiValuesDiplomacy.AI_LIMIT_IMPROVING_RELATIONS - civ.diplomacy.iImprovingRelationsSize) - 1; a >= 0; a--) {
            type = Game.oR.nextInt(civs.size());
            addImproveRelations(civID, civs.get(type));
            civs.remove(type);
         }
      } else {
         for (int a = 0; a < Math.min(civs.size(), Game.aiValuesDiplomacy.AI_LIMIT_IMPROVING_RELATIONS - civ.diplomacy.iImprovingRelationsSize); a++) {
            addImproveRelations(civID, civs.get(a));
         }
      }

      civs.clear();
   }

   public static final void addImproveRelations(int civID, int withCivID) {
      Game.getCiv(civID).diplomacy.addImproveRelations(civID, withCivID);
      if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_SEND_GIFT_CHANCE) {
         DiplomacyManager.giftGold(
            civID,
            withCivID,
            Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_SEND_GIFT_CLICKS + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_SEND_GIFT_CLICKS_RANDOM)
         );
      }
   }

   public static void buildScore(int civID, int civB, float randDistanceScore, float randSecondScore) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         Game.getCiv(civB).aiScore = randDistanceScore
               * Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID())
            + (randSecondScore + (differentReligion(civID, civB) ? Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_DISTANCE_SCORE_EXTRA_DIFFERENT_RELIGION : 0.0F))
               * (
                  1.0F
                     - Math.min(Game.getCiv(civID).fTotalIncomePerMonth, Game.getCiv(civB).fTotalIncomePerMonth)
                        / Math.max(Game.getCiv(civID).fTotalIncomePerMonth, Game.getCiv(civB).fTotalIncomePerMonth)
               );
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static void buildScoreNumOfProvinces(int civID, int civB, float randDistanceScore, float randSecondScore) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         Game.getCiv(civB).aiScore = randDistanceScore
               * Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID())
            + (randSecondScore + (differentReligion(civID, civB) ? Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_DISTANCE_SCORE_EXTRA_DIFFERENT_RELIGION : 0.0F))
               * (
                  1.0F
                     - (float)Math.min(Game.getCiv(civID).getNumOfProvinces(), Game.getCiv(civB).getNumOfProvinces())
                        / Math.max(Game.getCiv(civID).getNumOfProvinces(), Game.getCiv(civB).getNumOfProvinces())
               );
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static void buildScoreManpower(int civID, int civB, float randDistanceScore, float randSecondScore) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         Game.getCiv(civB).aiScore = (float)(
            randDistanceScore * Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID())
               + (
                     randSecondScore
                        + (differentReligion(civID, civB) ? Game.aiValuesDiplomacy.AI_IMPROVE_RELATIONS_DISTANCE_SCORE_EXTRA_DIFFERENT_RELIGION : 0.0F)
                  )
                  * (
                     1.0
                        - Math.min(Game.getCiv(civID).fManpowerMax, Game.getCiv(civB).fManpowerMax)
                           / (float)Math.max(Game.getCiv(civID).fManpowerMax, Game.getCiv(civB).fManpowerMax)
                  )
         );
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static boolean canBeAdded(int civID, int civB) {
      return !Game.getCiv(civID).diplomacy.isRival(civB)
         && !Game.getCiv(civID).diplomacy.isDamagingRelations(civB)
         && !Game.getCiv(civID).diplomacy.isImprovingRelations(civB)
         && !DiplomacyManager.isAtWar(civID, civB)
         && !(Game.getCiv(civID).diplomacy.getRelation(civB) >= GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_MAX);
   }

   public static boolean differentGovernment(int civA, int civB) {
      return Game.getCiv(civA).getIdeologyID() != Game.getCiv(civB).getIdeologyID();
   }

   public static boolean differentReligion(int civA, int civB) {
      return Game.getCiv(civA).getReligionID() != Game.getCiv(civB).getReligionID();
   }
}
