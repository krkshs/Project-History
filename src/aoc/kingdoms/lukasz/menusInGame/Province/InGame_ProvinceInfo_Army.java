package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_PinnedArmy_Province;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceInfo_Army extends Menu {
   public long LAST_TIME_CHECK = 0L;
   public long TIME_CHECK = 1000L;

   public InGame_ProvinceInfo_Army() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = 0;
      int buttonY = 0;
      int menuH = ImageManager.getImage(Images.pinnedGeneralFrame).getHeight() + 1;
      if (InGame_ProvinceInfo.iProvinceID >= 0) {
         try {
            if (!Game.FOG_OF_WAR || Game.getProvince(InGame_ProvinceInfo.iProvinceID).getFogDrawArmy()) {
               for (int i = 0; i < Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmySize(); i++) {
                  if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(i).civID == Game.player.iCivID) {
                     menuElements.add(
                        new Button_PinnedArmy_Province(
                           Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(i).civID,
                           Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(i).key,
                           buttonX,
                           buttonY,
                           InGame_ProvinceInfo.iProvinceID
                        )
                     );
                     buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  }
               }

               for (int ix = 0; ix < Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmySize(); ix++) {
                  if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(ix).civID != Game.player.iCivID) {
                     menuElements.add(
                        new Button_PinnedArmy_Province(
                           Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(ix).civID,
                           Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(ix).key,
                           buttonX,
                           buttonY,
                           InGame_ProvinceInfo.iProvinceID
                        )
                     );
                     buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  }
               }
            }
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }
      }

      this.initMenu(
         null,
         ImageManager.getImage(Images.title580).getWidth() + CFG.PADDING + 1,
         CFG.GAME_HEIGHT - menuH,
         ImageManager.getImage(Images.pinnedGeneralFrame).getWidth() * 3 + CFG.PADDING * 2,
         menuH,
         menuElements,
         Game.menuManager.getVisibleInGame_ProvinceInfo()
      );
      this.drawScrollPositionAlways = false;
      this.LAST_TIME_CHECK = CFG.currentTimeMillis;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (this.getMenuElementsSize() == 0 && this.LAST_TIME_CHECK + this.TIME_CHECK < CFG.currentTimeMillis) {
         this.LAST_TIME_CHECK = CFG.currentTimeMillis;

         try {
            for (int i = 0; i < Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmySize(); i++) {
               if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getArmy(i).civID == Game.player.iCivID) {
                  Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_ProvinceInfo_Army") {
                     @Override
                     public void update() {
                        Game.menuManager.rebuildInGame_ProvinceInfo_Army();
                     }
                  });
                  return;
               }
            }
         } catch (Exception var7) {
            CFG.exceptionStack(var7);
         }
      }
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameProvinceInfo();
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_ProvinceInfo.nTranslateY;
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_ProvinceInfo.nTranslateY;
   }

   @Override
   public int getPosX() {
      return super.getPosX() + InGame_ProvinceInfo.nTranslateX;
   }

   @Override
   public int getMenuPosX() {
      return super.getMenuPosX() + InGame_ProvinceInfo.nTranslateX;
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut();
   }
}
