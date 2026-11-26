package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonFlag2_CivName_Random extends Button {
   public ButtonFlag2_CivName_Random(int iPosX, int iPosY, boolean isClickable) {
      this.init(
         Game.lang.get("Random"),
         CFG.FONT_REGULAR_SMALL,
         -1,
         iPosX,
         iPosY,
         Math.max(CFG.BUTTON_WIDTH, ImageManager.getImage(Images.flagOverDefault).getHeight()),
         ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2,
         isClickable,
         true,
         false,
         false
      );
      int tWMax = 0;

      while (this.iTextWidth >= this.getWidth() - (this.iTextPositionX > 0 ? this.iTextPositionX : 0) - CFG.PADDING && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      int nY;
      int nH;
      if (!this.getIsHovered() && !isActive) {
         nY = this.getPosY() + CFG.TEXT_HEIGHT + CFG.PADDING * 2;
         nH = this.getHeight() - CFG.TEXT_HEIGHT - CFG.PADDING * 2;
      } else {
         nY = this.getPosY();
         nH = this.getHeight();
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, nY + iTranslateY, this.getWidth(), nH, 1.0F);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.dice)
         .draw(
            oSB,
            this.getPosX() + iTranslateX + (this.getWidth() - ImageManager.getImage(Images.dice).getWidth()) / 2,
            nY + iTranslateY + (ImageManager.getImage(Images.flagOverDefault).getHeight() - ImageManager.getImage(Images.dice).getHeight()) / 2
         );
      if (this.getIsHovered() || isActive) {
         Renderer.drawText(
            oSB,
            this.fontID,
            this.sText,
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY()
               + ImageManager.getImage(Images.flagOverDefault).getHeight()
               + (CFG.TEXT_HEIGHT + CFG.PADDING * 2) / 2
               - this.getTextHeight() / 2
               + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RandomCivilization"), Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.dice, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements, true);
   }

   @Override
   public void actionElement() {
      try {
         List<Integer> civs = new ArrayList<>();

         for (int i = 0; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               civs.add(i);
            }
         }

         if (!civs.isEmpty()) {
            int civID = civs.get(Game.oR.nextInt(civs.size()));
            if (Game.player.iCivID != civID) {
               Game.player.iCivID = civID;
               Game.menuManager.rebuildNewGameCiv();
               if (Game.getCiv(Game.player.iCivID).getCapitalProvinceID() >= 0) {
                  Game.mapCoords.centerToProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                  Game.setActiveProvinceID(Game.getCiv(Game.player.iCivID).getCapitalProvinceID());
                  ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  Game.menuManager.addToastGold(Game.getCiv(Game.player.iCivID).getCivName(), Images.dice);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }
}
