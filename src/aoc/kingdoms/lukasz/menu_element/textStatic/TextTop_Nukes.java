package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextTop_Nukes extends TextTop {
   public int lastValuePerMonth = -997654;

   public TextTop_Nukes(int imageID, String sText, String sText2, int iPosX, int iPosY) {
      super(imageID, sText, sText2, iPosX, iPosY);
      this.setText2("---");
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValuePerMonth != Game.getCiv(Game.player.iCivID).getNukes()) {
         this.setText(CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).getNukes(), 1));
         this.lastValuePerMonth = Game.getCiv(Game.player.iCivID).getNukes();
      }

      if (Game.getCiv(Game.player.iCivID).iNukesSize > 0) {
         try {
            if (this.lastValue
               != (float)Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).daysLeft / Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).investTime) {
               this.setText2(
                  CFG.getPrecision2(
                        (
                              1.0F
                                 - (float)Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).daysLeft
                                    / Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).investTime
                           )
                           * 100.0F,
                        1
                     )
                     + "%"
               );
               this.lastValue = (float)Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).daysLeft
                  / Game.getCiv(Game.player.iCivID).nukesDaysLeft.get(0).investTime;
            }
         } catch (Exception var2) {
            CFG.exceptionStack(var2);
         }
      } else if (this.lastValue != 97948.25F) {
         this.setText2("---");
         this.lastValue = 97948.25F;
      }

      return super.getTextToDraw();
   }

   @Override
   public boolean getIsActiveButton() {
      return Game.menuManager.getVisibleInGame_Nukes();
   }
}
