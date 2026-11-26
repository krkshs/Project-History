package aoc.kingdoms.lukasz.menusInGame.Laws;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.Button_Law3;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_LawReform extends Menu {
   public static int lawID = 0;
   public static int lawID2 = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_LawReform(int nLawID, int lawID2) {
      List<MenuElement> menuElements = new ArrayList<>();
      lawID = nLawID;
      InGame_LawReform.lawID2 = lawID2;
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonX = paddingLeft;
      int tempY = buttonY;
      menuElements.add(
         new Button_Law3(
            Game.lang.get(LawsManager.laws.get(lawID).Law[lawID2]),
            Game.lang
               .get(LawsManager.laws.get(lawID).LawDesc != null ? LawsManager.laws.get(lawID).LawDesc[lawID2] : LawsManager.laws.get(lawID).Law[lawID2] + ".d"),
            paddingLeft + CFG.PADDING,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
            LawsManager.laws.get(lawID).ImageID[lawID2],
            lawID,
            lawID2
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Laws(this.getValue1());
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<MenuElement> bonuses = LawsManager.getLawBonuses(lawID, lawID2, paddingLeft + CFG.PADDING, menuWidth);
      if (!bonuses.isEmpty()) {
         for (int a = 0; a < bonuses.size(); a++) {
            bonuses.get(a).setPosY(buttonY);
            menuElements.add(bonuses.get(a));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         tempY = buttonY - tempY;
         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft, buttonY - tempY, menuWidth - paddingLeft * 2, tempY));
         buttonY += CFG.PADDING;
      }

      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD, 100),
            Images.gold,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         )
      );
      menuElements.add(
         new TextBonus(
            Game.lang.get("LegacyPoints") + ": ",
            "" + CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS, 100),
            Images.legacy,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("AdoptReform"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_LawReform.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = LawsManager.getHover(InGame_LawReform.lawID, InGame_LawReform.lawID2);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, tMenuHeight)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.lang.get(LawsManager.laws.get(lawID).Title), Game.lang.get("Law"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_LawReform.lTime;
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
      if (Game.getCiv(Game.player.iCivID).fGold < GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_GOLD, 100), Images.gold
            );
      } else if (Game.getCiv(Game.player.iCivID).fLegacy < GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientLegacy") + ": ", CFG.getPrecision2((float)GameValues.laws.LAW_ADAPT_REFORM_COST_LEGACY_POINTS, 100), Images.legacy
            );
      } else if (LawsManager.adoptReform(Game.player.iCivID, lawID, lawID2)) {
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("ReformAdopted"), Game.lang.get(LawsManager.laws.get(lawID).Law[lawID2]));
         InGame_Info.imgID = Images.infoCrown;
         if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions.iLawID) {
            Game.menuManager.rebuildInGame_LawsCourt();
            Game.menuManager.setVisibleInGame_Court(true);
            InGame_Court.lTime = 0L;
         }
      }

      Game.menuManager.setVisibleInGame_PopUp(false);
   }
}
