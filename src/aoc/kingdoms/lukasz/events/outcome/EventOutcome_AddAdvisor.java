package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationAdvisorsPool;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AddAdvisor extends EventOutcome {
   public int advisorTypeID;

   public EventOutcome_AddAdvisor(int advisorTypeID) {
      this.advisorTypeID = advisorTypeID;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         switch (this.advisorTypeID) {
            case 0:
               if (Game.getCiv(iCivID).advisorAdministration.sName != null) {
                  AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, -1);
               }

               Game.getCiv(iCivID).advisorAdministration = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, this.advisorTypeID);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, 1);
               break;
            case 1:
               if (Game.getCiv(iCivID).advisorEconomy.sName != null) {
                  AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, -1);
               }

               Game.getCiv(iCivID).advisorEconomy = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, this.advisorTypeID);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, 1);
               break;
            case 2:
               if (Game.getCiv(iCivID).advisorTechnology.sName != null) {
                  AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, -1);
               }

               Game.getCiv(iCivID).advisorTechnology = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, this.advisorTypeID);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, 1);
               break;
            default:
               if (Game.getCiv(iCivID).advisorMilitary.sName != null) {
                  AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, -1);
               }

               Game.getCiv(iCivID).advisorMilitary = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, this.advisorTypeID);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, 1);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("HireAdvisor") + ": ";
   }

   @Override
   public String getStringRight() {
      return "" + AdvisorManager.getAdvisorGroupName(this.advisorTypeID);
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
