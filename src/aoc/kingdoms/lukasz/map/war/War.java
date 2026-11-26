package aoc.kingdoms.lukasz.map.war;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.PeaceTreaty;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import java.util.ArrayList;
import java.util.List;

public class War {
   public String key;
   public int iWarTurnID = 0;
   public int lastFight_TurnID = 0;
   public float warScore = 0.0F;
   public float warScoreFromBattles = 0.0F;
   public float warScoreFromOccupiedProvinces = 0.0F;
   public float tickingWarScore = 0.0F;
   public List<WarCivilization> lAggressors = new ArrayList<>();
   public List<WarCivilization> lDefenders = new ArrayList<>();
   public boolean conquerVassal = false;
   public boolean isCoalition = false;

   public War() {
   }

   public War(int nAggressor, int nDefender, String tKey, boolean conquerVassal, boolean isCoalition) {
      this.key = tKey;
      this.iWarTurnID = Game_Calendar.TURN_ID;
      this.lastFight_TurnID = Game_Calendar.TURN_ID;
      this.warScore = 0.0F;
      this.conquerVassal = conquerVassal;
      this.isCoalition = isCoalition;
      this.addAggressor(nAggressor);
      this.addDefender(nDefender);
   }

   public final void updateWars_TickingWarScore() {
      float tTickingWarScore = (this.warScoreFromOccupiedProvinces + this.warScoreFromBattles) * GameValues.war.TICKING_WAR_SCORE_EACH_MONTH;
      if (tTickingWarScore > 0.0F) {
         tTickingWarScore *= 1.0F + Game.getCiv(this.lDefenders.get(0).iCivID).getWarWeariness() / 100.0F;
      } else {
         tTickingWarScore *= 1.0F + Game.getCiv(this.lAggressors.get(0).iCivID).getWarWeariness() / 100.0F;
      }

      if (tTickingWarScore > 0.0F) {
         if (this.tickingWarScore + tTickingWarScore > GameValues.war.TICKING_WAR_SCORE_LIMIT) {
            tTickingWarScore = Math.max(0.0F, GameValues.war.TICKING_WAR_SCORE_LIMIT - this.tickingWarScore);
         }
      } else if (this.tickingWarScore + tTickingWarScore < -GameValues.war.TICKING_WAR_SCORE_LIMIT) {
         tTickingWarScore = -Math.max(0.0F, GameValues.war.TICKING_WAR_SCORE_LIMIT + this.tickingWarScore);
      }

      this.warScore += tTickingWarScore;
      this.tickingWarScore += tTickingWarScore;
      if (this.warScore > 0.0F && this.tickingWarScore < 0.0F) {
         this.warScore = this.warScore - this.tickingWarScore;
         this.tickingWarScore = 0.0F;
      } else if (this.warScore < 0.0F && this.tickingWarScore > 0.0F) {
         this.warScore = this.warScore + this.tickingWarScore;
         this.tickingWarScore = 0.0F;
      }
   }

   public final void updateWars_AllProvincesOccupied() {
      boolean allOccupied = true;
      Civilization civDef = Game.getCiv(this.lDefenders.get(0).iCivID);

      for (int i = 0; i < civDef.getNumOfProvinces(); i++) {
         if (Game.getProvinceData(civDef.getProvinceID(i)).getOccupiedByCivID() == 0) {
            allOccupied = false;
            break;
         }
      }

      if (allOccupied) {
         this.tickingWarScore = this.tickingWarScore + GameValues.war.TICKING_WAR_SCORE_IF_ALL_PROVINCES_OCCUPIED;
         this.warScore = this.warScore + GameValues.war.TICKING_WAR_SCORE_IF_ALL_PROVINCES_OCCUPIED;
      } else {
         allOccupied = true;
         Civilization civAgr = Game.getCiv(this.lAggressors.get(0).iCivID);

         for (int ix = 0; ix < civAgr.getNumOfProvinces(); ix++) {
            if (Game.getProvinceData(civAgr.getProvinceID(ix)).getOccupiedByCivID() == 0) {
               allOccupied = false;
               break;
            }
         }

         if (allOccupied) {
            this.tickingWarScore = this.tickingWarScore - GameValues.war.TICKING_WAR_SCORE_IF_ALL_PROVINCES_OCCUPIED;
            this.warScore = this.warScore - GameValues.war.TICKING_WAR_SCORE_IF_ALL_PROVINCES_OCCUPIED;
         }
      }
   }

