package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapSeaProvinces extends Menu {
   public static int nLevelOfPort = -1;

   public EditorMapSeaProvinces() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            SaveManager.saveProvinceDetails();
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            Game.menuManager.addToast(Game.lang.get("Saved"));
         }
      });
      menuElements.add(
         new ButtonMain(null, 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Back"));
            }

            @Override
            public void actionElement() {
               Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            }
         }
      );
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBox_EDGE_TOP_LR(
         oSB,
         Images.boxSimple,
         iTranslateX,
         iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2,
         CFG.BUTTON_WIDTH * 4 + CFG.PADDING * 3,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2
      );
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText = "SET TO LEVEL: "
         + nLevelOfPort
         + " ["
         + (nLevelOfPort == -1 ? "LAND" : (nLevelOfPort == -2 ? "SEA" : (nLevelOfPort == -3 ? "CLOSED SEA" : "SEA PROVINCE, x1 SCALE")))
         + "]\n\nENTER/SPACE -> SET LEVEL\nLEFT - > LEVEL--\nRIGHT - > LEVEL++\n\n-1 = LAND PROVINCE\n-2 = SEA PROVINCE\n-3 = CLOSED SEA, LAKES\n-4 = SEA PROVINCE, x1 SCALE";
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, (int)Renderer.glyphLayout.width, (int)Renderer.glyphLayout.height
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, Colors.COLOR_TEXT_TITLE);
   }

   public static boolean keyUp(int keycode) {
      if (keycode == 21) {
         nLevelOfPort--;
         if (nLevelOfPort < -4) {
            nLevelOfPort = -4;
         }

         return true;
      } else if (keycode == 22) {
         nLevelOfPort++;
         if (nLevelOfPort > -1) {
            nLevelOfPort = -1;
         }

         return true;
      } else if (keycode != 66 && keycode != 62) {
         return false;
      } else {
         if (Game.iActiveProvince >= 0) {
            boolean reloadProvinceBG = false;
            if (Game.getProvince(Game.iActiveProvince).getLevelOfPort() >= -1 && nLevelOfPort < -1) {
               reloadProvinceBG = true;
            } else if (Game.getProvince(Game.iActiveProvince).getLevelOfPort() < -1 && nLevelOfPort >= -1) {
               reloadProvinceBG = true;
            }

            Game.getProvince(Game.iActiveProvince).setLevelOfPort(nLevelOfPort);
            if (Game.getProvince(Game.iActiveProvince).getLevelOfPort() < -1) {
               Game.getProvince(Game.iActiveProvince).setContinent(0);
            } else if (Game.getProvince(Game.iActiveProvince).getContinent() == 0) {
               Game.getProvince(Game.iActiveProvince).setContinent(1);
            }

            if (reloadProvinceBG) {
               Game.getProvince(Game.iActiveProvince).loadProvinceBG();
            }

            Game.getProvince(Game.iActiveProvince).updateSeaProvince();
            Game.updateProvincesInView = true;
            ProvinceDrawDetails.updateDrawProvinceDetails_SeaProvinces(Game.iActiveProvince);
         }

         return true;
      }
   }
}
