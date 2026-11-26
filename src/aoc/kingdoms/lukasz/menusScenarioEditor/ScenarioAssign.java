package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTopFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.menusEditor.GameCivs;
import aoc.kingdoms.lukasz.menusScenarioEditor.Wasteland.ScenarioWasteland;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioAssign extends Menu {
   public ScenarioAssign() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Minimap(0, 0) {
         @Override
         public int getPosX() {
            return CFG.GAME_WIDTH - this.getWidth();
         }

         @Override
         public int getPosY() {
            return CFG.GAME_HEIGHT - this.getHeight();
         }
      });
      menuElements.add(
         new Text_Static(Game.lang.get("AssignProvinces"), CFG.PADDING, CFG.BUTTON_HEIGHT + CFG.PADDING * 3) {
            @Override
            public int getPosX() {
               return CFG.GAME_WIDTH - CFG.PADDING * 4 - this.getTextWidth();
            }

            @Override
            public int getPosY() {
               return ScenarioAssign.this.getMenuElement(0).getPosY() - CFG.PADDING - this.getHeight();
            }

            @Override
            public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               Images.statsBG
                  .draw2(
                     oSB,
                     this.getPosX() - CFG.PADDING * 3 + iTranslateX,
                     this.getPosY() - CFG.PADDING * 2 + iTranslateY,
                     Images.statsBG.getWidth(),
                     this.getHeight() + CFG.PADDING * 4
                  );
               Images.statsBG2
                  .draw2(
                     oSB,
                     this.getPosX() - CFG.PADDING * 3 + Images.statsBG.getWidth() + iTranslateX,
                     this.getPosY() - CFG.PADDING * 2 + iTranslateY,
                     this.getWidth() + CFG.PADDING * 6 - Images.statsBG.getWidth(),
                     this.getHeight() + CFG.PADDING * 4,
                     true,
                     false
                  );
               super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Help")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AssignProvinces"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     Game.lang.get("ClickAProvinceOnTheMapToAssignProvinceToCivilization"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            GameCivs.sSearch = "";
            Game.mapBG.requestToDisposeMinimap = true;
            CFG.brushTool = false;
            ScenarioWasteland.lUndo.clear();
            Game.menuManager.setViewID(View.SCENARIO_CIVILIZATIONS);
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ManageCivilizations"), Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      menuElements.add(
         new ButtonMain("", 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               oSB.setColor(Colors.getColorTopStats(isActive, this.getIsHovered()));
               ImageManager.getImage(Images.information)
                  .draw(
                     oSB,
                     this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.information).getWidth() / 2 + iTranslateX,
                     this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.information).getHeight() / 2 + iTranslateY
                  );
               oSB.setColor(Color.WHITE);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Help")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AssignProvinces"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     Game.lang.get("ClickAProvinceOnTheMapToAssignProvinceToCivilization"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(new ButtonMain(null, 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Next"));
         }

         @Override
         public void actionElement() {
            Game.mapBG.requestToDisposeMinimap = true;
            CFG.brushTool = false;
            ScenarioWasteland.lUndo.clear();
            Game.menuManager.setViewID(View.SCENARIO_SETTINGS);
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ScenarioSettings"), Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      menuElements.add(new ButtonTopFlag(CFG.PADDING * 5 + CFG.BUTTON_WIDTH * 3 + Images.boxTitleBORDERWIDTH, CFG.PADDING * 2, true) {
         @Override
         protected void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
         }

         @Override
         public int getFlagCivID() {
            return CFG.iCreateScenario_AssignProvinces_Civ;
         }
      });
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            iTranslateX,
            iTranslateY,
            CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            true
         );
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH + iTranslateX,
            iTranslateY,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            false,
            true
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public static void actionUpdateData(boolean addUndo) {
      try {
         if (Game.iActiveProvince >= 0
            && !Game.getProvince(Game.iActiveProvince).getSeaProvince()
            && Game.getProvince(Game.iActiveProvince).getWasteland() < 0
            && Game.getProvince(Game.iActiveProvince).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ) {
            if (Game.getProvince(Game.iActiveProvince).isCapital) {
               if (CFG.brushTool) {
                  if (!CFG.actionBrush) {
                     Dialog.setDialogType(Dialog.DialogType.CREATE_SCENARIO_ASSIGN_CIVILIZATION);
                  }
               } else {
                  Dialog.setDialogType(Dialog.DialogType.CREATE_SCENARIO_ASSIGN_CIVILIZATION);
               }
            } else {
               if (addUndo) {
                  addUndo(Game.iActiveProvince);
               }

               if (Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                  int tCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                  Game.getProvince(Game.iActiveProvince).setCivID_RemoveOldAddNewToCiv(CFG.iCreateScenario_AssignProvinces_Civ);
                  Game.gameThreadUpdate.addSimpleTask(new Game.SimpleTask("buildCivilizationsRegion" + tCivID, tCivID) {
                     @Override
                     public void update() {
                        CivilizationRegionsManager.buildCivilizationsRegion(this.id);
                        Game.updateProvincesInView = true;
                        CivilizationRegionsManager.updateRegionsInView = true;
                     }
                  });
               } else {
                  Game.getProvince(Game.iActiveProvince).setCivID_RemoveOldAddNewToCiv(CFG.iCreateScenario_AssignProvinces_Civ);
               }

               Game.updateProvinceBorder(Game.iActiveProvince);
               if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                  Game.gameThreadUpdate
                     .addSimpleTask(
                        new Game.SimpleTask("buildCivilizationsRegion" + CFG.iCreateScenario_AssignProvinces_Civ, CFG.iCreateScenario_AssignProvinces_Civ) {
                           @Override
                           public void update() {
                              CivilizationRegionsManager.buildCivilizationsRegion(this.id);
                              Game.updateProvincesInView = true;
                              CivilizationRegionsManager.updateRegionsInView = true;
                           }
                        }
                     );
               }
            }
         }
      } catch (IndexOutOfBoundsException var2) {
         CFG.exceptionStack(var2);
      }
   }

   private static final void addUndo(int nProvinceID) {
      if (nProvinceID >= 0) {
         if (Game.getProvince(nProvinceID).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ) {
            if (ScenarioWasteland.lUndo.size() > 50) {
               ScenarioWasteland.lUndo.remove(0);
            }

            ScenarioWasteland.lUndo.add(new ScenarioWasteland.Undo(nProvinceID, Game.getProvince(nProvinceID).getCivID()));
         }
      }
   }

   public static void popUndo() {
      if (ScenarioWasteland.lUndo.size() > 0) {
         int tempCivID = CFG.iCreateScenario_AssignProvinces_Civ;
         Game.iActiveProvince = ScenarioWasteland.lUndo.get(ScenarioWasteland.lUndo.size() - 1).iProvinceID;
         CFG.iCreateScenario_AssignProvinces_Civ = ScenarioWasteland.lUndo.get(ScenarioWasteland.lUndo.size() - 1).iState;
         actionUpdateData(false);
         CFG.iCreateScenario_AssignProvinces_Civ = tempCivID;
         if (!Game.getProvince(Game.iActiveProvince).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
         }

         ScenarioWasteland.lUndo.remove(ScenarioWasteland.lUndo.size() - 1);
      }
   }
}
