package aoc.kingdoms.lukasz.map.army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_Army_Composition;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy_NewArmy;
import java.util.ArrayList;
import java.util.List;

public class ArmyDivision {
   public String key;
   public List<ArmyRegiment> lArmyRegiment = new ArrayList<>();
   public int iArmyRegimentSize = 0;
   public int civID;
   public int provinceID;
   public ArmyGeneral armyGeneral = null;
   public boolean inRetreat = false;
   public int iArmyOfProvinceID;
   public float fMaintenanceCost = 0.0F;
   private float fAverageSpeed = 1.0F;
   public float fMorale = 0.0F;
   public int fMoraleDraw = 0;
   public boolean inMovement = false;
   public boolean inBattle = false;
   public boolean addedToBattle = false;
   public int iArmy = 0;
   public String sArmy;
   public int iShiftX;
   public int iShiftY;
   public int iShiftX_Scaled;
   public int iShiftY_Scaled;
   public int iArmyWidth;
   public int iArmyExtraPosX;
   public boolean updateMorale = false;
   public byte iStack = 0;

   public ArmyDivision() {
   }

   public ArmyDivision(int nCivID, List<InGame_RecruitArmy_NewArmy.CreateNewArmy> tArmyRegiment) {
      this.civID = nCivID;
      List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
      int i = 0;

      for (int iSize = tArmyRegiment.size(); i < iSize; i++) {
         for (int j = 0; j < tArmyRegiment.get(i).numOfRegiments; j++) {
            nArmyRegiment.add(new ArmyRegiment(tArmyRegiment.get(i).iUnitID, tArmyRegiment.get(i).iArmyID));
         }
      }

      this.sortArmyRegiment(nArmyRegiment);
      this.setArmyGeneral(null);
   }

   public ArmyDivision(int nCivID, int nProvinceID, List<ArmyRegiment> nArmyRegiment) {
      this.civID = nCivID;
      this.provinceID = nProvinceID;
      this.iArmyOfProvinceID = nProvinceID;
      this.key = CFG.extraRandomTag();
      this.sortArmyRegiment(nArmyRegiment);
      this.updateArmy(true);
      this.updateMorale();
      this.setArmyGeneral(null);
   }

   public ArmyDivision(int nCivID, int nProvinceID, List<ArmyRegiment> nArmyRegiment, ArmyGeneral armyGeneral) {
      this.civID = nCivID;
      this.provinceID = nProvinceID;
      this.iArmyOfProvinceID = nProvinceID;
      this.key = CFG.extraRandomTag();
      this.sortArmyRegiment(nArmyRegiment);
      this.updateArmy(true);
      this.updateMorale();
      this.setArmyGeneral(armyGeneral);
   }

   public ArmyDivision(SaveGameManager.Save_Provinces_ArmyDivision armyDivision) {
      this.civID = armyDivision.c;
      this.provinceID = armyDivision.p;
      this.iArmyOfProvinceID = armyDivision.p;
      this.key = armyDivision.k;
      this.inRetreat = armyDivision.t;
      this.armyGeneral = armyDivision.g;
      List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
      int j = 0;

      for (int jSize = armyDivision.r.size(); j < jSize; j++) {
         nArmyRegiment.add(new ArmyRegiment(armyDivision.r.get(j)));
      }

      this.sortArmyRegiment(nArmyRegiment);
      this.updateArmy(true);
      this.updateMorale();
   }

   public final void sortArmyRegiment(List<ArmyRegiment> nArmyRegiment) {
      this.lArmyRegiment.clear();

      while (!nArmyRegiment.isEmpty()) {
         int addID = 0;
         int i = 1;

         for (int iSize = nArmyRegiment.size(); i < iSize; i++) {
            if (ArmyManager.lUnitsTypes.get(nArmyRegiment.get(i).uID).Line < ArmyManager.lUnitsTypes.get(nArmyRegiment.get(addID).uID).Line) {
               addID = i;
            } else if (ArmyManager.lUnitsTypes.get(nArmyRegiment.get(i).uID).Line == ArmyManager.lUnitsTypes.get(nArmyRegiment.get(addID).uID).Line) {
               if (nArmyRegiment.get(i).uID < nArmyRegiment.get(addID).uID) {
                  addID = i;
               } else if (nArmyRegiment.get(i).uID == nArmyRegiment.get(addID).uID && nArmyRegiment.get(i).aID < nArmyRegiment.get(addID).aID) {
                  addID = i;
               }
            }
         }

         this.lArmyRegiment.add(nArmyRegiment.get(addID));
         nArmyRegiment.remove(addID);
      }

      this.iArmyRegimentSize = this.lArmyRegiment.size();
   }

