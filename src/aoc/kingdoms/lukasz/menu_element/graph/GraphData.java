package aoc.kingdoms.lukasz.menu_element.graph;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class GraphData {
   private static final float ALPHA_CIV_LINE = 0.8F;
   private int iCivID;
   private List<Integer> lPointsY;
   private int iPointsSize = 0;
   private Array<Vector2> lVectorPoints;
   private int iBeginTurnID;
   private boolean drawData = true;
   private boolean visible = true;
   private boolean backAnimation = false;
   protected static final int ANIMATION_TIME = 450;
   private long lTime = 0L;

   protected GraphData(int iCivID, List<Integer> nPointsY, int iBeginTurnID) {
      this.iCivID = iCivID;
      this.iPointsSize = nPointsY.size();
      this.lPointsY = new ArrayList<>();
      this.lVectorPoints = new Array<>();

      for (int i = 0; i < this.iPointsSize; i++) {
         this.lPointsY.add(nPointsY.get(i));
      }

      this.iBeginTurnID = iBeginTurnID;
      this.drawData = false;
   }

   protected final void draw(SpriteBatch oSB, int iPosX, int iPosY, int iWidth, int iHeight, List<Integer> nPointsPosX, int id, boolean active, int iFixPosY) {
      if (this.lTime + 450L >= CFG.currentTimeMillis) {
         this.drawAnimation(oSB, iPosX, iPosY, iWidth, iHeight, nPointsPosX, id, active, iFixPosY);
      } else {
         this.drawGraphData(oSB, iPosX, iPosY - iFixPosY, nPointsPosX, id, active);
      }
   }

   protected final void drawAnimation(
      SpriteBatch oSB, int iPosX, int iPosY, int iWidth, int iHeight, List<Integer> nPointsPosX, int id, boolean active, int iFixPosY
   ) {
      if (this.backAnimation) {
         Renderer.clipView_Start(oSB, iPosX, CFG.GAME_HEIGHT - iPosY, iWidth - (int)(iWidth * ((float)(CFG.currentTimeMillis - this.lTime) / 450.0F)), -iHeight);
      } else {
         Renderer.clipView_Start(oSB, iPosX, CFG.GAME_HEIGHT - iPosY, (int)(iWidth * ((float)(CFG.currentTimeMillis - this.lTime) / 450.0F)), -iHeight);
      }

      this.drawGraphData(oSB, iPosX, iPosY - iFixPosY, nPointsPosX, id, true);
      Renderer.clipView_End(oSB);
   }

   private final void drawGraphData(SpriteBatch oSB, int iPosX, int iPosY, List<Integer> nPointsPosX, int id, boolean active) {
      try {
         oSB.setColor(new Color(Game.getCiv(this.iCivID).getR(), Game.getCiv(this.iCivID).getG(), Game.getCiv(this.iCivID).getB(), active ? 1.0F : 0.8F));
      } catch (IndexOutOfBoundsException var10) {
         oSB.setColor(new Color(0.05882353F, 0.05882353F, 0.05882353F, active ? 1.0F : 0.8F));
      }

      if (this.lVectorPoints.size > 1) {
         Array<Vector2> nPath = new Array<>();
         nPath.add(new Vector2(iPosX + this.lVectorPoints.get(0).x, -iPosY + -this.lVectorPoints.get(0).y));
         int i = 1;

         for (int iSize = this.lVectorPoints.size; i < iSize; i++) {
            if (this.lVectorPoints.get(i).x != this.lVectorPoints.get(i - 1).x) {
               nPath.add(new Vector2(iPosX + this.lVectorPoints.get(i).x, -iPosY + -this.lVectorPoints.get(i).y));
            }
         }

         Renderer.shapeDrawer.setColor(Graph.GRAPH_LINE_COLOR);
         Renderer.shapeDrawer.path(nPath, 1.0F, JoinType.SMOOTH, true);
         Renderer.oSBBorder.end();
         Renderer.oSBBorder.begin();
      }
   }

   protected final void drawCivButton(SpriteBatch oSB, int iPosX, int iPosY, boolean active) {
      oSB.setColor(
         new Color(
            Graph.GRAPH_BG_COLOR.r,
            Graph.GRAPH_BG_COLOR.g,
            Graph.GRAPH_BG_COLOR.b,
            active ? Graph.GRAPH_BG_COLOR.a * 2.0F : (this.drawData ? Graph.GRAPH_BG_COLOR.a : Graph.GRAPH_BG_COLOR.a / 4.0F)
         )
      );
      Images.pix.draw(oSB, iPosX, iPosY, Graph.getGraphButtonWidth(), Graph.getGraphButtonHeight());
      oSB.setColor(
         new Color(Graph.GRAPH_BORDERS_COLOR.r, Graph.GRAPH_BORDERS_COLOR.g, Graph.GRAPH_BORDERS_COLOR.b, this.drawData ? Graph.GRAPH_BORDERS_COLOR.a : 0.25F)
      );

      try {
         oSB.setColor(new Color(Game.getCiv(this.iCivID).getR(), Game.getCiv(this.iCivID).getG(), Game.getCiv(this.iCivID).getB(), this.drawData ? 0.8F : 0.4F));
      } catch (IndexOutOfBoundsException var7) {
         oSB.setColor(new Color(0.05882353F, 0.05882353F, 0.05882353F, this.drawData ? 0.8F : 0.4F));
      }

      Images.pix.draw(oSB, iPosX, iPosY, CFG.CIV_COLOR_WIDTH, Graph.getGraphButtonHeight());
      oSB.setColor(this.drawData ? Color.WHITE : new Color(1.0F, 1.0F, 1.0F, 0.25F));

      try {
         Game.getCiv(this.iCivID)
            .getFlag()
            .draw(
               oSB,
               iPosX + Graph.getGraphButtonWidth() / 2 - CFG.CIV_FLAG_WIDTH / 2,
               iPosY + Graph.getGraphButtonHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2,
               CFG.CIV_FLAG_WIDTH,
               CFG.CIV_FLAG_HEIGHT
            );
      } catch (IndexOutOfBoundsException var6) {
         ImageManager.getImage(Images.randomCivilizationFlag)
            .draw(
               oSB,
               iPosX + Graph.getGraphButtonWidth() / 2 - CFG.CIV_FLAG_WIDTH / 2,
               iPosY + Graph.getGraphButtonHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - ImageManager.getImage(Images.randomCivilizationFlag).getHeight(),
               CFG.CIV_FLAG_WIDTH,
               CFG.CIV_FLAG_HEIGHT
            );
      }

      oSB.setColor(Color.WHITE);
   }

   protected final void buildGraph(int iHeight, int nMinPoint, int nMaxPoint, List<Integer> nPointsPosX) {
      this.lVectorPoints.clear();

      for (int i = 0; i < this.iPointsSize; i++) {
         this.lVectorPoints
            .add(
               new Vector2(
                  nPointsPosX.get(this.iBeginTurnID + i).intValue(),
                  iHeight - iHeight * (100.0F * this.lPointsY.get(i).intValue()) / (nMaxPoint - nMinPoint) / 100.0F
               )
            );
      }
   }

   protected final int getPointY(int i) {
      try {
         return this.lPointsY.get(i);
      } catch (IndexOutOfBoundsException var3) {
         return 0;
      }
   }

   protected final int getPointsSize() {
      return this.iPointsSize;
   }

   protected final int getCivID() {
      return this.iCivID;
   }

   protected final int getBeginTurnID() {
      return this.iBeginTurnID;
   }

   protected final boolean getDrawData() {
      return this.drawData;
   }

   protected final void setDrawData(boolean drawData) {
      if (drawData != this.drawData) {
         if (this.lTime <= CFG.currentTimeMillis - 450L || !this.drawData && !this.backAnimation) {
            this.lTime = CFG.currentTimeMillis;
         } else {
            this.lTime = CFG.currentTimeMillis - (450L - (CFG.currentTimeMillis - this.lTime));
         }

         this.backAnimation = !drawData;
      }

      this.drawData = drawData;
   }

   protected final boolean getVisible() {
      return this.visible;
   }

   protected final void setVisible(boolean visible) {
      this.visible = visible;
   }

   protected final boolean getBackAnimation() {
      return this.backAnimation;
   }

   protected final void setBackAnimation(boolean backAnimation) {
      this.backAnimation = backAnimation;
   }

   protected final long getTime() {
      return this.lTime;
   }
}
