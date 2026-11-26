package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;

public class EventTrigger_HasVariableCiv extends EventTrigger_Value {
   public String civTAG;
   public String value;

   public EventTrigger_HasVariableCiv(String civTAG, String value) {
      this.civTAG = civTAG;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int civID = Game.getCivID(this.civTAG);
      return civID <= 0 ? false : Game.getCiv(civID).eventsDataVariables.hasVariable(this.value);
   }

   @Override
   public String getText() {
      return Game.lang.getCiv(this.civTAG) + ", " + Game.lang.get("HasVariable") + ": ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.get(this.value).replace("_", " ");
   }

   @Override
   public String getText3() {
      return "";
   }
}
