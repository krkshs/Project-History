package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menusMapEditor.EditorMap_PrintMap;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioSettings;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class MapBG {
   public List<Image> gameMap = new ArrayList<>();
   public int gameMapSize = 0;
   public int iMapScale = 1;
   public float iMapScaleBG = 1.0F;
   public float iMapExtraScale = 1.0F;
   private int iBackgroundSize_X = 1;
   private int iBackgroundSize_Y = 1;
   public static int iWidthOfMap = 1;
   public static int iHeightOfMap = 1;
   private int iWidthOfMap_Real = 1;
   private int iHeightOfMap_Real = 1;
   private int iWidthOfSingleBG = 1;
   private int iHeightOfSingleBG = 1;
   private int iWidthOfSingleBG_xMapScale = 1;
   private int iHeightOfSingleBG_xMapScale = 1;
   public List<Image> gameMap2 = new ArrayList<>();
   public int gameMap2Size = 0;
   private int iBackground2Size_X = 1;
   private int iBackground2Size_Y = 1;
   private int iWidthOfSingleBG2 = 1;
   private int iHeightOfSingleBG2 = 1;
   private int iWidthOfSingleBGTech = 1;
   private int iHeightOfSingleBGTech = 1;
   private int iWidthOfMapTech = 1;
   public int iHeightOfMapTech = 1;
   public boolean secondSideOfMap = true;
   public Image minimapOfCivilizations = null;
   public Image minimapBG = null;
   public Image minimapOver = null;
   public boolean requestToDisposeMinimap = false;
   public int iMinimapHeight;
   public int iMinimapWidth;
   public float fMinimapScaleX;
   public float fMinimapScaleY;
   public Image scenarioBG = null;
   public Image scenarioMask = null;
   public Image scenarioOver = null;
   public Image previewOfCivilizations = null;
   public int iPreviewHeight;
   public int iPreviewWidth;
   public float fPreviewScaleX;
   public float fPreviewScaleY;
   public static int loadMapBG_FileID = 0;
   private MapBG.MapShader mapShader;
   private List<MapBG.OutsideMapImage> outsideMapImagesData = new ArrayList<>();
   private List<Image> outsideMapImages = new ArrayList<>();
   private int outsideMapImagesSize = 0;
   private List<MapBG.OutsideMapImage> outsideMapImagesData_Below = new ArrayList<>();
   private List<Image> outsideMapImages_Below = new ArrayList<>();
   private int outsideMapImagesSize_Below = 0;
   public MapBG.MapBorder mapBorder = new MapBG.MapBorder();
   private MapBG.WorldMap worldMap = null;
   public boolean drawMapAnimation = true;
   private int iBGID = 0;
   private long animationTime = 0L;
   private boolean inAnimation = false;
   public static MapBG.MapBGSea mapBGSea = new MapBG.MapBGSea() {
      @Override
      public void drawMapSea(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
      }
   };
   public boolean isOutsideInView = false;
   public boolean isOutsideInView_Bot = false;
   public float outsideScale = 1.0F;
   public float outsideScale2 = 1.0F;
   public final int ALPHA_MINIMAPS = 220;
   public final float EXTRA_XY = 0.125F;
   public int iMinimapScaled_PosX = 0;
   public int iMinimapScaled_PosY = 0;
   public int iMinimapScaled_Width = 1;
   public int iMinimapScaled_Height = 1;
   public float fMinimapScaled_Scale = 1.0F;
   public boolean minimapBelowZero = false;
   public int iPreviewScaled_PosX = 0;
   public int iPreviewScaled_PosY = 0;
   public int iPreviewScaled_Width = 1;
   public int iPreviewScaled_Height = 1;
   public float fPreviewScaled_Scale = 1.0F;
   public boolean PreviewBelowZero = false;

   public boolean getHideMenuZoomOut() {
      return Game.mapScale.getCurrentScale() > Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Scale;
   }

   public MapBG() {
      this.updateActiveMapBGShader();
      this.updateWorldMap();
   }

   public final void loadMapBG_Begin() {
      if (this.gameMapSize > 0) {
         this.dispose();
      }

      this.iBackgroundSize_X = Game.map.getActiveMap_MapData().mapData.BackgroundSize_X;
      this.iBackgroundSize_Y = Game.map.getActiveMap_MapData().mapData.BackgroundSize_Y;
   }

   public final boolean loadMapBG() {
      int j = loadMapBG_FileID++;
      if (j >= this.iBackgroundSize_Y) {
         return false;
      } else {
         for (int i = 0; i < this.iBackgroundSize_X; i++) {
            this.gameMap
               .add(
                  new Image(
                     ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/main/" + j + "_" + i + ".png"),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
         }

         return true;
      }
   }

   public final void loadMapBG_End() {
      this.gameMapSize = this.gameMap.size();
      iWidthOfMap = 0;

      for (int i = 0; i < this.iBackgroundSize_X; i++) {
         iWidthOfMap = iWidthOfMap + this.gameMap.get(i).getWidth();
      }

      iHeightOfMap = 0;

      for (int i = 0; i < this.iBackgroundSize_Y; i++) {
         iHeightOfMap = iHeightOfMap + this.gameMap.get(i * this.iBackgroundSize_X).getHeight();
      }

      iWidthOfMap = (int)(iWidthOfMap * Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.BackgroundScale);
      iHeightOfMap = (int)(iHeightOfMap * Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.BackgroundScale);
      this.iWidthOfMap_Real = iWidthOfMap;
      this.iHeightOfMap_Real = iHeightOfMap;
      iWidthOfMap = iWidthOfMap * this.iMapScale;
      iHeightOfMap = iHeightOfMap * this.iMapScale;
      Game.updateMapDistance();
      Game.iMaxDistance = (int)Math.ceil(
         Math.sqrt(Math.pow(this.getWidth_Real() / (Game.map.isWorldMap(Game.map.getActiveMapID()) ? 2 : 1), 2.0) + Math.pow(this.getHeight_Real(), 2.0))
      );
      Game.iMaxDistanceManhattan = this.getWidth_Real() + this.getHeight_Real();
      this.iWidthOfSingleBG = (int)(this.gameMap.get(this.gameMapSize - 1).getWidth() * Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.BackgroundScale);
      this.iHeightOfSingleBG = (int)(this.gameMap.get(this.gameMapSize - 1).getHeight() * Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.BackgroundScale);
      this.iWidthOfSingleBG_xMapScale = this.iWidthOfSingleBG * this.iMapScale;
      this.iHeightOfSingleBG_xMapScale = this.iHeightOfSingleBG * this.iMapScale;
      this.iMapScaleBG = this.iMapScale * Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.BackgroundScale;
      MapScale.MINSCALE = (float)CFG.GAME_HEIGHT / this.getHeight();
      if ((float)CFG.GAME_WIDTH / this.getWidth() > MapScale.MINSCALE) {
         MapScale.MINSCALE = (float)CFG.GAME_WIDTH / this.getWidth();
      }

      if (MapScale.MINSCALE > 1.0F) {
         MapScale.MINSCALE = 1.0F;
      }

      this.iBackground2Size_X = Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Size_X;
      this.iBackground2Size_Y = Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Size_Y;
   }

   public final boolean loadMapBG_ZoomOut() {
      if (Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Enable) {
         int j = loadMapBG_FileID++;
         if (j < this.iBackground2Size_Y) {
            for (int i = 0; i < this.iBackground2Size_X; i++) {
               this.gameMap2
                  .add(
                     new Image(
                        ImageManager.loadTexture_RGB888("map/" + Game.map.getFile_ActiveMap_Path() + "background/zoomOut/" + j + "_" + i + ".png"),
                        TextureFilter.Linear,
                        TextureWrap.ClampToEdge
                     )
                  );
            }

            return true;
         }
      }

      return false;
   }

   public final void loadMapBG_ZoomOut_End() {
      if (Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Enable) {
         this.gameMap2Size = this.gameMap2.size();
         this.iWidthOfSingleBG2 = iWidthOfMap / this.iBackground2Size_X;
         this.iHeightOfSingleBG2 = iHeightOfMap / this.iBackground2Size_Y;
         this.iWidthOfSingleBGTech = this.gameMap2.get(0).getWidth();
         this.iHeightOfSingleBGTech = this.gameMap2.get(0).getHeight();
         this.iWidthOfMapTech = 0;

         for (int i = 0; i < this.iBackground2Size_X; i++) {
            this.iWidthOfMapTech = this.iWidthOfMapTech + this.gameMap2.get(i).getWidth();
         }

         this.iHeightOfMapTech = 0;

         for (int i = 0; i < this.iBackground2Size_Y; i++) {
            this.iHeightOfMapTech = this.iHeightOfMapTech + this.gameMap2.get(i * this.iBackground2Size_X).getHeight();
         }
      }
   }

   public final void loadMinimap() {
      this.minimapBG = new Image(
         ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/minimap/" + CFG.getRescouresPath_Short() + "minimapBG.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );
      this.minimapOver = new Image(
         ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/minimap/" + CFG.getRescouresPath_Short() + "minimapOver.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );
      this.scenarioBG = new Image(
         ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/minimap/" + CFG.getRescouresPath_Short() + "scenarioBG.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );
      this.scenarioOver = new Image(
         ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/minimap/" + CFG.getRescouresPath_Short() + "scenarioOver.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );
      this.scenarioMask = new Image(
         ImageManager.loadTexture("map/" + Game.map.getFile_ActiveMap_Path() + "background/minimap/" + CFG.getRescouresPath_Short() + "scenarioMask.png"),
         TextureFilter.Linear,
         TextureWrap.ClampToEdge
      );
   }

   public final void updateActiveMapBGShader() {
      try {
         if (Game.menuManager.getInGame() && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DISEASES) {
            this.mapShader = new MapBG.MapShader() {
               @Override
               public void drawMap(SpriteBatch oSB) {
                  Renderer.setBlackWhite(oSB);
                  MapBG.this.drawMapAnimation = false;
               }

               @Override
               public void drawMapEnd(SpriteBatch oSB) {
                  Renderer.setShaderDefault(oSB);
                  MapBG.this.drawMapAnimation = false;
               }
            };
         } else {
            this.mapShader = new MapBG.MapShader() {
               @Override
               public void drawMap(SpriteBatch oSB) {
                  MapBG.this.drawMapAnimation = true;
               }

               @Override
               public void drawMapEnd(SpriteBatch oSB) {
                  MapBG.this.drawMapAnimation = true;
               }
            };
         }
      } catch (Exception var2) {
         this.mapShader = new MapBG.MapShader() {
            @Override
            public void drawMap(SpriteBatch oSB) {
               MapBG.this.drawMapAnimation = true;
            }

            @Override
            public void drawMapEnd(SpriteBatch oSB) {
               MapBG.this.drawMapAnimation = true;
            }
         };
      }
   }

   protected final void drawMap(SpriteBatch oSB, int nPosX, int nPosY) {
      this.worldMap.drawMap(oSB, nPosX, nPosY);
   }

   protected final void drawMapBorder(SpriteBatch oSB, int nPosX, int nPosY) {
      this.worldMap.drawMapBorder(oSB, nPosX, nPosY);
   }

   public final void loadMapBorder() {
      Json json = new Json();
      FileHandle file = FileManager.loadFile("gfx/map/MapBorder.json");
      this.mapBorder = json.fromJson(MapBG.MapBorder.class, file);
      this.loadOutsideMapImages();
      this.loadOutsideMapImages_Below();
   }

   private final void loadOutsideMapImages() {
      try {
         FileHandle fileList = FileManager.loadFile("gfx/map/OutsideMapImages_Over.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(MapBG.ConfigJson.class, "Data", MapBG.OutsideMapImage.class);
         MapBG.ConfigJson data = json.fromJson(MapBG.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            MapBG.OutsideMapImage tempData = (MapBG.OutsideMapImage)e;
            this.outsideMapImagesData.add(tempData);
            this.outsideMapImages.add(new Image(ImageManager.loadTexture("gfx/map/" + tempData.imageFileName)));
            int tID = this.outsideMapImagesData.size() - 1;
            this.outsideMapImagesData.get(tID).fPosX_Percentage = this.getWidth() * this.outsideMapImagesData.get(tID).fPosX_Percentage
               - (int)(this.outsideMapImages.get(tID).getWidth() * this.outsideMapImagesData.get(tID).fScale) / 2;
            if (this.outsideMapImagesData.get(tID).fPosX_Percentage > this.getWidth()) {
               this.outsideMapImagesData.get(tID).fPosX_Percentage = this.getWidth() - this.outsideMapImages.get(tID).getWidth() - CFG.PADDING * 4;
            }

            if (this.outsideMapImagesData.get(tID).fPosY_Percentage >= 1.0F) {
               this.outsideMapImagesData.get(tID).fPosY_Percentage = this.mapBorder.OutsideTheMapMaxY_Over
                  - (int)(this.outsideMapImages.get(tID).getHeight() * this.outsideMapImagesData.get(tID).fScale);
            } else {
               this.outsideMapImagesData.get(tID).fPosY_Percentage = this.mapBorder.OutsideTheMapMaxY_Over
                     * this.outsideMapImagesData.get(tID).fPosY_Percentage
                  - (int)(this.outsideMapImages.get(tID).getHeight() * this.outsideMapImagesData.get(tID).fScale) / 2;
            }
         }

         this.outsideMapImagesSize = this.outsideMapImages.size();
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   private final void loadOutsideMapImages_Below() {
      try {
         FileHandle fileList = FileManager.loadFile("gfx/map/OutsideMapImages_Below.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(MapBG.ConfigJson.class, "Data", MapBG.OutsideMapImage.class);
         MapBG.ConfigJson data = json.fromJson(MapBG.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            MapBG.OutsideMapImage tempData = (MapBG.OutsideMapImage)e;
            this.outsideMapImagesData_Below.add(tempData);
            this.outsideMapImages_Below.add(new Image(ImageManager.loadTexture("gfx/map/" + tempData.imageFileName)));
            int tID = this.outsideMapImagesData_Below.size() - 1;
            this.outsideMapImagesData_Below.get(tID).fPosX_Percentage = this.getWidth() * this.outsideMapImagesData_Below.get(tID).fPosX_Percentage
               - (int)(this.outsideMapImages_Below.get(tID).getWidth() * this.outsideMapImagesData_Below.get(tID).fScale) / 2;
            if (this.outsideMapImagesData_Below.get(tID).fPosX_Percentage > this.getWidth()) {
               this.outsideMapImagesData_Below.get(tID).fPosX_Percentage = this.getWidth() - this.outsideMapImages_Below.get(tID).getWidth() - CFG.PADDING * 4;
            }

            if (this.outsideMapImagesData_Below.get(tID).fPosY_Percentage <= 0.01F) {
               this.outsideMapImagesData_Below.get(tID).fPosY_Percentage = 0.0F;
            }

            if (this.outsideMapImagesData_Below.get(tID).fPosY_Percentage >= 1.0F) {
               this.outsideMapImagesData_Below.get(tID).fPosY_Percentage = this.mapBorder.OutsideTheMapMaxY_Below
                  - (int)(this.outsideMapImages_Below.get(tID).getHeight() * this.outsideMapImagesData_Below.get(tID).fScale);
            } else {
               this.outsideMapImagesData_Below.get(tID).fPosY_Percentage = this.mapBorder.OutsideTheMapMaxY_Below
                     * this.outsideMapImagesData_Below.get(tID).fPosY_Percentage
                  - (int)(this.outsideMapImages_Below.get(tID).getHeight() * this.outsideMapImagesData_Below.get(tID).fScale) / 2;
            }

            if (this.outsideMapImagesData_Below.get(tID).fPosY_Percentage
                  + this.outsideMapImages_Below.get(tID).getHeight() * this.outsideMapImagesData_Below.get(tID).fScale
               > this.mapBorder.OutsideTheMapMaxY_Below) {
               this.outsideMapImagesData_Below.get(tID).fPosY_Percentage = this.mapBorder.OutsideTheMapMaxY_Below - CFG.PADDING * 2;
            }

            if (this.outsideMapImagesData_Below.get(tID).fPosY_Percentage < 0.0F) {
               this.outsideMapImagesData_Below.get(tID).fPosY_Percentage = 0.0F;
            }
         }

         this.outsideMapImagesSize_Below = this.outsideMapImages_Below.size();
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public final void updateWorldMap() {
      if (this.gameMapSize != 0
         && !Game.menuManager.getInInitGameMenu()
         && !Game.menuManager.getInInitGame_Menus()
         && !Game.menuManager.getInMainMenu()
         && !Game.menuManager.getInLoadGamesList()
         && !Game.menuManager.getInLoadScenario()) {
         if (Game.map.isWorldMap(Game.map.getActiveMapID())) {
            if (Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Enable) {
               this.worldMap = new MapBG.WorldMap() {
                  @Override
                  public void drawMap(SpriteBatch oSB, int nPosX, int nPosY) {
                     try {
                        if (MapBG.this.drawMapAnimation) {
                           if (Game.mapScale.getCurrentScale() > Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Scale) {
                              if (MapBG.this.iBGID == 1) {
                                 MapBG.this.iBGID = 0;
                                 MapBG.this.updateBGAnimationTime();
                              }

                              if (MapBG.this.inAnimation) {
                                 if (CFG.currentTimeMillis
                                    < MapBG.this.animationTime + Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration) {
                                    MapBG.this.drawMapBG_Sea(
                                       oSB,
                                       nPosX,
                                       nPosY,
                                       1.0F
                                          * (float)(CFG.currentTimeMillis - MapBG.this.animationTime)
                                          / Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration
                                    );
                                    MapBG.this.drawMapBG(oSB, nPosX, nPosY, 1.0F);
                                    oSB.setColor(
                                       new Color(
                                          1.0F,
                                          1.0F,
                                          1.0F,
                                          1.0F
                                             - 1.0F
                                                * (float)(CFG.currentTimeMillis - MapBG.this.animationTime)
                                                / Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration
                                       )
                                    );
                                    MapBG.this.drawMapBG2(oSB, nPosX, nPosY);
                                    oSB.setColor(Color.WHITE);
                                 } else {
                                    MapBG.this.drawMapBG_Sea(oSB, nPosX, nPosY, 1.0F);
                                    MapBG.this.inAnimation = false;
                                    MapBG.this.drawMapBG(oSB, nPosX, nPosY, 1.0F);
                                 }
                              } else {
                                 MapBG.this.drawMapBG_Sea(oSB, nPosX, nPosY, 1.0F);
                                 MapBG.this.drawMapBG(oSB, nPosX, nPosY, 1.0F);
                              }
                           } else {
                              if (MapBG.this.iBGID == 0) {
                                 MapBG.this.iBGID = 1;
                                 MapBG.this.updateBGAnimationTime();
                              }

                              if (MapBG.this.inAnimation) {
                                 if (CFG.currentTimeMillis
                                    < MapBG.this.animationTime + Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration) {
                                    MapBG.this.drawMapBG2(oSB, nPosX, nPosY);
                                    MapBG.this.drawMapBG(
                                       oSB,
                                       nPosX,
                                       nPosY,
                                       1.0F
                                          - 1.0F
                                             * (float)(CFG.currentTimeMillis - MapBG.this.animationTime)
                                             / Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration
                                    );
                                    oSB.setColor(Color.WHITE);
                                 } else {
                                    MapBG.this.inAnimation = false;
                                    MapBG.this.drawMapBG2(oSB, nPosX, nPosY);
                                 }
                              } else {
                                 MapBG.this.drawMapBG2(oSB, nPosX, nPosY);
                              }
                           }
                        } else {
                           MapBG.this.drawMapBG_Sea(oSB, nPosX, nPosY, 1.0F);
                           MapBG.this.drawMapBG(oSB, nPosX, nPosY, 1.0F);
                        }
                     } catch (Exception var5) {
                        CFG.exceptionStack(var5);
                     }
                  }

                  @Override
                  public void drawMapBorder(SpriteBatch oSB, int nPosX, int nPosY) {
                     MapBG.this.drawOutsideTheMap_Over(oSB, nPosX, nPosY);
                     MapBG.this.drawOutsideTheMap_Below(oSB, nPosX, nPosY);
                  }
               };
            } else {
               this.worldMap = new MapBG.WorldMap() {
                  @Override
                  public void drawMap(SpriteBatch oSB, int nPosX, int nPosY) {
                     try {
                        MapBG.this.drawMapBG_Sea(oSB, nPosX, nPosY, 1.0F);
                        MapBG.this.drawMapBG(oSB, nPosX, nPosY, 1.0F);
                     } catch (Exception var5) {
                        CFG.exceptionStack(var5);
                     }
                  }

                  @Override
                  public void drawMapBorder(SpriteBatch oSB, int nPosX, int nPosY) {
                     MapBG.this.drawOutsideTheMap_Over(oSB, nPosX, nPosY);
                     MapBG.this.drawOutsideTheMap_Below(oSB, nPosX, nPosY);
                  }
               };
            }
         } else {
            this.worldMap = new MapBG.WorldMap() {
               @Override
               public void drawMap(SpriteBatch oSB, int nPosX, int nPosY) {
                  try {
                     int j = MapBG.this.iBackgroundSize_Y - 1;
                     int currID = MapBG.this.gameMapSize - 1;

                     for (int tempHeight = MapBG.iHeightOfMap - MapBG.this.iHeightOfSingleBG * MapBG.this.iMapScale; j >= 0; j--) {
                        int tempWidth = MapBG.iWidthOfMap;

                        for (int i = MapBG.this.iBackgroundSize_X - 1; i >= 0; i--) {
                           MapBG.this.gameMap
                              .get(currID)
                              .draw(
                                 oSB,
                                 nPosX + tempWidth - MapBG.this.gameMap.get(currID).getWidth() * MapBG.this.iMapScale,
                                 nPosY + tempHeight,
                                 MapBG.this.iMapScaleBG
                              );
                           tempWidth -= MapBG.this.gameMap.get(currID).getWidth() * MapBG.this.iMapScale;
                           currID--;
                        }

                        tempHeight -= MapBG.this.iHeightOfSingleBG * MapBG.this.iMapScale;
                     }

                     Game.mapOver.drawMapOverlay(oSB, nPosX, nPosY, 1.0F);
                  } catch (Exception var9) {
                     CFG.exceptionStack(var9);
                  }
               }

               @Override
               public void drawMapBorder(SpriteBatch oSB, int nPosX, int nPosY) {
                  if (-nPosY + (int)Math.ceil(CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()) > MapBG.this.getHeight()) {
                     ImageManager.getImage(Images.map_border)
                        .draw2(
                           oSB,
                           nPosX + MapBG.this.mapBorder.ShadowY - ImageManager.getImage(Images.map_border).getHeight(),
                           -ImageManager.getImage(Images.map_border).getHeight(),
                           MapBG.this.getHeight() + nPosY,
                           ImageManager.getImage(Images.map_border).getHeight(),
                           -nPosY,
                           0,
                           270.0F,
                           false,
                           true
                        );
                     ImageManager.getImage(Images.map_border)
                        .draw2(
                           oSB,
                           nPosX - MapBG.this.mapBorder.ShadowY + MapBG.this.getWidth(),
                           -ImageManager.getImage(Images.map_border).getHeight(),
                           MapBG.this.getHeight() + nPosY,
                           ImageManager.getImage(Images.map_border).getHeight(),
                           -nPosY,
                           0,
                           270.0F,
                           false,
                           false
                        );
                  } else {
                     ImageManager.getImage(Images.map_border)
                        .draw2(
                           oSB,
                           nPosX + MapBG.this.mapBorder.ShadowY - ImageManager.getImage(Images.map_border).getHeight(),
                           -ImageManager.getImage(Images.map_border).getHeight(),
                           (int)Math.ceil(CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()),
                           ImageManager.getImage(Images.map_border).getHeight(),
                           -nPosY,
                           0,
                           270.0F,
                           false,
                           true
                        );
                     ImageManager.getImage(Images.map_border)
                        .draw2(
                           oSB,
                           nPosX - MapBG.this.mapBorder.ShadowY + MapBG.this.getWidth(),
                           -ImageManager.getImage(Images.map_border).getHeight(),
                           (int)Math.ceil(CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()),
                           ImageManager.getImage(Images.map_border).getHeight(),
                           -nPosY,
                           0,
                           270.0F,
                           false,
                           false
                        );
                  }

                  MapBG.this.drawOutsideTheMap_Over(oSB, nPosX, nPosY);
                  MapBG.this.drawOutsideTheMap_Below(oSB, nPosX, nPosY);
               }
            };
         }
      } else {
         this.worldMap = new MapBG.WorldMap() {
            @Override
            public void drawMap(SpriteBatch oSB, int nPosX, int nPosY) {
            }

            @Override
            public void drawMapBorder(SpriteBatch oSB, int nPosX, int nPosY) {
            }
         };
      }
   }

   private final void updateBGAnimationTime() {
      if (this.inAnimation) {
         this.animationTime = CFG.currentTimeMillis
            - (Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_AnimationDuration - (CFG.currentTimeMillis - this.animationTime));
      } else {
         this.animationTime = CFG.currentTimeMillis;
      }

      this.inAnimation = true;
   }

   public void updateMapBGSea() {
      if (!CFG.isDesktop() && GameValues.value.MOBILE_DISABLE_SEA_WAVES) {
         mapBGSea = new MapBG.MapBGSea() {
            @Override
            public void drawMapSea(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
            }
         };
      } else {
         mapBGSea = new MapBG.MapBGSea() {
            @Override
            public void drawMapSea(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
               oSB.setColor(
                  new Color(
                     Renderer.BACKGROUND_COLOR.r,
                     Renderer.BACKGROUND_COLOR.g,
                     Renderer.BACKGROUND_COLOR.b,
                     Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.SeaOverAlpha * fAlpha * Math.min(1.0F, Game.mapScale.getCurrentScale())
                  )
               );
               Game.mapOverSea.drawMapOverlaySea(oSB, nPosX, nPosY, fAlpha);
               oSB.setColor(Color.WHITE);
               Renderer.setShaderWater2(oSB);
               ImageManager.getImage(Images.waves)
                  .draw2(
                     oSB,
                     0,
                     0,
                     (int)(CFG.GAME_WIDTH / Game.mapScale.getCurrentScale()),
                     (int)(CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()),
                     -nPosX,
                     -nPosY
                  );
               Renderer.setShaderDefault(oSB);
            }
         };
      }
   }

   private final void drawMapBG_Sea(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
      mapBGSea.drawMapSea(oSB, nPosX, nPosY, fAlpha);
   }

   private final void drawMapBG(SpriteBatch oSB, int nPosX, int nPosY, float fAlpha) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      this.mapShader.drawMap(oSB);
      if (this.secondSideOfMap) {
         int j = this.iBackgroundSize_Y - 1;
         int currID = this.gameMapSize - 1;

         for (int tempHeight = iHeightOfMap - this.iHeightOfSingleBG_xMapScale; j >= 0; j--) {
            if (!Game.inViewY(tempHeight, tempHeight + this.iHeightOfSingleBG_xMapScale)) {
               currID -= this.iBackgroundSize_X;
            } else {
               int tempWidth = iWidthOfMap * 2;

               for (int i = this.iBackgroundSize_X - 1; i >= 0; i--) {
                  if (Game.inViewX(tempWidth - this.iWidthOfSingleBG_xMapScale, tempWidth)
                     || Game.inViewX2(tempWidth - this.iWidthOfSingleBG_xMapScale, tempWidth)) {
                     this.gameMap.get(currID).draw(oSB, nPosX + tempWidth - this.iWidthOfSingleBG_xMapScale, nPosY + tempHeight, this.iMapScaleBG);
                  }

                  tempWidth -= this.iWidthOfSingleBG_xMapScale;
                  currID--;
               }
            }

            tempHeight -= this.iHeightOfSingleBG_xMapScale;
         }
      }

      int j = this.iBackgroundSize_Y - 1;
      int currID = this.gameMapSize - 1;

      for (int tempHeight = iHeightOfMap - this.iHeightOfSingleBG_xMapScale; j >= 0; j--) {
         if (!Game.inViewY(tempHeight, tempHeight + this.iHeightOfSingleBG_xMapScale)) {
            currID -= this.iBackgroundSize_X;
         } else {
            int tempWidth = iWidthOfMap;

            for (int i = this.iBackgroundSize_X - 1; i >= 0; i--) {
               if (Game.inViewX(tempWidth - this.iWidthOfSingleBG_xMapScale, tempWidth)
                  || Game.inViewX2(tempWidth - this.iWidthOfSingleBG_xMapScale, tempWidth)) {
                  this.gameMap.get(currID).draw(oSB, nPosX + tempWidth - this.iWidthOfSingleBG_xMapScale, nPosY + tempHeight, this.iMapScaleBG);
               }

               tempWidth -= this.iWidthOfSingleBG_xMapScale;
               currID--;
            }
         }

         tempHeight -= this.iHeightOfSingleBG_xMapScale;
      }

      this.mapShader.drawMapEnd(oSB);
      Game.mapOver.drawMapOverlay(oSB, nPosX, nPosY, fAlpha);
   }

   private final void drawMapBG2(SpriteBatch oSB, int nPosX, int nPosY) {
      this.mapShader.drawMap(oSB);
      if (this.secondSideOfMap) {
         int j = this.iBackground2Size_Y - 1;
         int currID = this.gameMap2Size - 1;

         for (int tempHeight = iHeightOfMap - this.iHeightOfSingleBG2; j >= 0; j--) {
            int tempWidth = iWidthOfMap * 2;

            for (int i = this.iBackground2Size_X - 1; i >= 0; i--) {
               this.gameMap2
                  .get(currID)
                  .draw(oSB, nPosX + tempWidth - this.iWidthOfSingleBG2, nPosY + tempHeight, this.iWidthOfSingleBG2, this.iHeightOfSingleBG2);
               tempWidth -= this.iWidthOfSingleBG2;
               currID--;
            }

            tempHeight -= this.iHeightOfSingleBG2;
         }
      }

      int j = this.iBackground2Size_Y - 1;
      int currID = this.gameMap2Size - 1;

      for (int tempHeight = iHeightOfMap - this.iHeightOfSingleBG2; j >= 0; j--) {
         int tempWidth = iWidthOfMap;

         for (int i = this.iBackground2Size_X - 1; i >= 0; i--) {
            this.gameMap2
               .get(currID)
               .draw(oSB, nPosX + tempWidth - this.iWidthOfSingleBG2, nPosY + tempHeight, this.iWidthOfSingleBG2, this.iHeightOfSingleBG2);
            tempWidth -= this.iWidthOfSingleBG2;
            currID--;
         }

         tempHeight -= this.iHeightOfSingleBG2;
      }

      this.mapShader.drawMapEnd(oSB);
   }

   public final void drawMapBG2_TechTree(SpriteBatch oSB, int nPosX, int nPosY) {
      int j = this.iBackground2Size_Y - 1;
      int currID = this.gameMap2Size - 1;

      for (int tempHeight = this.iHeightOfMapTech - this.iHeightOfSingleBGTech; j >= 0; j--) {
         int tempWidth = this.iWidthOfMapTech;

         for (int i = this.iBackground2Size_X - 1; i >= 0; i--) {
            this.gameMap2
               .get(currID)
               .draw(oSB, nPosX + tempWidth - this.iWidthOfSingleBGTech, nPosY + tempHeight, this.iWidthOfSingleBGTech, this.iHeightOfSingleBGTech);
            tempWidth -= this.iWidthOfSingleBGTech;
            currID--;
         }

         tempHeight -= this.iHeightOfSingleBGTech;
      }
   }

   private final int drawOutsideTheMap_Shadow() {
      return this.mapBorder.ShadowY * 3;
   }

   public void updateOutsideTheMap_Scale() {
      if (!this.isOutsideInView && !this.isOutsideInView_Bot || !(Math.abs(this.outsideScale2 - Game.mapScale.getCurrentScale()) < 0.245F)) {
         this.outsideScale2 = Game.mapScale.getCurrentScale();
         if (Game.mapScale.getCurrentScale() >= 0.95F) {
            this.outsideScale = 1.0F;
         } else {
            this.outsideScale = 1.0F / Game.mapScale.getCurrentScale();
         }
      }
   }

   private final void drawOutsideTheMap_Over(SpriteBatch oSB, int nPosX, int nPosY) {
      if (Game.mapCoords.getPosY() < -this.mapBorder.ShadowY) {
         this.isOutsideInView = false;
      } else {
         this.updateOutsideTheMap_Scale();
         this.isOutsideInView = true;
         ImageManager.getImage(Images.outsideMap)
            .draw2_Scale(
               oSB,
               nPosX,
               nPosY - this.mapBorder.OutsideTheMapMaxY_Over - ImageManager.getImage(Images.map_border).getHeight() + this.mapBorder.ShadowY,
               this.getWidth() * 2,
               this.mapBorder.OutsideTheMapMaxY_Over,
               this.outsideScale
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(oSB, nPosX, nPosY - this.mapBorder.OutsideTheMapMaxY_Over / 2, this.getWidth() * 2, this.mapBorder.OutsideTheMapMaxY_Over / 2, false, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               nPosX,
               nPosY - this.mapBorder.OutsideTheMapMaxY_Over - ImageManager.getImage(Images.map_border).getHeight(),
               this.getWidth() * 2,
               this.mapBorder.OutsideTheMapMaxY_Over / 8,
               false,
               false
            );
         oSB.setColor(Color.WHITE);
         this.drawOutsideTheMap_OverImages(oSB, nPosX, nPosY);
         if (Game.mapCoords.getSecondSideOfMap()) {
            this.drawOutsideTheMap_OverImages(oSB, nPosX + this.getWidth(), nPosY);
         }

         ImageManager.getImage(Images.map_border)
            .draw2(
               oSB,
               0,
               nPosY + this.mapBorder.ShadowY - ImageManager.getImage(Images.map_border).getHeight(),
               (int)Math.ceil(CFG.GAME_WIDTH / Game.mapScale.getCurrentScale()),
               ImageManager.getImage(Images.map_border).getHeight(),
               -nPosX,
               0
            );
      }
   }

   private final void drawOutsideTheMap_OverImages(SpriteBatch oSB, int nPosX, int nPosY) {
      for (int i = 0; i < this.outsideMapImagesSize; i++) {
         this.outsideMapImages
            .get(i)
            .draw(
               oSB,
               nPosX + (int)this.outsideMapImagesData.get(i).fPosX_Percentage,
               nPosY
                  - this.mapBorder.OutsideTheMapMaxY_Over
                  - ImageManager.getImage(Images.map_border).getHeight()
                  + this.mapBorder.ShadowY
                  + (int)this.outsideMapImagesData.get(i).fPosY_Percentage,
               this.outsideMapImagesData.get(i).fScale
            );
      }
   }

   private final void drawOutsideTheMap_Below(SpriteBatch oSB, int nPosX, int nPosY) {
      if (-Game.mapCoords.getPosY() + CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale() < this.getHeight() - this.mapBorder.ShadowY) {
         this.isOutsideInView_Bot = false;
      } else {
         this.updateOutsideTheMap_Scale();
         this.isOutsideInView_Bot = true;
         ImageManager.getImage(Images.outsideMap)
            .draw2_Scale(
               oSB,
               nPosX,
               nPosY + this.getHeight() + ImageManager.getImage(Images.map_border).getHeight() - this.mapBorder.ShadowY,
               this.getWidth() * 2,
               this.mapBorder.OutsideTheMapMaxY_Below,
               false,
               true,
               this.outsideScale
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               nPosX,
               nPosY + this.getHeight() + ImageManager.getImage(Images.map_border).getHeight() - this.mapBorder.ShadowY,
               this.getWidth() * 2,
               this.mapBorder.OutsideTheMapMaxY_Below / 2
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               nPosX,
               nPosY
                  + this.getHeight()
                  + ImageManager.getImage(Images.map_border).getHeight()
                  - this.mapBorder.ShadowY
                  + this.mapBorder.OutsideTheMapMaxY_Below
                  - this.mapBorder.OutsideTheMapMaxY_Below / 8,
               this.getWidth() * 2,
               this.mapBorder.OutsideTheMapMaxY_Below / 8,
               false,
               true
            );
         oSB.setColor(Color.WHITE);
         this.drawOutsideTheMap_BelowImages(oSB, nPosX, nPosY);
         if (Game.mapCoords.getSecondSideOfMap()) {
            this.drawOutsideTheMap_BelowImages(oSB, nPosX + this.getWidth(), nPosY);
         }

         ImageManager.getImage(Images.map_border)
            .draw2(
               oSB,
               0,
               nPosY - this.mapBorder.ShadowY + this.getHeight(),
               (int)Math.ceil(CFG.GAME_WIDTH / Game.mapScale.getCurrentScale()),
               ImageManager.getImage(Images.map_border).getHeight(),
               -nPosX,
               0,
               0.0F,
               false,
               true
            );
      }
   }

   private final void drawOutsideTheMap_BelowImages(SpriteBatch oSB, int nPosX, int nPosY) {
      for (int i = 0; i < this.outsideMapImagesSize_Below; i++) {
         this.outsideMapImages_Below
            .get(i)
            .draw(
               oSB,
               nPosX + (int)this.outsideMapImagesData_Below.get(i).fPosX_Percentage,
               nPosY
                  + this.getHeight()
                  + ImageManager.getImage(Images.map_border).getHeight()
                  - this.mapBorder.ShadowY
                  + (int)this.outsideMapImagesData_Below.get(i).fPosY_Percentage,
               this.outsideMapImagesData_Below.get(i).fScale
            );
      }
   }

   public int getWidth() {
      return iWidthOfMap;
   }

   public int getHeight() {
      return iHeightOfMap;
   }

   public int getWidth_Real() {
      return this.iWidthOfMap_Real;
   }

   public int getHeight_Real() {
      return this.iHeightOfMap_Real;
   }

   public final void dispose() {
      for (int i = 0; i < this.gameMap.size(); i++) {
         this.gameMap.get(i).getTexture().dispose();
      }

      this.gameMap.clear();
      this.gameMapSize = 0;

      for (int i = 0; i < this.gameMap2.size(); i++) {
         this.gameMap2.get(i).getTexture().dispose();
      }

      this.gameMap2.clear();
      this.gameMap2Size = 0;
      iHeightOfMap = 0;
      iWidthOfMap = 0;
      if (this.minimapBG != null) {
         this.minimapBG.dispose();
         this.minimapBG = null;
      }

      if (this.minimapOver != null) {
         this.minimapOver.dispose();
         this.minimapOver = null;
      }

      if (this.scenarioMask != null) {
         this.scenarioMask.dispose();
         this.scenarioMask = null;
      }

      if (this.scenarioBG != null) {
         this.scenarioBG.dispose();
         this.scenarioBG = null;
      }

      if (this.scenarioOver != null) {
         this.scenarioOver.dispose();
         this.scenarioOver = null;
      }
   }

   protected final void updateMinimapScaleXY() {
      this.updateMinimapScaleX();
      this.updateMinimapScaleY();
   }

   protected final void updateMinimapScaleX() {
      this.fMinimapScaleX = (float)this.getWidth() / this.iMinimapWidth;
   }

   protected final void updateMinimapScaleY() {
      this.fMinimapScaleY = (float)this.getHeight() / this.iMinimapHeight;
   }

   public final void updateMinimapResolution() {
      this.iMinimapHeight = this.minimapBG.getHeight();
      this.updateMinimapScaleY();
      this.iMinimapWidth = (int)(this.getWidth() / this.fMinimapScaleY);
      this.updateMinimapScaleX();
   }

   public final float getMinimapScaled_ScaleX() {
      return (float)this.iMinimapScaled_Width / this.iMinimapWidth;
   }

   public final float getMinimapScaled_ScaleY() {
      return (float)this.iMinimapScaled_Height / this.iMinimapHeight;
   }

   protected final void updatePreviewScaleX() {
      this.fPreviewScaleX = (float)this.getWidth() / this.iPreviewWidth;
   }

   protected final void updatePreviewScaleY() {
      this.fPreviewScaleY = (float)this.getHeight() / this.iPreviewHeight;
   }

   public final void updatePreviewResolution() {
      this.iPreviewHeight = this.scenarioBG.getHeight();
      this.updatePreviewScaleY();
      this.iPreviewWidth = (int)(this.getWidth() / this.fPreviewScaleY);
      this.updatePreviewScaleX();
   }

   public final float getPreviewScaled_ScaleX() {
      return (float)this.iPreviewScaled_Width / this.iPreviewWidth;
   }

   public final float getPreviewScaled_ScaleY() {
      return (float)this.iPreviewScaled_Height / this.iPreviewHeight;
   }

   protected final void drawMap(SpriteBatch oSB, int nPosX, int nPosY, float scale) {
      scale *= this.iMapScale;
      int j = this.iBackgroundSize_Y - 1;
      int currID = this.gameMapSize - 1;

      for (int tempHeight = (int)(iHeightOfMap - this.iHeightOfSingleBG * scale); j >= 0; j--) {
         int tempWidth = iWidthOfMap;

         for (int i = this.iBackgroundSize_X - 1; i >= 0; i--) {
            this.gameMap.get(currID).draw(oSB, nPosX + tempWidth - (int)(this.gameMap.get(currID).getWidth() * scale), nPosY + tempHeight, scale);
            tempWidth = (int)(tempWidth - this.gameMap.get(currID).getWidth() * scale);
            currID--;
         }

         tempHeight = (int)(tempHeight - this.iHeightOfSingleBG * scale);
      }
   }

   public final void drawMinimapTexture_Generate(SpriteBatch oSB) {
      if (this.minimapOfCivilizations == null
         && !Game.menuManager.getInInitGameMenu()
         && !Game.menuManager.getInInitGame_Menus()
         && !Game.menuManager.getInLoadScenario()
         && this.gameMap.size() > 0) {
         try {
            int extraScale = 1;

            try {
               Renderer.clipView_End(oSB);

               try {
                  oSB.end();
               } catch (Exception var37) {
                  oSB.begin();
                  oSB.end();
               }

               this.minimapBelowZero = false;
               int tMinX = this.getWidth();
               int tMaxX = -this.getWidth();
               int tMinY = this.getHeight();
               int tMaxY = 0;
               int numOfProvinces = 0;
               int var40 = 0;
               int var44 = 0;
               tMaxX = this.getWidth();
               tMaxY = this.getHeight();
               int tempExtra = (int)((tMaxX - var40) * 0.125F);
               var40 -= tempExtra;
               tMaxX += tempExtra;
               tempExtra = (int)((tMaxY - var44) * 0.125F);
               var44 -= tempExtra;
               tMaxY += tempExtra;
               if (var44 < 0) {
                  var44 = 0;
               }

               int tPosX = 0;
               int tPosY = 0;
               float tScale = 1.0F;
               tPosX = var40;
               tPosY = var44;
               int tWidth = tMaxX - var40;
               int tHeight = tMaxY - var44;
               if ((float)(tMaxX - var40) / this.getWidth() >= (float)(tMaxY - var44) / this.getHeight()) {
                  tHeight = (int)((float)(tMaxX - var40) / this.getWidth() * this.getHeight());
                  tPosY = var44 + (tMaxY - var44) / 2 - tHeight / 2;
                  tScale = this.getHeight() / ((float)(tMaxX - var40) / this.getWidth() * this.getHeight());
               } else {
                  tWidth = (int)((float)(tMaxY - var44) / this.getHeight() * this.getWidth());
                  tPosX = var40 + (tMaxX - var40) / 2 - tWidth / 2;
                  tScale = this.getWidth() / ((float)(tMaxY - var44) / this.getHeight() * this.getWidth());
               }

               tPosY = Math.max(0, tPosY);
               if ((float)tWidth / this.getWidth() >= 0.95F || (float)tHeight / this.getHeight() >= 0.95F || var44 < 0 || tMaxY >= this.getHeight()) {
                  tPosX = 0;
                  tPosY = 0;
                  tScale = 1.0F;
                  tWidth = this.getWidth();
                  tHeight = this.getHeight();
               }

               this.iMinimapScaled_PosX = tPosX;
               this.iMinimapScaled_PosY = tPosY;
               this.iMinimapScaled_Width = tWidth;
               this.iMinimapScaled_Height = tHeight;
               this.fMinimapScaled_Scale = tScale;
               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH, -CFG.GAME_HEIGHT);
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var36) {
                  oSB.end();
                  oSB.begin();
               }

               this.minimapBG.draw(oSB, 0, 0, this.iMinimapWidth * extraScale, this.iMinimapHeight * extraScale);

               try {
                  oSB.end();
               } catch (Exception var35) {
                  oSB.begin();
                  oSB.end();
               }

               Renderer.viewport
                  .setWorldSize(
                     CFG.GAME_WIDTH * ((float)this.getWidth() / this.iMinimapWidth) / extraScale,
                     CFG.GAME_HEIGHT * ((float)this.getHeight() / this.iMinimapHeight) / extraScale
                  );
               Renderer.viewport.apply();
               Renderer.camera
                  .setToOrtho(
                     true,
                     CFG.GAME_WIDTH * ((float)this.getWidth() / this.iMinimapWidth) / extraScale,
                     -(CFG.GAME_HEIGHT * ((float)this.getHeight() / this.iMinimapHeight)) / extraScale
                  );
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var34) {
                  oSB.end();
                  oSB.begin();
               }

               Renderer.clipView_Start(oSB, 0, CFG.GAME_HEIGHT, this.iMinimapWidth * extraScale, -this.iMinimapHeight * extraScale);
               oSB.setColor(Color.WHITE);
               oSB.setShader(Renderer.shaderDefaultProvince);
               ProvinceDraw.drawProvinces_Minimap(oSB, -((int)(tPosX * tScale)), -((int)(tPosY * tScale)), tScale, 220);
               if (tPosX + this.getWidth() * tScale > this.getWidth()) {
                  ProvinceDraw.drawProvinces_Minimap(oSB, -((int)(tPosX * tScale)) + (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220);
               }

               if (tPosX < 0) {
                  ProvinceDraw.drawProvinces_Minimap(oSB, -((int)(tPosX * tScale)) - (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220);
                  this.minimapBelowZero = true;
               }

               oSB.setShader(Renderer.shaderDefault);
               Renderer.clipView_End(oSB);

               try {
                  oSB.end();
               } catch (Exception var33) {
                  oSB.begin();
                  oSB.end();
               }
            } finally {
               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH, -CFG.GAME_HEIGHT);
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var32) {
                  oSB.end();
                  oSB.begin();
               }

               this.minimapOfCivilizations = new Image(
                  new Texture(
                     ScreenUtils.getFrameBufferPixmap(
                        0, CFG.GAME_HEIGHT - this.iMinimapHeight * extraScale, this.iMinimapWidth * extraScale, this.iMinimapHeight * extraScale
                     )
                  )
               );
               oSB.setColor(Color.BLACK);
               Images.pix.draw(oSB, 0, 0, this.iMinimapWidth, this.iMinimapHeight);
               oSB.setColor(Color.WHITE);

               try {
                  oSB.end();
               } catch (Exception var31) {
                  oSB.begin();
                  oSB.end();
               }

               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH / Game.mapScale.getCurrentScale(), CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale());
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH / Game.mapScale.getCurrentScale(), -CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale());
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var30) {
                  oSB.end();
                  oSB.begin();
               }

               oSB.setShader(Renderer.shaderDefault);
            }
         } catch (Exception var39) {
            CFG.exceptionStack(var39);
            this.requestToDisposeMinimap = true;
         }
      }
   }

   public final void disposeMinimapOfCivilizations() {
      try {
         if (this.minimapOfCivilizations != null) {
            this.requestToDisposeMinimap = true;
         }
      } catch (Exception var2) {
      }
   }

   public final void disposeMinimapOfCivilizations_Real() {
      try {
         if (this.minimapOfCivilizations != null) {
            this.minimapOfCivilizations.getTexture().dispose();
            this.minimapOfCivilizations = null;
            this.requestToDisposeMinimap = false;
         }
      } catch (Exception var2) {
      }
   }

   public final void previewTexture_Generate(SpriteBatch oSB) {
      if (this.previewOfCivilizations != null) {
         this.previewOfCivilizations.dispose();
         this.previewOfCivilizations = null;
      }

      if (this.previewOfCivilizations == null && this.gameMap.size() > 0) {
         try {
            int extraScale = 1;

            try {
               Renderer.clipView_End(oSB);

               try {
                  oSB.end();
               } catch (Exception var40) {
                  oSB.begin();
                  oSB.end();
               }

               this.minimapBelowZero = false;
               int numOfProvinces = 0;
               int tMinX = 0;
               int tMinY = 0;
               int tMaxX = this.getWidth();
               int tMaxY = this.getHeight();
               int tempExtra = (int)((tMaxX - tMinX) * 0.125F);
               tMinX -= tempExtra;
               tMaxX += tempExtra;
               tempExtra = (int)((tMaxY - tMinY) * 0.125F);
               tMinY -= tempExtra;
               tMaxY += tempExtra;
               if (tMinY < 0) {
                  tMinY = 0;
               }

               int tPosX = 0;
               int tPosY = 0;
               float tScale = 1.0F;
               tPosX = tMinX;
               tPosY = tMinY;
               int tWidth = tMaxX - tMinX;
               int tHeight = tMaxY - tMinY;
               if ((float)(tMaxX - tMinX) / this.getWidth() >= (float)(tMaxY - tMinY) / this.getHeight()) {
                  tHeight = (int)((float)(tMaxX - tMinX) / this.getWidth() * this.getHeight());
                  tPosY = tMinY + (tMaxY - tMinY) / 2 - tHeight / 2;
                  tScale = this.getHeight() / ((float)(tMaxX - tMinX) / this.getWidth() * this.getHeight());
               } else {
                  tWidth = (int)((float)(tMaxY - tMinY) / this.getHeight() * this.getWidth());
                  tPosX = tMinX + (tMaxX - tMinX) / 2 - tWidth / 2;
                  tScale = this.getWidth() / ((float)(tMaxY - tMinY) / this.getHeight() * this.getWidth());
               }

               tPosY = Math.max(0, tPosY);
               if ((float)tWidth / this.getWidth() >= 0.95F || (float)tHeight / this.getHeight() >= 0.95F || tMinY < 0 || tMaxY >= this.getHeight()) {
                  tPosX = 0;
                  tPosY = 0;
                  tScale = 1.0F;
                  tWidth = this.getWidth();
                  tHeight = this.getHeight();
               }

               this.iPreviewScaled_PosX = tPosX;
               this.iPreviewScaled_PosY = tPosY;
               this.iPreviewScaled_Width = tWidth;
               this.iPreviewScaled_Height = tHeight;
               this.fPreviewScaled_Scale = tScale;
               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH, -CFG.GAME_HEIGHT);
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var39) {
                  oSB.end();
                  oSB.begin();
               }

               this.scenarioBG.draw(oSB, 0, 0, this.iPreviewWidth * extraScale, this.iPreviewHeight * extraScale);

               try {
                  oSB.end();
               } catch (Exception var38) {
                  oSB.begin();
                  oSB.end();
               }

               Renderer.viewport
                  .setWorldSize(
                     CFG.GAME_WIDTH * ((float)this.getWidth() / this.iPreviewWidth) / extraScale,
                     CFG.GAME_HEIGHT * ((float)this.getHeight() / this.iPreviewHeight) / extraScale
                  );
               Renderer.viewport.apply();
               Renderer.camera
                  .setToOrtho(
                     true,
                     CFG.GAME_WIDTH * ((float)this.getWidth() / this.iPreviewWidth) / extraScale,
                     -(CFG.GAME_HEIGHT * ((float)this.getHeight() / this.iPreviewHeight)) / extraScale
                  );
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var37) {
                  oSB.end();
                  oSB.begin();
               }

               Renderer.clipView_Start(oSB, 0, CFG.GAME_HEIGHT, this.iPreviewWidth * extraScale, -this.iPreviewHeight * extraScale);
               oSB.setColor(Color.WHITE);
               oSB.setShader(Renderer.shaderDefaultProvince);
               if (Game.FOG_OF_WAR) {
                  ProvinceDraw.drawProvinces_FogOfWarDiscovery(oSB, -((int)(tPosX * tScale)), -((int)(tPosY * tScale)), tScale, 220);
               } else {
                  ProvinceDraw.drawProvinces(oSB, -((int)(tPosX * tScale)), -((int)(tPosY * tScale)), tScale, 220);
               }

               if (tPosX + this.getWidth() * tScale > this.getWidth()) {
                  if (Game.FOG_OF_WAR) {
                     ProvinceDraw.drawProvinces_FogOfWarDiscovery(
                        oSB, -((int)(tPosX * tScale)) + (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220
                     );
                  } else {
                     ProvinceDraw.drawProvinces(oSB, -((int)(tPosX * tScale)) + (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220);
                  }
               }

               if (tPosX < 0) {
                  if (Game.FOG_OF_WAR) {
                     ProvinceDraw.drawProvinces_FogOfWarDiscovery(
                        oSB, -((int)(tPosX * tScale)) - (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220
                     );
                  } else {
                     ProvinceDraw.drawProvinces(oSB, -((int)(tPosX * tScale)) - (int)(this.getWidth() * tScale), -((int)(tPosY * tScale)), tScale, 220);
                  }

                  this.minimapBelowZero = true;
               }

               oSB.setShader(Renderer.shaderDefault);
               Renderer.clipView_End(oSB);

               try {
                  oSB.end();
               } catch (Exception var36) {
                  oSB.begin();
                  oSB.end();
               }
            } finally {
               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH, -CFG.GAME_HEIGHT);
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var35) {
                  oSB.end();
                  oSB.begin();
               }

               this.previewOfCivilizations = new Image(
                  new Texture(
                     ScreenUtils.getFrameBufferPixmap(
                        0, CFG.GAME_HEIGHT - this.iPreviewHeight * extraScale, this.iPreviewWidth * extraScale, this.iPreviewHeight * extraScale
                     )
                  )
               );

               try {
                  this.previewOfCivilizations.getTexture().getTextureData().prepare();
               } catch (Exception var34) {
               }

               if (FileManager.IS_MAC) {
                  PixmapIO.writePNG(
                     Gdx.files.external("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "preview.png"),
                     EditorMap_PrintMap.flipPixmap(this.previewOfCivilizations.getTexture().getTextureData().consumePixmap())
                  );
               } else {
                  PixmapIO.writePNG(
                     Gdx.files.local("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "preview.png"),
                     EditorMap_PrintMap.flipPixmap(this.previewOfCivilizations.getTexture().getTextureData().consumePixmap())
                  );
               }

               oSB.setColor(Color.BLACK);
               Images.pix.draw(oSB, 0, 0, this.iPreviewWidth, this.iPreviewHeight);
               oSB.setColor(Color.WHITE);

               try {
                  oSB.end();
               } catch (Exception var33) {
                  oSB.begin();
                  oSB.end();
               }

               Renderer.viewport.setWorldSize(CFG.GAME_WIDTH / Game.mapScale.getCurrentScale(), CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale());
               Renderer.viewport.apply();
               Renderer.camera.setToOrtho(true, CFG.GAME_WIDTH / Game.mapScale.getCurrentScale(), -CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale());
               oSB.setProjectionMatrix(Renderer.camera.combined);

               try {
                  oSB.begin();
               } catch (Exception var32) {
                  oSB.end();
                  oSB.begin();
               }

               oSB.setShader(Renderer.shaderDefault);
            }
         } catch (Exception var42) {
            CFG.exceptionStack(var42);
         }
      }
   }

   public static class ConfigJson {
      String Age_of_History;
      ArrayList Data;
   }

   public interface MapBGSea {
      void drawMapSea(SpriteBatch var1, int var2, int var3, float var4);
   }

   public static class MapBorder {
      public int ShadowY;
      public int OutsideTheMapMaxY_Over;
      public int OutsideTheMapMaxY_Below;
   }

   protected interface MapShader {
      void drawMap(SpriteBatch var1);

      void drawMapEnd(SpriteBatch var1);
   }

   public static class OutsideMapImage {
      String imageFileName;
      float fPosX_Percentage;
      float fPosY_Percentage;
      float fScale;
   }

   private interface WorldMap {
      void drawMap(SpriteBatch var1, int var2, int var3);

      void drawMapBorder(SpriteBatch var1, int var2, int var3);
   }
}
