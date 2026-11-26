package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import space.earlygrey.shapedrawer.JoinType;

public class MoveUnits_BiggestCities_Diplomacy extends MoveUnits_BiggestCities {
   public float widthPercentage = 1.0F;

   public MoveUnits_BiggestCities_Diplomacy(int nCivID, int iFromProvinceID, int iToProvinceID, Color nLineColor) {
      super(nCivID, iFromProvinceID, iToProvinceID);
      this.ColorLine = nLineColor;
      this.ColorLine2 = nLineColor;
   }

   @Override
   public void buildAnimation(boolean updateAnimation) {
      if (updateAnimation) {
         this.lMovingTime = CFG.currentTimeMillis;
         this.fMovingPercentage = 0.01F;
         this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
            @Override
            public void update() {
               MoveUnits_BiggestCities_Diplomacy.this.fMovingPercentage = MoveUnits_BiggestCities_Diplomacy.this.fMovingPercentage
                  + (float)(CFG.currentTimeMillis - MoveUnits_BiggestCities_Diplomacy.this.lMovingTime) / GameValues.inGame.DIPLOMACY_LINES_ANIMATION_DURATION;
               MoveUnits_BiggestCities_Diplomacy.this.lMovingTime = CFG.currentTimeMillis;
               if (MoveUnits_BiggestCities_Diplomacy.this.fMovingPercentage >= 1.0F) {
                  MoveUnits_BiggestCities_Diplomacy.this.fMovingPercentage = 1.0F;
                  MoveUnits_BiggestCities_Diplomacy.this.lMovingTime = CFG.currentTimeMillis;
                  MoveUnits_BiggestCities_Diplomacy.this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
                     @Override
                     public void update() {
                        MoveUnits_BiggestCities_Diplomacy.this.widthPercentage = MoveUnits_BiggestCities_Diplomacy.this.widthPercentage
                           - (float)(CFG.currentTimeMillis - MoveUnits_BiggestCities_Diplomacy.this.lMovingTime)
                              / GameValues.inGame.DIPLOMACY_LINES_ANIMATION_DURATION;
                        MoveUnits_BiggestCities_Diplomacy.this.lMovingTime = CFG.currentTimeMillis;
                     }
                  };
               }
            }
         };
      }
   }

   @Override
   public boolean draw2(SpriteBatch oSB, float nScale) {
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
               for (int j = 0; j < this.iPrecision; j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            if (this.fMovingPercentage < 0.99F) {
               Renderer.shapeDrawer.setColor(new Color(this.ColorLine.r, this.ColorLine.g, this.ColorLine.b, 0.55F));
            } else {
               Renderer.shapeDrawer.setColor(new Color(this.ColorLine2.r, this.ColorLine2.g, this.ColorLine2.b, 0.55F));
            }

            Renderer.shapeDrawer
               .path(
                  nPath,
                  2.5F
                     * (0.25F + 0.75F * this.fMovingPercentage)
                     * this.widthPercentage
                     * (Game.mapScale.getCurrentScale() < 1.0F ? Game.mapScale.getCurrentScale() : 1.0F),
                  JoinType.SMOOTH,
                  true
               );
            if (this.fMovingPercentage > 0.99F && Game.getProvince(this.lRoute.get(this.lRoute.size() - 1)).getDrawProvince()) {
               Renderer.shapeDrawer.setColor(new Color(this.ColorLine2.r, this.ColorLine2.g, this.ColorLine2.b, 0.45F * this.widthPercentage));
               Renderer.shapeDrawer
                  .filledCircle(
                     (this.vPoints[this.vPoints.length - 1].x + Game.getProvince(this.lRoute.get(this.lRoute.size() - 1)).getTranslateProvincePosX()) * nScale,
                     (this.vPoints[this.vPoints.length - 1].y - Game.mapCoords.getPosY()) * nScale,
                     12.0F * nScale * this.fMovingPercentage * this.widthPercentage
                  );
               Renderer.shapeDrawer.setColor(new Color(this.ColorLine2.r, this.ColorLine2.g, this.ColorLine2.b, 0.6F * this.widthPercentage));
               Renderer.shapeDrawer
                  .circle(
                     (this.vPoints[this.vPoints.length - 1].x + Game.getProvince(this.lRoute.get(this.lRoute.size() - 1)).getTranslateProvincePosX()) * nScale,
                     (this.vPoints[this.vPoints.length - 1].y - Game.mapCoords.getPosY()) * nScale,
                     16.0F * nScale * this.fMovingPercentage * this.widthPercentage,
                     2.0F * nScale
                  );
            }

            if (this.widthPercentage < 0.05F) {
               return true;
            }
         }

         return false;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
         return true;
      }
   }

   @Override
   public int getShiftPosXY() {
      return 0;
   }
}
