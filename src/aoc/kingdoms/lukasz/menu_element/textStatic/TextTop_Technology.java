package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextTop_Technology extends TextTop {
   public float lastValue = -997654.3F;
   public float lastValueResearch = -997654.3F;

   public TextTop_Technology(int imageID, String sText, String sText2, int iPosX, int iPosY) {
      super(imageID, sText, sText2, iPosX, iPosY);
   }

   @Override
   public String getTextToDraw() {
      if (Game.getCiv(Game.player.iCivID).getActiveTechResearch() < 0) {
         if (this.lastValue != -997652.3F) {
            this.setText("---");
            this.lastValue = 997652.3F;
         }
      } else if (this.lastValue != Game.getCiv(Game.player.iCivID).getResearchProgress(Game.getCiv(Game.player.iCivID).getActiveTechResearch())) {
         this.setText(
            ""
               + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getResearchProgress(Game.getCiv(Game.player.iCivID).getActiveTechResearch()) * 100.0F, 1)
               + "%"
         );
         this.lastValue = Game.getCiv(Game.player.iCivID).getResearchProgress(Game.getCiv(Game.player.iCivID).getActiveTechResearch());
      }

      if (this.lastValueResearch != Game.getCiv(Game.player.iCivID).fResearchPerMonth) {
         this.setText2(
            (Game.getCiv(Game.player.iCivID).fResearchPerMonth > 0.0F ? "+" : "")
               + CFG.getPrecision2(
                  Game.getCiv(Game.player.iCivID).fResearchPerMonth,
                  Game.getCiv(Game.player.iCivID).fResearchPerMonth < 100.0F ? (Game.getCiv(Game.player.iCivID).fResearchPerMonth < 10.0F ? 100 : 10) : 1
               )
         );
         this.lastValueResearch = Game.getCiv(Game.player.iCivID).fResearchPerMonth;
      }

      return super.getTextToDraw();
   }

   @Override
   public boolean getIsActiveButton() {
      return Game.menuManager.getVisibleInGame_TechnologyChoose();
   }
}
