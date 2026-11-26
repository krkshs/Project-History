package aoc.kingdoms.lukasz.map.battles;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class Battle {
   public String key;
   public int provinceID;
   public int roundID;
   public BattleLine attackingArmy;
   public BattleLine defendingArmy;
   public int posX = 0;
   public int posY = 0;
   public int battleWidth = 0;
   public int iMaxBattleWidth = 0;

   public Battle() {
   }

   public Battle(int iProvinceID, List<ArmyDivision> nAttackingArmy, List<ArmyDivision> nDefendingArmy) {
      this.key = CFG.extraRandomTag();
      this.roundID = 0;
      this.provinceID = iProvinceID;
      this.deployArmies(nAttackingArmy, nDefendingArmy);
   }

   public final void deployArmies(List<ArmyDivision> nAttackingArmy, List<ArmyDivision> nDefendingArmy) {
      int maxWidth = GameValues.battle.BATTLE_MIN_BATTLE_WIDTH;

      for (int i = 0; i < nAttackingArmy.size(); i++) {
         maxWidth = Math.min(GameValues.battle.BATTLE_MAX_BATTLE_WIDTH, BattleManager.getBattleWidth(nAttackingArmy.get(i).civID));
         nAttackingArmy.get(i).inBattle = true;
         if (nAttackingArmy.get(i).inMovement) {
            if (nAttackingArmy.get(i).civID < 0) {
               Game.revolutionMoveUnits.updateMoveInBattle(nAttackingArmy.get(i).key, nAttackingArmy.get(i).inBattle);
            } else {
               Game.getCiv(nAttackingArmy.get(i).civID).updateMoveInBattle(nAttackingArmy.get(i).key, nAttackingArmy.get(i).inBattle);
            }
         }
      }

      for (int ix = 0; ix < nDefendingArmy.size(); ix++) {
         maxWidth = Math.min(GameValues.battle.BATTLE_MAX_BATTLE_WIDTH, BattleManager.getBattleWidth(nDefendingArmy.get(ix).civID));
         nDefendingArmy.get(ix).inBattle = true;
         if (nDefendingArmy.get(ix).inMovement) {
            if (nDefendingArmy.get(ix).civID < 0) {
               Game.revolutionMoveUnits.updateMoveInBattle(nDefendingArmy.get(ix).key, nDefendingArmy.get(ix).inBattle);
            } else {
               Game.getCiv(nDefendingArmy.get(ix).civID).updateMoveInBattle(nDefendingArmy.get(ix).key, nDefendingArmy.get(ix).inBattle);
            }
         }
      }

      this.iMaxBattleWidth = maxWidth;
      int sideArmyMin = Math.min(getArmyInFirstLine(nAttackingArmy), getArmyInFirstLine(nDefendingArmy));
      this.attackingArmy = new BattleLine(nAttackingArmy, maxWidth, sideArmyMin, this.key);
      this.defendingArmy = new BattleLine(nDefendingArmy, maxWidth, sideArmyMin, this.key);
      if (nAttackingArmy.get(0).armyGeneral == null) {
         for (int ixx = 0; ixx < nAttackingArmy.size(); ixx++) {
            if (nAttackingArmy.get(ixx).armyGeneral != null) {
               this.attackingArmy.armyGeneral = nAttackingArmy.get(ixx).armyGeneral;
               break;
            }
         }
      } else {
         this.attackingArmy.armyGeneral = nAttackingArmy.get(0).armyGeneral;
      }

      if (nDefendingArmy.get(0).armyGeneral == null) {
         for (int ixxx = 0; ixxx < nDefendingArmy.size(); ixxx++) {
            if (nDefendingArmy.get(ixxx).armyGeneral != null) {
               this.defendingArmy.armyGeneral = nDefendingArmy.get(ixxx).armyGeneral;
               break;
            }
         }
      } else {
         this.defendingArmy.armyGeneral = nDefendingArmy.get(0).armyGeneral;
      }

      this.attackingArmy.iCivID = nAttackingArmy.get(0).civID;
      this.defendingArmy.iCivID = nDefendingArmy.get(0).civID;

      for (int ixxxx = 0; ixxxx < nAttackingArmy.size(); ixxxx++) {
         if (nAttackingArmy.get(ixxxx).armyGeneral != null) {
            nAttackingArmy.get(ixxxx).armyGeneral.addCombatExperience(GameValues.generals.COMBAT_EXPERIENCE_JOIN_BATTLE);
         }
      }

      for (int ixxxxx = 0; ixxxxx < nDefendingArmy.size(); ixxxxx++) {
         if (nDefendingArmy.get(ixxxxx).armyGeneral != null) {
            nDefendingArmy.get(ixxxxx).armyGeneral.addCombatExperience(GameValues.generals.COMBAT_EXPERIENCE_JOIN_BATTLE);
         }
      }
   }

   public static int getArmyInFirstLine(List<ArmyDivision> nArmy) {
      int out = 0;
      int j = 0;

      for (int jSize = nArmy.size(); j < jSize; j++) {
         for (int i = 0; i < nArmy.get(j).iArmyRegimentSize; i++) {
            if (ArmyManager.lUnitsTypes.get(nArmy.get(j).lArmyRegiment.get(i).uID).Line < 2) {
               out++;
            }
         }
      }

      return out;
   }

   public int getBattleWidth() {
      return (ImageManager.getImage(Images.armyFlag).getWidth() + 3 + 2) * 2
         + this.attackingArmy.textW
         + 10
         + ImageManager.getImage(Images.battleIcon0).getWidth()
         + this.defendingArmy.textW
         + 10;
   }

   public final void drawBattle_Just(SpriteBatch oSB, float fAlpha) {
      if (Game.getProvince(this.provinceID).getDrawProvince()) {
         this.battleWidth = this.getBattleWidth();
         this.posX = getArmyPosX(this.provinceID) - this.battleWidth / 2;
         int var10001 = getArmyPosY(this.provinceID);
         int var10002 = ImageManager.getImage(Images.army).getHeight() + 2;
         Province var10003 = Game.getProvince(this.provinceID);
         int var3 = var10003.iBattlesInProvince;
         var10003.iBattlesInProvince += 1;
         this.posY = var10001 - var10002 * var3;
         if (Game.hoveredBattle.iProvinceID == this.provinceID && Game.hoveredBattle.key.equals(this.key)) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.animationHover.getAlpha() / 255.0F * fAlpha));
            ProvinceDrawArmy.drawProvinceArmyHover(oSB, this.posX, this.posY, this.battleWidth);
            oSB.setColor(Color.WHITE);
         }

         this.drawBattle_Just(oSB, fAlpha, this.posX, this.posY);
      }
   }

   public final void drawBattle_Just(SpriteBatch oSB, float fAlpha, int posX, int posY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      drawProvinceArmyFlag(oSB, posX, posY, this.attackingArmy.iCivID, Images.armyLeft, false);
      int nX = ImageManager.getImage(Images.armyFlag).getWidth() + 3 + 2;
      ImageManager.getImage(Images.armyPlayer0)
         .draw2(
            oSB,
            posX + nX,
            posY,
            this.attackingArmy.textW + 10 + ImageManager.getImage(Images.battleIcon0).getWidth() / 2,
            ImageManager.getImage(Images.army).getHeight(),
            true,
            false
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_ARMY,
         this.attackingArmy.text,
         posX + nX + this.attackingArmy.armyExtraPosX + 5,
         posY + (int)(ImageManager.getImage(Images.armyLeft).getHeight() / 2.0F) - (int)(ProvinceDrawArmy.ARMY_HEIGHT / 2.0F),
         new Color(ProvinceDrawArmy.COLOR_ARMY.r, ProvinceDrawArmy.COLOR_ARMY.g, ProvinceDrawArmy.COLOR_ARMY.b, ProvinceDrawArmy.COLOR_ARMY.a * fAlpha)
      );
      nX += this.attackingArmy.textW + 10 + ImageManager.getImage(Images.battleIcon0).getWidth() / 2;
      ImageManager.getImage(Images.armyEnemy)
         .draw2(
            oSB,
            posX + nX,
            posY,
            this.defendingArmy.textW + 10 + ImageManager.getImage(Images.battleIcon0).getWidth() / 2,
            ImageManager.getImage(Images.army).getHeight(),
            false,
            false
         );
      nX += ImageManager.getImage(Images.battleIcon0).getWidth() / 2;
      Renderer.drawText(
         oSB,
         CFG.FONT_ARMY,
         this.defendingArmy.text,
         posX + nX + this.defendingArmy.armyExtraPosX + 5,
         posY + (int)(ImageManager.getImage(Images.armyLeft).getHeight() / 2.0F) - (int)(ProvinceDrawArmy.ARMY_HEIGHT / 2.0F),
         new Color(ProvinceDrawArmy.COLOR_ARMY.r, ProvinceDrawArmy.COLOR_ARMY.g, ProvinceDrawArmy.COLOR_ARMY.b, ProvinceDrawArmy.COLOR_ARMY.a * fAlpha)
      );
      nX += this.defendingArmy.textW + 10;
      drawProvinceArmyFlag(oSB, posX + nX, posY, this.defendingArmy.iCivID, Images.armyLeft, true);
      ImageManager.getImage(BattleManager.BATTLE_IMG_ID)
         .draw(
            oSB,
            posX + ImageManager.getImage(Images.armyFlag).getWidth() + 3 + 2 + this.attackingArmy.textW + 10,
            posY + ImageManager.getImage(Images.army).getHeight() / 2 - ImageManager.getImage(BattleManager.BATTLE_IMG_ID).getHeight() / 2
         );
      oSB.setColor(Color.WHITE);
   }

   private static final void drawProvinceArmyFlag(SpriteBatch oSB, int nPosX, int nPosY, int nCivID, int ImageID, boolean flipX) {
      ImageManager.getImage(ImageID)
         .draw2(oSB, nPosX, nPosY, ImageManager.getImage(Images.armyFlag).getWidth() + 3 + 2, ImageManager.getImage(ImageID).getHeight(), flipX, false);
      if (nCivID < 0) {
         ImageManager.getImage(Images.rebelsFlag)
            .draw(oSB, nPosX + 3, nPosY + 2, ImageManager.getImage(Images.armyFlag).getWidth(), ImageManager.getImage(Images.armyFlag).getHeight());
      } else {
         Game.getCiv(nCivID)
            .getFlag()
            .draw(oSB, nPosX + 3, nPosY + 2, ImageManager.getImage(Images.armyFlag).getWidth(), ImageManager.getImage(Images.armyFlag).getHeight());
      }

      ImageManager.getImage(Images.armyFlag).draw(oSB, nPosX + 3, nPosY + 2);
   }

   public static final int getArmyPosX(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftX + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * Game.mapScale.getCurrentScale());
   }

   public static final int getArmyPosY(int nProvinceID) {
      return (int)(
         (Game.getProvince(nProvinceID).iCenterShiftY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale()
            - ImageManager.getImage(Images.army).getHeight()
            - ImageManager.getImage(Images.army).getHeight() / 2
            - 2.0F
      );
   }

   public final void updateBattle() {
      if (++this.roundID > GameValues.battle.BATTLE_DEPLOYMENT_PHASE_TURNS) {
         this.attackingArmy.updateDefeated();
         this.defendingArmy.updateDefeated();
         this.attackingArmy.updateDiceRoll();
         this.defendingArmy.updateDiceRoll();
         this.attackingArmy.updateAttack(this.defendingArmy, this.roundID, 0);
         this.defendingArmy.updateAttack(this.attackingArmy, this.roundID, getDefendersProvinceBonuses(this.provinceID));
         this.attackingArmy.updateCasualties();
         this.defendingArmy.updateCasualties();
         this.attackingArmy.updateMoraleAndRetreat();
         this.defendingArmy.updateMoraleAndRetreat();
         if (this.roundID % 2 == 1) {
            this.updateNumOfUnits();
         }

         this.attackingArmy.setText("" + CFG.getPrecision2(this.attackingArmy.numOfUnits / 1000.0F, 10));
         this.defendingArmy.setText("" + CFG.getPrecision2(this.defendingArmy.numOfUnits / 1000.0F, 10));
      }
   }

   public static int getDefendersProvinceBonuses(int iProvinceID) {
      return Game.terrainManager.terrains.get(Game.getProvince(iProvinceID).getTerrainID()).Defense + Game.getProvince(iProvinceID).provBonuses.DefenseBonus;
   }

   public final boolean endOfBattle() {
      return this.attackingArmy.numOfUnits <= 0 || this.defendingArmy.numOfUnits <= 0;
   }

   public final boolean endOfBattle_NoAttacks() {
      return this.roundID - this.attackingArmy.iLastAttackRoundID > 7 || this.roundID - this.defendingArmy.iLastAttackRoundID > 7;
   }

   public final void updateNumOfUnits() {
      this.attackingArmy.updateNumOfUnits();
      this.defendingArmy.updateNumOfUnits();
   }

   public final void updateBattle_Summary(boolean armyCanBeDestroyed) {
      boolean aggressorsWon = this.attackingArmy.numOfUnits > this.defendingArmy.numOfUnits;
      this.updateBattle_SummaryCasualties(aggressorsWon);
      this.attackingArmy.updateBattle_Summary(this.provinceID, !aggressorsWon, this.roundID, armyCanBeDestroyed);
      this.defendingArmy.updateBattle_Summary(this.provinceID, aggressorsWon, this.roundID, armyCanBeDestroyed);
      Game.getProvince(this.provinceID).updateArmyPosY();
   }

   public final void updateBattle_SummaryCasualties(boolean aggressorsWon) {
      String warKey = WarManager.getWarKey(this.attackingArmy.lCivs.get(0), this.defendingArmy.lCivs.get(0));
      if (warKey != null) {
         try {
            this.attackingArmy.buildCasualties();
            this.defendingArmy.buildCasualties();

            for (int i = this.attackingArmy.lCivs.size() - 1; i >= 0; i--) {
               WarManager.lWars.get(warKey).addCasualties(this.attackingArmy.lCivs.get(i), this.attackingArmy.lCasualties.get(i));
            }

            for (int i = this.defendingArmy.lCivs.size() - 1; i >= 0; i--) {
               WarManager.lWars.get(warKey).addCasualties(this.defendingArmy.lCivs.get(i), this.defendingArmy.lCasualties.get(i));
            }

            float fWarScore;
            if (aggressorsWon) {
               fWarScore = Math.min(
                  this.defendingArmy.iCasualties
                     * GameValues.battle.BATTLE_WAR_SCORE_CASUALTIES
                     / (this.defendingArmy.getCivsRegimentsLimit() * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE),
                  GameValues.battle.BATTLE_WAR_SCORE_MAX
               );
               fWarScore = WarManager.lWars.get(warKey).addWarScore_ValueToAdd(fWarScore, this.attackingArmy.lCivs.get(0), this.defendingArmy.lCivs.get(0));
               if (WarManager.lWars.get(warKey).isAggressor(this.attackingArmy.lCivs.get(0))) {
                  WarManager.lWars.get(warKey).addWarScore_Just(fWarScore);
                  WarManager.lWars.get(warKey).warScoreFromBattles += fWarScore;
               } else {
                  WarManager.lWars.get(warKey).addWarScore_Just(-fWarScore);
                  WarManager.lWars.get(warKey).warScoreFromBattles -= fWarScore;
               }

               Game.getCiv(this.defendingArmy.lCivs.get(0)).updateWarWeariness(fWarScore * GameValues.war.WAR_WAR_WEARINESS_BATTLE);
               this.attackingArmy.updateMoraleEndOfBattle(GameValues.battle.BATTLE_MIN_MORALE_VICTORIOUS_ARMY);
               this.defendingArmy.updateMoraleEndOfBattle(GameValues.battle.BATTLE_MIN_MORALE_DEFEATED_ARMY);
            } else {
               fWarScore = Math.min(
                  this.attackingArmy.iCasualties
                     * GameValues.battle.BATTLE_WAR_SCORE_CASUALTIES
                     / (this.attackingArmy.getCivsRegimentsLimit() * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE),
                  GameValues.battle.BATTLE_WAR_SCORE_MAX
               );
               fWarScore = WarManager.lWars.get(warKey).addWarScore_ValueToAdd(fWarScore, this.attackingArmy.lCivs.get(0), this.defendingArmy.lCivs.get(0));
               if (WarManager.lWars.get(warKey).isAggressor(this.defendingArmy.lCivs.get(0))) {
                  WarManager.lWars.get(warKey).addWarScore_Just(fWarScore);
                  WarManager.lWars.get(warKey).warScoreFromBattles += fWarScore;
               } else {
                  WarManager.lWars.get(warKey).addWarScore_Just(-fWarScore);
                  WarManager.lWars.get(warKey).warScoreFromBattles -= fWarScore;
               }

               Game.getCiv(this.attackingArmy.lCivs.get(0)).updateWarWeariness(fWarScore * GameValues.war.WAR_WAR_WEARINESS_BATTLE);
               this.attackingArmy.updateMoraleEndOfBattle(GameValues.battle.BATTLE_MIN_MORALE_DEFEATED_ARMY);
               this.defendingArmy.updateMoraleEndOfBattle(GameValues.battle.BATTLE_MIN_MORALE_VICTORIOUS_ARMY);
            }

            if (this.attackingArmy.isInBattleCiv(Game.player.iCivID) || this.defendingArmy.isInBattleCiv(Game.player.iCivID)) {
               boolean battleWon = false;
               if (aggressorsWon && this.attackingArmy.isInBattleCiv(Game.player.iCivID)) {
                  battleWon = true;
               } else if (!aggressorsWon && this.defendingArmy.isInBattleCiv(Game.player.iCivID)) {
                  battleWon = true;
               }

               String notificationKey = CFG.extraRandomTag();
               Game.player
                  .addNotification(
                     new Notification(
                        Notification.Notification_Type.BATTLE_REPORT,
                        (battleWon ? Game.lang.get("BattleWon") : Game.lang.get("BattleLost")) + ": " + Game.getProvince(this.provinceID).getProvinceName(),
                        Images.battle,
                        Game_Calendar.TURN_ID,
                        battleWon ? Notification.Notification_BG.GREEN : Notification.Notification_BG.RED,
                        notificationKey,
                        this.provinceID
                     )
                  );
               Game.player
                  .addBattleReport(
                     new BattleReport(
                        notificationKey,
                        this.provinceID,
                        fWarScore,
                        aggressorsWon,
                        battleWon,
                        Game_Calendar.TURN_ID,
                        new BattleReport.CivReport(
                           this.attackingArmy.lCivs.get(0),
                           this.attackingArmy.numOfUnits + this.attackingArmy.iCasualties + this.attackingArmy.iRetreated,
                           this.attackingArmy.iCasualties,
                           this.attackingArmy.iRetreated
                        ),
                        new BattleReport.CivReport(
                           this.defendingArmy.lCivs.get(0),
                           this.defendingArmy.numOfUnits + this.defendingArmy.iCasualties + this.defendingArmy.iRetreated,
                           this.defendingArmy.iCasualties,
                           this.defendingArmy.iRetreated
                        )
                     )
                  );
            }
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }
      }
   }

   public boolean isInBattle(String key) {
      return this.attackingArmy.isInBattleArmy(key) ? true : this.defendingArmy.isInBattleArmy(key);
   }
}
