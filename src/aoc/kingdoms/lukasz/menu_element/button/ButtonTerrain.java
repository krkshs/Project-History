package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.plague.PlagueManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Disease;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonTerrain extends Button {
   public int iProvinceID;
   public int lastValue = -997654;
   public String sName;
   public String sDeaths;
   public int iNameWidth;
   public int iNameHeight;
   public int iDeathsWidth;
   public int iDeathsHeight;
   public int boxW;
   public int boxW2;
   public int boxH;
   public int terrainID2;

   public final int getPlaguePadding() {
      return CFG.PADDING;
   }

   public final int getPlaguePaddingInner() {
      return CFG.PADDING;
   }

   public final int getPlaguePaddingInnerX() {
      return CFG.PADDING + CFG.PADDING / 2;
   }

   public ButtonTerrain(int iProvinceID, int iPosX, int iPosY) {
      this.iProvinceID = iProvinceID;
      this.fontID = CFG.FONT_REGULAR_SMALL;
      this.terrainID2 = iProvinceID % Game.terrainManager.terrainImages.get(Game.getProvince(iProvinceID).getTerrainID()).size();
      this.init(
         Game.terrainManager.terrains.get(Game.getProvince(iProvinceID).getTerrainID()).Name,
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.terrainFrame).getWidth(),
         ImageManager.getImage(Images.terrainFrame).getHeight(),
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Game.terrainManager
         .terrainImages
         .get(Game.getProvince(this.iProvinceID).getTerrainID())
         .get(this.terrainID2)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.terrainFrame).getWidth(),
            ImageManager.getImage(Images.terrainFrame).getHeight()
         );
      if (InGame_ProvinceInfo.provinceIMG != null) {
         InGame_ProvinceInfo.provinceIMG
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.terrainFrame).getWidth(),
               ImageManager.getImage(Images.terrainFrame).getHeight()
            );
      }

      try {
         if (Game.getProvince(this.iProvinceID).provincePlague != null) {
            if (this.lastValue != Game.getProvince(this.iProvinceID).provincePlague.deaths) {
               this.sName = PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).sName;
               this.sDeaths = Game.lang.get("Deaths") + ": " + CFG.getNumberWithSpaces("" + Game.getProvince(this.iProvinceID).provincePlague.deaths);
               Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sName);
               this.iNameWidth = (int)Renderer.glyphLayout.width;
               this.iNameHeight = (int)Renderer.glyphLayout.height;
               Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sDeaths);
               this.iDeathsWidth = (int)Renderer.glyphLayout.width;
               this.iDeathsHeight = (int)Renderer.glyphLayout.height;
               this.boxW = PlagueManager.plagueImagesBig
                     .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                     .getWidth()
                  + this.getPlaguePaddingInnerX() * 4
                  + Math.max(this.iNameWidth, this.iDeathsWidth);
               this.boxW2 = PlagueManager.plagueImagesBig
                     .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                     .getWidth()
                  + this.getPlaguePaddingInnerX() * 2;
               this.boxH = this.getPlaguePaddingInner() * 2
                  + Math.max(
                     PlagueManager.plagueImagesBig
                        .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                        .getHeight(),
                     this.iDeathsHeight + this.iNameHeight + this.getPlaguePaddingInner()
                  );
            }

            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
            Renderer.drawBox(
               oSB,
               Images.statsRectBG,
               this.getPosX() + this.getPlaguePadding() + iTranslateX,
               this.getPosY() + this.getPlaguePadding() + iTranslateY,
               this.boxW,
               this.boxH,
               1.0F
            );
            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
            Renderer.drawBox(
               oSB,
               Images.statsRectBG,
               this.getPosX() + this.getPlaguePadding() + iTranslateX,
               this.getPosY() + this.getPlaguePadding() + iTranslateY,
               this.boxW2,
               this.boxH,
               1.0F
            );
            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.25F));
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + this.getPlaguePadding() + iTranslateX,
                  this.getPosY() + this.boxH / 2 + this.getPlaguePadding() + iTranslateY,
                  this.boxW,
                  CFG.PADDING * 2
               );
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + this.getPlaguePadding() + iTranslateX,
                  this.getPosY() + this.getPlaguePadding() + iTranslateY,
                  this.boxW,
                  CFG.PADDING * 2,
                  false,
                  true
               );
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
            Images.gradientFull
               .draw(oSB, this.getPosX() + this.getPlaguePadding() + iTranslateX, this.getPosY() + this.getPlaguePadding() + iTranslateY, this.boxW, 1);
            Images.gradientFull
               .draw(
                  oSB,
                  this.getPosX() + this.getPlaguePadding() + iTranslateX,
                  this.getPosY() + this.boxH - 1 + this.getPlaguePadding() + iTranslateY,
                  this.boxW,
                  1
               );
            oSB.setColor(Color.WHITE);
            PlagueManager.plagueImagesBig
               .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
               .draw(
                  oSB,
                  this.getPosX() + this.getPlaguePadding() + this.getPlaguePaddingInnerX() + iTranslateX,
                  this.getPosY()
                     + this.getPlaguePadding()
                     + this.boxH / 2
                     - PlagueManager.plagueImagesBig
                           .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                           .getHeight()
                        / 2
                     + iTranslateY
               );
            Renderer.drawText(
               oSB,
               this.fontID,
               this.sName,
               this.getPosX()
                  + this.getPlaguePadding()
                  + this.getPlaguePaddingInnerX() * 3
                  + iTranslateX
                  + PlagueManager.plagueImagesBig
                     .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                     .getWidth()
                  + iTranslateX,
               this.getPosY()
                  + this.getPlaguePadding()
                  + this.boxH / 2
                  - (this.iNameHeight + this.iDeathsHeight + this.getPlaguePaddingInnerX()) / 2
                  + iTranslateY,
               Colors.HOVER_IMPORTANT
            );
            Renderer.drawText(
               oSB,
               this.fontID,
               this.sDeaths,
               this.getPosX()
                  + this.getPlaguePadding()
                  + this.getPlaguePaddingInnerX() * 3
                  + iTranslateX
                  + PlagueManager.plagueImagesBig
                     .get(PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID)
                     .getWidth()
                  + iTranslateX,
               this.getPosY()
                  + this.getPlaguePadding()
                  + this.boxH / 2
                  - (this.iNameHeight + this.iDeathsHeight + this.getPlaguePaddingInnerX()) / 2
                  + this.iNameHeight
                  + this.getPlaguePaddingInnerX()
                  + iTranslateY,
               Colors.HOVER_IMPORTANT
            );
         } else {
            this.lastValue = -997654;
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.5F));
      MenuManager.sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.terrainFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (isActive) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.terrainFrame).getWidth(),
            ImageManager.getImage(Images.terrainFrame).getHeight(),
            Colors.COLOR_BOX_ACTIVE
         );
      } else if (this.getIsHovered()) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.terrainFrame).getWidth(),
            ImageManager.getImage(Images.terrainFrame).getHeight(),
            Colors.COLOR_BOX_HOVER
         );
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getWidth() - this.getTextWidth() - CFG.PADDING * 3 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING * 3 - this.getTextHeight() + iTranslateY,
         this.getTextWidth() + CFG.PADDING * 2,
         this.getTextHeight() + CFG.PADDING * 2,
         1.0F
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.getTextWidth() + iTranslateX,
         this.getPosY() + this.getHeight() - this.getTextHeight() - CFG.PADDING * 2 + iTranslateY,
         Colors.HOVER_IMPORTANT
      );
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Terrain") + ": ", CFG.FONT_BOLD));
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle(
            Game.terrainManager.terrains.get(Game.getProvince(this.iProvinceID).getTerrainID()).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      int nTerrainID = Game.getProvince(this.iProvinceID).getTerrainID();
      if (Game.terrainManager.terrains.get(nTerrainID).Defense != 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DefenseBonus") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + (Game.terrainManager.terrains.get(nTerrainID).Defense > 0 ? "+" : "") + Game.terrainManager.terrains.get(nTerrainID).Defense,
               Game.terrainManager.terrains.get(nTerrainID).Defense == 0
                  ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                  : (Game.terrainManager.terrains.get(nTerrainID).Defense > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.defense, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.terrainManager.terrains.get(nTerrainID).MovementSpeed != 1.0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MovementSpeed") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ""
                  + (Game.terrainManager.terrains.get(nTerrainID).MovementSpeed > 1.0 ? "+" : "")
                  + (int)((Game.terrainManager.terrains.get(nTerrainID).MovementSpeed - 1.0F) * 100.0F)
                  + "%",
               Game.terrainManager.terrains.get(nTerrainID).MovementSpeed == 1.0
                  ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                  : (
                     Game.terrainManager.terrains.get(nTerrainID).MovementSpeed > 1.0
                        ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  )
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.movementSpeed, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.terrainManager.terrains.get(nTerrainID).PopulationGrowth != 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("PopulationGrowthModifier") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ""
                  + (Game.terrainManager.terrains.get(nTerrainID).PopulationGrowth > 0 ? "+" : "")
                  + Game.terrainManager.terrains.get(nTerrainID).PopulationGrowth
                  + "%",
               Game.terrainManager.terrains.get(nTerrainID).PopulationGrowth == 0
                  ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                  : (
                     Game.terrainManager.terrains.get(nTerrainID).PopulationGrowth > 0
                        ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  )
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.populationGrowth, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.terrainManager.terrains.get(nTerrainID).IncreaseGrowthRateCost != 1.0F) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IncreaseGrowthRateCost") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + (int)(Game.terrainManager.terrains.get(nTerrainID).IncreaseGrowthRateCost * 100.0F) + "%",
               Game.terrainManager.terrains.get(nTerrainID).IncreaseGrowthRateCost == 1.0F
                  ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                  : (
                     Game.terrainManager.terrains.get(nTerrainID).IncreaseGrowthRateCost < 1.0F
                        ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  )
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.populationGrowth, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.terrainManager.terrains.get(nTerrainID).BuildCost != 1.0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BuildCostModifier") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               (Game.terrainManager.terrains.get(nTerrainID).BuildCost > 0.0F ? "+" : "")
                  + (int)((Game.terrainManager.terrains.get(nTerrainID).BuildCost - 1.0F) * 100.0F)
                  + "%",
               Game.terrainManager.terrains.get(nTerrainID).BuildCost == 0.0F
                  ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                  : (Game.terrainManager.terrains.get(nTerrainID).BuildCost < 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.build, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      try {
         if (Game.getProvince(this.iProvinceID).provincePlague != null) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Plague") + ": ", CFG.FONT_BOLD));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).sName, CFG.FONT_BOLD, Colors.HOVER_GOLD
               )
            );
            nData.add(
               new MenuElement_HoverElement_Type_Disease(
                  PlagueManager.activePlagues.get(Game.getProvince(this.iProvinceID).provincePlague.id).iImageID, CFG.PADDING, 0
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Deaths") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + CFG.getNumberWithSpaces("" + Game.getProvince(this.iProvinceID).provincePlague.deaths),
                  CFG.FONT_BOLD_SMALL,
                  Game.getProvince(this.iProvinceID).provincePlague.deaths > 0 ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.COLOR_TEXT_MODIFIER_NEUTRAL
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Day") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + (Game_Calendar.TURN_ID - Game.getProvince(this.iProvinceID).provincePlague.sinceTurnID + 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TotalNumberOfAllDiseasesInProvince") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getNumberWithSpaces("" + Game.getProvinceData10(this.iProvinceID).n), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.disease, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TotalDeathsDueToDiseasesDuringTheGame") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getNumberWithSpaces("" + Game.getProvinceData10(this.iProvinceID).d),
            CFG.FONT_BOLD_SMALL,
            Game.getProvinceData10(this.iProvinceID).d > 0 ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.COLOR_TEXT_MODIFIER_NEUTRAL
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (Game.getCiv(Game.player.iCivID).getCapitalProvinceID() >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DistanceFromCapital") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(Game.getDistanceFromAToB_Km(Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), this.iProvinceID), 10) + " km",
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
         nData.add(new MenuElement_HoverElement_Type_Flag(Game.player.iCivID, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Infrastructure") + ": ", CFG.FONT_BOLD_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + Game.getProvince(this.iProvinceID).getInfrastructure() + " / " + Game.getProvince(this.iProvinceID).iInfrastructureMax,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.infrastructure, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiseasesDeathRate") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            (Game.getProvince(this.iProvinceID).getInfrastructure() > 0 ? "+" : "")
               + CFG.getPrecision2(
                  GameValues.infrastructure.INFRASTRUCTURE_DISEASE_DEATH_RATE_PER_LVL * 100.0F * Game.getProvince(this.iProvinceID).getInfrastructure(), 100
               )
               + "%",
            CFG.FONT_BOLD_SMALL,
            Game.getProvince(this.iProvinceID).getInfrastructure() > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEUTRAL
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.disease, CFG.PADDING, CFG.PADDING));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.lang.get("PerLevel") + ": " + CFG.getPrecision2(GameValues.infrastructure.INFRASTRUCTURE_DISEASE_DEATH_RATE_PER_LVL * 100.0F, 100) + "%",
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT3
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements) {
         @Override
         public int getMinPosX() {
            return InGame_ProvinceInfo.HOVER_POSX;
         }

         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY) {
            nPosY = InGame_ProvinceInfo.HOVER_POSY + (CFG.GAME_HEIGHT - InGame_ProvinceInfo.HOVER_POSY) / 2 - this.iHeight / 2;
            super.draw(oSB, nPosX, nPosY);
         }
      };
   }
}
