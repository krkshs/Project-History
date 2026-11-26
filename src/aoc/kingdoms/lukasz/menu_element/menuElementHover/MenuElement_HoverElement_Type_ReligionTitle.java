package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_ReligionTitle implements MenuElement_HoverElement_Type {
   private int iReligionID;
   private int offsetLeft = 0;
   private int offsetRight = 0;
   private int iWidth = 0;
   private int iHeight = 0;

   public MenuElement_HoverElement_Type_ReligionTitle(int iReligionID, int offsetLeft, int offsetRight) {
      this.iReligionID = iReligionID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iWidth = (int)(Game.religionManager.religionImages.get(iReligionID).getWidth() * this.getImageScale());
      this.iHeight = (int)(Game.religionManager.religionImages.get(iReligionID).getHeight() * this.getImageScale());
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      Game.religionManager
         .religionImages
         .get(this.iReligionID)
         .draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING * 2 + (CFG.TEXT_HEIGHT - this.iHeight) / 2, this.iWidth, this.iHeight);
   }

   @Override
   public int getWidth() {
      return this.offsetRight + this.offsetLeft + this.iWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 3 / 4;
   }

   private final float getImageScale() {
      return (float)this.getHeight() / Game.religionManager.religionImages.get(this.iReligionID).getHeight();
   }
}
