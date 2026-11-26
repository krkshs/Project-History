package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.InGame_War;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class MessageWar extends Button {
   public static int warMaskY = 0;
   public String key;
   private int iCivID = 1;
   public int lastValue = -9415629;
   public Color colorText = Color.WHITE;

   public MessageWar(int iPosX, int iPosY, boolean isClickable, int iCivID, String key) {
      this.iCivID = iCivID;
      this.key = key;
      this.init(
         "",
         CFG.FONT_BOLD_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.warBG).getWidth(),
         ImageManager.getImage(Images.warBG).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.getTextToDraw();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(Images.warBG).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.setShader(Renderer.shaderAlpha);
      Game.getCiv(this.iCivID).getFlag().getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.warMask)
         .draw(
            oSB,
            this.getPosX() + ImageManager.getImage(Images.warBG).getWidth() / 2 - ImageManager.getImage(Images.warMask).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + warMaskY
               + ImageManager.getImage(Images.warBG).getHeight() / 2
               - ImageManager.getImage(Images.warMask).getHeight() / 2
               + iTranslateY
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.warOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.getIsHovered()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         ImageManager.getImage(Images.warOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      }

      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 1.0F));
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.getHeight() - this.iTextHeight + iTranslateY,
            this.getWidth() - CFG.PADDING * 4,
            this.iTextHeight + CFG.PADDING / 2,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.iTextWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - this.iTextHeight + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public String getTextToDraw() {
      try {
         if (WarManager.lWars.containsKey(this.key) && this.lastValue != (int)WarManager.lWars.get(this.key).warScore) {
            boolean playSound = Math.abs(this.lastValue) < GameValues.peace.WAR_MAKE_DEMANDS_MIN_WAR_SCORE;
            this.lastValue = (int)WarManager.lWars.get(this.key).warScore;
            int lastValue2 = this.lastValue * WarManager.lWars.get(this.key).getWarScore_Side(this.iCivID);
            if (playSound && Math.abs(this.lastValue) >= GameValues.peace.WAR_MAKE_DEMANDS_MIN_WAR_SCORE) {
               Game.soundsManager.playSound(SoundsManager.SOUND_WAR_END);
            }

            if (lastValue2 == 0) {
               this.colorText = Colors.COLOR_TOP_STATS3;
            } else if (lastValue2 < 0) {
               this.colorText = Colors.HOVER_NEGATIVE;
            } else if (lastValue2 >= GameValues.peace.WAR_MAKE_DEMANDS_MIN_WAR_SCORE) {
               this.colorText = Colors.HOVER_GOLD;
            } else {
               this.colorText = Colors.HOVER_POSITIVE;
            }

            this.setText((lastValue2 > 0 ? "+" : "") + Math.min(100, Math.max(-100, lastValue2)) + "%");
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return super.getTextToDraw();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.getIsHovered() ? Colors.getColorTopStats3(isActive, this.getIsHovered()) : this.colorText;
   }

   @Override
   public void actionElement() {
      if (Game.menuManager.getVisibleInGame_War() && InGame_War.key.equals(this.key)) {
         Game.menuManager.setVisibleInGame_War(false);
      } else {
         Game.gameActiveProvince.resetLastActiveProvince();
         Game.setActiveProvinceID(-1);
         Game.clearActiveArmy();
         Game.menuManager.showInGame_Battle_HideMenus();
         InGame_War.key = this.key;
         Game.menuManager.rebuildInGame_War();
         if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_WAR_VIEW) {
            Game.mapModes.setActiveViewID(Game.mapModes.MODE_WAR_VIEW);
         } else {
            Game.mapModes.updateWarView(InGame_War.key);
         }
      }
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCivID;
      }
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = getHoverWar(this.key, this.iCivID);
   }

   public static MenuElement_Hover getHoverWar(String key, int iCivID) {
      try {
         if (key == null) {
            return null;
         }

         if (WarManager.lWars.containsKey(key)) {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Attackers"), CFG.FONT_BOLD, Colors.HOVER_NEGATIVE));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                  Game.getCiv(WarManager.lWars.get(key).lAggressors.get(0).iCivID).getCivName(),
                  "",
                  WarManager.lWars.get(key).lAggressors.get(0).iCivID,
                  CFG.FONT_BOLD_SMALL,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            if (WarManager.lWars.get(key).lAggressors.size() > 1) {
               for (int i = 1; i < WarManager.lWars.get(key).lAggressors.size() && i < 9; i++) {
                  nData.add(new MenuElement_HoverElement_Type_FlagTitle(WarManager.lWars.get(key).lAggressors.get(i).iCivID, 0, CFG.PADDING));
               }

               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Empty());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            }

            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Defenders"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                  Game.getCiv(WarManager.lWars.get(key).lDefenders.get(0).iCivID).getCivName(),
                  "",
                  WarManager.lWars.get(key).lDefenders.get(0).iCivID,
                  CFG.FONT_BOLD_SMALL,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            if (WarManager.lWars.get(key).lDefenders.size() > 1) {
               for (int i = 1; i < WarManager.lWars.get(key).lDefenders.size() && i < 9; i++) {
                  nData.add(new MenuElement_HoverElement_Type_FlagTitle(WarManager.lWars.get(key).lDefenders.get(i).iCivID, 0, CFG.PADDING));
               }

               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Empty());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            }

            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            float lastValue2 = WarManager.lWars.get(key).getWarScore_Side(iCivID);
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("WarScore") + ": ", CFG.FONT_BOLD));
            nData.add(
               new MenuElement_HoverElement_Type_TextTitle(
                  CFG.getPrecision2(Math.min(Math.max(WarManager.lWars.get(key).warScore * lastValue2, -100.0F), 100.0F), 1) + "%",
                  CFG.FONT_BOLD,
                  Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.victoryPoints, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            if (Math.abs(WarManager.lWars.get(key).warScore) >= 1.0F) {
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("InFavorOf") + ": ", CFG.FONT_REGULAR));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     Game.getCiv(
                           WarManager.lWars.get(key).warScore > 0.0F
                              ? WarManager.lWars.get(key).lAggressors.get(0).iCivID
                              : WarManager.lWars.get(key).lDefenders.get(0).iCivID
                        )
                        .getCivName(),
                     CFG.FONT_BOLD,
                     Colors.HOVER_POSITIVE
                  )
               );
               nData.add(
                  new MenuElement_HoverElement_Type_FlagTitle(
                     WarManager.lWars.get(key).warScore > 0.0F
                        ? WarManager.lWars.get(key).lAggressors.get(0).iCivID
                        : WarManager.lWars.get(key).lDefenders.get(0).iCivID,
                     CFG.PADDING,
                     0
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            }

            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("OccupiedProvinces") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(WarManager.lWars.get(key).warScoreFromOccupiedProvinces * lastValue2, 10) + "%",
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("WarScoreFromBattles") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(WarManager.lWars.get(key).warScoreFromBattles * lastValue2, 10) + "%",
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TickingWarScore") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(WarManager.lWars.get(key).tickingWarScore * lastValue2, 10) + "%", CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("WarStarted") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  Game_Calendar.getDate_ByTurnID(WarManager.lWars.get(key).iWarTurnID), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            if (Math.abs(WarManager.lWars.get(key).warScore) < GameValues.war.WAR_AUTO_WHITE_PEACE_IF_WARSCORE_BELOW) {
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("AWhitePeaceWillBeAutomaticallySignedIfThereIsNoActivityForACertainPeriodAndTheWarscoreRemainsLow"),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_LEFT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("WhitePeace")
                        + ": "
                        + Game_Calendar.getDate_ByTurnID(
                           WarManager.lWars.get(key).lastFight_TurnID + GameValues.war.WAR_AUTO_WHITE_PEACE_IF_NOTHING_HAPPENS_IN_WAR_DAYS
                        )
                        + ",  |"
                        + Game.lang.get("WarScore")
                        + "| < "
                        + CFG.getPrecision2(GameValues.war.WAR_AUTO_WHITE_PEACE_IF_WARSCORE_BELOW, 10),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_LEFT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
            }

            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("WarOverview"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.war, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            return new MenuElement_Hover(nElements, true);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return null;
   }

   @Override
   public int getSFX() {
      return SoundsManager.SOUND_CLICK_WAR;
   }
}
