package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon extends Button {
   public boolean row = true;
   public int imageID;
   public static int maxWidth = 1;
   public int iconWidth;
   public int iconHeight;

   public TextIcon(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int i) {
      this.init(sText, CFG.FONT_BOLD_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      if (this.iconWidth > maxWidth) {
         maxWidth = this.iconWidth;
      }

      this.row = i % 2 == 0;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.row) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.5F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      } else {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE2.r, Colors.COLOR_GRADIENT_OVER_BLUE2.g, Colors.COLOR_GRADIENT_OVER_BLUE2.b, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE2.r, Colors.COLOR_GRADIENT_OVER_BLUE2.g, Colors.COLOR_GRADIENT_OVER_BLUE2.b, 0.75F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.14F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 4 + maxWidth, this.getHeight());
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 4 + maxWidth, this.getHeight());
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 4 + maxWidth, this.getHeight(), true, false);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 2 + (maxWidth - this.iconWidth) / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      if (isActive) {
         Renderer.drawText(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX() + CFG.PADDING * 6 + maxWidth + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
            this.getColor(isActive)
         );
      } else {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX() + CFG.PADDING * 6 + maxWidth + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   public int getInnerWidth() {
      return this.getTextWidth() + CFG.PADDING + this.iconWidth;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }
}
