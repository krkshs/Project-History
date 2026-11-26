package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_Flag_TextRight extends MenuTitleIMG_Flag {
   public String sRight;
   public int iTextRightWidth;

   public MenuTitleIMG_Flag_TextRight(String sText, String sText2, boolean moveable, boolean resizable, int imageID, String sRight) {
      super(sText, sText2, moveable, resizable, imageID);
      this.sRight = sRight;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sRight);
      this.iTextRightWidth = (int)glyphLayout.width;
   }

   @Override
   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         nPosX + nWidth - CFG.PADDING * 3 - Images.boxTitleBORDERWIDTH - this.iTextRightWidth,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING - CFG.PADDING,
         this.iTextRightWidth + CFG.PADDING * 2,
         CFG.TEXT_HEIGHT + CFG.PADDING * 2,
         1.0F
      );
      super.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sRight,
         nPosX + nWidth - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH - this.iTextRightWidth,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
   }
}
