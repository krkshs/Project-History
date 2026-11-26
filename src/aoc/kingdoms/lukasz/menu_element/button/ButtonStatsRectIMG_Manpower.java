package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsRectIMG_Manpower extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int maxIconWidth;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int iProvinceID;
   public float lastValue = -997654.3F;

   public ButtonStatsRectIMG_Manpower(int nProvinceID, String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth) {
      this.iProvinceID = nProvinceID;
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.maxIconWidth = maxIconWidth;
      float iconScale = this.getImageScale(imageID) * 1.2F;
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

      try {
         if (Game.getProvince(this.iProvinceID).iProvinceIncreaseManpowerSize > 0) {
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
                           - (float)Game.getProvince(this.iProvinceID).provinceIncreaseManpowerDaysLeft.get(0).daysLeft
                              / Game.getProvince(this.iProvinceID).provinceIncreaseManpowerDaysLeft.get(0).investTime
                     )
               ),
               this.getHeight(),
               this.getWidth()
            );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BLUE.r,
            Colors.COLOR_GRADIENT_OVER_BLUE.g,
            Colors.COLOR_GRADIENT_OVER_BLUE.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive) / 2.0F
         )
      );
      Renderer.drawBox(
         oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight(), 1.0F
      );
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
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
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.iProvinceID).getManpower()) {
         this.setText("" + CFG.getPrecision2(Game.getProvince(this.iProvinceID).getManpower(), 10));
         this.lastValue = Game.getProvince(this.iProvinceID).getManpower();
      }

      return super.getTextToDraw();
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (!this.getIsHovered()
         || Game.getProvince(this.iProvinceID).getCivID() != Game.player.iCivID
            && Game.getCiv(Game.getProvince(this.iProvinceID).getCivID()).getPuppetOfCivID() != Game.player.iCivID) {
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - this.iconWidth / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
               this.iconWidth,
               this.iconHeight
            );
      } else {
         ImageManager.getImage(Game_Calendar.IMG_MANPOWER_UP)
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - this.iconWidth / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
               this.iconWidth,
               this.iconHeight
            );
      }

      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX()
            + CFG.PADDING * 2
            + this.maxIconWidth
            + (this.getWidth() - (CFG.PADDING * 2 + this.maxIconWidth)) / 2
            - this.getTextWidth() / 2
            + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }
}
