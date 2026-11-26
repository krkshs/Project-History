package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Resource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.Buildings.InGame_BuildingsGroup;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceBonuses;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonBuilding2 extends MenuElement {
   public int building;
   public int buildingID;
   public String sText;
   public int iTextWidth = 0;
   public int iTextHeight = 0;
   public boolean built = false;
   public float underConstruction = -1.0F;
   public String sCost;
   public int iCostWidth;
   public int iCostHeight;
   public String sRecruitmentTime;
   public int iRecruitmentTimeWidth;
   public int iRecruitmentTimeHeight;
   public static float iconScale = 1.0F;
   public static int timeIconWidth;
   public static int timeIconHeight;
   public static int goldIconWidth;
   public static int goldIconHeight;
   public String sProgress = "";
   public int iProgressWidth = 0;

   public ButtonBuilding2(int nProvinceID, boolean built, int building, int buildingID, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.built = built;
      this.building = building;
      this.buildingID = buildingID;
      this.fontID = CFG.FONT_REGULAR;
      iconScale = this.getImageScale(Images.gold) * 1.1F;
      timeIconWidth = (int)(ImageManager.getImage(Images.buildTime).getWidth() * iconScale);
      timeIconHeight = (int)(ImageManager.getImage(Images.buildTime).getHeight() * iconScale);
      goldIconWidth = (int)(ImageManager.getImage(Images.gold).getWidth() * iconScale);
      goldIconHeight = (int)(ImageManager.getImage(Images.gold).getHeight() * iconScale);
      this.sRecruitmentTime = "" + Game.getBuildingConstructionTime(Game.player.iCivID, -1, building, buildingID);
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sRecruitmentTime);
      this.iRecruitmentTimeWidth = (int)glyphLayout.width;
      this.iRecruitmentTimeHeight = (int)glyphLayout.height;
      this.sCost = "" + Game.getBuildingConstructionCost(Game.player.iCivID, nProvinceID, building, buildingID);
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sCost);
      this.iCostWidth = (int)glyphLayout.width;
      this.iCostHeight = (int)glyphLayout.height;
      this.setText(BuildingsManager.buildings.get(building).Name[buildingID]);
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(nWidth);
      this.setHeight(ImageManager.getImage(Images.buildingsFrame).getHeight() + this.getTitleHeight());
      this.setClickable(isClickable);
      this.setVisible(true);
      int tWMax = 0;

      while (this.iTextWidth > nWidth - CFG.PADDING * 2 && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   public final void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      this.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      this.drawText(oSB, iTranslateX, iTranslateY, isActive);
   }

   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.built) {
         this.underConstruction = -1.0F;
      } else {
         this.underConstruction = Game.getProvince(InGame_BuildingsGroup.iProvinceID).underConstruction(this.building, this.buildingID);
         if (this.underConstruction < 0.0F) {
            this.built = Game.getProvince(InGame_BuildingsGroup.iProvinceID).buildingBuilt(this.building, this.buildingID);
            if (this.built) {
               Game.addSimpleTask(new Game.SimpleTask("rebuildBuildings") {
                  @Override
                  public void update() {
                     if (Game.menuManager.getVisibleInGame_ProvinceBonuses()) {
                        Game.menuManager.rebuildInGame_ProvinceBonuses();
                        Game.menuManager.setVisibleInGame_ProvinceBonuses(true, false);
                        InGame_ProvinceBonuses.lTime = 0L;
                     }

                     if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                        Game.menuManager.rebuildInGame_ProvinceInfo(false);
                     }

                     if (Game.menuManager.getVisibleInGame_Buildings()) {
                        Game.menuManager.rebuildInGame_Buildings(false);
                     }
                  }
               });
            }
         }
      }

      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      if (this.built) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_GREEN.r, Colors.COLOR_GRADIENT_BG_GREEN.g, Colors.COLOR_GRADIENT_BG_GREEN.b, 0.4F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_GREEN.r, Colors.COLOR_GRADIENT_OVER_GREEN.g, Colors.COLOR_GRADIENT_OVER_GREEN.b, 0.75F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + iTranslateY,
               this.getInnerWidth(),
               this.getImageHeight()
            );
         oSB.setColor(new Color(Colors.BUTTON_BUILDING_COLORS_STATS.r, Colors.BUTTON_BUILDING_COLORS_STATS.g, Colors.BUTTON_BUILDING_COLORS_STATS.b, 0.4F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + iTranslateY,
               this.getInnerWidth(),
               this.getImageHeight()
            );
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() + iTranslateY, this.getInnerWidth(), 1);
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() - 1 + iTranslateY,
               this.getInnerWidth(),
               1
            );
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - 1 + iTranslateY, this.getInnerWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_GREEN.r, Colors.COLOR_GRADIENT_BG_GREEN.g, Colors.COLOR_GRADIENT_BG_GREEN.b, 0.8F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() + iTranslateY, this.getInnerWidth(), CFG.PADDING
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() - CFG.PADDING + iTranslateY,
               this.getInnerWidth(),
               CFG.PADDING,
               false,
               true
            );
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_GREEN.r, Colors.COLOR_GRADIENT_BG_GREEN.g, Colors.COLOR_GRADIENT_BG_GREEN.b, 0.9F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_GREEN.r, Colors.COLOR_GRADIENT_OVER_GREEN.g, Colors.COLOR_GRADIENT_OVER_GREEN.b, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_GREEN.r, Colors.COLOR_GRADIENT_OVER_GREEN.g, Colors.COLOR_GRADIENT_OVER_GREEN.b, 0.75F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_GREEN.r, Colors.COLOR_GRADIENT_OVER_GREEN.g, Colors.COLOR_GRADIENT_OVER_GREEN.b, 1.0F));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - 2 + iTranslateY, this.getWidth(), 1);
      } else {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.4F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + iTranslateY,
               this.getInnerWidth(),
               this.getImageHeight()
            );
         oSB.setColor(new Color(Colors.BUTTON_BUILDING_COLORS_STATS.r, Colors.BUTTON_BUILDING_COLORS_STATS.g, Colors.BUTTON_BUILDING_COLORS_STATS.b, 0.4F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + iTranslateY,
               this.getInnerWidth(),
               this.getImageHeight()
            );
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() + iTranslateY, this.getInnerWidth(), 1);
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() - 1 + iTranslateY,
               this.getInnerWidth(),
               1
            );
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - 1 + iTranslateY, this.getInnerWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.8F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() + iTranslateY, this.getInnerWidth(), CFG.PADDING
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() - CFG.PADDING + iTranslateY,
               this.getInnerWidth(),
               CFG.PADDING,
               false,
               true
            );
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - 2 + iTranslateY, this.getWidth(), 1);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);

      try {
         BuildingsManager.buildingImages
            .get(BuildingsManager.buildings.get(this.building).ImageID[this.buildingID])
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getTitleHeight() + iTranslateY,
               ImageManager.getImage(Images.buildingsFrame).getWidth(),
               ImageManager.getImage(Images.buildingsFrame).getHeight()
            );
      } catch (Exception var6) {
      }

      oSB.setColor(Color.WHITE);
      if (BuildingsManager.buildings.get(this.building).RequiredResource >= 0) {
         ResourcesManager.resourceImages
            .get(BuildingsManager.buildings.get(this.building).RequiredResource)
            .draw(
               oSB,
               this.getPosX()
                  + ImageManager.getImage(Images.buildingsFrame).getWidth()
                  - ResourcesManager.resourceImages.get(BuildingsManager.buildings.get(this.building).RequiredResource).getWidth()
                  - CFG.PADDING
                  + iTranslateX,
               this.getPosY()
                  + ImageManager.getImage(Images.buildingsFrame).getHeight()
                  - ResourcesManager.resourceImages.get(BuildingsManager.buildings.get(this.building).RequiredResource).getHeight()
                  + this.getTitleHeight()
                  - CFG.PADDING
                  + iTranslateY
            );
      }

      ImageManager.getImage(Images.buildingsFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() + iTranslateY);
      if (isActive) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getTitleHeight() + iTranslateY,
            ImageManager.getImage(Images.buildingsFrame).getWidth(),
            ImageManager.getImage(Images.buildingsFrame).getHeight(),
            Colors.COLOR_BOX_ACTIVE
         );
      } else if (this.getIsHovered()) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getTitleHeight() + iTranslateY,
            ImageManager.getImage(Images.buildingsFrame).getWidth(),
            ImageManager.getImage(Images.buildingsFrame).getHeight(),
            Colors.COLOR_BOX_HOVER
         );
      }

      if (this.underConstruction >= 0.0F) {
         oSB.setColor(new Color(ProvinceDraw.progressBarBG));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX()
                  + ImageManager.getImage(Images.buildingsFrame).getWidth() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2
                  + iTranslateX,
               this.getPosY()
                  - CFG.PADDING / 2
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY
            );
         oSB.setColor(new Color(ProvinceDraw.progressBar));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX()
                  + ImageManager.getImage(Images.buildingsFrame).getWidth() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2
                  + iTranslateX,
               this.getPosY()
                  - CFG.PADDING / 2
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY,
               (int)(ImageManager.getImage(Images.progressBarFrameMask).getWidth() * (1.0F - this.underConstruction)),
               ImageManager.getImage(Images.progressBarFrameMask).getHeight()
            );
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.progressBarFrame)
            .draw(
               oSB,
               this.getPosX()
                  + ImageManager.getImage(Images.buildingsFrame).getWidth() / 2
                  - ImageManager.getImage(Images.progressBarFrame).getWidth() / 2
                  + iTranslateX,
               this.getPosY() - CFG.PADDING / 2 + this.getHeight() - ImageManager.getImage(Images.progressBarFrame).getHeight() + iTranslateY
            );
      }
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getTitleHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
      if (!this.built) {
         if (this.underConstruction >= 0.0F) {
            this.sProgress = "" + (int)Math.floor((1.0F - this.underConstruction) * 100.0F) + "%";
            GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
            glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sProgress);
            this.iProgressWidth = (int)glyphLayout.width;
            Renderer.drawText(
               oSB,
               CFG.FONT_REGULAR,
               this.sProgress,
               this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - this.iProgressWidth / 2 + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 2 - CFG.TEXT_HEIGHT / 2 + iTranslateY,
               Colors.HOVER_RIGHT
            );
         } else {
            ImageManager.getImage(Images.gold)
               .draw(
                  oSB,
                  this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - (this.iCostWidth + CFG.PADDING + goldIconWidth) / 2 + iTranslateX,
                  this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 4 - goldIconHeight / 2 + iTranslateY,
                  goldIconWidth,
                  goldIconHeight
               );
            Renderer.drawText(
               oSB,
               CFG.FONT_REGULAR_SMALL,
               this.sCost,
               this.getPosX()
                  + this.getInnerPosX()
                  + this.getInnerWidth() / 2
                  - (this.iCostWidth + CFG.PADDING + goldIconWidth) / 2
                  + goldIconWidth
                  + CFG.PADDING
                  + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 4 - CFG.TEXT_HEIGHT_SMALL / 2 + iTranslateY,
               Colors.HOVER_RIGHT2
            );
            ImageManager.getImage(Images.buildTime)
               .draw(
                  oSB,
                  this.getPosX()
                     + this.getInnerPosX()
                     + this.getInnerWidth() / 2
                     - (this.iRecruitmentTimeWidth + CFG.PADDING + timeIconWidth) / 2
                     + iTranslateX,
                  this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 2 + this.getImageHeight() / 4 - timeIconHeight / 2 + iTranslateY,
                  timeIconWidth,
                  timeIconHeight
               );
            Renderer.drawText(
               oSB,
               CFG.FONT_REGULAR_SMALL,
               this.sRecruitmentTime,
               this.getPosX()
                  + this.getInnerPosX()
                  + this.getInnerWidth() / 2
                  - (this.iRecruitmentTimeWidth + CFG.PADDING + timeIconWidth) / 2
                  + CFG.PADDING
                  + timeIconWidth
                  + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 2 + this.getImageHeight() / 4 - CFG.TEXT_HEIGHT_SMALL / 2 + iTranslateY,
               Colors.HOVER_RIGHT2
            );
         }
      } else {
         ImageManager.getImage(Images.x)
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - ImageManager.getImage(Images.x).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.getTitleHeight() + this.getImageHeight() / 2 - ImageManager.getImage(Images.x).getHeight() / 2 + iTranslateY
            );
      }
   }

   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.getText());
      this.iTextWidth = (int)glyphLayout.width;
      this.iTextHeight = (int)glyphLayout.height;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }

   @Override
   public String getText() {
      return this.sText;
   }

   public int getInnerPosX() {
      return ImageManager.getImage(Images.buildingsFrame).getWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - ImageManager.getImage(Images.buildingsFrame).getWidth();
   }

   public int getTitleHeight() {
      return this.iTextHeight + CFG.PADDING * 4;
   }

   public int getImageHeight() {
      return ImageManager.getImage(Images.buildingsFrame).getHeight();
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public int getValue1() {
      return this.building;
   }

   @Override
   public int getValue2() {
      return this.buildingID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle(BuildingsManager.buildings.get(this.building).Name[this.buildingID], CFG.FONT_BOLD, Colors.HOVER_GOLD)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (BuildingsManager.buildings.get(this.building).RequiredResource < 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Text_Desc(
               Game.lang.get(BuildingsManager.buildings.get(this.building).NameDesc[this.buildingID]), CFG.FONT_REGULAR_SMALL, Colors.BUTTON_TEXT_BRIGHT
            )
         );
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (BuildingsManager.buildings.get(this.building).RequiredResource >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RequiredResource") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ResourcesManager.getResourceName(BuildingsManager.buildings.get(this.building).RequiredResource), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Resource(BuildingsManager.buildings.get(this.building).RequiredResource, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).RequiredGovernmentID >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Government") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               Game.ideologiesManager.getIdeology(BuildingsManager.buildings.get(this.building).RequiredGovernmentID).Name,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.government, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).RequiredReligionID >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Religion") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               Game.religionManager.getReligion(BuildingsManager.buildings.get(this.building).RequiredReligionID).Name, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.religion, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (BuildingsManager.buildings.get(this.building).MonthlyIncome != null
         && BuildingsManager.buildings.get(this.building).MonthlyIncome[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyIncome") + ": ",
               (BuildingsManager.buildings.get(this.building).MonthlyIncome[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).MonthlyIncome[this.buildingID], 100),
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).TaxEfficiency != null
         && BuildingsManager.buildings.get(this.building).TaxEfficiency[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("TaxEfficiency") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).TaxEfficiency[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).TaxEfficiency[this.buildingID], 100)
                  + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).LocalTaxEfficiency != null
         && BuildingsManager.buildings.get(this.building).LocalTaxEfficiency[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("LocalTaxEfficiency") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).LocalTaxEfficiency[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).LocalTaxEfficiency[this.buildingID], 100)
                  + "%",
               Images.tax,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).MaxInfrastructure != null
         && BuildingsManager.buildings.get(this.building).MaxInfrastructure[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumInfrastructureLevel") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).MaxInfrastructure[this.buildingID] > 0 ? "+" : "")
                  + BuildingsManager.buildings.get(this.building).MaxInfrastructure[this.buildingID],
               Images.infrastructure,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).BuildingSlots != null
         && BuildingsManager.buildings.get(this.building).BuildingSlots[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("BuildingSlots") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).BuildingSlots[this.buildingID] > 0 ? "+" : "")
                  + BuildingsManager.buildings.get(this.building).BuildingSlots[this.buildingID],
               Images.build,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).IncomeProduction != null
         && BuildingsManager.buildings.get(this.building).IncomeProduction[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncomeProduction") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).IncomeProduction[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).IncomeProduction[this.buildingID], 100)
                  + "%",
               Images.goods,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ProductionEfficiency != null
         && BuildingsManager.buildings.get(this.building).ProductionEfficiency[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ProductionEfficiency") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ProductionEfficiency[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).ProductionEfficiency[this.buildingID], 100)
                  + "%",
               Images.goods,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ProvinceMaintenance != null
         && BuildingsManager.buildings.get(this.building).ProvinceMaintenance[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ProvinceMaintenance") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ProvinceMaintenance[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).ProvinceMaintenance[this.buildingID], 100)
                  + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).Economy != null && BuildingsManager.buildings.get(this.building).Economy[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Economy") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).Economy[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).Economy[this.buildingID], 100)
                  + "",
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ConstructionCost != null
         && BuildingsManager.buildings.get(this.building).ConstructionCost[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ConstructionCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ConstructionCost[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)(BuildingsManager.buildings.get(this.building).ConstructionCost[this.buildingID] * 100), 100)
                  + "%",
               Images.construction,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ConstructionTimeBonus != null
         && BuildingsManager.buildings.get(this.building).ConstructionTimeBonus[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ConstructionTime") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ConstructionTimeBonus[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)(BuildingsManager.buildings.get(this.building).ConstructionTimeBonus[this.buildingID] * 100), 100)
                  + "%",
               Images.buildTime,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).LocalGrowthRate != null
         && BuildingsManager.buildings.get(this.building).LocalGrowthRate[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("LocalGrowthRate") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).LocalGrowthRate[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).LocalGrowthRate[this.buildingID], 100)
                  + "%",
               Images.populationGrowth,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).InvestInEconomyCost != null
         && BuildingsManager.buildings.get(this.building).InvestInEconomyCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("InvestInEconomyCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).InvestInEconomyCost[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).InvestInEconomyCost[this.buildingID] * 100.0F, 100)
                  + "%",
               Game_Calendar.IMG_ECONOMY_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).IncreaseGrowthRateCost != null
         && BuildingsManager.buildings.get(this.building).IncreaseGrowthRateCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseGrowthRateCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).IncreaseGrowthRateCost[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).IncreaseGrowthRateCost[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.populationUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).DevelopInfrastructureCost != null
         && BuildingsManager.buildings.get(this.building).DevelopInfrastructureCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DevelopInfrastructureCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).DevelopInfrastructureCost[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).DevelopInfrastructureCost[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.infrastructureUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).IncreaseManpowerCost != null
         && BuildingsManager.buildings.get(this.building).IncreaseManpowerCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseManpowerCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).IncreaseManpowerCost[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).IncreaseManpowerCost[this.buildingID] * 100.0F, 100)
                  + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).IncreaseTaxEfficiencyCost != null
         && BuildingsManager.buildings.get(this.building).IncreaseTaxEfficiencyCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("IncreaseTaxEfficiencyCost") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).IncreaseTaxEfficiencyCost[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).IncreaseTaxEfficiencyCost[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.taxUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ResearchPoints != null
         && BuildingsManager.buildings.get(this.building).ResearchPoints[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ResearchPerMonth") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ResearchPoints[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).ResearchPoints[this.buildingID], 100),
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).MonthlyLegacy != null
         && BuildingsManager.buildings.get(this.building).MonthlyLegacy[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MonthlyLegacy") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).MonthlyLegacy[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).MonthlyLegacy[this.buildingID], 100)
                  + "",
               Images.legacy,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).MaximumManpower != null
         && BuildingsManager.buildings.get(this.building).MaximumManpower[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaximumManpower") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).MaximumManpower[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)BuildingsManager.buildings.get(this.building).MaximumManpower[this.buildingID], 1)
                  + "",
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).LocalManpower != null
         && BuildingsManager.buildings.get(this.building).LocalManpower[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("LocalManpower") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).LocalManpower[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).LocalManpower[this.buildingID] * 100.0F, 1)
                  + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).RecruitArmyCostInProvince != null
         && BuildingsManager.buildings.get(this.building).RecruitArmyCostInProvince[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyRecruitmentCostInProvince") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).RecruitArmyCostInProvince[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).RecruitArmyCostInProvince[this.buildingID], 100)
                  + "%",
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).DefenseBonus != null
         && BuildingsManager.buildings.get(this.building).DefenseBonus[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DefenseBonus") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).DefenseBonus[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)BuildingsManager.buildings.get(this.building).DefenseBonus[this.buildingID], 100),
               Images.defense,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).FortLevel != null && BuildingsManager.buildings.get(this.building).FortLevel[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DefenseLevel") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).FortLevel[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)BuildingsManager.buildings.get(this.building).FortLevel[this.buildingID], 100),
               Images.fort,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).FortDefense != null && BuildingsManager.buildings.get(this.building).FortDefense[this.buildingID] != 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ProvinceDefense") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).FortDefense[this.buildingID] > 0 ? "+" : "")
                  + CFG.getPrecision2((float)BuildingsManager.buildings.get(this.building).FortDefense[this.buildingID], 1),
               Game_Calendar.IMG_FORT_DEFENSE,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).ArmyMovementSpeed != null
         && BuildingsManager.buildings.get(this.building).ArmyMovementSpeed[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ArmyMovementSpeed") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).ArmyMovementSpeed[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).ArmyMovementSpeed[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.movementSpeed,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).CasualtiesNuclearAttacks != null
         && BuildingsManager.buildings.get(this.building).CasualtiesNuclearAttacks[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("CasualtiesFromNuclearAttacks") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).CasualtiesNuclearAttacks[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).CasualtiesNuclearAttacks[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.nuke,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).DiseaseDeathRate != null
         && BuildingsManager.buildings.get(this.building).DiseaseDeathRate[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DiseasesDeathRate") + ": ",
               ""
                  + (BuildingsManager.buildings.get(this.building).DiseaseDeathRate[this.buildingID] > 0.0F ? "+" : "")
                  + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).DiseaseDeathRate[this.buildingID] * 100.0F, 100)
                  + "%",
               Images.disease,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_POSITIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (BuildingsManager.buildings.get(this.building).MaintenanceCost[this.buildingID] != 0.0F) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("MaintenanceCost") + ": ",
               Game.lang.get("XPerMonth", "" + CFG.getPrecision2(BuildingsManager.buildings.get(this.building).MaintenanceCost[this.buildingID], 1000)),
               Images.gold,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text(this.sCost, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ConstructionTime") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.lang.get("DaysX", Game.getBuildingConstructionTime(Game.player.iCivID, -1, this.building, this.buildingID)),
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.buildTime, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (BuildingsManager.buildings.get(this.building).FortLevel != null && BuildingsManager.buildings.get(this.building).FortLevel[this.buildingID] != 0) {
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData, false));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Text_Desc(
               Game.lang.get("AfterASuccessfulSiegeAllNeighboringProvincesWithoutDefensiveBuildingsWillBeOccupied"),
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT3
            )
         );
         nElements.add(new MenuElement_HoverElement(nData, false));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getSFX() {
      return this.getClickable() ? Game.soundsManager.getBuild() : super.getSFX();
   }
}
