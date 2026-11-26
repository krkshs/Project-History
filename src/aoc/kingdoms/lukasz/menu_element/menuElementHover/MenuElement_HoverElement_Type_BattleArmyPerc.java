package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_BattleArmyPerc extends MenuElement_HoverElement_Type_BattleArmy {
   public float fPerc;

   public MenuElement_HoverElement_Type_BattleArmyPerc(String nArmy, int niUnitTypeID, int nArmyID, int offsetLeft, float fPerc) {
      super(nArmy, niUnitTypeID, nArmyID, offsetLeft);
      this.fPerc = Math.min(fPerc, 1.0F);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      nPosX += this.offsetLeft;
      nPosY += CFG.PADDING;
      Renderer.clipView_Start(
         oSB,
         nPosX,
         CFG.GAME_HEIGHT - nPosY,
         ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
         -ImageManager.getImage(Images.unitsFrameBattle).getHeight()
      );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ArmyManager.armyImages
         .get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID)
         .draw(
            oSB,
            nPosX - this.unitImgWidth / 2 + ImageManager.getImage(Images.unitsFrameBattle).getWidth() / 2,
            nPosY,
            this.unitImgWidth,
            ImageManager.getImage(Images.unitsFrameBattle).getHeight()
         );
      Renderer.clipView_End(oSB);
      ImageManager.getImage(Images.unitsFrameBattle).draw(oSB, nPosX, nPosY);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX,
            nPosY + ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
            ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
            getStatsHeight()
         );
      if (this.fPerc >= 0.0F) {
         oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, 0.8F));
         Images.pix
            .draw(
               oSB,
               nPosX + 1,
               nPosY - 1 + ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
               (int)((ImageManager.getImage(Images.unitsFrameBattle).getWidth() - 2) * this.fPerc),
               CFG.PADDING / 2
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               nPosX + 1,
               nPosY - 1 + ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
               ImageManager.getImage(Images.unitsFrameBattle).getWidth() - 2,
               CFG.PADDING / 2
            );
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, nPosX, nPosY + ImageManager.getImage(Images.unitsFrameBattle).getHeight(), 1, getStatsHeight());
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX + ImageManager.getImage(Images.unitsFrameBattle).getWidth() - 1,
            nPosY + ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
            1,
            getStatsHeight()
         );
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText,
         nPosX + ImageManager.getImage(Images.unitsFrameBattle).getWidth() / 2 - this.iTextWidth / 2,
         nPosY + ImageManager.getImage(Images.unitsFrameBattle).getHeight() + CFG.PADDING,
         Colors.BUTTON_TEXT
      );
   }
}
