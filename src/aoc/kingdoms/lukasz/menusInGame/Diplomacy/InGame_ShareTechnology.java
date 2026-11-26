package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
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
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy_Flip;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ShareTechnology extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int shareWithCivID = 0;
   public static int technologyID = 0;

   public void shareRandomTechnology(int civID) {
      List<Integer> possibleToShare = new ArrayList<>();

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         if (Game.getCiv(Game.player.iCivID).getTechResearched(i)
            && !Game.getCiv(civID).getTechResearched(i)
            && (TechnologyTree.lTechnology.get(i).RequiredTech < 0 || Game.getCiv(civID).getTechResearched(TechnologyTree.lTechnology.get(i).RequiredTech))
            && (TechnologyTree.lTechnology.get(i).RequiredTech2 < 0 || Game.getCiv(civID).getTechResearched(TechnologyTree.lTechnology.get(i).RequiredTech2))) {
            possibleToShare.add(i);
         }
      }

      if (possibleToShare.size() > 0) {
         technologyID = possibleToShare.get(Game.oR.nextInt(possibleToShare.size()));
      } else {
         technologyID = -1;
      }
   }

   public InGame_ShareTechnology(int nCivID) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      shareWithCivID = nCivID;
      this.shareRandomTechnology(shareWithCivID);
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
      menuElements.add(new ButtonFlag_Diplomacy(shareWithCivID, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
      menuElements.add(
         new Text_Static_ID(
            shareWithCivID,
            Game.getCiv(shareWithCivID).getCivName(),
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
      menuElements.add(new TextIcon_Diplomacy(Game_Calendar.IMG_TECHNOLOGY, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
         @Override
         public Color getColorBar() {
            return Colors.TECH_BLUE;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int statsX = paddingLeft + ButtonRuler_Diplomacy.getButtonWidth() + CFG.PADDING * 2;
      int statsW = menuWidth / 2 - statsX - CFG.PADDING / 2;
      int statsH = (ButtonRuler_Diplomacy.getButtonHeight() - CFG.PADDING * 2) / 3;
      int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            Game.lang.get("Opinion") + ": " + CFG.getPrecision2(Game.getCiv(shareWithCivID).diplomacy.getRelation(Game.player.iCivID), 1),
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
                  InGame_ShareTechnology.shareWithCivID,
                  Game.player.iCivID,
                  Game.getCiv(Game.player.iCivID).diplomacy.isImprovingRelations(InGame_ShareTechnology.shareWithCivID),
                  Game.getCiv(Game.player.iCivID).diplomacy.isDamagingRelations(InGame_ShareTechnology.shareWithCivID)
               );
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            "" + CFG.getShortNumber(Game.getCiv(Game.player.iCivID).getResearchedTechnologies()),
            Game_Calendar.IMG_TECHNOLOGY,
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("UnlockedTechnologies") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + Game.getCiv(Game.player.iCivID).getResearchedTechnologies(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy_Flip(
            "" + CFG.getShortNumber(Game.getCiv(shareWithCivID).getResearchedTechnologies()),
            Game_Calendar.IMG_TECHNOLOGY,
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("UnlockedTechnologies") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + Game.getCiv(InGame_ShareTechnology.shareWithCivID).getResearchedTechnologies(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Opinion") + ": ",
            (GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION > 0.0F ? "+" : "") + CFG.getPrecision2(GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION, 100),
            Images.relationsUp,
            statsX,
            buttonY + (CFG.PADDING + statsH) * 2,
            statsW * 2 + CFG.PADDING,
            statsH,
            ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 2
         )
      );
      RulersManager.loadRulerIMG_DiplomacyLeft(Game.player.iCivID);
      RulersManager.loadRulerIMG_DiplomacyRight(shareWithCivID);
      menuElements.add(new ButtonRuler_Diplomacy(Game.player.iCivID, paddingLeft + CFG.PADDING, buttonY));
      menuElements.add(new ButtonRuler_Diplomacy(shareWithCivID, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING, buttonY) {
         @Override
         public Image getRulerImage() {
            return RulersManager.rulerIMG_DiplomacyRight;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (technologyID < 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         menuElements.add(
            new ButtonTechnology(
               Images.techAvailable, technologyID, menuWidth / 2 - ImageManager.getImage(Images.techGray).getWidth() / 2, buttonY + CFG.PADDING, false, -1
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_ShareTechnology(InGame_ShareTechnology.shareWithCivID);
               }
            }
         );
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY, menuWidth - paddingLeft * 2, TechnologyTree.iTechnologyHeight + CFG.PADDING * 2)
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

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
            Game.lang.get("ShareTechnology"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true,
            Game_Calendar.IMG_TECHNOLOGY
         ) {
            @Override
            public void actionElement() {
               InGame_ShareTechnology.confirm();
            }

            @Override
            public boolean getClickable() {
               return InGame_ShareTechnology.technologyID >= 0;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ShareTechnology"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Opinion") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     (GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION > 0.0F ? "+" : "") + CFG.getPrecision2(GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION, 100),
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.relations, CFG.PADDING, 0));
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
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("ShareTechnology"), true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_ShareTechnology.lTime;
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
      if (technologyID >= 0) {
         Game.getCiv(shareWithCivID).addTechnology(technologyID, false);
         Game.getCiv(shareWithCivID).diplomacy.updateRelation(shareWithCivID, Game.player.iCivID, GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION);
         Game.getCiv(Game.player.iCivID).diplomacy.updateRelation(Game.player.iCivID, shareWithCivID, GameValues.diplomacy.SHARE_TECHNOLOGY_OPINION);
         if (Game.menuManager.getVisibleInGame_Civ()) {
            InGame_Civ.rebuildMenu();
         }

         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = shareWithCivID;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("TechnologyShared"), Game.getCiv(shareWithCivID).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
         ProvinceDraw.addDiplomacyLines(
            Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), Game.getCiv(shareWithCivID).getCapitalProvinceID(), Colors.TECH_BLUE
         );
         Game.menuManager.setVisibleInGame_PopUp(false);
      }
   }
}
