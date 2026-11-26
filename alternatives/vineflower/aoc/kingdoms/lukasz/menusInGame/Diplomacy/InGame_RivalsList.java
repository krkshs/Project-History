package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RivalsManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Rank;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_HorizontalSplit;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_Religion;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Compare;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RivalsList extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iSortID = 6;

   public InGame_RivalsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = 0;
      List<Integer> rivals = RivalsManager.buildRivals(Game.player.iCivID, GameValues.rivals.NUM_OF_RIVALS_TO_CHOOSE_FROM);
      int rivalsSize = rivals.size();
      int iActiveCivID = Game.player.iCivID;
      int leftW = menuWidth - paddingLeft * 2 - ButtonFlag_Diplomacy.getButtonWidth() * 4 - CFG.PADDING * 4;
      int lineH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + CFG.PADDING * 2;
      int maxIconW = ImageManager.getImage(Images.relations).getWidth() + CFG.PADDING * 2;
      int linesAdded = 0;

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.rivals.size() > 0) {
            int buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData data : Game.getCiv(iActiveCivID).diplomacy.rivals.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(data.iCivID, buttonX, buttonY + CFG.PADDING, true));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("Rivals"),
                  Images.rivals,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            int var30 = false;
            buttonY = Math.max(buttonY + lineH, menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var20) {
         CFG.exceptionStack(var20);
      }

      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
      int var22 = Images.boxTitleBORDERWIDTH;
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 6 || iSortID == 7,
            iSortID == 6,
            Game.lang.get("Ranking"),
            -1,
            var22,
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
               if (InGame_RivalsList.iSortID == 6) {
                  InGame_RivalsList.iSortID = 7;
               } else {
                  InGame_RivalsList.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_RivalsList_SavePos();
               InGame_RivalsList.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      var22 += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1, iSortID == 1, Game.lang.get("Name"), -1, var22, buttonY, r0W, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_RivalsList.iSortID == 0) {
                  InGame_RivalsList.iSortID = 1;
               } else {
                  InGame_RivalsList.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RivalsList_SavePos();
               InGame_RivalsList.lTime = 0L;
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
      var22 += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 2 || iSortID == 3,
            iSortID == 3,
            Game.lang.get("Opinion"),
            -1,
            var22,
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
               if (InGame_RivalsList.iSortID == 2) {
                  InGame_RivalsList.iSortID = 3;
               } else {
                  InGame_RivalsList.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RivalsList_SavePos();
               InGame_RivalsList.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Opinion"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      var22 += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("RegimentsLimit"),
            -1,
            var22,
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
               if (InGame_RivalsList.iSortID == 4) {
                  InGame_RivalsList.iSortID = 5;
               } else {
                  InGame_RivalsList.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_RivalsList_SavePos();
               InGame_RivalsList.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      var22 = paddingLeft;
      r0W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 3) * 0.4F);
      r1W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 3) * 0.2F);
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT4 : CFG.BUTTON_HEIGHT2;
      if (rivals.isEmpty()) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      while (rivals.size() > 0) {
         int toAddID = 0;
         if (iSortID == 6) {
            for (int o = 1; o < rivals.size(); o++) {
               if (Game.getCiv(rivals.get(toAddID)).iCivRankPosition > Game.getCiv(rivals.get(o)).iCivRankPosition) {
                  toAddID = o;
               }
            }
         } else if (iSortID == 7) {
            for (int ox = 1; ox < rivals.size(); ox++) {
               if (Game.getCiv(rivals.get(toAddID)).iCivRankPosition < Game.getCiv(rivals.get(ox)).iCivRankPosition) {
                  toAddID = ox;
               }
            }
         } else if (iSortID == 0) {
            for (int oxx = 1; oxx < rivals.size(); oxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(rivals.get(toAddID)).getCivName(), Game.getCiv(rivals.get(oxx)).getCivName())) {
                  toAddID = oxx;
               }
            }
         } else if (iSortID == 1) {
            for (int oxxx = 1; oxxx < rivals.size(); oxxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(rivals.get(oxxx)).getCivName(), Game.getCiv(rivals.get(toAddID)).getCivName())) {
                  toAddID = oxxx;
               }
            }
         } else if (iSortID == 2) {
            for (int oxxxx = 1; oxxxx < rivals.size(); oxxxx++) {
               if (Game.getCiv(rivals.get(toAddID)).diplomacy.getRelation(Game.player.iCivID)
                  < Game.getCiv(rivals.get(oxxxx)).diplomacy.getRelation(Game.player.iCivID)) {
                  toAddID = oxxxx;
               }
            }
         } else if (iSortID == 3) {
            for (int oxxxxx = 1; oxxxxx < rivals.size(); oxxxxx++) {
               if (Game.getCiv(rivals.get(toAddID)).diplomacy.getRelation(Game.player.iCivID)
                  > Game.getCiv(rivals.get(oxxxxx)).diplomacy.getRelation(Game.player.iCivID)) {
                  toAddID = oxxxxx;
               }
            }
         } else if (iSortID == 4) {
            for (int oxxxxxx = 1; oxxxxxx < rivals.size(); oxxxxxx++) {
               if (Game.getCiv(rivals.get(toAddID)).iRegimentsLimit < Game.getCiv(rivals.get(oxxxxxx)).iRegimentsLimit) {
                  toAddID = oxxxxxx;
               }
            }
         } else if (iSortID == 5) {
            for (int oxxxxxxx = 1; oxxxxxxx < rivals.size(); oxxxxxxx++) {
               if (Game.getCiv(rivals.get(toAddID)).iRegimentsLimit > Game.getCiv(rivals.get(oxxxxxxx)).iRegimentsLimit) {
                  toAddID = oxxxxxxx;
               }
            }
         }

         menuElements.add(
            new ButtonStatsRectIMG_Rank(
               "" + Game.getCiv(rivals.get(toAddID)).iCivRankPosition,
               CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(rivals.get(toAddID)),
               var22,
               buttonY,
               r1W,
               buttonH,
               ImageManager.getImage(Images.rankGold).getWidth()
            ) {
               int id = 0;

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                  if (this.id == InGame_Civ_Compare.civLeft_Rank) {
                     oSB.setColor(Colors.COLOR_NOTIFICATION_OVER_GREEN);
                     Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
                     oSB.setColor(Color.WHITE);
                  }
               }

               @Override
               public void actionElement() {
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     if (InGame_Civ_Compare.civLeft == this.id) {
                        Game.menuManager
                           .addToast_Error(
                              Game.lang.get("Compare")
                                 + ": "
                                 + Game.getCiv(InGame_Civ_Compare.civLeft).getCivName()
                                 + " == "
                                 + Game.getCiv(this.id).getCivName()
                           );
                     } else {
                        InGame_Civ_Compare.civRight_Rank = this.id;
                        InGame_Civ_Compare.civLeft = InGame_Civ_Compare.civLeft_Rank;
                        InGame_Civ_Compare.civRight = InGame_Civ_Compare.civRight_Rank;
                        Game.menuManager.setVisibleInGame_PopUp(false);
                        Game.menuManager.rebuildInGame_Civ_Compare();
                     }
                  } else {
                     InGame_Civ_Compare.civLeft_Rank = this.id;
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CompareCivilizations"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.compare, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ",
                           Game.getCiv(InGame_Civ_Compare.civLeft_Rank).getCivName(),
                           InGame_Civ_Compare.civLeft_Rank,
                           CFG.FONT_REGULAR,
                           CFG.FONT_BOLD,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  } else {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("CivilizationRank") + ": ",
                        this.getText(),
                        this.imageID,
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
         menuElements.get(menuElements.size() - 1).setCurrent(rivals.get(toAddID));
         var22 += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_Religion(
               "" + Game.getCiv(rivals.get(toAddID)).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, var22, buttonY, r0W, buttonH, rivals.get(toAddID)
            ) {
               @Override
               public void actionElement() {
                  InGame_Rivals.rivalCivID = this.getCurrent();
                  InGame_Rivals.backToRivalsList = true;
                  Game.menuManager.rebuildInGame_Rivals();
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }

               @Override
               public void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  this.drawTextCiv(oSB, iTranslateX, iTranslateY, isActive);
               }
            }
         );
         var22 += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_Special(
               "" + CFG.getPrecision2((float)((int)Game.getCiv(rivals.get(toAddID)).diplomacy.getRelation(Game.player.iCivID)), 1),
               CFG.FONT_REGULAR_SMALL,
               -1,
               var22,
               buttonY,
               r1W,
               buttonH,
               rivals.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }
            }
         );
         var22 += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         float tDiff = ((float)Game.getCiv(rivals.get(toAddID)).iRegimentsLimit / Game.getCiv(Game.player.iCivID).iRegimentsLimit - 1.0F) * 100.0F;
         menuElements.add(
            new Text_StaticBG_ID_Special(
               (tDiff > 0.0F ? "+" : "") + CFG.getPrecision2(tDiff, 1) + "%", CFG.FONT_REGULAR_SMALL, -1, var22, buttonY, r1W, buttonH, rivals.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }
            }
         );
         var22 = paddingLeft;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         rivals.remove(toAddID);
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(
            Game.lang.get("ChooseYourRivals")
               + ": "
               + Game.getCiv(Game.player.iCivID).diplomacy.rivals.size()
               + " / "
               + GameValues.rivals.RIVALS_LIMIT
               + " ["
               + rivalsSize
               + "]",
            true,
            false,
            Images.title600
         ) {
            @Override
            public long getTime() {
               return InGame_RivalsList.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         menuY,
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT * 3 / 5 + (int)(CFG.BUTTON_HEIGHT * 3 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.outlinerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.outlinerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.outlinerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
