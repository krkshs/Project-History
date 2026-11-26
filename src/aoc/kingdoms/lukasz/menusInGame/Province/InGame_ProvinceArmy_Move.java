package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmy_Move extends Menu {
   public long LAST_TIME_CHECK = 0L;
   public long TIME_CHECK = 1000L;

   public InGame_ProvinceArmy_Move() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = 0;
      int buttonY = 0;
      int menuH = CFG.BUTTON_HEIGHT + 1;
      menuElements.add(new ButtonGame_Style(Game.lang.get("Move"), CFG.FONT_REGULAR, -1, buttonX, buttonY, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true) {
         @Override
         public void actionElement() {
            Game.chooseProvinceMode = !Game.chooseProvinceMode;
            if (Game.chooseProvinceMode) {
               InGame_ProvinceArmy.lTimeMOVE = CFG.currentTimeMillis;
               InGame_ProvinceArmy.chooseProvinceExtraY = 0;
            }
         }

         @Override
         protected Color getColor(boolean isActive) {
            return Game.chooseProvinceMode ? Colors.HOVER_GOLD : super.getColor(isActive);
         }
      });
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      boolean menuVisible = false;
      if (!CFG.isDesktop() || GameValues.move.DESKTOP_SHOW_MOVE_BUTTON) {
         if (!GameValues.move.PLAYER_CAN_MOVE_ALL_ARMIES && (!Game.SPECTATOR_MODE || !Game.SANDBOX)) {
            int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
               .getArmyKeyID(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key);
            if (tDivID >= 0) {
               menuVisible = Game.getProvince(InGame_ProvinceArmy.iProvinceID).getArmy(tDivID).civID == Game.player.iCivID
                  || GameValues.move.PLAYER_CAN_MOVE_VASSALS_ARMIES
                     && Game.getCiv(Game.getProvince(InGame_ProvinceArmy.iProvinceID).getArmy(tDivID).civID).getPuppetOfCivID() == Game.player.iCivID;
            }
         } else {
            menuVisible = true;
         }
      }

      this.initMenu(
         null,
         ImageManager.getImage(Images.title530).getWidth() + CFG.PADDING * 2 + 1,
         CFG.GAME_HEIGHT - menuH - CFG.PADDING,
         CFG.BUTTON_WIDTH + 1,
         menuH,
         menuElements,
         Game.menuManager.getVisibleInGame_ProvinceArmy() && menuVisible
      );
      this.drawScrollPositionAlways = false;
      this.LAST_TIME_CHECK = CFG.currentTimeMillis;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() - 1, this.getHeight() - 1);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameProvinceArmy();
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_ProvinceArmy.nTranslateY;
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_ProvinceArmy.nTranslateY;
   }

   @Override
   public int getPosX() {
      return super.getPosX() + InGame_ProvinceArmy.nTranslateX;
   }

   @Override
   public int getMenuPosX() {
      return super.getMenuPosX() + InGame_ProvinceArmy.nTranslateX;
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut();
   }
}
