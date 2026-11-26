package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_RelationsDamage {
   public static void updateRelations_Damage() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateRelations_Damage(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateRelations_Damage(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void updateRelations_Damage(int civID) {
      if (Game.getCiv(civID).diplomacy.iImprovingRelationsSize > 0 && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_CLEAR_DECREASE_RELATIONS_CHANCE) {
         Game.getCiv(civID).diplomacy.clearDamageRelations_AI(civID);
      }

      if (Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_PRIORITIZE_TRIBAL
         && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_PRIORITIZE_TRIBAL_CHANCE
         && !Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).TRIBAL) {
         boolean added = false;

         for (int i = 0; i < Game.getCiv(civID).civNeighbors.civsSize; i++) {
            if (Game.ideologiesManager.getIdeology(Game.getCiv(Game.getCiv(civID).civNeighbors.civs.get(i).civID).getIdeologyID()).TRIBAL
               && Game.getCiv(Game.getCiv(civID).civNeighbors.civs.get(i).civID).getNumOfProvinces() > 0
               && !DiplomacyManager.isAlly(civID, Game.getCiv(civID).civNeighbors.civs.get(i).civID)
               && Game.getCiv(civID).getResearchedTechnologies() > Game.getCiv(Game.getCiv(civID).civNeighbors.civs.get(i).civID).getResearchedTechnologies()
               && Game.getCiv(civID).getNumOfProvinces() > Game.getCiv(Game.getCiv(civID).civNeighbors.civs.get(i).civID).getNumOfProvinces()
               && Game.getCiv(civID).diplomacy.addDamageRelations(civID, Game.getCiv(civID).civNeighbors.civs.get(i).civID)) {
               added = true;
            }
         }

         if (added) {
            return;
         }
      }

      if (Game.getCiv(civID).diplomacy.iDamagingRelationsSize < Game.aiValuesDiplomacy.AI_LIMIT_DECREASING_RELATIONS) {
         int type = Game.oR.nextInt(3);
         switch (type) {
            case 0:
               float value = Game.getCiv(civID).fTotalIncomePerMonth * Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_INCOME_MODIFIER;

               for (int ix = 1; ix < civID; ix++) {
                  buildScore(civID, ix, value);
               }

               for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
                  buildScore(civID, ix, value);
               }
               break;
            case 1:
               double value = Game.getCiv(civID).fManpowerMax * Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_MANPOWER_MODIFIER;

               for (int ix = 1; ix < civID; ix++) {
                  buildScoreManpower(civID, ix, value);
               }

               for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
                  buildScoreManpower(civID, ix, value);
               }
               break;
            case 2:
               float value = Game.getCiv(civID).iRegiments * Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_REGIMENTS_MODIFIER;

               for (int ix = 1; ix < civID; ix++) {
                  buildScoreRegiments(civID, ix, value);
               }

               for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
                  buildScoreRegiments(civID, ix, value);
               }
         }

         List<Integer> civs = new ArrayList<>();
         Game.getCiv(civID).aiScore = 9.999877E7F;

         for (int ix = 0; ix < Game.getCiv(civID).diplomacy.iVassalsSize; ix++) {
            Game.getCiv(Game.getCiv(civID).diplomacy.lVassals.get(ix).c).aiScore = 9.999997E7F;
         }

         for (int a = Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_NUM_TO_CHOOSE_FROM - 1; a >= 0; a--) {
            int bestID = 1;

            for (int ix = 2; ix < Game.getCivsSize(); ix++) {
               if (Game.getCiv(bestID).aiScore > Game.getCiv(ix).aiScore
                  && !Game.getCiv(civID).diplomacy.isDamagingRelations(ix)
                  && !Game.getCiv(civID).diplomacy.isImprovingRelations(ix)
                  && Game.getCiv(civID).diplomacy.getRelation(ix) > GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX) {
                  bestID = ix;
               }
            }

            if (bestID == civID) {
               break;
            }

            if (!Game.getCiv(civID).diplomacy.isDamagingRelations(bestID)
               && !Game.getCiv(civID).diplomacy.isImprovingRelations(bestID)
               && !(Game.getCiv(civID).diplomacy.getRelation(bestID) < GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX)) {
               if (Game.getCiv(bestID).getNumOfProvinces() <= 0 || !(Game.getCiv(bestID).aiScore < 999800.0F)) {
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

         for (int a = Math.min(civs.size(), Game.aiValuesDiplomacy.AI_LIMIT_DECREASING_RELATIONS - Game.getCiv(civID).diplomacy.iDamagingRelationsSize) - 1;
            a >= 0;
            a--
         ) {
            int rand = Game.oR.nextInt(civs.size());
            Game.getCiv(civID).diplomacy.addDamageRelations(civID, civs.get(rand));
            civs.remove(rand);
         }

         civs.clear();
      }
   }

   public static void buildScore(int civID, int civB, float value) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         if (Game.getCiv(civB).fTotalIncomePerMonth < value) {
            Game.getCiv(civB).aiScore = Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID());
         } else {
            Game.getCiv(civB).aiScore = 999876.0F;
         }
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static void buildScoreManpower(int civID, int civB, double value) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         if (Game.getCiv(civB).fManpowerMax < value) {
            Game.getCiv(civB).aiScore = Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID());
         } else {
            Game.getCiv(civB).aiScore = 999876.0F;
         }
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static void buildScoreRegiments(int civID, int civB, float value) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         if (Game.getCiv(civB).iRegiments < value) {
            Game.getCiv(civB).aiScore = Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID());
         } else {
            Game.getCiv(civB).aiScore = 999876.0F;
         }
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static boolean differentGovernment(int civA, int civB) {
      return Game.getCiv(civA).getIdeologyID() != Game.getCiv(civB).getIdeologyID();
   }

   public static boolean differentReligion(int civA, int civB) {
      return Game.getCiv(civA).getReligionID() != Game.getCiv(civB).getReligionID();
   }
}
