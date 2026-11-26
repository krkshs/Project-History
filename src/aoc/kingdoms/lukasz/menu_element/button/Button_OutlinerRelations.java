package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_OutlinerRelations extends Button_Outliner {
   public String sText2;
   public int iTextWidth2 = 0;
   public int imageID;
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 1.0F);
   public Color progressBar = new Color(0.29411766F, 0.49019608F, 0.64705884F, 1.0F);
   public Color colorText = new Color(1.0F, 1.0F, 1.0F, 1.0F);
   public int maxTextWidth2 = 0;
   public int pieWidth = 0;
   public PieChart pieChart;
   public float lastValue = -991258.25F;
   public int lastTurnID = 0;
   public float fPerc = 99.9F;
   public boolean improvingMode;

   public Button_OutlinerRelations(String sText, String sText2, int nPosX, int nPosY, int nWidth, int nHeight, int imageID, int iCivID, boolean improvingMode) {
      super(sText, nPosX, nPosY, nWidth, nHeight, iCivID);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.imageID = imageID;
      this.improvingMode = improvingMode;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), "-99");
      this.maxTextWidth2 = (int)Renderer.glyphLayout.width;
      if (improvingMode) {
         this.progressBar = new Color(DiplomacyManager.COLOR_GREEN.r, DiplomacyManager.COLOR_GREEN.g, DiplomacyManager.COLOR_GREEN.b, 0.35F);
      } else {
         this.progressBar = new Color(DiplomacyManager.COLOR_RED.r, DiplomacyManager.COLOR_RED.g, DiplomacyManager.COLOR_RED.b, 0.35F);
      }

      PieChart_Data nPieChartData = new PieChart_Data();
      nPieChartData.addPieChartValues(new PieChart_Value(0, 100.0F));
      this.pieWidth = nHeight - CFG.PADDING * 2;
      this.pieChart = new PieChart(nWidth - CFG.PADDING * 2 - this.pieWidth, CFG.PADDING, this.pieWidth, this.pieWidth, nPieChartData, null);

      try {
         this.lastTurnID = -1;
         this.lastValue = Game.getCiv(this.iCurrent).diplomacy.getRelation(Game.player.iCivID);
         this.colorText = DiplomacyManager.getOpinion_Color((int)this.lastValue);
      } catch (Exception var12) {
         CFG.exceptionStack(var12);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         if (Game_Calendar.TURN_ID != this.lastTurnID) {
            float preUpdatePerc = this.fPerc;
            this.fPerc = this.improvingMode
               ? Game.getCiv(Game.player.iCivID).diplomacy.getImprovingRelations_Perc(this.iCurrent)
               : Game.getCiv(Game.player.iCivID).diplomacy.getDamagingRelations_Perc(this.iCurrent);
            if (this.fPerc < 0.0F) {
               Game.addSimpleTask(new Game.SimpleTask("rebuildRight") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Right();
                  }
               });
            }

            if (this.fPerc > preUpdatePerc) {
               ProvinceDraw.addDiplomacyLines(
                  Game.getCiv(Game.player.iCivID).getCapitalProvinceID(),
                  Game.getCiv(this.iCurrent).getCapitalProvinceID(),
                  this.improvingMode ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE
               );
            }

            this.lastTurnID = Game_Calendar.TURN_ID;
         }

         if (this.lastValue != Game.getCiv(this.iCurrent).diplomacy.getRelation(Game.player.iCivID)) {
            this.lastValue = Game.getCiv(this.iCurrent).diplomacy.getRelation(Game.player.iCivID);
            this.setText(DiplomacyManager.getOpinion_String((int)this.lastValue));
            this.sText2 = "" + (int)this.lastValue;
            Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText2);
            this.iTextWidth2 = (int)Renderer.glyphLayout.width;
            this.colorText = DiplomacyManager.getOpinion_Color((int)this.lastValue);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateY
         );
      oSB.setShader(Renderer.shaderAlpha);
      if (this.iCurrent >= 0) {
         Game.getCiv(this.iCurrent).getFlag().getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagRect2Mask)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 4 + ImageManager.getImage(this.imageID).getWidth() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagRect2)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 4 + ImageManager.getImage(this.imageID).getWidth() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      this.pieChart.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, isActive, false, this.fPerc, this.progressBar);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + ImageManager.getImage(this.imageID).getWidth() + ImageManager.getImage(Images.flagRect2).getWidth() + CFG.PADDING * 6 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() - CFG.PADDING * 4 - this.pieWidth - this.iTextWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations(this.iCurrent, Game.player.iCivID, this.improvingMode, !this.improvingMode);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.getIsHovered() ? super.getColor(isActive) : this.colorText;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCurrent;
      }
   }
}
