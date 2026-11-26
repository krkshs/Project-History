package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Missions.MissionTree;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleTechTree;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMissionTree;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMissionTreeCiv;
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

public class InGame_MissionTree extends Menu {
   public static SparksAnimation sparksAnimation = new SparksAnimation();
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public int iMapPosY = 0;
   public List<InGame_MissionTree.TechLine> lines = new ArrayList<>();
   public int iLinesSize = 0;

   public InGame_MissionTree() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int paddingTop = CFG.PADDING * 4;
      int titleHeight = ImageManager.getImage(Images.titleTechTree).getHeight();
      int menuWidth = CFG.GAME_WIDTH;
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title928).getHeight();
      int centerToPosX = 0;
      if (Game.getCiv(Game.player.iCivID).iMissionsSize > 0) {
         for (int i = 0; i < Game.getCiv(Game.player.iCivID).iMissionsSize; i++) {
            boolean missionUnlocked = MissionTree.haveUnlockedMission_Civ(Game.player.iCivID, i);
            boolean missionCanBeUnlocked = MissionTree.canRunMission_Civ(Game.player.iCivID, i);
            menuElements.add(
               new ButtonMissionTreeCiv(
                  i,
                  Game.getCiv(Game.player.iCivID).lMissions.get(i).ImageID,
                  100 + (MissionTree.iMissionWidth + 100) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeColumn,
                  15 + (MissionTree.iMissionHeight + 15) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeRow,
                  missionUnlocked,
                  missionCanBeUnlocked
               )
            );
            if (Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission >= 0) {
               this.lines
                  .add(
                     new InGame_MissionTree.TechLine(
                        i,
                        100
                           + MissionTree.iMissionWidth / 2
                           + (MissionTree.iMissionWidth + 100)
                              * Game.getCiv(Game.player.iCivID).lMissions.get(Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission).TreeColumn,
                        15
                           + MissionTree.iMissionHeight / 2
                           + (MissionTree.iMissionHeight + 15)
                              * Game.getCiv(Game.player.iCivID).lMissions.get(Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission).TreeRow,
                        100 + MissionTree.iMissionWidth / 2 + (MissionTree.iMissionWidth + 100) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeColumn,
                        15 + MissionTree.iMissionHeight / 2 + (MissionTree.iMissionHeight + 15) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeRow,
                        missionUnlocked
                     )
                  );
            }

            if (Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission2 >= 0) {
               this.lines
                  .add(
                     new InGame_MissionTree.TechLine(
                        i,
                        100
                           + MissionTree.iMissionWidth / 2
                           + (MissionTree.iMissionWidth + 100)
                              * Game.getCiv(Game.player.iCivID).lMissions.get(Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission2).TreeColumn,
                        15
                           + MissionTree.iMissionHeight / 2
                           + (MissionTree.iMissionHeight + 15)
                              * Game.getCiv(Game.player.iCivID).lMissions.get(Game.getCiv(Game.player.iCivID).lMissions.get(i).RequiredMission2).TreeRow,
                        100 + MissionTree.iMissionWidth / 2 + (MissionTree.iMissionWidth + 100) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeColumn,
                        15 + MissionTree.iMissionHeight / 2 + (MissionTree.iMissionHeight + 15) * Game.getCiv(Game.player.iCivID).lMissions.get(i).TreeRow,
                        missionUnlocked
                     )
                  );
            }
         }
      } else {
         for (int i = 0; i < MissionTree.iMissionsSize; i++) {
            boolean missionUnlockedx = MissionTree.haveUnlockedMission(Game.player.iCivID, i);
            boolean missionCanBeUnlockedx = MissionTree.canRunMission(Game.player.iCivID, i);
            menuElements.add(
               new ButtonMissionTree(
                  i,
                  MissionTree.lMissions.get(i).ImageID,
                  100 + (MissionTree.iMissionWidth + 100) * MissionTree.lMissions.get(i).TreeColumn,
                  15 + (MissionTree.iMissionHeight + 15) * MissionTree.lMissions.get(i).TreeRow,
                  missionUnlockedx,
                  missionCanBeUnlockedx
               )
            );
            if (MissionTree.lMissions.get(i).RequiredMission >= 0) {
               this.lines
                  .add(
                     new InGame_MissionTree.TechLine(
                        i,
                        100
                           + MissionTree.iMissionWidth / 2
                           + (MissionTree.iMissionWidth + 100) * MissionTree.lMissions.get(MissionTree.lMissions.get(i).RequiredMission).TreeColumn,
                        15
                           + MissionTree.iMissionHeight / 2
                           + (MissionTree.iMissionHeight + 15) * MissionTree.lMissions.get(MissionTree.lMissions.get(i).RequiredMission).TreeRow,
                        100 + MissionTree.iMissionWidth / 2 + (MissionTree.iMissionWidth + 100) * MissionTree.lMissions.get(i).TreeColumn,
                        15 + MissionTree.iMissionHeight / 2 + (MissionTree.iMissionHeight + 15) * MissionTree.lMissions.get(i).TreeRow,
                        missionUnlockedx
                     )
                  );
            }

            if (MissionTree.lMissions.get(i).RequiredMission2 >= 0) {
               this.lines
                  .add(
                     new InGame_MissionTree.TechLine(
                        i,
                        100
                           + MissionTree.iMissionWidth / 2
                           + (MissionTree.iMissionWidth + 100) * MissionTree.lMissions.get(MissionTree.lMissions.get(i).RequiredMission2).TreeColumn,
                        15
                           + MissionTree.iMissionHeight / 2
                           + (MissionTree.iMissionHeight + 15) * MissionTree.lMissions.get(MissionTree.lMissions.get(i).RequiredMission2).TreeRow,
                        100 + MissionTree.iMissionWidth / 2 + (MissionTree.iMissionWidth + 100) * MissionTree.lMissions.get(i).TreeColumn,
                        15 + MissionTree.iMissionHeight / 2 + (MissionTree.iMissionHeight + 15) * MissionTree.lMissions.get(i).TreeRow,
                        missionUnlockedx
                     )
                  );
            }
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
      buttonY = (int)Math.max((float)buttonY, (MissionTree.iMissionHeight + 15) * (GameValues.inGame.TECH_TREE_NUM_OF_ROWS + 0.25F) + paddingTop * 2);
      this.initMenu(new MenuTitleTechTree(Game.getCiv(Game.player.iCivID).getCivName() + ": " + Game.lang.get("Missions"), titleHeight, false, false) {
         @Override
         public long getTime() {
            return InGame_MissionTree.lTime;
         }

         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            this.draw2(oSB, nPosX, nPosY, nWidth, titleStatus);
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
         if (this.lines.get(i).unlocked) {
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
      public boolean unlocked = false;
      public int iMissionID;
      private Array<Vector2> nPath = new Array<>();
      public List<Integer> lPointsX = new ArrayList<>();
      public List<Integer> lPointsY = new ArrayList<>();

      public TechLine(int iMissionID, int iX, int iY, int iX2, int iY2, boolean unlocked) {
         this.iMissionID = iMissionID;
         this.unlocked = unlocked;
         this.lPointsX.add(iX);
         this.lPointsY.add(iY);
         if (iY != iY2) {
            int mod = iY > iY2 ? -1 : 1;
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 - 8);
            this.lPointsY.add(iY);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 - 6);
            this.lPointsY.add(iY + 1 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 - 4);
            this.lPointsY.add(iY + 2 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 - 2);
            this.lPointsY.add(iY + 4 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 - 1);
            this.lPointsY.add(iY + 6 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50);
            this.lPointsY.add(iY + 8 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50);
            this.lPointsY.add(iY2 - 8 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 + 1);
            this.lPointsY.add(iY2 - 6 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 + 2);
            this.lPointsY.add(iY2 - 4 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 + 4);
            this.lPointsY.add(iY2 - 2 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 + 6);
            this.lPointsY.add(iY2 - 1 * mod);
            this.lPointsX.add(iX2 - MissionTree.iMissionWidth / 2 - 50 + 8);
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
