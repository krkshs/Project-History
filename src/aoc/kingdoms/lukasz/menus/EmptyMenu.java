package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import java.util.ArrayList;
import java.util.List;

public class EmptyMenu extends Menu {
   public EmptyMenu() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(0, 0, 1, 1));
      this.initMenu(null, 0, 0, 2, 2, menuElements, false);
      this.getMenuElement(0).setVisible(false);
   }
}
