package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_CapitalMove extends Button {
   public Button_CapitalMove(int iPosX, int iPosY) {
      this.init(
         "",
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.buildingMoveCapital).getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.buildingMoveCapital).getWidth(),
            ImageManager.getImage(Images.buildingMoveCapital).getHeight()
         );
      }

      ImageManager.getImage(Images.buildingMoveCapital).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.buildingMoveCapital).getHeight();
   }
}
