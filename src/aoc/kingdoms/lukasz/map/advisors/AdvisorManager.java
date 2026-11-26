package aoc.kingdoms.lukasz.map.advisors;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class AdvisorManager {
   public List<Integer> advisorsImagesSize = new ArrayList<>();

   public static int getRecruitGoldCost(int iCivID) {
      return (int)(
         GameValues.advisors.RECRUIT_ADVISOR_GOLD_COST
            * Math.max(0.0F, 1.0F + Game.getCiv(iCivID).civBonuses.AdvisorCost + GameValues.civRank.CIV_RANK_ADVISOR_COST[Game.getCiv(iCivID).iCivRankID])
      );
   }

   public static int getRecruitCostLegacy(int iCivID) {
      return (int)(
         GameValues.advisors.RECRUIT_ADVISOR_LEGACY_COST
            * Math.max(0.0F, 1.0F + Game.getCiv(iCivID).civBonuses.AdvisorCost + GameValues.civRank.CIV_RANK_ADVISOR_COST[Game.getCiv(iCivID).iCivRankID])
      );
   }

   public final void update_ChanceOfDeathAdvisor_Administrative(int iCivID) {
      try {
         if (Game.getCiv(iCivID).advisorAdministration.sName != null
            && RulersManager.characterDies(iCivID, Game.getCiv(iCivID).advisorAdministration.iYearOfBirth)) {
            this.advisorDied(iCivID, 0);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void update_ChanceOfDeathAdvisor_Economic(int iCivID) {
      try {
         if (Game.getCiv(iCivID).advisorEconomy.sName != null && RulersManager.characterDies(iCivID, Game.getCiv(iCivID).advisorEconomy.iYearOfBirth)) {
            this.advisorDied(iCivID, 1);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void update_ChanceOfDeathAdvisor_Innovation(int iCivID) {
      try {
         if (Game.getCiv(iCivID).advisorTechnology.sName != null && RulersManager.characterDies(iCivID, Game.getCiv(iCivID).advisorTechnology.iYearOfBirth)) {
            this.advisorDied(iCivID, 2);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void update_ChanceOfDeathAdvisor_Military(int iCivID) {
      try {
         if (Game.getCiv(iCivID).advisorMilitary.sName != null && RulersManager.characterDies(iCivID, Game.getCiv(iCivID).advisorMilitary.iYearOfBirth)) {
            this.advisorDied(iCivID, 3);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void advisorDied(int iCivID, int iAdvisorTypeID) {
      switch (iAdvisorTypeID) {
         case 0:
            if (Game.getCiv(iCivID).advisorAdministration.sName != null) {
               if (iCivID == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.ADVISOR_DIED,
                           Game.lang.get("AdvisorDied") + ": " + Game.getCiv(iCivID).advisorAdministration.sName,
                           Images.council,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.RED,
                           Game.player.iCivID
                        )
                     );
                  Game.addSimpleTask(new Game.SimpleTask("rebuildCourt") {
                     @Override
                     public void update() {
                        if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                           Game.menuManager.rebuildInGame_CourtSavePos();
                           Game.menuManager.setVisibleInGame_Court(true);
                           InGame_Court.lTime = 0L;
                        }
                     }
                  });
               }

               updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, -1);
               Game.getCiv(iCivID).advisorAdministration = new Advisor();
            }

            return;
         case 1:
            if (Game.getCiv(iCivID).advisorEconomy.sName != null) {
               if (iCivID == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.ADVISOR_DIED,
                           Game.lang.get("AdvisorDied") + ": " + Game.getCiv(iCivID).advisorEconomy.sName,
                           Images.council,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.RED,
                           Game.player.iCivID
                        )
                     );
                  Game.addSimpleTask(new Game.SimpleTask("rebuildCourt") {
                     @Override
                     public void update() {
                        if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                           Game.menuManager.rebuildInGame_CourtSavePos();
                           Game.menuManager.setVisibleInGame_Court(true);
                           InGame_Court.lTime = 0L;
                        }
                     }
                  });
               }

               updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, -1);
               Game.getCiv(iCivID).advisorEconomy = new Advisor();
            }

            return;
         case 2:
            if (Game.getCiv(iCivID).advisorTechnology.sName != null) {
               if (iCivID == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.ADVISOR_DIED,
                           Game.lang.get("AdvisorDied") + ": " + Game.getCiv(iCivID).advisorTechnology.sName,
                           Images.council,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.RED,
                           Game.player.iCivID
                        )
                     );
                  Game.addSimpleTask(new Game.SimpleTask("rebuildCourt") {
                     @Override
                     public void update() {
                        if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                           Game.menuManager.rebuildInGame_CourtSavePos();
                           Game.menuManager.setVisibleInGame_Court(true);
                           InGame_Court.lTime = 0L;
                        }
                     }
                  });
               }

               updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, -1);
               Game.getCiv(iCivID).advisorTechnology = new Advisor();
            }

            return;
         case 3:
            if (Game.getCiv(iCivID).advisorMilitary.sName != null) {
               if (iCivID == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.ADVISOR_DIED,
                           Game.lang.get("AdvisorDied") + ": " + Game.getCiv(iCivID).advisorMilitary.sName,
                           Images.council,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.RED,
                           Game.player.iCivID
                        )
                     );
                  Game.addSimpleTask(new Game.SimpleTask("rebuildCourt") {
                     @Override
                     public void update() {
                        if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                           Game.menuManager.rebuildInGame_CourtSavePos();
                           Game.menuManager.setVisibleInGame_Court(true);
                           InGame_Court.lTime = 0L;
                        }
                     }
                  });
               }

               updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, -1);
               Game.getCiv(iCivID).advisorMilitary = new Advisor();
            }

            return;
      }
   }

   public final void loadAdvisors() {
      for (int i = 0; i < RulersManager.iGroupsSize; i++) {
         int numOfImages = 0;

         try {
            FileHandle tempFileT = FileManager.loadFile("game/advisors/advisors/" + CFG.getRescouresPath_Short() + i + "/numImages.txt");
            numOfImages = Integer.parseInt(tempFileT.readString());
         } catch (GdxRuntimeException var4) {
            CFG.exceptionStack(var4);
         }

         this.advisorsImagesSize.add(numOfImages);
      }
   }

   public static String getAdvisorsImgPath() {
      return !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
            && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG
         ? "advisors/"
         : "advisors2/";
   }

   public static String getAdvisorsImgPath(int iCivID) {
      return (
               Game.ideologiesManager.getIdeology(Game.getCiv(iCivID).getIdeologyID()).KingsImages
                  || !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
            )
            && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG
         ? "advisors/"
         : "advisors2/";
   }

   public int getRandomGeneralImage(int iCivID) {
      if (Game.player.iCivID != iCivID) {
         return Game.oR.nextInt(Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID));
      } else {
         List<Boolean> isUsed = new ArrayList<>();

         for (int i = 0; i < Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); i++) {
            isUsed.add(false);
         }

         int i = 0;

         for (int iSize = Game.player.civAdvisorsPool_Military.lAdvisors.size(); i < iSize; i++) {
            isUsed.set(Game.player.civAdvisorsPool_Military.lAdvisors.get(i).imageID, true);
         }

         List<Integer> out = new ArrayList<>();
         int ix = 0;

         for (int iSize = Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID); ix < iSize; ix++) {
            if (!isUsed.get(ix)) {
               out.add(ix);
            }
         }

         return out.size() == 0
            ? Game.oR.nextInt(Game.generalManager.generalsImagesSize.get(Game.getCiv(iCivID).iGroupID))
            : out.get(Game.oR.nextInt(out.size()));
      }
   }

   public int getRandomImage(int iCivID, int iAdvisorType) {
      List<Boolean> isUsed = new ArrayList<>();

      for (int i = 0; i < this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID); i++) {
         isUsed.add(false);
      }

      if (iCivID == Game.player.iCivID) {
         for (int i = 0; i < Game.player.civAdvisorsPool_Administration.lAdvisors.size(); i++) {
            try {
               isUsed.set(Game.player.civAdvisorsPool_Administration.lAdvisors.get(i).imageID, true);
            } catch (Exception var12) {
               CFG.exceptionStack(var12);
            }
         }

         for (int i = 0; i < Game.player.civAdvisorsPool_Economy.lAdvisors.size(); i++) {
            try {
               isUsed.set(Game.player.civAdvisorsPool_Economy.lAdvisors.get(i).imageID, true);
            } catch (Exception var11) {
               CFG.exceptionStack(var11);
            }
         }

         for (int i = 0; i < Game.player.civAdvisorsPool_Technology.lAdvisors.size(); i++) {
            try {
               isUsed.set(Game.player.civAdvisorsPool_Technology.lAdvisors.get(i).imageID, true);
            } catch (Exception var10) {
               CFG.exceptionStack(var10);
            }
         }
      }

      if (Game.getCiv(iCivID).advisorAdministration.sName != null) {
         try {
            isUsed.set(Game.getCiv(iCivID).advisorAdministration.imageID, true);
         } catch (Exception var9) {
            CFG.exceptionStack(var9);
         }
      }

      if (Game.getCiv(iCivID).advisorEconomy.sName != null) {
         try {
            isUsed.set(Game.getCiv(iCivID).advisorEconomy.imageID, true);
         } catch (Exception var8) {
            CFG.exceptionStack(var8);
         }
      }

      if (Game.getCiv(iCivID).advisorTechnology.sName != null) {
         try {
            isUsed.set(Game.getCiv(iCivID).advisorTechnology.imageID, true);
         } catch (Exception var7) {
            CFG.exceptionStack(var7);
         }
      }

      List<Integer> out = new ArrayList<>();
      int i = 0;

      for (int iSize = this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID); i < iSize; i++) {
         if (!isUsed.get(i)) {
            out.add(i);
         }
      }

      if (out.size() != 0) {
         return out.get(Game.oR.nextInt(out.size()));
      } else if (iCivID != Game.player.iCivID) {
         return Game.oR.nextInt(this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID));
      } else {
         for (int ix = 0; ix < this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID); ix++) {
            isUsed.set(ix, false);
         }

         if (iAdvisorType == 0) {
            i = 0;

            for (int iSizex = Game.player.civAdvisorsPool_Administration.lAdvisors.size(); i < iSizex; i++) {
               isUsed.set(Game.player.civAdvisorsPool_Administration.lAdvisors.get(i).imageID, true);
            }
         } else if (iAdvisorType == 1) {
            i = 0;

            for (int iSizex = Game.player.civAdvisorsPool_Economy.lAdvisors.size(); i < iSizex; i++) {
               isUsed.set(Game.player.civAdvisorsPool_Economy.lAdvisors.get(i).imageID, true);
            }
         } else if (iAdvisorType == 2) {
            i = 0;

            for (int iSizex = Game.player.civAdvisorsPool_Technology.lAdvisors.size(); i < iSizex; i++) {
               isUsed.set(Game.player.civAdvisorsPool_Technology.lAdvisors.get(i).imageID, true);
            }
         }

         i = 0;

         for (int iSizex = this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID); i < iSizex; i++) {
            if (!isUsed.get(i)) {
               out.add(i);
            }
         }

         return out.size() == 0 ? Game.oR.nextInt(this.advisorsImagesSize.get(Game.getCiv(iCivID).iGroupID)) : out.get(Game.oR.nextInt(out.size()));
      }
   }

   public static String getAdvisorGroupName(int iAdvisorTypeID) {
      switch (iAdvisorTypeID) {
         case 0:
            return Game.lang.get(GameValues.court.ADVISOR_NAME_ADMINISTRATIVE);
         case 1:
            return Game.lang.get(GameValues.court.ADVISOR_NAME_ECONOMIC);
         case 2:
            return Game.lang.get(GameValues.court.ADVISOR_NAME_INNOVATION);
         case 3:
            return Game.lang.get(GameValues.court.ADVISOR_NAME_MILITARY);
         default:
            return Game.lang.get(GameValues.court.ADVISOR_NAME_DIPLOMATIC);
      }
   }

   public static Advisor getAdvisor(int iAdvisorTypeID) {
      return getAdvisor(Game.player.iCivID, iAdvisorTypeID);
   }

   public static Advisor getAdvisor(int iCivID, int iAdvisorTypeID) {
      switch (iAdvisorTypeID) {
         case 0:
            return Game.getCiv(iCivID).advisorAdministration;
         case 1:
            return Game.getCiv(iCivID).advisorEconomy;
         case 2:
            return Game.getCiv(iCivID).advisorTechnology;
         default:
            return Game.getCiv(iCivID).advisorMilitary;
      }
   }

   public static boolean advisorIsRecruited(int iCivID, int iAdvisorTypeID) {
      switch (iAdvisorTypeID) {
         case 0:
            return Game.getCiv(iCivID).advisorAdministration.sName != null;
         case 1:
            return Game.getCiv(iCivID).advisorEconomy.sName != null;
         case 2:
            return Game.getCiv(iCivID).advisorTechnology.sName != null;
         default:
            return Game.getCiv(iCivID).advisorMilitary.sName != null;
      }
   }

   public static final void updateCivBonuses(Advisor nAdvisor, int iCivID, int mod) {
      CivilizationBonuses var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.TaxEfficiency = var10000.TaxEfficiency + nAdvisor.TaxEfficiency * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ProvinceMaintenance = var10000.ProvinceMaintenance + nAdvisor.ProvinceMaintenance * mod;
      if (nAdvisor.GrowthRate != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.GrowthRate = var10000.GrowthRate + nAdvisor.GrowthRate * mod;
         Game.getCiv(iCivID).updateResearchPerMonth();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ProductionEfficiency = var10000.ProductionEfficiency + nAdvisor.ProductionEfficiency * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncomeProduction = var10000.IncomeProduction + nAdvisor.IncomeProduction * mod;
      if (nAdvisor.MonthlyLegacy != 0.0F) {
         Game.gameThread.addCivUpdateLegacyPerMonth(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MonthlyLegacy = var10000.MonthlyLegacy + nAdvisor.MonthlyLegacy * mod;
      if (nAdvisor.MaxManpower != 0.0F) {
         Game.gameThreadTurns.addCivUpdateMaxManpower(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MaxManpower = var10000.MaxManpower + nAdvisor.MaxManpower * mod;
      if (nAdvisor.ArmyMaintenance != 0.0F) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.ArmyMaintenance = var10000.ArmyMaintenance + nAdvisor.ArmyMaintenance * mod;
         Game.gameThread.addCivUpdateArmyMaintenance(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitmentTime = var10000.RecruitmentTime + nAdvisor.RecruitmentTime * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.RecruitArmyCost = var10000.RecruitArmyCost + nAdvisor.RecruitArmyCost * mod;
      if (nAdvisor.Research != 0.0F) {
         Game.gameThread.addCivUpdateResearchPerMonth(iCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ResearchPoints = var10000.ResearchPoints + nAdvisor.Research * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ConstructionCost = var10000.ConstructionCost + nAdvisor.ConstructionCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.AdministrationBuildingsCost = var10000.AdministrationBuildingsCost + nAdvisor.AdministrationBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.EconomyBuildingsCost = var10000.EconomyBuildingsCost + nAdvisor.EconomyBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MilitaryBuildingsCost = var10000.MilitaryBuildingsCost + nAdvisor.MilitaryBuildingsCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ConstructionTime = var10000.ConstructionTime + nAdvisor.ConstructionTime * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.InvestInEconomyCost = var10000.InvestInEconomyCost + nAdvisor.InvestInEconomyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseManpowerCost = var10000.IncreaseManpowerCost + nAdvisor.IncreaseManpowerCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseTaxEfficiencyCost = var10000.IncreaseTaxEfficiencyCost + nAdvisor.IncreaseTaxEfficiencyCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.IncreaseGrowthRateCost = var10000.IncreaseGrowthRateCost + nAdvisor.IncreaseGrowthRateCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.DevelopInfrastructureCost = var10000.DevelopInfrastructureCost + nAdvisor.DevelopInfrastructureCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.GeneralAttack = (int)(var10000.GeneralAttack + nAdvisor.GeneralAttack * mod);
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.GeneralDefense = (int)(var10000.GeneralDefense + nAdvisor.GeneralDefense * mod);
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.UnitsAttack = (int)(var10000.UnitsAttack + nAdvisor.UnitsAttack * mod);
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.UnitsDefense = (int)(var10000.UnitsDefense + nAdvisor.UnitsDefense * mod);
      if (nAdvisor.RegimentsLimit != 0) {
         var10000 = Game.getCiv(iCivID).civBonuses;
         var10000.RegimentsLimit = var10000.RegimentsLimit + nAdvisor.RegimentsLimit * mod;
         Game.getCiv(iCivID).updateRegimentsLimit();
      }

      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.MaxMorale = var10000.MaxMorale + nAdvisor.MaxMorale * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ArmyMovementSpeed = var10000.ArmyMovementSpeed + nAdvisor.ArmyMovementSpeed * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.SiegeEffectiveness = var10000.SiegeEffectiveness + nAdvisor.SiegeEffectiveness * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ImproveRelationsModifier = var10000.ImproveRelationsModifier + nAdvisor.ImproveRelationsModifier * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.LoanInterest = var10000.LoanInterest + nAdvisor.LoanInterest * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.CoreCost = var10000.CoreCost + nAdvisor.CoreCost * mod;
      var10000 = Game.getCiv(iCivID).civBonuses;
      var10000.ReligionCost = var10000.ReligionCost + nAdvisor.ReligionCost * mod;
      Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
   }

   public static final int getAdvisorMaxLevel(int iCivID) {
      return GameValues.advisors.ADVISOR_MAX_LVL + Game.getCiv(iCivID).civBonuses.AdvisorMaxLevel;
   }

   public static final int getAdvisorPromoteCost(int iCivID, int iLevel) {
      return (int)(GameValues.advisors.ADVISOR_PROMOTE_COST_PER_LEVEL * iLevel);
   }

   public static final int getAdvisorPromoteCostLegacy(int iCivID, int iLevel) {
      return (int)(GameValues.advisors.ADVISOR_PROMOTE_COST_LEGACY_PER_LEVEL * iLevel);
   }

   public static final boolean promoteAdvisor(int iCivID, int iAdvisorTypeID, boolean free) {
      if (getAdvisor(iCivID, iAdvisorTypeID) != null
         && (getAdvisor(iCivID, iAdvisorTypeID).iLevel < getAdvisorMaxLevel(iCivID) || free)
         && advisorIsRecruited(iCivID, iAdvisorTypeID)) {
         if (!free) {
            if (Game.getCiv(iCivID).fGold < getAdvisorPromoteCost(iCivID, getAdvisor(iCivID, iAdvisorTypeID).iLevel)) {
               return false;
            }

            if (Game.getCiv(iCivID).fLegacy < getAdvisorPromoteCostLegacy(iCivID, getAdvisor(iCivID, iAdvisorTypeID).iLevel)) {
               return false;
            }

            Civilization var10000 = Game.getCiv(iCivID);
            var10000.fGold = var10000.fGold - getAdvisorPromoteCost(iCivID, getAdvisor(iCivID, iAdvisorTypeID).iLevel);
            var10000 = Game.getCiv(iCivID);
            var10000.fLegacy = var10000.fLegacy - getAdvisorPromoteCostLegacy(iCivID, getAdvisor(iCivID, iAdvisorTypeID).iLevel);
         }

         updateCivBonuses(getAdvisor(iCivID, iAdvisorTypeID), iCivID, -1);
         getAdvisor(iCivID, iAdvisorTypeID).iLevel++;

         try {
            float mod = 1.0F + GameValues.advisors.ADVISOR_BONUSES_UPGRADE_PER_LEVEL;
            getAdvisor(iCivID, iAdvisorTypeID).TaxEfficiency *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ProvinceMaintenance *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).GrowthRate *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ProductionEfficiency *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).IncomeProduction *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).MonthlyLegacy *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).MaxManpower *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ArmyMaintenance *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).RecruitmentTime *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).RecruitArmyCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).Research *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ConstructionCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).AdministrationBuildingsCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).EconomyBuildingsCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).MilitaryBuildingsCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ConstructionTime *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).DevelopInfrastructureCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).IncreaseTaxEfficiencyCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).IncreaseGrowthRateCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).InvestInEconomyCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).IncreaseManpowerCost *= mod;
            if (getAdvisor(iCivID, iAdvisorTypeID).GeneralAttack != 0.0F) {
               getAdvisor(iCivID, iAdvisorTypeID).GeneralAttack = (int)Math.max(
                  (double)(getAdvisor(iCivID, iAdvisorTypeID).GeneralAttack + 1.0F), Math.ceil(getAdvisor(iCivID, iAdvisorTypeID).GeneralAttack * mod)
               );
            }

            if (getAdvisor(iCivID, iAdvisorTypeID).GeneralDefense != 0.0F) {
               getAdvisor(iCivID, iAdvisorTypeID).GeneralDefense = (int)Math.max(
                  (double)(getAdvisor(iCivID, iAdvisorTypeID).GeneralDefense + 1.0F), Math.ceil(getAdvisor(iCivID, iAdvisorTypeID).GeneralDefense * mod)
               );
            }

            if (getAdvisor(iCivID, iAdvisorTypeID).UnitsAttack != 0.0F) {
               getAdvisor(iCivID, iAdvisorTypeID).UnitsAttack = (int)Math.max(
                  (double)(getAdvisor(iCivID, iAdvisorTypeID).UnitsAttack + 1.0F), Math.ceil(getAdvisor(iCivID, iAdvisorTypeID).UnitsAttack * mod)
               );
            }

            if (getAdvisor(iCivID, iAdvisorTypeID).UnitsDefense != 0.0F) {
               getAdvisor(iCivID, iAdvisorTypeID).UnitsDefense = (int)Math.max(
                  (double)(getAdvisor(iCivID, iAdvisorTypeID).UnitsDefense + 1.0F), Math.ceil(getAdvisor(iCivID, iAdvisorTypeID).UnitsDefense * mod)
               );
            }

            if (getAdvisor(iCivID, iAdvisorTypeID).RegimentsLimit != 0) {
               getAdvisor(iCivID, iAdvisorTypeID).RegimentsLimit = (int)Math.max(
                  (double)(getAdvisor(iCivID, iAdvisorTypeID).RegimentsLimit + 1), Math.ceil(getAdvisor(iCivID, iAdvisorTypeID).RegimentsLimit * mod)
               );
            }

            getAdvisor(iCivID, iAdvisorTypeID).MaxMorale *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ArmyMovementSpeed *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).SiegeEffectiveness *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ImproveRelationsModifier *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).LoanInterest *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).CoreCost *= mod;
            getAdvisor(iCivID, iAdvisorTypeID).ReligionCost *= mod;
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }

         updateCivBonuses(getAdvisor(iCivID, iAdvisorTypeID), iCivID, 1);
         return true;
      } else {
         return false;
      }
   }
}
