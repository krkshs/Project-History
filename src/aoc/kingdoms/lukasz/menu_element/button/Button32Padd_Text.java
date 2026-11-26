package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button32Padd_Text extends Button32Padd {
   public int value = 0;

   public Button32Padd_Text(int iconImageID, int nPosX, int nPosY) {
      super(iconImageID, nPosX, nPosY);
      this.fontID = CFG.FONT_REGULAR_SMALL;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }
}
