package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Title_v2_TextLR extends Text_Title_v2 {
   public String sTextRight;
   public int iTextRightWidth;

   public Text_Title_v2_TextLR(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, String sTextRight) {
      super(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.sTextRight = sTextRight;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sTextRight);
      this.iTextRightWidth = (int)Renderer.glyphLayout.width;
   }

   public Text_Title_v2_TextLR(String sText, String sTextRight, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      super(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.sTextRight = sTextRight;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sTextRight);
      this.iTextRightWidth = (int)Renderer.glyphLayout.width;
   }

   public Text_Title_v2_TextLR(String sText, int nFontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, String sTextRight) {
      super(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.fontID = nFontID;
      this.sTextRight = sTextRight;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sTextRight);
      this.iTextRightWidth = (int)Renderer.glyphLayout.width;
   }

   @Override
   public void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sTextRight,
         this.getPosX() + this.getWidth() - this.textPosition.getTextPosition() - this.iTextRightWidth + iTranslateX,
         this.getPosY() + (this.getHeight() - this.getTextHeight()) / 2 + iTranslateY,
         this.getColor2(isActive)
      );
      super.drawText(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
   }

   protected Color getColor2(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }
}
