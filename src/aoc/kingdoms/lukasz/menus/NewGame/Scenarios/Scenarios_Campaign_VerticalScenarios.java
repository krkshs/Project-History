package aoc.kingdoms.lukasz.menus.NewGame.Scenarios;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_Preview;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Scenarios_Campaign_VerticalScenarios extends Menu {
   private int iXPos = 0;
   private int iYPos = 0;
   private int iWidth = 480;
   private int iHeight = 480;

   public Scenarios_Campaign_VerticalScenarios() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingTopBot = CFG.PADDING * 2 + CFG.PADDING / 2;
      int buttonPaddingY = CFG.PADDING * 4;
      this.iYPos = -CFG.BUTTON_HEIGHT;
      this.iWidth = Game.mapBG.scenarioOver.getWidth() + paddingTopBot * 2;
      this.iHeight = CFG.GAME_HEIGHT + CFG.BUTTON_HEIGHT * 2;
      this.iXPos = CFG.GAME_WIDTH / 2 - this.iWidth / 2;
      this.iYPos = (int)(0.5F * (CFG.GAME_HEIGHT - this.iHeight));
      int buttonPadding = CFG.PADDING * 2;
      int buttonX = paddingTopBot;
      int buttonY = CFG.BUTTON_HEIGHT / 2;
      int activeX = 0;

      for (int i = Game.mapScenarios.SCENARIOS_SIZE - 1; i >= 0; i--) {
         if (Game.mapScenarios.details.get(i).Campaign) {
            menuElements.add(new Button_Preview(buttonX, buttonY, i));
            if (i == Game.scenarioID) {
               activeX = menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() / 2;
            }

            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonPadding;
         }
      }

      if (buttonY < CFG.GAME_HEIGHT) {
         activeX = 0;
         int tW = 0;

         for (int ix = 0; ix < menuElements.size(); ix++) {
            tW += menuElements.get(ix).getHeight() + buttonPadding;
         }

         tW -= buttonPadding;
         buttonY = (CFG.GAME_HEIGHT - tW) / 2;

         for (int ix = 0; ix < menuElements.size(); ix++) {
            menuElements.get(ix).setPosY(buttonY);
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonPadding;
         }
      }

      menuElements.add(new Empty(0, 0, this.iWidth - 1, buttonY + CFG.BUTTON_HEIGHT));
      this.initMenu(null, this.iXPos, 0, this.iWidth, CFG.GAME_HEIGHT, menuElements, true);
      this.drawScrollPositionAlways2 = false;
      if (activeX != 0) {
         this.setMenuPosY(-activeX + CFG.GAME_HEIGHT / 2);
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
