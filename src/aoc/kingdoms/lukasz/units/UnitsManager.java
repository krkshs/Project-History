package aoc.kingdoms.lukasz.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class UnitsManager {
   public UnitType templar;
   public UnitType unitSheep;
   private List<UnitsManager.MoveUnitInDirection> moveUnit = new ArrayList<>();

   public UnitsManager() {
      this.initMoveUnit();
      this.loadUnits();
   }

   public final void initTestUnits() {
   }

   public final void loadUnits() {
      this.templar = new UnitType_Templar();
      this.unitSheep = new UnitType_Sheep();
   }

   public final void tempUpdateUnits() {
   }

   public final void tempDrawUnits(SpriteBatch oSB) {
   }

   public final void initMoveUnit() {
   }

   public interface MoveUnitInDirection {
      void moveUnit(int var1);
   }
}
