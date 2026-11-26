package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding_Special;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Buildings2 extends Menu {
   public static Point_XY oBuildingID = null;
   public static int savePos_X = 0;
   public static int savePos_Y = 0;
   public static int saveMenuPos_X = 0;
   public static int saveMenuPos_Y = 0;
   public static boolean SHOW_BONUSES = true;
   public static int modeID = -1;

   public InGame_Court_Buildings2() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonY = 0;
      int tW0 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING) * 0.8F);
      int tW1 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING) * 0.2F);
      oBuildingID = null;
      int tempAdded = 0;
      int topButtonW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      Civilization civPlayer = Game.getCiv(Game.player.iCivID);
      int buildingsConstructedAll = 0;

      for (int i = 0; i < civPlayer.getNumOfProvinces(); i++) {
         buildingsConstructedAll += Game.getProvince(civPlayer.getProvinceID(i)).iBuildingsSize;
      }

      int titleW = (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 4;
      menuElements.add(new Text_Title_Diplomacy(Game.lang.get("All"), Images.boxTitleBORDERWIDTH, buttonY, titleW, CFG.BUTTON_HEIGHT4, modeID == -1) {
         @Override
         public void actionElement() {
            if (InGame_Court_Buildings2.modeID != -1) {
               InGame_Court_Buildings2.modeID = -1;
               Game.menuManager.rebuildInGame_Buildings2SavePos();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.buildings, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public int getSFX() {
            return Game.soundsManager.getTab();
         }
      });
      menuElements.add(
         new Text_Title_Diplomacy(Game.lang.get("Administration"), Images.boxTitleBORDERWIDTH + titleW, buttonY, titleW, CFG.BUTTON_HEIGHT4, modeID == 0) {
            @Override
            public void actionElement() {
               if (InGame_Court_Buildings2.modeID != 0) {
                  InGame_Court_Buildings2.modeID = 0;
                  Game.menuManager.rebuildInGame_Buildings2SavePos();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }
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
      menuElements.add(
         new Text_Title_Diplomacy(Game.lang.get("Military"), Images.boxTitleBORDERWIDTH + titleW * 2, buttonY, titleW, CFG.BUTTON_HEIGHT4, modeID == 1) {
            @Override
            public void actionElement() {
               if (InGame_Court_Buildings2.modeID != 1) {
                  InGame_Court_Buildings2.modeID = 1;
                  Game.menuManager.rebuildInGame_Buildings2SavePos();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
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
         new Text_Title_Diplomacy(Game.lang.get("Economy"), Images.boxTitleBORDERWIDTH + titleW * 3, buttonY, titleW, CFG.BUTTON_HEIGHT4, modeID == 2) {
            @Override
            public void actionElement() {
               if (InGame_Court_Buildings2.modeID != 2) {
                  InGame_Court_Buildings2.modeID = 2;
                  Game.menuManager.rebuildInGame_Buildings2SavePos();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonCurrentSituation(
            "" + CFG.getNumberWithSpaces("" + buildingsConstructedAll),
            Images.buildings,
            paddingLeft,
            buttonY,
            topButtonW,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BuildingsConstructed") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.buildings, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get("Bonuses"),
            SHOW_BONUSES ? Images.v : Images.x,
            paddingLeft + CFG.PADDING + topButtonW,
            buttonY,
            topButtonW,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court_Buildings2.SHOW_BONUSES = !InGame_Court_Buildings2.SHOW_BONUSES;
               Game.menuManager.rebuildInGame_Buildings2SavePos();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Bonuses"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.information, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return SoundsManager.getClickSound_CivOptions();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int x = modeID == -1 ? 0 : modeID; x < (modeID == -1 ? 3 : modeID + 1); x++) {
         menuElements.add(
            new Text_Title_v2Center(
               x == 0 ? Game.lang.get("Administration") : (x == 1 ? Game.lang.get("Military") : Game.lang.get("Economy")),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         tempAdded = 0;

         for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
            for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
               if (BuildingsManager.buildings.get(i).GroupID == x
                  && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                  && civPlayer.isBuildingResearched(i, j)
                  && (
                     BuildingsManager.buildings.get(i).RequiredGovernmentID < 0
                        || BuildingsManager.buildings.get(i).RequiredGovernmentID == civPlayer.getIdeologyID()
                  )
                  && (
                     BuildingsManager.buildings.get(i).RequiredReligionID < 0
                        || BuildingsManager.buildings.get(i).RequiredReligionID == civPlayer.getReligionID()
                  )) {
                  tempAdded++;
                  int tNum = 0;
                  int tPossible = 0;

                  for (int k = 0; k < civPlayer.getNumOfProvinces(); k++) {
                     if (!BuildingsManager.buildings.get(i).SeaAccessRequired || Game.getProvince(civPlayer.getProvinceID(k)).getLevelOfPort() >= 0) {
                        if (Game.getProvince(civPlayer.getProvinceID(k)).buildingBuilt(i, j)
                           || Game.getProvince(civPlayer.getProvinceID(k)).isUnderConstruction(i, j)) {
                           tNum++;
                        }

                        tPossible++;
                     }
                  }

                  int tempY = buttonY;
                  menuElements.add(
                     new ButtonBuilding_Special(
                        true, i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true, "" + tNum + " / " + tPossible, tNum > 0 && tNum == tPossible
                     ) {
                        @Override
                        public void actionElement() {
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                           InGame_Court_Buildings2.oBuildingID = new Point_XY(this.getValue1(), this.getValue2());
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_BUILDING);
                           Game.menuManager.savePosInGame_Buildings2();
                           Game.menuManager.rebuildInGame_Build();
                           Game.menuManager.setVisibleInGame_Court(true);
                           InGame_Court.lTime = 0L;
                        }

                        @Override
                        public void actionElementPPM() {
                           try {
                              int startedConstruction = 0;

                              for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ix++) {
                                 if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(ix))
                                    .addBuildingConstruction(this.getValue1(), this.getValue2())) {
                                    startedConstruction++;
                                 }
                              }

                              if (startedConstruction > 0) {
                                 Game.menuManager.addToastGold(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                              } else {
                                 Game.menuManager.addToast_Error(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                              }
                           } catch (Exception var3) {
                              CFG.exceptionStack(var3);
                           }
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                  if (SHOW_BONUSES) {
                     List<MenuElement> bonuses = ButtonBuilding.getBuildingBonuses(i, j, paddingLeft + CFG.PADDING, menuWidth);
                     if (!bonuses.isEmpty()) {
                        for (int a = 0; a < bonuses.size(); a++) {
                           bonuses.get(a).setPosY(buttonY);
                           menuElements.add(bonuses.get(a));
                           buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                        }

                        tempY = buttonY - tempY;
                        menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY - tempY, menuWidth - paddingLeft * 2, tempY));
                        buttonY += CFG.PADDING;
                     }
                  }
               }
            }
         }

         if (tempAdded == 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      }

      if (modeID == -1 || modeID == 2) {
         menuElements.add(
            new Text_Title_v2Center(
               Game.lang.get("Resources"),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         tempAdded = 0;

         for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
            for (int jx = 0; jx < BuildingsManager.buildingSize.get(i); jx++) {
               if (BuildingsManager.buildings.get(i).RequiredResource >= 0
                  && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                  && civPlayer.isBuildingResearched(i, jx)
                  && (
                     BuildingsManager.buildings.get(i).RequiredGovernmentID < 0
                        || BuildingsManager.buildings.get(i).RequiredGovernmentID == civPlayer.getIdeologyID()
                  )
                  && (
                     BuildingsManager.buildings.get(i).RequiredReligionID < 0
                        || BuildingsManager.buildings.get(i).RequiredReligionID == civPlayer.getReligionID()
                  )) {
                  int tNum = 0;
                  int tPossible = 0;

                  for (int kx = 0; kx < civPlayer.getNumOfProvinces(); kx++) {
                     if (Game.getProvince(civPlayer.getProvinceID(kx)).buildingBuilt(i, jx)
                        || Game.getProvince(civPlayer.getProvinceID(kx)).isUnderConstruction(i, jx)) {
                        tNum++;
                     }

                     if (Game.getProvince(civPlayer.getProvinceID(kx)).getResourceID() == BuildingsManager.buildings.get(i).RequiredResource) {
                        tPossible++;
                     }
                  }

                  if (tPossible > 0) {
                     tempAdded++;
                     int tempY = buttonY;
                     menuElements.add(
                        new ButtonBuilding_Special(
                           true,
                           i,
                           jx,
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingLeft * 2,
                           true,
                           true,
                           "" + tNum + " / " + tPossible,
                           tNum > 0 && tNum == tPossible
                        ) {
                           @Override
                           public void actionElement() {
                              Game.gameActiveProvince.resetLastActiveProvince();
                              Game.setActiveProvinceID(-1);
                              Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                              InGame_Court_Buildings2.oBuildingID = new Point_XY(this.getValue1(), this.getValue2());
                              Game.mapModes.setActiveViewID(Game.mapModes.MODE_BUILDING);
                              Game.menuManager.savePosInGame_Buildings2();
                              Game.menuManager.rebuildInGame_Build();
                              Game.menuManager.setVisibleInGame_Court(true);
                              InGame_Court.lTime = 0L;
                           }

                           @Override
                           public void actionElementPPM() {
                              try {
                                 int startedConstruction = 0;

                                 for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
                                    if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i))
                                       .addBuildingConstruction(this.getValue1(), this.getValue2())) {
                                       startedConstruction++;
                                    }
                                 }

                                 if (startedConstruction > 0) {
                                    Game.menuManager.addToastGold(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                                 } else {
                                    Game.menuManager.addToast_Error(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                                 }
                              } catch (Exception var3) {
                                 CFG.exceptionStack(var3);
                              }
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                     if (SHOW_BONUSES) {
                        List<MenuElement> bonuses = ButtonBuilding.getBuildingBonuses(i, jx, paddingLeft + CFG.PADDING, menuWidth);
                        if (!bonuses.isEmpty()) {
                           for (int a = 0; a < bonuses.size(); a++) {
                              bonuses.get(a).setPosY(buttonY);
                              menuElements.add(bonuses.get(a));
                              buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                           }

                           tempY = buttonY - tempY;
                           menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY - tempY, menuWidth - paddingLeft * 2, tempY));
                           buttonY += CFG.PADDING;
                        }
                     }
                  }
               }
            }
         }

         if (GameValues.inGame.SHOW_BUILDINGS_NO_PROVINCES_SIDEBAR) {
            if (modeID == -1) {
               boolean addedTitleNoProvinces = false;

               for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
                  for (int jxx = 0; jxx < BuildingsManager.buildingSize.get(i); jxx++) {
                     if (BuildingsManager.buildings.get(i).RequiredResource >= 0
                        && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                        && civPlayer.isBuildingResearched(i, jxx)) {
                        int tNum = 0;
                        int tPossible = 0;

                        for (int kx = 0; kx < civPlayer.getNumOfProvinces(); kx++) {
                           if (Game.getProvince(civPlayer.getProvinceID(kx)).buildingBuilt(i, jxx)
                              || Game.getProvince(civPlayer.getProvinceID(kx)).isUnderConstruction(i, jxx)) {
                              tNum++;
                           }

                           if (Game.getProvince(civPlayer.getProvinceID(kx)).getResourceID() == BuildingsManager.buildings.get(i).RequiredResource) {
                              tPossible++;
                           }
                        }

                        if (tPossible == 0) {
                           if (!addedTitleNoProvinces) {
                              menuElements.add(
                                 new Text_Title(
                                    Game.lang.get("Provinces") + ": 0",
                                    -1,
                                    Images.boxTitleBORDERWIDTH,
                                    buttonY,
                                    menuWidth - Images.boxTitleBORDERWIDTH * 2,
                                    CFG.TEXT_HEIGHT + CFG.PADDING * 6
                                 ) {
                                    @Override
                                    public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                                    }
                                 }
                              );
                              buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                              addedTitleNoProvinces = true;
                           }

                           tempAdded++;
                           int tempY = buttonY;
                           menuElements.add(
                              new ButtonBuilding_Special(
                                 true,
                                 i,
                                 jxx,
                                 paddingLeft,
                                 buttonY,
                                 menuWidth - paddingLeft * 2,
                                 true,
                                 true,
                                 "" + tNum + " / " + tPossible,
                                 tNum > 0 && tNum == tPossible
                              ) {
                                 @Override
                                 public void actionElement() {
                                    Game.gameActiveProvince.resetLastActiveProvince();
                                    Game.setActiveProvinceID(-1);
                                    Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                                    InGame_Court_Buildings2.oBuildingID = new Point_XY(this.getValue1(), this.getValue2());
                                    Game.mapModes.setActiveViewID(Game.mapModes.MODE_BUILDING);
                                    Game.menuManager.savePosInGame_Buildings2();
                                    Game.menuManager.rebuildInGame_Build();
                                    Game.menuManager.setVisibleInGame_Court(true);
                                    InGame_Court.lTime = 0L;
                                 }

                                 @Override
                                 public void actionElementPPM() {
                                    try {
                                       int startedConstruction = 0;

                                       for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
                                          if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i))
                                             .addBuildingConstruction(this.getValue1(), this.getValue2())) {
                                             startedConstruction++;
                                          }
                                       }

                                       if (startedConstruction > 0) {
                                          Game.menuManager.addToastGold(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                                       } else {
                                          Game.menuManager.addToast_Error(Game.lang.get("UnderConstruction") + ": " + startedConstruction, Images.build);
                                       }
                                    } catch (Exception var3) {
                                       CFG.exceptionStack(var3);
                                    }
                                 }
                              }
                           );
                           buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                           if (SHOW_BONUSES) {
                              List<MenuElement> bonuses = ButtonBuilding.getBuildingBonuses(i, jxx, paddingLeft + CFG.PADDING, menuWidth);
                              if (!bonuses.isEmpty()) {
                                 for (int a = 0; a < bonuses.size(); a++) {
                                    bonuses.get(a).setPosY(buttonY);
                                    menuElements.add(bonuses.get(a));
                                    buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                                 }

                                 tempY = buttonY - tempY;
                                 menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY - tempY, menuWidth - paddingLeft * 2, tempY));
                                 buttonY += CFG.PADDING;
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (tempAdded == 0) {
               menuElements.add(
                  new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            }
         }
      }

      if (GameValues.inGame.SHOW_TO_BE_RESEARCHED_BUILDINGS_SIDEBAR && modeID == -1) {
         menuElements.add(
            new Text_Title(
               Game.lang.get("ToBeResearched"),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         tempAdded = 0;

         for (int x = 0; x < 3; x++) {
            for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
               for (int jxxx = 0; jxxx < BuildingsManager.buildingSize.get(i); jxxx++) {
                  if (BuildingsManager.buildings.get(i).GroupID == x
                     && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                     && !civPlayer.isBuildingResearched(i, jxxx)) {
                     tempAdded++;
                     int tNum = 0;
                     int tPossible = 0;

                     for (int kx = 0; kx < civPlayer.getNumOfProvinces(); kx++) {
                        if (Game.getProvince(civPlayer.getProvinceID(kx)).buildingBuilt(i, jxxx)
                           || Game.getProvince(civPlayer.getProvinceID(kx)).isUnderConstruction(i, jxxx)) {
                           tNum++;
                        }

                        tPossible++;
                     }

                     menuElements.add(
                        new ButtonBuilding_Special(
                           true,
                           i,
                           jxxx,
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingLeft * 2,
                           true,
                           false,
                           "" + tNum + " / " + tPossible,
                           tNum > 0 && tNum == tPossible
                        ) {
                           @Override
                           public void actionElement() {
                              if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                                 Game.menuManager.setVisibleInGame_TechnologyTree(false);
                              } else {
                                 Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                                 InGame_TechnologyTree.centerToTechID = BuildingsManager.buildings.get(this.getValue1()).RequiredTechID[this.getValue2()];
                                 Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                              }
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                  }
               }
            }
         }

         if (tempAdded == 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }

         tempAdded = 0;
         menuElements.add(
            new Text_Title(
               Game.lang.get("ToBeResearched") + ": " + Game.lang.get("Resources"),
               -1,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

         for (int i = BuildingsManager.buildingsResourceStartID; i < BuildingsManager.buildingsResourceSize; i++) {
            for (int jxxxx = 0; jxxxx < BuildingsManager.buildingSize.get(i); jxxxx++) {
               if (BuildingsManager.buildings.get(i).RequiredResource >= 0
                  && !BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                  && !civPlayer.isBuildingResearched(i, jxxxx)) {
                  tempAdded++;
                  int tNum = 0;
                  int tPossible = 0;

                  for (int kx = 0; kx < civPlayer.getNumOfProvinces(); kx++) {
                     if (Game.getProvince(civPlayer.getProvinceID(kx)).buildingBuilt(i, jxxxx)
                        || Game.getProvince(civPlayer.getProvinceID(kx)).isUnderConstruction(i, jxxxx)) {
                        tNum++;
                     }

                     if (Game.getProvince(civPlayer.getProvinceID(kx)).getResourceID() == BuildingsManager.buildings.get(i).RequiredResource) {
                        tPossible++;
                     }
                  }

                  menuElements.add(
                     new ButtonBuilding_Special(
                        true,
                        i,
                        jxxxx,
                        paddingLeft,
                        buttonY,
                        menuWidth - paddingLeft * 2,
                        true,
                        false,
                        "" + tNum + " / " + tPossible,
                        tNum > 0 && tNum == tPossible
                     ) {
                        @Override
                        public void actionElement() {
                           if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                              Game.menuManager.setVisibleInGame_TechnologyTree(false);
                           } else {
                              Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                              InGame_TechnologyTree.centerToTechID = BuildingsManager.buildings.get(this.getValue1()).RequiredTechID[this.getValue2()];
                              Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                           }
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
               }
            }
         }

         if (tempAdded == 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("Buildings"));
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
      ImageManager.getImage(Images.civOptionsOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civOptionsOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civOptionsOver).getHeight())
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
}
