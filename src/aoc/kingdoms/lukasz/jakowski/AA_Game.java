package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamManager;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.LegacyManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.map.Map;
import aoc.kingdoms.lukasz.map.map.MapBG;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.HoverManager;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Renderer;
import aoc.kingdoms.lukasz.menus.InitGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.codedisaster.steamworks.SteamAPI;

public class AA_Game extends ApplicationAdapter {
   private Touch touch = new Touch();
   private Renderer renderer;

   @Override
   public void create() {
      FileManager.initLoadInterface();
      CFG.currentTimeMillis = System.currentTimeMillis();
      this.renderer = new Renderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

      try {
         Game.loadLowSettings();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      try {
         Game.loadSettings();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      this.initUIScale();
      if (CFG.isDesktop()) {
         SteamManager.init();
         SteamManager.loadSubscribedItems();
         SteamManager.userStats.requestCurrentStats();
      }

      Game.loadLanguage();
      Renderer.loadFont(Game.lang.get("font"), "A", Game.settingsManager.FONT_MAIN_SIZE);
      CFG.FONT_BOLD = Renderer.fontMain.size() - 1;
      this.initGame();
      this.initInput();
      CFG.isDesktop = Gdx.app.getType() == ApplicationType.Desktop;
      CFG.isAndroid = Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
      CFG.isiOS = Gdx.app.getType() == ApplicationType.iOS;
      ProvinceDraw.updateDrawProvinces();
      CFG.UIScale = CFG.getUIScale();
      Game.mapScale.initDefinedScales();
   }

   public void initUIScale() {
      if (Game.settingsManager.UI_SCALE < 0) {
         if (CFG.isAndroid()) {
            CFG.XHDPI = Gdx.graphics.getPpiX() >= 300.0F || CFG.GAME_WIDTH >= 1200 || CFG.GAME_HEIGHT >= 1200;
            CFG.XXHDPI = Gdx.graphics.getPpiX() >= 380.0F || CFG.GAME_WIDTH >= 1800 || CFG.GAME_HEIGHT >= 1800;
         } else if (CFG.isDesktop()) {
            CFG.XHDPI = CFG.GAME_WIDTH >= 2400;
         }
      } else if (Game.settingsManager.UI_SCALE == 1) {
         CFG.XHDPI = true;
         CFG.XXHDPI = false;
      } else if (Game.settingsManager.UI_SCALE == 2) {
         CFG.XHDPI = false;
         CFG.XXHDPI = true;
      } else {
         CFG.XHDPI = false;
         CFG.XXHDPI = false;
      }
   }

   protected final void initGame() {
      Game.map = new Map();
      Game.mapBG = new MapBG();
      Game.mapBG.iMapExtraScale = Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.ExtraMapScale;
      Game.mapBG.iMapScale = (int)(Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.DefaultMapScale * Game.mapBG.iMapExtraScale);
      Images.imageNotFound = ImageManager.addImage("gfx/imageNotFound.png");
      Images.buttonMenu = ImageManager.addImage("ui/" + CFG.getRescouresPath() + "buttons/" + "buttonMenu.png");
      Images.buttonMenuH = ImageManager.addImage("ui/" + CFG.getRescouresPath() + "buttons/" + "buttonMenuH.png");
      Images.btn_close = ImageManager.addImage("ui/" + CFG.getRescouresPath() + "buttons/" + "close.png");
      CFG.BUTTON_HEIGHT = ImageManager.getImage(Images.buttonMenu).getHeight();
      CFG.BUTTON_WIDTH = CFG.XXHDPI ? 160 : (CFG.XHDPI ? 120 : 90);
      GameValues.initGameValue();
      CFG.GUI_SCALE = 100.0F * CFG.BUTTON_HEIGHT / 68.0F / 100.0F;
      CFG.PADDING = (int)(GameValues.value.PADDING * (1.0F + (CFG.GUI_SCALE > 1.0F ? (CFG.GUI_SCALE - 1.0F) / 2.0F : 0.0F)));
      CFG.BUTTON_HEIGHT2 = (int)(CFG.BUTTON_HEIGHT2 * CFG.GUI_SCALE);
      CFG.BUTTON_HEIGHT3 = (int)(CFG.BUTTON_HEIGHT3 * CFG.GUI_SCALE);
      CFG.BUTTON_HEIGHT4 = (int)(CFG.BUTTON_HEIGHT4 * CFG.GUI_SCALE);
      CFG.LEFT_MENU_WIDTH = Math.max(CFG.LEFT_MENU_WIDTH, Math.min(CFG.LEFT_MENU_WIDTH, CFG.GAME_WIDTH / 4));
      CFG.LEFT_MENU_WIDTH = (int)(CFG.LEFT_MENU_WIDTH * CFG.GUI_SCALE);
      CFG.LEFT_MENU_WIDTH2 = Math.max(CFG.LEFT_MENU_WIDTH2, Math.min(CFG.LEFT_MENU_WIDTH2, CFG.GAME_WIDTH / 3));
      CFG.LEFT_MENU_WIDTH2 = (int)(CFG.LEFT_MENU_WIDTH2 * CFG.GUI_SCALE);
      initGame_LoadImages();
      Renderer.pieChartRenderer = new PieChart_Renderer();
      Game.soundsManager = new SoundsManager();
      Game.ambienceManager = new AmbienceManager();
      Game.hoverManager = new HoverManager();
      Game.menuManager = new MenuManager();
      Game.menuManager.setViewIDWithoutAnimation(View.INIT_GAME_MENU);
      Game.menuManager.initColorPicker();
      Game.animationManager = new AnimationManager();
      Game.animationManager.loadAnimations();
      Game.map.updateWorldMap();
   }

   private static final void initGame_LoadImages() {
      Images.pickerSV = ImageManager.addImage("ui/picker/sv.png", Format.RGBA8888, TextureFilter.Linear, TextureWrap.ClampToEdge);
      Images.pickerHUE = ImageManager.addImage("ui/picker/hue.png", Format.RGBA8888, TextureFilter.Linear, TextureWrap.ClampToEdge);
      Images.pickerSVPos = ImageManager.addImage("ui/picker/pos.png");
      Images.pickerEdge = ImageManager.addImage("ui/picker/edge.png");
      Images.scroll_position = ImageManager.addImage("ui/" + CFG.getRescouresPath() + "scroll/" + "position.png");
      Images.scroll_position_active = ImageManager.addImage("ui/" + CFG.getRescouresPath() + "scroll/" + "position_active.png");
      Images.patt = ImageManager.addImage("ui/patterns/0.png", Format.RGBA8888, TextureFilter.Nearest, TextureWrap.Repeat);
      Images.patt2 = ImageManager.addImage("ui/patterns/1.png", Format.RGBA8888, TextureFilter.Nearest, TextureWrap.Repeat);
      Images.patt3 = ImageManager.addImage("ui/patterns/2.png", Format.RGBA8888, TextureFilter.Nearest, TextureWrap.Repeat);
      Images.patt4 = ImageManager.addImage("ui/patterns/3.png", Format.RGBA8888, TextureFilter.Nearest, TextureWrap.Repeat);
      Images.pattOccupied = ImageManager.addImage("ui/patterns/4.png", Format.RGBA8888, TextureFilter.Nearest, TextureWrap.Repeat);
      Images.gradientVertical = ImageManager.addImage("ui/gradients/gradientVertical.png");
      Images.gradientHorizontal = ImageManager.addImage("ui/gradients/gradientHorizontal.png");
      Images.gradientHorizontal2 = ImageManager.addImage("ui/gradients/gradientHorizontal2.png");
      Images.gradientHorizontal3 = ImageManager.addImage("ui/gradients/gradientHorizontal3.png");
      Images.gradientFull = ImageManager.loadImage("ui/gradients/gradientFull.png", Format.RGBA8888, TextureFilter.Linear);
      Images.gradientFull2 = ImageManager.addImage("ui/gradients/gradientFull2.png");
      Images.gradientXY = ImageManager.loadImage("ui/gradients/gradientXY.png", Format.RGBA8888, TextureFilter.Linear);
      Images.logo2 = ImageManager.addImage("ui/aoh.png");
      Images.gradientXYVertical = ImageManager.addImage("ui/gradients/gradientXYVertical.png");
      Images.line_32_off1 = ImageManager.loadImage("ui/lines/line_32_off1.png", Format.RGBA8888, TextureFilter.Linear);
      Images.line_131 = ImageManager.addImage("ui/patterns/line_131.png", Format.RGBA8888, TextureFilter.Linear, TextureWrap.Repeat);
      Images.line_131_vertical = ImageManager.addImage("ui/patterns/line_131_vertical.png", Format.RGBA8888, TextureFilter.Linear, TextureWrap.Repeat);
      if (CFG.XXHDPI) {
         Images.logo = ImageManager.addImage("gfx/logo/gameLogo_XXH.png");
      } else if (CFG.XHDPI) {
         Images.logo = ImageManager.addImage("gfx/logo/gameLogo_XH.png");
      } else {
         Images.logo = ImageManager.addImage("gfx/logo/gameLogo.png");
      }
   }

   @Override
   public void render() {
      this.renderer.render();
   }

   @Override
   public void resize(int width, int height) {
      this.renderer.resize(width, height);
   }

   private void initInput() {
      Gdx.input.setInputProcessor(new InputAdapter() {
         @Override
         public boolean keyDown(int keycode) {
            return AA_KeyManager.keyDown(keycode);
         }

         @Override
         public boolean keyUp(int keycode) {
            return AA_KeyManager.keyUp(keycode);
         }

         @Override
         public boolean keyTyped(char character) {
            if (Keyboard.keyboardMode && character > 0) {
               if (character == 18 || character == '\b') {
                  Keyboard.keyboardAction.delete();
                  Game.hoverManager.resetHoverActive_Menu();
               } else if (character != '\r' && character != '\n' && character != '\t') {
                  Keyboard.keyboardAction.actionType("" + character);
                  Game.hoverManager.resetHoverActive_Menu();
               }
            }

            return false;
         }

         @Override
         public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Touch.buttonTouch = button;
            Touch.setMousePosXY(screenX, screenY);
            AA_Game.this.touch.actionDown(screenX, screenY, pointer, button);
            return true;
         }

         @Override
         public boolean touchDragged(int screenX, int screenY, int pointer) {
            try {
               if (!Game.menuManager.getInInitGameMenu() && !Game.menuManager.getInLoadScenario()) {
                  Game.mapScroll.setScrollPos(screenX, screenY);
                  Touch.setMousePosXY(screenX, screenY);
                  if (Gdx.input.isTouched(1) && pointer == 0) {
                     AA_Game.this.touch.actionMove(Gdx.input.getX(0), Gdx.input.getY(0), Gdx.input.getX(1), Gdx.input.getY(1));
                  } else {
                     AA_Game.this.touch.actionMove(screenX, screenY, pointer);
                  }

                  if (CFG.brushTool) {
                     AA_Game.this.touch.actionMove_Hover(screenX, screenY);
                  }
               }
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
            }

            return true;
         }

         @Override
         public boolean mouseMoved(int screenX, int screenY) {
            Touch.setMousePosXY(screenX, screenY);
            Game.mapEdgeMove.MouseMoved_EdgeMove(screenX, screenY);
            AA_Game.this.touch.actionMove_Hover(screenX, screenY);
            return true;
         }

         @Override
         public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Touch.setMousePosXY(screenX, screenY);
            AA_Game.this.touch.actionUp(screenX, screenY, pointer, button);
            return true;
         }

         @Override
         public boolean scrolled(float amountX, float amountY) {
            try {
               if (!Game.menuManager.getInInitGameMenu() && !Game.menuManager.getInLoadScenario()) {
                  return ScrollManager.scrolled((int)(amountX + amountY));
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }

            return true;
         }
      });
   }

   @Override
   public void dispose() {
      try {
         if (SteamManager.initSteam || CFG.isDesktop()) {
            SteamManager.steamUGC.dispose();
            SteamManager.steamUtils.dispose();
            SteamManager.userStats.dispose();
            SteamAPI.shutdown();
         }
      } catch (Exception var21) {
      }

      try {
         this.renderer.dispose();
      } catch (Exception var11) {
      }

      try {
         Game.map.dispose();
      } catch (Exception var10) {
      }

      try {
         for (int i = 0; i < Game.cloudsAnimation.imageCloud.size(); i++) {
            Game.cloudsAnimation.imageCloud.get(i).dispose();
         }
      } catch (Exception var20) {
      }

      try {
         Game.soundsManager.dispose();
      } catch (Exception var9) {
      }

      try {
         InitGame.background.dispose();
      } catch (Exception var8) {
      }

      try {
         for (int i = 0; i < ImageManager.images.size(); i++) {
            ImageManager.getImage(i).dispose();
         }
      } catch (Exception var19) {
      }

      try {
         Game.gameThread.running = false;
         Game.gameThread.interrupt();
         Game.gameThread.join();
      } catch (Exception var7) {
      }

      try {
         Game.gameThreadUpdate.running = false;
         Game.gameThreadUpdate.interrupt();
         Game.gameThreadUpdate.join();
      } catch (Exception var6) {
      }

      try {
         Game.gameThreadTurns.running = false;
         Game.gameThreadTurns.interrupt();
         Game.gameThreadTurns.join();
      } catch (Exception var5) {
      }

      try {
         Game.gameThreadEvents.running = false;
         Game.gameThreadEvents.interrupt();
         Game.gameThreadEvents.join();
      } catch (Exception var4) {
      }

      try {
         Game.lProvinces.clear();
         Game.lProvincesData.clear();
         Game.lProvincesData2.clear();
         Game.lProvincesData3.clear();
         Game.lProvincesData4.clear();
         Game.lProvincesData5.clear();
         Game.lProvincesData6.clear();
         Game.lProvincesData7.clear();
         Game.lProvincesData8.clear();
         Game.lProvincesData9.clear();
         Game.lProvincesData10.clear();
         Game.lProvincesPopulation.clear();
      } catch (Exception var3) {
      }

      try {
         for (int i = 0; i < Game.getCivsSize(); i++) {
            Game.lCivs.get(i).getFlag().dispose();
         }
      } catch (Exception var18) {
      }

      try {
         Game.lCivs.clear();
      } catch (Exception var2) {
      }

      try {
         for (int i = 0; i < BuildingsManager.buildingImages.size(); i++) {
            BuildingsManager.buildingImages.get(i).dispose();
         }

         BuildingsManager.buildingImages.clear();
         BuildingsManager.buildings.clear();
      } catch (Exception var17) {
      }

      try {
         for (int i = 0; i < Game.ideologiesManager.ideologiesImages.size(); i++) {
            Game.ideologiesManager.ideologiesImages.get(i).dispose();
         }

         Game.ideologiesManager.lIdeologies.clear();
      } catch (Exception var16) {
      }

      try {
         for (int i = 0; i < Game.religionManager.religionImages.size(); i++) {
            Game.religionManager.religionImages.get(i).dispose();
         }

         Game.religionManager.lReligions.clear();
      } catch (Exception var15) {
      }

      try {
         for (int i = 0; i < ArmyManager.armyImages.size(); i++) {
            ArmyManager.armyImages.get(i).dispose();
         }

         ArmyManager.armyImages.clear();
         ArmyManager.lUnitsTypes.clear();
         ArmyManager.lArmy.clear();
      } catch (Exception var14) {
      }

      try {
         for (int i = 0; i < LegacyManager.legacyImages.size(); i++) {
            LegacyManager.legacyImages.get(i).dispose();
         }

         LegacyManager.legacyImages.clear();
         LegacyManager.legacies.clear();
      } catch (Exception var13) {
      }

      try {
         for (int i = 0; i < AdvantagesManager.advantagesImages.size(); i++) {
            AdvantagesManager.advantagesImages.get(i).dispose();
         }

         AdvantagesManager.advantagesImages.clear();
         AdvantagesManager.advantages.clear();
      } catch (Exception var12) {
      }

      super.dispose();
      Gdx.app.exit();
   }
}
