package aoc.kingdoms.lukasz.units;

public class Hitbox {
   private int iPosX;
   private int iPosY;
   private int iWidth;
   private int iHeight;

   public Hitbox(int iPosX, int iPosY, int iWidth, int iHeight) {
      this.iPosX = iPosX;
      this.iPosY = iPosY;
      this.iWidth = iWidth;
      this.iHeight = iHeight;
   }

   public int getPosX() {
      return this.iPosX;
   }

   public void setPosX(int iPosX) {
      this.iPosX = iPosX;
   }

   public int getPosY() {
      return this.iPosY;
   }

   public void setPosY(int iPosY) {
      this.iPosY = iPosY;
   }

   public int getWidth() {
      return this.iWidth;
   }

   public void setWidth(int iWidth) {
      this.iWidth = iWidth;
   }

   public int getHeight() {
      return this.iHeight;
   }

   public void setHeight(int iHeight) {
      this.iHeight = iHeight;
   }
}
