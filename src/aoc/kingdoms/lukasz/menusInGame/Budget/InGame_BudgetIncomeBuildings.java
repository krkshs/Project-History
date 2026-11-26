package aoc.kingdoms.lukasz.menusInGame.Budget;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Balance;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Right2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_BudgetIncomeBuildings extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public int iActiveCivID;
   public static int iSortID = 4;

   public InGame_BudgetIncomeBuildings() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      this.iActiveCivID = Game.player.iCivID;
      int tIconMaxW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int tButtonH2 = CFG.BUTTON_HEIGHT * 4 / 5;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "+999 999");
      int tButtonRightW = (int)Renderer.glyphLayout.width + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonW = menuWidth - paddingLeft * 2 - CFG.PADDING - tButtonRightW;
      int iRow = 0;
      menuElements.add(
         new ButtonStatsBudget(Game.lang.get("TotalIncome"), Images.goldPositive, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW, iRow++ % 2 == 0)
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "+" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth + Game.getCiv(this.iActiveCivID).civBonuses.MonthlyIncome, 100),
            paddingLeft + tButtonW + CFG.PADDING,
            buttonY,
            tButtonRightW,
            tButtonH
         ) {
            @Override
            public String getTextToDraw() {
               if (this.lastValue
                  != Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
                     + Game.getCiv(InGame_BudgetIncomeBuildings.this.iActiveCivID).civBonuses.MonthlyIncome) {
                  float fVal = Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
                     + Game.getCiv(InGame_BudgetIncomeBuildings.this.iActiveCivID).civBonuses.MonthlyIncome;
                  this.setText((fVal > 0.0F ? "+" : "") + CFG.getPrecision2(fVal, 100));
                  this.lastValue = fVal;
                  if (fVal == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fVal > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  }
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      float fGold = Game.getCiv(this.iActiveCivID).fTotalExpensesPerMonth;
      menuElements.add(
         new ButtonStatsBudget(Game.lang.get("TotalExpenses"), Images.goldNegative, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW, iRow++ % 2 == 0)
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            (fGold >= 0.0F ? "+" : "-") + CFG.getPrecision2(Game.getCiv(this.iActiveCivID).fTotalExpensesPerMonth, 100),
            paddingLeft + tButtonW + CFG.PADDING,
            buttonY,
            tButtonRightW,
            tButtonH
         ) {
            @Override
            public String getTextToDraw() {
               if (this.lastValue != Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth) {
                  float fVal = Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth;
                  this.setText((fVal > 0.0F ? "-" : "") + CFG.getPrecision2(fVal, 100));
                  this.lastValue = fVal;
                  if (fVal == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fVal > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  }
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      fGold = Game.getCiv(this.iActiveCivID).fTotalIncomePerMonth
         + Game.getCiv(this.iActiveCivID).civBonuses.MonthlyIncome
         - Game.getCiv(this.iActiveCivID).fTotalExpensesPerMonth;
      menuElements.add(
         new ButtonStatsBudget_Balance(
            Game.lang.get("Balance") + ": ",
            (fGold > 0.0F ? "+" : "") + CFG.getPrecision2(fGold, 100),
            Images.gold,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 10
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Budget();
               Game.menuManager.setVisibleInGame_Budget(true);
               InGame_Budget.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.35F);
      int r0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.45F);
      int r0W3 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
      int r1W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.35F);
      int r1W2 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.45F);
      int r1W3 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.2F);
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
               if (InGame_BudgetIncomeBuildings.iSortID == 0) {
                  InGame_BudgetIncomeBuildings.iSortID = 1;
               } else {
                  InGame_BudgetIncomeBuildings.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_BudgetIncomeBuildings();
               InGame_BudgetIncomeBuildings.lTime = 0L;
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
      menuElements.add(new Text_TitleBlue(Game.lang.get("Building"), -1, buttonX, buttonY, r0W2, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_BOLD_SMALL) {
         @Override
         public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
         }
      });
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Income"),
            -1,
            buttonX,
            buttonY,
            r0W3,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_BudgetIncomeBuildings.iSortID == 4) {
                  InGame_BudgetIncomeBuildings.iSortID = 5;
               } else {
                  InGame_BudgetIncomeBuildings.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_BudgetIncomeBuildings();
               InGame_BudgetIncomeBuildings.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Income"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      List<Integer> tProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).provBonuses.MonthlyIncome > 0.0F) {
            tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(i));
         }
      }

      if (tProvinces.size() > 0) {
         int tID = 1;

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
            } else if (iSortID == 4) {
               for (int oxx = 1; oxx < tProvinces.size(); oxx++) {
                  if (Game.getProvince(tProvinces.get(oxx)).provBonuses.MonthlyIncome > Game.getProvince(tProvinces.get(toAddID)).provBonuses.MonthlyIncome) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxx)).provBonuses.MonthlyIncome < Game.getProvince(tProvinces.get(toAddID)).provBonuses.MonthlyIncome) {
                     toAddID = oxxx;
                  }
               }
            }

            if (Game.getProvince(tProvinces.get(toAddID)).isCapital) {
               menuElements.add(
                  new Text_StaticBG_ID(
                     "" + tID++ + ". " + Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                     CFG.FONT_BOLD_SMALL,
                     CFG.PADDING * 2,
                     paddingLeft,
                     buttonY,
                     r1W,
                     buttonH,
                     tProvinces.get(toAddID)
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.iActiveProvince == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_Budget(false);
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
                        this.menuElementHover = InGame_ProvinceInfo.getHoverProvince(this.id);
                     }
                  }
               );
               buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG_ID(Game.lang.get("Capital"), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH, tProvinces.get(toAddID))
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               fGold = GameValues.capital.CAPITAL_MONTHLY_INCOME;
               menuElements.add(
                  new ButtonStatsBudget_Right2(
                     (fGold > 0.0F ? "+" : "") + CFG.getPrecision2(fGold, 100), buttonX, buttonY, r1W3, buttonH, tProvinces.get(toAddID)
                  )
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }

            for (int ix = 0; ix < Game.getProvince(tProvinces.get(toAddID)).iBuildingsSize; ix++) {
               if (BuildingsManager.buildings.get(Game.getProvince(tProvinces.get(toAddID)).getBuildings(ix).getBuilding()).MonthlyIncome != null
                  && BuildingsManager.buildings.get(Game.getProvince(tProvinces.get(toAddID)).getBuildings(ix).getBuilding()).MonthlyIncome[Game.getProvince(
                           tProvinces.get(toAddID)
                        )
                        .getBuildings(ix)
                        .getBuildingID()]
                     != 0.0F) {
                  menuElements.add(
                     new Text_StaticBG_ID(
                        "" + tID++ + ". " + Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                        Game.getProvince(tProvinces.get(toAddID)).isCapital ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                        CFG.PADDING * 2,
                        paddingLeft,
                        buttonY,
                        r1W,
                        buttonH,
                        tProvinces.get(toAddID)
                     ) {
                        @Override
                        public void actionElement() {
                           if (Game.iActiveProvince == this.getCurrent()) {
                              Game.menuManager.setVisibleInGame_Budget(false);
                              Game.setActiveProvinceID(this.getCurrent());
                              Game.menuManager.rebuildInGame_ProvinceInfo(true);
                              ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                           } else {
                              Game.mapCoords.centerToProvinceID(this.getCurrent());
                              Game.setActiveProvinceID(this.getCurrent());
                              ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                           }
                        }
                     }
                  );
                  buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  menuElements.add(
                     new Text_StaticBG_ID(
                        BuildingsManager.buildings.get(Game.getProvince(tProvinces.get(toAddID)).getBuildings(ix).getBuilding()).Name[Game.getProvince(
                              tProvinces.get(toAddID)
                           )
                           .getBuildings(ix)
                           .getBuildingID()],
                        CFG.FONT_REGULAR_SMALL,
                        -1,
                        buttonX,
                        buttonY,
                        r1W2,
                        buttonH,
                        tProvinces.get(toAddID)
                     )
                  );
                  buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  fGold = BuildingsManager.buildings.get(Game.getProvince(tProvinces.get(toAddID)).getBuildings(ix).getBuilding()).MonthlyIncome[Game.getProvince(
                        tProvinces.get(toAddID)
                     )
                     .getBuildings(ix)
                     .getBuildingID()];
                  menuElements.add(
                     new ButtonStatsBudget_Right2(
                        (fGold > 0.0F ? "+" : "") + CFG.getPrecision2(fGold, 100), buttonX, buttonY, r1W3, buttonH, tProvinces.get(toAddID)
                     )
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }

            tProvinces.remove(toAddID);
         }
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.lang.get("Buildings"), Game.getCiv(this.iActiveCivID).getCivName(), false, false, Images.title500) {
         @Override
         public int getFlagCivID() {
            return InGame_BudgetIncomeBuildings.this.iActiveCivID;
         }

         @Override
         public long getTime() {
            return InGame_BudgetIncomeBuildings.lTime2;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
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
      ImageManager.getImage(Images.budgetOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.budgetOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.budgetOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Buildings"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime2 = CFG.currentTimeMillis;
   }
}
