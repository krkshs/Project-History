package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.setting.SettingsProvince;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class ProvinceBorder {
   private List<ProvinceBorderLine> provinceBorderLine = new ArrayList<>();
   private Array<Vector2> nPath = new Array<>();
   private int iProvinceBorderLineSize;
   private int iLineWidth = 0;
   public int withProvinceID;
   private boolean civilizationBorder = false;
   private boolean wastelandBorder = false;
   public boolean isLocked = false;
   public static final float PROVINCE_BORDER_INTERVAL = 425.0F;
   public long animationTime = 0L;
   private int pathLastPointX;
   private int pathLastPointY;
   public List<Integer> lPointsX = new ArrayList<>();
   public List<Integer> lPointsY = new ArrayList<>();
   public ProvinceBorder.DrawProvinceBorder drawProvinceBorder;
   public static ProvinceBorder.DrawCivBorder drawCivBorder;
   public static ProvinceBorder.DrawCivBorder drawCivBorderWar;
   public static int mapCordsPosY = 0;
   public static float pathProvinceBorderExtraWidth = 1.0F;
   public static float pathProvinceBorderExtraWidth2 = 1.0F;

   public ProvinceBorder(int nWithProvinceID, List<Integer> nPointsX, List<Integer> nPointsY) {
      this.withProvinceID = nWithProvinceID;
      if (nPointsX.size() > 0) {
         this.pathLastPointX = nPointsX.get(nPointsX.size() - 1) * Game.mapBG.iMapScale;
         this.pathLastPointY = nPointsY.get(nPointsY.size() - 1) * Game.mapBG.iMapScale;
      }

      int i = 0;

      for (int iSize = nPointsX.size() - 1; i < iSize; i++) {
         this.provinceBorderLine
            .add(
               new ProvinceBorderLine(
                  nPointsX.get(i) * Game.mapBG.iMapScale,
                  nPointsY.get(i) * Game.mapBG.iMapScale,
                  nPointsX.get(i + 1) * Game.mapBG.iMapScale,
                  nPointsY.get(i + 1) * Game.mapBG.iMapScale
               )
            );
      }

      i = 0;

      for (int iSize = nPointsX.size(); i < iSize; i++) {
         this.lPointsX.add(nPointsX.get(i));
         this.lPointsY.add(nPointsY.get(i));
      }

      this.iProvinceBorderLineSize = this.provinceBorderLine.size();

      for (int ix = 0; ix < this.iProvinceBorderLineSize; ix++) {
         this.iLineWidth = this.iLineWidth + this.provinceBorderLine.get(ix).getWidth();
      }

      for (int ix = 0; ix < this.iProvinceBorderLineSize; ix++) {
         this.nPath.add(new Vector2(this.provinceBorderLine.get(ix).getPosX(), -this.provinceBorderLine.get(ix).getPosY()));
      }

      this.nPath.add(new Vector2(this.pathLastPointX, -this.pathLastPointY));
   }

   public final void updateDrawProvinceBorder(int nProvinceID) {
      try {
         if (this.isLocked) {
            return;
         }

         if (this.wastelandBorder) {
            if (this.civilizationBorder) {
               if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS) {
                  if (Game.settingsManager.SETTINGS_PROVINCE_BORDER == 1) {
                     this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                        @Override
                        public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                           oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                           ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                        }
                     };
                  } else {
                     this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                        @Override
                        public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                           ProvinceBorder.this.drawStraightBorder_Shape(nTranslateProvincePosX, joinType, lineWidth);
                        }
                     };
                  }
               } else if (Game.mapScale.getCurrentScale() > SettingsProvince.value.DRAW_BORDERS) {
                  this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                     @Override
                     public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                        ProvinceBorder.this.drawStraightBorder_Shape2(nTranslateProvincePosX, joinType, lineWidth);
                     }
                  };
               } else {
                  this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                     @Override
                     public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     }
                  };
               }
            } else {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                     ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                  }
               };
            }
         } else if (this.civilizationBorder) {
            if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS) {
               if (Game.settingsManager.SETTINGS_PROVINCE_BORDER == 1) {
                  this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                     @Override
                     public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                        oSB.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
                        ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                     }
                  };
               } else if (!GameValues.provinceBorderWar.ENABLE_WAR_BORDER
                  || !DiplomacyManager.isAtWar(Game.player.iCivID, Game.getProvince(nProvinceID).getCivID())
                     && !DiplomacyManager.isAtWar(Game.player.iCivID, Game.getProvince(this.withProvinceID).getCivID())) {
                  this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                     @Override
                     public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                        ProvinceBorder.this.drawStraightBorder_Shape(nTranslateProvincePosX, joinType, lineWidth);
                     }
                  };
               } else {
                  this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                     @Override
                     public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                        ProvinceBorder.this.drawStraightBorder_ShapeWar(nTranslateProvincePosX, joinType, lineWidth);
                     }
                  };
               }
            } else if (Game.mapScale.getCurrentScale() > SettingsProvince.value.DRAW_BORDERS) {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     ProvinceBorder.this.drawStraightBorder_Shape2(nTranslateProvincePosX, joinType, lineWidth);
                  }
               };
            } else {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                  }
               };
            }
         } else if (Game.getProvince(nProvinceID).getWasteland() >= 0) {
            this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
               @Override
               public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
               }
            };
         } else if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS) {
            this.animationTime = CFG.currentTimeMillis;
            if (!Game.getProvince(nProvinceID).getSeaProvince() && !Game.getProvince(this.withProvinceID).getSeaProvince()) {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                     float tempPerc = (float)(CFG.currentTimeMillis - ProvinceBorder.this.animationTime) / 425.0F;
                     if (tempPerc >= 1.0F) {
                        ProvinceBorder.this.drawDashedBorder_Percentage(oSB, nTranslateProvincePosX, 1.0F);
                        ProvinceBorder.this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                           @Override
                           public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                              oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                              ProvinceBorder.this.drawInnerBorder(oSB, nTranslateProvincePosX);
                           }
                        };
                     } else {
                        ProvinceBorder.this.drawDashedBorder_Percentage(oSB, nTranslateProvincePosX, tempPerc);
                     }
                  }
               };
            } else if (Game.getProvince(nProvinceID).getSeaProvince() && Game.getProvince(this.withProvinceID).getSeaProvince()) {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     float tempPerc = (float)(CFG.currentTimeMillis - ProvinceBorder.this.animationTime) / 425.0F;
                     if (tempPerc >= 1.0F) {
                        oSB.setColor(CFG.COLOR_PROVINCE_SEABYSEA);
                        ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                        ProvinceBorder.this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                           @Override
                           public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                              oSB.setColor(CFG.COLOR_PROVINCE_SEABYSEA);
                              ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                           }
                        };
                     } else {
                        oSB.setColor(CFG.COLOR_PROVINCE_SEABYSEA);
                        ProvinceBorder.this.drawStraightBorder_Percentage(oSB, nTranslateProvincePosX, tempPerc);
                     }
                  }
               };
            } else {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                  }
               };
            }
         } else {
            this.animationTime = CFG.currentTimeMillis;
            if (!Game.getProvince(nProvinceID).getSeaProvince() && !Game.getProvince(this.withProvinceID).getSeaProvince()) {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     oSB.setColor(CFG.COLOR_PROVINCE_DASHED);
                     float tempPerc = (float)(CFG.currentTimeMillis - ProvinceBorder.this.animationTime) / 425.0F;
                     if (tempPerc >= 1.0F) {
                        ProvinceBorder.this.drawDashedBorder_Percentage(oSB, nTranslateProvincePosX, 0.0F);
                        ProvinceBorder.this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                           @Override
                           public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                           }
                        };
                     } else {
                        ProvinceBorder.this.drawDashedBorder_Percentage(oSB, nTranslateProvincePosX, 1.0F - tempPerc);
                     }
                  }
               };
            } else if (Game.getProvince(nProvinceID).getSeaProvince() && Game.getProvince(this.withProvinceID).getSeaProvince()) {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                     float tempPerc = (float)(CFG.currentTimeMillis - ProvinceBorder.this.animationTime) / 425.0F;
                     if (tempPerc >= 1.0F) {
                        ProvinceBorder.this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                           @Override
                           public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                           }
                        };
                     } else {
                        oSB.setColor(CFG.COLOR_PROVINCE_SEABYSEA);
                        ProvinceBorder.this.drawStraightBorder(oSB, nTranslateProvincePosX);
                     }
                  }
               };
            } else {
               this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
                  @Override
                  public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
                  }
               };
            }
         }
      } catch (GdxRuntimeException var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateDrawProvinceBorder_ActiveProvince() {
      this.isLocked = true;
      this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
         @Override
         public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
            if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS) {
               if (ProvinceBorder.this.getIsCivilizationBorder() || ProvinceBorder.this.getIsWastelandBorder()) {
                  ProvinceBorder.this.drawStraightBorder_Shape(nTranslateProvincePosX, joinType, lineWidth);
                  Renderer.oSBBorder.end();
                  Renderer.oSBBorder.begin();
               }

               oSB.setColor(
                  new Color(
                     CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r,
                     CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g,
                     CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b,
                     Game.activeProvince_Animation_Data.getBorderAlpha() / 255.0F
                  )
               );
               ProvinceBorder.this.drawDashedBorder(oSB, ProvinceBorderManager.iLineOffset, nTranslateProvincePosX);
            } else if (ProvinceBorder.this.getIsCivilizationBorder() || ProvinceBorder.this.getIsWastelandBorder()) {
               if (Game.mapScale.getCurrentScale() >= Game.DRAW_INNER_BORDERS) {
                  ProvinceBorder.this.drawStraightBorder_Shape(nTranslateProvincePosX, joinType, lineWidth);
               } else {
                  ProvinceBorder.this.drawStraightBorder_Shape2(nTranslateProvincePosX, joinType, lineWidth);
               }
            }
         }
      };
   }

   public final void updateDrawProvinceBorder_ActiveCivilizationBorder() {
      this.isLocked = true;
      this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
         @Override
         public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
            oSB.setColor(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER);
            ProvinceBorder.this.drawStraightBorder_Shape(
               nTranslateProvincePosX, joinType, lineWidth, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER2
            );
         }
      };
   }

   public final void updateDrawProvinceBorder_RelationUp() {
      this.isLocked = true;
      this.drawProvinceBorder = new ProvinceBorder.DrawProvinceBorder() {
         @Override
         public void draw(SpriteBatch oSB, int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
            oSB.setColor(Colors.HOVER_POSITIVE);
            ProvinceBorder.this.drawStraightBorder_Shape(
               nTranslateProvincePosX,
               joinType,
               lineWidth,
               Colors.HOVER_POSITIVE,
               new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER2.a)
            );
         }
      };
   }

   public static final void updateDrawCivBorder() {
      if (Game.settingsManager.SETTINGS_PROVINCE_BORDER == 0) {
         drawCivBorder = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
            }
         };
         drawCivBorderWar = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
            }
         };
      } else if (Game.settingsManager.SETTINGS_PROVINCE_BORDER == 1) {
         drawCivBorder = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
            }
         };
         drawCivBorderWar = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
            }
         };
      } else if (SettingsProvince.value.ENABLE_DOUBLE_BORDER && Game.settingsManager.DOUBLE_BORDER) {
         drawCivBorder = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
               Renderer.shapeDrawer
                  .path2_Double(
                     nPath,
                     lineWidth,
                     joinType,
                     true,
                     offsetX,
                     offsetY,
                     ProvinceDraw.joinType_Shadow,
                     ProvinceBorder.pathProvinceBorderExtraWidth,
                     nColor,
                     nColor2
                  );
            }
         };
         drawCivBorderWar = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
               Renderer.shapeDrawer
                  .path2_Double(
                     nPath,
                     lineWidth,
                     joinType,
                     true,
                     nTranslateProvincePosX,
                     ProvinceBorder.mapCordsPosY,
                     ProvinceDraw.joinType_Shadow,
                     ProvinceBorder.pathProvinceBorderExtraWidth,
                     CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE,
                     CFG.COLOR_PROVINCE_STRAIGHT2_WAR
                  );
            }
         };
      } else {
         drawCivBorder = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
               Renderer.shapeDrawer.setColor(nColor);
               Renderer.shapeDrawer.path2(nPath, ProvinceBorder.pathProvinceBorderExtraWidth2, joinType, true, offsetX, offsetY);
            }
         };
         drawCivBorderWar = new ProvinceBorder.DrawCivBorder() {
            @Override
            public void drawCivBorder(
               int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2, Array<Vector2> nPath, float offsetX, float offsetY
            ) {
               Renderer.shapeDrawer.setColor(CFG.COLOR_PROVINCE_STRAIGHT_WAR_ACTIVE);
               Renderer.shapeDrawer.path2(nPath, ProvinceBorder.pathProvinceBorderExtraWidth2, joinType, true, offsetX, offsetY);
            }
         };
      }
   }

   public final void drawStraightBorder_Shape(int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor, Color nColor2) {
      drawCivBorder.drawCivBorder(nTranslateProvincePosX, joinType, lineWidth, nColor, nColor2, this.nPath, nTranslateProvincePosX, mapCordsPosY);
   }

   public final void drawStraightBorder_Shape2(int nTranslateProvincePosX, JoinType joinType, float lineWidth, Color nColor) {
      Renderer.shapeDrawer.setColor(new Color(nColor.r, nColor.g, nColor.b, nColor.a));
      Renderer.shapeDrawer.path2(this.nPath, lineWidth, joinType, true, nTranslateProvincePosX, mapCordsPosY);
      Renderer.shapeDrawer.setColor(nColor);
      Renderer.shapeDrawer.path(this.nPath, pathProvinceBorderExtraWidth2, joinType, true);
   }

   public final void drawStraightBorder_Shape(int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
      drawCivBorder.drawCivBorder(
         nTranslateProvincePosX,
         joinType,
         lineWidth,
         CFG.COLOR_PROVINCE_STRAIGHT,
         CFG.COLOR_PROVINCE_STRAIGHT2,
         this.nPath,
         nTranslateProvincePosX,
         mapCordsPosY
      );
   }

   public final void drawStraightBorder_ShapeWar(int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
      drawCivBorderWar.drawCivBorder(
         nTranslateProvincePosX,
         joinType,
         lineWidth,
         CFG.COLOR_PROVINCE_STRAIGHT,
         CFG.COLOR_PROVINCE_STRAIGHT2,
         this.nPath,
         nTranslateProvincePosX,
         mapCordsPosY
      );
   }

   public final void drawStraightBorder_Shape2(int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
      Renderer.shapeDrawer.setColor(CFG.COLOR_PROVINCE_STRAIGHT);
      Renderer.shapeDrawer.path2(this.nPath, pathProvinceBorderExtraWidth2, ProvinceDraw.joinType_Shadow, true, nTranslateProvincePosX, mapCordsPosY);
   }

   public final void drawStraightBorder_Shape_Sea(int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
      Renderer.shapeDrawer.setColor(new Color(CFG.COLOR_PROVINCE_SEABYSEA.r, CFG.COLOR_PROVINCE_SEABYSEA.g, CFG.COLOR_PROVINCE_SEABYSEA.b, 0.05F));
      Renderer.shapeDrawer.path2(this.nPath, lineWidth, joinType, true, nTranslateProvincePosX, mapCordsPosY);
   }

   public final void drawStraightBorder_Shape_Sea(int nTranslateProvincePosX, JoinType joinType, float lineWidth, float fPercentage) {
      Renderer.shapeDrawer
         .setColor(new Color(CFG.COLOR_PROVINCE_SEABYSEA.r, CFG.COLOR_PROVINCE_SEABYSEA.g, CFG.COLOR_PROVINCE_SEABYSEA.b, 0.05F * fPercentage));
      Renderer.shapeDrawer.path2(this.nPath, lineWidth, joinType, true, nTranslateProvincePosX, mapCordsPosY);
   }

   public final void drawStraightBorder_Shape_ActiveProvince(int nTranslateProvincePosX, JoinType joinType, float lineWidth) {
      Renderer.shapeDrawer.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
      Renderer.shapeDrawer.path2(this.nPath, pathProvinceBorderExtraWidth, ProvinceDraw.joinType_Shadow, true, nTranslateProvincePosX, mapCordsPosY);
   }

   public final void drawStraightBorder(SpriteBatch oSB, int nTranslateProvincePosX) {
      for (int i = 0; i < this.iProvinceBorderLineSize; i++) {
         ProvinceBorderLine borderLine = this.provinceBorderLine.get(i);
         Images.pix
            .draw2(
               oSB,
               nTranslateProvincePosX + borderLine.getPosX(),
               Game.mapCoords.getPosY() + borderLine.getPosY(),
               borderLine.getWidth(),
               1,
               borderLine.getAngle()
            );
      }
   }

   protected final void drawStraightBorder_Percentage(SpriteBatch oSB, int nTranslateProvincePosX, float fPercent) {
      int lineWidth = (int)(this.iLineWidth * fPercent);
      int i = 0;

      for (int currentWidth = 0; i < this.iProvinceBorderLineSize && currentWidth <= lineWidth; i++) {
         ProvinceBorderLine borderLine = this.provinceBorderLine.get(i);
         Images.pix
            .draw2(
               oSB,
               nTranslateProvincePosX + borderLine.getPosX(),
               Game.mapCoords.getPosY() + borderLine.getPosY(),
               borderLine.getWidth(),
               1,
               borderLine.getAngle(),
               currentWidth
            );
         currentWidth += borderLine.getWidth();
      }
   }

   public final void drawInnerBorder_Shape(int nTranslateProvincePosX) {
   }

   public final void drawInnerBorder(SpriteBatch oSB, int nTranslateProvincePosX) {
      this.drawDashedBorder(oSB, 0, nTranslateProvincePosX);
   }

   public final void drawDashedBorder(SpriteBatch oSB, int offsetX, int nTranslateProvincePosX) {
      for (int i = 0; i < this.iProvinceBorderLineSize; i++) {
         ProvinceBorderLine borderLine = this.provinceBorderLine.get(i);
         Images.imgLine_32
            .draw2(
               oSB,
               nTranslateProvincePosX + borderLine.getPosX(),
               Game.mapCoords.getPosY() + borderLine.getPosY(),
               borderLine.getWidth(),
               1,
               borderLine.getAngle(),
               offsetX
            );
         offsetX += borderLine.getWidth();
      }
   }

   protected final void drawDashedBorder_Percentage(SpriteBatch oSB, int nTranslateProvincePosX, float fPercent) {
      int lineWidth = (int)(this.iLineWidth * fPercent);
      int i = 0;

      for (int currentWidth = 0; i < this.iProvinceBorderLineSize && currentWidth <= lineWidth; i++) {
         ProvinceBorderLine borderLine = this.provinceBorderLine.get(i);
         Images.imgLine_32
            .draw2(
               oSB,
               nTranslateProvincePosX + borderLine.getPosX(),
               Game.mapCoords.getPosY() + borderLine.getPosY(),
               borderLine.getWidth(),
               1,
               borderLine.getAngle(),
               currentWidth
            );
         currentWidth += borderLine.getWidth();
      }
   }

   public final int getWithProvinceID() {
      return this.withProvinceID;
   }

   public final boolean getIsWastelandBorder() {
      return this.wastelandBorder;
   }

   public final boolean getIsCivilizationBorder() {
      return this.civilizationBorder;
   }

   public final void setIsCivilizationBorder(boolean civilizationBorder, int iProvinceID) {
      this.civilizationBorder = civilizationBorder;
      this.updateDrawProvinceBorder(iProvinceID);
   }

   public final void setIsCivilizationBorder_Just(boolean civilizationBorder, int iProvinceID) {
      this.civilizationBorder = civilizationBorder;
   }

   protected final void setIsWastelandBorder(boolean wastelandBorder, int iProvinceID) {
      this.wastelandBorder = wastelandBorder;
      this.updateDrawProvinceBorder(iProvinceID);
   }

   public interface DrawCivBorder {
      void drawCivBorder(int var1, JoinType var2, float var3, Color var4, Color var5, Array<Vector2> var6, float var7, float var8);
   }

   public interface DrawProvinceBorder {
      void draw(SpriteBatch var1, int var2, JoinType var3, float var4);
   }
}
