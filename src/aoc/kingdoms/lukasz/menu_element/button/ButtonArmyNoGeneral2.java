package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonArmyNoGeneral2 extends Button {
   public int iCivID;
   public boolean canAssign;

   public ButtonArmyNoGeneral2(String sName, int iCivID, int iPosX, int iPosY, boolean canAssign) {
      this.init(
         sName,
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.generalFrameBattle).getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
      this.iCivID = iCivID;
      this.canAssign = canAssign;
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
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.generalFrameBattle).getWidth(),
            ImageManager.getImage(Images.generalFrameBattle).getHeight()
         );
      }

      Game.generalManager
         .noGeneral
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.generalFrameBattle).getWidth(),
            ImageManager.getImage(Images.generalFrameBattle).getHeight()
         );
      ImageManager.getImage(Images.generalFrameBattle).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(
            oSB,
            Images.statsRectBGBorder,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.generalFrameBattle).getWidth(),
            ImageManager.getImage(Images.generalFrameBattle).getHeight(),
            1.0F
         );
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight()
         );
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight(),
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB, this.getPosX() + iTranslateX, this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY, 1, getStatsHeight()
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - 1 + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + iTranslateY,
            1,
            getStatsHeight()
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static int getStatsHeight() {
      return CFG.TEXT_HEIGHT_SMALL + CFG.PADDING * 2;
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.generalFrameBattle).getHeight() + getStatsHeight();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("NoGeneral"), CFG.FONT_BOLD));
      if (this.iCivID < 0) {
         nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.rebelsFlag, CFG.PADDING, 0));
      } else {
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.iCivID, CFG.PADDING, 0));
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (this.canAssign) {
         nData.add(new MenuElement_HoverElement_Type_Empty());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectGeneralToRecruit"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements, true);
      } else {
         this.menuElementHover = new MenuElement_Hover(nElements);
      }
   }
}
