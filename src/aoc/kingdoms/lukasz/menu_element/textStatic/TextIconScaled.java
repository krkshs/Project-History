package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIconScaled extends Button {
   public int imageID;
   public int imgWidth;
   public int imgHeight;

   public TextIconScaled(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      if (ImageManager.getImage(imageID).getHeight() > this.getHeight() - this.getTextH() - CFG.PADDING * 2) {
         this.imgHeight = this.getHeight() - this.getTextH() - CFG.PADDING * 2;
         this.imgWidth = (int)(
            ImageManager.getImage(imageID).getWidth()
               * ((float)(this.getHeight() - this.getTextH() - CFG.PADDING * 2) / ImageManager.getImage(imageID).getHeight())
         );
      } else {
         this.imgWidth = ImageManager.getImage(imageID).getWidth();
         this.imgHeight = ImageManager.getImage(imageID).getHeight();
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
         this.getWidth(),
         this.getTextH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.imgWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - this.imgHeight / 2 + iTranslateY,
            this.imgWidth,
            this.imgHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - this.getTextHeight() + iTranslateY,
         this.getColor(isActive)
      );
   }

   public int getTextH() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 2;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }
}
