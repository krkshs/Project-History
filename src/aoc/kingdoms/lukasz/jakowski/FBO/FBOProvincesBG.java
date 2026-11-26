package aoc.kingdoms.lukasz.jakowski.FBO;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class FBOProvincesBG {
   public static FrameBuffer fboProvince_PBG = null;
   public static Texture textureProvince_PBG = null;
   public static int fboNumToGenerate_PB = 0;
   public static int lastPosX = -1;
   public static int lastPosY = -1;
   public static int fboPosX = -1;
   public static int fboPosY = -1;

   public static final void redrawnProvinces() {
      lastPosX = -9194519;
   }

   public static final void disposeProvincesFBO() {
      if (fboProvince_PBG != null) {
         fboProvince_PBG.dispose();
         fboProvince_PBG = null;
      }
   }

   public static final void disposeProvincesTexture() {
      if (textureProvince_PBG != null) {
         textureProvince_PBG.dispose();
         textureProvince_PBG = null;
      }
   }

   public static final void updateFBO() {
      if (lastPosX == Game.mapCoords.getPosX() && lastPosY == Game.mapCoords.getPosY()) {
         fboNumToGenerate_PB++;
      } else {
         lastPosX = Game.mapCoords.getPosX();
         lastPosY = Game.mapCoords.getPosY();
         fboNumToGenerate_PB = 0;
      }
   }

   public static final void drawPBG(SpriteBatch oSB, float fAlpha) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
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
         textureProvince_PBG,
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
