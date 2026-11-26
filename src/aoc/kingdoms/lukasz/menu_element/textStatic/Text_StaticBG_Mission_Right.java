package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Title;
import aoc.kingdoms.lukasz.menusInGame.InGame_Event;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_StaticBG_Mission_Right extends Text_StaticBG_Mission {
   public static long lTimeAnimation = 0L;
   public static int animationState = 0;
   public static final int ANIMATION_T = 2000;
   public int iLastTurnID = -65151;
   public int iRespondTurnID = 0;
   public String sText2 = "";
   public int iText2Width = 0;

   public Text_StaticBG_Mission_Right(
      String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int eventType, int id, int missionImage, int iRespondTurnID
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, eventType, id, missionImage);
      this.iRespondTurnID = iRespondTurnID;
      this.sText2 = Game.lang.get("DaysX", GameValues.events.EVENT_TIME_TO_RESPOND);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), this.sText2);
      this.iText2Width = (int)Renderer.glyphLayout.width;
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - this.textPosition.getTextPosition() * 2 - CFG.PADDING * 2 - this.iText2Width && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   @Override
   public void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      if (animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 1 + iTranslateY, (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc), 1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            oSB.setColor(
               new Color(
                  ButtonGame.getColorLine().r,
                  ButtonGame.getColorLine().g,
                  ButtonGame.getColorLine().b,
                  0.35F * Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F)
               )
            );
            Renderer.drawBox(
               oSB,
               Images.statsRectBGBorder,
               this.getPosX() - 1 + iTranslateX,
               this.getPosY() - 1 + iTranslateY,
               this.getWidth() + 2,
               this.getHeight() + 2,
               1.0F
            );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            oSB.setColor(
               new Color(
                  ButtonGame.getColorLine().r,
                  ButtonGame.getColorLine().g,
                  ButtonGame.getColorLine().b,
                  0.35F - 0.35F * Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F)
               )
            );
            Renderer.drawBox(
               oSB,
               Images.statsRectBGBorder,
               this.getPosX() - 1 + iTranslateX,
               this.getPosY() - 1 + iTranslateY,
               this.getWidth() + 2,
               this.getHeight() + 2,
               1.0F
            );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      try {
         if (Game_Calendar.TURN_ID != this.iLastTurnID) {
            this.iLastTurnID = Game_Calendar.TURN_ID;
            this.sText2 = Game.lang.get("DaysX", this.iRespondTurnID - Game_Calendar.TURN_ID);
            Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText2);
            this.iText2Width = (int)Renderer.glyphLayout.width;
            if (this.iRespondTurnID - Game_Calendar.TURN_ID <= 0) {
               Game.addSimpleTask(new Game.SimpleTask("RebuildInGameRight", this.getCurrent()) {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Right();
                     if (Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.id) {
                        Game.menuManager.setVisibleInGame_Event(false);
                     }
                  }
               });
            }
         }

         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sText2,
            this.getPosX() + this.getWidth() - this.textPosition.getTextPosition() - this.iText2Width + iTranslateX,
            this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
            Colors.getColorTopStats(isActive, this.getIsHovered())
         );
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Title(Images.missions, "", this.getText(), Colors.HOVER_GOLD, Game.lang.get("Event"), "", Colors.HOVER_RIGHT));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ClickToView"), CFG.FONT_BOLD_SMALL, Colors.HOVER_POSITIVE));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
