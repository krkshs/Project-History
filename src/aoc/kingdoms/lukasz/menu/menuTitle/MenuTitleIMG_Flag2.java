package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.Status;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_Flag2 extends MenuTitleIMG_Flag {
   public String sText3;
   public int iTextWidth3;

   public MenuTitleIMG_Flag2(String sText, String sText2, String sText3, boolean moveable, boolean resizable, int imageID) {
      super(sText, sText2, moveable, resizable, imageID);
      this.sText3 = sText3;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sText3);
      this.iTextWidth3 = (int)glyphLayout.width;
   }

   @Override
   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         nPosX + this.flagWidth + CFG.PADDING,
         1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
         this.getColorText(titleStatus)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText2,
         nPosX + this.flagWidth + CFG.PADDING,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText3,
         nPosX + nWidth - this.iTextWidth3 - CFG.PADDING,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
   }
}
