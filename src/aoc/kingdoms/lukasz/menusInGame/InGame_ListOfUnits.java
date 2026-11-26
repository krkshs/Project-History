package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonUnit_03;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyChoose;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ListOfUnits extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;

   public InGame_ListOfUnits() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title698).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop698).getWidth() - Images.boxTitleBORDERWIDTH * 2;
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title698).getHeight();
      int buttonX = paddingLeft;
      int buttonY = CFG.PADDING;
      InGame_ListOfBuildings.IN_BUILDINGS = false;
      InGame_TechnologyChoose.IN_TECHNOLOGY_CHOOSE = false;
      int numOfUnits = 0;
      int buttonWidth = ImageManager.getImage(Images.insideTop).getWidth() - paddingLeft * 2 - CFG.BUTTON_HEIGHT;

      for (int i = 0; i < ArmyManager.lArmy.size(); i++) {
         for (int j = 0; j < ArmyManager.lArmy.get(i).size(); j++) {
            menuElements.add(new ButtonUnit_03(i, j, buttonX, buttonY, buttonWidth, true));
            buttonX += buttonWidth + CFG.PADDING;
            numOfUnits++;
         }

         buttonX = paddingLeft;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      int maxWidth = 0;

      for (int i = menuElements.size() - 1; i >= 0; i--) {
         maxWidth = Math.max(maxWidth, menuElements.get(i).getPosX() + menuElements.get(i).getWidth() + CFG.PADDING);
      }

      menuElements.add(new Empty(0, 0, maxWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_DoubleText(Game.lang.get("ListOfUnits"), "" + numOfUnits, false, false, Images.title698) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            nWidth += Images.boxTitleBORDERWIDTH * 2;
            super.draw(oSB, nPosX - Images.boxTitleBORDERWIDTH, nPosY, nWidth, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_ListOfUnits.lTime2;
         }
      }, menuX + Images.boxTitleBORDERWIDTH, menuY, menuWidth, menuHeight, menuElements, false, true);
      this.scrollExtraPosX = Images.boxTitleBORDERWIDTH;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() - Images.boxTitleBORDERWIDTH + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH * 2,
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() - Images.boxTitleBORDERWIDTH + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH * 2,
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop698,
         Images.insideBot698
      );
      ImageManager.getImage(Images.outlinerOver)
         .draw2(
            oSB,
            this.getPosX() + (this.getWidth() + Images.boxTitleBORDERWIDTH) / 2 - ImageManager.getImage(Images.outlinerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.outlinerOver).getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.outlinerOver).getHeight())
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
