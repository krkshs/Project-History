package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextTop_Diplomacy extends TextTop {
   public float lastValuePerMonth = -997654.3F;

   public TextTop_Diplomacy(int imageID, String sText, String sText2, int iPosX, int iPosY) {
      super(imageID, sText, sText2, iPosX, iPosY);
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getCiv(Game.player.iCivID).fDiplomacy) {
         if (Game.getCiv(Game.player.iCivID).fDiplomacy >= 10000.0F) {
            this.setText("" + CFG.getShortNumber((int)Math.floor(Game.getCiv(Game.player.iCivID).fDiplomacy)));
         } else {
            this.setText(
               CFG.getPrecision2(
                  Game.getCiv(Game.player.iCivID).fDiplomacy,
                  Game.getCiv(Game.player.iCivID).fDiplomacy < 100.0F ? (Game.getCiv(Game.player.iCivID).fDiplomacy < 10.0F ? 10 : 10) : 1
               )
            );
         }

         this.lastValue = Game.getCiv(Game.player.iCivID).fDiplomacy;
      }

      if (this.lastValuePerMonth != Game.getCiv(Game.player.iCivID).getDiplomacyPerMonth()) {
         this.lastValuePerMonth = Game.getCiv(Game.player.iCivID).getDiplomacyPerMonth();
         this.setText2(
            (this.lastValuePerMonth > 0.0F ? "+" : "")
               + CFG.getPrecision2(this.lastValuePerMonth, this.lastValuePerMonth < 100.0F ? (this.lastValuePerMonth < 10.0F ? 100 : 10) : 1)
         );
      }

      return super.getTextToDraw();
   }

   @Override
   public boolean getIsActiveButton() {
      return Game.menuManager.getVisibleInGame_Civ();
   }
}
