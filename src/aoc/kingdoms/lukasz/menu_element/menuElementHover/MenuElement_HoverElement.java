package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class MenuElement_HoverElement {
   private List<MenuElement_HoverElement_Type> lElements;
   private int iMaxHeight = 0;
   public boolean drawElement = true;

   public MenuElement_HoverElement(List<MenuElement_HoverElement_Type> nElements) {
      this.lElements = new ArrayList<>();

      for (int i = 0; i < nElements.size(); i++) {
         this.lElements.add(nElements.get(i));
         this.iMaxHeight = Math.max(this.iMaxHeight, nElements.get(i).getHeight());
      }
   }

   public MenuElement_HoverElement(List<MenuElement_HoverElement_Type> nElements, boolean drawElement) {
      this.drawElement = drawElement;
      this.lElements = new ArrayList<>();

      for (int i = 0; i < nElements.size(); i++) {
         this.lElements.add(nElements.get(i));
         this.iMaxHeight = Math.max(this.iMaxHeight, nElements.get(i).getHeight());
      }
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      int i = 0;

      for (int tX = 0; i < this.lElements.size(); i++) {
         this.lElements.get(i).draw(oSB, nPosX + tX, nPosY, nAlpha, iMaxWidth);
         tX += this.lElements.get(i).getWidth();
      }
   }

   public final int getWidth() {
      int out = 0;

      for (int i = 0; i < this.lElements.size(); i++) {
         out += this.lElements.get(i).getWidth();
      }

      return out;
   }

   public int getHeight() {
      return this.iMaxHeight;
   }
}
