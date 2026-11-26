package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyChoose;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ListOfBuildings extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static boolean IN_BUILDINGS = false;

   public InGame_ListOfBuildings() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = 0;
      int buttonYPadding = CFG.PADDING;
      IN_BUILDINGS = true;
      InGame_TechnologyChoose.IN_TECHNOLOGY_CHOOSE = false;
      int numOfBuildings = 0;

      for (int x = 0; x < 4; x++) {
         menuElements.add(
            new Text_Title_v2Center(
               x == 0
                  ? Game.lang.get("Administration")
                  : (x == 1 ? Game.lang.get("Military") : (x == 2 ? Game.lang.get("Economy") : Game.lang.get("UniqueCapitalBuildings"))),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

         for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
            for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
               if (BuildingsManager.buildings.get(i).GroupID == x) {
                  menuElements.add(new ButtonBuilding_Special(true, i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true, "", false));
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                  numOfBuildings++;
               }
            }
         }
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Resources"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
         for (int jx = 0; jx < BuildingsManager.buildingSize.get(i); jx++) {
            if (BuildingsManager.buildings.get(i).RequiredResource >= 0 && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding) {
               menuElements.add(new ButtonBuilding_Special(true, i, jx, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true, "", false));
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
               numOfBuildings++;
            }
         }
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_DoubleText(Game.lang.get("ListOfBuildings"), "" + numOfBuildings, false, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_ListOfBuildings.lTime2;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.rulerOver).getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      InGame_Civ.actionOnClose();
   }
}
