package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvinceNames;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.province.ProvinceNameData;
import aoc.kingdoms.lukasz.map.province.ProvinceNamesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapProvinceNamePoints extends Menu {
   public static boolean firstPoint = true;
   public static boolean centerPoint = false;

   public EditorMapProvinceNamePoints() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            SaveManager.saveProvinceNamesPoints();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            Game.menuManager.addToast(Game.lang.get("Saved"));
         }
      });
      menuElements.add(
         new ButtonMain(
            null, 1, -1, CFG.GAME_WIDTH - CFG.PADDING - CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true
         ) {
            @Override
            public void updateLanguage() {
               this.setText("Change point");
            }

            @Override
            public void actionElement() {
               EditorMapProvinceNamePoints.firstPoint = !EditorMapProvinceNamePoints.firstPoint;
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
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            false
         );
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH + iTranslateX,
            iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            false,
            false
         );
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      FBOProvinceNames.fboNumToGenerate_Names = 0;
      FBOProvinceNames.disposeProvinceNamesTexture();
      FBOProvinceNames.disposeProvinceNamesFBO();
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText;
      if (Game.iActiveProvince >= 0) {
         sText = "PROVINCE NAME: " + Game.getProvince(Game.iActiveProvince).getProvinceName();
      } else {
         sText = "SELECT PROVINCE";
      }

      sText = sText + "\n W A S D x3";
      sText = sText + "\n CTRL + W A S D x15";
      sText = sText + "\n ARROWS x1";
      sText = sText + "\n SPACE -> Change Point MODE";
      sText = sText + "\n C -> Center Point ON/OFF";
      sText = sText + "\n P -> RESET";
      if (centerPoint) {
         sText = sText + "\n MODE -> CENTER POINT";
      } else if (firstPoint) {
         sText = sText + "\n MODE -> LEFT POINT";
      } else {
         sText = sText + "\n MODE -> RIGHT POINT";
      }

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
         firstPoint = !firstPoint;
         centerPoint = false;
         return true;
      } else if (keycode == 31) {
         centerPoint = !centerPoint;
         return true;
      } else {
         if (Game.iActiveProvince >= 0) {
            if (ProvinceNamesManager.provinceNames.get(Game.iActiveProvince) != null) {
               if (keycode == 46) {
                  if (Game.settingsManager.SETTINGS_PROVINCE_NAMES == 3) {
                     Game.settingsManager.SETTINGS_PROVINCE_NAMES = 2;
                  } else {
                     Game.settingsManager.SETTINGS_PROVINCE_NAMES = 3;
                  }

                  Game.addSimpleTask(new Game.SimpleTask("updateDrawProvinceNames") {
                     @Override
                     public void update() {
                        ProvinceNamesManager.updateDrawProvinceNames();
                        Game.mapCities.updateCitiesInGame();
                     }
                  });
               }

               if (centerPoint) {
                  if (keycode == 21) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterX += -1.0F;
                  }

                  if (keycode == 22) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterX++;
                  }

                  if (keycode == 19) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterY += -1.0F;
                  }

                  if (keycode == 20) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterY++;
                  }

                  if (AA_KeyManager.CTRL_HOLD) {
                     if (keycode == 29) {
                        ProvinceNameData var7 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var7.fCenterX = var7.fCenterX + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var8 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var8.fCenterX = var8.fCenterX + 15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var9 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var9.fCenterY = var9.fCenterY + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var10 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var10.fCenterY = var10.fCenterY + 15 * Game.mapBG.iMapScale;
                     }
                  } else {
                     if (keycode == 29) {
                        ProvinceNameData var11 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var11.fCenterX = var11.fCenterX + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var12 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var12.fCenterX = var12.fCenterX + 3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var13 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var13.fCenterY = var13.fCenterY + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var14 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var14.fCenterY = var14.fCenterY + 3 * Game.mapBG.iMapScale;
                     }
                  }
               } else if (firstPoint) {
                  if (keycode == 21) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX += -1.0F;
                  }

                  if (keycode == 22) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX++;
                  }

                  if (keycode == 19) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY += -1.0F;
                  }

                  if (keycode == 20) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY++;
                  }

                  if (AA_KeyManager.CTRL_HOLD) {
                     if (keycode == 29) {
                        ProvinceNameData var19 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var19.fX = var19.fX + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var20 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var20.fX = var20.fX + 15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var21 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var21.fY = var21.fY + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var22 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var22.fY = var22.fY + 15 * Game.mapBG.iMapScale;
                     }
                  } else {
                     if (keycode == 29) {
                        ProvinceNameData var23 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var23.fX = var23.fX + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var24 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var24.fX = var24.fX + 3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var25 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var25.fY = var25.fY + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var26 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var26.fY = var26.fY + 3 * Game.mapBG.iMapScale;
                     }
                  }
               } else {
                  if (keycode == 21) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2 += -1.0F;
                  }

                  if (keycode == 22) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2++;
                  }

                  if (keycode == 19) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY2 += -1.0F;
                  }

                  if (keycode == 20) {
                     ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY2++;
                  }

                  if (AA_KeyManager.CTRL_HOLD) {
                     if (keycode == 29) {
                        ProvinceNameData var31 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var31.fX2 = var31.fX2 + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var32 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var32.fX2 = var32.fX2 + 15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var33 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var33.fY2 = var33.fY2 + -15 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var34 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var34.fY2 = var34.fY2 + 15 * Game.mapBG.iMapScale;
                     }
                  } else {
                     if (keycode == 29) {
                        ProvinceNameData var35 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var35.fX2 = var35.fX2 + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 32) {
                        ProvinceNameData var36 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var36.fX2 = var36.fX2 + 3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 51) {
                        ProvinceNameData var37 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var37.fY2 = var37.fY2 + -3 * Game.mapBG.iMapScale;
                     }

                     if (keycode == 47) {
                        ProvinceNameData var38 = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince);
                        var38.fY2 = var38.fY2 + 3 * Game.mapBG.iMapScale;
                     }
                  }
               }

               if (keycode == 44) {
                  if (ProvinceNamesManager.provinceNames.get(Game.iActiveProvince) == null) {
                     return true;
                  }

                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX = Game.getProvince(Game.iActiveProvince).getCenterX();
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2 = Game.getProvince(Game.iActiveProvince).getCenterX();
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY = Game.getProvince(Game.iActiveProvince).getCenterY();
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY2 = Game.getProvince(Game.iActiveProvince).getCenterY();
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterX = Game.getProvince(Game.iActiveProvince).getCenterX();
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fCenterY = Game.getProvince(Game.iActiveProvince).getCenterY();
               }

               if (ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2 < ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX) {
                  float tSw = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX;
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2;
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fX2 = tSw;
                  tSw = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY;
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY = ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY2;
                  ProvinceNamesManager.provinceNames.get(Game.iActiveProvince).fY2 = tSw;
                  firstPoint = !firstPoint;
               }

               ProvinceNamesManager.clearProvNameData(Game.iActiveProvince);
               ProvinceNamesManager.buildProvNameData(Game.iActiveProvince, false);
               return true;
            }

            ProvinceNameData newProvinceName = new ProvinceNameData();
            newProvinceName.fCenterX = Game.getProvince(Game.iActiveProvince).getCenterX();
            newProvinceName.fCenterY = Game.getProvince(Game.iActiveProvince).getCenterY();
            newProvinceName.fX = Game.getProvince(Game.iActiveProvince).getMinX();
            newProvinceName.fY = Game.getProvince(Game.iActiveProvince).getCenterY();
            newProvinceName.fX2 = Game.getProvince(Game.iActiveProvince).getMaxX();
            newProvinceName.fY2 = Game.getProvince(Game.iActiveProvince).getCenterY();
            ProvinceNamesManager.provinceNames.set(Game.iActiveProvince, newProvinceName);
            ProvinceNamesManager.clearProvNameData(Game.iActiveProvince);
            ProvinceNamesManager.buildProvNameData(Game.iActiveProvince, false);
         }

         return false;
      }
   }
}
