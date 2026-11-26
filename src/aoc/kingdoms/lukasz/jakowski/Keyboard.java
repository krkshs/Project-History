package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvinceNames;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.province.ProvinceNamesManager;
import aoc.kingdoms.lukasz.menu.ColorPicker;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menusEditor.CreateCiv;
import aoc.kingdoms.lukasz.menusEditor.GameCivs;
import aoc.kingdoms.lukasz.menusEditor.GameCivsEdit;
import aoc.kingdoms.lukasz.menusInGame.InGame_Console;
import aoc.kingdoms.lukasz.menusInGame.InGame_Encyclopedia;
import aoc.kingdoms.lukasz.menusInGame.InGame_Ranking;
import aoc.kingdoms.lukasz.menusInGame.InGame_SaveGame;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_Provinces;
import aoc.kingdoms.lukasz.menusInGame.Court.World.InGame_Court_WorldCivs;
import aoc.kingdoms.lukasz.menusInGame.Court.World.InGame_Court_WorldSearch;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioCreateAllianceList;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioPopulation_List;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioSettings;
import aoc.kingdoms.lukasz.menusScenarioEditor.Scenario_Calendar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.OnscreenKeyboardType;

public class Keyboard {
   public static boolean keyboardMode = false;
   public static String keyboardMessage = "";
   public static boolean keyboardVerticalLine = false;
   public static int VERTICAL_LINE_UPDATE_TIME = 375;
   public static long verticalLineTime = CFG.currentTimeMillis;
   public static Keyboard.Keyboard_Action keyboardAction;
   public static Keyboard.KeyboardActionType keyboardActionType = Keyboard.KeyboardActionType.NONE;

   public static void updateKeyboardVerticalLine() {
      if (keyboardMode && verticalLineTime + VERTICAL_LINE_UPDATE_TIME < CFG.currentTimeMillis) {
         keyboardVerticalLine = !keyboardVerticalLine;
         verticalLineTime = CFG.currentTimeMillis;
      }
   }

   public static String getKeyboardVerticalLine() {
      return keyboardVerticalLine ? "|" : "";
   }

