package aoc.kingdoms.lukasz.menusEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc_Simple;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menusMapEditor.EditorSelectProvinces;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Editor extends Menu {
   public Editor() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int textPosX = CFG.PADDING * 4;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, CFG.PADDING, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.MAINMENU);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(Game.lang.get("GameCivilizations"), 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            MainMenu.canContinue = false;
            Game.reloadScenario = true;
            Game.menuManager.setViewID(View.EDITOR_GAMECIVS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(Game.lang.get("CreateaCivilization"), 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            MainMenu.canContinue = false;
            Game.reloadScenario = true;
            CreateCiv.saveFlag = false;
            CreateCiv.goBackTo = View.EDITOR;
            Game.menuManager.setViewID(View.CREATE_CIV);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("CreateaScenario"));
         }

         @Override
         public void actionElement() {
            MainMenu.canContinue = false;
            Game.reloadScenario = true;
            Game.menuManager.setViewID(View.EDITOR_SCENARIOS_LIST);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("MapEditor"));
         }

         @Override
         public void actionElement() {
            MainMenu.canContinue = false;
            Game.reloadScenario = true;
            CFG.selectMode = true;
            MenuManager.mapEditorDrawProvinces = true;
            Game.menuManager.setViewID(View.EDITOR_MAPS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 3;
      menuElements.add(
         new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("ManageMods"));
            }

            @Override
            public void actionElement() {
               Game.menuManager.setViewID(View.MANAGE_MODS);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("ManageMods"), "", Images.technology2, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("SteamWorkshop"));
            }

            @Override
            public void actionElement() {
               Game.menuManager.setViewID(View.WORKSHOP);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("SubmitYourModsToTheSteamWorkshop"),
                     "",
                     Images.technology2,
                     CFG.FONT_REGULAR,
                     CFG.FONT_REGULAR,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new Text_Desc_Simple(
            "All modifications, custom content, and user-generated assets created within or for the game are the sole property of Łukasz Jakowski Games. By creating or uploading any mods, you agree that Łukasz Jakowski Games retains full ownership and rights to use, modify, distribute, or remove the content at its discretion. Mod creators waive any claim to ownership or compensation for their creations.",
            paddingLeft,
            buttonY,
            CFG.LEFT_MENU_WIDTH - paddingLeft * 2
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.addToastGold("Terms of Use", Images.technology2);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("SelectProvinces"));
         }

         @Override
         public void actionElement() {
            CFG.selectMode = true;
            MenuManager.mapEditorDrawProvinces = true;
            EditorSelectProvinces.selectedProvinces.clear();
            Game.menuManager.setViewID(View.EDITOR_SELECT_PROVINCES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText("www.LukaszJakowski.pl");
         }

         @Override
         public void actionElement() {
            Dialog.GO_TO_LINK = "https://lukaszjakowski.pl/";
            Dialog.setDialogType(Dialog.DialogType.GO_TO_LINK);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      buttonY += menuElements.get(0).getHeight() + buttonYPadding * 2;
      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2),
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
      this.getTitle().setText(Game.lang.get("GameEditor"));
   }
}
