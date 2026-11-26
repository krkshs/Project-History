package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_ChangeIdeology extends EventOutcome {
   public int value;

   public EventOutcome_ChangeIdeology(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value >= 0
            && this.value < Game.ideologiesManager.getIdeologiesSize()
            && Game.ideologiesManager.changeGovernmentType(iCivID, this.value, true)) {
            Civilization var10000 = Game.getCiv(iCivID);
            var10000.fGold = var10000.fGold + GameValues.government.CHANGE_GOVERNMENT_COST;
            var10000 = Game.getCiv(iCivID);
            var10000.fLegacy = var10000.fLegacy + GameValues.government.CHANGE_GOVERNMENT_COST_LEGACY;
            if (iCivID == Game.player.iCivID) {
               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = 0;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("Government"), Game.ideologiesManager.getIdeology(this.value).Name);
               InGame_Info.imgID = Images.infoCrown;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("ChangeIdeology") + ": ";
   }

   @Override
   public String getStringRight() {
      return this.value >= 0 && this.value < Game.ideologiesManager.getIdeologiesSize()
         ? Game.ideologiesManager.getIdeology(this.value).Name
         : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
