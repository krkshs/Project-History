package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_KillRuler extends EventOutcome {
   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         RulersManager.deathOfRuler(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("DeathOfALeader") + ": ";
   }

   @Override
   public String getStringRight() {
      return Game.getCiv(Game.player.iCivID).ruler.Name;
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
