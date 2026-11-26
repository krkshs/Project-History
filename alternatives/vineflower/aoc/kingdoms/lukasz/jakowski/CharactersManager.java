package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.map.advisors.Advisor;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.map.civilization.CivilizationAdvisorsPool;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;

public class CharactersManager {
   public static final void loadGeneral(int iCivID, String fileName, int nAttack, int nDefense) {
      try {
         if (FileManager.loadFile("game/characters/" + fileName + ".json").exists()) {
            FileHandle fileList = FileManager.loadFile("game/characters/" + fileName + ".json");
            Json json = new Json();

            for (JsonValue jValue : json.fromJson(ArrayList.class, fileList)) {
               try {
                  CharactersManager.Characters tData = json.readValue(CharactersManager.Characters.class, jValue);
                  if (tData != null && tData.ImageID != null && tData.ImageID.length() > 0) {
                     int bornYear = tData.BornYear;
                     if (Game_Calendar.currentYear - tData.BornYear < 10 || Game_Calendar.currentYear - tData.BornYear > 99) {
                        bornYear = Game_Calendar.currentYear
                           - GameValues.generals.GENERAL_YEARS_OLD_MIN
                           - Game.oR.nextInt(GameValues.generals.GENERAL_YEARS_OLD_RANDOM);
                     }

                     if (nAttack != -99) {
                        tData.Attack = nAttack;
                     }

                     if (nDefense != -99) {
                        tData.Defense = nDefense;
                     }

                     ArmyGeneral armyGeneral = new ArmyGeneral(
                        CFG.checkName(tData.Name),
                        0,
                        tData.Attack >= 0
                           ? tData.Attack
                           : GameValues.generals.GENERAL_ATTACK_BASE_VALUE
                              + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM)
                              + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM2),
                        tData.Defense >= 0
                           ? tData.Defense
                           : GameValues.generals.GENERAL_DEFENSE_BASE_VALUE
                              + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM)
                              + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM2),
                        bornYear,
                        iCivID,
                        tData.ImageID
                     );
                     armyGeneral.d = tData.BornDay;
                     armyGeneral.m = tData.BornMonth;
                     Game.getCiv(iCivID).addGeneral(armyGeneral);
                     break;
                  }
               } catch (Exception var12) {
                  CFG.exceptionStack(var12);
               }
            }
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }
   }

   public static final void loadAdvisor(int iCivID, String fileName, int iAdvisorType) {
      try {
         if (FileManager.loadFile("game/characters/" + fileName + ".json").exists()) {
            FileHandle fileList = FileManager.loadFile("game/characters/" + fileName + ".json");
            Json json = new Json();

            for (JsonValue jValue : json.fromJson(ArrayList.class, fileList)) {
               try {
                  CharactersManager.Characters tData = json.readValue(CharactersManager.Characters.class, jValue);
                  if (tData != null && tData.ImageID != null && tData.ImageID.length() > 0) {
                     int bornYear = tData.BornYear;
                     if (Game_Calendar.currentYear - tData.BornYear < 10 || Game_Calendar.currentYear - tData.BornYear > 99) {
                        bornYear = Game_Calendar.currentYear
                           - GameValues.advisors.ADVISOR_YEARS_OLD_MIN
                           - Game.oR.nextInt(Math.max(1, GameValues.advisors.ADVISOR_YEARS_OLD_RANDOM));
                     }

                     int advIMG;
                     if (iAdvisorType == 3) {
                        advIMG = Game.advisorManager.getRandomGeneralImage(iCivID);
                     } else {
                        advIMG = Game.advisorManager.getRandomImage(iCivID, iAdvisorType);
                     }

                     Advisor advisor = new Advisor(CFG.checkName(tData.Name), advIMG, bornYear, tData.ImageID);
                     advisor = CivilizationAdvisorsPool.buildAdvisorBonuses(advisor, iAdvisorType);
                     advisor.iDayOfBirth = tData.BornDay;
                     advisor.iMonthOfBirth = tData.BornMonth;
                     switch (iAdvisorType) {
                        case 0:
                           if (Game.getCiv(iCivID).advisorAdministration.sName != null) {
                              AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, -1);
                           }

                           Game.getCiv(iCivID).advisorAdministration = advisor;
                           AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, 1);
                           return;
                        case 1:
                           if (Game.getCiv(iCivID).advisorEconomy.sName != null) {
                              AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, -1);
                           }

                           Game.getCiv(iCivID).advisorEconomy = advisor;
                           AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, 1);
                           return;
                        case 2:
                           if (Game.getCiv(iCivID).advisorTechnology.sName != null) {
                              AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, -1);
                           }

                           Game.getCiv(iCivID).advisorTechnology = advisor;
                           AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, 1);
                           return;
                        default:
                           if (Game.getCiv(iCivID).advisorMilitary.sName != null) {
                              AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, -1);
                           }

                           Game.getCiv(iCivID).advisorMilitary = advisor;
                           AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, 1);
                           return;
                     }
                  }
               } catch (Exception var12) {
                  CFG.exceptionStack(var12);
               }
            }
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }
   }

   public static class Characters {
      public String Name;
      public String ImageID;
      public int BornDay;
      public int BornMonth;
      public int BornYear;
      public int Attack;
      public int Defense;
   }

   public static class ConfigCharactersData {
      public String Age_of_History;
      public ArrayList Rulers;
   }

   public static class ScenarioCharacters {
      public String CivTAG;
      public String Administrative;
      public String Economic;
      public String Innovation;
      public String Military;
      public String[] Generals;
   }
}
