package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class MoveUnits_RegroupLine {
   private List<Integer> lRoute = new ArrayList<>();
   public int iRouteSize = 0;
   public Color ColorLine = new Color(0.57254905F, 0.50980395F, 0.4509804F, 1.0F);
   public Color ColorLine2 = new Color(0.04F, 0.04F, 0.04F, 1.0F);
   private int iPrecision;
   private Vector2[] vPoints;
   public static final int PRECISION = 15;

   public MoveUnits_RegroupLine(List<Integer> nProvinces) {
      this.setPath(nProvinces);
      if (this.iRouteSize > 1) {
         this.buildMoveUnitsLine(true);
         this.ColorLine2 = CFG.getColorStep(Colors.HOVER_GOLD, Game.getCiv(Game.getProvince(this.lRoute.get(0)).getCivID()).getColor(1.0F), 50, 100, 0.4F);
      }
   }

   protected final void setPath(List<Integer> lPath) {
      for (int i = 0; i < lPath.size(); i++) {
         this.lRoute.add(lPath.get(i));
      }

      this.iRouteSize = this.lRoute.size();
   }

   public void draw(SpriteBatch oSB, float nScale) {
      try {
         if (this.iRouteSize > 0) {
            Array<Vector2> nPath = new Array<>();
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < this.iPrecision - 2; j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < this.iPrecision; j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.ColorLine2.r, this.ColorLine2.g, this.ColorLine2.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 3.0F, JoinType.SMOOTH, true);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void buildMoveUnitsLine(boolean updateAnimation) {
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
            Game.getProvince(this.lRoute.get(0)).getCity(0).getPosX() - 15 + Game.oR.nextInt(31),
            -(Game.getProvince(this.lRoute.get(0)).getCity(0).getPosY() - 15 + Game.oR.nextInt(31))
         );
      } else {
         dataSet[0] = new Vector2(
            Game.getProvince(this.lRoute.get(0)).iCenterShiftX - 15 + Game.oR.nextInt(31),
            -(Game.getProvince(this.lRoute.get(0)).iCenterShiftY - 15 + Game.oR.nextInt(31))
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
}
