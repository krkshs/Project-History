package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.allianceHRE.Alliance;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioCreateAllianceList extends Menu {
   public static Alliance editAlliance;
   public static String sMessageName = "";
   public static String sMessageLeader = "";
   public static String sMessageFirstTier = "";
   public static String sMessageRest = "";
   public static String sMessageFlag = "";

   public ScenarioCreateAllianceList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      sMessageName = editAlliance.Name_Alliance;
      sMessageLeader = editAlliance.Name_Leader;
      sMessageFirstTier = editAlliance.Name_FirstTier;
      sMessageRest = editAlliance.Name_Rest;
      sMessageFlag = editAlliance.FlagTag;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("AllianceName"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Name") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return ScenarioCreateAllianceList.sMessageName
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_NAME ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_NAME, ScenarioCreateAllianceList.sMessageName);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Name of the Alliance", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.alliance, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Example: ", CFG.FONT_BOLD, Colors.HOVER_LEFT));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Holy Roman Empire", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Flag") + ", " + Game.lang.get("Tag") + ": ",
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return ScenarioCreateAllianceList.sMessageFlag
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_FLAG ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_FLAG, ScenarioCreateAllianceList.sMessageFlag);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Flag of the Alliance", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.alliance, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Path: ", CFG.FONT_BOLD, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "gfx/flagsXH/" + ScenarioCreateAllianceList.sMessageFlag + ".png", CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonGame(
            "Type of the Alliance: " + editAlliance.getTypeOfAlliance_Name(),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH - CFG.PADDING,
            CFG.BUTTON_HEIGHT
         ) {
            @Override
            public void actionElement() {
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            ">>", paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_WIDTH - CFG.PADDING) + CFG.PADDING, buttonY, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT
         ) {
            @Override
            public void actionElement() {
               ScenarioCreateAllianceList.editAlliance.incTypeOfAlliance();
               Game.menuManager.rebuildInGame_ScenarioEditorCreateAlliance_Edit_SavePos();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Leader"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Name") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return ScenarioCreateAllianceList.sMessageLeader
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_LEADER ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_LEADER, ScenarioCreateAllianceList.sMessageLeader);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Name of the Leader", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.alliance, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Example: ", CFG.FONT_BOLD, Colors.HOVER_LEFT));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("Emperor", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv(
            editAlliance.iLeaderCivID == 0 ? Game.lang.get("SelectCivilization") : Game.getCiv(editAlliance.iLeaderCivID).getCivName(),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            editAlliance.iLeaderCivID
         ) {
            @Override
            public void actionElement() {
               if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                  ScenarioCreateAllianceList.editAlliance.setLeader(Game.getProvince(Game.iActiveProvince).getCivID());
                  this.id = ScenarioCreateAllianceList.editAlliance.iLeaderCivID;
                  this.setText(
                     ScenarioCreateAllianceList.editAlliance.iLeaderCivID == 0
                        ? Game.lang.get("SelectCivilization")
                        : Game.getCiv(ScenarioCreateAllianceList.editAlliance.iLeaderCivID).getCivName()
                  );
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SelectProvince"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Electors"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Name") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return ScenarioCreateAllianceList.sMessageFirstTier
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_FIRST_TIER ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_FIRST_TIER, ScenarioCreateAllianceList.sMessageFirstTier);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonGame(Game.lang.get("AddCivilization"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
         @Override
         public void actionElement() {
            if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
               ScenarioCreateAllianceList.editAlliance.addFirstTier(Game.getProvince(Game.iActiveProvince).getCivID());
               Game.menuManager.rebuildInGame_ScenarioEditorCreateAlliance_Edit_SavePos();
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SelectProvince"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < editAlliance.firstTier.size(); i++) {
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv(
               Game.getCiv(editAlliance.firstTier.get(i)).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               editAlliance.firstTier.get(i)
            ) {
               @Override
               public String getTextToDraw() {
                  return ScenarioCreateAllianceList.sMessageFirstTier + ": " + this.sText;
               }

               @Override
               public void actionElement() {
                  ScenarioCreateAllianceList.editAlliance.removeFirstTier(this.getCurrent());
                  Game.menuManager.addToastGold(Game.lang.get("Removed"), Images.v);
                  Game.menuManager.rebuildInGame_ScenarioEditorCreateAlliance_Edit_SavePos();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Remove"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.x, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Princes"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Name") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return ScenarioCreateAllianceList.sMessageRest
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_REST ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_CREATE_ALLIANCE_REST, ScenarioCreateAllianceList.sMessageRest);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonGame(Game.lang.get("AddCivilization"), paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
         @Override
         public void actionElement() {
            if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
               ScenarioCreateAllianceList.editAlliance.addSecondTier(Game.getProvince(Game.iActiveProvince).getCivID());
               Game.menuManager.rebuildInGame_ScenarioEditorCreateAlliance_Edit_SavePos();
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SelectProvince"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < editAlliance.secondTier.size(); i++) {
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv(
               Game.getCiv(editAlliance.secondTier.get(i)).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               editAlliance.secondTier.get(i)
            ) {
               @Override
               public String getTextToDraw() {
                  return ScenarioCreateAllianceList.sMessageRest + ": " + this.sText;
               }

               @Override
               public void actionElement() {
                  ScenarioCreateAllianceList.editAlliance.removeSecondTier(this.getCurrent());
                  Game.menuManager.addToastGold(Game.lang.get("Removed"), Images.v);
                  Game.menuManager.rebuildInGame_ScenarioEditorCreateAlliance_Edit_SavePos();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Remove"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.x, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
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
      this.getTitle().setText(Game.lang.get("CreateAlliance"));
   }
}
