package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_MainMenuIcon extends Button {
   public int imgID;

   public Button_MainMenuIcon(int imgID, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.imgID = imgID;
      this.init("", this.fontID, this.iTextPositionX, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (isActive) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
      } else if (this.getIsHovered()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.035F));
      }

      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), true, false);
      if (isActive) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
      } else if (this.getIsHovered()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
      }

      ImageManager.getImage(this.imgID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imgID).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imgID).getHeight() / 2 + iTranslateY
         );
      oSB.setColor(Color.WHITE);
   }
}
