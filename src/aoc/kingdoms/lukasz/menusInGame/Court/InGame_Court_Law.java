package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_Law3;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.Laws.InGame_Laws;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Law extends Menu {
   public InGame_Court_Law() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;

      for (int i = 0; i < LawsManager.iLawsSize; i++) {
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get(LawsManager.laws.get(i).Title),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6,
               Game.getCiv(Game.player.iCivID).laws.get(i) + 1 + " / " + LawsManager.getAvailableLaws(Game.player.iCivID, i)
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int tempY = buttonY;
         menuElements.add(
            new Button_Law3(
               Game.lang.get(LawsManager.laws.get(i).Law[Game.getCiv(Game.player.iCivID).laws.get(i)]),
               Game.lang
                  .get(
                     LawsManager.laws.get(i).LawDesc != null
                        ? LawsManager.laws.get(i).LawDesc[Game.getCiv(Game.player.iCivID).laws.get(i)]
                        : LawsManager.laws.get(i).Law[Game.getCiv(Game.player.iCivID).laws.get(i)] + ".d"
                  ),
               paddingLeft + CFG.PADDING,
               buttonY,
               menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
               LawsManager.laws.get(i).ImageID[Game.getCiv(Game.player.iCivID).laws.get(i)],
               i,
               Game.getCiv(Game.player.iCivID).laws.get(i)
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 23 && InGame_Laws.lawID == this.getValue1()) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.rebuildInGame_Laws(this.getValue1());
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<MenuElement> bonuses = LawsManager.getLawBonuses(i, Game.getCiv(Game.player.iCivID).laws.get(i), paddingLeft + CFG.PADDING, menuWidth);
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

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("Laws"));
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }
}
