package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_WhitePeace2 extends EventOutcome {
   public String value;
   public String value2;

   public EventOutcome_WhitePeace2(String value, String value2) {
      this.value = value;
      this.value2 = value2;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int tID = Game.getCivID(this.value);
         int tID2 = Game.getCivID(this.value2);
         if (tID > 0 && tID2 > 0 && tID2 != tID && Game.getCiv(tID).getNumOfProvinces() > 0) {
            String warKey = WarManager.getWarKey(tID, tID2);
            if (warKey != null && warKey.length() > 0) {
               DiplomacyManager.whitePeace(warKey);
               if (iCivID == Game.player.iCivID) {
                  InGame_Info.iCivID = iCivID;
                  InGame_Info.iCivID2 = tID;
                  Game.menuManager.rebuildInGame_Info(Game.lang.get("WhitePeace"), Game.getCiv(tID).getCivName() + " - " + Game.getCiv(tID2).getCivName());
                  InGame_Info.imgID = Images.infoDiplomacy;
                  Game.menuManager.rebuildInGame_Wars();
               }
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("WhitePeace") + ": ";
   }

   @Override
   public String getStringRight() {
      int tID = Game.getCivID(this.value);
      int tID2 = Game.getCivID(this.value2);
      return tID > 0 && tID2 > 0 ? Game.getCiv(tID).getCivName() + " - " + Game.getCiv(tID2).getCivName() : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.peace;
   }
}
