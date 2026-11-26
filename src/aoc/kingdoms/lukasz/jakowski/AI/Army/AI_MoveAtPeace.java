package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyPosition;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_MoveAtPeace {
   public static void moveAtPeace(int civID) {
      try {
         if (Game.getCiv(civID).underSiegeSize > 0 || Game.getCiv(civID).occupiedProvincesSize > 0) {
            List<AI_MoveAtPeace.ArmyDivision_TempData> possibleToMove = new ArrayList<>();

            for (int j = 0; j < Game.getCiv(civID).iArmyPositionSize; j++) {
               if (Game.getProvince(Game.getCiv(civID).getArmyPosition(j)).isOccupied()
                  && !Game.getProvinceData4(Game.getCiv(civID).getArmyPosition(j)).isUnderSiege()) {
                  SiegeManager.checkForSiege(Game.getCiv(civID).getArmyPosition(j));
               }

               if (!Game.getProvinceData4(Game.getCiv(civID).getArmyPosition(j)).isUnderSiege()) {
                  ArmyDivision armyDivision = Game.getProvince(Game.getCiv(civID).getArmyPosition(j)).getArmy(Game.getCiv(civID).getArmyPositionKey(j));
                  if (armyDivision != null) {
                     if (armyDivision.inMovement) {
                        if (!Game.getCiv(civID).isInMoveUnits_ArmyKey(armyDivision.key)) {
                           armyDivision.inMovement = false;
                           armyDivision.inRetreat = false;
                        }
                     } else if (!armyDivision.inBattle) {
                        possibleToMove.add(new AI_MoveAtPeace.ArmyDivision_TempData(Game.getCiv(civID).armyPosition.get(j), armyDivision.iArmyRegimentSize));
                     }
                  }
               }
            }

            if (possibleToMove.isEmpty()) {
               if (Game.getCiv(civID).getArmyRegimentSize() < 6) {
                  AI_RecruitMercenaries.recruitMercenaries(civID);
               }

               return;
            }

            List<Integer> provinces = new ArrayList<>();

            for (int i = Game.getCiv(civID).underSiegeSize - 1; i >= 0; i--) {
               if (Game.getProvinceData4(Game.getCiv(civID).underSiege.get(i)).isUnderSiege()) {
                  provinces.add(Game.getCiv(civID).underSiege.get(i));
               } else {
                  Game.getCiv(civID).underSiege.remove(i);
                  Game.getCiv(civID).underSiegeSize = Game.getCiv(civID).underSiege.size();
               }
            }

            while (provinces.size() > 0) {
               int id = Game.oR.nextInt(provinces.size());
               int enemyArmy = 0;

               for (int j = 0; j < Game.getProvince(provinces.get(id)).getArmySize(); j++) {
                  if (DiplomacyManager.isAtWar(civID, Game.getProvince(provinces.get(id)).getArmy(j).civID)) {
                     enemyArmy += Game.getProvince(provinces.get(id)).getArmy(j).iArmyRegimentSize;
                  }
               }

               enemyArmy -= Game.getCiv(civID).armyMovingToProvince_MoveUnits(provinces.get(id));
               if (enemyArmy > 0 && Game.getCiv(civID).getArmyRegimentSize() >= enemyArmy) {
                  for (int a = possibleToMove.size() - 1; a >= 0; a--) {
                     possibleToMove.get(a).distance = Game.getDistanceFromProvinceToProvince(provinces.get(id), possibleToMove.get(a).armyPosition.provinceID);
                  }

                  while (enemyArmy > 0 && possibleToMove.size() > 0) {
                     int bestID = 0;

                     for (int a = possibleToMove.size() - 1; a > 0; a--) {
                        if (possibleToMove.get(bestID).distance > possibleToMove.get(a).distance) {
                           bestID = a;
                        }
                     }

                     if (possibleToMove.get(bestID).distance == 100000.0F) {
                        break;
                     }

                     if (Game.getCiv(civID)
                        .newMove(possibleToMove.get(bestID).armyPosition.provinceID, provinces.get(id), possibleToMove.get(bestID).armyPosition.key, 0, false)) {
                        enemyArmy -= possibleToMove.get(bestID).iRegiments;
                        possibleToMove.remove(bestID);
                     } else {
                        possibleToMove.get(bestID).distance = 100000.0F;
                     }
                  }
               }

               provinces.remove(id);
            }

            provinces.clear();
            if (possibleToMove.size() == 0) {
               return;
            }

            for (int ix = Game.getCiv(civID).occupiedProvincesSize - 1; ix >= 0; ix--) {
               if (Game.getProvinceData(Game.getCiv(civID).occupiedProvinces.get(ix)).getOccupiedByCivID() != 0) {
                  provinces.add(Game.getCiv(civID).occupiedProvinces.get(ix));
               } else {
                  Game.getCiv(civID).occupiedProvinces.remove(ix);
                  Game.getCiv(civID).occupiedProvincesSize = Game.getCiv(civID).occupiedProvinces.size();
               }
            }

            while (!provinces.isEmpty()) {
               int id = Game.oR.nextInt(provinces.size());
               if (Game.getProvince(provinces.get(id)).getFortLevel() <= 0) {
                  int iNeigh = 0;

                  for (int ixx = 0; ixx < Game.getProvince(provinces.get(id)).getNeighboringProvincesSize(); ixx++) {
                     if (Game.getProvince(Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixx)).getCivID() == civID
                        && Game.getProvinceData(Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixx)).getOccupiedByCivID() != 0) {
                        iNeigh++;
                     }
                  }

                  for (int ixxx = 0; ixxx < Game.getProvince(provinces.get(id)).getNeighboringProvincesSize(); ixxx++) {
                     int pNeigh = 0;

                     for (int jx = 0;
                        jx < Game.getProvince(Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixxx)).getNeighboringProvincesSize();
                        jx++
                     ) {
                        if (Game.getProvince(Game.getProvince(Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixxx)).getNeighboringProvinces(jx))
                                 .getCivID()
                              == civID
                           && Game.getProvinceData(
                                    Game.getProvince(Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixxx)).getNeighboringProvinces(jx)
                                 )
                                 .getOccupiedByCivID()
                              != 0) {
                           pNeigh++;
                        }
                     }

                     if (iNeigh < pNeigh) {
                        for (int k = provinces.size() - 1; k >= 0; k--) {
                           if (provinces.get(k) == Game.getProvince(provinces.get(id)).getNeighboringProvinces(ixxx)) {
                              id = k;
                              break;
                           }
                        }
                     }
                  }
               }

               int enemyArmy = GameValues.siege.SIEGE_REGIMENTS_MIN + 1;

               for (int jxx = 0; jxx < Game.getProvince(provinces.get(id)).getArmySize(); jxx++) {
                  if (DiplomacyManager.isAtWar(civID, Game.getProvince(provinces.get(id)).getArmy(jxx).civID)) {
                     enemyArmy += Game.getProvince(provinces.get(id)).getArmy(jxx).iArmyRegimentSize;
                  }
               }

               enemyArmy -= Game.getCiv(civID).armyMovingToProvince_MoveUnits(provinces.get(id));
               if (enemyArmy > 0) {
                  for (int ax = possibleToMove.size() - 1; ax >= 0; ax--) {
                     possibleToMove.get(ax).distance = Game.getDistanceFromProvinceToProvince(provinces.get(id), possibleToMove.get(ax).armyPosition.provinceID);
                  }

                  while (enemyArmy > 0 && possibleToMove.size() > 0) {
                     int bestID = 0;

                     for (int ax = possibleToMove.size() - 1; ax > 0; ax--) {
                        if (possibleToMove.get(bestID).distance > possibleToMove.get(ax).distance) {
                           bestID = ax;
                        }
                     }

                     if (possibleToMove.get(bestID).distance == 100000.0F) {
                        break;
                     }

                     if (Game.getCiv(civID)
                        .newMove(possibleToMove.get(bestID).armyPosition.provinceID, provinces.get(id), possibleToMove.get(bestID).armyPosition.key, 0, false)) {
                        enemyArmy -= possibleToMove.get(bestID).iRegiments;
                        possibleToMove.remove(bestID);
                     } else {
                        possibleToMove.get(bestID).distance = 100000.0F;
                     }
                  }
               }

               provinces.remove(id);
            }

            possibleToMove.clear();
            provinces.clear();
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static class ArmyDivision_TempData {
      public ArmyPosition armyPosition;
      public int iRegiments;
      public float distance;

      public ArmyDivision_TempData(ArmyPosition armyPosition, int iRegiments) {
         this.armyPosition = armyPosition;
         this.iRegiments = iRegiments;
      }
   }
}
