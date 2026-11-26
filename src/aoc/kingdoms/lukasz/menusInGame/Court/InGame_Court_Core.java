package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Value;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc2_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Core;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Core extends Menu {
   public static int iSortID = 0;
   public int iNumOfProvinces = 0;

   public InGame_Court_Core() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonY = CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.25F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.25F);
      if (Game.settingsManager.COUNCIL_TIPS) {
         menuElements.add(new Text_Desc2_Special(Game.lang.get("CoreIsALegitimatePartOfCivilization"), paddingLeft, buttonY, menuWidth - paddingLeft * 2) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorButton(isActive, this.getIsHovered());
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new ButtonGame(
            Game.lang.get("AddCore") + ": " + Game.lang.get("AllProvinces"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) * 7 / 10,
            true
         ) {
            @Override
            public void actionElement() {
               Dialog.setDialogType(Dialog.DialogType.CORE_ALL_PROVINCES);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               float fCost = 0.0F;
               int numOfProvinces = 0;

               for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
                  if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).isOccupied()
                     && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).coreCreation == null
                     && !Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).haveACore(Game.player.iCivID)) {
                     numOfProvinces++;
                     fCost += Game.getCoreCreationCost(Game.getCiv(Game.player.iCivID).getProvinceID(i));
                  }
               }

               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("AddCore") + ": " + Game.lang.get("AllProvinces"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.core, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + numOfProvinces, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + CFG.getPrecision2(fCost, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public boolean getClickable() {
               return InGame_Court_Core.this.iNumOfProvinces > 0;
            }
         }
      );
      float tCost = 0.0F;

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
         if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).isOccupied()
            && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).coreCreation == null
            && !Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).haveACore(Game.player.iCivID)) {
            tCost += Game.getCoreCreationCost(Game.getCiv(Game.player.iCivID).getProvinceID(i));
         }
      }

      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + CFG.getNumberWithSpaces(CFG.getPrecision2((float)((int)Math.ceil(tCost)), 1)),
            Images.gold,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) * 7 / 10,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 10,
            CFG.BUTTON_HEIGHT2,
            ImageManager.getImage(Images.gold).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               Dialog.setDialogType(Dialog.DialogType.CORE_ALL_PROVINCES);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               float fCost = 0.0F;
               int numOfProvinces = 0;

               for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
                  if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).isOccupied()
                     && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).coreCreation == null
                     && !Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).haveACore(Game.player.iCivID)) {
                     numOfProvinces++;
                     fCost += Game.getCoreCreationCost(Game.getCiv(Game.player.iCivID).getProvinceID(i));
                  }
               }

               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("AddCore") + ": " + Game.lang.get("AllProvinces"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.core, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + numOfProvinces, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + CFG.getPrecision2(fCost, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public boolean getClickable() {
               return InGame_Court_Core.this.iNumOfProvinces > 0;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1,
            iSortID == 1,
            Game.lang.get("Name"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Core.iSortID == 0) {
                  InGame_Court_Core.iSortID = 1;
               } else {
                  InGame_Court_Core.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Core();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Name"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 2 || iSortID == 3,
            iSortID == 3,
            Game.lang.get("Economy"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Core.iSortID == 2) {
                  InGame_Court_Core.iSortID = 3;
               } else {
                  InGame_Court_Core.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_Core();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Economy"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("ConstructionTime"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Core.iSortID == 4) {
                  InGame_Court_Core.iSortID = 5;
               } else {
                  InGame_Court_Core.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_Core();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ConstructionTime"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 6 || iSortID == 7,
            iSortID == 7,
            Game.lang.get("Cost"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Core.iSortID == 6) {
                  InGame_Court_Core.iSortID = 7;
               } else {
                  InGame_Court_Core.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_Core();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Cost"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.25F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.25F);
      List<Integer> tProvinces = new ArrayList<>();

      for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ix++) {
         if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(ix)).isOccupied()
            && !Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(ix)).haveACore(Game.player.iCivID)) {
            tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(ix));
         }
      }

      if (tProvinces.size() == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         while (tProvinces.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tProvinces.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(tProvinces.get(toAddID)).getProvinceName(), Game.getProvince(tProvinces.get(o)).getProvinceName()
                  )) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tProvinces.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(tProvinces.get(ox)).getProvinceName(), Game.getProvince(tProvinces.get(toAddID)).getProvinceName()
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tProvinces.size(); oxx++) {
                  if (Game.getProvince(tProvinces.get(oxx)).getEconomyWithBonuses() > Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses()) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxx)).getEconomyWithBonuses() < Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses()) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
                  if (Game.getCoreCreationTime(tProvinces.get(toAddID)) > Game.getCoreCreationTime(tProvinces.get(oxxxx))) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
                  if (Game.getCoreCreationTime(tProvinces.get(oxxxxx)) > Game.getCoreCreationTime(tProvinces.get(toAddID))) {
                     toAddID = oxxxxx;
                  }
               }
            } else if (iSortID == 6) {
               for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
                  if (Game.getCoreCreationCost(tProvinces.get(toAddID)) > Game.getCoreCreationCost(tProvinces.get(oxxxxxx))) {
                     toAddID = oxxxxxx;
                  }
               }
            } else if (iSortID == 7) {
               for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
                  if (Game.getCoreCreationCost(tProvinces.get(toAddID)) < Game.getCoreCreationCost(tProvinces.get(oxxxxxxx))) {
                     toAddID = oxxxxxxx;
                  }
               }
            }

            menuElements.add(
               new Text_StaticBG_ID(
                  Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  paddingLeft,
                  buttonY,
                  r0W,
                  buttonH,
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.iActiveProvince == this.getCurrent()) {
                        Game.menuManager.hideCourtCiv();
                        Game.menuManager.setVisibleInGame_Civ(false);
                        Game.setActiveProvinceID(this.getCurrent());
                        Game.menuManager.rebuildInGame_ProvinceInfo(true);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     } else {
                        Game.mapCoords.centerToProvinceID(this.getCurrent());
                        Game.setActiveProvinceID(this.getCurrent());
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverPopulation(this.getCurrent(), false);
                  }
               }
            );
            buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG(
                  "" + CFG.getPrecision2(Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses(), 100),
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r0W,
                  buttonH
               )
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG_Core(
                  "" + CFG.getPrecision2((float)Game.getCoreCreationTime(tProvinces.get(toAddID)), 10),
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonH,
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.getProvince(this.getCurrent()).getCivID() == Game.player.iCivID && Game.getProvince(this.getCurrent()).coreCreation == null) {
                        if (!Game.getProvince(this.getCurrent()).addCoreCreation()) {
                           Game.menuManager
                              .addToastInsufficient(
                                 Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getCoreCreationCost(this.getCurrent()), 100), Images.gold
                              );
                        } else {
                           MenuManager.addClickAnimation(
                              new ClickAnimation(
                                 this.getPosX() + this.getWidth() / 2 + InGame_Court_Core.this.getMenuPosX(),
                                 this.getPosY() + this.getHeight() / 2 + InGame_Court_Core.this.getMenuPosY(),
                                 this.getWidth(),
                                 this.getHeight()
                              ) {
                                 @Override
                                 public Color getColor() {
                                    return Colors.HOVER_POSITIVE;
                                 }
                              }
                           );
                        }
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverCores(this.getCurrent(), false);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Value(
                  "" + CFG.getPrecision2(Game.getCoreCreationCost(tProvinces.get(toAddID)), 100),
                  Images.gold,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonH,
                  ImageManager.getImage(Images.gold).getWidth(),
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.getProvince(this.getCurrent()).getCivID() == Game.player.iCivID && Game.getProvince(this.getCurrent()).coreCreation == null) {
                        if (!Game.getProvince(this.getCurrent()).addCoreCreation()) {
                           Game.menuManager
                              .addToastInsufficient(
                                 Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getCoreCreationCost(this.getCurrent()), 100), Images.gold
                              );
                        } else {
                           MenuManager.addClickAnimation(
                              new ClickAnimation(
                                 this.getPosX() + this.getWidth() / 2 + InGame_Court_Core.this.getMenuPosX(),
                                 this.getPosY() + this.getHeight() / 2 + InGame_Court_Core.this.getMenuPosY(),
                                 this.getWidth(),
                                 this.getHeight()
                              ) {
                                 @Override
                                 public Color getColor() {
                                    return Colors.HOVER_POSITIVE;
                                 }
                              }
                           );
                        }
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverCores(this.getCurrent(), false);
                  }

                  @Override
                  public String getTextToDraw() {
                     if (this.lastValue != Game.getCoreCreationCost(this.id)) {
                        this.setText("" + CFG.getPrecision2(Game.getCoreCreationCost(this.id), 100));
                        this.lastValue = Game.getCoreCreationCost(this.id);
                     }

                     return this.sText;
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            if (Game.getProvince(tProvinces.get(toAddID)).coreCreation == null) {
               this.iNumOfProvinces++;
            }

            tProvinces.remove(toAddID);
         }
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("CoreConstruction"));
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
}
