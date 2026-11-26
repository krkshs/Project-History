package aoc.kingdoms.lukasz.map.moveUnits;

import aoc.kingdoms.lukasz.jakowski.Game;

public class MoveUnits_Player extends MoveUnits {
   public MoveUnits_Player(int nCivID, int iFromProvinceID, int iToProvinceID, String key, int extraArmyY, boolean inRetreat, boolean landOnly) {
      super(nCivID, iFromProvinceID, iToProvinceID, key, extraArmyY, inRetreat, landOnly);
   }

   public MoveUnits_Player(int nCivID, int iFromProvinceID, int iToProvinceID, String key, int extraArmyY, int iFromProvinceIDExtra) {
      super(nCivID, iFromProvinceID, iToProvinceID, key, extraArmyY, iFromProvinceIDExtra);
   }

   @Override
   public boolean getWas(int nProvinceID) {
      return Game.getProvince(nProvinceID).wasPlayer;
   }

   @Override
   public void setWas(int nProvinceID, boolean nWas) {
      Game.getProvince(nProvinceID).wasPlayer = nWas;
   }
}
