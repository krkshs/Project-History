package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceBonuses extends Menu {
   public static int iProvinceID = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_ProvinceBonuses() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = InGame_ProvinceInfo.menuPosY;
      int buttonYPadding = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int buttonY = buttonYPadding;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 2;
      int buttonH = CFG.TEXT_HEIGHT + CFG.PADDING * 6;
      List<MenuElement> mElementsToSort = new ArrayList<>();
      if (Game.getProvince(iProvinceID).provBonuses.MonthlyLegacy != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyLegacy") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.MonthlyLegacy, 100),
               Images.legacy,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.DefenseBonus != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DefenseBonus") + ": ",
               "+" + CFG.getPrecision2((float)Game.getProvince(iProvinceID).provBonuses.DefenseBonus, 100),
               Images.defense,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.FortLevel != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DefenseLevel") + ": ",
               "+" + CFG.getPrecision2((float)Game.getProvince(iProvinceID).provBonuses.FortLevel, 100),
               Images.fort,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.FortDefense != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProvinceDefense") + ": ",
               "+" + CFG.getPrecision2((float)Game.getProvince(iProvinceID).provBonuses.FortDefense, 1),
               Game_Calendar.IMG_FORT_DEFENSE,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.MaximumManpower != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumManpower") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.MaximumManpower > 0 ? "+" : "")
                  + CFG.getPrecision2((float)Game.getProvince(iProvinceID).provBonuses.MaximumManpower, 1),
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.LocalManpower != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LocalManpower") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.LocalManpower > 0 ? "+" : "")
                  + CFG.getPrecision2((float)(Game.getProvince(iProvinceID).provBonuses.LocalManpower * 100), 10),
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.RecruitArmyCostInProvince != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyRecruitmentCostInProvince") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.RecruitArmyCostInProvince > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.RecruitArmyCostInProvince, 100)
                  + "%",
               Game_Calendar.IMG_MANPOWER,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ConstructionCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.ConstructionCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ConstructionCost * 100.0F, 100)
                  + "%",
               Images.construction,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ConstructionTimeBonus != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionTime") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.ConstructionTimeBonus > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ConstructionTimeBonus * 100.0F, 100)
                  + "%",
               Images.buildTime,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.LocalGrowthRate != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LocalGrowthRate") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.LocalGrowthRate > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.LocalGrowthRate, 100)
                  + "%",
               Images.populationGrowth,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Game.getProvince(InGame_ProvinceBonuses.iProvinceID).provBonuses.LocalGrowthRate > 0.0F
                     ? Colors.HOVER_POSITIVE
                     : Colors.HOVER_NEGATIVE;
               }
            }
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.InvestInEconomyCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("InvestInEconomyCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.InvestInEconomyCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.InvestInEconomyCost * 100.0F, 100)
                  + "%",
               Game_Calendar.IMG_ECONOMY_UP,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.IncreaseGrowthRateCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseGrowthRateCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.IncreaseGrowthRateCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.IncreaseGrowthRateCost * 100.0F, 100)
                  + "%",
               Images.populationUp,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.DevelopInfrastructureCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DevelopInfrastructureCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.DevelopInfrastructureCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.DevelopInfrastructureCost * 100.0F, 100)
                  + "%",
               Images.infrastructureUp,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.IncreaseManpowerCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseManpowerCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.IncreaseManpowerCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.IncreaseManpowerCost * 100.0F, 100)
                  + "%",
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.IncreaseTaxEfficiencyCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncreaseTaxEfficiencyCost") + ": ",
               (Game.getProvince(iProvinceID).provBonuses.IncreaseTaxEfficiencyCost > 0.0F ? "+" : "")
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.IncreaseTaxEfficiencyCost * 100.0F, 100)
                  + "%",
               Images.taxUp,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.MaintenanceCost != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("BuildingsMaintenance") + ": ",
               "" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.MaintenanceCost, 100),
               Images.buildings,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ProvinceMaintenance != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProvinceMaintenance") + ": ",
               "" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ProvinceMaintenance, 100) + "%",
               Images.provinces,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.MonthlyIncome != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MonthlyIncome") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.MonthlyIncome, 100),
               Images.gold,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.MaxInfrastructure != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("MaximumInfrastructureLevel") + ": ",
               "+" + Game.getProvince(iProvinceID).provBonuses.MaxInfrastructure,
               Images.infrastructure,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.BuildingSlots != 0) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("BuildingSlots") + ": ",
               "+" + Game.getProvince(iProvinceID).provBonuses.BuildingSlots,
               Images.build,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.LocalTaxEfficiency != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LocalTaxEfficiency") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.LocalTaxEfficiency, 100),
               Images.tax,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ArmyMovementSpeed != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyMovementSpeed") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ArmyMovementSpeed * 10.0F, 100) + "%",
               Images.movementSpeed,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.DiseaseDeathRate != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DiseasesDeathRate") + ": ",
               "" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.DiseaseDeathRate * 100.0F, 100) + "%",
               Images.disease,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.CasualtiesNuclearAttacks != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("CasualtiesFromNuclearAttacks") + ": ",
               "" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.CasualtiesNuclearAttacks * 100.0F, 100) + "%",
               Images.nuke,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.Economy != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Economy") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.Economy, 100),
               Game_Calendar.IMG_ECONOMY,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ResearchPoints != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ResearchPerMonth") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ResearchPoints, 100),
               Game_Calendar.IMG_TECHNOLOGY,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.IncomeProduction != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("IncomeProduction") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.IncomeProduction, 100) + "%",
               Images.goods,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      if (Game.getProvince(iProvinceID).provBonuses.ProductionEfficiency != 0.0F) {
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProductionEfficiency") + ": ",
               "+" + CFG.getPrecision2(Game.getProvince(iProvinceID).provBonuses.ProductionEfficiency, 100) + "%",
               Images.goods,
               paddingLeft,
               buttonYPadding,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
      }

      while (mElementsToSort.size() > 0) {
         int addID = 0;
         int o = 1;

         for (int oSize = mElementsToSort.size(); o < oSize; o++) {
            if (CFG.compareAlphabetic_TwoString(mElementsToSort.get(addID).getText(), mElementsToSort.get(o).getText())) {
               addID = o;
            }
         }

         menuElements.add(mElementsToSort.get(addID));
         menuElements.get(menuElements.size() - 1).setPosY(buttonY);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         mElementsToSort.remove(addID);
      }

      if (menuElements.size() == 0) {
         menuElements.add(
            new Text_Static(Game.lang.get("None") + ".", CFG.FONT_REGULAR_SMALL, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
               @Override
               protected Color getColor(boolean isActive) {
                  if (isActive) {
                     return Colors.BUTTON_TEXT_ACTIVE;
                  } else if (this.getIsHovered()) {
                     return Colors.BUTTON_TEXT_HOVERED;
                  } else {
                     return this.getClickable() ? Colors.BUTTON_TEXT : Colors.BUTTON_TEXT_DISABLED;
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      if (Game.getProvince(iProvinceID).getInfrastructure() > 0) {
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("Infrastructure"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("TaxEfficiency") + ": ",
               "+"
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_TAX_EFFICIENCY_PER_LVL, 100)
                  + "%",
               Images.tax,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionCost") + ": ",
               ""
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_COST_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.build,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ConstructionTime") + ": ",
               ""
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_TIME_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.buildTime,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("BuildingSlots") + ": ",
               "+" + Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_BUILDINGS_SLOT_PER_LVL,
               Images.build,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("InvestInEconomyCost") + ": ",
               ""
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_INVEST_COST_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Game_Calendar.IMG_ECONOMY_UP,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProductionEfficiency") + ": ",
               "+"
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_PRODUCTION_EFFICIENCY_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.goods,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("RecruitmentTime") + ": ",
               ""
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_RECRUITMENT_TIME_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Game_Calendar.IMG_MANPOWER_TIME,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LocalGrowthRate") + ": ",
               "+"
                  + CFG.getPrecision2(Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_GROWTH_RATE_PER_LVL, 100)
                  + "%",
               Images.populationGrowth,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ArmyMovementSpeed") + ": ",
               "+"
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_ARMY_MOVEMENT_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.movementSpeed,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            )
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("ProvinceMaintenance") + ": ",
               "+"
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_PROVINCE_MAINTENANCE_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.gold,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
               }
            }
         );
         mElementsToSort.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("DiseasesDeathRate") + ": ",
               ""
                  + CFG.getPrecision2(
                     Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_DISEASE_DEATH_RATE_PER_LVL * 100.0F, 100
                  )
                  + "%",
               Images.disease,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               maxIconW
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
               }
            }
         );
         if (Game.getProvince(iProvinceID).wonderID >= 0) {
            mElementsToSort.add(
               new ButtonStatsRectIMG_Bonuses(
                  Game.lang.get("Wonder") + ", " + Game.lang.get("ConstructionCost") + ": ",
                  ""
                     + CFG.getPrecision2(
                        Game.getProvince(iProvinceID).getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_WONDER_CONSTRUCTION_COST_PER_LVL * 100.0F,
                        100
                     )
                     + "%",
                  Images.mapModesWonders,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  buttonH,
                  maxIconW
               )
            );
         }

         while (mElementsToSort.size() > 0) {
            int addID = 0;
            int o = 1;

            for (int oSizex = mElementsToSort.size(); o < oSizex; o++) {
               if (CFG.compareAlphabetic_TwoString(mElementsToSort.get(addID).getText(), mElementsToSort.get(o).getText())) {
                  addID = o;
               }
            }

            menuElements.add(mElementsToSort.get(addID));
            menuElements.get(menuElements.size() - 1).setPosY(buttonY);
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            mElementsToSort.remove(addID);
         }
      }

      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, Math.max(buttonY, i))));
      this.initMenu(new MenuTitleIMG_DoubleText("", Game.getProvince(iProvinceID).getProvinceName(), false, false, Images.title1Red) {
         @Override
         public long getTime() {
            return InGame_ProvinceBonuses.lTime;
         }
      }, InGame_ProvinceInfo.menuW + CFG.PADDING * 2, menuY, menuWidth, i, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY + CFG.BUTTON_HEIGHT * 2 / 5 - (int)(CFG.BUTTON_HEIGHT * 2 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.newGameOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.newGameOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("ProvinceBonuses"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut();
   }
}
