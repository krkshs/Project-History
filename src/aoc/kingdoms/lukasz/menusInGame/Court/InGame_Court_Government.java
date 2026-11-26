package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Formable;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology_Court;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_Active;
import aoc.kingdoms.lukasz.menu_element.button.Button_Capital;
import aoc.kingdoms.lukasz.menu_element.button.Button_MilitaryAcademy;
import aoc.kingdoms.lukasz.menu_element.button.Button_MilitaryAcademyForGenerals;
import aoc.kingdoms.lukasz.menu_element.button.Button_SupremeCourt;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageFull;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.InGame_MoveCapital_PopUp;
import aoc.kingdoms.lukasz.menusInGame.Budget.InGame_Budget;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Diplomacy.InGame_FormCiv;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Government extends Menu {
   public static List<Image> lFlags = new ArrayList<>();
   public static boolean reloadFlags = true;
   public static int modeID = 1;

   public InGame_Court_Government() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonY = 0;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int statH = (Button_Capital.getButtonHeight() - CFG.PADDING * 2) / 3;
      int c0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) / 2.0F);
      int c1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 3 / 5.0F);
      int c1W2 = menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5 - c1W;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      menuElements.add(
         new Text_Title_Diplomacy(
            Game.lang.get("Government"), Images.boxTitleBORDERWIDTH, buttonY, (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2, CFG.BUTTON_HEIGHT4, modeID == 0
         ) {
            @Override
            public void actionElement() {
               if (InGame_Court_Government.modeID == 0) {
                  InGame_Court_Government.modeID = 1;
               } else {
                  InGame_Court_Government.modeID = 0;
               }

               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Government_SavePos();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.government, CFG.PADDING, 0));
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
            Game.lang.get("Capital"),
            Images.boxTitleBORDERWIDTH + (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2,
            buttonY,
            (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 2,
            CFG.BUTTON_HEIGHT4,
            modeID == 1
         ) {
            @Override
            public void actionElement() {
               if (InGame_Court_Government.modeID == 0) {
                  InGame_Court_Government.modeID = 1;
               } else {
                  InGame_Court_Government.modeID = 0;
               }

               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Government_SavePos();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
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
      buttonX = paddingLeft + Button_Capital.getButtonHeight() + CFG.PADDING;
      if (modeID == 0) {
         buttonY += CFG.PADDING;
         int buttonBGY = buttonY - CFG.PADDING;
         List<MenuElement> nElementsBonuses = Game.ideologiesManager
            .getMenuElements(Game.getCiv(Game.player.iCivID).getIdeologyID(), buttonX, buttonY, menuWidth - paddingLeft - buttonX, statH);

         for (int i = 0; i < nElementsBonuses.size(); i++) {
            if (i > 2) {
               nElementsBonuses.get(i).setPosX(paddingLeft);
               nElementsBonuses.get(i).setWidth(menuWidth - paddingLeft * 2);
            }

            menuElements.add(nElementsBonuses.get(i));
         }

         menuElements.add(
            new ButtonIdeology_Court(
               Game.getCiv(Game.player.iCivID).getIdeologyID(), paddingLeft, buttonY, Button_Capital.getButtonHeight(), Button_Capital.getButtonHeight()
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = Game.ideologiesManager.getHoverIdeology(Game.getCiv(Game.player.iCivID).getIdeologyID(), true, false);
               }

               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 10) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_ChangeIdeology();
                  }
               }
            }
         );
         buttonY = 0;
         int i = 0;

         for (int iSize = menuElements.size(); i < iSize; i++) {
            buttonY = Math.max(buttonY, menuElements.get(i).getPosY() + menuElements.get(i).getHeight());
         }

         buttonY += CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
               CFG.PADDING + Images.boxTitleBORDERWIDTH, buttonBGY, menuWidth - (CFG.PADDING + Images.boxTitleBORDERWIDTH) * 2, buttonY - buttonBGY
            )
         );
         buttonY += CFG.PADDING;
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get("Religion"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               ""
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         buttonX = paddingLeft + Button_Capital.getButtonHeight() + CFG.PADDING;
         buttonY += CFG.PADDING;
         buttonBGY = buttonY - CFG.PADDING;
         nElementsBonuses.clear();
         nElementsBonuses = Game.religionManager
            .getMenuElements(Game.getCiv(Game.player.iCivID).getReligionID(), buttonX, buttonY, menuWidth - paddingLeft - buttonX, statH);
         if (nElementsBonuses.isEmpty()) {
            menuElements.add(
               new Text_StaticBG(
                  Game.lang.get("None"), CFG.FONT_REGULAR, -1, buttonX, buttonY, menuWidth - paddingLeft - buttonX, Button_Capital.getButtonHeight() * 3 / 5
               )
            );
         } else if (nElementsBonuses.size() == 1) {
            nElementsBonuses.get(0).setHeight(Button_Capital.getButtonHeight() * 3 / 5);
            menuElements.add(nElementsBonuses.get(0));
         } else {
            for (int ix = 0; ix < nElementsBonuses.size(); ix++) {
               if (ix > 2) {
                  nElementsBonuses.get(ix).setPosX(paddingLeft);
                  nElementsBonuses.get(ix).setWidth(menuWidth - paddingLeft * 2);
               }

               menuElements.add(nElementsBonuses.get(ix));
            }
         }

         menuElements.add(
            new ButtonReligion2(
               Game.getCiv(Game.player.iCivID).getReligionID(),
               paddingLeft,
               buttonY,
               Button_Capital.getButtonHeight(),
               Button_Capital.getButtonHeight() * 3 / 5
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 49) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_ChangeReligion();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = Game.religionManager.getHoverReligion(this.religionID, Game.player.iCivID);
               }
            }
         );
         buttonY = 0;
         i = 0;

         for (int iSize = menuElements.size(); i < iSize; i++) {
            buttonY = Math.max(buttonY, menuElements.get(i).getPosY() + menuElements.get(i).getHeight());
         }

         buttonY += CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
               CFG.PADDING + Images.boxTitleBORDERWIDTH, buttonBGY, menuWidth - (CFG.PADDING + Images.boxTitleBORDERWIDTH) * 2, buttonY - buttonBGY
            )
         );
         buttonY += CFG.PADDING;
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get("Stability"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               ""
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("CivilizationStability") + ": ",
               "" + CFG.getPrecision2((1.0F - Game.getCiv(Game.player.iCivID).civStability_LostFrom100) * 100.0F, 100) + "%",
               Images.civStability,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               ImageManager.getImage(Images.capital).getWidth() + CFG.PADDING * 2
            ) {
               float value = 0.0F;

               @Override
               public void setIsHovered(boolean isHovered) {
                  super.setIsHovered(isHovered);
                  if (CFG.isDesktop()) {
                     if (isHovered) {
                        if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_CIV_STABILITY_HOVER);
                        }
                     } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CIV_STABILITY_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }

               @Override
               public void actionElement() {
                  for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
                     if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).haveACore
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).coreCreation == null) {
                        InGame_CourtOptions2.actionCores(InGame_CourtOptions2.idCores);
                        return;
                     }
                  }

                  for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ix++) {
                     if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(ix)).getReligion() != Game.getCiv(Game.player.iCivID).getReligionID()
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(ix)).religionConversion == null) {
                        InGame_CourtOptions2.actionReligion(InGame_CourtOptions2.idReligion);
                        return;
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Civ.getHover_CivStability(Game.player.iCivID);
               }

               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).civStability_LostFrom100 > 0.0F
                     ? Colors.getColorNegative(false, this.getIsHovered())
                     : Colors.getColorPositive(false, this.getIsHovered());
               }

               @Override
               public String getTextToDraw() {
                  if (this.value != Game.getCiv(Game.player.iCivID).civStability_LostFrom100) {
                     this.setText2("" + CFG.getPrecision2((1.0F - Game.getCiv(Game.player.iCivID).civStability_LostFrom100) * 100.0F, 100) + "%");
                     this.value = Game.getCiv(Game.player.iCivID).civStability_LostFrom100;
                  }

                  return super.getTextToDraw();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("WarWeariness") + ": ",
               "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getWarWeariness(), 100) + "%",
               Images.weariness,
               paddingLeft,
               buttonY,
               c1W,
               buttonH,
               ImageManager.getImage(Images.capital).getWidth() + CFG.PADDING * 2
            ) {
               float value = 0.0F;

               @Override
               public void actionElement() {
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Civ.getHover_WarWeariness(Game.player.iCivID, false);
               }

               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getWarWeariness() < 0.01F
                     ? Colors.getColorPositive(false, this.getIsHovered())
                     : Colors.getColorNegative(false, this.getIsHovered());
               }

               @Override
               public String getTextToDraw() {
                  if (this.value != Game.getCiv(Game.player.iCivID).getWarWeariness()) {
                     this.setText2("" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getWarWeariness(), 100) + "%");
                     this.value = Game.getCiv(Game.player.iCivID).getWarWeariness();
                  }

                  return super.getTextToDraw();
               }
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRect_Active(Game.lang.get("Reduce"), buttonX, buttonY, c1W2, buttonH) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).fLegacy < GameValues.warWeariness.WW_REDUCE_COST_LEGACY) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientLegacy") + ": ", CFG.getPrecision2(GameValues.warWeariness.WW_REDUCE_COST_LEGACY, 100), Images.legacy
                        );
                  } else if (Game.getCiv(Game.player.iCivID).fGold < GameValues.warWeariness.WW_REDUCE_COST_GOLD) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(GameValues.warWeariness.WW_REDUCE_COST_GOLD, 100), Images.gold
                        );
                  } else if (Game.getCiv(Game.player.iCivID).getWarWeariness() < 0.01F) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("WarWeariness") + ": ",
                           CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getWarWeariness(), 1) + "%",
                           Images.weariness
                        );
                  } else if (Game.getCiv(Game.player.iCivID).reduceWarWeariness()) {
                     Game.menuManager.addToastGold(Game.lang.get("Done"), Images.v);
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Reduce") + ": ", Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("WarWeariness"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.weariness, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("WarWeariness") + ": ",
                        CFG.getPrecision2(GameValues.warWeariness.WW_REDUCE, 100),
                        Images.weariness,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Gold") + ": ",
                        CFG.getPrecision2(GameValues.warWeariness.WW_REDUCE_COST_GOLD, 100),
                        Images.gold,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_NEGATIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Legacy") + ": ",
                        CFG.getPrecision2(GameValues.warWeariness.WW_REDUCE_COST_LEGACY, 100),
                        Images.legacy,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_NEGATIVE
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
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AggressiveExpansion") + ": ",
               "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getAggressiveExpansion(), 100),
               Images.aggressiveExpansion,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               ImageManager.getImage(Images.capital).getWidth() + CFG.PADDING * 2
            ) {
               float value = 0.0F;

               @Override
               public void actionElement() {
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Civ.getHover_AggressiveExpansion(Game.player.iCivID, false);
               }

               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getAggressiveExpansion() < 0.01F
                     ? Colors.getColorPositive(false, this.getIsHovered())
                     : Colors.getColorNegative(false, this.getIsHovered());
               }

               @Override
               public String getTextToDraw() {
                  if (this.value != Game.getCiv(Game.player.iCivID).getAggressiveExpansion()) {
                     this.setText2("" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getAggressiveExpansion(), 100));
                     this.value = Game.getCiv(Game.player.iCivID).getAggressiveExpansion();
                  }

                  return super.getTextToDraw();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         buttonX = Images.boxTitleBORDERWIDTH;
         List<Integer> revolution = Game.revolutionManager.getRevolutionaryProvinces_Sorted(Game.player.iCivID);
         String revTextRight = (revolution.size() > 0 ? "" + revolution.size() : "") + " / " + Game.getCiv(Game.player.iCivID).getNumOfProvinces();
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get("RevolutionaryMovements"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               revTextRight
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         if (revolution.size() <= 0) {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, buttonH));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            for (int ix = 0; ix < revolution.size() && ix < GameValues.rebels.GOVERNMENT_VIEW_PROVINCES_UNREST_LIMIT; ix++) {
               menuElements.add(
                  new ButtonCurrentSituation(
                     Game.getProvince(revolution.get(ix)).getProvinceName(),
                     Images.revolutionRisk,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.BUTTON_WIDTH,
                     CFG.BUTTON_HEIGHT3,
                     ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
                     true
                  ) {
                     int id;

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = InGame_ProvinceInfo.getHoverUnrest(this.id, true, true);
                     }

                     @Override
                     public void setCurrent(int nCurrent) {
                        this.id = nCurrent;
                     }

                     @Override
                     public void actionElement() {
                        if (InGame_ProvinceInfo.actionUnrest(this.id)) {
                           Game.menuManager.rebuildInGame_Government_SavePos();
                           if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 45) {
                              Game.menuManager.setVisibleInGame_PopUp(false);
                           }
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        Game.mapCoords.centerToProvinceID(this.id);
                     }
                  }
               );
               menuElements.get(menuElements.size() - 1).setCurrent(revolution.get(ix));
               menuElements.add(
                  new ButtonStatsRect_Active(
                     "" + CFG.getPrecision2(Game.getProvince(revolution.get(ix)).getRevulutionaryRisk(), 100) + "%",
                     menuWidth - paddingLeft - CFG.BUTTON_WIDTH,
                     buttonY,
                     CFG.BUTTON_WIDTH,
                     CFG.BUTTON_HEIGHT3,
                     CFG.FONT_BOLD_SMALL
                  ) {
                     int id;
                     float lastValue = 0.0F;

                     @Override
                     public String getTextToDraw() {
                        if (Game.getProvince(this.id).getRevulutionaryRisk() != this.lastValue) {
                           this.lastValue = Game.getProvince(this.id).getRevulutionaryRisk();
                           this.setText("" + CFG.getPrecision2(this.lastValue, 100) + "%");
                        }

                        return super.getTextToDraw();
                     }

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = InGame_ProvinceInfo.getHoverUnrest(this.id, true, true);
                     }

                     @Override
                     public void setCurrent(int nCurrent) {
                        this.id = nCurrent;
                     }

                     @Override
                     protected Color getColor(boolean isActive) {
                        return Colors.getColorNegative(isActive, this.getIsHovered());
                     }

                     @Override
                     public void actionElement() {
                        if (InGame_ProvinceInfo.actionUnrest(this.id)) {
                           Game.menuManager.rebuildInGame_Government_SavePos();
                           if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 45) {
                              Game.menuManager.setVisibleInGame_PopUp(false);
                           }
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        Game.mapCoords.centerToProvinceID(this.id);
                     }
                  }
               );
               menuElements.get(menuElements.size() - 1).setCurrent(revolution.get(ix));
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }

            if (revolution.size() > 2) {
               menuElements.add(
                  new Text_StaticBG(
                     Game.lang.get("More") + ": " + (revolution.size() - 2), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, buttonH
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 45) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        } else {
                           Game.menuManager.setVisibleInGame_Court(false);
                           Game.menuManager.rebuildInGame_Revolutions();
                        }
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }
         }

         try {
            List<Integer> occupiedProvinceByRebels = new ArrayList<>();

            for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ix++) {
               if (Game.getProvinceData(Game.getCiv(Game.player.iCivID).getProvinceID(ix)).getOccupiedByCivID() < 0) {
                  occupiedProvinceByRebels.add(Game.getCiv(Game.player.iCivID).getProvinceID(ix));
               }
            }

            revTextRight = occupiedProvinceByRebels.size() + " / " + Game.getCiv(Game.player.iCivID).getNumOfProvinces();
            menuElements.add(
               new Text_Title_v2_TextLR(
                  Game.lang.get("Rebels") + ": " + Game.lang.get("OccupiedProvinces"),
                  CFG.BUTTON_WIDTH / 4,
                  Images.boxTitleBORDERWIDTH,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  revTextRight
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            if (occupiedProvinceByRebels.size() <= 0) {
               menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, buttonH));
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            } else {
               List<Integer> sortedByLiberation = new ArrayList<>();
               List<Integer> occupiedProvinceLiberation = new ArrayList<>();

               for (int ixx = 0; ixx < occupiedProvinceByRebels.size(); ixx++) {
                  occupiedProvinceLiberation.add(Game.revolutionManager.declareIndependence_TurnsLeft(occupiedProvinceByRebels.get(ixx)));
               }

               while (occupiedProvinceByRebels.size() > 0 && sortedByLiberation.size() < GameValues.rebels.GOVERNMENT_VIEW_PROVINCES_OCCUPIED_LIMIT) {
                  int bestID = 0;
                  int ixx = 1;

                  for (int iSize = occupiedProvinceByRebels.size(); ixx < iSize; ixx++) {
                     if (occupiedProvinceLiberation.get(bestID) > occupiedProvinceLiberation.get(ixx)) {
                        bestID = ixx;
                     }
                  }

                  sortedByLiberation.add(occupiedProvinceByRebels.get(bestID));
                  occupiedProvinceByRebels.remove(bestID);
                  occupiedProvinceLiberation.remove(bestID);
               }

               int ixx = 0;

               for (int iSizex = sortedByLiberation.size(); ixx < iSizex; ixx++) {
                  menuElements.add(
                     new ButtonCurrentSituation(
                        Game.getProvince(sortedByLiberation.get(ixx)).getProvinceName(),
                        Images.revolutionRisk,
                        paddingLeft,
                        buttonY,
                        menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.BUTTON_WIDTH,
                        CFG.BUTTON_HEIGHT3,
                        ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
                        true
                     ) {
                        int id;

                        @Override
                        public void buildElementHover() {
                           this.menuElementHover = InGame_ProvinceInfo.getHoverProvince(this.id);
                        }

                        @Override
                        public void setCurrent(int nCurrent) {
                           this.id = nCurrent;
                        }

                        @Override
                        public void actionElement() {
                           Game.mapCoords.centerToProvinceID(this.id);
                        }

                        @Override
                        public void actionElementPPM() {
                           Game.mapCoords.centerToProvinceID(this.id);
                        }
                     }
                  );
                  menuElements.get(menuElements.size() - 1).setCurrent(sortedByLiberation.get(ixx));
                  menuElements.add(
                     new ButtonStatsRect_Active(
                        "" + Game.lang.get("DaysX", Game.revolutionManager.declareIndependence_TurnsLeft(sortedByLiberation.get(ixx))),
                        menuWidth - paddingLeft - CFG.BUTTON_WIDTH,
                        buttonY,
                        CFG.BUTTON_WIDTH,
                        CFG.BUTTON_HEIGHT3,
                        CFG.FONT_BOLD_SMALL
                     ) {
                        int id;
                        int lastValue = 0;

                        @Override
                        public String getTextToDraw() {
                           if (Game.revolutionManager.declareIndependence_TurnsLeft(this.id) != this.lastValue) {
                              this.lastValue = Game.revolutionManager.declareIndependence_TurnsLeft(this.id);
                              if (this.lastValue == 0) {
                                 this.setText("---");
                              } else {
                                 this.setText("" + Game.lang.get("DaysX", this.lastValue));
                              }
                           }

                           return super.getTextToDraw();
                        }

                        @Override
                        public void buildElementHover() {
                           List<MenuElement_HoverElement> nElements = new ArrayList<>();
                           List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                           nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Liberation"), CFG.FONT_BOLD, Colors.HOVER_NEGATIVE));
                           nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.revolutionRisk, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(new MenuElement_HoverElement_Type_Line());
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(
                              new MenuElement_HoverElement_Type_Text(
                                 Game.lang.get("DaysX", Game.revolutionManager.declareIndependence_TurnsLeft(this.id)),
                                 CFG.FONT_REGULAR_SMALL,
                                 Colors.HOVER_LEFT
                              )
                           );
                           nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           this.menuElementHover = new MenuElement_Hover(nElements);
                        }

                        @Override
                        public void setCurrent(int nCurrent) {
                           this.id = nCurrent;
                        }

                        @Override
                        protected Color getColor(boolean isActive) {
                           return Colors.getColorNegative(isActive, this.getIsHovered());
                        }

                        @Override
                        public void actionElement() {
                           Game.mapCoords.centerToProvinceID(this.id);
                        }

                        @Override
                        public void actionElementPPM() {
                           Game.mapCoords.centerToProvinceID(this.id);
                        }
                     }
                  );
                  menuElements.get(menuElements.size() - 1).setCurrent(sortedByLiberation.get(ixx));
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }
         } catch (Exception var27) {
            CFG.exceptionStack(var27);
         }

         menuElements.add(
            new Text_Title_v2Center(
               Game.lang.get("FormableCivilizations"),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         if (Game.getCiv(Game.player.iCivID).sTagsCanForm.size() > 0) {
            buttonX = paddingLeft2;

            try {
               for (int ixx = 0; ixx < Game.getCiv(Game.player.iCivID).sTagsCanForm.size(); ixx++) {
                  buttonY += CFG.PADDING;
                  menuElements.add(new ButtonFlag_Formable(ixx, buttonX, buttonY) {
                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = FormableCivManager.getHover(this.getCurrent());
                     }

                     @Override
                     public void actionElement() {
                        Game.menuManager.setVisibleInGame_Court(false);
                        InGame_FormCiv.GO_BACK_TO_COURT = true;
                        Game.menuManager.rebuildInGame_FormCiv(this.getCurrent());
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_FORM_CIV);
                     }
                  });
                  buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  int bHeight = (ButtonFlag_Formable.getButtonHeight() - CFG.PADDING) / 2;
                  menuElements.add(
                     new Text_StaticBG_RulerTitle(
                        Game.lang.getCiv(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(ixx)), buttonX, buttonY, menuWidth - buttonX - paddingLeft2, bHeight
                     ) {
                        int id;

                        @Override
                        public int getCurrent() {
                           return this.id;
                        }

                        @Override
                        public void setCurrent(int nCurrent) {
                           this.id = nCurrent;
                        }

                        @Override
                        public void actionElement() {
                           Game.menuManager.setVisibleInGame_Court(false);
                           InGame_FormCiv.GO_BACK_TO_COURT = true;
                           Game.menuManager.rebuildInGame_FormCiv(this.getCurrent());
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_FORM_CIV);
                        }

                        @Override
                        public void buildElementHover() {
                           this.menuElementHover = FormableCivManager.getHover(this.getCurrent());
                        }
                     }
                  );
                  menuElements.get(menuElements.size() - 1).setCurrent(ixx);
                  menuElements.add(
                     new ButtonStatsRectIMG_Bonuses(
                        Game.lang.get("Provinces") + ": ",
                        Game.player.formableCivs.get(ixx).getControlledProvinces(Game.player.iCivID)
                           + " / "
                           + Game.player.formableCivs.get(ixx).getProvincesSize_WithoutNeutral(),
                        Images.provinces,
                        buttonX,
                        buttonY + bHeight + CFG.PADDING,
                        menuWidth - buttonX - paddingLeft2,
                        bHeight,
                        maxIconW
                     ) {
                        int id;

                        @Override
                        public int getCurrent() {
                           return this.id;
                        }

                        @Override
                        public void setCurrent(int nCurrent) {
                           this.id = nCurrent;
                        }

                        @Override
                        public void actionElement() {
                           Game.menuManager.setVisibleInGame_Court(false);
                           InGame_FormCiv.GO_BACK_TO_COURT = true;
                           Game.menuManager.rebuildInGame_FormCiv(this.getCurrent());
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_FORM_CIV);
                        }

                        @Override
                        public void buildElementHover() {
                           this.menuElementHover = FormableCivManager.getHover(this.getCurrent());
                        }

                        @Override
                        public Color getColorBonus() {
                           return Colors.HOVER_GOLD;
                        }
                     }
                  );
                  menuElements.get(menuElements.size() - 1).setCurrent(ixx);
                  buttonX = paddingLeft2;
                  buttonY += ButtonFlag_Formable.getButtonHeight() + CFG.PADDING;
                  menuElements.add(
                     new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
                        paddingLeft,
                        buttonY - (ButtonFlag_Formable.getButtonHeight() + CFG.PADDING * 2),
                        menuWidth - paddingLeft * 2,
                        ButtonFlag_Formable.getButtonHeight() + CFG.PADDING * 2
                     )
                  );
                  buttonY += CFG.PADDING;
               }
            } catch (Exception var26) {
               CFG.exceptionStack(var26);
            }
         } else {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, buttonH));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      } else {
         menuElements.add(
            new ButtonStatsRectIMG_Active_Click(
               Game.getCiv(Game.player.iCivID).getCapitalProvinceID() >= 0
                  ? Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getProvinceName()
                  : "--",
               Images.capital,
               paddingLeft,
               buttonY,
               c0W,
               buttonH,
               ImageManager.getImage(Images.capital).getWidth(),
               0
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getCapitalProvinceID() >= 0) {
                     if (Game.iActiveProvince == Game.getCiv(Game.player.iCivID).getCapitalProvinceID()) {
                        Game.menuManager.hideCourtCiv();
                        Game.menuManager.setVisibleInGame_Civ(false);
                        Game.setActiveProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                        Game.menuManager.rebuildInGame_ProvinceInfo(true);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     } else {
                        Game.mapCoords.centerToProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                        Game.setActiveProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  if (Game.getCiv(Game.player.iCivID).getCapitalProvinceID() >= 0) {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Capital") + ": "));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  } else {
                     this.menuElementHover = null;
                  }
               }
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonStatsRect_Active(Game.lang.get("MoveCapital"), buttonX, buttonY, c0W, buttonH) {
            @Override
            public void actionElement() {
               if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_MOVE_CAPITAL) {
                  Game.menuManager.addToastGold(Game.lang.get("ChooseAProvince"), Images.provinces);
                  InGame_MoveCapital_PopUp.toProvinceID = -1;
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_MOVE_CAPITAL);
               } else {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("MoveCapital"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         });
         int var56 = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), Game.lang.get("Cost") + ": 99999");
         int tButtonRightW = (int)Renderer.glyphLayout.width + CFG.PADDING * 4 + ImageManager.getImage(Images.gold).getWidth();
         int tButtonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Cost") + ": ",
               CFG.getPrecision2(Game.getCapital_Cost(Game.player.iCivID), 1),
               Images.gold,
               menuWidth - CFG.PADDING - tButtonRightW,
               var56 + 1 + (int)Math.ceil((CFG.TEXT_HEIGHT + CFG.PADDING * 6 - tButtonH) / 2.0F),
               tButtonRightW,
               tButtonH,
               ImageManager.getImage(Images.gold).getWidth(),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("CapitalCity"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               var56,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         int var57 = var56 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         int statsY = var57;
         int buttonBGY = var57 - CFG.PADDING;
         menuElements.add(
            new Button_Capital(paddingLeft2, var57) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getCapitalLevel() >= Game.getCapital_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
                           Images.capital
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 8) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeCapital();
                  }
               }
            }
         );
         buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int var58 = var57 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int statW = menuWidth - buttonX - paddingLeft2;
         menuElements.add(
            new Text_StaticBG_RulerTitle(
               Game.lang.get("Level") + ": " + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
               buttonX,
               statsY,
               statW,
               statH
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getCapitalLevel() >= Game.getCapital_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
                           Images.capital
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 8) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeCapital();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Court_Government.getHoverCapitalCity();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyIncome") + ": ",
               (Game.getCapital_Income(Game.player.iCivID) > 0.0F ? "+" : "") + CFG.getPrecision2(Game.getCapital_Income(Game.player.iCivID), 100),
               Images.gold,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getCapitalLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumResearch") + ": ",
               "" + CFG.getPrecision2(Game.getCapital_MaxResearch(Game.player.iCivID), 100),
               Game_Calendar.IMG_TECHNOLOGY,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getCapitalLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProvincesMaintenance") + ": ",
               "" + CFG.getPrecision2(Game.getCapital_ProvincesMaintenance(Game.player.iCivID) * 100.0F, 100) + "%",
               Images.provinces,
               paddingLeft,
               statsY,
               menuWidth - paddingLeft * 2,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getCapitalLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int var59 = var58 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonBGY, menuWidth - paddingLeft * 2, var59 - buttonBGY - CFG.PADDING));
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Cost") + ": ",
               CFG.getPrecision2(Game.getMilitaryAcademy_Cost(Game.player.iCivID), 1),
               Images.gold,
               menuWidth - CFG.PADDING - tButtonRightW,
               var59 + 1 + (int)Math.ceil((CFG.TEXT_HEIGHT + CFG.PADDING * 6 - tButtonH) / 2.0F),
               tButtonRightW,
               tButtonH,
               ImageManager.getImage(Images.gold).getWidth(),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("MilitaryAcademy"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               var59,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         int var60 = var59 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         buttonBGY = var60 - CFG.PADDING;
         menuElements.add(
            new Button_MilitaryAcademy(paddingLeft2, var60) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() >= Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
                           Game_Calendar.IMG_MANPOWER
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 2) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeMilitaryAcademy();
                  }
               }
            }
         );
         buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int var61 = var60 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         maxIconW = ImageManager.getImage(Images.gold).getWidth();
         statW = menuWidth - buttonX - paddingLeft;
         statH = (Button_MilitaryAcademy.getButtonHeight() - CFG.PADDING * 2) / 3;
         menuElements.add(
            new Text_StaticBG_RulerTitle(
               Game.lang.get("Level")
                  + ": "
                  + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel()
                  + " / "
                  + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
               buttonX,
               var60,
               statW,
               statH
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() >= Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
                           Game_Calendar.IMG_MANPOWER
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 2) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeMilitaryAcademy();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_RecruitArmy.getHoverMilitaryAcademy();
               }
            }
         );
         statsY = var60 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("UnitsAttack") + ": ",
               (Game.getMilitaryAcademy_Attack(Game.player.iCivID) > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getMilitaryAcademy_Attack(Game.player.iCivID), 100),
               Images.attack,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("UnitsDefense") + ": ",
               (Game.getMilitaryAcademy_Defense(Game.player.iCivID) > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getMilitaryAcademy_Defense(Game.player.iCivID), 100),
               Images.defense,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int iRegimentsLimitW = buttonX - (CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH) - CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG(
               "" + Game.getCiv(Game.player.iCivID).iRegimentsLimit,
               Images.regimentsLimit,
               CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
               var61,
               iRegimentsLimitW,
               statH,
               ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth()
            ) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv(Game.player.iCivID).iArmyRecruitSize_Total
                        > Game.getCiv(Game.player.iCivID).iRegimentsLimit
                     ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                     : super.getColor(isActive);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle("" + Game.getCiv(Game.player.iCivID).iRegimentsLimit, CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.regimentsLimit, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        " = "
                           + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENTS_LIMIT_BASE_VALUE
                           + " + "
                           + Game.lang.get("LocalManpower")
                           + " * "
                           + CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_MANPOWER_LEVEL, 100)
                           + " + "
                           + Game.lang.get("CivilizationBonuses"),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT3
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (Game.player.iCivID != Game.getCiv(Game.player.iCivID).getPuppetOfCivID()) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           Game.lang.get("Vassal") + ": " + CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_VASSAL * 100.0F, 10) + "%",
                           CFG.FONT_REGULAR_SMALL,
                           Colors.HOVER_RIGHT3
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.lang.get("WhenTheMaximumRegimentLimitIsReachedArmyRecruitmentCostIncreasesBy") + ": ", CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER * 100.0F, 10) + "%",
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("RegimentsLimit") + ": ",
               (Game.getMilitaryAcademy_RegimentsLimit(Game.player.iCivID) > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getMilitaryAcademy_RegimentsLimit(Game.player.iCivID), 1),
               Images.regimentsLimit,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int var62 = var61 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonBGY, menuWidth - paddingLeft * 2, var62 - buttonBGY - CFG.PADDING));
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Cost") + ": ",
               CFG.getPrecision2(Game.getMilitaryAcademyForGenerals_Cost(Game.player.iCivID), 1),
               Images.gold,
               menuWidth - CFG.PADDING - tButtonRightW,
               var62 + 1 + (int)Math.ceil((CFG.TEXT_HEIGHT + CFG.PADDING * 6 - tButtonH) / 2.0F),
               tButtonRightW,
               tButtonH,
               ImageManager.getImage(Images.gold).getWidth(),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("MilitaryAcademyForGenerals"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               var62,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         int var63 = var62 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         buttonBGY = var63 - CFG.PADDING;
         menuElements.add(
            new Button_MilitaryAcademyForGenerals(paddingLeft, var63) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel() >= Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           ""
                              + Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                              + " / "
                              + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
                           Images.general
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 3) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeMilitaryAcademyForGenerals();
                  }
               }
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int var64 = var63 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_RulerTitle(
               Game.lang.get("Level")
                  + ": "
                  + Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                  + " / "
                  + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
               buttonX,
               var63,
               statW,
               statH
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel() >= Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           ""
                              + Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                              + " / "
                              + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
                           Images.general
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 3) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeMilitaryAcademyForGenerals();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Generals.getHoverMilitaryAcademyForGenerals();
               }
            }
         );
         statsY = var63 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GeneralsAttack") + ": ",
               (Game.getMilitaryAcademyForGenerals_GeneralAttack(Game.player.iCivID) > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getMilitaryAcademyForGenerals_GeneralAttack(Game.player.iCivID), 100),
               Images.attack,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("GeneralsDefense") + ": ",
               (Game.getMilitaryAcademyForGenerals_GeneralDefense(Game.player.iCivID) > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getMilitaryAcademyForGenerals_GeneralDefense(Game.player.iCivID), 100),
               Images.defense,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel() == 0
                     ? Colors.getColorNeutral(this.getIsHovered(), this.getIsHovered())
                     : super.getColorBonus();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int var65 = var64 + CFG.PADDING;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonBGY, menuWidth - paddingLeft * 2, var65 - buttonBGY - CFG.PADDING));
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Cost") + ": ",
               CFG.getPrecision2(Game.getSupremeCourt_Cost(Game.player.iCivID), 1),
               Images.gold,
               menuWidth - CFG.PADDING - tButtonRightW,
               var65 + 1 + (int)Math.ceil((CFG.TEXT_HEIGHT + CFG.PADDING * 6 - tButtonH) / 2.0F),
               tButtonRightW,
               tButtonH,
               ImageManager.getImage(Images.gold).getWidth(),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("SupremeCourt"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               var65,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         int var66 = var65 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         buttonBGY = var66 - CFG.PADDING;
         menuElements.add(
            new Button_SupremeCourt(paddingLeft2, var66) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() >= Game.getSupremeCourt_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() + " / " + Game.getSupremeCourt_MaxLvl(Game.player.iCivID),
                           Images.stability
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 4) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeSupremeCourt();
                  }
               }
            }
         );
         buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int var67 = var66 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         maxIconW = ImageManager.getImage(Images.gold).getWidth();
         statW = menuWidth - buttonX - paddingLeft;
         statH = (Button_SupremeCourt.getButtonHeight() - CFG.PADDING * 2) / 3;
         menuElements.add(
            new Text_StaticBG_RulerTitle(
               Game.lang.get("Level") + ": " + Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() + " / " + Game.getSupremeCourt_MaxLvl(Game.player.iCivID),
               buttonX,
               var66,
               statW,
               statH
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() >= Game.getSupremeCourt_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() + " / " + Game.getSupremeCourt_MaxLvl(Game.player.iCivID),
                           Images.stability
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 4) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeSupremeCourt();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Court_IncreaseTaxEfficiency.getHoverSupremeCourt();
               }
            }
         );
         statsY = var66 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Corruption") + ": ",
               "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getCorruption() * 100.0F, 100) + "%",
               Images.corruption,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getCiv(Game.player.iCivID).getCorruption() > 0.0F ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
               }

               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() >= Game.getSupremeCourt_MaxLvl(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("MaximumLevel") + ": ",
                           "" + Game.getCiv(Game.player.iCivID).getSupremeCourtLevel() + " / " + Game.getSupremeCourt_MaxLvl(Game.player.iCivID),
                           Images.stability
                        );
                  } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 4) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_UpgradeSupremeCourt();
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Budget.getHoverCorruption();
               }
            }
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("CorruptionPerLevel") + ": ",
               "" + CFG.getPrecision2(GameValues.supremeCourt.SUPREME_COURT_CORRUPTION_REDUCTION_PER_LVL * 100.0F, 100) + "%",
               Images.corruption,
               buttonX,
               statsY,
               statW,
               statH,
               maxIconW
            )
         );
         statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         buttonY = var67 + CFG.PADDING;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonBGY, menuWidth - paddingLeft * 2, buttonY - buttonBGY - CFG.PADDING));
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("Government"));
      loadFormableFlags();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }

   public static MenuElement_Hover getHoverCapitalCity() {
      return getHoverCapitalCity(true, Game.player.iCivID);
   }

   public static MenuElement_Hover getHoverCapitalCity(boolean showUpgrade, int iCivID) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("CapitalCity"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_ImageFull(
            Images.buildingCapitalBig.get(Math.min(Game.getCiv(iCivID).getCapitalLevel(), GameValues.capital.CAPITAL_IMAGES - 1)), 0, 0
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Level") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.getCiv(iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(iCivID), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MonthlyIncome") + ": ",
            (Game.getCapital_Income(iCivID) > 0.0F ? "+" : "") + CFG.getPrecision2(Game.getCapital_Income(iCivID), 100),
            Images.gold,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getCapital_Income(iCivID) > 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MaximumResearch") + ": ",
            (Game.getCapital_MaxResearch(iCivID) > 0.0F ? "+" : "") + CFG.getPrecision2(Game.getCapital_MaxResearch(iCivID), 100),
            Game_Calendar.IMG_TECHNOLOGY,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getCapital_MaxResearch(iCivID) > 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("ProvincesMaintenance") + ": ",
            (Game.getCapital_ProvincesMaintenance(iCivID) > 0.0F ? "+" : "")
               + CFG.getPrecision2(Game.getCapital_ProvincesMaintenance(iCivID) * 100.0F, 100)
               + "%",
            Images.provinces,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getCapital_ProvincesMaintenance(iCivID) < 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (showUpgrade) {
         if (Game.getCiv(iCivID).getCapitalLevel() >= Game.getCapital_MaxLvl(iCivID)) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevel"), CFG.FONT_BOLD_SMALL));
            nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else {
            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UpgradeCapitalCity"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(new MenuElement_HoverElement_Type_Text(CFG.getPrecision2(Game.getCapital_Cost(iCivID), 100), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MonthlyIncomePerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2(GameValues.capital.CAPITAL_INCOME_PER_LVL, 100), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumResearch") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2(GameValues.capital.CAPITAL_MAX_RESEARCH_PER_LVL, 100), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ProvincesMaintenance") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + CFG.getPrecision2(GameValues.capital.CAPITAL_PROVINCES_MAINTENANCE_COST_PER_LVL * 100.0F, 100) + "%",
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      return new MenuElement_Hover(nElements);
   }

   public static final void upgradeCapital() {
      if (Game.getCiv(Game.player.iCivID).getCapitalLevel() >= Game.getCapital_MaxLvl(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("MaximumLevel") + ": ",
               "" + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
               Images.capital
            );
      } else if (Game.getCiv(Game.player.iCivID).fGold < Game.getCapital_Cost(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getCapital_Cost(Game.player.iCivID), 100), Images.gold);
      } else if (Game.getCiv(Game.player.iCivID).upgradeCapitalCity()) {
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.menuManager
            .rebuildInGame_Info(
               Game.lang.get("CapitalCity"),
               Game.lang.get("Level") + ": " + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID)
            );
         InGame_Info.imgID = Images.infoCrown;
         Game.getCiv(Game.player.iCivID).updateTotalIncomePerMonth();
      }
   }

   public static final void loadFormableFlags() {
      if (reloadFlags) {
         for (int i = 0; i < lFlags.size(); i++) {
            lFlags.get(i).dispose();
         }

         lFlags.clear();

         for (int i = 0; i < Game.getCiv(Game.player.iCivID).sTagsCanForm.size(); i++) {
            loadFlag(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(i));
         }

         reloadFlags = false;
      }
   }

   public static final void loadFlag(String civTag) {
      try {
         if (FileManager.loadFile("gfx/flagsXH/" + civTag + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + civTag + ".png")), TextureFilter.Linear));
         } else if (FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(civTag) + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(civTag) + ".png")), TextureFilter.Linear));
         } else if (FileManager.loadFile("gfx/flagsH/" + civTag + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + civTag + ".png")), TextureFilter.Linear));
         } else if (FileManager.loadFile("gfx/flagsH/" + Game.ideologiesManager.getRealTag(civTag) + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + Game.ideologiesManager.getRealTag(civTag) + ".png")), TextureFilter.Linear));
         } else if (FileManager.loadFile("gfx/flags/" + civTag + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + civTag + ".png")), TextureFilter.Nearest));
         } else if (FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(civTag) + ".png").exists()) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(civTag) + ".png")), TextureFilter.Nearest));
         } else {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/ran.png")), TextureFilter.Nearest));
         }
      } catch (Exception var2) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/ran.png")), TextureFilter.Nearest));
      }
   }
}
