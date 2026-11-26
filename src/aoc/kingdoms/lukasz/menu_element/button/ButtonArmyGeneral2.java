package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonArmyGeneral2 extends ButtonArmyGeneral {
   public ButtonArmyGeneral2(
      String sName,
      int iCivID,
      int iAttack,
      int iDefense,
      int iPosX,
      int iPosY,
      int imageID,
      int iDay,
      int iMonth,
      int iYear,
      String sIMG,
      int combatExperience
   ) {
      super(sName, iCivID, iAttack, iDefense, iPosX, iPosY, imageID, iDay, iMonth, iYear, sIMG, combatExperience);
      this.setWidth(ImageManager.getImage(Images.generalFrameBattle).getWidth());
      this.setHeight(ImageManager.getImage(Images.generalFrameBattle).getHeight());
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.generalFrameBattle).getWidth(),
            ImageManager.getImage(Images.generalFrameBattle).getHeight()
         );
      }

      if (this.generalImage != null) {
         this.generalImage
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.generalFrameBattle).getWidth(),
               ImageManager.getImage(Images.generalFrameBattle).getHeight()
            );
      }

      ImageManager.getImage(Images.generalFrameBattle).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight()
         );
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight(),
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB, this.getPosX() + iTranslateX, this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY, 1, getStatsHeight()
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - 1 + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            1,
            getStatsHeight()
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
      ImageManager.getImage(Images.attack)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() - CFG.PADDING - attackIconHeight + iTranslateY,
            attackIconWidth,
            attackIconHeight
         );
      ImageManager.getImage(Images.defense)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - defenseIconWidth - CFG.PADDING + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() - CFG.PADDING - defenseIconHeight + iTranslateY,
            defenseIconWidth,
            defenseIconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sAttack,
         this.getPosX() + attackIconWidth + CFG.PADDING + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() - CFG.PADDING - CFG.TEXT_HEIGHT_SMALL + iTranslateY,
         Colors.BUTTON_TEXT_HOVERED
      );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sDefense,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - defenseIconWidth - this.iDefenseWidth + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() - CFG.PADDING - CFG.TEXT_HEIGHT_SMALL + iTranslateY,
         Colors.BUTTON_TEXT_HOVERED
      );
   }

   public static int getStatsHeight() {
      return CFG.TEXT_HEIGHT_SMALL + CFG.PADDING * 2;
   }
}
