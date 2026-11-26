package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonStatsBudget_Icon extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int iLevel;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;

   public ButtonStatsBudget_Icon(int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int iLevel) {
      this.init(this.sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.iLevel = iLevel;
      float iconScale = Math.min(1.0F, this.getImageScale(imageID));
      if (iLevel == 0) {
         iconScale *= 0.5F;
      } else if (iLevel == 1) {
         iconScale *= 0.75F;
      }

      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
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
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered()) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 1.0F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.25F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.25F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.65F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.65F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getClickable() && this.getIsHovered() && animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
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
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
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
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      if (this.isLeveLActive()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.175F));
         ImageManager.getImage(Images.activeSort)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.activeSort).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.activeSort).getHeight() * 3 / 4 + iTranslateY,
               false,
               true
            );
         oSB.setColor(Color.WHITE);
      }
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(1.0F, 1.0F, 1.0F, this.isLeveLActive() ? 1.0F : (this.getIsHovered() ? 0.65F : 0.15F));
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.iconWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      oSB.setColor(Color.WHITE);
   }

   private final float getImageScale(int iImageID) {
      return (float)(this.getHeight() - CFG.PADDING * 2) / ImageManager.getImage(iImageID).getHeight();
   }

   public boolean isLeveLActive() {
      return false;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (this.iLevel == 0) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LowTaxation"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TaxEfficiency") + ": ",
               CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_EFFICIENCY[0] * 100.0F, 100) + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncomeEconomy") + ": ",
               CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_INCOME_ECONOMY[0] * 100.0F, 100) + "%",
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Research") + ": ",
               "+" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_RESEARCH[0] * 100.0F, 100) + "%",
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyLegacy") + ": ",
               "+" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_LEGACY[0] * 100.0F, 100) + "%",
               Images.legacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Unrest") + ": ",
               "" + Game.lang.get("XPerMonth", CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_UNREST_PER_MONTH[0], 100)),
               Images.revolutionRisk,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else if (this.iLevel == 1) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("DefaultTaxation"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TaxEfficiency") + ": ",
               CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_EFFICIENCY[1] * 100.0F, 100) + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEUTRAL
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncomeEconomy") + ": ",
               CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_INCOME_ECONOMY[1] * 100.0F, 100) + "%",
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEUTRAL
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Unrest") + ": ",
               "" + Game.lang.get("XPerMonth", CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_UNREST_PER_MONTH[1], 100)),
               Images.revolutionRisk,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEUTRAL
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SevereTaxation"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TaxEfficiency") + ": ",
               "+" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_EFFICIENCY[2] * 100.0F, 100) + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncomeEconomy") + ": ",
               "+" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_INCOME_ECONOMY[2] * 100.0F, 100) + "%",
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Research") + ": ",
               "" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_RESEARCH[2] * 100.0F, 100) + "%",
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyLegacy") + ": ",
               "" + CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_LEGACY[2] * 100.0F, 100) + "%",
               Images.legacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Unrest") + ": ",
               "" + Game.lang.get("XPerMonth", CFG.getPrecision2(GameValues.budget.TAXATION_LEVEL_UNREST_PER_MONTH[2], 100)),
               Images.revolutionRisk,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }
}
