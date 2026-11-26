package aoc.kingdoms.lukasz.menusInGame.Buildings;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Buildings extends Menu {
   protected static final int ANIMATION_TIME = 60;
   private long lTime = 0L;
   public static int iProvinceID = 0;

   public InGame_Buildings() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 4;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int textPosX = CFG.PADDING * 4;
      menuElements.add(
         new Text_Title(
            Game.lang.get("BuildingsConstructed"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("BuildingsConstructed"));
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonBuilding(true, 6, 0, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      boolean addUniqueCapitalBuildings = true;
      if (addUniqueCapitalBuildings) {
         menuElements.add(
            new Text_TitleBlue(
               Game.lang.get("UniqueCapitalBuildings"),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            ) {
               @Override
               public void updateLanguage() {
                  this.setText(Game.lang.get("UniqueCapitalBuildings"));
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      boolean addOnce = true;

      for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
         for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
            if (addOnce && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding) {
               if (addUniqueCapitalBuildings) {
                  menuElements.add(
                     new Text_TitleBlue(
                        Game.lang.get("Buildings"),
                        -1,
                        Images.boxTitleBORDERWIDTH,
                        buttonY,
                        menuWidth - Images.boxTitleBORDERWIDTH * 2,
                        CFG.TEXT_HEIGHT + CFG.PADDING * 6
                     ) {
                        @Override
                        public void updateLanguage() {
                           this.setText(Game.lang.get("Buildings"));
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
               }

               addOnce = false;
            }

            menuElements.add(new ButtonBuilding(false, i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_FlagCenter(Game.lang.get("ConstructNewBuilding"), Game.getProvince(iProvinceID).getProvinceName(), false, false, Images.title1Red) {
            @Override
            public int getFlagCivID() {
               return Game.getProvince(InGame_Buildings.iProvinceID).getCivID();
            }

            @Override
            public long getTime() {
               return InGame_Buildings.this.lTime;
            }
         }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (this.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - this.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      this.lTime = CFG.currentTimeMillis;
   }
}
