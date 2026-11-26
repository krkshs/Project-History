package aoc.kingdoms.lukasz.map.civilization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class CivilizationRegion {
   private int iRegionID;
   private List<Integer> lProvinces = new ArrayList<>();
   private int iProvincesSize;
   protected List<Integer> lCoastlineProvinces = new ArrayList<>();
   private List<Integer> shortestLine = new ArrayList<>();
   private int iMinX = 0;
   private int iMaxX = 0;
   private int iMinY = 0;
   private int iMaxY = 0;
   public int iAveragePointPosX = 0;
   public int iAveragePointPosY = 0;
   private float fontScale = 1.0F;
   private float fontScale2 = 1.0F;
   private float fAngle = 0.0F;
   private float fAngle_Low = 0.0F;
   private int iCharMaxWidth = 0;
   private int iCharMaxHeight = 0;
   public List<Point_XY> lPoints = new ArrayList<>();
   public List<Matrix4> drawMatrix4 = new ArrayList<>();
   public Point_XY centerCharXY;
   protected boolean drawName = true;
   private List<Boolean> triedToUse = new ArrayList<>();
   private int numOfTries = 0;

   public CivilizationRegion() {
   }

   public CivilizationRegion(int nProvinceID, int iRegionID) {
      this.iRegionID = iRegionID;
      this.addProvince(nProvinceID);
   }

   public final void drawCivRegion(SpriteBatch oSB) {
      if (this.drawName && this.getFontScale() * Game.mapScale.getCurrentScale() > Game.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT) {
         List<Integer> shortestPath = this.getShortestPath();
         if (shortestPath.size() > 1) {
            if (Game.mapCoords.getSecondSideOfMap()) {
               this.drawCivilizationName(oSB, 0, this.getProvince(shortestPath.get(0)), this.getFontScale());
               this.drawCivilizationName_SecondSideOfMap(oSB, 0, this.getProvince(shortestPath.get(0)), this.getFontScale());
            } else {
               this.drawCivilizationName(oSB, 0, this.getProvince(shortestPath.get(0)), this.getFontScale());
            }
         }
      }
   }

   public final void drawCivRegion_Low(SpriteBatch oSB) {
      if (this.drawName && this.getFontScale() * Game.mapScale.getCurrentScale() > Game.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT) {
         List<Integer> shortestPath = this.getShortestPath();
         if (shortestPath.size() > 1) {
            if (Game.mapCoords.getSecondSideOfMap()) {
               this.drawCivilizationName_Low(oSB, 0, this.getProvince(shortestPath.get(0)), this.fontScale2);
            } else {
               this.drawCivilizationName_Low(oSB, 0, this.getProvince(shortestPath.get(0)), this.fontScale2);
            }
         }
      }
   }

   public final synchronized void drawCivilizationName(SpriteBatch oSB, int nFontID, int fromProvinceID, float fontScale) {
      Renderer.fontBorder.get(nFontID).getData().setScale(fontScale);

      for (int i = 0; i < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength(); i++) {
         Renderer.drawTextRotatedBorder(
            oSB,
            nFontID,
            Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameCharacter(i),
            Game.mapCoords.getPosX() + this.lPoints.get(i).getPosX() - this.centerCharXY.getPosX(),
            Game.mapCoords.getPosY() + this.lPoints.get(i).getPosY() - this.centerCharXY.getPosY(),
            this.drawMatrix4.get(i)
         );
      }
   }

   protected final synchronized void drawCivilizationName_SecondSideOfMap(SpriteBatch oSB, int nFontID, int fromProvinceID, float fontScale) {
      if (Game.getProvince(fromProvinceID).getTranslateProvincePosX() > 0) {
         Renderer.fontBorder.get(nFontID).getData().setScale(fontScale);

         for (int i = 0; i < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength(); i++) {
            Renderer.drawTextRotatedBorder(
               oSB,
               nFontID,
               Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameCharacter(i),
               Game.mapCoords.getSecondSideOfMap_MoveX() + Game.mapCoords.getPosX() + this.lPoints.get(i).getPosX() - this.centerCharXY.getPosX(),
               Game.mapCoords.getPosY() + this.lPoints.get(i).getPosY() - this.centerCharXY.getPosY(),
               this.drawMatrix4.get(i)
            );
         }
      }
   }

   public final synchronized void drawCivilizationName_Low(SpriteBatch oSB, int nFontID, int fromProvinceID, float fontScale) {
      Renderer.fontBorder.get(nFontID).getData().setScale(fontScale);
      Renderer.drawTextRotatedBorder_2(
         oSB,
         nFontID,
         Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).sCivName_UpperCase,
         Game.mapCoords.getPosX() + this.lPoints.get(0).getPosX() - this.centerCharXY.getPosX(),
         Game.mapCoords.getPosY() + this.lPoints.get(0).getPosY() - this.centerCharXY.getPosY(),
         this.fAngle_Low
      );
   }

   public final void addProvince(int nProvinceID) {
      this.lProvinces.add(nProvinceID);
      this.iProvincesSize = this.lProvinces.size();

      for (int i = 0; i < Game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); i++) {
         if (Game.getProvince(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getLevelOfPort() == -2) {
            this.lCoastlineProvinces.add(nProvinceID);
            break;
         }
      }

      Game.getProvince(nProvinceID).setCivRegionID(this.iRegionID);
   }

   public final void addProvince_Just(int nProvinceID) {
      this.lProvinces.add(nProvinceID);
      this.iProvincesSize = this.lProvinces.size();
   }

   public final void removeProvinceID(int nProvinceID) {
      for (int i = 0; i < this.iProvincesSize; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            this.lProvinces.remove(i);
            this.iProvincesSize = this.lProvinces.size();

            for (int j = 0; j < this.lCoastlineProvinces.size(); j++) {
               if (this.lCoastlineProvinces.get(j) == nProvinceID) {
                  this.lCoastlineProvinces.remove(j);
                  break;
               }
            }

            Game.getProvince(nProvinceID).setCivRegionID(-1);
            break;
         }
      }
   }

   public final void removeProvince(int i) {
      Game.getProvince(this.lProvinces.get(i)).setCivRegionID(-1);

      for (int j = 0; j < this.lCoastlineProvinces.size(); j++) {
         if (this.lCoastlineProvinces.get(j) == this.lProvinces.get(i)) {
            this.lCoastlineProvinces.remove(j);
            break;
         }
      }

      this.lProvinces.remove(i);
      this.iProvincesSize = this.lProvinces.size();
   }

   protected final boolean containsProvince(int nProvinceID) {
      for (int i = 0; i < this.iProvincesSize; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            return true;
         }
      }

      return false;
   }

   public final void buildRegionPath_TriedToUse() {
      this.triedToUse.clear();

      for (int i = 0; i < this.iProvincesSize; i++) {
         this.triedToUse.add(false);
      }

      for (int i = 0; i < this.iProvincesSize; i++) {
         if (Game.getProvince(this.lProvinces.get(i)).getBelowZero()) {
            this.triedToUse.set(i, true);
         }
      }

      this.numOfTries = 0;
   }

   public final boolean buildRegionPath() {
      try {
         this.drawName = false;
         this.buildMinMaxBounds();
         if (this.lProvinces.size() == 1) {
            return false;
         } else {
            if (this.lProvinces.size() > 1) {
               if (!Game.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PROVINCES_IN_GAME) {
                  return false;
               }

               int startID = -1;

               for (int i = 0; i < this.iProvincesSize; i++) {
                  if (!this.triedToUse.get(i)) {
                     startID = i;
                     break;
                  }
               }

               if (startID == -1) {
                  return false;
               }

               int fromProvinceID_LEFTRIGHT = startID;
               int toProvinceID_LEFTRIGHT = startID;
               int fromProvinceID_RIGHTLEFT = startID;
               int toProvinceID_RIGHTLEFT = startID;
               int fromProvinceID_BOTTOM = startID;
               int toProvinceID_TOP = startID;
               int fromProvinceID_LR = startID;
               int toProvinceID_LR = startID;
               int leftBottomDistance = (int)Math.sqrt(
                  Math.pow(this.iMinX - Game.getProvince(this.lProvinces.get(startID)).getCenterX(), 2.0)
                     + Math.pow(this.iMaxY - Game.getProvince(this.lProvinces.get(startID)).getCenterY(), 2.0)
               );
               int rightTopDistance = (int)Math.sqrt(
                  Math.pow(this.iMaxX - Game.getProvince(this.lProvinces.get(startID)).getCenterX(), 2.0)
                     + Math.pow(this.iMinY - Game.getProvince(this.lProvinces.get(startID)).getCenterY(), 2.0)
               );
               int rightBottomDistance = (int)Math.sqrt(
                  Math.pow(this.iMaxX - Game.getProvince(this.lProvinces.get(startID)).getCenterX(), 2.0)
                     + Math.pow(this.iMaxY - Game.getProvince(this.lProvinces.get(startID)).getCenterY(), 2.0)
               );
               int leftTopDistance = (int)Math.sqrt(
                  Math.pow(this.iMinX - Game.getProvince(this.lProvinces.get(startID)).getCenterX(), 2.0)
                     + Math.pow(this.iMinY - Game.getProvince(this.lProvinces.get(startID)).getCenterY(), 2.0)
               );
               this.triedToUse.add(true);

               for (int ix = startID + 1; ix < this.iProvincesSize; ix++) {
                  if (!this.triedToUse.get(ix)) {
                     int toPosX = Game.getProvince(this.lProvinces.get(ix)).iCenterShiftX;
                     int toPosY = Game.getProvince(this.lProvinces.get(ix)).iCenterShiftY;
                     int tempDistance = getLineWidth(this.iMinX, this.iMaxY, toPosX, toPosY);
                     if (tempDistance < leftBottomDistance) {
                        leftBottomDistance = tempDistance;
                        fromProvinceID_LEFTRIGHT = ix;
                     }

                     tempDistance = getLineWidth(this.iMaxX, this.iMinY, toPosX, toPosY);
                     if (tempDistance < rightTopDistance) {
                        rightTopDistance = tempDistance;
                        toProvinceID_LEFTRIGHT = ix;
                     }

                     tempDistance = getLineWidth(this.iMaxX, this.iMaxY, toPosX, toPosY);
                     if (tempDistance < rightBottomDistance) {
                        rightBottomDistance = tempDistance;
                        fromProvinceID_RIGHTLEFT = ix;
                     }

                     tempDistance = getLineWidth(this.iMinX, this.iMinY, toPosX, toPosY);
                     if (tempDistance < leftTopDistance) {
                        leftTopDistance = tempDistance;
                        toProvinceID_RIGHTLEFT = ix;
                     }

                     if (Game.getProvince(this.lProvinces.get(fromProvinceID_BOTTOM)).iCenterShiftY < toPosY) {
                        fromProvinceID_BOTTOM = ix;
                     }

                     if (Game.getProvince(this.lProvinces.get(toProvinceID_TOP)).iCenterShiftY > toPosY) {
                        toProvinceID_TOP = ix;
                     }

                     if (Game.getProvince(this.lProvinces.get(fromProvinceID_LR)).iCenterShiftX > toPosX
                        && toPosY >= this.iMinY + (this.iMaxY - this.iMinY) / 2) {
                        fromProvinceID_LR = ix;
                     }

                     if (Game.getProvince(this.lProvinces.get(toProvinceID_LR)).iCenterShiftX < toPosX && toPosY <= this.iMinY + (this.iMaxY - this.iMinY) / 2) {
                        toProvinceID_LR = ix;
                     }
                  }
               }

               if (this.getLineWidth(fromProvinceID_LEFTRIGHT, toProvinceID_LEFTRIGHT) > this.getLineWidth(fromProvinceID_RIGHTLEFT, toProvinceID_RIGHTLEFT)) {
                  if (this.getLineWidth(fromProvinceID_LEFTRIGHT, toProvinceID_LEFTRIGHT) > this.getLineWidth(fromProvinceID_BOTTOM, toProvinceID_TOP)) {
                     if (this.getLineWidth(fromProvinceID_LEFTRIGHT, toProvinceID_LEFTRIGHT) > this.getLineWidth(fromProvinceID_LR, toProvinceID_LR)) {
                        this.shortestLine.add(fromProvinceID_LEFTRIGHT);
                        this.shortestLine.add(toProvinceID_LEFTRIGHT);
                     } else {
                        this.shortestLine.add(fromProvinceID_LR);
                        this.shortestLine.add(toProvinceID_LR);
                     }
                  } else if (this.getLineWidth(fromProvinceID_BOTTOM, toProvinceID_TOP) > this.getLineWidth(fromProvinceID_LR, toProvinceID_LR)) {
                     this.shortestLine.add(fromProvinceID_BOTTOM);
                     this.shortestLine.add(toProvinceID_TOP);
                  } else {
                     this.shortestLine.add(fromProvinceID_LR);
                     this.shortestLine.add(toProvinceID_LR);
                  }
               } else if (this.getLineWidth(fromProvinceID_RIGHTLEFT, toProvinceID_RIGHTLEFT) > this.getLineWidth(fromProvinceID_BOTTOM, toProvinceID_TOP)) {
                  if (this.getLineWidth(fromProvinceID_RIGHTLEFT, toProvinceID_RIGHTLEFT) > this.getLineWidth(fromProvinceID_LR, toProvinceID_LR)) {
                     this.shortestLine.add(fromProvinceID_RIGHTLEFT);
                     this.shortestLine.add(toProvinceID_RIGHTLEFT);
                  } else {
                     this.shortestLine.add(fromProvinceID_LR);
                     this.shortestLine.add(toProvinceID_LR);
                  }
               } else if (this.getLineWidth(fromProvinceID_BOTTOM, toProvinceID_TOP) > this.getLineWidth(fromProvinceID_LR, toProvinceID_LR)) {
                  this.shortestLine.add(fromProvinceID_BOTTOM);
                  this.shortestLine.add(toProvinceID_TOP);
               } else {
                  this.shortestLine.add(fromProvinceID_LR);
                  this.shortestLine.add(toProvinceID_LR);
               }

               if (Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCenterX()
                  > Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getCenterX()) {
                  int tempS = this.shortestLine.get(0);
                  this.shortestLine.set(0, this.shortestLine.get(1));
                  this.shortestLine.set(1, tempS);
               }

               if (this.shortestLine.size() == 0 || this.shortestLine.get(0) == this.shortestLine.get(1)) {
                  this.shortestLine.clear();
                  this.triedToUse.clear();
                  return false;
               }

               Point_XY tD = this.canDrawTextProperly(this.lProvinces.get(this.shortestLine.get(0)), this.lProvinces.get(this.shortestLine.get(1)));
               if (tD != null) {
                  if (getLineWidth(
                        tD.getPosX(),
                        tD.getPosY(),
                        Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).iCenterShiftX,
                        Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).iCenterShiftY
                     )
                     < getLineWidth(
                        tD.getPosX(),
                        tD.getPosY(),
                        Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).iCenterShiftX,
                        Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).iCenterShiftY
                     )) {
                     this.triedToUse.set(this.shortestLine.get(0), true);
                  } else {
                     this.triedToUse.set(this.shortestLine.get(1), true);
                  }

                  this.shortestLine.clear();
                  return this.numOfTries++ < 100 ? this.buildRegionPath() : false;
               }

               tD = null;
               this.triedToUse.clear();
            }

            this.updateDrawRegionName();
            return true;
         }
      } catch (StackOverflowError var18) {
         CFG.exceptionStack(var18);
         return false;
      }
   }

   private final void buildMinMaxBounds() {
      try {
         this.iMinX = Game.getProvince(this.lProvinces.get(0)).getMinX();
         this.iMaxX = Game.getProvince(this.lProvinces.get(0)).getMaxX();
         this.iMinY = Game.getProvince(this.lProvinces.get(0)).getMinY();
         this.iMaxY = Game.getProvince(this.lProvinces.get(0)).getMaxY();

         for (int i = 1; i < this.iProvincesSize; i++) {
            if (Game.getProvince(this.lProvinces.get(i)).getMinX() < this.iMinX) {
               this.iMinX = Game.getProvince(this.lProvinces.get(i)).getMinX();
            }

            if (Game.getProvince(this.lProvinces.get(i)).getMaxX() > this.iMaxX) {
               this.iMaxX = Game.getProvince(this.lProvinces.get(i)).getMaxX();
            }

            if (Game.getProvince(this.lProvinces.get(i)).getMinY() < this.iMinY) {
               this.iMinY = Game.getProvince(this.lProvinces.get(i)).getMinY();
            }

            if (Game.getProvince(this.lProvinces.get(i)).getMaxY() > this.iMaxY) {
               this.iMaxY = Game.getProvince(this.lProvinces.get(i)).getMaxY();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   private final Point_XY canDrawTextProperly(int fromProvinceID, int toProvinceID) {
      this.buildAveragePoint();
      List<Point_XY> tempPoints = new ArrayList<>();
      int tX = Game.getProvince(fromProvinceID).iCenterShiftX;
      int tX2 = Game.getProvince(toProvinceID).iCenterShiftX;
      int extra10X = tX + (int)Math.abs((tX2 - tX) * 0.15F) * (tX > tX2 ? -1 : 1);
      int extra10X2 = tX2 + (int)Math.abs((tX2 - tX) * 0.15F) * (tX2 > tX ? -1 : 1);
      int tY = Game.getProvince(fromProvinceID).iCenterShiftY;
      int tY2 = Game.getProvince(toProvinceID).iCenterShiftY;
      int extra10Y = tY + (int)Math.abs((tY2 - tY) * 0.15F) * (tY > tY2 ? -1 : 1);
      int extra10Y2 = tY2 + (int)Math.abs((tY2 - tY) * 0.15F) * (tY2 > tY ? -1 : 1);
      int iPrecision = Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength() * 10;
      Vector2[] vPoints = new Vector2[iPrecision];
      Vector2[] dataSet = new Vector2[]{
         new Vector2(extra10X, extra10Y),
         new Vector2(extra10X, extra10Y),
         new Vector2(this.iAveragePointPosX, this.iAveragePointPosY),
         new Vector2(extra10X2, extra10Y2),
         new Vector2(extra10X2, extra10Y2)
      };
      CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

      for (int i = 0; i < iPrecision; i++) {
         vPoints[i] = new Vector2();
         oCatmull.valueAt(vPoints[i], i / (iPrecision - 1.0F));
      }

      float tempPrecisionWidth = 0.0F;

      for (int i = 0; i < iPrecision - 1; i++) {
         tempPrecisionWidth += getLineWidth2((int)vPoints[i].x, (int)vPoints[i].y, (int)vPoints[i + 1].x, (int)vPoints[i + 1].y);
      }

      tempPoints.add(new Point_XY((int)vPoints[0].x, (int)vPoints[0].y));
      float acceptableWidth = 0.0F;

      try {
         acceptableWidth = tempPrecisionWidth / (Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength() - 1);
      } catch (ArithmeticException var23) {
         CFG.exceptionStack(var23);
      }

      float currentPointsWidth = 0.0F;
      int i = 1;

      for (int startPrecision = 0; i < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength(); i++) {
         while (startPrecision < iPrecision - 1) {
            float tempPrecisionWidth2 = getLineWidth2(
               (int)vPoints[startPrecision].x, (int)vPoints[startPrecision].y, (int)vPoints[startPrecision + 1].x, (int)vPoints[startPrecision + 1].y
            );
            if (currentPointsWidth + tempPrecisionWidth2 >= acceptableWidth) {
               tempPoints.add(new Point_XY((int)vPoints[startPrecision].x, (int)vPoints[startPrecision].y));
               currentPointsWidth = acceptableWidth - (currentPointsWidth + tempPrecisionWidth2);
               break;
            }

            currentPointsWidth += tempPrecisionWidth2;
            startPrecision++;
         }
      }

      tempPoints.add(new Point_XY((int)vPoints[vPoints.length - 1].x, (int)vPoints[vPoints.length - 1].y));

      for (int ix = tempPoints.size() - 1; ix >= 0; ix--) {
         int nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ix).getPosX(), tempPoints.get(ix).getPosY());
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ix);
         }
      }

      i = (int)this.buildScaleOfText(0);

      for (int ixx = tempPoints.size() - 1; ixx >= 0; ixx--) {
         int nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() + i, tempPoints.get(ixx).getPosY());
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() - i, tempPoints.get(ixx).getPosY());
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX(), tempPoints.get(ixx).getPosY() + i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX(), tempPoints.get(ixx).getPosY() - i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() - i, tempPoints.get(ixx).getPosY() + i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() + i, tempPoints.get(ixx).getPosY() + i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() - i, tempPoints.get(ixx).getPosY() - i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }

         nNewChosenProvinceID = Game.setProvinceID_Point(tempPoints.get(ixx).getPosX() + i, tempPoints.get(ixx).getPosY() - i);
         if (nNewChosenProvinceID >= 0 && Game.getProvince(fromProvinceID).getCivID() != Game.getProvince(nNewChosenProvinceID).getCivID()) {
            return tempPoints.get(ixx);
         }
      }

      return null;
   }

   private final void buildAveragePoint() {
      long lAverageX = 0L;
      long lAverageY = 0L;
      int tempMinX = Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getMinX();
      int tempMaxX = Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getMaxX();
      int tempMinY = Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getMinY();
      int tempMaxY = Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getMaxY();
      if (Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMinX() < tempMinX) {
         tempMinX = Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMinX();
      }

      if (Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMaxX() > tempMaxX) {
         tempMaxX = Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMaxX();
      }

      if (Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMinY() < tempMinY) {
         tempMinY = Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMinY();
      }

      if (Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMaxY() > tempMaxY) {
         tempMaxY = Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getMaxY();
      }

      int tSize = 0;

      for (int i = 0; i < this.getProvincesSize(); i++) {
         if (Game.getProvince(this.getProvince(i)).iCenterShiftX < tempMinX || Game.getProvince(this.getProvince(i)).iCenterShiftX > tempMaxX) {
            if ((
                  Game.getProvince(this.getProvince(i)).getMinX() > tempMinX && Game.getProvince(this.getProvince(i)).getMinX() <= tempMaxX
                     || Game.getProvince(this.getProvince(i)).getMaxX() > tempMinX && Game.getProvince(this.getProvince(i)).getMaxX() <= tempMaxX
               )
               && (
                  Game.getProvince(this.getProvince(i)).getMinY() >= tempMinY && Game.getProvince(this.getProvince(i)).getMinY() <= tempMaxY
                     || Game.getProvince(this.getProvince(i)).getMaxY() >= tempMinY && Game.getProvince(this.getProvince(i)).getMaxY() <= tempMaxY
               )) {
               lAverageX += Game.getProvince(this.getProvince(i)).iCenterShiftX;
               lAverageY += Game.getProvince(this.getProvince(i)).iCenterShiftY;
               tSize++;
            }
         } else if (Game.getProvince(this.getProvince(i)).iCenterShiftY >= tempMinY && Game.getProvince(this.getProvince(i)).iCenterShiftY <= tempMaxY) {
            lAverageX += Game.getProvince(this.getProvince(i)).iCenterShiftX;
            lAverageY += Game.getProvince(this.getProvince(i)).iCenterShiftY;
            tSize++;
         }
      }

      if (tSize == 0) {
         tSize = 1;
      }

      this.iAveragePointPosX = (int)(lAverageX / tSize);
      this.iAveragePointPosY = (int)(lAverageY / tSize);
      int tAveX = (
            Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCenterX()
               + Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getCenterX()
         )
         / 2;
      int tAveY = (
            Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCenterY()
               + Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).getCenterY()
         )
         / 2;
      this.iAveragePointPosX = (int)(tAveX + (tAveX - this.iAveragePointPosX) * 0.6F);
      this.iAveragePointPosY = (int)(tAveY + (tAveY - this.iAveragePointPosY) * 0.6F);
   }

   protected final float buildScaleOfText(int nFontID) {
      float outTextH = 1.0F;

      try {
         if (this.shortestLine.size() > 1) {
            float iDistance = (float)Math.sqrt(
               Math.pow(
                     Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).iCenterShiftX
                        - Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).iCenterShiftX,
                     2.0
                  )
                  + Math.pow(
                     Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).iCenterShiftY
                        - Game.getProvince(this.lProvinces.get(this.shortestLine.get(1))).iCenterShiftY,
                     2.0
                  )
            );
            iDistance *= 0.95F;
            GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
            synchronized (this) {
               glyphLayout.setText(
                  Renderer.fontBorder.get(nFontID), Game.getCiv(Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCivID()).sCivName_UpperCase
               );
               int tempNumOfIterations = 0;
               float tempScale = this.fontScale;

               try {
                  while (true) {
                     if (iDistance > glyphLayout.width) {
                        tempScale += 0.05F;
                        Renderer.fontBorder.get(nFontID).getData().setScale(tempScale);
                        glyphLayout.setText(
                           Renderer.fontBorder.get(nFontID),
                           Game.getCiv(Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCivID()).sCivName_UpperCase
                        );
                        outTextH = glyphLayout.height;
                        if (iDistance < glyphLayout.width) {
                           this.fontScale = tempScale - 0.05F;
                           break;
                        }
                     } else {
                        tempScale -= 0.05F;
                        Renderer.fontBorder.get(nFontID).getData().setScale(tempScale);
                        glyphLayout.setText(
                           Renderer.fontBorder.get(nFontID),
                           Game.getCiv(Game.getProvince(this.lProvinces.get(this.shortestLine.get(0))).getCivID()).sCivName_UpperCase
                        );
                        outTextH = glyphLayout.height;
                        if (iDistance > glyphLayout.width) {
                           this.fontScale = tempScale + 0.05F;
                           break;
                        }
                     }

                     if (tempNumOfIterations++ > 999) {
                        this.fontScale = 1.0E-4F;
                        break;
                     }
                  }
               } catch (IndexOutOfBoundsException var13) {
                  this.fontScale = 1.0E-4F;
               } catch (NullPointerException var14) {
                  this.fontScale = 1.0E-4F;
                  CFG.exceptionStack(var14);

                  try {
                     Game.getCiv(Game.getProvince(this.lProvinces.get(0)).getCivID()).setUpdateRegions(true);
                  } catch (Exception var12) {
                  }
               } catch (IllegalStateException var15) {
                  this.fontScale = 1.0E-4F;
                  CFG.exceptionStack(var15);
               }
            }

            this.buildAveragePoint();
            this.buildDrawData(nFontID);
         }
      } catch (NullPointerException var17) {
         this.fontScale = 1.0E-4F;

         try {
            Game.getCiv(Game.getProvince(this.lProvinces.get(0)).getCivID()).setUpdateRegions(true);
         } catch (Exception var11) {
         }
      }

      this.fontScale2 = this.fontScale * 0.875F;
      return outTextH;
   }

   public final void buildDrawData(int nFontID) {
      synchronized (this) {
         Renderer.fontBorder.get(nFontID).getData().setScale(this.fontScale);
         this.iCharMaxWidth = 1;
         this.iCharMaxHeight = 1;

         try {
            for (int i = 0; i < Game.getCiv(Game.getProvince(this.shortestLine.get(0)).getCivID()).getCivNameLength(); i++) {
               GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
               glyphLayout.setText(
                  Renderer.fontBorder.get(nFontID), "" + Game.getCiv(Game.getProvince(this.shortestLine.get(0)).getCivID()).getCivNameCharacter(i)
               );
               if (glyphLayout.width > this.iCharMaxWidth) {
                  this.iCharMaxWidth = (int)glyphLayout.width;
               }

               if (glyphLayout.height > this.iCharMaxWidth) {
                  this.iCharMaxHeight = (int)glyphLayout.height;
               }
            }
         } catch (IndexOutOfBoundsException var41) {
            CFG.exceptionStack(var41);
         } catch (NullPointerException var42) {
            CFG.exceptionStack(var42);

            try {
               Game.getCiv(Game.getProvince(this.lProvinces.get(0)).getCivID()).setUpdateRegions(true);
            } catch (Exception var34) {
            }
         } catch (IllegalStateException var43) {
            CFG.exceptionStack(var43);
         }
      }

      this.fAngle = (float)(
         Math.atan2(
               Game.getProvince(this.getProvince(this.shortestLine.get(0))).iCenterShiftY
                  - Game.getProvince(this.getProvince(this.shortestLine.get(1))).iCenterShiftY,
               -Game.getProvince(this.getProvince(this.shortestLine.get(0))).iCenterShiftX
                  + Game.getProvince(this.getProvince(this.shortestLine.get(1))).iCenterShiftX
            )
            * 180.0
            / Math.PI
      );
      this.fAngle_Low = (float)(
         Math.atan2(
               Game.getProvince(this.getProvince(this.shortestLine.get(0))).iCenterShiftY
                  - Game.getProvince(this.getProvince(this.shortestLine.get(this.shortestLine.size() - 1))).iCenterShiftY,
               -Game.getProvince(this.getProvince(this.shortestLine.get(0))).iCenterShiftX
                  + Game.getProvince(this.getProvince(this.shortestLine.get(this.shortestLine.size() - 1))).iCenterShiftX
            )
            * 180.0
            / Math.PI
      );
      this.lPoints.clear();
      this.drawMatrix4.clear();
      List<Float> lPointsAngle = new ArrayList<>();

      try {
         int fromProvinceID = this.lProvinces.get(this.shortestLine.get(0));
         int toProvinceID = this.lProvinces.get(this.shortestLine.get(1));
         int tX = Game.getProvince(fromProvinceID).iCenterShiftX;
         int tX2 = Game.getProvince(toProvinceID).iCenterShiftX;
         int extra10X = tX + (int)Math.abs((tX2 - tX) * 0.15F) * (tX > tX2 ? -1 : 1);
         int extra10X2 = tX2 + (int)Math.abs((tX2 - tX) * 0.15F) * (tX2 > tX ? -1 : 1);
         int tY = Game.getProvince(fromProvinceID).iCenterShiftY;
         int tY2 = Game.getProvince(toProvinceID).iCenterShiftY;
         int extra10Y = tY + (int)Math.abs((tY2 - tY) * 0.15F) * (tY > tY2 ? -1 : 1);
         int extra10Y2 = tY2 + (int)Math.abs((tY2 - tY) * 0.15F) * (tY2 > tY ? -1 : 1);
         int iPrecision = Math.max(3, Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength()) * 100;
         Vector2[] vPoints = new Vector2[iPrecision];
         Vector2[] dataSet = new Vector2[]{
            new Vector2(extra10X, extra10Y),
            new Vector2(extra10X, extra10Y),
            new Vector2(this.iAveragePointPosX, this.iAveragePointPosY),
            new Vector2(extra10X2, extra10Y2),
            new Vector2(extra10X2, extra10Y2)
         };
         CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

         for (int i = 0; i < iPrecision; i++) {
            vPoints[i] = new Vector2();
            oCatmull.valueAt(vPoints[i], i / (iPrecision - 1.0F));
         }

         float tempPrecissionWidth = 0.0F;

         for (int i = 0; i < iPrecision - 1; i++) {
            tempPrecissionWidth += getLineWidth2((int)vPoints[i].x, (int)vPoints[i].y, (int)vPoints[i + 1].x, (int)vPoints[i + 1].y);
         }

         this.lPoints.add(new Point_XY((int)vPoints[0].x, (int)vPoints[0].y));
         float acceptableWidth = 0.0F;

         try {
            acceptableWidth = tempPrecissionWidth / (Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength() - 1);
         } catch (ArithmeticException var33) {
            CFG.exceptionStack(var33);
         }

         float currentPointsWidth = 0.0F;
         int i = 1;

         for (int startPrecision = 0; i < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength(); i++) {
            while (startPrecision < iPrecision - 1) {
               float tempPrecisionWidth = getLineWidth2(
                  (int)vPoints[startPrecision].x, (int)vPoints[startPrecision].y, (int)vPoints[startPrecision + 1].x, (int)vPoints[startPrecision + 1].y
               );
               if (currentPointsWidth + tempPrecisionWidth >= acceptableWidth) {
                  this.lPoints.add(new Point_XY((int)vPoints[startPrecision].x, (int)vPoints[startPrecision].y));
                  currentPointsWidth = acceptableWidth - (currentPointsWidth + tempPrecisionWidth);
                  break;
               }

               currentPointsWidth += tempPrecisionWidth;
               startPrecision++;
            }
         }

         this.lPoints.add(new Point_XY((int)vPoints[vPoints.length - 1].x, (int)vPoints[vPoints.length - 1].y));

         try {
            for (int ix = 0; ix < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength(); ix++) {
               float tempPointsAngle = 0.0F;

               try {
                  if (ix < Game.getCiv(Game.getProvince(fromProvinceID).getCivID()).getCivNameLength() - 1) {
                     tempPointsAngle = getLinesAngle(
                        this.lPoints.get(ix).getPosX(), this.lPoints.get(ix).getPosY(), this.lPoints.get(ix + 1).getPosX(), this.lPoints.get(ix + 1).getPosY()
                     );
                  } else if (ix - 1 >= 0) {
                     tempPointsAngle = getLinesAngle(
                        this.lPoints.get(ix - 1).getPosX(), this.lPoints.get(ix - 1).getPosY(), this.lPoints.get(ix).getPosX(), this.lPoints.get(ix).getPosY()
                     );
                  }

                  lPointsAngle.add(tempPointsAngle);
               } catch (IndexOutOfBoundsException var35) {
                  if (ix == 0) {
                     try {
                        lPointsAngle.add(
                           getLinesAngle(
                              this.lPoints.get(ix).getPosX(),
                              this.lPoints.get(ix).getPosY(),
                              this.lPoints.get(ix + 1).getPosX(),
                              this.lPoints.get(ix + 1).getPosY()
                           )
                        );
                     } catch (IndexOutOfBoundsException var32) {
                        lPointsAngle.add(this.fAngle);
                     }
                  } else {
                     try {
                        lPointsAngle.add(
                           getLinesAngle(
                              this.lPoints.get(ix - 1).getPosX(),
                              this.lPoints.get(ix - 1).getPosY(),
                              this.lPoints.get(ix).getPosX(),
                              this.lPoints.get(ix).getPosY()
                           )
                        );
                     } catch (IndexOutOfBoundsException var31) {
                        lPointsAngle.add(this.fAngle);
                     }
                  }
               } catch (NullPointerException var36) {
                  if (ix == 0) {
                     try {
                        lPointsAngle.add(
                           getLinesAngle(
                              this.lPoints.get(ix).getPosX(),
                              this.lPoints.get(ix).getPosY(),
                              this.lPoints.get(ix + 1).getPosX(),
                              this.lPoints.get(ix + 1).getPosY()
                           )
                        );
                     } catch (IndexOutOfBoundsException var30) {
                        lPointsAngle.add(this.fAngle);
                     }
                  } else {
                     try {
                        lPointsAngle.add(
                           getLinesAngle(
                              this.lPoints.get(ix - 1).getPosX(),
                              this.lPoints.get(ix - 1).getPosY(),
                              this.lPoints.get(ix).getPosX(),
                              this.lPoints.get(ix).getPosY()
                           )
                        );
                     } catch (IndexOutOfBoundsException var29) {
                        lPointsAngle.add(this.fAngle);
                     }
                  }

                  try {
                     Game.getCiv(Game.getProvince(this.lProvinces.get(0)).getCivID()).setUpdateRegions(true);
                  } catch (IndexOutOfBoundsException var27) {
                  } catch (NullPointerException var28) {
                  }
               }
            }
         } catch (IndexOutOfBoundsException var37) {
            CFG.exceptionStack(var37);
         } catch (NullPointerException var38) {
            CFG.exceptionStack(var38);

            try {
               Game.getCiv(Game.getProvince(this.lProvinces.get(0)).getCivID()).setUpdateRegions(true);
            } catch (IndexOutOfBoundsException var25) {
            } catch (NullPointerException var26) {
            }
         } catch (IllegalStateException var39) {
            CFG.exceptionStack(var39);
         }

         float tempAngle = 0.0F;
         int ix = 0;

         for (int iSize = lPointsAngle.size(); ix < iSize; ix++) {
            tempAngle += lPointsAngle.get(ix);
         }

         float var53 = tempAngle / lPointsAngle.size();
         this.centerCharXY = new Point_XY(
            (int)(this.getCharMaxWidth() / 2 * (1.0F - (90.0F - Math.min(Math.abs(var53), 90.0F)) / 90.0F)),
            (int)(this.getCharMaxHeight() / 2 * ((90.0F - Math.min(Math.abs(var53), 90.0F)) / 90.0F))
         );
      } catch (Exception var40) {
         CFG.exceptionStack(var40);
      }

      int i = 0;

      for (int iSize = lPointsAngle.size(); i < iSize; i++) {
         this.drawMatrix4.add(new Matrix4().rotate(Renderer.textRotatedVector3, lPointsAngle.get(i)));
      }
   }

   protected final void updateDrawRegionName() {
      this.drawName = true;
   }

   public static float getLinesAngle(int fromPosX, int fromPosY, int toPosX, int toPosY) {
      return (float)(Math.atan2(fromPosY - toPosY, -fromPosX + toPosX) * 180.0 / Math.PI);
   }

   public static float getLinesAngle2(float fromPosX, float fromPosY, float toPosX, float toPosY) {
      return (float)(Math.atan2(fromPosY - toPosY, -fromPosX + toPosX) * 180.0 / Math.PI);
   }

   protected int getLineWidth(int fromCenterPosProvinceID, int toCenterPosProvinceID) {
      return getLineWidth(
         Game.getProvince(this.lProvinces.get(fromCenterPosProvinceID)).iCenterShiftX,
         Game.getProvince(this.lProvinces.get(fromCenterPosProvinceID)).iCenterShiftY,
         Game.getProvince(this.lProvinces.get(toCenterPosProvinceID)).iCenterShiftX,
         Game.getProvince(this.lProvinces.get(toCenterPosProvinceID)).iCenterShiftY
      );
   }

   public static int getLineWidth(int fromPosX, int fromPosY, int toPosX, int toPosY) {
      return (int)Math.sqrt(Math.pow(fromPosX - toPosX, 2.0) + Math.pow(fromPosY - toPosY, 2.0));
   }

   public static float getLineWidth2(int fromPosX, int fromPosY, int toPosX, int toPosY) {
      return (float)Math.sqrt(Math.pow(fromPosX - toPosX, 2.0) + Math.pow(fromPosY - toPosY, 2.0));
   }

   public static float getLineWidth3(float fromPosX, float fromPosY, float toPosX, float toPosY) {
      return (float)Math.sqrt(Math.pow(fromPosX - toPosX, 2.0) + Math.pow(fromPosY - toPosY, 2.0));
   }

   public final int getProvince(int i) {
      return this.lProvinces.get(i);
   }

   public final int getProvincesSize() {
      return this.iProvincesSize;
   }

   public final List<Integer> getShortestPath() {
      return this.shortestLine;
   }

   public final float getFontScale() {
      return this.fontScale;
   }

   public final float getAngle() {
      return this.fAngle;
   }

   public final int getCharMaxWidth() {
      return this.iCharMaxWidth;
   }

   public final int getCharMaxHeight() {
      return this.iCharMaxHeight;
   }
}
