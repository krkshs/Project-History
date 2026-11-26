package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon_VerticalProductionIncome extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int iProvinceID;
   public float lastValue = -997654.3F;
   public float lastValueProductionEfficiency = -997654.3F;

   public TextIcon_VerticalProductionIncome(String sText, int imageID, int nProvinceID, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.iProvinceID = nProvinceID;
      this.lastValue = Game.getProvince(this.iProvinceID).getEconomyWithBonuses();
      this.lastValueProductionEfficiency = ResourcesManager.getProductionEfficiency(this.iProvinceID);
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
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BLUE.r,
            Colors.COLOR_GRADIENT_OVER_BLUE.g,
            Colors.COLOR_GRADIENT_OVER_BLUE.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive) / 2.0F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - this.iconHeight - CFG.PADDING * 2 + iTranslateY,
         this.getWidth(),
         this.iconHeight + CFG.PADDING * 2,
         1.0F
      );
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING - this.iconHeight + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawTextWithShadowRotated(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.PADDING * 2 - this.iconHeight) / 2 + this.getTextWidth() / 2 + iTranslateY,
         Colors.COLOR_TEXT_MODIFIER_POSITIVE,
         90.0F
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT_SMALL / ImageManager.getImage(iImageID).getWidth();
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.iProvinceID).getEconomyWithBonuses()
         || this.lastValueProductionEfficiency != ResourcesManager.getProductionEfficiency(this.iProvinceID)) {
         float tIncome = ResourcesManager.getMonthlyIncome(this.iProvinceID, Game.getProvince(this.iProvinceID).getResourceID());
         this.setText("" + CFG.getPrecision2(tIncome, tIncome >= 100.0F ? 10 : 100));
         this.lastValue = Game.getProvince(this.iProvinceID).getEconomyWithBonuses();
         this.lastValueProductionEfficiency = ResourcesManager.getProductionEfficiency(this.iProvinceID);
      }

      return super.getTextToDraw();
   }
}
