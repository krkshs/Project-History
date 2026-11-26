package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class CoalitionManager {
   public static final void updateCreateCoalition() {
      for (int i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_COALITION;
         i < Game.getCivsSize();
         i += GameValues.gameUpdate.GAME_UPDATE_COALITION
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               updateCreateCoalition(i);
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
            }
         }
      }
   }

   public static final void updateCreateCoalition(int civID) {
      if (Game.getCiv(civID).getAggressiveExpansion() > GameValues.aggressiveExpansion.START_COALITION_IF_AE_OVER) {
         if (civID == Game.player.iCivID) {
            if (Game.difficultyID >= GameValues.difficulty.NORMAL_ID) {
               createCoalition(civID);
            }
         } else {
            createCoalition(civID);
         }
      }
   }

   public static void createCoalition(int againstCivID) {
      List<Integer> possibleCivs = new ArrayList<>();
      List<Point_XY> militaryAccess = new ArrayList<>();
      Civilization civ = Game.getCiv(againstCivID);
      int numOfRegiments = 0;

      for (int i = 0; i < civ.civNeighbors.civsSize; i++) {
         int neighbor = civ.civNeighbors.civs.get(i).civID;
         if (!DiplomacyManager.isAlly(againstCivID, neighbor)
            && !Game.getCiv(againstCivID).diplomacy.haveDefensivePact(neighbor)
            && !DiplomacyManager.isAtWar(againstCivID, neighbor)
            && !possibleCivs.contains(neighbor)) {
            possibleCivs.add(neighbor);
            numOfRegiments += Game.getCiv(neighbor).iRegiments;
         }
      }

      if (!possibleCivs.isEmpty()) {
         List<Integer> alliesDefenders = DiplomacyManager.declareWar_AlliesDefender(againstCivID, possibleCivs.get(0));
         int regimentsB = Game.getCiv(againstCivID).iRegiments;

         try {
            for (int ix = alliesDefenders.size() - 1; ix >= 0; ix--) {
               regimentsB += Game.getCiv(alliesDefenders.get(ix)).getArmyRegimentSize();
            }
         } catch (Exception var10) {
            CFG.exceptionStack(var10);
         }

         if (numOfRegiments < regimentsB * GameValues.aggressiveExpansion.COALITION_ARMY_OVER_PERC) {
            for (int ix = 0; ix < civ.civNeighbors.civsSize; ix++) {
               int neighbor = civ.civNeighbors.civs.get(ix).civID;

               for (int j = 0; j < Game.getCiv(neighbor).civNeighbors.civsSize; j++) {
                  if (!DiplomacyManager.isAlly(againstCivID, Game.getCiv(neighbor).civNeighbors.civs.get(j).civID)
                     && !Game.getCiv(againstCivID).diplomacy.haveDefensivePact(Game.getCiv(neighbor).civNeighbors.civs.get(j).civID)
                     && !DiplomacyManager.isAtWar(againstCivID, Game.getCiv(neighbor).civNeighbors.civs.get(j).civID)
                     && againstCivID != Game.getCiv(neighbor).civNeighbors.civs.get(j).civID
                     && !possibleCivs.contains(Game.getCiv(neighbor).civNeighbors.civs.get(j))) {
                     possibleCivs.add(Game.getCiv(neighbor).civNeighbors.civs.get(j).civID);
                     numOfRegiments += Game.getCiv(Game.getCiv(neighbor).civNeighbors.civs.get(j).civID).iRegiments;
                     militaryAccess.add(new Point_XY(Game.getCiv(neighbor).civNeighbors.civs.get(j).civID, neighbor));
                     if (numOfRegiments > regimentsB * GameValues.aggressiveExpansion.COALITION_ARMY_OVER_PERC) {
                        ix = civ.civNeighbors.civsSize;
                        break;
                     }
                  }
               }
            }
         }

         if (numOfRegiments > regimentsB) {
            int bestID = 0;
            if (possibleCivs.get(bestID) == Game.player.iCivID) {
               if (possibleCivs.size() == 1) {
                  return;
               }

               bestID = 1;
            }

            for (int ix = 1; ix < possibleCivs.size(); ix++) {
               if (Game.getCiv(possibleCivs.get(bestID)).iRegiments < Game.getCiv(possibleCivs.get(ix)).iRegiments
                  && possibleCivs.get(ix) != Game.player.iCivID) {
                  bestID = ix;
               }
            }

            if (DiplomacyManager.declareWar(possibleCivs.get(bestID), againstCivID, false, possibleCivs, true)) {
               Game.getCiv(againstCivID)
                  .setAggressiveExpansion(
                     Game.getCiv(againstCivID).getAggressiveExpansion() * GameValues.aggressiveExpansion.COALITION_STARTED_REDUCE_AGGRESSIVE_EXPANSION
                  );

               for (int ixx = militaryAccess.size() - 1; ixx >= 0; ixx--) {
                  DiplomacyManager.addMilitaryAccess(militaryAccess.get(ixx).getPosX(), militaryAccess.get(ixx).getPosY());
               }
            }
         }
      }
   }
}
