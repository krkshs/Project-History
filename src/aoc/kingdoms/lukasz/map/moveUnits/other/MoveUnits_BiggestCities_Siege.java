package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.Game;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class MoveUnits_BiggestCities_Siege extends MoveUnits_BiggestCities {
   public MoveUnits_BiggestCities_Siege(int nCivID, int iFromProvinceID, int iToProvinceID) {
      super(nCivID, iFromProvinceID, iToProvinceID);
   }

   @Override
   public void buildMoveUnitsLine(boolean updateAnimation) {
      this.buildAnimation(updateAnimation);
      this.iPrecision = 15 * this.iRouteSize;
      this.vPoints = new Vector2[this.iPrecision];
      Vector2[] dataSet = new Vector2[this.iRouteSize + 2];

      for (int i = 0; i < this.iRouteSize; i++) {
         dataSet[i + 1] = new Vector2(Game.getProvince(this.lRoute.get(i)).iCenterShiftX, -Game.getProvince(this.lRoute.get(i)).iCenterShiftY);
      }

      dataSet[0] = new Vector2(
         Game.getProvince(this.lRoute.get(0)).iCenterShiftX + this.getShiftPosXY(),
         -(Game.getProvince(this.lRoute.get(0)).iCenterShiftY + this.getShiftPosXY())
      );
      dataSet[this.iRouteSize + 1] = new Vector2(
         Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftX, -Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftY
      );
      CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

      for (int j = 0; j < this.iPrecision; j++) {
         this.vPoints[j] = new Vector2();
         oCatmull.valueAt(this.vPoints[j], j / (this.iPrecision - 1.0F));
      }
   }
}
