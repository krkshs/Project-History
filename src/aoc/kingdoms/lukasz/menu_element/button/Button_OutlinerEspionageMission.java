package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_OutlinerEspionageMission extends Button_Outliner {
   public String sText2;
   public int iTextWidth2 = 0;
   public int imageID;
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 1.0F);
   public Color progressBar = new Color(0.29411766F, 0.49019608F, 0.64705884F, 1.0F);
   public Color colorText = new Color(1.0F, 1.0F, 1.0F, 1.0F);
   public int maxTextWidth2 = 0;
   public int pieWidth = 0;
   public PieChart pieChart;
   public int lastTurnID = 0;
   public float fPerc = 0.0F;
   public int espionageStartedTurnID;
   public int espionageEndTurnID;
   public boolean inRightMenu;

   public Button_OutlinerEspionageMission(
      String sText,
      String sText2,
      int nPosX,
      int nPosY,
      int nWidth,
      int nHeight,
      int imageID,
      int iCivID,
      int espionageStartedTurnID,
      int espionageEndTurnID,
      boolean inRightMenu
   ) {
      super(sText, nPosX, nPosY, nWidth, nHeight, iCivID);
      this.inRightMenu = inRightMenu;
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.espionageStartedTurnID = espionageStartedTurnID;
      this.espionageEndTurnID = espionageEndTurnID;
      this.imageID = imageID;
      this.fPerc = (float)(Game_Calendar.TURN_ID - espionageStartedTurnID) / (espionageEndTurnID - espionageStartedTurnID) * 100.0F;
      this.sText2 = "" + (int)Math.min(100.0F, this.fPerc) + "%";
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), "99%");
      this.maxTextWidth2 = (int)Renderer.glyphLayout.width;
      this.progressBar = new Color(DiplomacyManager.COLOR_SPY.r, DiplomacyManager.COLOR_SPY.g, DiplomacyManager.COLOR_SPY.b, 0.35F);
      PieChart_Data nPieChartData = new PieChart_Data();
      nPieChartData.addPieChartValues(new PieChart_Value(0, 100.0F));
      this.pieWidth = nHeight - CFG.PADDING * 2;
      this.pieChart = new PieChart(nWidth - CFG.PADDING * 2 - this.pieWidth, CFG.PADDING, this.pieWidth, this.pieWidth, nPieChartData, null);

      try {
         this.lastTurnID = Game_Calendar.TURN_ID;
         this.colorText = DiplomacyManager.COLOR_NEUTRAL;
      } catch (Exception var14) {
         CFG.exceptionStack(var14);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         if (Game_Calendar.TURN_ID != this.lastTurnID) {
            this.fPerc = (float)(Game_Calendar.TURN_ID - this.espionageStartedTurnID) / (this.espionageEndTurnID - this.espionageStartedTurnID) * 100.0F;
            if (this.inRightMenu && this.fPerc >= 100.0F) {
               Game.addSimpleTask(
                  new Game.SimpleTask("rebuildRight", this.iCurrent) {
                     @Override
                     public void update() {
                        Game.menuManager.rebuildInGame_Right();
                        InGame_Info.iCivID = Game.player.iCivID;
                        InGame_Info.iCivID2 = this.id;
                        Game.menuManager
                           .rebuildInGame_Info(Game.lang.get("EspionageMission") + ": " + Game.lang.get("Completed"), Game.getCiv(this.id).getCivName());
                        InGame_Info.imgID = Images.infoDiplomacy;
                     }
                  }
               );
               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_CourtSavePos") {
                  @Override
                  public void update() {
                     if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                        Game.menuManager.rebuildInGame_CourtSavePos();
                        Game.menuManager.setVisibleInGame_Court(true);
                        InGame_Court.lTime = 0L;
                     }
                  }
               });
            }

            this.sText2 = "" + (int)Math.min(100.0F, this.fPerc) + "%";
            Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText2);
            this.iTextWidth2 = (int)Renderer.glyphLayout.width;
            this.lastTurnID = Game_Calendar.TURN_ID;
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
      this.pieChart
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            isActive,
            false,
            Math.max(0.0F, 100.0F - Math.max(1.0F, this.fPerc)),
            this.progressBar
         );
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
         DiplomacyManager.COLOR_SPY
      );
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(this.iCurrent, Game.lang.get("EspionageMission")));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("EspionageProgress") + ": ", this.sText2, Images.time, CFG.FONT_BOLD, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
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

   @Override
   public int getCurrent() {
      return this.iCurrent;
   }
}
