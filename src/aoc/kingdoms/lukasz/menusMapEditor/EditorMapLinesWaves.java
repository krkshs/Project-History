package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.map.Waves.WavesLine;
import aoc.kingdoms.lukasz.map.map.Waves.WavesManager;
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

public class EditorMapLinesWaves extends Menu {
   public static WavesLine wavesLine = new WavesLine();

   public EditorMapLinesWaves() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
         }
      });
      menuElements.add(
         new ButtonMain(
            null, 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true
         ) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Save"));
            }

            @Override
            public void actionElement() {
               WavesManager.addWavesLine(EditorMapLinesWaves.wavesLine);
               EditorMapLinesWaves.wavesLine = new WavesLine();
            }
         }
      );
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
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH + iTranslateX,
            iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            false,
            false
         );
      String sText = "SPACE -> ADD POINT\nBACKSPACE -> REMOVE POINT";
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB,
         iTranslateX + CFG.PADDING * 3,
         iTranslateY + CFG.PADDING * 3,
         (int)Renderer.glyphLayout.width + CFG.PADDING * 2,
         (int)Renderer.glyphLayout.height + CFG.PADDING * 2
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, Colors.COLOR_TEXT_TITLE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);

      for (int i = 0; i < WavesManager.wavesLinesSize; i++) {
         WavesManager.wavesLines.get(i).draw(oSB);
      }

      wavesLine.draw(oSB);
   }

   public static boolean keyUp(int keycode) {
      if (keycode == 67) {
         wavesLine.removePoint();
         return true;
      } else if (keycode == 62) {
         wavesLine.addNewPoint();
         return true;
      } else {
         return false;
      }
   }
}
