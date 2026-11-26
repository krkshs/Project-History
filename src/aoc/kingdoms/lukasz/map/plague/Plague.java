package aoc.kingdoms.lukasz.map.plague;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class Plague {
   public String sName;
   public int iPlagueID_InGame = 0;
   public List<Integer> lProvinces = new ArrayList<>();
   public int iProvincesSize = 0;
   public List<Integer> lProvinces_Active = new ArrayList<>();
   public float fDeathRate = 0.0F;
   public int iDurationTurnsLeft = 0;
   public float fDevastation = 0.0F;
   public int iDeaths = 0;
   public int iImageID = 0;
   public int iDurationTurnsLeft_BEGINNING = 0;
   public float fR;
   public float fG;
   public float fB;
   public float EXPANSION_MODIFIER;
   public float EXPANSION_SCORE;
   public boolean nS = false;

   public Plague() {
   }

   public Plague(
      int outbreakProvince,
      String sName,
      float fR,
      float fG,
      float fB,
      int nPlagueID_InGame,
      float fDeathRate,
      int iDurationTurnsLeft,
      float EXPANSION_MODIFIER,
      int iImageID,
      float fDevastation
   ) {
      this.sName = Game.lang.get(sName);
      this.iPlagueID_InGame = nPlagueID_InGame;
      this.fR = fR;
      this.fG = fG;
      this.fB = fB;
      this.iImageID = iImageID;
      this.fDevastation = fDevastation;
      this.fDeathRate = fDeathRate;
      this.iDurationTurnsLeft = iDurationTurnsLeft;
      this.iDurationTurnsLeft_BEGINNING = iDurationTurnsLeft;
      this.EXPANSION_MODIFIER = EXPANSION_MODIFIER;
      this.addProvince(outbreakProvince);
   }

   protected final void runDisease() {
      for (int i = this.lProvinces_Active.size() - 1; i >= 0; i--) {
         if (Game.getProvince(this.lProvinces_Active.get(i)).provincePlague != null
            && Game.getProvince(this.lProvinces_Active.get(i)).provincePlague.id == this.getPlagueID_InGame()) {
            int nPopBefore = Game.getProvince(this.lProvinces_Active.get(i)).getPopulationTotal();
            int nDeaths = (int)Math.ceil(
               nPopBefore
                  * (this.fDeathRate * (0.225F + 0.325F * this.getDurationPercLEFT() + 0.55F * Game.oR.nextInt(100) / 100.0F))
                  * (
                     1.0F
                        + Math.max(
                           GameValues.plagues.DISEASE_MAX_DEATH_RATE_REDUCTION,
                           Game.getProvince(this.lProvinces_Active.get(i)).provBonuses.DiseaseDeathRate
                              + Game.getCiv(Game.getProvince(this.lProvinces_Active.get(i)).getCivID()).civBonuses.DiseaseDeathRate
                              + Game.getProvince(this.lProvinces_Active.get(i)).getInfrastructure()
                                 * GameValues.infrastructure.INFRASTRUCTURE_DISEASE_DEATH_RATE_PER_LVL
                        )
                  )
            );
            if (nDeaths > 0) {
               for (int k = Game.getProvince(this.lProvinces_Active.get(i)).getPopulationSize() - 1; k >= 0; k--) {
                  Game.getProvince(this.lProvinces_Active.get(i))
                     .setPopulationOfCivID(
                        Game.getProvince(this.lProvinces_Active.get(i)).getPopulationCivID(k),
                        (int)(
                           Game.getProvince(this.lProvinces_Active.get(i)).getPopulationID(k)
                              - Math.floor(nDeaths * ((float)Game.getProvince(this.lProvinces_Active.get(i)).getPopulationID(k) / nPopBefore))
                        )
                     );
               }

               nPopBefore -= Game.getProvince(this.lProvinces_Active.get(i)).getPopulationTotal();
               Game.getProvince(this.lProvinces_Active.get(i)).provincePlague.deaths += nPopBefore;
               Game.getProvinceData10(this.lProvinces_Active.get(i)).d += nPopBefore;
               this.iDeaths += nPopBefore;
               Game.getProvince(this.lProvinces_Active.get(i))
                  .setDevastation(Game.getProvince(this.lProvinces_Active.get(i)).getDevastation() + this.fDevastation * (0.75F + Game.oR.nextInt(75) / 100.0F));
            }

            ProvincePlague var7 = Game.getProvince(this.lProvinces_Active.get(i)).provincePlague;
            var7.turnsLeft = var7.turnsLeft
               - (
                  0.875F
                     - 0.065F * (Game.getProvince(this.lProvinces_Active.get(i)).getGrowthRateWithBonuses() / 100.0F)
                     + Game.oR.nextInt(GameValues.plagues.DISEASE_DAYS_LEFT_RANDOM) / 1000.0F
               );
            if (Game.getProvince(this.lProvinces_Active.get(i)).provincePlague.turnsLeft <= 0.0F) {
               Game.getProvinceData10(this.lProvinces_Active.get(i)).t = Game_Calendar.TURN_ID;
               Game.getProvince(this.lProvinces_Active.get(i)).provincePlague = null;
               this.lProvinces_Active.remove(i);
            }
         }
      }

      this.fDeathRate = this.fDeathRate
         * (GameValues.plagues.DISEASE_DEATH_RATE_CHANGE_PER_DAY - Game.oR.nextInt(GameValues.plagues.DISEASE_DEATH_RATE_CHANGE_PER_DAY_RANDOM) / 10000.0F);
   }

   protected final void spreadDisease() {
      if (this.iDurationTurnsLeft > 0 && this.lProvinces_Active.size() > 0) {
         if ((float)this.lProvinces.size() / Game.getProvincesSize() > 0.35F) {
            return;
         }

         this.EXPANSION_SCORE = this.EXPANSION_SCORE
            + this.lProvinces_Active.size() * 0.425F * this.EXPANSION_MODIFIER * (0.1F + 0.9F * this.getDurationPercLEFT());
         this.EXPANSION_MODIFIER = this.EXPANSION_MODIFIER * (0.925F - Game.oR.nextInt(17850) / 100000.0F);
         if (this.EXPANSION_SCORE >= 1.0F) {
            int nRand = Game.oR.nextInt((int)this.EXPANSION_SCORE);
            if (nRand > 0) {
               this.EXPANSION_SCORE -= nRand;
               this.spreadDisease(nRand);
            }
         }
      }
   }

   protected final void spreadDisease(int nNumOfProvinces) {
      try {
         nNumOfProvinces = (int)Math.min((float)nNumOfProvinces, Math.max(Game.getProvincesSize() * 0.01425F, 16.0F));
         List<Integer> tPossibleSpreadProvinces = new ArrayList<>();
         List<Integer> tPossibleSpreadProvinces_Scores = new ArrayList<>();

         for (int i = 0; i < this.lProvinces_Active.size(); i++) {
            if (Game.getProvince(this.lProvinces_Active.get(i)).getSeaProvince()) {
               for (int k = 0; k < Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvincesSize(); k++) {
                  if (Game.getProvince(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(k)).provincePlague == null
                     && Game_Calendar.TURN_ID - Game.getProvinceData10(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(k)).t
                        > GameValues.plagues.PLAGUE_PAUSE_FOR_X_DAYS) {
                     tPossibleSpreadProvinces.add(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(k));
                  }
               }
            } else {
               for (int kx = 0; kx < Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvincesSize(); kx++) {
                  if (Game.getProvince(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(kx)).getWasteland() < 0
                     && Game.getProvince(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(kx)).provincePlague == null
                     && Game_Calendar.TURN_ID - Game.getProvinceData10(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(kx)).t
                        > GameValues.plagues.PLAGUE_PAUSE_FOR_X_DAYS) {
                     tPossibleSpreadProvinces.add(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvinces(kx));
                  }
               }

               if (Game.getProvince(this.lProvinces_Active.get(i)).getLevelOfPort() > 0
                  || Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringProvincesSize() < 2) {
                  for (int kxx = 0; kxx < Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringSeaProvincesSize(); kxx++) {
                     if (Game.getProvince(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringSeaProvinces(kxx)).getWasteland() < 0
                        && Game.getProvince(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringSeaProvinces(kxx)).provincePlague == null
                        && Game_Calendar.TURN_ID - Game.getProvinceData10(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringSeaProvinces(kxx)).t
                           > GameValues.plagues.PLAGUE_PAUSE_FOR_X_DAYS) {
                        tPossibleSpreadProvinces.add(Game.getProvince(this.lProvinces_Active.get(i)).getNeighboringSeaProvinces(kxx));
                     }
                  }
               }
            }
         }

         if (tPossibleSpreadProvinces.size() > 0) {
            int tTotalScore = 0;

            for (int ix = tPossibleSpreadProvinces.size() - 1; ix >= 0; ix--) {
               int tempScore = this.getSpreadScore(tPossibleSpreadProvinces.get(ix)) * 3 + 1;
               tPossibleSpreadProvinces_Scores.add(tempScore);
               tTotalScore += tempScore;
            }

            if (tTotalScore > 0) {
               while (tPossibleSpreadProvinces_Scores.size() > 0 && nNumOfProvinces > 0) {
                  int tRandScore = Game.oR.nextInt(tTotalScore);
                  int ix = 0;

                  for (int tCurrentScore = 0; ix < tPossibleSpreadProvinces_Scores.size(); ix++) {
                     tCurrentScore += tPossibleSpreadProvinces_Scores.get(ix);
                     if (tCurrentScore > tRandScore) {
                        this.addProvince(tPossibleSpreadProvinces.get(ix));
                        tTotalScore -= tPossibleSpreadProvinces_Scores.get(ix);
                        tPossibleSpreadProvinces_Scores.remove(ix);
                        tPossibleSpreadProvinces.remove(ix);
                        nNumOfProvinces--;
                        break;
                     }
                  }
               }

               if (nNumOfProvinces > 0) {
                  this.spreadDisease(nNumOfProvinces);
               }
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   protected final int getSpreadScore(int nProvinceID) {
      int tempScore = 0;

      for (int k = 0; k < Game.getProvince(nProvinceID).getNeighboringProvincesSize(); k++) {
         if (Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(k)).provincePlague == null) {
            tempScore += Game.getProvince(Game.getProvince(nProvinceID).getNeighboringProvinces(k)).getSeaProvince() ? 1 : 2;
         }
      }

      for (int kx = 0; kx < Game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); kx++) {
         if (Game.getProvince(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(kx)).provincePlague == null) {
            tempScore += Game.getProvince(Game.getProvince(nProvinceID).getNeighboringSeaProvinces(kx)).getSeaProvince() ? 1 : 2;
         }
      }

      return tempScore;
   }

   protected final void addProvince(int nProvinceID) {
      for (int i = 0; i < this.iProvincesSize; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            return;
         }
      }

      Game.getProvinceData10(nProvinceID).t = Game_Calendar.TURN_ID;
      if (Game.getProvince(nProvinceID).provincePlague == null) {
         Game.getProvince(nProvinceID).provincePlague = new ProvincePlague(
            this.iPlagueID_InGame, Game_Calendar.TURN_ID, this.iDurationTurnsLeft * (0.625F + Game.oR.nextInt(6000) / 10000.0F), 0
         );
         if (!this.nS && Game.getProvince(nProvinceID).getCivID() == Game.player.iCivID && Game.player.iCivID > 0) {
            this.nS = true;
            if (Game.oR.nextInt(100) < GameValues.plagues.SEND_NOTIFICATION_CHANCE) {
               Game.player
                  .addNotification(
                     new Notification(
                        Notification.Notification_Type.DISEASE,
                        this.sName + ": " + Game.lang.get("Disease"),
                        Images.disease,
                        Game_Calendar.TURN_ID,
                        Notification.Notification_BG.RED,
                        this.sName,
                        nProvinceID
                     )
                  );
            }
         }

         Game.getProvinceData10(nProvinceID).n++;
         this.lProvinces.add(nProvinceID);
         this.lProvinces_Active.add(nProvinceID);
         this.iProvincesSize = this.lProvinces.size();
      }
   }

   protected final String getPlagueName() {
      try {
         return this.sName;
      } catch (Exception var2) {
         return Game.lang.get("Plague");
      }
   }

   protected final void setPlagueID_InGame(int iPlagueID_InGame) {
      this.iPlagueID_InGame = iPlagueID_InGame;
   }

   protected final int getPlagueID_InGame() {
      return this.iPlagueID_InGame;
   }

   protected final float getDurationPercLEFT() {
      return (float)this.iDurationTurnsLeft / this.iDurationTurnsLeft_BEGINNING;
   }

   protected final float getDurationPercLEFT(int nNumOfTurns) {
      return (float)nNumOfTurns / this.iDurationTurnsLeft_BEGINNING;
   }

   protected final int getOutbreakProvinceID() {
      try {
         return this.lProvinces.get(0);
      } catch (IndexOutOfBoundsException var2) {
         return -1;
      }
   }

   protected final int getDeaths() {
      return this.iDeaths;
   }

   protected final int getNumOfProvinces_Total() {
      return this.lProvinces.size();
   }

   protected final int getNumOfProvinces_Active() {
      return this.lProvinces_Active.size();
   }
}
