package aoc.kingdoms.lukasz.menusInGame.Info;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_InfoTop;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Info_2 extends Menu {
   public static int iCivID = 0;
   public static int iCivID2 = 0;
   public static int TIME_IN_VIEW = 3500;
   public final int ANIMATION_TIME = 225;
   public long lTime = 0L;
   public static int iInfoY;
   public static boolean hideAnimation = false;
   public static float fAnimationPerc = 1.0F;
   public static int iAnimationWidth = 1;

   public InGame_Info_2(String sText, String sText2) {
      List<MenuElement> menuElements = new ArrayList<>();
      iInfoY = ButtonFlag.getButtonHeight() + CFG.PADDING * 4;
      menuElements.add(
         new Text_InfoTop(
            sText,
            CFG.FONT_REGULAR,
            -1,
            CFG.GAME_WIDTH / 2 - this.getInfoBoxWidth() / 2,
            iInfoY,
            ImageManager.getImage(Images.infoBox2).getWidth(),
            ImageManager.getImage(Images.infoBox2).getHeight() * 3 / 5
         ) {
            @Override
            public void actionElement() {
               InGame_Info_2.this.setVisible(false);
            }

            @Override
            protected Color getColor(boolean isActive) {
               if (isActive) {
                  return new Color(
                     Colors.COLOR_TOP_STATS_ACTIVE.r, Colors.COLOR_TOP_STATS_ACTIVE.g, Colors.COLOR_TOP_STATS_ACTIVE.b, 1.0F * InGame_Info_2.fAnimationPerc
                  );
               } else {
                  return this.getIsHovered()
                     ? new Color(
                        Colors.COLOR_TOP_STATS_HOVER.r, Colors.COLOR_TOP_STATS_HOVER.g, Colors.COLOR_TOP_STATS_HOVER.b, 1.0F * InGame_Info_2.fAnimationPerc
                     )
                     : new Color(Colors.COLOR_INFO_BOX2.r, Colors.COLOR_INFO_BOX2.g, Colors.COLOR_INFO_BOX2.b, 1.0F * InGame_Info_2.fAnimationPerc);
               }
            }
         }
      );
      menuElements.add(
         new Text_InfoTop(
            sText2,
            CFG.FONT_REGULAR_SMALL,
            -1,
            CFG.GAME_WIDTH / 2 - this.getInfoBoxWidth() / 2,
            iInfoY + ImageManager.getImage(Images.infoBox2).getHeight() / 2,
            ImageManager.getImage(Images.infoBox2).getWidth(),
            ImageManager.getImage(Images.infoBox2).getHeight() / 2
         ) {
            @Override
            protected Color getColor(boolean isActive) {
               if (isActive) {
                  return new Color(
                     Colors.COLOR_TOP_STATS_ACTIVE.r, Colors.COLOR_TOP_STATS_ACTIVE.g, Colors.COLOR_TOP_STATS_ACTIVE.b, 1.0F * InGame_Info_2.fAnimationPerc
                  );
               } else {
                  return this.getIsHovered()
                     ? new Color(
                        Colors.COLOR_TOP_STATS_HOVER.r, Colors.COLOR_TOP_STATS_HOVER.g, Colors.COLOR_TOP_STATS_HOVER.b, 1.0F * InGame_Info_2.fAnimationPerc
                     )
                     : new Color(Colors.COLOR_INFO_BOX2_BOT.r, Colors.COLOR_INFO_BOX2_BOT.g, Colors.COLOR_INFO_BOX2_BOT.b, 1.0F * InGame_Info_2.fAnimationPerc);
               }
            }

            @Override
            public void actionElement() {
               InGame_Info_2.this.setVisible(false);
            }
         }
      );
      InGame_Info.imgID = Images.infoLegacy;
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, false);
      Game.soundsManager.playSound(SoundsManager.INFO_BOX);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (hideAnimation) {
         if (this.lTime + 225L >= CFG.currentTimeMillis) {
            fAnimationPerc = 1.0F - (float)(CFG.currentTimeMillis - this.lTime) / 225.0F;
            fAnimationPerc = Math.max(0.0F, fAnimationPerc);
         } else {
            fAnimationPerc = 0.0F;
            this.setVisible(false);
         }

         iAnimationWidth = (int)(ImageManager.getImage(Images.infoBox2).getWidth() * fAnimationPerc);
      } else {
         if (this.lTime + 225L >= CFG.currentTimeMillis) {
            fAnimationPerc = (float)(CFG.currentTimeMillis - this.lTime) / 225.0F;
            fAnimationPerc = Math.min(1.0F, fAnimationPerc);
         } else {
            fAnimationPerc = 1.0F;
         }

         iAnimationWidth = (int)(ImageManager.getImage(Images.infoBox2).getWidth() * fAnimationPerc);
         if (this.lTime + TIME_IN_VIEW <= CFG.currentTimeMillis) {
            hideAnimation = true;
            this.lTime = CFG.currentTimeMillis;
         }
      }

      this.drawInfoBox(oSB, CFG.GAME_WIDTH / 2 - this.getInfoBoxWidthTotalAnimation() / 2, iInfoY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public void drawInfoBox(SpriteBatch oSB, int iX, int iY) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F * fAnimationPerc));
      Images.gradientXY
         .draw(
            oSB,
            iX,
            iY - this.getInfoBoxHeightTotal() / 4,
            this.getInfoBoxWidthTotalAnimation(),
            this.getInfoBoxHeightTotal() / 2 + this.getInfoBoxHeightTotal() / 4
         );
      Images.gradientXY
         .draw(oSB, iX, iY + this.getInfoBoxHeightTotal() / 2, this.getInfoBoxWidthTotalAnimation(), this.getInfoBoxHeightTotal() * 3 / 4, false, true);
      oSB.setColor(Color.WHITE);
      if (hideAnimation) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F * fAnimationPerc));
      }

      ImageManager.getImage(InGame_Info.imgID)
         .draw(
            oSB,
            iX + this.getInfoBoxWidthTotalAnimation() / 2 - (int)(ImageManager.getImage(InGame_Info.imgID).getWidth() * (0.5F + 0.5F * fAnimationPerc) / 2.0F),
            iY + this.getInfoBoxHeightTotal() / 2 - (int)(ImageManager.getImage(InGame_Info.imgID).getHeight() * (0.5F + 0.5F * fAnimationPerc) / 2.0F),
            (int)(ImageManager.getImage(InGame_Info.imgID).getWidth() * (0.5F + 0.5F * fAnimationPerc)),
            (int)(ImageManager.getImage(InGame_Info.imgID).getHeight() * (0.5F + 0.5F * fAnimationPerc))
         );
      ImageManager.getImage(Images.infoBox2).draw(oSB, iX, iY, iAnimationWidth, ImageManager.getImage(Images.infoBox2).getHeight());
      oSB.setColor(Color.WHITE);
   }

   public int getInfoBoxWidth() {
      return ImageManager.getImage(Images.infoBox2).getWidth();
   }

   public int getInfoBoxHeightTotal() {
      return ImageManager.getImage(Images.infoBox2).getHeight();
   }

   public int getInfoBoxWidthTotalAnimation() {
      return iAnimationWidth;
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      if (visible) {
         this.lTime = CFG.currentTimeMillis;
         hideAnimation = false;
      }
   }
}
