package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menus.NewGame.NewGame;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_Government;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_AgeOfChaos extends Menu {
   public InGame_AgeOfChaos() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.title600).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = buttonYPadding * 2;
      int buttonX = paddingLeft;
      int maxIconW = ImageManager.getImage(Images.relations).getWidth() + CFG.PADDING * 2;

      for (int i = 0; i < Game.AGE_OF_CHAOS_CIVS_LIST.size(); i++) {
         buttonY += CFG.PADDING;
         menuElements.add(new ButtonFlag(Game.AGE_OF_CHAOS_CIVS_LIST.get(i), buttonX, buttonY, true) {
            @Override
            public void actionElement() {
               InGame_AgeOfChaos.this.actionButton(this.getCurrent());
            }

            @Override
            public int getFlagCivID() {
               return this.getCurrent();
            }
         });
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         int bHeight = (ButtonFlag.getButtonHeight() - CFG.PADDING) / 2;
         menuElements.add(
            new Text_StaticBG_RulerTitle(
               Game.getCiv(Game.AGE_OF_CHAOS_CIVS_LIST.get(i)).getCivName(), buttonX, buttonY, menuWidth - buttonX - paddingLeft2, bHeight
            ) {
               int id;

               @Override
               public int getCurrent() {
                  return this.id;
               }

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               public void actionElement() {
                  InGame_AgeOfChaos.this.actionButton(this.id);
               }

               @Override
               public void actionElementPPM() {
                  try {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                  } catch (Exception var2) {
                  }
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(Game.AGE_OF_CHAOS_CIVS_LIST.get(i));
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("Provinces") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(Game.AGE_OF_CHAOS_CIVS_LIST.get(i)).getNumOfProvinces()),
               Images.provinces,
               buttonX,
               buttonY + bHeight + CFG.PADDING,
               menuWidth - buttonX - paddingLeft2,
               bHeight,
               maxIconW
            ) {
               int id;

               @Override
               public int getCurrent() {
                  return this.id;
               }

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               public void actionElement() {
                  InGame_AgeOfChaos.this.actionButton(this.id);
               }

               @Override
               public void actionElementPPM() {
                  try {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                  } catch (Exception var2) {
                  }
               }

               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_GOLD;
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(Game.AGE_OF_CHAOS_CIVS_LIST.get(i));
         buttonX = paddingLeft2;
         buttonY += ButtonFlag.getButtonHeight() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
               paddingLeft,
               buttonY - (ButtonFlag.getButtonHeight() + CFG.PADDING * 2),
               menuWidth - paddingLeft * 2,
               ButtonFlag.getButtonHeight() + CFG.PADDING * 2
            )
         );
         buttonY += CFG.PADDING;
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - CFG.GAME_HEIGHT / 8 - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("AgeOfChaos"), true, false, Images.title600),
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         CFG.GAME_HEIGHT / 8,
         menuWidth,
         i,
         menuElements,
         true,
         false
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
      Images.gradientXY.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT * 2, false, true);
      Images.gradientXY.draw(oSB, iTranslateX, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 2 + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT * 2);
      oSB.setColor(Color.WHITE);
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
   }

   public void actionButton(int civID) {
      Game.player.iCivID = civID;
      NewGame.clearPlayerData();

      try {
         Game.player.currSituation.updateCurrentSituation();
         ProvinceDrawArmy.updateArmyImgID();
         Game.menuManager.rebuildInGame_Right();
         Game.menuManager.rebuildInGame_Wars();
         Game.menuManager.rebuildInGame_Messages();
         Game.menuManager.rebuildInGame_Notifications();
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      Game.menuManager.setViewIDWithoutAnimation(View.IN_GAME);
      Game.addSimpleTask(new Game.SimpleTask("Game.player.loadFormableCivs") {
         @Override
         public void update() {
            Game.player.loadFormableCivs();
         }
      });

      try {
         ProvinceDrawArmy.updateArmyImgID();
      } catch (Exception var7) {
      }

      InGame_Info.iCivID = civID;
      InGame_Info.iCivID2 = civID;
      Game.menuManager.rebuildInGame_Info(Game.lang.get("AgeOfChaos"), Game.getCiv(civID).getCivName());
      InGame_Info.imgID = Images.infoCrown;
      Game.menuManager.setVisibleInGame_CurrentSituation(false);
      InGame_Court_Government.reloadFlags = true;

      try {
         Game.player.fog.buildFogOfWar(Game.player.iCivID);
         if (GameValues.fog.HIDE_ARMIES) {
            NewGame.updateArmiesText();
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      if (!Game.menuManager.getVisibleInGame_Court()) {
         InGame.action1();
      }

      try {
         Game.mapCoords.centerToProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
      } catch (Exception var5) {
      }

      try {
         NewGame.hideMenus();
      } catch (Exception var4) {
      }

      try {
         Game.menuManager.hideInGameMenus();
      } catch (Exception var3) {
      }

      Game.clearActiveArmy();
      Game.regroupArmy_Clear();
      Game.invasionArmy_Clear();
      Game.invasionArmyMode = false;
      Game.regroupArmyMode = false;
      Game.addSimpleTask(new Game.SimpleTask("rebuildSelectCivilization") {
         @Override
         public void update() {
            Game.menuManager.rebuildInGame_Right();
            Game.menuManager.rebuildInGame_Messages();
            Game.menuManager.rebuildInGame_Wars();
         }
      });
      Game.menuManager.addToastPositive(Game.lang.get("Civilization") + ": ", Game.getCiv(Game.player.iCivID).getCivName(), Images.v);
   }
}
