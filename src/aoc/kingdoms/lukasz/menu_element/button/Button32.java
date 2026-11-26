package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button32 extends Button {
   public int iconImageID;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 750;
   private static final Color colorLine = new Color(0.5176471F, 0.43529412F, 0.25882354F, 0.55F);
   public static final Color buttonColor = new Color(0.627451F, 0.50980395F, 0.29411766F, 1.0F);

   public Button32(int iconImageID) {
      this.iconImageID = iconImageID;
      this.init(
         this.sText,
         this.fontID,
         this.iTextPositionX,
         0,
         0,
         ImageManager.getImage(iconImageID).getWidth(),
         ImageManager.getImage(iconImageID).getHeight(),
         true,
         true,
         false,
         false
      );
   }

   public Button32(int iconImageID, int nPosX, int nPosY) {
      this.iconImageID = iconImageID;
      this.init(
         this.sText,
         this.fontID,
         this.iTextPositionX,
         nPosX,
         nPosY,
         ImageManager.getImage(iconImageID).getWidth(),
         ImageManager.getImage(iconImageID).getHeight(),
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      if ((isActive || this.getIsHovered()) && this.getClickable()) {
         oSB.setColor(COLOR_BUTTON_MENU_HOVER_BG);
         if (animationState >= 0) {
            if (animationState == 0) {
               float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 750.0F, 1.0F);
               oSB.setColor(getColorLine());
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + iTranslateX,
                     this.getPosY() + 1 + iTranslateY,
                     (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + iTranslateX,
                     this.getPosY() + this.getHeight() - 2 + iTranslateY,
                     (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               if (lTimeAnimation < CFG.currentTimeMillis - 750L) {
                  animationState++;
                  lTimeAnimation = CFG.currentTimeMillis;
               }
            } else {
               float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 750.0F, 1.0F);
               oSB.setColor(getColorLine());
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                     this.getPosY() + 1 + iTranslateY,
                     this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                     this.getPosY() + this.getHeight() - 2 + iTranslateY,
                     this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               if (lTimeAnimation < CFG.currentTimeMillis - 750L) {
                  animationState = 0;
                  lTimeAnimation = CFG.currentTimeMillis;
               }
            }

            oSB.setColor(Color.WHITE);
         }
      }

      oSB.setColor(this.getColor(isActive));
      ImageManager.getImage(this.iconImageID).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, false, this.getFlipY());
      oSB.setColor(Color.WHITE);
   }

   public int getButtonBG() {
      return Images.buttonMenu;
   }

   public int getButtonBG_Active() {
      return Images.buttonMenuH;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }

   protected static final Color getColorLine() {
      return colorLine;
   }

   @Override
   public int getWidth() {
      return ImageManager.getImage(this.iconImageID).getWidth();
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(this.iconImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.COLOR_TOP_STATS_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.COLOR_TOP_STATS_HOVER;
      } else {
         return this.isActiveView() ? Colors.COLOR_TOP_STATS : buttonColor;
      }
   }

   public boolean isActiveView() {
      return false;
   }

   public boolean getFlipY() {
      return false;
   }
}
