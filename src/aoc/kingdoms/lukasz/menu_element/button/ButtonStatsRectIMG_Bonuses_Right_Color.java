package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;

public class ButtonStatsRectIMG_Bonuses_Right_Color extends ButtonStatsRectIMG_Bonuses_Right {
   public Color bonusColors;

   public ButtonStatsRectIMG_Bonuses_Right_Color(String sText, String sText2, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.sText_Orginal = sText;
      this.fontID2 = CFG.FONT_BOLD_SMALL;
      this.imageID = imageID;
      this.maxIconWidth = maxIconWidth;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.sText2 = sText2;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(this.fontID2), sText2);
      this.iTextBonusW = (int)glyphLayout.width;
      int tWMax = 0;

      while (this.iTextWidth >= this.getWidth() - (maxIconWidth + CFG.PADDING * 6 + this.iTextBonusW) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }

      this.bonusColors = Colors.HOVER_POSITIVE;
   }

   public ButtonStatsRectIMG_Bonuses_Right_Color(
      String sText, String sText2, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, Color nColor
   ) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.sText_Orginal = sText;
      this.fontID2 = CFG.FONT_BOLD_SMALL;
      this.imageID = imageID;
      this.maxIconWidth = maxIconWidth;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.sText2 = sText2;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(this.fontID2), sText2);
      this.iTextBonusW = (int)glyphLayout.width;
      int tWMax = 0;

      while (this.iTextWidth >= this.getWidth() - (maxIconWidth + CFG.PADDING * 6 + this.iTextBonusW) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }

      this.bonusColors = nColor;
   }

   @Override
   public Color getColorBonus() {
      return this.bonusColors;
   }
}
