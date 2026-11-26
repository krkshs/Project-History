package aoc.kingdoms.lukasz.menusInGame.Court.ChangeGovernmentReligion;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter2;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.Button_Capital;
import aoc.kingdoms.lukasz.menu_element.button.Button_CapitalMove;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ChangeReligion2 extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int toReligionID = 0;

   public InGame_ChangeReligion2() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING;
      int statH = (Button_CapitalMove.getButtonHeight() - CFG.PADDING * 2) / 3;
      int buttonX = paddingLeft + Button_Capital.getButtonHeight() + CFG.PADDING;
      buttonY += CFG.PADDING;
      int buttonBGY = buttonY - CFG.PADDING;
      List<MenuElement> nElementsBonuses = Game.religionManager.getMenuElements(toReligionID, buttonX, buttonY, menuWidth - paddingLeft - buttonX, statH);
      if (nElementsBonuses.isEmpty()) {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("None"), CFG.FONT_REGULAR, -1, buttonX, buttonY, menuWidth - paddingLeft - buttonX, Button_Capital.getButtonHeight() * 3 / 5
            )
         );
      } else if (nElementsBonuses.size() == 1) {
         nElementsBonuses.get(0).setHeight(Button_Capital.getButtonHeight() * 3 / 5);
         menuElements.add(nElementsBonuses.get(0));
      } else {
         for (int i = 0; i < nElementsBonuses.size(); i++) {
            if (i > 2) {
               nElementsBonuses.get(i).setPosX(paddingLeft);
               nElementsBonuses.get(i).setWidth(menuWidth - paddingLeft * 2);
            }

            menuElements.add(nElementsBonuses.get(i));
         }
      }

      menuElements.add(new ButtonReligion2(toReligionID, paddingLeft, buttonY, Button_Capital.getButtonHeight(), Button_Capital.getButtonHeight() * 3 / 5) {
         @Override
         public void buildElementHover() {
            this.menuElementHover = Game.religionManager.getHoverReligion(this.religionID, Game.player.iCivID);
         }
      });
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         buttonY = Math.max(buttonY, menuElements.get(i).getPosY() + menuElements.get(i).getHeight());
      }

      buttonY += CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
            CFG.PADDING + Images.boxTitleBORDERWIDTH, buttonBGY, menuWidth - (CFG.PADDING + Images.boxTitleBORDERWIDTH) * 2, buttonY - buttonBGY
         )
      );
      buttonY += CFG.PADDING;
      i = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      int costW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(GameValues.religion.CHANGE_RELIGION_COST, 10),
            Images.gold,
            paddingLeft,
            buttonY,
            costW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            i
         )
      );
      menuElements.add(
         new TextBonus(
            Game.lang.get("LegacyPoints") + ": ",
            "" + CFG.getPrecision2(GameValues.religion.CHANGE_RELIGION_COST_LEGACY, 10),
            Images.legacy,
            paddingLeft + costW + CFG.PADDING,
            buttonY,
            costW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            i
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_ChangeReligion2.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = Game.religionManager.getHoverReligion(InGame_ChangeReligion2.toReligionID, Game.player.iCivID);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonY = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (buttonY < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING * 2;
         }
      }

      menuY = CFG.GAME_HEIGHT / 8;
      ix = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ix)));
      this.initMenu(
         new MenuTitleIMG_FlagCenter2(
            Game.getCiv(Game.player.iCivID).getCivName() + ": " + Game.religionManager.getReligion(toReligionID).Name, true, false, Images.title500
         ) {
            @Override
            public long getTime() {
               return InGame_ChangeReligion2.lTime;
            }

            @Override
            public int getFlagCivID() {
               return Game.player.iCivID;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         menuY,
         menuWidth,
         ix,
         menuElements,
         false,
         true
      );
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
      if (Game.getCiv(Game.player.iCivID).getReligionID() == toReligionID) {
         Game.menuManager
            .addToast_Error(
               Game.religionManager.getReligion(Game.getCiv(Game.player.iCivID).getReligionID()).Name
                  + " == "
                  + Game.religionManager.getReligion(toReligionID).Name
            );
      } else if (Game.getCiv(Game.player.iCivID).fGold < GameValues.religion.CHANGE_RELIGION_COST) {
         Game.menuManager
            .addToastInsufficient(Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(GameValues.religion.CHANGE_RELIGION_COST, 10), Images.gold);
      } else if (Game.getCiv(Game.player.iCivID).fLegacy < GameValues.religion.CHANGE_RELIGION_COST_LEGACY) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientLegacy") + ": ", CFG.getPrecision2(GameValues.religion.CHANGE_RELIGION_COST_LEGACY, 10), Images.legacy
            );
      } else {
         Civilization var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fGold = var10000.fGold - GameValues.religion.CHANGE_RELIGION_COST;
         var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fLegacy = var10000.fLegacy - GameValues.religion.CHANGE_RELIGION_COST_LEGACY;
         Game.getCiv(Game.player.iCivID).setReligionID_UpdateBonuses(toReligionID);
         Game.getCiv(Game.player.iCivID).updateCivStability();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.player.iCivID);
         if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions.iGovernmentID) {
            Game.menuManager.rebuildInGame_Government();
            Game.menuManager.setVisibleInGame_Court(true);
            InGame_Court.lTime = 0L;
         }

         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = Game.player.iCivID;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("Religion"), Game.religionManager.getReligion(toReligionID).Name);
         InGame_Info.imgID = Images.infoCrown;
         Game.menuManager.setVisibleInGame_PopUp(false);
      }
   }
}
