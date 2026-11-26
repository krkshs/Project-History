package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;

public class ButtonArmyGeneral2_Armies extends ButtonArmyGeneral2 {
   public String key;
   public int iProvinceID;
   public int iCivID;

   public ButtonArmyGeneral2_Armies(
      String sName,
      int iCivID,
      int iAttack,
      int iDefense,
      int iPosX,
      int iPosY,
      int imageID,
      int iDay,
      int iMonth,
      int iYear,
      String key,
      int nCivID,
      int iProvinceID,
      String sIMG,
      int combatExperience
   ) {
      super(sName, iCivID, iAttack, iDefense, iPosX, iPosY, imageID, iDay, iMonth, iYear, sIMG, combatExperience);
      this.key = key;
      this.iProvinceID = iProvinceID;
      this.iCivID = nCivID;
   }

   @Override
   public void actionElement() {
      int nArmyID = Game.getProvince(this.iProvinceID).getArmyKeyID(this.key);
      if (nArmyID < 0) {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            int outID = Game.getProvince(i).getArmyKeyID(this.key, this.iCivID);
            if (outID >= 0) {
               this.iProvinceID = i;
               nArmyID = outID;
               break;
            }
         }
      }

      if (nArmyID >= 0) {
         if (!Game.getProvince(this.iProvinceID).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(this.iProvinceID);
         }

         Game.clearActiveArmy();
         Game.HoveredArmy nHA = new Game.HoveredArmy();
         nHA.key = Game.getProvince(this.iProvinceID).getArmy(nArmyID).key;
         nHA.iCivID = Game.getProvince(this.iProvinceID).getArmy(nArmyID).civID;
         nHA.iProvinceID = this.iProvinceID;
         nHA.iArmyID = nArmyID;
         Game.addActiveArmy(nHA);
         ProvinceTouchExtraAction.actionUp_SetActiveArmy();
         if (Game.menuManager.getVisibleInGame_Generals()) {
            if (InGame_Generals.assignProvinceID >= 0
               && InGame_Generals.assignProvinceID != this.iProvinceID
               && !InGame_Generals.assignArmyKey.equals(this.key)) {
               int tDivID = Game.getProvince(this.iProvinceID).getArmyKeyID(this.key);
               if (tDivID >= 0) {
                  InGame_Generals.assignProvinceID = this.iProvinceID;
                  InGame_Generals.assignArmyKey = this.key;
               } else {
                  InGame_Generals.assignProvinceID = -1;
                  Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
               }

               Game.menuManager.setOrderInGame_Generals();
            } else {
               Game.menuManager.setVisibleInGame_Generals(false);
            }
         } else {
            InGame_Generals.assignProvinceID = this.iProvinceID;
            InGame_Generals.assignArmyKey = this.key;
            InGame_Generals.backButton = false;
            Game.menuManager.rebuildInGame_Generals();
            Game.menuManager.setVisibleInGame_Generals(true);
         }
      }
   }
}
