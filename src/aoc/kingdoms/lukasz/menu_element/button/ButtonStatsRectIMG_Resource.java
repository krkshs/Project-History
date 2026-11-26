package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsRectIMG_Resource extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int maxIconWidth;
   public int id;

   public ButtonStatsRectIMG_Resource(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, int id) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.id = id;
      this.maxIconWidth = maxIconWidth;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ResourcesManager.resourceImages.get(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ResourcesManager.resourceImages.get(imageID).getHeight() * iconScale);
      int tWMax = 0;

      while (
         this.iTextWidth >= this.getWidth() - (this.iTextPositionX > 0 ? this.iTextPositionX : 0) - (maxIconWidth + CFG.PADDING * 2)
            && this.getText().length() > 5
      ) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
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
            Colors.COLOR_GRADIENT_OVER_BLUE.r,
            Colors.COLOR_GRADIENT_OVER_BLUE.g,
            Colors.COLOR_GRADIENT_OVER_BLUE.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive) / 2.0F
         )
      );
      Renderer.drawBox(
         oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight(), 1.0F
      );
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ResourcesManager.resourceImages
         .get(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - this.iconWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX()
            + CFG.PADDING * 2
            + this.maxIconWidth
            + (this.getWidth() - (CFG.PADDING * 2 + this.maxIconWidth)) / 2
            - this.getTextWidth() / 2
            + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ResourcesManager.resourceImages.get(iImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public int getCurrent() {
      return this.id;
   }

   @Override
   public int getValue1() {
      return this.imageID;
   }
}
