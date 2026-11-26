package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_CivHasMoreTechsThanCiv extends EventTrigger_Value {
   public String civA;
   public String civB;

   public EventTrigger_CivHasMoreTechsThanCiv(String civA, String civB) {
      this.civA = civA;
      this.civB = civB;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      int idB = Game.getCivID(this.civB);
      return idA > 0 && idB > 0 ? Game.getCiv(idA).getResearchedTechnologies() > Game.getCiv(idB).getResearchedTechnologies() : false;
   }

   @Override
   public String getText() {
      return Game.lang.get("UnlockedTechnologies") + ": ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.getCiv(this.civA) + " > " + Game.lang.getCiv(this.civB);
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_TECHNOLOGY;
   }
}
