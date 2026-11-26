package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_StaticBG_EconomyRanking extends Text_StaticBG {
   public int id;
   public static int activeID = 0;
   public static int resourceID = 0;

   public Text_StaticBG_EconomyRanking(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.id = id;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered()) {
         if (activeID != this.id) {
            activeID = this.id;
            resourceID = ResourcesManager.getLargestGoodsProducedByCiv(this.id);
         }

         if (resourceID >= 0) {
            float tempScale = (float)(CFG.TEXT_HEIGHT + CFG.PADDING * 2) / ResourcesManager.resourceImages.get(resourceID).getHeight();
            int resW = (int)(ResourcesManager.resourceImages.get(resourceID).getWidth() * tempScale);
            int resH = (int)(ResourcesManager.resourceImages.get(resourceID).getHeight() * tempScale);
            ResourcesManager.resourceImages
               .get(resourceID)
               .draw(
                  oSB,
                  this.getPosX() + this.getWidth() / 2 - resW / 2 + iTranslateX,
                  this.getPosY() + this.getHeight() / 2 - resH / 2 + iTranslateY,
                  resW,
                  resH
               );
         } else {
            Renderer.drawTextWithShadow(
               oSB,
               this.fontID,
               this.sText,
               this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
               this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
               this.getColor(isActive)
            );
         }
      } else {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sText,
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(this.id, Game.lang.get("ProducedGoods")));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      List<Float> goodsProduced = new ArrayList<>();

      for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
         goodsProduced.add(0.0F);
      }

      int bestResourceID = -1;

      for (int i = 0; i < Game.getCiv(this.id).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID() >= 0) {
            goodsProduced.set(
               Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID(),
               goodsProduced.get(Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID())
                  + ResourcesManager.getProducedGoods(Game.getCiv(this.id).getProvinceID(i))
            );
            bestResourceID = Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID();
         }
      }

      if (bestResourceID >= 0) {
         int numAdded = 0;

         while (numAdded++ < 6) {
            for (int ix = 0; ix < ResourcesManager.iResourcesSize; ix++) {
               if (goodsProduced.get(bestResourceID) < goodsProduced.get(ix)) {
                  bestResourceID = ix;
               }
            }

            if (!(goodsProduced.get(bestResourceID) > 0.0F)) {
               break;
            }

            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusResource(
                  ResourcesManager.getResourceName(bestResourceID) + ": ",
                  CFG.getPrecision2(goodsProduced.get(bestResourceID), 10),
                  bestResourceID,
                  CFG.FONT_BOLD_SMALL,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_GOLD
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            goodsProduced.set(bestResourceID, 0.0F);
         }
      } else {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("None"), "", Images.resourceNone, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      goodsProduced.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
