package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_TextTitle_BG_Clear implements MenuElement_HoverElement_Type {
   private String sText;
   private int iTextWidth;
   private int iTextHeight;
   private Color oColor;
   private int fontID = 0;

   public MenuElement_HoverElement_Type_TextTitle_BG_Clear(String sText) {
      this.init(sText, 0, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Clear(String sText, int fontID) {
      this.init(sText, fontID, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Clear(String sText, Color nColor) {
      this.init(sText, 0, nColor);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Clear(String sText, int fontID, Color nColor) {
      this.init(sText, fontID, nColor);
   }

   public final void init(String sText, int nFontID, Color oColor) {
      this.sText = sText;
      this.oColor = oColor;
      this.fontID = nFontID;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX,
         nPosY + this.getHeight() / 2 - this.iTextHeight / 2,
         new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 5;
   }

   public int getHeight2() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 3;
   }
}
