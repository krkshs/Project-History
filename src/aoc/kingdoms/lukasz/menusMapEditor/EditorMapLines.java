package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.map.Ship.ShipLine;
import aoc.kingdoms.lukasz.map.map.Ship.ShipManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapLines extends Menu {
   public static ShipLine shipLine = new ShipLine();

   public EditorMapLines() {
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
               FileHandle fileSave = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "Lines_Sea.txt");
               fileSave.writeString("\n", true);

               for (int i = 0; i < EditorMapLines.shipLine.pointsSize; i++) {
                  fileSave.writeString(EditorMapLines.shipLine.points.get(i).getPosX() / Game.mapBG.iMapScale + ";", true);
               }

               fileSave.writeString("\n", true);

               for (int i = 0; i < EditorMapLines.shipLine.pointsSize; i++) {
                  fileSave.writeString(EditorMapLines.shipLine.points.get(i).getPosY() / Game.mapBG.iMapScale + ";", true);
               }

               ShipManager.addShipLine(EditorMapLines.shipLine);
               EditorMapLines.shipLine = new ShipLine();
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

      for (int i = 0; i < ShipManager.shipLinesSize; i++) {
         ShipManager.shipLines.get(i).draw(oSB);
      }

      shipLine.draw(oSB);
   }

   public static boolean keyUp(int keycode) {
      if (keycode == 67) {
         shipLine.removePoint();
         return true;
      } else if (keycode == 62) {
         shipLine.addNewPoint();
         return true;
      } else {
         return false;
      }
   }
}
