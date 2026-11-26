package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivInnovationAdvisorSkillOver extends EventTrigger_Value {
   public int value;

   public EventTrigger_CivInnovationAdvisorSkillOver(int value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game.getCiv(iCivID).advisorTechnology.sName == null ? false : Game.getCiv(iCivID).advisorTechnology.iLevel > this.value;
   }

   @Override
   public String getText() {
      return Game.lang.get(GameValues.court.ADVISOR_NAME_INNOVATION) + ", " + Game.lang.get("AdvisorSkillLevel") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getText3() {
      return Game.getCiv(Game.player.iCivID).advisorTechnology.sName == null
         ? " [" + Game.lang.get("Currently") + ": " + Game.lang.get("NoAdvisor") + "]"
         : " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).advisorTechnology.iLevel, 1) + "]";
   }

   @Override
   public int getImage() {
      return Images.skill;
   }
}
