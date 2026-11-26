package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Init_SelectMap extends Menu {
   public Init_SelectMap() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;

      for (int i = 0; i < Game.map.lMaps.size(); i++) {
         int var10009 = CFG.LEFT_MENU_WIDTH - paddingLeft * 2;
         menuElements.add(new ButtonGame2(Game.map.lMaps.get(i).Folder, 0, -1, paddingLeft, buttonY, var10009, true) {
            int id = 0;

            @Override
            public void actionElement() {
               Game.map.setActiveMapID(this.id);
               Game.mapBG.iMapExtraScale = Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.ExtraMapScale;
               Game.mapBG.iMapScale = (int)(Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.DefaultMapScale * Game.mapBG.iMapExtraScale);
               Game.menuManager.setViewIDWithoutAnimation(View.INIT_GAME_MENU);
            }

            @Override
            public void setCurrent(int nCurrent) {
               this.id = nCurrent;
            }
         });
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("SelectMapType"), 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         true,
         false
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(0.047058824F, 0.047058824F, 0.047058824F, 1.0F));
      Images.pix.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
      InitGame.background
         .draw(
            oSB,
            iTranslateX + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2,
            iTranslateY + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2,
            InitGame.backgroundWidth,
            InitGame.backgroundHeight
         );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, iTranslateX, CFG.GAME_HEIGHT - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
      oSB.setColor(Color.WHITE);
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
