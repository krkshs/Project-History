package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_GraphVertical implements MenuElement_HoverElement_Type {
   public Graph_Vertical graphVertical = new Graph_Vertical(
      Graph_Vertical_Data_Type.NUM_OF_PROVINCES_BY_CONTINENT, Game.lang.get("Civilizations"), Game.lang.get("Provinces"), 0, 0, 500, 200, true
   );

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      this.graphVertical.draw(oSB, nPosX, nPosY + CFG.PADDING, false, false);
   }

   @Override
   public int getWidth() {
      return this.graphVertical.getWidth();
   }

   @Override
   public int getHeight() {
      return this.graphVertical.getHeight();
   }
}
