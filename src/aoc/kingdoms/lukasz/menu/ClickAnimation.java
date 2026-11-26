package aoc.kingdoms.lukasz.menu;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ClickAnimation {
   public int iX;
   public int iY;
   public int iWMax;
   public int iHMax;
   public static final float ANIMATION_DURATION = 400.0F;
   public long animationTime = 0L;
   public float fPerc = 1.0F;
   public float extraSpeed;
   public List<ClickAnimation.ClickXY> clickXY = new ArrayList<>();
   public int clickXYSize = 0;

   public ClickAnimation(int iX, int iY, int iWMax, int iHMax) {
      this.iX = iX;
      this.iY = iY;
      this.iWMax = iWMax;
      this.iHMax = iHMax;
      this.extraSpeed = 1.35F + Game.oR.nextInt(50) / 100.0F;
      this.animationTime = CFG.currentTimeMillis;

      for (int i = GameValues.clickAnim.CLICK_ANIM_X.length - 1; i >= 0; i--) {
         this.clickXY
            .add(
               new ClickAnimation.ClickXY(
                  iWMax * GameValues.clickAnim.CLICK_ANIM_X[i],
                  iHMax * GameValues.clickAnim.CLICK_ANIM_Y[i],
                  GameValues.clickAnim.CLICK_ANIM_SPEED_X[i],
                  GameValues.clickAnim.CLICK_ANIM_SPEED_Y[i]
               )
            );
      }

      this.clickXYSize = this.clickXY.size();
   }

   public boolean draw(SpriteBatch oSB) {
      boolean out = false;
      this.fPerc = this.fPerc - (float)(CFG.currentTimeMillis - this.animationTime) / 400.0F;
      this.animationTime = CFG.currentTimeMillis;
      if (this.fPerc <= 0.0F) {
         this.fPerc = 0.0F;
         out = true;
      }

      Renderer.shapeDrawer.setColor(new Color(this.getColor().r, this.getColor().g, this.getColor().b, 0.65F * this.fPerc));

      for (int i = 0; i < this.clickXYSize; i++) {
         Renderer.shapeDrawer
            .filledCircle(this.iX + (int)this.clickXY.get(i).fX, -this.iY + (int)this.clickXY.get(i).fY, this.clickXY.get(i).width * this.fPerc);
         ClickAnimation.ClickXY var10000 = this.clickXY.get(i);
         var10000.fX = var10000.fX + this.clickXY.get(i).speedX * this.extraSpeed;
         var10000 = this.clickXY.get(i);
         var10000.fY = var10000.fY + this.clickXY.get(i).speedY * this.extraSpeed * (0.75F + 0.25F * this.fPerc);
      }

      return out;
   }

   public Color getColor() {
      return ProvinceDraw.progressBar;
   }

   public static class ClickXY {
      public float fX;
      public float fY;
      public float speedX;
      public float speedY;
      public float width;

      public ClickXY(float fX, float fY, float speedX, float speedY) {
         this.fX = fX;
         this.fY = fY;
         this.speedX = speedX;
         this.speedY = speedY;
         this.width = GameValues.clickAnim.CLICK_WIDTH;
         if (GameValues.clickAnim.CLICK_WIDTH_RANDOM >= 2.0F) {
            this.width = this.width + Game.oR.nextInt((int)GameValues.clickAnim.CLICK_WIDTH_RANDOM);
         }
      }
   }
}
