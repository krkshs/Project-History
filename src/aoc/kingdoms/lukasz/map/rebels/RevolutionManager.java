package aoc.kingdoms.lukasz.map.rebels;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_Army_Composition;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyPosition;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class RevolutionManager {
   public List<ArmyPosition> armyPosition = new ArrayList<>();
   public int iArmyPositionSize = 0;
   public List<OccupiedProvince> occupiedProvinces = new ArrayList<>();

   public void clearData() {
      this.occupiedProvinces.clear();
      this.armyPosition.clear();
      this.iArmyPositionSize = 0;
      Game.revolutionMoveUnits.clearData();
   }

   public final void update() {
      try {
         this.declareIndependence();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      try {
         this.moveArmies();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      try {
         this.startRevolution();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void startRevolution() {
      List<Integer> possibleProvinces = new ArrayList<>();

      for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_UPRISING_STEPS;
         i < Game.getCivsSize();
         i += GameValues.gameUpdate.GAME_UPDATE_UPRISING_STEPS
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            possibleProvinces.clear();

            for (int j = 0; j < Game.getCiv(i).getNumOfProvinces(); j++) {
               if (Game.getProvince(Game.getCiv(i).getProvinceID(j)).getRevulutionaryRisk() >= GameValues.rebels.START_UPRISING_MIN_UNREST
                  && !Game.getProvince(Game.getCiv(i).getProvinceID(j)).isOccupied()) {
                  possibleProvinces.add(Game.getCiv(i).getProvinceID(j));
               }
            }

            if (possibleProvinces.size() > 0) {
               this.spawnRevolution(i, possibleProvinces);
            }
         }
      }

      possibleProvinces.clear();
      possibleProvinces = null;
   }

   public void spawnRevolution(int civID, List<Integer> provinces) {
      this.spawnRevolution(civID, provinces.get(Game.oR.nextInt(provinces.size())), (int)(provinces.size() * GameValues.rebels.UPRISING_PROVINCES_PERC));
   }

   public void spawnRevolution(int civID, int provinceID, int limitOfProvinces) {
      try {
         try {
            if (Game.getProvince(provinceID).getCivRegionID() >= 0) {
               int regionID = Game.getProvince(provinceID).getCivRegionID();

               for (int i = 0; i < Game.getCiv(civID).getCivRegion(regionID).getProvincesSize(); i++) {
                  if (provinceID != Game.getCiv(civID).getCivRegion(regionID).getProvince(i)) {
                     Game.getProvince(Game.getCiv(civID).getCivRegion(regionID).getProvince(i))
                        .setRevulutionaryRisk(
                           Game.getProvince(Game.getCiv(civID).getCivRegion(regionID).getProvince(i)).getRevulutionaryRisk()
                              - Game.getProvince(Game.getCiv(civID).getCivRegion(regionID).getProvince(i)).getRevulutionaryRisk()
                                 * (
                                    GameValues.rebels.DECREASE_UNREST_IN_PROVINCES_BY_AFTER_REVOLT
                                       * (1.0F - Game.getDistance_PercOfMax(provinceID, Game.getCiv(civID).getCivRegion(regionID).getProvince(i)))
                                 )
                        );
                  }
               }
            }
         } catch (Exception var15) {
            CFG.exceptionStack(var15);
         }

         List<Integer> spawnProvinces = new ArrayList<>();
         spawnProvinces.add(provinceID);
         if (limitOfProvinces > 1) {
            for (int ix = 0; ix < Game.getProvince(provinceID).getNeighboringProvincesSize(); ix++) {
               if (Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ix)).getCivID() == civID
                  && !Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ix)).isOccupied()) {
                  spawnProvinces.add(Game.getProvince(provinceID).getNeighboringProvinces(ix));
                  if (spawnProvinces.size() >= limitOfProvinces) {
                     break;
                  }
               }
            }

            if (spawnProvinces.size() < limitOfProvinces) {
               for (int ixx = 0; ixx < Game.getProvince(provinceID).getNeighboringProvincesSize(); ixx++) {
                  for (int j = 0; j < Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ixx)).getNeighboringProvincesSize(); j++) {
                     if (Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ixx)).getNeighboringProvinces(j)).getCivID()
                           == civID
                        && !Game.getProvince(Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ixx)).getNeighboringProvinces(j))
                           .isOccupied()) {
                        spawnProvinces.add(Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(ixx)).getNeighboringProvinces(j));
                        if (spawnProvinces.size() >= limitOfProvinces) {
                           break;
                        }
                     }
                  }

                  if (spawnProvinces.size() >= limitOfProvinces) {
                     break;
                  }
               }
            }
         }

         int ixx = 0;

         for (int iSize = spawnProvinces.size(); ixx < iSize; ixx++) {
            Game.getProvince(spawnProvinces.get(ixx)).setOccupiedByCivID(-civID);
            this.addOccupiedProvince(spawnProvinces.get(ixx));
            Game.getProvince(spawnProvinces.get(ixx))
               .setRevulutionaryRisk(
                  Math.min(Game.getProvince(spawnProvinces.get(ixx)).getRevulutionaryRisk(), GameValues.rebels.UNREST_AFTER_REVOLUTION_IN_PROVINCE)
               );
         }

         ixx = (int)Math.max(
            (float)GameValues.rebels.UPRISING_REGIMENTS_MIN, Game.getCiv(civID).iRegimentsLimit * GameValues.rebels.UPRISING_PERC_OF_REGIMENTS_LIMIT
         );
         int ixxx = 0;

         for (int iSize = spawnProvinces.size(); ixxx < iSize; ixxx++) {
            List<ArmyRegiment> rebelsRegiments = new ArrayList<>();
            AI_Army_Composition armyComposition = new AI_Army_Composition(civID, Math.min(ixx, GameValues.rebels.UPRISING_MAX_REGIMENTS_IN_PROVINCE));
            armyComposition.numSupport = armyComposition.numSupport + armyComposition.numSiege;
            List<TechnologyTree.Unit> firstLine = Game.getCiv(civID).getUnlockedUnitsFirstLine();
            List<TechnologyTree.Unit> flank = Game.getCiv(civID).getUnlockedUnitsFlank();
            List<TechnologyTree.Unit> secondLine = Game.getCiv(civID).getUnlockedUnitsSupport();
            if (flank.size() == 0 && armyComposition.numFlank > 0) {
               armyComposition.numFirstLine = armyComposition.numFirstLine + armyComposition.numFlank;
            }

            if (secondLine.size() == 0 && armyComposition.numSupport > 0) {
               armyComposition.numFirstLine = armyComposition.numFirstLine + armyComposition.numSupport;
            }

            if (firstLine.size() == 0) {
               firstLine.add(new TechnologyTree.Unit(0, 0));
            }

            int randID = 0;

            for (int jx = 0; jx < armyComposition.numFirstLine; jx++) {
               randID = Game.oR.nextInt(firstLine.size());
               rebelsRegiments.add(new ArmyRegiment(firstLine.get(randID).unitID, firstLine.get(randID).armyID));
            }

            if (flank.size() > 0) {
               for (int jx = 0; jx < armyComposition.numFlank; jx++) {
                  randID = Game.oR.nextInt(flank.size());
                  rebelsRegiments.add(new ArmyRegiment(flank.get(randID).unitID, flank.get(randID).armyID));
               }
            }

            if (secondLine.size() > 0) {
               for (int jx = 0; jx < armyComposition.numSupport; jx++) {
                  randID = Game.oR.nextInt(secondLine.size());
                  rebelsRegiments.add(new ArmyRegiment(secondLine.get(randID).unitID, secondLine.get(randID).armyID));
               }
            }

            ixx -= rebelsRegiments.size();
            Game.getProvince(spawnProvinces.get(ixxx)).addArmy(new ArmyDivision(-civID, spawnProvinces.get(ixxx), rebelsRegiments));
            if (ixx < 0) {
               break;
            }
         }

         ixxx = 0;

         for (int iSize = spawnProvinces.size(); ixxx < iSize; ixxx++) {
            Game.getProvince(spawnProvinces.get(ixxx)).checkForBattle();
         }

         if (civID == Game.player.iCivID) {
            InGame_Info.iCivID = -civID;
            InGame_Info.iCivID2 = civID;
            Game.menuManager.rebuildInGame_Info(Game.lang.get("Revolt"), Game.getProvince(provinceID).getProvinceName());
            InGame_Info.imgID = Images.infoUnrest;
            Game.player
               .addNotification(
                  new Notification(
                     Notification.Notification_Type.SIEGE,
                     Game.lang.get("SiegeLost") + ": " + Game.getProvince(provinceID).getProvinceName(),
                     Images.siege,
                     Game_Calendar.TURN_ID,
                     Notification.Notification_BG.RED
                  ) {
                     @Override
                     public void onAction() {
                        Game.mapCoords.centerToProvinceID(this.id);
                     }
                  }
               );
            Game.player
               .addNotification(
                  new Notification(
                     Notification.Notification_Type.REVOLT,
                     Game.lang.get("Revolt") + ": " + Game.getProvince(provinceID).getProvinceName(),
                     Images.siege,
                     Game_Calendar.TURN_ID,
                     Notification.Notification_BG.RED,
                     provinceID
                  ) {
                     @Override
                     public void onAction() {
                        Game.mapCoords.centerToProvinceID(this.id);
                     }
                  }
               );
         }
      } catch (Exception var16) {
         CFG.exceptionStack(var16);
      }
   }

   public final int declareIndependence_TurnsLeft(int provinceID) {
      for (int i = this.occupiedProvinces.size() - 1; i >= 0; i--) {
         if (this.occupiedProvinces.get(i).p == provinceID) {
            return Math.max(1, GameValues.rebels.DECLARE_INDEPENDENCE_AFTER_X_DAYS - (Game_Calendar.TURN_ID - this.occupiedProvinces.get(i).sinceTurnID));
         }
      }

      return 0;
   }

   public final void declareIndependence() {
      if (Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_REBELS_INDEPENDENCE_STEPS == 0) {
         for (int i = this.occupiedProvinces.size() - 1; i >= 0; i--) {
            if (Game.getProvince(this.occupiedProvinces.get(i).p).isOccupied()
               && Game.getProvinceData(this.occupiedProvinces.get(i).p).getOccupiedByCivID() <= 0) {
               Game.getProvince(this.occupiedProvinces.get(i).p).aiRebelsIndependenceChecked = false;
            } else {
               this.occupiedProvinces.remove(i);
            }
         }

         for (int ix = this.occupiedProvinces.size() - 1; ix >= 0; ix--) {
            if (Game_Calendar.TURN_ID - this.occupiedProvinces.get(ix).sinceTurnID > GameValues.rebels.DECLARE_INDEPENDENCE_AFTER_X_DAYS
               && !Game.getProvince(this.occupiedProvinces.get(ix).p).aiRebelsIndependenceChecked) {
               boolean cleanOccupiedProvinces = false;
               List<Integer> provinces = new ArrayList<>();
               int provID = this.occupiedProvinces.get(ix).p;
               Game.getProvince(this.occupiedProvinces.get(ix).p).aiRebelsIndependenceChecked = true;
               cleanOccupiedProvinces = cleanOccupiedProvinces
                  || Game_Calendar.TURN_ID - this.occupiedProvinces.get(ix).sinceTurnID > GameValues.rebels.DISBAND_REBELS_ARMIES_AFTER_X_DAYS;
               int fromCivID = Game.getProvince(this.occupiedProvinces.get(ix).p).getCivID();
               provinces.add(provID);

               for (int j = ix - 1; j >= 0; j--) {
                  if (ix != j
                     && Game.getProvince(this.occupiedProvinces.get(j).p).getCivID() == Game.getProvince(provID).getCivID()
                     && Game_Calendar.TURN_ID - this.occupiedProvinces.get(j).sinceTurnID >= GameValues.rebels.DECLARE_INDEPENDENCE_MIN_OCCUPIED_DAYS) {
                     provinces.add(this.occupiedProvinces.get(j).p);
                     Game.getProvince(this.occupiedProvinces.get(j).p).aiRebelsIndependenceChecked = true;
                     cleanOccupiedProvinces = cleanOccupiedProvinces
                        || Game_Calendar.TURN_ID - this.occupiedProvinces.get(j).sinceTurnID > GameValues.rebels.DISBAND_REBELS_ARMIES_AFTER_X_DAYS;
                  }
               }

               if (Game.getProvince(provID).getCivRegionID() >= 0) {
                  for (int jx = provinces.size() - 1; jx >= 0; jx--) {
                     if (Game.getProvince(provinces.get(jx)).getCivRegionID() != Game.getProvince(provID).getCivRegionID()) {
                        Game.getProvince(provinces.get(jx)).aiRebelsIndependenceChecked = false;
                        provinces.remove(jx);
                     }
                  }
               }

               for (int jxx = provinces.size() - 1; jxx >= 0; jxx--) {
                  for (int k = 0; k < Game.getProvince(provinces.get(jxx)).getNeighboringProvincesSize(); k++) {
                     if (!provinces.contains(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k))
                        && !Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k)).isCapital
                        && Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k)).getCivID() == fromCivID) {
                        boolean canBeAdded = true;

                        for (int z = 0;
                           z < Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k)).getNeighboringProvincesSize();
                           z++
                        ) {
                           if (Game.getProvince(Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k)).getNeighboringProvinces(z))
                                    .getCivID()
                                 == fromCivID
                              && !provinces.contains(
                                 Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k)).getNeighboringProvinces(z)
                              )) {
                              canBeAdded = false;
                              break;
                           }
                        }

                        if (canBeAdded) {
                           provinces.add(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(k));
                        }
                     }
                  }
               }

               for (int jxx = provinces.size() - 1; jxx > 0; jxx--) {
                  if (Game.getProvince(provinces.get(jxx)).getNeighboringProvincesSize() > 0) {
                     boolean removeProvince = true;

                     for (int kx = 0; kx < Game.getProvince(provinces.get(jxx)).getNeighboringProvincesSize(); kx++) {
                        if (provinces.contains(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(kx))
                           || Game.getProvince(Game.getProvince(provinces.get(jxx)).getNeighboringProvinces(kx)).getCivID() == 0) {
                           removeProvince = false;
                           break;
                        }
                     }

                     if (removeProvince) {
                        provinces.remove(jxx);
                     }
                  }
               }

               List<Integer> possibleCivs = new ArrayList<>();

               for (int jxxx = provinces.size() - 1; jxxx >= 0; jxxx--) {
                  for (int kxx = 0; kxx < Game.getProvince(provinces.get(jxxx)).iCoresSize; kxx++) {
                     if (Game.getProvince(provinces.get(jxxx)).getCore(kxx) > 0
                        && Game.getCiv(Game.getProvince(provinces.get(jxxx)).getCore(kxx)).getNumOfProvinces() <= 0
                        && !possibleCivs.contains(Game.getProvince(provinces.get(jxxx)).getCore(kxx))) {
                        possibleCivs.add(Game.getProvince(provinces.get(jxxx)).getCore(kxx));
                     }
                  }
               }

               if (possibleCivs.size() > 0) {
                  int civID = possibleCivs.get(Game.oR.nextInt(possibleCivs.size()));

                  for (int jxxx = provinces.size() - 1; jxxx >= 0; jxxx--) {
                     Game.getProvince(provinces.get(jxxx)).setRevulutionaryRisk(0.0F);
                     Game.getProvince(provinces.get(jxxx)).setOccupiedByCivID(0);
                     Game.getProvince(provinces.get(jxxx)).setCivID(civID);
                     Game.getProvince(provinces.get(jxxx)).addCore(civID);
                     Game.getProvince(provinces.get(jxxx)).resetSiegeData();
                     this.removeOccupiedProvince(provinces.get(jxxx));

                     try {
                        for (int a = Game.getProvince(provinces.get(jxxx)).getArmySize() - 1; a >= 0; a--) {
                           if (Game.getProvince(provinces.get(jxxx)).getArmy(a).civID < 0) {
                              Game.revolutionMoveUnits.removeMove(Game.getProvince(provinces.get(jxxx)).getArmy(a).key);
                              this.removeArmyPosition(provinces.get(jxxx), Game.getProvince(provinces.get(jxxx)).getArmy(a).key);
                              Game.getProvince(provinces.get(jxxx)).removeArmy(Game.getProvince(provinces.get(jxxx)).getArmy(a).key);
                              a--;
                           }
                        }
                     } catch (Exception var13) {
                        CFG.exceptionStack(var13);
                     }
                  }

                  if (Game.getCiv(civID).getCapitalProvinceID() >= 0 && Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getCivID() == civID) {
                     Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).setIsCapital(true);
                  } else {
                     Game.getCiv(civID).moveCapital_ToLargestProvince();
                  }

                  Game.getCiv(civID).setPuppetOfCivID(civID);
                  Game.gameThread.addCivUpdateTotalIncomePerMonth(civID);
                  Game.gameThread.addCivUpdateLegacyPerMonth(civID);
                  Game.gameThread.addCivUpdateResearchPerMonth(civID);
                  Game.gameThread.addCivUpdateArmyMaintenance(civID);
                  Game.getCiv(civID).updateManpowerPerMonth();
                  Game.getCiv(civID).updateDiplomacyPerMonth();
                  Game.getCiv(civID).setManpower(Math.max(Game.getCiv(civID).fManpower, Game.getCiv(civID).fManpowerMax));
                  Civilization var10000 = Game.getCiv(civID);
                  int var10003 = GameValues.rebels.INDEPENDENCE_GOLD_RANDOM > 0 ? Game.oR.nextInt(GameValues.rebels.INDEPENDENCE_GOLD_RANDOM) : 0;
                  var10000.fGold = Math.max(Game.getCiv(civID).fGold, (float)(GameValues.rebels.INDEPENDENCE_GOLD + var10003));
                  var10000 = Game.getCiv(civID);
                  var10003 = GameValues.rebels.INDEPENDENCE_LEGACY_RANDOM > 0 ? Game.oR.nextInt(GameValues.rebels.INDEPENDENCE_LEGACY_RANDOM) : 0;
                  var10000.fLegacy = Math.max(Game.getCiv(civID).fLegacy, (float)(GameValues.rebels.INDEPENDENCE_LEGACY + var10003));
                  Game.getCiv(civID).fDiplomacy = Game.getCiv(civID).fDiplomacyMax;

                  for (int t = 0; t < TechnologyTree.iTechnologySize; t++) {
                     if (Game.getCiv(fromCivID).getTechResearched(t)) {
                        Game.getCiv(civID).addTechnology(t, false);
                     }
                  }

                  DiplomacyManager.addTruce(fromCivID, civID);
                  this.sentNotification(fromCivID, civID);
                  Game.revolutionMoveUnits.removeAllRebelsArmiesMovingToCiv(civID);
                  Game.getCiv(civID).updateProvinceBorder();
                  possibleCivs.clear();
                  provinces.clear();
                  return;
               }

               List<String> civTags = new ArrayList<>();

               for (int jxxx = provinces.size() - 1; jxxx >= 0; jxxx--) {
                  List<String> tTags = Game.loadSuggestedCivs(provinces.get(jxxx));

                  for (int kxxx = tTags.size() - 1; kxxx >= 0; kxxx--) {
                     if (!civTags.contains(tTags.get(kxxx))) {
                        civTags.add(tTags.get(kxxx));
                     }
                  }
               }

               for (int jxxx = civTags.size() - 1; jxxx >= 0; jxxx--) {
                  for (int kxxxx = 1; kxxxx < Game.getCivsSize(); kxxxx++) {
                     if (Game.getCiv(kxxxx).getCivTag().equals(civTags.get(jxxx))) {
                        civTags.remove(jxxx);
                        break;
                     }
                  }
               }

               if (!civTags.isEmpty()) {
                  String toAdd = civTags.get(Game.oR.nextInt(civTags.size()));
                  if (this.addCiv(toAdd, provinces, fromCivID)) {
                     civTags.clear();
                     provinces.clear();
                     return;
                  }
               }

               if (GameValues.rebels.DECLARE_INDEPENDENCE_ENABLE_DIFFERENT_GOVERNMENT) {
                  List<Integer> differentGovernment = new ArrayList<>();

                  for (int jxxx = 0; jxxx < Game.ideologiesManager.getIdeologiesSize(); jxxx++) {
                     if (!Game.ideologiesManager.getIdeology(jxxx).TRIBAL
                        && !Game.ideologiesManager.getIdeology(jxxx).REVOLUTIONISTS
                        && !Game.ideologiesManager.getIdeology(jxxx).CITY_STATE
                        && (
                           Game.ideologiesManager.getIdeology(jxxx).REQUIRED_TECHNOLOGY < 0
                              || Game.getCiv(fromCivID).getTechResearched(Game.ideologiesManager.getIdeology(jxxx).REQUIRED_TECHNOLOGY)
                        )
                        && Game.getCiv(fromCivID).getIdeologyID() != jxxx) {
                        differentGovernment.add(jxxx);
                     }
                  }

                  if (differentGovernment.isEmpty()) {
                     for (int jxxxx = 0; jxxxx < Game.ideologiesManager.getIdeologiesSize(); jxxxx++) {
                        if (!Game.ideologiesManager.getIdeology(jxxxx).TRIBAL
                           && !Game.ideologiesManager.getIdeology(jxxxx).REVOLUTIONISTS
                           && !Game.ideologiesManager.getIdeology(jxxxx).CITY_STATE
                           && Game.getCiv(fromCivID).getIdeologyID() != jxxxx) {
                           differentGovernment.add(jxxxx);
                        }
                     }
                  }

                  if (!differentGovernment.isEmpty()) {
                     String tag = Game.getCiv(fromCivID).realTag;

                     for (int g = differentGovernment.size() - 1; g >= 0; g--) {
                        String tTag = tag
                           + (
                              Game.ideologiesManager.getIdeology(differentGovernment.get(g)).Extra_Tag.length() > 0
                                 ? "_" + Game.ideologiesManager.getIdeology(differentGovernment.get(g)).Extra_Tag
                                 : ""
                           );

                        for (int jxxxxx = 1; jxxxxx < Game.getCivsSize(); jxxxxx++) {
                           if (Game.getCiv(jxxxxx).getCivTag().equals(tTag)) {
                              differentGovernment.remove(g);
                              break;
                           }
                        }
                     }

                     if (!differentGovernment.isEmpty()) {
                        int govID = Game.oR.nextInt(differentGovernment.size());
                        tag = tag
                           + (Game.ideologiesManager.getIdeology(govID).Extra_Tag.length() > 0 ? "_" + Game.ideologiesManager.getIdeology(govID).Extra_Tag : "");
                        if (this.addCiv(tag, provinces, fromCivID)) {
                           civTags.clear();
                           provinces.clear();
                           return;
                        }
                     }
                  }
               }

               if (cleanOccupiedProvinces) {
                  try {
                     for (int jxxxxxx = provinces.size() - 1; jxxxxxx >= 0; jxxxxxx--) {
                        Game.getProvince(provinces.get(jxxxxxx)).setRevulutionaryRisk(0.0F);
                        Game.getProvince(provinces.get(jxxxxxx)).setOccupiedByCivID(0);
                        Game.getProvince(provinces.get(jxxxxxx)).resetSiegeData();
                        this.removeOccupiedProvince(provinces.get(jxxxxxx));

                        try {
                           for (int ax = Game.getProvince(provinces.get(jxxxxxx)).getArmySize() - 1; ax >= 0; ax--) {
                              if (Game.getProvince(provinces.get(jxxxxxx)).getArmy(ax).civID < 0) {
                                 Game.revolutionMoveUnits.removeMove(Game.getProvince(provinces.get(jxxxxxx)).getArmy(ax).key);
                                 this.removeArmyPosition(provinces.get(jxxxxxx), Game.getProvince(provinces.get(jxxxxxx)).getArmy(ax).key);
                                 Game.getProvince(provinces.get(jxxxxxx)).removeArmy(Game.getProvince(provinces.get(jxxxxxx)).getArmy(ax).key);
                              }
                           }
                        } catch (Exception var14) {
                           CFG.exceptionStack(var14);
                        }
                     }
                  } catch (Exception var15) {
                  }

                  ix = Math.min(ix, this.occupiedProvinces.size() - 1);
               }

               provinces.clear();
            }
         }
      }
   }

   public boolean addCiv(String toAdd, List<Integer> provinces, int fromCivID) {
      Game.addCivilization(toAdd, provinces.get(0), true, true, true, true, true);
      int civID = -1;

      for (int j = Game.getCivsSize() - 1; j > 0; j--) {
         if (Game.getCiv(j).getCivTag().equals(toAdd)) {
            civID = j;
            break;
         }
      }

      if (civID <= 0) {
         return false;
      } else {
         for (int jx = provinces.size() - 1; jx >= 0; jx--) {
            Game.getProvince(provinces.get(jx)).setRevulutionaryRisk(0.0F);
            Game.getProvince(provinces.get(jx)).setOccupiedByCivID(0);
            Game.getProvince(provinces.get(jx)).resetSiegeData();
            Game.getProvince(provinces.get(jx)).setCivID(civID);
            Game.getProvince(provinces.get(jx)).addCore(civID);
            Game.getProvince(provinces.get(jx)).setReligion(Game.getCiv(civID).getReligionID());
            this.removeOccupiedProvince(provinces.get(jx));

            try {
               for (int a = Game.getProvince(provinces.get(jx)).getArmySize() - 1; a >= 0; a--) {
                  if (Game.getProvince(provinces.get(jx)).getArmy(a).civID < 0) {
                     Game.revolutionMoveUnits.removeMove(Game.getProvince(provinces.get(jx)).getArmy(a).key);
                     this.removeArmyPosition(provinces.get(jx), Game.getProvince(provinces.get(jx)).getArmy(a).key);
                     Game.getProvince(provinces.get(jx)).removeArmy(Game.getProvince(provinces.get(jx)).getArmy(a).key);
                  }
               }
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         }

         Game.getCiv(civID).moveCapital_ToLargestProvince();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(civID);
         Game.gameThread.addCivUpdateLegacyPerMonth(civID);
         Game.gameThread.addCivUpdateResearchPerMonth(civID);
         Game.gameThread.addCivUpdateArmyMaintenance(civID);
         Game.getCiv(civID).updateManpowerPerMonth();
         Game.getCiv(civID).updateDiplomacyPerMonth();
         Game.getCiv(civID).setManpower(Math.max(Game.getCiv(civID).fManpower, Game.getCiv(civID).fManpowerMax));
         Civilization var10000 = Game.getCiv(civID);
         int var10003 = GameValues.rebels.INDEPENDENCE_GOLD_RANDOM > 0 ? Game.oR.nextInt(GameValues.rebels.INDEPENDENCE_GOLD_RANDOM) : 0;
         var10000.fGold = Math.max(Game.getCiv(civID).fGold, (float)(GameValues.rebels.INDEPENDENCE_GOLD + var10003));
         var10000 = Game.getCiv(civID);
         var10003 = GameValues.rebels.INDEPENDENCE_LEGACY_RANDOM > 0 ? Game.oR.nextInt(GameValues.rebels.INDEPENDENCE_LEGACY_RANDOM) : 0;
         var10000.fLegacy = Math.max(Game.getCiv(civID).fLegacy, (float)(GameValues.rebels.INDEPENDENCE_LEGACY + var10003));
         Game.getCiv(civID).fDiplomacy = Game.getCiv(civID).fDiplomacyMax;

         for (int t = 0; t < TechnologyTree.iTechnologySize; t++) {
            if (Game.getCiv(fromCivID).getTechResearched(t)) {
               Game.getCiv(civID).addTechnology(t, false);
            }
         }

         DiplomacyManager.addTruce(fromCivID, civID);
         this.sentNotification(fromCivID, civID);
         if (Game.getCiv(civID).updateArmyRegimentSize() <= 0) {
            Game.mapScenarios.buildStartingArmy(civID);
            Game.getCiv(civID).setAdvantagePoints(Math.min(Game.getCiv(civID).getAdvantagePoints(), GameValues.rebels.INDEPENDENCE_MAX_ADVANTAGE_POINTS));
         }

         Game.getCiv(civID).updateProvinceBorder();
         Game.revolutionMoveUnits.removeAllRebelsArmiesMovingToCiv(civID);
         Game.addSimpleTask(new Game.SimpleTask("LOAD_RULER" + civID, civID) {
            @Override
            public void update() {
               RulersManager.loadRuler(this.id, Game.getCiv(this.id).getCivTag(), true);
            }
         });
         return true;
      }
   }

   public void sentNotification(int fromCivID, int civID) {
      if (fromCivID == Game.player.iCivID && civID < Game.getCivsSize()) {
         InGame_Info.iCivID = fromCivID;
         InGame_Info.iCivID2 = civID;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("Liberation"), Game.getCiv(civID).getCivName());
         InGame_Info.imgID = Images.infoUnrest;
      }
   }

   public final void moveArmies() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_REBELS_ARMIES_STEPS;
            i < this.iArmyPositionSize;
            i += GameValues.gameUpdate.GAME_UPDATE_REBELS_ARMIES_STEPS
         ) {
            if (!Game.getProvinceData4(this.armyPosition.get(i).provinceID).isUnderSiege()) {
               int armyID = Game.getProvince(this.armyPosition.get(i).provinceID).getArmyKeyID(this.armyPosition.get(i).key);
               if (armyID < 0) {
                  this.armyPosition.remove(i);
                  this.iArmyPositionSize = this.armyPosition.size();
                  i--;
               } else if (Game.getProvince(this.armyPosition.get(i).provinceID).getArmy(armyID).iArmyRegimentSize < GameValues.siege.SIEGE_REGIMENTS_MIN) {
                  Game.getProvince(this.armyPosition.get(i).provinceID).removeArmy(armyID);
               } else {
                  if (Game.getProvince(this.armyPosition.get(i).provinceID).getArmy(armyID).fMorale < GameValues.rebels.REBELS_MAX_MORALE) {
                     Game.getProvince(this.armyPosition.get(i).provinceID)
                        .getArmy(armyID)
                        .updateMorale_Regiments(GameValues.rebels.REBELS_MAX_MORALE, GameValues.rebels.REBELS_MORALE_RECOVERY);
                  }

                  if (!Game.getProvince(this.armyPosition.get(i).provinceID).getArmy(armyID).inBattle
                     && !Game.getProvince(this.armyPosition.get(i).provinceID).getArmy(armyID).inMovement) {
                     if (Game.getProvince(this.armyPosition.get(i).provinceID).getSeaProvince()) {
                        Game.getProvince(this.armyPosition.get(i).provinceID).removeArmy(this.armyPosition.get(i).key);
                     } else if (Game.getProvince(this.armyPosition.get(i).provinceID).getCivRegionID() >= 0) {
                        List<Integer> possibleToMove = new ArrayList<>();

                        for (int r = 0;
                           r
                              < Game.getCiv(Game.getProvince(this.armyPosition.get(i).provinceID).getCivID())
                                 .getCivRegion(Game.getProvince(this.armyPosition.get(i).provinceID).getCivRegionID())
                                 .getProvincesSize();
                           r++
                        ) {
                           if (!Game.getProvince(
                                    Game.getCiv(Game.getProvince(this.armyPosition.get(i).provinceID).getCivID())
                                       .getCivRegion(Game.getProvince(this.armyPosition.get(i).provinceID).getCivRegionID())
                                       .getProvince(r)
                                 )
                                 .isOccupied()
                              && Game.getCiv(Game.getProvince(this.armyPosition.get(i).provinceID).getCivID())
                                    .getCivRegion(Game.getProvince(this.armyPosition.get(i).provinceID).getCivRegionID())
                                    .getProvince(r)
                                 != this.armyPosition.get(i).provinceID) {
                              possibleToMove.add(
                                 Game.getCiv(Game.getProvince(this.armyPosition.get(i).provinceID).getCivID())
                                    .getCivRegion(Game.getProvince(this.armyPosition.get(i).provinceID).getCivRegionID())
                                    .getProvince(r)
                              );
                           }
                        }

                        if (possibleToMove.size() > 0) {
                           int bestID = 0;
                           float bestDistance = Game.getDistanceFromProvinceToProvince(this.armyPosition.get(i).provinceID, possibleToMove.get(bestID));

                           for (int j = possibleToMove.size() - 1; j > 0; j--) {
                              float tDistance = Game.getDistanceFromProvinceToProvince(this.armyPosition.get(i).provinceID, possibleToMove.get(j));
                              if (tDistance < bestDistance) {
                                 if (Game.revolutionMoveUnits.isArmyAlreadyMoving(this.armyPosition.get(i).provinceID, possibleToMove.get(j))) {
                                    tDistance *= 10.0F;
                                 }

                                 if (tDistance < bestDistance) {
                                    bestDistance = tDistance;
                                    bestID = j;
                                 }
                              }
                           }

                           Game.revolutionMoveUnits
                              .newMove(this.armyPosition.get(i).provinceID, possibleToMove.get(bestID), this.armyPosition.get(i).key, 0, false);
                           possibleToMove.clear();
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public void addOccupiedProvince(int provinceID) {
      for (int i = this.occupiedProvinces.size() - 1; i >= 0; i--) {
         if (this.occupiedProvinces.get(i).p == provinceID) {
            return;
         }
      }

      this.occupiedProvinces.add(new OccupiedProvince(provinceID, Game_Calendar.TURN_ID));
   }

   public void removeOccupiedProvince(int provinceID) {
      for (int i = this.occupiedProvinces.size() - 1; i >= 0; i--) {
         if (this.occupiedProvinces.get(i).p == provinceID) {
            this.occupiedProvinces.remove(i);
            return;
         }
      }
   }

   public List<Integer> getRevolutionaryProvinces(int civID) {
      List<Integer> out = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(civID).getProvinceID(i)).getRevulutionaryRisk() > 0.0F) {
            out.add(Game.getCiv(civID).getProvinceID(i));
         }
      }

      return out;
   }

   public List<Integer> getRevolutionaryProvinces_Sorted(int civID) {
      List<Integer> list = this.getRevolutionaryProvinces(civID);
      List<Integer> out = new ArrayList<>();

      while (list.size() > 0) {
         int bestID = 0;

         for (int i = list.size() - 1; i > 0; i--) {
            if (Game.getProvince(list.get(bestID)).getRevulutionaryRisk() < Game.getProvince(list.get(i)).getRevulutionaryRisk()) {
               bestID = i;
            }
         }

         out.add(list.get(bestID));
         list.remove(bestID);
      }

      return out;
   }

   public float getDecreaseRevolutionaryRisk_CostGold(int nProvinceID) {
      return Math.max(0.0F, GameValues.province.UNREST_DECREASE_COST_GOLD * Game.getProvince(nProvinceID).getRevulutionaryRisk());
   }

   public float getDecreaseRevolutionaryRisk_CostLegacy(int nProvinceID) {
      return Math.max(0.0F, GameValues.province.UNREST_DECREASE_COST_LEGACY * Game.getProvince(nProvinceID).getRevulutionaryRisk());
   }

   public boolean decreaseRevolutionaryRisk(int civID, int provinceID) {
      if (civID != Game.getProvince(provinceID).getCivID()) {
         return false;
      } else if (Game.getProvince(provinceID).getRevulutionaryRisk() <= 0.0F) {
         return false;
      } else if (Game.getCiv(civID).fGold < GameValues.province.UNREST_DECREASE_COST_GOLD) {
         return false;
      } else if (Game.getCiv(civID).fLegacy < GameValues.province.UNREST_DECREASE_COST_LEGACY) {
         return false;
      } else {
         float possibleToDecrease = Game.getProvince(provinceID).getRevulutionaryRisk();
         possibleToDecrease = Math.min(possibleToDecrease, Game.getCiv(civID).fGold / GameValues.province.UNREST_DECREASE_COST_GOLD);
         possibleToDecrease = Math.min(possibleToDecrease, Game.getCiv(civID).fLegacy / GameValues.province.UNREST_DECREASE_COST_LEGACY);
         if (possibleToDecrease > 0.0F) {
            Game.getProvince(provinceID).setRevulutionaryRisk(Game.getProvince(provinceID).getRevulutionaryRisk() - possibleToDecrease);
            Civilization var10000 = Game.getCiv(civID);
            var10000.fGold = var10000.fGold - possibleToDecrease * GameValues.province.UNREST_DECREASE_COST_GOLD;
            var10000 = Game.getCiv(civID);
            var10000.fLegacy = var10000.fLegacy - possibleToDecrease * GameValues.province.UNREST_DECREASE_COST_LEGACY;
         }

         return true;
      }
   }

   public final void addArmyPosition(int nProvinceID, String key) {
      try {
         for (int i = this.iArmyPositionSize - 1; i >= 0; i--) {
            if (this.armyPosition.get(i).provinceID == nProvinceID && this.armyPosition.get(i).key.equals(key)) {
               return;
            }
         }

         this.armyPosition.add(new ArmyPosition(nProvinceID, key));
         this.iArmyPositionSize = this.armyPosition.size();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void removeArmyPosition(int nProvinceID, String key) {
      try {
         for (int i = 0; i < this.iArmyPositionSize; i++) {
            if (this.armyPosition.get(i).provinceID == nProvinceID && this.armyPosition.get(i).key.equals(key)) {
               this.armyPosition.remove(i);
               this.iArmyPositionSize = this.armyPosition.size();
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public void updateArmyPosition(String key, int nProvinceID) {
      try {
         for (int i = 0; i < this.iArmyPositionSize; i++) {
            if (this.armyPosition.get(i).key.equals(key)) {
               this.armyPosition.get(i).provinceID = nProvinceID;
               return;
            }
         }

         this.armyPosition.add(new ArmyPosition(nProvinceID, key));
         this.iArmyPositionSize = this.armyPosition.size();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
