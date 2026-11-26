package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonMainTitle2 extends Button {
   public long lTime = 0L;
   public int ANIMATION_TIME = 1500;

   public ButtonMainTitle2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, boolean isClickable) {
      this.init(
         sText,
         fontID,
         iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.logo2).getWidth(),
         ImageManager.getImage(Images.logo2).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.lTime = System.currentTimeMillis();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, !this.getIsHovered() && !isActive ? 1.0F : 0.9F));
      if (this.lTime + this.ANIMATION_TIME > System.currentTimeMillis()) {
         float t = (float)(System.currentTimeMillis() - this.lTime) / this.ANIMATION_TIME;
         if (t > 1.0F) {
            t = 1.0F;
         }

         if (t < 0.0F) {
            t = 0.0F;
         }

         t = 1.0F - (float)Math.pow(1.0F - t, 6.0);
         float extraH = ImageManager.getImage(Images.logo2).getHeight() * 0.425F;
         float offsetY = extraH - extraH * t;
         float drawX = this.getPosX() + this.getWidth() / 2.0F - ImageManager.getImage(Images.logo2).getWidth() / 2.0F + iTranslateX;
         float drawY = this.getPosY() + this.getHeight() / 2.0F - ImageManager.getImage(Images.logo2).getHeight() / 2.0F - offsetY + iTranslateY;
         ImageManager.getImage(Images.logo2).draw(oSB, (int)drawX, (int)drawY);
      } else {
         ImageManager.getImage(Images.logo2).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      }

      oSB.setColor(Color.WHITE);
   }
}
