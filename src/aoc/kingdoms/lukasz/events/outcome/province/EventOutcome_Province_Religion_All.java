package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Religion_All extends EventOutcome {
   public int value;

   public EventOutcome_Province_Religion_All(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
            Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).setReligion(this.value);
            Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).updateAfterReligionConversion();
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
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
      return Game.lang.get("AllProvinces");
   }

   @Override
   public int getImage() {
      return Images.religion;
   }
}
