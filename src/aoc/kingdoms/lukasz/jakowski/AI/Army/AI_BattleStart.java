package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.war.WarManager;

public class AI_BattleStart {
   public static void battleStarted(int battleID, int provinceID, int numOfProvincesToMove) {
      try {
         if (battleID < 0) {
            return;
         }

         if (Game.battleManager.getBattle(battleID).attackingArmy.iCivID < 0 || Game.battleManager.getBattle(battleID).defendingArmy.iCivID < 0) {
            return;
         }

         String warKey = WarManager.getWarKey(
            Game.battleManager.getBattle(battleID).attackingArmy.iCivID, Game.battleManager.getBattle(battleID).defendingArmy.iCivID
         );
         if (warKey == null) {
            return;
         }

         wasBattleStartRecursively(provinceID, numOfProvincesToMove);
         battleStarted_SeaProvinces_UpdateWas(provinceID);
         Game.getProvince(provinceID).wasBattleStart = true;
         moveArmiesRecursively(warKey, provinceID, provinceID, numOfProvincesToMove);
         battleStarted_SeaProvinces(warKey, provinceID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static void wasBattleStartRecursively(int currentProvinceID, int depth) {
      if (depth > 0) {
         Province currentProvince = Game.getProvince(currentProvinceID);

         for (int i = 0; i < currentProvince.getNeighboringProvincesSize(); i++) {
            int neighborID = currentProvince.getNeighboringProvinces(i);
            Game.getProvince(neighborID).wasBattleStart = false;
            wasBattleStartRecursively(neighborID, depth - 1);
         }
      }
   }

   public static void moveArmiesRecursively(String warKey, int originProvinceID, int currentProvinceID, int depth) {
      if (depth > 0) {
         Province currentProvince = Game.getProvince(currentProvinceID);

         for (int i = 0; i < currentProvince.getNeighboringProvincesSize(); i++) {
            int neighborID = currentProvince.getNeighboringProvinces(i);
            moveArmies(warKey, originProvinceID, neighborID);
            moveArmiesRecursively(warKey, originProvinceID, neighborID, depth - 1);
         }
      }
   }

   public static void battleStarted_2(int battleID, int provinceID) {
      try {
         if (battleID < 0) {
            return;
         }

         if (Game.battleManager.getBattle(battleID).attackingArmy.iCivID < 0 || Game.battleManager.getBattle(battleID).defendingArmy.iCivID < 0) {
            return;
         }

         String warKey = WarManager.getWarKey(
            Game.battleManager.getBattle(battleID).attackingArmy.iCivID, Game.battleManager.getBattle(battleID).defendingArmy.iCivID
         );
         if (warKey == null) {
            return;
         }

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            Game.getProvince(inProvinceID).wasBattleStart = false;

            for (int b = 0; b < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvincesSize(); b++) {
               inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvinces(b);
               Game.getProvince(inProvinceID).wasBattleStart = false;
            }
         }

         Game.getProvince(provinceID).wasBattleStart = true;

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            moveArmies(warKey, provinceID, inProvinceID);

            for (int b = 0; b < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvincesSize(); b++) {
               inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(a)).getNeighboringProvinces(b);
               moveArmies(warKey, provinceID, inProvinceID);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static void battleStarted_1(int battleID, int provinceID) {
      try {
         if (battleID < 0) {
            return;
         }

         if (Game.battleManager.getBattle(battleID).attackingArmy.iCivID < 0 || Game.battleManager.getBattle(battleID).defendingArmy.iCivID < 0) {
            return;
         }

         String warKey = WarManager.getWarKey(
            Game.battleManager.getBattle(battleID).attackingArmy.iCivID, Game.battleManager.getBattle(battleID).defendingArmy.iCivID
         );
         if (warKey == null) {
            return;
         }

         for (int a = 0; a < Game.getProvince(provinceID).getNeighboringProvincesSize(); a++) {
            int inProvinceID = Game.getProvince(provinceID).getNeighboringProvinces(a);
            if (!Game.getProvinceData4(inProvinceID).isUnderSiege()) {
               moveArmies(warKey, provinceID, inProvinceID);
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static void battleStarted_SeaProvinces_UpdateWas(int provinceID) {
      for (int i = 0; i < Game.getProvince(provinceID).getNeighboringSeaProvincesSize(); i++) {
         int inProvinceID = Game.getProvince(provinceID).getNeighboringSeaProvinces(i);
         Game.getProvince(inProvinceID).wasBattleStart = false;

         for (int j = 0; j < Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvincesSize(); j++) {
            inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j);
            Game.getProvince(inProvinceID).wasBattleStart = false;

            for (int k = 0;
               k
                  < Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j))
                     .getNeighboringProvincesSize();
               k++
            ) {
               inProvinceID = Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j))
                  .getNeighboringProvinces(k);
               Game.getProvince(inProvinceID).wasBattleStart = false;
            }
         }
      }
   }

   public static void battleStarted_SeaProvinces(String warKey, int provinceID) {
      for (int i = 0; i < Game.getProvince(provinceID).getNeighboringSeaProvincesSize(); i++) {
         int inProvinceID = Game.getProvince(provinceID).getNeighboringSeaProvinces(i);
         moveArmies(warKey, provinceID, inProvinceID);

         for (int j = 0; j < Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvincesSize(); j++) {
            inProvinceID = Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j);
            moveArmies(warKey, provinceID, inProvinceID);

            for (int k = 0;
               k
                  < Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j))
                     .getNeighboringProvincesSize();
               k++
            ) {
               inProvinceID = Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringSeaProvinces(i)).getNeighboringProvinces(j))
                  .getNeighboringProvinces(k);
               moveArmies(warKey, provinceID, inProvinceID);
            }
         }
      }
   }

   public static void moveArmies(String warKey, int battleProvinceID, int inProvinceID) {
      try {
         if (!Game.getProvince(inProvinceID).wasBattleStart) {
            Game.getProvince(inProvinceID).wasBattleStart = true;

            for (int i = 0; i < Game.getProvince(inProvinceID).getArmySize(); i++) {
               if ((Game.getProvince(inProvinceID).getArmy(i).civID != Game.player.iCivID || Game.player.allowAIMove)
                  && !Game.getProvince(inProvinceID).getArmy(i).inBattle
                  && !Game.getProvince(inProvinceID).getArmy(i).inRetreat
                  && WarManager.lWars.get(warKey).isInThisWar(Game.getProvince(inProvinceID).getArmy(i).civID)
                  && (
                     !Game.getProvinceData4(inProvinceID).isUnderSiege()
                        || !(Game.getProvince(inProvinceID).getSiegeProgress() >= Game.aiValuesArmies.BATTLE_START_DONT_MOVE_IF_SIEGE_OVER)
                           && Game.aiValuesArmies.BATTLE_START_DONT_MOVE_IF_SIEGE_IN_PROGRESS <= Game.oR.nextInt(100)
                  )
                  && !(Game.getProvince(inProvinceID).getArmy(i).fMorale <= Game.aiValuesArmies.BATTLE_START_DONT_MOVE_IF_MORALE_BELOW)
                  && (
                     !(Game.getCiv(Game.getProvince(inProvinceID).getArmy(i).civID).fManpower > 1000.0)
                        || !(
                           Game.getProvince(inProvinceID).getArmy(i).getPercOfTotalUnits()
                              < Game.aiValuesArmies.BATTLE_START_DONT_MOVE_IF_ARMY_UNITS_BELOW_PERC
                        )
                  )
                  && (
                     !Game.getProvince(inProvinceID).getArmy(i).inMovement
                        || Game.aiValuesArmies.BATTLE_START_DONT_MOVE_IF_ARMY_IS_IN_MOVEMENT <= Game.oR.nextInt(100)
                  )) {
                  Game.getCiv(Game.getProvince(inProvinceID).getArmy(i).civID)
                     .newMove(inProvinceID, battleProvinceID, Game.getProvince(inProvinceID).getArmy(i).key, 0, false);
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
