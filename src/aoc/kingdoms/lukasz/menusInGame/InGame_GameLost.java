package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonPlay;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_GameLost extends Menu {
   public static final int ANIMATION_TIME = 500;
   public static long lTime = 0L;

   public InGame_GameLost() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(
         new ButtonPlay(
            Game.lang.get("Continue"),
            1,
            -1,
            CFG.GAME_WIDTH - ImageManager.getImage(Images.buttonPlay).getWidth(),
            CFG.GAME_HEIGHT - ImageManager.getImage(Images.buttonPlay).getHeight(),
            false
         ) {
            @Override
            public void actionElement() {
               Game.setActiveProvinceID(-1);
               ProvinceBorderManager.clearProvinceBorder();
               MainMenu.canContinue = false;
               Game.menuManager.setViewID(View.MAINMENU);
            }
         }
      );
      menuElements.add(new ButtonPlay(Game.lang.get("SpectatorMode"), 1, -1, 0, CFG.GAME_HEIGHT - ImageManager.getImage(Images.buttonPlay).getHeight(), true) {
         @Override
         public void actionElement() {
            Game.SPECTATOR_MODE = true;
            Game.player.iCivID = 0;
            Game.setActiveProvinceID(-1);
            ProvinceBorderManager.clearProvinceBorder();
            Game.menuManager.setViewIDWithoutAnimation(View.IN_GAME);
         }
      });
      lTime = CFG.currentTimeMillis;
      menuElements.add(new Text_Static(Game.lang.get("YouHaveBeenDefeated"), -1, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT) {
         @Override
         public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
            Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
            oSB.setColor(Color.WHITE);
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         @Override
         protected Color getColor(boolean isActive) {
            return Colors.HOVER_GOLD;
         }
      });
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.BUTTON_HEIGHT / 2 + iTranslateY,
            this.getWidth(),
            CFG.BUTTON_HEIGHT / 2,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
