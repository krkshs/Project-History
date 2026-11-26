package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class EventTrigger_IsNotPlayer extends EventTrigger_Value {
   public String value;

   public EventTrigger_IsNotPlayer(String value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         return Game.player.iCivID != Game.getCivID(this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         return false;
      }
   }

   @Override
   public String getText() {
      return Game.lang.get("NOT") + ", " + Game.lang.get("Player") + " -> ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.getCiv(this.value);
   }

   @Override
   public String getText3() {
      return "";
   }
}
