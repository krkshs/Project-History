package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menusInGame.InGame_War;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class PeaceTreaty {
   public String warKey;
   public int iCivID;
   public int iCivID2;
   public float fScore;
   public float fScoreTotal;
   public float fTotalProvinceWarScore_CivLost;
   public List<Integer> lProvinces = new ArrayList<>();
   public List<Integer> lProvinces_Liberate = new ArrayList<>();
   public List<Integer> lSubjectTransfer = new ArrayList<>();
   public List<Integer> lLiberateCiv = new ArrayList<>();
   public int demandGold = 0;
   public float fAggressiveExpansion = 0.0F;
   public boolean demandWarReparations = false;
   public boolean demandVassalization = false;
   public boolean demandGovernmentChange = false;
   public boolean demandReligionConversion = false;
   public boolean demandMilitaryAccess = false;
   public boolean demandHumiliate = false;
   public boolean demandReturnProvinces = false;

   public PeaceTreaty(int iCivID, String nWarKey, boolean player) {
      this.warKey = nWarKey;
      this.iCivID = iCivID;

      try {
         if (WarManager.lWars.get(this.warKey).isDefender(iCivID)) {
            this.iCivID2 = WarManager.lWars.get(this.warKey).lAggressors.get(0).iCivID;
         } else {
            this.iCivID2 = WarManager.lWars.get(this.warKey).lDefenders.get(0).iCivID;
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      this.fTotalProvinceWarScore_CivLost = getTotalProvinceValue(this.iCivID2);
      this.buildScore();
      if (!player) {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).aiPeaceCivID = Game.getProvince(i).getCivID();
         }
      }
   }

   public void buildScore() {
      float scoreCivID = 0.0F;
      float scoreCivID2 = 0.0F;

      for (int i = 0; i < Game.getCiv(this.iCivID).getNumOfProvinces(); i++) {
         scoreCivID += Game.getProvince(Game.getCiv(this.iCivID).getProvinceID(i)).fProvinceValue;
      }

      try {
         for (int i = 0; i < Game.getCiv(this.iCivID).diplomacy.iVassalsSize; i++) {
            try {
               for (int j = 0; j < Game.getCiv(Game.getCiv(this.iCivID).diplomacy.lVassals.get(i).c).getNumOfProvinces(); j++) {
                  scoreCivID += Game.getProvince(Game.getCiv(Game.getCiv(this.iCivID).diplomacy.lVassals.get(i).c).getProvinceID(j)).fProvinceValue
                     * GameValues.peace.PEACE_SCORE_WINNING_SIDE_VASSALS;
               }
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      for (int i = 0; i < Game.getCiv(this.iCivID2).getNumOfProvinces(); i++) {
         scoreCivID2 += Game.getProvince(Game.getCiv(this.iCivID2).getProvinceID(i)).fProvinceValue;
      }

      this.fScoreTotal = scoreCivID2;
      this.fScore = Math.max(scoreCivID * GameValues.peace.PEACE_SCORE_WINNING_SIDE, scoreCivID2 * GameValues.peace.PEACE_SCORE_LOSING_SIDE);

      try {
         if (Game.getCiv(this.iCivID2).getCapitalProvinceID() >= 0
            && Game.getProvince(Game.getCiv(this.iCivID2).getCapitalProvinceID()).getCivID() == this.iCivID2) {
            this.fScore = Math.max(this.fScore, Game.getProvince(Game.getCiv(this.iCivID2).getCapitalProvinceID()).fProvinceValue + 0.5F);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      if (WarManager.lWars.get(this.warKey).isCoalition) {
         this.fScore = this.fScore * GameValues.peace.PEACE_SCORE_COALITION_WAR;
      }

      if (WarManager.lWars.get(this.warKey).conquerVassal) {
         this.fScore = this.fScore * GameValues.peace.PEACE_SCORE_CONQUER_VASSAL_WAR;
      }

      this.fScore = this.fScore
         * Math.max(
            0.05F,
            1.0F
               + (
                     Game.getCiv(this.iCivID).civBonuses.WarScoreCost
                        + Game.getCiv(this.iCivID).civStability_LostFrom100 * GameValues.civStability.CS_WARSCORE_COST_PER_POINT
                        + GameValues.civRank.CIV_RANK_WAR_SCORE_COST[Game.getCiv(this.iCivID).iCivRankID]
                  )
                  * -1.0F
         );
   }

   public void buildAggressiveExpansion() {
      this.fAggressiveExpansion = 0.0F;

      try {
         for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
            this.fAggressiveExpansion = this.fAggressiveExpansion
               + Math.min(
                  GameValues.aggressiveExpansion.AE_PROVINCE_MAX,
                  Game.getProvince(this.lProvinces.get(i)).fProvinceValue
                     * (GameValues.aggressiveExpansion.AE_PER_PROVINCE_VALUE * this.getAggressiveExpansion_Extra(this.lProvinces.get(i)))
               );
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      if (this.demandVassalization) {
         try {
            for (int i = 0; i < Game.getCiv(this.iCivID2).getNumOfProvinces(); i++) {
               if (!Game.getProvince(Game.getCiv(this.iCivID2).getProvinceID(i)).peaceTreatyIsTaken) {
                  this.fAggressiveExpansion = this.fAggressiveExpansion
                     + Math.min(
                        GameValues.aggressiveExpansion.AE_PROVINCE_MAX,
                        Game.getProvince(Game.getCiv(this.iCivID2).getProvinceID(i)).fProvinceValue
                           * (
                              GameValues.aggressiveExpansion.AE_PER_PROVINCE_VALUE_VASSALIZATION
                                 * this.getAggressiveExpansion_Extra(Game.getCiv(this.iCivID2).getProvinceID(i))
                           )
                     );
               }
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }

      for (int a = 0; a < this.lSubjectTransfer.size(); a++) {
         try {
            for (int ix = 0; ix < Game.getCiv(this.lSubjectTransfer.get(a)).getNumOfProvinces(); ix++) {
               this.fAggressiveExpansion = this.fAggressiveExpansion
                  + Math.min(
                     GameValues.aggressiveExpansion.AE_PROVINCE_MAX,
                     Game.getProvince(Game.getCiv(this.lSubjectTransfer.get(a)).getProvinceID(ix)).fProvinceValue
                        * (
                           GameValues.aggressiveExpansion.AE_PER_PROVINCE_VALUE_SUBJECT_TRANSFER
                              * this.getAggressiveExpansion_Extra(Game.getCiv(this.lSubjectTransfer.get(a)).getProvinceID(ix))
                        )
                  );
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }
      }

      if (this.demandHumiliate) {
         this.fAggressiveExpansion = this.fAggressiveExpansion + GameValues.peace.PEACE_HUMILIATE_AGGRESSIVE_EXPANSION_GAIN;
      }

      this.fAggressiveExpansion = this.fAggressiveExpansion * Math.max(0.0F, 1.0F + Game.getCiv(this.iCivID).civBonuses.AggressiveExpansion / 100.0F);
   }

   public float getAggressiveExpansion_Extra(int provinceID) {
      return 1.0F
         + (
            Game.getCiv(this.iCivID).getReligionID() == Game.getProvince(provinceID).getReligion()
               ? GameValues.aggressiveExpansion.AE_SAME_RELIGION
               : GameValues.aggressiveExpansion.AE_DIFFERENT_RELIGION
         )
         + Game.getDistance_PercOfMax(Game.getCiv(this.iCivID).getCapitalProvinceID(), provinceID) * GameValues.aggressiveExpansion.AE_DISTANCE;
   }

   public void takeProvince(int iProvinceID) {
      if (this.isProvinceTaken(iProvinceID)) {
         this.removeProvince(iProvinceID);
         Game.getProvince(iProvinceID).peaceTreatyIsTaken = false;
      } else if (this.addProvince(iProvinceID)) {
         Game.getProvince(iProvinceID).peaceTreatyIsTaken = true;
      }

      this.buildAggressiveExpansion();
   }

   public boolean isProvinceTaken(int iProvinceID) {
      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         if (this.lProvinces.get(i) == iProvinceID) {
            return true;
         }
      }

      return false;
   }

   public boolean addProvince(int iProvinceID) {
      if (this.fScore >= Game.getProvince(iProvinceID).fProvinceValue) {
         for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
            if (this.lProvinces.get(i) == iProvinceID) {
               return true;
            }
         }

         if (this.fScore >= Game.getProvince(iProvinceID).fProvinceValue) {
            this.lProvinces.add(iProvinceID);
            this.fScore = this.fScore - Game.getProvince(iProvinceID).fProvinceValue;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void removeProvince(int iProvinceID) {
      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         if (this.lProvinces.get(i) == iProvinceID) {
            Game.getProvince(iProvinceID).peaceTreatyIsTaken = false;
            this.lProvinces.remove(i);
            this.fScore = this.fScore + Game.getProvince(iProvinceID).fProvinceValue;
            return;
         }
      }
   }

   public void resetProvinces() {
      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         this.fScore = this.fScore + Game.getProvince(this.lProvinces.get(i)).fProvinceValue;
         Game.getProvince(this.lProvinces.get(i)).peaceTreatyIsTaken = false;
         this.lProvinces.remove(i);
      }

      this.buildAggressiveExpansion();
   }

   public final boolean demandGold() {
      if (this.demandGold < GameValues.peace.PEACE_GOLD_MAX && this.fScore >= this.getScore_DemandGold()) {
         this.demandGold++;
         this.fScore = this.fScore - this.getScore_DemandGold();
         return true;
      } else {
         return false;
      }
   }

   public final void demandGold_Cancel() {
      if (this.demandGold > 0) {
         this.demandGold--;
         this.fScore = this.fScore + this.getScore_DemandGold();
      }
   }

   public final float getScore_DemandGold() {
      return this.fScoreTotal * GameValues.peace.PEACE_GOLD_COST_SCORE;
   }

   public float getGold_PerDemand() {
      return this.getGold_PerDemand_One() * this.demandGold;
   }

   public float getGold_PerDemand_One() {
      return Game.getLoanValue(this.iCivID2) * GameValues.peace.PEACE_GOLD_MODIFIER;
   }

   public final void demandWarReparations() {
      if (this.demandWarReparations) {
         this.demandWarReparations = false;
         this.fScore = this.fScore + this.getScore_DemandWarReparations();
      } else if (this.fScore >= this.getScore_DemandWarReparations()) {
         this.demandWarReparations = true;
         this.fScore = this.fScore - this.getScore_DemandWarReparations();
      }
   }

   public final float getScore_DemandWarReparations() {
      return this.fScoreTotal * GameValues.peace.PEACE_WAR_REPARATIONS_COST_SCORE;
   }

   public final void demandMilitaryAccess() {
      if (this.demandMilitaryAccess) {
         this.demandMilitaryAccess = false;
         this.fScore = this.fScore + this.getScore_DemandMilitaryAccess();
      } else if (this.fScore >= this.getScore_DemandMilitaryAccess()) {
         this.demandMilitaryAccess = true;
         this.fScore = this.fScore - this.getScore_DemandMilitaryAccess();
      }
   }

   public final float getScore_DemandMilitaryAccess() {
      return this.fScoreTotal * GameValues.peace.PEACE_MILITARY_ACCESS_COST_SCORE;
   }

   public final void demandHumiliate() {
      if (this.demandHumiliate) {
         this.demandHumiliate = false;
         this.fScore = this.fScore + this.getScore_DemandHumiliate();
      } else if (this.fScore >= this.getScore_DemandHumiliate()) {
         this.demandHumiliate = true;
         this.fScore = this.fScore - this.getScore_DemandHumiliate();
      }

      this.buildAggressiveExpansion();
   }

   public final float getScore_DemandHumiliate() {
      return GameValues.peace.PEACE_HUMILIATE_COST_SCORE;
   }

   public final void demandVassalization() {
      if (this.demandVassalization) {
         this.demandVassalization = false;
         this.fScore = this.fScore + this.getScore_DemandVassalization();
      } else if (this.fScore >= this.getScore_DemandVassalization()) {
         this.demandVassalization = true;
         this.fScore = this.fScore - this.getScore_DemandVassalization();
      }

      this.buildAggressiveExpansion();
   }

   public final float getScore_DemandVassalization() {
      return this.fTotalProvinceWarScore_CivLost * GameValues.peace.PEACE_VASSALIZATION_COST_SCORE;
   }

   public final void demandSubjectTransfer(int civID) {
      if (this.lSubjectTransfer.contains(civID)) {
         for (int i = 0; i < this.lSubjectTransfer.size(); i++) {
            if (this.lSubjectTransfer.get(i) == civID) {
               this.lSubjectTransfer.remove(i);
               break;
            }
         }

         this.fScore = this.fScore + this.getScore_SubjectTransfer(civID);
      } else if (this.fScore >= this.getScore_SubjectTransfer(civID)) {
         this.lSubjectTransfer.add(civID);
         this.fScore = this.fScore - this.getScore_SubjectTransfer(civID);
      }

      this.buildAggressiveExpansion();
   }

   public final float getScore_SubjectTransfer(int civID) {
      return getTotalProvinceValue(civID) * GameValues.peace.PEACE_SUBJECT_TRANSFER_COST_SCORE;
   }

   public final void demandLiberateCiv(int civID, int liberateCivID) {
      if (this.lLiberateCiv.contains(liberateCivID)) {
         for (int i = 0; i < this.lLiberateCiv.size(); i++) {
            if (this.lLiberateCiv.get(i) == liberateCivID) {
               this.lLiberateCiv.remove(i);
               this.removeProvinces_LiberateCiv(civID, liberateCivID);
               break;
            }
         }

         this.fScore = this.fScore + this.getScore_LiberateCiv(civID, liberateCivID);
      } else if (this.fScore >= this.getScore_LiberateCiv(civID, liberateCivID)) {
         this.lLiberateCiv.add(liberateCivID);
         this.addProvinces_LiberateCiv(civID, liberateCivID);
         this.fScore = this.fScore - this.getScore_LiberateCiv(civID, liberateCivID);
      }

      this.buildAggressiveExpansion();
   }

   public final float getScore_LiberateCiv(int civID, int liberateCivID) {
      return getTotalProvinceValue_Liberate(civID, liberateCivID) * GameValues.peace.PEACE_LIBERATE_CIV_COST_SCORE;
   }

   public final void addProvinces_LiberateCiv(int civID, int liberateCivID) {
      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(civID).getProvinceID(i)).haveACore(liberateCivID)
            && !this.lProvinces_Liberate.contains(Game.getCiv(civID).getProvinceID(i))) {
            this.lProvinces_Liberate.add(Game.getCiv(civID).getProvinceID(i));
         }
      }
   }

   public final void removeProvinces_LiberateCiv(int civID, int liberateCivID) {
      int liberateCivSize = this.lLiberateCiv.size();

      for (int i = this.lProvinces_Liberate.size() - 1; i >= 0; i--) {
         boolean remove = true;

         for (int j = 0; j < liberateCivSize; j++) {
            if (Game.getProvince(this.lProvinces_Liberate.get(i)).haveACore(this.lLiberateCiv.get(j))) {
               remove = false;
               break;
            }
         }

         if (remove) {
            this.lProvinces_Liberate.remove(i);
         }
      }
   }

   public final void demandReligionConversion() {
      if (this.demandReligionConversion) {
         this.demandReligionConversion = false;
         this.fScore = this.fScore + this.getScore_DemandReligionConversion();
      } else if (this.fScore >= this.getScore_DemandReligionConversion()) {
         this.demandReligionConversion = true;
         this.fScore = this.fScore - this.getScore_DemandReligionConversion();
      }
   }

   public final float getScore_DemandReligionConversion() {
      return this.fTotalProvinceWarScore_CivLost * GameValues.peace.PEACE_RELIGION_CONVERSION_COST_SCORE;
   }

   public final void demandGovernmentChange() {
      if (this.demandGovernmentChange) {
         this.demandGovernmentChange = false;
         this.fScore = this.fScore + this.getScore_DemandGovernmentChange();
      } else if (this.fScore >= this.getScore_DemandGovernmentChange()) {
         this.demandGovernmentChange = true;
         this.fScore = this.fScore - this.getScore_DemandGovernmentChange();
      }
   }

   public final float getScore_DemandGovernmentChange() {
      return this.fTotalProvinceWarScore_CivLost * GameValues.peace.PEACE_GOVERNMENT_CHANGE_COST_SCORE;
   }

   public int getPopulation() {
      int out = 0;

      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         out += Game.getProvince(this.lProvinces.get(i)).getPopulationTotal();
      }

      return out;
   }

   public boolean enforceDemands(boolean byPlayer) {
      boolean updateFOG = DiplomacyManager.isAlly(this.iCivID, Game.player.iCivID) || DiplomacyManager.isAlly(this.iCivID2, Game.player.iCivID);
      Civilization civ = Game.getCiv(this.iCivID);
      Civilization civ2 = Game.getCiv(this.iCivID2);
      boolean updatePlayersWars = false;

      try {
         updatePlayersWars = !byPlayer && WarManager.lWars.get(this.warKey).isInThisWar(Game.player.iCivID);
      } catch (Exception var18) {
      }

      try {
         if (!byPlayer
            && !this.lProvinces.isEmpty()
            && GameValues.peace.AI_ABANDON_DEMAND_PROVINCES_IF_ARE_NOT_CONNECTED
            && !Game.ideologiesManager.getIdeology(civ2.getIdeologyID()).TRIBAL
            && Game.oR.nextInt(100) < GameValues.peace.AI_ABANDON_DEMAND_PROVINCES_IF_ARE_NOT_CONNECTED_CHANCE) {
            try {
               boolean connectionFound = false;
               boolean canAccessMainSea = false;

               for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
                  Province province = Game.getProvince(this.lProvinces.get(i));
                  if (province.accessToMainSea) {
                     canAccessMainSea = true;
                  }

                  for (int j = 0; j < province.getNeighboringProvincesSize(); j++) {
                     if (Game.getProvince(province.getNeighboringProvinces(j)).getCivID() == this.iCivID) {
                        connectionFound = true;
                        i = -1;
                        break;
                     }
                  }
               }

               if (!connectionFound) {
                  boolean removeProvinces = true;
                  if (canAccessMainSea) {
                     boolean canCivAccessMainSea = false;

                     for (int i = 0; i < civ.getNumOfProvinces(); i++) {
                        if (Game.getProvince(civ.getProvinceID(i)).accessToMainSea) {
                           canCivAccessMainSea = true;
                           break;
                        }
                     }

                     if (canCivAccessMainSea && Game.oR.nextInt(100) >= GameValues.peace.AI_ABANDON_DEMAND_PROVINCES_HAVE_ONLY_ACCESS_TO_MAIN_SEA_CHANCE) {
                        removeProvinces = false;
                     }
                  }

                  if (removeProvinces) {
                     for (int ix = this.lProvinces.size() - 1; ix >= 0; ix--) {
                        Game.getProvince(this.lProvinces.get(ix)).peaceTreatyIsTaken = false;
                        Game.getProvince(this.lProvinces.get(ix)).aiPeaceCivID = Game.getProvince(this.lProvinces.get(ix)).getCivID();
                        this.fScore = this.fScore + Game.getProvince(this.lProvinces.get(ix)).fProvinceValue;
                        this.lProvinces.remove(ix);
                     }

                     if (!this.demandReligionConversion) {
                        this.demandReligionConversion();
                     }

                     if (!this.demandGovernmentChange) {
                        this.demandGovernmentChange();
                     }

                     if (!this.demandVassalization) {
                        this.demandVassalization();
                     }

                     if (!this.demandWarReparations) {
                        this.demandWarReparations();
                     }

                     if (!this.demandHumiliate) {
                        this.demandHumiliate();
                     }

                     this.demandGold();
                     this.demandGold();
                     this.demandGold();
                     this.demandGold();
                     if (!this.demandMilitaryAccess) {
                        this.demandMilitaryAccess();
                     }
                  }
               }
            } catch (Exception var16) {
               CFG.exceptionStack(var16);
            }
         }

         for (int ix = this.lProvinces.size() - 1; ix >= 0; ix--) {
            int provID = this.lProvinces.get(ix);
            Game.getProvince(provID).setCivID(this.iCivID);
            if (!Game.getProvince(provID).haveACore(this.iCivID)) {
               Game.getProvince(provID).setEconomy(Game.getProvince(provID).getEconomy() * GameValues.peace.PEACE_ANNEX_PROVINCE_ECONOMY_CHANGE);
               Game.getProvince(provID).setTaxEfficiency(Game.getProvince(provID).getTaxEfficiency() * GameValues.peace.PEACE_ANNEX_PROVINCE_TAX_CHANGE);
               Game.getProvince(provID).setGrowthRate(Game.getProvince(provID).getGrowthRate() * GameValues.peace.PEACE_ANNEX_PROVINCE_GROWTH_RATE_CHANGE);
               Game.getProvince(provID).setManpower(Game.getProvince(provID).getManpower() * GameValues.peace.PEACE_ANNEX_PROVINCE_MANPOWER_CHANGE);
            }

            Game.getProvince(provID)
               .setRevulutionaryRisk(Math.min(Game.getProvince(provID).getRevulutionaryRisk(), GameValues.peace.PEACE_ANNEX_PROVINCE_MAX_UNREST));
            civ.eventsData3.addConqueredProvinces(1);
            if (civ.getCivID() == Game.player.iCivID) {
               Game.stats.civStats.cp++;
            }
         }

         if (!byPlayer && !this.lProvinces.isEmpty() && civ2.getNumOfProvinces() > 2 && GameValues.peace.AI_PEACE_EXTRA_DEMAND_SURROUNDED_PROVINCES) {
            List<Integer> extraDemand = new ArrayList<>();

            for (int ix = 0; ix < civ2.getNumOfProvinces(); ix++) {
               if (civ2.getCapitalProvinceID() != civ2.getProvinceID(ix)) {
                  Province province = Game.getProvince(civ2.getProvinceID(ix));
                  int numOfConnectionWithCivID = 0;

                  for (int jx = 0; jx < province.getNeighboringProvincesSize(); jx++) {
                     if (Game.getProvince(province.getNeighboringProvinces(jx)).getCivID() == this.iCivID2) {
                        numOfConnectionWithCivID = 0;
                        break;
                     }

                     if (Game.getProvince(province.getNeighboringProvinces(jx)).getCivID() == this.iCivID) {
                        numOfConnectionWithCivID++;
                     }
                  }

                  if (numOfConnectionWithCivID > 0) {
                     extraDemand.add(civ2.getProvinceID(ix));
                  }
               }
            }

            for (int ixx = extraDemand.size() - 1; ixx >= 0; ixx--) {
               Game.getProvince(extraDemand.get(ixx)).setCivID(this.iCivID);
               this.lProvinces.add(extraDemand.get(ixx));
               civ.eventsData3.addConqueredProvinces(1);
               if (civ.getCivID() == Game.player.iCivID) {
                  Game.stats.civStats.cp++;
               }
            }
         }

         if (Game.FOG_OF_WAR && updateFOG) {
            for (int ixxx = this.lProvinces.size() - 1; ixxx >= 0; ixxx--) {
               Game.player.fog.updateFogOfWar_All(this.lProvinces.get(ixxx));
            }
         }

         if (this.demandVassalization && civ2.getNumOfProvinces() > 0) {
            int puppetBefore = civ2.getPuppetOfCivID();
            civ2.setPuppetOfCivID(this.iCivID);
            Game.getCiv(puppetBefore).updateArmyImgID();
         }

         if (this.demandWarReparations && civ2.getNumOfProvinces() > 0) {
            CivilizationBonuses tBonus = new CivilizationBonuses();
            tBonus.MonthlyIncome = getWarReparationsPerMonth(this.iCivID2);
            tBonus.TempTurnID = Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_REPARATIONS_TURNS;
            civ.addCivilizationBonus_Temporary(tBonus);
            CivilizationBonuses tBonus2 = new CivilizationBonuses();
            tBonus2.MonthlyIncome = getWarReparationsPerMonth(this.iCivID2) * -1.0F;
            tBonus2.TempTurnID = Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_REPARATIONS_TURNS;
            civ2.addCivilizationBonus_Temporary(tBonus2);
         }

         if (this.demandGovernmentChange && civ2.getIdeologyID() != civ.getIdeologyID()) {
            Game.addSimpleTask(new Game.SimpleTask("changeGovernmentType" + this.iCivID2 + "_" + civ.getIdeologyID(), this.iCivID2, civ.getIdeologyID()) {
               @Override
               public void update() {
                  Game.ideologiesManager.changeGovernmentType(this.id, this.id2, true);
                  Civilization var10000 = Game.getCiv(this.id);
                  var10000.fGold = var10000.fGold + GameValues.government.CHANGE_GOVERNMENT_COST;
                  var10000 = Game.getCiv(this.id);
                  var10000.fLegacy = var10000.fLegacy + GameValues.government.CHANGE_GOVERNMENT_COST_LEGACY;
               }
            });
         }

         try {
            WarManager.lWars.get(this.warKey).updateCapitalProvinceID();
         } catch (Exception var14) {
            CFG.exceptionStack(var14);
         }

         if (this.demandGold > 0) {
            float demandedGold = this.getGold_PerDemand();
            civ.fGold += demandedGold;
            civ2.fGold -= demandedGold;
         }

         if (this.demandHumiliate) {
            civ.fLegacy = civ.fLegacy + GameValues.peace.PEACE_HUMILIATE_LEGACY_GAIN;
            civ2.fLegacy = civ2.fLegacy - GameValues.peace.PEACE_HUMILIATE_LEGACY_LOSER;
         }

         if (this.demandReligionConversion && civ2.getReligionID() != civ.getReligionID()) {
            civ2.setReligionID_UpdateBonuses(civ.getReligionID());

            try {
               if (civ2.getCapitalProvinceID() >= 0
                  && Game.getProvince(civ2.getCapitalProvinceID()).getCivID() == this.iCivID2
                  && Game.getProvince(civ2.getCapitalProvinceID()).getReligion() != civ2.getReligionID()) {
                  Game.getProvince(civ2.getCapitalProvinceID()).setReligion(civ2.getReligionID());
               }
            } catch (Exception var13) {
               CFG.exceptionStack(var13);
            }

            try {
               int toConvert = (int)Math.floor(civ2.getNumOfProvinces() * GameValues.peace.PEACE_RELIGION_CONVERSION_PROVINCES_CONVERT) - 1;
               if (toConvert > 0) {
                  List<Integer> tProvinces = new ArrayList<>();

                  for (int ixxx = 0; ixxx < civ2.getNumOfProvinces(); ixxx++) {
                     tProvinces.add(civ2.getProvinceID(ixxx));
                  }

                  toConvert = Math.min(toConvert, tProvinces.size());

                  for (int ixxx = toConvert - 1; ixxx >= 0 && tProvinces.size() > 0; ixxx--) {
                     int tR = Game.oR.nextInt(tProvinces.size());
                     if (Game.getProvince(tProvinces.get(tR)).getCivID() == this.iCivID2
                        && Game.getProvince(tProvinces.get(tR)).getReligion() != civ2.getReligionID()) {
                        Game.getProvince(tProvinces.get(tR)).setReligion(civ2.getReligionID());
                     }

                     tProvinces.remove(tR);
                  }
               }
            } catch (Exception var15) {
               CFG.exceptionStack(var15);
            }
         }
      } catch (Exception var17) {
         CFG.exceptionStack(var17);
      }

      civ.setAggressiveExpansion(
         civ.getAggressiveExpansion() + Math.min(GameValues.aggressiveExpansion.PEACE_TREATY_AE_FROM_WAR_LIMIT, this.fAggressiveExpansion)
      );

      for (int ixxx = 0; ixxx < this.lSubjectTransfer.size(); ixxx++) {
         if (Game.getCiv(this.lSubjectTransfer.get(ixxx)).getPuppetOfCivID() == this.iCivID2) {
            Game.getCiv(this.lSubjectTransfer.get(ixxx)).setPuppetOfCivID(this.iCivID);
            if (this.iCivID2 == Game.player.iCivID) {
               Game.getCiv(this.lSubjectTransfer.get(ixxx)).updateArmyImgID();
            }
         }
      }

      for (int ixxxx = 0; ixxxx < this.lLiberateCiv.size(); ixxxx++) {
         boolean liberated = false;

         for (int jx = civ2.getNumOfProvinces() - 1; jx >= 0; jx--) {
            if (Game.getProvince(civ2.getProvinceID(jx)).haveACore(this.lLiberateCiv.get(ixxxx))) {
               Game.getProvince(civ2.getProvinceID(jx)).setCivID(this.lLiberateCiv.get(ixxxx));
               liberated = true;
            }
         }

         if (liberated && Game.getCiv(this.lLiberateCiv.get(ixxxx)).getNumOfProvinces() > 0) {
            if (Game.getCiv(this.lLiberateCiv.get(ixxxx)).getPuppetOfCivID() != this.lLiberateCiv.get(ixxxx)) {
               Game.getCiv(this.lLiberateCiv.get(ixxxx)).setPuppetOfCivID(this.lLiberateCiv.get(ixxxx));
            }

            if (Game.getProvince(Game.getCiv(this.lLiberateCiv.get(ixxxx)).getCapitalProvinceID()).getCivID() == this.lLiberateCiv.get(ixxxx)) {
               Game.getCiv(this.lLiberateCiv.get(ixxxx)).setCapitalProvinceID(Game.getCiv(this.lLiberateCiv.get(ixxxx)).getCapitalProvinceID());
            } else if (Game.getCiv(this.lLiberateCiv.get(ixxxx)).getCapitalProvinceID() < 0
               || Game.getProvince(Game.getCiv(this.lLiberateCiv.get(ixxxx)).getCapitalProvinceID()).getCivID() != this.lLiberateCiv.get(ixxxx)) {
               Game.getCiv(this.lLiberateCiv.get(ixxxx)).moveCapital_ToLargestProvince();
            }

            civ.diplomacy.setRelation(civ.getCivID(), this.lLiberateCiv.get(ixxxx), GameValues.peace.PEACE_LIBERATE_CIV_RELATIONS);
            Game.getCiv(this.lLiberateCiv.get(ixxxx))
               .diplomacy
               .setRelation(this.lLiberateCiv.get(ixxxx), this.iCivID, GameValues.peace.PEACE_LIBERATE_CIV_RELATIONS);
            civ.fLegacy = civ.fLegacy + GameValues.peace.PEACE_LIBERATE_CIV_LEGACY;
            if (Game.getCiv(this.iCivID).getResearchedTechnologies() > Game.getCiv(this.lLiberateCiv.get(ixxxx)).getResearchedTechnologies()) {
               for (int a = 0; a < TechnologyTree.iTechnologySize; a++) {
                  if (Game.getCiv(this.iCivID).getTechResearched(a)) {
                     Game.getCiv(this.lLiberateCiv.get(ixxxx)).addTechnology(a, false);
                  }
               }
            }

            DiplomacyManager.addGuarantee(this.iCivID, this.lLiberateCiv.get(ixxxx));
         }
      }

      try {
         WarManager.lWars.get(this.warKey).peaceTreaty();
      } catch (Exception var12) {
         CFG.LOG("enforceDemands");
         CFG.exceptionStack(var12);
      }

      if (Game.FOG_OF_WAR && updateFOG) {
         if (this.demandVassalization && civ2.getNumOfProvinces() > 0) {
            for (int ixxxx = 0; ixxxx < civ2.getNumOfProvinces(); ixxxx++) {
               Game.player.fog.updateFogOfWar_All(civ2.getProvinceID(ixxxx));
            }
         }

         for (int ixxxx = 0; ixxxx < this.lSubjectTransfer.size(); ixxxx++) {
            for (int jxx = 0; jxx < Game.getCiv(this.lSubjectTransfer.get(ixxxx)).getNumOfProvinces(); jxx++) {
               Game.player.fog.updateFogOfWar_All(Game.getCiv(this.lSubjectTransfer.get(ixxxx)).getProvinceID(jxx));
            }
         }
      }

      if (this.demandReturnProvinces) {
         List<Integer> returnProvinces = new ArrayList<>();

         for (int ixxxx = 0; ixxxx < civ2.getNumOfProvinces(); ixxxx++) {
            if (civ2.getProvinceID(ixxxx) != civ2.getCapitalProvinceID()
               && Game.getProvince(civ2.getProvinceID(ixxxx)).getCivID() == this.iCivID2
               && Game.getProvince(civ2.getProvinceID(ixxxx)).iCoresSize > 0
               && Game.getProvince(civ2.getProvinceID(ixxxx)).getCore(0) != this.iCivID2) {
               returnProvinces.add(civ2.getProvinceID(ixxxx));
            }
         }

         for (int ixxxxx = returnProvinces.size() - 1; ixxxxx >= 0; ixxxxx--) {
            Game.getProvince(returnProvinces.get(ixxxxx)).setCivID(Game.getProvince(returnProvinces.get(ixxxxx)).getCore(0));
         }
      }

      WarManager.lWars.remove(this.warKey);
      WarManager.iWarsSize = WarManager.lWars.size();
      Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID);
      Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID2);
      updateRivals(this.iCivID);
      updateRivals(this.iCivID2);
      this.updateSpecialAlliances(this.iCivID);
      this.updateSpecialAlliances(this.iCivID2);
      this.updateVassals(this.iCivID);
      this.updateVassals(this.iCivID2);
      this.moveArmiesToOwnTerritory(this.iCivID);
      this.moveArmiesToOwnTerritory(this.iCivID2);
      Game.getCiv(this.iCivID).diplomacy.updateAlliance_ConqueredProvinces(this.iCivID);
      Game.getCiv(this.iCivID2).diplomacy.updateAlliance_ConqueredProvinces(this.iCivID2);
      if (civ2.getNumOfProvinces() <= 0 && civ2.getPuppetOfCivID() != this.iCivID2) {
         civ2.setPuppetOfCivID(this.iCivID2);
      }

      if (updatePlayersWars) {
         try {
            InGame_Info.iCivID = this.iCivID;
            InGame_Info.iCivID2 = this.iCivID2;
            Game.menuManager.rebuildInGame_Info(Game.lang.get("PeaceTreaty"), Game_Calendar.getCurrentDate());
            InGame_Info.imgID = Images.infoDiplomacy;
            Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
               @Override
               public void update() {
                  Game.menuManager.rebuildInGame_Wars();
               }
            });
            if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
               Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
                  @Override
                  public void update() {
                     ProvinceBorderManager.updateProvinceBorder();
                  }
               });
            }

            if (Game.menuManager.getVisibleInGame_War() && InGame_War.key.equals(this.warKey)) {
               Game.menuManager.setVisibleInGame_War(false);
            }
         } catch (Exception var11) {
            CFG.exceptionStack(var11);
         }
      }

      return true;
   }

   public final void moveArmiesToOwnTerritory(int civID) {
      try {
         Civilization civ = Game.getCiv(civID);

         for (int i = 0; i < civ.getNumOfProvinces(); i++) {
            int provinceID = civ.getProvinceID(i);

            for (int j = Game.getProvince(provinceID).getArmySize() - 1; j >= 0; j--) {
               ArmyDivision armyDivision = Game.getProvince(provinceID).getArmy(j);
               if (!MoveUnits.isFriendlyProvince_OrAtWAr(armyDivision.civID, provinceID) && Game.getCiv(armyDivision.civID).getNumOfProvinces() > 0) {
                  int bestProvince = Game.getCiv(armyDivision.civID).getProvinceID(0);
                  float bestDistance = Game.getDistanceFromProvinceToProvince(provinceID, Game.getCiv(armyDivision.civID).getProvinceID(0));

                  for (int b = 1; b < Game.getCiv(armyDivision.civID).getNumOfProvinces(); b++) {
                     if (bestDistance > Game.getDistanceFromProvinceToProvince(provinceID, Game.getCiv(armyDivision.civID).getProvinceID(b))) {
                        bestProvince = Game.getCiv(armyDivision.civID).getProvinceID(b);
                        bestDistance = Game.getDistanceFromProvinceToProvince(provinceID, Game.getCiv(armyDivision.civID).getProvinceID(b));
                     }
                  }

                  armyDivision.inBattle = false;
                  armyDivision.inRetreat = false;
                  Game.getProvince(provinceID).removeArmy(armyDivision.key);
                  Game.getProvince(bestProvince).addArmy(armyDivision);
               }
            }
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }
   }

   public final void updateVassals(int civID) {
      try {
         if (Game.getCiv(civID).getNumOfProvinces() <= 0) {
            for (int i = Game.getCiv(civID).diplomacy.iVassalsSize - 1; i >= 0; i--) {
               Game.getCiv(Game.getCiv(civID).diplomacy.lVassals.get(i).c).setPuppetOfCivID(Game.getCiv(civID).diplomacy.lVassals.get(i).c);
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateSpecialAlliances(int civID) {
      if (Game.getCiv(civID).getNumOfProvinces() <= 0) {
         for (int i = 0; i < Game.getCiv(civID).inAllianceSize; i++) {
            if (Game.alliancesSpecial.get(Game.getCiv(civID).inAlliance.get(i)).iLeaderCivID == civID) {
               Game.alliancesSpecial.get(Game.getCiv(civID).inAlliance.get(i)).elections();
            }
         }
      }
   }

   public static void updateRivals(int civID) {
      if (Game.getCiv(civID).getNumOfProvinces() <= 0) {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            Game.getCiv(i).diplomacy.removeRival(civID);
         }
      }
   }

   public static float getWarReparationsPerMonth(int iCivID) {
      return Math.max(0.0F, Game.getCiv(iCivID).fTotalIncomePerMonth * GameValues.peace.PEACE_WAR_REPARATIONS);
   }

   public static float getTotalProvinceValue(int nCivID) {
      float out = 0.0F;

      for (int i = 0; i < Game.getCiv(nCivID).getNumOfProvinces(); i++) {
         out += Game.getProvince(Game.getCiv(nCivID).getProvinceID(i)).fProvinceValue;
      }

      return out;
   }

   public static float getTotalProvinceValue_Liberate(int nCivID, int liberateCivID) {
      float out = 0.0F;

      for (int i = 0; i < Game.getCiv(nCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(nCivID).getProvinceID(i)).haveACore(liberateCivID)) {
            out += Game.getProvince(Game.getCiv(nCivID).getProvinceID(i)).fProvinceValue;
         }
      }

      return out;
   }

   public static List<Integer> getCivsPossibleToLiberate(int iCivID) {
      List<Integer> tCivs = new ArrayList<>();

      try {
         for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
            for (int j = 0; j < Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).iCoresSize; j++) {
               if (Game.getCiv(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getCore(j)).getNumOfProvinces() == 0
                  && !tCivs.contains(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getCore(j))) {
                  tCivs.add(Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getCore(j));
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return tCivs;
   }

   public static int getLiberateCivProvinces(int iCivID, int liberateCivID) {
      int out = 0;

      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).haveACore(liberateCivID)) {
            out++;
         }
      }

      return out;
   }

   public static long getLiberateCivPopulation(int iCivID, int liberateCivID) {
      long out = 0L;

      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).haveACore(liberateCivID)) {
            out += Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getPopulationTotal();
         }
      }

      return out;
   }

   public static int getLiberateCivEconomy(int iCivID, int liberateCivID) {
      int out = 0;

      for (int i = 0; i < Game.getCiv(iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).haveACore(liberateCivID)) {
            out = (int)(out + Game.getProvince(Game.getCiv(iCivID).getProvinceID(i)).getEconomyWithBonuses());
         }
      }

      return out;
   }

   public static void moveAllArmiesToOwnTerritory(int civID) {
      if (Game.getCiv(civID).getNumOfProvinces() > 0) {
         for (int i = Game.getCiv(civID).iArmyPositionSize - 1; i >= 0; i--) {
            if (!MoveUnits.isFriendlyProvince_OrAtWAr(civID, Game.getCiv(civID).getArmyPosition(i))) {
               int id = Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmyKeyID(Game.getCiv(civID).getArmyPositionKey(i), civID);
               if (id >= 0) {
                  if (Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(id).inMovement) {
                     Game.getCiv(civID).removeMove(Game.getCiv(civID).getArmyPositionKey(i));
                  }

                  int bestID = 0;
                  float bestDistance = Game.getDistanceFromProvinceToProvince(Game.getCiv(civID).getArmyPosition(i), Game.getCiv(civID).getProvinceID(0));

                  for (int j = 1; j < Game.getCiv(civID).getNumOfProvinces(); j++) {
                     if (!Game.getProvince(Game.getCiv(civID).getProvinceID(j)).isOccupied()
                        && !Game.getProvinceData4(Game.getCiv(civID).getProvinceID(j)).isUnderSiege()) {
                        float tDistance = Game.getDistanceFromProvinceToProvince(Game.getCiv(civID).getArmyPosition(i), Game.getCiv(civID).getProvinceID(j));
                        if (bestDistance > tDistance) {
                           bestDistance = tDistance;
                           bestID = j;
                        }
                     }
                  }

                  ArmyDivision extraRegroup = Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).getArmy(Game.getCiv(civID).getArmyPositionKey(i));
                  if (extraRegroup != null) {
                     extraRegroup.inBattle = false;
                     extraRegroup.inRetreat = false;
                     Game.getProvince(Game.getCiv(civID).getArmyPosition(i)).removeArmy(Game.getCiv(civID).getArmyPositionKey(i));
                     Game.getProvince(Game.getCiv(civID).getProvinceID(bestID)).addArmy(extraRegroup);
                  }
               }
            }
         }
      }
   }
}
