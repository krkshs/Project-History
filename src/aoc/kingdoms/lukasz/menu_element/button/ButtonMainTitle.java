package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonMainTitle extends Button {
   private final int LOGO_PADDING = 30;
   private int logoWidth;
   private int logoHeight;

   public ButtonMainTitle(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, ImageManager.getImage(Images.mainTitle).getHeight(), isClickable, true, false, false);
      float fScale = (ImageManager.getImage(Images.mainTitle).getHeight() - 60.0F) / ImageManager.getImage(Images.logo).getHeight();
      this.logoWidth = (int)(ImageManager.getImage(Images.logo).getWidth() * fScale);
      this.logoHeight = (int)(ImageManager.getImage(Images.logo).getHeight() * fScale);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawBox_EDGE_TOP_LR(oSB, Images.mainTitle, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(DiplomacyManager.COLOR_WAR.r, DiplomacyManager.COLOR_WAR.g, DiplomacyManager.COLOR_WAR.b, 0.25F));
      MenuManager.sparksAnimationHover.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(0.57254905F, 0.49411765F, 0.29411766F, 1.0F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (isActive) {
         oSB.setColor(COLOR_BUTTON_MENU_HOVER_BG);
      } else if (this.getIsHovered() && this.getClickable()) {
         oSB.setColor(COLOR_BUTTON_MENU_HOVER_BG);
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, !this.getIsHovered() && !isActive ? 1.0F : 0.9F));
      ImageManager.getImage(Images.logo)
         .draw(oSB, this.getPosX() + (this.getWidth() - this.logoWidth) / 2 + iTranslateX, this.getPosY() + 30 + iTranslateY, this.logoWidth, this.logoHeight);
      oSB.setColor(Color.WHITE);
   }
}
