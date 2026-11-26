package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Icon extends Button {
   public int imageID;
   public int id;

   public Icon(int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int id) {
      this.imageID = imageID;
      this.id = id;
      this.init("", CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static Color getColor_gradientXY() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F);
   }

   public static Color getColor_gradientFull() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.45F);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() / 2);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, this.getWidth(), this.getHeight() / 2, false, true);
      if (InGame_CourtOptions.iActiveID == this.id) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 1.0F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, this.getWidth(), this.getHeight() / 2);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.85F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.getImageID())
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.getImageID()).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.getImageID()).getHeight() / 2 + iTranslateY
         );
   }

   public int getImageID() {
      return this.imageID;
   }
}
