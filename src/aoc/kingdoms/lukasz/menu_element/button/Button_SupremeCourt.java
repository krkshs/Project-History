package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_IncreaseTaxEfficiency;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_SupremeCourt extends Button {
   public Button_SupremeCourt(int iPosX, int iPosY) {
      this.init(
         "",
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.supremeCourt).getWidth(),
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
            ImageManager.getImage(Images.supremeCourt).getWidth(),
            ImageManager.getImage(Images.supremeCourt).getHeight()
         );
      }

      ImageManager.getImage(Images.supremeCourt).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.supremeCourt).getHeight();
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = InGame_Court_IncreaseTaxEfficiency.getHoverSupremeCourt();
   }
}
