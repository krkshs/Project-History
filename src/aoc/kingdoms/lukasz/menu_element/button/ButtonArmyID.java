package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonArmyID extends Button {
   public int iCivID = 0;
   public int iProvinceID = 0;

   public ButtonArmyID(int iCivID, int iProvinceID, String nArmy, int iPosX, int iPosY, int nID) {
      this.init(nArmy, CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, 75, getButtonHeight(), true, true, false, false);
      this.setCurrent(nID);
      this.iCivID = iCivID;
      this.iProvinceID = iProvinceID;
      this.setWidth(ImageManager.getImage(Images.flag_rect).getWidth() + this.getTextWidth() + CFG.PADDING * 3);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            this.isArmyActive() ? 1.0F : getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.iCivID < 0) {
         ImageManager.getImage(Images.rebelsFlag)
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY,
               ImageManager.getImage(Images.flag_rect).getWidth(),
               ImageManager.getImage(Images.flag_rect).getHeight()
            );
      } else {
         Game.getCiv(this.iCivID)
            .getFlag()
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY,
               ImageManager.getImage(Images.flag_rect).getWidth(),
               ImageManager.getImage(Images.flag_rect).getHeight()
            );
      }

      ImageManager.getImage(Images.flag_rect)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + ImageManager.getImage(Images.flag_rect).getWidth() + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static int getButtonHeight() {
      return CFG.BUTTON_HEIGHT / 2 + CFG.PADDING;
   }

   public boolean isArmyActive() {
      return InGame_ProvinceArmy.iActiveID == this.getCurrent();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getProvince(this.iProvinceID).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText() + " ", CFG.FONT_BOLD, Colors.HOVER_IMPORTANT));
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Units"), CFG.FONT_BOLD));
      if (this.iCivID < 0) {
         nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.rebelsFlag, CFG.PADDING, 0));
      } else {
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.iCivID, CFG.PADDING, 0));
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
