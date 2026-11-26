package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG extends MenuTitle {
   public int imageID;

   public MenuTitleIMG(String sText, boolean moveable, boolean resizable, int imageID) {
      super(sText, ImageManager.getImage(imageID).getHeight(), moveable, resizable);
      this.imageID = imageID;
   }

   public MenuTitleIMG(String sText, int nHeight, boolean moveable, boolean resizable, int imageID) {
      super(sText, nHeight, moveable, resizable);
      this.imageID = imageID;
   }

   public MenuTitleIMG(String sText, int nHeight, int fontID, boolean moveable, boolean resizable, int imageID) {
      super(sText, nHeight, fontID, moveable, resizable);
      this.imageID = imageID;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      ImageManager.getImage(this.imageID).draw(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }
}
