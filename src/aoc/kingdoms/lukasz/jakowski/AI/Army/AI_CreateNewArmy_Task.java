package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import java.util.ArrayList;
import java.util.List;

public class AI_CreateNewArmy_Task {
   public String key;
   public List<AI_CreateNewArmy> cre = new ArrayList<>();
   public int rL = 0;
   public int t = 0;

   public AI_CreateNewArmy_Task() {
   }

   public AI_CreateNewArmy_Task(String key, List<AI_CreateNewArmy> nCreateNewArmy, int expiresTurnID) {
      this.key = key;
      this.cre = nCreateNewArmy;
      this.t = expiresTurnID;

      for (int i = this.cre.size() - 1; i >= 0; i--) {
         this.rL = this.rL + this.cre.get(i).n;
      }
   }

   public boolean recruitArmy(int civID) {
      List<Integer> possibleProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (!Game.getProvince(Game.getCiv(civID).getProvinceID(i)).isOccupied()) {
            possibleProvinces.add(Game.getCiv(civID).getProvinceID(i));
         }
      }

      int possibleProvincesSize = possibleProvinces.size();
      int iSize;
      if (possibleProvinces.size() > 0) {
         while ((iSize = this.cre.size()) > 0) {
            int randomID = Game.oR.nextInt(iSize);
            if (!Game.getCiv(civID)
               .recruitArmy(
                  new ArmyRecruit(possibleProvinces.get(Game.oR.nextInt(possibleProvincesSize)), this.cre.get(randomID).u, this.cre.get(randomID).a, this.key)
               )) {
               break;
            }

            this.cre.get(randomID).n--;
            this.rL--;
            if (this.cre.get(randomID).n <= 0) {
               this.cre.remove(randomID);
            }
         }
      }

      possibleProvinces.clear();
      return this.cre.size() <= 0;
   }
}
