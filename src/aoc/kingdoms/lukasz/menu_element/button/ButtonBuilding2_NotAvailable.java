package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonBuilding2_NotAvailable extends ButtonBuilding2 {
   public String sTitle;
   public int iTitleWidth;
   public int iTitleHeight;
   public String sTitle2 = "";
   public int iTitleWidth2;

   public ButtonBuilding2_NotAvailable(
      int nProvinceID,
      boolean built,
      int building,
      int buildingID,
      int iPosX,
      int iPosY,
      int nWidth,
      boolean isClickable,
      boolean dueToTech,
      boolean dueToReligion,
      boolean dueToGovernment
   ) {
      super(nProvinceID, built, building, buildingID, iPosX, iPosY, nWidth, isClickable);

      try {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         if (dueToTech) {
            this.sTitle = Game.lang.get("RequiredTechnology");
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle);
            this.iTitleWidth = (int)glyphLayout.width;
            this.iTitleHeight = (int)glyphLayout.height;
            this.sTitle2 = TechnologyTree.lTechnology.get(BuildingsManager.buildings.get(building).RequiredTechID[buildingID]).Name;
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle2);
            this.iTitleWidth2 = (int)glyphLayout.width;
         } else if (dueToReligion && BuildingsManager.buildings.get(building).RequiredReligionID >= 0) {
            this.sTitle = Game.lang.get("Religion");
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle);
            this.iTitleWidth = (int)glyphLayout.width;
            this.iTitleHeight = (int)glyphLayout.height;
            this.sTitle2 = Game.religionManager.getReligion(BuildingsManager.buildings.get(building).RequiredReligionID).Name;
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle2);
            this.iTitleWidth2 = (int)glyphLayout.width;
         } else if (dueToReligion && BuildingsManager.buildings.get(building).RequiredGovernmentID >= 0) {
            this.sTitle = Game.lang.get("Government");
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle);
            this.iTitleWidth = (int)glyphLayout.width;
            this.iTitleHeight = (int)glyphLayout.height;
            this.sTitle2 = Game.ideologiesManager.getIdeology(BuildingsManager.buildings.get(building).RequiredGovernmentID).Name;
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle2);
            this.iTitleWidth2 = (int)glyphLayout.width;
         } else {
            this.sTitle = Game.lang.get("RequiredBuilding");
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle);
            this.iTitleWidth = (int)glyphLayout.width;
            this.iTitleHeight = (int)glyphLayout.height;
            this.sTitle2 = BuildingsManager.buildings.get(building).Name[Math.max(0, buildingID - 1)];
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sTitle2);
            this.iTitleWidth2 = (int)glyphLayout.width;
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawText(oSB, iTranslateX, iTranslateY, isActive);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.6F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() / 2);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, this.getWidth(), this.getHeight() / 2, false, true);
      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sTitle,
         this.getPosX() + this.getWidth() / 2 - this.iTitleWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTitleHeight - CFG.PADDING + iTranslateY,
         Colors.HOVER_RIGHT
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sTitle2,
         this.getPosX() + this.getWidth() / 2 - this.iTitleWidth2 / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 + CFG.PADDING + iTranslateY,
         Colors.HOVER_RIGHT2
      );
   }
}
