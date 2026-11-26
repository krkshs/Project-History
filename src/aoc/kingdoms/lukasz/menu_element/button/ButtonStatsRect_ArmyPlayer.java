package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStatsRect_ArmyPlayer extends Button {
   public int id;
   public int imgID;

   public ButtonStatsRect_ArmyPlayer(String sText, int id, int imgID, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.id = id;
      this.imgID = imgID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      int imgW = Math.min(ImageManager.getImage(this.imgID).getWidth() / 2, this.getWidth() / 4);
      ImageManager.getImage(this.imgID)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - imgW / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imgID).getHeight() / 2 + iTranslateY,
            imgW,
            ImageManager.getImage(this.imgID).getHeight(),
            true,
            false
         );
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else {
         return this.getIsHovered() ? Colors.BUTTON_TEXT_HOVERED : Colors.HOVER_RIGHT2;
      }
   }

   @Override
   public void actionElement() {
      Game.player.playerData.armyImgID = this.id;
      Game.getCiv(Game.player.iCivID).updateArmyImgID_Player();
      Game.menuManager.addToastGold(Game.lang.get("Done"), Images.v);
   }
}
