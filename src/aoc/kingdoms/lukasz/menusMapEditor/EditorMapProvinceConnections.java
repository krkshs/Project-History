package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapProvinceConnections extends Menu {
   public static int activeProvinceID = -1;

   public EditorMapProvinceConnections() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            SaveManager.saveProvinceNeighboringProvinces();
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            Game.menuManager.addToast(Game.lang.get("Saved"));
         }
      });
      menuElements.add(
         new ButtonMain(null, 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Generate"));
            }

            @Override
            public void actionElement() {
               Dialog.setDialogType(Dialog.DialogType.GENERATE_PROVINCE_CONNECTIONS);
            }
         }
      );
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            iTranslateX,
            iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            false
         );
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText = "ACTIVE PROVINCE ID: "
         + activeProvinceID
         + "\n\nSPACE -> SET ACTIVE PROVINCE\nENTER -> ADD CONNECTION\nBACKSPACE -> REMOVE CONNECTION\nM -> REFLECT PROVINCE BORDER\nR -> REBUILD PROVINCE BACKGROUND\nF5 -> REBUILD PROVINCE BG WITHOUT CROP";
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB,
         iTranslateX + CFG.PADDING * 3,
         iTranslateY + CFG.PADDING * 3,
         (int)Renderer.glyphLayout.width + CFG.PADDING * 2,
         (int)Renderer.glyphLayout.height + CFG.PADDING * 2
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, Colors.COLOR_TEXT_TITLE);
   }

   public static boolean keyUp(int keycode) {
      if (keycode == 62) {
         if (activeProvinceID >= 0) {
            ProvinceDrawDetails.updateDrawProvinceDetails_ProvinceID(activeProvinceID);
         }

         activeProvinceID = Game.iActiveProvince;
         if (activeProvinceID >= 0) {
            ProvinceDrawDetails.updateDrawProvinceDetails_ProvinceID_Active(activeProvinceID);
         }

         Game.menuManager.rebuildEditorMapProvinceConnectionsList();
      }

      if (keycode == 46 && Game.iActiveProvince >= 0) {
         Game.getProvince(Game.iActiveProvince).buildProvinceBG(true);
         Game.getProvince(Game.iActiveProvince).loadProvinceBG();
         Game.menuManager.addToast(Game.lang.get("Done") + ": " + Game.iActiveProvince);
      }

      if (keycode == 135 && Game.iActiveProvince >= 0) {
         Game.getProvince(Game.iActiveProvince).buildProvinceBG_Just(true);
         Game.getProvince(Game.iActiveProvince).loadProvinceBG();
         Game.menuManager.addToast(Game.lang.get("Done") + ": " + Game.iActiveProvince);
      }

      if (activeProvinceID < 0) {
         return false;
      } else if (keycode == 66) {
         return addConnection(activeProvinceID, Game.iActiveProvince);
      } else if (keycode != 41) {
         if (keycode == 67) {
            if (activeProvinceID != Game.iActiveProvince) {
               Game.getProvince(Game.iActiveProvince).removeNeighboringProvince(activeProvinceID);
               Game.getProvince(Game.iActiveProvince).removeNeighboringSeaProvince(activeProvinceID);
               Game.getProvince(activeProvinceID).removeNeighboringProvince(Game.iActiveProvince);
               Game.getProvince(activeProvinceID).removeNeighboringSeaProvince(Game.iActiveProvince);
               if (!Game.getProvince(Game.iActiveProvince).getSeaProvince() && Game.getProvince(Game.iActiveProvince).getNeighboringSeaProvincesSize() == 0) {
                  Game.getProvince(Game.iActiveProvince).setLevelOfPort(-1);
               }

               if (!Game.getProvince(activeProvinceID).getSeaProvince() && Game.getProvince(activeProvinceID).getNeighboringSeaProvincesSize() == 0) {
                  Game.getProvince(activeProvinceID).setLevelOfPort(-1);
               }

               if (activeProvinceID > Game.iActiveProvince) {
                  Game.getProvince(Game.iActiveProvince).removeProvinceBorder(activeProvinceID);
               } else {
                  Game.getProvince(activeProvinceID).removeProvinceBorder(Game.iActiveProvince);
               }

               Game.menuManager.addToast(Game.lang.get("Removed") + ": [" + activeProvinceID + " - " + Game.iActiveProvince + "]");
               Game.menuManager.rebuildEditorMapProvinceConnectionsList();
            }

            return true;
         } else {
            return false;
         }
      } else {
         if (activeProvinceID != Game.iActiveProvince) {
            List<Integer> nPointsX = new ArrayList<>();
            List<Integer> nPointsY = new ArrayList<>();
            if (activeProvinceID > Game.iActiveProvince) {
               for (int i = 0; i < Game.getProvince(Game.iActiveProvince).getProvinceBordersLandByLand(activeProvinceID).lPointsX.size(); i++) {
                  nPointsX.add(
                     Game.getProvince(Game.iActiveProvince)
                        .getProvinceBordersLandByLand(activeProvinceID)
                        .lPointsX
                        .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersLandByLand(activeProvinceID).lPointsX.size() - 1 - i)
                  );
                  nPointsY.add(
                     Game.getProvince(Game.iActiveProvince)
                        .getProvinceBordersLandByLand(activeProvinceID)
                        .lPointsY
                        .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersLandByLand(activeProvinceID).lPointsY.size() - 1 - i)
                  );
               }

               if (nPointsX.size() == 0) {
                  for (int i = 0; i < Game.getProvince(Game.iActiveProvince).getProvinceBordersLandBySea(activeProvinceID).lPointsX.size(); i++) {
                     nPointsX.add(
                        Game.getProvince(Game.iActiveProvince)
                           .getProvinceBordersLandBySea(activeProvinceID)
                           .lPointsX
                           .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersLandBySea(activeProvinceID).lPointsX.size() - 1 - i)
                     );
                     nPointsY.add(
                        Game.getProvince(Game.iActiveProvince)
                           .getProvinceBordersLandBySea(activeProvinceID)
                           .lPointsY
                           .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersLandBySea(activeProvinceID).lPointsY.size() - 1 - i)
                     );
                  }
               }

               if (nPointsX.size() == 0) {
                  for (int i = 0; i < Game.getProvince(Game.iActiveProvince).getProvinceBordersSeaBySea(activeProvinceID).lPointsX.size(); i++) {
                     nPointsX.add(
                        Game.getProvince(Game.iActiveProvince)
                           .getProvinceBordersSeaBySea(activeProvinceID)
                           .lPointsX
                           .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersSeaBySea(activeProvinceID).lPointsX.size() - 1 - i)
                     );
                     nPointsY.add(
                        Game.getProvince(Game.iActiveProvince)
                           .getProvinceBordersSeaBySea(activeProvinceID)
                           .lPointsY
                           .get(Game.getProvince(Game.iActiveProvince).getProvinceBordersSeaBySea(activeProvinceID).lPointsY.size() - 1 - i)
                     );
                  }
               }
            } else {
               for (int i = 0; i < Game.getProvince(activeProvinceID).getProvinceBordersLandByLand(Game.iActiveProvince).lPointsX.size(); i++) {
                  nPointsX.add(
                     Game.getProvince(activeProvinceID)
                        .getProvinceBordersLandByLand(Game.iActiveProvince)
                        .lPointsX
                        .get(Game.getProvince(activeProvinceID).getProvinceBordersLandByLand(Game.iActiveProvince).lPointsX.size() - 1 - i)
                  );
                  nPointsY.add(
                     Game.getProvince(activeProvinceID)
                        .getProvinceBordersLandByLand(Game.iActiveProvince)
                        .lPointsY
                        .get(Game.getProvince(activeProvinceID).getProvinceBordersLandByLand(Game.iActiveProvince).lPointsY.size() - 1 - i)
                  );
               }

               if (nPointsX.size() == 0) {
                  for (int i = 0; i < Game.getProvince(activeProvinceID).getProvinceBordersLandBySea(Game.iActiveProvince).lPointsX.size(); i++) {
                     nPointsX.add(
                        Game.getProvince(activeProvinceID)
                           .getProvinceBordersLandBySea(Game.iActiveProvince)
                           .lPointsX
                           .get(Game.getProvince(activeProvinceID).getProvinceBordersLandBySea(Game.iActiveProvince).lPointsX.size() - 1 - i)
                     );
                     nPointsY.add(
                        Game.getProvince(activeProvinceID)
                           .getProvinceBordersLandBySea(Game.iActiveProvince)
                           .lPointsY
                           .get(Game.getProvince(activeProvinceID).getProvinceBordersLandBySea(Game.iActiveProvince).lPointsY.size() - 1 - i)
                     );
                  }
               }

               if (nPointsX.size() == 0) {
                  for (int i = 0; i < Game.getProvince(activeProvinceID).getProvinceBordersSeaBySea(Game.iActiveProvince).lPointsX.size(); i++) {
                     nPointsX.add(
                        Game.getProvince(activeProvinceID)
                           .getProvinceBordersSeaBySea(Game.iActiveProvince)
                           .lPointsX
                           .get(Game.getProvince(activeProvinceID).getProvinceBordersSeaBySea(Game.iActiveProvince).lPointsX.size() - 1 - i)
                     );
                     nPointsY.add(
                        Game.getProvince(activeProvinceID)
                           .getProvinceBordersSeaBySea(Game.iActiveProvince)
                           .lPointsY
                           .get(Game.getProvince(activeProvinceID).getProvinceBordersSeaBySea(Game.iActiveProvince).lPointsY.size() - 1 - i)
                     );
                  }
               }
            }

            if (activeProvinceID > Game.iActiveProvince) {
               Game.getProvince(Game.iActiveProvince).removeProvinceBorder(activeProvinceID);
            } else {
               Game.getProvince(activeProvinceID).removeProvinceBorder(Game.iActiveProvince);
            }

            if (activeProvinceID > Game.iActiveProvince) {
               Game.getProvince(Game.iActiveProvince).addProvinceBorder(activeProvinceID, nPointsX, nPointsY);
            } else {
               Game.getProvince(activeProvinceID).addProvinceBorder(Game.iActiveProvince, nPointsX, nPointsY);
            }
         }

         return true;
      }
   }

   public static final boolean generateConnections_BoxInBox(int i, int j) {
      if (generateConnections_PointInBox(i, Game.getProvince(j).getMinX(), Game.getProvince(j).getMinY())) {
         return true;
      } else if (generateConnections_PointInBox(i, Game.getProvince(j).getMaxX(), Game.getProvince(j).getMinY())) {
         return true;
      } else if (generateConnections_PointInBox(i, Game.getProvince(j).getMinX(), Game.getProvince(j).getMaxY())) {
         return true;
      } else if (generateConnections_PointInBox(i, Game.getProvince(j).getMaxX(), Game.getProvince(j).getMaxY())) {
         return true;
      } else if (generateConnections_PointInBox(j, Game.getProvince(i).getMinX(), Game.getProvince(i).getMinY())) {
         return true;
      } else if (generateConnections_PointInBox(j, Game.getProvince(i).getMaxX(), Game.getProvince(i).getMinY())) {
         return true;
      } else {
         return generateConnections_PointInBox(j, Game.getProvince(i).getMinX(), Game.getProvince(i).getMaxY())
            ? true
            : generateConnections_PointInBox(j, Game.getProvince(i).getMaxX(), Game.getProvince(i).getMaxY());
      }
   }

   public static final boolean generateConnections_PointInBox(int nProvinceID, int iX, int iY) {
      return iX >= Game.getProvince(nProvinceID).getMinX()
         && iX <= Game.getProvince(nProvinceID).getMaxX()
         && iY >= Game.getProvince(nProvinceID).getMinY()
         && iY <= Game.getProvince(nProvinceID).getMaxY();
   }

   public static void generateConnections() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         for (int j = i + 1; j < Game.getProvincesSize(); j++) {
            if (generateConnections_BoxInBox(i, j)) {
               boolean addConnection = false;

               for (int i2 = 0; i2 < Game.getProvince(i).getPointsSize(); i2++) {
                  for (int j2 = 0; j2 < Game.getProvince(j).getPointsSize(); j2++) {
                     if (Game.getProvince(i).getPointsX(i2) == Game.getProvince(j).getPointsX(j2)
                        && Game.getProvince(i).getPointsY(i2) == Game.getProvince(j).getPointsY(j2)) {
                        try {
                           if (Game.getProvince(i).getPointsX(i2 + 1) == Game.getProvince(j).getPointsX(j2 + 1)
                              && Game.getProvince(i).getPointsY(i2 + 1) == Game.getProvince(j).getPointsY(j2 + 1)) {
                              addConnection = true;
                              break;
                           }
                        } catch (IndexOutOfBoundsException var14) {
                           int ti2 = i2 + 1;
                           int tj2 = j2 + 1;
                           if (Game.getProvince(i).getPointsSize() == ti2) {
                              ti2 = 0;
                           }

                           if (Game.getProvince(j).getPointsSize() == tj2) {
                              tj2 = 0;
                           }

                           try {
                              if (Game.getProvince(i).getPointsX(ti2) == Game.getProvince(j).getPointsX(tj2)
                                 && Game.getProvince(i).getPointsY(ti2) == Game.getProvince(j).getPointsY(tj2)) {
                                 addConnection = true;
                                 break;
                              }
                           } catch (IndexOutOfBoundsException var11) {
                           }
                        }

                        try {
                           if (Game.getProvince(i).getPointsX(i2 + 1) == Game.getProvince(j).getPointsX(j2 - 1)
                              && Game.getProvince(i).getPointsY(i2 + 1) == Game.getProvince(j).getPointsY(j2 - 1)) {
                              addConnection = true;
                              break;
                           }
                        } catch (IndexOutOfBoundsException var13) {
                           int ti2x = i2 + 1;
                           int tj2x = j2 - 1;
                           if (Game.getProvince(i).getPointsSize() == ti2x) {
                              ti2x = 0;
                           }

                           if (tj2x < 0) {
                              tj2x = Game.getProvince(j).getPointsSize() - 1;
                           }

                           try {
                              if (Game.getProvince(i).getPointsX(ti2x) == Game.getProvince(j).getPointsX(tj2x)
                                 && Game.getProvince(i).getPointsY(ti2x) == Game.getProvince(j).getPointsY(tj2x)) {
                                 addConnection = true;
                                 break;
                              }
                           } catch (IndexOutOfBoundsException var10) {
                           }
                        }

                        try {
                           if (Game.getProvince(i).getPointsX(i2 - 1) == Game.getProvince(j).getPointsX(j2 + 1)
                              && Game.getProvince(i).getPointsY(i2 - 1) == Game.getProvince(j).getPointsY(j2 + 1)) {
                              addConnection = true;
                              break;
                           }
                        } catch (IndexOutOfBoundsException var12) {
                           int ti2xx = i2 - 1;
                           int tj2xx = j2 + 1;
                           if (ti2xx < 0) {
                              ti2xx = Game.getProvince(i).getPointsSize() - 1;
                           }

                           if (Game.getProvince(j).getPointsSize() == tj2xx) {
                              tj2xx = 0;
                           }

                           try {
                              if (Game.getProvince(i).getPointsX(ti2xx) == Game.getProvince(j).getPointsX(tj2xx)
                                 && Game.getProvince(i).getPointsY(ti2xx) == Game.getProvince(j).getPointsY(tj2xx)) {
                                 addConnection = true;
                                 break;
                              }
                           } catch (IndexOutOfBoundsException var9) {
                           }
                        }
                     }

                     if (addConnection) {
                        break;
                     }
                  }

                  if (addConnection) {
                     break;
                  }
               }

               if (addConnection) {
                  addConnection(i, j);
               }
            }
         }
      }
   }

   public static boolean addConnection(int nProvinceID, int nProvinceID2) {
      if (nProvinceID != nProvinceID2) {
         for (int i = 0; i < Game.getProvince(nProvinceID2).getNeighboringProvincesSize(); i++) {
            if (Game.getProvince(nProvinceID2).getNeighboringProvinces(i) == nProvinceID) {
               return true;
            }
         }

         for (int ix = 0; ix < Game.getProvince(nProvinceID2).getNeighboringSeaProvincesSize(); ix++) {
            if (Game.getProvince(nProvinceID2).getNeighboringSeaProvinces(ix) == nProvinceID) {
               return true;
            }
         }

         for (int ixx = 0; ixx < Game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ixx++) {
            if (Game.getProvince(nProvinceID).getNeighboringSeaProvinces(ixx) == nProvinceID2) {
               return true;
            }
         }

         if (Game.getProvince(nProvinceID).getSeaProvince()) {
            Game.getProvince(nProvinceID).addNeighboringProvince(nProvinceID2);
         } else if (Game.getProvince(nProvinceID2).getSeaProvince()) {
            Game.getProvince(nProvinceID).addNeighboringSeaProvince(nProvinceID2);
            Game.getProvince(nProvinceID).setLevelOfPort(1);
         } else {
            Game.getProvince(nProvinceID).addNeighboringProvince(nProvinceID2);
         }

         if (Game.getProvince(nProvinceID2).getSeaProvince()) {
            Game.getProvince(nProvinceID2).addNeighboringProvince(nProvinceID);
         } else if (Game.getProvince(nProvinceID).getSeaProvince()) {
            Game.getProvince(nProvinceID2).addNeighboringSeaProvince(nProvinceID);
            Game.getProvince(nProvinceID2).setLevelOfPort(1);
         } else {
            Game.getProvince(nProvinceID2).addNeighboringProvince(nProvinceID);
         }

         List<Integer> nPointsX = new ArrayList<>();
         List<Integer> nPointsY = new ArrayList<>();

         for (int ixxx = 0; ixxx < Game.getProvince(nProvinceID2).getPointsSize(); ixxx++) {
            boolean found = false;
            int j = 0;
            if (j < Game.getProvince(nProvinceID).getPointsSize()) {
               found = true;
               boolean l1 = false;
               int o = 0;

               for (int oSize = Game.getProvince(nProvinceID).getPointsSize(); o < oSize; o++) {
                  if (Game.getProvince(nProvinceID2).getPointsX(Game.getProvince(nProvinceID2).getPointsSize() - 1)
                        == Game.getProvince(nProvinceID).getPointsX(o)
                     && Game.getProvince(nProvinceID2).getPointsY(Game.getProvince(nProvinceID2).getPointsSize() - 1)
                        == Game.getProvince(nProvinceID).getPointsY(o)) {
                     l1 = true;
                  }
               }

               if (l1) {
                  l1 = false;
                  o = 0;

                  for (int oSizex = Game.getProvince(nProvinceID2).getPointsSize(); o < oSizex; o++) {
                     if (Game.getProvince(nProvinceID).getPointsX(Game.getProvince(nProvinceID).getPointsSize() - 1)
                           == Game.getProvince(nProvinceID2).getPointsX(o)
                        && Game.getProvince(nProvinceID).getPointsY(Game.getProvince(nProvinceID).getPointsSize() - 1)
                           == Game.getProvince(nProvinceID2).getPointsY(o)) {
                        l1 = true;
                     }
                  }

                  if (!l1) {
                     boolean f1 = false;

                     for (int ox = Game.getProvince(nProvinceID).getPointsSize() - 1; ox >= 0; ox--) {
                        if (!f1) {
                           int n = 0;

                           for (int nSize = Game.getProvince(nProvinceID2).getPointsSize(); n < nSize; n++) {
                              if (Game.getProvince(nProvinceID).getPointsX(ox) == Game.getProvince(nProvinceID2).getPointsX(n)
                                 && Game.getProvince(nProvinceID).getPointsY(ox) == Game.getProvince(nProvinceID2).getPointsY(n)) {
                                 f1 = true;
                                 nPointsX.add(Game.getProvince(nProvinceID).getPointsX(ox));
                                 nPointsY.add(Game.getProvince(nProvinceID).getPointsY(ox));
                                 break;
                              }
                           }
                        } else {
                           boolean end = true;
                           int n = 0;

                           for (int nSizex = Game.getProvince(nProvinceID2).getPointsSize(); n < nSizex; n++) {
                              if (Game.getProvince(nProvinceID).getPointsX(ox) == Game.getProvince(nProvinceID2).getPointsX(n)
                                 && Game.getProvince(nProvinceID).getPointsY(ox) == Game.getProvince(nProvinceID2).getPointsY(n)) {
                                 end = false;
                                 nPointsX.add(Game.getProvince(nProvinceID).getPointsX(ox));
                                 nPointsY.add(Game.getProvince(nProvinceID).getPointsY(ox));
                                 break;
                              }
                           }

                           if (end) {
                              break;
                           }
                        }
                     }
                  } else {
                     o = 0;
                     boolean t1 = false;

                     for (int oxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxx >= 0; oxx--) {
                        t1 = false;
                        int n = 0;

                        for (int nSizexx = Game.getProvince(nProvinceID).getPointsSize(); n < nSizexx; n++) {
                           if (Game.getProvince(nProvinceID2).getPointsX(oxx) == Game.getProvince(nProvinceID).getPointsX(n)
                              && Game.getProvince(nProvinceID2).getPointsY(oxx) == Game.getProvince(nProvinceID).getPointsY(n)) {
                              t1 = true;
                              break;
                           }
                        }

                        if (!t1) {
                           oxx++;

                           while (oxx < Game.getProvince(nProvinceID2).getPointsSize() - 1) {
                              nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxx));
                              nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxx));
                              oxx++;
                           }
                           break;
                        }
                     }

                     for (int h = 0; h < Game.getProvince(nProvinceID2).getPointsSize(); h++) {
                        boolean addT = false;

                        for (int n = 0; n < Game.getProvince(nProvinceID).getPointsSize(); n++) {
                           if (Game.getProvince(nProvinceID2).getPointsX(h) == Game.getProvince(nProvinceID).getPointsX(n)
                              && Game.getProvince(nProvinceID2).getPointsY(h) == Game.getProvince(nProvinceID).getPointsY(n)) {
                              addT = true;
                              break;
                           }
                        }

                        if (!addT) {
                           break;
                        }

                        nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(h));
                        nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(h));
                     }
                  }
               } else {
                  boolean f1 = false;

                  for (int oxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxx >= 0; oxx--) {
                     if (!f1) {
                        int nx = 0;

                        for (int nSizexxx = Game.getProvince(nProvinceID).getPointsSize(); nx < nSizexxx; nx++) {
                           if (Game.getProvince(nProvinceID2).getPointsX(oxx) == Game.getProvince(nProvinceID).getPointsX(nx)
                              && Game.getProvince(nProvinceID2).getPointsY(oxx) == Game.getProvince(nProvinceID).getPointsY(nx)) {
                              f1 = true;
                              nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxx));
                              nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxx));
                              break;
                           }
                        }
                     } else {
                        boolean end = true;
                        int nx = 0;

                        for (int nSizexxxx = Game.getProvince(nProvinceID).getPointsSize(); nx < nSizexxxx; nx++) {
                           if (Game.getProvince(nProvinceID2).getPointsX(oxx) == Game.getProvince(nProvinceID).getPointsX(nx)
                              && Game.getProvince(nProvinceID2).getPointsY(oxx) == Game.getProvince(nProvinceID).getPointsY(nx)) {
                              end = false;
                              nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxx));
                              nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxx));
                              break;
                           }
                        }

                        if (end) {
                           break;
                        }
                     }
                  }
               }
            }

            if (found) {
               break;
            }
         }

         if (nPointsX.size() == 0) {
            for (int ixxx = 0; ixxx < Game.getProvince(nProvinceID2).getPointsSize(); ixxx++) {
               boolean foundx = false;
               int jx = 0;
               if (jx < Game.getProvince(nProvinceID).getPointsSize()) {
                  foundx = true;
                  boolean l1 = false;
                  int o = 0;

                  for (int oSizexx = Game.getProvince(nProvinceID).getPointsSize(); o < oSizexx; o++) {
                     if (Game.getProvince(nProvinceID2).getPointsX(Game.getProvince(nProvinceID2).getPointsSize() - 1)
                           == Game.getProvince(nProvinceID).getPointsX(o) + Game.mapBG.getWidth()
                        && Game.getProvince(nProvinceID2).getPointsY(Game.getProvince(nProvinceID2).getPointsSize() - 1)
                           == Game.getProvince(nProvinceID).getPointsY(o)) {
                        l1 = true;
                     }
                  }

                  if (l1) {
                     l1 = false;
                     o = 0;

                     for (int oSizexxx = Game.getProvince(nProvinceID2).getPointsSize(); o < oSizexxx; o++) {
                        if (Game.getProvince(nProvinceID).getPointsX(Game.getProvince(nProvinceID).getPointsSize() - 1) + Game.mapBG.getWidth()
                              == Game.getProvince(nProvinceID2).getPointsX(o)
                           && Game.getProvince(nProvinceID).getPointsY(Game.getProvince(nProvinceID).getPointsSize() - 1)
                              == Game.getProvince(nProvinceID2).getPointsY(o)) {
                           l1 = true;
                        }
                     }

                     if (!l1) {
                        boolean f1 = false;

                        for (int oxxx = Game.getProvince(nProvinceID).getPointsSize() - 1; oxxx >= 0; oxxx--) {
                           if (!f1) {
                              int nx = 0;

                              for (int nSizexxxxx = Game.getProvince(nProvinceID2).getPointsSize(); nx < nSizexxxxx; nx++) {
                                 if (Game.getProvince(nProvinceID).getPointsX(oxxx) + Game.mapBG.getWidth() == Game.getProvince(nProvinceID2).getPointsX(nx)
                                    && Game.getProvince(nProvinceID).getPointsY(oxxx) == Game.getProvince(nProvinceID2).getPointsY(nx)) {
                                    f1 = true;
                                    nPointsX.add(Game.getProvince(nProvinceID).getPointsX(oxxx));
                                    nPointsY.add(Game.getProvince(nProvinceID).getPointsY(oxxx));
                                    break;
                                 }
                              }
                           } else {
                              boolean end = true;
                              int nx = 0;

                              for (int nSizexxxxxx = Game.getProvince(nProvinceID2).getPointsSize(); nx < nSizexxxxxx; nx++) {
                                 if (Game.getProvince(nProvinceID).getPointsX(oxxx) + Game.mapBG.getWidth() == Game.getProvince(nProvinceID2).getPointsX(nx)
                                    && Game.getProvince(nProvinceID).getPointsY(oxxx) == Game.getProvince(nProvinceID2).getPointsY(nx)) {
                                    end = false;
                                    nPointsX.add(Game.getProvince(nProvinceID).getPointsX(oxxx));
                                    nPointsY.add(Game.getProvince(nProvinceID).getPointsY(oxxx));
                                    break;
                                 }
                              }

                              if (end) {
                                 break;
                              }
                           }
                        }
                     } else {
                        o = 0;
                        boolean t1 = false;

                        for (int oxxxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxxxx >= 0; oxxxx--) {
                           t1 = false;
                           int nx = 0;

                           for (int nSizexxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nx < nSizexxxxxxx; nx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxx) == Game.getProvince(nProvinceID).getPointsX(nx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxx) == Game.getProvince(nProvinceID).getPointsY(nx)) {
                                 t1 = true;
                                 break;
                              }
                           }

                           if (!t1) {
                              oxxxx++;

                              while (oxxxx < Game.getProvince(nProvinceID2).getPointsSize() - 1) {
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxx));
                                 oxxxx++;
                              }
                              break;
                           }
                        }

                        for (int h = 0; h < Game.getProvince(nProvinceID2).getPointsSize(); h++) {
                           boolean addT = false;

                           for (int nx = 0; nx < Game.getProvince(nProvinceID).getPointsSize(); nx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(h) == Game.getProvince(nProvinceID).getPointsX(nx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(h) == Game.getProvince(nProvinceID).getPointsY(nx)) {
                                 addT = true;
                                 break;
                              }
                           }

                           if (!addT) {
                              break;
                           }

                           nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(h));
                           nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(h));
                        }
                     }
                  } else {
                     boolean f1 = false;

                     for (int oxxxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxxxx >= 0; oxxxx--) {
                        if (!f1) {
                           int nxx = 0;

                           for (int nSizexxxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nxx < nSizexxxxxxxx; nxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxx) == Game.getProvince(nProvinceID).getPointsX(nxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxx) == Game.getProvince(nProvinceID).getPointsY(nxx)) {
                                 f1 = true;
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxx));
                                 break;
                              }
                           }
                        } else {
                           boolean end = true;
                           int nxx = 0;

                           for (int nSizexxxxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nxx < nSizexxxxxxxxx; nxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxx) == Game.getProvince(nProvinceID).getPointsX(nxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxx) == Game.getProvince(nProvinceID).getPointsY(nxx)) {
                                 end = false;
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxx));
                                 break;
                              }
                           }

                           if (end) {
                              break;
                           }
                        }
                     }
                  }
               }

               if (foundx) {
                  break;
               }
            }
         }

         if (nPointsX.size() == 0) {
            for (int ixxx = 0; ixxx < Game.getProvince(nProvinceID2).getPointsSize(); ixxx++) {
               boolean foundxx = false;
               int jxx = 0;
               if (jxx < Game.getProvince(nProvinceID).getPointsSize()) {
                  foundxx = true;
                  boolean l1 = false;
                  int o = 0;

                  for (int oSizexxxx = Game.getProvince(nProvinceID).getPointsSize(); o < oSizexxxx; o++) {
                     if (Game.getProvince(nProvinceID2).getPointsX(Game.getProvince(nProvinceID2).getPointsSize() - 1) + Game.mapBG.getWidth()
                           == Game.getProvince(nProvinceID).getPointsX(o)
                        && Game.getProvince(nProvinceID2).getPointsY(Game.getProvince(nProvinceID2).getPointsSize() - 1)
                           == Game.getProvince(nProvinceID).getPointsY(o)) {
                        l1 = true;
                     }
                  }

                  if (l1) {
                     l1 = false;
                     o = 0;

                     for (int oSizexxxxx = Game.getProvince(nProvinceID2).getPointsSize(); o < oSizexxxxx; o++) {
                        if (Game.getProvince(nProvinceID).getPointsX(Game.getProvince(nProvinceID).getPointsSize() - 1)
                              == Game.getProvince(nProvinceID2).getPointsX(o) + Game.mapBG.getWidth()
                           && Game.getProvince(nProvinceID).getPointsY(Game.getProvince(nProvinceID).getPointsSize() - 1)
                              == Game.getProvince(nProvinceID2).getPointsY(o)) {
                           l1 = true;
                        }
                     }

                     if (!l1) {
                        boolean f1 = false;

                        for (int oxxxxx = Game.getProvince(nProvinceID).getPointsSize() - 1; oxxxxx >= 0; oxxxxx--) {
                           if (!f1) {
                              int nxx = 0;

                              for (int nSizexxxxxxxxxx = Game.getProvince(nProvinceID2).getPointsSize(); nxx < nSizexxxxxxxxxx; nxx++) {
                                 if (Game.getProvince(nProvinceID).getPointsX(oxxxxx) == Game.getProvince(nProvinceID2).getPointsX(nxx) + Game.mapBG.getWidth()
                                    && Game.getProvince(nProvinceID).getPointsY(oxxxxx) == Game.getProvince(nProvinceID2).getPointsY(nxx)) {
                                    f1 = true;
                                    nPointsX.add(Game.getProvince(nProvinceID).getPointsX(oxxxxx));
                                    nPointsY.add(Game.getProvince(nProvinceID).getPointsY(oxxxxx));
                                    break;
                                 }
                              }
                           } else {
                              boolean end = true;
                              int nxx = 0;

                              for (int nSizexxxxxxxxxxx = Game.getProvince(nProvinceID2).getPointsSize(); nxx < nSizexxxxxxxxxxx; nxx++) {
                                 if (Game.getProvince(nProvinceID).getPointsX(oxxxxx) == Game.getProvince(nProvinceID2).getPointsX(nxx) + Game.mapBG.getWidth()
                                    && Game.getProvince(nProvinceID).getPointsY(oxxxxx) == Game.getProvince(nProvinceID2).getPointsY(nxx)) {
                                    end = false;
                                    nPointsX.add(Game.getProvince(nProvinceID).getPointsX(oxxxxx));
                                    nPointsY.add(Game.getProvince(nProvinceID).getPointsY(oxxxxx));
                                    break;
                                 }
                              }

                              if (end) {
                                 break;
                              }
                           }
                        }
                     } else {
                        o = 0;
                        boolean t1 = false;

                        for (int oxxxxxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxxxxxx >= 0; oxxxxxx--) {
                           t1 = false;
                           int nxx = 0;

                           for (int nSizexxxxxxxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nxx < nSizexxxxxxxxxxxx; nxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxxxx) == Game.getProvince(nProvinceID).getPointsX(nxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxxxx) == Game.getProvince(nProvinceID).getPointsY(nxx)) {
                                 t1 = true;
                                 break;
                              }
                           }

                           if (!t1) {
                              oxxxxxx++;

                              while (oxxxxxx < Game.getProvince(nProvinceID2).getPointsSize() - 1) {
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxxxx));
                                 oxxxxxx++;
                              }
                              break;
                           }
                        }

                        for (int h = 0; h < Game.getProvince(nProvinceID2).getPointsSize(); h++) {
                           boolean addT = false;

                           for (int nxx = 0; nxx < Game.getProvince(nProvinceID).getPointsSize(); nxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(h) == Game.getProvince(nProvinceID).getPointsX(nxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(h) == Game.getProvince(nProvinceID).getPointsY(nxx)) {
                                 addT = true;
                                 break;
                              }
                           }

                           if (!addT) {
                              break;
                           }

                           nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(h));
                           nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(h));
                        }
                     }
                  } else {
                     boolean f1 = false;

                     for (int oxxxxxx = Game.getProvince(nProvinceID2).getPointsSize() - 1; oxxxxxx >= 0; oxxxxxx--) {
                        if (!f1) {
                           int nxxx = 0;

                           for (int nSizexxxxxxxxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nxxx < nSizexxxxxxxxxxxxx; nxxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxxxx) == Game.getProvince(nProvinceID).getPointsX(nxxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxxxx) == Game.getProvince(nProvinceID).getPointsY(nxxx)) {
                                 f1 = true;
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxxxx));
                                 break;
                              }
                           }
                        } else {
                           boolean end = true;
                           int nxxx = 0;

                           for (int nSizexxxxxxxxxxxxxx = Game.getProvince(nProvinceID).getPointsSize(); nxxx < nSizexxxxxxxxxxxxxx; nxxx++) {
                              if (Game.getProvince(nProvinceID2).getPointsX(oxxxxxx) == Game.getProvince(nProvinceID).getPointsX(nxxx) + Game.mapBG.getWidth()
                                 && Game.getProvince(nProvinceID2).getPointsY(oxxxxxx) == Game.getProvince(nProvinceID).getPointsY(nxxx)) {
                                 end = false;
                                 nPointsX.add(Game.getProvince(nProvinceID2).getPointsX(oxxxxxx));
                                 nPointsY.add(Game.getProvince(nProvinceID2).getPointsY(oxxxxxx));
                                 break;
                              }
                           }

                           if (end) {
                              break;
                           }
                        }
                     }
                  }
               }

               if (foundxx) {
                  break;
               }
            }
         }

         for (int a = 0; a < nPointsX.size(); a++) {
            nPointsX.set(a, nPointsX.get(a) / Game.mapBG.iMapScale);
            nPointsY.set(a, nPointsY.get(a) / Game.mapBG.iMapScale);
         }

         if (nProvinceID > nProvinceID2) {
            Game.getProvince(nProvinceID2).addProvinceBorder(nProvinceID, nPointsX, nPointsY);
         } else {
            Game.getProvince(nProvinceID).addProvinceBorder(nProvinceID2, nPointsX, nPointsY);
         }

         Game.menuManager.addToast(Game.lang.get("Added") + ": [" + nProvinceID + " - " + nProvinceID2 + "]");
         Game.menuManager.rebuildEditorMapProvinceConnectionsList();
      }

      return true;
   }
}
