package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadSavedGameManager;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.Button_LoadGame;
import aoc.kingdoms.lukasz.menu_element.button.Button_LoadGameX;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Menu_LoadGames_List extends Menu {
   public static List<Image> lFlags = new ArrayList<>();
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static List<SaveGameManager.SaveDetails> savedGames = new ArrayList<>();
   public static List<String> savedGamesKey = new ArrayList<>();

   public Menu_LoadGames_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING * 2;
      menuElements.add(new ButtonGame(Game.lang.get("Back"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.MAINMENU);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      try {
         disposeData();
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

               savedGames.add(tempSaveDetails.get(bestID));
               savedGamesKey.add(tempSaveKey.get(bestID));
               tempSaveDetails.remove(bestID);
               tempSaveKey.remove(bestID);
            }

            int ixx = 0;

            for (int iSize = savedGames.size(); ixx < iSize; ixx++) {
               loadFlag(savedGames.get(ixx).sCivTag);
            }

            ixx = 0;

            for (int iSize = savedGames.size(); ixx < iSize; ixx++) {
               menuElements.add(
                  new Button_LoadGame(
                     Game.lang.getCiv(savedGames.get(ixx).sCivTag),
                     ""
                        + savedGames.get(ixx).iDay
                        + " "
                        + Game_Calendar.getMonthName(savedGames.get(ixx).iMonth)
                        + " "
                        + Game.gameAges.getYear(savedGames.get(ixx).iYear),
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.BUTTON_WIDTH,
                     ixx
                  ) {
                     @Override
                     public void actionElement() {
                        LoadSavedGameManager.key = Menu_LoadGames_List.savedGamesKey.get(this.getCurrent());
                        Game.menuManager.setViewIDWithoutAnimation(View.LOAD_SAVED_GAME);
                     }
                  }
               );
               menuElements.add(
                  new Button_LoadGameX(
                     "X", menuWidth - paddingLeft - CFG.BUTTON_WIDTH, buttonY, CFG.BUTTON_WIDTH, menuElements.get(menuElements.size() - 1).getHeight(), ixx
                  ) {
                     @Override
                     public void actionElement() {
                        SaveGameManager.deleteSavedGameKey = Menu_LoadGames_List.savedGamesKey.get(this.getCurrent());
                        Dialog.setDialogType(Dialog.DialogType.DELETE_SAVE);
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }
         }
      } catch (Exception var14) {
         CFG.exceptionStack(var14);
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
      this.initMenu(new MenuTitleIMG(Game.lang.get("LoadGame"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return Menu_LoadGames_List.lTime;
         }
      }, CFG.GAME_WIDTH / 10, menuY, menuWidth, ixx, menuElements, true, false);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(0.050980393F, 0.08627451F, 0.13333334F, 1.0F));
      InitGame.background
         .draw(
            oSB,
            iTranslateX + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2,
            iTranslateY + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2,
            InitGame.backgroundWidth,
            InitGame.backgroundHeight
         );
      oSB.setColor(Color.WHITE);
      oSB.setShader(Renderer.shaderAlpha);
      InitGame.background.getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.gradientHorizontal2)
         .draw(
            oSB,
            (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
            (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
            InitGame.backgroundWidth,
            InitGame.backgroundHeight
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
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
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible) {
         disposeData();
      }
   }

   public static void loadFlag(String sCivTag) {
      if (FileManager.loadFile("gfx/flagsXH/" + sCivTag + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + sCivTag + ".png")), TextureFilter.Linear));
      } else if (FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png")), TextureFilter.Linear));
      } else if (FileManager.loadFile("gfx/flagsH/" + sCivTag + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + sCivTag + ".png")), TextureFilter.Linear));
      } else if (FileManager.loadFile("gfx/flagsH/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png")), TextureFilter.Linear));
      } else if (FileManager.loadFile("gfx/flags/" + sCivTag + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + sCivTag + ".png")), TextureFilter.Nearest));
      } else if (FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png").exists()) {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(sCivTag) + ".png")), TextureFilter.Nearest));
      } else {
         lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/ran.png")), TextureFilter.Nearest));
      }
   }

   public static void disposeData() {
      savedGames.clear();
      savedGamesKey.clear();

      for (int i = 0; i < lFlags.size(); i++) {
         lFlags.get(i).getTexture().dispose();
      }

      lFlags.clear();
   }
}
