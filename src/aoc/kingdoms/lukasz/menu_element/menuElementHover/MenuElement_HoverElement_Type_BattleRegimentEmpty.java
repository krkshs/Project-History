package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_BattleRegimentEmpty implements MenuElement_HoverElement_Type {
   public int iCivID;
   public int iImageID;
   public int offsetLeft;
   public int offsetRight;

   public MenuElement_HoverElement_Type_BattleRegimentEmpty(int iCivID, int iImageID) {
      this.init(iCivID, iImageID, 0, CFG.PADDING / 2);
   }

   public MenuElement_HoverElement_Type_BattleRegimentEmpty(int iCivID, int iImageID, int offsetLeft) {
      this.init(iCivID, iImageID, offsetLeft, CFG.PADDING / 2);
   }

   public MenuElement_HoverElement_Type_BattleRegimentEmpty(int iCivID, int iImageID, int offsetLeft, int offsetRight) {
      this.init(iCivID, iImageID, offsetLeft, offsetRight);
   }

   private final void init(int iCivID, int iImageID, int offsetLeft, int offsetRight) {
      this.iCivID = iCivID;
      this.iImageID = iImageID;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(Game.getCiv(this.iCivID).getR(), Game.getCiv(this.iCivID).getG(), Game.getCiv(this.iCivID).getB(), 0.225F * nAlpha));
      Images.pix
         .draw(
            oSB,
            nPosX + this.offsetLeft,
            nPosY + CFG.PADDING / 2,
            ImageManager.getImage(this.iImageID).getWidth(),
            ImageManager.getImage(this.iImageID).getHeight()
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F * nAlpha));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX + this.offsetLeft,
            nPosY + CFG.PADDING / 2,
            ImageManager.getImage(this.iImageID).getWidth(),
            ImageManager.getImage(this.iImageID).getHeight()
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F * nAlpha));
      ImageManager.getImage(this.iImageID).draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING / 2);
   }

   @Override
   public int getWidth() {
      return this.offsetLeft + this.offsetRight + ImageManager.getImage(this.iImageID).getWidth();
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(this.iImageID).getHeight();
   }
}
