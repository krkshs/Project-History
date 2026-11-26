package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class Text_Static_ID extends Text_Static {
   public int iCivID;

   public Text_Static_ID(int iCivID, String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.iCivID = iCivID;
   }

   @Override
   public int getCurrent() {
      return this.iCivID;
   }

   @Override
   public void buildElementHover() {
      try {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(
            new MenuElement_HoverElement_Type_FlagCiv_Title(
               this.iCivID, Game.lang.get("Provinces") + ": " + CFG.getNumberWithSpaces("" + Game.getCiv(this.iCivID).getNumOfProvinces())
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Population") + ": ",
               CFG.getNumberWithSpaces("" + Game.getCiv(this.iCivID).getPopulationTotal()),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.COLOR_POPULATION
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Economy") + ": ",
               CFG.getPrecision2(Game.getCiv(this.iCivID).getEconomyTotal(), 10),
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public void actionElement() {
      try {
         if (Game.getCiv(this.iCivID).getCapitalProvinceID() >= 0
            && Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getCivID() == this.iCivID) {
            Game.mapCoords.centerToProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
            Game.setActiveProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
            ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }
}
