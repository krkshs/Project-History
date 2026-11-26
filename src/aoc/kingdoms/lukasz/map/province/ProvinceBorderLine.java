package aoc.kingdoms.lukasz.map.province;

public class ProvinceBorderLine {
   private int iPosX;
   private int iPosY;
   private int iWidth;
   private float fAngle;

   public ProvinceBorderLine(int nPosX, int nPosY, int nPosX2, int nPosY2) {
      this.iPosX = nPosX;
      this.iPosY = nPosY;
      this.iWidth = (int)Math.ceil(Math.sqrt((nPosX2 - nPosX) * (nPosX2 - nPosX) + (nPosY - nPosY2) * (nPosY - nPosY2)));
      this.fAngle = (float)(Math.atan2(nPosY - nPosY2, -nPosX + nPosX2) * 180.0 / Math.PI);
   }

   public int getPosX() {
      return this.iPosX;
   }

   public int getPosY() {
      return this.iPosY;
   }

   public int getWidth() {
      return this.iWidth;
   }

   public float getAngle() {
      return this.fAngle;
   }
}
