package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Title_BattleLine extends Text_Title_v2 {
   public int activeLine;

   public Text_Title_BattleLine(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int activeLine) {
      super(sText, CFG.FONT_BOLD, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.activeLine = activeLine;
   }

   @Override
   public void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.325F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.15F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      int tX = ImageManager.getImage(Images.battleArmy1).getWidth() + CFG.PADDING * 2;

      for (int i = 0; i < 2; i++) {
         oSB.setColor(
            new Color(
               Game.getCiv(Game.player.iCivID).getR(),
               Game.getCiv(Game.player.iCivID).getG(),
               Game.getCiv(Game.player.iCivID).getB(),
               1 == this.activeLine ? 1.0F : 0.225F
            )
         );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1 == this.activeLine ? 1.0F : 0.4F));
         ImageManager.getImage(Images.battleArmy1).draw(oSB, this.getPosX() + this.getWidth() - tX + iTranslateX, this.getPosY() + CFG.PADDING + iTranslateY);
         tX += ImageManager.getImage(Images.battleArmy0).getWidth() + CFG.PADDING / 2;
      }

      for (int i = 0; i < 6; i++) {
         oSB.setColor(
            new Color(
               Game.getCiv(Game.player.iCivID).getR(),
               Game.getCiv(Game.player.iCivID).getG(),
               Game.getCiv(Game.player.iCivID).getB(),
               0 == this.activeLine ? 1.0F : 0.225F
            )
         );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0 == this.activeLine ? 1.0F : 0.4F));
         ImageManager.getImage(Images.battleArmy0).draw(oSB, this.getPosX() + this.getWidth() - tX + iTranslateX, this.getPosY() + CFG.PADDING + iTranslateY);
         tX += ImageManager.getImage(Images.battleArmy0).getWidth() + CFG.PADDING / 2;
      }

      for (int i = 0; i < 2; i++) {
         oSB.setColor(
            new Color(
               Game.getCiv(Game.player.iCivID).getR(),
               Game.getCiv(Game.player.iCivID).getG(),
               Game.getCiv(Game.player.iCivID).getB(),
               1 == this.activeLine ? 1.0F : 0.225F
            )
         );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1 == this.activeLine ? 1.0F : 0.4F));
         ImageManager.getImage(Images.battleArmy1).draw(oSB, this.getPosX() + this.getWidth() - tX + iTranslateX, this.getPosY() + CFG.PADDING + iTranslateY);
         tX += ImageManager.getImage(Images.battleArmy0).getWidth() + CFG.PADDING / 2;
      }

      tX = ImageManager.getImage(Images.battleArmy1).getWidth() + CFG.PADDING * 2;

      for (int i = 0; i < 10; i++) {
         oSB.setColor(
            new Color(
               Game.getCiv(Game.player.iCivID).getR(),
               Game.getCiv(Game.player.iCivID).getG(),
               Game.getCiv(Game.player.iCivID).getB(),
               2 == this.activeLine ? 1.0F : 0.225F
            )
         );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING * 2 + ImageManager.getImage(Images.battleArmy0).getHeight() + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               ImageManager.getImage(Images.battleArmy0).getWidth(),
               ImageManager.getImage(Images.battleArmy0).getHeight()
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 2 == this.activeLine ? 1.0F : 0.4F));
         ImageManager.getImage(Images.battleArmy2)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - tX + iTranslateX,
               this.getPosY() + CFG.PADDING * 2 + ImageManager.getImage(Images.battleArmy0).getHeight() + iTranslateY
            );
         tX += ImageManager.getImage(Images.battleArmy0).getWidth() + CFG.PADDING / 2;
      }

      oSB.setColor(Color.WHITE);
      super.drawText(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats2(isActive, this.getIsHovered());
   }
}
