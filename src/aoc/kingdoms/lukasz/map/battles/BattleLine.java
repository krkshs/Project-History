package aoc.kingdoms.lukasz.map.battles;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import java.util.ArrayList;
import java.util.List;

public class BattleLine {
   public String text;
   public int textW;
   public int armyExtraPosX;
   public int numOfUnits = 0;
   public int numOfUnitsOnBattlefield = 0;
   public int iCasualties = 0;
   public int iRetreated = 0;
   public int inReserve = 0;
   public float fMorale = 0.0F;
   public int diceRollGeneral = 0;
   public int iLastAttackRoundID = 0;
   public int iCivID = 0;
   public ArmyGeneral armyGeneral = null;
   public List<BattleRegiment> firstLine = new ArrayList<>();
   public List<BattleRegiment> secondLine = new ArrayList<>();
   public List<BattleRegiment> reserveFirstLine = new ArrayList<>();
   public List<BattleRegiment> reserveSecondLine = new ArrayList<>();
   public List<BattleRegiment> defeated = new ArrayList<>();
   public List<Integer> lCivs = new ArrayList<>();
   public List<Integer> lCasualties = new ArrayList<>();
   public List<String> lDefeatedArmyDivisionKeys = new ArrayList<>();
   public List<String> armyDivisionsKeys = new ArrayList<>();

   public void updateLoaded_Load() {
      try {
         for (int i = this.firstLine.size() - 1; i >= 0; i--) {
            if (this.firstLine.get(i) != null) {
               this.firstLine.get(i).l = 1;
            }
         }

         for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
            if (this.secondLine.get(ix) != null) {
               this.secondLine.get(ix).l = 1;
            }
         }

         for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
            if (this.reserveFirstLine.get(ixx) != null) {
               this.reserveFirstLine.get(ixx).l = 1;
            }
         }

         for (int ixxx = this.reserveSecondLine.size() - 1; ixxx >= 0; ixxx--) {
            if (this.reserveSecondLine.get(ixxx) != null) {
               this.reserveSecondLine.get(ixxx).l = 1;
            }
         }

         for (int ixxxx = this.defeated.size() - 1; ixxxx >= 0; ixxxx--) {
            if (this.defeated.get(ixxxx) != null) {
               this.defeated.get(ixxxx).l = 1;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public void updateInBattle_Load(int provinceID) {
      List<String> armyKeys = this.getBattleArmiesKeys();

      for (int i = armyKeys.size() - 1; i >= 0; i--) {
         int armyID = Game.getProvince(provinceID).getArmyKeyID(armyKeys.get(i));
         if (armyID >= 0) {
            Game.getProvince(provinceID).getArmy(armyID).inBattle = true;
         }
      }
   }

   public List<String> getBattleArmiesKeys() {
      List<String> armyKeys = new ArrayList<>();

      for (int i = this.firstLine.size() - 1; i >= 0; i--) {
         if (this.firstLine.get(i) != null && !armyKeys.contains(this.firstLine.get(i).aK)) {
            armyKeys.add(this.firstLine.get(i).aK);
         }
      }

      for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
         if (this.secondLine.get(ix) != null && !armyKeys.contains(this.secondLine.get(ix).aK)) {
            armyKeys.add(this.secondLine.get(ix).aK);
         }
      }

      for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
         if (!armyKeys.contains(this.reserveFirstLine.get(ixx).aK)) {
            armyKeys.add(this.reserveFirstLine.get(ixx).aK);
         }
      }

      for (int ixxx = this.reserveSecondLine.size() - 1; ixxx >= 0; ixxx--) {
         if (!armyKeys.contains(this.reserveSecondLine.get(ixxx).aK)) {
            armyKeys.add(this.reserveSecondLine.get(ixxx).aK);
         }
      }

      for (int ixxxx = this.defeated.size() - 1; ixxxx >= 0; ixxxx--) {
         if (!armyKeys.contains(this.defeated.get(ixxxx).aK)) {
            armyKeys.add(this.defeated.get(ixxxx).aK);
         }
      }

      return armyKeys;
   }

   public BattleLine() {
   }

   public BattleLine(List<ArmyDivision> lineArmy, int maxWidth, int sideArmyBegin, String battleKey) {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         this.firstLine.add(null);
         this.secondLine.add(null);
      }

      this.numOfUnits = 0;
      this.numOfUnitsOnBattlefield = 0;
      List<BattleRegiment> tempLine0 = new ArrayList<>();
      List<BattleRegiment> tempLine1 = new ArrayList<>();
      List<BattleRegiment> tempLine2 = new ArrayList<>();
      int j = 0;

      for (int jSize = lineArmy.size(); j < jSize; j++) {
         this.addCiv(lineArmy.get(j).civID, battleKey);

         for (int i = 0; i < lineArmy.get(j).iArmyRegimentSize; i++) {
            if (lineArmy.get(j).lArmyRegiment.get(i).num > 0) {
               this.numOfUnits = this.numOfUnits + lineArmy.get(j).lArmyRegiment.get(i).num;
               switch (ArmyManager.lUnitsTypes.get(lineArmy.get(j).lArmyRegiment.get(i).uID).Line) {
                  case 0:
                     tempLine0.add(new BattleRegiment(lineArmy.get(j).civID, lineArmy.get(j).lArmyRegiment.get(i), lineArmy.get(j).key));
                     break;
                  case 1:
                     tempLine1.add(new BattleRegiment(lineArmy.get(j).civID, lineArmy.get(j).lArmyRegiment.get(i), lineArmy.get(j).key));
                     break;
                  default:
                     tempLine2.add(new BattleRegiment(lineArmy.get(j).civID, lineArmy.get(j).lArmyRegiment.get(i), lineArmy.get(j).key));
               }
            } else {
               this.defeated.add(new BattleRegiment(lineArmy.get(j).civID, lineArmy.get(j).lArmyRegiment.get(i), lineArmy.get(j).key));
            }
         }
      }

