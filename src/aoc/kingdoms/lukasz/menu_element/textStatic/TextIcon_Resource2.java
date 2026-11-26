package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Resource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_GoodsMarket;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextIcon_Resource2 extends Button {
   public int resourceID;
   public int maxWidth;

   public TextIcon_Resource2(String sText, int resourceID, int nPosX, int nPosY, int nWidth, int nHeight, int maxWidth) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.resourceID = resourceID;
      this.maxWidth = maxWidth;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.65F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxWidth, this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.6F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxWidth, this.getHeight());
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.65F : 0.9F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientFull
         .draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() - this.maxWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY
         .draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() - this.maxWidth, CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + this.maxWidth + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY,
            this.getWidth() - this.maxWidth,
            CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull
         .draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth() - this.maxWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() - this.maxWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull
         .draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth() - this.maxWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + this.maxWidth + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth() - this.maxWidth, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ResourcesManager.resourceImages
         .get(this.resourceID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ResourcesManager.resourceImages.get(this.resourceID).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ResourcesManager.resourceImages.get(this.resourceID).getHeight() / 2 + iTranslateY
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() - CFG.PADDING - this.iTextWidth + iTranslateX,
         this.getPosY() + this.getHeight() - this.iTextHeight - CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public int getCurrent() {
      return this.resourceID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (this.resourceID >= 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusResource(
               Game.lang.get("Resource") + ": ",
               ResourcesManager.getResourceName(this.resourceID),
               this.resourceID,
               CFG.FONT_REGULAR,
               CFG.FONT_BOLD,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusResource(
               Game.lang.get("Position") + ": ",
               this.getText().equals("#1") ? Game.lang.get("LargestProducer") : this.getText(),
               this.resourceID,
               CFG.FONT_REGULAR,
               CFG.FONT_BOLD,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Resource") + ": ", CFG.FONT_BOLD));
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("None"), CFG.FONT_BOLD, Colors.HOVER_RIGHT));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (this.resourceID >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Price") + ": ", CFG.FONT_BOLD_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(ResourcesManager.getPrice(this.resourceID), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         if (ResourcesManager.lResources.get(this.resourceID).uniqueBuildingID >= 0) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UniqueBuilding") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + BuildingsManager.buildings.get(ResourcesManager.lResources.get(this.resourceID).uniqueBuildingID).Name[0],
                  CFG.FONT_REGULAR_SMALL,
                  Colors.HOVER_RIGHT
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Resource(this.resourceID, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }

         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ResourcesManager.getResourceGroupName(ResourcesManager.lResources.get(this.resourceID).GroupID), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Empty());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(InGame_GoodsMarket.iActiveCivID).getCivName() + ": "));
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(ResourcesManager.getResourceName(this.resourceID), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements, true);
   }
}
