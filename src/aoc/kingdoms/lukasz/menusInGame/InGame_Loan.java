package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Right2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Loan extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_Loan() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int tIconMaxW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "+999 999");
      int tButtonRightW = (int)Renderer.glyphLayout.width + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonW = menuWidth - paddingLeft * 2 - CFG.PADDING - tButtonRightW;
      menuElements.add(new ButtonStatsBudget(Game.lang.get("Loan"), Images.loan, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW, true));
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "+" + CFG.getPrecision2(Game.getLoanValue(Game.player.iCivID), 100), paddingLeft + tButtonW + CFG.PADDING, buttonY, tButtonRightW, tButtonH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsBudget(
            Game.lang.get("Interest") + ": " + CFG.getPrecision2(Game.getLoanInterest(Game.player.iCivID), 100) + "%",
            Images.gold,
            paddingLeft,
            buttonY,
            tButtonW,
            tButtonH,
            tIconMaxW,
            false
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Interest") + ": ", CFG.FONT_BOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(Game.getLoanInterest(Game.player.iCivID), 100) + "%", CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.loan, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(GameValues.loan.LOAN_DEFAULT_INTEREST, 100) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.loan, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "+" + CFG.getPrecision2(Game.getLoanInterestValue(Game.player.iCivID), 100), paddingLeft + tButtonW + CFG.PADDING, buttonY, tButtonRightW, tButtonH
         ) {
            @Override
            public void setText(String sText) {
               super.setText(sText);
               this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
               this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
               this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsBudget(Game.lang.get("MonthlyExpenses"), Images.goldNegative, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW, true));
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "-" + CFG.getPrecision2(Game.getLoanMonthlyExpenses(Game.getLoanValue(Game.player.iCivID) + Game.getLoanInterestValue(Game.player.iCivID)), 100),
            paddingLeft + tButtonW + CFG.PADDING,
            buttonY,
            tButtonRightW,
            tButtonH
         ) {
            @Override
            public void setText(String sText) {
               super.setText(sText);
               this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
               this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
               this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsBudget(Game.lang.get("Inflation"), Images.inflation, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW, true));
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "+" + CFG.getPrecision2(GameValues.loan.LOAN_INFLATION * 100.0F, 100) + "%", paddingLeft + tButtonW + CFG.PADDING, buttonY, tButtonRightW, tButtonH
         ) {
            @Override
            public void setText(String sText) {
               super.setText(sText);
               this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
               this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
               this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Inflation") + ": ",
                     "+" + CFG.getPrecision2(GameValues.loan.LOAN_INFLATION * 100.0F, 100) + "%",
                     Images.inflation,
                     CFG.FONT_BOLD,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsBudget(
            Game.lang.get("Expires") + ": " + Game_Calendar.getNumOfDates_ByTurnID(Game_Calendar.TURN_ID + Game.getLoanExpires()),
            Images.time,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            tButtonH,
            tIconMaxW,
            false
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsBudget(
            Game.lang.get("MaximumNumberOfLoans") + ": " + Game.getCiv(Game.player.iCivID).iLoansSize + " / " + Game.getLoanMaxNumber(Game.player.iCivID),
            Images.loan,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            tButtonH,
            tIconMaxW,
            false
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Cancel"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_TakeLoan(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            true
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_LOAN;
            }

            @Override
            public boolean getClickable() {
               return Game.getCiv(Game.player.iCivID).iLoansSize < Game.getLoanMaxNumber(Game.player.iCivID);
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(Game.player.iCivID).takeLoan()) {
                  InGame_Info.iCivID = Game.player.iCivID;
                  InGame_Info.iCivID2 = 0;
                  Game.menuManager
                     .rebuildInGame_Info(
                        Game.lang.get("ALoanHasBeenTaken"),
                        Game.lang.get("Loans")
                           + ": "
                           + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).iLoansSize, 100)
                           + " / "
                           + Game.getLoanMaxNumber(Game.player.iCivID)
                     );
                  InGame_Info.imgID = Images.infoCrown;
                  Game.player.currSituation.updateCurrentSituation();
               }

               Game.menuManager.setVisibleInGame_TakeLoan(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("TakeLoan"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.loan, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float fVal = Game.getLoanValue(Game.player.iCivID);
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Gold") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("+" + CFG.getPrecision2(fVal, 100), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_POSITIVE));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Interest") + ": ", CFG.FONT_BOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "+" + CFG.getPrecision2(Game.getLoanInterestValue(Game.player.iCivID), 100), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goldNegative, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (Game.getCiv(Game.player.iCivID).iLoansSize >= Game.getLoanMaxNumber(Game.player.iCivID)) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("MaximumNumberOfLoans") + ": ", CFG.FONT_BOLD));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(
                        "" + Game.getCiv(Game.player.iCivID).iLoansSize + " / " + Game.getLoanMaxNumber(Game.player.iCivID),
                        CFG.FONT_BOLD,
                        Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            "+",
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING * 2 + (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2),
            buttonY,
            CFG.BUTTON_HEIGHT2,
            true
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_LOAN;
            }

            @Override
            public boolean getClickable() {
               return Game.getCiv(Game.player.iCivID).iLoansSize < Game.getLoanMaxNumber(Game.player.iCivID);
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(Game.player.iCivID).takeLoan()) {
                  InGame_Info.iCivID = Game.player.iCivID;
                  InGame_Info.iCivID2 = 0;
                  Game.menuManager
                     .rebuildInGame_Info(
                        Game.lang.get("ALoanHasBeenTaken"),
                        Game.lang.get("Loans")
                           + ": "
                           + CFG.getPrecision2((float)Game.getCiv(Game.player.iCivID).iLoansSize, 100)
                           + " / "
                           + Game.getLoanMaxNumber(Game.player.iCivID)
                     );
                  InGame_Info.imgID = Images.infoCrown;
                  Game.player.currSituation.updateCurrentSituation();
               }

               if (Game.getCiv(Game.player.iCivID).iLoansSize >= Game.getLoanMaxNumber(Game.player.iCivID)) {
                  Game.menuManager.setVisibleInGame_TakeLoan(false);
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("TakeLoan"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.loan, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float fVal = Game.getLoanValue(Game.player.iCivID);
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Gold") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("+" + CFG.getPrecision2(fVal, 100), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_POSITIVE));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Interest") + ": ", CFG.FONT_BOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "+" + CFG.getPrecision2(Game.getLoanInterestValue(Game.player.iCivID), 100), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goldNegative, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (Game.getCiv(Game.player.iCivID).iLoansSize >= Game.getLoanMaxNumber(Game.player.iCivID)) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("MaximumNumberOfLoans") + ": ", CFG.FONT_BOLD));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(
                        "" + Game.getCiv(Game.player.iCivID).iLoansSize + " / " + Game.getLoanMaxNumber(Game.player.iCivID),
                        CFG.FONT_BOLD,
                        Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("TakeLoan"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_Loan.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, CFG.GAME_HEIGHT / 4, menuWidth, i, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
   }
}
