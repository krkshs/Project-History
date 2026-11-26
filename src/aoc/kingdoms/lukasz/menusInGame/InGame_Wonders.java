package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonWonder2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonWonderProvince;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle_General;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Wonders extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_Wonders() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      List<Integer> wondersL = new ArrayList<>();
      List<Integer> provinceID = new ArrayList<>();
      int iCivID = Game.player.iCivID;

      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).wonderID >= 0
            && !Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getWonderBuilt()
            && Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).wonderConstruction == null) {
            wondersL.add(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).wonderID);
            provinceID.add(Game.getCiv(iCivID).getProvinceID(i));
         }
      }

      while (wondersL.size() > 0) {
         int toAddID = 0;

         for (int o = 1; o < wondersL.size(); o++) {
            if (CFG.compareAlphabetic_TwoString(WondersManager.wonders.get(wondersL.get(o)).Name, WondersManager.wonders.get(wondersL.get(toAddID)).Name)) {
               toAddID = o;
            }
         }

         menuElements.add(new ButtonWonder2(paddingLeft, buttonY + CFG.PADDING, wondersL.get(toAddID), provinceID.get(toAddID)) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_CurrentSituation(false);
               InGame_Wonder.iProvinceID = this.iProvinceID;
               Game.menuManager.rebuildInGame_Wonder();
            }
         });
         int buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
         menuElements.add(
            new Text_StaticBG_RulerTitle_General(
               WondersManager.wonders.get(wondersL.get(toAddID)).Name,
               buttonX,
               buttonY,
               menuWidth - buttonX - paddingLeft,
               ButtonWonder2.getButtonHeight(),
               provinceID.get(toAddID),
               wondersL.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = ButtonWonderProvince.getHoverWonder(this.iProvinceID, this.iCivID, false);
               }

               @Override
               public void actionElement() {
                  Game.menuManager.setVisibleInGame_CurrentSituation(false);
                  InGame_Wonder.iProvinceID = this.iProvinceID;
                  Game.menuManager.rebuildInGame_Wonder();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         wondersL.remove(toAddID);
         provinceID.remove(toAddID);
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG("", false, false, Images.title1Red) {
         @Override
         public long getTime() {
            return InGame_Wonders.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
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
      ImageManager.getImage(Images.newGameOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.newGameOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.newGameOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Wonders"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
