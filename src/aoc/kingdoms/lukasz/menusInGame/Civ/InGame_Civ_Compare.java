package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonResource2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Rank_Ranking;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Horizontal;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Compare;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_Compare extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int civLeft = 0;
   public static int civRight = 0;
   public static int civLeft_Rank = -1;
   public static int civRight_Rank = -1;

   public InGame_Civ_Compare() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonX = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.BUTTON_HEIGHT3;
      int buttonW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 2;
      int rightPosX = paddingLeft + CFG.PADDING + buttonW;
      int rightW = (buttonW - CFG.PADDING) / 2;
      int rightPosX2 = rightPosX + CFG.PADDING + rightW;
      int maxWidth = ImageManager.getImage(Images.compareBig).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new ButtonFlag_Diplomacy(
            civLeft,
            menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonYPadding + tempTitlePaddingY,
            true
         )
      );
      menuElements.add(new ButtonFlag_Diplomacy(civRight, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonYPadding + tempTitlePaddingY, true));
      menuElements.add(
         new Text_Static_ID(
            civRight,
            Game.getCiv(civRight).getCivName(),
            CFG.FONT_REGULAR,
            -1,
            menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonYPadding,
            tempTextW,
            tempTitleH
         )
      );
      menuElements.add(new Text_Static_ID(civLeft, Game.getCiv(civLeft).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonYPadding, tempTextW, tempTitleH));
      menuElements.add(new TextIcon_Diplomacy(Images.compareBig, paddingLeft, buttonYPadding, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
         @Override
         public Color getColorBar() {
            return Colors.TECH_BLUE;
         }

         @Override
         public void actionElement() {
            Game.menuManager.rebuildInGame_Civ();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonH2 = (int)(buttonH * 1.25F);
      int buttonTopW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 3) / 4;
      menuElements.add(new ButtonIdeology2(Game.getCiv(civLeft).getIdeologyID(), paddingLeft, buttonY, buttonTopW, buttonH2));
      menuElements.add(new ButtonReligion2(Game.getCiv(civLeft).getReligionID(), buttonX + CFG.PADDING + buttonTopW, buttonY, buttonTopW, buttonH2));
      menuElements.add(new ButtonReligion2(Game.getCiv(civRight).getReligionID(), buttonX + (CFG.PADDING + buttonTopW) * 2, buttonY, buttonTopW, buttonH2));
      menuElements.add(new ButtonIdeology2(Game.getCiv(civRight).getIdeologyID(), buttonX + (CFG.PADDING + buttonTopW) * 3, buttonY, buttonTopW, buttonH2));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Ranking"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Ranking"), Images.rankGold, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new ButtonStatsRectIMG_Rank_Ranking(
            "" + Game.getCiv(civLeft).iCivRankPosition,
            CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(civLeft),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            ImageManager.getImage(Images.rankGold).getWidth()
         )
      );
      menuElements.add(
         new ButtonStatsRectIMG_Rank_Ranking(
            "" + Game.getCiv(civRight).iCivRankPosition,
            CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(civRight),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            ImageManager.getImage(Images.rankGold).getWidth()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Prestige"), Images.rankSilver, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("Prestige"),
            Images.rankSilver,
            "" + CFG.getNumberWithSpaces("" + (int)Math.ceil(Game.getCiv(civLeft).iCivRankScore)),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iCivRankScore > Game.getCiv(civRight).iCivRankScore
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("Prestige"),
            Images.rankSilver,
            "" + CFG.getNumberWithSpaces("" + (int)Math.ceil(Game.getCiv(civRight).iCivRankScore)),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iCivRankScore < Game.getCiv(civRight).iCivRankScore
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Provinces"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Provinces"), Images.provinces, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("Provinces"),
            Images.provinces,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getNumOfProvinces()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getNumOfProvinces() > Game.getCiv(civRight).getNumOfProvinces()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("Provinces"),
            Images.provinces,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getNumOfProvinces()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getNumOfProvinces() < Game.getCiv(civRight).getNumOfProvinces()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      long popLeft = Game.getCiv(civLeft).getPopulationTotal();
      long popRight = Game.getCiv(civRight).getPopulationTotal();
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Population"), Images.population, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      if (popLeft > 999999999L) {
         menuElements.add(
            new Text_StaticBG_Compare(
               true, Game.lang.get("Population"), Images.population, "" + CFG.getShortNumber(popLeft), rightPosX, buttonY, rightW, buttonH, popLeft > popRight
            )
         );
         menuElements.add(
            new Text_StaticBG_Compare(
               false,
               Game.lang.get("Population"),
               Images.population,
               "" + CFG.getShortNumber(popRight),
               rightPosX2,
               buttonY,
               rightW,
               buttonH,
               popLeft < popRight
            )
         );
      } else {
         menuElements.add(
            new Text_StaticBG_Compare(
               true,
               Game.lang.get("Population"),
               Images.population,
               "" + CFG.getNumberWithSpaces("" + popLeft),
               rightPosX,
               buttonY,
               rightW,
               buttonH,
               popLeft > popRight
            )
         );
         menuElements.add(
            new Text_StaticBG_Compare(
               false,
               Game.lang.get("Population"),
               Images.population,
               "" + CFG.getNumberWithSpaces("" + popRight),
               rightPosX2,
               buttonY,
               rightW,
               buttonH,
               popLeft < popRight
            )
         );
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Economy"), Game_Calendar.IMG_ECONOMY, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("Economy"),
            Game_Calendar.IMG_ECONOMY,
            "" + CFG.getNumberWithSpaces("" + (int)Math.ceil(Game.getCiv(civLeft).getEconomyTotal())),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getEconomyTotal() > Game.getCiv(civRight).getEconomyTotal()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("Economy"),
            Game_Calendar.IMG_ECONOMY,
            "" + CFG.getNumberWithSpaces("" + (int)Math.ceil(Game.getCiv(civRight).getEconomyTotal())),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getEconomyTotal() < Game.getCiv(civRight).getEconomyTotal()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Infrastructure"), Images.infrastructure, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("Infrastructure"),
            Images.infrastructure,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getInfrastructure()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getInfrastructure() > Game.getCiv(civRight).getInfrastructure()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("Infrastructure"),
            Images.infrastructure,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getInfrastructure()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getInfrastructure() < Game.getCiv(civRight).getInfrastructure()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("ConstructedBuildings"), Images.buildings, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("ConstructedBuildings"),
            Images.buildings,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getConstructedBuildings()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getConstructedBuildings() > Game.getCiv(civRight).getConstructedBuildings()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("ConstructedBuildings"),
            Images.buildings,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getConstructedBuildings()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getConstructedBuildings() < Game.getCiv(civRight).getConstructedBuildings()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonResource2(ResourcesManager.getLargestGoodsProducedByCiv(civLeft), buttonX, buttonY, buttonW, buttonH2) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(InGame_Civ_Compare.civLeft, Game.lang.get("ProducedGoods")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               List<Float> goodsProduced = new ArrayList<>();

               for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
                  goodsProduced.add(0.0F);
               }

               int bestResourceID = -1;

               for (int i = 0; i < Game.getCiv(InGame_Civ_Compare.civLeft).getNumOfProvinces(); i++) {
                  if (Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getProvinceID(i)).getResourceID() >= 0) {
                     goodsProduced.set(
                        Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getProvinceID(i)).getResourceID(),
                        goodsProduced.get(Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getProvinceID(i)).getResourceID())
                           + ResourcesManager.getProducedGoods(Game.getCiv(InGame_Civ_Compare.civLeft).getProvinceID(i))
                     );
                     bestResourceID = Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getProvinceID(i)).getResourceID();
                  }
               }

               if (bestResourceID >= 0) {
                  int numAdded = 0;

                  while (numAdded++ < 6) {
                     for (int ix = 0; ix < ResourcesManager.iResourcesSize; ix++) {
                        if (goodsProduced.get(bestResourceID) < goodsProduced.get(ix)) {
                           bestResourceID = ix;
                        }
                     }

                     if (!(goodsProduced.get(bestResourceID) > 0.0F)) {
                        break;
                     }

                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusResource(
                           ResourcesManager.getResourceName(bestResourceID) + ": ",
                           CFG.getPrecision2(goodsProduced.get(bestResourceID), 10),
                           bestResourceID,
                           CFG.FONT_BOLD_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     goodsProduced.set(bestResourceID, 0.0F);
                  }
               } else {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("None"), "", Images.resourceNone, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               goodsProduced.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonResource2(ResourcesManager.getLargestGoodsProducedByCiv(civRight), buttonX + CFG.PADDING + buttonW, buttonY, buttonW, buttonH2) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(InGame_Civ_Compare.civRight, Game.lang.get("ProducedGoods")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               List<Float> goodsProduced = new ArrayList<>();

               for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
                  goodsProduced.add(0.0F);
               }

               int bestResourceID = -1;

               for (int i = 0; i < Game.getCiv(InGame_Civ_Compare.civRight).getNumOfProvinces(); i++) {
                  if (Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getProvinceID(i)).getResourceID() >= 0) {
                     goodsProduced.set(
                        Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getProvinceID(i)).getResourceID(),
                        goodsProduced.get(Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getProvinceID(i)).getResourceID())
                           + ResourcesManager.getProducedGoods(Game.getCiv(InGame_Civ_Compare.civRight).getProvinceID(i))
                     );
                     bestResourceID = Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getProvinceID(i)).getResourceID();
                  }
               }

               if (bestResourceID >= 0) {
                  int numAdded = 0;

                  while (numAdded++ < 6) {
                     for (int ix = 0; ix < ResourcesManager.iResourcesSize; ix++) {
                        if (goodsProduced.get(bestResourceID) < goodsProduced.get(ix)) {
                           bestResourceID = ix;
                        }
                     }

                     if (!(goodsProduced.get(bestResourceID) > 0.0F)) {
                        break;
                     }

                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusResource(
                           ResourcesManager.getResourceName(bestResourceID) + ": ",
                           CFG.getPrecision2(goodsProduced.get(bestResourceID), 10),
                           bestResourceID,
                           CFG.FONT_BOLD_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     goodsProduced.set(bestResourceID, 0.0F);
                  }
               } else {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("None"), "", Images.resourceNone, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               goodsProduced.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Civilization"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG(Game.lang.get("UnlockedTechnologies"), Game_Calendar.IMG_TECHNOLOGY, paddingLeft, buttonY, buttonW, buttonH, maxIconW)
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("UnlockedTechnologies"),
            Game_Calendar.IMG_TECHNOLOGY,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getResearchedTechnologies()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getResearchedTechnologies() > Game.getCiv(civRight).getResearchedTechnologies()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("UnlockedTechnologies"),
            Game_Calendar.IMG_TECHNOLOGY,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getResearchedTechnologies()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getResearchedTechnologies() < Game.getCiv(civRight).getResearchedTechnologies()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("UnlockedLegacy"), Images.legacy, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("UnlockedLegacy"),
            Images.legacy,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getLegaciesUnlocked()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getLegaciesUnlocked() > Game.getCiv(civRight).getLegaciesUnlocked()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("UnlockedLegacy"),
            Images.legacy,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getLegaciesUnlocked()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getLegaciesUnlocked() < Game.getCiv(civRight).getLegaciesUnlocked()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("CivilizationAdvantages"), Images.advantages, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("CivilizationAdvantages"),
            Images.advantages,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).iAdvantagesSize),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iAdvantagesSize > Game.getCiv(civRight).iAdvantagesSize
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("CivilizationAdvantages"),
            Images.advantages,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).iAdvantagesSize),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iAdvantagesSize < Game.getCiv(civRight).iAdvantagesSize
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game_Ages.getVassals(), Images.vassal, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get(Game_Ages.getVassals()),
            Images.vassal,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).diplomacy.iVassalsSize),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).diplomacy.iVassalsSize > Game.getCiv(civRight).diplomacy.iVassalsSize
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get(Game_Ages.getVassals()),
            Images.vassal,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).diplomacy.iVassalsSize),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).diplomacy.iVassalsSize < Game.getCiv(civRight).diplomacy.iVassalsSize
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Military"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("MaximumManpower"), Game_Calendar.IMG_MANPOWER, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("MaximumManpower"),
            Game_Calendar.IMG_MANPOWER,
            "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(civLeft).fManpowerMax),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).fManpowerMax > Game.getCiv(civRight).fManpowerMax
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("MaximumManpower"),
            Game_Calendar.IMG_MANPOWER,
            "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(civRight).fManpowerMax),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).fManpowerMax < Game.getCiv(civRight).fManpowerMax
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("RegimentsLimit"), Images.regimentsLimit, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("RegimentsLimit"),
            Images.regimentsLimit,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).iRegimentsLimit),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iRegimentsLimit > Game.getCiv(civRight).iRegimentsLimit
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("RegimentsLimit"),
            Images.regimentsLimit,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).iRegimentsLimit),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).iRegimentsLimit < Game.getCiv(civRight).iRegimentsLimit
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("Discipline"), Images.discipline, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("Discipline"),
            Images.discipline,
            "" + (int)(Game.getCiv(civLeft).civBonuses.Discipline * 100.0F) + "%",
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).civBonuses.Discipline > Game.getCiv(civRight).civBonuses.Discipline
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("Discipline"),
            Images.discipline,
            "" + (int)(Game.getCiv(civRight).civBonuses.Discipline * 100.0F) + "%",
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).civBonuses.Discipline < Game.getCiv(civRight).civBonuses.Discipline
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("BattleWidth"), Images.battleWidth, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("BattleWidth"),
            Images.battleWidth,
            "" + CFG.getNumberWithSpaces("" + BattleManager.getBattleWidth(civLeft)),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            BattleManager.getBattleWidth(civLeft) > BattleManager.getBattleWidth(civRight)
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("BattleWidth"),
            Images.battleWidth,
            "" + CFG.getNumberWithSpaces("" + BattleManager.getBattleWidth(civRight)),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            BattleManager.getBattleWidth(civLeft) < BattleManager.getBattleWidth(civRight)
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG(Game.lang.get("AggressiveExpansion"), Images.aggressiveExpansion, paddingLeft, buttonY, buttonW, buttonH, maxIconW)
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("AggressiveExpansion"),
            Images.aggressiveExpansion,
            "" + (int)Game.getCiv(civLeft).getAggressiveExpansion(),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getAggressiveExpansion() < Game.getCiv(civRight).getAggressiveExpansion()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("AggressiveExpansion"),
            Images.aggressiveExpansion,
            "" + (int)Game.getCiv(civRight).getAggressiveExpansion(),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getAggressiveExpansion() > Game.getCiv(civRight).getAggressiveExpansion()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("WarWeariness"), Images.weariness, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("WarWeariness"),
            Images.weariness,
            "" + (int)Game.getCiv(civLeft).getWarWeariness(),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getWarWeariness() < Game.getCiv(civRight).getWarWeariness()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("WarWeariness"),
            Images.weariness,
            "" + (int)Game.getCiv(civRight).getWarWeariness(),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getWarWeariness() > Game.getCiv(civRight).getWarWeariness()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Capital"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new TextIcon2_Horizontal(
            Game.getCiv(civLeft).getCapitalProvinceID() >= 0 ? Game.getProvince(Game.getCiv(civLeft).getCapitalProvinceID()).getProvinceName() : "",
            Images.capital,
            paddingLeft,
            buttonY,
            buttonW,
            buttonH,
            maxIconW
         ) {
            @Override
            public void actionElement() {
               if (Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID() >= 0) {
                  if (Game.iActiveProvince == Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()) {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.setVisibleInGame_Civ(false);
                     Game.setActiveProvinceID(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID());
                     Game.menuManager.rebuildInGame_ProvinceInfo(true);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  } else {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID());
                     Game.setActiveProvinceID(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID());
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  }
               }
            }

            @Override
            public void buildElementHover() {
               if (Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID() >= 0) {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        InGame_Civ_Compare.civLeft,
                        Game.lang.get("Capital") + ": " + Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()).getProvinceName()
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Population") + ": ",
                        CFG.getNumberWithSpaces("" + Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()).getPopulationTotal()),
                        Images.population,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.COLOR_POPULATION
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Economy") + ": ",
                        CFG.getPrecision2(Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()).getEconomyWithBonuses(), 10),
                        Game_Calendar.IMG_ECONOMY,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Income") + ": ",
                        CFG.getPrecision2(
                           Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()).getProvinceIncome()
                              - Game.getProvince(Game.getCiv(InGame_Civ_Compare.civLeft).getCapitalProvinceID()).fProvinceMaintenance,
                           100
                        ),
                        Images.gold,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               } else {
                  this.menuElementHover = null;
               }
            }
         }
      );
      menuElements.add(
         new TextIcon2_Horizontal(
            Game.getCiv(civRight).getCapitalProvinceID() >= 0 ? Game.getProvince(Game.getCiv(civRight).getCapitalProvinceID()).getProvinceName() : "",
            Images.capital,
            paddingLeft + buttonW + CFG.PADDING,
            buttonY,
            buttonW,
            buttonH,
            maxIconW
         ) {
            @Override
            public void actionElement() {
               if (Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID() >= 0) {
                  if (Game.iActiveProvince == Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()) {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.setVisibleInGame_Civ(false);
                     Game.setActiveProvinceID(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID());
                     Game.menuManager.rebuildInGame_ProvinceInfo(true);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  } else {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID());
                     Game.setActiveProvinceID(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID());
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  }
               }
            }

            @Override
            public void buildElementHover() {
               if (Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID() >= 0) {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        InGame_Civ_Compare.civRight,
                        Game.lang.get("Capital") + ": " + Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()).getProvinceName()
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Population") + ": ",
                        CFG.getNumberWithSpaces("" + Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()).getPopulationTotal()),
                        Images.population,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.COLOR_POPULATION
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Economy") + ": ",
                        CFG.getPrecision2(Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()).getEconomyWithBonuses(), 10),
                        Game_Calendar.IMG_ECONOMY,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Income") + ": ",
                        CFG.getPrecision2(
                           Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()).getProvinceIncome()
                              - Game.getProvince(Game.getCiv(InGame_Civ_Compare.civRight).getCapitalProvinceID()).fProvinceMaintenance,
                           100
                        ),
                        Images.gold,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               } else {
                  this.menuElementHover = null;
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("CapitalCity"), Images.capital, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("CapitalCity"),
            Images.capital,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getCapitalLevel()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getCapitalLevel() > Game.getCiv(civRight).getCapitalLevel()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("CapitalCity"),
            Images.capital,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getCapitalLevel()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getCapitalLevel() < Game.getCiv(civRight).getCapitalLevel()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("MilitaryAcademy"), Game_Calendar.IMG_MANPOWER, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("MilitaryAcademy"),
            Game_Calendar.IMG_MANPOWER,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getMilitaryAcademyLevel()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getMilitaryAcademyLevel() > Game.getCiv(civRight).getMilitaryAcademyLevel()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("MilitaryAcademy"),
            Game_Calendar.IMG_MANPOWER,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getMilitaryAcademyLevel()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getMilitaryAcademyLevel() < Game.getCiv(civRight).getMilitaryAcademyLevel()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("MilitaryAcademyForGenerals"), Images.general, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("MilitaryAcademyForGenerals"),
            Images.general,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getMilitaryAcademyForGeneralsLevel()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getMilitaryAcademyForGeneralsLevel() > Game.getCiv(civRight).getMilitaryAcademyForGeneralsLevel()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("MilitaryAcademyForGenerals"),
            Images.general,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getMilitaryAcademyForGeneralsLevel()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getMilitaryAcademyForGeneralsLevel() < Game.getCiv(civRight).getMilitaryAcademyForGeneralsLevel()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRectIMG(Game.lang.get("SupremeCourt"), Images.stability, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
      menuElements.add(
         new Text_StaticBG_Compare(
            true,
            Game.lang.get("SupremeCourt"),
            Images.stability,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getSupremeCourtLevel()),
            rightPosX,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getSupremeCourtLevel() > Game.getCiv(civRight).getSupremeCourtLevel()
         )
      );
      menuElements.add(
         new Text_StaticBG_Compare(
            false,
            Game.lang.get("SupremeCourt"),
            Images.stability,
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getSupremeCourtLevel()),
            rightPosX2,
            buttonY,
            rightW,
            buttonH,
            Game.getCiv(civLeft).getSupremeCourtLevel() < Game.getCiv(civRight).getSupremeCourtLevel()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(civLeft).canBuildNuke || Game.getCiv(civRight).canBuildNuke) {
         menuElements.add(new ButtonStatsRectIMG(Game.lang.get("NuclearReactor"), Images.nuke, paddingLeft, buttonY, buttonW, buttonH, maxIconW));
         menuElements.add(
            new Text_StaticBG_Compare(
               true,
               Game.lang.get("NuclearReactor"),
               Images.nuke,
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(civLeft).getNuclearReactorLevel()),
               rightPosX,
               buttonY,
               rightW,
               buttonH,
               Game.getCiv(civLeft).getNuclearReactorLevel() > Game.getCiv(civRight).getNuclearReactorLevel()
            )
         );
         menuElements.add(
            new Text_StaticBG_Compare(
               false,
               Game.lang.get("NuclearReactor"),
               Images.nuke,
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(civRight).getNuclearReactorLevel()),
               rightPosX2,
               buttonY,
               rightW,
               buttonH,
               Game.getCiv(civLeft).getNuclearReactorLevel() < Game.getCiv(civRight).getNuclearReactorLevel()
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(
            Game.lang.get("CompareCivilizations"),
            Game.getCiv(civLeft).getCivName() + " - " + Game.getCiv(civRight).getCivName(),
            false,
            false,
            Images.title500
         ) {
            @Override
            public long getTime() {
               return InGame_Civ_Compare.lTime2;
            }
         },
         menuX,
         menuY,
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      InGame_Civ.actionOnClose();
   }
}
