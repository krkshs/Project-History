package aoc.kingdoms.lukasz.jakowski.AI;

public class AI_Civ {
   public float army_First = 1.1F;
   public float army_Second = 0.9F;
   public float army_Third = 0.75F;
   public float army_Fourth = 0.75F;
   public float regimentsLimit_UseMax_Perc = 1.25F;
   public float armies_FirstLine_Perc = 0.8F;
   public float armies_FlankLine_Perc = 0.2F;
   public int armies_SiegeWeaponPerSupportUnits = 10;
   public int armies_SiegeWeapon_Max = 1;

   public AI_Civ() {
      this.buildPersonality();
   }

   public final void buildPersonality() {
   }
}
