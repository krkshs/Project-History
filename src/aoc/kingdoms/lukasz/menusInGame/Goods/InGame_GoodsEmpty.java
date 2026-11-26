package aoc.kingdoms.lukasz.menusInGame.Goods;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import java.util.ArrayList;
import java.util.List;

public class InGame_GoodsEmpty extends Menu {
   public InGame_GoodsEmpty() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT) {
         @Override
         public void actionElement() {
            Game.menuManager.setVisibleInGame_Goods(false);
         }
      });
      menuElements.get(menuElements.size() - 1).setClickable(true);
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, false);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameGoods();
   }
}
