package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvincesBG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.setting.SettingsProvince;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.map.MapModeManager;
import aoc.kingdoms.lukasz.map.map.MapScale;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.moveUnits.other.MoveUnits_BiggestCities;
import aoc.kingdoms.lukasz.map.moveUnits.other.MoveUnits_BiggestCities_Diplomacy;
import aoc.kingdoms.lukasz.map.moveUnits.other.MoveUnits_BiggestCities_Siege;
import aoc.kingdoms.lukasz.map.province.animation.ProvinceAnimationDot;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menus.NewGame.NewGame_AddCiv;
import aoc.kingdoms.lukasz.menus.Settings.Settings_Menu;
import aoc.kingdoms.lukasz.menusEditor.CreateCiv;
import aoc.kingdoms.lukasz.menusEditor.GameCivsEdit;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.menusInGame.InGame_ReleaseAVassal;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Diplomacy.InGame_DeclareWar;
import aoc.kingdoms.lukasz.menusInGame.Diplomacy.InGame_FormCiv;
import aoc.kingdoms.lukasz.menusMapEditor.EditorMapOptimizationRegions;
import aoc.kingdoms.lukasz.menusMapEditor.EditorMap_FormableCiv;
import aoc.kingdoms.lukasz.menusMapEditor.EditorSelectProvinces;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioCreateAllianceList;
import aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy.ScenarioRelationsList;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import space.earlygrey.shapedrawer.JoinType;