   public final void addWarScore(float nWarScore, int civA, int civB) {
      this.warScore = this.warScore + this.addWarScore_ValueToAdd(nWarScore, civA, civB);
   }

   public final void addWarScore_Just(float nWarScore) {
      this.warScore += nWarScore;
   }

   public final float addWarScore_ValueToAdd(float nWarScore, int civA, int civB) {
      return (civA != this.lAggressors.get(0).iCivID || civB != this.lDefenders.get(0).iCivID)
            && (civB != this.lAggressors.get(0).iCivID || civA != this.lDefenders.get(0).iCivID)
         ? nWarScore * GameValues.war.WAR_SCORE_ALLIES
         : nWarScore;
   }

   public final float addWarScore_ValueToAdd_Province(float nWarScore, int civA, int civB, int provinceID) {
      return (civA != this.lAggressors.get(0).iCivID || civB != this.lDefenders.get(0).iCivID)
            && (civB != this.lAggressors.get(0).iCivID || civA != this.lDefenders.get(0).iCivID)
            && Game.getProvince(provinceID).getCivID() != this.lDefenders.get(0).iCivID
            && Game.getProvince(provinceID).getCivID() != this.lAggressors.get(0).iCivID
         ? nWarScore * GameValues.war.WAR_SCORE_ALLIES
         : nWarScore;
   }

   public final void loadSave_AddInWar() {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         Game.getCiv(this.lDefenders.get(i).iCivID).diplomacy.addWar(this.key, this.lDefenders.get(i).iCivID);
      }

