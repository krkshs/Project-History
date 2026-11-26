package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Flag implements MenuElement_HoverElement_Type {
   private int iCivID;
   private int offsetLeft = 0;
   private int offsetRight = 0;

   public MenuElement_HoverElement_Type_Flag(int iCivID) {
      this.iCivID = iCivID;
      this.offsetLeft = 0;
      this.offsetRight = CFG.PADDING;
   }

   public MenuElement_HoverElement_Type_Flag(int iCivID, int offsetLeft) {
      this.iCivID = iCivID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = CFG.PADDING;
   }

   public MenuElement_HoverElement_Type_Flag(int iCivID, int offsetLeft, int offsetRight) {
      this.iCivID = iCivID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));

      try {
         if (this.iCivID >= 0) {
            Game.getCiv(this.iCivID)
               .getFlag()
               .draw(
                  oSB,
                  nPosX + this.offsetLeft,
                  nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F),
                  (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale()),
                  (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale())
               );
         } else {
            ImageManager.getImage(Images.randomCivilizationFlag)
               .draw(
                  oSB,
                  nPosX + this.offsetLeft,
                  nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F),
                  (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale()),
                  (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale())
               );
         }
      } catch (IndexOutOfBoundsException var7) {
         ImageManager.getImage(Images.randomCivilizationFlag)
            .draw(
               oSB,
               nPosX + this.offsetLeft,
               nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F),
               (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale()),
               (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale())
            );
      }

      ImageManager.getImage(Images.flag_rect)
         .draw(
            oSB,
            nPosX + this.offsetLeft,
            nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F),
            (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale()),
            (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale())
         );
   }

   @Override
   public int getWidth() {
      return this.offsetRight + this.offsetLeft + (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale());
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING;
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT * 1.2F;
   }
}
