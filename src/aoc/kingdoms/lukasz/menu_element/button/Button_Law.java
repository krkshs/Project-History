package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LawsManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_Law extends Button {
   public int imageID;

   public Button_Law(int iPosX, int iPosY, int imageID) {
      this.init("", CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), true, true, false, false);
      this.imageID = imageID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      LawsManager.lawsImages.get(this.imageID).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonWidth() {
      return LawsManager.lawsImages.get(0).getWidth();
   }

   public static int getButtonHeight() {
      return LawsManager.lawsImages.get(0).getHeight();
   }
}
