package aoc.kingdoms.lukasz.events;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class EventOption {
   public String name = "";
   public int bonus_duration = 1;
   public float ai = 1.0F;
   public List<EventOutcome> outcome = new ArrayList<>();

   public final void executeOutcome() {
      try {
         for (int i = 0; i < this.outcome.size(); i++) {
            this.outcome.get(i).update();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void executeOutcome(int iCivID) {
      try {
         if (Game.getCiv(iCivID).eventProvinceID >= 0) {
            for (int i = 0; i < this.outcome.size(); i++) {
               this.outcome.get(i).updateProvince(Game.getCiv(iCivID).eventProvinceID);
            }
         }

         for (int i = 0; i < this.outcome.size(); i++) {
            this.outcome.get(i).updateCiv(iCivID, this.bonus_duration);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }
}
