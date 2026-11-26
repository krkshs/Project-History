package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadSavedGameManager;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.Button_LoadGame;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.menus.Menu_LoadGames_List;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_SaveGame extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String sSaveName = "";

   public InGame_SaveGame(boolean visible) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int buttonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      sSaveName = ""
         + Game.getCiv(Game.player.iCivID).getCivName().replace(" ", "_").replace(":", "_").replace(";", "_").replace(",", "_").replace(".", "_")
         + "_"
         + Game_Calendar.getCurrentDate_Simple().replace(" ", "_").replace(":", "_").replace(";", "_").replace(",", "_").replace(".", "_");
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("SaveName") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return InGame_SaveGame.sSaveName + Keyboard.getKeyboardVerticalLine();
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SAVE_NAME, InGame_SaveGame.sSaveName);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_SaveGame(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("SaveTheGame"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_SaveGame(false);
               if (InGame_SaveGame.sSaveName.length() > 0) {
                  SaveGameManager.saveKey = InGame_SaveGame.sSaveName;
               } else {
                  SaveGameManager.saveKey = "" + CFG.currentTimeMillis;
               }

               SaveGameManager.saveKey = SaveGameManager.saveKey.replace(" ", "_").replace(":", "_").replace(";", "_").replace(",", "_").replace(".", "_");
               Game.menuManager.setViewIDWithoutAnimation(View.LOAD_SAVE_GAME);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      try {
         Menu_LoadGames_List.disposeData();
         FileHandle file;
         if (FileManager.IS_MAC) {
            file = Gdx.files.external("saves/" + Game.map.getFile_ActiveMap_Path() + "AoH.txt");
         } else if (CFG.readLocalFiles()) {
            file = Gdx.files.local("saves/" + Game.map.getFile_ActiveMap_Path() + "AoH.txt");
         } else {
            file = Gdx.files.internal("saves/" + Game.map.getFile_ActiveMap_Path() + "AoH.txt");
         }

         if (file.exists()) {
            String[] tempTags = file.readString().split(";");
            List<SaveGameManager.SaveDetails> tempSaveDetails = new ArrayList<>();
            List<String> tempSaveKey = new ArrayList<>();

            for (int i = 0; i < tempTags.length; i++) {
               SaveGameManager.SaveDetails readSD = LoadSavedGameManager.loadSave_Details(tempTags[i]);
               if (readSD != null) {
                  tempSaveDetails.add(readSD);
                  tempSaveKey.add(tempTags[i]);
               }
            }

            while (tempSaveDetails.size() > 0) {
               int bestID = 0;

               for (int ix = tempSaveDetails.size() - 1; ix > 0; ix--) {
                  if (tempSaveDetails.get(ix).time > tempSaveDetails.get(bestID).time) {
                     bestID = ix;
                  }
               }

               Menu_LoadGames_List.savedGames.add(tempSaveDetails.get(bestID));
               Menu_LoadGames_List.savedGamesKey.add(tempSaveKey.get(bestID));
               tempSaveDetails.remove(bestID);
               tempSaveKey.remove(bestID);
            }

            int ixx = 0;

            for (int iSize = Menu_LoadGames_List.savedGames.size(); ixx < iSize; ixx++) {
               Menu_LoadGames_List.loadFlag(Menu_LoadGames_List.savedGames.get(ixx).sCivTag);
            }

            ixx = 0;

            for (int iSize = Menu_LoadGames_List.savedGames.size(); ixx < iSize; ixx++) {
               menuElements.add(
                  new Button_LoadGame(
                     Game.lang.getCiv(Menu_LoadGames_List.savedGames.get(ixx).sCivTag),
                     ""
                        + Menu_LoadGames_List.savedGames.get(ixx).iDay
                        + " "
                        + Game_Calendar.getMonthName(Menu_LoadGames_List.savedGames.get(ixx).iMonth)
                        + " "
                        + Menu_LoadGames_List.savedGames.get(ixx).iYear,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft * 2,
                     ixx
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SaveTheGame"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.save, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements, true);
                     }

                     @Override
                     public void actionElement() {
                        InGame_SaveGame.sSaveName = Menu_LoadGames_List.savedGamesKey.get(this.getCurrent());
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }
         }
      } catch (Exception var17) {
         CFG.exceptionStack(var17);
      }

      buttonY = 0;
      int ixx = 0;

      for (int iSize = menuElements.size(); ixx < iSize; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING * 2;
         }
      }

      buttonY = Math.max(buttonY, CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT * 4);
      ixx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixx)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("SaveTheGame"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_SaveGame.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, ixx, menuElements, visible, true);
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
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
   }
}
