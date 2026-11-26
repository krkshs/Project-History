package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_ProvinceIsUnderSiege extends EventTrigger_Value {
   public int provID;

   public EventTrigger_ProvinceIsUnderSiege(int provID) {
      this.provID = provID;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return this.provID >= 0 ? Game.getProvinceData4(this.provID).isUnderSiege() : false;
   }

   @Override
   public String getText() {
      return Game.getProvince(this.provID).getProvinceName() + ", " + Game.lang.get("UnderSiege");
   }

   @Override
   public String getText2() {
      return "";
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_FORT_DEFENSE;
   }
}
