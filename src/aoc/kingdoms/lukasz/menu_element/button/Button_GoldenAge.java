package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_GoldenAge extends Button {
   public int imageID;
   public int progressIcon;
   public int progressIcon2;
   public int titleH;
   public int maxIconWidth;
   public String sProgressLeft;
   public String sProgressRight;
   public int iProgressLeftW;
   public int iProgressH;
   public String sProgressLeft2;
   public String sProgressRight2;
   public int iProgressLeftW2;
   public String sLevel;
   public int iLevelW;

   public Button_GoldenAge(
      String sText,
      int iPosX,
      int iPosY,
      int nWidth,
      int imageID,
      int progressIcon,
      int progressIcon2,
      String sProgressLeft,
      String sProgressRight,
      String sProgressLeft2,
      String sProgressRight2,
      String sLevel
   ) {
      this.progressIcon = progressIcon;
      this.progressIcon2 = progressIcon2;
      this.imageID = imageID;
      this.maxIconWidth = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4;
      this.titleH = Math.max(ImageManager.getImage(imageID).getHeight(), CFG.TEXT_HEIGHT);
      this.init(sText, CFG.FONT_REGULAR, 0, iPosX, iPosY, nWidth, this.getButtonHeight2(), true, true, false, false);
      this.sProgressLeft = sProgressLeft;
      this.sProgressRight = sProgressRight;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sProgressLeft);
      this.iProgressLeftW = (int)Renderer.glyphLayout.width;
      this.iProgressH = (int)Renderer.glyphLayout.height;
      this.sProgressLeft2 = sProgressLeft2;
      this.sProgressRight2 = sProgressRight2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sProgressLeft2);
      this.iProgressLeftW2 = (int)Renderer.glyphLayout.width;
      this.sLevel = sLevel;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sLevel);
      this.iLevelW = (int)Renderer.glyphLayout.width;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_NOTIFICATION_BG.r, Colors.COLOR_NOTIFICATION_BG.g, Colors.COLOR_NOTIFICATION_BG.b, !this.getIsHovered() && !isActive ? 0.25F : 0.5F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG.r, Colors.COLOR_NOTIFICATION_BG.g, Colors.COLOR_NOTIFICATION_BG.b, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() - 1 + iTranslateX, this.getPosY() - 1 + iTranslateY, this.getWidth() + 2, this.getHeight() + 2, 1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.3F : 0.5F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getWidth() - CFG.PADDING * 2,
         this.titleH + CFG.PADDING * 4,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.3F));
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + CFG.PADDING + iTranslateY,
            this.getWidth() - CFG.PADDING * 2,
            this.titleH + CFG.PADDING * 4
         );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.45F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + iTranslateY,
         this.getWidth() - CFG.PADDING * 2,
         this.getStatH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + iTranslateY,
         this.maxIconWidth,
         this.getStatH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.45F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() + CFG.PADDING + iTranslateY,
         this.getWidth() - CFG.PADDING * 2,
         this.getStatH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() + CFG.PADDING + iTranslateY,
         this.maxIconWidth,
         this.getStatH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + ImageManager.getImage(this.imageID).getWidth() + CFG.PADDING * 2) / 2 + iTranslateX,
            this.getPosY() + this.titleH / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + CFG.PADDING * 3 + iTranslateY
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX()
            + this.getWidth() / 2
            - (this.getTextWidth() + ImageManager.getImage(this.imageID).getWidth() + CFG.PADDING * 2) / 2
            + ImageManager.getImage(this.imageID).getWidth()
            + CFG.PADDING * 2
            + iTranslateX,
         this.getPosY() + this.titleH / 2 - this.getTextHeight() / 2 + CFG.PADDING * 3 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sLevel,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iLevelW + iTranslateX,
         this.getPosY() + this.titleH / 2 - this.iProgressH / 2 + CFG.PADDING * 3 + iTranslateY,
         Colors.HOVER_RIGHT3
      );
      ImageManager.getImage(this.progressIcon)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - ImageManager.getImage(this.progressIcon).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() / 2 - ImageManager.getImage(this.progressIcon).getHeight() / 2 + iTranslateY
         );
      ImageManager.getImage(this.progressIcon2)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - ImageManager.getImage(this.progressIcon2).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + this.titleH
               + this.getStatH()
               + CFG.PADDING
               + CFG.PADDING * 6
               + this.getStatH() / 2
               - ImageManager.getImage(this.progressIcon2).getHeight() / 2
               + iTranslateY
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sProgressLeft,
         this.getPosX() + this.maxIconWidth + CFG.PADDING * 3 + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() / 2 - this.iProgressH / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sProgressRight,
         this.getPosX() + this.maxIconWidth + CFG.PADDING * 3 + this.iProgressLeftW + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() / 2 - this.iProgressH / 2 + iTranslateY,
         Colors.HOVER_GOLD
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sProgressLeft2,
         this.getPosX() + this.maxIconWidth + CFG.PADDING * 3 + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() + CFG.PADDING + this.getStatH() / 2 - this.iProgressH / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sProgressRight2,
         this.getPosX() + this.maxIconWidth + CFG.PADDING * 3 + this.iProgressLeftW2 + iTranslateX,
         this.getPosY() + this.titleH + CFG.PADDING * 6 + this.getStatH() + CFG.PADDING + this.getStatH() / 2 - this.iProgressH / 2 + iTranslateY,
         Colors.HOVER_GOLD
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover2(isActive, this.getIsHovered());
   }

   public int getButtonHeight2() {
      return this.titleH + CFG.PADDING * 8 + this.getStatH() * 2;
   }

   public int getStatH() {
      return this.titleH + CFG.PADDING * 4;
   }
}
