package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.terrain.TerrainManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapTerrainTypeList extends Menu {
   public EditorMapTerrainTypeList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int i = 1;

      for (int iSize = Game.terrainManager.terrains.size(); i < iSize; i++) {
         menuElements.add(
            new ButtonMain("" + Game.terrainManager.terrains.get(i).Name, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
               @Override
               public void actionElement() {
                  EditorMapTerrainType.currentTerrainTypeID = this.getCurrent();
               }

               @Override
               public boolean getCheckboxState() {
                  return this.getCurrent() == EditorMapTerrainType.currentTerrainTypeID;
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  oSB.setColor(
                     new Color(
                        Game.terrainManager.terrains.get(this.getCurrent()).Color[0],
                        Game.terrainManager.terrains.get(this.getCurrent()).Color[1],
                        Game.terrainManager.terrains.get(this.getCurrent()).Color[2],
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
                  Renderer.clipView_Start(
                     oSB,
                     this.getPosX() + this.getWidth() - ImageManager.getImage(Images.terrainSmall).getWidth() - CFG.PADDING + iTranslateX,
                     CFG.GAME_HEIGHT - (this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.terrainSmall).getHeight() / 2 + iTranslateY),
                     ImageManager.getImage(Images.terrainSmall).getWidth(),
                     -ImageManager.getImage(Images.terrainSmall).getHeight()
                  );
                  Game.terrainManager
                     .terrainImages
                     .get(this.getCurrent())
                     .get(0)
                     .draw(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - ImageManager.getImage(Images.terrainSmall).getWidth() / 2
                           - CFG.PADDING
                           - TerrainManager.terrainSmallWidth / 2
                           + iTranslateX,
                        this.getPosY() + this.getHeight() / 2 - TerrainManager.terrainSmallHeight / 2 + iTranslateY,
                        TerrainManager.terrainSmallWidth,
                        TerrainManager.terrainSmallHeight
                     );
                  ImageManager.getImage(Images.terrainSmall)
                     .draw(
                        oSB,
                        this.getPosX() + this.getWidth() - ImageManager.getImage(Images.terrainSmall).getWidth() - CFG.PADDING + iTranslateX,
                        this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.terrainSmall).getHeight() / 2 + iTranslateY
                     );
                  Renderer.clipView_End(oSB);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("TerrainType"), 1.0F, titleHeight, true, true),
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
      this.getTitle().setText(Game.lang.get("TerrainType"));
   }
}
