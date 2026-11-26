package aoc.kingdoms.lukasz.menusInGame.Technology;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleTechTree;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menus.InitGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.JoinType;

public class InGame_TechnologyTree extends Menu {
   public static SparksAnimation sparksAnimation = new SparksAnimation();
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public int iMapPosY = 0;
   public List<InGame_TechnologyTree.TechLine> lines = new ArrayList<>();
   public int iLinesSize = 0;
   public static int centerToTechID = -1;

   public InGame_TechnologyTree() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int paddingTop = CFG.PADDING * 4;
      int titleHeight = ImageManager.getImage(Images.titleTechTree).getHeight();
      int menuWidth = CFG.GAME_WIDTH;
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title928).getHeight();
      int centerToPosX = 0;

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         menuElements.add(
            new ButtonTechnology(
               TechnologyTree.getTechBG(i, Game.player.iCivID),
               i,
               100 + (TechnologyTree.iTechnologyWidth + 100) * TechnologyTree.lTechnology.get(i).TreeColumn,
               15 + (TechnologyTree.iTechnologyHeight + 15) * TechnologyTree.lTechnology.get(i).TreeRow,
               true,
               Game.player.playerData.techQueue.getTechIsInQueue(i)
            )
         );
         if (centerToTechID >= 0) {
            if (i == centerToTechID) {
               centerToPosX = Math.max(centerToPosX, menuElements.get(menuElements.size() - 1).getPosX());
            }
         } else if (Game.getCiv(Game.player.iCivID).getTechResearched(i)) {
            centerToPosX = Math.max(centerToPosX, menuElements.get(menuElements.size() - 1).getPosX());
         }

         if (TechnologyTree.lTechnology.get(i).RequiredTech >= 0) {
            this.lines
               .add(
                  new InGame_TechnologyTree.TechLine(
                     i,
                     100
                        + TechnologyTree.iTechnologyWidth / 2
                        + (TechnologyTree.iTechnologyWidth + 100) * TechnologyTree.lTechnology.get(TechnologyTree.lTechnology.get(i).RequiredTech).TreeColumn,
                     15
                        + TechnologyTree.iTechnologyHeight / 2
                        + (TechnologyTree.iTechnologyHeight + 15) * TechnologyTree.lTechnology.get(TechnologyTree.lTechnology.get(i).RequiredTech).TreeRow,
                     100 + TechnologyTree.iTechnologyWidth / 2 + (TechnologyTree.iTechnologyWidth + 100) * TechnologyTree.lTechnology.get(i).TreeColumn,
                     15 + TechnologyTree.iTechnologyHeight / 2 + (TechnologyTree.iTechnologyHeight + 15) * TechnologyTree.lTechnology.get(i).TreeRow,
                     Game.getCiv(Game.player.iCivID).getTechResearched(i)
                  )
               );
         }

         if (TechnologyTree.lTechnology.get(i).RequiredTech2 >= 0) {
            this.lines
               .add(
                  new InGame_TechnologyTree.TechLine(
                     i,
                     100
                        + TechnologyTree.iTechnologyWidth / 2
                        + (TechnologyTree.iTechnologyWidth + 100) * TechnologyTree.lTechnology.get(TechnologyTree.lTechnology.get(i).RequiredTech2).TreeColumn,
                     15
                        + TechnologyTree.iTechnologyHeight / 2
                        + (TechnologyTree.iTechnologyHeight + 15) * TechnologyTree.lTechnology.get(TechnologyTree.lTechnology.get(i).RequiredTech2).TreeRow,
                     100 + TechnologyTree.iTechnologyWidth / 2 + (TechnologyTree.iTechnologyWidth + 100) * TechnologyTree.lTechnology.get(i).TreeColumn,
                     15 + TechnologyTree.iTechnologyHeight / 2 + (TechnologyTree.iTechnologyHeight + 15) * TechnologyTree.lTechnology.get(i).TreeRow,
                     Game.getCiv(Game.player.iCivID).getTechResearched(i)
                  )
               );
         }
      }

      int buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      int tMaxX = 0;
      int ix = 0;

      for (int iSizex = menuElements.size(); ix < iSizex; ix++) {
         if (menuElements.get(ix).getPosX() + menuElements.get(ix).getWidth() > tMaxX) {
            tMaxX = menuElements.get(ix).getPosX() + menuElements.get(ix).getWidth();
         }
      }

      tMaxX += 100;
      menuElements.add(new Empty(0, 0, tMaxX, buttonY));
      buttonY = (int)Math.max((float)buttonY, (TechnologyTree.iTechnologyHeight + 15) * (GameValues.inGame.TECH_TREE_NUM_OF_ROWS + 0.25F) + paddingTop * 2);
      this.initMenu(new MenuTitleTechTree(Game.lang.get("TechnologyTree"), titleHeight, false, false) {
         @Override
         public long getTime() {
            return InGame_TechnologyTree.lTime;
         }
      }, 0, CFG.GAME_HEIGHT / 2 - i / 2, menuWidth, i, menuElements, false, true);
      this.iMapPosY = (int)(Math.max(0, Game.mapBG.iHeightOfMapTech - CFG.GAME_HEIGHT) * (Game.oR.nextInt(100) / 100.0F));
      this.iLinesSize = this.lines.size();
      if (centerToPosX != 0) {
         this.setMenuPosX(-centerToPosX + CFG.GAME_WIDTH / 4);
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      float fA = 1.0F;
      if (lTime + 60L >= CFG.currentTimeMillis) {
         fA = (float)(CFG.currentTimeMillis - lTime) / 60.0F;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.6F * fA));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            iTranslateX,
            this.getPosY() - this.getTitle().getHeight() - CFG.BUTTON_HEIGHT2 / 2 + iTranslateY,
            CFG.GAME_WIDTH,
            CFG.BUTTON_HEIGHT2 / 2,
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT2 / 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.3F * fA));
      Images.gradientXY
         .draw(oSB, iTranslateX, this.getPosY() - this.getTitle().getHeight() - CFG.BUTTON_HEIGHT2 / 2 + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT2 / 2);
      Images.gradientXY.draw(oSB, iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT2 / 2, false, true);
      oSB.setColor(Color.WHITE);
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      ImageManager.getImage(Images.insideTechTree)
         .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false, true);
      Renderer.clipView_Start(oSB, this.getPosX() + iTranslateX, CFG.GAME_HEIGHT - (this.getPosY() + iTranslateY), this.getWidth(), -this.getHeight());
      Renderer.setShaderWater(oSB);
      ImageManager.getImage(Images.waves).draw2(oSB, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, -this.getMenuPosX(), -this.getMenuPosY());
      Renderer.setShaderDefault(oSB);
      if (Game.map.getActiveMap_MapData().mapData.BackgroundZoomOut_Enable) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
         Game.mapBG.drawMapBG2_TechTree(oSB, this.getPosX() + this.getMenuPosX() + iTranslateX, this.getPosY() + iTranslateY);
         oSB.setColor(Color.WHITE);
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
         oSB.setShader(Renderer.shaderAlpha);
         InitGame.background.getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         Images.gradientFull
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         Images.gradientXY
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setColor(Color.WHITE);
      }

      Renderer.clipView_End(oSB);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.25F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() - 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() - 1, false, true);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.35F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() - 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 4);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 4, false, true);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
      sparksAnimation.draw2(
         oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - Images.sparkHeight + iTranslateY, this.getWidth(), Images.sparkHeight
      );
      oSB.setColor(Color.WHITE);

      for (int i = 0; i < this.iLinesSize; i++) {
         this.lines.get(i).lPointsX.get(0);
         int j = 0;

         for (int jSize = this.lines.get(i).lPointsX.size(); j < jSize; j++) {
            this.lines.get(i).nPath.get(j).x = this.getPosX() + this.getMenuPosX() + iTranslateX + this.lines.get(i).lPointsX.get(j);
            this.lines.get(i).nPath.get(j).y = -(this.getMenuPosY() + iTranslateY + this.lines.get(i).lPointsY.get(j));
         }

         Renderer.shapeDrawer.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
         Renderer.shapeDrawer.path(this.lines.get(i).nPath, 3.0F, JoinType.SMOOTH, true);
         if (this.lines.get(i).researched) {
            Renderer.shapeDrawer.setColor(new Color(0.39215687F, 0.33333334F, 0.19607843F, 1.0F));
         } else {
            Renderer.shapeDrawer.setColor(new Color(0.3137255F, 0.3137255F, 0.3137255F, 1.0F));
         }

         Renderer.shapeDrawer.path(this.lines.get(i).nPath, 1.0F, JoinType.SMOOTH, true);
      }

      Renderer.oSBBorder.end();
      Renderer.oSBBorder.begin();
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_TechnologyTree();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_TechnologyTree(false);
   }

   public class TechLine {
      public boolean researched = false;
      public int iTechID;
      private Array<Vector2> nPath = new Array<>();
      public List<Integer> lPointsX = new ArrayList<>();
      public List<Integer> lPointsY = new ArrayList<>();

      public TechLine(int iTechID, int iX, int iY, int iX2, int iY2, boolean researched) {
         this.iTechID = iTechID;
         this.researched = researched;
         this.lPointsX.add(iX);
         this.lPointsY.add(iY);
         if (iY != iY2) {
            int mod = iY > iY2 ? -1 : 1;
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 - 8);
            this.lPointsY.add(iY);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 - 6);
            this.lPointsY.add(iY + 1 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 - 4);
            this.lPointsY.add(iY + 2 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 - 2);
            this.lPointsY.add(iY + 4 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 - 1);
            this.lPointsY.add(iY + 6 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50);
            this.lPointsY.add(iY + 8 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50);
            this.lPointsY.add(iY2 - 8 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 + 1);
            this.lPointsY.add(iY2 - 6 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 + 2);
            this.lPointsY.add(iY2 - 4 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 + 4);
            this.lPointsY.add(iY2 - 2 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 + 6);
            this.lPointsY.add(iY2 - 1 * mod);
            this.lPointsX.add(iX2 - TechnologyTree.iTechnologyWidth / 2 - 50 + 8);
            this.lPointsY.add(iY2);
         }

         this.lPointsX.add(iX2);
         this.lPointsY.add(iY2);

         for (int i = this.lPointsX.size() - 1; i >= 0; i--) {
            this.nPath.add(new Vector2(0.0F, 0.0F));
         }
      }
   }
}
