package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon2_GrowthRate extends Button {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int imageID;
   public int iProvinceID;
   public float lastValue = -997654.3F;

   public TextIcon2_GrowthRate(int nProvinceID, String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.iProvinceID = nProvinceID;
      this.imageID = imageID;
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static Color getColor_gradientXY() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F);
   }

   public static Color getColor_gradientFull() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.45F);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, !this.getIsHovered() && !isActive ? 0.5F : 0.6F)
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
         this.getWidth(),
         this.getTextH(),
         1.0F
      );
      if (Game.getProvince(this.iProvinceID).iProvinceIncreaseGrowthRateSize > 0) {
         oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
         Renderer.drawBox(
            oSB,
            Images.statsRectBG,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
            this.getWidth(),
            this.getTextH(),
            1.0F
         );
      }

      oSB.setColor(Color.WHITE);
      oSB.setColor(getColor_gradientXY());
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), this.getTextH());
      oSB.setColor(getColor_gradientFull());
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      try {
         if (Game.getProvince(this.iProvinceID).iProvinceIncreaseGrowthRateSize > 0) {
            oSB.setColor(new Color(ProvinceDraw.progressBar));
            Renderer.drawBoxProgress(
               oSB,
               Images.statsRectBG,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
               (int)(
                  this.getWidth()
                     * (
                        1.0F
                           - (float)Game.getProvince(this.iProvinceID).provinceIncreaseGrowthRateDaysLeft.get(0).daysLeft
                              / Game.getProvince(this.iProvinceID).provinceIncreaseGrowthRateDaysLeft.get(0).investTime
                     )
               ),
               this.getTextH(),
               this.getWidth()
            );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

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

      if (this.getIsHovered() && Game.getProvince(this.iProvinceID).getCivID() == Game.player.iCivID) {
         ImageManager.getImage(Images.populationUp)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.populationUp).getWidth() / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - ImageManager.getImage(Images.populationUp).getHeight() / 2 + iTranslateY
            );
      } else {
         ImageManager.getImage(this.getImageID())
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.getImageID()).getWidth() / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - ImageManager.getImage(this.getImageID()).getHeight() / 2 + iTranslateY
            );
      }

      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - this.getTextHeight() + iTranslateY,
         this.getColor(isActive)
      );
   }

   public int getTextH() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 2;
   }

   public int getImageID() {
      return this.imageID;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.iProvinceID).getGrowthRateWithBonuses()) {
         this.setText("" + CFG.getPrecision2(Game.getProvince(this.iProvinceID).getGrowthRateWithBonuses(), 1) + "%");
         this.lastValue = Game.getProvince(this.iProvinceID).getGrowthRateWithBonuses();
      }

      return super.getTextToDraw();
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }
}
