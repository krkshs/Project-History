package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import java.util.ArrayList;
import java.util.List;

public class MainMenu_StatsEmpty extends Menu {
   public MainMenu_StatsEmpty() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT) {
         @Override
         public void actionElement() {
            MainMenu_Stats.disposeFlags();
            Game.menuManager.setViewID(View.MAINMENU);
         }
      });
      menuElements.get(menuElements.size() - 1).setClickable(true);
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_MainMenu_Stats();
   }
}
