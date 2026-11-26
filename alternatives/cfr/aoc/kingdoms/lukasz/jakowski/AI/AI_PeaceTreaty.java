/*
 * Decompiled with CFR 0.152.
 */
package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageTruce;
import aoc.kingdoms.lukasz.map.PeaceTreaty;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarManager;
import java.util.ArrayList;
import java.util.List;

public class AI_PeaceTreaty {
    public static void peaceTreaty(String warKey, int civID, int lostID) {
        if (civID == Game.player.iCivID) {
            return;
        }
        PeaceTreaty peaceTreaty = new PeaceTreaty(civID, warKey, false);
        boolean conquerVassal = false;
        boolean isCoalition = false;
        try {
            War war = WarManager.lWars.get(warKey);
            conquerVassal = war.conquerVassal;
            isCoalition = war.isCoalition;
        }
        catch (Exception ex) {
            CFG.exceptionStack(ex);
        }
        try {
            int[] order;
            ArrayList<Integer> provinceToTake = new ArrayList<Integer>();
            Civilization civ = Game.getCiv(civID);
            Civilization civLost = Game.getCiv(lostID);
            for (int i = 0; i < civLost.getNumOfProvinces(); ++i) {
                provinceToTake.add(civLost.getProvinceID(i));
                AI_PeaceTreaty.buildProvinceScore(civID, civLost.getProvinceID(i), lostID);
            }
            int[] nArray = order = Game.oR.nextInt(Game.ideologiesManager.getIdeology((int)civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE + Game.ideologiesManager.getIdeology((int)civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE2) < Game.ideologiesManager.getIdeology((int)civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE ? Game.ideologiesManager.getIdeology((int)civ.getIdeologyID()).AI_PEACE_ORDER : Game.ideologiesManager.getIdeology((int)civ.getIdeologyID()).AI_PEACE_ORDER2;
            if (isCoalition) {
                order = GameValues.peace.AI_PEACE_DEMAND_COALITION;
            } else if (conquerVassal) {
                order = GameValues.peace.AI_PEACE_DEMAND_ANNEX_VASSAL_PEACE_ORDER;
            } else if (lostID == Game.player.iCivID && GameValues.peace.AI_PEACE_DEMAND_VASSALIZE_PLAYER && !Game.player.playerData.wasVassalized && civLost.getPuppetOfCivID() != civID && peaceTreaty.fScore >= peaceTreaty.getScore_DemandVassalization()) {
                Game.player.playerData.wasVassalized = true;
                order = GameValues.peace.AI_PEACE_DEMAND_VASSALIZE_PLAYER_PEACE_ORDER;
            }
            block19: for (int a = 0; a < order.length; ++a) {
                switch (order[a]) {
                    case 0: {
                        int i;
                        while (peaceTreaty.fScore > 0.0f && provinceToTake.size() > 0) {
                            int bestID = 0;
                            for (i = provinceToTake.size() - 1; i >= 0; --i) {
                                if (Game.getProvince((int)((Integer)provinceToTake.get((int)bestID)).intValue()).aiPeaceScore >= Game.getProvince((int)((Integer)provinceToTake.get((int)i)).intValue()).aiPeaceScore) continue;
                                bestID = i;
                            }
                            if (!peaceTreaty.addProvince((Integer)provinceToTake.get(bestID))) continue block19;
                            Game.getProvince((int)((Integer)provinceToTake.get((int)bestID)).intValue()).aiPeaceCivID = civID;
                            for (int j = 0; j < Game.getProvince((Integer)provinceToTake.get(bestID)).getNeighboringProvincesSize(); ++j) {
                                AI_PeaceTreaty.buildProvinceScore(civID, Game.getProvince((Integer)provinceToTake.get(bestID)).getNeighboringProvinces(j), lostID);
                            }
                            provinceToTake.remove(bestID);
                        }
                        continue block19;
                    }
                    case 1: {
                        if (!(peaceTreaty.fScore >= peaceTreaty.getScore_DemandVassalization())) continue block19;
                        peaceTreaty.demandVassalization();
                        continue block19;
                    }
                    case 2: {
                        int i;
                        if (civLost.diplomacy.iVassalsSize <= 0) continue block19;
                        try {
                            ArrayList<Integer> possibleVassals = new ArrayList<Integer>();
                            for (i = 0; i < civLost.diplomacy.iVassalsSize; ++i) {
                                possibleVassals.add(civLost.diplomacy.lVassals.get((int)i).c);
                            }
                            while (possibleVassals.size() > 0) {
                                int bestID = 0;
                                float bestDistance = Game.getDistanceFromProvinceToProvince(civ.getCapitalProvinceID(), Game.getCiv((Integer)possibleVassals.get(bestID)).getCapitalProvinceID());
                                for (int i2 = possibleVassals.size() - 1; i2 > 0; --i2) {
                                    if (!(bestDistance > Game.getDistanceFromProvinceToProvince(civ.getCapitalProvinceID(), Game.getCiv((Integer)possibleVassals.get(i2)).getCapitalProvinceID()))) continue;
                                    bestDistance = Game.getDistanceFromProvinceToProvince(civ.getCapitalProvinceID(), Game.getCiv((Integer)possibleVassals.get(i2)).getCapitalProvinceID());
                                    bestID = i2;
                                }
                                peaceTreaty.demandSubjectTransfer((Integer)possibleVassals.get(bestID));
                                possibleVassals.remove(bestID);
                            }
                            possibleVassals.clear();
                        }
                        catch (Exception ex) {
                            CFG.exceptionStack(ex);
                        }
                        continue block19;
                    }
                    case 3: {
                        if (civ.getReligionID() == civLost.getReligionID() || !(peaceTreaty.fScore >= peaceTreaty.getScore_DemandReligionConversion())) continue block19;
                        peaceTreaty.demandReligionConversion();
                        continue block19;
                    }
                    case 4: {
                        if (civ.getIdeologyID() == civLost.getIdeologyID() || !(peaceTreaty.fScore >= peaceTreaty.getScore_DemandGovernmentChange())) continue block19;
                        peaceTreaty.demandGovernmentChange();
                        continue block19;
                    }
                    case 5: {
                        for (int i = 0; i < GameValues.peace.PEACE_GOLD_MAX && peaceTreaty.demandGold(); ++i) {
                        }
                        continue block19;
                    }
                    case 6: {
                        int i;
                        List<Integer> liberateCivs = PeaceTreaty.getCivsPossibleToLiberate(lostID);
                        for (i = 0; i < liberateCivs.size(); ++i) {
                            peaceTreaty.demandLiberateCiv(lostID, liberateCivs.get(i));
                        }
                        continue block19;
                    }
                    case 7: {
                        if (!(peaceTreaty.fScore >= peaceTreaty.getScore_DemandWarReparations())) continue block19;
                        peaceTreaty.demandWarReparations();
                        continue block19;
                    }
                    case 8: {
                        if (!(peaceTreaty.fScore >= peaceTreaty.getScore_DemandHumiliate())) continue block19;
                        peaceTreaty.demandHumiliate();
                        continue block19;
                    }
                    case 9: {
                        peaceTreaty.demandReturnProvinces = true;
                    }
                }
            }
            order = null;
        }
        catch (Exception ex) {
            CFG.exceptionStack(ex);
        }
        peaceTreaty.enforceDemands(false);
        if (lostID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageTruce(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS, peaceTreaty));
        }
    }

    public static void buildProvinceScore(int civID, int provinceID, int lostID) {
        Province province = Game.getProvince(provinceID);
        province.aiPeaceScore = 0;
        int neighboringPerc = 0;
        float neighboringPerc_Total = 0.0f;
        for (int i = 0; i < province.getNeighboringProvincesSize(); ++i) {
            if (Game.getProvince(province.getNeighboringProvinces(i)).getWasteland() >= 0) continue;
            if (Game.getProvince((int)province.getNeighboringProvinces((int)i)).aiPeaceCivID == civID || Game.getProvince((int)province.getNeighboringProvinces((int)i)).aiPeaceCivID == Game.getProvince(province.getNeighboringProvinces(i)).getCivID()) {
                neighboringPerc_Total += 1.0f;
            }
            if (Game.getProvince((int)province.getNeighboringProvinces((int)i)).aiPeaceCivID == lostID) {
                province.aiPeaceScore = (int)((float)province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_LOSER_SCORE);
            }
            if (Game.getProvince((int)province.getNeighboringProvinces((int)i)).aiPeaceCivID != civID) continue;
            province.aiPeaceScore = (int)((float)province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_SCORE);
            ++neighboringPerc;
        }
        if (neighboringPerc_Total > 0.0f) {
            province.aiPeaceScore = (int)((float)province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_PERC * ((float)neighboringPerc / neighboringPerc_Total));
        }
        if (province.accessToMainSea) {
            province.aiPeaceScore = (int)((float)province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA);
            boolean extraSeaPoints = false;
            for (int i = 0; i < province.getNeighboringProvincesSize(); ++i) {
                if (Game.getProvince(province.getNeighboringProvinces(i)).getWasteland() >= 0 || Game.getProvince((int)province.getNeighboringProvinces((int)i)).aiPeaceCivID == province.getCivID()) continue;
                extraSeaPoints = true;
                break;
            }
            if (extraSeaPoints) {
                province.aiPeaceScore = (int)((float)province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA_BEGIN);
            }
        }
        province.aiPeaceScore += (int)(GameValues.peace.AI_PEACE_DEMAND_PROVINCE_DISTANCE * Math.max(0.0f, 1.0f - Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), provinceID)));
    }

    public static int buildProvinceScore_Player(List<Integer> aiPeaceCivID, int civID, int provinceID) {
        int out = 0;
        int neighboringPerc = 0;
        float neighboringPerc_Total = 0.0f;
        for (int i = 0; i < Game.getProvince(provinceID).getNeighboringProvincesSize(); ++i) {
            if (Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getWasteland() >= 0) continue;
            if (aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i)) == civID || aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i)).intValue() == Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getCivID()) {
                neighboringPerc_Total += 1.0f;
            }
            if (aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i)) != civID) continue;
            out = (int)((float)out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_SCORE);
            ++neighboringPerc;
        }
        if (neighboringPerc_Total > 0.0f) {
            out = (int)((float)out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_PERC * ((float)neighboringPerc / neighboringPerc_Total));
        }
        if (Game.getProvince((int)provinceID).accessToMainSea) {
            out = (int)((float)out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA);
        }
        return out += (int)(GameValues.peace.AI_PEACE_DEMAND_PROVINCE_DISTANCE * Math.max(0.0f, 1.0f - Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), provinceID)));
    }
}
