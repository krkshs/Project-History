package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_Flag;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyID;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus_Perc;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Siege;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battlefield;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmy extends Menu {
   public static int nTranslateX;
   public static int nTranslateY;
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String key = "";
   public static int iCivFlagID = 0;
   public static int iProvinceID = 0;
   public static int iActiveID = 0;
   public static String sActiveKEY = "";
   public static final int ANIMATION_TIME_MOVE = 150;
   public static long lTimeMOVE = 0L;
   public int imageOverID = 0;
   public static boolean besiegesProvince = false;
   public static int chooseProvinceExtraY = 0;

   public InGame_ProvinceArmy() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title530).getHeight();
      int menuWidth = ImageManager.getImage(Images.title530).getWidth() - Images.boxTitleBORDERWIDTH;
      int menuMinHeight = ImageManager.getImage(Images.armyCampOver0).getHeight();
      int menuHeight = 240;
      int menuX = 0;
      int menuY = CFG.GAME_HEIGHT - menuHeight;
      int buttonYPadding = CFG.PADDING * 2;
      int generalPadding = CFG.PADDING * 2;
      int defaultXArmyPos = ImageManager.getImage(Images.generalFrame).getWidth() + generalPadding * 2;
      String sArmyStatus = "";
      besiegesProvince = false;

      try {
         if (Game.activeArmySize > 0) {
            try {
               if (iActiveID >= Game.activeArmySize) {
                  iActiveID = 0;
                  sActiveKEY = Game.activeArmy.get(iActiveID).key;
               }

               int tDivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmyKeyID(Game.activeArmy.get(iActiveID).key);
               InGame_ProvinceArmyUnits.iActiveID = iActiveID;
               InGame_ProvinceArmyUnits.tDivID = tDivID;
               InGame_ProvinceArmyUnits.key = Game.activeArmy.get(iActiveID).key;
               iCivFlagID = Game.activeArmy.get(iActiveID).iCivID;
               iProvinceID = Game.activeArmy.get(iActiveID).iProvinceID;
               if (tDivID >= 0) {
                  key = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).key;
               } else {
                  key = "";
               }

               int buttonY = ButtonArmyID.getButtonHeight() + buttonYPadding + CFG.PADDING;
               if (tDivID >= 0) {
                  if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral == null) {
                     menuElements.add(
                        new ButtonArmyNoGeneral(Game.lang.get("NoGeneral"), iCivFlagID, generalPadding, buttonY) {
                           @Override
                           public void actionElementPPM() {
                              InGame_ProvinceArmy.centerToArmy();
                           }

                           @Override
                           public void actionElement() {
                              if (Game.menuManager.getVisibleInGame_Generals()) {
                                 Game.menuManager.setVisibleInGame_Generals(false);
                              } else {
                                 int tDivIDx = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
                                    .getArmyKeyID(InGame_ProvinceArmy.key);
                                 if (tDivIDx >= 0) {
                                    InGame_Generals.assignProvinceID = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID;
                                    InGame_Generals.assignArmyKey = InGame_ProvinceArmy.key;
                                 } else {
                                    InGame_Generals.assignProvinceID = -1;
                                    Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
                                 }

                                 if (Game.getCiv(Game.player.iCivID).getGeneralsNotAssignedSize() != 0
                                    || Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivIDx).civID
                                       != Game.player.iCivID) {
                                    InGame_Generals.backButton = false;
                                    Game.menuManager.rebuildInGame_Generals();
                                    Game.menuManager.setVisibleInGame_Generals(true);
                                 } else if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
                                    Game.menuManager.setVisibleInGame_GeneralRecruit(false);
                                 } else {
                                    Game.menuManager.rebuildInGame_GeneralRecruit();
                                 }
                              }
                           }
                        }
                     );
                  } else {
                     menuElements.add(
                        new ButtonArmyGeneral(
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.n,
                           iCivFlagID,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.getAttack(),
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.getDefense(),
                           generalPadding,
                           buttonY,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.g,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.d,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.m,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.y,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.sI,
                           Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).armyGeneral.getCombatExperience()
                        ) {
                           @Override
                           public void actionElementPPM() {
                              InGame_ProvinceArmy.centerToArmy();
                           }

                           @Override
                           public void actionElement() {
                              if (Game.menuManager.getVisibleInGame_Generals()) {
                                 Game.menuManager.setVisibleInGame_Generals(false);
                              } else {
                                 int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
                                    .getArmyKeyID(InGame_ProvinceArmy.key);
                                 if (tDivID >= 0) {
                                    InGame_Generals.assignProvinceID = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID;
                                    InGame_Generals.assignArmyKey = InGame_ProvinceArmy.key;
                                 } else {
                                    InGame_Generals.assignProvinceID = -1;
                                    Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
                                 }

                                 InGame_Generals.backButton = false;
                                 Game.menuManager.rebuildInGame_Generals();
                                 Game.menuManager.setVisibleInGame_Generals(true);
                              }
                           }
                        }
                     );
                  }

                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                  int maxIconWidth = ImageManager.getImage(Images.morale).getWidth();
                  if (maxIconWidth < ImageManager.getImage(Images.gold).getWidth()) {
                     maxIconWidth = ImageManager.getImage(Images.gold).getWidth();
                  }

                  if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inMovement) {
                     int toProvinceID = -1;

                     for (int i = 0;
                        i < Game.getCiv(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID).getMoveUnitsSize();
                        i++
                     ) {
                        if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID)
                           .getArmy(tDivID)
                           .key
                           .equals(Game.getCiv(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID).getMoveUnits(i).key)) {
                           toProvinceID = Game.getCiv(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID)
                              .getMoveUnits(i)
                              .getToProvinceLastID();
                           break;
                        }
                     }

                     if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inBattle) {
                        sArmyStatus = Game.lang.get("ArmyIsFightingInABattle");
                     } else if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inRetreat) {
                        if (toProvinceID >= 0) {
                           sArmyStatus = Game.lang.get("ArmyIsRetreating") + ": " + Game.getProvince(toProvinceID).getProvinceName();
                        } else {
                           sArmyStatus = Game.lang.get("ArmyIsRetreating");
                        }
                     } else if (toProvinceID >= 0) {
                        sArmyStatus = Game.lang.get("MovingTo") + ": " + Game.getProvince(toProvinceID).getProvinceName();
                     } else {
                        sArmyStatus = Game.lang.get("Moving");
                     }

                     if (Game.getProvince(iProvinceID).getSeaProvince()) {
                        if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 0) {
                           this.imageOverID = Images.armyAtSea0;
                        } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 1) {
                           this.imageOverID = Images.armyAtSea1;
                        } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 2) {
                           this.imageOverID = Images.armyAtSea2;
                        } else {
                           this.imageOverID = Images.armyAtSea3;
                        }
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 0) {
                        this.imageOverID = Images.armyMove0;
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 1) {
                        this.imageOverID = Images.armyMove1;
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 2) {
                        this.imageOverID = Images.armyMove2;
                     } else {
                        this.imageOverID = Images.armyMove3;
                     }
                  } else {
                     if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inBattle) {
                        sArmyStatus = Game.lang.get("ArmyIsFightingInABattle");
                     } else if (Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inRetreat) {
                        sArmyStatus = Game.lang.get("ArmyIsRetreating");
                     } else if (!Game.getProvinceData4(Game.activeArmy.get(iActiveID).iProvinceID).isUnderSiege()
                        || !DiplomacyManager.isAtWar(
                              Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getCivID(), Game.activeArmy.get(iActiveID).iCivID
                           )
                           && !DiplomacyManager.isAtWar(
                              Game.getProvinceData(Game.activeArmy.get(iActiveID).iProvinceID).getOccupiedByCivID(), Game.activeArmy.get(iActiveID).iCivID
                           )) {
                        sArmyStatus = Game.lang.get("WaitingForOrders");
                     } else {
                        sArmyStatus = Game.lang.get("ArmyBesiegesProvince");
                        besiegesProvince = true;
                     }

                     if (Game.getProvince(iProvinceID).getSeaProvince()) {
                        if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 0) {
                           this.imageOverID = Images.armyAtSea0;
                        } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 1) {
                           this.imageOverID = Images.armyAtSea1;
                        } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 2) {
                           this.imageOverID = Images.armyAtSea2;
                        } else {
                           this.imageOverID = Images.armyAtSea3;
                        }
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 0) {
                        this.imageOverID = Images.armyCampOver0;
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 1) {
                        this.imageOverID = Images.armyCampOver1;
                     } else if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID == 2) {
                        this.imageOverID = Images.armyCampOver2;
                     } else {
                        this.imageOverID = Images.armyCampOver3;
                     }
                  }

                  menuElements.add(
                     new TextIcon_Siege(
                        "",
                        iProvinceID,
                        Images.siege,
                        generalPadding,
                        buttonY,
                        ImageManager.getImage(Images.generalFrame).getWidth(),
                        (CFG.TEXT_HEIGHT + CFG.PADDING * 4) * 2 + CFG.PADDING,
                        ImageManager.getImage(Images.siege).getWidth()
                     ) {
                        @Override
                        public void buildElementHover() {
                           this.menuElementHover = InGame_ProvinceSiege.getHoverSiege(this.iProvinceID);
                        }

                        @Override
                        public boolean getVisible() {
                           return InGame_ProvinceArmy.besiegesProvince;
                        }

                        @Override
                        public void actionElement() {
                           Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                           if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
                              Game.menuManager.setVisibleInGame_GeneralRecruit(false);
                           }

                           InGame_ProvinceSiege.iProvinceID = this.iProvinceID;
                           Game.menuManager.rebuildInGame_Siege();
                           Game.clearActiveArmy();
                        }

                        @Override
                        public int getSFX() {
                           return SoundsManager.SIEGE;
                        }

                        @Override
                        protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                           DiplomacyManager.updateInAnimation();
                           super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                        }
                     }
                  );
                  int checkCivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).civID;
                  if (Game.FOG_OF_WAR
                     && GameValues.fog.HIDE_MANPOWER
                     && checkCivID != Game.player.iCivID
                     && !DiplomacyManager.isAlly(checkCivID, Game.player.iCivID)) {
                     menuElements.add(
                        new ButtonStatsRectIMG(
                           GameValues.fog.TEXT_UNKNOWN_MANPOWER,
                           Images.gold,
                           generalPadding,
                           buttonY,
                           ImageManager.getImage(Images.generalFrame).getWidth(),
                           CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                           maxIconWidth
                        ) {
                           @Override
                           public void buildElementHover() {
                              List<MenuElement_HoverElement> nElements = new ArrayList<>();
                              List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                              nData.add(
                                 new MenuElement_HoverElement_Type_Button_TextBonus(
                                    Game.lang.get("MaintenanceCost") + ": ",
                                    Game.lang.get("XPerYear", GameValues.fog.TEXT_UNKNOWN_MANPOWER),
                                    Images.gold,
                                    CFG.FONT_BOLD,
                                    CFG.FONT_BOLD,
                                    Colors.HOVER_LEFT,
                                    Colors.HOVER_GOLD
                                 )
                              );
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                              this.menuElementHover = new MenuElement_Hover(nElements);
                           }

                           @Override
                           public boolean getVisible() {
                              return !InGame_ProvinceArmy.besiegesProvince;
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                  } else {
                     menuElements.add(
                        new ButtonStatsRectIMG(
                           CFG.getPrecision2(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).fMorale * 100.0F, 1) + "%",
                           Images.morale,
                           generalPadding,
                           buttonY,
                           ImageManager.getImage(Images.generalFrame).getWidth(),
                           CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                           maxIconWidth
                        ) {
                           @Override
                           public void actionElement() {
                              try {
                                 if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                                    Game.menuManager.setVisibleInGame_PopUp(false);
                                 } else {
                                    InGame_Battlefield.armyDivision = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
                                       .getArmy(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key);
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
                              nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ArmyDeployment"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                              nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battleWidth, CFG.PADDING, 0));
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                              ArmyDivision armyDivision = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
                                 .getArmyKey(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key);
                              float perc = 1.0F;
                              if (armyDivision != null) {
                                 if (armyDivision.civID > 0) {
                                    perc = armyDivision.fMorale / Game.getCiv(armyDivision.civID).getMaxMorale();
                                 } else {
                                    perc = Math.min(1.0F, armyDivision.fMorale / 1.0F);
                                 }
                              }

                              nData.add(
                                 new MenuElement_HoverElement_Type_Button_TextBonus_Perc(
                                    Game.lang.get("Morale") + ": ",
                                    this.getText() + " / " + CFG.getPrecision2(Game.getCiv(InGame_ProvinceArmy.iCivFlagID).getMaxMorale() * 100.0F, 1) + "%",
                                    Images.morale,
                                    CFG.FONT_BOLD,
                                    CFG.FONT_BOLD,
                                    Colors.HOVER_LEFT,
                                    Colors.HOVER_GOLD,
                                    perc
                                 )
                              );
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                              this.menuElementHover = new MenuElement_Hover(nElements);
                           }

                           @Override
                           public boolean getVisible() {
                              return !InGame_ProvinceArmy.besiegesProvince;
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                  }

                  menuElements.add(
                     new ButtonStatsRectIMG(
                        CFG.getPrecision2(Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).getArmyMovementSpeed(), 100),
                        Images.movementSpeed,
                        generalPadding,
                        buttonY,
                        ImageManager.getImage(Images.generalFrame).getWidth(),
                        CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                        maxIconWidth
                     ) {
                        @Override
                        public void buildElementHover() {
                           List<MenuElement_HoverElement> nElements = new ArrayList<>();
                           List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                           nData.add(
                              new MenuElement_HoverElement_Type_Button_TextBonus(
                                 Game.lang.get("MovementSpeed") + ": ",
                                 this.getText(),
                                 Images.movementSpeed,
                                 CFG.FONT_BOLD,
                                 CFG.FONT_BOLD,
                                 Colors.HOVER_LEFT,
                                 Colors.HOVER_GOLD
                              )
                           );
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           this.menuElementHover = new MenuElement_Hover(nElements);
                        }

                        @Override
                        public boolean getVisible() {
                           return !InGame_ProvinceArmy.besiegesProvince;
                        }
                     }
                  );
               }
            } catch (Exception var19) {
               CFG.exceptionStack(var19);
            }
         }
      } catch (Exception var20) {
         CFG.exceptionStack(var20);
      }

      int var25 = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (var25 < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING) {
            var25 = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING;
         }
      }

      menuHeight = Math.max(menuMinHeight, var25);
      menuY = CFG.GAME_HEIGHT - menuHeight;
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(var25, menuHeight)));
      InGame_ProvinceArmyUnits.mPosX = defaultXArmyPos - CFG.PADDING;
      InGame_ProvinceArmyUnits.mPosY = menuY + ButtonArmyID.getButtonHeight() + buttonYPadding;
      InGame_ProvinceArmyUnits.mWidth = menuWidth - InGame_ProvinceArmyUnits.mPosX;
      InGame_ProvinceArmyTopBar.mPosX = menuX;
      InGame_ProvinceArmyTopBar.mPosY = menuY;
      InGame_ProvinceArmyTopBar.mWidth = menuWidth;
      this.initMenu(new MenuTitleIMG_Flag(Game.getProvince(iProvinceID).getProvinceName(), sArmyStatus, false, false, Images.title530) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            super.draw(oSB, nPosX, nPosY, nWidth + Images.boxTitleBORDERWIDTH, titleStatus);
         }

         @Override
         public int getFlagCivID() {
            return InGame_ProvinceArmy.iCivFlagID;
         }

         @Override
         public long getTime() {
            return InGame_ProvinceArmy.lTime;
         }

         @Override
         public void onHovered() {
            Game.menuManager.setOrderOfMenu_InGameProvinceArmy();
         }

         @Override
         public void action() {
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         iTranslateY += (int)(CFG.BUTTON_HEIGHT / 2.0F - CFG.BUTTON_HEIGHT / 2 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      if (Game.chooseProvinceMode && chooseProvinceExtraY < this.getHeight()) {
         chooseProvinceExtraY = (int)Math.min((float)this.getHeight(), this.getHeight() * ((float)(CFG.currentTimeMillis - lTimeMOVE) / 150.0F));
      }

      nTranslateX = iTranslateX;
      nTranslateY = iTranslateY;
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH,
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth() + Images.boxTitleBORDERWIDTH,
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop530,
         Images.insideBot530
      );
      ImageManager.getImage(this.imageOverID)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(this.imageOverID).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (besiegesProvince && !Game.getProvinceData4(iProvinceID).isUnderSiege()) {
         this.getTitle().setText(Game.lang.get("WaitingForOrders"));
         besiegesProvince = false;
      }

      try {
         if (Game.activeArmySize == 0) {
            Game.addSimpleTask(new Game.SimpleTask("setVisibleInGame_ProvinceArmy") {
               @Override
               public void update() {
                  Game.menuManager.setVisibleInGame_ProvinceArmy(false);
               }
            });
         } else if (iActiveID >= Game.activeArmySize) {
            iActiveID = 0;
            Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_ProvinceArmy") {
               @Override
               public void update() {
                  Game.menuManager.rebuildInGame_ProvinceArmy();
               }
            });
         } else {
            int tDivID = Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmyKeyID(Game.activeArmy.get(iActiveID).key);
            if (tDivID >= 0 && Game.getProvince(Game.activeArmy.get(iActiveID).iProvinceID).getArmy(tDivID).inBattle) {
               InGame_Battle.iProvinceID = Game.activeArmy.get(iActiveID).iProvinceID;
               InGame_Battle.key = Game.battleManager.getArmyBattleKey(Game.activeArmy.get(iActiveID).iProvinceID, Game.activeArmy.get(iActiveID).key);
               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Battle") {
                  @Override
                  public void update() {
                     Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                     Game.menuManager.rebuildInGame_Battle();
                  }
               });
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      if (Game.checkActiveArmy_Fog()) {
         Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_ProvinceArmy") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_ProvinceArmy();
            }
         });
      }
   }

   @Override
   public void setVisible(boolean visible) {
      if (!this.getVisible()) {
         lTime = CFG.currentTimeMillis;
      }

      if (!visible) {
         if (Game.invasionArmyMode) {
            Game.invasionArmyMode = false;
         }

         if (Game.regroupArmyMode) {
            Game.regroupArmyMode = false;
         }

         if (CFG.brushTool) {
            CFG.brushTool = false;
         }
      }

      super.setVisible(visible);
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.clearActiveArmy();
      Game.menuManager.setVisibleInGame_ProvinceArmy(false);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameProvinceArmy();
   }

   @Override
   public int getPosY() {
      if (!Game.chooseProvinceMode) {
         chooseProvinceExtraY = 0;
      }

      return super.getPosY() + chooseProvinceExtraY;
   }

   public static void centerToArmy() {
      int nArmyID = Game.getProvince(iProvinceID).getArmyKeyID(key);
      if (nArmyID < 0) {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            int outID = Game.getProvince(i).getArmyKeyID(key, iCivFlagID);
            if (outID >= 0) {
               iProvinceID = i;
               nArmyID = outID;
               break;
            }
         }
      }

      if (nArmyID >= 0) {
         Game.mapCoords.centerToProvinceID(iProvinceID);
      }
   }
}
