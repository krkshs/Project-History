package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_BattleArmy implements MenuElement_HoverElement_Type {
   public String sText;
   public int iTextWidth;
   public int iUnitTypeID;
   public int iArmyID;
   public int unitImgWidth = 0;
   public int offsetLeft;

   public MenuElement_HoverElement_Type_BattleArmy(String nArmy, int niUnitTypeID, int nArmyID, int offsetLeft) {
      this.sText = CFG.getNumberWithSpaces(nArmy);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iUnitTypeID = niUnitTypeID;
      this.iArmyID = nArmyID;
      this.offsetLeft = offsetLeft;
      this.unitImgWidth = (int)(
         ArmyManager.armyImages.get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID).getWidth()
            * (
               (float)ImageManager.getImage(Images.unitsFrameBattle).getHeight()
                  / ArmyManager.armyImages.get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID).getHeight()
            )
      );
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

   public static int getStatsHeight() {
      return CFG.TEXT_HEIGHT_SMALL + CFG.PADDING * 2;
   }

   @Override
   public int getWidth() {
      return ImageManager.getImage(Images.unitsFrameBattle).getWidth() + this.offsetLeft;
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(Images.unitsFrameBattle).getHeight() + getStatsHeight();
   }
}
