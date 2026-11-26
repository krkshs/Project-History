package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy_Flip;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Ultimatum extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iCivID = 0;
   public static boolean demandAnnexation = false;
   public static List<Integer> demandProvinces = new ArrayList<>();

   public InGame_Ultimatum(int nCivID) {
      List<MenuElement> menuElements = new ArrayList<>();
      demandAnnexation = false;
      demandProvinces.clear();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      iCivID = nCivID;
      int maxWidth = ImageManager.getImage(Images.insultBig).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new ButtonFlag_Diplomacy(
            Game.player.iCivID,
            menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonY + tempTitlePaddingY,
            true
         )
      );
      menuElements.add(new ButtonFlag_Diplomacy(iCivID, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
      menuElements.add(
         new Text_Static_ID(
            iCivID,
            Game.getCiv(iCivID).getCivName(),
            CFG.FONT_REGULAR,
            -1,
            menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonY,
            tempTextW,
            tempTitleH
         )
      );
      menuElements.add(
         new Text_Static_ID(Game.player.iCivID, Game.getCiv(Game.player.iCivID).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH)
      );
      menuElements.add(new TextIcon_Diplomacy(Images.swords, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
         @Override
         public Color getColorBar() {
            return DiplomacyManager.COLOR_INSULT;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int statsX = paddingLeft + ButtonRuler_Diplomacy.getButtonWidth() + CFG.PADDING * 2;
      int statsW = menuWidth / 2 - statsX - CFG.PADDING / 2;
      int statsH = (ButtonRuler_Diplomacy.getButtonHeight() - CFG.PADDING * 2) / 3;
      int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            Game.lang.get("Opinion") + ": " + CFG.getPrecision2(Game.getCiv(iCivID).diplomacy.getRelation(Game.player.iCivID), 1),
            Images.relations,
            statsX,
            buttonY,
            statsW * 2 + CFG.PADDING / 2 * 2,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorTopStats(isActive, this.getIsHovered());
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations(
                  InGame_Ultimatum.iCivID,
                  Game.player.iCivID,
                  Game.getCiv(Game.player.iCivID).diplomacy.isImprovingRelations(InGame_Ultimatum.iCivID),
                  Game.getCiv(Game.player.iCivID).diplomacy.isDamagingRelations(InGame_Ultimatum.iCivID)
               );
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            "" + CFG.getShortNumber(Game.getCiv(Game.player.iCivID).iRegimentsLimit),
            Images.regimentsLimit,
            statsX,
            buttonY + CFG.PADDING + statsH,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + Game.getCiv(Game.player.iCivID).iRegimentsLimit, CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            "" + Game.getCiv(Game.player.iCivID).getNumOfProvinces(),
            Images.provinces,
            statsX,
            buttonY + (CFG.PADDING + statsH) * 2,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy_Flip(
            "" + CFG.getShortNumber(Game.getCiv(iCivID).iRegimentsLimit),
            Images.regimentsLimit,
            statsX + CFG.PADDING + statsW,
            buttonY + CFG.PADDING + statsH,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle("" + Game.getCiv(InGame_Ultimatum.iCivID).iRegimentsLimit, CFG.FONT_BOLD, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy_Flip(
            "" + Game.getCiv(iCivID).getNumOfProvinces(),
            Images.provinces,
            statsX + CFG.PADDING + statsW,
            buttonY + (CFG.PADDING + statsH) * 2,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      RulersManager.loadRulerIMG_DiplomacyLeft(Game.player.iCivID);
      RulersManager.loadRulerIMG_DiplomacyRight(iCivID);
      menuElements.add(new ButtonRuler_Diplomacy(Game.player.iCivID, paddingLeft + CFG.PADDING, buttonY));
      menuElements.add(new ButtonRuler_Diplomacy(iCivID, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING, buttonY) {
         @Override
         public Image getRulerImage() {
            return RulersManager.rulerIMG_DiplomacyRight;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      menuElements.add(
         new TextBonus(
            Game.lang.get("LegacyPoints") + ": ",
            "" + CFG.getPrecision2(Math.abs(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY), 10),
            Images.legacy,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("LegacyPoints") + ": ",
                     "" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY, 10),
                     Images.legacy,
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
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST, 100),
            Images.diplomacy,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("DiplomacyPoints") + ": ",
                     "" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST, 100),
                     Images.diplomacy,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      paddingLeft += CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame_ImageSparks(
            Game.lang.get("SendUltimatum"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true,
            Images.swords
         ) {
            @Override
            public void actionElement() {
               InGame_Ultimatum.confirm();
            }

            @Override
            public boolean getClickable() {
               return InGame_Ultimatum.demandAnnexation || !InGame_Ultimatum.demandProvinces.isEmpty();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SendUltimatum"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.swords, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Opinion") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_AN_INSULT_DAMAGE, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.relations, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Legacy") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.legacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("DiplomacyPoints") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "-" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_FlagTitle(InGame_Ultimatum.iCivID, 0, CFG.PADDING));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("AttitudeTowards"), CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_FlagTitle(Game.player.iCivID, CFG.PADDING, CFG.PADDING));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     DiplomacyManager.getOpinion_String((int)Game.getCiv(InGame_Ultimatum.iCivID).diplomacy.getRelation(Game.player.iCivID)) + "",
                     CFG.FONT_BOLD,
                     DiplomacyManager.getOpinion_Color((int)Game.getCiv(InGame_Ultimatum.iCivID).diplomacy.getRelation(Game.player.iCivID))
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Opinion") + ": ",
                     (Game.getCiv(InGame_Ultimatum.iCivID).diplomacy.getRelation(Game.player.iCivID) > 0.0F ? "+" : "")
                        + CFG.getPrecision2(Game.getCiv(InGame_Ultimatum.iCivID).diplomacy.getRelation(Game.player.iCivID), 10),
                     Images.relations,
                     CFG.FONT_BOLD,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     DiplomacyManager.getOpinion_Color((int)Game.getCiv(InGame_Ultimatum.iCivID).diplomacy.getRelation(Game.player.iCivID))
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public int getSFX() {
               return SoundsManager.DIPLOMACY_CLICK;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int buttonHProvinces = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("DemandAnnexation"),
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
         new Text_StaticBG_ID_Flag(
            Game.getCiv(iCivID).getCivName() + ": " + Game.lang.get("FullAnnexation"),
            CFG.FONT_REGULAR_SMALL,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonHProvinces,
            Game.getCiv(iCivID).getProvinceID(0),
            false
         ) {
            @Override
            public void actionElement() {
               InGame_Ultimatum.demandAnnexation = !InGame_Ultimatum.demandAnnexation;
            }

            @Override
            public void actionElementPPM() {
               Game.mapCoords.centerToProvinceID(this.getCurrent());
            }

            @Override
            public int getFlagID() {
               return InGame_Ultimatum.demandAnnexation ? Game.player.iCivID : InGame_Ultimatum.iCivID;
            }

            @Override
            protected Color getColor(boolean isActive) {
               return InGame_Ultimatum.demandAnnexation ? Colors.getColorPositive(isActive, this.getIsHovered()) : super.getColor(isActive);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Provinces") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                     CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Ultimatum.iCivID).getNumOfProvinces()), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Population") + ": ",
                     CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Ultimatum.iCivID).getPopulationTotal()),
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
                     CFG.getPrecision2(Game.getCiv(InGame_Ultimatum.iCivID).getEconomyTotal(), 10),
                     Game_Calendar.IMG_ECONOMY,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
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

      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         tProvinces.add(Game.getCiv(iCivID).getProvinceID(i));
      }

      if (!tProvinces.isEmpty()) {
         for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
            Game.getCiv(iCivID).getProvinceID(i);

            while (tProvinces.size() > 0) {
               int toAddID = 0;
               int o = 1;

               for (int oSize = tProvinces.size(); o < oSize; o++) {
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
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft * 2,
                     buttonHProvinces,
                     tProvinces.get(toAddID),
                     false
                  ) {
                     boolean take = false;

                     @Override
                     public void actionElement() {
                        this.take = !this.take;
                        if (this.take) {
                           for (int i = InGame_Ultimatum.demandProvinces.size() - 1; i >= 0; i--) {
                              if (InGame_Ultimatum.demandProvinces.get(i) == this.getCurrent()) {
                                 return;
                              }
                           }

                           InGame_Ultimatum.demandProvinces.add(this.getCurrent());
                        } else {
                           for (int ix = InGame_Ultimatum.demandProvinces.size() - 1; ix >= 0; ix--) {
                              if (InGame_Ultimatum.demandProvinces.get(ix) == this.getCurrent()) {
                                 InGame_Ultimatum.demandProvinces.remove(ix);
                                 break;
                              }
                           }
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        this.setText(Game.getProvince(this.getCurrent()).getProvinceName());
                        Game.mapCoords.centerToProvinceID(this.getCurrent());
                     }

                     @Override
                     public int getFlagID() {
                        return this.take ? Game.player.iCivID : super.getFlagID();
                     }

                     @Override
                     protected Color getColor(boolean isActive) {
                        return this.take ? Colors.getColorPositive(isActive, this.getIsHovered()) : super.getColor(isActive);
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Province") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                              Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                           )
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
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tProvinces.remove(toAddID);
            }
         }
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT4));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("Ultimatum"), true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_Ultimatum.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT * 3 / 5 + (int)(CFG.BUTTON_HEIGHT * 3 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
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
      DiplomacyManager.updateAnimationTime();
   }

   public static final void confirm() {
      if (Game.getCiv(Game.player.iCivID).fLegacy < -GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientLegacy") + ": ",
               CFG.getPrecision2(Math.abs(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY), 100),
               Images.legacy
            );
      } else if (Game.getCiv(Game.player.iCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("Cost") + ", " + Game.lang.get("DiplomacyPoints") + ": ",
               CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST, 100),
               Images.diplomacy
            );
      } else {
         Game.getCiv(Game.player.iCivID).addLegacy(-GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST_LEGACY);
         Civilization var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_SEND_ULTIMATUM_COST;
         if (demandAnnexation || demandProvinces.size() > 0) {
            Game.getCiv(iCivID).diplomacy.updateRelation(iCivID, Game.player.iCivID, GameValues.diplomacy.ULTIMATUM_RELATION_CHANGE);
            Game.getCiv(Game.player.iCivID).diplomacy.updateRelation(Game.player.iCivID, iCivID, GameValues.diplomacy.ULTIMATUM_RELATION_CHANGE);
            if (!(
               Game.getCiv(Game.player.iCivID).getArmyRegimentSize() > Game.getCiv(iCivID).getArmyRegimentSize() * GameValues.diplomacy.ULTIMATUM_ARMY_MODIFIER
            )) {
               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = iCivID;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("RefusedUltimatum"), "");
               InGame_Info.imgID = Images.infoDiplomacy;
            } else {
               if (demandAnnexation) {
                  for (int i = Game.getCiv(iCivID).getNumOfProvinces() - 1; i >= 0; i--) {
                     try {
                        int pID = Game.getCiv(iCivID).getProvinceID(i);
                        Game.getProvince(pID).setCivID(Game.player.iCivID);
                        Game.getProvince(pID).removeArmyCivID(iCivID);
                     } catch (Exception var4) {
                        CFG.exceptionStack(var4);
                     }
                  }
               } else if (demandProvinces.size() > 0) {
                  for (int i = demandProvinces.size() - 1; i >= 0; i--) {
                     try {
                        int pID = demandProvinces.get(i);
                        if (Game.getProvince(pID).getCivID() == iCivID) {
                           Game.getProvince(pID).setCivID(Game.player.iCivID);
                           Game.getProvince(pID).removeArmyCivID(iCivID);
                        }
                     } catch (Exception var3) {
                        CFG.exceptionStack(var3);
                     }
                  }
               }

               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = iCivID;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("AcceptedOurUltimatum"), "");
               InGame_Info.imgID = Images.infoDiplomacy;
            }

            ProvinceDraw.addDiplomacyLines(
               Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), Game.getCiv(iCivID).getCapitalProvinceID(), DiplomacyManager.COLOR_INSULT
            );
         }

         Game.menuManager.setVisibleInGame_PopUp(false);
      }
   }
}
