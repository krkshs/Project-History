package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Hover;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class MenuElement_Hover implements Hover {
   public static final float TEXT_SCALE = 0.9F;
   private List<MenuElement_HoverElement> lElements;
   private int iElementsSize = 0;
   public static int DRAW_EXTRA_TIME = 2500;
   public static long lTimeDrawExtra = 0L;
   public boolean haveDrawExtraElement = false;
   public long INIT_TIME;
   public static long ANIMATION_TIME;
   public static float ANIMATION_ALPHA;
   public static float ANIMATION_PADDING;
   public static int ANIMATION_INTERVAL = 2450;
   public int iFontID = 0;
   public int iHeight = 0;
   public int iHeight2 = 0;
   public int iHeightDrawExtra = 0;
   private int iWidth = 0;
   private int iMaxWidth = 0;
   private int iWidthOver = 0;
   private int iScrollPosX = 0;
   private boolean backAnimation = true;
   private long lTime = 0L;
   public static Color colorGradient = new Color(0.078431375F, 0.078431375F, 0.1254902F, 0.45F);
   public static Color colorGradientLoading = new Color(0.13725491F, 0.19607843F, 0.25490198F, 1.0F);

   public static final void resetAnimation() {
      ANIMATION_TIME = CFG.currentTimeMillis;
      ANIMATION_ALPHA = 0.0225F;
      ANIMATION_PADDING = CFG.PADDING;
   }

   public MenuElement_Hover(List<MenuElement_HoverElement> nElements) {
      this.init(nElements, 0, false);
   }

   public MenuElement_Hover(List<MenuElement_HoverElement> nElements, boolean titleBGLast) {
      this.init(nElements, 0, titleBGLast);
   }

   public MenuElement_Hover(List<MenuElement_HoverElement> nElements, int iFontID) {
      this.init(nElements, iFontID, false);
   }

   private final void init(List<MenuElement_HoverElement> nElements, int iFontID, boolean titleBGLast) {
      this.lElements = nElements;
      this.iElementsSize = this.lElements.size();
      this.iWidth = 0;
      this.iFontID = iFontID;

      for (int i = 0; i < this.iElementsSize; i++) {
         if (this.lElements.get(i).getWidth() > CFG.GAME_WIDTH - CFG.PADDING * 2
            && this.lElements.get(i).getWidth() - CFG.GAME_WIDTH - CFG.PADDING * 2 > this.iWidthOver) {
            this.iWidthOver = this.lElements.get(i).getWidth() - CFG.GAME_WIDTH - CFG.PADDING * 2;
         }

         if (!this.lElements.get(i).drawElement) {
            this.haveDrawExtraElement = true;
         }
      }

      if (this.iWidthOver > 0) {
         this.iScrollPosX = this.iWidthOver + CFG.PADDING * 10;
         this.lTime = CFG.currentTimeMillis;
      }

      for (int i = 0; i < this.iElementsSize; i++) {
         if (this.lElements.get(i).getWidth() > this.iWidth) {
            this.iWidth = this.lElements.get(i).getWidth();
            this.iMaxWidth = this.lElements.get(i).getWidth();
         }
      }

      this.iWidth = this.iWidth + getDrawExtraXPos() * 2;
      this.iHeight2 = 0;
      this.iHeightDrawExtra = CFG.PADDING * (this.iElementsSize - 1) + CFG.PADDING * 6;
      int numElements = 0;

      for (int ix = 0; ix < this.iElementsSize; ix++) {
         this.iHeightDrawExtra = this.iHeightDrawExtra + this.lElements.get(ix).getHeight();
         if (this.lElements.get(ix).drawElement) {
            this.iHeight2 = this.iHeight2 + this.lElements.get(ix).getHeight();
            numElements++;
         }
      }

      if (titleBGLast) {
         this.iHeight2 = this.iHeight2 - CFG.PADDING * 2;
         this.iHeightDrawExtra = this.iHeightDrawExtra - CFG.PADDING * 2;
      }

      this.iHeight2 = this.iHeight2 + CFG.PADDING * numElements + CFG.PADDING * 5;
      this.iHeight = this.iHeight2;
      lTimeDrawExtra = CFG.currentTimeMillis;
      this.INIT_TIME = CFG.currentTimeMillis;
   }

   public final void updateHeight() {
      this.iHeight = CFG.currentTimeMillis - lTimeDrawExtra >= DRAW_EXTRA_TIME ? this.iHeightDrawExtra : this.iHeight2;
   }

   public boolean initHide() {
      return this.INIT_TIME + GameValues.notifications.HOVER_HIDE_TIME > CFG.currentTimeMillis;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY) {
      if (!this.initHide() || Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DEFAULT) {
         this.updateHeight();
         nPosX = Math.max(this.getMinPosX(), nPosX);
         nPosX = (int)(nPosX + ANIMATION_PADDING);
         if (nPosX + this.iWidth > CFG.GAME_WIDTH - CFG.PADDING) {
            nPosX = CFG.GAME_WIDTH - this.iWidth - CFG.PADDING;
         }

         if (nPosY < 0) {
            nPosY = CFG.PADDING;
         } else if (nPosY + this.iHeight > CFG.GAME_HEIGHT) {
            nPosY = CFG.GAME_HEIGHT - this.iHeight - CFG.PADDING;
         }

         this.draw_Hover(oSB, nPosX, nPosY);
      }
   }

   @Override
   public final void drawAlwaysOver(SpriteBatch oSB, int nPosX, int nPosY) {
      if (!this.initHide()) {
         this.updateHeight();
         nPosX = (int)(nPosX + ANIMATION_PADDING);
         nPosX += CFG.PADDING;
         nPosY = nPosY - this.iHeight - CFG.PADDING;
         if (nPosX + this.iWidth > CFG.GAME_WIDTH - CFG.PADDING) {
            nPosX = CFG.GAME_WIDTH - this.iWidth - CFG.PADDING;
         }

         if (nPosY < 0) {
            nPosY = CFG.PADDING;
         } else if (nPosY + this.iHeight > CFG.GAME_HEIGHT) {
            nPosY = CFG.GAME_HEIGHT - this.iHeight - CFG.PADDING;
         }

         this.draw_Hover(oSB, nPosX, nPosY);
      }
   }

   @Override
   public final void drawAlwaysOver_Mobile(SpriteBatch oSB, int nPosX, int nPosY) {
      this.updateHeight();
      nPosX = (int)(nPosX + ANIMATION_PADDING);
      nPosX -= this.iWidth / 4;
      nPosY = nPosY - this.iHeight - CFG.PADDING * 4;
      if (nPosX < CFG.PADDING) {
         nPosX = CFG.PADDING;
      }

      if (nPosX + this.iWidth > CFG.GAME_WIDTH - CFG.PADDING) {
         nPosX = CFG.GAME_WIDTH - this.iWidth - CFG.PADDING;
      }

      if (nPosY < 0) {
         nPosY = CFG.PADDING;
      } else if (nPosY + this.iHeight > CFG.GAME_HEIGHT) {
         nPosY = CFG.GAME_HEIGHT - this.iHeight - CFG.PADDING;
      }

      this.draw_Hover(oSB, nPosX, nPosY);
   }

   @Override
   public final void drawAlwaysBelow(SpriteBatch oSB, int nPosX, int nPosY) {
      this.updateHeight();
      nPosX = (int)(nPosX + ANIMATION_PADDING);
      nPosX += CFG.PADDING;
      nPosY += CFG.PADDING;
      if (nPosX + this.iWidth > CFG.GAME_WIDTH - CFG.PADDING) {
         nPosX = CFG.GAME_WIDTH - this.iWidth - CFG.PADDING;
      }

      if (nPosY < 0) {
         nPosY = CFG.PADDING;
      } else if (nPosY + this.iHeight > CFG.GAME_HEIGHT) {
         nPosY = CFG.GAME_HEIGHT - this.iHeight - CFG.PADDING;
      }

      this.draw_Hover(oSB, nPosX, nPosY);
   }

   protected final int getScrollPosX() {
      if (this.iWidthOver > 0) {
         if (this.backAnimation) {
            if (this.lTime + 1500L < CFG.currentTimeMillis && this.iScrollPosX-- < -CFG.PADDING) {
               this.backAnimation = !this.backAnimation;
               this.lTime = CFG.currentTimeMillis;
            }
         } else if (this.lTime + 1000L < CFG.currentTimeMillis && this.iScrollPosX++ > this.iWidthOver + CFG.PADDING * 10) {
            this.backAnimation = !this.backAnimation;
            this.lTime = CFG.currentTimeMillis;
         }

         return this.iScrollPosX;
      } else {
         return 0;
      }
   }

   @Override
   public final void drawProvinceInfo(SpriteBatch oSB, int nPosX, int nPosY) {
      if (!this.initHide()) {
         nPosX = (int)(nPosX + ANIMATION_PADDING);
         this.draw_Hover(oSB, nPosX, nPosY);
      }
   }

   public static final int getDrawExtraXPos() {
      return CFG.PADDING * 3;
   }

   @Override
   public final void draw_Hover(SpriteBatch oSB, int nPosX, int nPosY) {
      int tempScrollX = this.getScrollPosX();
      boolean drawExtra = CFG.currentTimeMillis - lTimeDrawExtra >= DRAW_EXTRA_TIME;
      if (drawExtra) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.975F * ANIMATION_ALPHA));
         Renderer.drawBoxHover(oSB, nPosX + tempScrollX, nPosY, this.iWidth, this.iHeightDrawExtra, ANIMATION_ALPHA);
         oSB.setColor(new Color(colorGradient.r, colorGradient.g, colorGradient.b, colorGradient.a * ANIMATION_ALPHA));
         Images.gradientXY
            .draw(oSB, nPosX + tempScrollX, nPosY + this.iHeightDrawExtra / 2, this.iWidth, this.iHeightDrawExtra - this.iHeightDrawExtra / 2 - 2, false, false);
         if (this.haveDrawExtraElement) {
            oSB.setColor(new Color(colorGradientLoading.r, colorGradientLoading.g, colorGradientLoading.b, colorGradientLoading.a * ANIMATION_ALPHA));
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - CFG.PADDING,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - CFG.PADDING * 2,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING * 2,
                  false,
                  false
               );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F * ANIMATION_ALPHA));
            Images.gradientFull
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - 1,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  1,
                  false,
                  false
               );
         }

         oSB.setColor(Color.WHITE);
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.975F * ANIMATION_ALPHA));
         Renderer.drawBoxHover(oSB, nPosX + tempScrollX, nPosY, this.iWidth, this.iHeight, ANIMATION_ALPHA);
         oSB.setColor(new Color(colorGradient.r, colorGradient.g, colorGradient.b, colorGradient.a * ANIMATION_ALPHA));
         Images.gradientXY.draw(oSB, nPosX + tempScrollX, nPosY + this.iHeight / 2, this.iWidth, this.iHeight - this.iHeight / 2 - 2, false, false);
         if (this.haveDrawExtraElement) {
            oSB.setColor(new Color(colorGradientLoading.r, colorGradientLoading.g, colorGradientLoading.b, colorGradientLoading.a * ANIMATION_ALPHA));
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - CFG.PADDING,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - CFG.PADDING * 2,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING * 2,
                  false,
                  false
               );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F * ANIMATION_ALPHA));
            Images.gradientFull
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - 1,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  1,
                  false,
                  false
               );
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, ANIMATION_ALPHA));
      this.draw_HoverWithoutAnimation(oSB, nPosX, nPosY, tempScrollX, ANIMATION_ALPHA, drawExtra);
      oSB.setColor(Color.WHITE);
   }

   @Override
   public final void draw_HoverWithoutAnimation(SpriteBatch oSB, int nPosX, int nPosY) {
      int tempScrollX = this.getScrollPosX();
      boolean drawExtra = CFG.currentTimeMillis - lTimeDrawExtra >= DRAW_EXTRA_TIME;
      if (drawExtra) {
         if (nPosY + this.iHeightDrawExtra > CFG.GAME_HEIGHT - CFG.PADDING * 2) {
            nPosY = CFG.GAME_HEIGHT - CFG.PADDING * 2 - this.iHeightDrawExtra;
         }

         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.975F));
         Renderer.drawBoxHover(oSB, nPosX + tempScrollX, nPosY, this.iWidth, this.iHeightDrawExtra, 1.0F);
         oSB.setColor(colorGradient);
         Images.gradientXY
            .draw(oSB, nPosX + tempScrollX, nPosY + this.iHeightDrawExtra / 2, this.iWidth, this.iHeightDrawExtra - this.iHeightDrawExtra / 2 - 2, false, false);
         if (this.haveDrawExtraElement) {
            oSB.setColor(new Color(colorGradientLoading.r, colorGradientLoading.g, colorGradientLoading.b, colorGradientLoading.a));
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - CFG.PADDING,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - CFG.PADDING * 2,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING * 2,
                  false,
                  false
               );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
            Images.gradientFull
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeightDrawExtra - 1,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  1,
                  false,
                  false
               );
         }

         oSB.setColor(Color.WHITE);
      } else {
         if (nPosY + this.iHeight > CFG.GAME_HEIGHT - CFG.PADDING * 2) {
            nPosY = CFG.GAME_HEIGHT - CFG.PADDING * 2 - this.iHeight;
         }

         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.975F));
         Renderer.drawBoxHover(oSB, nPosX + tempScrollX, nPosY, this.iWidth, this.iHeight, 1.0F);
         oSB.setColor(colorGradient);
         Images.gradientXY.draw(oSB, nPosX + tempScrollX, nPosY + this.iHeight / 2, this.iWidth, this.iHeight - this.iHeight / 2 - 2, false, false);
         if (this.haveDrawExtraElement) {
            oSB.setColor(new Color(colorGradientLoading.r, colorGradientLoading.g, colorGradientLoading.b, colorGradientLoading.a));
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - CFG.PADDING,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - CFG.PADDING * 2,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  CFG.PADDING * 2,
                  false,
                  false
               );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
            Images.gradientFull
               .draw(
                  oSB,
                  nPosX + tempScrollX,
                  nPosY + this.iHeight - 1,
                  (int)(this.iWidth * Math.min((float)(CFG.currentTimeMillis - lTimeDrawExtra) / DRAW_EXTRA_TIME, 1.0F)),
                  1,
                  false,
                  false
               );
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
      this.draw_HoverWithoutAnimation(oSB, nPosX, nPosY, tempScrollX, 1.0F, drawExtra);
      oSB.setColor(Color.WHITE);
   }

   public final void draw_HoverWithoutAnimation(SpriteBatch oSB, int nPosX, int nPosY, int tempScrollX, float fAlpha, boolean drawExtra) {
      int i = 0;

      for (int tempPosY = 0; i < this.iElementsSize; i++) {
         if (drawExtra || this.lElements.get(i).drawElement) {
            this.lElements.get(i).draw(oSB, nPosX + tempScrollX + getDrawExtraXPos(), nPosY + CFG.PADDING * 2 + tempPosY, fAlpha, this.iMaxWidth);
            tempPosY += this.lElements.get(i).getHeight() + CFG.PADDING;
         }
      }
   }

   public int getMinPosX() {
      return 0;
   }
}
