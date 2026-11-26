package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.menu_element.button.Button_Preview;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_ScenarioPreview implements MenuElement_HoverElement_Type {
   public Button_Preview buttonPreview;

   public MenuElement_HoverElement_Type_ScenarioPreview(int scenarioID) {
      this.buttonPreview = new Button_Preview(0, 0, scenarioID);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      this.buttonPreview.draw(oSB, nPosX, nPosY, false, false);
   }

   @Override
   public int getWidth() {
      return this.buttonPreview.getWidth();
   }

   @Override
   public int getHeight() {
      return this.buttonPreview.getHeight();
   }

   public int getHeight2() {
      return this.buttonPreview.getHeight();
   }
}
