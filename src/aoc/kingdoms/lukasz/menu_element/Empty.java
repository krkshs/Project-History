package aoc.kingdoms.lukasz.menu_element;

public class Empty extends MenuElement {
   public Empty(int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TRANSPARENT_BACKGROUND;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setClickable(false);
      this.setVisible(true);
   }

   @Override
   public boolean playSFX_Hovered() {
      return false;
   }
}
