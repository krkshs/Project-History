package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.AddNewCivData;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2_CivName;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2_CivName_Random;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2Sparks;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGameFlags extends Menu {
   public NewGameFlags() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING * 2;
      int buttonY = CFG.PADDING * 2;
      List<Integer> tCivs = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0 && Game.getCiv(i).getCapitalProvinceID() >= 0) {
            tCivs.add(i);
         }
      }

      int menuH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2 + buttonY * 2;
      menuElements.add(
         new ButtonGame2Sparks(
            Game.lang.get("AddCivilization"),
            CFG.FONT_BOLD,
            -1,
            buttonX,
            buttonY + CFG.TEXT_HEIGHT + CFG.PADDING * 2,
            CFG.BUTTON_WIDTH * 2 + CFG.BUTTON_WIDTH / 4,
            true,
            ImageManager.getImage(Images.flagOverDefault).getHeight()
         ) {
            @Override
            public void actionElement() {
               CFG.brushTool = false;
               CFG.selectMode = true;
               NewGame_AddCiv.addNewCivData = new AddNewCivData();
               NewGame_AddCiv.addNewCivData.addProvince(Game.iActiveProvince);
               NewGame_AddCiv.addNewCivData.technologyID = Math.max(0, Game.mapScenarios.details.get(Game.scenarioID).CivDefault_Technology);
               Game.menuManager.setViewID(View.NEW_GAME_ADD_CIV);
            }

            @Override
            protected Color getColor(boolean isActive) {
               return !this.getIsHovered() ? Colors.HOVER_GOLD : super.getColor(isActive);
            }

            @Override
            public boolean getClickable() {
               return Game.iActiveProvince >= 0
                  && Game.getProvince(Game.iActiveProvince).getWasteland() < 0
                  && !Game.getProvince(Game.iActiveProvince).getSeaProvince()
                  && !Game.getProvince(Game.iActiveProvince).isCapital;
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING + CFG.PADDING / 2;
      menuElements.add(new ButtonFlag2_CivName_Random(buttonX, buttonY, true));
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING + CFG.PADDING / 2;

      while (!tCivs.isEmpty()) {
         int bestID = 0;
         int ix = 1;

         for (int iSize = tCivs.size(); ix < iSize; ix++) {
            if (Game.getCiv(tCivs.get(bestID)).iCivRankScore < Game.getCiv(tCivs.get(ix)).iCivRankScore) {
               bestID = ix;
            }
         }

         menuElements.add(
            new ButtonFlag2_CivName(tCivs.get(bestID), buttonX, buttonY, true) {
               @Override
               public void actionElement() {
                  Game.setActiveProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                  if (Game.iActiveProvince >= 0
                     && Game.getProvince(Game.iActiveProvince).getCivID() > 0
                     && Game.player.iCivID != Game.getProvince(Game.iActiveProvince).getCivID()) {
                     Game.player.iCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
                     Game.menuManager.rebuildNewGameCiv();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = null;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING + CFG.PADDING / 2;
         tCivs.remove(bestID);
         if (menuElements.size() >= GameValues.newGame.NEW_GAME_FLAGS_LIMIT) {
            break;
         }
      }

      int buttonPlayW = ImageManager.getImage(Images.buttonPlay).getWidth() + CFG.PADDING;
      this.initMenu(null, buttonPlayW, CFG.GAME_HEIGHT - menuH, CFG.GAME_WIDTH - buttonPlayW * 2, menuH, menuElements, true);
      this.drawScrollPositionAlways = false;
      this.drawScrollPositionAlways2 = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.65F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, this.getWidth(), this.getHeight() / 2);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
