package aoc.kingdoms.lukasz.menus.NewGame.Scenarios;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_Preview;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Scenarios_Campaign_HorizontalScenarios extends Menu {
   private int iXPos = 0;
   private int iYPos = 0;
   private int iWidth = 480;
   private int iHeight = 480;

   public Scenarios_Campaign_HorizontalScenarios() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingTopBot = CFG.PADDING * 2 + CFG.PADDING / 2;
      int buttonPaddingY = CFG.PADDING * 4;
      this.iXPos = -CFG.BUTTON_WIDTH;
      this.iWidth = CFG.GAME_WIDTH + CFG.BUTTON_WIDTH * 2;
      this.iHeight = Game.mapBG.scenarioOver.getHeight() + buttonPaddingY * 2;
      this.iYPos = (int)(0.5F * (CFG.GAME_HEIGHT - this.iHeight));
      int buttonPadding = CFG.PADDING * 2;
      int buttonX = CFG.BUTTON_WIDTH;
      int buttonY = CFG.PADDING * 3;
      int activeX = 0;

      for (int i = Game.mapScenarios.SCENARIOS_SIZE - 1; i >= 0; i--) {
         if (Game.mapScenarios.details.get(i).Campaign) {
            menuElements.add(new Button_Preview(buttonX, buttonY, i));
            if (i == Game.scenarioID) {
               activeX = menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() / 2;
            }

            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + buttonPadding;
         }
      }

      if (buttonX < CFG.GAME_WIDTH) {
         activeX = 0;
         int tW = 0;

         for (int ix = 0; ix < menuElements.size(); ix++) {
            tW += menuElements.get(ix).getWidth() + buttonPadding;
         }

         tW -= buttonPadding;
         buttonX = (CFG.GAME_WIDTH - tW) / 2;

         for (int ix = 0; ix < menuElements.size(); ix++) {
            menuElements.get(ix).setPosX(buttonX);
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + buttonPadding;
         }
      }

      this.initMenu(
         null,
         0,
         CFG.GAME_HEIGHT / 2 - (Button_Preview.getButtonHeight() + buttonY * 2) / 2,
         CFG.GAME_WIDTH,
         Button_Preview.getButtonHeight() + buttonY * 2,
         menuElements,
         true
      );
      this.drawScrollPositionAlways = false;
      if (activeX != 0) {
         this.setMenuPosX(-activeX + CFG.GAME_WIDTH / 2);
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(oSB, iTranslateX + this.iXPos, iTranslateY + this.iYPos, this.iWidth, this.iHeight);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_Scenarios();
   }
}
