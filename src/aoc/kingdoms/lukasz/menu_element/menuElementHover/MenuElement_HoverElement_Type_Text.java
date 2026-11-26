package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Text implements MenuElement_HoverElement_Type {
   private String sText;
   private int iTextWidth;
   private Color oColor;
   private int fontID = 0;

   public MenuElement_HoverElement_Type_Text(String sText) {
      this.init(sText, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_Text(String sText, int fontID) {
      this.init(sText, fontID, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_Text(String sText, Color nColor) {
      this.init(sText, CFG.FONT_BOLD_SMALL, nColor);
   }

   public MenuElement_HoverElement_Type_Text(String sText, int fontID, Color nColor) {
      this.init(sText, fontID, nColor);
   }

   public final void init(String sText, int nFontID, Color oColor) {
      this.sText = sText;
      this.oColor = oColor;
      this.fontID = nFontID;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX,
         nPosY + CFG.PADDING + CFG.PADDING / 2 + (int)((CFG.TEXT_HEIGHT - CFG.TEXT_HEIGHT * 0.9F) / 2.0F),
         new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING;
   }
}
