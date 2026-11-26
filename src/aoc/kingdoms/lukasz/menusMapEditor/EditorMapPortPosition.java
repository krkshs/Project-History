package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapPortPosition extends Menu {
   public EditorMapPortPosition() {
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
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            iTranslateX,
            iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            false
         );
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText = "SHIFT PORT: " + Game.iActiveProvince;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, (int)Renderer.glyphLayout.width, (int)Renderer.glyphLayout.height
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, Colors.COLOR_TEXT_TITLE);
   }

   public static boolean keyUp(int keycode) {
      if (Game.iActiveProvince >= 0) {
         if (keycode == 21) {
            Game.getProvince(Game.iActiveProvince).setPortShiftX(Game.getProvince(Game.iActiveProvince).getPortShiftX() - 1);
            return true;
         }

         if (keycode == 22) {
            Game.getProvince(Game.iActiveProvince).setPortShiftX(Game.getProvince(Game.iActiveProvince).getPortShiftX() + 1);
            return true;
         }

         if (keycode == 19) {
            Game.getProvince(Game.iActiveProvince).setPortShiftY(Game.getProvince(Game.iActiveProvince).getPortShiftY() - 1);
            return true;
         }

         if (keycode == 20) {
            Game.getProvince(Game.iActiveProvince).setPortShiftY(Game.getProvince(Game.iActiveProvince).getPortShiftY() + 1);
            return true;
         }
      }

      return false;
   }
}
