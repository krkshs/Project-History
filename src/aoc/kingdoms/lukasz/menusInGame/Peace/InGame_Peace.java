package aoc.kingdoms.lukasz.menusInGame.Peace;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.AI_PeaceTreaty;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.PeaceTreaty;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonDiplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonDiplomacy_Cost;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIcon;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Value;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_Active;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStats_Score;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusIdeology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusReligion;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Peace extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public int iCivID = 1;
   public int activeMapModeID = 0;
   public int iNumOfProvinces_Prov = 0;
   public int iNumOfProvinces_Pop = 0;
   public float iAggressiveExpansion = 0.0F;
   public float iAggressiveExpansion2 = 0.0F;
   public static boolean brushModeDemand = false;
   public boolean enforceDemandsConfirm = false;

   public InGame_Peace() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT2 : CFG.BUTTON_HEIGHT;
      InGame_Civ.updateMaxIconH();
      this.activeMapModeID = 0;
      brushModeDemand = false;
      int maxWidth = ImageManager.getImage(Images.warPeaceBig).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      int buttonY;
      if (Game.player.peaceTreaty != null && WarManager.lWars.containsKey(Game.player.peaceTreaty.warKey)) {
         if (WarManager.lWars.get(Game.player.peaceTreaty.warKey).isAggressor(Game.player.iCivID)) {
            this.iCivID = WarManager.lWars.get(Game.player.peaceTreaty.warKey).lDefenders.get(0).iCivID;
         } else {
            this.iCivID = WarManager.lWars.get(Game.player.peaceTreaty.warKey).lAggressors.get(0).iCivID;
         }

         menuElements.add(
            new ButtonFlag_Diplomacy(
               Game.player.iCivID,
               menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonYPadding + tempTitlePaddingY,
               true
            ) {
               @Override
               public void actionElement() {
               }
            }
         );
         menuElements.add(new ButtonFlag_Diplomacy(this.iCivID, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonYPadding + tempTitlePaddingY, true) {
            @Override
            public void actionElement() {
            }
         });
         menuElements.add(
            new Text_Static_ID(
               this.iCivID,
               Game.getCiv(this.iCivID).getCivName(),
               CFG.FONT_REGULAR,
               -1,
               menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonYPadding,
               tempTextW,
               tempTitleH
            )
         );
         menuElements.add(
            new Text_Static_ID(
               Game.player.iCivID, Game.getCiv(Game.player.iCivID).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonYPadding, tempTextW, tempTitleH
            )
         );
         menuElements.add(new TextIcon_Diplomacy(Images.warPeaceBig, paddingLeft, buttonYPadding, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
            @Override
            public Color getColorBar() {
               return Game.getCiv(InGame_Peace.this.iCivID).getColor(1.0F);
            }
         });
         buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int statH = CFG.BUTTON_HEIGHT3;
         int buttonW2 = (menuWidth - paddingLeft * 2 - CFG.PADDING * 3) / 4;
         int buttonH2 = (int)(buttonW2 * 1.1F);
         int buttonX = paddingLeft;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Provinces") + ": ",
               "" + Game.player.peaceTreaty.lProvinces.size(),
               Images.provinces,
               buttonX,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               statH,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_GOLD;
               }

               @Override
               public String getTextToDraw() {
                  if (Game.player.peaceTreaty.lProvinces.size() != InGame_Peace.this.iNumOfProvinces_Prov) {
                     this.sText2 = "" + Game.player.peaceTreaty.lProvinces.size();
                     Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID2), this.sText2);
                     this.iTextBonusW = (int)Renderer.glyphLayout.width;
                     InGame_Peace.this.iNumOfProvinces_Prov = Game.player.peaceTreaty.lProvinces.size();
                  }

                  return super.getTextToDraw();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Annexation"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        this.getText(), this.sText2, this.imageID, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("NonCoreProvince"), CFG.FONT_BOLD, Colors.HOVER_NEGATIVE));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Economy") + ": ",
                        "" + CFG.getPrecision2(Math.ceil((1.0F - GameValues.peace.PEACE_ANNEX_PROVINCE_ECONOMY_CHANGE) * 100.0F) * -1.0, 1) + "%",
                        Game_Calendar.IMG_ECONOMY,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.peace.PEACE_ANNEX_PROVINCE_ECONOMY_CHANGE < 1.0F ? Colors.HOVER_NEGATIVE : Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("TaxEfficiency") + ": ",
                        "" + CFG.getPrecision2(Math.ceil((1.0F - GameValues.peace.PEACE_ANNEX_PROVINCE_TAX_CHANGE) * 100.0F) * -1.0, 1) + "%",
                        Images.tax,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.peace.PEACE_ANNEX_PROVINCE_TAX_CHANGE < 1.0F ? Colors.HOVER_NEGATIVE : Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("LocalManpower") + ": ",
                        "" + CFG.getPrecision2((float)((int)((1.0F - GameValues.peace.PEACE_ANNEX_PROVINCE_MANPOWER_CHANGE) * 100.0F) * -1), 1) + "%",
                        Game_Calendar.IMG_MANPOWER,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.peace.PEACE_ANNEX_PROVINCE_MANPOWER_CHANGE < 1.0F ? Colors.HOVER_NEGATIVE : Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("GrowthRate") + ": ",
                        "" + CFG.getPrecision2((float)((int)((1.0F - GameValues.peace.PEACE_ANNEX_PROVINCE_GROWTH_RATE_CHANGE) * 100.0F) * -1), 1) + "%",
                        Images.populationGrowth,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.peace.PEACE_ANNEX_PROVINCE_GROWTH_RATE_CHANGE < 1.0F ? Colors.HOVER_NEGATIVE : Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Population") + ": ",
               "" + CFG.getShortNumber(Game.player.peaceTreaty.getPopulation()),
               Images.population,
               buttonX + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               statH,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               @Override
               public String getTextToDraw() {
                  if (Game.player.peaceTreaty.lProvinces.size() != InGame_Peace.this.iNumOfProvinces_Pop) {
                     this.sText2 = "" + CFG.getShortNumber(Game.player.peaceTreaty.getPopulation());
                     Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID2), this.sText2);
                     this.iTextBonusW = (int)Renderer.glyphLayout.width;
                     InGame_Peace.this.iNumOfProvinces_Pop = Game.player.peaceTreaty.lProvinces.size();
                  }

                  return super.getTextToDraw();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int aeWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 4;
         int aeWidth2 = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 1 / 4;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("AggressiveExpansion") + ": ",
               "" + CFG.getPrecision2(Game.player.peaceTreaty.fAggressiveExpansion, 100),
               Images.aggressiveExpansion,
               buttonX,
               buttonY,
               aeWidth,
               statH,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               @Override
               public String getTextToDraw() {
                  if (Game.player.peaceTreaty.fAggressiveExpansion != InGame_Peace.this.iAggressiveExpansion) {
                     this.sText2 = (Game.player.peaceTreaty.fAggressiveExpansion > 0.0F ? "" : "")
                        + CFG.getPrecision2(Game.player.peaceTreaty.fAggressiveExpansion, 100);
                     Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID2), this.sText2);
                     this.iTextBonusW = (int)Renderer.glyphLayout.width;
                     InGame_Peace.this.iAggressiveExpansion = Game.player.peaceTreaty.fAggressiveExpansion;
                  }

                  return super.getTextToDraw();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("PeaceTreaty"), CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.peace, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        this.getText(),
                        "min("
                           + CFG.getPrecision2(Game.player.peaceTreaty.fAggressiveExpansion, 100)
                           + ", "
                           + CFG.getPrecision2(GameValues.aggressiveExpansion.PEACE_TREATY_AE_FROM_WAR_LIMIT, 100)
                           + ")",
                        this.imageID,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("AggressiveExpansion") + " > " + CFG.getPrecision2(GameValues.aggressiveExpansion.START_COALITION_IF_AE_OVER, 10) + " = ",
                        Game.lang.get("Coalition"),
                        Images.alliance,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_NEGATIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               "",
               "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getAggressiveExpansion() + Game.player.peaceTreaty.fAggressiveExpansion, 10),
               Images.aggressiveExpansion,
               buttonX + CFG.PADDING + aeWidth,
               buttonY,
               aeWidth2,
               statH,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               @Override
               public String getTextToDraw() {
                  if (Game.player.peaceTreaty.fAggressiveExpansion != InGame_Peace.this.iAggressiveExpansion2) {
                     this.sText2 = CFG.getPrecision2(
                        Game.getCiv(Game.player.iCivID).getAggressiveExpansion() + Game.player.peaceTreaty.fAggressiveExpansion, 10
                     );
                     Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID2), this.sText2);
                     this.iTextBonusW = (int)Renderer.glyphLayout.width;
                     InGame_Peace.this.iAggressiveExpansion2 = Game.player.peaceTreaty.fAggressiveExpansion;
                  }

                  return super.getTextToDraw();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(Game.player.iCivID, Game.lang.get("AggressiveExpansion")));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("AggressiveExpansion") + ": ",
                        this.sText2,
                        this.imageID,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("AggressiveExpansion") + " > " + CFG.getPrecision2(GameValues.aggressiveExpansion.START_COALITION_IF_AE_OVER, 10) + " = ",
                        Game.lang.get("Coalition"),
                        Images.alliance,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_NEGATIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonGame_ImageSparks(
               Game.lang.get("EnforceDemands"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT2,
               true,
               Images.warDemands
            ) {
               @Override
               public void actionElement() {
                  if (!InGame_Peace.this.enforceDemandsConfirm) {
                     InGame_Peace.this.enforceDemandsConfirm = true;
                     this.setText(Game.lang.get("EnforceDemands") + ": " + Game.lang.get("Confirm"));
                  } else {
                     int tCivID = Game.player.peaceTreaty.iCivID;
                     int tCivID2 = Game.player.peaceTreaty.iCivID2;
                     if (Game.player.peaceTreaty.enforceDemands(true)) {
                        Game.menuManager.setVisibleInGame_Peace(false);
                        Game.menuManager.rebuildInGame_Wars();
                        InGame_Info.iCivID = tCivID;
                        InGame_Info.iCivID2 = tCivID2;
                        Game.menuManager.rebuildInGame_Info(Game.lang.get("PeaceTreaty"), Game_Calendar.getCurrentDate());
                        InGame_Info.imgID = Images.infoDiplomacy;
                     }
                  }
               }

               @Override
               protected Color getColor(boolean isActive) {
                  return InGame_Peace.this.enforceDemandsConfirm ? Colors.HOVER_GOLD : super.getColor(isActive);
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || InGame_Peace.this.enforceDemandsConfirm;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  if (!InGame_Peace.this.enforceDemandsConfirm) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SignPeaceTreaty"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.warPeace, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  } else {
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG(
                           Game.lang.get("SignPeaceTreaty") + ": " + Game.lang.get("Confirm"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.warPeace, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("MapMode"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int statsH = ImageManager.getImage(Images.provinces).getHeight() + CFG.PADDING * 4;
         int typeW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 5) / 6;
         int typeX = paddingLeft;
         menuElements.add(new ButtonIcon("", Images.mapModesCivs, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               Renderer.drawArmyInProvince = true;
               InGame_Peace.this.activeMapModeID = 0;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("None"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.mapModesCivs, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 0;
            }
         });
         typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonIcon("", Images.victoryPoints, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               if (InGame_Peace.this.activeMapModeID == 1) {
                  InGame_Peace.this.activeMapModeID = 0;
                  Renderer.drawArmyInProvince = true;
               } else {
                  InGame_Peace.this.activeMapModeID = 1;
                  Renderer.drawArmyInProvince = false;
                  ProvinceDrawDetails.updateDrawProvinceDetails_PeaceVictoryPoints();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("VictoryPoints"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.victoryPoints, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 1;
            }
         });
         typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonIcon("", Images.population, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               if (InGame_Peace.this.activeMapModeID == 2) {
                  InGame_Peace.this.activeMapModeID = 0;
                  Renderer.drawArmyInProvince = true;
               } else {
                  InGame_Peace.this.activeMapModeID = 2;
                  Renderer.drawArmyInProvince = false;
                  ProvinceDrawDetails.updateDrawProvinceDetails_PeacePopulation();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Population"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.population, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 2;
            }
         });
         typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonIcon("", Game_Calendar.IMG_ECONOMY, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               if (InGame_Peace.this.activeMapModeID == 3) {
                  InGame_Peace.this.activeMapModeID = 0;
                  Renderer.drawArmyInProvince = true;
               } else {
                  InGame_Peace.this.activeMapModeID = 3;
                  Renderer.drawArmyInProvince = false;
                  ProvinceDrawDetails.updateDrawProvinceDetails_PeaceEconomy();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Economy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 3;
            }
         });
         typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonIcon("", Images.goods, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               if (InGame_Peace.this.activeMapModeID == 4) {
                  InGame_Peace.this.activeMapModeID = 0;
                  Renderer.drawArmyInProvince = true;
               } else {
                  InGame_Peace.this.activeMapModeID = 4;
                  Renderer.drawArmyInProvince = false;
                  ProvinceDrawDetails.updateDrawProvinceDetails_PeaceGoods();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Resources"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 4;
            }
         });
         typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonIcon("", Images.religion, typeX, buttonY, typeW, statsH) {
            @Override
            public void actionElement() {
               if (InGame_Peace.this.activeMapModeID == 5) {
                  InGame_Peace.this.activeMapModeID = 0;
                  Renderer.drawArmyInProvince = true;
               } else {
                  InGame_Peace.this.activeMapModeID = 5;
                  Renderer.drawArmyInProvince = false;
                  ProvinceDrawDetails.updateDrawProvinceDetails_PeaceReligion();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public boolean isActive() {
               return InGame_Peace.this.activeMapModeID == 5;
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("Demands"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStats_Score(Game.lang.get("Score") + ": ", paddingLeft, buttonY, menuWidth - paddingLeft * 2, statH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Score") + ": ", this.sText2, Images.victoryPoints, CFG.FONT_BOLD, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        Game.lang.get("PointsUsedToClaimDemandsInAPeaceTreaty"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int c0W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.6F);
         int c1W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.2F);
         int cH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Gold") + ": ",
               (Game.player.peaceTreaty.demandGold > 0 ? "+" : "") + CFG.getPrecision2(Game.player.peaceTreaty.getGold_PerDemand(), 100),
               Images.gold,
               buttonX,
               buttonY,
               c0W,
               cH,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               public int lastValue = 0;

               @Override
               public String getTextToDraw() {
                  if (this.lastValue != Game.player.peaceTreaty.demandGold) {
                     this.setText2((Game.player.peaceTreaty.demandGold > 0 ? "+" : "") + CFG.getPrecision2(Game.player.peaceTreaty.getGold_PerDemand(), 100));
                     this.lastValue = Game.player.peaceTreaty.demandGold;
                  }

                  return this.sText;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonStatsRect_Active("-", buttonX, buttonY, c1W, cH, CFG.FONT_BOLD) {
            @Override
            public void actionElement() {
               Game.player.peaceTreaty.demandGold_Cancel();
            }
         });
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRect_Active("+", buttonX, buttonY, c1W, cH, CFG.FONT_BOLD) {
               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandGold();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Gold"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandGold(), 100),
                        Images.victoryPoints,
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
                        Game.lang.get("Gold") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getGold_PerDemand_One(), 100),
                        Images.gold,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonX = paddingLeft;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<MenuElement> tempElements = new ArrayList<>();
         tempElements.add(
            new ButtonDiplomacy(Game.lang.get("Brush"), Images.brush, paddingLeft, buttonY, buttonW2, buttonH2) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  if (!CFG.brushTool) {
                     CFG.brushTool = true;
                     InGame_Peace.brushModeDemand = true;
                     this.setText(Game.lang.get("Brush") + " " + Game.lang.get("DemandProvinces"));
                  } else if (InGame_Peace.brushModeDemand) {
                     InGame_Peace.brushModeDemand = false;
                     this.setText(Game.lang.get("Brush") + " " + Game.lang.get("Cancel"));
                  } else {
                     CFG.brushTool = false;
                     this.setText(Game.lang.get("Brush"));
                  }
               }

               @Override
               public Color getColorHover1() {
                  if (CFG.brushTool) {
                     return InGame_Peace.brushModeDemand
                        ? new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F)
                        : new Color(Colors.COLOR_TEXT_MODIFIER_NEGATIVE.r, Colors.COLOR_TEXT_MODIFIER_NEGATIVE.g, Colors.COLOR_TEXT_MODIFIER_NEGATIVE.b, 0.15F);
                  } else {
                     return new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.15F);
                  }
               }

               @Override
               public Color getColorHover2() {
                  if (CFG.brushTool) {
                     return InGame_Peace.brushModeDemand
                        ? new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F)
                        : new Color(Colors.COLOR_TEXT_MODIFIER_NEGATIVE.r, Colors.COLOR_TEXT_MODIFIER_NEGATIVE.g, Colors.COLOR_TEXT_MODIFIER_NEGATIVE.b, 0.5F);
                  } else {
                     return new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.5F);
                  }
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || CFG.brushTool;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Brush"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.brush, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         float value = Game.player.peaceTreaty.getScore_DemandWarReparations();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get("WarReparations"),
               Images.gold,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandWarReparations();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandWarReparations;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F);
               }

               @Override
               public Color getColorHover2() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("WarReparations"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandWarReparations(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("MonthlyIncome") + ": ",
                        CFG.getPrecision2(PeaceTreaty.getWarReparationsPerMonth(Game.player.peaceTreaty.iCivID2), 100),
                        Images.gold,
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
                        Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_REPARATIONS_TURNS),
                        "",
                        Images.time,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         value = Game.player.peaceTreaty.getScore_DemandVassalization();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get(Game_Ages.getDemandVassalization()),
               Images.vassal,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandVassalization();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandVassalization;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F);
               }

               @Override
               public Color getColorHover2() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(Game_Ages.getDemandVassalization()), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.vassal, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandVassalization(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         value = Game.player.peaceTreaty.getScore_DemandGovernmentChange();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get("GovernmentChange"),
               Images.government,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandGovernmentChange();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandGovernmentChange;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F);
               }

               @Override
               public Color getColorHover2() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("GovernmentChange"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.government, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandGovernmentChange(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusIdeology(
                        Game.ideologiesManager.getIdeology(Game.getCiv(Game.player.peaceTreaty.iCivID2).getIdeologyID()).Name,
                        "",
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getIdeologyID(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         value = Game.player.peaceTreaty.getScore_DemandReligionConversion();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get("DemandReligionConversion"),
               Images.religion,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandReligionConversion();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandReligionConversion;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F);
               }

               @Override
               public Color getColorHover2() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("DemandReligionConversion"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandReligionConversion(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusReligion(
                        Game.religionManager.getReligion(Game.getCiv(Game.player.peaceTreaty.iCivID2).getReligionID()).Name,
                        "",
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getReligionID(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         value = Game.player.peaceTreaty.getScore_DemandHumiliate();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get("Humiliate"),
               Images.insult,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandHumiliate();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandHumiliate;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.r,
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.g,
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.b,
                     0.15F
                  );
               }

               @Override
               public Color getColorHover2() {
                  return new Color(
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.r,
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.g,
                     Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE.b,
                     0.5F
                  );
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Humiliate"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.insult, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandHumiliate(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Legacy") + ": ",
                        (GameValues.peace.PEACE_HUMILIATE_LEGACY_GAIN > 0.0F ? "+" : "") + CFG.getPrecision2(GameValues.peace.PEACE_HUMILIATE_LEGACY_GAIN, 100),
                        Images.legacy,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (GameValues.peace.PEACE_HUMILIATE_AGGRESSIVE_EXPANSION_GAIN != 0.0F) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("AggressiveExpansion") + ": ",
                           (GameValues.peace.PEACE_HUMILIATE_AGGRESSIVE_EXPANSION_GAIN > 0.0F ? "+" : "")
                              + CFG.getPrecision2(GameValues.peace.PEACE_HUMILIATE_AGGRESSIVE_EXPANSION_GAIN, 100),
                           Images.aggressiveExpansion,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_NEGATIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         value = Game.player.peaceTreaty.getScore_DemandMilitaryAccess();
         tempElements.add(
            new ButtonDiplomacy_Cost(
               Game.lang.get("DemandMilitaryAccess"),
               Images.militaryAccess,
               paddingLeft,
               buttonY,
               buttonW2,
               buttonH2,
               CFG.getPrecision2(value, value < 10.0F ? 100 : (value < 100.0F ? 10 : 1))
            ) {
               @Override
               protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }

               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.demandMilitaryAccess();
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || Game.player.peaceTreaty.demandMilitaryAccess;
               }

               @Override
               public Color getColorHover1() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.15F);
               }

               @Override
               public Color getColorHover2() {
                  return new Color(Colors.COLOR_TEXT_MODIFIER_POSITIVE.r, Colors.COLOR_TEXT_MODIFIER_POSITIVE.g, Colors.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.5F);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("DemandMilitaryAccess"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.militaryAccess, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Cost") + ": ",
                        CFG.getPrecision2(Game.player.peaceTreaty.getScore_DemandMilitaryAccess(), 100),
                        Images.victoryPoints,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(Game.player.peaceTreaty.iCivID2).getCivName(),
                        "",
                        Game.player.peaceTreaty.iCivID2,
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_LEFT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         int i = 0;

         for (int iSize = tempElements.size(); i < iSize; i++) {
            tempElements.get(i).setPosX(buttonX);
            tempElements.get(i).setPosY(buttonY);
            buttonX += tempElements.get(i).getWidth() + CFG.PADDING;
            menuElements.add(tempElements.get(i));
            if ((i + 1) % 4 == 0 || i == iSize - 1) {
               buttonY += tempElements.get(i).getHeight() + CFG.PADDING;
               buttonX = paddingLeft;
            }
         }

         tempElements.clear();
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("DemandProvinces"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.get("Suggest"),
               Images.provinces,
               paddingLeft,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public void actionElement() {
                  List<Integer> provinceToTake = new ArrayList<>();
                  List<Integer> aiPeaceCivID = new ArrayList<>();
                  List<Integer> aiPeaceScore = new ArrayList<>();

                  try {
                     for (int i = 0; i < Game.getProvincesSize(); i++) {
                        aiPeaceCivID.add(Game.getProvince(i).getCivID());
                        aiPeaceScore.add(0);
                     }

                     for (int i = 0; i < Game.getCiv(InGame_Peace.this.iCivID).getNumOfProvinces(); i++) {
                        provinceToTake.add(Game.getCiv(InGame_Peace.this.iCivID).getProvinceID(i));
                        aiPeaceScore.set(
                           Game.getCiv(InGame_Peace.this.iCivID).getProvinceID(i),
                           AI_PeaceTreaty.buildProvinceScore_Player(aiPeaceCivID, Game.player.iCivID, Game.getCiv(InGame_Peace.this.iCivID).getProvinceID(i))
                        );
                     }

                     while (Game.player.peaceTreaty.fScore > 0.0F && provinceToTake.size() > 0) {
                        int bestID = 0;

                        for (int i = provinceToTake.size() - 1; i >= 0; i--) {
                           if (aiPeaceScore.get(provinceToTake.get(bestID)) < aiPeaceScore.get(provinceToTake.get(i))) {
                              bestID = i;
                           }
                        }

                        if (!Game.player.peaceTreaty.addProvince(provinceToTake.get(bestID))) {
                           break;
                        }

                        aiPeaceCivID.set(provinceToTake.get(bestID), Game.player.iCivID);
                        Game.getProvince(provinceToTake.get(bestID)).peaceTreatyIsTaken = true;

                        for (int j = 0; j < Game.getProvince(provinceToTake.get(bestID)).getNeighboringProvincesSize(); j++) {
                           aiPeaceScore.set(
                              Game.getProvince(provinceToTake.get(bestID)).getNeighboringProvinces(j),
                              AI_PeaceTreaty.buildProvinceScore_Player(
                                 aiPeaceCivID, Game.player.iCivID, Game.getProvince(provinceToTake.get(bestID)).getNeighboringProvinces(j)
                              )
                           );
                        }

                        provinceToTake.remove(bestID);
                     }

                     Game.player.peaceTreaty.buildAggressiveExpansion();
                  } catch (Exception var6) {
                     CFG.exceptionStack(var6);
                  }

                  aiPeaceCivID.clear();
                  aiPeaceScore.clear();
                  provinceToTake.clear();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Suggest") + ": " + Game.lang.get("Provinces"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.get("Reset"),
               Images.provinces,
               paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public void actionElement() {
                  Game.player.peaceTreaty.resetProvinces();
                  Game.menuManager.addToastGold(Game.lang.get("Done"), Images.provinces);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Reset") + ": " + Game.lang.get("Provinces"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         i = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.75F);
         int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
         int r0W2 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING) * 0.75F);
         int r1W2 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING) * 0.25F);
         int buttonHProvinces = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("DemandSubjectTransfer"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<Integer> tCivs = new ArrayList<>();

         for (int ix = 0; ix < Game.getCiv(this.iCivID).diplomacy.iVassalsSize; ix++) {
            if (Game.getCiv(Game.getCiv(this.iCivID).diplomacy.lVassals.get(ix).c).getNumOfProvinces() > 0) {
               tCivs.add(Game.getCiv(this.iCivID).diplomacy.lVassals.get(ix).c);
            }
         }

         if (tCivs.size() <= 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            while (tCivs.size() > 0) {
               int toAddID = 0;
               int o = 1;

               for (int oSize = tCivs.size(); o < oSize; o++) {
                  if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(toAddID)).getCivName(), Game.getCiv(tCivs.get(o)).getCivName())) {
                     toAddID = o;
                  }
               }

               menuElements.add(
                  new Text_StaticBG_ID_Flag(
                     Game.getCiv(tCivs.get(toAddID)).getCivName(),
                     CFG.FONT_REGULAR_SMALL,
                     CFG.PADDING * 2,
                     buttonX,
                     buttonY,
                     r0W2,
                     buttonHProvinces,
                     tCivs.get(toAddID),
                     false
                  ) {
                     @Override
                     public void actionElement() {
                        Game.player.peaceTreaty.demandSubjectTransfer(this.getCurrent());
                     }

                     @Override
                     public void actionElementPPM() {
                        if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                           Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                        }
                     }

                     @Override
                     public int getFlagID() {
                        return Game.player.peaceTreaty.lSubjectTransfer.contains(this.getCurrent()) ? Game.player.iCivID : this.getCurrent();
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(this.getCurrent()).getCivName(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.vassal, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Provinces") + ": ",
                              CFG.getNumberWithSpaces("" + Game.getCiv(this.getCurrent()).getNumOfProvinces()),
                              Images.provinces,
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
                              Game.lang.get("Population") + ": ",
                              CFG.getNumberWithSpaces("" + Game.getCiv(this.getCurrent()).getPopulationTotal()),
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
                              CFG.getPrecision2(Game.getCiv(this.getCurrent()).getEconomyTotal(), 10),
                              Game_Calendar.IMG_ECONOMY,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Cost") + ": ",
                              CFG.getPrecision2(Game.player.peaceTreaty.getScore_SubjectTransfer(this.getCurrent()), 10),
                              Images.victoryPoints,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG_Active_Value(
                     "" + CFG.getPrecision2(Game.player.peaceTreaty.getScore_SubjectTransfer(tCivs.get(toAddID)), 10),
                     Images.victoryPoints,
                     buttonX,
                     buttonY,
                     r1W,
                     buttonHProvinces,
                     ImageManager.getImage(Images.gold).getWidth(),
                     tCivs.get(toAddID)
                  ) {
                     @Override
                     public void actionElement() {
                     }
                  }
               );
               buttonX = paddingLeft;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tCivs.remove(toAddID);
            }
         }

         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("LiberateCivilization"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tCivs = PeaceTreaty.getCivsPossibleToLiberate(this.iCivID);
         if (tCivs.size() <= 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            while (tCivs.size() > 0) {
               int toAddID = 0;
               int o = 1;

               for (int oSizex = tCivs.size(); o < oSizex; o++) {
                  if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(toAddID)).getCivName(), Game.getCiv(tCivs.get(o)).getCivName())) {
                     toAddID = o;
                  }
               }

               menuElements.add(
                  new Text_StaticBG_ID_Flag(
                     Game.getCiv(tCivs.get(toAddID)).getCivName(),
                     CFG.FONT_REGULAR_SMALL,
                     CFG.PADDING * 2,
                     buttonX,
                     buttonY,
                     r0W2,
                     buttonHProvinces,
                     tCivs.get(toAddID),
                     false
                  ) {
                     @Override
                     public void actionElement() {
                        Game.player.peaceTreaty.demandLiberateCiv(InGame_Peace.this.iCivID, this.getCurrent());
                     }

                     @Override
                     public void actionElementPPM() {
                        if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                           Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                        }
                     }

                     @Override
                     public int getFlagID() {
                        return Game.player.peaceTreaty.lLiberateCiv.contains(this.getCurrent()) ? Game.player.iCivID : this.getCurrent();
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(this.getCurrent()).getCivName(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Provinces") + ": ",
                              CFG.getNumberWithSpaces("" + PeaceTreaty.getLiberateCivProvinces(InGame_Peace.this.iCivID, this.getCurrent())),
                              Images.provinces,
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
                              Game.lang.get("Population") + ": ",
                              CFG.getNumberWithSpaces("" + PeaceTreaty.getLiberateCivPopulation(InGame_Peace.this.iCivID, this.getCurrent())),
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
                              CFG.getPrecision2((float)PeaceTreaty.getLiberateCivEconomy(InGame_Peace.this.iCivID, this.getCurrent()), 10),
                              Game_Calendar.IMG_ECONOMY,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Legacy") + ": ",
                              (GameValues.peace.PEACE_LIBERATE_CIV_LEGACY > 0.0F ? "+" : "")
                                 + CFG.getPrecision2(GameValues.peace.PEACE_LIBERATE_CIV_LEGACY, 100),
                              Images.legacy,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              GameValues.peace.PEACE_LIBERATE_CIV_LEGACY >= 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Cost") + ": ",
                              CFG.getPrecision2(Game.player.peaceTreaty.getScore_LiberateCiv(InGame_Peace.this.iCivID, this.getCurrent()), 10),
                              Images.victoryPoints,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG_Active_Value(
                     "" + CFG.getPrecision2(Game.player.peaceTreaty.getScore_LiberateCiv(this.iCivID, tCivs.get(toAddID)), 10),
                     Images.victoryPoints,
                     buttonX,
                     buttonY,
                     r1W,
                     buttonHProvinces,
                     ImageManager.getImage(Images.gold).getWidth(),
                     tCivs.get(toAddID)
                  ) {
                     @Override
                     public void actionElement() {
                     }
                  }
               );
               buttonX = paddingLeft;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tCivs.remove(toAddID);
            }
         }

         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("DemandProvinces"),
               CFG.FONT_REGULAR_SMALL,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<Integer> tProvinces = new ArrayList<>();

         for (int ixx = 0; ixx < Game.getCiv(this.iCivID).getNumOfProvinces(); ixx++) {
            tProvinces.add(Game.getCiv(this.iCivID).getProvinceID(ixx));
         }

         while (tProvinces.size() > 0) {
            int toAddID = 0;
            int o = 1;

            for (int oSizexx = tProvinces.size(); o < oSizexx; o++) {
               if (CFG.compareAlphabetic_TwoString(
                  Game.getProvince(tProvinces.get(toAddID)).getProvinceName(), Game.getProvince(tProvinces.get(o)).getProvinceName()
               )) {
                  toAddID = o;
               }
            }

            menuElements.add(
               new Text_StaticBG_ID_Flag(
                  Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  r0W2,
                  buttonHProvinces,
                  tProvinces.get(toAddID),
                  false
               ) {
                  @Override
                  public void actionElement() {
                     Game.player.peaceTreaty.takeProvince(this.getCurrent());
                  }

                  @Override
                  public int getFlagID() {
                     return Game.getProvince(this.getCurrent()).peaceTreatyIsTaken ? Game.player.iCivID : super.getFlagID();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG(Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD)
                     );
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Population") + ": ",
                           CFG.getNumberWithSpaces("" + Game.getProvince(this.getCurrent()).getPopulationTotal()),
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
                           CFG.getPrecision2(Game.getProvince(this.getCurrent()).getEconomyWithBonuses(), 10),
                           Game_Calendar.IMG_ECONOMY,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Cost") + ": ",
                           CFG.getPrecision2(Game.getProvince(this.getCurrent()).fProvinceValue, 10),
                           Images.victoryPoints,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Value(
                  "" + CFG.getPrecision2(Game.getProvince(tProvinces.get(toAddID)).fProvinceValue, 10),
                  Images.victoryPoints,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonHProvinces,
                  ImageManager.getImage(Images.gold).getWidth(),
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                  }
               }
            );
            buttonX = paddingLeft;
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tProvinces.remove(toAddID);
         }
      } else {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("Error"), CFG.FONT_REGULAR, -1, paddingLeft, buttonYPadding, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
         );
         buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_DoubleText(Game.lang.get("PeaceTreaty"), Game.lang.get("MakeDemands"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_Peace.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
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
      if (!visible) {
         CFG.brushTool = false;
      }

      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PEACE_VIEW) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      CFG.brushTool = false;
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PEACE_VIEW) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }
}
