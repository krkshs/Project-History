package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonPlay extends ButtonMain {
   public boolean flipX;

   public ButtonPlay(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, boolean flipX) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, ImageManager.getImage(Images.buttonPlay).getWidth(), true);
      this.flipX = flipX;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (!this.getIsHovered() && !isActive) {
         ImageManager.getImage(Images.buttonPlay)
            .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), this.flipX, false);
      } else {
         ImageManager.getImage(Images.buttonPlay2)
            .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), this.flipX, false);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (isActive) {
         Renderer.drawText(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX()
               + (this.flipX ? 0 : Images.buttonPlayPADDING)
               + (this.getWidth() - Images.buttonPlayPADDING) / 2
               - this.getTextWidth() / 2
               + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
            this.getColor(isActive)
         );
      } else {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX()
               + (this.flipX ? 0 : Images.buttonPlayPADDING)
               + (this.getWidth() - Images.buttonPlayPADDING) / 2
               - this.getTextWidth() / 2
               + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats(isActive, this.getIsHovered());
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(Images.buttonPlay).getHeight();
   }
}
