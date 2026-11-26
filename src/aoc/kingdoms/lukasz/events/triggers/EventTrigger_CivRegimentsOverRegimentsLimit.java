package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivRegimentsOverRegimentsLimit extends EventTrigger_Value {
   public boolean value;

   public EventTrigger_CivRegimentsOverRegimentsLimit(boolean value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return this.value
         ? Game.getCiv(iCivID).getArmyRegimentSize() >= Game.getCiv(iCivID).iRegimentsLimit
         : Game.getCiv(iCivID).getArmyRegimentSize() < Game.getCiv(iCivID).iRegimentsLimit;
   }

   @Override
   public String getText() {
      return Game.lang.get("NumberOfRegiments") + (this.value ? " >= " : " < ");
   }

   @Override
   public String getText2() {
      return Game.lang.get("RegimentsLimit");
   }

   @Override
   public String getText3() {
      return " ["
         + Game.lang.get("Currently")
         + ": "
         + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).getArmyRegimentSize(), 100)
         + " | "
         + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).iRegimentsLimit, 100)
         + "]";
   }

   @Override
   public int getImage() {
      return Images.regimentsLimit;
   }
}
