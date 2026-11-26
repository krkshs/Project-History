package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_MoveCapital extends EventOutcome {
   public int value;

   public EventOutcome_MoveCapital(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value >= 0 && this.value < Game.getProvincesSize() && Game.getProvince(this.value).getCivID() == iCivID) {
            Game.getCiv(iCivID).setCapitalProvinceID(this.value);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("MoveCapitalTo") + ": ";
   }

   @Override
   public String getStringRight() {
      return this.value >= 0 && this.value < Game.getProvincesSize() && Game.getProvince(this.value).getCivID() == Game.player.iCivID
         ? Game.getProvince(this.value).getProvinceName()
         : Game.lang.get("MaintainStatusQuo");
   }

   @Override
   public int getImage() {
      return Images.capital;
   }
}
