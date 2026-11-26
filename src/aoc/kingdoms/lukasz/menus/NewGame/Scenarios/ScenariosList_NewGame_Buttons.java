package aoc.kingdoms.lukasz.menus.NewGame.Scenarios;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_PreviewSmall;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenariosList_NewGame_Buttons extends Menu {
   public ScenariosList_NewGame_Buttons() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.title600).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = buttonYPadding * 2;
      int buttonW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      int i = Game.mapScenarios.SCENARIOS_SIZE - 1;

      for (int j = 0; i >= 0; i--) {
         if (!Game.mapScenarios.details.get(i).Campaign) {
            String sYear = "" + Game.gameAges.getYear(Game.mapScenarios.details.get(i).Year);
            menuElements.add(
               new Button_PreviewSmall(
                  Game.lang.get(Game.mapScenarios.details.get(i).Name),
                  sYear,
                  j % 2 == 0 ? paddingLeft : paddingLeft + CFG.PADDING + buttonW,
                  buttonY,
                  buttonW,
                  CFG.BUTTON_HEIGHT4,
                  i
               )
            );
            if (j % 2 == 1) {
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }

            j++;
         }
      }

      buttonY = 0;
      i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - CFG.GAME_HEIGHT / 8 - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("Scenarios"), false, false, Images.title600),
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         CFG.GAME_HEIGHT / 8,
         menuWidth,
         i,
         menuElements,
         true,
         false
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
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
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
