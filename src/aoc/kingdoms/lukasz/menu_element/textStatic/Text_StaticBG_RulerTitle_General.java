package aoc.kingdoms.lukasz.menu_element.textStatic;

public class Text_StaticBG_RulerTitle_General extends Text_StaticBG_RulerTitle {
   public String key;
   public int iProvinceID;
   public int iCivID;

   public Text_StaticBG_RulerTitle_General(String sText, int iPosX, int iPosY, int iWidth, int iHeight, int iProvinceID, int iCivID) {
      super(sText, iPosX, iPosY, iWidth, iHeight);
      this.iProvinceID = iProvinceID;
      this.iCivID = iCivID;
   }

   public Text_StaticBG_RulerTitle_General(String sText, int iPosX, int iPosY, int iWidth, int iHeight, String key, int iProvinceID, int iCivID) {
      super(sText, iPosX, iPosY, iWidth, iHeight);
      this.key = key;
      this.iProvinceID = iProvinceID;
      this.iCivID = iCivID;
   }
}
