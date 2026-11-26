package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.setting.SettingsProvince;
import java.util.ArrayList;
import java.util.List;

public class ProvinceBorderManager {
   public static List<ProvinceBorderManager.PBData> lProvinces = new ArrayList<>();
   public static List<ProvinceBorderManager.PBData> lProvincesLandBySea = new ArrayList<>();
   public static List<ProvinceBorderManager.PBData> lProvincesSeaBySea = new ArrayList<>();
   public static long lTimeLine;
   public static int iLineOffset = 0;
   public static int iLineOffsetInterval = 75;
   public static boolean drawInnerBorder = true;
   public static boolean drawBorder = true;
   public static long WAR_COLOR_TIME = 0L;
   public static int WAR_COLOR_STEP = 1;
   public static boolean warColorMovingBack = false;
   public static ProvinceBorderManager.Action action = new ProvinceBorderManager.Action() {
      @Override
      public void setProvinceID(int nProvinceID) {
      }

      @Override
      public void resetProvinceID() {
      }

      @Override
      public void update() {
      }
   };

   public static final void update() {
      updateWarColor();
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS && !drawInnerBorder) {
         updateProvinceBorder();
         drawInnerBorder = true;
      } else if (Game.mapScale.getCurrentScale() < Game.DRAW_INNER_BORDERS && drawInnerBorder) {
         updateProvinceBorder();
         drawInnerBorder = false;
      } else if (Game.mapScale.getCurrentScale() > SettingsProvince.value.DRAW_BORDERS && !drawBorder) {
         updateProvinceBorder();
         drawBorder = true;
      } else if (Game.mapScale.getCurrentScale() <= SettingsProvince.value.DRAW_BORDERS && drawBorder) {
         updateProvinceBorder();
         drawBorder = false;
      }
   }

   public static final void updateWarColor() {
      if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
         if (warColorMovingBack) {
            WAR_COLOR_STEP = (int)(WAR_COLOR_STEP + Math.min(100L, CFG.currentTimeMillis - WAR_COLOR_TIME));
            if (WAR_COLOR_STEP < 0) {
               CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE = CFG.COLOR_PROVINCE_STRAIGHT_WAR;
               WAR_COLOR_TIME = CFG.currentTimeMillis;
            } else {
               CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE = CFG.getColorStep(
                  CFG.COLOR_PROVINCE_STRAIGHT_WAR,
                  CFG.COLOR_PROVINCE_STRAIGHT_WAR2,
                  WAR_COLOR_STEP,
                  GameValues.provinceBorderWar.WAR_COLOR_TIME_ANIMATION,
                  1.0F
               );
               WAR_COLOR_TIME = CFG.currentTimeMillis;
               if (WAR_COLOR_STEP >= GameValues.provinceBorderWar.WAR_COLOR_TIME_ANIMATION) {
                  WAR_COLOR_STEP = -GameValues.provinceBorderWar.WAR_COLOR_TIME_PAUSE2;
                  warColorMovingBack = false;
               }
            }
         } else {
            WAR_COLOR_STEP = (int)(WAR_COLOR_STEP + Math.min(100L, CFG.currentTimeMillis - WAR_COLOR_TIME));
            if (WAR_COLOR_STEP < 0) {
               CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE = CFG.COLOR_PROVINCE_STRAIGHT_WAR2;
               WAR_COLOR_TIME = CFG.currentTimeMillis;
            } else {
               CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE = CFG.getColorStep(
                  CFG.COLOR_PROVINCE_STRAIGHT_WAR2,
                  CFG.COLOR_PROVINCE_STRAIGHT_WAR,
                  WAR_COLOR_STEP,
                  GameValues.provinceBorderWar.WAR_COLOR_TIME_ANIMATION,
                  1.0F
               );
               WAR_COLOR_TIME = CFG.currentTimeMillis;
               if (WAR_COLOR_STEP >= GameValues.provinceBorderWar.WAR_COLOR_TIME_ANIMATION) {
                  WAR_COLOR_STEP = -GameValues.provinceBorderWar.WAR_COLOR_TIME_PAUSE;
                  warColorMovingBack = true;
               }
            }
         }
      }
   }

   public static final void updateProvinceBorder() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         for (int j = 0; j < Game.getProvince(i).getNeighboringProvincesSize(); j++) {
            if (i < Game.getProvince(i).getNeighboringProvinces(j)) {
               if (Game.getProvince(i).getSeaProvince() && Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
                  Game.getProvince(i).getProvinceBordersSeaBySea(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
               } else if (!Game.getProvince(i).getSeaProvince() && !Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
                  Game.getProvince(i).getProvinceBordersLandByLand(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
               } else {
                  Game.getProvince(i).getProvinceBordersLandBySea(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
               }
            } else if (Game.getProvince(i).getSeaProvince() && Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
               Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
                  .getProvinceBordersSeaBySea(j)
                  .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
            } else if (!Game.getProvince(i).getSeaProvince() && !Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
               Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
                  .getProvinceBordersLandByLand(j)
                  .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
            } else {
               Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
                  .getProvinceBordersLandBySea(j)
                  .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
            }
         }
      }
   }

   public static final void updateProvinceBorder(int i) {
      for (int j = 0; j < Game.getProvince(i).getNeighboringProvincesSize(); j++) {
         if (i < Game.getProvince(i).getNeighboringProvinces(j)) {
            if (Game.getProvince(i).getSeaProvince() && Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
               Game.getProvince(i).getProvinceBordersSeaBySea(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
            } else if (!Game.getProvince(i).getSeaProvince() && !Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
               Game.getProvince(i).getProvinceBordersLandByLand(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
            } else {
               Game.getProvince(i).getProvinceBordersLandBySea(Game.getProvince(i).getNeighboringProvinces(j)).updateDrawProvinceBorder(i);
            }
         } else if (Game.getProvince(i).getSeaProvince() && Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
            Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
               .getProvinceBordersSeaBySea(j)
               .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
         } else if (!Game.getProvince(i).getSeaProvince() && !Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j)).getSeaProvince()) {
            Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
               .getProvinceBordersLandByLand(j)
               .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
         } else {
            Game.getProvince(Game.getProvince(i).getNeighboringProvinces(j))
               .getProvinceBordersLandBySea(j)
               .updateDrawProvinceBorder(Game.getProvince(i).getNeighboringProvinces(j));
         }
      }
   }

   public static final void clearProvinceBorder() {
      int i = 0;

      for (int iSize = lProvinces.size(); i < iSize; i++) {
         Game.getProvince(lProvinces.get(i).iProvinceID).getProvinceBordersLandByLand(lProvinces.get(i).iWithProvinceID).isLocked = false;
         Game.getProvince(lProvinces.get(i).iProvinceID)
            .getProvinceBordersLandByLand(lProvinces.get(i).iWithProvinceID)
            .updateDrawProvinceBorder(lProvinces.get(i).iProvinceID);
      }

      lProvinces.clear();
      i = 0;

      for (int iSize = lProvincesLandBySea.size(); i < iSize; i++) {
         Game.getProvince(lProvincesLandBySea.get(i).iProvinceID).getProvinceBordersLandBySea(lProvincesLandBySea.get(i).iWithProvinceID).isLocked = false;
         Game.getProvince(lProvincesLandBySea.get(i).iProvinceID)
            .getProvinceBordersLandBySea(lProvincesLandBySea.get(i).iWithProvinceID)
            .updateDrawProvinceBorder(lProvincesLandBySea.get(i).iProvinceID);
      }

      lProvincesLandBySea.clear();
      i = 0;

      for (int iSize = lProvincesSeaBySea.size(); i < iSize; i++) {
         Game.getProvince(lProvincesSeaBySea.get(i).iProvinceID).getProvinceBordersSeaBySea(lProvincesSeaBySea.get(i).iWithProvinceID).isLocked = false;
         Game.getProvince(lProvincesSeaBySea.get(i).iProvinceID)
            .getProvinceBordersSeaBySea(lProvincesSeaBySea.get(i).iWithProvinceID)
            .updateDrawProvinceBorder(lProvincesSeaBySea.get(i).iProvinceID);
      }

      lProvincesSeaBySea.clear();
   }

   public static final void updateAction() {
      if (Game.menuManager.getInGame()) {
         action = new ProvinceBorderManager.Action() {
            @Override
            public void setProvinceID(int nProvinceID) {
               try {
                  if (nProvinceID >= 0 && !Game.menuManager.getVisibleInGame_Civ() && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_PEACE_VIEW) {
                     ProvinceBorderManager.clearProvinceBorder();

                     for (int i = 0; i < Game.getProvince(nProvinceID).getNeighboringProvincesSize(); i++) {
                        if (Game.getProvince(nProvinceID).getSeaProvince()
                           && Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(i)).getSeaProvince()) {
                           if (nProvinceID < Game.getProvince(nProvinceID).getNeighboringProvinces(i)) {
                              Game.getProvince(nProvinceID)
                                 .getProvinceBordersSeaBySea(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                                 .updateDrawProvinceBorder_ActiveProvince();
                              ProvinceBorderManager.lProvincesSeaBySea
                                 .add(new ProvinceBorderManager.PBData(nProvinceID, Game.getProvince(nProvinceID).getNeighboringProvinces(i)));
                           } else {
                              Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                                 .getProvinceBordersSeaBySea(nProvinceID)
                                 .updateDrawProvinceBorder_ActiveProvince();
                              ProvinceBorderManager.lProvincesSeaBySea
                                 .add(new ProvinceBorderManager.PBData(Game.getProvince(nProvinceID).getNeighboringProvinces(i), nProvinceID));
                           }
                        } else if (!Game.getProvince(nProvinceID).getSeaProvince()
                           && !Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(i)).getSeaProvince()) {
                           if (nProvinceID < Game.getProvince(nProvinceID).getNeighboringProvinces(i)) {
                              Game.getProvince(nProvinceID)
                                 .getProvinceBordersLandByLand(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                                 .updateDrawProvinceBorder_ActiveProvince();
                              ProvinceBorderManager.lProvinces
                                 .add(new ProvinceBorderManager.PBData(nProvinceID, Game.getProvince(nProvinceID).getNeighboringProvinces(i)));
                           } else {
                              Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                                 .getProvinceBordersLandByLand(nProvinceID)
                                 .updateDrawProvinceBorder_ActiveProvince();
                              ProvinceBorderManager.lProvinces
                                 .add(new ProvinceBorderManager.PBData(Game.getProvince(nProvinceID).getNeighboringProvinces(i), nProvinceID));
                           }
                        } else if (nProvinceID < Game.getProvince(nProvinceID).getNeighboringProvinces(i)) {
                           Game.getProvince(nProvinceID)
                              .getProvinceBordersLandBySea(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                              .updateDrawProvinceBorder_ActiveProvince();
                           ProvinceBorderManager.lProvincesLandBySea
                              .add(new ProvinceBorderManager.PBData(nProvinceID, Game.getProvince(nProvinceID).getNeighboringProvinces(i)));
                        } else {
                           Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(i))
                              .getProvinceBordersLandBySea(nProvinceID)
                              .updateDrawProvinceBorder_ActiveProvince();
                           ProvinceBorderManager.lProvincesLandBySea
                              .add(new ProvinceBorderManager.PBData(Game.getProvince(nProvinceID).getNeighboringProvinces(i), nProvinceID));
                        }
                     }

                     for (int ix = 0; ix < Game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ix++) {
                        if (nProvinceID < Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ix)) {
                           Game.getProvince(nProvinceID)
                              .getProvinceBordersLandBySea(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ix))
                              .updateDrawProvinceBorder_ActiveProvince();
                           ProvinceBorderManager.lProvincesLandBySea
                              .add(new ProvinceBorderManager.PBData(nProvinceID, Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ix)));
                        } else {
                           Game.getProvince(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ix))
                              .getProvinceBordersLandBySea(nProvinceID)
                              .updateDrawProvinceBorder_ActiveProvince();
                           ProvinceBorderManager.lProvincesLandBySea
                              .add(new ProvinceBorderManager.PBData(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ix), nProvinceID));
                        }
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }

            @Override
            public void resetProvinceID() {
               if (!Game.menuManager.getVisibleInGame_Civ()) {
                  ProvinceBorderManager.clearProvinceBorder();
               }
            }

            @Override
            public void update() {
               if (ProvinceBorderManager.lTimeLine < CFG.currentTimeMillis - ProvinceBorderManager.iLineOffsetInterval) {
                  ProvinceBorderManager.iLineOffset++;
                  ProvinceBorderManager.lTimeLine = CFG.currentTimeMillis;
               }
            }
         };
      } else if (Game.menuManager.getInNewGame()) {
         action = new ProvinceBorderManager.Action() {
            @Override
            public void setProvinceID(int nProvinceID) {
               ProvinceBorderManager.updateDrawProvinceBorder_SelectCiv_ByProvinceID(nProvinceID);
            }

            @Override
            public void resetProvinceID() {
            }

            @Override
            public void update() {
            }
         };
      } else if (Game.menuManager.getInGameLost()) {
         action = new ProvinceBorderManager.Action() {
            @Override
            public void setProvinceID(int nProvinceID) {
            }

            @Override
            public void resetProvinceID() {
            }

            @Override
            public void update() {
            }
         };
      } else if (!Game.menuManager.getInInitGameMenu() && !Game.menuManager.getInInitGame_Menus() && !Game.menuManager.getInLoadScenario()) {
         action = new ProvinceBorderManager.Action() {
            @Override
            public void setProvinceID(int nProvinceID) {
            }

            @Override
            public void resetProvinceID() {
            }

            @Override
            public void update() {
            }
         };
      } else {
         action = new ProvinceBorderManager.Action() {
            @Override
            public void setProvinceID(int nProvinceID) {
            }

            @Override
            public void resetProvinceID() {
            }

            @Override
            public void update() {
            }
         };
      }
   }

   public static final void updateDrawProvinceBorder_SelectCiv_ByProvinceID(int nProvinceID) {
      clearProvinceBorder();
      if (Game.getProvince(nProvinceID).getCivID() > 0 && Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getNumOfProvinces() > 0) {
         for (int o = 0; o < Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getNumOfProvinces(); o++) {
            for (int i = 0; i < Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvincesSize(); i++) {
               if (Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getCivID()
                  != Game.getProvince(Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i))
                     .getCivID()) {
                  if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)
                     < Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i)) {
                     Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o))
                        .getProvinceBordersLandByLand(
                           Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i)
                        )
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o),
                           Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i)
                        )
                     );
                  } else {
                     Game.getProvince(Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i))
                        .getProvinceBordersLandByLand(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o))
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getProvince(Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)).getNeighboringProvinces(i),
                           Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getProvinceID(o)
                        )
                     );
                  }
               }
            }
         }
      }
   }

   public static final void updateDrawProvinceBorder_SelectCiv_ByCivID(int nCivID) {
      clearProvinceBorder();
      if (nCivID > 0 && Game.getCiv(nCivID).getNumOfProvinces() > 0) {
         for (int o = 0; o < Game.getCiv(nCivID).getNumOfProvinces(); o++) {
            for (int i = 0; i < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvincesSize(); i++) {
               if (Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getCivID()
                  != Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)).getCivID()) {
                  if (Game.getCiv(nCivID).getProvinceID(o) < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)) {
                     Game.getProvince(Game.getCiv(nCivID).getProvinceID(o))
                        .getProvinceBordersLandByLand(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i))
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getCiv(nCivID).getProvinceID(o), Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)
                        )
                     );
                  } else {
                     Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i))
                        .getProvinceBordersLandByLand(Game.getCiv(nCivID).getProvinceID(o))
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i), Game.getCiv(nCivID).getProvinceID(o)
                        )
                     );
                  }
               }
            }
         }
      }
   }

   public static final void updateDrawProvinceBorder_ImprovingRelations() {
      clearProvinceBorder();

      for (int p = Game.getCiv(Game.player.iCivID).diplomacy.iImprovingRelationsSize - 1; p >= 0; p--) {
         int nCivID = Game.getCiv(Game.player.iCivID).diplomacy.improvingRelations.get(p).iCivID;
         if (nCivID > 0 && Game.getCiv(nCivID).getNumOfProvinces() > 0) {
            for (int o = 0; o < Game.getCiv(nCivID).getNumOfProvinces(); o++) {
               for (int i = 0; i < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvincesSize(); i++) {
                  if (Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getCivID()
                     != Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)).getCivID()) {
                     if (Game.getCiv(nCivID).getProvinceID(o) < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)) {
                        Game.getProvince(Game.getCiv(nCivID).getProvinceID(o))
                           .getProvinceBordersLandByLand(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i))
                           .updateDrawProvinceBorder_RelationUp();
                        lProvinces.add(
                           new ProvinceBorderManager.PBData(
                              Game.getCiv(nCivID).getProvinceID(o), Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i)
                           )
                        );
                     } else {
                        Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i))
                           .getProvinceBordersLandByLand(Game.getCiv(nCivID).getProvinceID(o))
                           .updateDrawProvinceBorder_RelationUp();
                        lProvinces.add(
                           new ProvinceBorderManager.PBData(
                              Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(i), Game.getCiv(nCivID).getProvinceID(o)
                           )
                        );
                     }
                  }
               }
            }
         }
      }

      int nCivID = Game.player.iCivID;
      if (nCivID > 0 && Game.getCiv(nCivID).getNumOfProvinces() > 0) {
         for (int o = 0; o < Game.getCiv(nCivID).getNumOfProvinces(); o++) {
            for (int ix = 0; ix < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvincesSize(); ix++) {
               if (Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getCivID()
                  != Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix)).getCivID()) {
                  if (Game.getCiv(nCivID).getProvinceID(o) < Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix)) {
                     Game.getProvince(Game.getCiv(nCivID).getProvinceID(o))
                        .getProvinceBordersLandByLand(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix))
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getCiv(nCivID).getProvinceID(o), Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix)
                        )
                     );
                  } else {
                     Game.getProvince(Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix))
                        .getProvinceBordersLandByLand(Game.getCiv(nCivID).getProvinceID(o))
                        .updateDrawProvinceBorder_ActiveCivilizationBorder();
                     lProvinces.add(
                        new ProvinceBorderManager.PBData(
                           Game.getProvince(Game.getCiv(nCivID).getProvinceID(o)).getNeighboringProvinces(ix), Game.getCiv(nCivID).getProvinceID(o)
                        )
                     );
                  }
               }
            }
         }
      }
   }

   public interface Action {
      void setProvinceID(int var1);

      void resetProvinceID();

      void update();
   }

   public static class PBData {
      public int iProvinceID;
      public int iWithProvinceID;

      public PBData(int iProvinceID, int iWithProvinceID) {
         this.iProvinceID = iProvinceID;
         this.iWithProvinceID = iWithProvinceID;
      }
   }
}
