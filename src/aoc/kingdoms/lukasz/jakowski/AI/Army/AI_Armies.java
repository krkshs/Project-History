package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyPosition;
import java.util.ArrayList;
import java.util.List;

public class AI_Armies {
   public static final void regroupPeace(int civID) {
      try {
         if (!Game.getCiv(civID).diplomacy.isAtWar() && Game.getCiv(civID).getNumOfProvinces() > 0) {
            List<ArmyPosition> toMove = new ArrayList<>();

            for (int i = 0; i < Game.getCiv(civID).iArmyPositionSize; i++) {
               if (Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getCivID() != civID) {
                  ArmyDivision armyDivision = Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(Game.getCiv(civID).getArmyPositionKey(i));
                  if (armyDivision != null && !armyDivision.inBattle && !armyDivision.inRetreat) {
                     toMove.add(new ArmyPosition(Game.getCiv(civID).getArmyPosition(i), Game.getCiv(civID).getArmyPositionKey(i)));
                  }
               }
            }

            for (int ix = toMove.size() - 1; ix >= 0; ix--) {
               int bestID = Game.getCiv(civID).getProvinceID(0);
               float bestDistance = Game.getDistanceFromProvinceToProvince(toMove.get(ix).provinceID, Game.getCiv(civID).getProvinceID(0));

               for (int j = 1; j < Game.getCiv(civID).getNumOfProvinces(); j++) {
                  if (bestDistance > Game.getDistanceFromProvinceToProvince(toMove.get(ix).provinceID, Game.getCiv(civID).getProvinceID(j))) {
                     bestID = Game.getCiv(civID).getProvinceID(j);
                     bestDistance = Game.getDistanceFromProvinceToProvince(toMove.get(ix).provinceID, Game.getCiv(civID).getProvinceID(j));
                  }
               }

               Game.getCiv(civID).newMove(toMove.get(ix).provinceID, bestID, toMove.get(ix).key, 0, false);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }
}
