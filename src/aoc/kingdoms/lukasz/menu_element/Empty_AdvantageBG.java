package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivilizationAdvantages;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Empty_AdvantageBG extends Empty {
   public Empty_AdvantageBG(int iPosX, int iPosY, int iWidth, int iHeight) {
      super(iPosX, iPosY, iWidth, iHeight);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.3F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            this.getHeight()
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            this.getHeight(),
            false,
            true
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + 1 + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            1
         );
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + this.getHeight() - 2 + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            1
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            CFG.PADDING * 2
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            CFG.PADDING * 2,
            false,
            true
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            1
         );
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + this.getHeight() - 1 + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            1
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() - InGame_CivilizationAdvantages.iMenuWidth + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
            InGame_CivilizationAdvantages.iMenuWidth,
            CFG.PADDING,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   public boolean playSFX_Hovered() {
      return false;
   }
}
