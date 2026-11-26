package aoc.kingdoms.lukasz.menu_element.pieChart;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PieChart extends MenuElement {
   protected PieChart_Data pieChartData;
   protected int iPieChartWidth;
   protected int iPieChartHeight;
   protected long animationTimer = -1L;

   public PieChart(int iPosX, int iPosY, int iWidth, int iHeight, PieChart_Data nPieChartData, MenuElement_Hover menuElementHover) {
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.fontID = CFG.FONT_REGULAR;
      this.iPieChartWidth = iWidth;
      this.iPieChartHeight = iHeight;
      this.typeOfElement = MenuElement_Type.PIECHART;
      this.menuElementHover = menuElementHover;
      this.pieChartData = nPieChartData;
      this.pieChartData.sortAndBuild_PieChartValues();
      this.animationTimer = CFG.currentTimeMillis;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      this.drawPieChart(
         oSB,
         iTranslateX,
         iTranslateY,
         isActive,
         scrollableY,
         this.getPosX(),
         this.getPosY(),
         this.getWidth(),
         this.getHeight(),
         Renderer.pieChartRenderer.getWidth()
      );
   }

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
      PieChart_Renderer.pieChart_Frame.draw(oSB, nPosX + iTranslateX, nPosY + iTranslateY, this.iPieChartWidth, this.iPieChartHeight);
   }

   public void draw2(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY, float fPerc, Color nColor) {
      this.drawPieChart2(
         oSB,
         iTranslateX,
         iTranslateY,
         isActive,
         scrollableY,
         this.getPosX(),
         this.getPosY(),
         this.getWidth(),
         this.getHeight(),
         Renderer.pieChartRenderer.getWidth(),
         fPerc,
         nColor
      );
   }

   protected void drawPieChart2(
      SpriteBatch oSB,
      int iTranslateX,
      int iTranslateY,
      boolean isActive,
      boolean scrollableY,
      int nPosX,
      int nPosY,
      int nWidth,
      int nHeight,
      int nWidth_LEFT,
      float fPerc,
      Color nColor
   ) {
      Renderer.pieChartRenderer
         .draw2(
            oSB,
            PieChart_Renderer.pieChart_BG2,
            nPosX + iTranslateX,
            nPosY + iTranslateY,
            this.iPieChartWidth,
            this.iPieChartHeight,
            this.pieChartData,
            isActive || this.getIsHovered(),
            this.animationPerc(),
            fPerc,
            nColor
         );
      PieChart_Renderer.pieChart_Frame.draw(oSB, nPosX + iTranslateX, nPosY + iTranslateY, this.iPieChartWidth, this.iPieChartHeight);
   }

   protected float animationPerc() {
      return Math.min(1.0F, (float)(CFG.currentTimeMillis - this.animationTimer) / 150.0F);
   }

   public TextureRegion getPieChartBackground() {
      return PieChart_Renderer.pieChart_BG;
   }
}
