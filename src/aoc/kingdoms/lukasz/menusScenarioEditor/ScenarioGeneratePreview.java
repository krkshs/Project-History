package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioGeneratePreview extends Menu {
   public ScenarioGeneratePreview() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(0, 0, 1, 1));
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);

      try {
         Game.mapBG.previewTexture_Generate(oSB);
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      try {
         Game.mapScenarios.loadScenarios(false);
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      Game.menuManager.setViewIDWithoutAnimation(View.EDITOR_SCENARIOS_LIST);
   }
}
