package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_Notification_Flag extends Button_Notification {
   public int civID;

   public Button_Notification_Flag(
      String sText, String sText2, int nPosX, int nPosY, int nWidth, int nHeight, int imageID, int notificationID, long lTime, int civID
   ) {
      super(sText, sText2, nPosX, nPosY, nWidth, nHeight, imageID, notificationID, lTime);
      this.civID = civID;
   }

   @Override
   public void updateTextWidth() {
      int tWMax = 0;

      while (
         this.iTextWidth > this.getWidth() - this.iTextWidth2 - CFG.PADDING * 8 - ImageManager.getImage(Images.flagRect2).getWidth() - this.iconWidth
            && this.getText().length() > 5
      ) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.drawIcon(oSB, iTranslateX, iTranslateY, isActive);
      oSB.setShader(Renderer.shaderAlpha);
      if (this.civID >= 0) {
         Game.getCiv(this.civID).getFlag().getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagRect2Mask)
         .draw(
            oSB,
            this.getPosX() + this.iconWidth + CFG.PADDING * 4 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagRect2)
         .draw(
            oSB,
            this.getPosX() + this.iconWidth + CFG.PADDING * 4 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.iconWidth + CFG.PADDING * 6 + ImageManager.getImage(Images.flagRect2).getWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor2(this.getColor(isActive))
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iTextWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor2(this.getColor(isActive))
      );
   }
}
