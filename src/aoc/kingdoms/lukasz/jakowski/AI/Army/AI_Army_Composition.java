package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;

public class AI_Army_Composition {
   public int numFirstLine = 0;
   public int numFlank = 0;
   public int numSupport = 0;
   public int numSiege = 0;
   public int numOfRegiments = 0;

   public AI_Army_Composition() {
   }

   public AI_Army_Composition(AI_Army_Composition armyComposition) {
      this.numFirstLine = armyComposition.numFirstLine;
      this.numFlank = armyComposition.numFlank;
      this.numSupport = armyComposition.numSupport;
      this.numSiege = armyComposition.numSiege;
      this.updateNumOfRegiments();
   }

   public AI_Army_Composition(int civID, int numOfRegiments) {
      int numNotUsed = 0;
      Civilization civ = Game.getCiv(civID);
      int maxBattleWidth = BattleManager.getBattleWidth(civID);
      this.numSupport = Math.min(maxBattleWidth, numOfRegiments / 2);
      numOfRegiments -= this.numSupport;
      this.numFirstLine = (int)Math.ceil(numOfRegiments * civ.aiCiv.armies_FirstLine_Perc);
      this.numFlank = (int)(numOfRegiments * civ.aiCiv.armies_FlankLine_Perc);
      if (civ.unitsBest_FirstLineSize <= 0) {
         numNotUsed += this.numFirstLine;
         this.numFirstLine = 0;
      }

      if (civ.unitsBest_FlankSize <= 0) {
         numNotUsed += this.numFlank;
         this.numFlank = 0;
      }

      if (civ.unitsBest_SupportSize <= 0) {
         numNotUsed += this.numSupport;
         this.numSupport = 0;
      }

      if (numNotUsed > 0) {
         if (civ.unitsBest_SupportSize > 0) {
            if (civ.unitsBest_FirstLineSize > 0) {
               this.numFirstLine = (int)(this.numFirstLine + Math.ceil(numNotUsed / 2.0F));
               this.numSupport = (int)(this.numSupport + Math.floor(numNotUsed / 2.0F));
            } else if (civ.unitsBest_FlankSize > 0) {
               this.numFlank = (int)(this.numFlank + Math.ceil(numNotUsed / 2.0F));
               this.numSupport = (int)(this.numSupport + Math.floor(numNotUsed / 2.0F));
            }
         } else if (civ.unitsBest_FirstLineSize > 0) {
            this.numFirstLine += numNotUsed;
         } else if (civ.unitsBest_FlankSize > 0) {
            this.numFlank += numNotUsed;
         }
      }

      if (civ.unitsBest_SiegeSize > 0 && this.numSupport > civ.aiCiv.armies_SiegeWeaponPerSupportUnits) {
         this.numSiege = Math.min(this.numSupport / civ.aiCiv.armies_SiegeWeaponPerSupportUnits, civ.aiCiv.armies_SiegeWeapon_Max);
         this.numSupport = this.numSupport - this.numSiege;
      }

      this.updateNumOfRegiments();
   }

   public void updateNumOfRegiments() {
      this.numOfRegiments = this.numFirstLine + this.numFlank + this.numSupport + this.numSiege;
   }

   public void LOG(String text) {
      CFG.LOG(text + ": " + this.numOfRegiments);
      CFG.LOG("numFirstLine: " + this.numFirstLine);
      CFG.LOG("numFlank: " + this.numFlank);
      CFG.LOG("numSupport: " + this.numSupport);
      CFG.LOG("numSiege: " + this.numSiege);
   }
}