      j = 0;
      int sideArmyWidth = Math.min(maxWidth - (int)(maxWidth * GameValues.battle.BATTLE_SIDES_RATIO), sideArmyBegin);

      while (j < maxWidth && (tempLine0.size() > 0 || tempLine1.size() > 0)) {
         boolean sideArmy = j >= sideArmyWidth;
         if (!sideArmy && tempLine0.size() > 0) {
            this.firstLine.set(BattleManager.FILL_ORDER[j++], tempLine0.get(0));
            tempLine0.remove(0);
         } else if (tempLine1.size() > 0) {
            this.firstLine.set(BattleManager.FILL_ORDER[j++], tempLine1.get(0));
            tempLine1.remove(0);
         } else if (tempLine0.size() > 0) {
            this.firstLine.set(BattleManager.FILL_ORDER[j++], tempLine0.get(0));
            tempLine0.remove(0);
         }
      }

      int secondAdded = 0;

      while (secondAdded < maxWidth && tempLine2.size() > 0) {
         if (tempLine2.size() > 0) {
            if (this.firstLine.get(BattleManager.FILL_ORDER[secondAdded]) == null) {
               this.firstLine.set(BattleManager.FILL_ORDER[secondAdded], tempLine2.get(0));
            } else {
               this.secondLine.set(BattleManager.FILL_ORDER[secondAdded++], tempLine2.get(0));
            }

            tempLine2.remove(0);
         }
      }

      while (tempLine0.size() > 0) {
         this.reserveFirstLine.add(tempLine0.get(0));
         tempLine0.remove(0);
      }

      while (tempLine1.size() > 0) {
         this.reserveFirstLine.add(tempLine1.get(0));
         tempLine1.remove(0);
      }

      while (tempLine2.size() > 0) {
         this.reserveSecondLine.add(tempLine2.get(0));
         tempLine2.remove(0);
      }

      int tRegID = 1;

      for (int ix = 0; ix < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ix++) {
         if (this.firstLine.get(BattleManager.FILL_ORDER[ix]) != null) {
            this.firstLine.get(BattleManager.FILL_ORDER[ix]).rn = tRegID++;
            this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.firstLine.get(BattleManager.FILL_ORDER[ix]).aR.num;
         }
      }

      for (int ixx = 0; ixx < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ixx++) {
         if (this.secondLine.get(BattleManager.FILL_ORDER[ixx]) != null) {
            this.secondLine.get(BattleManager.FILL_ORDER[ixx]).rn = tRegID++;
            this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.secondLine.get(BattleManager.FILL_ORDER[ixx]).aR.num;
         }
      }

      this.inReserve = 0;
      int ixxx = 0;

      for (int nReserveSize = this.reserveFirstLine.size(); ixxx < nReserveSize; ixxx++) {
         this.reserveFirstLine.get(ixxx).rn = tRegID++;
         this.inReserve = this.inReserve + this.reserveFirstLine.get(ixxx).aR.num;
      }

      ixxx = 0;

      for (int nReserveSize = this.reserveSecondLine.size(); ixxx < nReserveSize; ixxx++) {
         this.reserveSecondLine.get(ixxx).rn = tRegID++;
         this.inReserve = this.inReserve + this.reserveSecondLine.get(ixxx).aR.num;
      }

