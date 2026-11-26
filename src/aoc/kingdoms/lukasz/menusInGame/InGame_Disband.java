package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReorganize;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Disband extends Menu {
   public static boolean restartAnimation = true;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static ArmyDivision armyLeft = new ArmyDivision(0, 0, new ArrayList<>());
   public static ArmyDivision armyRight = new ArmyDivision(0, 0, new ArrayList<>());

   public InGame_Disband() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 4;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = menuWidth / 4 - ButtonArmy.getButtonWidth() / 2;
      int numOfUnits = 0;

      for (int i = 0; i < armyLeft.iArmyRegimentSize; i++) {
         numOfUnits += armyLeft.lArmyRegiment.get(i).num;
      }

      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonYPadding, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true) {
            @Override
            public void actionElement() {
               InGame_Disband.this.setVisible(false);
            }
         }
      );
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonReorganize(Game.lang.get("Army"), "" + numOfUnits, 0, buttonY, menuWidth / 2) {
            @Override
            public void actionElement() {
               for (int i = InGame_Disband.armyLeft.iArmyRegimentSize - 1; i >= 0; i--) {
                  InGame_Disband.armyRight.addRegiment(InGame_Disband.armyLeft.lArmyRegiment.get(i));
                  InGame_Disband.armyLeft.removeRegiment(i);
               }

               Game.menuManager.rebuildInGame_DisbandUnits();
               InGame_Disband.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               int numOfUnitsx = 0;

               for (int i = 0; i < InGame_Disband.armyLeft.iArmyRegimentSize; i++) {
                  numOfUnitsx += InGame_Disband.armyLeft.lArmyRegiment.get(i).num;
               }

               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("DisbandUnits") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + numOfUnitsx, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getPrecision2(Game.getManpowerRecoveryFromADisbandedArmy(Game.player.iCivID) * 100.0F, 10) + "%",
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_DISBAND, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < armyLeft.iArmyRegimentSize; i++) {
         menuElements.add(
            new ButtonArmy(
               "" + armyLeft.lArmyRegiment.get(i).num,
               1,
               armyLeft.civID,
               buttonX,
               buttonY,
               armyLeft.lArmyRegiment.get(i).uID,
               armyLeft.lArmyRegiment.get(i).aID,
               i,
               -1
            ) {
               @Override
               public void actionElement() {
                  InGame_Disband.armyRight.addRegiment(InGame_Disband.armyLeft.lArmyRegiment.get(this.getCurrent()));
                  InGame_Disband.armyLeft.removeRegiment(this.getCurrent());
                  Game.menuManager.rebuildInGame_DisbandUnits();
                  InGame_Disband.lTime = 0L;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      buttonX = menuWidth / 2 + menuWidth / 4 - ButtonArmy.getButtonWidth() / 2;
      numOfUnits = 0;

      for (int i = 0; i < armyRight.iArmyRegimentSize; i++) {
         numOfUnits += armyRight.lArmyRegiment.get(i).num;
      }

      menuElements.add(
         new ButtonGame(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonYPadding,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               if (InGame_Disband.armyRight.iArmyRegimentSize > 0) {
                  if (!Game.getProvince(InGame_Disband.armyLeft.provinceID)
                     .disbandRegiment(InGame_Disband.armyLeft.key, InGame_Disband.armyRight.lArmyRegiment)) {
                     Game.menuManager.addToast_Error("ArmyNotFound");
                  } else if (InGame_Disband.armyLeft.key != null) {
                     Game.setActiveArmy(InGame_Disband.armyLeft.provinceID, InGame_Disband.armyLeft.key);
                  } else {
                     Game.clearActiveArmy();
                  }

                  InGame_Disband.this.setVisible(false);
                  if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
                     Game.menuManager.rebuildInGame_ProvinceArmy();
                  }

                  if (Game.menuManager.getVisibleInGame_Generals()) {
                     Game.menuManager.rebuildInGame_Generals();
                     Game.menuManager.setVisibleInGame_Generals(true);
                     InGame_Generals.lTime = 0L;
                  }

                  if (Game.menuManager.getVisibleInGame_Armies()) {
                     Game.menuManager.rebuildInGame_Armies(false, false);
                     Game.menuManager.setVisibleInGame_Armies(true);
                     InGame_Armies.lTime = 0L;
                  }
               }
            }

            @Override
            public boolean getClickable() {
               return super.getClickable() && InGame_Disband.armyRight.iArmyRegimentSize > 0;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               int numOfUnits = 0;

               for (int i = 0; i < InGame_Disband.armyLeft.iArmyRegimentSize; i++) {
                  numOfUnits += InGame_Disband.armyLeft.lArmyRegiment.get(i).num;
               }

               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ", CFG.FONT_BOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(Game.getManpowerRecoveryFromADisbandedArmy(Game.player.iCivID) * 100.0F, 10) + "%",
                     CFG.FONT_BOLD,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER_DISBAND, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonReorganize(Game.lang.get("Disband"), "" + numOfUnits, menuWidth / 2, buttonY, menuWidth / 2) {
         @Override
         protected Color getColor(boolean isActive) {
            return Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
         }

         @Override
         public void actionElement() {
            for (int i = InGame_Disband.armyRight.iArmyRegimentSize - 1; i >= 0; i--) {
               InGame_Disband.armyLeft.addRegiment(InGame_Disband.armyRight.lArmyRegiment.get(i));
               InGame_Disband.armyRight.removeRegiment(i);
            }

            Game.menuManager.rebuildInGame_DisbandUnits();
            InGame_Disband.lTime = 0L;
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            int numOfUnits = 0;

            for (int i = 0; i < InGame_Disband.armyRight.iArmyRegimentSize; i++) {
               numOfUnits += InGame_Disband.armyRight.lArmyRegiment.get(i).num;
            }

            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Cancel") + ": "));
            nData.add(new MenuElement_HoverElement_Type_TextTitle("" + numOfUnits, Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < armyRight.iArmyRegimentSize; i++) {
         menuElements.add(
            new ButtonArmy(
               "" + armyRight.lArmyRegiment.get(i).num,
               1,
               armyRight.civID,
               buttonX,
               buttonY,
               armyRight.lArmyRegiment.get(i).uID,
               armyRight.lArmyRegiment.get(i).aID,
               i,
               -1
            ) {
               @Override
               public void actionElement() {
                  InGame_Disband.armyLeft.addRegiment(InGame_Disband.armyRight.lArmyRegiment.get(this.getCurrent()));
                  InGame_Disband.armyRight.removeRegiment(this.getCurrent());
                  Game.menuManager.rebuildInGame_DisbandUnits();
                  InGame_Disband.lTime = 0L;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      buttonY += buttonYPadding;
      i = Math.min(Math.max(ImageManager.getImage(Images.recruitArmyOver).getHeight(), buttonY), CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG_FlagCenter("", Game.getProvince(armyLeft.provinceID).getProvinceName(), false, false, Images.title1Red) {
         @Override
         public int getFlagCivID() {
            return InGame_Disband.armyLeft.civID;
         }

         @Override
         public long getTime() {
            return InGame_Disband.lTime;
         }
      }, menuX, menuY, menuWidth, i, menuElements, false, true);
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
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + this.getWidth() / 2 + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() / 2, this.getHeight());
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() / 2,
            this.getHeight(),
            true,
            false
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("DisbandUnits"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      if (restartAnimation) {
         lTime = CFG.currentTimeMillis;
      }
   }

   public static void setArmyLeft(ArmyDivision nArmyLeft) {
      armyLeft = null;
      armyRight = null;
      List<ArmyRegiment> tempArmyRegiment = new ArrayList<>();
      int i = 0;

      for (int iSize = nArmyLeft.lArmyRegiment.size(); i < iSize; i++) {
         tempArmyRegiment.add(nArmyLeft.lArmyRegiment.get(i));
      }

      armyLeft = new ArmyDivision(nArmyLeft.civID, nArmyLeft.provinceID, tempArmyRegiment);
      armyLeft.key = nArmyLeft.key;
      armyRight = new ArmyDivision(nArmyLeft.civID, nArmyLeft.provinceID, new ArrayList<>());
   }
}
