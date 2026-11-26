package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG_ID_SpecialColor extends Text_StaticBG_ID_Special {
   public Color textColor;
   public String sText2;
   public int iTextWidth2;

   public Text_StaticBG_ID_SpecialColor(
      String sText, String sText2, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id, Color nColor
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight, id);
      this.textColor = nColor;
      this.id = id;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.textColor;
   }

   @Override
   public void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() - this.iTextWidth2 - this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }
}
