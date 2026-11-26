package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonWonder;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Wonder extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iProvinceID = 0;
   public static Image wonderIMG = null;
   public static boolean rebuildMenu = false;

   public InGame_Wonder(boolean visible) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;

      try {
         if (visible) {
            try {
               if (wonderIMG != null) {
                  wonderIMG.dispose();
                  wonderIMG = null;
               }
            } catch (Exception var15) {
               CFG.exceptionStack(var15);
            }

            if (FileManager.loadFile(
                  "map/"
                     + Game.map.getFile_ActiveMap_Path()
                     + "wonders/"
                     + "wondersImagesBig/"
                     + CFG.getRescouresPath_Short()
                     + WondersManager.wonders.get(Game.getProvince(iProvinceID).wonderID).ImageID
                     + ".png"
               )
               .exists()) {
               wonderIMG = new Image(
                  ImageManager.loadTexture(
                     "map/"
                        + Game.map.getFile_ActiveMap_Path()
                        + "wonders/"
                        + "wondersImagesBig/"
                        + CFG.getRescouresPath_Short()
                        + WondersManager.wonders.get(Game.getProvince(iProvinceID).wonderID).ImageID
                        + ".png"
                  ),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            } else {
               wonderIMG = new Image(
                  ImageManager.loadTexture(
                     "map/"
                        + Game.map.getFile_ActiveMap_Path()
                        + "wonders/"
                        + "wondersImagesBig/"
                        + CFG.getRescouresPath_Short_H()
                        + WondersManager.wonders.get(Game.getProvince(iProvinceID).wonderID).ImageID
                        + ".png"
                  ),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            }

            menuElements.add(new ButtonWonder(Images.boxTitleBORDERWIDTH, buttonY));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      } catch (Exception var16) {
         CFG.exceptionStack(var16);
      }

      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      int wonderID = Game.getProvince(iProvinceID).wonderID;
      menuElements.add(
         new TextBonus(
            Game.lang.get("Province") + ": ",
            Game.getProvince(iProvinceID).getProvinceName(),
            Images.provinces,
            Images.boxTitleBORDERWIDTH + CFG.PADDING,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            iconWidth
         ) {
            @Override
            public void actionElement() {
               Game.mapCoords.centerToProvinceID(InGame_Wonder.iProvinceID);
               Game.setActiveProvinceID(InGame_Wonder.iProvinceID);
               ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (wonderID >= 0) {
         if (WondersManager.wonders.get(wonderID).MonthlyIncome != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MonthlyIncome") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MonthlyIncome > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MonthlyIncome, 100)
                     + "",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).LocalTaxEfficiency != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("LocalTaxEfficiency") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).LocalTaxEfficiency > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).LocalTaxEfficiency, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).TaxEfficiency != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("TaxEfficiency") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).TaxEfficiency > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).TaxEfficiency, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ProductionEfficiency != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ProductionEfficiency") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ProductionEfficiency > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ProductionEfficiency, 100)
                     + "%",
                  Images.goods,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ProvinceMaintenance != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ProvinceMaintenance") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ProvinceMaintenance > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ProvinceMaintenance, 100)
                     + "%",
                  Images.provinces,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).BuildingsMaintenanceCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("BuildingsMaintenanceCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).BuildingsMaintenanceCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).BuildingsMaintenanceCost * 100.0F, 100)
                     + "%",
                  Images.buildings,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).Economy != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Economy") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).Economy > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).Economy, 100)
                     + "",
                  Game_Calendar.IMG_ECONOMY,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).GrowthRate != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("GrowthRate") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).GrowthRate > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).GrowthRate, 100)
                     + "%",
                  Images.populationGrowth,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).Research != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Research") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).Research > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).Research, 100)
                     + "%",
                  Game_Calendar.IMG_TECHNOLOGY,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ResearchPoints != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ResearchPerMonth") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ResearchPoints > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ResearchPoints, 100)
                     + "",
                  Game_Calendar.IMG_TECHNOLOGY,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).TechnologyCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("TechnologyCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).TechnologyCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).TechnologyCost, 100)
                     + "%",
                  Game_Calendar.IMG_TECHNOLOGY,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MonthlyLegacy != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MonthlyLegacy") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MonthlyLegacy > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MonthlyLegacy, 100)
                     + "",
                  Images.legacy,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ArmyMaintenance != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ArmyMaintenance") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ArmyMaintenance > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ArmyMaintenance, 100)
                     + "%",
                  Images.armyMaintenance,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaxManpower != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaximumManpower") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MaxManpower > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MaxManpower, 100)
                     + "",
                  Game_Calendar.IMG_MANPOWER_UP,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ManpowerRecoverySpeed != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ManpowerRecoverySpeed") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ManpowerRecoverySpeed > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ManpowerRecoverySpeed * 100.0F, 100)
                     + "%",
                  Game_Calendar.IMG_MANPOWER_TIME,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RecruitmentTime != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("RecruitmentTime") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RecruitmentTime > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).RecruitmentTime, 100)
                     + "%",
                  Game_Calendar.IMG_MANPOWER_TIME,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RecruitArmyCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ArmyRecruitmentCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RecruitArmyCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).RecruitArmyCost, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RecruitArmyFirstLineCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("FirstLineArmyRecruitmentCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RecruitArmyFirstLineCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).RecruitArmyFirstLineCost, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RecruitArmySecondLineCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("SecondLineArmyRecruitmentCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RecruitArmySecondLineCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).RecruitArmySecondLineCost, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ConstructionCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ConstructionCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ConstructionCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ConstructionCost * 100.0F, 100)
                     + "%",
                  Images.construction,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).AdministrationBuildingsCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("AdministrationBuildingsCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).AdministrationBuildingsCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).AdministrationBuildingsCost * 100.0F, 100)
                     + "%",
                  Images.construction,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).EconomyBuildingsCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("EconomyBuildingsCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).EconomyBuildingsCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).EconomyBuildingsCost * 100.0F, 100)
                     + "%",
                  Images.construction,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MilitaryBuildingsCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MilitaryBuildingsCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MilitaryBuildingsCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MilitaryBuildingsCost * 100.0F, 100)
                     + "%",
                  Images.construction,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ConstructionTimeBonus != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ConstructionTime") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ConstructionTimeBonus > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ConstructionTimeBonus * 100.0F, 100)
                     + "%",
                  Images.buildTime,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaxInfrastructure != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaximumInfrastructureLevel") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).MaxInfrastructure > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).MaxInfrastructure,
                  Images.infrastructure,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).AdvisorMaxLevel != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaximumAdvisorSkillLevel") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).AdvisorMaxLevel > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).AdvisorMaxLevel,
                  Images.skill,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).AdvisorPoolSize != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("AdvisorPool") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).AdvisorPoolSize > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).AdvisorPoolSize,
                  Images.council,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).BuildingSlot != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("BuildingSlot") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).BuildingSlot > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).BuildingSlot,
                  Images.build,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).InvestInEconomyCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("InvestInEconomyCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).InvestInEconomyCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).InvestInEconomyCost * 100.0F, 100)
                     + "%",
                  Game_Calendar.IMG_ECONOMY_UP,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).IncreaseTaxEfficiencyCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("IncreaseTaxEfficiencyCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).IncreaseTaxEfficiencyCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).IncreaseTaxEfficiencyCost * 100.0F, 100)
                     + "%",
                  Images.taxUp,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).IncreaseGrowthRateCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("IncreaseGrowthRateCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).IncreaseGrowthRateCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).IncreaseGrowthRateCost * 100.0F, 100)
                     + "%",
                  Images.populationUp,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).DevelopInfrastructureCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("DevelopInfrastructureCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).DevelopInfrastructureCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).DevelopInfrastructureCost * 100.0F, 100)
                     + "%",
                  Images.infrastructureUp,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).IncreaseManpowerCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("IncreaseManpowerCost") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).IncreaseManpowerCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).IncreaseManpowerCost, 100)
                     + "%",
                  Game_Calendar.IMG_MANPOWER_UP,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).GeneralAttack != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("GeneralsAttack") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).GeneralAttack > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).GeneralAttack,
                  Images.attack,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).GeneralDefense != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("GeneralsDefense") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).GeneralDefense > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).GeneralDefense,
                  Images.defense,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).UnitsAttack != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("UnitsAttack") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).UnitsAttack > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).UnitsAttack,
                  Images.attack,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).UnitsDefense != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("UnitsDefense") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).UnitsDefense > 0 ? "+" : "") + WondersManager.wonders.get(wonderID).UnitsDefense,
                  Images.defense,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaxMorale != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaxMorale") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MaxMorale > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MaxMorale * 100.0F, 100)
                     + "%",
                  Images.morale,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ArmyMovementSpeed != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ArmyMovementSpeed") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ArmyMovementSpeed > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ArmyMovementSpeed, 100)
                     + "%",
                  Images.movementSpeed,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).SiegeEffectiveness != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("SiegeEffectiveness") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).SiegeEffectiveness > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).SiegeEffectiveness * 100.0F, 100)
                     + "%",
                  Images.siege,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ImproveRelationsModifier != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ImproveRelationsModifier") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ImproveRelationsModifier > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ImproveRelationsModifier, 100)
                     + "%",
                  Images.relations,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).IncomeFromVassals != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("IncomeFromVassals") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).IncomeFromVassals > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).IncomeFromVassals * 100.0F, 100)
                     + "%",
                  Images.vassal,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).LoanInterest != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("LoanInterest") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).LoanInterest > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).LoanInterest, 100)
                     + "%",
                  Images.loan,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaxNumberOfLoans != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaximumNumberOfLoans") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MaxNumberOfLoans > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).MaxNumberOfLoans, 100)
                     + "",
                  Images.loan,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).DiplomacyPoints != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("DiplomacyPoints") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).DiplomacyPoints > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).DiplomacyPoints * 100.0F, 100)
                     + "%",
                  Images.diplomacy,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).AggressiveExpansion != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("AggressiveExpansion") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).AggressiveExpansion > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).AggressiveExpansion, 100)
                     + "%",
                  Images.war,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RevolutionaryRisk != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("RevolutionaryRisk") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RevolutionaryRisk > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).RevolutionaryRisk, 100)
                     + "%",
                  Images.revolutionRisk,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).CoreCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("CoreConstruction") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).CoreCost > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).CoreCost, 100)
                     + "%",
                  Images.core,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).AllCharactersLifeExpectancy != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("AllCharactersLifeExpectancy") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).AllCharactersLifeExpectancy > 0 ? "+" : "")
                     + Game.lang.get("YearsX", WondersManager.wonders.get(wonderID).AllCharactersLifeExpectancy),
                  Images.council,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).DefenseBonus != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Defense") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).DefenseBonus > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).DefenseBonus, 100),
                  Images.defense,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).FortLevel != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("DefenseLevel") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).FortLevel > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).FortLevel, 100),
                  Images.fort,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).FortDefense != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ProvinceDefense") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).FortDefense > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).FortDefense, 100),
                  Game_Calendar.IMG_FORT_DEFENSE,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).DiseaseDeathRate != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("DiseasesDeathRate") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).DiseaseDeathRate > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).DiseaseDeathRate * 100.0F, 100)
                     + "%",
                  Images.disease,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaximumAmountOfGold != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaximumAmountOfGold") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).MaximumAmountOfGold > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MaximumAmountOfGold * 100.0F, 100)
                     + "%",
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).Discipline != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Discipline") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).Discipline > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).Discipline * 100.0F, 100)
                     + "%",
                  Images.discipline,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).RegimentsLimit != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("RegimentsLimit") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).RegimentsLimit > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).RegimentsLimit, 1),
                  Images.regimentsLimit,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).BattleWidth != 0) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("BattleWidth") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).BattleWidth > 0 ? "+" : "")
                     + CFG.getPrecision2((float)WondersManager.wonders.get(wonderID).BattleWidth, 1),
                  Images.battleWidth,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).ManpowerRecoveryFromADisbandedArmy != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ",
                  ""
                     + (WondersManager.wonders.get(wonderID).ManpowerRecoveryFromADisbandedArmy > 0.0F ? "+" : "")
                     + CFG.getPrecision2(WondersManager.wonders.get(wonderID).ManpowerRecoveryFromADisbandedArmy * 100.0F, 100)
                     + "%",
                  Game_Calendar.IMG_MANPOWER_DISBAND,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).Legacy != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Legacy") + ": ",
                  "" + (WondersManager.wonders.get(wonderID).Legacy > 0.0F ? "+" : "") + CFG.getPrecision2(WondersManager.wonders.get(wonderID).Legacy, 100),
                  Images.legacy,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (!Game.getProvince(iProvinceID).getWonderBuilt() && Game.getProvince(iProvinceID).wonderConstruction == null) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("Cost") + ": ",
                  "" + CFG.getPrecision2(WondersManager.getWonderConstructionCost(iProvinceID, wonderID), 10),
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            menuElements.add(
               new TextBonus(
                  Game.lang.get("ConstructionTime") + ": ",
                  Game.lang.get("DaysX", WondersManager.wonders.get(wonderID).ConstructionTime),
                  Images.buildTime,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         if (WondersManager.wonders.get(wonderID).MaintenanceCost != 0.0F) {
            menuElements.add(
               new TextBonus(
                  Game.lang.get("MaintenanceCost") + ": ",
                  Game.lang.get("XPerMonth", "" + CFG.getPrecision2(WondersManager.wonders.get(wonderID).MaintenanceCost, 100)),
                  Images.gold,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  iconWidth
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      buttonY += CFG.PADDING;
      if (Game.getProvince(iProvinceID).wonderConstruction != null) {
         menuElements.add(
            new TextBonus(
               Game.lang.get("ConstructionWillBeCompleted") + ": ",
               Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + Game.getProvince(iProvinceID).wonderConstruction.daysLeft),
               Images.time,
               Images.boxTitleBORDERWIDTH + CFG.PADDING,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               iconWidth
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else if (!Game.getProvince(iProvinceID).getWonderBuilt()) {
         menuElements.add(
            new ButtonGame(
               Game.lang.get("ConstructAWonder"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               Game.getProvince(iProvinceID).getCivID() == Game.player.iCivID
            ) {
               @Override
               public void actionElement() {
                  if (Game.getProvince(InGame_Wonder.iProvinceID).getCivID() == Game.player.iCivID && Game.getProvince(InGame_Wonder.iProvinceID).wonderID >= 0
                     )
                   {
                     if (Game.getProvince(InGame_Wonder.iProvinceID).buildWonder()) {
                        Game.hoverManager.resetHoverActive_Menu_Force();
                        Game.menuManager.rebuildInGame_Wonder();
                        InGame_Wonder.lTime = 0L;
                        Game.player.currSituation.updateCurrentSituation();
                     } else {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(
                                 WondersManager.getWonderConstructionCost(InGame_Wonder.iProvinceID, Game.getProvince(InGame_Wonder.iProvinceID).wonderID), 100
                              ),
                              Images.gold
                           );
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  if (Game.getProvince(InGame_Wonder.iProvinceID).getCivID() == Game.player.iCivID) {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ConstructAWonder") + ": ", CFG.FONT_BOLD));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           WondersManager.wonders.get(Game.getProvince(InGame_Wonder.iProvinceID).wonderID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           ""
                              + CFG.getPrecision2(
                                 WondersManager.getWonderConstructionCost(InGame_Wonder.iProvinceID, Game.getProvince(InGame_Wonder.iProvinceID).wonderID), 10
                              ),
                           CFG.FONT_BOLD_SMALL,
                           Game.getCiv(Game.player.iCivID).fGold >= WondersManager.wonders.get(Game.getProvince(InGame_Wonder.iProvinceID).wonderID).CostGold
                              ? Colors.HOVER_GOLD
                              : Colors.HOVER_NEGATIVE
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ConstructionTime") + ": ", CFG.FONT_REGULAR_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + Game.lang.get("DaysX", WondersManager.wonders.get(Game.getProvince(InGame_Wonder.iProvinceID).wonderID).ConstructionTime),
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_RIGHT
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_Image(Images.buildTime, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  } else {
                     this.menuElementHover = null;
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      if (Game.getProvince(iProvinceID).getWonderBuilt()) {
         this.initMenu(new MenuTitleIMG_FlagCenter("", Game.lang.get("WonderConstructed"), false, false, Images.title1Red) {
            @Override
            public int getFlagCivID() {
               return Game.getProvince(InGame_Wonder.iProvinceID).getCivID();
            }

            @Override
            public long getTime() {
               return InGame_Wonder.lTime;
            }
         }, menuX, menuY, menuWidth, i, menuElements, visible, true);
      } else {
         this.initMenu(new MenuTitleIMG("", false, false, Images.title1Red) {
            @Override
            public long getTime() {
               return InGame_Wonder.lTime;
            }
         }, menuX, menuY, menuWidth, i, menuElements, visible, true);
      }

      if (visible) {
         lTime = CFG.currentTimeMillis;
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * 4 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (rebuildMenu) {
         rebuildMenu = false;
         Game.menuManager.rebuildInGame_Wonder();
         lTime = 0L;
      }
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle()
         .setText(
            Game.getProvince(iProvinceID).wonderID >= 0 ? WondersManager.wonders.get(Game.getProvince(iProvinceID).wonderID).Name : Game.lang.get("Wonder")
         );
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;

      try {
         if (!visible && wonderIMG != null) {
            wonderIMG.dispose();
            wonderIMG = null;
         }
      } catch (Exception var3) {
      }
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_WONDERS) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }
}
