package aoc.kingdoms.lukasz.menusInGame.AllianceSpecial;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.allianceHRE.HREManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter_HRE;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.Button_HRE_Reform;
import aoc.kingdoms.lukasz.menu_element.button.Button_HRE_Reform_Red;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_AllianceSpecialReformHRE extends Menu {
   public static int allianceID = 0;
   public static int reformID = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_AllianceSpecialReformHRE(int nAllianceID, int nReformID) {
      List<MenuElement> menuElements = new ArrayList<>();
      allianceID = nAllianceID;
      reformID = nReformID;
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING * 2;
      if (reformID == Game.alliancesSpecial.get(allianceID).iReformsPassed) {
         menuElements.add(
            new Button_HRE_Reform(
               Game.lang.get("Reform" + reformID),
               Game.lang.get("Reform" + reformID + ".d"),
               paddingLeft + CFG.PADDING,
               buttonY,
               menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
               reformID,
               reformID
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp()
                     && MenuManager.IN_GAME_POP_UP_MENU_ID == 35
                     && InGame_AllianceSpecial.allianceID == InGame_AllianceSpecialReformHRE.allianceID) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_AllianceSpecial(InGame_AllianceSpecialReformHRE.allianceID);
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecialReformHRE.allianceID, this.reformID);
               }
            }
         );
      } else {
         menuElements.add(
            new Button_HRE_Reform_Red(
               Game.lang.get("Reform" + reformID),
               Game.lang.get("Reform" + reformID + ".d"),
               paddingLeft + CFG.PADDING,
               buttonY,
               menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
               reformID,
               reformID
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp()
                     && MenuManager.IN_GAME_POP_UP_MENU_ID == 35
                     && InGame_AllianceSpecial.allianceID == InGame_AllianceSpecialReformHRE.allianceID) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_AllianceSpecial(InGame_AllianceSpecialReformHRE.allianceID);
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecialReformHRE.allianceID, this.reformID);
               }
            }
         );
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("ReformRequirements"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      if (GameValues.hre.HRE_REFORM_COST_REQUIRED_TECH[reformID] >= 0) {
         menuElements.add(
            new TextBonus(
               Game.lang.get("RequiredTechnology") + ": ",
               "" + TechnologyTree.lTechnology.get(GameValues.hre.HRE_REFORM_COST_REQUIRED_TECH[reformID]).Name,
               Game_Calendar.IMG_TECHNOLOGY,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 5,
               iconWidth
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                     Game.menuManager.setVisibleInGame_TechnologyTree(false);
                  } else {
                     Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                     InGame_TechnologyTree.centerToTechID = GameValues.hre.HRE_REFORM_COST_REQUIRED_TECH[InGame_AllianceSpecialReformHRE.reformID];
                     Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      if (GameValues.hre.HRE_REFORM_COST_GOLD[reformID] > 0.0F) {
         menuElements.add(
            new TextBonus(
               Game.lang.get("Gold") + ": ",
               "" + CFG.getPrecision2(GameValues.hre.HRE_REFORM_COST_GOLD[reformID], 100),
               Images.gold,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 5,
               iconWidth
            ) {}
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new TextBonus(
            "",
            "" + CFG.getPrecision2(GameValues.hre.HRE_REFORM_COST_DIPLOMACY[reformID], 10),
            Images.diplomacy,
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("DiplomacyPoints") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.sText2, CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(this.imageID, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new TextBonus(
            "",
            "" + CFG.getPrecision2(GameValues.hre.HRE_REFORM_COST_LEGACY[reformID], 10),
            Images.legacy,
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("LegacyPoints") + ": ", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.sText2, CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(this.imageID, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               if (Game.menuManager.getVisibleInGame_PopUp()
                  && MenuManager.IN_GAME_POP_UP_MENU_ID == 35
                  && InGame_AllianceSpecial.allianceID == InGame_AllianceSpecialReformHRE.allianceID) {
                  Game.menuManager.setVisibleInGame_PopUp(false);
               } else {
                  Game.menuManager.rebuildInGame_AllianceSpecial(InGame_AllianceSpecialReformHRE.allianceID);
               }
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("PassReform"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            !HREManager.passReform_ReformsPassed(allianceID, reformID)
         ) {
            @Override
            public void actionElement() {
               InGame_AllianceSpecialReformHRE.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecialReformHRE.allianceID, InGame_AllianceSpecialReformHRE.reformID);
            }

            @Override
            public boolean getClickable() {
               return super.getClickable() && Game.player.iCivID == Game.alliancesSpecial.get(InGame_AllianceSpecialReformHRE.allianceID).iLeaderCivID;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, tMenuHeight)));
      this.initMenu(new MenuTitleIMG_FlagCenter_HRE(allianceID, Game.lang.get("Reform" + reformID), Game.lang.get("Reform"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_AllianceSpecialReformHRE.lTime;
         }

         @Override
         public int getFlagCivID() {
            return Game.player.iCivID;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, tMenuHeight, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
   }

   public static void confirm() {
      if (Game.alliancesSpecial.get(allianceID).iLeaderCivID == Game.player.iCivID) {
         if (HREManager.passReform_Legacy(allianceID, reformID)) {
            Game.menuManager
               .addToastInsufficient(
                  Game.lang.get("InsufficientLegacy") + ": ", CFG.getPrecision2(GameValues.hre.HRE_REFORM_COST_LEGACY[reformID], 100), Images.legacy
               );
         } else if (HREManager.passReform_Diplomacy(allianceID, reformID)) {
            Game.menuManager
               .addToastInsufficient(
                  Game.lang.get("DiplomacyPoints") + ": ", CFG.getPrecision2(GameValues.hre.HRE_REFORM_COST_DIPLOMACY[reformID], 100), Images.diplomacy
               );
         } else if (HREManager.passReform_RequiredTech(allianceID, reformID)) {
            Game.menuManager
               .addToastInsufficient(
                  Game.lang.get("RequiredTechnology") + ": ",
                  TechnologyTree.lTechnology.get(GameValues.hre.HRE_REFORM_COST_REQUIRED_TECH[reformID]).Name,
                  Game_Calendar.IMG_TECHNOLOGY
               );
         } else if (HREManager.passReform(allianceID, reformID)) {
            Game.menuManager.rebuildInGame_AllianceSpecial(allianceID);
            InGame_Info.iCivID = Game.player.iCivID;
            InGame_Info.iCivID2 = 0;
            Game.menuManager.rebuildInGame_Info(Game.lang.get("ReformPassed"), Game.lang.get("Reform" + reformID));
            InGame_Info.imgID = Images.infoCrown;
         }
      }
   }
}
