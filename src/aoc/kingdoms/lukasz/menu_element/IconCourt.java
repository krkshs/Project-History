package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IconCourt extends Button {
   public int imageID;
   public int id;
   public int widthDraw;

   public IconCourt(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int id, int widthDraw) {
      this.imageID = imageID;
      this.id = id;
      this.widthDraw = widthDraw;
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      int posX = this.getPosX() + iTranslateX + Game.settingsManager.IN_GAME_LEFT_PADDING_EXTRA;
      int posY = this.getPosY() + iTranslateY;
      if (InGame_CourtOptions2.isOptionHovered) {
         int extraX = 0;
         float perc = 1.0F;
         if (InGame_CourtOptions2.TEXT_TIME + InGame_CourtOptions2.TEXT_ANIMATION_TIME >= CFG.currentTimeMillis) {
            perc = (float)(CFG.currentTimeMillis - InGame_CourtOptions2.TEXT_TIME) / InGame_CourtOptions2.TEXT_ANIMATION_TIME;
            extraX = -InGame_CourtOptions2.textMaxWidth + (int)(InGame_CourtOptions2.textMaxWidth * perc);
         }

         posX += extraX + 1 + this.widthDraw;
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F * perc));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, posX, posY, InGame_CourtOptions2.textMaxWidth, this.getHeight() + 1);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F * perc));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, posX, posY, InGame_CourtOptions2.textMaxWidth, this.getHeight() + 1);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.75F * perc));
         Images.gradientFull.draw(oSB, posX, posY, InGame_CourtOptions2.textMaxWidth, this.getHeight() + 1);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE3.r, Colors.COLOR_GRADIENT_OVER_BLUE3.g, Colors.COLOR_GRADIENT_OVER_BLUE3.b, 0.15F * perc));
         ImageManager.getImage(Images.gradientXYVertical).draw(oSB, posX, posY, this.getTextWidth(), this.getHeight() + 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F * perc));
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, posX, posY + 1, InGame_CourtOptions2.textMaxWidth, 1);
         ImageManager.getImage(Images.gradientHorizontal).draw(oSB, posX, posY + this.getHeight() + 1 - 2, InGame_CourtOptions2.textMaxWidth, 1);
         posX -= this.widthDraw;
         oSB.setColor(Color.WHITE);
         if (extraX != 0) {
            Color nColor = this.getColor(isActive);
            nColor = new Color(nColor.r, nColor.g, nColor.b, Math.min(1.0F, nColor.a * perc));
            Renderer.drawText(
               oSB, this.fontID, this.getTextToDraw(), posX + super.getWidth() + CFG.PADDING, posY + this.getHeight() / 2 - this.iTextHeight / 2, nColor
            );
         } else {
            Renderer.drawTextWithShadow(
               oSB,
               this.fontID,
               this.getTextToDraw(),
               posX + super.getWidth() + CFG.PADDING,
               posY + this.getHeight() / 2 - this.iTextHeight / 2,
               this.getColor(isActive)
            );
         }

         posX -= extraX + 1;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.4F));
      Images.gradientXY.draw(oSB, posX, posY, this.widthDraw, this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      Images.gradientFull.draw(oSB, posX, posY, this.widthDraw, 1);
      Images.gradientFull.draw(oSB, posX, posY + this.getHeight() - 1, this.widthDraw, 1);
      oSB.setColor(Color.WHITE);
      if (InGame_CourtOptions.iActiveID == this.id) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 1.0F));
         ImageManager.getImage(Images.gradientXYVertical).draw(oSB, posX, posY, this.widthDraw, this.getHeight());
         ImageManager.getImage(Images.gradientXYVertical).draw(oSB, posX, posY + this.getHeight() / 2, this.widthDraw, this.getHeight() / 2);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.65F));
         ImageManager.getImage(Images.gradientXYVertical).draw(oSB, posX, posY, this.widthDraw, this.getHeight());
      }

      if (InGame_CourtOptions.iActiveID == this.id) {
         oSB.setColor(MainMenu.sparksColors);
         MenuManager.sparksAnimationSidebarActive.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      } else if (this.getIsHovered() || isActive) {
         oSB.setColor(MainMenu.sparksColors);
         MenuManager.sparksAnimationSidebar.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(Color.WHITE);
   }

   public static Color getColor_gradientXY() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F);
   }

   public static Color getColor_gradientFull() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.45F);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return InGame_CourtOptions.iActiveID == this.id ? Colors.HOVER_GOLD : super.getColor(isActive);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (!this.getIsHovered() && !isActive) {
         ImageManager.getImage(this.getImageID())
            .draw(
               oSB,
               this.getPosX() + iTranslateX + this.widthDraw / 2 - ImageManager.getImage(this.getImageID()).getWidth() / 2,
               this.getPosY() + iTranslateY + this.getHeight() / 2 - ImageManager.getImage(this.getImageID()).getHeight() / 2
            );
      } else {
         int imgW = (int)(ImageManager.getImage(this.getImageID()).getWidth() * 1.1F);
         int imgH = (int)(ImageManager.getImage(this.getImageID()).getHeight() * 1.1F);
         ImageManager.getImage(this.getImageID())
            .draw(oSB, this.getPosX() + iTranslateX + this.widthDraw / 2 - imgW / 2, this.getPosY() + iTranslateY + this.getHeight() / 2 - imgH / 2, imgW, imgH);
      }
   }

   @Override
   public int getWidth() {
      return InGame_CourtOptions2.isOptionHovered ? super.getWidth() + InGame_CourtOptions2.textMaxWidth / 2 : super.getWidth();
   }

   public int getImageID() {
      return this.imageID;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
