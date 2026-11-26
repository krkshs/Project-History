package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonReligion extends Button {
   public int religionID;

   public ButtonReligion(int religionID, int iPosX, int iPosY) {
      this.religionID = religionID;
      this.init(
         Game.religionManager.getReligion(religionID).Name,
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         getButtonWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
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
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Game.religionManager
         .religionImages
         .get(this.religionID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - Game.religionManager.religionImages.get(this.religionID).getWidth() / 2 + iTranslateX,
            this.getPosY() + getImageHeight() / 2 - Game.religionManager.religionImages.get(this.religionID).getHeight() / 2 + iTranslateY
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.TEXT_HEIGHT - CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.9F : (isHovered ? 0.65F : 0.1F)) : 0.05F;
   }

   public static int getButtonWidth() {
      return Game.religionManager.maxWidth + CFG.PADDING * 4;
   }

   public static int getImageHeight() {
      return ButtonIdeology.maxHeight + CFG.PADDING * 4;
   }

   public static int getButtonHeight() {
      return getImageHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Religion") + ": ", CFG.FONT_BOLD));
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.religionManager.getReligion(this.religionID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
