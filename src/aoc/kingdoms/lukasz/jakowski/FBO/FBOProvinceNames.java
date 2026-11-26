package aoc.kingdoms.lukasz.jakowski.FBO;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class FBOProvinceNames {
   public static FrameBuffer fboProvince_Names = null;
   public static Texture textureProvince_Names = null;
   public static int fboNumToGenerate_Names = 0;
   public static int lastPosX = -1;
   public static int lastPosY = -1;
   public static int fboPosX = -1;
   public static int fboPosY = -1;

   public static final void redrawnProvinceNames() {
      lastPosX = -9194519;
   }

   public static final void disposeProvinceNamesFBO() {
      if (fboProvince_Names != null) {
         fboProvince_Names.dispose();
         fboProvince_Names = null;
      }
   }

   public static final void disposeProvinceNamesTexture() {
      if (textureProvince_Names != null) {
         textureProvince_Names.dispose();
         textureProvince_Names = null;
      }
   }

   public static final void updateFBO() {
      if (lastPosX == Game.mapCoords.getPosX() && lastPosY == Game.mapCoords.getPosY()) {
         fboNumToGenerate_Names++;
      } else {
         lastPosX = Game.mapCoords.getPosX();
         lastPosY = Game.mapCoords.getPosY();
         fboNumToGenerate_Names = 0;
      }
   }

   public static final void drawProvinceNames(SpriteBatch oSB) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.settingsManager.PROVINCE_NAMES_ALPHA * ProvinceDrawArmy.DRAW_PROVINCE_NAMES_ALPHA));
      draw(oSB, 0, 0, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, false, true);
      oSB.setColor(Color.WHITE);
   }

   public static final void draw(
      SpriteBatch oSB,
      int nPosX,
      int nPosY,
      int originX,
      int originY,
      int nWidth,
      int nHeight,
      float scaleX,
      float scaleY,
      float rotation,
      int srcX,
      int srcY,
      int srcWidth,
      int srcHeight,
      boolean flipX,
      boolean flipY
   ) {
      oSB.draw(
         textureProvince_Names,
         nPosX,
         -nPosY - CFG.GAME_HEIGHT,
         originX,
         originY,
         nWidth,
         nHeight,
         scaleX,
         scaleY,
         rotation,
         srcX,
         srcY,
         srcWidth,
         srcHeight,
         flipX,
         flipY
      );
   }
}
