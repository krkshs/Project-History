package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonSiegeTime;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Loot;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Siege;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceSiege extends Menu {
   public static SparksAnimation sparksAnimation = new SparksAnimation();
   public static int iProvinceID;
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public int imageOverID = 0;

   public InGame_ProvinceSiege() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title530).getHeight();
      int menuWidth = ImageManager.getImage(Images.title530).getWidth() - Images.boxTitleBORDERWIDTH;
      int menuMinHeight = ImageManager.getImage(Images.armyCampOver0).getHeight();
      int menuHeight = 240;
      int menuX = 0;
      int menuY = CFG.GAME_HEIGHT - menuHeight;
      int buttonYPadding = CFG.PADDING;
      int generalPadding = CFG.PADDING * 3;
      int buttonY = CFG.PADDING;
      if (Game.getProvince(iProvinceID).getFortLevel() == 0) {
         switch (iProvinceID % 3) {
            case 0:
               this.imageOverID = Images.siegeOver0;
               break;
            case 1:
               this.imageOverID = Images.siegeOver1;
               break;
            default:
               this.imageOverID = Images.siegeOver2;
         }
      } else {
         switch (iProvinceID % 3) {
            case 0:
               this.imageOverID = Images.siegeOverFort0;
               break;
            case 1:
               this.imageOverID = Images.siegeOverFort1;
               break;
            default:
               this.imageOverID = Images.siegeOverFort2;
         }
      }

      int maxWidth = ImageManager.getImage(Images.siege).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      int numOfSoldiers = 0;
      int iSiegeCivID = 0;
      int tempSiegeArmy = 0;
      float dailySiegeProgress = SiegeManager.getDailySiegeProgress(iProvinceID);
      if (Game.getProvince(iProvinceID).isOccupied()) {
         for (int i = 0; i < Game.getProvince(iProvinceID).getArmySize(); i++) {
            if (DiplomacyManager.isAtWar(Game.getProvinceData(iProvinceID).getOccupiedByCivID(), Game.getProvince(iProvinceID).getArmy(i).civID)) {
               numOfSoldiers += Game.getProvince(iProvinceID).getArmy(i).iArmy;
               if (iSiegeCivID == 0 || Game.getProvince(iProvinceID).getArmy(i).iArmy > tempSiegeArmy) {
                  tempSiegeArmy = Game.getProvince(iProvinceID).getArmy(i).iArmy;
                  iSiegeCivID = Game.getProvince(iProvinceID).getArmy(i).civID;
               }
            }
         }
      } else {
         for (int ix = 0; ix < Game.getProvince(iProvinceID).getArmySize(); ix++) {
            if (DiplomacyManager.isAtWar(Game.getProvince(iProvinceID).getCivID(), Game.getProvince(iProvinceID).getArmy(ix).civID)) {
               numOfSoldiers += Game.getProvince(iProvinceID).getArmy(ix).iArmy;
               if (iSiegeCivID == 0 || Game.getProvince(iProvinceID).getArmy(ix).iArmy > tempSiegeArmy) {
                  tempSiegeArmy = Game.getProvince(iProvinceID).getArmy(ix).iArmy;
                  iSiegeCivID = Game.getProvince(iProvinceID).getArmy(ix).civID;
               }
            }
         }
      }

      menuElements.add(new ButtonFlag_Diplomacy(iSiegeCivID, paddingLeft + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
      menuElements.add(
         new ButtonFlag_Diplomacy(
            Game.getProvince(iProvinceID).isOccupied() ? Game.getProvinceData(iProvinceID).getOccupiedByCivID() : Game.getProvince(iProvinceID).getCivID(),
            menuWidth - paddingLeft - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonY + tempTitlePaddingY,
            true
         )
      );
      menuElements.add(
         new TextIcon_Siege(
            Game.lang.get("Progress") + ": ", iProvinceID, Images.siege, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_ProvinceSiege.getHoverSiege(this.iProvinceID);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      menuElements.add(new ButtonSiegeTime("", paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4));
      buttonY += CFG.PADDING;
      int buttonW = CFG.BUTTON_HEIGHT;
      int buttonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int buttonH2 = CFG.BUTTON_HEIGHT;
      menuElements.add(
         new TextIcon2(
            "" + Game.getProvince(iProvinceID).getFortLevel(),
            Images.fort,
            menuWidth - paddingLeft - buttonW,
            ImageManager.getImage(this.imageOverID).getHeight() - paddingLeft - buttonH2,
            buttonW,
            buttonH2
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_ProvinceInfo.getHoverFort(InGame_ProvinceSiege.iProvinceID, false);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Loot(
            "",
            Images.loot,
            menuWidth - paddingLeft - buttonW,
            ImageManager.getImage(this.imageOverID).getHeight() - paddingLeft - buttonH2 - CFG.PADDING - (CFG.TEXT_HEIGHT + CFG.PADDING * 4),
            buttonW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ImageManager.getImage(Images.fort).getWidth(),
            iProvinceID
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_ProvinceInfo.getHoverLoot(InGame_ProvinceSiege.iProvinceID, false);
            }
         }
      );
      menuElements.add(
         new TextIcon2(
            "" + CFG.getShortNumber(numOfSoldiers),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            ImageManager.getImage(this.imageOverID).getHeight() - paddingLeft - buttonH2,
            buttonW,
            buttonH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               int numOfSoldiers = 0;
               if (Game.getProvince(InGame_ProvinceSiege.iProvinceID).isOccupied()) {
                  for (int i = 0; i < Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmySize(); i++) {
                     if (DiplomacyManager.isAtWar(
                        Game.getProvinceData(InGame_ProvinceSiege.iProvinceID).getOccupiedByCivID(),
                        Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(i).civID
                     )) {
                        numOfSoldiers += Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(i).iArmy;
                     }
                  }
               } else {
                  for (int ix = 0; ix < Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmySize(); ix++) {
                     if (DiplomacyManager.isAtWar(
                        Game.getProvince(InGame_ProvinceSiege.iProvinceID).getCivID(), Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(ix).civID
                     )) {
                        numOfSoldiers += Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(ix).iArmy;
                     }
                  }
               }

               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("BesiegingSoldiers") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + CFG.getNumberWithSpaces("" + numOfSoldiers), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DailySiegeProgress") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "+" + CFG.getPrecision2(SiegeManager.getDailySiegeProgress(InGame_ProvinceSiege.iProvinceID), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("DailySiegeProgressIsTheSumOfTheSiegeAbilitiesOfAllUnitsInvolvedInTheSiege"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new TextIcon2(
            "+" + CFG.getPrecision2(dailySiegeProgress, 100),
            Images.siege,
            paddingLeft + buttonW + CFG.PADDING,
            ImageManager.getImage(this.imageOverID).getHeight() - paddingLeft - buttonH2,
            buttonW,
            buttonH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               int numOfSoldiers = 0;
               if (Game.getProvince(InGame_ProvinceSiege.iProvinceID).isOccupied()) {
                  for (int i = 0; i < Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmySize(); i++) {
                     if (DiplomacyManager.isAtWar(
                        Game.getProvinceData(InGame_ProvinceSiege.iProvinceID).getOccupiedByCivID(),
                        Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(i).civID
                     )) {
                        numOfSoldiers += Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(i).iArmy;
                     }
                  }
               } else {
                  for (int ix = 0; ix < Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmySize(); ix++) {
                     if (DiplomacyManager.isAtWar(
                        Game.getProvince(InGame_ProvinceSiege.iProvinceID).getCivID(), Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(ix).civID
                     )) {
                        numOfSoldiers += Game.getProvince(InGame_ProvinceSiege.iProvinceID).getArmy(ix).iArmy;
                     }
                  }
               }

               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("BesiegingSoldiers") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + CFG.getNumberWithSpaces("" + numOfSoldiers), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DailySiegeProgress") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "+" + CFG.getPrecision2(SiegeManager.getDailySiegeProgress(InGame_ProvinceSiege.iProvinceID), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("DailySiegeProgressIsTheSumOfTheSiegeAbilitiesOfAllUnitsInvolvedInTheSiege"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY = 0;
      int ixx = 0;

      for (int iSize = menuElements.size(); ixx < iSize; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING;
         }
      }

      menuHeight = Math.max(menuMinHeight, buttonY);
      menuY = CFG.GAME_HEIGHT - menuHeight;
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("SiegeOf", Game.getProvince(iProvinceID).getProvinceName()), false, false, Images.title530) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            super.draw(oSB, nPosX, nPosY, nWidth + Images.boxTitleBORDERWIDTH, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_ProvinceSiege.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
      ProvinceDraw.buildSiegeLines(iProvinceID);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         iTranslateY += (int)(CFG.BUTTON_HEIGHT / 2.0F - CFG.BUTTON_HEIGHT / 2 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH,
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH,
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop530,
         Images.insideBot530
      );
      ImageManager.getImage(this.imageOverID)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(this.imageOverID).getHeight())
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
      ImageManager.getImage(this.imageOverID)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            (int)(this.getWidth() * Game.getProvince(iProvinceID).getSiegeProgress()),
            Math.min(this.getHeight(), ImageManager.getImage(this.imageOverID).getHeight())
         );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
      sparksAnimation.draw2(
         oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - Images.sparkHeight + iTranslateY, this.getWidth(), Images.sparkHeight
      );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (!Game.getProvinceData4(iProvinceID).isUnderSiege()) {
         Game.menuManager.setVisibleInGame_Siege(false);
      }
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible) {
         ProvinceDraw.clearSiegeLines();
      }
   }

   public static MenuElement_Hover getHoverSiege(int nProvinceID) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SiegeProgress") + ": ", CFG.FONT_BOLD));
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle(
            "" + CFG.getPrecision2(Game.getProvince(nProvinceID).getSiegeProgress() * 100.0F, 1) + "%", CFG.FONT_BOLD, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.siege, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Progress") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2(Game.getProvinceData4(nProvinceID).getSiegeProgress(), 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
      nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2((float)Game.getProvince(nProvinceID).getFortDefense(), 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_FORT_DEFENSE, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DailySiegeProgress") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "+" + CFG.getPrecision2(SiegeManager.getDailySiegeProgress(nProvinceID), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Max") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text("+" + CFG.getPrecision2(GameValues.siege.SIEGE_MAX_PROGRESS, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text_Desc(
            Game.lang.get("TheSiegeEndsWhenTheProgressReachesTheProvincesDefense"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ProvinceDefense") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2((float)Game.getProvince(nProvinceID).getFortDefense(), 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_FORT_DEFENSE, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text_Desc(
            " = "
               + CFG.getPrecision2(GameValues.siege.SIEGE_FORT_DEFENSE_DEFAULT, 100)
               + " + "
               + Game.lang.get("DefenseLevel")
               + " * "
               + CFG.getPrecision2(GameValues.siege.SIEGE_FORT_DEFENSE_PER_FORT_LVL, 100)
               + " + "
               + Game.lang.get("LocalManpowerLevel")
               + " * "
               + CFG.getPrecision2(GameValues.siege.SIEGE_FORT_DEFENSE_PER_MANPOWER_LVL, 100)
               + " + "
               + Game.lang.get("GrowthRate")
               + "*"
               + CFG.getPrecision2(GameValues.siege.SIEGE_FORT_DEFENSE_PER_GROWTH_RATE_LVL, 100),
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT3
         )
      );
      nElements.add(new MenuElement_HoverElement(nData, false));
      nData.clear();
      return new MenuElement_Hover(nElements);
   }
}
