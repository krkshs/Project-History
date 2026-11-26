package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.ExportScenarioAsMod;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style_Value;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.button.Button_PreviewSmall;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ScenarioPreview;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menus.LoadSave.Menu_LoadScenario;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenariosList extends Menu {
   public ScenariosList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int textPosX = CFG.PADDING * 4;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("CreateNewScenario"));
         }

         @Override
         public void actionElement() {
            Game.mapScenarios.createScenario();
            Game.mapBG.requestToDisposeMinimap = true;
            CFG.iCreateScenario_AssignProvinces_Civ = 0;
            Game.mapScenarios.loadReligions_JustBuild(true);
            Game.mapScenarios.scenarioEditor_isCampaign = false;
            Game.menuManager.setViewID(View.SCENARIO_WASTELAND_CONTINENTS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int leftW = (int)((CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2 - CFG.PADDING) * 0.65F);
      int rightW = CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2 - CFG.PADDING - leftW;

      for (int i = 0; i < Game.mapScenarios.SCENARIOS_SIZE; i++) {
         menuElements.add(
            new Button_PreviewSmall(
               Game.lang.get(Game.mapScenarios.details.get(i).Name),
               ""
                  + Game.mapScenarios.details.get(i).Day
                  + " "
                  + Game_Calendar.getMonthName(Game.mapScenarios.details.get(i).Month)
                  + " "
                  + Game.gameAges.getYear(Game.mapScenarios.details.get(i).Year),
               paddingLeft,
               buttonY,
               leftW,
               CFG.BUTTON_HEIGHT,
               i
            ) {
               @Override
               public void actionElement() {
                  ScenarioSettings.sAuthor = Game.mapScenarios.details.get(this.getCurrent()).Author;
                  ScenarioSettings.sTag = Game.mapScenarios.lScenarios_TagsList.get(this.getCurrent());
                  ScenarioSettings.sName = Game.mapScenarios.details.get(this.getCurrent()).Name;
                  Game.scenarioID = this.getCurrent();
                  CFG.iCreateScenario_AssignProvinces_Civ = 0;
                  Menu_LoadScenario.editorMode = true;
                  Menu_LoadScenario.goToMenu = View.SCENARIO_CIVILIZATIONS;
                  Game.menuManager.setViewIDWithoutAnimation(View.LOAD_SCENARIO);
               }
            }
         );
         menuElements.add(
            new ButtonGame_Style_Value(
               Game.lang.get("Generate") + ": " + Game.lang.get("Mod"),
               CFG.FONT_REGULAR_SMALL,
               -1,
               paddingLeft + leftW + CFG.PADDING,
               buttonY,
               rightW,
               CFG.BUTTON_HEIGHT,
               CFG.isDesktop()
                  && !Game.mapScenarios.details.get(i).Author.equals("Łukasz Jakowski")
                  && !Game.mapScenarios.details.get(i).Author.equals("Lukasz Jakowski"),
               i
            ) {
               @Override
               public void actionElement() {
                  ExportScenarioAsMod.exportScenario(Game.mapScenarios.lScenarios_TagsList.get(this.getCurrent()));
                  Game.menuManager.setViewID(View.WORKSHOP);
                  Game.menuManager.addToastGold(Game.lang.get("Done") + ": Now share the mod scenario to the Steam Workshop", Images.v);
                  Game.menuManager.addToastGold(Game.lang.get("Done") + ": Now share the mod scenario to the Steam Workshop", Images.v);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG("Export the scenario as a mod to the Steam Workshop", Colors.HOVER_GOLD));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        "The scenario will be automatically generated as a mod in the mods/ folder", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        "Exported mod won't include custom-made Civilizations if they are in the scenario!", CFG.FONT_REGULAR_SMALL, Colors.HOVER_IMPORTANT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (!this.getClickable()) {
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc(
                           "You cannot export official, unchanged game scenarios made by the Author of the game.",
                           CFG.FONT_REGULAR_SMALL,
                           Colors.HOVER_NEGATIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc(
                           "To export data as a mod, the author's name of the scenario must not be 'Łukasz Jakowski'.",
                           CFG.FONT_REGULAR_SMALL,
                           Colors.HOVER_NEGATIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc("Change the author name of the scenario.", CFG.FONT_REGULAR_SMALL, Colors.HOVER_NEGATIVE)
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_ScenarioPreview(this.getCurrent()));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.mapScenarios.details.get(this.getCurrent()).Day
                           + " "
                           + Game_Calendar.getMonthName(Game.mapScenarios.details.get(this.getCurrent()).Month)
                           + " "
                           + Game.mapScenarios.details.get(this.getCurrent()).Year,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_TITLE_2
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.gameAges.lAges.get(Game.mapScenarios.details.get(this.getCurrent()).Age).Name, CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilizations") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text("" + Game.mapScenarios.details.get(this.getCurrent()).Civs, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Author") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + Game.mapScenarios.details.get(this.getCurrent()).Author, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Tag") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + Game.mapScenarios.lScenarios_TagsList.get(this.getCurrent()), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH2,
         CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2,
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
      this.getTitle().setText(Game.lang.get("ScenarioEditor"));
   }
}
