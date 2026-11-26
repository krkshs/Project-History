package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioGovernment_List extends Menu {
   public int iCivID = 0;

   public ScenarioGovernment_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int textPosX = CFG.PADDING * 4;
      if (Game.iActiveProvince >= 0) {
         this.iCivID = Game.getProvince(Game.iActiveProvince).getCivID();
      } else {
         this.iCivID = 0;
      }

      menuElements.add(
         new ButtonMain(Game.lang.get("CapitalCity"), 1, CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH, paddingLeft, buttonYPadding, menuWidth - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + ": " + Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.CCL;
            }
         }
      );
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("+", 1, -1, paddingLeft + (menuWidth - paddingLeft * 2) / 2, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
         @Override
         public void actionElement() {
            Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.CCL++;
         }
      });
      menuElements.add(
         new ButtonMain("-", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.CCL = Math.max(
                  0, Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.CCL - 1
               );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      menuElements.add(
         new ButtonMain(Game.lang.get("MilitaryAcademy"), 1, CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + ": " + Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAL;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("+", 1, -1, paddingLeft + (menuWidth - paddingLeft * 2) / 2, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
         @Override
         public void actionElement() {
            Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAL++;
         }
      });
      menuElements.add(
         new ButtonMain("-", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAL = Math.max(
                  0, Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAL - 1
               );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      menuElements.add(
         new ButtonMain(
            Game.lang.get("MilitaryAcademyForGenerals"), 1, CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true
         ) {
            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + ": " + Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAGL;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("+", 1, -1, paddingLeft + (menuWidth - paddingLeft * 2) / 2, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
         @Override
         public void actionElement() {
            Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAGL++;
         }
      });
      menuElements.add(
         new ButtonMain("-", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAGL = Math.max(
                  0, Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.MAGL - 1
               );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      menuElements.add(
         new ButtonMain(Game.lang.get("SupremeCourt"), 1, CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + ": " + Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.SCL;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("+", 1, -1, paddingLeft + (menuWidth - paddingLeft * 2) / 2, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
         @Override
         public void actionElement() {
            Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.SCL++;
         }
      });
      menuElements.add(
         new ButtonMain("-", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.SCL = Math.max(
                  0, Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.SCL - 1
               );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      menuElements.add(
         new ButtonMain(Game.lang.get("NuclearReactor"), 1, CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + ": " + Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.NRL;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("+", 1, -1, paddingLeft + (menuWidth - paddingLeft * 2) / 2, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
         @Override
         public void actionElement() {
            Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.NRL++;
         }
      });
      menuElements.add(
         new ButtonMain("-", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.NRL = Math.max(
                  0, Game.getCiv(ScenarioGovernment_List.this.iCivID).scenarioEditorData.NRL - 1
               );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         this.iCivID > 0
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
      this.getTitle().setText(Game.iActiveProvince >= 0 ? Game.getCiv(Game.getProvince(Game.iActiveProvince).getCivID()).getCivName() : Game.lang.get("None"));
   }
}
