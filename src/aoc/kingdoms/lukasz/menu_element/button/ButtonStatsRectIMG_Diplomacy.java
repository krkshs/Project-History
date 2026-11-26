package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsRectIMG_Diplomacy extends ButtonStatsRectIMG_Active {
   public ButtonStatsRectIMG_Diplomacy(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, int id) {
      super(sText, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth + CFG.PADDING, id, CFG.FONT_REGULAR);
      this.iconWidth = ImageManager.getImage(imageID).getWidth();
      this.iconHeight = ImageManager.getImage(imageID).getHeight();
   }

   public ButtonStatsRectIMG_Diplomacy(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, int id, int fontID) {
      super(sText, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth + CFG.PADDING, id, fontID);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.35F : 0.65F
         )
      );
      Renderer.drawBox(
         oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight(), 1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.25F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.15F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + (this.maxIconWidth + CFG.PADDING * 2) / 2 - this.iconWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX()
            + this.maxIconWidth
            + CFG.PADDING * 2
            + (this.getWidth() - (CFG.PADDING * 2 + this.maxIconWidth)) / 2
            - this.getTextWidth() / 2
            + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT : Colors.BUTTON_TEXT_DISABLED;
      }
   }
}
