package aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioVassalsList extends Menu {
   public static int activeCivID = 0;
   public static int activeCivID2 = 0;

   public ScenarioVassalsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("CreateAVassal"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv(
            activeCivID == 0 ? Game.lang.get("SelectCivilization") : Game.getCiv(activeCivID).getCivName(),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            activeCivID
         ) {
            @Override
            public void actionElement() {
               if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                  ScenarioVassalsList.activeCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                  Game.menuManager.rebuildInGame_ScenarioEditorVassals();
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv(
            activeCivID2 == 0 ? Game.lang.get("SelectCivilization") : Game.getCiv(activeCivID2).getCivName(),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            activeCivID2
         ) {
            @Override
            public void actionElement() {
               if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                  ScenarioVassalsList.activeCivID2 = Game.getProvince(Game.iActiveProvince).getCivID();
                  Game.menuManager.rebuildInGame_ScenarioEditorVassals();
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Confirm"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
            @Override
            public void actionElement() {
               if (ScenarioVassalsList.activeCivID > 0
                  && ScenarioVassalsList.activeCivID2 > 0
                  && ScenarioVassalsList.activeCivID != ScenarioVassalsList.activeCivID2) {
                  DiplomacyManager.addVassal(ScenarioVassalsList.activeCivID, ScenarioVassalsList.activeCivID2);
                  ScenarioVassalsList.activeCivID2 = 0;
                  Game.menuManager.rebuildInGame_ScenarioEditorVassals();
                  Game.menuManager.addToastGold(Game.lang.get("Added"), Images.v);
                  if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
                     Game.gameThread.addCivUpdateTotalIncomePerMonth(ScenarioVassalsList.activeCivID2);
                     Game.gameThread.addCivUpdateTotalIncomePerMonth(ScenarioVassalsList.activeCivID);
                     Game.gameThreadTurns.addCivUpdateMaxManpower(ScenarioVassalsList.activeCivID2);
                     Game.gameThreadTurns.addCivUpdateMaxManpower(ScenarioVassalsList.activeCivID);
                  }
               } else {
                  Game.menuManager.addToast_Error(Game.lang.get("SelectCivilization"));
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get(Game_Ages.getVassals()) + ": " + Game.getCiv(activeCivID).getCivName(),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(activeCivID).diplomacy.lVassals.size() == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("Remove"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT3)
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         for (int i = 0; i < Game.getCiv(activeCivID).diplomacy.lVassals.size(); i++) {
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  Game.getCiv(Game.getCiv(activeCivID).diplomacy.lVassals.get(i).c).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  CFG.BUTTON_HEIGHT4,
                  Game.getCiv(activeCivID).diplomacy.lVassals.get(i).c
               ) {
                  @Override
                  public void actionElement() {
                     DiplomacyManager.removeVassal(this.getCurrent());
                     Game.menuManager.rebuildInGame_ScenarioEditorVassals();
                     Game.menuManager.addToastGold(Game.lang.get("Removed"), Images.v);
                     if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
                        Game.gameThread.addCivUpdateTotalIncomePerMonth(ScenarioVassalsList.activeCivID2);
                        Game.gameThread.addCivUpdateTotalIncomePerMonth(ScenarioVassalsList.activeCivID);
                        Game.gameThreadTurns.addCivUpdateMaxManpower(ScenarioVassalsList.activeCivID2);
                        Game.gameThreadTurns.addCivUpdateMaxManpower(ScenarioVassalsList.activeCivID);
                     }
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
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
      this.getTitle().setText(Game.lang.get(Game_Ages.getVassals()));
   }
}
