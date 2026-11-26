package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleTechTree extends MenuTitle {
   public MenuTitleTechTree(String sText, int iHeight, boolean moveable, boolean resizable) {
      super(sText, iHeight, moveable, resizable);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      ImageManager.getImage(Images.titleTechTree)
         .draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth - ImageManager.getImage(Images.titleTechTree).getWidth(), this.getHeight());
      ImageManager.getImage(Images.titleTechTree)
         .draw2(
            oSB,
            nPosX + nWidth - ImageManager.getImage(Images.titleTechTree).getWidth(),
            nPosY - this.getHeight(),
            ImageManager.getImage(Images.titleTechTree).getWidth(),
            this.getHeight(),
            true,
            false
         );
      ImageManager.getImage(Images.titleTechTreeOver).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
      oSB.setColor(new Color(0.57254905F, 0.49411765F, 0.29411766F, 1.0F));
      Images.gradientFull.draw(oSB, nPosX, nPosY - 1, nWidth, 1);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.45F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, nPosX, nPosY - this.getHeight() / 2, nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2, 1, true, false);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            nPosX + nWidth / 2 + this.getTextWidth() / 2 + CFG.PADDING * 2,
            nPosY - this.getHeight() / 2,
            nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2,
            1
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, nPosX, nPosY - 1 - this.getHeight() / 2, nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2, 1, true, false);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            nPosX + nWidth / 2 + this.getTextWidth() / 2 + CFG.PADDING * 2,
            nPosY - 1 - this.getHeight() / 2,
            nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2,
            1
         );
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, nPosX, nPosY + 1 - this.getHeight() / 2, nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2, 1, true, false);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            nPosX + nWidth / 2 + this.getTextWidth() / 2 + CFG.PADDING * 2,
            nPosY + 1 - this.getHeight() / 2,
            nWidth / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2,
            1
         );
      oSB.setColor(Color.WHITE);
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }

   public void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      ImageManager.getImage(Images.titleTechTree)
         .draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth - ImageManager.getImage(Images.titleTechTree).getWidth(), this.getHeight());
      ImageManager.getImage(Images.titleTechTree)
         .draw2(
            oSB,
            nPosX + nWidth - ImageManager.getImage(Images.titleTechTree).getWidth(),
            nPosY - this.getHeight(),
            ImageManager.getImage(Images.titleTechTree).getWidth(),
            this.getHeight(),
            true,
            false
         );
      ImageManager.getImage(Images.titleTechTreeOver).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
      oSB.setColor(Color.WHITE);
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }
}
