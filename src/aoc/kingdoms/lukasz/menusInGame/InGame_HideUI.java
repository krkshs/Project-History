package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTopDate;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_HideUI extends Menu {
   public static boolean addDate = true;

   public InGame_HideUI() {
      List<MenuElement> menuElements = new ArrayList<>();
      if (addDate) {
         menuElements.add(new ButtonTopDate(Game_Calendar.getCurrentDate(), 0, InGame.topStatsHeight) {
            @Override
            public int getSFX() {
               return Game.gameThread.play ? SoundsManager.SOUND_CLICK_TOP : SoundsManager.PLAY;
            }

            @Override
            public void actionElement() {
               Game.gameThread.play = !Game.gameThread.play;
            }

            @Override
            public int getPosX() {
               return CFG.GAME_WIDTH - InGame.getDatePadding() - this.getWidth();
            }

            @Override
            protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         });
      }

      menuElements.add(new Empty(0, 0, CFG.GAME_WIDTH / 4, CFG.GAME_HEIGHT / 4) {
         @Override
         public void actionElement() {
            Game.menuManager.setViewIDWithoutAnimation(View.IN_GAME);
         }
      });
      menuElements.get(menuElements.size() - 1).setClickable(true);
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }
}
