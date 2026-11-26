package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_RandomChance extends EventTrigger_Value {
   public float value;

   public EventTrigger_RandomChance(float value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game.oR.nextInt(1000000) < this.value * 10000.0F;
   }

   @Override
   public String getText() {
      return Game.lang.get("Random") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 10) + "%";
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.dice;
   }
}