   public final void showKeyboard(Keyboard.KeyboardActionType actionType, String nMessage) {
      keyboardMode = true;
      keyboardMessage = nMessage;
      verticalLineTime = CFG.currentTimeMillis;
      keyboardVerticalLine = true;
      keyboardActionType = actionType;
      if (!CFG.isDesktop()) {
         try {
            Gdx.input.setOnscreenKeyboardVisible(true, OnscreenKeyboardType.Default);
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }

      switch (actionType) {
         case SEARCH_GAMECIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildEditorGameCivs();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildEditorGameCivs();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildEditorGameCivs();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_COURT_ALL_CIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Court_WorldCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtSearchCivs();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Court_WorldCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtSearchCivs();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void save() {
                  InGame_Court_WorldCivs.sSearch = Keyboard.keyboardMessage;
                  if (Game.menuManager.getVisibleInGame_Court()) {
                     Game.menuManager.rebuildInGame_CourtSearchCivs();
                     Game.menuManager.setVisibleInGame_Court(true);
                     InGame_Court.lTime = 0L;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_ENCYCLOPEDIA:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Encyclopedia.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_Encyclopedia();
                  InGame_Encyclopedia.lTime = 0L;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Encyclopedia.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_Encyclopedia();
                  InGame_Encyclopedia.lTime = 0L;
               }

               @Override
               public void save() {
                  InGame_Court_WorldSearch.sSearch = Keyboard.keyboardMessage;
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 15) {
                     InGame_Encyclopedia.sSearch = Keyboard.keyboardMessage;
                     Game.menuManager.rebuildInGame_Encyclopedia();
                     InGame_Encyclopedia.lTime = 0L;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_RANKING:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Ranking.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
                  InGame_Ranking.lTime = 0L;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Ranking.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
                  InGame_Ranking.lTime = 0L;
               }

               @Override
               public void save() {
                  InGame_Court_WorldSearch.sSearch = Keyboard.keyboardMessage;
                  if (Game.menuManager.getVisibleInGame_CurrentSituation_Ranking()) {
                     InGame_Ranking.sSearch = Keyboard.keyboardMessage;
                     Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
                     InGame_Ranking.lTime = 0L;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_COURT_ALL_PROVINCES:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Court_WorldSearch.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtSearch();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Court_WorldSearch.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtSearch();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void save() {
                  InGame_Court_WorldSearch.sSearch = Keyboard.keyboardMessage;
                  if (Game.menuManager.getVisibleInGame_Court()) {
                     Game.menuManager.rebuildInGame_CourtSearch();
                     Game.menuManager.setVisibleInGame_Court(true);
                     InGame_Court.lTime = 0L;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_RENAME_PROVINCE:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_ProvinceInfo.renameProvince = Keyboard.keyboardMessage;
                  if (InGame_ProvinceInfo.renameProvince.length() > 2) {
                     if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getCitiesSize() > 0) {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID)
                           .getCity(0)
                           .setCityName(InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.renameProvince);
                     } else {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID).setProvinceName(InGame_ProvinceInfo.renameProvince);
                        Game.addSimpleTask(new Game.SimpleTask("rebuildProvNameData" + InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.iProvinceID) {
                           @Override
                           public void update() {
                              ProvinceNamesManager.buildProvNameData(this.id, true);
                           }
                        });
                     }
                  }

                  Game.menuManager.getInGame_ProvinceInfo_updateLanguage();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_ProvinceInfo.renameProvince = Keyboard.keyboardMessage;
                  if (InGame_ProvinceInfo.renameProvince.length() > 2) {
                     if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getCitiesSize() > 0) {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID)
                           .getCity(0)
                           .setCityName(InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.renameProvince);
                     } else {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID).setProvinceName(InGame_ProvinceInfo.renameProvince);
                        Game.addSimpleTask(new Game.SimpleTask("rebuildProvNameData" + InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.iProvinceID) {
                           @Override
                           public void update() {
                              ProvinceNamesManager.buildProvNameData(this.id, true);
                           }
                        });
                     }
                  }

                  Game.menuManager.getInGame_ProvinceInfo_updateLanguage();
               }

               @Override
               public void save() {
                  InGame_ProvinceInfo.renameProvince = Keyboard.keyboardMessage;
                  if (InGame_ProvinceInfo.renameProvince.length() > 2) {
                     if (Game.getProvince(InGame_ProvinceInfo.iProvinceID).getCitiesSize() > 0) {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID)
                           .getCity(0)
                           .setCityName(InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.renameProvince);
                     } else {
                        Game.getProvince(InGame_ProvinceInfo.iProvinceID).setProvinceName(InGame_ProvinceInfo.renameProvince);
                        Game.addSimpleTask(new Game.SimpleTask("rebuildProvNameData" + InGame_ProvinceInfo.iProvinceID, InGame_ProvinceInfo.iProvinceID) {
                           @Override
                           public void update() {
                              ProvinceNamesManager.buildProvNameData(this.id, true);
                           }
                        });
                     }
                  }

                  FBOProvinceNames.redrawnProvinceNames();
                  Game.menuManager.getInGame_ProvinceInfo_updateLanguage();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_RENAME_CIV:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Civ.renameCiv = Keyboard.keyboardMessage;
                  if (InGame_Civ.renameCiv.length() > 2) {
                     Game.getCiv(InGame_Civ.iActiveCivID).setCivName(InGame_Civ.renameCiv);
                  }

                  Game.menuManager.getInGame_Civ_updateLanguage();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Civ.renameCiv = Keyboard.keyboardMessage;
                  if (InGame_Civ.renameCiv.length() > 2) {
                     Game.getCiv(InGame_Civ.iActiveCivID).setCivName(InGame_Civ.renameCiv);
                  }

                  Game.menuManager.getInGame_Civ_updateLanguage();
               }

               @Override
               public void save() {
                  InGame_Civ.renameCiv = Keyboard.keyboardMessage;
                  if (InGame_Civ.renameCiv.length() > 2) {
                     Game.getCiv(InGame_Civ.iActiveCivID).setCivName(InGame_Civ.renameCiv);
                     Renderer.addSimpleTaskCivsNames(new Game.SimpleTask("" + InGame_Civ.iActiveCivID, InGame_Civ.iActiveCivID) {
                        @Override
                        public void update() {
                           CivilizationRegionsManager.buildCivilizationsRegions_TextOver(this.id);
                        }
                     });
                  }

                  Game.menuManager.getInGame_Civ_updateLanguage();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_COURT_PROVINCES:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Court_Provinces.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtProvinces();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Court_Provinces.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGame_CourtProvinces();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }

