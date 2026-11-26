package aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.war.WarManager;
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

public class ScenarioTrucesList extends Menu {
   public static int activeCivID = 0;
   public static int activeCivID2 = 0;

   public ScenarioTrucesList() {
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
            Game.lang.get("TruceWith"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
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
                  ScenarioTrucesList.activeCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                  Game.menuManager.rebuildInGame_ScenarioEditorTruces();
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
                  ScenarioTrucesList.activeCivID2 = Game.getProvince(Game.iActiveProvince).getCivID();
                  Game.menuManager.rebuildInGame_ScenarioEditorTruces();
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Confirm"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
            @Override
            public void actionElement() {
               if (ScenarioTrucesList.activeCivID > 0
                  && ScenarioTrucesList.activeCivID2 > 0
                  && ScenarioTrucesList.activeCivID != ScenarioTrucesList.activeCivID2) {
                  if (ScenarioDiplomacy.goBackTo == View.IN_GAME && DiplomacyManager.isAtWar(ScenarioTrucesList.activeCivID, ScenarioTrucesList.activeCivID2)) {
                     String warKey = WarManager.getWarKey(ScenarioTrucesList.activeCivID, ScenarioTrucesList.activeCivID2);
                     if (warKey != null && DiplomacyManager.whitePeace(warKey)) {
                        Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                           @Override
                           public void update() {
                              Game.menuManager.rebuildInGame_Wars();
                           }
                        });
                     }
                  }

                  DiplomacyManager.addTruce(ScenarioTrucesList.activeCivID, ScenarioTrucesList.activeCivID2);
                  ScenarioTrucesList.activeCivID2 = 0;
                  Game.menuManager.rebuildInGame_ScenarioEditorTruces();
                  Game.menuManager.addToastGold(Game.lang.get("Added"), Images.v);
               } else {
                  Game.menuManager.addToast_Error(Game.lang.get("SelectCivilization"));
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Truces") + ": " + Game.getCiv(activeCivID).getCivName(),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(activeCivID).diplomacy.truce.size() == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("Remove"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT3)
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         for (Diplomacy.DiplomacyData nData : Game.getCiv(activeCivID).diplomacy.truce.values()) {
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  Game.getCiv(nData.iCivID).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  CFG.BUTTON_HEIGHT4,
                  nData.iCivID
               ) {
                  @Override
                  public void actionElement() {
                     DiplomacyManager.removeTruce(ScenarioTrucesList.activeCivID, this.getCurrent());
                     Game.menuManager.rebuildInGame_ScenarioEditorTruces();
                     Game.menuManager.addToastGold(Game.lang.get("Removed"), Images.v);
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
      this.getTitle().setText(Game.lang.get("Truces"));
   }
}
