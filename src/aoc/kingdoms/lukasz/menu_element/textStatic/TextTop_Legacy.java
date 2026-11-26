package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextTop_Legacy extends TextTop {
   public float lastValuePerMonth = -997654.3F;

   public TextTop_Legacy(int imageID, String sText, String sText2, int iPosX, int iPosY) {
      super(imageID, sText, sText2, iPosX, iPosY);
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getCiv(Game.player.iCivID).fLegacy) {
         if (Game.getCiv(Game.player.iCivID).fLegacy >= 10000.0F) {
            this.setText("" + CFG.getShortNumber((int)Math.floor(Game.getCiv(Game.player.iCivID).fLegacy)));
         } else {
            this.setText(
               CFG.getPrecision2(
                  Game.getCiv(Game.player.iCivID).fLegacy,
                  Game.getCiv(Game.player.iCivID).fLegacy < 100.0F ? (Game.getCiv(Game.player.iCivID).fLegacy < 10.0F ? 10 : 10) : 1
               )
            );
         }

         this.lastValue = Game.getCiv(Game.player.iCivID).fLegacy;
      }

      if (this.lastValuePerMonth != Game.getCiv(Game.player.iCivID).getLegacyPerMonth()) {
         this.lastValuePerMonth = Game.getCiv(Game.player.iCivID).getLegacyPerMonth();
         this.setText2(
            (this.lastValuePerMonth > 0.0F ? "+" : "")
               + CFG.getPrecision2(this.lastValuePerMonth, this.lastValuePerMonth < 100.0F ? (this.lastValuePerMonth < 10.0F ? 100 : 10) : 1)
         );
      }

      return super.getTextToDraw();
   }
}
