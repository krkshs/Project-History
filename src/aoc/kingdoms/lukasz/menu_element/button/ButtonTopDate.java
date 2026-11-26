package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Space;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonTopDate extends Button {
   public static int iMaxWidth = 0;
   protected static final Color COLOR_SPEED = new Color(0.27450982F, 0.039215688F, 0.11764706F, 1.0F);
   public static long ANIMATION_TIME = 0L;
   public static float ANIMATION_DURATION = 150.0F;

   public ButtonTopDate(String sText, int iPosY, int nHeight) {
      this.fontID = CFG.FONT_BOLD;
      this.setText(sText);
      updateMaxWidth();
      this.init(
         sText,
         this.fontID,
         this.iTextPositionX,
         0,
         iPosY,
         this.getTextWidth() + getPaddingX() * 2 + ImageManager.getImage(Images.pause).getWidth() + CFG.PADDING * 2,
         nHeight,
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      int tSpeedWidth = (int)(this.getWidth() * 0.9F - CFG.PADDING * (Game.gameThread.playMaxSpeed - 1)) / Game.gameThread.playMaxSpeed;
      int tX = (int)(this.getWidth() * 0.9F - tSpeedWidth * Game.gameThread.playMaxSpeed - CFG.PADDING * (Game.gameThread.playMaxSpeed - 1)) / 2;
      int tY = this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY;
      int tH = CFG.PADDING;
      if ((float)ANIMATION_TIME + ANIMATION_DURATION >= (float)CFG.currentTimeMillis) {
         int extra = Math.max(
            0,
            ImageManager.getImage(Images.topStats).getHeight() / 3
               - (int)(ImageManager.getImage(Images.topStats).getHeight() / 3 * ((float)(CFG.currentTimeMillis - ANIMATION_TIME) / ANIMATION_DURATION))
         );
         tY -= extra;
         tH += extra;
         if ((float)(CFG.currentTimeMillis - ANIMATION_TIME) > ANIMATION_DURATION) {
            ANIMATION_TIME = 0L;
         }
      }

      for (int i = 0; i < Game.gameThread.playSpeed; i++) {
         draw_Speed(oSB, tX + this.getPosX() + (int)(this.getWidth() * 0.05F) + (tSpeedWidth + CFG.PADDING) * i + iTranslateX, tY, tSpeedWidth, tH);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.5F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
         Images.gradientXY
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(Color.WHITE);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(this.getColor(isActive));
      ImageManager.getImage(Game.gameThread.play ? Images.pause : Images.play)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - getPaddingX() - ImageManager.getImage(Images.pause).getWidth() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.pause).getHeight() / 2 + iTranslateY
         );
      oSB.setColor(Color.WHITE);
      this.setText(Game_Calendar.getCurrentDate());
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX()
            + this.getWidth()
            - getPaddingX()
            - ImageManager.getImage(Images.pause).getWidth()
            - CFG.PADDING * 2
            - this.getTextWidth()
            + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   protected static final void draw_Speed(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight) {
      oSB.setColor(new Color(COLOR_SPEED.r, COLOR_SPEED.g, COLOR_SPEED.b, 0.85F));
      Images.pix.draw(oSB, nPosX, nPosY, nWidth, nHeight);
      oSB.setColor(COLOR_SPEED);
      Images.gradientXY.draw(oSB, nPosX, nPosY + nHeight - nHeight / 2, nWidth, nHeight / 2, false, true);
      Images.gradientXY.draw(oSB, nPosX, nPosY, nWidth, nHeight / 2, false, false);
      ImageManager.getImage(Images.gradientXYVertical).draw(oSB, nPosX, nPosY, CFG.PADDING, nHeight);
      ImageManager.getImage(Images.gradientXYVertical).draw(oSB, nPosX + nWidth - CFG.PADDING, nPosY, CFG.PADDING, nHeight, true, false);
      oSB.setColor(Color.WHITE);
   }

   public static int getPaddingX() {
      return CFG.PADDING * 4;
   }

   public static int getPaddingY() {
      return CFG.PADDING;
   }

   @Override
   public int getWidth() {
      return iMaxWidth + getPaddingX() * 2 + ImageManager.getImage(Images.pause).getWidth() + CFG.PADDING * 2;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats(isActive, this.getIsHovered());
   }

   public static Color getColorDate(boolean isActive, boolean isHovered, float fAlpha) {
      if (isActive) {
         return new Color(Colors.COLOR_TOP_STATS_ACTIVE.r, Colors.COLOR_TOP_STATS_ACTIVE.g, Colors.COLOR_TOP_STATS_ACTIVE.b, fAlpha);
      } else {
         return isHovered
            ? new Color(Colors.COLOR_TOP_STATS_HOVER.r, Colors.COLOR_TOP_STATS_HOVER.g, Colors.COLOR_TOP_STATS_HOVER.b, fAlpha)
            : new Color(Colors.COLOR_TOP_STATS.r, Colors.COLOR_TOP_STATS.g, Colors.COLOR_TOP_STATS.b, fAlpha);
      }
   }

   public static final void updateMaxWidth() {
      for (int i = 0; i < 12; i++) {
         updateMaxWidth((Game.HOURS_PER_TURN < 24 ? "24:00, " : "") + "31 " + Game_Calendar.getMonthName(i) + " 2345");
      }
   }

   public static final void updateMaxWidth(String sText) {
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), sText);
      iMaxWidth = 0;
      iMaxWidth = Math.max(iMaxWidth, (int)Renderer.glyphLayout.width);
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (Game.gameThread.play) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ClickToPauseTheGame"), Colors.HOVER_GOLD));
      } else {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ClickToUnpauseTheGame"), Colors.HOVER_GOLD));
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Date") + ": ",
            "" + Game_Calendar.getCurrentDate(),
            Images.time,
            CFG.FONT_REGULAR,
            CFG.FONT_BOLD,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (Game_Calendar.TURN_ID != 1) {
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("PlayingTime") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(new MenuElement_HoverElement_Type_Text(Game_Calendar.getNumOfDates_ByTurnID(1), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            "",
            "" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).Name,
            Images.time,
            CFG.FONT_REGULAR,
            CFG.FONT_BOLD,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (Game.gameAges.getAge_TurnDays(Game_Calendar.CURRENT_AGEID) > 1) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TurnsX", 1) + " = ",
               Game.lang.get("XDays", Game.gameAges.getAge_TurnDays(Game_Calendar.CURRENT_AGEID)),
               Images.time,
               CFG.FONT_REGULAR,
               CFG.FONT_BOLD,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Regiment") + ", " + Game.lang.get("Manpower") + ": ",
            "" + CFG.getNumberWithSpaces("" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE),
            Game_Calendar.IMG_MANPOWER,
            CFG.FONT_REGULAR,
            CFG.FONT_BOLD,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (CFG.isDesktop()) {
         nData.add(new MenuElement_HoverElement_Type_Space());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(new MenuElement_HoverElement_Type_Text("SPACE", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
