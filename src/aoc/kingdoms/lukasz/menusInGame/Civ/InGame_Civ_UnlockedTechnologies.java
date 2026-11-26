package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_UnlockedTechnologies extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 0;

   public InGame_Civ_UnlockedTechnologies() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
         InGame_Civ.iActiveCivID = Game.getProvince(Game.iActiveProvince).getCivID();
      } else if (InGame_Civ.iActiveCivID <= 0) {
         InGame_Civ.iActiveCivID = Game.player.iCivID;
      }

      int c0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 4) / 3.0F);
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Civ.iActiveCivID).getResearchedTechnologies()),
            Game_Calendar.IMG_TECHNOLOGY,
            buttonX,
            buttonYPadding,
            c0W,
            buttonH,
            ImageManager.getImage(Game_Calendar.IMG_TECHNOLOGY).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnlockedTechnologies") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Civ.iActiveCivID).getResearchedTechnologies()), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      float fGold = Game.getCiv(InGame_Civ.iActiveCivID).fResearchPerMonth;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
            Game_Calendar.IMG_TECHNOLOGY,
            buttonX,
            buttonYPadding,
            c0W,
            buttonH,
            ImageManager.getImage(Game_Calendar.IMG_TECHNOLOGY).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ResearchPerMonth") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     CFG.getPrecision2(Game.getCiv(InGame_Civ.iActiveCivID).fResearchPerMonth, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + Game.getCiv(InGame_Civ.iActiveCivID).iAdvantagesSize,
            Images.advantages,
            buttonX,
            buttonYPadding,
            c0W,
            buttonH,
            ImageManager.getImage(Game_Calendar.IMG_TECHNOLOGY).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ_UnlockedAdvantages();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.getCiv(InGame_Civ.iActiveCivID).getCivName() + ": " + Game.lang.get("CivilizationAdvantages"), Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.advantages, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      Graph_Vertical graphVertical = new Graph_Vertical(
         Graph_Vertical_Data_Type.CIVS_UNLOCKED_TECHS,
         Game.lang.get("Civilizations"),
         Game.lang.get("Civilizations"),
         paddingLeft,
         buttonY,
         menuWidth - paddingLeft * 2,
         CFG.BUTTON_HEIGHT * 3,
         true
      ) {};
      menuElements.add(graphVertical);
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonX = Images.boxTitleBORDERWIDTH;
      int statW = menuWidth - paddingLeft * 2 - CFG.PADDING - ImageManager.getImage(Images.techGray).getWidth();
      int statWTitle = menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING - ImageManager.getImage(Images.techGray).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1,
            iSortID == 1,
            Game.lang.get("ResearchCost"),
            -1,
            buttonX,
            buttonY,
            statWTitle,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Civ_UnlockedTechnologies.iSortID == 0) {
                  InGame_Civ_UnlockedTechnologies.iSortID = 1;
               } else {
                  InGame_Civ_UnlockedTechnologies.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_UnlockedTechnologies();
               InGame_Civ_UnlockedTechnologies.lTime = 0L;
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
         new Text_TitleBlue(
            Game.lang.get("UnlockedTechnologies"),
            -1,
            buttonX,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2 - statWTitle,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> tTechnologies = new ArrayList<>();

      try {
         int i = 0;

         for (int iSize = TechnologyTree.iTechnologySize; i < iSize; i++) {
            if (Game.getCiv(InGame_Civ.iActiveCivID).getTechResearched(i)) {
               tTechnologies.add(i);
            }
         }
      } catch (Exception var18) {
         CFG.exceptionStack(var18);
      }

      if (tTechnologies.isEmpty()) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         while (tTechnologies.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tTechnologies.size(); o++) {
                  if (TechnologyTree.lTechnology.get(tTechnologies.get(toAddID)).getResearchCost()
                     < TechnologyTree.lTechnology.get(tTechnologies.get(o)).getResearchCost()) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tTechnologies.size(); ox++) {
                  if (TechnologyTree.lTechnology.get(tTechnologies.get(toAddID)).getResearchCost()
                     > TechnologyTree.lTechnology.get(tTechnologies.get(ox)).getResearchCost()) {
                     toAddID = ox;
                  }
               }
            }

            menuElements.add(
               new TextIcon2(
                  "" + CFG.getPrecision2(TechnologyTree.getResearchCost(tTechnologies.get(toAddID), Game.player.iCivID), 10),
                  Game_Calendar.IMG_TECHNOLOGY,
                  paddingLeft,
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
            buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonTechnology(
                  Images.techResearched,
                  tTechnologies.get(toAddID),
                  buttonX,
                  buttonY,
                  false,
                  Game.player.playerData.techQueue.getTechIsInQueue(tTechnologies.get(toAddID))
               ) {
                  @Override
                  public void actionElement() {
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            tTechnologies.remove(toAddID);
         }
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_FlagCenter(Game.getCiv(InGame_Civ.iActiveCivID).getCivName(), Game.lang.get("UnlockedTechnologies"), false, false, Images.title500) {
            @Override
            public int getFlagCivID() {
               return InGame_Civ.iActiveCivID;
            }

            @Override
            public long getTime() {
               return InGame_Civ_UnlockedTechnologies.lTime2;
            }
         }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true
      );
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      InGame_Civ.actionOnClose();
   }
}
