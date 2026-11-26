package aoc.kingdoms.lukasz.map.civilization.data;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class Civilization_ArmiesWithoutGenerals {
   public List<String> keys = new ArrayList<>();
   public int keysSize = 0;

   public final void addArmyKey(String armyKey) {
      if (!this.keys.contains(armyKey)) {
         this.keys.add(armyKey);
         this.keysSize = this.keys.size();
      }
   }

   public final void removeArmyKey(String armyKey) {
      try {
         for (int i = 0; i < this.keysSize; i++) {
            if (this.keys.get(i).equals(armyKey)) {
               this.keys.remove(i);
               this.keysSize = this.keys.size();
               return;
            }
         }
      } catch (Exception var3) {
      }
   }

   public final void buildArmiesWithoutGenerals(int civID) {
      for (int i = 0; i < Game.getCiv(civID).iArmyPositionSize; i++) {
         for (int a = 0; a < Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmySize(); a++) {
            if (Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(a).civID == civID
               && Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(a).armyGeneral == null) {
               this.addArmyKey(Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(a).key);
            }
         }
      }
   }
}
