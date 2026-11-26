package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Formable;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Value;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_SellProvince extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int sellToCivID = 0;

   public InGame_SellProvince(int nSellToCivID) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      sellToCivID = nSellToCivID;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      buttonY += CFG.PADDING;
      menuElements.add(new ButtonFlag(sellToCivID, paddingLeft2, buttonY, true) {
         @Override
         public void actionElement() {
         }

         @Override
         public int getFlagCivID() {
            return InGame_SellProvince.sellToCivID;
         }
      });
      int buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      int bHeight = (ButtonFlag_Formable.getButtonHeight() - CFG.PADDING) / 2;
      menuElements.add(new Text_StaticBG_RulerTitle(Game.getCiv(sellToCivID).getCivName(), buttonX, buttonY, menuWidth - buttonX - paddingLeft2, bHeight) {
         @Override
         public void actionElement() {
         }
      });
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Provinces") + ": ",
            "" + Game.getCiv(sellToCivID).getNumOfProvinces(),
            Images.provinces,
            buttonX,
            buttonY + bHeight + CFG.PADDING,
            menuWidth - buttonX - paddingLeft2,
            bHeight,
            maxIconW
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public String getText2() {
               return "" + Game.getCiv(InGame_SellProvince.sellToCivID).getNumOfProvinces();
            }
         }
      );
      buttonX = paddingLeft;
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
      menuElements.add(new ButtonGame(Game.lang.get("Close"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Game.menuManager.setVisibleInGame_PopUp(false);
            Game.menuManager.rebuildInGame_Civ(true);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("SellProvinces") + ": " + Game.lang.get("ChooseAProvince"),
            Images.provinces,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            maxIconW,
            0,
            true
         ) {
            @Override
            public void actionElement() {
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_SELL_PROVINCES) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
               } else {
                  Game.gameActiveProvince.resetLastActiveProvince();
                  Game.setActiveProvinceID(-1);
                  Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_SELL_PROVINCES);
               }
            }

            @Override
            protected Color getColor(boolean isActive) {
               return Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_SELL_PROVINCES ? Colors.HOVER_GOLD : super.getColor(isActive);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SellProvinces") + ": " + Game.lang.get("ChooseAProvince"), Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> tProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
         if (Game.getCiv(Game.player.iCivID).getProvinceID(i) != Game.getCiv(Game.player.iCivID).getCapitalProvinceID()) {
            tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(i));
         }
      }

      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
      int r0W2 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING) * 0.75F);
      int buttonHProvinces = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Provinces"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (tProvinces.isEmpty()) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT4));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         while (tProvinces.size() > 0) {
            int toAddID = 0;
            int o = 1;

            for (int oSize = tProvinces.size(); o < oSize; o++) {
               if (CFG.compareAlphabetic_TwoString(
                  Game.getProvince(tProvinces.get(toAddID)).getProvinceName(), Game.getProvince(tProvinces.get(o)).getProvinceName()
               )) {
                  toAddID = o;
               }
            }

            menuElements.add(
               new Text_StaticBG_ID_Flag(
                  Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  r0W2,
                  buttonHProvinces,
                  tProvinces.get(toAddID),
                  false
               ) {
                  boolean confirm = false;

                  @Override
                  public void actionElement() {
                     if (this.confirm) {
                        if (InGame_SellProvince.getSellProvince(this.getCurrent())) {
                           InGame_Info.iCivID = Game.player.iCivID;
                           InGame_Info.iCivID2 = InGame_SellProvince.sellToCivID;
                           Game.menuManager.rebuildInGame_Info(Game.lang.get("ProvinceSold"), Game.getProvince(this.getCurrent()).getProvinceName());
                           InGame_Info.imgID = Images.infoCrown;
                           this.setText(Game.lang.get("ProvinceSold") + ": " + Game.getProvince(this.getCurrent()).getProvinceName());
                        }
                     } else {
                        this.confirm = true;
                        this.setText(Game.lang.get("Confirm") + ": " + this.getText());
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     this.confirm = false;
                     this.setText(Game.getProvince(this.getCurrent()).getProvinceName());
                     Game.mapCoords.centerToProvinceID(this.getCurrent());
                  }

                  @Override
                  public int getFlagID() {
                     return Game.getProvince(this.getCurrent()).peaceTreatyIsTaken ? Game.player.iCivID : super.getFlagID();
                  }

                  @Override
                  protected Color getColor(boolean isActive) {
                     return this.confirm && Game.getProvince(this.getCurrent()).getCivID() != InGame_SellProvince.sellToCivID
                        ? Colors.getColorNegative(isActive, this.getIsHovered())
                        : super.getColor(isActive);
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SellProvince") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Population") + ": ",
                           CFG.getNumberWithSpaces("" + Game.getProvince(this.getCurrent()).getPopulationTotal()),
                           Images.population,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.COLOR_POPULATION
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Economy") + ": ",
                           CFG.getPrecision2(Game.getProvince(this.getCurrent()).getEconomyWithBonuses(), 10),
                           Game_Calendar.IMG_ECONOMY,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Gold") + ": ",
                           CFG.getPrecision2(InGame_SellProvince.getSellProvince_Gold(this.getCurrent()), 10),
                           Images.victoryPoints,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
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
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Value(
                  "" + CFG.getPrecision2(getSellProvince_Gold(tProvinces.get(toAddID)), 100),
                  Images.gold,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonHProvinces,
                  ImageManager.getImage(Images.gold).getWidth(),
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Population") + ": ",
                           CFG.getNumberWithSpaces("" + Game.getProvince(this.getCurrent()).getPopulationTotal()),
                           Images.population,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.COLOR_POPULATION
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Economy") + ": ",
                           CFG.getPrecision2(Game.getProvince(this.getCurrent()).getEconomyWithBonuses(), 10),
                           Game_Calendar.IMG_ECONOMY,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Gold") + ": ",
                           CFG.getPrecision2(InGame_SellProvince.getSellProvince_Gold(this.getCurrent()), 10),
                           Images.victoryPoints,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
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
            buttonX = paddingLeft;
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tProvinces.remove(toAddID);
         }
      }

      for (int ix = menuElements.size() - 1; ix >= 0; ix--) {
         buttonY = Math.max(buttonY, menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING);
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("SellProvince"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_SellProvince.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      DiplomacyManager.updateAnimationTime();
      if (!visible && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_SELL_PROVINCES) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_SELL_PROVINCES) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   public static boolean getSellProvince(int provinceID) {
      if (Game.player.iCivID == Game.getProvince(provinceID).getCivID()) {
         boolean zeroProvinces = Game.getCiv(sellToCivID).getNumOfProvinces() == 0;
         Game.getProvince(provinceID).setCivID(sellToCivID);

         try {
            if (zeroProvinces
               || Game.getCiv(sellToCivID).getCapitalProvinceID() == provinceID
               || Game.getCiv(sellToCivID).getCapitalProvinceID() < 0
               || Game.getProvince(Game.getCiv(sellToCivID).getCapitalProvinceID()).getCivID() != sellToCivID) {
               Game.getCiv(sellToCivID).moveCapital_ToLargestProvince();
            }
         } catch (Exception var3) {
         }

         Civilization var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fGold = var10000.fGold + getSellProvince_Gold(provinceID);
         Game.player.fog.updateFogOfWar(provinceID);
         return true;
      } else {
         return false;
      }
   }

   public static float getSellProvince_Gold(int provinceID) {
      return Game.getProvince(provinceID).fProvinceValue * GameValues.diplomacy.SELL_PROVINCE_GOLD_PER_PROVINCE_VALUE;
   }
}
