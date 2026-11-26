package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Religion_ID extends EventOutcome {
   public int provID;
   public int value;

   public EventOutcome_Province_Religion_ID(int provID, int value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(this.provID).setReligion(this.value);
         Game.getProvince(this.provID).updateAfterReligionConversion();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Religion") + ": ";
   }

   @Override
   public String getStringRight() {
      try {
         return Game.religionManager.getReligion(this.value).Name;
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return "";
      }
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      try {
         return Game.getProvince(this.provID).getProvinceName();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return null;
      }
   }

   @Override
   public int getImage() {
      return Images.religion;
   }
}
