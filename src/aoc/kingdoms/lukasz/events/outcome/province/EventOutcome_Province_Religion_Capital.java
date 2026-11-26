package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Religion_Capital extends EventOutcome {
   public int value;

   public EventOutcome_Province_Religion_Capital(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).setReligion(this.value);
         Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).updateAfterReligionConversion();
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
      try {
         return Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getProvinceName();
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
