package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG_Economy extends Text_StaticBG {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int id;
   public float lastValue = -997654.3F;
   public float percOfMax = 0.0F;
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 0.5F);
   public static Color progressBar = new Color(0.0F, 0.60784316F, 0.3529412F, 0.5F);

   public Text_StaticBG_Economy(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.id = id;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      if (Game.getProvince(this.id).iProvinceInvestSize > 0) {
         oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(ProvinceDraw.progressBar));
         Renderer.drawBoxProgress(
            oSB,
            Images.statsRectBG,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            (int)(
               this.getWidth()
                  * (
                     1.0F
                        - (float)Game.getProvince(this.id).provinceInvestDaysLeft.get(0).daysLeft
                           / Game.getProvince(this.id).provinceInvestDaysLeft.get(0).investTime
                  )
            ),
            this.getHeight(),
            this.getWidth()
         );
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.2F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(progressBarBG);
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
         this.getWidth(),
         CFG.PADDING,
         1.0F
      );
      oSB.setColor(progressBar);
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
         (int)(this.getWidth() * this.percOfMax),
         CFG.PADDING,
         1.0F
      );
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

      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.id).getEconomyWithBonuses()) {
         this.setText("" + CFG.getPrecision2(Game.getProvince(this.id).getEconomyWithBonuses(), 100));
         this.lastValue = Game.getProvince(this.id).getEconomyWithBonuses();
         this.percOfMax = Math.min(1.0F, Math.max(0.15F, Game.getProvince(this.id).getEconomyWithBonuses() / Game.getMaxEconomy(this.id)));
      }

      return this.sText;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }
}
