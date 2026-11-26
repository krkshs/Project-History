package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_IsNotAtWar extends EventTrigger_Value {
   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return !Game.getCiv(iCivID).diplomacy.isAtWar();
   }

   @Override
   public String getText() {
      return Game.lang.get("IsNotAtWar");
   }

   @Override
   public String getText2() {
      return "";
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.war;
   }
}
