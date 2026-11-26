package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Space implements MenuElement_HoverElement_Type {
   private static final String SPACE_TEXT = "-----";
   private int iTextWidth;

   public MenuElement_HoverElement_Type_Space() {
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD_SMALL), "-----");
      this.iTextWidth = (int)Renderer.glyphLayout.width;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      Renderer.drawText(
         oSB,
         CFG.FONT_BOLD_SMALL,
         "-----",
         nPosX,
         nPosY + CFG.PADDING + (int)((CFG.TEXT_HEIGHT - CFG.TEXT_HEIGHT * 0.9F) / 2.0F),
         new Color(Colors.HOVER_RIGHT2.r, Colors.HOVER_RIGHT2.g, Colors.HOVER_RIGHT2.b, nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT;
   }
}
