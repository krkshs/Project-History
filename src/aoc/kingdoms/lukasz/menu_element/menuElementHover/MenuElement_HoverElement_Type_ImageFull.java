package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_ImageFull implements MenuElement_HoverElement_Type {
   private int iImageID;
   private int offsetLeft;
   private int offsetRight;
   private int iWidth;
   private int iHeight;

   public MenuElement_HoverElement_Type_ImageFull(int iImageID) {
      this.init(iImageID, 0, CFG.PADDING);
   }

   public MenuElement_HoverElement_Type_ImageFull(int iImageID, int offsetLeft) {
      this.init(iImageID, offsetLeft, CFG.PADDING);
   }

   public MenuElement_HoverElement_Type_ImageFull(int iImageID, int offsetLeft, int offsetRight) {
      this.init(iImageID, offsetLeft, offsetRight);
   }

   private final void init(int iImageID, int offsetLeft, int offsetRight) {
      this.iImageID = iImageID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iWidth = ImageManager.getImage(iImageID).getWidth();
      this.iHeight = ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ImageManager.getImage(this.iImageID).draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING + CFG.PADDING / 2, this.iWidth, this.iHeight);
   }

   @Override
   public int getWidth() {
      return this.offsetLeft + this.offsetRight + this.iWidth;
   }

   @Override
   public int getHeight() {
      return Math.max(CFG.TEXT_HEIGHT, this.iHeight) + CFG.PADDING;
   }
}
