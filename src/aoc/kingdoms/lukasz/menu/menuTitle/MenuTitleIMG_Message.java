package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_Message extends MenuTitleIMG {
   public MenuTitleIMG_Message(String sText, boolean moveable, boolean resizable, int imageID) {
      super(sText, moveable, resizable, imageID);
   }

   public MenuTitleIMG_Message(String sText, int nHeight, boolean moveable, boolean resizable, int imageID) {
      super(sText, nHeight, moveable, resizable, imageID);
   }

   public MenuTitleIMG_Message(String sText, int nHeight, int fontID, boolean moveable, boolean resizable, int imageID) {
      super(sText, nHeight, fontID, moveable, resizable, imageID);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      super.draw(oSB, nPosX, nPosY, nWidth, titleStatus);
      ImageManager.getImage(Images.message)
         .draw(
            oSB,
            nPosX + (this.getHeight() - ImageManager.getImage(Images.message).getHeight()) / 2,
            nPosY - this.getHeight() / 2 - ImageManager.getImage(Images.message).getHeight() / 2
         );
   }
}
