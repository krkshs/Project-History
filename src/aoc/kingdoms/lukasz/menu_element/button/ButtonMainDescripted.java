package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonMainDescripted extends ButtonMain {
   private String sDesc;

   public ButtonMainDescripted(String sText, String sDesc, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
      this.sDesc = sDesc;
   }

   public ButtonMainDescripted(
      String sText, String sDesc, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable, checkBox);
      this.sDesc = sDesc;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.getText(),
         this.getPosX() + this.getTextPos() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - CFG.TEXT_HEIGHT + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sDesc,
         this.getPosX() + this.getTextPos() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 + CFG.PADDING + iTranslateY,
         Colors.HOVER_RIGHT
      );
   }
}
