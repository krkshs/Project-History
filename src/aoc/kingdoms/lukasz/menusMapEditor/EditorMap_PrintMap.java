package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.Image;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_PrintMap extends Menu {
   public int iMapPosX = 0;
   public int iMapPosY = 0;
   public int id = 0;
   public Pixmap pixBIG;
   public static float scale;
   public boolean error = false;
   public static View goBack = View.EDITOR_MAPS_EDIT;
   public int prepare = 0;

   public EditorMap_PrintMap() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(1, 1, 1, 1));
      this.iMapPosX = 0;
      this.iMapPosY = 0;
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);

      try {
         this.pixBIG = new Pixmap((int)(Game.mapBG.getWidth() / scale), (int)(Game.mapBG.getHeight() / scale), Format.RGBA8888);
      } catch (Exception var3) {
         this.error = true;
         CFG.exceptionStack(var3);
         Game.menuManager.addToast_Error("MAP SIZE TOO BIG");
      }

      Game.mapScale.setCurrentScale(1.0F / scale);
      Game.mapCoords.setNewPosX(this.iMapPosX);
      Game.mapCoords.setNewPosY(this.iMapPosY);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (this.prepare++ >= 13) {
         if (this.error) {
            Game.menuManager.setViewIDWithoutAnimation(goBack);
         } else {
            this.saveScenarioMinimapPreviewTexture(oSB);
            this.iMapPosX = (int)(this.iMapPosX - CFG.GAME_WIDTH * scale);
            if (-this.iMapPosX >= Game.mapBG.getWidth()) {
               this.iMapPosX = 0;
               this.iMapPosY = (int)(this.iMapPosY - CFG.GAME_HEIGHT * scale);
               if (-this.iMapPosY >= Game.mapBG.getHeight()) {
                  try {
                     Image tempIMG = new Image(new Texture(this.pixBIG));
                     PixmapIO.writePNG(
                        FileManager.getSaveType("PRINTED_MAP/out" + (1000 + Game.oR.nextInt(1000)) + ".png"),
                        tempIMG.getTexture().getTextureData().consumePixmap()
                     );
                     tempIMG.dispose();
                     Image var8 = null;
                     this.pixBIG.dispose();
                     this.pixBIG = null;
                  } catch (Exception var7) {
                     CFG.exceptionStack(var7);
                     Game.menuManager.addToast_Error("ERROR");
                  }

                  Game.menuManager.addToast(Game.lang.get("Saved"));
                  Game.menuManager.setViewIDWithoutAnimation(goBack);
               }
            }

            Game.mapCoords.setNewPosX(this.iMapPosX);
            Game.mapCoords.setNewPosY(this.iMapPosY);
         }
      }
   }

   protected final void saveScenarioMinimapPreviewTexture(SpriteBatch oSB) {
      try {
         oSB.flush();
         ScissorStack.popScissors();
      } catch (IllegalStateException var3) {
      }

      this.drawPixmapBIG(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - CFG.GAME_HEIGHT, CFG.GAME_WIDTH, CFG.GAME_HEIGHT));
   }

   public static Pixmap flipPixmap(Pixmap src) {
      int width = src.getWidth();
      int height = src.getHeight();
      Pixmap flipped = new Pixmap(width, height, src.getFormat());

      for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
            flipped.drawPixel(x, y, src.getPixel(x, height - y - 1));
         }
      }

      return flipped;
   }

   public void drawPixmapBIG(Pixmap src) {
      int width = src.getWidth();
      int height = src.getHeight();

      try {
         for (int x = 0; x < width && x + Math.abs(this.iMapPosX / scale) < Game.mapBG.getWidth() / scale; x++) {
            for (int y = 0; y < height && y + Math.abs(this.iMapPosY / scale) < Game.mapBG.getHeight() / scale; y++) {
               Color tempColor = new Color(src.getPixel(x, height - y - 1));
               tempColor = new Color(1.0F, tempColor.b, tempColor.g, tempColor.r);
               this.pixBIG.drawPixel(x + (int)Math.abs(this.iMapPosX / scale), y + (int)Math.abs(this.iMapPosY / scale), tempColor.toIntBits());
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }
}
