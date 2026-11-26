package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_Diplomacy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioEconomy_List extends Menu {
   public String sDefault;
   public static ScenarioEconomy_List.ListMode listMode = ScenarioEconomy_List.ListMode.ECONOMY;
   public static int modeID = 0;

   public ScenarioEconomy_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int textPosX = CFG.PADDING * 4;
      menuElements.add(
         new Text_Title_Diplomacy(
            Game.lang.get("Name"), Images.boxTitleBORDERWIDTH, buttonY, (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2, CFG.BUTTON_HEIGHT4, modeID == 0
         ) {
            @Override
            public void actionElement() {
               if (ScenarioEconomy_List.modeID == 0) {
                  ScenarioEconomy_List.modeID = 1;
               } else {
                  ScenarioEconomy_List.modeID = 0;
               }

               Game.menuManager.setViewIDWithoutAnimation(View.SCENARIO_ECONOMY);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return Game.soundsManager.getTab();
            }
         }
      );
      menuElements.add(
         new Text_Title_Diplomacy(
            listMode == ScenarioEconomy_List.ListMode.MANPOWER ? Game.lang.get("MaximumManpower") : Game.lang.get("Income"),
            Images.boxTitleBORDERWIDTH + (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2,
            buttonY,
            (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2,
            CFG.BUTTON_HEIGHT4,
            modeID == 1
         ) {
            @Override
            public void actionElement() {
               if (ScenarioEconomy_List.modeID == 0) {
                  ScenarioEconomy_List.modeID = 1;
               } else {
                  ScenarioEconomy_List.modeID = 0;
               }

               Game.menuManager.setViewIDWithoutAnimation(View.SCENARIO_ECONOMY);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.outliner, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return Game.soundsManager.getTab();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      ScenarioPopulation_List.iCivID = -1;
      this.sDefault = Game.lang.get("Default");
      List<Integer> tCivs = new ArrayList<>();

      for (int i = 0; i < Game.getCivsSize(); i++) {
         tCivs.add(i);
      }

      while (tCivs.size() > 0) {
         int toAddID = 0;
         if (modeID != 0) {
            if (listMode == ScenarioEconomy_List.ListMode.MANPOWER) {
               int i = 1;

               for (int iSize = tCivs.size(); i < iSize; i++) {
                  if (Game.getCiv(tCivs.get(toAddID)).fManpowerMax < Game.getCiv(tCivs.get(i)).fManpowerMax) {
                     toAddID = i;
                  }
               }
            } else {
               int i = 1;

               for (int iSizex = tCivs.size(); i < iSizex; i++) {
                  if (Game.getCiv(tCivs.get(toAddID)).fTotalIncomePerMonth < Game.getCiv(tCivs.get(i)).fTotalIncomePerMonth) {
                     toAddID = i;
                  }
               }
            }
         } else {
            int i = 1;

            for (int iSizexx = tCivs.size(); i < iSizexx; i++) {
               for (int j = 0; j < Math.min(Game.getCiv(tCivs.get(toAddID)).getCivName().length(), Game.getCiv(tCivs.get(i)).getCivName().length()); j++) {
                  if (Game.getCiv(tCivs.get(toAddID)).getCivName().charAt(j) > Game.getCiv(tCivs.get(i)).getCivName().charAt(j)) {
                     toAddID = i;
                     break;
                  }

                  if (Game.getCiv(tCivs.get(toAddID)).getCivName().charAt(j) < Game.getCiv(tCivs.get(i)).getCivName().charAt(j)) {
                     break;
                  }
               }
            }
         }

         menuElements.add(
            new ButtonMain(
               Game.getCiv(tCivs.get(toAddID)).getCivName(),
               1,
               CFG.PADDING * 2 + CFG.CIV_FLAG_WIDTH,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH,
               true
            ) {
               @Override
               public void actionElement() {
                  if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.ECONOMY) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.Economy;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_ECONOMY, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.TAX_EFFICIENCY) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.TaxEff;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_TAX_EFFICIENCY, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.MANPOWER) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.Manpower;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_MANPOWER, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.GOLD) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.Gold;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_GOLD, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.LEGACY) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.Legacy;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_LEGACY, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.AI_AGGRESSIVENESS) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.v;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_AI_AGGRESSIVENESS, ScenarioPopulation_List.sPopulation);
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.NUKES) {
                     ScenarioPopulation_List.iCivID = this.getCurrent();
                     ScenarioPopulation_List.sPopulation = "" + Game.getCiv(this.getCurrent()).scenarioEditorData.Nukes;
                     Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CIV_NUKES, ScenarioPopulation_List.sPopulation);
                  }
               }

               @Override
               public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                  super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                  Game.getCiv(this.getCurrent())
                     .getFlag()
                     .draw(
                        oSB,
                        this.getPosX() + CFG.PADDING + iTranslateX,
                        this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY,
                        CFG.CIV_FLAG_WIDTH,
                        CFG.CIV_FLAG_HEIGHT
                     );
                  ImageManager.getImage(Images.flag_rect)
                     .draw(
                        oSB,
                        this.getPosX() + this.getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX,
                        this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY
                     );
               }

               @Override
               public String getTextToDraw() {
                  if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.ECONOMY) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.Economy == 0
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.Economy
                        );
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.TAX_EFFICIENCY) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.TaxEff == 0
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.TaxEff
                        );
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.MANPOWER) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.Manpower == 0
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.Manpower
                        );
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.AI_AGGRESSIVENESS) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.v == 0
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.v
                        );
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.GOLD) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.Gold == MapScenarios.DEFAULT_VALUE
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.Gold
                        );
                  } else if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.LEGACY) {
                     return super.getTextToDraw()
                        + ": "
                        + (
                           Game.getCiv(this.getCurrent()).scenarioEditorData.Legacy == MapScenarios.DEFAULT_VALUE
                              ? ScenarioEconomy_List.this.sDefault
                              : Game.getCiv(this.getCurrent()).scenarioEditorData.Legacy
                        );
                  } else {
                     return ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.NUKES
                        ? super.getTextToDraw()
                           + ": "
                           + (
                              Game.getCiv(this.getCurrent()).scenarioEditorData.Nukes == 0
                                 ? ScenarioEconomy_List.this.sDefault
                                 : Game.getCiv(this.getCurrent()).scenarioEditorData.Nukes
                           )
                        : super.getTextToDraw()
                           + ": "
                           + (
                              Game.getCiv(this.getCurrent()).scenarioEditorData.Economy == 0
                                 ? ScenarioEconomy_List.this.sDefault
                                 : Game.getCiv(this.getCurrent()).scenarioEditorData.Economy
                           );
                  }
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || ScenarioPopulation_List.iCivID == this.getCurrent();
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(tCivs.get(toAddID));
         menuElements.add(
            new ButtonMain(
               ""
                  + (
                     listMode == ScenarioEconomy_List.ListMode.MANPOWER
                        ? CFG.getShortNumber((int)Game.getCiv(tCivs.get(toAddID)).fManpowerMax)
                        : CFG.getPrecision2(Game.getCiv(tCivs.get(toAddID)).fTotalIncomePerMonth, 10)
                  ),
               1,
               -1,
               paddingLeft + menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH,
               buttonY,
               CFG.BUTTON_WIDTH,
               true
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  if (ScenarioEconomy_List.listMode == ScenarioEconomy_List.ListMode.MANPOWER) {
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumManpower") + ": ", CFG.FONT_BOLD));
                  } else {
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MonthlyIncome") + ": ", CFG.FONT_BOLD));
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         tCivs.remove(toAddID);
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
      if (listMode != ScenarioEconomy_List.ListMode.GOLD && listMode != ScenarioEconomy_List.ListMode.LEGACY) {
         this.getTitle().setText(Game.lang.get("Default") + ": 0");
      } else {
         this.getTitle().setText(Game.lang.get("Default") + ": " + MapScenarios.DEFAULT_VALUE);
      }
   }

   public static enum ListMode {
      ECONOMY,
      TAX_EFFICIENCY,
      MANPOWER,
      GOLD,
      LEGACY,
      NUKES,
      AI_AGGRESSIVENESS;
   }
}
