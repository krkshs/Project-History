package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapGeoRegionsList extends Menu {
   public EditorMapGeoRegionsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 5;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int i = 1;

      for (int iSize = Game.geographicalRegions.lGeographicalRegions.size(); i < iSize; i++) {
         menuElements.add(
            new ButtonMain(
               "" + Game.geographicalRegions.lGeographicalRegions.get(i).sName, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true
            ) {
               @Override
               public void actionElement() {
                  EditorMapGeoRegions.currentGeoRegionID = this.getCurrent();
               }

               @Override
               public boolean getCheckboxState() {
                  return this.getCurrent() == EditorMapGeoRegions.currentGeoRegionID;
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  oSB.setColor(
                     new Color(
                        Game.geographicalRegions.lGeographicalRegions.get(this.getCurrent()).iR / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(this.getCurrent()).iG / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(this.getCurrent()).iB / 255.0F,
                        1.0F
                     )
                  );
                  Images.pix
                     .draw(
                        oSB,
                        iTranslateX + this.getPosX() + CFG.PADDING,
                        iTranslateY + this.getPosY() + this.getHeight() / 2 - CFG.TEXT_HEIGHT / 2,
                        CFG.CIV_FLAG_WIDTH,
                        CFG.TEXT_HEIGHT
                     );
                  oSB.setColor(Color.WHITE);
                  super.drawText(oSB, iTranslateX, iTranslateY, isActive);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("Regions"), 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         true,
         false
      );
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
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Regions"));
   }
}
