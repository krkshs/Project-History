package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MessageWar;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_MessagesWars extends Menu {
   public static SparksAnimation sparksAnimation = new SparksAnimation();

   public InGame_MessagesWars() {
      List<MenuElement> menuElements = new ArrayList<>();
      int iWarsWidth = Math.min(4, Math.max(Game.getCiv(Game.player.iCivID).diplomacy.iWarsSize, 1));
      int menuPadding = CFG.PADDING * 3;
      int menuWidth = ImageManager.getImage(Images.warBG).getWidth() * iWarsWidth + CFG.PADDING * (iWarsWidth - 1);
      int menuX = CFG.GAME_WIDTH - (Game.mapBG.iMinimapWidth + Images.boxTitleBORDERWIDTH) - menuWidth - menuPadding;
      menuWidth += Game.getCiv(Game.player.iCivID).diplomacy.iWarsSize > 4 ? menuPadding : 0;
      int buttonX = 0;
      int buttonY = CFG.PADDING / 2;

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).diplomacy.iWarsSize; i++) {
         try {
            if (WarManager.lWars.get(Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)).isAggressor(Game.player.iCivID)) {
               menuElements.add(
                  new MessageWar(
                     buttonX,
                     buttonY,
                     true,
                     WarManager.lWars.get(Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)).lDefenders.get(0).iCivID,
                     Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            } else if (WarManager.lWars.get(Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)).isDefender(Game.player.iCivID)) {
               menuElements.add(
                  new MessageWar(
                     buttonX,
                     buttonY,
                     true,
                     WarManager.lWars.get(Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)).lAggressors.get(0).iCivID,
                     Game.getCiv(Game.player.iCivID).diplomacy.lWars.get(i)
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         } catch (Exception var10) {
            CFG.exceptionStack(var10);
         }
      }

      this.initMenu(
         null,
         menuX,
         CFG.GAME_HEIGHT - menuPadding - ImageManager.getImage(Images.warBG).getHeight(),
         menuWidth,
         ImageManager.getImage(Images.warBG).getHeight() + CFG.PADDING * 4,
         menuElements,
         true
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (this.getMenuElementsSize() > 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         sparksAnimation.draw2(
            oSB,
            CFG.GAME_WIDTH - (Game.mapBG.iMinimapWidth + Images.boxTitleBORDERWIDTH) - Images.sparkWidth * 3 / 4 + iTranslateX,
            CFG.GAME_HEIGHT - Images.sparkHeight * 3 / 4 + iTranslateY,
            Images.sparkWidth * 3 / 4,
            Images.sparkHeight * 3 / 4
         );
         oSB.setColor(Color.WHITE);
      }

      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut();
   }
}
