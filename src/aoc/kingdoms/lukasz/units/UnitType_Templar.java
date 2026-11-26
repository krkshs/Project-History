package aoc.kingdoms.lukasz.units;

public class UnitType_Templar extends UnitType {
   @Override
   public void initUnit() {
      this.animations_Idle.add(new Animation_Unit("gfx/units/Templar/idle.png", 42, 42, new Hitbox(7, 3, 28, 36), 64));
      this.animations_Idle.add(new Animation_Unit("gfx/units/Templar/idle1.png", 42, 42, new Hitbox(7, 3, 28, 36), 64));
      this.animations_Idle.add(new Animation_Unit("gfx/units/Templar/idle2.png", 42, 42, new Hitbox(7, 3, 28, 36), 64));
      this.animations_Walk.add(new Animation_Unit("gfx/units/Templar/walk.png", 44, 44, new Hitbox(8, 4, 28, 36), 48));
      this.unitSpeed = 1.14F;
      this.maxHP = 100;
   }
}
