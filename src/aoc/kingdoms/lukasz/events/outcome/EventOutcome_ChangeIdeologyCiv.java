package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_ChangeIdeologyCiv extends EventOutcome {
   public int value;
   public String civA;

   public EventOutcome_ChangeIdeologyCiv(int value, String civA) {
      this.value = value;
      this.civA = civA;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value >= 0 && this.value < Game.ideologiesManager.getIdeologiesSize()) {
            int idA = Game.getCivID(this.civA);
            if (idA > 0 && Game.ideologiesManager.changeGovernmentType(idA, this.value, true)) {
               Civilization var10000 = Game.getCiv(idA);
               var10000.fGold = var10000.fGold + GameValues.government.CHANGE_GOVERNMENT_COST;
               var10000 = Game.getCiv(idA);
               var10000.fLegacy = var10000.fLegacy + GameValues.government.CHANGE_GOVERNMENT_COST_LEGACY;
               if (idA == Game.player.iCivID) {
                  InGame_Info.iCivID = Game.player.iCivID;
                  InGame_Info.iCivID2 = 0;
                  Game.menuManager.rebuildInGame_Info(Game.lang.get("Government"), Game.ideologiesManager.getIdeology(this.value).Name);
                  InGame_Info.imgID = Images.infoCrown;
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getCivName() + ", " + Game.lang.get("ChangeIdeology") + ": " : " -- ";
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
