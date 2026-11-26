package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonIdeology_Court extends Button {
   public int ideologyID;
   public int imgWidth;
   public int imgHeight;

   public ButtonIdeology_Court(int ideologyID, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.ideologyID = ideologyID;
      this.init(
         Game.ideologiesManager.getIdeology(ideologyID).Name,
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         iWidth,
         iHeight,
         true,
         true,
         false,
         false
      );
      if (ImageManager.getImage(Images.population).getHeight() < Game.ideologiesManager.ideologiesImages.get(ideologyID).getHeight()) {
         float fScale = (float)ImageManager.getImage(Images.population).getHeight() / Game.ideologiesManager.ideologiesImages.get(ideologyID).getHeight();
         this.imgWidth = (int)(Game.ideologiesManager.ideologiesImages.get(ideologyID).getWidth() * fScale);
         this.imgHeight = (int)(Game.ideologiesManager.ideologiesImages.get(ideologyID).getHeight() * fScale);
      } else {
         this.imgWidth = Game.ideologiesManager.ideologiesImages.get(ideologyID).getWidth();
         this.imgHeight = Game.ideologiesManager.ideologiesImages.get(ideologyID).getHeight();
      }

      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(TextIcon2.getColor_gradientXY());
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - getTextHeightBG() + iTranslateY, this.getWidth(), getTextHeightBG());
      oSB.setColor(new Color(TextIcon2.getColor_gradientFull().r, TextIcon2.getColor_gradientFull().g, TextIcon2.getColor_gradientFull().b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - getTextHeightBG() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Game.ideologiesManager
         .ideologiesImages
         .get(this.ideologyID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.imgWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - getTextHeightBG()) / 2 - this.imgHeight / 2 + iTranslateY,
            this.imgWidth,
            this.imgHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - getTextHeightBG() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static int getTextHeightBG() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Government") + ": ", CFG.FONT_BOLD));
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.ideologiesManager.getIdeology(this.ideologyID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats(isActive, this.getIsHovered());
   }

   @Override
   public int getCurrent() {
      return this.ideologyID;
   }
}
