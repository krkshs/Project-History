/*
 * Decompiled with CFR 0.152.
 */
package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
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
        block8: {
            try {
                if (!FileManager.loadFile("game/characters/" + fileName + ".json").exists()) break block8;
                FileHandle fileList = FileManager.loadFile("game/characters/" + fileName + ".json");
                Json json = new Json();
                ArrayList tempArrayData = json.fromJson(ArrayList.class, fileList);
                for (JsonValue jValue : tempArrayData) {
                    try {
                        Characters tData = json.readValue(Characters.class, jValue);
                        if (tData == null || tData.ImageID == null || tData.ImageID.length() <= 0) continue;
                        int bornYear = tData.BornYear;
                        if (Game_Calendar.currentYear - tData.BornYear < 10 || Game_Calendar.currentYear - tData.BornYear > 99) {
                            bornYear = Game_Calendar.currentYear - GameValues.generals.GENERAL_YEARS_OLD_MIN - Game.oR.nextInt(GameValues.generals.GENERAL_YEARS_OLD_RANDOM);
                        }
                        if (nAttack != -99) {
                            tData.Attack = nAttack;
                        }
                        if (nDefense != -99) {
                            tData.Defense = nDefense;
                        }
                        ArmyGeneral armyGeneral = new ArmyGeneral(CFG.checkName(tData.Name), 0, tData.Attack >= 0 ? tData.Attack : GameValues.generals.GENERAL_ATTACK_BASE_VALUE + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM) + Game.oR.nextInt(GameValues.generals.GENERAL_ATTACK_RANDOM2), tData.Defense >= 0 ? tData.Defense : GameValues.generals.GENERAL_DEFENSE_BASE_VALUE + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM) + Game.oR.nextInt(GameValues.generals.GENERAL_DEFENSE_RANDOM2), bornYear, iCivID, tData.ImageID);
                        armyGeneral.d = tData.BornDay;
                        armyGeneral.m = tData.BornMonth;
                        Game.getCiv(iCivID).addGeneral(armyGeneral);
                        break;
                    }
                    catch (Exception ex) {
                        CFG.exceptionStack(ex);
                    }
                }
            }
            catch (Exception ex) {
                CFG.exceptionStack(ex);
            }
        }
    }

    public static final void loadAdvisor(int iCivID, String fileName, int iAdvisorType) {
        block15: {
            try {
                if (!FileManager.loadFile("game/characters/" + fileName + ".json").exists()) break block15;
                FileHandle fileList = FileManager.loadFile("game/characters/" + fileName + ".json");
                Json json = new Json();
                ArrayList tempArrayData = json.fromJson(ArrayList.class, fileList);
                for (JsonValue jValue : tempArrayData) {
                    try {
                        Characters tData = json.readValue(Characters.class, jValue);
                        if (tData == null || tData.ImageID == null || tData.ImageID.length() <= 0) continue;
                        int bornYear = tData.BornYear;
                        if (Game_Calendar.currentYear - tData.BornYear < 10 || Game_Calendar.currentYear - tData.BornYear > 99) {
                            bornYear = Game_Calendar.currentYear - GameValues.advisors.ADVISOR_YEARS_OLD_MIN - Game.oR.nextInt(Math.max(1, GameValues.advisors.ADVISOR_YEARS_OLD_RANDOM));
                        }
                        int advIMG = iAdvisorType == 3 ? Game.advisorManager.getRandomGeneralImage(iCivID) : Game.advisorManager.getRandomImage(iCivID, iAdvisorType);
                        Advisor advisor = new Advisor(CFG.checkName(tData.Name), advIMG, bornYear, tData.ImageID);
                        advisor = CivilizationAdvisorsPool.buildAdvisorBonuses(advisor, iAdvisorType);
                        advisor.iDayOfBirth = tData.BornDay;
                        advisor.iMonthOfBirth = tData.BornMonth;
                        switch (iAdvisorType) {
                            case 0: {
                                if (Game.getCiv((int)iCivID).advisorAdministration.sName != null) {
                                    AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorAdministration, iCivID, -1);
                                }
                                Game.getCiv((int)iCivID).advisorAdministration = advisor;
                                AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorAdministration, iCivID, 1);
                                break;
                            }
                            case 1: {
                                if (Game.getCiv((int)iCivID).advisorEconomy.sName != null) {
                                    AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorEconomy, iCivID, -1);
                                }
                                Game.getCiv((int)iCivID).advisorEconomy = advisor;
                                AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorEconomy, iCivID, 1);
                                break;
                            }
                            case 2: {
                                if (Game.getCiv((int)iCivID).advisorTechnology.sName != null) {
                                    AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorTechnology, iCivID, -1);
                                }
                                Game.getCiv((int)iCivID).advisorTechnology = advisor;
                                AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorTechnology, iCivID, 1);
                                break;
                            }
                            default: {
                                if (Game.getCiv((int)iCivID).advisorMilitary.sName != null) {
                                    AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorMilitary, iCivID, -1);
                                }
                                Game.getCiv((int)iCivID).advisorMilitary = advisor;
                                AdvisorManager.updateCivBonuses(Game.getCiv((int)iCivID).advisorMilitary, iCivID, 1);
                                break;
                            }
                        }
                        break;
                    }
                    catch (Exception ex) {
                        CFG.exceptionStack(ex);
                    }
                }
            }
            catch (Exception ex) {
                CFG.exceptionStack(ex);
            }
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

    public static class ScenarioCharacters {
        public String CivTAG;
        public String Administrative;
        public String Economic;
        public String Innovation;
        public String Military;
        public String[] Generals;
    }

    public static class ConfigCharactersData {
        public String Age_of_History;
        public ArrayList Rulers;
    }
}
