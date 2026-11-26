package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Empty implements MenuElement_HoverElement_Type {
   private int iWidth = 1;
   private int iHeight = 1;

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
   }

   @Override
   public int getWidth() {
      return this.iWidth;
   }

   @Override
   public int getHeight() {
      return this.iHeight;
   }
}
