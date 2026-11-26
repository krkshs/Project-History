package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitle2 extends MenuTitle {
   public MenuTitle2(String sText, int iHeight, boolean moveable, boolean resizable) {
      super(sText, iHeight, moveable, resizable);
   }

   public MenuTitle2(String sText, float fontScale, int iHeight, boolean moveable, boolean resizable) {
      super(sText, fontScale, iHeight, moveable, resizable);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      ImageManager.getImage(Images.title2).draw(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }
}
