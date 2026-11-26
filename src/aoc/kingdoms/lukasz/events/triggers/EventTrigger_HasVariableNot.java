package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;

public class EventTrigger_HasVariableNot extends EventTrigger_Value {
   public String value;

   public EventTrigger_HasVariableNot(String value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return !Game.getCiv(iCivID).eventsDataVariables.hasVariable(this.value);
   }

   @Override
   public String getText() {
      return Game.lang.get("HasntVariable") + ": ";
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