               @Override
               public void save() {
                  InGame_Court_Provinces.sSearch = Keyboard.keyboardMessage;
                  if (Game.menuManager.getVisibleInGame_Court()) {
                     Game.menuManager.rebuildInGame_CourtProvinces();
                     Game.menuManager.setVisibleInGame_Court(true);
                     InGame_Court.lTime = 0L;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CREATE_ALLIANCE_REST:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioCreateAllianceList.sMessageRest = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioCreateAllianceList.sMessageRest = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioCreateAllianceList.sMessageRest = Keyboard.keyboardMessage;
                  ScenarioCreateAllianceList.editAlliance.Name_Rest = ScenarioCreateAllianceList.sMessageRest;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CREATE_ALLIANCE_FIRST_TIER:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioCreateAllianceList.sMessageFirstTier = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioCreateAllianceList.sMessageFirstTier = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioCreateAllianceList.sMessageFirstTier = Keyboard.keyboardMessage;
                  ScenarioCreateAllianceList.editAlliance.Name_FirstTier = ScenarioCreateAllianceList.sMessageFirstTier;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CREATE_ALLIANCE_FLAG:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioCreateAllianceList.sMessageFlag = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioCreateAllianceList.sMessageFlag = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioCreateAllianceList.sMessageFlag = Keyboard.keyboardMessage;
                  ScenarioCreateAllianceList.editAlliance.FlagTag = ScenarioCreateAllianceList.sMessageFlag;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CREATE_ALLIANCE_LEADER:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioCreateAllianceList.sMessageLeader = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioCreateAllianceList.sMessageLeader = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioCreateAllianceList.sMessageLeader = Keyboard.keyboardMessage;
                  ScenarioCreateAllianceList.editAlliance.Name_Leader = ScenarioCreateAllianceList.sMessageLeader;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CREATE_ALLIANCE_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioCreateAllianceList.sMessageName = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioCreateAllianceList.sMessageName = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioCreateAllianceList.sMessageName = Keyboard.keyboardMessage;
                  ScenarioCreateAllianceList.editAlliance.Name_Alliance = ScenarioCreateAllianceList.sMessageName;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case CONSOLE:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_Console.sMessage = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_Console.sMessage = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  InGame_Console.sMessage = Keyboard.keyboardMessage;
                  InGame_Console.execute(InGame_Console.sMessage);
                  InGame_Console.sMessage = "";
                  Game.menuManager.rebuildInGame_Console();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SAVE_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  InGame_SaveGame.sSaveName = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  InGame_SaveGame.sSaveName = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  InGame_SaveGame.sSaveName = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_YEAR:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  Scenario_Calendar.sYear = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  Scenario_Calendar.sYear = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  Scenario_Calendar.sYear = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(Scenario_Calendar.sYear);
                     Game_Calendar.currentYear = tYear;
                  } catch (Exception var2) {
                     Scenario_Calendar.sYear = "" + Game_Calendar.currentYear;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_GOLD:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sGold = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sGold = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sGold = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sGold);
                     MapScenarios.scenarioEditorDetails.CivDefault_Gold = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sGold = "" + MapScenarios.scenarioEditorDetails.CivDefault_Gold;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_GOLD_RANDOM:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sGoldRandom = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sGoldRandom = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sGoldRandom = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sGoldRandom);
                     MapScenarios.scenarioEditorDetails.CivDefault_GoldRandom = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sGoldRandom = "" + MapScenarios.scenarioEditorDetails.CivDefault_GoldRandom;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_LEGACY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sLegacy = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sLegacy = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sLegacy = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sLegacy);
                     MapScenarios.scenarioEditorDetails.CivDefault_Legacy = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sLegacy = "" + MapScenarios.scenarioEditorDetails.CivDefault_Legacy;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_LEGACY_RANDOM:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sLegacyRandom = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sLegacyRandom = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sLegacyRandom = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sLegacyRandom);
                     MapScenarios.scenarioEditorDetails.CivDefault_LegacyRandom = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sLegacyRandom = "" + MapScenarios.scenarioEditorDetails.CivDefault_LegacyRandom;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_MANPOWER_PERC:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sManpower = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sManpower = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sManpower = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sManpower);
                     MapScenarios.scenarioEditorDetails.CivDefault_ManpowerPercentage = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sManpower = "" + MapScenarios.scenarioEditorDetails.CivDefault_ManpowerPercentage;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_NUKES:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Nukes = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Nukes = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Nukes = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_LEGACY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Legacy = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Legacy = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Legacy = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_AI_AGGRESSIVENESS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioPopulation_List.sPopulation);
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.v = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioPopulation_List.sPopulation);
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.v = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioPopulation_List.sPopulation);
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.v = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_GOLD:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Gold = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Gold = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Gold = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_MANPOWER:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Manpower = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Manpower = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Manpower = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_TAX_EFFICIENCY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.TaxEff = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.TaxEff = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.TaxEff = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_ECONOMY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Economy = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Economy = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Economy = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_CIV_POPULATION:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Population = tYear;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Population = tYear;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  ScenarioPopulation_List.sPopulation = Keyboard.keyboardMessage;

                  try {
                     int tYear = Math.max(0, Integer.parseInt(ScenarioPopulation_List.sPopulation));
                     Game.getCiv(ScenarioPopulation_List.iCivID).scenarioEditorData.Population = tYear;
                  } catch (Exception var2) {
                     ScenarioPopulation_List.sPopulation = "0";
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_PROVINCE_POPULATION:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sProvinceDefault_Population = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sProvinceDefault_Population = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sProvinceDefault_Population = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sProvinceDefault_Population);
                     MapScenarios.scenarioEditorDetails.ProvinceDefault_Population = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sProvinceDefault_Population = "" + MapScenarios.scenarioEditorDetails.ProvinceDefault_Population;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_PROVINCE_ECONOMY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sProvinceDefault_Economy = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sProvinceDefault_Economy = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sProvinceDefault_Economy = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sProvinceDefault_Economy);
                     MapScenarios.scenarioEditorDetails.ProvinceDefault_Economy = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sProvinceDefault_Economy = "" + MapScenarios.scenarioEditorDetails.ProvinceDefault_Economy;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_PROVINCE_TAX_EFFICIENCY:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sProvinceDefault_TaxEfficiency = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sProvinceDefault_TaxEfficiency = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sProvinceDefault_TaxEfficiency = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sProvinceDefault_TaxEfficiency);
                     MapScenarios.scenarioEditorDetails.ProvinceDefault_TaxEfficiency = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sProvinceDefault_TaxEfficiency = "" + MapScenarios.scenarioEditorDetails.ProvinceDefault_TaxEfficiency;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_PROVINCE_MANPOWER:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sProvinceDefault_Manpower = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sProvinceDefault_Manpower = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sProvinceDefault_Manpower = Keyboard.keyboardMessage;

                  try {
                     int tYear = Integer.parseInt(ScenarioSettings.sProvinceDefault_Manpower);
                     MapScenarios.scenarioEditorDetails.ProvinceDefault_Manpower = tYear;
                  } catch (Exception var2) {
                     ScenarioSettings.sProvinceDefault_Manpower = "" + MapScenarios.scenarioEditorDetails.ProvinceDefault_Manpower;
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case CREATE_CIV_EDIT:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  CreateCiv.nCiv.Tag = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  CreateCiv.nCiv.Tag = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  CreateCiv.nCiv.Tag = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case GAMECIVS_EDIT:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivsEdit.nCiv.Tag = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivsEdit.nCiv.Tag = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  GameCivsEdit.nCiv.Tag = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case GAMECIVS_EDIT_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivsEdit.nCiv.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivsEdit.nCiv.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  GameCivsEdit.nCiv.Name = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case CREATE_CIV_EDIT_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  CreateCiv.nCiv.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  CreateCiv.nCiv.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  CreateCiv.nCiv.Name = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case GAMECIVS_EDIT_WIKI:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivsEdit.nCiv.Wiki = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivsEdit.nCiv.Wiki = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  GameCivsEdit.nCiv.Wiki = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case CREATE_CIV_EDIT_WIKI:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  CreateCiv.nCiv.Wiki = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  CreateCiv.nCiv.Wiki = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  CreateCiv.nCiv.Wiki = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_NEW_GAME_CIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildNewGameSelectCivilizationsList();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildNewGameSelectCivilizationsList();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildNewGameSelectCivilizationsList();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_IN_GAME_CIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildInGameSelectCivilizationsList();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGameSelectCivilizationsList();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildInGameSelectCivilizationsList();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_SCENARIO_CIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildScenarioCivilizationsList();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildScenarioCivilizationsList();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildScenarioCivilizationsList();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_SCENARIO_CORES_CIVS:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildScenarioCoresList();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildScenarioCoresList();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildScenarioCoresList();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SEARCH_CIV_SELECT:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  GameCivs.chosen_AlphabetCharachter = "";
                  Game.menuManager.rebuildEditorCivSelect();
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildEditorCivSelect();
               }

               @Override
               public void save() {
                  GameCivs.sSearch = Keyboard.keyboardMessage;
                  Game.menuManager.rebuildEditorCivSelect();
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sName = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sName = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sName = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_RULER_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  Game.getCiv(InGame_Court.iActiveCivID).ruler.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  Game.getCiv(InGame_Court.iActiveCivID).ruler.Name = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  Game.getCiv(InGame_Court.iActiveCivID).ruler.Name = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_ADVISOR_ADMINISTRATIVE_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorAdministration.sName = Keyboard.keyboardMessage;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorAdministration.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorAdministration.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_ADVISOR_ECONOMIC_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorEconomy.sName = Keyboard.keyboardMessage;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorEconomy.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorEconomy.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_ADVISOR_INNOVATION_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorTechnology.sName = Keyboard.keyboardMessage;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorTechnology.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorTechnology.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case INGAME_ADVISOR_MILITARY_NAME:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorMilitary.sName = Keyboard.keyboardMessage;
                  } catch (Exception var3) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorMilitary.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }
               }

               @Override
               public void save() {
                  try {
                     Game.getCiv(InGame_Court.iActiveCivID).advisorMilitary.sName = Keyboard.keyboardMessage;
                  } catch (Exception var2) {
                  }

                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_AUTHOR:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sAuthor = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sAuthor = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sAuthor = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_WIKI:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sWiki = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sWiki = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sWiki = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case SCENARIO_TAG:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                  ScenarioSettings.sTag = Keyboard.keyboardMessage;
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "";
                  }

                  ScenarioSettings.sTag = Keyboard.keyboardMessage;
               }

               @Override
               public void save() {
                  ScenarioSettings.sTag = Keyboard.keyboardMessage;
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
         case COLORPICKER:
            keyboardAction = new Keyboard.Keyboard_Action() {
               @Override
               public void actionType(String nChar) {
                  try {
                     if (nChar.charAt(0) >= '0' && nChar.charAt(0) <= '9') {
                        if (Keyboard.keyboardMessage.charAt(0) == '0') {
                           Keyboard.keyboardMessage = nChar;
                        } else {
                           Keyboard.keyboardMessage = Keyboard.keyboardMessage + nChar;
                        }

                        try {
                           if (Integer.parseInt(Keyboard.keyboardMessage) > 255) {
                              Keyboard.keyboardMessage = "255";
                           }
                        } catch (IllegalArgumentException var3) {
                           Keyboard.keyboardMessage = "255";
                        }

                        Game.menuManager.getColorPicker();
                        switch (ColorPicker.activeRGB) {
                           case 0:
                              Game.menuManager.getColorPicker().getActiveColor().r = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                              break;
                           case 1:
                              Game.menuManager.getColorPicker().getActiveColor().g = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                              break;
                           case 2:
                              Game.menuManager.getColorPicker().getActiveColor().b = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                        }

                        Game.menuManager
                           .getColorPicker()
                           .RGBtoHSV(
                              (int)(Game.menuManager.getColorPicker().getActiveColor().r * 255.0F),
                              (int)(Game.menuManager.getColorPicker().getActiveColor().g * 255.0F),
                              (int)(Game.menuManager.getColorPicker().getActiveColor().b * 255.0F)
                           );
                        Game.menuManager.getColorPicker().getColorPickerAction().update();
                        Game.menuManager.getColorPicker().updateRGBWidth();
                     }
                  } catch (IndexOutOfBoundsException var4) {
                  }
               }

               @Override
               public void delete() {
                  if (Keyboard.keyboardMessage.length() > 1) {
                     Keyboard.keyboardMessage = Keyboard.keyboardMessage.substring(0, Keyboard.keyboardMessage.length() - 1);
                  } else {
                     Keyboard.keyboardMessage = "0";
                  }

                  Game.menuManager.getColorPicker();
                  switch (ColorPicker.activeRGB) {
                     case 0:
                        Game.menuManager.getColorPicker().getActiveColor().r = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                        break;
                     case 1:
                        Game.menuManager.getColorPicker().getActiveColor().g = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                        break;
                     case 2:
                        Game.menuManager.getColorPicker().getActiveColor().b = Integer.parseInt(Keyboard.keyboardMessage) / 255.0F;
                  }

                  Game.menuManager
                     .getColorPicker()
                     .RGBtoHSV(
                        (int)(Game.menuManager.getColorPicker().getActiveColor().r * 255.0F),
                        (int)(Game.menuManager.getColorPicker().getActiveColor().g * 255.0F),
                        (int)(Game.menuManager.getColorPicker().getActiveColor().b * 255.0F)
                     );
                  Game.menuManager.getColorPicker().getColorPickerAction().update();
                  Game.menuManager.getColorPicker().updateRGBWidth();
               }

               @Override
               public void save() {
                  Keyboard.this.hideKeyboard();
               }
            };
            return;
      }
   }

   public final void hideKeyboard() {
      if (keyboardMode) {
         try {
            if (keyboardAction != null && keyboardActionType != Keyboard.KeyboardActionType.NONE) {
               Keyboard.KeyboardActionType tempAction = keyboardActionType;
               keyboardActionType = Keyboard.KeyboardActionType.NONE;
               if (tempAction != Keyboard.KeyboardActionType.CONSOLE) {
                  keyboardAction.save();
               }
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }

         keyboardMode = false;
         keyboardMessage = "";
         keyboardVerticalLine = false;
         keyboardActionType = Keyboard.KeyboardActionType.NONE;
         keyboardAction = new Keyboard.Keyboard_Action() {
            @Override
            public void actionType(String nChar) {
            }

            @Override
            public void delete() {
            }

            @Override
            public void save() {
            }
         };
         if (!CFG.isDesktop()) {
            try {
               Gdx.input.setOnscreenKeyboardVisible(false);
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
            }
         }
      }
   }

   public static enum KeyboardActionType {
      NONE,
      SEARCH_GAMECIVS,
      GAMECIVS_EDIT,
      GAMECIVS_EDIT_NAME,
      GAMECIVS_EDIT_WIKI,
      CREATE_CIV_EDIT,
      CREATE_CIV_EDIT_NAME,
      CREATE_CIV_EDIT_WIKI,
      SEARCH_IN_GAME_CIVS,
      SEARCH_NEW_GAME_CIVS,
      SEARCH_SCENARIO_CIVS,
      SEARCH_SCENARIO_CORES_CIVS,
      SEARCH_CIV_SELECT,
      CONSOLE,
      SAVE_NAME,
      SCENARIO_YEAR,
      SEARCH_COURT_PROVINCES,
      SEARCH_COURT_ALL_PROVINCES,
      SEARCH_COURT_ALL_CIVS,
      SEARCH_ENCYCLOPEDIA,
      SEARCH_RANKING,
      INGAME_RENAME_CIV,
      INGAME_RENAME_PROVINCE,
      INGAME_RULER_NAME,
      INGAME_ADVISOR_ADMINISTRATIVE_NAME,
      INGAME_ADVISOR_ECONOMIC_NAME,
      INGAME_ADVISOR_INNOVATION_NAME,
      INGAME_ADVISOR_MILITARY_NAME,
      SCENARIO_GOLD,
      SCENARIO_GOLD_RANDOM,
      SCENARIO_LEGACY,
      SCENARIO_LEGACY_RANDOM,
      SCENARIO_MANPOWER_PERC,
      SCENARIO_PROVINCE_POPULATION,
      SCENARIO_PROVINCE_ECONOMY,
      SCENARIO_PROVINCE_TAX_EFFICIENCY,
      SCENARIO_PROVINCE_MANPOWER,
      SCENARIO_CIV_POPULATION,
      SCENARIO_CIV_ECONOMY,
      SCENARIO_CIV_TAX_EFFICIENCY,
      SCENARIO_CIV_MANPOWER,
      SCENARIO_CIV_GOLD,
      SCENARIO_CIV_LEGACY,
      SCENARIO_CIV_AI_AGGRESSIVENESS,
      SCENARIO_CIV_NUKES,
      SCENARIO_CREATE_ALLIANCE_NAME,
      SCENARIO_CREATE_ALLIANCE_LEADER,
      SCENARIO_CREATE_ALLIANCE_FIRST_TIER,
      SCENARIO_CREATE_ALLIANCE_REST,
      SCENARIO_CREATE_ALLIANCE_FLAG,
      SCENARIO_NAME,
      SCENARIO_AUTHOR,
      SCENARIO_TAG,
      SCENARIO_WIKI,
      LOBBY_NAME,
      COLORPICKER;
   }

   public interface Keyboard_Action {
      void actionType(String var1);

      void delete();

      void save();
   }
}
