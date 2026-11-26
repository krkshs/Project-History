package aoc.kingdoms.lukasz.menusInGame.Goods;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon3;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Resource;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_LargestProducer;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Goods extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iCivID = 0;
   public static int sortBy = 0;
   public static boolean inGoods = true;
   public static boolean inGoodsView = false;

   public static InGame_Goods.BonusText getBonusText(int iResID) {
      if (ResourcesManager.lResources.get(iResID).MonthlyIncome != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("MonthlyIncome") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).MonthlyIncome, 100), Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).TaxEfficiency != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("TaxEfficiency") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).TaxEfficiency, 100) + "%", Images.tax
         );
      } else if (ResourcesManager.lResources.get(iResID).IncomeProduction != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("IncomeProduction") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).IncomeProduction, 100) + "%", Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).ProductionEfficiency != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ProductionEfficiency") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ProductionEfficiency, 100) + "%",
            Images.goods
         );
      } else if (ResourcesManager.lResources.get(iResID).ProvinceMaintenance != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ProvinceMaintenance") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ProvinceMaintenance, 100) + "%",
            Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).GrowthRate != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("GrowthRate") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).GrowthRate, 100) + "%", Images.populationGrowth
         );
      } else if (ResourcesManager.lResources.get(iResID).MonthlyLegacy != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("MonthlyLegacy") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).MonthlyLegacy, 100), Images.legacy
         );
      } else if (ResourcesManager.lResources.get(iResID).MaxManpower != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("MaximumManpower") + ": ", "+" + (int)ResourcesManager.lResources.get(iResID).MaxManpower, Game_Calendar.IMG_MANPOWER_UP
         );
      } else if (ResourcesManager.lResources.get(iResID).ManpowerRecoverySpeed != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ManpowerRecoverySpeed") + ": ",
            (ResourcesManager.lResources.get(iResID).ManpowerRecoverySpeed > 0.0F ? "+" : "")
               + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ManpowerRecoverySpeed * 100.0F, 100)
               + "%",
            Game_Calendar.IMG_MANPOWER_TIME
         );
      } else if (ResourcesManager.lResources.get(iResID).ArmyMaintenance != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ArmyMaintenance") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ArmyMaintenance, 100) + "%",
            Images.armyMaintenance
         );
      } else if (ResourcesManager.lResources.get(iResID).RecruitmentTime != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("RecruitmentTime") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).RecruitmentTime, 100) + "%",
            Game_Calendar.IMG_MANPOWER_TIME
         );
      } else if (ResourcesManager.lResources.get(iResID).RecruitArmyCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ArmyRecruitmentCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).RecruitArmyCost, 100) + "%",
            Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).RecruitArmyFirstLineCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("FirstLineArmyRecruitmentCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).RecruitArmyFirstLineCost, 100) + "%",
            Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).RecruitArmySecondLineCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("SecondLineArmyRecruitmentCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).RecruitArmySecondLineCost, 100) + "%",
            Images.gold
         );
      } else if (ResourcesManager.lResources.get(iResID).ResearchPoints != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ResearchPerMonth") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ResearchPoints, 100),
            Game_Calendar.IMG_TECHNOLOGY
         );
      } else if (ResourcesManager.lResources.get(iResID).TechnologyCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("TechnologyCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).TechnologyCost, 100) + "%",
            Game_Calendar.IMG_TECHNOLOGY
         );
      } else if (ResourcesManager.lResources.get(iResID).ConstructionCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ConstructionCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ConstructionCost * 100.0F, 100) + "%",
            Images.construction
         );
      } else if (ResourcesManager.lResources.get(iResID).AdministrationBuildingsCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("AdministrationBuildingsCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).AdministrationBuildingsCost * 100.0F, 100) + "%",
            Images.construction
         );
      } else if (ResourcesManager.lResources.get(iResID).MilitaryBuildingsCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("MilitaryBuildingsCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).MilitaryBuildingsCost * 100.0F, 100) + "%",
            Images.construction
         );
      } else if (ResourcesManager.lResources.get(iResID).EconomyBuildingsCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("EconomyBuildingsCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).EconomyBuildingsCost * 100.0F, 100) + "%",
            Images.construction
         );
      } else if (ResourcesManager.lResources.get(iResID).ConstructionTime != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ConstructionTime") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ConstructionTime * 100.0F, 100) + "%",
            Images.buildTime
         );
      } else if (ResourcesManager.lResources.get(iResID).InvestInEconomyCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("InvestInEconomyCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).InvestInEconomyCost * 100.0F, 100) + "%",
            Game_Calendar.IMG_ECONOMY_UP
         );
      } else if (ResourcesManager.lResources.get(iResID).IncreaseManpowerCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("IncreaseManpowerCost") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).IncreaseManpowerCost, 100) + "%",
            Game_Calendar.IMG_MANPOWER_UP
         );
      } else if (ResourcesManager.lResources.get(iResID).GeneralAttack != 0) {
         return new InGame_Goods.BonusText(Game.lang.get("GeneralsAttack") + ": ", "+" + ResourcesManager.lResources.get(iResID).GeneralAttack, Images.attack);
      } else if (ResourcesManager.lResources.get(iResID).GeneralDefense != 0) {
         return new InGame_Goods.BonusText(
            Game.lang.get("GeneralsDefense") + ": ", "+" + ResourcesManager.lResources.get(iResID).GeneralDefense, Images.defense
         );
      } else if (ResourcesManager.lResources.get(iResID).UnitsAttack != 0) {
         return new InGame_Goods.BonusText(Game.lang.get("UnitsAttack") + ": ", "+" + ResourcesManager.lResources.get(iResID).UnitsAttack, Images.attack);
      } else if (ResourcesManager.lResources.get(iResID).UnitsDefense != 0) {
         return new InGame_Goods.BonusText(Game.lang.get("UnitsDefense") + ": ", "+" + ResourcesManager.lResources.get(iResID).UnitsDefense, Images.defense);
      } else if (ResourcesManager.lResources.get(iResID).MaxMorale != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("Morale") + ": ", "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).MaxMorale * 100.0F, 100) + "%", Images.morale
         );
      } else if (ResourcesManager.lResources.get(iResID).ArmyMovementSpeed != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ArmyMovementSpeed") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ArmyMovementSpeed, 100) + "%",
            Images.movementSpeed
         );
      } else if (ResourcesManager.lResources.get(iResID).SiegeEffectiveness != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("SiegeEffectiveness") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).SiegeEffectiveness * 100.0F, 100) + "%",
            Images.siege
         );
      } else if (ResourcesManager.lResources.get(iResID).ImproveRelationsModifier != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("ImproveRelationsModifier") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).ImproveRelationsModifier, 100) + "%",
            Images.relations
         );
      } else if (ResourcesManager.lResources.get(iResID).IncomeFromVassals != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("IncomeFromVassals") + ": ",
            "+" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).IncomeFromVassals * 100.0F, 100) + "%",
            Images.vassal
         );
      } else if (ResourcesManager.lResources.get(iResID).LoanInterest != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("LoanInterest") + ": ", "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).LoanInterest, 100) + "%", Images.loan
         );
      } else if (ResourcesManager.lResources.get(iResID).AggressiveExpansion != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("AggressiveExpansion") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).AggressiveExpansion, 100) + "%",
            Images.war
         );
      } else if (ResourcesManager.lResources.get(iResID).RevolutionaryRisk != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("RevolutionaryRisk") + ": ",
            "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).RevolutionaryRisk, 100) + "%",
            Images.revolutionRisk
         );
      } else if (ResourcesManager.lResources.get(iResID).CoreCost != 0.0F) {
         return new InGame_Goods.BonusText(
            Game.lang.get("CoreConstruction") + ": ", "" + CFG.getPrecision2(ResourcesManager.lResources.get(iResID).CoreCost, 100) + "%", Images.core
         );
      } else if (ResourcesManager.lResources.get(iResID).RegimentsLimit != 0) {
         return new InGame_Goods.BonusText(
            Game.lang.get("RegimentsLimit") + ": ",
            "+" + CFG.getPrecision2((float)ResourcesManager.lResources.get(iResID).RegimentsLimit, 1),
            Images.regimentsLimit
         );
      } else {
         return ResourcesManager.lResources.get(iResID).BattleWidth != 0
            ? new InGame_Goods.BonusText(
               Game.lang.get("BattleWidth") + ": ", "+" + CFG.getPrecision2((float)ResourcesManager.lResources.get(iResID).BattleWidth, 1), Images.battleWidth
            )
            : new InGame_Goods.BonusText(Game.lang.get("None"), "", Images.x);
      }
   }

   public InGame_Goods() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.title928).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonResW = (int)(CFG.BUTTON_WIDTH * 1.25F);
      int buttonResH = ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
      int elementWidth = menuWidth - paddingLeft * 2 - buttonResW - ImageManager.getImage(Images.flagOverDefault).getWidth() - CFG.PADDING * 4;
      inGoods = true;
      inGoodsView = true;
      menuElements.add(
         new Text_TitleBlueSort(
            sortBy == 0, false, Game.lang.get("Name"), -1, buttonX, buttonY, buttonResW + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Goods.sortBy != 0) {
                  InGame_Goods.sortBy = 0;
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Name"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            sortBy == 1, false, Game.lang.get("Bonus"), -1, buttonX, buttonY, (int)(elementWidth * 0.65F), CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Goods.sortBy != 1) {
                  InGame_Goods.sortBy = 1;
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Bonus"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            sortBy == 2,
            false,
            Game.lang.get("LargestProducer"),
            -1,
            buttonX,
            buttonY,
            menuWidth - buttonX - Images.boxTitleBORDERWIDTH,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Goods.sortBy != 2) {
                  InGame_Goods.sortBy = 2;
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Civilization"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      buttonX = paddingLeft;
      List<Integer> listRes = new ArrayList<>();
      List<String> tBonus = new ArrayList<>();

      for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
         if (ResourcesManager.worldResourcesProduced.get(i) > 0) {
            listRes.add(i);
         }
      }

      if (sortBy == 1) {
         int ix = 0;

         for (int iSize = listRes.size(); ix < iSize; ix++) {
            tBonus.add(getBonusText(listRes.get(ix)).bonusText);
         }
      }

      while (listRes.size() > 0) {
         int toAddID = 0;
         if (sortBy == 0) {
            for (int o = 1; o < listRes.size(); o++) {
               if (CFG.compareAlphabetic_TwoString(ResourcesManager.getResourceName(listRes.get(toAddID)), ResourcesManager.getResourceName(listRes.get(o)))) {
                  toAddID = o;
               }
            }
         } else if (sortBy == 1) {
            for (int ox = 1; ox < listRes.size(); ox++) {
               if (CFG.compareAlphabetic_TwoString(tBonus.get(toAddID), tBonus.get(ox))) {
                  toAddID = ox;
               }
            }
         } else {
            for (int oxx = 1; oxx < listRes.size(); oxx++) {
               if (CFG.compareAlphabetic_TwoString(
                  Game.getCiv(ResourcesManager.worldResources_LargestProducer.get(listRes.get(toAddID))).getCivName(),
                  Game.getCiv(ResourcesManager.worldResources_LargestProducer.get(listRes.get(oxx))).getCivName()
               )) {
                  toAddID = oxx;
               }
            }
         }

         menuElements.add(
            new TextIcon_Resource(ResourcesManager.getResourceName(listRes.get(toAddID)), listRes.get(toAddID), buttonX, buttonY, buttonResW, buttonResH) {
               @Override
               public void actionElement() {
                  InGame_Goods_Provinces.RESOURCE_ID = this.getCurrent();
                  InGame_Goods_Provinces.lTime = 0L;
                  InGame_Goods.lTime = 0L;
                  Game.menuManager.rebuildInGame_Goods_Provinces();
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         InGame_Goods.BonusText nBonus = getBonusText(listRes.get(toAddID));
         menuElements.add(new TextIcon3(nBonus.bonusText, nBonus.bonusText2, nBonus.bonusIMGID, buttonX, buttonY, (int)(elementWidth * 0.65F), buttonResH));
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_LargestProducer(
               ""
                  + CFG.getPrecision2(ResourcesManager.worldResources_LargestProducer_Amount.get(listRes.get(toAddID)).intValue() / 100.0F, 10)
                  + " / "
                  + CFG.getPrecision2(ResourcesManager.worldResourcesProduced.get(listRes.get(toAddID)).intValue() / 100.0F, 10),
               listRes.get(toAddID),
               CFG.FONT_REGULAR,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.25F),
               buttonResH
            ) {
               @Override
               public void actionElement() {
                  InGame_Goods_LargestProducers.RESOURCE_ID = this.getCurrent();
                  InGame_Goods_LargestProducers.lTime = 0L;
                  InGame_Goods.lTime = 0L;
                  Game.menuManager.rebuildInGame_Goods_LargestProducers();
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_LargestProducer(
               ""
                  + CFG.getPrecision2(
                     (float)Math.min(
                        100.0,
                        Math.floor(
                           (float)ResourcesManager.worldResources_LargestProducer_Amount.get(listRes.get(toAddID)).intValue()
                              / ResourcesManager.worldResourcesProduced.get(listRes.get(toAddID)).intValue()
                              * 100.0F
                        )
                     ),
                     10
                  )
                  + "%",
               listRes.get(toAddID),
               CFG.FONT_BOLD,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.1F),
               buttonResH
            ) {
               @Override
               public void actionElement() {
                  InGame_Goods_LargestProducers.RESOURCE_ID = this.getCurrent();
                  InGame_Goods_LargestProducers.lTime = 0L;
                  InGame_Goods.lTime = 0L;
                  Game.menuManager.rebuildInGame_Goods_LargestProducers();
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonFlag2(ResourcesManager.worldResources_LargestProducer.get(listRes.get(toAddID)), buttonX, buttonY, true) {
            @Override
            public void actionElement() {
               if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                  if (!Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getDrawProvince()) {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  }

                  Game.setActiveProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  Game.menuManager.setVisibleInGame_Goods(false);
                  Game.menuManager.rebuildInGame_Civ();
               }
            }
         });
         menuElements.add(
            new Text_StaticBG(
               Game.getCiv(ResourcesManager.worldResources_LargestProducer.get(listRes.get(toAddID))).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY + ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.PADDING,
               ImageManager.getImage(Images.flagOverDefault).getWidth(),
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += buttonResH + buttonYPadding;
         buttonX = paddingLeft;
         listRes.remove(toAddID);
         if (sortBy == 1) {
            tBonus.remove(toAddID);
         }
      }

      buttonY = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (buttonY < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING * 2;
         }
      }

      ix = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY + CFG.PADDING, ix)));
      this.initMenu(new MenuTitleIMG_DoubleText("", Game.lang.get("Year") + ": " + Game_Calendar.currentYear, false, false, Images.title928) {
         @Override
         public long getTime() {
            return InGame_Goods.lTime2;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, ix, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      float fA = 1.0F;
      if (lTime + 60L >= CFG.currentTimeMillis) {
         fA = (float)(CFG.currentTimeMillis - lTime) / 60.0F;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.2F * fA));
      Images.pix.draw2(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.65F * fA));
      Images.gradientFull.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(Color.WHITE);
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * 4 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop928,
         Images.insideBot928
      );
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("LargestGoodsProducers"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameGoods();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_Goods(false);
   }

   public static class BonusText {
      public String bonusText;
      public String bonusText2;
      public int bonusIMGID;

      public BonusText(String bonusText, String bonusText2, int bonusIMGID) {
         this.bonusText = bonusText;
         this.bonusText2 = bonusText2;
         this.bonusIMGID = bonusIMGID;
      }
   }
}
