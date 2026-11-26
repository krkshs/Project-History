package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_CreateNewArmy_Task;
import java.util.ArrayList;
import java.util.List;

public class AI_Civ_CreateNewArmy {
   public List<AI_CreateNewArmy_Task> cNA = new ArrayList<>();

   public final void runCreateNewArmy_Task(int civID) {
      for (int i = 0; i < this.cNA.size(); i++) {
         if (this.runCreateNewArmy_Task(civID, i)) {
            i--;
         } else if (this.cNA.get(i).t < Game_Calendar.TURN_ID) {
            this.cNA.remove(i--);
         }
      }
   }

   public final void runCreateNewArmy_Expired(int civID) {
      for (int i = 0; i < this.cNA.size(); i++) {
         if (this.cNA.get(i).t < Game_Calendar.TURN_ID) {
            this.cNA.remove(i--);
         }
      }
   }

   public final boolean runCreateNewArmy_Task(int civID, int id) {
      if (this.cNA.get(id).recruitArmy(civID)) {
         this.cNA.remove(id);
         return true;
      } else {
         return false;
      }
   }

   public int createNewArmy_RegimentsLeft() {
      int out = 0;

      for (int i = this.cNA.size() - 1; i >= 0; i--) {
         out += this.cNA.get(i).rL;
      }

      return out;
   }

   public int getCreateNewArmy_RegimentsInQueue(String armyKey) {
      int out = 0;

      for (int i = this.cNA.size() - 1; i >= 0; i--) {
         if (this.cNA.get(i).key.equals(armyKey)) {
            out += this.cNA.get(i).rL;
         }
      }

      return out;
   }
}
