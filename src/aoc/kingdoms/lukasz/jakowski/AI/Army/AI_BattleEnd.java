package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;

public class AI_BattleEnd {
   public static void battleEnded(int provinceID) {
      try {
         boolean isThereAnythingToMove = false;

         for (int i = 0; i < Game.getProvince(provinceID).getArmySize(); i++) {
            if (!Game.getProvince(provinceID).getArmy(i).inRetreat
               && !Game.getProvince(provinceID).getArmy(i).inBattle
               && !Game.getProvince(provinceID).getArmy(i).inMovement
               && Game.getProvince(provinceID).getArmy(i).civID > 0
               && (Game.getProvince(provinceID).getArmy(i).civID != Game.player.iCivID || Game.player.allowAIMove)) {
               isThereAnythingToMove = true;
               break;
            }
         }

         if (!isThereAnythingToMove) {
            return;
         }

         Game.getProvince(provinceID).wasBattleEnded = true;

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            Game.getProvince(inProvinceID).wasBattleEnded = false;

            for (int b = 0; b < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvincesSize(); b++) {
               inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvinces(b);
               Game.getProvince(inProvinceID).wasBattleEnded = false;
            }
         }

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            moveArmiesToBattle(provinceID, inProvinceID);

            for (int b = 0; b < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvincesSize(); b++) {
               inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvinces(b);
               moveArmiesToBattle(provinceID, inProvinceID);
            }
         }

         isThereAnythingToMove = false;

         for (int ix = 0; ix < Game.getProvince(provinceID).getArmySize(); ix++) {
            if (!Game.getProvince(provinceID).getArmy(ix).inRetreat
               && !Game.getProvince(provinceID).getArmy(ix).inBattle
               && !Game.getProvince(provinceID).getArmy(ix).inMovement
               && Game.getProvince(provinceID).getArmy(ix).civID > 0
               && (Game.getProvince(provinceID).getArmy(ix).civID != Game.player.iCivID || Game.player.allowAIMove)) {
               isThereAnythingToMove = true;
               break;
            }
         }

         if (!isThereAnythingToMove) {
            return;
         }

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            moveArmiesToEnemyArmy(provinceID, inProvinceID);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static void moveArmiesToBattle(int fromProvinceID, int inProvinceID) {
      try {
         if (!Game.getProvince(inProvinceID).wasBattleEnded) {
            Game.getProvince(inProvinceID).wasBattleEnded = true;
            if (!Game.battleManager.isBattleInProvince(inProvinceID)) {
               return;
            }

            if (Game.getProvince(inProvinceID).getArmySize() > 0) {
               for (int i = 0; i < Game.getProvince(fromProvinceID).getArmySize(); i++) {
                  if (!Game.getProvince(fromProvinceID).getArmy(i).inRetreat
                     && !Game.getProvince(fromProvinceID).getArmy(i).inBattle
                     && !Game.getProvince(fromProvinceID).getArmy(i).inMovement
                     && Game.getProvince(fromProvinceID).getArmy(i).civID > 0
                     && (Game.getProvince(fromProvinceID).getArmy(i).civID != Game.player.iCivID || Game.player.allowAIMove)) {
                     for (int j = 0; j < Game.getProvince(inProvinceID).getArmySize(); j++) {
                        if (DiplomacyManager.isAtWar(Game.getProvince(fromProvinceID).getArmy(i).civID, Game.getProvince(inProvinceID).getArmy(j).civID)) {
                           Game.getCiv(Game.getProvince(fromProvinceID).getArmy(i).civID)
                              .newMove(fromProvinceID, inProvinceID, Game.getProvince(fromProvinceID).getArmy(i).key, 0, false);
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static void moveArmiesToEnemyArmy(int fromProvinceID, int inProvinceID) {
      try {
         if (Game.getProvince(inProvinceID).getArmySize() > 0) {
            for (int i = 0; i < Game.getProvince(fromProvinceID).getArmySize(); i++) {
               if (!Game.getProvince(fromProvinceID).getArmy(i).inRetreat
                  && !Game.getProvince(fromProvinceID).getArmy(i).inBattle
                  && !Game.getProvince(fromProvinceID).getArmy(i).inMovement
                  && Game.getProvince(fromProvinceID).getArmy(i).civID > 0
                  && (Game.getProvince(fromProvinceID).getArmy(i).civID != Game.player.iCivID || Game.player.allowAIMove)) {
                  for (int j = 0; j < Game.getProvince(inProvinceID).getArmySize(); j++) {
                     if (DiplomacyManager.isAtWar(Game.getProvince(fromProvinceID).getArmy(i).civID, Game.getProvince(inProvinceID).getArmy(j).civID)
                        && Game.getProvince(fromProvinceID).getArmy(i).iArmy > Game.getProvince(inProvinceID).getArmy(j).civID) {
                        Game.getCiv(Game.getProvince(fromProvinceID).getArmy(i).civID)
                           .newMove(fromProvinceID, inProvinceID, Game.getProvince(fromProvinceID).getArmy(i).key, 0, false);
                     }
                  }
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
