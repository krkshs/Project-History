package aoc.kingdoms.lukasz.menusInGame.Laws;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_Law3;
import aoc.kingdoms.lukasz.menu_element.button.Button_Law3_Green;
import aoc.kingdoms.lukasz.menu_element.button.Button_Law3_Red;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Laws extends Menu {
   public static int lawID = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_Laws(int nLawID) {
      List<MenuElement> menuElements = new ArrayList<>();
      lawID = nLawID;
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING * 2;

      for (int i = 0; i < LawsManager.laws.get(lawID).Law.length; i++) {
         int tempY = buttonY;
         if (Game.getCiv(Game.player.iCivID).laws.get(lawID) == i) {
            menuElements.add(
               new Button_Law3_Green(
                  Game.lang.get(LawsManager.laws.get(lawID).Law[i]),
                  Game.lang
                     .get(LawsManager.laws.get(lawID).LawDesc != null ? LawsManager.laws.get(lawID).LawDesc[i] : LawsManager.laws.get(lawID).Law[i] + ".d"),
                  paddingLeft + CFG.PADDING,
                  buttonY,
                  menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                  LawsManager.laws.get(lawID).ImageID[i],
                  lawID,
                  i
               )
            );
         } else if (LawsManager.laws.get(lawID).RequiredTechID[i] >= 0
            && !Game.getCiv(Game.player.iCivID).getTechResearched(LawsManager.laws.get(lawID).RequiredTechID[i])) {
            menuElements.add(
               new Button_Law3_Red(
                  Game.lang.get(LawsManager.laws.get(lawID).Law[i]),
                  Game.lang
                     .get(LawsManager.laws.get(lawID).LawDesc != null ? LawsManager.laws.get(lawID).LawDesc[i] : LawsManager.laws.get(lawID).Law[i] + ".d"),
                  paddingLeft + CFG.PADDING,
                  buttonY,
                  menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                  LawsManager.laws.get(lawID).ImageID[i],
                  lawID,
                  i
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                        Game.menuManager.setVisibleInGame_TechnologyTree(false);
                     } else {
                        Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                        InGame_TechnologyTree.centerToTechID = LawsManager.laws.get(this.getValue1()).RequiredTechID[this.getValue2()];
                        Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                     }
                  }
               }
            );
         } else if (LawsManager.laws.get(lawID).RequiredGovernmentID != null
            && LawsManager.laws.get(lawID).RequiredGovernmentID[i] >= 0
            && LawsManager.laws.get(lawID).RequiredGovernmentID[i] != Game.getCiv(Game.player.iCivID).getIdeologyID()) {
            menuElements.add(
               new Button_Law3_Red(
                  Game.lang.get(LawsManager.laws.get(lawID).Law[i]),
                  Game.lang
                     .get(LawsManager.laws.get(lawID).LawDesc != null ? LawsManager.laws.get(lawID).LawDesc[i] : LawsManager.laws.get(lawID).Law[i] + ".d"),
                  paddingLeft + CFG.PADDING,
                  buttonY,
                  menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                  LawsManager.laws.get(lawID).ImageID[i],
                  lawID,
                  i
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager
                        .addToastNegative(
                           Game.lang.get("Government") + ": ",
                           Game.ideologiesManager.getIdeology(LawsManager.laws.get(this.getValue1()).RequiredGovernmentID[this.getValue2()]).Name,
                           Images.government
                        );
                  }
               }
            );
         } else {
            menuElements.add(
               new Button_Law3(
                  Game.lang.get(LawsManager.laws.get(lawID).Law[i]),
                  Game.lang
                     .get(LawsManager.laws.get(lawID).LawDesc != null ? LawsManager.laws.get(lawID).LawDesc[i] : LawsManager.laws.get(lawID).Law[i] + ".d"),
                  paddingLeft + CFG.PADDING,
                  buttonY,
                  menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                  LawsManager.laws.get(lawID).ImageID[i],
                  lawID,
                  i
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.rebuildInGame_LawReform(this.getValue1(), this.getValue2());
                  }
               }
            );
         }

         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<MenuElement> bonuses = LawsManager.getLawBonuses(lawID, i, paddingLeft + CFG.PADDING, menuWidth);
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
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.lang.get(LawsManager.laws.get(lawID).Title), Game.lang.get("Law"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_Laws.lTime;
         }

         @Override
         public int getFlagCivID() {
            return Game.player.iCivID;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, i, menuElements, false, true);
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
}
