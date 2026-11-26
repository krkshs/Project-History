package aoc.kingdoms.lukasz.menusInGame.Technology;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc3;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_Government;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_TechnologyChoose extends Menu {
   public static int HOVER_POSX = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static boolean IN_TECHNOLOGY_CHOOSE = true;

   public InGame_TechnologyChoose() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = buttonYPadding;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT2 : CFG.BUTTON_HEIGHT;
      IN_TECHNOLOGY_CHOOSE = true;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("OpenTechnologyTree"),
            Game_Calendar.IMG_TECHNOLOGY,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonH,
            ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
            0,
            0L
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                  Game.menuManager.setVisibleInGame_TechnologyTree(false);
               } else {
                  Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                  InGame_TechnologyTree.centerToTechID = -1;
                  Game.menuManager.rebuildInGame_TechnologyTree(false, true);
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTechnologyTree"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("CivilizationAdvantages"),
            Images.advantages,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonH,
            ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
            0,
            0L
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_CivilizationAdvantages(Game.player.iCivID);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CivilizationAdvantages"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.advantages, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Advantages") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).iAdvantagesSize, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.advantages, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdvantagePoints") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).getAdvantagePoints(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.advantages, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("EachTimeYouUnlockATechnologyYouWillGainOneAdvantagePointThatYouCanExchangeForABonus"),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      if (Game.getCiv(Game.player.iCivID).getAdvantagePoints() > 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Active_Click(
               Game.lang.get("AdvantagePoints") + ": " + Game.getCiv(Game.player.iCivID).getAdvantagePoints(),
               Images.advantages,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
               0,
               0L
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_CivilizationAdvantages(Game.player.iCivID);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  return !isActive && !this.getIsHovered() ? Colors.HOVER_GOLD : super.getColor(isActive);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CivilizationAdvantages"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.advantages, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Advantages") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).iAdvantagesSize, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.advantages, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdvantagePoints") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).getAdvantagePoints(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.advantages, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        Game.lang.get("EachTimeYouUnlockATechnologyYouWillGainOneAdvantagePointThatYouCanExchangeForABonus"),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Research"),
            -1,
            CFG.FONT_BOLD,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("AvailableResearch"), CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int tempY = buttonY;
      buttonY += CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("CapitalCity") + ": " + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
            Images.capital,
            paddingLeft + CFG.PADDING,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
            0,
            CFG.FONT_REGULAR_SMALL
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
               this.menuElementHover = InGame_Court_Government.getHoverCapitalCity(true, Game.player.iCivID);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new Text_Desc3(
            Game.lang.get("ResearchPerMonth")
               + ": +"
               + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fResearchPerMonth, 100)
               + " / "
               + CFG.getPrecision2(TechnologyTree.getMaxResearch(Game.player.iCivID), 100),
            paddingLeft + CFG.PADDING,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
            CFG.FONT_REGULAR_SMALL
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
            protected Color getColor(boolean isActive) {
               return Game.getCiv(Game.player.iCivID).fResearchPerMonth >= TechnologyTree.getMaxResearch(Game.player.iCivID)
                  ? Colors.HOVER_NEGATIVE
                  : super.getColor(isActive);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MonthlyChange") + ": ",
                     "+"
                        + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fResearchPerMonth, 100)
                        + " / "
                        + CFG.getPrecision2(TechnologyTree.getMaxResearch(Game.player.iCivID), 100),
                     Game_Calendar.IMG_TECHNOLOGY,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MaximumResearch") + ": ",
                     CFG.getPrecision2(TechnologyTree.getMaxResearch(Game.player.iCivID), 100),
                     Game_Calendar.IMG_TECHNOLOGY,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
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
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("MaximumResearch")
                        + " = "
                        + CFG.getPrecision2(GameValues.research.MAX_RESEARCH_BASE, 100)
                        + " + "
                        + Game.lang.get("Capital")
                        + ", "
                        + Game.lang.get("GrowthRate")
                        + " * "
                        + CFG.getPrecision2(GameValues.research.MAX_RESEARCH_PER_GROWTH_RATE_IN_CAPITAL, 100)
                        + " + "
                        + Game.lang.get("CapitalCity")
                        + ", "
                        + Game.lang.get("Level")
                        + " * "
                        + CFG.getPrecision2(GameValues.capital.CAPITAL_MAX_RESEARCH_PER_LVL, 100),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT3
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("CapitalCity") + ": ",
                     Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
                     Images.capital,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      tempY = buttonY - tempY;
      menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY - tempY, menuWidth - paddingLeft * 2, tempY));
      buttonY += buttonYPadding;
      int buttonX = paddingLeft;
      int statW = menuWidth - paddingLeft * 2 - CFG.PADDING - ImageManager.getImage(Images.techGray).getWidth();
      int tAdded = 0;

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         if (Game.getCiv(Game.player.iCivID).getAvailableToResearch(i)) {
            menuElements.add(
               new TextIcon2(
                  "" + CFG.getPrecision2(TechnologyTree.getResearchCost(i, Game.player.iCivID), 10),
                  Game_Calendar.IMG_TECHNOLOGY,
                  buttonX,
                  buttonY,
                  statW,
                  ImageManager.getImage(Images.techGray).getHeight()
               ) {
                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ResearchCost") + ": ", CFG.FONT_BOLD));
                     nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.COLOR_TEXT_RESEARCH));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }

                  @Override
                  public int getTextH() {
                     return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonTechnology(
                  TechnologyTree.getTechBG(i, Game.player.iCivID), i, buttonX, buttonY, false, Game.player.playerData.techQueue.getTechIsInQueue(i)
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            buttonX = paddingLeft;
            tAdded++;
         }
      }

      if (tAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("More"),
            -1,
            CFG.FONT_BOLD,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("ListOfUnits"),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonH,
            ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
            0,
            0L
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_ListOfUnits();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ListOfUnits"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("ListOfBuildings"),
            Images.buildings,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonH,
            ImageManager.getImage(Images.advantages).getWidth() + CFG.PADDING * 4,
            0,
            0L
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_ListOfBuildings();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ListOfBuildings"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.buildings, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      HOVER_POSX = menuX + menuWidth + CFG.PADDING;
      this.initMenu(new MenuTitleIMG(Game.lang.get("ChooseResearch"), false, false, Images.title1Red) {
         @Override
         public long getTime() {
            return InGame_TechnologyChoose.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
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
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
