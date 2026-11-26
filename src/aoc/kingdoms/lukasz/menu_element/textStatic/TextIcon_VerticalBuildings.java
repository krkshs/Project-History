package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon_VerticalBuildings extends Button {
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int lastValue = -997654;
   public int lastValue2 = -997654;
   public Color mainTextColor = Colors.BUTTON_TEXT;

   public TextIcon_VerticalBuildings(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
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
         this.getColor(isActive),
         90.0F
      );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT_SMALL / ImageManager.getImage(iImageID).getWidth();
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(InGame_ProvinceInfo.iProvinceID).iBuildingsLimit
         || this.lastValue2 != Game.getProvince(InGame_ProvinceInfo.iProvinceID).getUsedBuildingsSlots()) {
         this.setText(
            ""
               + Game.getProvince(InGame_ProvinceInfo.iProvinceID).getUsedBuildingsSlots()
               + " / "
               + Game.getProvince(InGame_ProvinceInfo.iProvinceID).iBuildingsLimit
         );
         this.lastValue = Game.getProvince(InGame_ProvinceInfo.iProvinceID).iBuildingsLimit;
         this.lastValue2 = Game.getProvince(InGame_ProvinceInfo.iProvinceID).getUsedBuildingsSlots();
         if (this.lastValue == this.lastValue2) {
            this.mainTextColor = Colors.COLOR_TEXT_NEGATIVE;
         } else {
            this.mainTextColor = Colors.BUTTON_TEXT;
         }
      }

      return super.getTextToDraw();
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? this.mainTextColor : Colors.BUTTON_TEXT_DISABLED;
      }
   }
}
