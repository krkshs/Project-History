package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Unrest_All extends EventOutcome {
   public float value;

   public EventOutcome_Province_Unrest_All(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
            Game.getProvince(Game.getCiv(iCivID).getProvinceID(i))
               .setRevulutionaryRisk(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getRevulutionaryRisk() + this.value);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Unrest") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 100);
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      return Game.lang.get("AllProvinces");
   }

   @Override
   public int getImage() {
      return Images.revolutionRisk;
   }
}
