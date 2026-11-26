package aoc.kingdoms.lukasz.menusInGame.Buildings;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_BuildingsGroup extends Menu {
   public static int menuX = 0;
   public static int menuY = 0;
   public static int menuHeight = 0;
   public static int mTranslateX = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iProvinceID = 0;
   public static boolean isCapital = false;
   public int imgTitle;
   public int imgTop;
   public int imgBot;

   public InGame_BuildingsGroup() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int buttonWidth;
      if (Game.getProvince(iProvinceID).isCapital && Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getCapitalProvinceID() == iProvinceID) {
         this.imgTitle = Images.title928;
         this.imgTop = Images.insideTop928;
         this.imgBot = Images.insideBot928;
         isCapital = true;
         buttonWidth = (ImageManager.getImage(this.imgTitle).getWidth() - Images.boxTitleBORDERWIDTH * 2) / 4;
      } else {
         this.imgTitle = Images.title698;
         this.imgTop = Images.insideTop698;
         this.imgBot = Images.insideBot698;
         isCapital = false;
         buttonWidth = (ImageManager.getImage(this.imgTitle).getWidth() - Images.boxTitleBORDERWIDTH * 2) / 3;
      }

      int menuWidth = ImageManager.getImage(this.imgTitle).getWidth();
      int tMenuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      menuX = tMenuX + Images.boxTitleBORDERWIDTH;
      int tMenuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      menuY = tMenuY + this.getInnerTitleHeight();
      int buttonY = 0;
      menuElements.add(new Text_TitleBlue(Game.lang.get("Administration"), -1, Images.boxTitleBORDERWIDTH, 0, buttonWidth, this.getInnerTitleHeight()) {
         @Override
         protected Color getColor(boolean isActive) {
            return Colors.getColorTopStats3(isActive, this.getIsHovered());
         }

         @Override
         public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
         }
      });
      menuElements.add(
         new Text_TitleBlue(Game.lang.get("Military"), -1, Images.boxTitleBORDERWIDTH + buttonWidth, 0, buttonWidth, this.getInnerTitleHeight()) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorTopStats3(isActive, this.getIsHovered());
            }

            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      menuElements.add(
         new Text_TitleBlue(Game.lang.get("Economy"), -1, Images.boxTitleBORDERWIDTH + buttonWidth * 2, 0, buttonWidth, this.getInnerTitleHeight()) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorTopStats3(isActive, this.getIsHovered());
            }

            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      if (isCapital) {
         menuElements.add(
            new Text_TitleBlue(Game.lang.get("Capital"), -1, Images.boxTitleBORDERWIDTH + buttonWidth * 3, 0, buttonWidth, this.getInnerTitleHeight()) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.getColorTopStats3(isActive, this.getIsHovered());
               }

               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("UniqueCapitalBuildings"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_FlagTitle(Game.player.iCivID, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      buttonY += (ImageManager.getImage(Images.buildingsFrame).getHeight() + CFG.PADDING * 4) * 6;
      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - tMenuY - (CFG.GAME_HEIGHT - InGame_ProvinceInfo.menuPosY) - CFG.PADDING * 3);
      if (!CFG.isDesktop()) {
         tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - tMenuY - CFG.PADDING * 2);
      }

      menuHeight = tMenuHeight - this.getInnerTitleHeight();
      menuElements.add(new Empty(0, 0, menuWidth, Math.min(tMenuY - 1, Math.max(buttonY, tMenuHeight))));
      this.initMenu(
         new MenuTitleIMG_DoubleText(Game.lang.get("ConstructNewBuilding"), Game.getProvince(iProvinceID).getProvinceName(), false, false, this.imgTitle) {
            @Override
            public long getTime() {
               return InGame_BuildingsGroup.lTime;
            }

            @Override
            public void onHovered() {
               Game.menuManager.setOrderOfMenu_InGameBuildings();
            }

            @Override
            public void action() {
               Game.menuManager.setOrderOfMenu_InGameBuildings();
            }
         }, tMenuX, tMenuY, menuWidth, tMenuHeight, menuElements, false, true
      );
   }

   public int getInnerTitleHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 6;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      mTranslateX = iTranslateX;
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false, this.imgTop, this.imgBot
      );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameBuildings();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_Buildings(false, false);
   }
}
