package aoc.kingdoms.lukasz.jakowski.Renderer;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SparksAnimation {
   public int currentIMG = 0;
   public long ANIMATION_TIME = 0L;

   public void draw(SpriteBatch oSB, int posX, int posY, int width, int height) {
      Images.sparks.get(this.currentIMG).draw(oSB, posX, posY, width, height);
      this.updateAnimation();
   }

   public void draw(SpriteBatch oSB, int posX, int posY, int width, int height, boolean flipX, boolean flipY) {
      Images.sparks.get(this.currentIMG).draw(oSB, posX, posY, width, height, flipX, flipY);
      this.updateAnimation();
   }

   public void draw2(SpriteBatch oSB, int posX, int posY, int width, int height) {
      Images.sparks.get(this.currentIMG).draw2(oSB, posX, posY, width, height);
      this.updateAnimation();
   }

   public void draw2(SpriteBatch oSB, int posX, int posY, int width, int height, boolean flipX, boolean flipY) {
      Images.sparks.get(this.currentIMG).draw2(oSB, posX, posY, width, height, flipX, flipY);
      this.updateAnimation();
   }

   public void updateAnimation() {
      if (CFG.currentTimeMillis - this.ANIMATION_TIME > 45L) {
         this.ANIMATION_TIME = CFG.currentTimeMillis;
         this.currentIMG++;
         if (this.currentIMG >= Images.SPARKS_SIZE) {
            this.currentIMG = 0;
         }
      }
   }
}
