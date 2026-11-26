package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class AI_Merge {
   public List<AI_MergeTask> aiMergeTasks = new ArrayList<>();

   public void addMerge(int provinceID, String armyKey) {
      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (this.aiMergeTasks.get(i).iProvinceID == provinceID) {
            this.aiMergeTasks.get(i).addKey(armyKey);
            return;
         }
      }

      this.aiMergeTasks.add(new AI_MergeTask(provinceID, armyKey));
   }

   public boolean mergeArmies(int mergeID, String armyKey, String armyKey2) {
      for (int i = 0; i < this.aiMergeTasks.get(mergeID).keys.size(); i++) {
         for (int j = i + 1; j < this.aiMergeTasks.get(mergeID).keys.size(); j++) {
            if (this.aiMergeTasks.get(mergeID).keys.get(i).equals(armyKey) && this.aiMergeTasks.get(mergeID).keys.get(j).equals(armyKey2)
               || this.aiMergeTasks.get(mergeID).keys.get(i).equals(armyKey2) && this.aiMergeTasks.get(mergeID).keys.get(j).equals(armyKey)) {
               return true;
            }
         }
      }

      return false;
   }

   public void checkMerge(int provinceID, String armyKey) {
      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (this.aiMergeTasks.get(i).iProvinceID == provinceID) {
            for (int a = 0; a < Game.getProvince(provinceID).getArmySize(); a++) {
               for (int b = Game.getProvince(provinceID).getArmySize() - 1; b > a; b--) {
                  if (Game.getProvince(provinceID).getArmy(a).civID == Game.getProvince(provinceID).getArmy(b).civID
                     && this.mergeArmies(i, Game.getProvince(provinceID).getArmy(a).key, Game.getProvince(provinceID).getArmy(b).key)) {
                     try {
                        List<String> toMerge = new ArrayList<>();
                        toMerge.add(Game.getProvince(provinceID).getArmy(a).key);
                        toMerge.add(Game.getProvince(provinceID).getArmy(b).key);
                        Game.getProvince(provinceID).mergeUnits(toMerge);
                     } catch (Exception var7) {
                        CFG.exceptionStack(var7);
                     }
                  }
               }
            }
         }
      }
   }

   public void checkMerge_ArmyExists(int civID) {
      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (Game.getProvince(this.aiMergeTasks.get(i).iProvinceID).getCivID() != civID) {
            this.aiMergeTasks.remove(i);
         } else {
            for (int j = this.aiMergeTasks.get(i).keys.size() - 1; j >= 0; j--) {
               if (!Game.getCiv(civID).armyExists(this.aiMergeTasks.get(i).keys.get(j))) {
               }
            }
         }
      }
   }

   public boolean isArmyMerging(int civID, String armyKey) {
      this.checkMergeIsPossibleForArmy(civID, armyKey);

      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (this.aiMergeTasks.get(i).keys.contains(armyKey)) {
            return true;
         }
      }

      return false;
   }

   public boolean isArmyMerging_Just(int civID, String armyKey) {
      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (this.aiMergeTasks.get(i).keys.contains(armyKey)) {
            return true;
         }
      }

      return false;
   }

   public void checkMergeIsPossibleForArmy(int civID, String armyKey) {
      for (int i = this.aiMergeTasks.size() - 1; i >= 0; i--) {
         if (this.aiMergeTasks.get(i).keys.contains(armyKey)) {
            this.aiMergeTasks.get(i).checkArmiesExists(civID);
            if (this.aiMergeTasks.get(i).keys.size() < 2) {
               this.aiMergeTasks.remove(i);
            }
         }
      }
   }
}
