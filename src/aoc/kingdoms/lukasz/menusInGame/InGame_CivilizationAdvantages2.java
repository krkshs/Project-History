package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_Advantage2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyChoose;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_CivilizationAdvantages2 extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iMenuWidth = 0;

   public InGame_CivilizationAdvantages2() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title928).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop928).getWidth();
      iMenuWidth = menuWidth - Images.boxTitleBORDERWIDTH * 2;
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title928).getHeight();
      int buttonYPadding = CFG.PADDING + CFG.PADDING / 2;
      int buttonY = CFG.PADDING * 2;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      InGame_TechnologyChoose.IN_TECHNOLOGY_CHOOSE = false;
      new ArrayList();
      int buttonWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING * 4) / 3;
      menuElements.add(new Button_Advantage2(paddingLeft, buttonY, 0, 0, Game.lang.get("ArmyRecruitmentCost"), "-10%", Images.gold));
      menuElements.add(
         new Text_Desc2(
            Game.lang.get("AdditionalBuildingsInProvince"),
            paddingLeft + Button_Advantage2.getButtonHeight() + CFG.PADDING,
            buttonY,
            buttonWidth - Button_Advantage2.getButtonWidth() - CFG.PADDING
         )
      );
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
            paddingLeft - CFG.PADDING, buttonY - CFG.PADDING, buttonWidth + CFG.PADDING * 2, Button_Advantage2.getButtonHeight() + CFG.PADDING * 2
         )
      );
      int buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new Button_Advantage2(buttonX, buttonY, 0, 0, Game.lang.get("ArmyRecruitmentCost"), "-10%", Images.gold));
      menuElements.add(
         new Text_Desc2(
            Game.lang.get("AdditionalBuildingsInProvince"),
            buttonX + Button_Advantage2.getButtonHeight() + CFG.PADDING,
            buttonY,
            buttonWidth - Button_Advantage2.getButtonWidth() - CFG.PADDING
         )
      );
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
            buttonX - CFG.PADDING, buttonY - CFG.PADDING, buttonWidth + CFG.PADDING * 2, Button_Advantage2.getButtonHeight() + CFG.PADDING * 2
         )
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new Button_Advantage2(buttonX, buttonY, 0, 0, Game.lang.get("ArmyRecruitmentCost"), "-10%", Images.gold));
      menuElements.add(
         new Text_Desc2(
            Game.lang.get("AdditionalBuildingsInProvince"),
            buttonX + Button_Advantage2.getButtonHeight() + CFG.PADDING,
            buttonY,
            buttonWidth - Button_Advantage2.getButtonWidth() - CFG.PADDING
         )
      );
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
            buttonX - CFG.PADDING, buttonY - CFG.PADDING, buttonWidth + CFG.PADDING * 2, Button_Advantage2.getButtonHeight() + CFG.PADDING * 2
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_FlagCenter(
            Game.lang.get("CivilizationAdvantages"),
            Game.lang.get("AdvantagePoints") + ": " + Game.getCiv(Game.player.iCivID).getAdvantagePoints(),
            false,
            false,
            Images.title928
         ) {
            @Override
            public long getTime() {
               return InGame_CivilizationAdvantages2.lTime2;
            }

            @Override
            public int getFlagCivID() {
               return Game.player.iCivID;
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
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop928,
         Images.insideBot928
      );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      InGame_Civ.actionOnClose();
   }
}
