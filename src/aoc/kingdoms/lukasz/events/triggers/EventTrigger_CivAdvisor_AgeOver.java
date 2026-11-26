package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivAdvisor_AgeOver extends EventTrigger_Value {
   public int value;
   public int advisorType;

   public EventTrigger_CivAdvisor_AgeOver(int advisorType, int value) {
      this.advisorType = advisorType;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return AdvisorManager.getAdvisor(iCivID, this.advisorType).sName == null
         ? false
         : Game_Calendar.currentYear - AdvisorManager.getAdvisor(iCivID, this.advisorType).iYearOfBirth > this.value;
   }

   @Override
   public String getText() {
      return AdvisorManager.getAdvisorGroupName(this.advisorType) + ", " + Game.lang.get("Age") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getText3() {
      return AdvisorManager.getAdvisor(Game.player.iCivID, this.advisorType).sName == null
         ? " [" + Game.lang.get("Currently") + ": " + Game.lang.get("NoAdvisor") + "]"
         : " ["
            + Game.lang.get("Currently")
            + ": "
            + Game.lang.get("XYearsOld", Game_Calendar.currentYear - AdvisorManager.getAdvisor(Game.player.iCivID, this.advisorType).iYearOfBirth)
            + "]";
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
