package aoc.kingdoms.lukasz.map.civilization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral_Assign;
import java.util.ArrayList;
import java.util.List;

public class CivilizationGeneralsPool {
   public List<ArmyGeneral> lGenerals = new ArrayList<>();
   public int generateYear = -55000;

   public final void generateGenerals(int iCivID) {
      for (int i = this.lGenerals.size(); i < this.getPoolOfGenerals(iCivID); i++) {
         int generalIMG = this.getRandomImage(iCivID);
         this.lGenerals
            .add(
               new ArmyGeneral(
                  Game.generalManager.getGeneralRandomName(iCivID) + " " + Game.generalManager.getGeneralRandomSurname(iCivID),
                  generalIMG,
                  GameValues.generals.GENERAL_ATTACK_BASE_VALUE
                     + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM)
                     + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM2),
                  GameValues.generals.GENERAL_DEFENSE_BASE_VALUE
                     + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM)
                     + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM2),
                  Game_Calendar.currentYear - GameValues.generals.GENERAL_YEARS_OLD_MIN - Game.oR.nextInt(GameValues.generals.GENERAL_YEARS_OLD_RANDOM),
                  iCivID,
                  null
               )
            );
      }
   }

   public static final ArmyGeneral getGeneral_Random(int iCivID) {
      int generalIMG = getRandomImage_Just(iCivID);
      return new ArmyGeneral(
         Game.generalManager.getGeneralRandomName(iCivID) + " " + Game.generalManager.getGeneralRandomSurname(iCivID),
         generalIMG,
         GameValues.generals.GENERAL_ATTACK_BASE_VALUE
            + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM)
            + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM2),
         GameValues.generals.GENERAL_DEFENSE_BASE_VALUE
            + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM)
            + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM2),
         Game_Calendar.currentYear - GameValues.generals.GENERAL_YEARS_OLD_MIN - Game.oR.nextInt(GameValues.generals.GENERAL_YEARS_OLD_RANDOM),
         iCivID,
         null
      );
   }

   public final void updatePoolOfGenerals(int iCivID) {
      if (this.lGenerals.size() == 0) {
         this.generateGenerals(iCivID);
         this.generateYear = Game_Calendar.currentYear;
      } else if (this.generateYear + GameValues.generals.GENERALS_REGENERATE_YEARS <= Game_Calendar.currentYear) {
         this.lGenerals.clear();
         this.generateGenerals(iCivID);
         this.generateYear = Game_Calendar.currentYear;
      } else if (this.lGenerals.size() < this.getPoolOfGenerals(iCivID)) {
         this.generateGenerals(iCivID);
      }
   }

   public int getPoolOfGenerals(int iCivID) {
      return GameValues.generals.GENERALS_DEFAULT_POOL_SIZE;
   }

   public void clearData() {
      this.lGenerals.clear();
      this.generateYear = -55000;
   }

   public final boolean recruitGeneralID(int iCivID, int id, boolean assignGeneral) {
      if (id < this.lGenerals.size()) {
         if (Game.getCiv(iCivID).fGold < GeneralManager.getRecruitGoldCost(iCivID)) {
            return false;
         } else if (Game.getCiv(iCivID).fLegacy < GeneralManager.getRecruitLegacyCost(iCivID)) {
            return false;
         } else {
            Civilization var10000 = Game.getCiv(iCivID);
            var10000.fGold = var10000.fGold - GeneralManager.getRecruitGoldCost(iCivID);
            var10000 = Game.getCiv(iCivID);
            var10000.fLegacy = var10000.fLegacy - GeneralManager.getRecruitLegacyCost(iCivID);
            Game.getCiv(iCivID).addGeneral(this.lGenerals.get(id));

            try {
               if (assignGeneral) {
                  ButtonArmyGeneral_Assign.assignGeneral(iCivID, this.lGenerals.get(id).key, false);
               }
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
            }

            this.lGenerals.remove(id);
            this.generateGenerals(iCivID);
            if (iCivID == Game.player.iCivID) {
               Game.player.playerStats.recruitedGenerals++;
               Game.stats.civStats.rg++;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static int getRandomImage_Just(int iCivID) {
      return Game.oR.nextInt(Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID));
   }

   public int getRandomImage(int iCivID) {
      List<Boolean> isUsed = new ArrayList<>();

      for (int i = 0; i < Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); i++) {
         isUsed.add(false);
      }

      int i = 0;

      for (int iSize = this.lGenerals.size(); i < iSize; i++) {
         isUsed.set(this.lGenerals.get(i).g, true);
      }

      for (int ix = 0; ix < Game.getCiv(iCivID).iGeneralsSize; ix++) {
         isUsed.set(Game.getCiv(iCivID).getGeneralNotAssigned(ix).g, true);
      }

      try {
         for (int ix = 0; ix < Game.getCiv(iCivID).iArmyPositionSize; ix++) {
            for (int j = 0; j < Game.getProvince(Game.getCiv(iCivID).getArmyPosition(ix)).getArmySize(); j++) {
               if (Game.getProvince(Game.getCiv(iCivID).getArmyPosition(ix)).getArmy(j).civID == iCivID
                  && Game.getProvince(Game.getCiv(iCivID).getArmyPosition(ix)).getArmy(j).armyGeneral != null) {
                  isUsed.set(Game.getProvince(Game.getCiv(iCivID).getArmyPosition(ix)).getArmy(j).armyGeneral.g, true);
               }
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      List<Integer> out = new ArrayList<>();
      int ix = 0;

      for (int iSize = Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); ix < iSize; ix++) {
         if (!isUsed.get(ix)) {
            out.add(ix);
         }
      }

      if (out.size() != 0) {
         return out.get(Game.oR.nextInt(out.size()));
      } else {
         ix = 0;

         for (int iSizex = Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); ix < iSizex; ix++) {
            isUsed.set(ix, false);
         }

         ix = 0;

         for (int iSizex = this.lGenerals.size(); ix < iSizex; ix++) {
            isUsed.set(this.lGenerals.get(ix).g, true);
         }

         ix = 0;

         for (int iSizex = Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); ix < iSizex; ix++) {
            if (!isUsed.get(ix)) {
               out.add(ix);
            }
         }

         return out.size() == 0
            ? Game.oR.nextInt(Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID))
            : out.get(Game.oR.nextInt(out.size()));
      }
   }
}
