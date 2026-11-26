package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.Status;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_DoubleText extends MenuTitleIMG {
   public String sText2;
   public int iText2Width = 0;

   public MenuTitleIMG_DoubleText(String sText, String sText2, boolean moveable, boolean resizable, int imageID) {
      super(sText, moveable, resizable, imageID);
      this.fontID = CFG.FONT_BOLD;
      this.sText2 = sText2;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sText2);
      this.iText2Width = (int)glyphLayout.width;
   }

   @Override
   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         nPosX + nWidth / 2 - this.getTextWidth() / 2,
         1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
         this.getColorText(titleStatus)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText2,
         nPosX + nWidth / 2 - this.iText2Width / 2,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
   }
}
