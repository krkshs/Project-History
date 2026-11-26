package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_Pacts {
   public static final void update() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_DIPLOMACY_PACTS;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DIPLOMACY_PACTS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               update(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DIPLOMACY_PACTS;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DIPLOMACY_PACTS) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               update(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void update(int civID) {
      if (Game.getCiv(civID).getPuppetOfCivID() == civID) {
         if (!Game.getCiv(civID).diplomacy.isAtWar()) {
            if (Game.getCiv(civID).diplomacy.defensivePact.size() + Game.getCiv(civID).diplomacy.nonAggressionPact.size()
               < Game.aiValuesDiplomacy.AI_SIGN_PACT_DEFENSIVE_NON_AGGRESSION_LIMIT) {
               if (!(Game.getCiv(civID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST)) {
                  if (Game.getCiv(civID).civNeighbors.civsSize > 0) {
                     Civilization civ = Game.getCiv(civID);
                     List<Integer> possibleCivs = new ArrayList<>();

                     for (int i = 0; i < civ.civNeighbors.civsSize; i++) {
                        if (canSignPact(civID, civ.civNeighbors.civs.get(i).civID)) {
                           possibleCivs.add(civ.civNeighbors.civs.get(i).civID);
                        }
                     }

                     if (!possibleCivs.isEmpty()) {
                        int bestID = 0;

                        for (int ix = possibleCivs.size() - 1; ix > 0; ix--) {
                           if (civ.diplomacy.getRelation(possibleCivs.get(bestID)) < civ.diplomacy.getRelation(possibleCivs.get(ix))) {
                              bestID = ix;
                           }
                        }

                        if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_SIGN_PACT_DEFENSIVE_CHANCE) {
                           if (DiplomacyManager.getDefensivePact_Score(civID, possibleCivs.get(bestID)) >= 0) {
                              DiplomacyManager.offerDefensivePact(civID, possibleCivs.get(bestID));
                           }
                        } else if (DiplomacyManager.getNonAggression_Score(civID, possibleCivs.get(bestID)) >= 0) {
                           DiplomacyManager.offerNonAggressionPact(civID, possibleCivs.get(bestID));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean canSignPact(int civID, int withCivID) {
      if (Game.getCiv(civID).diplomacy.getRelation(withCivID) < Game.aiValuesDiplomacy.AI_SIGN_PACT_MIN_RELATIONS) {
         return false;
      } else if (DiplomacyManager.isAlly_AllianceCheck(civID, withCivID)) {
         return false;
      } else if (Game.getCiv(civID).diplomacy.isRival(withCivID) || Game.getCiv(withCivID).diplomacy.isRival(civID)) {
         return false;
      } else if (Game.getCiv(civID).diplomacy.isDamagingRelations(withCivID)) {
         return false;
      } else if (Game.getCiv(civID).diplomacy.haveDefensivePact(withCivID) || Game.getCiv(civID).diplomacy.haveNonAggressionPact(withCivID)) {
         return false;
      } else {
         return Game.getCiv(civID).aiCivDiplomacy.isPreparingForWarWithCivID(withCivID)
               || Game.getCiv(withCivID).aiCivDiplomacy.isPreparingForWarWithCivID(civID)
            ? false
            : !Game.getCiv(civID).aiCivDiplomacy.isPreparingForAllianceWithCivID(withCivID)
               && !Game.getCiv(withCivID).aiCivDiplomacy.isPreparingForAllianceWithCivID(civID);
      }
   }
}
