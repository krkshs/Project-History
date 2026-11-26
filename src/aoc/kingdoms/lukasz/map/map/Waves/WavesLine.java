package aoc.kingdoms.lukasz.map.map.Waves;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class WavesLine {
   public List<Point_XY> points = new ArrayList<>();
   public int pointsSize = 0;
   public Vector2[] vPoints;
   public List<Integer> width = new ArrayList<>();
   public final float ANIMATION_TIME = 5000.0F;
   public long time = 0L;
   public boolean direction = false;

   public WavesLine() {
      this.time = CFG.currentTimeMillis;
   }

   public void addNewPoint() {
      this.points
         .add(
            new Point_XY(
               (int)(Game.mapCoords.getPosX() - Touch.getMousePosX() / Game.mapScale.getCurrentScale()) * -1,
               (int)(Game.mapCoords.getPosY() - Touch.getMousePosY() / Game.mapScale.getCurrentScale()) * -1
            )
         );
      this.pointsSize = this.points.size();
      this.buildData();
   }

   public void removePoint() {
      if (this.pointsSize > 0) {
         this.points.remove(this.pointsSize - 1);
         this.pointsSize = this.points.size();
      }

      this.buildData();
   }

   public void addNewPoint_Just(int nX, int nY) {
      this.points.add(new Point_XY(nX, nY));
      this.pointsSize = this.points.size();
   }

   public final void buildData() {
      if (this.pointsSize > 2) {
         this.width.clear();
         this.vPoints = new Vector2[GameValues.ships.SHIP_LINE_PRECISION];
         Vector2[] dataSet = new Vector2[this.pointsSize + 2];
         dataSet[0] = new Vector2(this.points.get(0).getPosX(), this.points.get(0).getPosY());

         for (int i = 0; i < this.pointsSize; i++) {
            dataSet[i + 1] = new Vector2(this.points.get(i).getPosX(), this.points.get(i).getPosY());
         }

         dataSet[this.pointsSize + 1] = new Vector2(this.points.get(this.pointsSize - 1).getPosX(), this.points.get(this.pointsSize - 1).getPosY());
         CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

         for (int j = 0; j < GameValues.ships.SHIP_LINE_PRECISION; j++) {
            this.vPoints[j] = new Vector2();
            oCatmull.valueAt(this.vPoints[j], j / (GameValues.ships.SHIP_LINE_PRECISION - 1.0F));
         }

         for (int j = 0; j < GameValues.ships.SHIP_LINE_PRECISION - 1; j++) {
            this.width
               .add(
                  (int)Math.ceil(
                     Math.sqrt(
                        (this.vPoints[j + 1].x - this.vPoints[j].x) * (this.vPoints[j + 1].x - this.vPoints[j].x)
                           + (this.vPoints[j].y - this.vPoints[j + 1].y) * (this.vPoints[j].y - this.vPoints[j + 1].y)
                     )
                  )
               );
         }

         this.width.add(1);
      }
   }

   public void draw(SpriteBatch oSB) {
      try {
         if (this.pointsSize > 2) {
            float perc = (float)(CFG.currentTimeMillis - this.time) / 5000.0F;
            if (perc >= 1.0F) {
               this.time = CFG.currentTimeMillis;
               this.direction = !this.direction;
               if (this.direction) {
                  perc = 1.0F;
               } else {
                  perc = 1.0F;
               }
            }

            if (!this.direction) {
               perc = 1.0F - perc;
            }

            Array<Vector2> nPath = new Array<>();

            for (int j = 0; j < GameValues.ships.SHIP_LINE_PRECISION; j++) {
               nPath.add(
                  new Vector2(
                     (this.vPoints[j].x + Game.mapCoords.getPosX()) * Game.mapScale.getCurrentScale(),
                     (-this.vPoints[j].y - Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale()
                  )
               );
            }

            Renderer.shapeDrawer.setColor(new Color(1.0F, 1.0F, 1.0F, 0.025F + 0.125F * perc));
            Renderer.shapeDrawer.path(nPath, 0.1F + 0.85F * perc, JoinType.SMOOTH, true);
            Renderer.oSBBorder.end();
            Renderer.oSBBorder.begin();
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }
}
