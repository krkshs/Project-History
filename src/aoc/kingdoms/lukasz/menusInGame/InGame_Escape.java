package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMainTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.menus.InitGame;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menus.Settings.Settings_Menu;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Escape extends Menu {
   protected static final int ANIMATION_TIME = 125;
   private long lTime = 0L;

   public InGame_Escape() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING + CFG.PADDING / 2;
      int buttonY = CFG.PADDING;
      int textPosX = CFG.PADDING * 4;
      menuElements.add(
         new ButtonMainTitle("", 0, -1, 0, 0, menuWidth, true) {
            @Override
            public void actionElement() {
               MenuManager.addClickAnimation(
                  new ClickAnimation(
                     this.getPosX() + this.getWidth() / 2 + InGame_Escape.this.getMenuPosX(),
                     this.getPosY() + this.getHeight() / 2 + InGame_Escape.this.getMenuPosY(),
                     this.getWidth(),
                     this.getHeight()
                  ) {
                     @Override
                     public Color getColor() {
                        return DiplomacyManager.COLOR_WAR;
                     }
                  }
               );
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = MainMenu.getHoverAbout_Short();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ReturnToGame"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setVisibleInGame_SaveGame(false);
            Game.menuManager.setVisibleInGame_Escape(false);
            Game.mapBG.updateActiveMapBGShader();
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("SaveTheGame"));
         }

         @Override
         public void actionElement() {
            if (Game.menuManager.getVisibleInGame_SaveGame()) {
               Game.menuManager.setVisibleInGame_SaveGame(false);
            } else {
               Game.menuManager.rebuildInGame_SaveGame();
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding + buttonYPadding;
      menuElements.add(new ButtonGame2(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Audio"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setVisibleInGame_SaveGame(false);
            Game.menuManager.setVisibleInGame_Escape(false);
            Game.mapBG.updateActiveMapBGShader();
            Game.menuManager.rebuildInGame_Audio();
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("GameOptions"));
         }

         @Override
         public void actionElement() {
            Settings_Menu.goBackToMenu = View.IN_GAME;
            Game.menuManager.setViewID(View.SETTINGS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(
            Game.lang.get("Take provinces from anyone in peace treaties"),
            1,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            true,
            CFG.BUTTON_HEIGHT,
            true
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Take provinces from anyone in peace treaties") + ": ",
                     GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES = !GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES;
            }

            @Override
            public boolean getCheckboxState() {
               return GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ExitToMainMenu"));
         }

         @Override
         public void actionElement() {
            Dialog.setDialogType(Dialog.DialogType.ESCAPE_TO_MAIN_MENU);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2);
      this.initMenu(null, CFG.GAME_WIDTH / 10, CFG.GAME_HEIGHT / 2 - (titleHeight + menuHeight) / 2, menuWidth, menuHeight, menuElements, false, false);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      try {
         float fAlpha;
         if (this.lTime + 125L >= CFG.currentTimeMillis) {
            fAlpha = (float)(CFG.currentTimeMillis - this.lTime) / 125.0F;
         } else {
            fAlpha = 1.0F;
         }

         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.05F * fAlpha));
         Images.pix.draw2(oSB, iTranslateX, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F * fAlpha));
         ImageManager.getImage(Images.gradientHorizontal3)
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F * fAlpha));
         oSB.setShader(Renderer.shaderAlpha);
         InitGame.background.getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         Images.gradientFull
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         ImageManager.getImage(Images.gradientHorizontal3)
            .draw(
               oSB,
               (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         if (Game.menuManager.getVisibleInGame_SaveGame()) {
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F * fAlpha));
            Images.pix.draw2(oSB, iTranslateX, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         }

         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.15F * fAlpha));
         ImageManager.getImage(Images.patt2).draw2(oSB, iTranslateX, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F * fAlpha));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, iTranslateX, 0, CFG.LEFT_MENU_WIDTH / 2, CFG.GAME_HEIGHT);
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, CFG.GAME_WIDTH - CFG.LEFT_MENU_WIDTH / 2 + iTranslateX, 0, CFG.LEFT_MENU_WIDTH / 2, CFG.GAME_HEIGHT, true, false);
         ImageManager.getImage(Images.gradientVertical).draw(oSB, iTranslateX, 0, CFG.GAME_WIDTH, CFG.LEFT_MENU_WIDTH / 2);
         ImageManager.getImage(Images.gradientVertical)
            .draw(oSB, iTranslateX, 0 + CFG.GAME_HEIGHT - CFG.LEFT_MENU_WIDTH / 2, CFG.GAME_WIDTH, CFG.LEFT_MENU_WIDTH / 2, false, true);
         oSB.setColor(MainMenu.sparksColors);
         MenuManager.sparksAnimation.draw2(oSB, iTranslateX, CFG.GAME_HEIGHT - Images.sparkHeight + iTranslateY, CFG.GAME_WIDTH, Images.sparkHeight);
         oSB.setColor(Color.WHITE);
         if (this.lTime + 125L >= CFG.currentTimeMillis) {
            iTranslateY = iTranslateY - this.getHeight() * 4 / 5 + (int)(this.getHeight() * 4 / 5 * fAlpha);
         }

         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING);
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
         super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      this.lTime = CFG.currentTimeMillis;
      if (!visible) {
         Game.menuManager.setVisibleInGame_SaveGame(false);
      }
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameEscape();
   }
}
