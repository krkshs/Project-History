package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class AI_MergeTask {
   public int iProvinceID;
   public List<String> keys = new ArrayList<>();

   public AI_MergeTask() {
   }

   public AI_MergeTask(int iProvinceID, String armyKey) {
      this.iProvinceID = iProvinceID;
      this.keys.add(armyKey);
   }

   public void addKey(String key) {
      if (!this.keys.contains(key)) {
         this.keys.add(key);
      }
   }

   public void removeKey(String key) {
      for (int i = this.keys.size() - 1; i > 0; i--) {
         if (this.keys.get(i).equals(key)) {
            this.keys.remove(i);
            return;
         }
      }
   }

   public void checkArmiesExists(int civID) {
      for (int i = this.keys.size() - 1; i >= 0; i--) {
         if (!Game.getCiv(civID).armyExists(this.keys.get(i))) {
            this.keys.remove(i);
         } else if (Game.getProvince(this.iProvinceID).getArmyKeyID(this.keys.get(i), civID) < 0
            && !Game.getCiv(civID).isArmyMovingToProvince(this.keys.get(i), this.iProvinceID)) {
            this.keys.remove(i);
         }
      }
   }
}
