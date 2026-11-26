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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Resource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ResourceTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextIcon_Resource_Small extends Button {
   public int resourceID;
   public int iconWidth;
   public int iconHeight;
   public int iProvinceID;

   public TextIcon_Resource_Small(int iProvinceID, String sText, int resourceID, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.resourceID = resourceID;
      this.iProvinceID = iProvinceID;
      if (resourceID >= 0) {
         float iconScale = Math.min(1.0F, (float)ImageManager.getImage(Images.gold).getHeight() / ResourcesManager.resourceImages.get(resourceID).getHeight());
         this.iconWidth = (int)(ResourcesManager.resourceImages.get(resourceID).getWidth() * iconScale);
         this.iconHeight = (int)(ResourcesManager.resourceImages.get(resourceID).getHeight() * iconScale);
      } else {
         float iconScale = Math.min(1.0F, (float)ImageManager.getImage(Images.gold).getHeight() / ImageManager.getImage(Images.resourceNone).getHeight());
         this.iconWidth = (int)(ImageManager.getImage(Images.resourceNone).getWidth() * iconScale);
         this.iconHeight = (int)(ImageManager.getImage(Images.resourceNone).getHeight() * iconScale);
      }

      nWidth = this.getWidth() - CFG.PADDING / 2;
      int tWMax = 0;

      try {
         while (this.iTextWidth > nWidth && this.getText().length() > 5) {
            if (++tWMax >= 100) {
               break;
            }

            this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, !this.getIsHovered() && !isActive ? 0.5F : 0.6F)
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
         this.getWidth(),
         this.getTextH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(TextIcon2.getColor_gradientXY());
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), this.getTextH());
      oSB.setColor(TextIcon2.getColor_gradientFull());
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      if (this.resourceID >= 0) {
         ResourcesManager.resourceImages
            .get(this.resourceID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - this.iconWidth / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - this.iconHeight / 2 + iTranslateY,
               this.iconWidth,
               this.iconHeight
            );
      } else {
         ImageManager.getImage(Images.resourceNone)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - this.iconWidth / 2 + iTranslateX,
               this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - this.iconHeight / 2 + iTranslateY,
               this.iconWidth,
               this.iconHeight
            );
      }

      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - this.getTextHeight() + iTranslateY,
         this.getColor(isActive)
      );
   }

   public int getTextH() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 2;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Resource") + ": ", CFG.FONT_BOLD));
      if (this.resourceID >= 0) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle(ResourcesManager.lResources.get(this.resourceID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ResourceTitle(this.resourceID, CFG.PADDING, 0));
      } else {
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("None"), CFG.FONT_BOLD, Colors.HOVER_RIGHT));
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
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

      if (Game.menuManager.getVisibleInGame_Goods()) {
         nData.add(new MenuElement_HoverElement_Type_Empty());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_TextTitle_BG(
               Game.lang.get("Provinces") + ": " + ResourcesManager.lResources.get(this.resourceID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements, true);
      } else {
         this.menuElementHover = new MenuElement_Hover(nElements);
      }
   }

   @Override
   public int getCurrent() {
      return this.resourceID;
   }
}
