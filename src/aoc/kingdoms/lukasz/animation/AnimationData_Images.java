package aoc.kingdoms.lukasz.animation;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class AnimationData_Images {
   protected List<Image> lAnimation = new ArrayList<>();
   private int iNumOfFrames = 0;
   protected AnimationData_Type animationType;
   protected int ANIMATION_DURATION = 0;
   protected int ANIMATION_FRAME_TIME = 0;

   public AnimationData_Images(String sImagesPath, int numOfImages, AnimationData_Type animationType, int ANIMATION_DURATION, int ANIMATION_FRAME_TIME) {
      for (int i = 0; i < numOfImages; i++) {
         this.lAnimation.add(ImageManager.loadImage(sImagesPath.replaceAll("%03u", "" + i), TextureFilter.Nearest));
      }

      this.iNumOfFrames = this.lAnimation.size();
      this.animationType = animationType;
      this.ANIMATION_DURATION = ANIMATION_DURATION;
      this.ANIMATION_FRAME_TIME = ANIMATION_FRAME_TIME;
   }

   public final void drawFrame(SpriteBatch oSB, int nPosX, int nPosY, int currentFrameID) {
      this.lAnimation
         .get(currentFrameID)
         .draw(
            oSB,
            Game.mapCoords.getPosX() + nPosX - this.lAnimation.get(currentFrameID).getWidth() / 2,
            Game.mapCoords.getPosY() + nPosY - this.lAnimation.get(currentFrameID).getHeight() / 2
         );
   }

   public final int getNumOfFrames() {
      return this.iNumOfFrames;
   }
}
