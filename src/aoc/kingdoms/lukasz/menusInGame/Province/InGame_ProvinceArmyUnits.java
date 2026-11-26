package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.ColonizationManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmy;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Vertical;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battlefield;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmyUnits extends Menu {
   public static int mPosX = 0;
   public static int mPosY = 0;
   public static int mWidth = 0;
   public static String key = "";
   public static int iActiveID = 0;
   public static int tDivID = 0;

   public InGame_ProvinceArmyUnits() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING;
      int buttonY = CFG.PADDING;
      int buttonX_StatsPos = CFG.PADDING;
      int generalPadding = CFG.PADDING * 3;
      int defaultXArmyPos = ImageManager.getImage(Images.generalFrame).getWidth() + generalPadding * 2;
      if (Game.activeArmySize > 0) {
         try {
            if (tDivID >= Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmySize()) {
               tDivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmyKeyID(Game.activeArmy.get(iActiveID).key);
            }

            if (tDivID >= 0) {
               try {
                  if (GameValues.info.ENABLE_PROVINCE_ARMY_COMPOSITION_INFO
                     && Game_Calendar.TURN_ID < GameValues.info.ENABLE_PROVINCE_ARMY_COMPOSITION_INFO_UNTIL_TURN_ID) {
                     int infoCivID = Game.activeArmy.get(iActiveID).iCivID;
                     if (infoCivID == Game.player.iCivID) {
                        int armyFirstLine = 0;
                        int armySecondLine = 0;

                        for (int i = 0; i < Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize; i++) {
                           if (!ArmyManager.lArmy
                                 .get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(i).uID)
                                 .get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(i).aID)
                              .isSettler) {
                              if (ArmyManager.lUnitsTypes
                                       .get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(i).uID)
                                    .Line
                                 < 2) {
                                 armyFirstLine++;
                              } else if (ArmyManager.lUnitsTypes
                                       .get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(i).uID)
                                    .Line
                                 == 2) {
                                 armySecondLine++;
                              }
                           }
                        }

                        boolean addInformation = false;
                        int infoBattleWidth = BattleManager.getBattleWidth(infoCivID);
                        if (armyFirstLine < infoBattleWidth && armySecondLine < infoBattleWidth) {
                           if (armySecondLine > armyFirstLine + Math.max(3.0, Math.ceil(Math.max(armyFirstLine, armySecondLine) * 0.2F))) {
                              addInformation = true;
                           } else if (armyFirstLine > armySecondLine + Math.max(3.0, Math.ceil(Math.max(armyFirstLine, armySecondLine) * 0.2F))) {
                              addInformation = true;
                           } else if (armyFirstLine == 0 && armySecondLine > 3) {
                              addInformation = true;
                           } else if (armySecondLine == 0 && armyFirstLine > 3) {
                              addInformation = true;
                           }
                        } else if (armyFirstLine >= infoBattleWidth && armySecondLine - 4 < infoBattleWidth) {
                           addInformation = true;
                        } else if (armySecondLine >= infoBattleWidth && armyFirstLine - 4 < infoBattleWidth) {
                           addInformation = true;
                        }

                        if (addInformation) {
                           buttonX_StatsPos = 0;
                           buttonX = buttonX_StatsPos;
                           int infoH = ButtonArmy.getButtonHeight() * 2 + CFG.PADDING;
                           int infoW = CFG.TEXT_HEIGHT + CFG.PADDING * 2;
                           menuElements.add(
                              new TextIcon_Vertical(Game.lang.get("ArmyComposition"), Images.information, 0, buttonX_StatsPos, buttonY, infoW, infoH) {
                                 @Override
                                 public void actionElement() {
                                    try {
                                       if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                                          Game.menuManager.setVisibleInGame_PopUp(false);
                                       } else {
                                          InGame_Battlefield.armyDivision = Game.getProvince(
                                                Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID
                                             )
                                             .getArmy(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).key);
                                          if (InGame_Battlefield.armyDivision != null) {
                                             Game.menuManager.rebuildInGame_Battlefield();
                                          } else {
                                             Game.menuManager.setVisibleInGame_PopUp(false);
                                          }
                                       }
                                    } catch (Exception var2) {
                                       CFG.exceptionStack(var2);
                                    }
                                 }

                                 @Override
                                 public void buildElementHover() {
                                    List<MenuElement_HoverElement> nElements = new ArrayList<>();
                                    List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                                    nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ArmyDeployment"), Colors.HOVER_GOLD));
                                    nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battle, CFG.PADDING, 0));
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();

                                    try {
                                       int armyFirstLinex = 0;
                                       int armySecondLinex = 0;

                                       for (int ix = 0;
                                          ix
                                             < Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID)
                                                .getArmy(InGame_ProvinceArmyUnits.tDivID)
                                                .iArmyRegimentSize;
                                          ix++
                                       ) {
                                          if (!ArmyManager.lArmy
                                                .get(
                                                   Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID)
                                                         .getArmy(InGame_ProvinceArmyUnits.tDivID)
                                                         .lArmyRegiment
                                                         .get(ix)
                                                      .uID
                                                )
                                                .get(
                                                   Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID)
                                                         .getArmy(InGame_ProvinceArmyUnits.tDivID)
                                                         .lArmyRegiment
                                                         .get(ix)
                                                      .aID
                                                )
                                             .isSettler) {
                                             if (ArmyManager.lUnitsTypes
                                                      .get(
                                                         Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID)
                                                               .getArmy(InGame_ProvinceArmyUnits.tDivID)
                                                               .lArmyRegiment
                                                               .get(ix)
                                                            .uID
                                                      )
                                                   .Line
                                                < 2) {
                                                armyFirstLinex++;
                                             } else if (ArmyManager.lUnitsTypes
                                                      .get(
                                                         Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iProvinceID)
                                                               .getArmy(InGame_ProvinceArmyUnits.tDivID)
                                                               .lArmyRegiment
                                                               .get(ix)
                                                            .uID
                                                      )
                                                   .Line
                                                == 2) {
                                                armySecondLinex++;
                                             }
                                          }
                                       }

                                       int infoCivIDx = Game.activeArmy.get(InGame_ProvinceArmyUnits.iActiveID).iCivID;
                                       boolean negativeFirstLine = false;
                                       int infoBattleWidthx = BattleManager.getBattleWidth(infoCivIDx);
                                       if (armyFirstLinex < infoBattleWidthx && armySecondLinex < infoBattleWidthx) {
                                          if (armySecondLinex > armyFirstLinex - 3) {
                                             negativeFirstLine = true;
                                          } else if (armyFirstLinex == 0 && armySecondLinex > 1) {
                                             negativeFirstLine = true;
                                          } else if (armySecondLinex == 0 && armyFirstLinex > 4) {
                                             negativeFirstLine = false;
                                          }
                                       } else if (armyFirstLinex >= infoBattleWidthx && armySecondLinex - 4 < infoBattleWidthx) {
                                          negativeFirstLine = false;
                                       } else if (armySecondLinex >= infoBattleWidthx && armyFirstLinex - 4 < infoBattleWidthx) {
                                          negativeFirstLine = true;
                                       }

                                       nData.add(
                                          new MenuElement_HoverElement_Type_Button_TextBonus(
                                             Game.lang.get("FirstLine") + ": ",
                                             "" + armyFirstLinex,
                                             Game_Calendar.IMG_MANPOWER,
                                             CFG.FONT_BOLD,
                                             CFG.FONT_BOLD,
                                             Colors.HOVER_LEFT,
                                             negativeFirstLine ? Colors.HOVER_NEGATIVE : Colors.HOVER_GOLD
                                          )
                                       );
                                       nElements.add(new MenuElement_HoverElement(nData));
                                       nData.clear();
                                       nData.add(
                                          new MenuElement_HoverElement_Type_Button_TextBonus(
                                             Game.lang.get("SecondLine") + ": ",
                                             "" + armySecondLinex,
                                             Game_Calendar.IMG_MANPOWER,
                                             CFG.FONT_BOLD,
                                             CFG.FONT_BOLD,
                                             Colors.HOVER_LEFT,
                                             !negativeFirstLine ? Colors.HOVER_NEGATIVE : Colors.HOVER_GOLD
                                          )
                                       );
                                       nElements.add(new MenuElement_HoverElement(nData));
                                       nData.clear();
                                       nData.add(
                                          new MenuElement_HoverElement_Type_Button_TextBonus(
                                             Game.lang.get("BattleWidth") + ": ",
                                             "" + BattleManager.getBattleWidth(infoCivIDx),
                                             Images.battleWidth,
                                             CFG.FONT_BOLD,
                                             CFG.FONT_BOLD,
                                             Colors.HOVER_LEFT,
                                             Colors.HOVER_GOLD
                                          )
                                       );
                                       nElements.add(new MenuElement_HoverElement(nData));
                                       nData.clear();
                                       nData.add(new MenuElement_HoverElement_Type_Line());
                                       nElements.add(new MenuElement_HoverElement(nData));
                                       nData.clear();
                                    } catch (Exception var8) {
                                    }

                                    nData.add(
                                       new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine1"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                                    );
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();
                                    nData.add(new MenuElement_HoverElement_Type_Line());
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();
                                    nData.add(
                                       new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine2"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                                    );
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();
                                    nData.add(new MenuElement_HoverElement_Type_Line());
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();
                                    nData.add(
                                       new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine3"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                                    );
                                    nElements.add(new MenuElement_HoverElement(nData));
                                    nData.clear();
                                    this.menuElementHover = new MenuElement_Hover(nElements);
                                 }
                              }
                           );
                           buttonX_StatsPos += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                           buttonX = buttonX_StatsPos;
                        }
                     }
                  }
               } catch (Exception var14) {
                  CFG.exceptionStack(var14);
               }

               for (int ix = 0; ix < Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize; ix++) {
                  if (ArmyManager.lUnitsTypes.get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).uID).Line
                     < 2) {
                     int tUnits = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).num;
                     int numOfRegiments = 1;

                     for (int o = ix + 1;
                        o < Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize
                           && Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).uID
                              == Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(o).uID
                           && Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).aID
                              == Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(o).aID;
                        o++
                     ) {
                        ix++;
                        numOfRegiments++;
                        tUnits += Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).num;
                     }

                     int checkCivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID;
                     String textUnits = "";
                     float fPerc = -1.0F;
                     if (Game.FOG_OF_WAR
                        && GameValues.fog.HIDE_MANPOWER
                        && checkCivID != Game.player.iCivID
                        && !DiplomacyManager.isAlly(checkCivID, Game.player.iCivID)) {
                        textUnits = GameValues.fog.TEXT_UNKNOWN_MANPOWER;
                     } else {
                        textUnits = "" + tUnits;
                        fPerc = (float)tUnits / (numOfRegiments * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE);
                     }

                     menuElements.add(
                        new ButtonArmy(
                           textUnits,
                           numOfRegiments,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID,
                           buttonX,
                           buttonY,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).uID,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ix).aID,
                           ix,
                           Game.activeArmy.get(iActiveID).iProvinceID,
                           fPerc
                        ) {
                           @Override
                           public void actionElement() {
                              if (this.iCivID == Game.player.iCivID) {
                                 if (ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).isSettler) {
                                    int settlementEstablished = -1;

                                    for (int i = Game.activeArmySize - 1; i >= 0; i--) {
                                       if (Game.getProvince(Game.activeArmy.get(i).iProvinceID).getCivID() == 0) {
                                          int armyID = Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmyKeyID(Game.activeArmy.get(i).key);
                                          if (armyID >= 0
                                             && Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmy(armyID).civID == Game.player.iCivID
                                             && ColonizationManager.establishSettlement(Game.player.iCivID, Game.activeArmy.get(i).iProvinceID, 0)) {
                                             Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                                             InGame_Info.iCivID = Game.player.iCivID;
                                             InGame_Info.iCivID2 = 0;
                                             Game.menuManager
                                                .rebuildInGame_Info(
                                                   Game.lang.get("SettlementEstablished")
                                                      + ": "
                                                      + Game.getProvince(Game.activeArmy.get(i).iProvinceID).getProvinceName(),
                                                   Game.lang.get("Population")
                                                      + ": "
                                                      + CFG.getNumberWithSpaces("" + Game.getProvince(Game.activeArmy.get(i).iProvinceID).getPopulationTotal())
                                                );
                                             InGame_Info.imgID = Images.infoCrown;
                                             settlementEstablished = Game.activeArmy.get(i).iProvinceID;
                                          }
                                       }
                                    }

                                    if (settlementEstablished >= 0) {
                                       Game.clearActiveArmy();
                                       Game.setActiveProvinceID(settlementEstablished);
                                       Game.menuManager.rebuildInGame_ProvinceInfo(true);
                                    }
                                 } else {
                                    Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(this.iCivID, InGame_ProvinceArmyUnits.key);
                                    if (nArmyPos != null) {
                                       InGame_RecruitArmy.key = InGame_ProvinceArmyUnits.key;
                                       Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                                       Game.menuManager.rebuildInGame_RecruitSameType();
                                       return;
                                    }
                                 }
                              }
                           }
                        }
                     );
                     buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  }
               }

               buttonX = buttonX_StatsPos;
               buttonY += ButtonArmy.getButtonHeight() + CFG.PADDING;

               for (int ixx = 0; ixx < Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize; ixx++) {
                  if (ArmyManager.lUnitsTypes.get(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).uID).Line
                     >= 2) {
                     int tUnits = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).num;
                     int numOfRegiments = 1;

                     for (int o = ixx + 1;
                        o < Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize
                           && Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).uID
                              == Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(o).uID
                           && Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).aID
                              == Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(o).aID;
                        o++
                     ) {
                        ixx++;
                        numOfRegiments++;
                        tUnits += Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).num;
                     }

                     int checkCivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID;
                     String textUnits = "";
                     float fPerc = -1.0F;
                     if (Game.FOG_OF_WAR
                        && GameValues.fog.HIDE_MANPOWER
                        && checkCivID != Game.player.iCivID
                        && !DiplomacyManager.isAlly(checkCivID, Game.player.iCivID)) {
                        textUnits = GameValues.fog.TEXT_UNKNOWN_MANPOWER;
                     } else {
                        textUnits = "" + tUnits;
                        fPerc = (float)tUnits / (numOfRegiments * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE);
                     }

                     menuElements.add(
                        new ButtonArmy(
                           textUnits,
                           numOfRegiments,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID,
                           buttonX,
                           buttonY,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).uID,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).lArmyRegiment.get(ixx).aID,
                           ixx,
                           Game.activeArmy.get(iActiveID).iProvinceID,
                           fPerc
                        ) {
                           @Override
                           public void actionElement() {
                              if (this.iCivID == Game.player.iCivID) {
                                 if (ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).isSettler) {
                                    int settlementEstablished = -1;

                                    for (int i = Game.activeArmySize - 1; i >= 0; i--) {
                                       if (Game.getProvince(Game.activeArmy.get(i).iProvinceID).getCivID() == 0) {
                                          int armyID = Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmyKeyID(Game.activeArmy.get(i).key);
                                          if (armyID >= 0
                                             && Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmy(armyID).civID == Game.player.iCivID
                                             && ColonizationManager.establishSettlement(Game.player.iCivID, Game.activeArmy.get(i).iProvinceID, 0)) {
                                             Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                                             InGame_Info.iCivID = Game.player.iCivID;
                                             InGame_Info.iCivID2 = 0;
                                             Game.menuManager
                                                .rebuildInGame_Info(
                                                   Game.lang.get("SettlementEstablished")
                                                      + ": "
                                                      + Game.getProvince(Game.activeArmy.get(i).iProvinceID).getProvinceName(),
                                                   Game.lang.get("Population")
                                                      + ": "
                                                      + CFG.getNumberWithSpaces("" + Game.getProvince(Game.activeArmy.get(i).iProvinceID).getPopulationTotal())
                                                );
                                             InGame_Info.imgID = Images.infoCrown;
                                             settlementEstablished = Game.activeArmy.get(i).iProvinceID;
                                          }
                                       }
                                    }

                                    if (settlementEstablished >= 0) {
                                       Game.clearActiveArmy();
                                       Game.setActiveProvinceID(settlementEstablished);
                                       Game.menuManager.rebuildInGame_ProvinceInfo(true);
                                    }
                                 } else {
                                    Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(this.iCivID, InGame_ProvinceArmyUnits.key);
                                    if (nArmyPos != null) {
                                       InGame_RecruitArmy.key = InGame_ProvinceArmyUnits.key;
                                       Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                                       Game.menuManager.rebuildInGame_RecruitSameType();
                                       return;
                                    }
                                 }
                              }
                           }
                        }
                     );
                     buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  }
               }
            }
         } catch (Exception var15) {
            CFG.exceptionStack(var15);
         }
      }

      buttonY = 0;
      int ixxx = 0;

      for (int iSize = menuElements.size(); ixxx < iSize; ixxx++) {
         if (buttonY < menuElements.get(ixxx).getPosY() + menuElements.get(ixxx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixxx).getPosY() + menuElements.get(ixxx).getHeight() + CFG.PADDING;
         }
      }

      ixxx = Math.max(CFG.PADDING, buttonY);
      this.initMenu(null, mPosX, mPosY, mWidth, ixxx, menuElements, false);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameProvinceArmy();
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_ProvinceArmy.nTranslateY;
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_ProvinceArmy.nTranslateY + InGame_ProvinceArmy.chooseProvinceExtraY;
   }

   @Override
   public int getPosX() {
      return super.getPosX() + InGame_ProvinceArmy.nTranslateX;
   }

   @Override
   public int getMenuPosX() {
      return super.getMenuPosX() + InGame_ProvinceArmy.nTranslateX;
   }
}
