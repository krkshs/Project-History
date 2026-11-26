package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsRectIMG_ActiveBuilding extends ButtonStatsRectIMG_Active {
   public int building;
   public int buildingID;

   public ButtonStatsRectIMG_ActiveBuilding(
      String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, int id, int building, int buildingID
   ) {
      super(sText, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth, id);
      this.building = building;
      this.buildingID = buildingID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      float tUnderConstruction = Game.getProvince(this.getCurrent()).underConstruction(this.building, this.buildingID);
      if (tUnderConstruction >= 0.0F) {
         oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         if (tUnderConstruction < 0.99F) {
            oSB.setColor(new Color(ProvinceDraw.progressBar));
            Renderer.drawBox(
               oSB,
               Images.statsRectBG,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               (int)(this.getWidth() * (1.0F - tUnderConstruction)),
               this.getHeight(),
               1.0F
            );
         }

         oSB.setColor(Color.WHITE);
      } else {
         oSB.setColor(
            new Color(
               Colors.COLOR_STATS_RECT_BG.r,
               Colors.COLOR_STATS_RECT_BG.g,
               Colors.COLOR_STATS_RECT_BG.b,
               getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BLUE.r,
            Colors.COLOR_GRADIENT_OVER_BLUE.g,
            Colors.COLOR_GRADIENT_OVER_BLUE.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive) / 2.0F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getWidth() - (this.maxIconWidth + CFG.PADDING * 2) + iTranslateX,
         this.getPosY() + iTranslateY,
         this.maxIconWidth + CFG.PADDING * 2,
         this.getHeight(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      if (Game.getProvince(this.getCurrent()).buildingBuilt(this.building, this.buildingID)) {
         Game.addSimpleTask(new Game.SimpleTask("rebuildBuilding2") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_BuildSavePos();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }
         });
      }

      if (this.getClickable() && this.getIsHovered() && animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 1 + iTranslateY, (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc), 1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }
   }
}