      this.setText("" + CFG.getPrecision2(this.numOfUnits / 1000.0F, 10));
      this.updateAverageMoraleOnBattleField();
   }

   public final void joinBattle(ArmyDivision armyDivision, int iMaxBattleWidth, String battleKey) {
      List<BattleRegiment> tempLine0 = new ArrayList<>();
      List<BattleRegiment> tempLine2 = new ArrayList<>();
      this.addCiv(armyDivision.civID, battleKey);
      armyDivision.inBattle = true;
      if (armyDivision.inMovement) {
         if (armyDivision.civID < 0) {
            Game.revolutionMoveUnits.updateMoveInBattle(armyDivision.key, armyDivision.inBattle);
         } else {
            Game.getCiv(armyDivision.civID).updateMoveInBattle(armyDivision.key, armyDivision.inBattle);
         }
      }

      if (this.armyGeneral == null && armyDivision.armyGeneral != null) {
         this.armyGeneral = armyDivision.armyGeneral;
      }

      if (armyDivision.armyGeneral != null) {
         armyDivision.armyGeneral.addCombatExperience(GameValues.generals.COMBAT_EXPERIENCE_JOIN_BATTLE);
      }

      for (int i = 0; i < armyDivision.iArmyRegimentSize; i++) {
         this.numOfUnits = this.numOfUnits + armyDivision.lArmyRegiment.get(i).num;
         switch (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(i).uID).Line) {
            case 0:
            case 1:
               tempLine0.add(new BattleRegiment(armyDivision.civID, armyDivision.lArmyRegiment.get(i), armyDivision.key));
               break;
            default:
               tempLine2.add(new BattleRegiment(armyDivision.civID, armyDivision.lArmyRegiment.get(i), armyDivision.key));
         }
      }

      int tRegID = 1;

      while (tempLine0.size() > 0) {
         tempLine0.get(0).rn = tRegID++;
         this.inReserve = this.inReserve + tempLine0.get(0).aR.num;
         this.reserveFirstLine.add(tempLine0.get(0));
         tempLine0.remove(0);
      }

      while (tempLine2.size() > 0) {
         tempLine2.get(0).rn = tRegID++;
         this.inReserve = this.inReserve + tempLine2.get(0).aR.num;
         this.reserveSecondLine.add(tempLine2.get(0));
         tempLine2.remove(0);
      }

      try {
         for (int i = 0; i < iMaxBattleWidth && this.reserveFirstLine.size() > 0; i++) {
            if (this.firstLine.get(BattleManager.FILL_ORDER[i]) == null) {
               this.firstLine.set(BattleManager.FILL_ORDER[i], this.reserveFirstLine.get(0));
               this.inReserve = this.inReserve - this.reserveFirstLine.get(0).aR.num;
               this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.reserveFirstLine.get(0).aR.num;
               this.reserveFirstLine.remove(0);
            }
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      try {
         for (int ix = 0; ix < iMaxBattleWidth && this.reserveSecondLine.size() > 0; ix++) {
            if (this.secondLine.get(BattleManager.FILL_ORDER[ix]) == null) {
               this.secondLine.set(BattleManager.FILL_ORDER[ix], this.reserveSecondLine.get(0));
               this.inReserve = this.inReserve - this.reserveSecondLine.get(0).aR.num;
               this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.reserveSecondLine.get(0).aR.num;
               this.reserveSecondLine.remove(0);
            }
         }

         for (int ixx = 0; ixx < iMaxBattleWidth && this.reserveSecondLine.size() > 0; ixx++) {
            if (this.firstLine.get(BattleManager.FILL_ORDER[ixx]) == null) {
               this.firstLine.set(BattleManager.FILL_ORDER[ixx], this.reserveSecondLine.get(0));
               this.inReserve = this.inReserve - this.reserveSecondLine.get(0).aR.num;
               this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.reserveSecondLine.get(0).aR.num;
               this.reserveSecondLine.remove(0);
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      this.setText("" + CFG.getPrecision2(this.numOfUnits / 1000.0F, 10));
   }

   public void setText(String sText) {
      this.text = sText;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontArmy_GlyphLayout, sText);
      int tX = (int)glyphLayout.width;
      glyphLayout.setText(Renderer.fontArmy_GlyphLayout, sText.length() > 2 ? sText : "999");
      this.textW = (int)glyphLayout.width;
      this.armyExtraPosX = Math.max(0, this.textW / 2 - tX / 2);
   }

   public void addCiv(int iCivID, String battleKey) {
      int i = 0;

      for (int iSize = this.lCivs.size(); i < iSize; i++) {
         if (this.lCivs.get(i) == iCivID) {
            return;
         }
      }

      this.lCivs.add(iCivID);
      Game.getCiv(iCivID).addInBattles(battleKey);
   }

   public void updateAllArmiesNotInBattle(int provinceID) {
      List<String> armyKeys = this.getBattleArmiesKeys();

      for (int i = armyKeys.size() - 1; i >= 0; i--) {
         try {
            ArmyDivision armyDivision = Game.getProvince(provinceID).getArmy(armyKeys.get(i));
            if (armyDivision != null) {
               armyDivision.inBattle = false;
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }
      }
   }

   public void removeCivsInBattle(String battleKey) {
      int i = 0;

      for (int iSize = this.lCivs.size(); i < iSize; i++) {
         Game.getCiv(this.lCivs.get(i)).removeInBattles(battleKey);
      }
   }

   public final void updateNumOfUnits() {
      this.numOfUnits = 0;

      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            this.numOfUnits = this.numOfUnits + this.firstLine.get(i).aR.num;
         }

         if (this.secondLine.get(i) != null) {
            this.numOfUnits = this.numOfUnits + this.secondLine.get(i).aR.num;
         }
      }

      for (int i = this.reserveFirstLine.size() - 1; i >= 0; i--) {
         this.numOfUnits = this.numOfUnits + this.reserveFirstLine.get(i).aR.num;
      }

      for (int i = this.reserveSecondLine.size() - 1; i >= 0; i--) {
         this.numOfUnits = this.numOfUnits + this.reserveSecondLine.get(i).aR.num;
      }
   }

   public final void updateBattle_Summary(int iProvinceID, boolean haveToRetreat, int iRoundID, boolean armyCanBeDestroyed) {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            this.firstLine.get(i).aR.num = this.firstLine.get(i).aR.num + this.firstLine.get(i).re;
            if (this.firstLine.get(i).l > 0) {
               Game.getProvince(iProvinceID)
                  .updateArmy_AfterBattle(this.firstLine.get(i).aK, this.firstLine.get(i).aR.key, this.firstLine.get(i).aR.num, this.firstLine.get(i).aR.mo);
            }
         }

         if (this.secondLine.get(i) != null) {
            this.secondLine.get(i).aR.num = this.secondLine.get(i).aR.num + this.secondLine.get(i).re;
            if (this.secondLine.get(i).l > 0) {
               Game.getProvince(iProvinceID)
                  .updateArmy_AfterBattle(this.secondLine.get(i).aK, this.secondLine.get(i).aR.key, this.secondLine.get(i).aR.num, this.secondLine.get(i).aR.mo);
            }
         }
      }

      int i = 0;

      for (int iSize = this.defeated.size(); i < iSize; i++) {
         this.defeated.get(i).aR.num = this.defeated.get(i).re;
         if (this.defeated.get(i).l > 0) {
            Game.getProvince(iProvinceID)
               .updateArmy_AfterBattle(this.defeated.get(i).aK, this.defeated.get(i).aR.key, this.defeated.get(i).aR.num, this.defeated.get(i).aR.mo);
         }
      }

      try {
         this.armyDivisionsKeys.clear();

         try {
            i = 0;

            for (int iSizex = this.firstLine.size(); i < iSizex; i++) {
               if (this.firstLine.get(i) != null) {
                  this.addArmyDivisionKey(this.firstLine.get(i).aK);
               }
            }
         } catch (Exception var10) {
            CFG.exceptionStack(var10);
         }

         try {
            i = 0;

            for (int iSizexx = this.secondLine.size(); i < iSizexx; i++) {
               if (this.secondLine.get(i) != null) {
                  this.addArmyDivisionKey(this.secondLine.get(i).aK);
               }
            }
         } catch (Exception var9) {
            CFG.exceptionStack(var9);
         }

         i = 0;

         for (int iSizexxx = this.reserveFirstLine.size(); i < iSizexxx; i++) {
            this.addArmyDivisionKey(this.reserveFirstLine.get(i).aK);
         }

         i = 0;

         for (int iSizexxx = this.reserveSecondLine.size(); i < iSizexxx; i++) {
            this.addArmyDivisionKey(this.reserveSecondLine.get(i).aK);
         }

         i = 0;

         for (int iSizexxx = this.defeated.size(); i < iSizexxx; i++) {
            this.addArmyDivisionKey(this.defeated.get(i).aK);
         }

         for (int ix = this.armyDivisionsKeys.size() - 1; ix >= 0; ix--) {
            Game.getProvince(iProvinceID).updateArmy_BattleSummary(this.armyDivisionsKeys.get(ix));
         }

         this.armyDivisionsKeys.clear();
      } catch (Exception var11) {
         CFG.exceptionStack(var11);
      }

      if (!haveToRetreat && this.armyGeneral != null) {
         this.armyGeneral
            .addCombatExperience(
               Math.min(
                  GameValues.generals.COMBAT_EXPERIENCE_BATTLE_WON_LIMIT,
                  Math.min(
                     GameValues.generals.COMBAT_EXPERIENCE_BATTLE_WON_LIMIT_PER_DAY_OF_BATTLE * (iRoundID + 1),
                     GameValues.generals.COMBAT_EXPERIENCE_BATTLE_WON_EXPERIENCE_PER_DAY_OF_BATTLE * (iRoundID + 1)
                        + GameValues.generals.COMBAT_EXPERIENCE_BATTLE_WON_MIN
                        + Game.oR.nextInt(GameValues.generals.COMBAT_EXPERIENCE_BATTLE_WON_MIN_RANDOM)
                  )
               )
            );
      }

      if (haveToRetreat) {
         i = 0;
         this.buildArmyDivisionsKeys();
         if (armyCanBeDestroyed && iRoundID < GameValues.battle.BATTLE_ARMY_DESTROYED_ROUND_ID) {
            for (int ix = 0; ix < this.lDefeatedArmyDivisionKeys.size(); ix++) {
               Game.getProvince(iProvinceID).armyDestroyed(this.lDefeatedArmyDivisionKeys.get(ix));
            }
         } else {
            for (int ix = 0; ix < this.lDefeatedArmyDivisionKeys.size(); ix++) {
               ArmyDivision armyRetreat = Game.getProvince(iProvinceID).getArmy(this.lDefeatedArmyDivisionKeys.get(ix));
               if (armyRetreat != null
                  && armyRetreat.iArmy / Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * armyRetreat.iArmyRegimentSize
                     < GameValues.battle.BATTLE_ARMY_DESTROYED_IF_MANPOWER_PERC_BELOW) {
                  Game.getProvince(iProvinceID).armyDestroyed(this.lDefeatedArmyDivisionKeys.get(ix));
               } else {
                  int result = Game.getProvince(iProvinceID).armyRetreat(this.lDefeatedArmyDivisionKeys.get(ix), i);
                  if (result < 0) {
                     Game.getProvince(iProvinceID).armyDestroyed(this.lDefeatedArmyDivisionKeys.get(ix));
                  } else {
                     i = result;
                  }
               }
            }
         }
      }
   }

   public final void addArmyDivisionKey(String key) {
      for (int i = this.armyDivisionsKeys.size() - 1; i >= 0; i--) {
         if (this.armyDivisionsKeys.get(i).equals(key)) {
            return;
         }
      }

      this.armyDivisionsKeys.add(key);
   }

   public final void updateDiceRoll() {
      this.diceRollGeneral = 1 + Game.oR.nextInt(GameValues.battle.BATTLE_MAX_DICE_ROLL);

      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            this.firstLine.get(i).d = Game.oR.nextInt(GameValues.battle.BATTLE_MAX_DICE_ROLL_REGIMENT);
         }

         if (this.secondLine.get(i) != null) {
            this.secondLine.get(i).d = Game.oR.nextInt(GameValues.battle.BATTLE_MAX_DICE_ROLL_REGIMENT);
         }
      }
   }

   public final void updateAttack(BattleLine nAttacking, int iRoundID, int iDefense) {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            this.firstLine.get(i).cL = 0;
         }
      }

      int generalExtraCasualties = 0;
      if (nAttacking.armyGeneral != null) {
         if (this.armyGeneral != null) {
            generalExtraCasualties = Math.max(0, nAttacking.armyGeneral.getAttack() - this.armyGeneral.getDefense() - iDefense);
         } else {
            generalExtraCasualties = Math.max(0, nAttacking.armyGeneral.getAttack() - iDefense);
         }
      }

      for (int ix = 0; ix < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ix++) {
         if (nAttacking.firstLine.get(ix) != null) {
            float fArmyPercLeft = (float)nAttacking.firstLine.get(ix).aR.num / Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
            if (this.firstLine.get(ix) != null && this.firstLine.get(ix).aR.num > 0) {
               BattleRegiment var13 = this.firstLine.get(ix);
               var13.cL = var13.cL
                  + this.getAttackCasualties(
                     iRoundID,
                     generalExtraCasualties,
                     fArmyPercLeft,
                     (float)ArmyManager.lArmy
                           .get(nAttacking.firstLine.get(ix).aR.uID)
                           .get(nAttacking.firstLine.get(ix).aR.aID)
                           .getAttack(nAttacking.firstLine.get(ix).c)
                        / (
                           ArmyManager.lArmy.get(this.firstLine.get(ix).aR.uID).get(this.firstLine.get(ix).aR.aID).getDefense(this.firstLine.get(ix).c)
                              + iDefense
                        ),
                     nAttacking.firstLine.get(ix).c
                  );
               this.iLastAttackRoundID = iRoundID;
            } else {
               for (int j = 1; j < ArmyManager.lArmy.get(nAttacking.firstLine.get(ix).aR.uID).get(nAttacking.firstLine.get(ix).aR.aID).AttackRange + 1; j++) {
                  if (ix + j < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH && this.firstLine.get(ix + j) != null && this.firstLine.get(ix + j).aR.num > 0) {
                     BattleRegiment var12 = this.firstLine.get(ix + j);
                     var12.cL = var12.cL
                        + this.getAttackCasualties(
                           iRoundID,
                           generalExtraCasualties,
                           fArmyPercLeft,
                           (float)ArmyManager.lArmy
                                 .get(nAttacking.firstLine.get(ix).aR.uID)
                                 .get(nAttacking.firstLine.get(ix).aR.aID)
                                 .getAttack(nAttacking.firstLine.get(ix).c)
                              / (
                                 ArmyManager.lArmy
                                       .get(this.firstLine.get(ix + j).aR.uID)
                                       .get(this.firstLine.get(ix + j).aR.aID)
                                       .getDefense(this.firstLine.get(ix + j).c)
                                    + iDefense
                              ),
                           nAttacking.firstLine.get(ix).c
                        );
                     this.iLastAttackRoundID = iRoundID;
                     break;
                  }

                  if (ix - j >= 0 && this.firstLine.get(ix - j) != null && this.firstLine.get(ix - j).aR.num > 0) {
                     BattleRegiment var10000 = this.firstLine.get(ix - j);
                     var10000.cL = var10000.cL
                        + this.getAttackCasualties(
                           iRoundID,
                           generalExtraCasualties,
                           fArmyPercLeft,
                           (float)ArmyManager.lArmy
                                 .get(nAttacking.firstLine.get(ix).aR.uID)
                                 .get(nAttacking.firstLine.get(ix).aR.aID)
                                 .getAttack(nAttacking.firstLine.get(ix).c)
                              / (
                                 ArmyManager.lArmy
                                       .get(this.firstLine.get(ix - j).aR.uID)
                                       .get(this.firstLine.get(ix - j).aR.aID)
                                       .getDefense(this.firstLine.get(ix - j).c)
                                    + iDefense
                              ),
                           nAttacking.firstLine.get(ix).c
                        );
                     this.iLastAttackRoundID = iRoundID;
                     break;
                  }
               }
            }
         }
      }

      for (int ixx = 0; ixx < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ixx++) {
         if (nAttacking.secondLine.get(ixx) != null && ArmyManager.lUnitsTypes.get(nAttacking.secondLine.get(ixx).aR.uID).Line >= 2) {
            float fArmyPercLeft = (float)nAttacking.secondLine.get(ixx).aR.num / Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
            if (this.firstLine.get(ixx) != null && this.firstLine.get(ixx).aR.num > 0) {
               BattleRegiment var16 = this.firstLine.get(ixx);
               var16.cL = var16.cL
                  + this.getAttackCasualties(
                     iRoundID,
                     generalExtraCasualties,
                     fArmyPercLeft,
                     (float)ArmyManager.lArmy
                           .get(nAttacking.secondLine.get(ixx).aR.uID)
                           .get(nAttacking.secondLine.get(ixx).aR.aID)
                           .getAttack(nAttacking.secondLine.get(ixx).c)
                        / (
                           ArmyManager.lArmy.get(this.firstLine.get(ixx).aR.uID).get(this.firstLine.get(ixx).aR.aID).getDefense(this.firstLine.get(ixx).c)
                              + iDefense
                        ),
                     nAttacking.secondLine.get(ixx).c
                  );
               this.iLastAttackRoundID = iRoundID;
            } else {
               for (int j = 1; j < ArmyManager.lArmy.get(nAttacking.secondLine.get(ixx).aR.uID).get(nAttacking.secondLine.get(ixx).aR.aID).AttackRange + 1; j++) {
                  if (ixx + j < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH && this.firstLine.get(ixx + j) != null && this.firstLine.get(ixx + j).aR.num > 0) {
                     BattleRegiment var15 = this.firstLine.get(ixx + j);
                     var15.cL = var15.cL
                        + this.getAttackCasualties(
                           iRoundID,
                           generalExtraCasualties,
                           fArmyPercLeft,
                           (float)ArmyManager.lArmy
                                 .get(nAttacking.secondLine.get(ixx).aR.uID)
                                 .get(nAttacking.secondLine.get(ixx).aR.aID)
                                 .getAttack(nAttacking.secondLine.get(ixx).c)
                              / (
                                 ArmyManager.lArmy
                                       .get(this.firstLine.get(ixx + j).aR.uID)
                                       .get(this.firstLine.get(ixx + j).aR.aID)
                                       .getDefense(this.firstLine.get(ixx + j).c)
                                    + iDefense
                              ),
                           nAttacking.secondLine.get(ixx).c
                        );
                     this.iLastAttackRoundID = iRoundID;
                     break;
                  }

                  if (ixx - j >= 0 && this.firstLine.get(ixx - j) != null && this.firstLine.get(ixx - j).aR.num > 0) {
                     BattleRegiment var14 = this.firstLine.get(ixx - j);
                     var14.cL = var14.cL
                        + this.getAttackCasualties(
                           iRoundID,
                           generalExtraCasualties,
                           fArmyPercLeft,
                           (float)ArmyManager.lArmy
                                 .get(nAttacking.secondLine.get(ixx).aR.uID)
                                 .get(nAttacking.secondLine.get(ixx).aR.aID)
                                 .getAttack(nAttacking.secondLine.get(ixx).c)
                              / (
                                 ArmyManager.lArmy
                                       .get(this.firstLine.get(ixx - j).aR.uID)
                                       .get(this.firstLine.get(ixx - j).aR.aID)
                                       .getDefense(this.firstLine.get(ixx - j).c)
                                    + iDefense
                              ),
                           nAttacking.secondLine.get(ixx).c
                        );
                     this.iLastAttackRoundID = iRoundID;
                     break;
                  }
               }
            }
         }
      }
   }

   private final int getAttackCasualties(int iRoundID, int generalExtraCasualties, float fArmyPercLeft, float fAttackDefense, int iCivID) {
      return (int)Math.ceil(
         (GameValues.battle.BATTLE_CASUALTIES + generalExtraCasualties + this.diceRollGeneral)
            * (fArmyPercLeft * fAttackDefense * (1.0F + iRoundID / 50.0F + Game.getCiv(iCivID).civBonuses.Discipline))
      );
   }

   public final void updateMoraleAndRetreat() {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null && this.firstLine.get(i).cL > 0 && this.firstLine.get(i).aR.num > 0 && this.firstLine.get(i).cL > 0) {
            this.firstLine.get(i).aR.mo = Math.max(
               0.0F,
               this.firstLine.get(i).aR.mo - (float)this.firstLine.get(i).cL / this.firstLine.get(i).aR.num * GameValues.battle.BATTLE_MORALE_LOSS_MULTIPLIER
            );
            if (this.firstLine.get(i).aR.mo < 0.96F) {
               if (this.firstLine.get(i).aR.mo <= GameValues.battle.BATTLE_FULL_RETREAT_IF_MORALE_BELOW) {
                  this.firstLine.get(i).rL = this.firstLine.get(i).aR.num;
                  BattleRegiment var10000 = this.firstLine.get(i);
                  var10000.re = var10000.re + this.firstLine.get(i).rL;
                  ArmyRegiment var2 = this.firstLine.get(i).aR;
                  var2.num = var2.num - this.firstLine.get(i).rL;
                  this.iRetreated = this.iRetreated + this.firstLine.get(i).rL;
                  this.numOfUnits = this.numOfUnits - this.firstLine.get(i).rL;
                  this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield - this.firstLine.get(i).rL;
               } else {
                  this.firstLine.get(i).rL = (int)Math.max(0.0F, this.firstLine.get(i).cL * (1.0F - this.firstLine.get(i).aR.mo));
                  if (this.firstLine.get(i).rL > 0) {
                     if (this.firstLine.get(i).rL > this.firstLine.get(i).aR.num) {
                        this.firstLine.get(i).rL = this.firstLine.get(i).aR.num;
                     }

                     BattleRegiment var3 = this.firstLine.get(i);
                     var3.re = var3.re + this.firstLine.get(i).rL;
                     ArmyRegiment var4 = this.firstLine.get(i).aR;
                     var4.num = var4.num - this.firstLine.get(i).rL;
                     this.iRetreated = this.iRetreated + this.firstLine.get(i).rL;
                     this.numOfUnits = this.numOfUnits - this.firstLine.get(i).rL;
                     this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield - this.firstLine.get(i).rL;
                  }
               }
            }
         }
      }

      this.updateAverageMoraleOnBattleField();
   }

   public final void updateAverageMoraleOnBattleField() {
      this.fMorale = 0.0F;
      int tNum = 0;

      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            this.fMorale = this.fMorale + this.firstLine.get(i).aR.mo;
            tNum++;
         }
      }

      for (int ix = 0; ix < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ix++) {
         if (this.secondLine.get(ix) != null) {
            this.fMorale = this.fMorale + this.secondLine.get(ix).aR.mo;
            tNum++;
         }
      }

      this.fMorale /= tNum;
   }

   public final void updateCasualties() {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         if (this.firstLine.get(i) != null) {
            if (this.firstLine.get(i).cL > this.firstLine.get(i).aR.num) {
               this.firstLine.get(i).cL = this.firstLine.get(i).aR.num;
            }

            BattleRegiment var10000 = this.firstLine.get(i);
            var10000.ca = var10000.ca + this.firstLine.get(i).cL;
            ArmyRegiment var2 = this.firstLine.get(i).aR;
            var2.num = var2.num - this.firstLine.get(i).cL;
            this.iCasualties = this.iCasualties + this.firstLine.get(i).cL;
            this.numOfUnits = this.numOfUnits - this.firstLine.get(i).cL;
            this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield - this.firstLine.get(i).cL;
         }
      }
   }

   public final void updateDefeated() {
      try {
         int regroup = 0;

         for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
            if (this.firstLine.get(i) != null && this.firstLine.get(i).aR.num <= 0) {
               if (this.firstLine.get(i).f == 0) {
                  this.defeated.add(this.firstLine.get(i));
                  this.firstLine.get(i).f = 1;
               }

               if (this.reserveFirstLine.size() > 0) {
                  this.firstLine.set(i, this.reserveFirstLine.get(0));
                  this.inReserve = this.inReserve - this.reserveFirstLine.get(0).aR.num;
                  this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.reserveFirstLine.get(0).aR.num;
                  this.reserveFirstLine.remove(0);
               } else {
                  boolean updated = false;

                  for (int j = 0; j < this.secondLine.size(); j++) {
                     if (this.secondLine.get(j) != null && ArmyManager.lUnitsTypes.get(this.secondLine.get(j).aR.uID).Line < 2) {
                        this.firstLine.set(i, this.secondLine.get(j));
                        this.secondLine.set(j, null);
                        updated = true;
                        break;
                     }
                  }

                  if (!updated) {
                     if (this.reserveSecondLine.size() > 0) {
                        this.firstLine.set(i, this.reserveSecondLine.get(0));
                        this.inReserve = this.inReserve - this.reserveSecondLine.get(0).aR.num;
                        this.numOfUnitsOnBattlefield = this.numOfUnitsOnBattlefield + this.reserveSecondLine.get(0).aR.num;
                        this.reserveSecondLine.remove(0);
                     } else if (this.secondLine.get(i) != null) {
                        this.firstLine.set(i, this.secondLine.get(i));
                        this.secondLine.set(i, null);
                     } else if (i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH / 2) {
                        if (i > 0) {
                           BattleRegiment tRegiment = this.firstLine.get(i);
                           this.firstLine.set(i, this.firstLine.get(i - 1));
                           this.firstLine.set(i - 1, tRegiment);
                           regroup++;
                        }
                     } else if (i + 1 < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH) {
                        BattleRegiment tRegiment = this.firstLine.get(i);
                        this.firstLine.set(i, this.firstLine.get(i + 1));
                        this.firstLine.set(i + 1, tRegiment);
                        regroup++;
                     }
                  }
               }
            }
         }

         if (regroup > 0) {
            for (int k = 0; k < regroup; k++) {
               for (int ix = 0; ix < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ix++) {
                  if (this.firstLine.get(ix) != null && this.firstLine.get(ix).aR.num <= 0) {
                     if (ix < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH / 2) {
                        if (ix > 0) {
                           BattleRegiment tRegiment = this.firstLine.get(ix);
                           this.firstLine.set(ix, this.firstLine.get(ix - 1));
                           this.firstLine.set(ix - 1, tRegiment);
                        }
                     } else if (ix + 1 < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH) {
                        BattleRegiment tRegiment = this.firstLine.get(ix);
                        this.firstLine.set(ix, this.firstLine.get(ix + 1));
                        this.firstLine.set(ix + 1, tRegiment);
                     }
                  }
               }
            }
         }

         for (int ixx = 0; ixx < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ixx++) {
            if (this.firstLine.get(ixx) != null) {
               if (this.firstLine.get(ixx).f <= 0) {
                  break;
               }

               this.firstLine.set(ixx, null);
            }
         }

         for (int ixxx = GameValues.battle.BATTLE_MAX_BATTLE_WIDTH - 1; ixxx >= 0; ixxx--) {
            if (this.firstLine.get(ixxx) != null) {
               if (this.firstLine.get(ixxx).f <= 0) {
                  break;
               }

               this.firstLine.set(ixxx, null);
            }
         }

         if (regroup > 0) {
            int firstID = -1;
            int lastID = -1;
            int numOfNull = 0;

            for (int ixxxx = 0; ixxxx < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ixxxx++) {
               if (this.firstLine.get(ixxxx) != null) {
                  firstID = ixxxx;
                  break;
               }
            }

            for (int ixxxxx = GameValues.battle.BATTLE_MAX_BATTLE_WIDTH - 1; ixxxxx >= 0; ixxxxx--) {
               if (this.firstLine.get(ixxxxx) != null) {
                  lastID = ixxxxx;
                  break;
               }
            }

            if (firstID >= 0 && lastID >= 0) {
               List<BattleRegiment> tReg = new ArrayList<>();

               for (int ixxxxxx = firstID; ixxxxxx <= lastID; ixxxxxx++) {
                  if (this.firstLine.get(ixxxxxx) == null) {
                     numOfNull++;
                  } else {
                     tReg.add(this.firstLine.get(ixxxxxx));
                     this.firstLine.set(ixxxxxx, null);
                  }
               }

               try {
                  int ixxxxxxx = firstID;
                  int jx = 0;

                  for (int pos = (int)Math.max(0.0, GameValues.battle.BATTLE_MAX_BATTLE_WIDTH / 2 - Math.floor((lastID - numOfNull - firstID) / 2.0F));
                     ixxxxxxx <= lastID - numOfNull;
                     pos++
                  ) {
                     this.firstLine.set(pos, tReg.get(jx));
                     ixxxxxxx++;
                     jx++;
                  }
               } catch (Exception var10) {
               }
            }

            firstID = -1;
            lastID = -1;
            numOfNull = 0;

            for (int ixxxxxxx = 0; ixxxxxxx < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; ixxxxxxx++) {
               if (this.secondLine.get(ixxxxxxx) != null) {
                  firstID = ixxxxxxx;
                  break;
               }
            }

            for (int ixxxxxxxx = GameValues.battle.BATTLE_MAX_BATTLE_WIDTH - 1; ixxxxxxxx >= 0; ixxxxxxxx--) {
               if (this.secondLine.get(ixxxxxxxx) != null) {
                  lastID = ixxxxxxxx;
                  break;
               }
            }

            if (firstID >= 0 && lastID >= 0) {
               List<BattleRegiment> tReg = new ArrayList<>();

               for (int ixxxxxxxxx = firstID; ixxxxxxxxx <= lastID; ixxxxxxxxx++) {
                  if (this.secondLine.get(ixxxxxxxxx) == null) {
                     numOfNull++;
                  } else {
                     tReg.add(this.secondLine.get(ixxxxxxxxx));
                     this.secondLine.set(ixxxxxxxxx, null);
                  }
               }

               try {
                  int ixxxxxxxxxx = firstID;
                  int jx = 0;

                  for (int pos = (int)Math.max(0.0, GameValues.battle.BATTLE_MAX_BATTLE_WIDTH / 2 - Math.floor((lastID - numOfNull - firstID) / 2.0F));
                     ixxxxxxxxxx <= lastID - numOfNull;
                     pos++
                  ) {
                     this.secondLine.set(pos, tReg.get(jx));
                     ixxxxxxxxxx++;
                     jx++;
                  }
               } catch (Exception var9) {
               }
            }
         }
      } catch (Exception var11) {
         CFG.exceptionStack(var11);
      }
   }

   public final void buildCasualties() {
      this.lCasualties.clear();

      for (int i = this.lCivs.size() - 1; i >= 0; i--) {
         this.lCasualties.add(0);
      }

      for (int i = this.firstLine.size() - 1; i >= 0; i--) {
         if (this.firstLine.get(i) != null) {
            this.addCasualties(this.firstLine.get(i).c, this.firstLine.get(i).ca);
         }
      }

      for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
         if (this.secondLine.get(ix) != null) {
            this.addCasualties(this.secondLine.get(ix).c, this.secondLine.get(ix).ca);
         }
      }

      for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
         this.addCasualties(this.reserveFirstLine.get(ixx).c, this.reserveFirstLine.get(ixx).ca);
      }

      for (int ixx = this.reserveSecondLine.size() - 1; ixx >= 0; ixx--) {
         this.addCasualties(this.reserveSecondLine.get(ixx).c, this.reserveSecondLine.get(ixx).ca);
      }

      for (int ixx = this.defeated.size() - 1; ixx >= 0; ixx--) {
         this.addCasualties(this.defeated.get(ixx).c, this.defeated.get(ixx).ca);
      }
   }

   public final void addCasualties(int iCivID, int iCasualties) {
      if (iCasualties > 0) {
         for (int i = this.lCivs.size() - 1; i >= 0; i--) {
            if (this.lCivs.get(i) == iCivID) {
               this.lCasualties.set(i, this.lCasualties.get(i) + iCasualties);
               return;
            }
         }
      }
   }

   public final void buildArmyDivisionsKeys() {
      this.lDefeatedArmyDivisionKeys.clear();

      for (int i = this.firstLine.size() - 1; i >= 0; i--) {
         if (this.firstLine.get(i) != null) {
            this.addDefeatedArmyDivisionKey(this.firstLine.get(i).aK);
         }
      }

      for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
         if (this.secondLine.get(ix) != null) {
            this.addDefeatedArmyDivisionKey(this.secondLine.get(ix).aK);
         }
      }

      for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
         this.addDefeatedArmyDivisionKey(this.reserveFirstLine.get(ixx).aK);
      }

      for (int ixx = this.reserveSecondLine.size() - 1; ixx >= 0; ixx--) {
         this.addDefeatedArmyDivisionKey(this.reserveSecondLine.get(ixx).aK);
      }

      for (int ixx = this.defeated.size() - 1; ixx >= 0; ixx--) {
         this.addDefeatedArmyDivisionKey(this.defeated.get(ixx).aK);
      }
   }

   public final void addDefeatedArmyDivisionKey(String key) {
      if (key != null) {
         for (int i = this.lDefeatedArmyDivisionKeys.size() - 1; i >= 0; i--) {
            if (this.lDefeatedArmyDivisionKeys.get(i).equals(key)) {
               return;
            }
         }

         this.lDefeatedArmyDivisionKeys.add(key);
      }
   }

   public boolean isInBattleArmy(String key) {
      try {
         for (int i = this.firstLine.size() - 1; i >= 0; i--) {
            if (this.firstLine.get(i) != null && this.firstLine.get(i).aK.equals(key)) {
               return true;
            }
         }

         for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
            if (this.secondLine.get(ix) != null && this.secondLine.get(ix).aK.equals(key)) {
               return true;
            }
         }

         for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
            if (this.reserveFirstLine.get(ixx).aK.equals(key)) {
               return true;
            }
         }

         for (int ixxx = this.reserveSecondLine.size() - 1; ixxx >= 0; ixxx--) {
            if (this.reserveSecondLine.get(ixxx).aK.equals(key)) {
               return true;
            }
         }

         for (int ixxxx = this.defeated.size() - 1; ixxxx >= 0; ixxxx--) {
            if (this.defeated.get(ixxxx).aK.equals(key)) {
               return true;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return false;
   }

   public boolean isInBattleCiv(int iCivID) {
      for (int i = this.lCivs.size() - 1; i >= 0; i--) {
         if (this.lCivs.get(i) == iCivID) {
            return true;
         }
      }

      return false;
   }

   public int getCivsRegimentsLimit() {
      int out = 0;

      for (int i = this.lCivs.size() - 1; i >= 0; i--) {
         out += Game.getCiv(this.lCivs.get(i)).iRegimentsLimit;
      }

      return out;
   }

   public final void updateMoraleEndOfBattle(float fMorale) {
      for (int i = this.firstLine.size() - 1; i >= 0; i--) {
         if (this.firstLine.get(i) != null) {
            this.firstLine.get(i).aR.mo = Math.max(fMorale, this.firstLine.get(i).aR.mo);
         }
      }

      for (int ix = this.secondLine.size() - 1; ix >= 0; ix--) {
         if (this.secondLine.get(ix) != null) {
            this.secondLine.get(ix).aR.mo = Math.max(fMorale, this.secondLine.get(ix).aR.mo);
         }
      }

      for (int ixx = this.reserveFirstLine.size() - 1; ixx >= 0; ixx--) {
         this.reserveFirstLine.get(ixx).aR.mo = Math.max(fMorale, this.reserveFirstLine.get(ixx).aR.mo);
      }

      for (int ixx = this.reserveSecondLine.size() - 1; ixx >= 0; ixx--) {
         this.reserveSecondLine.get(ixx).aR.mo = Math.max(fMorale, this.reserveSecondLine.get(ixx).aR.mo);
      }

      for (int ixx = this.defeated.size() - 1; ixx >= 0; ixx--) {
         this.defeated.get(ixx).aR.mo = Math.max(fMorale, this.defeated.get(ixx).aR.mo);
      }
   }
}
