package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.allianceHRE.Alliance;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style_Value;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioCreateAlliance_AlliancesList extends Menu {
   public ScenarioCreateAlliance_AlliancesList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      menuElements.add(new ButtonGame(Game.lang.get("CreateAlliance"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
         @Override
         public void actionElement() {
            ScenarioCreateAllianceList.editAlliance = new Alliance();
            Game.menuManager.setViewID(View.SCENARIO_CREATE_ALLIANCE_EDIT);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < Game.alliancesSpecial.size(); i++) {
         int var10006 = CFG.PADDING * 4;
         int var10009 = menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH - CFG.PADDING;
         menuElements.add(
            new ButtonGame_Style_Value(Game.alliancesSpecial.get(i).Name_Alliance, CFG.FONT_REGULAR, var10006, paddingLeft, buttonY, var10009, true, i) {
               @Override
               public void actionElement() {
                  ScenarioCreateAllianceList.editAlliance = Game.alliancesSpecial.get(this.getCurrent());
                  Game.menuManager.setViewID(View.SCENARIO_CREATE_ALLIANCE_EDIT);
               }
            }
         );
         menuElements.add(
            new ButtonGame_Style_Value(
               Game.lang.get("Remove"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH - CFG.PADDING) + CFG.PADDING,
               buttonY,
               CFG.BUTTON_WIDTH,
               true,
               i
            ) {
               @Override
               public void actionElement() {
                  Game.removeAllianceSpecial(this.getCurrent());
                  Game.menuManager.setViewID(View.SCENARIO_CREATE_ALLIANCE);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         true
      );
   }

   public final void drawEditorMenuBG(SpriteBatch oSB, int iX, int iY, int iWidth, int iHeight, int iTranslateX, int iTranslateY) {
      Renderer.drawBoxCorner(oSB, iX + iTranslateX, iY - this.getTitle().getHeight() + iTranslateY, iWidth, iHeight + this.getTitle().getHeight() + CFG.PADDING);
      Renderer.drawBox_EDGE_TOP_LR(oSB, Images.mainBox, iX + iTranslateX, iY + iTranslateY, iWidth, iHeight + CFG.PADDING, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight(), iTranslateX, iTranslateY);
      super.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, menuIsActive);
      super.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Alliances"));
   }
}
