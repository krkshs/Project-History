package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.animation.AnimationData_Images;
import aoc.kingdoms.lukasz.animation.AnimationOnce_Images;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class AnimationManager {
   public static final String IMAGE_REPLACE = "%03u";
   public AnimationData_Images animationMove;
   private List<AnimationOnce_Images> animationOnceImages = new ArrayList<>();
   private int animationOnceImagesSize = 0;
   public List<AnimationManager.ClickAnimation> clickAnimations = new ArrayList<>();
   public int clickAnimationDuration = 425;

   protected final void loadAnimations() {
   }

   public final void addAnimationOnce(AnimationOnce_Images nAnimationOnceImages) {
      this.animationOnceImages.add(nAnimationOnceImages);
      this.animationOnceImagesSize = this.animationOnceImages.size();
   }

   public final void update() {
      for (int i = this.animationOnceImages.size() - 1; i >= 0; i--) {
         this.animationOnceImages.get(i).update();
         if (this.animationOnceImages.get(i).getAnimationFinished()) {
            this.animationOnceImages.remove(i);
         }
      }

      this.animationOnceImagesSize = this.animationOnceImages.size();
   }

   public final void draw(SpriteBatch oSB) {
      for (int i = 0; i < this.animationOnceImagesSize; i++) {
         this.animationOnceImages.get(i).draw(oSB);
      }
   }

   public final void drawScaled(SpriteBatch oSB) {
      for (int i = this.clickAnimations.size() - 1; i >= 0; i--) {
         if (CFG.currentTimeMillis < this.clickAnimations.get(i).clickTime + this.clickAnimationDuration) {
            oSB.setColor(
               new Color(
                  Colors.HOVER_GOLD.r,
                  Colors.HOVER_GOLD.g,
                  Colors.HOVER_GOLD.b,
                  0.5F * (1.0F - (float)(CFG.currentTimeMillis - this.clickAnimations.get(i).clickTime) / this.clickAnimationDuration)
               )
            );
            ImageManager.getImage(Images.click)
               .draw(
                  oSB,
                  (int)((Game.mapCoords.getPosX() - this.clickAnimations.get(i).clickPosX) * Game.mapScale.getCurrentScale()),
                  (int)((Game.mapCoords.getPosY() - this.clickAnimations.get(i).clickPosY) * Game.mapScale.getCurrentScale())
               );
            oSB.setColor(Color.WHITE);
         } else {
            this.clickAnimations.remove(i);
         }
      }
   }

   public void clickAnimation(int nX, int nY) {
      this.clickAnimations.add(new AnimationManager.ClickAnimation(nX, nY));
   }

   public class ClickAnimation {
      public long clickTime = 0L;
      public int clickPosX = 0;
      public int clickPosY = 0;

      public ClickAnimation(int nX, int nY) {
         this.clickTime = CFG.currentTimeMillis;
         this.clickPosX = Game.mapCoords.getPosX() - (int)(nX / Game.mapScale.getCurrentScale()) + ImageManager.getImage(Images.click).getWidth() / 2;
         this.clickPosY = Game.mapCoords.getPosY() - (int)(nY / Game.mapScale.getCurrentScale()) + ImageManager.getImage(Images.click).getHeight() / 2;
      }
   }
}
