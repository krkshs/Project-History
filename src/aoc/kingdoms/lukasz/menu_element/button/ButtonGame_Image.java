package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame_Image extends ButtonGame {
   public int imageID = 0;
   public int iconWidth;
   public int iconHeight;

   public ButtonGame_Image(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int imageID) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.25F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
   }

   public ButtonGame_Image(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable, int imageID) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.25F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + CFG.PADDING * 2 + this.iconWidth) / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + CFG.PADDING * 2 + this.iconWidth) / 2 + CFG.PADDING * 2 + this.iconWidth + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