      for (int i = 0; i < this.lAggressors.size(); i++) {
         Game.getCiv(this.lAggressors.get(i).iCivID).diplomacy.addWar(this.key, this.lAggressors.get(i).iCivID);
      }
   }

   public final void addAggressor(int nCivID) {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == nCivID) {
            return;
         }
      }

      for (int ix = 0; ix < this.lAggressors.size(); ix++) {
         if (this.lAggressors.get(ix).iCivID == nCivID) {
            return;
         }
      }

      this.lAggressors.add(new WarCivilization(nCivID));
      Game.getCiv(nCivID).diplomacy.addWar(this.key, nCivID);
      if (nCivID == Game.player.iCivID) {
         Game.player.playerStats.numOfWars++;
         Game.stats.civStats.nw++;
      }

      Game.getCiv(nCivID).eventsData3.addNumOfWars(1);

      for (int ixx = 0; ixx < this.lDefenders.size(); ixx++) {
         if (!DiplomacyManager.isAtWar(nCivID, this.lDefenders.get(ixx).iCivID)) {
            DiplomacyManager.declareWar_UpdateRelation(this.lDefenders.get(ixx).iCivID, nCivID);
         }
      }

      if (this.lAggressors.size() > 1
         && Game.getCiv(nCivID).getPuppetOfCivID() == this.lAggressors.get(0).iCivID
         && !DiplomacyManager.isAlly_AllianceCheck(Game.getCiv(nCivID).getPuppetOfCivID(), this.lAggressors.get(0).iCivID)) {
         Game.getCiv(this.lAggressors.get(0).iCivID).diplomacy.setLibertyDesire_Change(nCivID, GameValues.vassal.LIBERTY_DESIRE_LORD_CALL_TO_WAR);
      }
   }

   public final void removeAggressor(int nCivID) {
      for (int i = 0; i < this.lAggressors.size(); i++) {
         if (this.lAggressors.get(i).iCivID == nCivID) {
            this.lAggressors.remove(i);
            return;
         }
      }
   }

   public final void addDefender(int nCivID) {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == nCivID) {
            return;
         }
      }

      for (int ix = 0; ix < this.lAggressors.size(); ix++) {
         if (this.lAggressors.get(ix).iCivID == nCivID) {
            return;
         }
      }

      this.lDefenders.add(new WarCivilization(nCivID));
      Game.getCiv(nCivID).diplomacy.addWar(this.key, nCivID);
      if (nCivID == Game.player.iCivID) {
         Game.player.playerStats.numOfWars++;
         Game.stats.civStats.nw++;
      }

      Game.getCiv(nCivID).eventsData3.addNumOfWars(1);

      for (int ixx = 0; ixx < this.lAggressors.size(); ixx++) {
         if (!DiplomacyManager.isAtWar(nCivID, this.lAggressors.get(ixx).iCivID)) {
            DiplomacyManager.declareWar_UpdateRelation(this.lAggressors.get(ixx).iCivID, nCivID);
         }
      }

      if (this.lDefenders.size() > 1
         && Game.getCiv(nCivID).getPuppetOfCivID() == this.lDefenders.get(0).iCivID
         && !DiplomacyManager.isAlly_AllianceCheck(Game.getCiv(nCivID).getPuppetOfCivID(), this.lDefenders.get(0).iCivID)) {
         Game.getCiv(this.lDefenders.get(0).iCivID).diplomacy.setLibertyDesire_Change(nCivID, GameValues.vassal.LIBERTY_DESIRE_LORD_CALL_TO_WAR);
      }
   }

   public final void removeDefender(int nCivID) {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == nCivID) {
            this.lDefenders.remove(i);
            return;
         }
      }
   }

   public final void removeCiv(int nCivID) {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == nCivID) {
            this.lDefenders.remove(i);
            return;
         }
      }

      for (int ix = 0; ix < this.lAggressors.size(); ix++) {
         if (this.lAggressors.get(ix).iCivID == nCivID) {
            this.lAggressors.remove(ix);
            return;
         }
      }
   }

   public final boolean isAggressor(int iCivID) {
      for (int i = 0; i < this.lAggressors.size(); i++) {
         if (this.lAggressors.get(i).iCivID == iCivID) {
            return true;
         }
      }

      return false;
   }

   public final boolean isDefender(int iCivID) {
      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == iCivID) {
            return true;
         }
      }

      return false;
   }

   public final boolean areInThisWar(int iCivA, int iCivB) {
      return this.isAggressor(iCivA) && this.isDefender(iCivB) ? true : this.isAggressor(iCivB) && this.isDefender(iCivA);
   }

   public final boolean isInThisWar(int iCivA) {
      return this.isAggressor(iCivA) || this.isDefender(iCivA);
   }

   public final void peaceTreaty() {
      boolean updatePlayer = this.isInThisWar(Game.player.iCivID);
      Game.getCiv(this.lDefenders.get(0).iCivID).diplomacy.addTruce(this.lAggressors.get(0).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);
      Game.getCiv(this.lAggressors.get(0).iCivID).diplomacy.addTruce(this.lDefenders.get(0).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);

      for (int i = this.lAggressors.size() - 1; i > 0; i--) {
         Game.getCiv(this.lDefenders.get(0).iCivID).diplomacy.addTruce(this.lAggressors.get(i).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);
         Game.getCiv(this.lAggressors.get(i).iCivID).diplomacy.addTruce(this.lDefenders.get(0).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);
      }

      for (int i = this.lDefenders.size() - 1; i > 0; i--) {
         Game.getCiv(this.lAggressors.get(0).iCivID).diplomacy.addTruce(this.lDefenders.get(i).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);
         Game.getCiv(this.lDefenders.get(i).iCivID).diplomacy.addTruce(this.lAggressors.get(0).iCivID, Game_Calendar.TURN_ID + GameValues.peace.PEACE_WAR_DAYS);
      }

      Game.battleManager.stopAllBattles_PeaceTreaty(this.key);

      try {
         for (int i = this.lAggressors.size() - 1; i >= 0; i--) {
            Civilization civ = Game.getCiv(this.lAggressors.get(i).iCivID);
            if (civ.getCivID() != Game.player.iCivID) {
               for (int j = civ.getMoveUnitsSize() - 1; j >= 0; j--) {
                  int toCivID = Game.getProvince(civ.getMoveUnits(j).getToProvinceLastID()).getCivID();

                  for (int k = this.lDefenders.size() - 1; k >= 0; k--) {
                     if (this.lDefenders.get(k).iCivID == toCivID) {
                        civ.removeMove(j);
                        break;
                     }
                  }
               }
            }
         }

         for (int ix = this.lDefenders.size() - 1; ix >= 0; ix--) {
            Civilization civ = Game.getCiv(this.lDefenders.get(ix).iCivID);
            if (civ.getCivID() != Game.player.iCivID) {
               for (int j = civ.getMoveUnitsSize() - 1; j >= 0; j--) {
                  int toCivID = Game.getProvince(civ.getMoveUnits(j).getToProvinceLastID()).getCivID();

                  for (int kx = this.lAggressors.size() - 1; kx >= 0; kx--) {
                     if (this.lAggressors.get(kx).iCivID == toCivID) {
                        civ.removeMove(j);
                        break;
                     }
                  }
               }
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      for (int ixx = this.lAggressors.size() - 1; ixx >= 0; ixx--) {
         for (int j = this.lDefenders.size() - 1; j >= 0; j--) {
            try {
               DiplomacyManager.declareWar_UpdateRelation_Peace(this.lAggressors.get(ixx).iCivID, this.lDefenders.get(j).iCivID, this.key);
               this.retakeOccupiedProvinces(this.lAggressors.get(ixx).iCivID, this.lDefenders.get(j).iCivID);
               this.retakeOccupiedProvinces(this.lDefenders.get(j).iCivID, this.lAggressors.get(ixx).iCivID);
               if (this.lAggressors.get(ixx).iCivID == Game.player.iCivID) {
                  Game.getCiv(this.lDefenders.get(j).iCivID).updateArmyImgID();
               } else if (this.lDefenders.get(j).iCivID == Game.player.iCivID) {
                  Game.getCiv(this.lAggressors.get(ixx).iCivID).updateArmyImgID();
               }
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         }
      }

      for (int ixx = this.lAggressors.size() - 1; ixx >= 0; ixx--) {
         if (Game.getCiv(this.lAggressors.get(ixx).iCivID).getNumOfProvinces() <= 0) {
            Game.getCiv(this.lAggressors.get(ixx).iCivID).removeAllArmies();
         } else {
            PeaceTreaty.moveAllArmiesToOwnTerritory(this.lAggressors.get(ixx).iCivID);
            if (this.lAggressors.get(ixx).iCivID != Game.player.iCivID && !Game.getCiv(this.lAggressors.get(ixx).iCivID).diplomacy.isAtWar()) {
               Game.gameThread
                  .addAI_SimpleTask(
                     new Game.SimpleTask("update_ReorganizeArmiesAtPeace" + this.lAggressors.get(ixx).iCivID, this.lAggressors.get(ixx).iCivID) {
                        @Override
                        public void update() {
                           Game.aiManager.update_ReorganizeArmiesAtPeace(this.id);
                        }
                     }
                  );
            }
         }
      }

      for (int ixxx = this.lDefenders.size() - 1; ixxx >= 0; ixxx--) {
         if (Game.getCiv(this.lDefenders.get(ixxx).iCivID).getNumOfProvinces() <= 0) {
            Game.getCiv(this.lDefenders.get(ixxx).iCivID).removeAllArmies();
         } else {
            PeaceTreaty.moveAllArmiesToOwnTerritory(this.lDefenders.get(ixxx).iCivID);
         }

         if (this.lDefenders.get(ixxx).iCivID != Game.player.iCivID && !Game.getCiv(this.lDefenders.get(ixxx).iCivID).diplomacy.isAtWar()) {
            Game.gameThread
               .addAI_SimpleTask(new Game.SimpleTask("update_ReorganizeArmiesAtPeace" + this.lDefenders.get(ixxx).iCivID, this.lDefenders.get(ixxx).iCivID) {
                  @Override
                  public void update() {
                     Game.aiManager.update_ReorganizeArmiesAtPeace(this.id);
                  }
               });
         }
      }

      this.lAggressors.clear();
      this.lDefenders.clear();
      if (updatePlayer && GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
         Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
            @Override
            public void update() {
               ProvinceBorderManager.updateProvinceBorder();
            }
         });
      }
   }

   public final void retakeOccupiedProvinces(int iCivA, int iCivB) {
      for (int i = 0; i < Game.getCiv(iCivA).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iCivA).getProvinceID(i)).isOccupied()
            && Game.getProvinceData(Game.getCiv(iCivA).getProvinceID(i)).getOccupiedByCivID() == iCivB) {
            Game.getProvince(Game.getCiv(iCivA).getProvinceID(i)).retakeOccupiedProvince_Peace();
         }
      }
   }

   public final void addCasualties(int iCivID, int iCasualties) {
      this.lastFight_TurnID = Game_Calendar.TURN_ID;

      for (int i = 0; i < this.lDefenders.size(); i++) {
         if (this.lDefenders.get(i).iCivID == iCivID) {
            this.lDefenders.get(i).iCasualties += iCasualties;
            return;
         }
      }

      for (int ix = 0; ix < this.lAggressors.size(); ix++) {
         if (this.lAggressors.get(ix).iCivID == iCivID) {
            this.lAggressors.get(ix).iCasualties += iCasualties;
            return;
         }
      }
   }

   public int getWarScore_Side(int iCivID) {
      return this.isAggressor(iCivID) ? -1 : 1;
   }

   public final int getCasualties_Aggressors() {
      int out = 0;

      for (int i = 0; i < this.lAggressors.size(); i++) {
         out += this.lAggressors.get(i).iCasualties;
      }

      return out;
   }

   public final int getCasualties_Defenders() {
      int out = 0;

      for (int i = 0; i < this.lDefenders.size(); i++) {
         out += this.lDefenders.get(i).iCasualties;
      }

      return out;
   }

   public boolean isWarLeader(int civID) {
      return this.lAggressors.get(0).iCivID == civID || this.lDefenders.get(0).iCivID == civID;
   }

   public final void updateCapitalProvinceID() {
      try {
         for (int i = this.lDefenders.size() - 1; i >= 0; i--) {
            if (Game.getCiv(this.lDefenders.get(i).iCivID).getCapitalProvinceID() < 0) {
               Game.getCiv(this.lDefenders.get(i).iCivID).moveCapital_ToLargestProvince();
            } else if (Game.getProvince(Game.getCiv(this.lDefenders.get(i).iCivID).getCapitalProvinceID()).getCivID() != this.lDefenders.get(i).iCivID) {
               Game.getCiv(this.lDefenders.get(i).iCivID).moveCapital_ToLargestProvince();
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      try {
         for (int ix = this.lAggressors.size() - 1; ix >= 0; ix--) {
            if (Game.getCiv(this.lAggressors.get(ix).iCivID).getCapitalProvinceID() < 0) {
               Game.getCiv(this.lAggressors.get(ix).iCivID).moveCapital_ToLargestProvince();
            } else if (Game.getProvince(Game.getCiv(this.lAggressors.get(ix).iCivID).getCapitalProvinceID()).getCivID() != this.lAggressors.get(ix).iCivID) {
               Game.getCiv(this.lAggressors.get(ix).iCivID).moveCapital_ToLargestProvince();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }
}
