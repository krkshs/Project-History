package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;

public class EventOutcome_DeclareWar extends EventOutcome {
   public String value;

   public EventOutcome_DeclareWar(String value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int tID = Game.getCivID(this.value);
         if (tID > 0 && iCivID != tID && Game.getCiv(tID).getNumOfProvinces() > 0) {
            DiplomacyManager.declareWar(iCivID, tID, true, new ArrayList<>());
            if (iCivID == Game.player.iCivID) {
               InGame_Info.iCivID = iCivID;
               InGame_Info.iCivID2 = tID;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("War"), Game.getCiv(Game.player.iCivID).getCivName() + " - " + Game.getCiv(tID).getCivName());
               InGame_Info.imgID = Images.infoWar;
               Game.soundsManager.loadNextMusicWar();
               Game.menuManager.rebuildInGame_Wars();
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("DeclareWarOn") + ": ";
   }

   @Override
   public String getStringRight() {
      int tID = Game.getCivID(this.value);
      return tID > 0 ? Game.getCiv(tID).getCivName() : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.war;
   }
}
