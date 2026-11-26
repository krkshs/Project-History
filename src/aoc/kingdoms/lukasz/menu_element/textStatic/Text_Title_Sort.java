package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Title_Sort extends Text_Title {
   public boolean active;
   public boolean flipY;

   public Text_Title_Sort(boolean active, boolean flipY, String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int fontID) {
      super(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight, fontID);
      this.active = active;
      this.flipY = flipY;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      if (this.active) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
         ImageManager.getImage(Images.activeSort)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.activeSort).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.activeSort).getHeight() / 2 + iTranslateY,
               false,
               this.flipY
            );
         oSB.setColor(Color.WHITE);
      }
   }
}
