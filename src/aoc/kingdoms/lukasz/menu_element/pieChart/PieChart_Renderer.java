package aoc.kingdoms.lukasz.menu_element.pieChart;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

public class PieChart_Renderer {
   private static Color COLOR_UNKNOWN_DATA = new Color(0.0F, 0.0F, 0.0F, 1.0F);
   private PolygonSpriteBatch oPB;
   public static TextureRegion pieChart_BG;
   public static TextureRegion pieChart_BG2;
   public static Image pieChart_Frame;
   private Vector2 center;
   private Vector2 centerTop;
   private Vector2 leftTop;
   private Vector2 leftBottom;
   private Vector2 rightBottom;
   private Vector2 rightTop;
   private float[] fv;
   private PieChart_Renderer.IntersectAt intersectAt;
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 1.0F);
   public static Color progressBar = new Color(0.29411766F, 0.49019608F, 0.64705884F, 1.0F);
   public static final int ANIMATION_TIME = 150;

   public PieChart_Renderer() {
      pieChart_BG = new TextureRegion(ImageManager.loadTexture("ui/piechart/bg.png", Format.RGBA8888));
      pieChart_BG2 = new TextureRegion(ImageManager.loadTexture("ui/piechart/bg2.png", Format.RGBA8888));
      pieChart_Frame = new Image(ImageManager.loadTexture("ui/piechart/frame.png", Format.RGBA8888), TextureFilter.Linear);
      this.oPB = new PolygonSpriteBatch();
      this.center = new Vector2(pieChart_BG.getRegionWidth() / 2, pieChart_BG.getRegionHeight() / 2);
      this.centerTop = new Vector2(pieChart_BG.getRegionWidth() / 2, pieChart_BG.getRegionHeight());
      this.leftTop = new Vector2(0.0F, pieChart_BG.getRegionHeight());
      this.leftBottom = new Vector2(0.0F, 0.0F);
      this.rightBottom = new Vector2(pieChart_BG.getRegionWidth(), 0.0F);
      this.rightTop = new Vector2(pieChart_BG.getRegionWidth(), pieChart_BG.getRegionHeight());
      this.setPercentage(0.0F);
   }

   protected final void draw(
      SpriteBatch oSB, TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, PieChart_Data nData, boolean isActive, float fPerc
   ) {
      try {
         try {
            this.drawCircle_100Percent(
               oSB,
               nPieChartBG,
               nPosX,
               nPosY,
               nWidth,
               nHeight,
               new Color(nData.getPieChartValue_ColorR(0), nData.getPieChartValue_ColorG(0), nData.getPieChartValue_ColorB(0), 1.0F)
            );
         } catch (Exception var11) {
            this.drawCircle_100Percent(oSB, nPieChartBG, nPosX, nPosY, nWidth, nHeight, COLOR_UNKNOWN_DATA);
         }

         oSB.end();
         this.drawPieChart_Data(nPieChartBG, nPosX, nPosY, nWidth, nHeight, nData, fPerc);
         oSB.begin();
      } catch (Exception var12) {
         CFG.exceptionStack(var12);
      }
   }

   protected final void draw2(
      SpriteBatch oSB,
      TextureRegion nPieChartBG,
      int nPosX,
      int nPosY,
      int nWidth,
      int nHeight,
      PieChart_Data nData,
      boolean isActive,
      float fPerc,
      float fPercValue,
      Color nColor
   ) {
      try {
         try {
            this.drawCircle_100Percent(oSB, nPieChartBG, nPosX, nPosY, nWidth, nHeight, progressBarBG);
         } catch (IndexOutOfBoundsException var13) {
            this.drawCircle_100Percent(oSB, nPieChartBG, nPosX, nPosY, nWidth, nHeight, COLOR_UNKNOWN_DATA);
         }

         oSB.end();
         this.drawPieChart_Data2(nPieChartBG, nPosX, nPosY, nWidth, nHeight, nData, fPerc, fPercValue, nColor);
         oSB.begin();
      } catch (Exception var14) {
         CFG.exceptionStack(var14);
      }
   }

   private final void drawPieChart_Data(TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, PieChart_Data nData, float fPerc) {
      try {
         this.oPB.begin();
         float drawnPercentage = nData.getPieChartValue(0).getPercentage() * fPerc;

         for (int i = 1; i < nData.getPieChartValuesSize(); i++) {
            this.setPercentage(drawnPercentage);

            try {
               this.drawCircle(
                  nPieChartBG,
                  nPosX,
                  nPosY,
                  nWidth,
                  nHeight,
                  new Color(nData.getPieChartValue_ColorR(i), nData.getPieChartValue_ColorG(i), nData.getPieChartValue_ColorB(i), 1.0F)
               );
            } catch (IndexOutOfBoundsException var11) {
               this.drawCircle(nPieChartBG, nPosX, nPosY, nWidth, nHeight, COLOR_UNKNOWN_DATA);
            }

            drawnPercentage += nData.getPieChartValue(i).getPercentage() * fPerc;
         }

         this.oPB.end();
      } catch (Exception var12) {
      }
   }

   private final void drawPieChart_Data2(
      TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, PieChart_Data nData, float fPerc, float fPercValue, Color nColor
   ) {
      try {
         this.oPB.begin();
         this.setPercentage(fPercValue);
         this.drawCircle2(nPieChartBG, nPosX, nPosY, nWidth, nHeight, nColor);
         this.oPB.end();
      } catch (Exception var11) {
      }
   }

   private final void drawCircle_100Percent(SpriteBatch oSB, TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, Color nColor) {
      oSB.setColor(nColor);
      oSB.draw(
         nPieChartBG.getTexture(),
         nPosX,
         -nPosY - nHeight,
         0.0F,
         0.0F,
         nWidth,
         nHeight,
         1.0F,
         1.0F,
         0.0F,
         0,
         0,
         nPieChartBG.getRegionWidth(),
         nPieChartBG.getRegionHeight(),
         false,
         false
      );
      oSB.setColor(Color.WHITE);
   }

   private final void drawCircle_100Percent2(SpriteBatch oSB, TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, Color nColor) {
      oSB.setColor(new Color(nColor.r - 0.1F, nColor.g - 0.1F, nColor.b - 0.1F, nColor.a));
      oSB.draw(
         nPieChartBG.getTexture(),
         nPosX,
         -nPosY - nHeight,
         0.0F,
         0.0F,
         nWidth,
         nHeight,
         1.0F,
         1.0F,
         0.0F,
         0,
         0,
         nPieChartBG.getRegionWidth(),
         nPieChartBG.getRegionHeight(),
         false,
         false
      );
      oSB.setColor(Color.WHITE);
   }

   private final void drawCircle(TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, Color nColor) {
      if (this.fv != null) {
         EarClippingTriangulator e = new EarClippingTriangulator();
         ShortArray sv = e.computeTriangles(this.fv);
         PolygonRegion polyReg = new PolygonRegion(nPieChartBG, this.fv, sv.toArray());
         PolygonSprite poly = new PolygonSprite(polyReg);
         poly.setOrigin(0.0F, 0.0F);
         poly.setPosition(nPosX, CFG.GAME_HEIGHT - nHeight - nPosY);
         poly.setRotation(0.0F);
         poly.setColor(nColor);
         poly.setSize(nWidth, nHeight);
         poly.draw(this.oPB);
      }
   }

   private final void drawCircle2(TextureRegion nPieChartBG, int nPosX, int nPosY, int nWidth, int nHeight, Color nColor) {
      if (this.fv != null) {
         EarClippingTriangulator e = new EarClippingTriangulator();
         ShortArray sv = e.computeTriangles(this.fv);
         PolygonRegion polyReg = new PolygonRegion(nPieChartBG, this.fv, sv.toArray());
         PolygonSprite poly = new PolygonSprite(polyReg);
         poly.setOrigin(0.0F, 0.0F);
         poly.setPosition(nPosX, CFG.GAME_HEIGHT - nHeight - nPosY);
         poly.setRotation(0.0F);
         poly.setColor(new Color(nColor.r - 0.1F, nColor.g - 0.1F, nColor.b - 0.1F, nColor.a));
         poly.setSize(nWidth, nHeight);
         poly.draw(this.oPB);
      }
   }

   protected final void setPercentage(float percent) {
      float angle = this.convertToRadians(90.0F);
      angle -= this.convertToRadians(percent * 360.0F / 100.0F);
      float len = pieChart_BG.getRegionWidth() > pieChart_BG.getRegionHeight() ? pieChart_BG.getRegionWidth() : pieChart_BG.getRegionHeight();
      float dy = (float)(Math.sin(angle) * len);
      float dx = (float)(Math.cos(angle) * len);
      Vector2 line = new Vector2(this.center.x + dx, this.center.y + dy);
      Vector2 v = this.IntersectPoint(line);
      if (this.intersectAt == PieChart_Renderer.IntersectAt.TOP) {
         if (v.x >= pieChart_BG.getRegionWidth() / 2) {
            this.fv = new float[]{
               this.center.x,
               this.center.y,
               this.centerTop.x,
               this.centerTop.y,
               this.leftTop.x,
               this.leftTop.y,
               this.leftBottom.x,
               this.leftBottom.y,
               this.rightBottom.x,
               this.rightBottom.y,
               this.rightTop.x,
               this.rightTop.y,
               v.x,
               v.y
            };
         } else {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, v.x, v.y};
         }
      } else if (this.intersectAt == PieChart_Renderer.IntersectAt.BOTTOM) {
         this.fv = new float[]{
            this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, v.x, v.y
         };
      } else if (this.intersectAt == PieChart_Renderer.IntersectAt.LEFT) {
         this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, v.x, v.y};
      } else if (this.intersectAt == PieChart_Renderer.IntersectAt.RIGHT) {
         this.fv = new float[]{
            this.center.x,
            this.center.y,
            this.centerTop.x,
            this.centerTop.y,
            this.leftTop.x,
            this.leftTop.y,
            this.leftBottom.x,
            this.leftBottom.y,
            this.rightBottom.x,
            this.rightBottom.y,
            v.x,
            v.y
         };
      } else {
         this.fv = null;
      }
   }

   private final Vector2 IntersectPoint(Vector2 line) {
      Vector2 v = new Vector2();
      boolean isIntersect = Intersector.intersectSegments(this.leftTop, this.rightTop, this.center, line, v);
      if (isIntersect) {
         this.intersectAt = PieChart_Renderer.IntersectAt.TOP;
         return v;
      } else {
         isIntersect = Intersector.intersectSegments(this.leftBottom, this.rightBottom, this.center, line, v);
         if (isIntersect) {
            this.intersectAt = PieChart_Renderer.IntersectAt.BOTTOM;
            return v;
         } else {
            isIntersect = Intersector.intersectSegments(this.leftTop, this.leftBottom, this.center, line, v);
            if (isIntersect) {
               this.intersectAt = PieChart_Renderer.IntersectAt.LEFT;
               return v;
            } else {
               isIntersect = Intersector.intersectSegments(this.rightTop, this.rightBottom, this.center, line, v);
               if (isIntersect) {
                  this.intersectAt = PieChart_Renderer.IntersectAt.RIGHT;
                  return v;
               } else {
                  this.intersectAt = PieChart_Renderer.IntersectAt.NONE;
                  return null;
               }
            }
         }
      }
   }

   private final float convertToRadians(float angleInDegrees) {
      return angleInDegrees * (float) (Math.PI / 180.0);
   }

   public final int getWidth() {
      return pieChart_Frame.getWidth();
   }

   public final int getHeight() {
      return pieChart_Frame.getHeight();
   }

   protected static enum IntersectAt {
      NONE,
      TOP,
      BOTTOM,
      LEFT,
      RIGHT;
   }
}
