package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Value;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Infrastructure;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_DevelopInfrastructure extends Menu {
   public static int iSortID = 2;

   public InGame_Court_DevelopInfrastructure() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonY = 0;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.3F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
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
               if (InGame_Court_DevelopInfrastructure.iSortID == 0) {
                  InGame_Court_DevelopInfrastructure.iSortID = 1;
               } else {
                  InGame_Court_DevelopInfrastructure.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_DevelopInfrastructure();
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
            Game.lang.get("Infrastructure"),
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
               if (InGame_Court_DevelopInfrastructure.iSortID == 2) {
                  InGame_Court_DevelopInfrastructure.iSortID = 3;
               } else {
                  InGame_Court_DevelopInfrastructure.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_DevelopInfrastructure();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Infrastructure"), Colors.HOVER_GOLD));
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
            Game.lang.get("Cost") + ": " + Game.lang.get("LegacyPoints"),
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
               if (InGame_Court_DevelopInfrastructure.iSortID == 4) {
                  InGame_Court_DevelopInfrastructure.iSortID = 5;
               } else {
                  InGame_Court_DevelopInfrastructure.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_DevelopInfrastructure();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Cost") + ": " + Game.lang.get("LegacyPoints"), Colors.HOVER_GOLD));
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
               if (InGame_Court_DevelopInfrastructure.iSortID == 6) {
                  InGame_Court_DevelopInfrastructure.iSortID = 7;
               } else {
                  InGame_Court_DevelopInfrastructure.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_DevelopInfrastructure();
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
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.3F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.2F);
      List<Integer> tProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
         if (!Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).isOccupied()
            && Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).iInfrastructureMax
               > Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).getInfrastructure()
                  + Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).iProvinceDevelopInfrastructureSize) {
            tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(i));
         }
      }

      if (tProvinces.size() <= 0) {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("MaximumInfrastructureLevel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
            )
         );
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
                  if (Game.getProvince(tProvinces.get(toAddID)).getInfrastructure() < Game.getProvince(tProvinces.get(oxx)).getInfrastructure()
                     || Game.getProvince(tProvinces.get(toAddID)).getInfrastructure() == Game.getProvince(tProvinces.get(oxx)).getInfrastructure()
                        && Game.getProvince(tProvinces.get(toAddID)).iInfrastructureMax < Game.getProvince(tProvinces.get(oxx)).iInfrastructureMax) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID != 3) {
               if (iSortID == 4) {
                  for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                     if (Game.getDevelopInfrastructureCostLegacy(tProvinces.get(toAddID)) < Game.getDevelopInfrastructureCostLegacy(tProvinces.get(oxxx))) {
                        toAddID = oxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
                     if (Game.getDevelopInfrastructureCostLegacy(tProvinces.get(toAddID)) > Game.getDevelopInfrastructureCostLegacy(tProvinces.get(oxxxx))) {
                        toAddID = oxxxx;
                     }
                  }
               } else if (iSortID == 6) {
                  for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
                     if (Game.getDevelopInfrastructureCost(tProvinces.get(toAddID)) < Game.getDevelopInfrastructureCost(tProvinces.get(oxxxxx))) {
                        toAddID = oxxxxx;
                     }
                  }
               } else if (iSortID == 7) {
                  for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
                     if (Game.getDevelopInfrastructureCost(tProvinces.get(toAddID)) > Game.getDevelopInfrastructureCost(tProvinces.get(oxxxxxx))) {
                        toAddID = oxxxxxx;
                     }
                  }
               }
            } else {
               for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(toAddID)).getInfrastructure() > Game.getProvince(tProvinces.get(oxxxxxxx)).getInfrastructure()
                     || Game.getProvince(tProvinces.get(toAddID)).getInfrastructure() == Game.getProvince(tProvinces.get(oxxxxxxx)).getInfrastructure()
                        && Game.getProvince(tProvinces.get(toAddID)).iInfrastructureMax > Game.getProvince(tProvinces.get(oxxxxxxx)).iInfrastructureMax) {
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
               new Text_StaticBG_ID_Infrastructure(
                  "" + Game.getProvince(tProvinces.get(toAddID)).getInfrastructure() + " / " + Game.getProvince(tProvinces.get(toAddID)).iInfrastructureMax,
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r0W,
                  buttonH,
                  tProvinces.get(toAddID)
               )
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Value(
                  "" + Game.getDevelopInfrastructureCostLegacy(tProvinces.get(toAddID)),
                  Images.legacy,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonH,
                  ImageManager.getImage(Images.gold).getWidth(),
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.getProvince(this.getCurrent()).getCivID() == Game.player.iCivID
                        && Game.getProvince(this.getCurrent()).getCivID() == Game.player.iCivID
                        && InGame_ProvinceInfo.addDevelopInfrastructureCost(this.getCurrent())) {
                        ProvinceDraw.addProvinceDot_Infrastructure(this.getCurrent());
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_DevelopInfrastructure.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_DevelopInfrastructure.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.HOVER_LEFT;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverInfrastructure(this.getCurrent(), false);
                  }

                  @Override
                  public String getTextToDraw() {
                     if (this.lastValue != Game.getDevelopInfrastructureCostLegacy(this.id)) {
                        this.setText("" + CFG.getPrecision2(Game.getDevelopInfrastructureCostLegacy(this.id), 10));
                        this.lastValue = Game.getDevelopInfrastructureCostLegacy(this.id);
                     }

                     return this.sText;
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Value(
                  "" + Game.getDevelopInfrastructureCost(tProvinces.get(toAddID)),
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
                     if (Game.getProvince(this.getCurrent()).getCivID() == Game.player.iCivID
                        && InGame_ProvinceInfo.addDevelopInfrastructureCost(this.getCurrent())) {
                        ProvinceDraw.addProvinceDot_Infrastructure(this.getCurrent());
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_DevelopInfrastructure.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_DevelopInfrastructure.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.HOVER_LEFT;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverInfrastructure(this.getCurrent(), false);
                  }

                  @Override
                  public String getTextToDraw() {
                     if (this.lastValue != Game.getDevelopInfrastructureCost(this.id)) {
                        this.setText("" + CFG.getPrecision2(Game.getDevelopInfrastructureCost(this.id), 10));
                        this.lastValue = Game.getDevelopInfrastructureCost(this.id);
                     }

                     return this.sText;
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tProvinces.remove(toAddID);
         }
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("DevelopInfrastructure"));
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
