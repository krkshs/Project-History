package aoc.kingdoms.lukasz.menu_element.pieChart;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PieChart_WithStatsFlags extends PieChart_WithStats {
   public int flagWidth;
   public int flagHeight;

   public PieChart_WithStatsFlags(int iPosX, int iPosY, int iWidth, int iHeight, PieChart_Data nPieChartData, MenuElement_Hover menuElementHover) {
      super(iPosX, iPosY, iWidth, iHeight, nPieChartData, menuElementHover);
   }

   @Override
   public void initPieChartStats() {
      this.typeOfElement = MenuElement_Type.PIECHART_WITH_STATS;
      this.updateScrollable_Y();
      this.flagWidth = (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale());
      this.flagHeight = (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale());

      try {
         float tempMaxWidth = 0.0F;

         for (int i = 0; i < this.pieChartData.getPieChartValuesSize(); i++) {
            Renderer.glyphLayout
               .setText(Renderer.fontMain.get(this.fontID), "" + CFG.getPercentage(this.pieChartData.getPieChartValue(i).getPercentage(), 5) + "%");
            if (tempMaxWidth < Renderer.glyphLayout.width) {
               tempMaxWidth = Renderer.glyphLayout.width;
            }
         }

         this.statsExtraWidth = (int)(tempMaxWidth * this.TEXT_SCALE) + CFG.PADDING * 4 + this.flagWidth;
      } catch (IndexOutOfBoundsException var3) {
         this.statsExtraWidth = super.getWidth();
      } catch (IllegalArgumentException var4) {
         this.statsExtraWidth = super.getWidth();
      }
   }

   public int getStatsImageWidth() {
      return this.flagWidth;
   }

   public int getStatsImageHeight() {
      return this.flagHeight;
   }

   @Override
   protected void drawPieChart(
      SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY, int nPosX, int nPosY, int nWidth, int nHeight, int nWidth_LEFT
   ) {
      Renderer.pieChartRenderer
         .draw(
            oSB,
            this.getPieChartBackground(),
            nPosX + iTranslateX,
            nPosY + iTranslateY,
            this.iPieChartWidth,
            this.iPieChartHeight,
            this.pieChartData,
            isActive || this.getIsHovered(),
            this.animationPerc()
         );
      if (this.isDescriptionActive || this.hideAnimation) {
         if (!Renderer.clipView_Start(oSB, nPosX + iTranslateX, CFG.GAME_HEIGHT - nPosY - iTranslateY, nWidth, -nHeight)) {
            return;
         }

         Renderer.setFontScale(this.TEXT_SCALE);
         iTranslateY += this.statsPosY;
         float tempFlagScale = CFG.TEXT_HEIGHT * this.TEXT_SCALE / this.getStatsImageHeight();

         for (int i = 0; i < this.pieChartData.getPieChartValuesSize(); i++) {
            try {
               oSB.setColor(
                  new Color(
                     this.pieChartData.getPieChartValue_ColorR(i),
                     this.pieChartData.getPieChartValue_ColorG(i),
                     this.pieChartData.getPieChartValue_ColorB(i),
                     0.725F
                  )
               );
               Images.pix
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i + CFG.PADDING * i + iTranslateY,
                     CFG.COLOR_WIDTH,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2)
                  );
               oSB.setColor(
                  new Color(
                     this.pieChartData.getPieChartValue_ColorR(i),
                     this.pieChartData.getPieChartValue_ColorG(i),
                     this.pieChartData.getPieChartValue_ColorB(i),
                     0.425F
                  )
               );
               ImageManager.getImage(Images.gradientHorizontal)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i + CFG.PADDING * i + iTranslateY,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2)
                  );
               oSB.setColor(
                  new Color(
                     this.pieChartData.getPieChartValue_ColorR(i),
                     this.pieChartData.getPieChartValue_ColorG(i),
                     this.pieChartData.getPieChartValue_ColorB(i),
                     0.3F
                  )
               );
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i + CFG.PADDING * i + iTranslateY,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4
                  );
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i
                        + CFG.PADDING * i
                        + iTranslateY
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2)
                        - (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4,
                     false,
                     true
                  );
               oSB.setColor(Color.WHITE);
               Game.getCiv(this.pieChartData.getCivID(i))
                  .getFlag()
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING * 2 + CFG.COLOR_WIDTH + iTranslateX,
                     nPosY
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i
                        + CFG.PADDING * i
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 2
                        - (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE / 2.0F)
                        + iTranslateY,
                     this.flagWidth,
                     this.flagHeight
                  );
               ImageManager.getImage(Images.flag_rect)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING * 2 + CFG.COLOR_WIDTH + iTranslateX,
                     nPosY
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i
                        + CFG.PADDING * i
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 2
                        - (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE / 2.0F)
                        + iTranslateY,
                     this.flagWidth,
                     this.flagHeight
                  );
            } catch (IndexOutOfBoundsException var14) {
               oSB.setColor(new Color(Colors.COLOR_UNKNOWN.r, Colors.COLOR_UNKNOWN.g, Colors.COLOR_UNKNOWN.b, 0.45F));
               ImageManager.getImage(Images.gradientHorizontal)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i + CFG.PADDING * i + iTranslateY,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2)
                  );
               oSB.setColor(new Color(Colors.COLOR_UNKNOWN.r, Colors.COLOR_UNKNOWN.g, Colors.COLOR_UNKNOWN.b, 0.2F));
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     nPosY + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i + CFG.PADDING * i + iTranslateY,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4
                  );
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2)
                        - (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4
                        + nPosY
                        + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i
                        + CFG.PADDING * i
                        + iTranslateY,
                     CFG.COLOR_WIDTH + nWidth - nWidth_LEFT,
                     (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 4,
                     false,
                     true
                  );
               oSB.setColor(Color.WHITE);
            }

            Renderer.drawTextWithShadow(
               oSB,
               this.fontID,
               "" + CFG.getPercentage(this.pieChartData.getPieChartValue(i).getPercentage(), 5) + "%",
               nPosX + nWidth_LEFT + this.flagWidth + CFG.PADDING * 3 + CFG.COLOR_WIDTH + iTranslateX,
               nPosY
                  + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) * i
                  + CFG.PADDING * i
                  + (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE + CFG.PADDING * 2) / 2
                  - (int)(CFG.TEXT_HEIGHT * this.TEXT_SCALE / 2.0F)
                  + iTranslateY,
               Colors.COLOR_TEXT_PIE_CHART_STATS
            );
         }

         iTranslateY -= this.statsPosY;
         Renderer.resetFontScale();
         Renderer.clipView_End(oSB);
      }

      PieChart_Renderer.pieChart_Frame.draw(oSB, nPosX + iTranslateX, nPosY + iTranslateY, this.iPieChartWidth, this.iPieChartHeight);
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT_SMALL / CFG.CIV_FLAG_HEIGHT;
   }
}
