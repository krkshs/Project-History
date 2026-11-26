package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonStatsBudget_Right2 extends Button {
   public float lastValue = -997654.3F;
   public Color nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
   public Color nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
   public Color nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
   public int id;

   public ButtonStatsBudget_Right2(String sText, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_BOLD_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
   }

   public ButtonStatsBudget_Right2(String sText, int iPosX, int iPosY, int nWidth, int nHeight, int id) {
      this.init(sText, CFG.FONT_BOLD_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.id = id;
   }

   public ButtonStatsBudget_Right2(String sText, int iPosX, int iPosY, int nWidth, int nHeight, float fValue) {
      this.init(sText, CFG.FONT_BOLD_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      if (fValue < 0.0F) {
         this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
         this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
         this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight(), true, false
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
      Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
      Images.line_32_off1
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - 2 - Images.line_32_off1.getHeight() + iTranslateY,
            this.getWidth(),
            1,
            false,
            true
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_TITLE_BLUE.r, Colors.COLOR_GRADIENT_TITLE_BLUE.g, Colors.COLOR_GRADIENT_TITLE_BLUE.b, 0.35F));
      Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
      Images.line_32_off1
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - 1 - Images.line_32_off1.getHeight() + iTranslateY,
            this.getWidth(),
            1,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.getTextWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return this.nColorA;
      } else if (this.getIsHovered()) {
         return this.nColorH;
      } else {
         return this.getClickable() ? this.nColor : Colors.BUTTON_TEXT_DISABLED;
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Gold") + ": ", this.getText(), Images.gold, CFG.FONT_BOLD, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
