package aoc.kingdoms.lukasz.jakowski;

public class ScrollManager {
   private static final int DEFAULT_SCROLL = 15;
   private static int iScroll = 15;
   private static long lScrollTime = 0L;

   public static final boolean scrolled(int amount) {
      try {
         int isAnyScrollableMenuHovered = Game.hoverManager.getHoveredMenuID_Scroll(Touch.getMousePosX(), Touch.getMousePosY());
         if (getIsScrollable_Hovered_MenuElement()) {
            updateScroll();
            scrollHoveredMenuElement(-iScroll * amount);
         } else if (isAnyScrollableMenuHovered >= 0) {
            if (getIsScrollableY_MenuHovered(isAnyScrollableMenuHovered)) {
               updateScroll();
               scrollHoveredMenu_Y(isAnyScrollableMenuHovered, -iScroll * amount);
            } else if (getIsScrollableX_MenuHovered(isAnyScrollableMenuHovered)) {
               updateScroll();
               scrollHoveredMenu_X(isAnyScrollableMenuHovered, -iScroll * amount);
            }
         } else if (Game.hoverManager.hoverActiveSliderMenuID < 0 && Game.hoverManager.hoverActiveMenuElementID < 0) {
            Game.mapTouchManager.Scroll(amount);
         }

         Game.soundsManager.playHover();
      } catch (IndexOutOfBoundsException var2) {
         CFG.exceptionStack(var2);
      } catch (NullPointerException var3) {
         CFG.exceptionStack(var3);
      } catch (StackOverflowError var4) {
         CFG.exceptionStack(var4);
      } catch (ArithmeticException var5) {
         CFG.exceptionStack(var5);
      }

      return true;
   }

   protected static final void updateScroll() {
      if (lScrollTime + 50L > CFG.currentTimeMillis) {
         lScrollTime = CFG.currentTimeMillis;
         iScroll = iScroll + (int)(iScroll * 1.2F);
         if (iScroll > 75) {
            iScroll = 75;
         }
      } else {
         lScrollTime = CFG.currentTimeMillis;
         iScroll = 15;
      }
   }

   protected static final void updateHoveredMenuElement_Hover(int nPosX, int nPosY) {
      try {
         if (Game.hoverManager.hoverActiveSliderMenuID >= 0 && Game.hoverManager.hoverActiveMenuElementID >= 0) {
            Game.menuManager
               .getActiveMenu()
               .get(Game.hoverManager.hoverActiveSliderMenuID)
               .getMenuElement(Game.hoverManager.hoverActiveMenuElementID)
               .updateHover(
                  nPosX,
                  nPosY,
                  Game.menuManager.getActiveMenu().get(Game.hoverManager.hoverActiveSliderMenuID).getMenuPosX(),
                  Game.menuManager.getActiveMenu().get(Game.hoverManager.hoverActiveSliderMenuID).getMenuPosY()
               );
         }
      } catch (IndexOutOfBoundsException var3) {
         CFG.exceptionStack(var3);
      } catch (NullPointerException var4) {
         CFG.exceptionStack(var4);
      }
   }

   protected static final boolean getIsScrollableX_MenuHovered(int menuID) {
      try {
         return Game.menuManager.getActiveMenu().get(menuID).getScrollableX();
      } catch (IndexOutOfBoundsException var2) {
         return false;
      } catch (NullPointerException var3) {
         return false;
      }
   }

   protected static final void scrollHoveredMenu_X(int menuID, int nChange) {
      try {
         Game.menuManager.getActiveMenu().get(menuID).stopScrolling();
         Game.menuManager.getActiveMenu().get(menuID).updateMenuPosX(Game.menuManager.getActiveMenu().get(menuID).getNewMenuPosX() + nChange);
         Game.hoverManager.actionMove_Hover(Touch.getMousePosX(), Touch.getMousePosY(), true);
      } catch (IndexOutOfBoundsException var3) {
         CFG.exceptionStack(var3);
      } catch (NullPointerException var4) {
         CFG.exceptionStack(var4);
      }
   }

   protected static final boolean getIsScrollableY_MenuHovered(int menuID) {
      try {
         return Game.menuManager.getActiveMenu().get(menuID).getScrollableY();
      } catch (IndexOutOfBoundsException var2) {
         return false;
      } catch (NullPointerException var3) {
         return false;
      }
   }

   protected static final void scrollHoveredMenu_Y(int menuID, int nChange) {
      try {
         Game.menuManager.getActiveMenu().get(menuID).stopScrolling();
         Game.menuManager.getActiveMenu().get(menuID).updateMenuPosY(Game.menuManager.getActiveMenu().get(menuID).getNewMenuPosY() + nChange);
         Game.hoverManager.actionMove_Hover(Touch.getMousePosX(), Touch.getMousePosY(), true);
      } catch (IndexOutOfBoundsException var3) {
         CFG.exceptionStack(var3);
      } catch (NullPointerException var4) {
         CFG.exceptionStack(var4);
      }
   }

   protected static final boolean getIsScrollable_Hovered_MenuElement() {
      try {
         return Game.menuManager
            .getActiveMenu()
            .get(Game.hoverManager.hoverActiveSliderMenuID)
            .getMenuElement(Game.hoverManager.hoverActiveMenuElementID)
            .getScrollable();
      } catch (IndexOutOfBoundsException var1) {
         return false;
      } catch (NullPointerException var2) {
         return false;
      }
   }

   protected static final void scrollHoveredMenuElement(int nChange) {
      try {
         Game.menuManager
            .getActiveMenu()
            .get(Game.hoverManager.hoverActiveSliderMenuID)
            .getMenuElement(Game.hoverManager.hoverActiveMenuElementID)
            .scrollByWheel(nChange);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }
}
