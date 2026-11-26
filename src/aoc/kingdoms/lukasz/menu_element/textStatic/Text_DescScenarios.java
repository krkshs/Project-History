package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_DescScenarios extends Text_Desc {
   public Text_DescScenarios(String sText, int iPosX, int iPosY, int iWidth) {
      super(sText, iPosX, iPosY, iWidth);
      this.typeOfElement = MenuElement_Type.TRANSPARENT_BACKGROUND;
   }

   public Text_DescScenarios(String sText, int iPosX, int iPosY, int iWidth, int fontID) {
      super(sText, iPosX, iPosY, iWidth, fontID);
      this.typeOfElement = MenuElement_Type.TRANSPARENT_BACKGROUND;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sLines.get(i),
            this.getPosX() + this.getPadding() + iTranslateX,
            this.getPosY() + this.getPaddingY() + (this.iTextHeight + CFG.PADDING * 2) * i + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   public void setText(String sText) {
      this.updateTextPosition();
      this.sLines.clear();
      this.iLineSize = 0;
      this.iTextWidth = 0;
      String[] words = sText.split(" ");
      int textPosX = 0;
      int maxW = this.getWidth() - this.getPadding() * 2;
      String currentLine = "";
      int i = 0;

      for (int iSize = words.length; i < iSize; i++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), words[i] + " ");
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         textPosX += this.iTextWidth;
         if (textPosX < maxW) {
            currentLine = currentLine + words[i] + " ";
         } else {
            this.sLines.add(currentLine);
            currentLine = words[i] + " ";
            textPosX = this.iTextWidth;
         }
      }

      if (currentLine.length() > 0) {
         this.sLines.add(currentLine);
      }

      if (this.sLines.size() > 0 && this.sLines.get(0).length() > 0) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(0));
         this.iTextHeight = (int)Renderer.glyphLayout.height;
      }

      this.iLineSize = this.sLines.size();
      this.setHeight(this.iTextHeight * this.sLines.size() + (this.sLines.size() - 1) * CFG.PADDING * 2 + this.getPaddingY() * 2);
   }
}
