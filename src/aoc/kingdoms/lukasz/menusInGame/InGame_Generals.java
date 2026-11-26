package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral_Assign;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageFull;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle_General;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Generals extends Menu {
   public static int assignProvinceID = 0;
   public static String assignArmyKey = "";
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static boolean backButton = false;

   public InGame_Generals() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int leftW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 5;
      int rightW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 2 / 5;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int maxIconWidth = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth();
      buttonY += CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            backButton ? Game.lang.get("Back") : Game.lang.get("Close"),
            backButton ? Game_Calendar.IMG_MANPOWER : Images.x,
            paddingLeft,
            buttonY,
            leftW,
            buttonH,
            maxIconWidth,
            0,
            true
         ) {
            @Override
            public void actionElement() {
               if (InGame_Generals.backButton) {
                  InGame.action3();
               } else {
                  Game.menuManager.setVisibleInGame_Generals(false);
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (InGame_Generals.backButton) {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               } else {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Close"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.x, CFG.PADDING, 0));
               }

               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + Game.getCiv(Game.player.iCivID).getTotalGenerals(),
            Images.general,
            paddingLeft + leftW + CFG.PADDING,
            buttonY,
            rightW,
            buttonH,
            maxIconWidth,
            0
         ) {
            @Override
            public void actionElement() {
               if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
                  Game.menuManager.setVisibleInGame_GeneralRecruit(false);
               } else {
                  Game.menuManager.rebuildInGame_GeneralRecruit();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitGeneral"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Generals") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).getTotalGenerals(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnassignedGenerals") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(Game.player.iCivID).getGeneralsNotAssignedSize(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MilitaryAcademyForGenerals"), CFG.FONT_BOLD_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Level") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                        + " / "
                        + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
                     CFG.FONT_BOLD_SMALL,
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
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      int buttonX = paddingLeft;
      int buttonGeneralWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 2;
      menuElements.add(new ButtonGame(Game.lang.get("RecruitGeneral"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
               Game.menuManager.setVisibleInGame_GeneralRecruit(false);
            } else {
               Game.menuManager.rebuildInGame_GeneralRecruit();
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitGeneral"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      boolean assignedGenerals = false;

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).iArmyPositionSize; i++) {
         for (int j = 0; j < Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(i)).getArmySize(); j++) {
            if (Game.player.iCivID == Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(i)).getArmy(j).civID
               && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(i)).getArmy(j).armyGeneral != null) {
               assignedGenerals = true;
               break;
            }
         }

         if (assignedGenerals) {
            break;
         }
      }

      if (Game.getCiv(Game.player.iCivID).iGeneralsSize == 0 && !assignedGenerals) {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("NoGenerals") + ".",
               CFG.FONT_REGULAR_SMALL,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("UnassignedGenerals"),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      if (Game.getCiv(Game.player.iCivID).iGeneralsSize > 0) {
         for (int i = 0; i < Game.getCiv(Game.player.iCivID).iGeneralsSize; i++) {
            menuElements.add(
               new ButtonArmyGeneral_Assign(
                  buttonX,
                  buttonY,
                  buttonGeneralWidth,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).n,
                  Game.player.iCivID,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).getAttack(),
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).getDefense(),
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).g,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).d,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).m,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).y,
                  false,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).key,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).sI,
                  Game.getCiv(Game.player.iCivID).getGeneralNotAssigned(i).getCombatExperience()
               )
            );
            if (i % 2 == 1) {
               buttonX = paddingLeft;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            } else {
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         }
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      if (assignedGenerals) {
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("GeneralsCommandingArmies"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      buttonX = paddingLeft;
      int ix = 0;

      for (int addedGenerals = 0; ix < Game.getCiv(Game.player.iCivID).iArmyPositionSize; ix++) {
         for (int jx = 0; jx < Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmySize(); jx++) {
            if (Game.player.iCivID == Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).civID
               && Game.getCiv(Game.player.iCivID)
                  .getArmyPositionKey(ix)
                  .equals(Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).key)
               && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral != null) {
               menuElements.add(
                  new ButtonArmyGeneral_Assign(
                     buttonX,
                     buttonY,
                     buttonGeneralWidth,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.n,
                     Game.player.iCivID,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.getAttack(),
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.getDefense(),
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.g,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.d,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.m,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.y,
                     false,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.key,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.sI,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.getCombatExperience()
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               int statW2 = menuWidth - buttonX - paddingLeft;
               int statH2 = (ButtonArmyGeneral_Assign.getButtonHeight() - CFG.PADDING * 2) / 3;
               menuElements.add(
                  new Text_StaticBG_RulerTitle_General(
                     "" + Game.getProvince(Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).provinceID).getProvinceName(),
                     buttonX,
                     buttonY,
                     statW2,
                     statH2,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).key,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).provinceID,
                     Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).civID
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Army") + ": ", CFG.FONT_BOLD));
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear("" + this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements, true);
                     }

                     @Override
                     public void actionElement() {
                        int nArmyID = Game.getProvince(this.iProvinceID).getArmyKeyID(this.key);
                        if (nArmyID < 0) {
                           for (int i = 0; i < Game.getProvincesSize(); i++) {
                              int outID = Game.getProvince(i).getArmyKeyID(this.key, this.iCivID);
                              if (outID >= 0) {
                                 this.iProvinceID = i;
                                 nArmyID = outID;
                                 break;
                              }
                           }
                        }

                        if (nArmyID >= 0) {
                           if (Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(Game.getProvince(this.iProvinceID).getArmy(nArmyID).key)) {
                              Game.mapCoords.centerToProvinceID(this.iProvinceID);
                           } else {
                              Game.clearActiveArmy();
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(this.iProvinceID).getArmy(nArmyID).key;
                              nHA.iCivID = Game.getProvince(this.iProvinceID).getArmy(nArmyID).civID;
                              nHA.iProvinceID = this.iProvinceID;
                              nHA.iArmyID = nArmyID;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                              if (!Game.getProvince(this.iProvinceID).getDrawProvince()) {
                                 Game.mapCoords.centerToProvinceID(this.iProvinceID);
                              }
                           }

                           if (Game.menuManager.getVisibleInGame_Generals()) {
                              Game.menuManager.setVisibleInGame_Generals(false);
                           }

                           if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
                              Game.menuManager.setVisibleInGame_GeneralRecruit(false);
                           }
                        }
                     }
                  }
               );
               int statsY = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG(
                     CFG.getNumberWithSpaces("" + Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).iArmy),
                     Game_Calendar.IMG_MANPOWER,
                     buttonX,
                     statsY,
                     statW2,
                     statH2,
                     maxIconW
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmySize") + ": ", CFG.FONT_REGULAR_SMALL));
                        nData.add(new MenuElement_HoverElement_Type_Text("" + this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG(
                     CFG.getPrecision2(Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).fMaintenanceCost, 100),
                     Images.gold,
                     buttonX,
                     statsY,
                     statW2,
                     statH2,
                     maxIconW
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmyMaintenance") + ": ", CFG.FONT_REGULAR_SMALL));
                        nData.add(new MenuElement_HoverElement_Type_Text("" + this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.armyMaintenance, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               buttonX = paddingLeft;
               buttonY += ButtonArmyGeneral_Assign.getButtonHeight() + CFG.PADDING;
               addedGenerals++;
            }
         }
      }

      if (menuElements.size() > 0) {
         buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      }

      ix = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ix)));
      this.initMenu(new MenuTitleIMG("", false, false, Images.title1Red) {
         @Override
         public long getTime() {
            return InGame_Generals.lTime;
         }
      }, menuX, menuY, menuWidth, ix, menuElements, false, true);
      this.drawScrollPositionAlways = false;
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
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Generals"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   public static MenuElement_Hover getHoverMilitaryAcademyForGenerals() {
      return getHoverMilitaryAcademyForGenerals(true, Game.player.iCivID);
   }

   public static MenuElement_Hover getHoverMilitaryAcademyForGenerals(boolean showUpgrade, int iCivID) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("MilitaryAcademyForGenerals"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_ImageFull(Images.armyGeneralAcademyBig, 0, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Level") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.getCiv(iCivID).getMilitaryAcademyForGeneralsLevel() + " / " + Game.getMilitaryAcademyForGenerals_MaxLvl(iCivID),
            CFG.FONT_BOLD_SMALL,
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
            Game.lang.get("GeneralsAttack") + ": ",
            (Game.getMilitaryAcademyForGenerals_GeneralAttack(iCivID) > 0 ? "+" : "")
               + CFG.getPrecision2((float)Game.getMilitaryAcademyForGenerals_GeneralAttack(iCivID), 100),
            Images.attack,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademyForGenerals_GeneralAttack(iCivID) > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("GeneralsDefense") + ": ",
            (Game.getMilitaryAcademyForGenerals_GeneralDefense(iCivID) > 0 ? "+" : "")
               + CFG.getPrecision2((float)Game.getMilitaryAcademyForGenerals_GeneralDefense(iCivID), 100),
            Images.defense,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademyForGenerals_GeneralDefense(iCivID) > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MaintenanceCost") + ": ",
            "" + CFG.getPrecision2(Game.getMilitaryAcademyForGenerals_MaintenanceCost(iCivID), 100),
            Images.gold,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademyForGenerals_MaintenanceCost(iCivID) > 0.0F ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (showUpgrade) {
         if (Game.getCiv(iCivID).getMilitaryAcademyForGeneralsLevel() >= Game.getMilitaryAcademyForGenerals_MaxLvl(iCivID)) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevel"), CFG.FONT_BOLD_SMALL));
            nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else {
            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UpgradeMilitaryAcademyForGenerals"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(Game.getMilitaryAcademyForGenerals_Cost(iCivID), 100), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GeneralsAttackPerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2((float)GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_ATTACK_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.attack, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GeneralsDefensePerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2((float)GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_DEFENSE_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.defense, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaintenanceCost") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2(GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_MAINTENANCE_COST_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      return new MenuElement_Hover(nElements);
   }

   public static final void upgradeMilitaryAcademyForGenerals() {
      if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel() >= Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("MaximumLevel") + ": ",
               ""
                  + Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                  + " / "
                  + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
               Images.general
            );
      } else if (Game.getCiv(Game.player.iCivID).fGold < Game.getMilitaryAcademyForGenerals_Cost(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getMilitaryAcademyForGenerals_Cost(Game.player.iCivID), 100), Images.gold
            );
      } else {
         Game.getCiv(Game.player.iCivID).upgradeMilitaryAcademyForGenerals();
         if (Game.menuManager.getVisibleInGame_Generals()) {
            Game.menuManager.rebuildInGame_Generals();
            Game.menuManager.setVisibleInGame_Generals(true);
            lTime = 0L;
         }

         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.menuManager
            .rebuildInGame_Info(
               Game.lang.get("MilitaryAcademyForGenerals"),
               Game.lang.get("Level")
                  + ": "
                  + Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                  + " / "
                  + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID)
            );
         InGame_Info.imgID = Images.infoGeneral;
         Game.getCiv(Game.player.iCivID).updateTotalIncomePerMonth();
      }
   }
}
