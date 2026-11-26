package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Vassalize extends EventOutcome {
   public String lordCivTag;
   public String vassalCivTag;

   public EventOutcome_Vassalize(String lordCivTag, String vassalCivTag) {
      this.lordCivTag = lordCivTag;
      this.vassalCivTag = vassalCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int lordCivID = Game.getCivID(this.lordCivTag);
         int vassalCivID = Game.getCivID(this.vassalCivTag);
         if (lordCivID > 0 && vassalCivID > 0) {
            Game.getCiv(vassalCivID).setPuppetOfCivID(lordCivID);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   @Override
   public String getStringLeft() {
      int lordCivID = Game.getCivID(this.lordCivTag);
      return lordCivID > 0 ? Game.getCiv(lordCivID).getCivName() + ": " : "";
   }

   @Override
   public String getStringRight() {
      int vassalCivID = Game.getCivID(this.vassalCivTag);
      return Game.lang.get(Game_Ages.getVassal()) + (vassalCivID > 0 ? ": " + Game.getCiv(vassalCivID).getCivName() : "?");
   }

   @Override
   public int getImage() {
      return Images.vassal;
   }
}
