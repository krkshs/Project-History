package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_ImageTemporaryLoad implements MenuElement_HoverElement_Type {
   private int iHoverLoadedTemporaryImagesID;
   private int offsetLeft;
   private int offsetRight;
   private int iWidth;
   private int iHeight;

   public MenuElement_HoverElement_Type_ImageTemporaryLoad(String sFile) {
      this.init(sFile, 0, CFG.PADDING);
   }

   public MenuElement_HoverElement_Type_ImageTemporaryLoad(String sFile, int offsetLeft) {
      this.init(sFile, offsetLeft, CFG.PADDING);
   }

   public MenuElement_HoverElement_Type_ImageTemporaryLoad(String sFile, int offsetLeft, int offsetRight) {
      this.init(sFile, offsetLeft, offsetRight);
   }

   private final void init(String sFile, int offsetLeft, int offsetRight) {
      this.iHoverLoadedTemporaryImagesID = Game.hoverManager.loadHoverLoadedTemporaryImage(sFile);
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iWidth = (int)(this.getImage().getWidth() * this.getImageScale());
      this.iHeight = (int)(this.getImage().getHeight() * this.getImageScale());
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      this.getImage().draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING, this.iWidth, this.iHeight);
   }

   private final Image getImage() {
      try {
         return Game.hoverManager.lHoverLoadedTemporaryImages.get(this.iHoverLoadedTemporaryImagesID);
      } catch (Exception var2) {
         return ImageManager.getImage(Images.imageNotFound);
      }
   }

   @Override
   public int getWidth() {
      return this.offsetLeft + this.offsetRight + this.iWidth;
   }

   private final float getImageScale() {
      return 1.0F;
   }

   @Override
   public int getHeight() {
      return this.getImage().getHeight();
   }
}