public class ProvinceDraw {
   public static ProvinceDraw.DrawProvinces drawProvinces;
   public static final float PROVINCE_ALPHA_TERRAIN = 0.55F;
   public static ProvinceDraw.DrawExtraDetails drawExtraDetails = new ProvinceDraw.DrawExtraDetails();
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 1.0F);
   public static Color progressBar = new Color(0.29411766F, 0.49019608F, 0.64705884F, 1.0F);
   public static Color progressBar2 = new Color(0.3529412F, 0.64705884F, 0.8039216F, 1.0F);
   public static int drawProvincesCiv_HoveredFlagID = 0;
   public static Color PROVINCE_DIPLOMACY_NEUTRAL = new Color(0.8235294F, 0.8235294F, 0.78431374F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_NEUTRAL_GREEN = new Color(0.9019608F, 0.9019608F, 0.8235294F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_RED = new Color(0.92941177F, 0.627451F, 0.5882353F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_RED2 = new Color(0.627451F, 0.19607843F, 0.19607843F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_GREEN = new Color(0.5176471F, 0.74509805F, 0.43137255F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_GREEN2 = new Color(0.0F, 0.4F, 0.0F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_ALLIANCE = new Color(0.0F, 0.627451F, 0.92156863F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_AT_WAR = new Color(0.8039216F, 0.0F, 0.0F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_VASSAL = new Color(0.21568628F, 0.9529412F, 0.8745098F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_PACT = new Color(0.8F, 0.8F, 0.0F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_INDEPENDENCE = new Color(0.74509805F, 0.23529412F, 0.627451F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_INDEPENDENCE2 = new Color(0.5882353F, 0.15686275F, 0.47058824F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_MILITARY_ACCESS = new Color(0.08627451F, 0.08627451F, 0.5411765F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_DEFENSIVE_PACT = new Color(0.5019608F, 0.23921569F, 0.7490196F, 1.0F);
   public static Color PROVINCE_DIPLOMACY_TRUCE = new Color(0.92156863F, 0.92156863F, 0.92156863F, 1.0F);
   public static JoinType joinType = JoinType.POINTY;
   public static JoinType joinType_Shadow = JoinType.SMOOTH;
   public static float lineWidth = 1.0F;
   public static ProvinceDraw.DrawMoveUnits oDrawMoveUnits = new ProvinceDraw.DrawMoveUnits() {
      @Override
      public void drawMoveUnits(SpriteBatch oSB) {
      }
   };
   public static int FRAME_ID = 0;
   public static List<MoveUnits_BiggestCities_Siege> siegeLines = new ArrayList<>();
   public static int iSiegeLinesSize = 0;
   public static List<MoveUnits_BiggestCities> biggestCitiesLines = new ArrayList<>();
   public static int iBiggestCitiesLinesSize = 0;
   public static List<MoveUnits_BiggestCities> diplomacyLines = new ArrayList<>();
   public static int iDiplomacyLinesSize = 0;
   public static List<ProvinceAnimationDot> provinceDots = new ArrayList<>();
   public static int iProvinceDotsSize = 0;

   public static void updateDrawExtraDetails() {
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY) {
         drawExtraDetails = new ProvinceDraw.DrawExtraDetails() {
            @Override
            public void draw(SpriteBatch oSB) {
               try {
                  if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
                     for (int i = 0; i < Game.getCiv(Game.player.iCivID).getArmyRecruitSize(); i++) {
                        for (int j = 0; j < Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).size(); j++) {
                           if (Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).getDrawProvince()) {
                              oSB.setShader(Renderer.shaderAlpha);
                              ArmyManager.armyImages
                                 .get(
                                    ArmyManager.lArmy
                                          .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).unitID)
                                          .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).armyID)
                                       .ImageID
                                 )
                                 .getTexture()
                                 .bind(1);
                              Gdx.gl.glActiveTexture(33984);
                              ImageManager.getImage(Images.unitsFrameMapMask)
                                 .draw(
                                    oSB,
                                    (int)(
                                       (
                                                Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID)
                                                      .getTranslateProvincePosX()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftX
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                                    ),
                                    (int)(
                                       (
                                                Game.mapCoords.getPosY()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftY
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ProvinceDrawArmy.getArmyHeight() / 2
                                          - ImageManager.getImage(Images.unitsFrameMap).getHeight() * (j + 1)
                                          - 1 * (j + 1)
                                    )
                                 );
                              oSB.flush();
                              oSB.setShader(Renderer.shaderDefault);
                              ImageManager.getImage(Images.unitsFrameMap)
                                 .draw(
                                    oSB,
                                    (int)(
                                       (
                                                Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID)
                                                      .getTranslateProvincePosX()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftX
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                                    ),
                                    (int)(
                                       (
                                                Game.mapCoords.getPosY()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftY
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ProvinceDrawArmy.getArmyHeight() / 2
                                          - ImageManager.getImage(Images.unitsFrameMap).getHeight() * (j + 1)
                                          - 1 * (j + 1)
                                    )
                                 );
                              int tCenterX = (
                                    ImageManager.getImage(Images.progressBarFrame).getWidth() - ImageManager.getImage(Images.progressBarFrameMask).getWidth()
                                 )
                                 / 2;
                              int tCenterY = (
                                    ImageManager.getImage(Images.progressBarFrame).getHeight() - ImageManager.getImage(Images.progressBarFrameMask).getHeight()
                                 )
                                 / 2;
                              oSB.setColor(ProvinceDraw.progressBarBG);
                              ImageManager.getImage(Images.progressBarFrameMask)
                                 .draw(
                                    oSB,
                                    tCenterX
                                       + (int)(
                                          (
                                                   Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID)
                                                         .getTranslateProvincePosX()
                                                      + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftX
                                                )
                                                * Game.mapScale.getCurrentScale()
                                             - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                                       ),
                                    tCenterY
                                       + (int)(
                                          (
                                                   Game.mapCoords.getPosY()
                                                      + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftY
                                                )
                                                * Game.mapScale.getCurrentScale()
                                             - ProvinceDrawArmy.getArmyHeight() / 2
                                             - ImageManager.getImage(Images.unitsFrameMap).getHeight() * (j + 1)
                                             - 1 * (j + 1)
                                             + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                                             - ImageManager.getImage(Images.progressBarFrame).getHeight()
                                       )
                                 );
                              oSB.setColor(ProvinceDraw.progressBar);
                              ImageManager.getImage(Images.progressBarFrameMask)
                                 .draw(
                                    oSB,
                                    tCenterX
                                       + (int)(
                                          (
                                                   Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID)
                                                         .getTranslateProvincePosX()
                                                      + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftX
                                                )
                                                * Game.mapScale.getCurrentScale()
                                             - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                                       ),
                                    tCenterY
                                       + (int)(
                                          (
                                                   Game.mapCoords.getPosY()
                                                      + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftY
                                                )
                                                * Game.mapScale.getCurrentScale()
                                             - ProvinceDrawArmy.getArmyHeight() / 2
                                             - ImageManager.getImage(Images.unitsFrameMap).getHeight() * (j + 1)
                                             - 1 * (j + 1)
                                             + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                                             - ImageManager.getImage(Images.progressBarFrame).getHeight()
                                       ),
                                    (int)(
                                       ImageManager.getImage(Images.progressBarFrameMask).getWidth()
                                          * (
                                             (float)(
                                                   ArmyManager.lArmy
                                                            .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).unitID)
                                                            .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).armyID)
                                                         .RecruitmentTime
                                                      - Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).timeLeft
                                                )
                                                / ArmyManager.lArmy
                                                      .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).unitID)
                                                      .get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).armyID)
                                                   .RecruitmentTime
                                          )
                                    ),
                                    ImageManager.getImage(Images.progressBarFrameMask).getHeight()
                                 );
                              oSB.setColor(Color.WHITE);
                              ImageManager.getImage(Images.progressBarFrame)
                                 .draw(
                                    oSB,
                                    (int)(
                                       (
                                                Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID)
                                                      .getTranslateProvincePosX()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftX
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                                    ),
                                    (int)(
                                       (
                                                Game.mapCoords.getPosY()
                                                   + Game.getProvince(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(i).get(j).provinceID).iCenterShiftY
                                             )
                                             * Game.mapScale.getCurrentScale()
                                          - ProvinceDrawArmy.getArmyHeight() / 2
                                          - ImageManager.getImage(Images.unitsFrameMap).getHeight() * (j + 1)
                                          - 1 * (j + 1)
                                          + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                                          - ImageManager.getImage(Images.progressBarFrame).getHeight()
                                    )
                                 );
                           }
                        }
                     }
                  }
               } catch (Exception var6) {
                  CFG.exceptionStack(var6);
               }
            }
         };
      } else {
         drawExtraDetails = new ProvinceDraw.DrawExtraDetails() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
                  if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                     ProvinceDraw.drawWondersConstruction(oSB);
                     ProvinceDraw.drawBuildingsInConstruction(oSB);
                     ProvinceDraw.drawRecruitingArmyPlayer(oSB);
                  } else {
                     ProvinceDraw.drawBuildingsInConstruction(oSB);
                  }
               }
            }
         };
      }
   }

   public static final void drawProvinces(SpriteBatch oSB) {
      try {
         Game.updateProvincesInView();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      oSB.setShader(Renderer.shaderDefaultProvince);

      try {
         MapModeManager.updateAlpha();
         drawProvinces.draw(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      oSB.setShader(Renderer.shaderDefault);
      oSB.setColor(Color.WHITE);
   }

   public static final void drawProvinces_Settings(SpriteBatch oSB) {
      long time = System.nanoTime();

      try {
         Game.updateProvincesInView();
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      if (Settings_Menu.updateTimes) {
         Settings_Menu.provinceInView_Time = System.nanoTime() - time;
         time = System.nanoTime();
      }

      oSB.setShader(Renderer.shaderDefaultProvince);

      try {
         MapModeManager.updateAlpha();
         drawProvinces.draw(oSB);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      oSB.setShader(Renderer.shaderDefault);
      if (Settings_Menu.updateTimes) {
         Settings_Menu.drawProvinces_Time = System.nanoTime() - time;
      }

      oSB.setColor(Color.WHITE);
   }

   public static final void updateDrawProvinces() {
      if (Game.menuManager.getInInitGameMenu() || Game.menuManager.getInInitGame_Menus() || Game.menuManager.getInLoadScenario()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
            }
         };
      } else if (Game.menuManager.getInGame()) {
         drawProvinces = Game.mapModes.lMapModes.get(Game.mapModes.iActiveMapModeID).drawProvinces;
      } else if (Game.menuManager.getInMapEditorProvinceConnections()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               ProvinceDraw.drawProvincesInMapEditor_Connections(oSB);
            }
         };
      } else if (Game.menuManager.getInEditorGameCivsEdit()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               float fAlpha = ProvinceDraw.getProvinceAlpha();

               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(new Color(GameCivsEdit.nCiv.iR / 255.0F, GameCivsEdit.nCiv.iG / 255.0F, GameCivsEdit.nCiv.iB / 255.0F, fAlpha));
                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(new Color(GameCivsEdit.nCiv.iR / 255.0F, GameCivsEdit.nCiv.iG / 255.0F, GameCivsEdit.nCiv.iB / 255.0F, fAlpha));
                  Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
               }
            }
         };
      } else if (Game.menuManager.getInEditorCreateCiv()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               float fAlpha = ProvinceDraw.getProvinceAlpha();

               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(new Color(CreateCiv.nCiv.iR / 255.0F, CreateCiv.nCiv.iG / 255.0F, CreateCiv.nCiv.iB / 255.0F, fAlpha));
                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(new Color(CreateCiv.nCiv.iR / 255.0F, CreateCiv.nCiv.iG / 255.0F, CreateCiv.nCiv.iB / 255.0F, fAlpha));
                  Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorGrowthRate()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(CFG.getGrowthRateColor((int)Game.getProvince(Game.getProvinceInViewID(i)).getGrowthRate(), 0.75F));
                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }

                  for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(CFG.getGrowthRateColor((int)Game.getProvince(Game.getExtraProvinceInViewID(i)).getGrowthRate(), 0.75F));
                     Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioCivilizations()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               try {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditor_CreateAllianceEdit()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
                     if (ScenarioCreateAllianceList.editAlliance.isInAlliance(Game.getProvince(Game.getProvinceInViewID(i)).getCivID())) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA * 1.75F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA / 3.0F);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
                     if (ScenarioCreateAllianceList.editAlliance.isInAlliance(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID())) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA * 1.75F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA / 3.0F);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInPrintMap()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               ProvinceDraw.updateDrawProvinces_Standard();
            }
         };
      } else if (Game.menuManager.getInMapEditorEconomy()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(CFG.getGrowthRateColor((int)Game.getProvince(Game.getProvinceInViewID(i)).BaseDevelopment, 0.75F));
                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }

                  for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(CFG.getGrowthRateColor((int)Game.getProvince(Game.getExtraProvinceInViewID(i)).BaseDevelopment, 0.75F));
                     Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorLines() || Game.menuManager.getInMapEditorWaves()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               ProvinceDraw.updateDrawProvinces_Standard();
            }
         };
      } else if (Game.menuManager.getInScenarioEditorAlliances()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.alliance.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.alliance.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorVassals()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.lVassals.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.lVassals.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorTruces()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.truce.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.truce.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorGuarantee()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.guarantee.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.guarantee.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorMilitaryAccess()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.militaryAccess.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.militaryAccess.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorNonAggression()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.nonAggressionPact.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.nonAggressionPact.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorDefensive()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.defensivePact.size() > 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.defensivePact.size() > 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, 1.0F);
                     } else {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorRelations()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (ScenarioRelationsList.activeCivID == 0) {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                        Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

                  for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, Game.settingsManager.PROVINCE_ALPHA);
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                     }
                  }
               } else {
                  for (int ixx = 0; ixx < Game.NUM_OF_PROVINCES_IN_VIEW; ixx++) {
                     if (Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID() != 0) {
                        if (Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID() == ScenarioRelationsList.activeCivID) {
                           oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, Game.settingsManager.PROVINCE_ALPHA));
                        } else {
                           int tempRelation = (int)Game.getCiv(ScenarioRelationsList.activeCivID)
                              .diplomacy
                              .getRelation(Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID());
                           if (tempRelation == 0) {
                              oSB.setColor(
                                 new Color(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.r,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.g,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.b,
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           } else if (tempRelation > 0) {
                              oSB.setColor(
                                 CFG.getColorStep(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_GREEN,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_GREEN2,
                                    tempRelation,
                                    (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX,
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           } else {
                              oSB.setColor(
                                 CFG.getColorStep(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_RED,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_RED2,
                                    -tempRelation,
                                    (int)(-GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN),
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           }
                        }

                        Game.getProvince(Game.getProvinceInViewID(ixx)).drawLandProvince(oSB);
                     }
                  }

                  for (int ixxx = 0; ixxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxx++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID() == 0) {
                        if (Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID() != ScenarioRelationsList.activeCivID) {
                           oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, Game.settingsManager.PROVINCE_ALPHA));
                        } else {
                           int tempRelation = (int)Game.getCiv(ScenarioRelationsList.activeCivID)
                              .diplomacy
                              .getRelation(Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID());
                           if (tempRelation == 0) {
                              oSB.setColor(
                                 new Color(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.r,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.g,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_NEUTRAL_GREEN.b,
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           } else if (tempRelation > 0) {
                              oSB.setColor(
                                 CFG.getColorStep(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_GREEN,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_GREEN2,
                                    tempRelation,
                                    (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX,
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           } else {
                              oSB.setColor(
                                 CFG.getColorStep(
                                    ProvinceDraw.PROVINCE_DIPLOMACY_RED,
                                    ProvinceDraw.PROVINCE_DIPLOMACY_RED2,
                                    -tempRelation,
                                    (int)(-GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN),
                                    Game.settingsManager.PROVINCE_ALPHA
                                 )
                              );
                           }
                        }

                        Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).drawLandProvince(oSB);
                     }
                  }
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorTerrain()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(
                        new Color(
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getProvinceInViewID(i)).getTerrainID()).Color[0],
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getProvinceInViewID(i)).getTerrainID()).Color[1],
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getProvinceInViewID(i)).getTerrainID()).Color[2],
                           0.55F
                        )
                     );
                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }

                  for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                     oSB.setColor(
                        new Color(
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getTerrainID()).Color[0],
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getTerrainID()).Color[1],
                           Game.terrainManager.terrains.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getTerrainID()).Color[2],
                           0.55F
                        )
                     );
                     Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorTechnologiesCivs()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).scenarioEditorData.TechnologyID == MapScenarios.DEFAULT_VALUE) {
                        oSB.setColor(
                           Colors.getWhiteBlackColor(
                              (int)(
                                 (float)Math.max(
                                       Math.max(0, MapScenarios.scenarioEditorDetails.CivDefault_Technology),
                                       Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).scenarioEditorData.TechnologyID
                                    )
                                    / TechnologyTree.iTechnologySize
                                    * 100.0F
                              ),
                              Game.settingsManager.PROVINCE_ALPHA
                           )
                        );
                     } else {
                        oSB.setColor(
                           Colors.getWhiteBlackColor(
                              (int)(
                                 (float)Math.max(0, Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).scenarioEditorData.TechnologyID)
                                    / TechnologyTree.iTechnologySize
                                    * 100.0F
                              ),
                              Game.settingsManager.PROVINCE_ALPHA
                           )
                        );
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ix)).getCivID() > 0) {
                     if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(ix)).getCivID()).scenarioEditorData.TechnologyID == MapScenarios.DEFAULT_VALUE) {
                        oSB.setColor(
                           Colors.getWhiteBlackColor(
                              (int)(
                                 (float)Math.max(
                                       Math.max(0, MapScenarios.scenarioEditorDetails.CivDefault_Technology),
                                       Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).scenarioEditorData.TechnologyID
                                    )
                                    / TechnologyTree.iTechnologySize
                                    * 100.0F
                              ),
                              Game.settingsManager.PROVINCE_ALPHA
                           )
                        );
                     } else {
                        oSB.setColor(
                           Colors.getWhiteBlackColor(
                              (int)(
                                 (float)Math.max(0, Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).scenarioEditorData.TechnologyID)
                                    / TechnologyTree.iTechnologySize
                                    * 100.0F
                              ),
                              Game.settingsManager.PROVINCE_ALPHA
                           )
                        );
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }
            }
         };
      } else if (Game.menuManager.getInEditorFormableCiv()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (EditorMap_FormableCiv.drawMap) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               oSB.setShader(Renderer.shaderDefaultProvince);

               try {
                  for (int i = 0; i < FormableCivManager.activeFormableCiv.Provinces.size(); i++) {
                     if (Game.getProvince(FormableCivManager.activeFormableCiv.Provinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(FormableCivManager.activeFormableCiv.Provinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }

               oSB.setShader(Renderer.shaderDefault);
            }
         };
      } else if (Game.menuManager.getInEditorSelectProvinces()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               try {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInNewGameAddCiv()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               try {
                  for (int i = 0; i < NewGame_AddCiv.addNewCivData.lProvinces.size(); i++) {
                     if (Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInScenarioCores()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               try {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInScenarioReligion()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  ProvinceDraw.drawProvinces_Standard(oSB);
               }

               try {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInScenarioEditorBuildings()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
                     Game.getProvince(Game.getProvinceInViewID(i))
                        .setProvinceColor(oSB, Game.getProvince(Game.getProvinceInViewID(i)).iBuildingsSize > 0 ? 1.0F : Game.settingsManager.PROVINCE_ALPHA);
                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
                     Game.getProvince(Game.getExtraProvinceInViewID(ix))
                        .setProvinceColor(oSB, Game.getProvince(Game.getProvinceInViewID(ix)).iBuildingsSize > 0 ? 1.0F : Game.settingsManager.PROVINCE_ALPHA);
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                  }
               }

               try {
                  for (int ixx = 0; ixx < EditorSelectProvinces.selectedProvinces.size(); ixx++) {
                     if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(ixx)).getDrawProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA));
                        Game.getProvince(EditorSelectProvinces.selectedProvinces.get(ixx)).drawLandProvince(oSB);
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorResource()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               if (MenuManager.mapEditorDrawProvinces) {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     if (Game.getProvince(Game.getProvinceInViewID(i)).getResourceID() >= 0) {
                        oSB.setColor(
                           new Color(
                              ResourcesManager.lResources.get(Game.getProvince(Game.getProvinceInViewID(i)).getResourceID()).Color[0],
                              ResourcesManager.lResources.get(Game.getProvince(Game.getProvinceInViewID(i)).getResourceID()).Color[1],
                              ResourcesManager.lResources.get(Game.getProvince(Game.getProvinceInViewID(i)).getResourceID()).Color[2],
                              0.55F
                           )
                        );
                        Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

                  for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getResourceID() >= 0) {
                        oSB.setColor(
                           new Color(
                              ResourcesManager.lResources.get(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getResourceID()).Color[0],
                              ResourcesManager.lResources.get(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getResourceID()).Color[1],
                              ResourcesManager.lResources.get(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getResourceID()).Color[2],
                              0.55F
                           )
                        );
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
                     }
                  }
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorContinents()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        Game.continents.lContinents.get(Game.getProvince(Game.getProvinceInViewID(i)).getContinent()).iR / 255.0F,
                        Game.continents.lContinents.get(Game.getProvince(Game.getProvinceInViewID(i)).getContinent()).iG / 255.0F,
                        Game.continents.lContinents.get(Game.getProvince(Game.getProvinceInViewID(i)).getContinent()).iB / 255.0F,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        Game.continents.lContinents.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getContinent()).iR / 255.0F,
                        Game.continents.lContinents.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getContinent()).iG / 255.0F,
                        Game.continents.lContinents.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getContinent()).iB / 255.0F,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorGeoRegions()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getProvinceInViewID(i)).getGeoRegion()).iR / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getProvinceInViewID(i)).getGeoRegion()).iG / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getProvinceInViewID(i)).getGeoRegion()).iB / 255.0F,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getGeoRegion()).iR / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getGeoRegion()).iG / 255.0F,
                        Game.geographicalRegions.lGeographicalRegions.get(Game.getProvince(Game.getExtraProvinceInViewID(i)).getGeoRegion()).iB / 255.0F,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorOptimizationRegions()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getProvinceInViewID(i))).r,
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getProvinceInViewID(i))).g,
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getProvinceInViewID(i))).b,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                  oSB.setColor(
                     new Color(
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getExtraProvinceInViewID(i))).r,
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getExtraProvinceInViewID(i))).g,
                        EditorMapOptimizationRegions.lColors.get(Game.regions.getRegionID(Game.getExtraProvinceInViewID(i))).b,
                        0.55F
                     )
                  );
                  Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
               }

               if (Game.iActiveProvince >= 0) {
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
                  Images.pix
                     .draw(
                        oSB,
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX() + Game.mapCoords.getPosX(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY() + Game.mapCoords.getPosY(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxX()
                           - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxY()
                           - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY()
                     );
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
                  Renderer.drawRect(
                     oSB,
                     Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX() + Game.mapCoords.getPosX(),
                     Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY() + Game.mapCoords.getPosY(),
                     Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxX()
                        - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX(),
                     Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxY()
                        - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY()
                  );
                  if (Game.map.getMapWorldMap(Game.map.getActiveMapID())) {
                     oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
                     Images.pix
                        .draw(
                           oSB,
                           Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX()
                              + Game.mapCoords.getPosX()
                              + Game.mapBG.getWidth(),
                           Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY() + Game.mapCoords.getPosY(),
                           Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxX()
                              - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX(),
                           Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxY()
                              - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY()
                        );
                     oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
                     Renderer.drawRect(
                        oSB,
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX() + Game.mapCoords.getPosX() + Game.mapBG.getWidth(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY() + Game.mapCoords.getPosY(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxX()
                           - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinX(),
                        Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMaxY()
                           - Game.regions.lRegions.get(Game.regions.getRegionID(Game.iActiveProvince)).getMinY()
                     );
                  }
               }
            }
         };
      } else if (Game.menuManager.getInMapEditorSeaProvinces()) {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               try {
                  for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                     if (Game.getProvince(Game.getProvinceInViewID(i)).getLevelOfPort() >= -1) {
                        oSB.setColor(new Color(0.1254902F, 0.2901961F, 0.043137256F, 0.6F));
                        Game.getProvince(Game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     } else if (Game.getProvince(Game.getProvinceInViewID(i)).getLevelOfPort() == -1) {
                        oSB.setColor(new Color(0.02745098F, 0.12941177F, 0.18431373F, 0.6F));
                        Game.getProvince(Game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     } else {
                        oSB.setColor(new Color(0.007843138F, 0.09411765F, 0.13725491F, 0.6F));
                        Game.getProvince(Game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     }

                     Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }

                  for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(i)).getLevelOfPort() >= -1) {
                        oSB.setColor(new Color(0.1254902F, 0.2901961F, 0.043137256F, 0.6F));
                        Game.getProvince(Game.getExtraProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     } else if (Game.getProvince(Game.getExtraProvinceInViewID(i)).getLevelOfPort() == -1) {
                        oSB.setColor(new Color(0.02745098F, 0.12941177F, 0.18431373F, 0.6F));
                        Game.getProvince(Game.getExtraProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     } else {
                        oSB.setColor(new Color(0.007843138F, 0.09411765F, 0.13725491F, 0.6F));
                        Game.getProvince(Game.getExtraProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
                     }

                     Game.getProvince(Game.getExtraProvinceInViewID(i)).drawLandProvinceExtra(oSB);
                  }
               } catch (Exception var3) {
               }
            }
         };
      } else {
         drawProvinces = new ProvinceDraw.DrawProvinces() {
            @Override
            public void draw(SpriteBatch oSB) {
               ProvinceDraw.updateDrawProvinces_Standard();
            }
         };
      }
   }

   public static final void drawRecruitingArmyPlayer(SpriteBatch oSB) {
      Civilization civPlayer = Game.getCiv(Game.player.iCivID);
      Image progressBarFrame = ImageManager.getImage(Images.progressBarFrame);
      Image progressBarFrameMask = ImageManager.getImage(Images.progressBarFrameMask);
      Image unitsFrameMap = ImageManager.getImage(Images.unitsFrameMap);

      for (int i = 0; i < civPlayer.getArmyRecruitSize(); i++) {
         for (int j = 0; j < civPlayer.lArmyRecruit.get(i).size() && j < 1; j++) {
            ArmyRecruit armyRecruit = civPlayer.lArmyRecruit.get(i).get(j);
            Province province = Game.getProvince(armyRecruit.provinceID);
            if (province.getDrawProvince()) {
               int extraY = province.iBuildingsConstructionSize > 0 ? 1 : 0;
               if (province.wonderConstruction != null) {
                  extraY++;
               }

               oSB.setShader(Renderer.shaderAlpha);
               ArmyManager.armyImages.get(ArmyManager.lArmy.get(armyRecruit.unitID).get(armyRecruit.armyID).ImageID).getTexture().bind(1);
               Gdx.gl.glActiveTexture(33984);
               ImageManager.getImage(Images.unitsFrameMapMask)
                  .draw(
                     oSB,
                     (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrameMap.getWidth() / 2),
                     (int)(
                        (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - unitsFrameMap.getHeight() * (j + extraY + 1)
                           - 1 * j
                     )
                  );
               oSB.flush();
               oSB.setShader(Renderer.shaderDefault);
               unitsFrameMap.draw(
                  oSB,
                  (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrameMap.getWidth() / 2),
                  (int)(
                     (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                        - ProvinceDrawArmy.getArmyHeight() / 2
                        - unitsFrameMap.getHeight() * (j + extraY + 1)
                        - 1 * j
                  )
               );
               int tCenterX = (progressBarFrame.getWidth() - progressBarFrameMask.getWidth()) / 2;
               int tCenterY = (progressBarFrame.getHeight() - progressBarFrameMask.getHeight()) / 2;
               oSB.setColor(new Color(progressBarBG));
               progressBarFrameMask.draw(
                  oSB,
                  tCenterX
                     + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrameMap.getWidth() / 2),
                  tCenterY
                     + (int)(
                        (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - unitsFrameMap.getHeight() * (j + extraY + 1)
                           - 1 * j
                           + unitsFrameMap.getHeight()
                           - progressBarFrame.getHeight()
                     )
               );
               oSB.setColor(progressBar);
               progressBarFrameMask.draw(
                  oSB,
                  tCenterX
                     + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrameMap.getWidth() / 2),
                  tCenterY
                     + (int)(
                        (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - unitsFrameMap.getHeight() * (j + extraY + 1)
                           - 1 * j
                           + unitsFrameMap.getHeight()
                           - progressBarFrame.getHeight()
                     ),
                  (int)Math.max(
                     1.0F,
                     progressBarFrameMask.getWidth()
                        * (
                           (float)(ArmyManager.lArmy.get(armyRecruit.unitID).get(armyRecruit.armyID).RecruitmentTime - armyRecruit.timeLeft)
                              / ArmyManager.lArmy.get(armyRecruit.unitID).get(armyRecruit.armyID).RecruitmentTime
                        )
                  ),
                  progressBarFrameMask.getHeight()
               );
               oSB.setColor(Color.WHITE);
               progressBarFrame.draw(
                  oSB,
                  (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrameMap.getWidth() / 2),
                  (int)(
                     (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                        - ProvinceDrawArmy.getArmyHeight() / 2
                        - unitsFrameMap.getHeight() * (j + extraY + 1)
                        - 1 * j
                        + unitsFrameMap.getHeight()
                        - progressBarFrame.getHeight()
                  )
               );
            }
         }
      }
   }

   public static final void drawWondersConstruction(SpriteBatch oSB) {
      try {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).wonderConstruction != null) {
               oSB.setShader(Renderer.shaderAlpha);
               WondersManager.wonderImages.get(WondersManager.wonders.get(Game.getProvince(Game.getProvinceInViewID(i)).wonderID).ImageID).getTexture().bind(1);
               Gdx.gl.glActiveTexture(33984);
               ImageManager.getImage(Images.unitsFrameMapMask)
                  .draw(
                     oSB,
                     (int)(
                        (Game.getProvince(Game.getProvinceInViewID(i)).getTranslateProvincePosX() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftX)
                              * Game.mapScale.getCurrentScale()
                           - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                     ),
                     (int)(
                        (Game.mapCoords.getPosY() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - ImageManager.getImage(Images.unitsFrameMap).getHeight()
                     )
                  );
               oSB.flush();
               oSB.setShader(Renderer.shaderDefault);
               ImageManager.getImage(Images.unitsFrameMap)
                  .draw(
                     oSB,
                     (int)(
                        (Game.getProvince(Game.getProvinceInViewID(i)).getTranslateProvincePosX() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftX)
                              * Game.mapScale.getCurrentScale()
                           - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                     ),
                     (int)(
                        (Game.mapCoords.getPosY() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - ImageManager.getImage(Images.unitsFrameMap).getHeight()
                     )
                  );
               int tCenterX = (ImageManager.getImage(Images.progressBarFrame).getWidth() - ImageManager.getImage(Images.progressBarFrameMask).getWidth()) / 2;
               int tCenterY = (ImageManager.getImage(Images.progressBarFrame).getHeight() - ImageManager.getImage(Images.progressBarFrameMask).getHeight()) / 2;
               oSB.setColor(new Color(progressBarBG));
               ImageManager.getImage(Images.progressBarFrameMask)
                  .draw(
                     oSB,
                     tCenterX
                        + (int)(
                           (
                                    Game.getProvince(Game.getProvinceInViewID(i)).getTranslateProvincePosX()
                                       + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftX
                                 )
                                 * Game.mapScale.getCurrentScale()
                              - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                        ),
                     tCenterY
                        + (int)(
                           (Game.mapCoords.getPosY() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - ImageManager.getImage(Images.unitsFrameMap).getHeight()
                              + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                              - ImageManager.getImage(Images.progressBarFrame).getHeight()
                        )
                  );
               oSB.setColor(progressBar);
               ImageManager.getImage(Images.progressBarFrameMask)
                  .draw(
                     oSB,
                     tCenterX
                        + (int)(
                           (
                                    Game.getProvince(Game.getProvinceInViewID(i)).getTranslateProvincePosX()
                                       + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftX
                                 )
                                 * Game.mapScale.getCurrentScale()
                              - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                        ),
                     tCenterY
                        + (int)(
                           (Game.mapCoords.getPosY() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - ImageManager.getImage(Images.unitsFrameMap).getHeight()
                              + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                              - ImageManager.getImage(Images.progressBarFrame).getHeight()
                        ),
                     (int)(
                        ImageManager.getImage(Images.progressBarFrameMask).getWidth()
                           * (
                              1.0F
                                 - (float)Game.getProvince(Game.getProvinceInViewID(i)).wonderConstruction.daysLeft
                                    / Game.getProvince(Game.getProvinceInViewID(i)).wonderConstruction.investTime
                           )
                     ),
                     ImageManager.getImage(Images.progressBarFrameMask).getHeight()
                  );
               oSB.setColor(Color.WHITE);
               ImageManager.getImage(Images.progressBarFrame)
                  .draw(
                     oSB,
                     (int)(
                        (Game.getProvince(Game.getProvinceInViewID(i)).getTranslateProvincePosX() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftX)
                              * Game.mapScale.getCurrentScale()
                           - ImageManager.getImage(Images.unitsFrameMap).getWidth() / 2
                     ),
                     (int)(
                        (Game.mapCoords.getPosY() + Game.getProvince(Game.getProvinceInViewID(i)).iCenterShiftY) * Game.mapScale.getCurrentScale()
                           - ProvinceDrawArmy.getArmyHeight() / 2
                           - ImageManager.getImage(Images.unitsFrameMap).getHeight()
                           + ImageManager.getImage(Images.unitsFrameMap).getHeight()
                           - ImageManager.getImage(Images.progressBarFrame).getHeight()
                     )
                  );
            }
         }
      } catch (Exception var4) {
      }
   }

   public static final void drawBuildingsInConstruction(SpriteBatch oSB) {
      try {
         if (Game.FOG_OF_WAR) {
            for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
               try {
                  Province province = Game.getProvince(Game.getProvinceInViewID(i));
                  if (province.getCivID() == Game.player.iCivID && province.iBuildingsConstructionSize > 0) {
                     int extraY = province.wonderConstruction != null ? 1 : 0;
                     Image unitsFrame = ImageManager.getImage(Images.unitsFrameMap);
                     Image progressBarFrame = ImageManager.getImage(Images.progressBarFrame);
                     Image progressBarFrameMask = ImageManager.getImage(Images.progressBarFrameMask);
                     oSB.setShader(Renderer.shaderAlpha);
                     BuildingsManager.buildingImages
                        .get(
                           BuildingsManager.buildings.get(province.getBuildingsConstruction(0).getBuilding()).ImageID[province.getBuildingsConstruction(0)
                              .getBuildingID()]
                        )
                        .getTexture()
                        .bind(1);
                     Gdx.gl.glActiveTexture(33984);
                     ImageManager.getImage(Images.unitsFrameMapMask)
                        .draw(
                           oSB,
                           (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                           (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           )
                        );
                     oSB.flush();
                     oSB.setShader(Renderer.shaderDefault);
                     unitsFrame.draw(
                        oSB,
                        (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        (int)(
                           (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - unitsFrame.getHeight()
                              - unitsFrame.getHeight() * extraY
                        )
                     );
                     int tCenterX = (progressBarFrame.getWidth() - progressBarFrameMask.getWidth()) / 2;
                     int tCenterY = (progressBarFrame.getHeight() - progressBarFrameMask.getHeight()) / 2;
                     oSB.setColor(new Color(progressBarBG));
                     progressBarFrameMask.draw(
                        oSB,
                        tCenterX
                           + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        tCenterY
                           + (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 + unitsFrame.getHeight()
                                 - progressBarFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           )
                     );
                     oSB.setColor(progressBar);
                     progressBarFrameMask.draw(
                        oSB,
                        tCenterX
                           + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        tCenterY
                           + (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 + unitsFrame.getHeight()
                                 - progressBarFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           ),
                        (int)(
                           progressBarFrameMask.getWidth()
                              * (
                                 1.0F
                                    - (float)province.getBuildingsConstruction(0).getConstructionTimeLeft()
                                       / province.getBuildingsConstruction(0).getConstructionTime()
                              )
                        ),
                        progressBarFrameMask.getHeight()
                     );
                     oSB.setColor(Color.WHITE);
                     progressBarFrame.draw(
                        oSB,
                        (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        (int)(
                           (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - unitsFrame.getHeight()
                              + unitsFrame.getHeight()
                              - progressBarFrame.getHeight()
                              - unitsFrame.getHeight() * extraY
                        )
                     );
                  }
               } catch (Exception var10) {
               }
            }
         } else {
            for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
               try {
                  Province province = Game.getProvince(Game.getProvinceInViewID(i));
                  if (province.iBuildingsConstructionSize > 0) {
                     int extraY = province.wonderConstruction != null ? 1 : 0;
                     Image unitsFrame = ImageManager.getImage(Images.unitsFrameMap);
                     Image progressBarFrame = ImageManager.getImage(Images.progressBarFrame);
                     Image progressBarFrameMask = ImageManager.getImage(Images.progressBarFrameMask);
                     oSB.setShader(Renderer.shaderAlpha);
                     BuildingsManager.buildingImages
                        .get(
                           BuildingsManager.buildings.get(province.getBuildingsConstruction(0).getBuilding()).ImageID[province.getBuildingsConstruction(0)
                              .getBuildingID()]
                        )
                        .getTexture()
                        .bind(1);
                     Gdx.gl.glActiveTexture(33984);
                     ImageManager.getImage(Images.unitsFrameMapMask)
                        .draw(
                           oSB,
                           (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                           (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           )
                        );
                     oSB.flush();
                     oSB.setShader(Renderer.shaderDefault);
                     unitsFrame.draw(
                        oSB,
                        (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        (int)(
                           (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - unitsFrame.getHeight()
                              - unitsFrame.getHeight() * extraY
                        )
                     );
                     int tCenterX = (progressBarFrame.getWidth() - progressBarFrameMask.getWidth()) / 2;
                     int tCenterY = (progressBarFrame.getHeight() - progressBarFrameMask.getHeight()) / 2;
                     oSB.setColor(new Color(progressBarBG));
                     progressBarFrameMask.draw(
                        oSB,
                        tCenterX
                           + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        tCenterY
                           + (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 + unitsFrame.getHeight()
                                 - progressBarFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           )
                     );
                     oSB.setColor(progressBar);
                     progressBarFrameMask.draw(
                        oSB,
                        tCenterX
                           + (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        tCenterY
                           + (int)(
                              (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                                 - ProvinceDrawArmy.getArmyHeight() / 2
                                 - unitsFrame.getHeight()
                                 + unitsFrame.getHeight()
                                 - progressBarFrame.getHeight()
                                 - unitsFrame.getHeight() * extraY
                           ),
                        (int)(
                           progressBarFrameMask.getWidth()
                              * (
                                 1.0F
                                    - (float)province.getBuildingsConstruction(0).getConstructionTimeLeft()
                                       / province.getBuildingsConstruction(0).getConstructionTime()
                              )
                        ),
                        progressBarFrameMask.getHeight()
                     );
                     oSB.setColor(Color.WHITE);
                     progressBarFrame.draw(
                        oSB,
                        (int)((province.getTranslateProvincePosX() + province.iCenterShiftX) * Game.mapScale.getCurrentScale() - unitsFrame.getWidth() / 2),
                        (int)(
                           (Game.mapCoords.getPosY() + province.iCenterShiftY) * Game.mapScale.getCurrentScale()
                              - ProvinceDrawArmy.getArmyHeight() / 2
                              - unitsFrame.getHeight()
                              + unitsFrame.getHeight()
                              - progressBarFrame.getHeight()
                              - unitsFrame.getHeight() * extraY
                        )
                     );
                  }
               } catch (Exception var9) {
               }
            }
         }
      } catch (Exception var11) {
      }
   }

   public static final void updateDrawProvinces_Standard() {
      drawProvinces = new ProvinceDraw.DrawProvinces() {
         @Override
         public void draw(SpriteBatch oSB) {
            ProvinceDraw.drawProvinces_Standard(oSB);
         }
      };
   }

   public static final void drawProvinces_StandardFog2(SpriteBatch oSB) {
      float fProvinceAlpha = getLandProvinces_Alpha();

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince_Fog(oSB, fProvinceAlpha);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince_Fog(oSB, fProvinceAlpha);
         }
      }
   }

   public static final void drawProvinces_Standard(SpriteBatch oSB) {
      drawProvinces_Standard_InvasionArmy(oSB);

      try {
         drawWastelandProvinces(oSB);
         drawProvinces_Standard_LandProvinces(oSB, getLandProvinces_Alpha());
         drawOccupiedProvinces(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      drawProvinces_Standard_RegroupArmy(oSB);
   }

   public static final void drawProvinces_Standard_FBO(SpriteBatch oSB) {
      drawProvinces_Standard_InvasionArmy(oSB);

      try {
         drawWastelandProvinces(oSB);
         drawProvinces_Standard_LandProvinces_FBO(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      drawProvinces_Standard_RegroupArmy(oSB);
   }

   public static final void drawProvinces_Standard_InGame(SpriteBatch oSB) {
      drawProvinces_Standard_InvasionArmy(oSB);

      try {
         drawWastelandProvinces(oSB);
         drawProvinces_Standard_LandProvinces_InGame(oSB, getLandProvinces_Alpha());
         drawOccupiedProvinces(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      drawProvinces_Standard_RegroupArmy(oSB);
   }

   public static final void drawProvinces_Standard_FBO_InGame(SpriteBatch oSB) {
      drawProvinces_Standard_InvasionArmy(oSB);

      try {
         drawWastelandProvinces(oSB);
         drawProvinces_Standard_LandProvinces_FBO(oSB);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      drawProvinces_Standard_RegroupArmy(oSB);
   }

   public static final void drawProvinces_Standard_RegroupArmy(SpriteBatch oSB) {
      try {
         if (Game.regroupArmyMode) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.selectedProvinces_Animation_Data.fAlpha / 255.0F));

            for (int i = 0; i < Game.iRegroupArmyProvincesSize; i++) {
               if (Game.getProvince(Game.regroupArmyProvinces.get(i)).getDrawProvince()) {
                  Game.getProvince(Game.regroupArmyProvinces.get(i)).drawLandProvince(oSB);
               }
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final void drawProvinces_Standard_InvasionArmy(SpriteBatch oSB) {
      if (Game.invasionArmyMode) {
         try {
            if (Game.player.playerData.invasion.invasionsSize > 0) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA / 2.0F + Game.selectedProvinces_Animation_Data.fAlpha / 255.0F));

               for (int i = 0; i < Game.player.playerData.invasion.invasionsSize; i++) {
                  int j = 0;

                  for (int jSize = Game.player.playerData.invasion.invasions.get(i).lProvinces.size(); j < jSize; j++) {
                     if (Game.getProvince(Game.player.playerData.invasion.invasions.get(i).lProvinces.get(j)).getDrawProvince()) {
                        Game.getProvince(Game.player.playerData.invasion.invasions.get(i).lProvinces.get(j)).drawLandProvince(oSB);
                     }
                  }
               }
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }

         try {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_ALPHA + Game.selectedProvinces_Animation_Data.fAlpha / 255.0F));

            for (int i = 0; i < Game.invasionArmyProvincesSize; i++) {
               if (Game.getProvince(Game.invasionArmyProvinces.get(i)).getDrawProvince()) {
                  Game.getProvince(Game.invasionArmyProvinces.get(i)).drawLandProvince(oSB);
               }
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final float getLandProvinces_Alpha() {
      return getProvinceAlpha() * MapModeManager.fAlphaAnimation;
   }

   public static final void drawProvinces_Standard_LandProvinces(SpriteBatch oSB, float fProvinceAlpha) {
      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB, fProvinceAlpha);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB, fProvinceAlpha);
         }
      }
   }

   public static final void drawProvinces_Standard_LandProvinces_InGame(SpriteBatch oSB, float fProvinceAlpha) {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() != 0) {
               Game.getProvince(Game.getProvinceInViewID(i)).fog_drawLandProvince.drawLandProvinceFog(oSB, fProvinceAlpha);
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() != 0) {
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).fog_drawLandProvince.drawLandProvinceFog(oSB, fProvinceAlpha);
            }
         }
      } else {
         drawProvinces_Standard_LandProvinces(oSB, fProvinceAlpha);
      }
   }

   public static final void drawProvinces_Standard_LandProvinces_FBO(SpriteBatch oSB) {
      try {
         if (FBOProvincesBG.textureProvince_PBG != null) {
            if (FBOProvincesBG.lastPosX != Game.mapCoords.getPosX() || FBOProvincesBG.lastPosY != Game.mapCoords.getPosY()) {
               FBOProvincesBG.disposeProvincesTexture();
               FBOProvincesBG.disposeProvincesFBO();
               drawProvinces_Standard_LandProvinces_InGame(oSB, getLandProvinces_Alpha());
            }
         } else {
            FBOProvincesBG.updateFBO();
            if (FBOProvincesBG.fboNumToGenerate_PB >= GameValues.value.FBO_NUM_TO_GENERATE_PB) {
               FBOProvincesBG.fboNumToGenerate_PB = 0;
               oSB.end();
               FBOProvincesBG.disposeProvincesFBO();
               FBOProvincesBG.disposeProvincesTexture();
               FBOProvincesBG.fboProvince_PBG = new FrameBuffer(Format.RGBA8888, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, false);
               FBOProvincesBG.textureProvince_PBG = new Texture(CFG.GAME_WIDTH, CFG.GAME_HEIGHT, Format.RGBA8888);
               FBOProvincesBG.fboProvince_PBG.begin();
               oSB.begin();
               drawProvinces_Standard_LandProvinces_InGame(oSB, 1.0F);
               oSB.end();
               FBOProvincesBG.fboProvince_PBG.end();
               FBOProvincesBG.textureProvince_PBG = FBOProvincesBG.fboProvince_PBG.getColorBufferTexture();
               FBOProvincesBG.fboPosX = Game.mapCoords.getPosX();
               FBOProvincesBG.fboPosY = Game.mapCoords.getPosY();
               oSB.begin();
               if (FBOProvincesBG.textureProvince_PBG != null) {
               }
            } else {
               drawProvinces_Standard_LandProvinces_InGame(oSB, getLandProvinces_Alpha());
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final void drawProvinces_Diplomacy(SpriteBatch oSB) {
      float fProvinceAlpha2 = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      if (InGame.ONLY_MAP_MODE) {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getPuppetOfCivID() == MapModeManager.diplomacyActiveCivID) {
               Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB, fProvinceAlpha2);
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getPuppetOfCivID() == MapModeManager.diplomacyActiveCivID) {
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB, fProvinceAlpha2);
            }
         }
      } else {
         drawWastelandProvinces(oSB);

         for (int ixx = 0; ixx < Game.NUM_OF_PROVINCES_IN_VIEW; ixx++) {
            if (Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID() > 0) {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getProvinceInViewID(ixx)).provinceColor.r,
                     Game.getProvince(Game.getProvinceInViewID(ixx)).provinceColor.g,
                     Game.getProvince(Game.getProvinceInViewID(ixx)).provinceColor.b,
                     fProvinceAlpha2 * Game.getProvince(Game.getProvinceInViewID(ixx)).provinceColor.a
                  )
               );
               Game.getProvince(Game.getProvinceInViewID(ixx)).drawLandProvince(oSB);
            }
         }

         for (int ixxx = 0; ixxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxx++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID() > 0) {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).provinceColor.r,
                     Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).provinceColor.g,
                     Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).provinceColor.b,
                     fProvinceAlpha2 * Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).provinceColor.a
                  )
               );
               Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).drawLandProvince(oSB);
            }
         }

         drawOccupiedProvinces(oSB);
      }
   }

   public static final void drawProvinces_Diplomacy_Hover(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha(Colors.PROVINCE_ALPHA_POPULATION) * MapModeManager.fAlphaAnimation;
      float fProvinceAlpha2 = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == MapModeManager.diplomacyActiveCivID) {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.r,
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.g,
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.b,
                     fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.r,
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.g,
                     Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.b,
                     fProvinceAlpha2 * Game.getProvince(Game.getProvinceInViewID(i)).provinceColor.a
                  )
               );
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == MapModeManager.diplomacyActiveCivID) {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.r,
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.g,
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.b,
                     fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(
                  new Color(
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.r,
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.g,
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.b,
                     fProvinceAlpha2 * Game.getProvince(Game.getExtraProvinceInViewID(ix)).provinceColor.a
                  )
               );
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_DiplomacyRelations(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlpha2 = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      drawWastelandProvinces(oSB);
      int nActiveCivID = InGame_Civ.iActiveCivID;

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == nActiveCivID) {
               oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
            } else if (DiplomacyManager.isAtWar(nActiveCivID, Game.getProvince(Game.getProvinceInViewID(i)).getCivID())) {
               oSB.setColor(new Color(PROVINCE_DIPLOMACY_AT_WAR.r, PROVINCE_DIPLOMACY_AT_WAR.g, PROVINCE_DIPLOMACY_AT_WAR.b, fProvinceAlpha));
            } else {
               int tempRelation = (int)Game.getCiv(nActiveCivID).diplomacy.getRelation(Game.getProvince(Game.getProvinceInViewID(i)).getCivID());
               if (tempRelation == 0) {
                  oSB.setColor(
                     new Color(
                        PROVINCE_DIPLOMACY_NEUTRAL_GREEN.r, PROVINCE_DIPLOMACY_NEUTRAL_GREEN.g, PROVINCE_DIPLOMACY_NEUTRAL_GREEN.b, fProvinceAlpha2 / 3.0F
                     )
                  );
               } else if (tempRelation > 0) {
                  oSB.setColor(
                     CFG.getColorStep(
                        PROVINCE_DIPLOMACY_GREEN, PROVINCE_DIPLOMACY_GREEN2, tempRelation, (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX, fProvinceAlpha2
                     )
                  );
               } else {
                  oSB.setColor(
                     CFG.getColorStep(
                        PROVINCE_DIPLOMACY_RED, PROVINCE_DIPLOMACY_RED2, -tempRelation, (int)(-GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN), fProvinceAlpha2
                     )
                  );
               }
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == nActiveCivID) {
               oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
            } else if (DiplomacyManager.isAtWar(nActiveCivID, Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID())) {
               oSB.setColor(new Color(PROVINCE_DIPLOMACY_AT_WAR.r, PROVINCE_DIPLOMACY_AT_WAR.g, PROVINCE_DIPLOMACY_AT_WAR.b, fProvinceAlpha));
            } else {
               int tempRelation = (int)Game.getCiv(nActiveCivID).diplomacy.getRelation(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID());
               if (tempRelation == 0) {
                  oSB.setColor(
                     new Color(
                        PROVINCE_DIPLOMACY_NEUTRAL_GREEN.r, PROVINCE_DIPLOMACY_NEUTRAL_GREEN.g, PROVINCE_DIPLOMACY_NEUTRAL_GREEN.b, fProvinceAlpha2 / 3.0F
                     )
                  );
               } else if (tempRelation > 0) {
                  oSB.setColor(
                     CFG.getColorStep(
                        PROVINCE_DIPLOMACY_GREEN, PROVINCE_DIPLOMACY_GREEN2, tempRelation, (int)GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX, fProvinceAlpha2
                     )
                  );
               } else {
                  oSB.setColor(
                     CFG.getColorStep(
                        PROVINCE_DIPLOMACY_RED, PROVINCE_DIPLOMACY_RED2, -tempRelation, (int)(-GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN), fProvinceAlpha2
                     )
                  );
               }
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_DeclareWar(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == InGame_DeclareWar.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
            } else if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_IsAggressor) {
               oSB.setColor(
                  new Color(MapModeManager.PROVINCE_BLUE2_ALLY.r, MapModeManager.PROVINCE_BLUE2_ALLY.g, MapModeManager.PROVINCE_BLUE2_ALLY.b, fProvinceAlpha)
               );
            } else if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_ParticipatesInWar) {
               oSB.setColor(
                  new Color(MapModeManager.PROVINCE_RED2_ALLY.r, MapModeManager.PROVINCE_RED2_ALLY.g, MapModeManager.PROVINCE_RED2_ALLY.b, fProvinceAlpha)
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == InGame_DeclareWar.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
            } else if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_IsAggressor) {
               oSB.setColor(
                  new Color(MapModeManager.PROVINCE_BLUE2_ALLY.r, MapModeManager.PROVINCE_BLUE2_ALLY.g, MapModeManager.PROVINCE_BLUE2_ALLY.b, fProvinceAlpha)
               );
            } else if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_ParticipatesInWar) {
               oSB.setColor(
                  new Color(MapModeManager.PROVINCE_RED2_ALLY.r, MapModeManager.PROVINCE_RED2_ALLY.g, MapModeManager.PROVINCE_RED2_ALLY.b, fProvinceAlpha)
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_CurrentWars(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.isAtWar()) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.isAtWar()) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_Alliances(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.alliance.isEmpty()) {
               oSB.setColor(new Color(DiplomacyManager.COLOR_ALLIANCE.r, DiplomacyManager.COLOR_ALLIANCE.g, DiplomacyManager.COLOR_ALLIANCE.b, fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.alliance.isEmpty()) {
               oSB.setColor(new Color(DiplomacyManager.COLOR_ALLIANCE.r, DiplomacyManager.COLOR_ALLIANCE.g, DiplomacyManager.COLOR_ALLIANCE.b, fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_DefensivePacts(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.defensivePact.isEmpty()) {
               oSB.setColor(
                  new Color(
                     DiplomacyManager.COLOR_DEFENSIVE_PACT.r, DiplomacyManager.COLOR_DEFENSIVE_PACT.g, DiplomacyManager.COLOR_DEFENSIVE_PACT.b, fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.defensivePact.isEmpty()) {
               oSB.setColor(
                  new Color(
                     DiplomacyManager.COLOR_DEFENSIVE_PACT.r, DiplomacyManager.COLOR_DEFENSIVE_PACT.g, DiplomacyManager.COLOR_DEFENSIVE_PACT.b, fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_NonAggressionPacts(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).diplomacy.nonAggressionPact.isEmpty()) {
               oSB.setColor(
                  new Color(
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.r,
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.g,
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.b,
                     fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (!Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).diplomacy.nonAggressionPact.isEmpty()) {
               oSB.setColor(
                  new Color(
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.r,
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.g,
                     DiplomacyManager.COLOR_NON_AGGRESSION_PACT.b,
                     fProvinceAlpha
                  )
               );
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_Nuke(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (DiplomacyManager.isAtWar(Game.player.iCivID, Game.getProvince(Game.getProvinceInViewID(i)).getCivID())) {
               oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
            } else if (DiplomacyManager.isAtWar(Game.player.iCivID, Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID())) {
               oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_Colonize(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);
      if (Game.getCiv(Game.player.iCivID).fGold < GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD_COST) {
         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getWasteland() < 0) {
               if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlphaGray));
               }

               Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getWasteland() < 0) {
               if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlphaGray));
               }

               Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
            }
         }
      } else {
         for (int ixx = 0; ixx < Game.NUM_OF_PROVINCES_IN_VIEW; ixx++) {
            if (Game.getProvince(Game.getProvinceInViewID(ixx)).getWasteland() < 0) {
               if (Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID() > 0) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(ixx)).getCivID()).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_GREEN.r, MapModeManager.PROVINCE_GREEN.g, MapModeManager.PROVINCE_GREEN.b, fProvinceAlphaGray));
               }

               Game.getProvince(Game.getProvinceInViewID(ixx)).drawLandProvince(oSB);
            }
         }

         for (int ixxx = 0; ixxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxx++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getWasteland() < 0) {
               if (Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID() > 0) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).getCivID()).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_GREEN.r, MapModeManager.PROVINCE_GREEN.g, MapModeManager.PROVINCE_GREEN.b, fProvinceAlphaGray));
               }

               Game.getProvince(Game.getExtraProvinceInViewID(ixxx)).drawLandProvinceExtra(oSB);
            }
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_FormCiv(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.6F;
      drawWastelandProvinces(oSB);

      try {
         if (InGame_FormCiv.formCivID >= 0 && Game.player.formableCivs.size() > InGame_FormCiv.formCivID) {
            for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
               if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).drawInMapMode) {
                     if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
                        Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, fProvinceAlpha);
                     } else {
                        oSB.setColor(
                           new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlphaGray)
                        );
                     }
                  } else if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
                     Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, fProvinceAlphaGray);
                  } else {
                     oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
                  }

                  Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }
            }

            for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
               if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawInMapMode) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
                        Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, fProvinceAlpha);
                     } else {
                        oSB.setColor(
                           new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlphaGray)
                        );
                     }
                  } else if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
                     Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, fProvinceAlphaGray);
                  } else {
                     oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
                  }

                  Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB);
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_SpecialAlliance_View(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(new Color(Colors.HOVER_YELLOW.r, Colors.HOVER_YELLOW.g, Colors.HOVER_YELLOW.b, fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(new Color(Colors.HOVER_YELLOW.r, Colors.HOVER_YELLOW.g, Colors.HOVER_YELLOW.b, fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_WarView(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_BLUE2.r, MapModeManager.PROVINCE_BLUE2.g, MapModeManager.PROVINCE_BLUE2.b, fProvinceAlpha));
               } else {
                  oSB.setColor(new Color(MapModeManager.PROVINCE_RED2.r, MapModeManager.PROVINCE_RED2.g, MapModeManager.PROVINCE_RED2.b, fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
         }
      }

      drawOccupiedProvinces(oSB);
   }

   public static final void drawProvinces_Peace(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
               } else if (Game.getProvince(Game.getProvinceInViewID(i)).peaceTreatyIsTaken) {
                  oSB.setColor(Game.getCiv(Game.player.iCivID).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_ParticipatesInWar) {
               if (Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).warView_IsAggressor) {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
               } else if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).peaceTreatyIsTaken) {
                  oSB.setColor(Game.getCiv(Game.player.iCivID).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB);
         }
      }

      try {
         for (int ixx = Game.player.peaceTreaty.lProvinces_Liberate.size() - 1; ixx >= 0; ixx--) {
            if (Game.getProvince(Game.player.peaceTreaty.lProvinces_Liberate.get(ixx)).getDrawProvince()) {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
               Game.getProvince(Game.player.peaceTreaty.lProvinces_Liberate.get(ixx)).drawLandProvince(oSB);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void drawProvinces_ReleaseVassal(SpriteBatch oSB) {
      float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;
      float fProvinceAlphaGray = getProvinceAlpha() * MapModeManager.fAlphaAnimation * 0.4F;
      drawWastelandProvinces(oSB);

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() > 0) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID) {
               if (Game.getProvince(Game.getProvinceInViewID(i)).peaceTreatyIsTaken) {
                  oSB.setColor(Game.getCiv(InGame_ReleaseAVassal.releaseVassalData.iCivID).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getProvinceInViewID(i)).getCivID()).getColor(fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() > 0) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID) {
               if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).peaceTreatyIsTaken) {
                  oSB.setColor(Game.getCiv(InGame_ReleaseAVassal.releaseVassalData.iCivID).getColor(fProvinceAlpha));
               } else {
                  oSB.setColor(Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID()).getColor(fProvinceAlpha));
               }
            } else {
               oSB.setColor(new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, fProvinceAlphaGray));
            }

            Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvince(oSB);
         }
      }
   }

   public static final void drawWastelandProvinces(SpriteBatch oSB) {
      for (int i = 0; i < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; i++) {
         oSB.setColor(getWastelandColor(Game.getProvince(Game.getWastelandProvinceInViewID(i)).getWasteland(), Game.settingsManager.PROVINCE_ALPHA_WASTELAND));
         Game.getProvince(Game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
      }
   }

   public static final float getProvinceAlpha() {
      if (Game.mapScale.getCurrentScale() > 1.0F) {
         return Math.max(
            0.0F,
            Game.settingsManager.PROVINCE_ALPHA
               * (1.0F - GameValues.zoom.PROVINCE_ALPHA_ZOOM_IN * Game.mapScale.getCurrentScale() / MapScale.defScales.definedScales[0])
         );
      } else if (Game.mapScale.getCurrentScale() == 1.0F) {
         return Game.settingsManager.PROVINCE_ALPHA;
      } else {
         return Game.mapScale.getCurrentScale() < Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Scale
            ? Math.min(
               1.0F,
               Game.settingsManager.PROVINCE_ALPHA
                  + (1.0F - Game.settingsManager.PROVINCE_ALPHA) * (GameValues.zoom.PROVINCE_ALPHA_ZOOM_OUT * 1.5F) * (1.0F - Game.mapScale.getCurrentScale())
            )
            : Math.min(
               1.0F,
               Game.settingsManager.PROVINCE_ALPHA
                  + (1.0F - Game.settingsManager.PROVINCE_ALPHA) * GameValues.zoom.PROVINCE_ALPHA_ZOOM_OUT * (1.0F - Game.mapScale.getCurrentScale())
            );
      }
   }

   public static final float getProvinceAlpha(float fAlpha) {
      if (Game.mapScale.getCurrentScale() > 1.0F) {
         return Math.max(0.0F, fAlpha * (1.0F - GameValues.zoom.PROVINCE_ALPHA_ZOOM_IN * Game.mapScale.getCurrentScale() / MapScale.defScales.definedScales[0]));
      } else {
         return Game.mapScale.getCurrentScale() == 1.0F
            ? fAlpha
            : Math.min(1.0F, fAlpha + (1.0F - fAlpha) * GameValues.zoom.PROVINCE_ALPHA_ZOOM_OUT * (1.0F - Game.mapScale.getCurrentScale()));
      }
   }

   public static final void drawOccupiedProvinces(SpriteBatch oSB) {
      if (CFG.isDesktop() && drawProvincesCiv_HoveredFlagID > 0) {
         oSB.setShader(Renderer.shaderDefaultProvince);
         float fProvinceAlpha = getProvinceAlpha() * MapModeManager.fAlphaAnimation;

         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == drawProvincesCiv_HoveredFlagID) {
               Game.getProvince(Game.getProvinceInViewID(i)).setProvinceColor(oSB, fProvinceAlpha);
               Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == drawProvincesCiv_HoveredFlagID) {
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).setProvinceColor(oSB, fProvinceAlpha);
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawLandProvinceExtra(oSB);
            }
         }

         oSB.setShader(Renderer.shaderDefault);
      }

      if (Game.mapScale.getCurrentScale() >= Game.DRAW_OCCUPIED_PROVINCES_MIN_SCALE) {
         drawOccupiedProvinces_Just(oSB);
      } else if (ProvinceDrawArmy.drawOccupiedHideAnimation) {
         drawOccupiedProvinces_Just(oSB);
      }
   }

   private static final void drawOccupiedProvinces_Just(SpriteBatch oSB) {
      float fAlpha = getProvinceAlpha() * (1.0F + Game.settingsManager.PROVINCE_OCCUPIED_ALPHA_EXTRA) * ProvinceDrawArmy.DRAW_OCCUPIED_ALPHA;
      if (fAlpha > 0.001F) {
         oSB.setShader(Renderer.shaderAlpha_Pattern);

         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).isOccupied()) {
               Game.getProvince(Game.getProvinceInViewID(i)).drawOccupiedProvince(oSB, fAlpha);
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).isOccupied()) {
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawOccupiedProvince(oSB, fAlpha);
            }
         }

         oSB.setShader(Renderer.shaderDefault);
      }
   }

   public static final Color getWastelandColor(int wastelandLevel, float fAlpha) {
      return new Color(
         Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.WastelandColor[0] - 0.0209F * wastelandLevel,
         Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.WastelandColor[1] - 0.0176F * wastelandLevel,
         Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.WastelandColor[2] - 0.0148F * wastelandLevel,
         fAlpha
      );
   }

   public static final void drawProvincesBorder_Prepare() {
      lineWidth = 1.5F;
      if (Game.mapScale.getCurrentScale() < 1.0F) {
         lineWidth = Math.min(6.0F, lineWidth / Game.mapScale.getCurrentScale());
      }

      lineWidth = lineWidth + Game.settingsManager.BORDER_EXTRA_WIDTH;
      ProvinceBorder.mapCordsPosY = -Game.mapCoords.getPosY();
      ProvinceBorder.pathProvinceBorderExtraWidth = Math.min(
            SettingsProvince.value.MAX_BORDER_WIDTH,
            Math.max(SettingsProvince.value.MIN_BORDER_WIDTH, SettingsProvince.value.MAX_BORDER_WIDTH / Game.mapScale.getCurrentScale())
         )
         + Game.settingsManager.BORDER_EXTRA_WIDTH;
      ProvinceBorder.pathProvinceBorderExtraWidth2 = Math.max(1.0F, ProvinceBorder.pathProvinceBorderExtraWidth / SettingsProvince.value.BORDER_WIDTH_DIVIDE)
         + Game.settingsManager.BORDER_EXTRA_WIDTH;
      if (Game.mapScale.getCurrentScale() < SettingsProvince.value.SCALE_NONE_NONE) {
         joinType = JoinType.NONE;
         joinType_Shadow = JoinType.NONE;
      } else if (Game.mapScale.getCurrentScale() < SettingsProvince.value.SCALE_NONE_POINTY) {
         joinType = JoinType.NONE;
         joinType_Shadow = JoinType.POINTY;
      } else if (Game.mapScale.getCurrentScale() < SettingsProvince.value.SCALE_POINTY_POINTY) {
         joinType = JoinType.POINTY;
         joinType_Shadow = JoinType.POINTY;
      } else if (Game.mapScale.getCurrentScale() < SettingsProvince.value.SCALE_POINTY_SMOOTH) {
         joinType = JoinType.POINTY;
         joinType_Shadow = JoinType.SMOOTH;
      } else {
         joinType = JoinType.SMOOTH;
         joinType_Shadow = JoinType.SMOOTH;
      }
   }

   public static final void drawProvincesBorder(SpriteBatch oSB) {
      if (Game.settingsManager.SETTINGS_PROVINCE_BORDER > 0 && Game.mapScale.getCurrentScale() > SettingsProvince.value.DRAW_BORDERS) {
         Renderer.oSBBorder.begin();
         drawProvincesBorder_Prepare();

         try {
            for (int i = 0; i < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; i++) {
               Game.getProvince(Game.getWastelandProvinceInViewID(i)).drawProvinceBorder(oSB, lineWidth, joinType);
            }

            for (int i = 0; i < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; i++) {
               Game.getProvince(Game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB, lineWidth, joinType);
            }

            for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
               Game.getProvince(Game.getExtraProvinceInViewID(i)).drawProvinceBorder(oSB, lineWidth, joinType);
            }

            for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
               Game.getProvince(Game.getProvinceInViewID(i)).drawProvinceBorder(oSB, lineWidth, joinType);
            }
         } catch (Exception var2) {
            CFG.exceptionStack(var2);
         }

         Renderer.oSBBorder.end();
         oSB.setColor(Color.WHITE);
      }
   }

   protected static final void drawProvincesInMapEditor_Connections(SpriteBatch oSB) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         Game.getProvince(Game.getProvinceInViewID(i)).drawLandProvince(oSB);
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         for (int j = 0; j < Game.getProvince(Game.getProvinceInViewID(i)).getProvinceBordersLandByLandSize(); j++) {
            drawProvincesInMapEditor_Connections_Line(
               oSB,
               Images.pix2,
               Game.getProvinceInViewID(i),
               Game.getProvince(Game.getProvinceInViewID(i)).getProvinceBordersLandByLand().get(j).getWithProvinceID()
            );
         }

         for (int j = 0; j < Game.getProvince(Game.getProvinceInViewID(i)).getProvinceBordersLandBySeaSize(); j++) {
            drawProvincesInMapEditor_Connections_Line(
               oSB,
               Images.line_33,
               Game.getProvinceInViewID(i),
               Game.getProvince(Game.getProvinceInViewID(i)).getProvinceBordersLandBySea().get(j).getWithProvinceID()
            );
         }
      }

      for (int i = 0; i < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; i++) {
         for (int j = 0; j < Game.getProvince(Game.getSeaProvinceInViewID(i)).getProvinceBordersLandBySeaSize(); j++) {
            drawProvincesInMapEditor_Connections_Line(
               oSB,
               Images.line_33,
               Game.getSeaProvinceInViewID(i),
               Game.getProvince(Game.getSeaProvinceInViewID(i)).getProvinceBordersLandBySea().get(j).getWithProvinceID()
            );
         }

         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));

         for (int j = 0; j < Game.getProvince(Game.getSeaProvinceInViewID(i)).getProvinceBordersSeaBySeaSize(); j++) {
            drawProvincesInMapEditor_Connections_Line(
               oSB,
               Images.line_33,
               Game.getSeaProvinceInViewID(i),
               Game.getProvince(Game.getSeaProvinceInViewID(i)).getProvinceBordersSeaBySea().get(j).getWithProvinceID()
            );
         }
      }
   }

   private static final void drawProvincesInMapEditor_Connections_Line(SpriteBatch oSB, int nImageID, int fromProvinceID, int toProvinceID) {
      if (Game.getProvince(toProvinceID).getDrawProvince()) {
         int iWidth = (int)Math.ceil(
            Math.sqrt(
               (
                        Game.getProvince(toProvinceID).iCenterShiftX
                           + Game.getProvince(toProvinceID).getTranslateProvincePosX()
                           - (Game.getProvince(fromProvinceID).iCenterShiftX + Game.getProvince(fromProvinceID).getTranslateProvincePosX())
                     )
                     * (
                        Game.getProvince(toProvinceID).iCenterShiftX
                           + Game.getProvince(toProvinceID).getTranslateProvincePosX()
                           - (Game.getProvince(fromProvinceID).iCenterShiftX + Game.getProvince(fromProvinceID).getTranslateProvincePosX())
                     )
                  + (Game.getProvince(fromProvinceID).iCenterShiftY - Game.getProvince(toProvinceID).iCenterShiftY)
                     * (Game.getProvince(fromProvinceID).iCenterShiftY - Game.getProvince(toProvinceID).iCenterShiftY)
            )
         );
         float fAngle = (float)(
            Math.atan2(
                  Game.getProvince(fromProvinceID).iCenterShiftY - Game.getProvince(toProvinceID).iCenterShiftY,
                  -(Game.getProvince(fromProvinceID).iCenterShiftX + Game.getProvince(fromProvinceID).getTranslateProvincePosX())
                     + Game.getProvince(toProvinceID).iCenterShiftX
                     + Game.getProvince(toProvinceID).getTranslateProvincePosX()
               )
               * 180.0
               / Math.PI
         );
         ImageManager.getImage(nImageID)
            .draw(
               oSB,
               Game.getProvince(fromProvinceID).iCenterShiftX + Game.getProvince(fromProvinceID).getTranslateProvincePosX(),
               Game.getProvince(fromProvinceID).iCenterShiftY + Game.mapCoords.getPosY(),
               iWidth,
               ImageManager.getImage(nImageID).getHeight(),
               fAngle,
               0
            );
      }
   }

   public static final void drawProvinces(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() != 0) {
            Game.getProvince(i).draw(oSB, nPosX, nPosY, scale, nAlpha);
         } else if (Game.getProvince(i).getWasteland() >= 0) {
            Game.getProvince(i).drawWasteland(oSB, nPosX, nPosY, scale, nAlpha);
         }
      }

      oSB.setColor(Color.WHITE);
   }

   public static final void drawProvinces_Minimap(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() != 0) {
            Game.getProvince(i).draw(oSB, nPosX, nPosY, scale, nAlpha);
         }
      }

      oSB.setColor(Color.WHITE);
   }

   public static final void drawProvinces_FogOfWarDiscovery(SpriteBatch oSB, int nPosX, int nPosY, float scale, int nAlpha) {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() != 0) {
            Game.getProvince(i).draw_FogOfWarDiscovery(oSB, nPosX, nPosY, scale, nAlpha);
         } else if (Game.getProvince(i).getWasteland() >= 0 && CFG.getMetProvince(i)) {
            Game.getProvince(i).drawWasteland(oSB, nPosX, nPosY, scale, nAlpha);
         }
      }

      oSB.setColor(Color.WHITE);
   }

   public static final void updateDrawMoveUnits() {
      if (Game.menuManager.getInGame()) {
         oDrawMoveUnits = new ProvinceDraw.DrawMoveUnits() {
            @Override
            public void drawMoveUnits(SpriteBatch oSB) {
               if (Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE) {
                  ProvinceDraw.drawMoveUnits_Just(oSB, Game.mapScale.getCurrentScale());

                  try {
                     if (Game.regroupArmyLine != null) {
                        Game.regroupArmyLine.draw(oSB, Game.mapScale.getCurrentScale());

                        try {
                           Renderer.oSBBorder.end();
                           Renderer.oSBBorder.begin();
                        } catch (Exception var3) {
                           CFG.exceptionStack(var3);
                        }
                     }
                  } catch (Exception var4) {
                     CFG.exceptionStack(var4);
                  }
               }
            }
         };
      } else {
         oDrawMoveUnits = new ProvinceDraw.DrawMoveUnits() {
            @Override
            public void drawMoveUnits(SpriteBatch oSB) {
            }
         };
      }
   }

   public static final void drawMoveUnits_Just_CheckArmies() {
      try {
         for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(Game.player.iCivID).diplomacy.alliance.entrySet()) {
            int civID = entry.getKey();
            if (Game.getCiv(civID).getPuppetOfCivID() != Game.player.iCivID) {
               for (int j = Game.getCiv(civID).getMoveUnitsSize() - 1; j >= 0; j--) {
                  if (Game.getProvince(Game.getCiv(civID).getMoveUnits(j).getFromProvinceID()).getArmy(Game.getCiv(civID).getMoveUnits(j).key) == null) {
                     Game.getCiv(civID).removeMove(j);
                  }
               }
            }
         }

         for (int i = 0; i < Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize; i++) {
            int civID = Game.getCiv(Game.player.iCivID).diplomacy.lVassals.get(i).c;

            for (int jx = Game.getCiv(civID).getMoveUnitsSize() - 1; jx >= 0; jx--) {
               if (Game.getProvince(Game.getCiv(civID).getMoveUnits(jx).getFromProvinceID()).getArmy(Game.getCiv(civID).getMoveUnits(jx).key) == null) {
                  Game.getCiv(civID).removeMove(jx);
               }
            }
         }

         for (int jxx = Game.getCiv(Game.player.iCivID).getMoveUnitsSize() - 1; jxx >= 0; jxx--) {
            if (Game.getProvince(Game.getCiv(Game.player.iCivID).getMoveUnits(jxx).getFromProvinceID())
                  .getArmy(Game.getCiv(Game.player.iCivID).getMoveUnits(jxx).key)
               == null) {
               Game.getCiv(Game.player.iCivID).removeMove(jxx);
               CFG.LOG("REMOVED");
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void drawMoveUnits_Just(SpriteBatch oSB, float nScale) {
      oSB.setColor(Color.WHITE);
      boolean drawn = false;

      try {
         if (++FRAME_ID >= 14400) {
            FRAME_ID = 0;
            drawMoveUnits_Just_CheckArmies();
         }

         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getMoveUnitsSize() > 0) {
               for (int j = 0; j < Game.getCiv(i).getMoveUnitsSize(); j++) {
                  try {
                     Game.getCiv(i).getMoveUnits(j).update();
                  } catch (Exception var13) {
                  }
               }
            }
         }

         for (int j = 0; j < Game.revolutionMoveUnits.iMoveUnitsSize; j++) {
            try {
               Game.revolutionMoveUnits.lMoveUnits.get(j).update();
            } catch (Exception var12) {
            }
         }

         try {
            for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(Game.player.iCivID).diplomacy.alliance.entrySet()) {
               int civID = entry.getKey();
               if (Game.getCiv(civID).getPuppetOfCivID() != Game.player.iCivID) {
                  for (int j = 0; j < Game.getCiv(civID).getMoveUnitsSize(); j++) {
                     try {
                        Game.getCiv(civID).getMoveUnits(j).draw_Ally(oSB, nScale);
                        drawn = true;
                     } catch (Exception var11) {
                     }
                  }
               }
            }

            for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize; ix++) {
               int civID = Game.getCiv(Game.player.iCivID).diplomacy.lVassals.get(ix).c;

               for (int j = 0; j < Game.getCiv(civID).getMoveUnitsSize(); j++) {
                  try {
                     Game.getCiv(civID).getMoveUnits(j).draw_Ally(oSB, nScale);
                     drawn = true;
                  } catch (Exception var10) {
                  }
               }
            }

            for (int j = 0; j < Game.getCiv(Game.player.iCivID).getMoveUnitsSize(); j++) {
               try {
                  Game.getCiv(Game.player.iCivID).getMoveUnits(j).draw(oSB, nScale);
                  drawn = true;
               } catch (Exception var9) {
               }
            }
         } catch (Exception var14) {
         }
      } catch (Exception var15) {
      }

      if (drawn) {
         try {
            Renderer.oSBBorder.end();
            Renderer.oSBBorder.begin();
         } catch (Exception var8) {
            CFG.exceptionStack(var8);
         }
      }
   }

   public static final void buildSiegeLines(int provinceID) {
      clearSiegeLines();
      Province province = Game.getProvince(provinceID);

      for (int i = 0; i < province.getNeighboringProvincesSize(); i++) {
         if (province.getCivID() == Game.getProvince(province.getNeighboringProvinces(i)).getCivID()
            && Game.getProvince(province.getNeighboringProvinces(i)).getFortLevel() == 0) {
            MoveUnits_BiggestCities_Siege nLine = new MoveUnits_BiggestCities_Siege(province.getCivID(), provinceID, province.getNeighboringProvinces(i));
            siegeLines.add(nLine);
         }
      }

      iSiegeLinesSize = siegeLines.size();
   }

   public static final void drawSiegeLines_Just(SpriteBatch oSB, float nScale) {
      if (iSiegeLinesSize > 0 && Game.mapScale.getCurrentScale() > Game.DRAW_INNER_BORDERS) {
         try {
            oSB.setColor(Color.WHITE);

            for (int j = 0; j < iSiegeLinesSize; j++) {
               try {
                  siegeLines.get(j).update();
               } catch (Exception var6) {
               }
            }

            try {
               for (int j = 0; j < iSiegeLinesSize; j++) {
                  try {
                     siegeLines.get(j).draw(oSB, nScale);
                  } catch (Exception var5) {
                  }
               }
            } catch (Exception var7) {
            }
         } catch (Exception var8) {
         }

         try {
            Renderer.oSBBorder.end();
            Renderer.oSBBorder.begin();
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final void clearSiegeLines() {
      siegeLines.clear();
      iSiegeLinesSize = 0;
   }

   public static final void buildBiggestCitiesLines(int iCivID) {
      final List<Integer> lCiv = new ArrayList<>();
      lCiv.add(iCivID);
      Game.gameThreadUpdate.addSimpleTask(new Game.SimpleTask("buildBiggestCitiesLines" + iCivID) {
         @Override
         public void update() {
            ProvinceDraw.buildBiggestCitiesLines(lCiv);
            if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DIPLOMACY) {
               ProvinceDraw.clearBiggestCities();
            }
         }
      });
   }

   public static final void buildBiggestCitiesLines(List<Integer> iCivID) {
      biggestCitiesLines.clear();
      iBiggestCitiesLinesSize = 0;
      int k = 0;

      for (int kSize = iCivID.size(); k < kSize; k++) {
         if (Game.getCiv(iCivID.get(k)).getNumOfProvinces() > 1 && Game.getCiv(iCivID.get(k)).getCapitalProvinceID() >= 0) {
            int numOfLines = Math.min(
               GameValues.diplomacy.DIPLOMACY_VIEW_BIGGEST_CITIES_LINES_MAX,
               Math.min(
                  Math.max(
                     GameValues.diplomacy.DIPLOMACY_VIEW_BIGGEST_CITIES_LINES_MIN,
                     (int)(Game.getCiv(iCivID.get(k)).getNumOfProvinces() * GameValues.diplomacy.DIPLOMACY_VIEW_BIGGEST_CITIES_LINES_PERC_OF_PROVINCES)
                  ),
                  Game.getCiv(iCivID.get(k)).getNumOfProvinces() - 1
               )
            );
            List<Integer> tProvinces = new ArrayList<>();

            for (int i = 0; i < Game.getCiv(iCivID.get(k)).getNumOfProvinces(); i++) {
               if (Game.getCiv(iCivID.get(k)).getProvinceID(i) != Game.getCiv(iCivID.get(k)).getCapitalProvinceID()) {
                  tProvinces.add(Game.getCiv(iCivID.get(k)).getProvinceID(i));
               }
            }

            for (int ix = 0; ix < numOfLines && ix < tProvinces.size(); ix++) {
               int bestID = 0;
               int j = 1;

               for (int jSize = tProvinces.size(); j < jSize; j++) {
                  if (Game.getProvince(tProvinces.get(bestID)).getPopulationTotal() < Game.getProvince(tProvinces.get(j)).getPopulationTotal()) {
                     bestID = j;
                  }
               }

               try {
                  MoveUnits_BiggestCities nLine = new MoveUnits_BiggestCities(
                     iCivID.get(k), Game.getCiv(iCivID.get(k)).getCapitalProvinceID(), tProvinces.get(bestID)
                  );
                  if (nLine.iRouteSize > 0) {
                     biggestCitiesLines.add(nLine);
                  } else {
                     ix--;
                  }
               } catch (Exception var9) {
                  CFG.exceptionStack(var9);
               }

               tProvinces.remove(bestID);
            }

            tProvinces.clear();
         }
      }

      iBiggestCitiesLinesSize = biggestCitiesLines.size();
   }

   public static final void buildBiggestCitiesLines_Province(int iProvinceA, int iProvinceB) {
      biggestCitiesLines.clear();
      iBiggestCitiesLinesSize = 0;
      MoveUnits_BiggestCities nLine = new MoveUnits_BiggestCities(Game.getProvince(iProvinceA).getCivID(), iProvinceA, iProvinceB) {
         @Override
         public boolean isFriendlyProvince(int nCivID, int toProvinceID) {
            return true;
         }

         @Override
         public boolean canBeUsedInPath(int nCivID, int nProvinceID, boolean moveToFriendlyProvince, int toProvinceID) {
            return Game.getProvince(nProvinceID).getWasteland() < 0;
         }
      };
      if (nLine.iRouteSize > 0) {
         biggestCitiesLines.add(nLine);
      }

      iBiggestCitiesLinesSize = biggestCitiesLines.size();
   }

   public static final void drawBiggestCitiesLines_Just(SpriteBatch oSB, float nScale) {
      if (iBiggestCitiesLinesSize > 0 && Game.mapScale.getCurrentScale() > Game.DRAW_INNER_BORDERS) {
         try {
            oSB.setColor(Color.WHITE);

            for (int j = 0; j < iBiggestCitiesLinesSize; j++) {
               try {
                  biggestCitiesLines.get(j).update();
               } catch (Exception var6) {
               }
            }

            try {
               for (int j = 0; j < iBiggestCitiesLinesSize; j++) {
                  try {
                     biggestCitiesLines.get(j).draw(oSB, nScale);
                  } catch (Exception var5) {
                  }
               }
            } catch (Exception var7) {
            }
         } catch (Exception var8) {
         }

         try {
            Renderer.oSBBorder.end();
            Renderer.oSBBorder.begin();
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final void clearBiggestCities() {
      biggestCitiesLines.clear();
      iBiggestCitiesLinesSize = 0;
   }

   public static final void addDiplomacyLines(int iProvinceA, int iProvinceB, Color nColor) {
      try {
         if (iProvinceA < 0 || iProvinceB < 0) {
            return;
         }

         if ((Game.getProvince(iProvinceA).getDrawProvince() || Game.getProvince(iProvinceB).getDrawProvince())
            && Game.mapScale.getCurrentScale() > Game.DRAW_INNER_BORDERS) {
            for (int i = diplomacyLines.size() - 1; i >= 0; i--) {
               if (diplomacyLines.get(i).getFromProvinceID() == iProvinceA && diplomacyLines.get(i).getToProvinceLastID() == iProvinceB) {
                  return;
               }
            }

            MoveUnits_BiggestCities nLine = new MoveUnits_BiggestCities_Diplomacy(Game.getProvince(iProvinceA).getCivID(), iProvinceA, iProvinceB, nColor) {
               @Override
               public boolean isFriendlyProvince(int nCivID, int toProvinceID) {
                  return true;
               }

               @Override
               public boolean canBeUsedInPath(int nCivID, int nProvinceID, boolean moveToFriendlyProvince, int toProvinceID) {
                  return Game.getProvince(nProvinceID).getWasteland() < 0;
               }
            };
            if (nLine.iRouteSize > 0) {
               diplomacyLines.add(nLine);
            }

            iDiplomacyLinesSize = diplomacyLines.size();
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void drawDiplomacyLines_Just(SpriteBatch oSB, float nScale) {
      if (iDiplomacyLinesSize > 0) {
         try {
            oSB.setColor(Color.WHITE);
            Renderer.oSBBorder.begin();

            for (int j = iDiplomacyLinesSize - 1; j >= 0; j--) {
               try {
                  diplomacyLines.get(j).update();
               } catch (Exception var6) {
                  diplomacyLines.remove(j);
                  CFG.exceptionStack(var6);
               }
            }

            try {
               for (int j = iDiplomacyLinesSize - 1; j >= 0; j--) {
                  try {
                     if (diplomacyLines.get(j).draw2(oSB, nScale)) {
                        diplomacyLines.remove(j);
                     }
                  } catch (Exception var5) {
                     diplomacyLines.remove(j);
                     CFG.exceptionStack(var5);
                  }
               }

               iDiplomacyLinesSize = diplomacyLines.size();
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         } catch (Exception var8) {
            CFG.exceptionStack(var8);
         }

         try {
            Renderer.oSBBorder.end();
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final void clearDiplomacyLines() {
      diplomacyLines.clear();
      iDiplomacyLinesSize = 0;
   }

   public static final void addProvinceDot(int iProvinceA, Color nColor) {
      try {
         for (int i = provinceDots.size() - 1; i >= 0; i--) {
            if (provinceDots.get(i).iProvinceID == iProvinceA && provinceDots.get(i).fPerc > 0.9F) {
               return;
            }
         }

         provinceDots.add(new ProvinceAnimationDot(iProvinceA, nColor));
         iProvinceDotsSize = provinceDots.size();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void addProvinceDot_Economy(int iProvinceID) {
      addProvinceDot(iProvinceID, Colors.COLOR_TEXT_ECONOMY);
   }

   public static final void addProvinceDot_TaxEfficiency(int iProvinceID) {
      addProvinceDot(iProvinceID, Colors.HOVER_YELLOW);
   }

   public static final void addProvinceDot_Manpower(int iProvinceID) {
      addProvinceDot(iProvinceID, Game.getCiv(Game.getProvince(iProvinceID).getCivID()).getColor(1.0F));
   }

   public static final void addProvinceDot_GrowthRate(int iProvinceID) {
      addProvinceDot(iProvinceID, Colors.COLOR_POPULATION);
   }

   public static final void addProvinceDot_Infrastructure(int iProvinceID) {
      addProvinceDot(iProvinceID, Colors.HOVER_LEFT);
   }

   public static final void drawProvinceDots_Just(SpriteBatch oSB, float nScale) {
      if (iProvinceDotsSize > 0 && Game.mapScale.getCurrentScale() > Game.DRAW_INNER_BORDERS) {
         try {
            oSB.setColor(Color.WHITE);
            Renderer.oSBBorder.begin();

            try {
               for (int j = iProvinceDotsSize - 1; j >= 0; j--) {
                  try {
                     if (provinceDots.get(j).draw(oSB, nScale)) {
                        provinceDots.remove(j);
                     }
                  } catch (Exception var5) {
                     CFG.exceptionStack(var5);
                  }
               }

               iProvinceDotsSize = provinceDots.size();
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }
         } catch (Exception var7) {
            CFG.exceptionStack(var7);
         }

         try {
            Renderer.oSBBorder.end();
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final void clearProvinceDots() {
      provinceDots.clear();
      iProvinceDotsSize = 0;
   }

   public static void drawReligionConversion(SpriteBatch oSB) {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE) {
         drawReligionConversion(oSB, ProvinceDrawArmy.DRAW_ARMY_ALPHA);
      } else if (Game.mapTouchManager.selectMode) {
         drawReligionConversion(oSB, 1.0F);
      } else if (ProvinceDrawArmy.drawHideAnimation && Game.mapScale.getCurrentScale() >= ProvinceDrawArmy.DRAW_ARMY_MIN_SCALE_ANIMATION) {
         drawReligionConversion(oSB, ProvinceDrawArmy.DRAW_ARMY_ALPHA);
      }
   }

   public static final void drawReligionConversion(SpriteBatch oSB, float fAlpha) {
      try {
         if (Game.getCiv(Game.player.iCivID).convertReligion.provincesSize > 0) {
            int iW = Game.religionManager.religionImages.get(Game.getCiv(Game.player.iCivID).getReligionID()).getWidth();
            int iH = Game.religionManager.religionImages.get(Game.getCiv(Game.player.iCivID).getReligionID()).getHeight();
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));

            for (int i = 0; i < Game.getCiv(Game.player.iCivID).convertReligion.provincesSize; i++) {
               if (Game.getProvince(Game.getCiv(Game.player.iCivID).convertReligion.provinces.get(i)).getDrawProvince()) {
                  drawReligionConversion(oSB, fAlpha, Game.getCiv(Game.player.iCivID).convertReligion.provinces.get(i), iW, iH);
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      oSB.setColor(Color.WHITE);
   }

   private static final void drawReligionConversion(SpriteBatch oSB, float fAlpha, int provinceID, int iW, int iH) {
      try {
         int iPosX = (int)(
               (
                     (
                           Game.getProvince(provinceID).getCitiesSize() > 0
                              ? Game.getProvince(provinceID).getCity(0).getPosX()
                              : Game.getProvince(provinceID).iCenterShiftX
                        )
                        + Game.getProvince(provinceID).getTranslateProvincePosX()
                  )
                  * Game.mapScale.getCurrentScale()
            )
            - iW / 2;
         int iPosY = (int)(
            (
                     (
                           Game.getProvince(provinceID).getCitiesSize() > 0
                              ? Game.getProvince(provinceID).getCity(0).getPosY()
                              : Game.getProvince(provinceID).iCenterShiftY
                        )
                        + Game.mapCoords.getPosY()
                  )
                  * Game.mapScale.getCurrentScale()
               - iH / 2
         );
         Game.religionManager.religionImages.get(Game.getCiv(Game.player.iCivID).getReligionID()).draw(oSB, iPosX, iPosY, iW, iH);
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static class DrawExtraDetails {
      public void draw(SpriteBatch oSB) {
      }
   }

   public interface DrawMoveUnits {
      void drawMoveUnits(SpriteBatch var1);
   }

   public interface DrawProvinces {
      void draw(SpriteBatch var1);
   }
}
