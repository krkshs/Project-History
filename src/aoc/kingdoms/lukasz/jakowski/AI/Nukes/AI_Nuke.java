package aoc.kingdoms.lukasz.jakowski.AI.Nukes;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import java.util.ArrayList;
import java.util.List;

public class AI_Nuke {
   public static void useNukes(int civID) {
      if (Game.getCiv(civID).getNukes() > 0) {
         try {
            List<Integer> possibleProvinces = new ArrayList<>();

            for (int i = 0; i < Game.getCiv(civID).diplomacy.iAtWarSize; i++) {
               Civilization civ = Game.getCiv(Game.getCiv(civID).diplomacy.atWar.get(i));

               for (int j = 0; j < civ.getNumOfProvinces(); j++) {
                  if (!Game.getProvince(civ.getProvinceID(j)).isOccupied()
                     && Game.getProvince(civ.getProvinceID(j)).getDevastation() < GameValues.atomic.AI_NUKE_IF_DEVASTATION_BELOW) {
                     possibleProvinces.add(civ.getProvinceID(j));
                  }
               }
            }

            while (Game.getCiv(civID).getNukes() > 0 && !possibleProvinces.isEmpty()) {
               int bestID = 0;

               for (int a = 0; !possibleProvinces.isEmpty() && a < Game.getProvince(possibleProvinces.get(bestID)).getArmySize(); a++) {
                  if (Game.getProvince(possibleProvinces.get(bestID)).getArmy(a).civID == civID) {
                     possibleProvinces.remove(bestID);
                  }
               }

               if (possibleProvinces.isEmpty()) {
                  break;
               }

               for (int ax = possibleProvinces.size() - 1; ax >= 0; ax--) {
                  if (Game.getProvince(possibleProvinces.get(bestID)).getPopulationTotal() < Game.getProvince(possibleProvinces.get(ax)).getPopulationTotal()) {
                     bestID = ax;
                  }
               }

               Game.dropAtomicBomb(civID, possibleProvinces.get(bestID));
               possibleProvinces.remove(bestID);
            }

            possibleProvinces.clear();
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }
      }
   }
}
