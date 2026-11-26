package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonBattleRegimentEmpty extends MenuElement {
   public int imageID;

   public ButtonBattleRegimentEmpty(int imageID, int iPosX, int iPosY) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.imageID = imageID;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(ImageManager.getImage(imageID).getWidth() + 2);
      this.setHeight(ImageManager.getImage(imageID).getHeight() + 2);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.getImageAlpha(isActive)));
      ImageManager.getImage(this.imageID).draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + 1 + iTranslateY);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered()) {
         oSB.setColor(Colors.HOVER_GOLD);
         Renderer.drawBox2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      }

      oSB.setColor(Color.WHITE);
   }

   public float getImageAlpha(boolean isActive) {
      return !this.getIsHovered() && !isActive ? 0.15F : 0.35F;
   }
}
