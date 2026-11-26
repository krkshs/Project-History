package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Stats.Stats;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.Icon;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Formable;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Stats;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title_Stats;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc_Simple;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu_Stats extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static List<Image> lFlags = new ArrayList<>();
   public static List<Stats> statsData = new ArrayList<>();
   public static int modeID = 1;
   public static int hideReview = 5;

   public MainMenu_Stats() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = (int)(CFG.GAME_WIDTH / (10.0F * CFG.GUI_SCALE));
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = 0;
      int topHeight = ButtonRuler_Diplomacy.getButtonHeight() + CFG.PADDING * 2;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      if (statsData.size() >= 4 && hideReview > 0) {
         buttonY = CFG.PADDING;
         menuElements.add(
            new Text_Desc_Simple(
               Game.lang.get("Review0"),
               paddingLeft + ImageManager.getImage(Images.heart).getWidth() + CFG.PADDING * 3,
               buttonY,
               menuWidth - paddingLeft * 2 - (ImageManager.getImage(Images.heart).getWidth() + CFG.PADDING * 3)
            ) {
               @Override
               public void actionElement() {
                  MenuManager.addClickAnimation(
                     new ClickAnimation(
                        this.getPosX() + this.getWidth() / 2 + MainMenu_Stats.this.getMenuPosX(),
                        this.getPosY() + this.getHeight() / 2 + MainMenu_Stats.this.getMenuPosY(),
                        this.getWidth(),
                        this.getHeight()
                     ) {
                        @Override
                        public Color getColor() {
                           return DiplomacyManager.COLOR_GREEN;
                        }
                     }
                  );
                  MainMenu_Stats.hideReview--;
                  if (MainMenu_Stats.hideReview <= 0) {
                     Game.menuManager.rebuildMainMenu_Stats();
                     Game.menuManager.addToastGold(":P", Images.heart);
                  }
               }
            }
         );
         menuElements.add(
            new Icon(
               Images.heart,
               paddingLeft,
               buttonY,
               ImageManager.getImage(Images.heart).getWidth() + CFG.PADDING * 2,
               menuElements.get(menuElements.size() - 1).getHeight(),
               0
            ) {
               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  oSB.setColor(
                     new Color(
                        Colors.COLOR_STATS_RECT_BG.r,
                        Colors.COLOR_STATS_RECT_BG.g,
                        Colors.COLOR_STATS_RECT_BG.b,
                        !this.getIsHovered() && !isActive ? 0.5F : 0.6F
                     )
                  );
                  Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
                  oSB.setColor(Color.WHITE);
                  super.drawText(oSB, iTranslateX, iTranslateY, isActive);
               }

               @Override
               public void actionElement() {
                  MenuManager.addClickAnimation(
                     new ClickAnimation(
                        this.getPosX() + this.getWidth() / 2 + MainMenu_Stats.this.getMenuPosX(),
                        this.getPosY() + this.getHeight() / 2 + MainMenu_Stats.this.getMenuPosY(),
                        this.getWidth(),
                        this.getHeight()
                     ) {
                        @Override
                        public Color getColor() {
                           return DiplomacyManager.COLOR_GREEN;
                        }
                     }
                  );
                  MainMenu_Stats.hideReview--;
                  if (MainMenu_Stats.hideReview <= 0) {
                     Game.menuManager.rebuildMainMenu_Stats();
                     Game.menuManager.addToastGold(":P", Images.heart);
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 2).getHeight() + CFG.PADDING;
      }

      int titleButtonW = (menuWidth - Images.boxTitleBORDERWIDTH * 2) / 4;
      menuElements.add(
         new Text_TitleBlueSort(modeID == 0, false, Game.lang.get("Name"), -1, Images.boxTitleBORDERWIDTH, buttonY, titleButtonW, CFG.BUTTON_HEIGHT4) {
            @Override
            public void actionElement() {
               if (MainMenu_Stats.modeID != 0) {
                  MainMenu_Stats.modeID = 0;
                  Game.menuManager.rebuildMainMenu_Stats();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Name"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
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
         new Text_TitleBlueSort(
            modeID == 1, false, Game.lang.get("PlayingTime"), -1, Images.boxTitleBORDERWIDTH + titleButtonW, buttonY, titleButtonW, CFG.BUTTON_HEIGHT4
         ) {
            @Override
            public void actionElement() {
               if (MainMenu_Stats.modeID != 1) {
                  MainMenu_Stats.modeID = 1;
                  Game.menuManager.rebuildMainMenu_Stats();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("PlayingTime"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.play, CFG.PADDING, 0));
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
         new Text_TitleBlueSort(
            modeID == 2, false, Game.lang.get("RecruitedArmy"), -1, Images.boxTitleBORDERWIDTH + titleButtonW * 2, buttonY, titleButtonW, CFG.BUTTON_HEIGHT4
         ) {
            @Override
            public void actionElement() {
               if (MainMenu_Stats.modeID != 2) {
                  MainMenu_Stats.modeID = 2;
                  Game.menuManager.rebuildMainMenu_Stats();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitedArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
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
         new Text_TitleBlueSort(
            modeID == 3, false, Game.lang.get("Wars"), -1, Images.boxTitleBORDERWIDTH + titleButtonW * 3, buttonY, titleButtonW, CFG.BUTTON_HEIGHT4
         ) {
            @Override
            public void actionElement() {
               if (MainMenu_Stats.modeID != 3) {
                  MainMenu_Stats.modeID = 3;
                  Game.menuManager.rebuildMainMenu_Stats();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Wars"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.war, CFG.PADDING, 0));
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
      int statH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      statsData.clear();
      statsData = Game.stats.loadAllStats();
      this.loadFlags();
      List<Integer> ids = new ArrayList<>();
      List<String> names = new ArrayList<>();

      for (int i = 0; i < statsData.size(); i++) {
         ids.add(i);
         names.add(Game.lang.getCiv(statsData.get(i).tg));
      }

      int statsRightW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 5) / 6;
      int statsRightH = CFG.BUTTON_HEIGHT;
      if (ids.isEmpty()) {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2) {
               @Override
               public void actionElement() {
                  MainMenu_Stats.disposeFlags();
                  Game.menuManager.setViewID(View.MAINMENU);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      while (!ids.isEmpty()) {
         int bestID = 0;
         if (modeID == 0) {
            for (int i = 1; i < ids.size(); i++) {
               if (CFG.compareAlphabetic_TwoString(names.get(bestID), names.get(i))) {
                  bestID = i;
               }
            }
         } else if (modeID == 1) {
            for (int ix = 1; ix < ids.size(); ix++) {
               if (statsData.get(ids.get(bestID)).tr < statsData.get(ids.get(ix)).tr) {
                  bestID = ix;
               }
            }
         } else if (modeID == 2) {
            for (int ixx = 1; ixx < ids.size(); ixx++) {
               if (statsData.get(ids.get(bestID)).rr < statsData.get(ids.get(ixx)).rr) {
                  bestID = ixx;
               }
            }
         } else {
            for (int ixxx = 1; ixxx < ids.size(); ixxx++) {
               if (statsData.get(ids.get(bestID)).nw < statsData.get(ids.get(ixxx)).nw) {
                  bestID = ixxx;
               }
            }
         }

         int startY = buttonY;
         buttonY += CFG.PADDING;
         menuElements.add(new ButtonFlag_Stats(ids.get(bestID), paddingLeft2, buttonY) {
            @Override
            public void actionElement() {
            }
         });
         int buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int bHeight = (ButtonFlag_Formable.getButtonHeight() - CFG.PADDING) / 2;
         menuElements.add(
            new Text_StaticBG_RulerTitle(Game.lang.getCiv(statsData.get(ids.get(bestID)).tg), buttonX, buttonY, menuWidth - buttonX - paddingLeft2, bHeight) {
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
               public void buildElementHover() {
                  this.menuElementHover = MainMenu_Stats.getHover(this.id);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(ids.get(bestID));
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Games") + ": ",
               CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).ga),
               Images.play,
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
               public void buildElementHover() {
                  this.menuElementHover = MainMenu_Stats.getHover(this.id);
               }

               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(ids.get(bestID));
         buttonY += ButtonFlag_Stats.getButtonHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("PlayingTime") + ": ",
               "" + Game_Calendar.getNumOfDays_ByTurnsPlayed(statsData.get(ids.get(bestID)).tr),
               Images.time,
               paddingLeft2,
               buttonY,
               menuWidth - paddingLeft2 * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("NumberOfProvinces") + ": ",
               CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).lp),
               Images.provinces,
               paddingLeft2,
               buttonY,
               menuWidth - paddingLeft2 * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyIncome") + ": ",
               CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).li),
               Images.gold,
               paddingLeft2,
               buttonY,
               menuWidth - paddingLeft2 * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ReinforceArmyCost") + ": ",
               CFG.getNumberWithSpaces("" + (int)(statsData.get(ids.get(bestID)).rf / 100.0F)),
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft2,
               buttonY,
               menuWidth - paddingLeft2 * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_RIGHT;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new TextIcon2("" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).nw), Images.war, paddingLeft2, buttonY, statsRightW, statsRightH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Wars") + ": ",
                        this.getText(),
                        this.imageID,
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
         buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextIcon2("" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).cp), Images.provinces, buttonX, buttonY, statsRightW, statsRightH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("ConqueredProvinces") + ": ",
                        this.getText(),
                        this.imageID,
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
            new TextIcon2(
               "" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).rr), Game_Calendar.IMG_MANPOWER, buttonX, buttonY, statsRightW, statsRightH
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("RecruitedArmy") + ", " + Game.lang.get("Regiments") + ": ",
                        this.getText(),
                        this.imageID,
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
            new TextIcon2("" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).rg), Images.general, buttonX, buttonY, statsRightW, statsRightH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Generals") + ": ",
                        this.getText(),
                        this.imageID,
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
            new TextIcon2("" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).ra), Images.council, buttonX, buttonY, statsRightW, statsRightH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Advisors") + ": ",
                        this.getText(),
                        this.imageID,
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
            new TextIcon2("" + CFG.getNumberWithSpaces("" + statsData.get(ids.get(bestID)).bc), Images.buildings, buttonX, buttonY, statsRightW, statsRightH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("BuildingsConstructed") + ": ",
                        this.getText(),
                        this.imageID,
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
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, startY, menuWidth - paddingLeft * 2, buttonY - startY));
         buttonY += CFG.PADDING;
         ids.remove(bestID);
         names.remove(bestID);
      }

      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(Game.lang.get("Close"), "", Images.x, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT3, maxIconW) {
            @Override
            public void actionElement() {
               MainMenu_Stats.disposeFlags();
               Game.menuManager.setViewID(View.MAINMENU);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("HallofFame"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return MainMenu_Stats.lTime2;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, true, false);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void beginClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      try {
         if (MainMenu.bgAlpha < 1.0F) {
            oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
            Images.pix.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
            MainMenu.bgAlpha = Math.min(1.0F, (float)(CFG.currentTimeMillis - MainMenu.bgTIME) / GameValues.text.MAIN_MENU_BG_ANIMATION_TIME);
         }

         oSB.setColor(new Color(0.050980393F, 0.08627451F, 0.13333334F, 1.0F * MainMenu.bgAlpha));
         InitGame.background
            .draw(
               oSB,
               iTranslateX + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2,
               iTranslateY + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, MainMenu.bgAlpha));
         oSB.setShader(Renderer.shaderAlpha);
         InitGame.background.getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.gradientHorizontal2)
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setColor(MainMenu.sparksColors);
         MenuManager.sparksAnimation.draw2(oSB, iTranslateX, CFG.GAME_HEIGHT - Images.sparkHeight + iTranslateY, CFG.GAME_WIDTH, Images.sparkHeight);
         oSB.setColor(Color.WHITE);
         if ((
               CFG.isDesktop() && GameValues.text.MAIN_MENU_BG_ENABLE_AUTO_BG_CHANGE
                  || !CFG.isDesktop() && GameValues.text.MAIN_MENU_BG_ENABLE_AUTO_BG_CHANGE_MOBILE
            )
            && CFG.currentTimeMillis > MainMenu.bgTIME_CHANGE + GameValues.text.MAIN_MENU_BG_CHANGE_BG_EVERY_X_MS) {
            MainMenu.bgTIME_CHANGE = CFG.currentTimeMillis;
            Game.addSimpleTask(new Game.SimpleTask("loadBackground") {
               @Override
               public void update() {
                  InitGame.loadBackground();
                  MainMenu.bgTIME = System.currentTimeMillis();
                  MainMenu.bgTIME_CHANGE = System.currentTimeMillis();
                  MainMenu.bgAlpha = 0.0F;
               }
            });
         }
      } catch (Exception var6) {
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
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
      if (!visible) {
         disposeFlags();
      }
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_MainMenu_Stats();
   }

   public final void loadFlags() {
      disposeFlags();

      for (int i = 0; i < statsData.size(); i++) {
         try {
            try {
               try {
                  lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + statsData.get(i).tg + ".png")), TextureFilter.Nearest));
               } catch (GdxRuntimeException var5) {
                  lFlags.add(
                     new Image(
                        new Texture(FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(statsData.get(i).tg) + ".png")),
                        TextureFilter.Nearest
                     )
                  );
               }
            } catch (GdxRuntimeException var6) {
               try {
                  lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + statsData.get(i).tg + ".png")), TextureFilter.Nearest));
               } catch (GdxRuntimeException var4) {
                  lFlags.add(
                     new Image(
                        new Texture(FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(statsData.get(i).tg) + ".png")),
                        TextureFilter.Nearest
                     )
                  );
               }
            }
         } catch (GdxRuntimeException var7) {
            lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/ran.png")), TextureFilter.Nearest));
         }
      }
   }

   public static final void disposeFlags() {
      for (int i = 0; i < lFlags.size(); i++) {
         lFlags.get(i).getTexture().dispose();
      }

      lFlags.clear();
   }

   public static MenuElement_Hover getHover(int id) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title_Stats(id, Game.lang.get("Games") + ": " + statsData.get(id).ga));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      return new MenuElement_Hover(nElements);
   }
}
