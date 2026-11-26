package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class MoveUnits_BiggestCities {
   public List<Integer> lRoute = new ArrayList<>();
   public int iRouteSize = 0;
   public long lMovingTime = 0L;
   public float fMovingPercentage = 0.0F;
   MoveUnits.LittleAnimation littleAnimationMainLine;
   public Color ColorLine = new Color(0.57254905F, 0.50980395F, 0.4509804F, 1.0F);
   public Color ColorLine2 = new Color(0.04F, 0.04F, 0.04F, 1.0F);
   public int iPrecision;
   public Vector2[] vPoints;
   public static final int PRECISION = 15;

   public MoveUnits_BiggestCities(int nCivID, int iFromProvinceID, int iToProvinceID) {
      this.buildRoute(nCivID, iFromProvinceID, iToProvinceID);
      if (this.iRouteSize > 1) {
         this.buildMoveUnitsLine(true);
         this.ColorLine2 = CFG.getColorStep(Colors.HOVER_GOLD, Game.getCiv(Game.getProvince(this.lRoute.get(0)).getCivID()).getColor(1.0F), 50, 100, 0.4F);
      }
   }

   public void update() {
      this.littleAnimationMainLine.update();
   }

   public void draw(SpriteBatch oSB, float nScale) {
      try {
         if (this.iRouteSize > 0) {
            Array<Vector2> nPath = new Array<>();
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            if (this.fMovingPercentage < 0.99F) {
               Renderer.shapeDrawer.setColor(CFG.getColorStep(Colors.HOVER_GOLD, this.ColorLine2, (int)(this.fMovingPercentage * 100.0F), 100, 0.4F));
            } else {
               Renderer.shapeDrawer.setColor(new Color(this.ColorLine2.r, this.ColorLine2.g, this.ColorLine2.b, 0.4F));
            }

            Renderer.shapeDrawer.path(nPath, 3.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public boolean draw2(SpriteBatch oSB, float nScale) {
      return false;
   }

   protected boolean buildRoute(int nCivID, int fromProvinceID, int toProvinceID) {
      this.lRoute.clear();
      if (fromProvinceID >= 0 && toProvinceID >= 0 && Game.getProvince(toProvinceID).getWasteland() < 0) {
         List<Integer> was = new ArrayList<>();
         was.add(fromProvinceID);

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).wasCities = false;
         }

         Game.getProvince(fromProvinceID).wasCities = true;
         List<Integer> in = new ArrayList<>();
         List<List<Integer>> inPath = new ArrayList<>();

         for (int i = 0; i < Game.getProvince(fromProvinceID).getNeighboringProvincesSize(); i++) {
            if (this.canBeUsedInPath(
               nCivID, Game.getProvince(fromProvinceID).getNeighboringProvinces(i), this.isFriendlyProvince(nCivID, toProvinceID), toProvinceID
            )) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID()).wasCities = true;
            }
         }

         if (!Game.getProvince(fromProvinceID).getSeaProvince()) {
            for (int ix = 0; ix < Game.getProvince(fromProvinceID).getNeighboringSeaProvincesSize(); ix++) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID()).wasCities = true;
            }
         }

         this.buildPath(nCivID, was, in, inPath, fromProvinceID, toProvinceID, true);
         return true;
      } else {
         return false;
      }
   }

   public boolean isFriendlyProvince(int nCivID, int toProvinceID) {
      return Game.getProvince(toProvinceID).getCivID() == nCivID;
   }

   public boolean canBeUsedInPath(int nCivID, int nProvinceID, boolean moveToFriendlyProvince, int toProvinceID) {
      return Game.getProvince(nProvinceID).getWasteland() >= 0 ? false : Game.getProvince(nProvinceID).getCivID() == nCivID;
   }

   protected boolean buildPath(int nCivID, List<Integer> was, List<Integer> in, List<List<Integer>> inPath, int from, int lookingFor, boolean forDirection) {
      List<Integer> nIN = new ArrayList<>();
      List<List<Integer>> nINPath = new ArrayList<>();

      for (int i = 0; i < in.size(); i++) {
         if (Game.getProvince(in.get(i)).getProvinceID() == lookingFor) {
            this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
            this.clearWas(was);
            return true;
         }
      }

      if (forDirection) {
         for (int ix = 0; ix < in.size(); ix++) {
            for (int j = 0; j < Game.getProvince(in.get(ix)).getNeighboringProvincesSize(); j++) {
               if (this.canBeUsedInPath(
                     nCivID,
                     Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID(),
                     this.isFriendlyProvince(nCivID, lookingFor),
                     lookingFor
                  )
                  && !Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).wasCities) {
                  if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>();

                  for (int u = 0; u < inPath.get(ix).size(); u++) {
                     tPL.add(inPath.get(ix).get(u));
                  }

                  tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
                  nINPath.add(tPL);
                  Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).wasCities = true;
                  was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
               }
            }

            if (!Game.getProvince(in.get(ix)).getSeaProvince()) {
               for (int jx = 0; jx < Game.getProvince(in.get(ix)).getNeighboringSeaProvincesSize(); jx++) {
                  if (!Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).wasCities) {
                     if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID() == lookingFor) {
                        this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                        this.clearWas(was);
                        return true;
                     }

                     nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                     List<Integer> tPL = new ArrayList<>();

                     for (int u = 0; u < inPath.get(ix).size(); u++) {
                        tPL.add(inPath.get(ix).get(u));
                     }

                     tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                     nINPath.add(tPL);
                     Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).wasCities = true;
                     was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                  }
               }
            }
         }
      } else {
         for (int ix = 0; ix < in.size(); ix++) {
            for (int jxx = Game.getProvince(in.get(ix)).getNeighboringProvincesSize() - 1; jxx >= 0; jxx--) {
               if (this.canBeUsedInPath(
                     nCivID,
                     Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID(),
                     this.isFriendlyProvince(nCivID, lookingFor),
                     lookingFor
                  )
                  && !Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID()).wasCities) {
                  if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>();

                  for (int u = 0; u < inPath.get(ix).size(); u++) {
                     tPL.add(inPath.get(ix).get(u));
                  }

                  tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
                  nINPath.add(tPL);
                  Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID()).wasCities = true;
                  was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
               }
            }

            if (!Game.getProvince(in.get(ix)).getSeaProvince()) {
               for (int jxxx = Game.getProvince(in.get(ix)).getNeighboringSeaProvincesSize() - 1; jxxx >= 0; jxxx--) {
                  if (!Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID()).wasCities) {
                     if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID() == lookingFor) {
                        this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                        this.clearWas(was);
                        return true;
                     }

                     nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     List<Integer> tPL = new ArrayList<>();

                     for (int u = 0; u < inPath.get(ix).size(); u++) {
                        tPL.add(inPath.get(ix).get(u));
                     }

                     tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     nINPath.add(tPL);
                     Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID()).wasCities = true;
                     was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                  }
               }
            }
         }
      }

      if (nIN.isEmpty()) {
         return false;
      } else {
         try {
            return this.buildPath(nCivID, was, nIN, nINPath, from, lookingFor, !forDirection);
         } catch (StackOverflowError var14) {
            this.clearWas(was);
            return false;
         }
      }
   }

   protected final void clearWas(List<Integer> was) {
      for (int i = was.size() - 1; i >= 0; i--) {
         Game.getProvince(was.get(i)).wasCities = false;
      }
   }

   protected final void setPath(int p1, int p2, List<Integer> lPath, int toProvinceID, int fromProvinceID) {
      this.lRoute.add(fromProvinceID);

      for (int i = 0; i < lPath.size(); i++) {
         this.lRoute.add(lPath.get(i));
      }

      if (toProvinceID != this.lRoute.get(this.lRoute.size() - 1)) {
         this.lRoute.add(toProvinceID);
      }

      this.iRouteSize = this.lRoute.size();
   }

   public void buildAnimation(boolean updateAnimation) {
      if (updateAnimation) {
         this.lMovingTime = CFG.currentTimeMillis;
         this.fMovingPercentage = 0.01F;
         this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
            @Override
            public void update() {
               MoveUnits_BiggestCities.this.fMovingPercentage = MoveUnits_BiggestCities.this.fMovingPercentage
                  + (float)(CFG.currentTimeMillis - MoveUnits_BiggestCities.this.lMovingTime) / GameValues.inGame.DIPLOMACY_LINES_ANIMATION_DURATION;
               MoveUnits_BiggestCities.this.lMovingTime = CFG.currentTimeMillis;
               if (MoveUnits_BiggestCities.this.fMovingPercentage >= 1.0F) {
                  MoveUnits_BiggestCities.this.fMovingPercentage = 1.0F;
                  MoveUnits_BiggestCities.this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
                     @Override
                     public void update() {
                     }
                  };
               }
            }
         };
      }
   }

   public int getShiftPosXY() {
      return -15 + Game.oR.nextInt(31);
   }

   public void buildMoveUnitsLine(boolean updateAnimation) {
      this.buildAnimation(updateAnimation);
      this.iPrecision = 15 * this.iRouteSize;
      this.vPoints = new Vector2[this.iPrecision];
      Vector2[] dataSet = new Vector2[this.iRouteSize + 2];

      for (int i = 0; i < this.iRouteSize; i++) {
         if (Game.getProvince(this.lRoute.get(i)).getCitiesSize() > 0) {
            dataSet[i + 1] = new Vector2(Game.getProvince(this.lRoute.get(i)).getCity(0).getPosX(), -Game.getProvince(this.lRoute.get(i)).getCity(0).getPosY());
         } else {
            dataSet[i + 1] = new Vector2(Game.getProvince(this.lRoute.get(i)).iCenterShiftX, -Game.getProvince(this.lRoute.get(i)).iCenterShiftY);
         }
      }

      if (Game.getProvince(this.lRoute.get(0)).getCitiesSize() > 0) {
         dataSet[0] = new Vector2(
            Game.getProvince(this.lRoute.get(0)).getCity(0).getPosX() + this.getShiftPosXY(),
            -(Game.getProvince(this.lRoute.get(0)).getCity(0).getPosY() + this.getShiftPosXY())
         );
      } else {
         dataSet[0] = new Vector2(
            Game.getProvince(this.lRoute.get(0)).iCenterShiftX + this.getShiftPosXY(),
            -(Game.getProvince(this.lRoute.get(0)).iCenterShiftY + this.getShiftPosXY())
         );
      }

      if (Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).getCitiesSize() > 0) {
         dataSet[this.iRouteSize + 1] = new Vector2(
            Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).getCity(0).getPosX(),
            -Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).getCity(0).getPosY()
         );
      } else {
         dataSet[this.iRouteSize + 1] = new Vector2(
            Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftX, -Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftY
         );
      }

      CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

      for (int j = 0; j < this.iPrecision; j++) {
         this.vPoints[j] = new Vector2();
         oCatmull.valueAt(this.vPoints[j], j / (this.iPrecision - 1.0F));
      }
   }

   public int getFromProvinceID() {
      return this.lRoute.get(0);
   }

   public int getToProvinceID() {
      return this.lRoute.get(1);
   }

   public int getToProvinceLastID() {
      return this.lRoute.get(this.iRouteSize - 1);
   }

   interface LittleAnimation {
      void update();
   }
}
