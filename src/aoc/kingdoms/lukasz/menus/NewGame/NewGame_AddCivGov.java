package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGame_AddCivGov extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public NewGame_AddCivGov() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int tTitleH = CFG.TEXT_HEIGHT + CFG.PADDING * 6;
      int tTitleH_Inner = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int tMaxInconWidth = ImageManager.getImage(Images.gold).getWidth();
      menuElements.add(new ButtonGame2(Game.lang.get("Back"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2) {
         @Override
         public void actionElement() {
            Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME_ADD_CIV);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("Government"),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            tTitleH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < Game.ideologiesManager.getIdeologiesSize(); i++) {
         menuElements.add(
            new ButtonIdeology2(i, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  NewGame_AddCiv.addNewCivData.sCivTag = Game.ideologiesManager.getRealTag(NewGame_AddCiv.addNewCivData.sCivTag)
                     + Game.ideologiesManager.getIdeology(this.getCurrent()).Extra_Tag;
                  Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME_ADD_CIV);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      buttonY += CFG.PADDING;
      int menuHeight = Math.min(
         buttonY,
         CFG.GAME_HEIGHT
            - menuY
            - (
               Game.menuManager.getInGame()
                  ? CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3
                  : ImageManager.getImage(Images.buttonPlay).getHeight() + CFG.PADDING * 3
            )
      );
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("AddNewCivilization"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return NewGame_AddCivGov.lTime;
         }
      }, Game.menuManager.getInGame() ? CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 2 : CFG.PADDING * 2, menuY, menuWidth, menuHeight, menuElements, true, false);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         if (Game.menuManager.getInGame()) {
            iTranslateX = iTranslateX + CFG.BUTTON_WIDTH - (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         } else {
            iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         }
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.keyboard.hideKeyboard();
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible) {
         Game.keyboard.hideKeyboard();
      }
   }
}
