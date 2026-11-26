package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsBudget_Balance extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public String sText2;
   public int iTextWidth2;
   public int iTextHeight2;
   public float lastValue = -997654.3F;
   public Color nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
   public Color nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
   public Color nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;

   public ButtonStatsBudget_Balance(String sText, String sText2, int imageID, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.setText2(sText2);
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
      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.45F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.8F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX()
               + this.getWidth() / 2
               - (this.getTextWidth() + this.iTextWidth2 + this.iconWidth) / 2
               + this.getTextWidth()
               + this.iTextWidth2
               + CFG.PADDING
               + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iTextWidth2 + this.iconWidth) / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor2(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_BOLD,
         this.getText2(),
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iTextWidth2 + this.iconWidth) / 2 + this.getTextWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight2 / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   protected Color getColor2(boolean isActive) {
      return Colors.getColorTopStats(isActive, this.getIsHovered());
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

   public String getText2() {
      if (this.lastValue
         != Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
            + Game.getCiv(Game.player.iCivID).civBonuses.MonthlyIncome
            - Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth) {
         float fGold = Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
            + Game.getCiv(Game.player.iCivID).civBonuses.MonthlyIncome
            - Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth;
         this.setText2(
            (fGold > 0.0F ? "+" : "") + (fGold >= 1000.0F ? CFG.getNumberWithSpaces("" + (int)fGold) : CFG.getPrecision2(fGold, fGold >= 100.0F ? 10 : 100))
         );
         this.lastValue = fGold;
         if (fGold == 0.0F) {
            this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
            this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
            this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
         } else if (fGold > 0.0F) {
            this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
            this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
            this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
         } else {
            this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
            this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
            this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
         }
      }

      return this.sText2;
   }

   @Override
   public final void setText2(String sText2) {
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.iTextHeight2 = (int)Renderer.glyphLayout.height;
   }
}
