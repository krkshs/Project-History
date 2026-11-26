package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsBudget extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int maxIconWidth;
   public boolean row;

   public ButtonStatsBudget(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, boolean row) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.row = row;
      this.maxIconWidth = maxIconWidth;
      float iconScale = Math.min(1.0F, this.getImageScale(imageID) * 1.1F);
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      if (this.getWidth() < maxIconWidth + CFG.PADDING * 4 + this.getTextWidth()) {
         this.setWidth(maxIconWidth + CFG.PADDING * 4 + this.getTextWidth());
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      if (this.row) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
         Images.line_32_off1
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - 2 - Images.line_32_off1.getHeight() + iTranslateY,
               this.getWidth(),
               1,
               false,
               true
            );
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_TITLE_BLUE.r, Colors.COLOR_GRADIENT_TITLE_BLUE.g, Colors.COLOR_GRADIENT_TITLE_BLUE.b, 0.35F));
         Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
         Images.line_32_off1
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - 1 - Images.line_32_off1.getHeight() + iTranslateY,
               this.getWidth(),
               1,
               false,
               true
            );
      } else {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BLUE.r,
            Colors.COLOR_GRADIENT_OVER_BLUE.g,
            Colors.COLOR_GRADIENT_OVER_BLUE.b,
            this.getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(
         oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight(), 1.0F
      );
      oSB.setColor(Color.WHITE);
      if (isActive || this.getIsHovered()) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.325F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      ImageManager.getImage(Images.gradientXYVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight());
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight(), true, false);
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_BOX_GOLD.r, Colors.COLOR_BOX_GOLD.g, Colors.COLOR_BOX_GOLD.b, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 2 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            1,
            this.getHeight() / 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 3 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 3 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            1,
            this.getHeight() / 2
         );
      oSB.setColor(Color.WHITE);
   }

   public final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
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
         this.getPosX() + CFG.PADDING * 2 + this.maxIconWidth + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)(CFG.TEXT_HEIGHT + CFG.PADDING) / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }
}
