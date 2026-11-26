package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class EventOutcome_WhitePeace extends EventOutcome {
   public String value;

   public EventOutcome_WhitePeace(String value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int tID = Game.getCivID(this.value);
         if (tID > 0 && Game.getCiv(tID).getNumOfProvinces() > 0) {
            List<String> abc = new ArrayList<>();

            for (int i = 0; i < Game.getCiv(tID).diplomacy.lWars.size(); i++) {
               abc.add(Game.getCiv(tID).diplomacy.lWars.get(i));
            }

            if (!abc.isEmpty()) {
               for (int i = 0; i < abc.size(); i++) {
                  DiplomacyManager.whitePeace(abc.get(i));
               }

               if (iCivID == Game.player.iCivID) {
                  InGame_Info.iCivID = iCivID;
                  InGame_Info.iCivID2 = tID;
                  Game.menuManager.rebuildInGame_Info(Game.lang.get("WhitePeace"), Game_Calendar.getCurrentDate());
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
      return tID > 0 ? Game.getCiv(tID).getCivName() + ": " + Game.lang.get("AllCivilizations") : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.peace;
   }
}
