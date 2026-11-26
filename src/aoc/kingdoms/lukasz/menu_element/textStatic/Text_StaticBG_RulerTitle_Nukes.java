package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class Text_StaticBG_RulerTitle_Nukes extends Text_StaticBG_RulerTitle {
   public int lastValue = -975124;

   public Text_StaticBG_RulerTitle_Nukes(String sText, int iPosX, int iPosY, int iWidth, int iHeight) {
      super(sText, iPosX, iPosY, iWidth, iHeight);
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getCiv(Game.player.iCivID).getNukes()) {
         this.setText(Game.lang.get("AtomicBombs") + ": " + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).getNukes(), 1));
         this.lastValue = Game.getCiv(Game.player.iCivID).getNukes();
      }

      return super.getTextToDraw();
   }
}
