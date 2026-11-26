package aoc.kingdoms.lukasz.menu;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class HoverManager {
   public int hoverActiveSliderMenuID = -1;
   public int hoverActiveMenuElementID = -1;
   public int hoverActiveMenuTitleID = -1;
   public boolean hoverActiveMenuTitleCloseHovered = false;
   public static long hoverTime = 0L;
   protected static long hoverMobileTime = 0L;
   public static int HOVER_MOBILE_TIME_VISIBLE = 25000;
   public List<Image> lHoverLoadedTemporaryImages = new ArrayList<>();

   public final void rebuildHoverAfterRebuildMenu() {
      try {
         if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
            Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).setIsHovered(true);
            Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).buildElementHover();
         }
      } catch (Exception var2) {
      }
   }

   public final void updateHoveredFlag() {
      try {
         if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
            if (Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getTypeOfElement()
               != MenuElement_Type.BUTTON_FLAG) {
               ProvinceDraw.drawProvincesCiv_HoveredFlagID = 0;
            }
         } else {
            ProvinceDraw.drawProvincesCiv_HoveredFlagID = 0;
         }
      } catch (Exception var2) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = 0;
         CFG.exceptionStack(var2);
      }
   }

   public final void updateHoveredElement() {
      if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
         try {
            if (Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getVisible()
               && Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getTypeOfElement()
                  != MenuElement_Type.TRANSPARENT_BACKGROUND) {
               Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).updateHovered();
            }
         } catch (Exception var2) {
         }
      }
   }

   public final boolean actionMove_Hover(int nPosX, int nPosY, boolean updateHoveredElementAfterScrollingTheMenu) {
      if (Game.menuManager.getInGame() && InGame.ONLY_MAP_MODE) {
         return false;
      } else {
         try {
            if (Game.menuManager.dialogMenu.getVisible()) {
               this.resetHoverActive_Menu();
               return true;
            }

            this.hoverActiveMenuTitleID = -1;
            this.hoverActiveMenuTitleCloseHovered = false;
            if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
               try {
                  if (!Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getVisible()
                     || Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getTypeOfElement()
                        == MenuElement_Type.TRANSPARENT_BACKGROUND) {
                     this.resetHoverActive_Menu();
                  } else if (Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getClickable()
                     && nPosX
                        >= Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosX()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX()
                     && nPosX
                        <= Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosX()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getWidth()
                     && nPosY
                        >= Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosY()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY()
                     && nPosY
                        <= Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosY()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getHeight()
                           + Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY()) {
                     return true;
                  }
               } catch (Exception var7) {
                  this.resetHoverActive_Menu();
               }
            }

            try {
               for (int i = 0; i < Game.menuManager.getActiveMenu().size(); i++) {
                  if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getVisible()) {
                     if (nPosX >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                        && nPosX
                           <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                              + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getWidth()
                        && nPosY >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                        && nPosY
                           <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                              + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getHeight()) {
                        for (int j = 0; j < Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElementsSize(); j++) {
                           if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getVisible()
                              && Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).canBeHovered()
                              && nPosX
                                 >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getPosX()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuPosX()
                              && nPosX
                                 <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getPosX()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuPosX()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getWidth()
                              && nPosY
                                 >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getPosY()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuPosY()
                              && nPosY
                                 <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getPosY()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuElement(j).getHeight()
                                    + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getMenuPosY()) {
                              if (this.hoverActiveSliderMenuID != Game.menuManager.getActiveOrder(i) || this.hoverActiveMenuElementID != j) {
                                 if (this.hoverActiveMenuElementID >= 0 && this.hoverActiveSliderMenuID >= 0) {
                                    this.resetHoverActive_Menu();
                                 } else {
                                    MenuElement_Hover.resetAnimation();
                                 }

                                 this.hoverActiveSliderMenuID = Game.menuManager.getActiveOrder(i);
                                 this.hoverActiveMenuElementID = j;
                                 hoverTime = CFG.currentTimeMillis;
                                 Game.menuManager
                                    .getActiveMenu()
                                    .get(this.hoverActiveSliderMenuID)
                                    .getMenuElement(this.hoverActiveMenuElementID)
                                    .setIsHovered(true);
                                 Game.menuManager
                                    .getActiveMenu()
                                    .get(this.hoverActiveSliderMenuID)
                                    .getMenuElement(this.hoverActiveMenuElementID)
                                    .buildElementHover();
                                 this.updateHoveredFlag();
                                 if (Game.menuManager
                                    .getActiveMenu()
                                    .get(this.hoverActiveSliderMenuID)
                                    .getMenuElement(this.hoverActiveMenuElementID)
                                    .playSFX_Hovered()) {
                                    Game.soundsManager.playHover();
                                 }
                              }

                              return true;
                           }
                        }

                        if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getLockHoverOverMenuBackground()) {
                           this.resetHoverActive_Menu();
                           return true;
                        }
                     } else if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getTitle() != null
                        && nPosX >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                        && nPosX
                           <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                              + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getWidth()
                        && nPosY
                           >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                              - Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getTitle().getHeight()
                        && nPosY <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()) {
                        if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getCloseable()
                           && nPosX >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_PosX()
                           && nPosX
                              <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_PosX()
                                 + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_Width()
                           && nPosY >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_PosY()
                           && nPosY
                              <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_PosY()
                                 + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).menuClose.getCloseMenu_Height()) {
                           this.hoverActiveMenuTitleCloseHovered = true;
                        }

                        this.resetHoverActive_Menu();
                        this.hoverActiveMenuTitleID = Game.menuManager.getActiveOrder(i);
                        return true;
                     }
                  }
               }
            } catch (IndexOutOfBoundsException var6) {
               CFG.exceptionStack(var6);
            }

            this.resetHoverActive_Menu();
         } catch (GdxRuntimeException var8) {
            CFG.exceptionStack(var8);
         } catch (NullPointerException var9) {
            CFG.exceptionStack(var9);
         }

         return false;
      }
   }

   public final int getHoveredMenuID_Scroll(int nPosX, int nPosY) {
      try {
         for (int i = 0; i < Game.menuManager.getActiveMenu().size(); i++) {
            if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getVisible()) {
               if (nPosX >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                  && nPosX
                     <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                        + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getWidth()
                  && nPosY >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                  && nPosY
                     <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                        + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getHeight()) {
                  if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getScrollableX()
                     || Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getScrollableY()) {
                     return Game.menuManager.getActiveOrder(i);
                  }
               } else if (Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getTitle() != null
                  && nPosX >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                  && nPosX
                     <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosX()
                        + Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getWidth()
                  && nPosY
                     >= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()
                        - Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getTitle().getHeight()
                  && nPosY <= Game.menuManager.getActiveMenu().get(Game.menuManager.getActiveOrder(i)).getPosY()) {
                  return -1;
               }
            }
         }
      } catch (IndexOutOfBoundsException var4) {
         CFG.exceptionStack(var4);
      }

      return -1;
   }

   public final void udpateMobile() {
      if (CFG.isAndroid && this.getMenuElementHover_IsInView() && hoverMobileTime < CFG.currentTimeMillis - HOVER_MOBILE_TIME_VISIBLE) {
         this.resetHoverActive_Menu();
      }
   }

   public final void updateHoveredMenuElement_Hover(int nPosX, int nPosY) {
      try {
         if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
            Game.menuManager
               .getActiveMenu()
               .get(this.hoverActiveSliderMenuID)
               .getMenuElement(this.hoverActiveMenuElementID)
               .updateHover(
                  nPosX,
                  nPosY,
                  Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX(),
                  Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY()
               );
         }
      } catch (IndexOutOfBoundsException var4) {
         CFG.exceptionStack(var4);
      } catch (NullPointerException var5) {
         CFG.exceptionStack(var5);
      }
   }

   protected final void updateElementHover_Animation() {
      if (MenuElement_Hover.ANIMATION_ALPHA < 1.0F) {
         MenuElement_Hover.ANIMATION_ALPHA = MenuElement_Hover.ANIMATION_ALPHA
            + (float)(CFG.currentTimeMillis - MenuElement_Hover.ANIMATION_TIME) / MenuElement_Hover.ANIMATION_INTERVAL;
         MenuElement_Hover.ANIMATION_PADDING = CFG.PADDING * 2 - CFG.PADDING * 2 * (MenuElement_Hover.ANIMATION_ALPHA * 1.65F);
         if (MenuElement_Hover.ANIMATION_PADDING < 0.0F) {
            MenuElement_Hover.ANIMATION_PADDING = 0.0F;
         }

         if (MenuElement_Hover.ANIMATION_ALPHA > 1.0F) {
            MenuElement_Hover.ANIMATION_ALPHA = 1.0F;
         }
      }
   }

   protected final boolean isSomethingHovered() {
      try {
         return this.hoverActiveMenuElementID >= 0
            && this.hoverActiveSliderMenuID >= 0
            && Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).menuElementHover != null;
      } catch (IndexOutOfBoundsException var2) {
         return false;
      } catch (NullPointerException var3) {
         return false;
      }
   }

   protected final boolean getMenuElementHover_IsInView() {
      try {
         if (Game.hoverManager.hoverActiveSliderMenuID >= 0
            && Game.hoverManager.hoverActiveMenuElementID >= 0
            && !Game.menuManager
               .getActiveMenu()
               .get(Game.hoverManager.hoverActiveSliderMenuID)
               .getMenuElement(Game.hoverManager.hoverActiveMenuElementID)
               .getMenuElement_Hover_IsNull()) {
            return true;
         }
      } catch (IndexOutOfBoundsException var2) {
         CFG.exceptionStack(var2);
      } catch (NullPointerException var3) {
         CFG.exceptionStack(var3);
      }

      return false;
   }

   public final void resetHoverActive_Menu() {
      try {
         if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
            Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).setIsHovered(false);
            Game.menuManager.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).resetElementHover();
            this.hoverActiveSliderMenuID = -1;
            this.hoverActiveMenuElementID = -1;
            this.hoverActiveMenuTitleID = -1;
            this.updateHoveredFlag();

            for (int i = this.lHoverLoadedTemporaryImages.size() - 1; i >= 0; i--) {
               this.lHoverLoadedTemporaryImages.get(i).dispose();
               this.lHoverLoadedTemporaryImages.remove(i);
            }

            return;
         }
      } catch (Exception var2) {
      }

      this.hoverActiveSliderMenuID = -1;
      this.hoverActiveMenuElementID = -1;
      this.hoverActiveMenuTitleID = -1;
   }

   public final void resetHoverActive_Menu_Force() {
      this.hoverActiveSliderMenuID = -1;
      this.hoverActiveMenuElementID = -1;
      this.hoverActiveMenuTitleID = -1;
      this.updateHoveredFlag();

      try {
         for (int i = this.lHoverLoadedTemporaryImages.size() - 1; i >= 0; i--) {
            this.lHoverLoadedTemporaryImages.get(i).dispose();
            this.lHoverLoadedTemporaryImages.remove(i);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final int loadHoverLoadedTemporaryImage(String sFile) {
      try {
         this.lHoverLoadedTemporaryImages.add(new Image(ImageManager.loadTexture(sFile)));
         return Math.max(0, this.lHoverLoadedTemporaryImages.size() - 1);
      } catch (GdxRuntimeException var3) {
         this.lHoverLoadedTemporaryImages.add(new Image(ImageManager.loadTexture("gfx/imageNotFound.png")));
         return Math.max(0, this.lHoverLoadedTemporaryImages.size() - 1);
      }
   }
}
