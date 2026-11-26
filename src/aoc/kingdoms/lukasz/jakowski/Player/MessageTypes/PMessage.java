package aoc.kingdoms.lukasz.jakowski.Player.MessageTypes;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.textures.Images;

public class PMessage {
   public String key = CFG.extraRandomTag();
   public int fromCivID;
   public int expiresTurnID;
   public int iValue1;
   public long time;

   public PMessage(int iFromCivID, int iExpiresTurnID, int iValue1) {
      this.fromCivID = iFromCivID;
      this.expiresTurnID = iExpiresTurnID;
      this.iValue1 = iValue1;
      this.time = CFG.currentTimeMillis;
   }

   public void actionClick() {
   }

   public void onAccept() {
   }

   public void onRefuse() {
   }

   public MenuElement_Hover buildElementHover() {
      return null;
   }

   public int getImageID() {
      return Images.diplomacy;
   }
}
