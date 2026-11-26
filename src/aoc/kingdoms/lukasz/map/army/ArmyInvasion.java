package aoc.kingdoms.lukasz.map.army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class ArmyInvasion {
   public String armyKey;
   public List<Integer> lProvinces = new ArrayList<>();

   public ArmyInvasion() {
   }

   public ArmyInvasion(int civID, String nKey, List<Integer> nProvinces) {
      this.armyKey = nKey;
      this.lProvinces = new ArrayList<>(nProvinces);
      this.invasionMoveArmy(civID);
   }

   public final boolean invasionMoveArmy(int civID) {
      try {
         Game.ArmyPos armyPos = Game.findArmy_FullCheck(civID, this.armyKey);
         if (armyPos == null) {
            return false;
         } else {
            for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
               if (!Game.getProvince(this.lProvinces.get(i)).isOccupied()
                  || Game.getCiv(Game.getProvince(this.lProvinces.get(i)).getCivID()).getPuppetOfCivID() != Game.player.iCivID) {
                  if (Game.getProvince(this.lProvinces.get(i)).getCivID() == civID) {
                     this.lProvinces.remove(i);
                  } else if (!DiplomacyManager.isAtWar(civID, Game.getProvince(this.lProvinces.get(i)).getCivID())) {
                     this.lProvinces.remove(i);
                  } else if (this.lProvinces.get(i) == armyPos.iProvinceID) {
                     this.lProvinces.remove(i);
                  } else if (Game.getProvince(this.lProvinces.get(i)).isOccupied()) {
                     this.lProvinces.remove(i);
                  }
               }
            }

            if (this.lProvinces.size() == 0) {
               return false;
            } else {
               int bestID = 0;
               float distanceBest = Game.getDistanceFromProvinceToProvince(armyPos.iProvinceID, this.lProvinces.get(0));
               int ix = 1;

               for (int iSize = this.lProvinces.size(); ix < iSize; ix++) {
                  float tempDistance = Game.getDistanceFromProvinceToProvince(armyPos.iProvinceID, this.lProvinces.get(ix));
                  if (distanceBest > tempDistance) {
                     bestID = ix;
                     distanceBest = tempDistance;
                  }
               }

               bestID = this.lProvinces.get(bestID);
               if (Game.getProvince(bestID).getFortLevel() == 0) {
                  ix = 0;
                  List<Integer> extraID = new ArrayList<>();
                  List<Integer> extraScore = new ArrayList<>();

                  for (int ixx = 0; ixx < Game.getProvince(bestID).getNeighboringProvincesSize(); ixx++) {
                     if (this.lProvinces.contains(Game.getProvince(bestID).getNeighboringProvinces(ixx))) {
                        ix++;
                        extraID.add(Game.getProvince(bestID).getNeighboringProvinces(ixx));
                        int tExtraScore = 0;

                        for (int j = 0; j < Game.getProvince(Game.getProvince(bestID).getNeighboringProvinces(ixx)).getNeighboringProvincesSize(); j++) {
                           if (this.lProvinces.contains(Game.getProvince(Game.getProvince(bestID).getNeighboringProvinces(ixx)).getNeighboringProvinces(j))) {
                              tExtraScore++;
                           }
                        }

                        extraScore.add(tExtraScore);
                     }
                  }

                  for (int ixxx = extraID.size() - 1; ixxx >= 0; ixxx--) {
                     if (extraScore.get(ixxx) > ix) {
                        bestID = extraID.get(ixxx);
                     }
                  }

                  extraID.clear();
                  extraScore.clear();
               }

               Game.getCiv(civID).newMove(armyPos.iProvinceID, bestID, this.armyKey, 0, false);
               return true;
            }
         }
      } catch (Exception var12) {
         CFG.exceptionStack(var12);
         return false;
      }
   }
}