   public final void removeRegiment(int i) {
      this.lArmyRegiment.remove(i);
      this.iArmyRegimentSize = this.lArmyRegiment.size();
      this.updateArmy(false);
   }

   public final boolean removeRegiment(String key, boolean bManpowerRecoveryFromADisbandedArmy, int disbandCivID) {
      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         if (this.lArmyRegiment.get(i).key.equals(key)) {
            if (bManpowerRecoveryFromADisbandedArmy) {
               Game.getCiv(disbandCivID)
                  .setManpower(
                     Game.getCiv(disbandCivID).fManpower
                        + (int)Math.max(0.0F, this.lArmyRegiment.get(i).num * Game.getManpowerRecoveryFromADisbandedArmy(disbandCivID))
                  );
            }

            this.lArmyRegiment.remove(i);
            this.iArmyRegimentSize = this.lArmyRegiment.size();
            this.updateArmy(false);
            this.updateMorale();
            return true;
         }
      }

      return false;
   }

   public final void addRegiment(ArmyRegiment nArmyRegiment) {
      List<ArmyRegiment> tempArmyRegiment = new ArrayList<>();

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         tempArmyRegiment.add(this.lArmyRegiment.get(i));
      }

      tempArmyRegiment.add(nArmyRegiment);
      this.sortArmyRegiment(tempArmyRegiment);
      this.updateArmy(false);
      this.updateMorale();
   }

   public final void updateRegiment(List<ArmyRegiment> nArmyRegiment) {
      List<ArmyRegiment> tempArmyRegiment = new ArrayList<>();
      int i = 0;

      for (int iSize = nArmyRegiment.size(); i < iSize; i++) {
         tempArmyRegiment.add(nArmyRegiment.get(i));
      }

      this.sortArmyRegiment(tempArmyRegiment);
      this.updateArmy(true);
      this.updateMorale();
      this.updateSpeed();
   }

   public final void updateMaintenanceCost() {
      this.fMaintenanceCost = 0.0F;

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         this.fMaintenanceCost = this.fMaintenanceCost
            + ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).MaintenanceCost;
      }

      Game.gameThread.addCivUpdateArmyMaintenance(this.civID);
   }

   public final void updateMorale_Regiments(float maxMorale, float moraleRecovery) {
      this.updateMorale = false;

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         if (this.lArmyRegiment.get(i).mo != maxMorale) {
            this.lArmyRegiment.get(i).mo = Math.min(maxMorale, this.lArmyRegiment.get(i).mo + moraleRecovery);
            this.updateMorale = true;
         }
      }

      if (this.updateMorale) {
         this.updateMorale();
      }
   }

   public final void updateMorale() {
      this.fMorale = 0.0F;

      for (int i = this.iArmyRegimentSize - 1; i >= 0; i--) {
         this.fMorale = this.fMorale + this.lArmyRegiment.get(i).mo;
      }

      this.fMorale = this.fMorale / this.iArmyRegimentSize;
      this.fMoraleDraw = Math.max(0, Math.min(GameValues.inGame.ARMY_LEFT_IMAGES, (int)(this.fMorale * 10.0F)));
   }

   public final float updateReinforce(int maxReinforce, float maxRegimentSize) {
      float reinforcedManpower = 0.0F;
      if (this.iArmy < this.iArmyRegimentSize * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
         for (int i = 0; i < this.iArmyRegimentSize; i++) {
            if (this.lArmyRegiment.get(i).num < Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
               if (this.lArmyRegiment.get(i).num + maxReinforce > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
                  int nReinforce = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE - this.lArmyRegiment.get(i).num;
                  reinforcedManpower += this.getReinforceCost(nReinforce, this.lArmyRegiment.get(i).uID, this.lArmyRegiment.get(i).aID, maxRegimentSize);
                  this.lArmyRegiment.get(i).num += nReinforce;
                  Game.getCiv(this.civID).fManpower -= nReinforce;
               } else {
                  reinforcedManpower += this.getReinforceCost(maxReinforce, this.lArmyRegiment.get(i).uID, this.lArmyRegiment.get(i).aID, maxRegimentSize);
                  this.lArmyRegiment.get(i).num += maxReinforce;
                  Game.getCiv(this.civID).fManpower -= maxReinforce;
               }

               maxReinforce = (int)Math.min((double)maxReinforce, Game.getCiv(this.civID).fManpower);
               if (maxReinforce <= 1) {
                  break;
               }
            }
         }

         this.updateArmy(false);
      }

      return reinforcedManpower;
   }

   public float getReinforceCost(int nReinforce, int uID, int aID, float maxRegimentSize) {
      return ArmyManager.lArmy.get(uID).get(aID).Cost * (nReinforce / maxRegimentSize) * GameValues.army.REINFORCE_ARMY_COST_MODIFIER;
   }

   public final void updateSpeed() {
      this.fAverageSpeed = 0.0F;

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         this.fAverageSpeed = this.fAverageSpeed + ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).MovementSpeed;
      }

      this.fAverageSpeed = this.fAverageSpeed / this.iArmyRegimentSize;
   }

   public final void updateArmy(boolean updateShift) {
      this.iArmy = 0;
      this.fAverageSpeed = 0.0F;

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         this.iArmy = this.iArmy + this.lArmyRegiment.get(i).num;
         this.fAverageSpeed = this.fAverageSpeed + ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).MovementSpeed;
      }

      this.fAverageSpeed = this.fAverageSpeed / this.iArmyRegimentSize;
      this.iStack = 0;
      this.updateMaintenanceCost();
      Renderer.addSimpleTask_ArmyWidth(new Renderer.SimpleTaskArmyText(this.key, this, updateShift));
   }

   public final void updateArmyWidth_Just(boolean updateShift) {
      try {
         if (Game.FOG_OF_WAR
            && GameValues.fog.HIDE_ARMIES
            && this.civID != Game.player.iCivID
            && !DiplomacyManager.isAlly(this.civID, Game.player.iCivID)
            && !DiplomacyManager.isAlly(Game.getProvince(this.provinceID).getCivID(), Game.player.iCivID)) {
            this.sArmy = GameValues.fog.TEXT_UNKNOWN_ARMIES;
         } else if (this.iArmy < 1000) {
            this.sArmy = "" + CFG.getPrecision2(this.iArmy / 1000.0F, 10);
         } else {
            this.sArmy = "" + this.iArmy / 1000;
         }

         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         if (!glyphLayout.setText(Renderer.fontArmy_GlyphLayout, this.sArmy) && this.iStack++ < 9) {
            this.updateArmyWidth_Just(updateShift);
         } else {
            int tX = (int)glyphLayout.width;
            if (!glyphLayout.setText(Renderer.fontArmy_GlyphLayout, this.sArmy.length() > 2 && !this.sArmy.contains(".") ? this.sArmy : "999")
               && this.iStack++ < 9) {
               this.updateArmyWidth_Just(updateShift);
            } else {
               this.iArmyWidth = (int)glyphLayout.width;
               this.iArmyExtraPosX = this.iArmyWidth / 2 - tX / 2;
               if (updateShift) {
                  this.iShiftX = this.defaultShiftX();
                  this.iShiftY = this.defaultShiftY();
                  this.iShiftX_Scaled = 0;
                  this.iShiftY_Scaled = 0;
                  Game.getProvince(this.provinceID).updateArmyPosY();
               }
            }
         }
      } catch (Exception var4) {
         if (this.iStack++ < 9) {
            this.updateArmyWidth_Just(updateShift);
         } else {
            CFG.exceptionStack(var4);
            this.iArmyWidth = CFG.PADDING;
         }
      }
   }

   public final int defaultShiftX() {
      return -ProvinceDrawArmy.getArmyWidth(this.iArmyWidth) / 2;
   }

   public final int defaultShiftY() {
      return -ProvinceDrawArmy.getArmyHeight() / 2;
   }

   public final float getArmyMovementSpeed() {
      return Math.max(
         0.1F,
         this.fAverageSpeed
            * (
               1.0F
                  + Game.getCiv(this.civID).getWarWeariness() * GameValues.warWeariness.WW_ARMY_MOVEMENT_SPEED_PER_POINT
                  + Game.getCiv(this.civID).civBonuses.ArmyMovementSpeed / 100.0F
            )
      );
   }

   public final float getCivilizationRanking_ArmyScore() {
      float out = 0.0F;

      try {
         int i = 0;

         for (int iSize = this.lArmyRegiment.size(); i < iSize; i++) {
            out += GameValues.civRank.CIV_SCORE_MAX_PER_REGIMENT
               * (
                  ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).getAttack()
                     + ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).getDefense()
               )
               / CivilizationRanking.bestArmyAttackDefense;
         }
      } catch (Exception var4) {
      }

      return out;
   }

   public final float getSiegeProgressPerDay() {
      float out = 0.0F;

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         out += ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).SiegeProgress
            * this.lArmyRegiment.get(i).num
            / Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
      }

      return out
         * (
            1.0F
               + Game.getCiv(this.civID).civBonuses.SiegeEffectiveness
               + Game.getCiv(this.civID).getWarWeariness() * GameValues.warWeariness.WW_SIEGE_EFFECTIVENESS_PER_POINT
         );
   }

   public final void setInMovement(boolean inMovement) {
      this.inMovement = inMovement;
      if (!inMovement) {
         this.inRetreat = false;
      } else {
         this.inBattle = false;
      }
   }

   public final int getNumberOfSettlers() {
      int out = 0;

      for (int i = this.iArmyRegimentSize - 1; i >= 0; i--) {
         if (ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).isSettler) {
            out += this.lArmyRegiment.get(i).num;
         }
      }

      return out;
   }

   public final void removeAllSettlers() {
      int tRemoved = 0;

      for (int i = this.iArmyRegimentSize - 1; i >= 0; i--) {
         if (ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).isSettler) {
            this.lArmyRegiment.remove(i);
            if (++tRemoved >= GameValues.colonization.COLONIZATION_MAX_SETTLERS) {
               break;
            }
         }
      }

      this.iArmyRegimentSize = this.lArmyRegiment.size();
      this.updateArmy(false);
   }

   public final void removeRegiment_ScenarioEditor(int unitTypeID, int armyID) {
      for (int i = this.iArmyRegimentSize - 1; i >= 0; i--) {
         if (this.lArmyRegiment.get(i).uID == unitTypeID && this.lArmyRegiment.get(i).aID == armyID) {
            this.lArmyRegiment.remove(i);
            this.iArmyRegimentSize = this.lArmyRegiment.size();
            this.updateArmy(false);
            return;
         }
      }
   }

   public final void setArmyGeneral(ArmyGeneral general) {
      this.armyGeneral = general;
      if (general == null) {
         Game.getCiv(this.civID).armiesWithoutGenerals.addArmyKey(this.key);
      } else {
         Game.getCiv(this.civID).armiesWithoutGenerals.removeArmyKey(this.key);
      }
   }

   public AI_Army_Composition getArmyComposition() {
      AI_Army_Composition out = new AI_Army_Composition();

      for (int i = 0; i < this.iArmyRegimentSize; i++) {
         if (ArmyManager.lUnitsTypes.get(this.lArmyRegiment.get(i).uID).Line == 0) {
            out.numFirstLine++;
         } else if (ArmyManager.lUnitsTypes.get(this.lArmyRegiment.get(i).uID).Line == 1) {
            out.numFlank++;
         } else if (ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).SiegeUnit) {
            out.numSiege++;
         } else if (!ArmyManager.lArmy.get(this.lArmyRegiment.get(i).uID).get(this.lArmyRegiment.get(i).aID).isSettler) {
            out.numSupport++;
         }
      }

      out.numOfRegiments = out.numFirstLine + out.numFlank + out.numSupport + out.numSiege;
      return out;
   }

   public final float getPercOfTotalUnits() {
      return (float)this.iArmy / (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * this.iArmyRegimentSize);
   }
}
