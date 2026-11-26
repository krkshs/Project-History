package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Resource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_StaticBG_LargestProducer extends Text_StaticBG {
   public int iResourceID;

   public Text_StaticBG_LargestProducer(String sText, int iResourceID, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.iResourceID = iResourceID;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = getHoverLargestProducers(this.getCurrent(), true);
   }

   public static MenuElement_Hover getHoverLargestProducers(int resourceID, boolean addResourceName) {
      try {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusResource(
               Game.lang.get("Resource") + ": ",
               ResourcesManager.getResourceName(resourceID),
               resourceID,
               CFG.FONT_REGULAR,
               CFG.FONT_BOLD,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LargestProducer") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(ResourcesManager.worldResources_LargestProducer_Amount.get(resourceID).intValue() / 100.0F, 10),
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Resource(resourceID, CFG.PADDING, 0));
         nData.add(new MenuElement_HoverElement_Type_Flag(ResourcesManager.worldResources_LargestProducer.get(resourceID), CFG.PADDING, 0));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               " ["
                  + CFG.getPrecision2(
                     (float)Math.min(
                        100.0,
                        Math.floor(
                           (float)ResourcesManager.worldResources_LargestProducer_Amount.get(resourceID).intValue()
                              / ResourcesManager.worldResourcesProduced.get(resourceID).intValue()
                              * 100.0F
                        )
                     ),
                     10
                  )
                  + "%]",
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT2
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("WorldProduction") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(ResourcesManager.worldResourcesProduced.get(resourceID).intValue() / 100.0F, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Resource(resourceID, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         if (ResourcesManager.worldResourcesProduced.get(resourceID) > 0) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            List<Integer> productionCivID = new ArrayList<>();
            List<Integer> production = new ArrayList<>();

            for (int i = 0; i < Game.getCivsSize(); i++) {
               production.add(0);
               productionCivID.add(i);
            }

            for (int i = 0; i < Game.getProvincesSize(); i++) {
               if (Game.getProvince(i).getResourceID() == resourceID && !Game.getProvince(i).getSeaProvince() && Game.getProvince(i).getCivID() > 0) {
                  production.set(
                     Game.getProvince(i).getCivID(), production.get(Game.getProvince(i).getCivID()) + (int)(ResourcesManager.getProducedGoods(i) * 100.0F)
                  );
               }
            }

            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusResource(
                  Game.lang.get("Production") + ": ",
                  ResourcesManager.getResourceName(resourceID),
                  resourceID,
                  CFG.FONT_REGULAR_SMALL,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_GOLD
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();

            for (int ix = 0; ix < 10 && ix < production.size(); ix++) {
               int toAdd = 0;

               for (int j = 1; j < production.size(); j++) {
                  if (production.get(toAdd) < production.get(j)) {
                     toAdd = j;
                  }
               }

               if (production.get(toAdd) == 0) {
                  break;
               }

               nData.add(new MenuElement_HoverElement_Type_Text("" + (ix + 1) + ".", CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_Flag(productionCivID.get(toAdd), CFG.PADDING, CFG.PADDING));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getPrecision2(production.get(toAdd).intValue() / 100.0F, 100), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Resource(resourceID, CFG.PADDING, CFG.PADDING));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     ""
                        + CFG.getPrecision2(
                           (float)Math.min(
                              100.0,
                              Math.floor((float)production.get(toAdd).intValue() / ResourcesManager.worldResourcesProduced.get(resourceID).intValue() * 100.0F)
                           ),
                           10
                        )
                        + "%",
                     CFG.FONT_REGULAR,
                     Colors.HOVER_RIGHT2
                  )
               );
               nData.add(
                  new MenuElement_HoverElement_Type_Text(" " + Game.getCiv(productionCivID.get(toAdd)).getCivName(), CFG.FONT_REGULAR, Colors.HOVER_RIGHT3)
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               production.remove(toAdd);
               productionCivID.remove(toAdd);
            }

            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(
               new MenuElement_HoverElement_Type_TextTitle_BG(
                  Game.lang.get("LargestGoodsProducers") + (addResourceName ? ": " + ResourcesManager.getResourceName(resourceID) : ""),
                  CFG.FONT_BOLD,
                  Colors.HOVER_GOLD
               )
            );
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }

         return new MenuElement_Hover(nElements, true);
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
         return null;
      }
   }

   @Override
   public int getCurrent() {
      return this.iResourceID;
   }
}
