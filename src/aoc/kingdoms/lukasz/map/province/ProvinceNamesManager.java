package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvinceNames;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegion;
import aoc.kingdoms.lukasz.menusMapEditor.EditorMapProvinceNamePoints;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class ProvinceNamesManager {
   public static final int FONT_ID = 0;
   public static int NULL_INDICATOR = 666;
   public static List<ProvinceNameData> provinceNames = new ArrayList<>();
   public static ProvinceNamesManager.DrawProvinceNames drawProvinceNames = new ProvinceNamesManager.DrawProvinceNames() {
      @Override
      public void drawProvNames(SpriteBatch oSB) {
      }
   };

   public static final void buildProvNamePoints(int i) {
      ProvinceNameData nameData = new ProvinceNameData();
      float maxWidth = 0.0F;
      List<Boolean> was = new ArrayList<>();

      for (int j = 0; j < Game.getProvince(i).getPointsSize(); j++) {
         was.add(false);
      }

      int checkedWidthNum = 0;

      while (checkedWidthNum < Game.getProvince(i).getPointsSize() - 2 && checkedWidthNum < 299) {
         int iID = 0;
         int jID = 1;
         checkedWidthNum += 2;
         maxWidth = 0.0F;

         for (int j = 0; j < Game.getProvince(i).getPointsSize() - 1; j++) {
            if (!was.get(j)) {
               for (int k = j + 1; k < Game.getProvince(i).getPointsSize(); k++) {
                  if (!was.get(k)) {
                     float tWidth = (float)Math.ceil(
                        Math.sqrt(
                           (Game.getProvince(i).getPointsX(k) - Game.getProvince(i).getPointsX(j))
                                 * (Game.getProvince(i).getPointsX(k) - Game.getProvince(i).getPointsX(j))
                              + (Game.getProvince(i).getPointsY(j) - Game.getProvince(i).getPointsY(k))
                                 * (Game.getProvince(i).getPointsY(j) - Game.getProvince(i).getPointsY(k))
                        )
                     );
                     if (tWidth > maxWidth) {
                        maxWidth = tWidth;
                        nameData.fX = Game.getProvince(i).getPointsX(j);
                        nameData.fX2 = Game.getProvince(i).getPointsX(k);
                        nameData.fY = Game.getProvince(i).getPointsY(j);
                        nameData.fY2 = Game.getProvince(i).getPointsY(k);
                        nameData.fCenterX = Game.getProvince(i).getCenterX();
                        nameData.fCenterY = Game.getProvince(i).getCenterY();
                        iID = j;
                        jID = k;
                     }
                  }
               }
            }
         }

         was.set(iID, true);
         was.set(jID, true);
         if (nameData.fX2 < nameData.fX) {
            float tSw = nameData.fX;
            nameData.fX = nameData.fX2;
            nameData.fX2 = tSw;
            tSw = nameData.fY;
            nameData.fY = nameData.fY2;
            nameData.fY2 = tSw;
         }

         float tfX = nameData.fX + (Game.getProvince(i).getCenterX() - nameData.fX) * 0.4F;
         float tfY = nameData.fY + (Game.getProvince(i).getCenterY() - nameData.fY) * 0.4F;
         float tfX2 = nameData.fX2 + (Game.getProvince(i).getCenterX() - nameData.fX2) * 0.4F;
         float tfY2 = nameData.fY2 + (Game.getProvince(i).getCenterY() - nameData.fY2) * 0.4F;
         int iPrecision = Game.getProvince(i).getProvinceName().length() * 4;
         Vector2[] vPoints = new Vector2[iPrecision];
         Vector2[] dataSet = new Vector2[]{
            new Vector2(tfX, tfY),
            new Vector2(tfX, tfY),
            new Vector2(Game.getProvince(i).getCenterX(), Game.getProvince(i).getCenterY()),
            new Vector2(tfX2, tfY2),
            new Vector2(tfX2, tfY2)
         };
         boolean isInProvince = true;
         CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

         for (int jx = 0; jx < iPrecision; jx++) {
            vPoints[jx] = new Vector2();
            oCatmull.valueAt(vPoints[jx], jx / (iPrecision - 1.0F));
         }

         for (int jx = vPoints.length - 1; jx >= 0; jx--) {
            if (Game.setProvinceID_Point((int)vPoints[jx].x, (int)vPoints[jx].y) != i
               || Game.setProvinceID_Point((int)vPoints[jx].x + CFG.PADDING, (int)vPoints[jx].y) != i
               || Game.setProvinceID_Point((int)vPoints[jx].x - CFG.PADDING, (int)vPoints[jx].y) != i
               || Game.setProvinceID_Point((int)vPoints[jx].x, (int)vPoints[jx].y + CFG.PADDING) != i
               || Game.setProvinceID_Point((int)vPoints[jx].x, (int)vPoints[jx].y - CFG.PADDING) != i) {
               isInProvince = false;
               break;
            }
         }

         if (isInProvince) {
            checkedWidthNum = -1;
            break;
         }
      }

      if (checkedWidthNum > 0) {
         provinceNames.add(null);
      } else {
         provinceNames.add(nameData);
      }
   }

   public static final void buildProvNameData() {
      boolean saveData = false;
      int i = 0;

      for (int iPNamesSize = provinceNames.size(); i < Game.getProvincesSize(); i++) {
         if (iPNamesSize <= i) {
            buildProvNamePoints(i);
            iPNamesSize = provinceNames.size();
            saveData = true;
         }

         buildProvNameData(i, false);
      }

      if (saveData) {
         SaveManager.saveProvinceNamesPoints();
      }
   }

   public static void clearProvNameData(int i) {
      if (provinceNames.get(i) != null) {
         provinceNames.get(i).drawPoints.clear();
         provinceNames.get(i).drawMatrix4.clear();
         provinceNames.get(i).fontScale = 1.0F;
         provinceNames.get(i).drawAngleLow = 0.0F;
      }
   }

   public static final void buildProvNameData(int i, boolean rebuild) {
      if (provinceNames.get(i) != null) {
         try {
            if (rebuild) {
               provinceNames.get(i).drawPoints.clear();
               provinceNames.get(i).drawMatrix4.clear();
               provinceNames.get(i).fontScale = 1.0F;
               provinceNames.get(0).drawAngleLow = 0.0F;
            }

            float maxWidth = CivilizationRegion.getLineWidth3(
               provinceNames.get(i).fX, provinceNames.get(i).fY, provinceNames.get(i).fX2, provinceNames.get(i).fY2
            );
            float tfX = provinceNames.get(i).fX + (provinceNames.get(i).fCenterX - provinceNames.get(i).fX) * 0.4F;
            float tfY = provinceNames.get(i).fY + (provinceNames.get(i).fCenterY - provinceNames.get(i).fY) * 0.4F;
            float tfX2 = provinceNames.get(i).fX2 + (provinceNames.get(i).fCenterX - provinceNames.get(i).fX2) * 0.4F;
            float tfY2 = provinceNames.get(i).fY2 + (provinceNames.get(i).fCenterY - provinceNames.get(i).fY2) * 0.4F;
            int iPrecision = Game.getProvince(i).getProvinceName().length() * 8;
            Vector2[] vPoints = new Vector2[iPrecision];
            Vector2[] dataSet = new Vector2[]{
               new Vector2(tfX, tfY),
               new Vector2(tfX, tfY),
               new Vector2(provinceNames.get(i).fCenterX, provinceNames.get(i).fCenterY),
               new Vector2(tfX2, tfY2),
               new Vector2(tfX2, tfY2)
            };
            CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

            for (int j = 0; j < iPrecision; j++) {
               vPoints[j] = new Vector2();
               oCatmull.valueAt(vPoints[j], j / (iPrecision - 1.0F));
            }

            float tempPrecisionWidth = 0.0F;

            for (int j = 0; j < iPrecision - 1; j++) {
               tempPrecisionWidth += CivilizationRegion.getLineWidth3(vPoints[j].x, vPoints[j].y, vPoints[j + 1].x, vPoints[j + 1].y);
            }

            float acceptableWidth = 0.0F;

            try {
               acceptableWidth = tempPrecisionWidth / (Game.getProvince(i).getProvinceName().length() - 1);
            } catch (ArithmeticException var27) {
               CFG.exceptionStack(var27);
            }

            List<Vector2> tempPoints = new ArrayList<>();
            tempPoints.add(new Vector2(vPoints[0].x, vPoints[0].y));
            float currentPointsWidth = 0.0F;
            int j = 1;

            for (int startPrecision = 0; j < Game.getProvince(i).getProvinceName().length(); j++) {
               while (startPrecision < iPrecision - 1) {
                  float tempPrecisionWidth2 = CivilizationRegion.getLineWidth3(
                     vPoints[startPrecision].x, vPoints[startPrecision].y, vPoints[startPrecision + 1].x, vPoints[startPrecision + 1].y
                  );
                  if (currentPointsWidth + tempPrecisionWidth2 >= acceptableWidth) {
                     tempPoints.add(new Vector2(vPoints[startPrecision].x, vPoints[startPrecision].y));
                     currentPointsWidth = acceptableWidth - (currentPointsWidth + tempPrecisionWidth2);
                     break;
                  }

                  currentPointsWidth += tempPrecisionWidth2;
                  startPrecision++;
               }
            }

            tempPoints.add(new Vector2(vPoints[vPoints.length - 1].x, vPoints[vPoints.length - 1].y));
            List<Float> lPointsAngle = new ArrayList<>();
            float fAngle = (float)(Math.atan2(tempPoints.get(0).y - tempPoints.get(1).y, -tempPoints.get(0).x + tempPoints.get(1).y) * 180.0 / Math.PI);
            int jx = 0;

            for (int jSize = Math.min(tempPoints.size(), Game.getProvince(i).getProvinceName().length()); jx < jSize; jx++) {
               try {
                  float tempPointsAngle;
                  if (jx < Game.getProvince(i).getProvinceName().length() - 1) {
                     tempPointsAngle = CivilizationRegion.getLinesAngle2(
                        tempPoints.get(jx).x, tempPoints.get(jx).y, tempPoints.get(jx + 1).x, tempPoints.get(jx + 1).y
                     );
                  } else {
                     tempPointsAngle = CivilizationRegion.getLinesAngle2(
                        tempPoints.get(jx - 1).x, tempPoints.get(jx - 1).y, tempPoints.get(jx).x, tempPoints.get(jx).y
                     );
                  }

                  lPointsAngle.add(tempPointsAngle);
               } catch (Exception var30) {
                  if (jx == 0) {
                     try {
                        lPointsAngle.add(
                           CivilizationRegion.getLinesAngle2(tempPoints.get(jx).x, tempPoints.get(jx).y, tempPoints.get(jx + 1).x, tempPoints.get(jx + 1).y)
                        );
                     } catch (IndexOutOfBoundsException var26) {
                        lPointsAngle.add(fAngle);
                     }
                  } else {
                     try {
                        lPointsAngle.add(
                           CivilizationRegion.getLinesAngle2(tempPoints.get(jx - 1).x, tempPoints.get(jx - 1).y, tempPoints.get(jx).x, tempPoints.get(jx).y)
                        );
                     } catch (IndexOutOfBoundsException var25) {
                        lPointsAngle.add(fAngle);
                     }
                  }
               }
            }

            float iDistance = maxWidth * 0.8F;
            GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
            synchronized (ProvinceNamesManager.class) {
               Renderer.fontBorder.get(0).getData().setScale(1.0F);
               glyphLayout.setText(Renderer.fontBorder.get(0), Game.getProvince(i).getProvinceNameUpperCase());
               int tempNumOfIterations = 0;
               float tempScale = Math.max(0.1F, iDistance / glyphLayout.width);
               if (glyphLayout.width > 0.1F) {
                  Renderer.fontBorder.get(0).getData().setScale(tempScale);
               }

               try {
                  if (iDistance > 0.0F) {
                     while (true) {
                        if (iDistance > glyphLayout.width) {
                           tempScale += 0.025F;
                           Renderer.fontBorder.get(0).getData().setScale(Math.max(0.001F, tempScale));
                           glyphLayout.setText(Renderer.fontBorder.get(0), Game.getProvince(i).getProvinceNameUpperCase());
                           if (iDistance < glyphLayout.width) {
                              provinceNames.get(i).fontScale = Math.max(1.0E-4F, tempScale - 0.0125F);
                              break;
                           }
                        } else {
                           tempScale -= 0.025F;
                           Renderer.fontBorder.get(0).getData().setScale(Math.max(0.001F, tempScale));
                           glyphLayout.setText(Renderer.fontBorder.get(0), Game.getProvince(i).getProvinceNameUpperCase());
                           if (iDistance > glyphLayout.width) {
                              provinceNames.get(i).fontScale = Math.max(1.0E-4F, tempScale + 0.0125F);
                              break;
                           }
                        }

                        if (tempNumOfIterations++ > 999) {
                           CFG.LOG("ProvinceNamesManager: tempNumOfIterations: " + tempNumOfIterations);
                           provinceNames.get(i).fontScale = 1.0E-4F;
                           break;
                        }
                     }
                  }
               } catch (Exception var28) {
                  provinceNames.get(i).fontScale = 1.0E-4F;
               }
            }

            int jxx = 0;

            for (int jSize = tempPoints.size(); jxx < jSize; jxx++) {
               provinceNames.get(i).drawPoints.add(new Point_XY((int)tempPoints.get(jxx).x, (int)tempPoints.get(jxx).y));
            }

            jxx = 0;

            for (int jSize = lPointsAngle.size(); jxx < jSize; jxx++) {
               provinceNames.get(i).drawMatrix4.add(new Matrix4().rotate(Renderer.textRotatedVector3, lPointsAngle.get(jxx)));
            }

            provinceNames.get(i).drawAngleLow = CivilizationRegion.getLinesAngle2(
               tempPoints.get(0).x, tempPoints.get(0).y, tempPoints.get(tempPoints.size() - 1).x, tempPoints.get(tempPoints.size() - 1).y
            );

            for (int a = provinceNames.get(i).drawMatrix4.size(); a < Game.getProvince(i).getProvinceNameUpperCase().length(); a++) {
               provinceNames.get(i).drawMatrix4.add(new Matrix4().rotate(Renderer.textRotatedVector3, 0.0F));
            }

            provinceNames.get(i).drawMatrix4.add(new Matrix4().rotate(Renderer.textRotatedVector3, provinceNames.get(i).drawAngleLow));
         } catch (Exception var31) {
         }
      }
   }

   public static final void updateDrawProvinceNames() {
      if (Game.settingsManager.SETTINGS_PROVINCE_NAMES > 1) {
         if (Game.settingsManager.SETTINGS_PROVINCE_NAMES == 2) {
            drawProvinceNames = new ProvinceNamesManager.DrawProvinceNames() {
               @Override
               public void drawProvNames(SpriteBatch oSB) {
                  if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
                     if (Game.settingsManager.FBO_PROVINCE_NAMES) {
                        ProvinceNamesManager.drawProvNames_Just_Medium(oSB);
                     } else {
                        ProvinceNamesManager.drawProvNames_Just_Medium_Default(oSB);
                     }
                  } else if (ProvinceDrawArmy.drawCitiesHideAnimation) {
                     ProvinceNamesManager.drawProvNames_Just_Medium_Default(oSB);
                  }
               }
            };
         } else {
            drawProvinceNames = new ProvinceNamesManager.DrawProvinceNames() {
               @Override
               public void drawProvNames(SpriteBatch oSB) {
                  if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
                     if (Game.settingsManager.FBO_PROVINCE_NAMES) {
                        ProvinceNamesManager.drawProvNames_Just(oSB);
                     } else {
                        ProvinceNamesManager.drawProvNames_Just_Default(oSB);
                     }
                  } else if (ProvinceDrawArmy.drawCitiesHideAnimation) {
                     ProvinceNamesManager.drawProvNames_Just_Default(oSB);
                  }
               }
            };
         }
      } else {
         drawProvinceNames = new ProvinceNamesManager.DrawProvinceNames() {
            @Override
            public void drawProvNames(SpriteBatch oSB) {
            }
         };
      }
   }

   public static final void drawProvNames(SpriteBatch oSB) {
      try {
         drawProvinceNames.drawProvNames(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final synchronized void drawProvNames_Just_Default(SpriteBatch oSB) {
      Matrix4 oldTransformMatrix = oSB.getTransformMatrix().cpy();
      Renderer.fontBorder.get(0).setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_NAMES_ALPHA * ProvinceDrawArmy.DRAW_PROVINCE_NAMES_ALPHA));
      drawProvNames_Just_Default_Inner(oSB);
      oSB.setTransformMatrix(oldTransformMatrix);
   }

   public static final void drawProvNames_Just_Default_Inner(SpriteBatch oSB) {
      try {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            drawProvName(oSB, Game.getProvinceInViewID(i), 0);
         }

         for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
            drawProvName(oSB, Game.getExtraProvinceInViewID(i), Game.mapBG.getWidth());
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final synchronized void drawProvNames_Just(SpriteBatch oSB) {
      try {
         if (FBOProvinceNames.textureProvince_Names != null) {
            if (FBOProvinceNames.lastPosX == Game.mapCoords.getPosX() && FBOProvinceNames.lastPosY == Game.mapCoords.getPosY()) {
               FBOProvinceNames.drawProvinceNames(oSB);
            } else {
               FBOProvinceNames.disposeProvinceNamesTexture();
               FBOProvinceNames.disposeProvinceNamesFBO();
               drawProvNames_Just_Default(oSB);
            }
         } else {
            FBOProvinceNames.updateFBO();
            if (FBOProvinceNames.fboNumToGenerate_Names >= GameValues.value.FBO_NUM_TO_GENERATE_NAMES) {
               FBOProvinceNames.fboNumToGenerate_Names = 0;
               oSB.end();
               FBOProvinceNames.disposeProvinceNamesFBO();
               FBOProvinceNames.disposeProvinceNamesTexture();
               FBOProvinceNames.fboProvince_Names = new FrameBuffer(Format.RGBA8888, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, false);
               FBOProvinceNames.fboProvince_Names.begin();
               oSB.begin();
               Matrix4 oldTransformMatrix = oSB.getTransformMatrix().cpy();
               Renderer.fontBorder.get(0).setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
               drawProvNames_Just_Default_Inner(oSB);
               oSB.setTransformMatrix(oldTransformMatrix);
               oSB.end();
               FBOProvinceNames.fboProvince_Names.end();
               FBOProvinceNames.textureProvince_Names = FBOProvinceNames.fboProvince_Names.getColorBufferTexture();
               FBOProvinceNames.fboPosX = Game.mapCoords.getPosX();
               FBOProvinceNames.fboPosY = Game.mapCoords.getPosY();
               oSB.begin();
               if (FBOProvinceNames.textureProvince_Names != null) {
                  FBOProvinceNames.drawProvinceNames(oSB);
               }
            } else {
               drawProvNames_Just_Default(oSB);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final synchronized void drawProvNames_Just_Medium_Default(SpriteBatch oSB) {
      Matrix4 oldTransformMatrix = oSB.getTransformMatrix().cpy();
      Renderer.fontBorder.get(0).setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_NAMES_ALPHA * ProvinceDrawArmy.DRAW_PROVINCE_NAMES_ALPHA));
      drawProvNames_Just_Medium_Default_Inner(oSB);
      oSB.setTransformMatrix(oldTransformMatrix);
   }

   public static final void drawProvNames_Just_Medium_Default_Inner(SpriteBatch oSB) {
      try {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            drawProvName_Medium(oSB, Game.getProvinceInViewID(i), 0);
         }

         for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
            drawProvName_Medium(oSB, Game.getExtraProvinceInViewID(i), Game.mapBG.getWidth());
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final synchronized void drawProvNames_Just_Medium(SpriteBatch oSB) {
      try {
         if (FBOProvinceNames.textureProvince_Names != null) {
            if (FBOProvinceNames.lastPosX == Game.mapCoords.getPosX() && FBOProvinceNames.lastPosY == Game.mapCoords.getPosY()) {
               FBOProvinceNames.drawProvinceNames(oSB);
            } else {
               FBOProvinceNames.disposeProvinceNamesTexture();
               FBOProvinceNames.disposeProvinceNamesFBO();
               drawProvNames_Just_Medium_Default(oSB);
            }
         } else {
            FBOProvinceNames.updateFBO();
            if (FBOProvinceNames.fboNumToGenerate_Names >= GameValues.value.FBO_NUM_TO_GENERATE_NAMES) {
               FBOProvinceNames.fboNumToGenerate_Names = 0;
               oSB.end();
               FBOProvinceNames.disposeProvinceNamesFBO();
               FBOProvinceNames.disposeProvinceNamesTexture();
               FBOProvinceNames.fboProvince_Names = new FrameBuffer(Format.RGBA8888, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, false);
               FBOProvinceNames.fboProvince_Names.begin();
               oSB.begin();
               Matrix4 oldTransformMatrix = oSB.getTransformMatrix().cpy();
               Renderer.fontBorder.get(0).setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
               drawProvNames_Just_Medium_Default_Inner(oSB);
               oSB.setTransformMatrix(oldTransformMatrix);
               oSB.end();
               FBOProvinceNames.fboProvince_Names.end();
               FBOProvinceNames.textureProvince_Names = FBOProvinceNames.fboProvince_Names.getColorBufferTexture();
               FBOProvinceNames.fboPosX = Game.mapCoords.getPosX();
               FBOProvinceNames.fboPosY = Game.mapCoords.getPosY();
               oSB.begin();
               if (FBOProvinceNames.textureProvince_Names != null) {
                  FBOProvinceNames.drawProvinceNames(oSB);
               }
            } else {
               drawProvNames_Just_Medium_Default(oSB);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final synchronized void drawProvName_Old(SpriteBatch oSB, int i, int extraX) {
      try {
         if (!Game.getProvince(i).getSeaProvince() && provinceNames.get(i) != null) {
            Renderer.fontBorder.get(0).getData().setScale(provinceNames.get(i).fontScale * Game.mapScale.getCurrentScale());
            extraX = Game.getProvince(i).getTranslateProvincePosX() + extraX;

            for (int j = Game.getProvince(i).iProvinceNameLength_Minus1; j >= 0; j--) {
               try {
                  Renderer.drawTextRotatedBorder(
                     oSB,
                     0,
                     "" + Game.getProvince(i).getProvinceNameUpperCase().charAt(j),
                     (int)((extraX + provinceNames.get(i).drawPoints.get(j).getPosX()) * Game.mapScale.getCurrentScale()),
                     (int)((Game.mapCoords.getPosY() + provinceNames.get(i).drawPoints.get(j).getPosY()) * Game.mapScale.getCurrentScale()),
                     provinceNames.get(i).drawMatrix4.get(j)
                  );
               } catch (Exception var5) {
               }
            }
         }
      } catch (Exception var6) {
      }
   }

   public static final synchronized void drawProvName(SpriteBatch oSB, int i, int extraX) {
      if (provinceNames.get(i) != null) {
         try {
            ProvinceNameData provinceName = provinceNames.get(i);
            float fontScale = provinceName.fontScale * Game.mapScale.getCurrentScale();
            if (fontScale > Game.settingsManager.PROVINCE_NAMES_SCALE) {
               Renderer.fontBorder.get(0).getData().setScale(fontScale);
               extraX = Game.getProvince(i).getTranslateProvincePosX() + extraX;

               for (int j = Game.getProvince(i).iProvinceNameLength_Minus1; j >= 0; j--) {
                  Renderer.drawTextRotatedBorder(
                     oSB,
                     0,
                     String.valueOf(Game.getProvince(i).getProvinceNameUpperCase().charAt(j)),
                     (int)((extraX + provinceName.drawPoints.get(j).getPosX()) * Game.mapScale.getCurrentScale()),
                     (int)((Game.mapCoords.getPosY() + provinceName.drawPoints.get(j).getPosY()) * Game.mapScale.getCurrentScale()),
                     provinceName.drawMatrix4.get(j)
                  );
               }
            }
         } catch (Exception var6) {
         }
      }
   }

   public static final synchronized void drawProvName_Medium(SpriteBatch oSB, int i, int extraX) {
      if (provinceNames.get(i) != null) {
         float fontScale = provinceNames.get(i).fontScale * Game.mapScale.getCurrentScale();
         if (fontScale > Game.settingsManager.PROVINCE_NAMES_SCALE) {
            Renderer.fontBorder.get(0).getData().setScale(fontScale);
            Renderer.drawTextRotatedBorder_2(
               oSB,
               0,
               "" + Game.getProvince(i).getProvinceNameUpperCase(),
               (int)(
                  (Game.getProvince(i).getTranslateProvincePosX() + extraX + provinceNames.get(i).drawPoints.get(0).getPosX())
                     * Game.mapScale.getCurrentScale()
               ),
               (int)((Game.mapCoords.getPosY() + provinceNames.get(i).drawPoints.get(0).getPosY()) * Game.mapScale.getCurrentScale()),
               provinceNames.get(i).drawAngleLow
            );
         }
      }
   }

   public static final void drawProvNamePoints(SpriteBatch oSB, int i) {
      if (provinceNames.get(i) != null) {
         if (EditorMapProvinceNamePoints.firstPoint && !EditorMapProvinceNamePoints.centerPoint) {
            oSB.setColor(Color.RED);
         }

         Images.pix
            .draw(oSB, (int)(Game.mapCoords.getPosX() + provinceNames.get(i).fX) - 1, (int)(Game.mapCoords.getPosY() + provinceNames.get(i).fY) - 1, 3, 3);
         oSB.setColor(Color.WHITE);
         if (EditorMapProvinceNamePoints.centerPoint) {
            oSB.setColor(Color.RED);
         }

         Images.pix
            .draw(
               oSB,
               (int)(Game.mapCoords.getPosX() + provinceNames.get(i).fCenterX) - 1,
               (int)(Game.mapCoords.getPosY() + provinceNames.get(i).fCenterY) - 1,
               3,
               3
            );
         oSB.setColor(Color.WHITE);
         if (!EditorMapProvinceNamePoints.firstPoint && !EditorMapProvinceNamePoints.centerPoint) {
            oSB.setColor(Color.RED);
         }

         Images.pix
            .draw(oSB, (int)(Game.mapCoords.getPosX() + provinceNames.get(i).fX2) - 1, (int)(Game.mapCoords.getPosY() + provinceNames.get(i).fY2) - 1, 3, 3);
         oSB.setColor(Color.WHITE);
      }
   }

   public interface DrawProvinceNames {
      void drawProvNames(SpriteBatch var1);
   }
}
