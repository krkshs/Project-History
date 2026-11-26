package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Resource implements MenuElement_HoverElement_Type {
   private int iResourceID;
   private int offsetLeft = 0;
   private int offsetRight = 0;
   private int iWidth = 0;
   private int iHeight = 0;

   public MenuElement_HoverElement_Type_Resource(int iResourceID, int offsetLeft, int offsetRight) {
      this.iResourceID = iResourceID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iWidth = (int)(
         ResourcesManager.resourceImages.get(iResourceID).getWidth()
            * (CFG.CIV_FLAG_HEIGHT * this.getImageScale())
            / ResourcesManager.resourceImages.get(iResourceID).getHeight()
      );
      this.iHeight = (int)(
         ResourcesManager.resourceImages.get(iResourceID).getHeight()
            * (CFG.CIV_FLAG_HEIGHT * this.getImageScale())
            / ResourcesManager.resourceImages.get(iResourceID).getHeight()
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ResourcesManager.resourceImages
         .get(this.iResourceID)
         .draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - this.iHeight / 2, this.iWidth, this.iHeight);
   }

   @Override
   public int getWidth() {
      return this.offsetRight + this.offsetLeft + this.iWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING;
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT * 1.2F;
   }
}
