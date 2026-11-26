package aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Slider;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioRelationsList extends Menu {
   public static int activeCivID = 0;
   public static int activeCivID2 = 0;

   public ScenarioRelationsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      menuElements.add(new ButtonGame(Game.lang.get("SelectCivilization"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
         @Override
         public void actionElement() {
            if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
               ScenarioRelationsList.activeCivID = Game.getProvince(Game.iActiveProvince).getCivID();
               Game.menuManager.rebuildInGame_ScenarioEditorRelations();
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Civilization"),
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
            Game.lang.get("Active") + ": " + Game.getCiv(activeCivID).getCivName(),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            activeCivID
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Slider(
            Game.lang.get("Opinion") + ": ",
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT,
            (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN,
            (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX,
            (int)Game.getCiv(activeCivID).diplomacy.getRelation(activeCivID2)
         ) {
            @Override
            public void actionElement() {
               if (ScenarioRelationsList.activeCivID > 0
                  && ScenarioRelationsList.activeCivID2 > 0
                  && ScenarioRelationsList.activeCivID != ScenarioRelationsList.activeCivID2) {
                  Game.getCiv(ScenarioRelationsList.activeCivID)
                     .diplomacy
                     .setRelation(ScenarioRelationsList.activeCivID, ScenarioRelationsList.activeCivID2, this.getCurrent());
                  Game.getCiv(ScenarioRelationsList.activeCivID2)
                     .diplomacy
                     .setRelation(ScenarioRelationsList.activeCivID2, ScenarioRelationsList.activeCivID, this.getCurrent());
               }
            }

            @Override
            public String getDrawText() {
               return super.getDrawText();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv(
            "" + Game.getCiv(activeCivID2).getCivName(),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            activeCivID2
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
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
      this.getTitle().setText(Game.lang.get("CustomizeRelations"));
   }
}
