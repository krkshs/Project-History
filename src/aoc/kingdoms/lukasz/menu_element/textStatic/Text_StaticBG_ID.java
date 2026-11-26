package aoc.kingdoms.lukasz.menu_element.textStatic;

public class Text_StaticBG_ID extends Text_StaticBG {
   public int id;

   public Text_StaticBG_ID(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.id = id;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
