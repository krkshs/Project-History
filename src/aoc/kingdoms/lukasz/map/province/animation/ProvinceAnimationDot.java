package aoc.kingdoms.lukasz.map.province.animation;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProvinceAnimationDot {
   public int iProvinceID;
   public Color dotColor;
   public int posX;
   public int posY;
   public static final float ANIMATION_DURATION = 1250.0F;
   public long animationTime = 0L;
   public float fPerc = 1.0F;

   public ProvinceAnimationDot(int nProvinceID, Color nColor) {
      this.iProvinceID = nProvinceID;
      this.dotColor = nColor;
      this.animationTime = CFG.currentTimeMillis;
      if (Game.getProvince(nProvinceID).getCitiesSize() > 0) {
         this.posX = Game.getProvince(nProvinceID).getCity(0).getPosX();
         this.posY = -Game.getProvince(nProvinceID).getCity(0).getPosY();
      } else {
         this.posX = Game.getProvince(nProvinceID).getCenterX();
         this.posY = -Game.getProvince(nProvinceID).getCenterY();
      }
   }

   public boolean draw(SpriteBatch oSB, float nScale) {
      boolean out = false;
      this.fPerc = this.fPerc - (float)(CFG.currentTimeMillis - this.animationTime) / 1250.0F;
      this.animationTime = CFG.currentTimeMillis;
      if (this.fPerc <= 0.0F) {
         this.fPerc = 0.0F;
         out = true;
      }

      if (Game.getProvince(this.iProvinceID).getDrawProvince()) {
         Renderer.shapeDrawer.setColor(new Color(this.dotColor.r, this.dotColor.g, this.dotColor.b, 0.15F * this.fPerc));
         Renderer.shapeDrawer
            .filledCircle(
               (this.posX + Game.getProvince(this.iProvinceID).getTranslateProvincePosX()) * nScale,
               (this.posY - Game.mapCoords.getPosY()) * nScale,
               20.0F * nScale * this.fPerc
            );
         Renderer.shapeDrawer.setColor(new Color(this.dotColor.r, this.dotColor.g, this.dotColor.b, 0.45F * this.fPerc));
         Renderer.shapeDrawer
            .filledCircle(
               (this.posX + Game.getProvince(this.iProvinceID).getTranslateProvincePosX()) * nScale,
               (this.posY - Game.mapCoords.getPosY()) * nScale,
               14.0F * nScale * this.fPerc
            );
      }

      return out;
   }
}
