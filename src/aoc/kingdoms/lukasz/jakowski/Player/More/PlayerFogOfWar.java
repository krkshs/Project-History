package aoc.kingdoms.lukasz.jakowski.Player.More;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvincesBG;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class PlayerFogOfWar {
   public void initFogOfWar() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         Game.getProvince(i).setFogDrawArmy(false);
      }
   }

   public final void buildFogOfWar(int civID) {
      try {
         if (Game.FOG_OF_WAR) {
            for (int i = 0; i < Game.getCivsSize(); i++) {
               Game.getCiv(i).isPlayerAlly = false;
            }

            Game.getCiv(Game.player.iCivID).isPlayerAlly = true;

            for (int i = 0; i < Game.getProvincesSize(); i++) {
               Game.getProvince(i).setFogDrawArmy(false);
            }

            this.buildFogOfWar_CivID(civID);
            List<Integer> civAllies = DiplomacyManager.civAllies(civID);

            for (int i = civAllies.size() - 1; i >= 0; i--) {
               Game.getCiv(civAllies.get(i)).isPlayerAlly = true;
            }

            for (int i = civAllies.size() - 1; i >= 0; i--) {
               this.buildFogOfWar_CivID(civAllies.get(i));
            }
         } else {
            for (int i = 0; i < Game.getProvincesSize(); i++) {
               Game.getProvince(i).setFogDrawArmy(true);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      Game.updateDrawArmy();
   }

   public final void buildFogOfWar_CivID(int civID) {
      if (Game.FOG_OF_WAR) {
         for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
            Game.getProvince(Game.getCiv(civID).getProvinceID(i)).setFogDrawArmy(true);

            for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvincesSize(); j++) {
               Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringProvinces(j)).setFogDrawArmy(true);
            }

            for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringSeaProvincesSize(); j++) {
               Game.getProvince(Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getNeighboringSeaProvinces(j)).setFogDrawArmy(true);
            }
         }

         for (int i = 0; i < Game.getCiv(civID).iArmyPositionSize; i++) {
            Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).setFogDrawArmy(true);

            for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringProvincesSize(); j++) {
               Game.getProvince(Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringProvinces(j)).setFogDrawArmy(true);
            }

            for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringProvincesSize(); j++) {
               for (int k = 0;
                  k < Game.getProvince(Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringProvinces(j)).getNeighboringProvincesSize();
                  k++
               ) {
                  Game.getProvince(
                        Game.getProvince(Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringProvinces(j)).getNeighboringProvinces(k)
                     )
                     .setFogDrawArmy(true);
               }
            }

            for (int j = 0; j < Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringSeaProvincesSize(); j++) {
               Game.getProvince(Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getNeighboringSeaProvinces(j)).setFogDrawArmy(true);
            }
         }
      }
   }

   public final void updateFogOfWar_Civ(int civID) {
      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         this.updateFogOfWar_All(Game.getCiv(civID).getProvinceID(i));
      }

      for (int i = 0; i < Game.getCiv(civID).iArmyPositionSize; i++) {
         if (Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getCivID() != civID) {
            this.updateFogOfWar_All(Game.getCiv(civID).getArmyPosition(i));
         }
      }
   }

   public final void updateFogOfWar_All_ArmyOneProvinceView(int provinceID) {
      if (Game.FOG_OF_WAR) {
         if (Game.getCiv(Game.getProvince(provinceID).getCivID()).isPlayerAlly) {
            this.setFogOfWar(provinceID, true);
            return;
         }

         this.updateFogOfWar(provinceID);

         for (int i = 0; i < Game.getProvince(provinceID).getNeighboringProvincesSize(); i++) {
            this.updateFogOfWar(Game.getProvince(provinceID).getNeighboringProvinces(i));
         }

         for (int i = 0; i < Game.getProvince(provinceID).getNeighboringSeaProvincesSize(); i++) {
            this.updateFogOfWar(Game.getProvince(provinceID).getNeighboringSeaProvinces(i));
         }
      }
   }

   public final void updateFogOfWar_All(int provinceID) {
      List<Integer> toUpdate = new ArrayList<>();
      toUpdate.add(provinceID);

      for (int i = 0; i < Game.getProvince(provinceID).getNeighboringProvincesSize(); i++) {
         if (!toUpdate.contains(Game.getProvince(provinceID).getNeighboringProvinces(i))) {
            toUpdate.add(Game.getProvince(provinceID).getNeighboringProvinces(i));
         }
      }

      for (int ix = 0; ix < Game.getProvince(provinceID).getNeighboringProvincesSize(); ix++) {
         for (int j = 0; j < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ix)).getNeighboringProvincesSize(); j++) {
            if (!toUpdate.contains(Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ix)).getNeighboringProvinces(j))) {
               toUpdate.add(Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ix)).getNeighboringProvinces(j));
            }
         }
      }

      for (int ix = 0; ix < Game.getProvince(provinceID).getNeighboringSeaProvincesSize(); ix++) {
         if (!toUpdate.contains(Game.getProvince(provinceID).getNeighboringSeaProvinces(ix))) {
            toUpdate.add(Game.getProvince(provinceID).getNeighboringSeaProvinces(ix));
         }
      }

      for (int ixx = toUpdate.size() - 1; ixx >= 0; ixx--) {
         this.updateFogOfWar(toUpdate.get(ixx));
      }

      toUpdate.clear();
   }

   public final void updateFogOfWar(int provinceID) {
      try {
         if (Game.FOG_OF_WAR) {
            Province province = Game.getProvince(provinceID);
            if (Game.getCiv(province.getCivID()).isPlayerAlly) {
               this.setFogOfWar(provinceID, true);
               return;
            }

            for (int i = 0; i < province.getArmySize(); i++) {
               if (Game.getCiv(province.getArmy(i).civID).isPlayerAlly) {
                  this.setFogOfWar(provinceID, true);
                  return;
               }
            }

            for (int ix = 0; ix < province.getNeighboringProvincesSize(); ix++) {
               if (Game.getCiv(Game.getProvince(province.getNeighboringProvinces(ix)).getCivID()).isPlayerAlly) {
                  this.setFogOfWar(provinceID, true);
                  return;
               }
            }

            for (int ixx = 0; ixx < province.getNeighboringProvincesSize(); ixx++) {
               for (int j = 0; j < Game.getProvince(province.getNeighboringProvinces(ixx)).getArmySize(); j++) {
                  if (Game.getCiv(Game.getProvince(province.getNeighboringProvinces(ixx)).getArmy(j).civID).isPlayerAlly) {
                     this.setFogOfWar(provinceID, true);
                     return;
                  }
               }
            }

            for (int ixx = 0; ixx < province.getNeighboringProvincesSize(); ixx++) {
               for (int jx = 0; jx < Game.getProvince(province.getNeighboringProvinces(ixx)).getArmySize(); jx++) {
                  if (Game.getCiv(Game.getProvince(province.getNeighboringProvinces(ixx)).getArmy(jx).civID).isPlayerAlly) {
                     this.setFogOfWar(provinceID, true);
                     return;
                  }
               }
            }

            for (int ixx = 0; ixx < province.getNeighboringProvincesSize(); ixx++) {
               for (int k = 0; k < Game.getProvince(province.getNeighboringProvinces(ixx)).getNeighboringProvincesSize(); k++) {
                  for (int jxx = 0;
                     jxx < Game.getProvince(Game.getProvince(province.getNeighboringProvinces(ixx)).getNeighboringProvinces(k)).getArmySize();
                     jxx++
                  ) {
                     if (Game.getCiv(Game.getProvince(Game.getProvince(province.getNeighboringProvinces(ixx)).getNeighboringProvinces(k)).getArmy(jxx).civID).isPlayerAlly
                        )
                      {
                        this.setFogOfWar(provinceID, true);
                        return;
                     }
                  }
               }
            }

            for (int ixx = 0; ixx < province.getNeighboringSeaProvincesSize(); ixx++) {
               for (int jxxx = 0; jxxx < Game.getProvince(province.getNeighboringSeaProvinces(ixx)).getArmySize(); jxxx++) {
                  if (Game.getCiv(Game.getProvince(province.getNeighboringSeaProvinces(ixx)).getArmy(jxxx).civID).isPlayerAlly) {
                     this.setFogOfWar(provinceID, true);
                     return;
                  }
               }
            }

            this.setFogOfWar(provinceID, false);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public final void setFogOfWar(int provinceID, boolean state) {
      if (Game.getProvince(provinceID).getFogDrawArmy() != state) {
         Game.getProvince(provinceID).setFogDrawArmy(state);
         Game.getProvince(provinceID).updateDrawArmy();
         if (Game.getProvince(provinceID).getDrawProvince()) {
            FBOProvincesBG.redrawnProvinces();
         }
      }
   }

   public final void setFogOfWar_ExtraCheck(int i, boolean isVisible) {
      try {
         Game.getProvince(i).setFogDrawArmy(isVisible || Game.getProvince(i).haveArmy(Game.player.iCivID));
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
