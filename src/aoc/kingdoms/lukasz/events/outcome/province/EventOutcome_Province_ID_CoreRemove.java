package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_ID_CoreRemove extends EventOutcome {
   public int provID;
   public String civA;

   public EventOutcome_Province_ID_CoreRemove(int provID, String civA) {
      this.provID = provID;
      this.civA = civA;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int idA = Game.getCivID(this.civA);
         if (idA > 0) {
            Game.getProvince(this.provID).removeCore(idA);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Remove") + ", " + Game.lang.get("Core") + ": ";
   }

   @Override
   public String getStringRight() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getCivName() : " -- ";
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
      return Images.core;
   }
}
