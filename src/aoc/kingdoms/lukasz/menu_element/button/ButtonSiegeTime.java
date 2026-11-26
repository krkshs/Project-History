package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceSiege;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonSiegeTime extends Button {
   public int lastValue = -89594215;
   public String sText2;
   public int iTextWidth2;

   public ButtonSiegeTime(String sText, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.sText2 = Game.lang.get("MaximumSiegeTime") + ": ";
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.getTextToDraw();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() * 2, false, true);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iTextWidth2) / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iTextWidth2) / 2 + this.iTextWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         Colors.getColorTopStats3(isActive, this.getIsHovered())
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public String getTextToDraw() {
      if (Game.battleManager.isBattleInProvince(InGame_ProvinceSiege.iProvinceID)) {
         if (this.lastValue != -999888) {
            this.setText(Game.lang.get("ArmyIsFightingInABattle"));
            this.lastValue = -999888;
         }
      } else if (SiegeManager.getSiegeDaysLeft(InGame_ProvinceSiege.iProvinceID) != this.lastValue) {
         this.lastValue = SiegeManager.getSiegeDaysLeft(InGame_ProvinceSiege.iProvinceID);
         this.setText("" + Game.lang.get("DaysX", Math.min(99999, this.lastValue)));
      }

      return super.getTextToDraw();
   }
}
