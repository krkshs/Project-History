package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_SiegeStart {
   public static void siegeStarted_Attacker(int provinceID) {
      try {
         if (Game.getProvince(provinceID).getFortLevel() > 0) {
            return;
         }

         if (Game.battleManager.isBattleInProvince(provinceID)) {
            return;
         }

         List<Integer> civs = new ArrayList<>();
         Province province = Game.getProvince(provinceID);

         for (int i = 0; i < province.getArmySize(); i++) {
            if (province.getArmy(i).civID > 0
               && (province.getArmy(i).civID != Game.player.iCivID || Game.player.allowAIMove)
               && !civs.contains(province.getArmy(i).civID)) {
               civs.add(province.getArmy(i).civID);
            }
         }

         if (civs.isEmpty()) {
            return;
         }

         List<Integer> possibleProvinces = new ArrayList<>();

         for (int ix = 0; ix < province.getNeighboringProvincesSize(); ix++) {
            if (Game.getProvinceData4(province.getNeighboringProvinces(ix)).isUnderSiege()
               && province.getCivID() == Game.getProvince(province.getNeighboringProvinces(ix)).getCivID()) {
               boolean allyArmy = false;

               for (int j = 0; j < Game.getProvince(province.getNeighboringProvinces(ix)).getArmySize(); j++) {
                  for (int k = civs.size() - 1; k >= 0; k--) {
                     if (DiplomacyManager.isAlly(civs.get(k), Game.getProvince(province.getNeighboringProvinces(ix)).getArmy(j).civID)) {
                        allyArmy = true;
                        break;
                     }
                  }

                  if (allyArmy) {
                     break;
                  }
               }

               if (allyArmy) {
                  possibleProvinces.add(province.getNeighboringProvinces(ix));
               }
            }
         }

         boolean end = false;
         int armyY = 0;
         if (possibleProvinces.size() > 0) {
            for (int ixx = province.getArmySize() - 1; ixx >= 0; ixx--) {
               if ((province.getArmy(ixx).civID != Game.player.iCivID || Game.player.allowAIMove)
                  && civs.contains(province.getArmy(ixx).civID)
                  && !province.getArmy(ixx).inRetreat
                  && !province.getArmy(ixx).inBattle) {
                  int toProvinceID = possibleProvinces.get(Game.oR.nextInt(possibleProvinces.size()));
                  if (DiplomacyManager.isAtWar(province.getArmy(ixx).civID, Game.getProvince(toProvinceID).getCivID())) {
                     Game.getCiv(province.getArmy(ixx).civID).newMove(provinceID, toProvinceID, province.getArmy(ixx).key, armyY++, false);
                  }

                  end = true;
               }
            }
         }

         possibleProvinces.clear();
         possibleProvinces = null;
         if (end) {
            civs.clear();
            civs = null;
            return;
         }

         for (int ixxx = 0; ixxx < province.getNeighboringProvincesSize(); ixxx++) {
            if (!Game.battleManager.isBattleInProvince(province.getNeighboringProvinces(ixxx))
               && !Game.getProvinceData4(province.getNeighboringProvinces(ixxx)).isUnderSiege()) {
               for (int j = 0; j < Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmySize(); j++) {
                  if (Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).civID > 0
                     && (Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).civID != Game.player.iCivID || Game.player.allowAIMove)
                     && !Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).inRetreat) {
                     for (int a = civs.size() - 1; a >= 0; a--) {
                        if (DiplomacyManager.isAlly(civs.get(a), Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).civID)) {
                           if (DiplomacyManager.isAtWar(province.getCivID(), Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).civID)) {
                              Game.getCiv(Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).civID)
                                 .newMove(
                                    province.getNeighboringProvinces(ixxx),
                                    provinceID,
                                    Game.getProvince(province.getNeighboringProvinces(ixxx)).getArmy(j).key,
                                    armyY++,
                                    false
                                 );
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }

         civs.clear();
         civs = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static void siegeStarted_Defend(int provinceID) {
      try {
         Province province = Game.getProvince(provinceID);
         if (province.getCivID() > 0
            && (province.getCivID() != Game.player.iCivID || Game.player.allowAIMove)
            && !province.isOccupied()
            && province.isCapital
            && Game.getCiv(province.getCivID()).getCapitalProvinceID() == provinceID
            && (
               Game.oR.nextInt(100) < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_DEFEND_CAPITAL_RANDOM
                  || Game.getCiv(province.getCivID()).getNumOfProvinces() < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_DEFEND_CAPITAL_IF_NUM_OF_PROVINCES_BELOW
            )) {
            int enemyArmies = countEnemyArmy(provinceID);
            int civID = province.getCivID();
            if (Game.getCiv(civID).getArmyRegimentSize() * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE >= enemyArmies * 0.95F
               || Game.getCiv(province.getCivID()).getNumOfProvinces() < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_DEFEND_CAPITAL_IF_NUM_OF_PROVINCES_BELOW) {
               List<Integer> armyPos = new ArrayList<>();
               List<Float> armyPosDistance = new ArrayList<>();

               for (int i = 0; i < Game.getCiv(civID).iArmyPositionSize; i++) {
                  armyPos.add(Game.getCiv(civID).getArmyPosition(i));
                  armyPosDistance.add(Game.getDistanceFromProvinceToProvince(provinceID, Game.getCiv(civID).getArmyPosition(i)));
               }

               while (armyPos.size() > 0 && enemyArmies > 0) {
                  int bestID = 0;

                  for (int i = armyPosDistance.size() - 1; i > 0; i--) {
                     if (armyPosDistance.get(i) < armyPosDistance.get(bestID)) {
                        bestID = i;
                     }
                  }

                  for (int j = Game.getProvince(armyPos.get(bestID)).getArmySize() - 1; j >= 0; j--) {
                     if (Game.getProvince(armyPos.get(bestID)).getArmy(j).civID == civID
                        && Game.getCiv(civID).newMove(armyPos.get(bestID), provinceID, Game.getProvince(armyPos.get(bestID)).getArmy(j).key, 0, false)) {
                        enemyArmies -= Game.getProvince(armyPos.get(bestID)).getArmy(j).iArmyRegimentSize;
                     }
                  }

                  armyPos.remove(bestID);
                  armyPosDistance.remove(bestID);
               }

               armyPos.clear();
               armyPosDistance.clear();
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static int countEnemyArmy(int provinceID) {
      Province province = Game.getProvince(provinceID);
      int enemyArmies = 0;

      for (int i = 0; i < province.getArmySize(); i++) {
         if (DiplomacyManager.isAtWar(province.getCivID(), province.getArmy(i).civID)) {
            enemyArmies += province.getArmy(i).iArmy;
         }
      }

      for (int j = 0; j < province.getNeighboringProvincesSize(); j++) {
         for (int ix = 0; ix < Game.getProvince(province.getNeighboringProvinces(j)).getArmySize(); ix++) {
            if (DiplomacyManager.isAtWar(province.getCivID(), Game.getProvince(province.getNeighboringProvinces(j)).getArmy(ix).civID)) {
               enemyArmies += Game.getProvince(province.getNeighboringProvinces(j)).getArmy(ix).iArmy;
            }
         }
      }

      return enemyArmies;
   }
}
