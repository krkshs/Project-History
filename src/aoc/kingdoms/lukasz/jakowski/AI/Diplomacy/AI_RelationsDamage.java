/*
 * Decompiled with CFR 0.152.
 */
package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;

public class AI_RelationsDamage {
    public static void updateRelations_Damage() {
        try {
            int i;
            for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE; i < Game.player.iCivID; i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE) {
                if (Game.getCiv(i).getNumOfProvinces() <= 0) continue;
                AI_RelationsDamage.updateRelations_Damage(i);
            }
            if (i == Game.player.iCivID) {
                i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE;
            }
            while (i < Game.getCivsSize()) {
                if (Game.getCiv(i).getNumOfProvinces() > 0) {
                    AI_RelationsDamage.updateRelations_Damage(i);
                }
                i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RELATIONS_DAMAGE;
            }
        }
        catch (Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    public static void updateRelations_Damage(int civID) {
        if (Game.getCiv((int)civID).diplomacy.iImprovingRelationsSize > 0 && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_CLEAR_DECREASE_RELATIONS_CHANCE) {
            Game.getCiv((int)civID).diplomacy.clearDamageRelations_AI(civID);
        }
        if (Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_PRIORITIZE_TRIBAL && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_PRIORITIZE_TRIBAL_CHANCE && !Game.ideologiesManager.getIdeology((int)Game.getCiv((int)civID).getIdeologyID()).TRIBAL) {
            boolean added = false;
            for (int i = 0; i < Game.getCiv((int)civID).civNeighbors.civsSize; ++i) {
                if (!Game.ideologiesManager.getIdeology((int)Game.getCiv((int)Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID).getIdeologyID()).TRIBAL || Game.getCiv(Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID).getNumOfProvinces() <= 0 || DiplomacyManager.isAlly(civID, Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID) || Game.getCiv(civID).getResearchedTechnologies() <= Game.getCiv(Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID).getResearchedTechnologies() || Game.getCiv(civID).getNumOfProvinces() <= Game.getCiv(Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID).getNumOfProvinces() || !Game.getCiv((int)civID).diplomacy.addDamageRelations(civID, Game.getCiv((int)civID).civNeighbors.civs.get((int)i).civID)) continue;
                added = true;
            }
            if (added) {
                return;
            }
        }
        if (Game.getCiv((int)civID).diplomacy.iDamagingRelationsSize < Game.aiValuesDiplomacy.AI_LIMIT_DECREASING_RELATIONS) {
            int a;
            int i;
            int type = Game.oR.nextInt(3);
            switch (type) {
                case 0: {
                    float value = Game.getCiv((int)civID).fTotalIncomePerMonth * Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_INCOME_MODIFIER;
                    for (i = 1; i < civID; ++i) {
                        AI_RelationsDamage.buildScore(civID, i, value);
                    }
                    for (i = civID + 1; i < Game.getCivsSize(); ++i) {
                        AI_RelationsDamage.buildScore(civID, i, value);
                    }
                    break;
                }
                case 1: {
                    int i2;
                    double value = Game.getCiv((int)civID).fManpowerMax * (double)Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_MANPOWER_MODIFIER;
                    for (i2 = 1; i2 < civID; ++i2) {
                        AI_RelationsDamage.buildScoreManpower(civID, i2, value);
                    }
                    for (i2 = civID + 1; i2 < Game.getCivsSize(); ++i2) {
                        AI_RelationsDamage.buildScoreManpower(civID, i2, value);
                    }
                    break;
                }
                case 2: {
                    float value = (float)Game.getCiv((int)civID).iRegiments * Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_REGIMENTS_MODIFIER;
                    for (i = 1; i < civID; ++i) {
                        AI_RelationsDamage.buildScoreRegiments(civID, i, value);
                    }
                    for (i = civID + 1; i < Game.getCivsSize(); ++i) {
                        AI_RelationsDamage.buildScoreRegiments(civID, i, value);
                    }
                    break;
                }
            }
            ArrayList<Integer> civs = new ArrayList<Integer>();
            Game.getCiv((int)civID).aiScore = 9.999877E7f;
            for (i = 0; i < Game.getCiv((int)civID).diplomacy.iVassalsSize; ++i) {
                Game.getCiv((int)Game.getCiv((int)civID).diplomacy.lVassals.get((int)i).c).aiScore = 9.999997E7f;
            }
            for (a = Game.aiValuesDiplomacy.AI_DAMAGE_RELATIONS_NUM_TO_CHOOSE_FROM - 1; a >= 0; --a) {
                int bestID = 1;
                for (int i3 = 2; i3 < Game.getCivsSize(); ++i3) {
                    if (!(Game.getCiv((int)bestID).aiScore > Game.getCiv((int)i3).aiScore) || Game.getCiv((int)civID).diplomacy.isDamagingRelations(i3) || Game.getCiv((int)civID).diplomacy.isImprovingRelations(i3) || !(Game.getCiv((int)civID).diplomacy.getRelation(i3) > GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX)) continue;
                    bestID = i3;
                }
                if (bestID == civID) break;
                if (Game.getCiv((int)civID).diplomacy.isDamagingRelations(bestID) || Game.getCiv((int)civID).diplomacy.isImprovingRelations(bestID) || Game.getCiv((int)civID).diplomacy.getRelation(bestID) < GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX) {
                    if (Game.getCiv((int)bestID).aiScore == 9.999832E7f) break;
                    Game.getCiv((int)bestID).aiScore = 9.999832E7f;
                    continue;
                }
                if (Game.getCiv(bestID).getNumOfProvinces() <= 0 || !(Game.getCiv((int)bestID).aiScore < 999800.0f)) break;
                civs.add(bestID);
                Game.getCiv((int)bestID).aiScore = 9.999877E7f;
            }
            for (a = Math.min(civs.size(), Game.aiValuesDiplomacy.AI_LIMIT_DECREASING_RELATIONS - Game.getCiv((int)civID).diplomacy.iDamagingRelationsSize) - 1; a >= 0; --a) {
                int rand = Game.oR.nextInt(civs.size());
                Game.getCiv((int)civID).diplomacy.addDamageRelations(civID, (Integer)civs.get(rand));
                civs.remove(rand);
            }
            civs.clear();
        }
    }

    public static void buildScore(int civID, int civB, float value) {
        Game.getCiv((int)civB).aiScore = Game.getCiv(civB).getNumOfProvinces() > 0 ? (Game.getCiv((int)civB).fTotalIncomePerMonth < value ? Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID()) : 999876.0f) : 999876.0f;
    }

    public static void buildScoreManpower(int civID, int civB, double value) {
        Game.getCiv((int)civB).aiScore = Game.getCiv(civB).getNumOfProvinces() > 0 ? (Game.getCiv((int)civB).fManpowerMax < value ? Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID()) : 999876.0f) : 999876.0f;
    }

    public static void buildScoreRegiments(int civID, int civB, float value) {
        Game.getCiv((int)civB).aiScore = Game.getCiv(civB).getNumOfProvinces() > 0 ? ((float)Game.getCiv((int)civB).iRegiments < value ? Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(civB).getCapitalProvinceID()) : 999876.0f) : 999876.0f;
    }

    public static boolean differentGovernment(int civA, int civB) {
        return Game.getCiv(civA).getIdeologyID() != Game.getCiv(civB).getIdeologyID();
    }

    public static boolean differentReligion(int civA, int civB) {
        return Game.getCiv(civA).getReligionID() != Game.getCiv(civB).getReligionID();
    }
}
