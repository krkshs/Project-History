package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;

public class ButtonStatsRectIMG_Bonuses2 extends ButtonStatsRectIMG_Bonuses {
   public ButtonStatsRectIMG_Bonuses2(String sText, String sText2, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.maxIconWidth = maxIconWidth;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD_SMALL), sText2);
      this.iTextBonusW = (int)Renderer.glyphLayout.width;
   }
}
