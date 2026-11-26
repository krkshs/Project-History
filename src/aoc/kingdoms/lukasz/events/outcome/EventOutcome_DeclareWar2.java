package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;

public class EventOutcome_DeclareWar2 extends EventOutcome {
   public String byCivTAG;
   public String onCivTag;

   public EventOutcome_DeclareWar2(String byCivTAG, String onCivTag) {
      this.byCivTAG = byCivTAG;
      this.onCivTag = onCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int byCivID = Game.getCivID(this.byCivTAG);
         int onCivID = Game.getCivID(this.onCivTag);
         if (byCivID > 0 && onCivID > 0 && byCivID != onCivID && Game.getCiv(byCivID).getNumOfProvinces() > 0 && Game.getCiv(onCivID).getNumOfProvinces() > 0) {
            DiplomacyManager.declareWar(byCivID, onCivID, true, new ArrayList<>());
            if (onCivID == Game.player.iCivID) {
               InGame_Info.iCivID = onCivID;
               InGame_Info.iCivID2 = byCivID;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("War"), Game.getCiv(byCivID).getCivName() + " - " + Game.getCiv(onCivID).getCivName());
               InGame_Info.imgID = Images.infoWar;
               Game.soundsManager.loadNextMusicWar();
               Game.menuManager.rebuildInGame_Wars();
            } else if (byCivID == Game.player.iCivID) {
               InGame_Info.iCivID = byCivID;
               InGame_Info.iCivID2 = onCivID;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("War"), Game.getCiv(byCivID).getCivName() + " - " + Game.getCiv(onCivID).getCivName());
               InGame_Info.imgID = Images.infoWar;
               Game.soundsManager.loadNextMusicWar();
               Game.menuManager.rebuildInGame_Wars();
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   @Override
   public String getStringLeft() {
      int tID = Game.getCivID(this.byCivTAG);
      return tID > 0 ? Game.getCiv(tID).getCivName() + ": " + Game.lang.get("DeclareWarOn") + ": " : Game.lang.get("DeclareWarOn") + ": ";
   }

   @Override
   public String getStringRight() {
      int tID = Game.getCivID(this.onCivTag);
      return tID > 0 ? Game.getCiv(tID).getCivName() : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.war;
   }
}
