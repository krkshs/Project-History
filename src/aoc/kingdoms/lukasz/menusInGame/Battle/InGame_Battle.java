package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.battles.BattleRegiment;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_Terrain;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegiment;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegimentDefeated;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegimentEmpty;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextFlags;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextFlagsCasualties;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIconScaled;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Battle extends Menu {
   public static int nTranslateX;
   public static int nTranslateY;
   public static int HOVER_POSX = 0;
   public static int HOVER_POSY = 0;
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String key = "";
   public static int iProvinceID = 0;
   public int regimentElementID = 0;
   public static boolean enableAnimation = true;
   public static List<Integer> battlePosY = new ArrayList<>();
   public static List<Integer> offsetDefY = new ArrayList<>();
   public static List<Integer> offsetAttY = new ArrayList<>();
   public int topY = 0;
   public int bottomY = 0;
   public static int battleID = -1;
   public static int TURN_ID = 0;
   public static String sDay = "";
   public static int iDayWidth;

   public InGame_Battle() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title630).getHeight();
      int menuWidth = ImageManager.getImage(Images.title630).getWidth() - Images.boxTitleBORDERWIDTH;
      int menuMinHeight = ImageManager.getImage(Images.battleOver).getHeight();
      int menuHeight = 240;
      int menuX = 0;
      int menuY = CFG.GAME_HEIGHT - menuHeight;
      int buttonYPadding = CFG.PADDING * 2;
      int generalPadding = CFG.PADDING * 3;
      int buttonX = 0;
      battleID = Game.battleManager.getBattleID(key);
      TURN_ID = Game_Calendar.TURN_ID;
      if (battleID >= 0) {
         sDay = Game_Calendar.getNumOfDates_ByTurnID(Game_Calendar.TURN_ID - Game.battleManager.getBattle(battleID).roundID - 1)
            + (Game.battleManager.getBattle(battleID).roundID == 0 ? " - " + Game.lang.get("ArmyDeployment") : "");
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sDay);
         iDayWidth = (int)Renderer.glyphLayout.width;
         if (offsetDefY.size() == 0) {
            for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
               offsetDefY.add(0);
               offsetAttY.add(0);
            }
         }

         battlePosY.clear();

         for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
            if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i) != null
               && Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i) != null) {
               if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.num
                  > Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.num) {
                  battlePosY.add(
                     (int)(
                        getMiddleHeight() / 2.0F
                           + getMiddleHeight()
                              / 2.0F
                              * (
                                 1.0F
                                    - (float)Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.num
                                       / Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.num
                              )
                     )
                  );
               } else {
                  battlePosY.add(
                     (int)(
                        getMiddleHeight() / 2
                           + getMiddleHeight()
                              / 2.0F
                              * -(
                                 1.0F
                                    - (float)Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.num
                                       / Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.num
                              )
                     )
                  );
               }
            } else {
               battlePosY.add((int)(getMiddleHeight() * 0.5F));
            }
         }

         menuElements.add(new ButtonFlag2(Game.battleManager.getBattle(battleID).attackingArmy.iCivID, generalPadding, buttonYPadding, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (this.getFlagCivID() < 0) {
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, 0, CFG.PADDING));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rebels"), Colors.HOVER_GOLD));
               } else {
                  nData.add(new MenuElement_HoverElement_Type_Flag(this.getFlagCivID(), 0, CFG.PADDING));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.getCiv(this.getFlagCivID()).getCivName(), Colors.HOVER_GOLD));
               }

               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": "));
               nData.add(new MenuElement_HoverElement_Type_Text("" + BattleManager.getBattleWidth(this.getFlagCivID()), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         });
         menuElements.add(
            new TextIconScaled(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).attackingArmy.numOfUnits),
               Game_Calendar.IMG_MANPOWER,
               generalPadding,
               buttonYPadding + CFG.PADDING + ImageManager.getImage(Images.flagOverDefault).getHeight(),
               ImageManager.getImage(Images.flagOverDefault).getWidth(),
               ImageManager.getImage(Images.generalFrameBattle).getHeight()
                  + ButtonArmyGeneral2.getStatsHeight()
                  - ImageManager.getImage(Images.flagOverDefault).getHeight()
                  - CFG.PADDING
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NumberOfUnits") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonX = generalPadding + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         if (Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral == null) {
            menuElements.add(
               new ButtonArmyNoGeneral2(Game.lang.get("NoGeneral"), Game.battleManager.getBattle(battleID).attackingArmy.iCivID, buttonX, buttonYPadding, false) {
                  @Override
                  public void actionElement() {
                     InGame_Battle.this.battleFull();
                  }
               }
            );
         } else {
            menuElements.add(
               new ButtonArmyGeneral2(
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.n,
                  Game.battleManager.getBattle(battleID).attackingArmy.iCivID,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.getAttack(),
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.getDefense(),
                  buttonX,
                  buttonYPadding,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.g,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.d,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.m,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.y,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.sI,
                  Game.battleManager.getBattle(battleID).attackingArmy.armyGeneral.getCombatExperience()
               ) {
                  @Override
                  public void buildElementHover() {
                     this.buildElementHover2();
                  }

                  @Override
                  public void actionElement() {
                     InGame_Battle.this.battleFull();
                  }
               }
            );
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextIconScaled(
               "" + Game.battleManager.getBattle(battleID).attackingArmy.diceRollGeneral,
               Images.dice,
               buttonX,
               buttonYPadding,
               ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
               (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiceRoll") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Text(" / " + GameValues.battle.BATTLE_MAX_DICE_ROLL, CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.dice, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new TextIconScaled(
               CFG.getPrecision2(Game.battleManager.getBattle(battleID).attackingArmy.fMorale * 100.0F, 1) + "%",
               Images.morale,
               buttonX,
               buttonYPadding
                  + CFG.PADDING
                  + (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2,
               ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
               (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmyMoraleOnTheBattlefield") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.morale, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         int buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<Integer> tAttackingCivs = new ArrayList<>();
         int j = 0;

         for (int jSize = Game.battleManager.getBattle(battleID).attackingArmy.lCivs.size(); j < jSize; j++) {
            tAttackingCivs.add(Game.battleManager.getBattle(battleID).attackingArmy.lCivs.get(j));
         }

         menuElements.add(
            new TextFlags(Game.lang.get("Attackers") + ":", tAttackingCivs, generalPadding, buttonY, 50, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
               @Override
               public void setWidth(int iWidth) {
                  super.setWidth(
                     Math.max(
                        ImageManager.getImage(Images.unitsFrameBattle).getWidth()
                           + ImageManager.getImage(Images.generalFrameBattle).getWidth()
                           + CFG.PADDING * 2
                           + ImageManager.getImage(Images.flagOverDefault).getWidth(),
                        iWidth
                     )
                  );
               }
            }
         );
         j = menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextFlagsCasualties(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).attackingArmy.numOfUnitsOnBattlefield),
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).attackingArmy.inReserve),
               CFG.getNumberWithSpaces(
                  "" + (Game.battleManager.getBattle(battleID).attackingArmy.iCasualties + Game.battleManager.getBattle(battleID).attackingArmy.iRetreated)
               ),
               generalPadding + j,
               buttonY,
               menuWidth - generalPadding * 2 - j,
               CFG.TEXT_HEIGHT + CFG.PADDING * 2,
               false
            )
         );
         this.topY = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
         buttonX = menuWidth / 2
            - (
                  GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                     + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
               )
               / 2
            + 1;

         for (int ix = 0; ix < Game.battleManager.getBattle(battleID).attackingArmy.secondLine.size(); ix++) {
            if (Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(ix) != null) {
               menuElements.add(
                  new ButtonBattleRegiment(
                     Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(ix).c,
                     getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(ix).aR.uID).Line),
                     buttonX,
                     buttonY,
                     ix,
                     0,
                     ArmyManager.lArmy
                           .get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(ix).aR.uID)
                           .get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(ix).aR.aID)
                        .AttackRange,
                     true,
                     false
                  ) {
                     @Override
                     public BattleRegiment getBattleRegiment(int battleID, int id) {
                        return Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(id);
                     }

                     @Override
                     public void actionElement() {
                        InGame_Battle.this.updateAnimationStatus();
                     }
                  }
               );
            } else {
               menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                  @Override
                  public void actionElement() {
                     InGame_Battle.this.updateAnimationStatus();
                  }
               });
            }

            buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
         }

         buttonX = menuWidth / 2
            - (
                  GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                     + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
               )
               / 2
            + 1;
         buttonY += ImageManager.getImage(Images.battleArmy0).getHeight() + 2;
         this.regimentElementID = menuElements.size();

         for (int ix = 0; ix < Game.battleManager.getBattle(battleID).attackingArmy.firstLine.size(); ix++) {
            if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix) != null) {
               if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).aR.num > 0) {
                  menuElements.add(
                     new ButtonBattleRegiment(
                        Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).c,
                        getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).aR.uID).Line),
                        buttonX,
                        buttonY,
                        ix,
                        enableAnimation ? offsetAttY.get(ix) : 0,
                        ArmyManager.lArmy
                              .get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).aR.uID)
                              .get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).aR.aID)
                           .AttackRange,
                        false,
                        false
                     ) {
                        @Override
                        public void actionElement() {
                           InGame_Battle.this.updateAnimationStatus();
                        }
                     }
                  );
               } else {
                  menuElements.add(
                     new ButtonBattleRegimentDefeated(
                        Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).c,
                        getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(ix).aR.uID).Line),
                        buttonX,
                        buttonY,
                        ix,
                        enableAnimation ? offsetAttY.get(ix) : 0
                     ) {
                        @Override
                        public void actionElement() {
                           InGame_Battle.this.updateAnimationStatus();
                        }
                     }
                  );
               }
            } else {
               menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy0, buttonX, buttonY));
            }

            buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
         }

         buttonX = menuWidth / 2
            - (
                  GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                     + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
               )
               / 2
            + 1;
         buttonY += getMiddleHeight() + ImageManager.getImage(Images.battleArmy0).getHeight() + 1;

         for (int ix = 0; ix < Game.battleManager.getBattle(battleID).defendingArmy.firstLine.size(); ix++) {
            if (Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix) != null) {
               if (Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).aR.num > 0) {
                  menuElements.add(
                     new ButtonBattleRegiment(
                        Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).c,
                        getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).aR.uID).Line),
                        buttonX,
                        buttonY,
                        ix,
                        enableAnimation ? offsetDefY.get(ix) : 0,
                        ArmyManager.lArmy
                              .get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).aR.uID)
                              .get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).aR.aID)
                           .AttackRange,
                        false,
                        true
                     ) {
                        @Override
                        public BattleRegiment getBattleRegiment(int battleID, int id) {
                           return Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(id);
                        }

                        @Override
                        public void actionElement() {
                           InGame_Battle.this.updateAnimationStatus();
                        }
                     }
                  );
               } else {
                  menuElements.add(
                     new ButtonBattleRegimentDefeated(
                        Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).c,
                        getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(ix).aR.uID).Line),
                        buttonX,
                        buttonY,
                        ix,
                        enableAnimation ? offsetDefY.get(ix) : 0
                     ) {
                        @Override
                        public BattleRegiment getBattleRegiment(int battleID, int id) {
                           return Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(id);
                        }

                        @Override
                        public void actionElement() {
                           InGame_Battle.this.updateAnimationStatus();
                        }
                     }
                  );
               }
            } else {
               menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy0, buttonX, buttonY));
            }

            buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
         }

         buttonX = menuWidth / 2
            - (
                  GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                     + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
               )
               / 2
            + 1;
         buttonY += ImageManager.getImage(Images.battleArmy0).getHeight() + 2;

         for (int ix = 0; ix < Game.battleManager.getBattle(battleID).defendingArmy.secondLine.size(); ix++) {
            if (Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(ix) != null) {
               menuElements.add(
                  new ButtonBattleRegiment(
                     Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(ix).c,
                     getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(ix).aR.uID).Line),
                     buttonX,
                     buttonY,
                     ix,
                     0,
                     ArmyManager.lArmy
                           .get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(ix).aR.uID)
                           .get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(ix).aR.aID)
                        .AttackRange,
                     true,
                     true
                  ) {
                     @Override
                     public BattleRegiment getBattleRegiment(int battleID, int id) {
                        return Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(id);
                     }

                     @Override
                     public void actionElement() {
                        InGame_Battle.this.updateAnimationStatus();
                     }
                  }
               );
            } else {
               menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                  @Override
                  public void actionElement() {
                     InGame_Battle.this.updateAnimationStatus();
                  }
               });
            }

            buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
         }

         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
         List<Integer> tDefendingCivs = new ArrayList<>();
         int jx = 0;

         for (int jSize = Game.battleManager.getBattle(battleID).defendingArmy.lCivs.size(); jx < jSize; jx++) {
            tDefendingCivs.add(Game.battleManager.getBattle(battleID).defendingArmy.lCivs.get(jx));
         }

         menuElements.add(
            new TextFlags(Game.lang.get("Defenders") + ":", tDefendingCivs, generalPadding, buttonY, 50, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
               @Override
               public void setWidth(int iWidth) {
                  super.setWidth(
                     Math.max(
                        ImageManager.getImage(Images.unitsFrameBattle).getWidth()
                           + ImageManager.getImage(Images.generalFrameBattle).getWidth()
                           + CFG.PADDING * 2
                           + ImageManager.getImage(Images.flagOverDefault).getWidth(),
                        iWidth
                     )
                  );
               }
            }
         );
         j = menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextFlagsCasualties(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).defendingArmy.numOfUnitsOnBattlefield),
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).defendingArmy.inReserve),
               CFG.getNumberWithSpaces(
                  "" + (Game.battleManager.getBattle(battleID).defendingArmy.iCasualties + Game.battleManager.getBattle(battleID).defendingArmy.iRetreated)
               ),
               generalPadding + j,
               buttonY,
               menuWidth - generalPadding * 2 - j,
               CFG.TEXT_HEIGHT + CFG.PADDING * 2,
               true
            )
         );
         this.bottomY = buttonY - CFG.PADDING;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(new ButtonFlag2(Game.battleManager.getBattle(battleID).defendingArmy.iCivID, generalPadding, buttonY, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (this.getFlagCivID() < 0) {
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, 0, CFG.PADDING));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rebels"), Colors.HOVER_GOLD));
               } else {
                  nData.add(new MenuElement_HoverElement_Type_Flag(this.getFlagCivID(), 0, CFG.PADDING));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.getCiv(this.getFlagCivID()).getCivName(), Colors.HOVER_GOLD));
               }

               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": "));
               nData.add(new MenuElement_HoverElement_Type_Text("" + BattleManager.getBattleWidth(this.getFlagCivID()), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         });
         menuElements.add(
            new TextIconScaled(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(battleID).defendingArmy.numOfUnits),
               Game_Calendar.IMG_MANPOWER,
               generalPadding,
               buttonY + CFG.PADDING + ImageManager.getImage(Images.flagOverDefault).getHeight(),
               ImageManager.getImage(Images.flagOverDefault).getWidth(),
               ImageManager.getImage(Images.generalFrameBattle).getHeight()
                  + ButtonArmyGeneral2.getStatsHeight()
                  - ImageManager.getImage(Images.flagOverDefault).getHeight()
                  - CFG.PADDING
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NumberOfUnits") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonX = generalPadding + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         if (Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral == null) {
            menuElements.add(
               new ButtonArmyNoGeneral2(Game.lang.get("NoGeneral"), Game.battleManager.getBattle(battleID).defendingArmy.iCivID, buttonX, buttonY, false) {
                  @Override
                  public void actionElement() {
                     InGame_Battle.this.battleFull();
                  }
               }
            );
         } else {
            menuElements.add(
               new ButtonArmyGeneral2(
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.n,
                  Game.battleManager.getBattle(battleID).defendingArmy.iCivID,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.getAttack(),
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.getDefense(),
                  buttonX,
                  buttonY,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.g,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.d,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.m,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.y,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.sI,
                  Game.battleManager.getBattle(battleID).defendingArmy.armyGeneral.getCombatExperience()
               ) {
                  @Override
                  public void buildElementHover() {
                     this.buildElementHover2();
                  }

                  @Override
                  public void actionElement() {
                     InGame_Battle.this.battleFull();
                  }
               }
            );
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextIconScaled(
               "" + Game.battleManager.getBattle(battleID).defendingArmy.diceRollGeneral,
               Images.dice,
               buttonX,
               buttonY,
               ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
               (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiceRoll") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Text(" / " + GameValues.battle.BATTLE_MAX_DICE_ROLL, CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.dice, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new TextIconScaled(
               CFG.getPrecision2(Game.battleManager.getBattle(battleID).defendingArmy.fMorale * 100.0F, 1) + "%",
               Images.morale,
               buttonX,
               buttonY + CFG.PADDING + (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2,
               ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
               (ImageManager.getImage(Images.generalFrameBattle).getHeight() + ButtonArmyGeneral2.getStatsHeight() - CFG.PADDING) / 2
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmyMoraleOnTheBattlefield") + ": ", CFG.FONT_BOLD_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.morale, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         InGame_BattleArmy.mPosX = buttonX;
         InGame_BattleArmy.mPosY = buttonY;
         InGame_BattleArmy.mWidth = menuWidth - buttonX - generalPadding;
         InGame_BattleArmyDefenders.mPosX = buttonX;
         InGame_BattleArmyDefenders.mPosY = CFG.PADDING;
         InGame_BattleArmyDefenders.mWidth = menuWidth - buttonX - generalPadding;
      } else if (Game.activeArmySize > 0) {
         Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_ProvinceArmy") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_ProvinceArmy();
            }
         });
      }

      int var35 = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (var35 < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING) {
            var35 = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING;
         }
      }

      menuHeight = Math.max(menuMinHeight, var35);
      menuY = CFG.GAME_HEIGHT - menuHeight;
      InGame_BattleArmy.mPosY += menuY;
      InGame_BattleArmyDefenders.mPosY += menuY;
      HOVER_POSX = menuX + menuWidth + Images.boxTitleBORDERWIDTH + CFG.PADDING;
      HOVER_POSY = menuY;
      menuElements.add(new Empty(0, 0, menuWidth, menuHeight));
      this.initMenu(
         new MenuTitleIMG_Terrain(
            Game.lang.get("BattleOf", Game.getProvince(iProvinceID).getProvinceName()),
            Battle.getDefendersProvinceBonuses(iProvinceID) != 0
               ? Game.lang.get("Defense") + ": +" + Battle.getDefendersProvinceBonuses(iProvinceID)
               : Game.terrainManager.terrains.get(Game.getProvince(iProvinceID).getTerrainID()).Name,
            false,
            false,
            Images.title630,
            Game.getProvince(iProvinceID).getTerrainID(),
            iProvinceID
         ) {
            @Override
            public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
               super.draw(oSB, nPosX, nPosY, nWidth + Images.boxTitleBORDERWIDTH, titleStatus);
            }

            @Override
            public long getTime() {
               return InGame_Battle.lTime;
            }

            @Override
            public void onHovered() {
               Game.menuManager.setOrderOfMenu_InGameBattle();
            }

            @Override
            public void action() {
               Game.menuManager.setOrderOfMenu_InGameBattle();
            }
         },
         menuX,
         menuY,
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         iTranslateY += (int)(CFG.BUTTON_HEIGHT / 2.0F - CFG.BUTTON_HEIGHT / 2 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
         if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).BATTLE_SOUND_2) {
            Game.soundsManager.playSound(SoundsManager.SOUND_BATTLE2, Game.soundsManager.getSoundsVolumeMaster());
         } else {
            Game.soundsManager.playSound(SoundsManager.SOUND_BATTLE, Game.soundsManager.getSoundsVolumeMaster());
         }
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
         Images.insideTop630,
         Images.insideBot630
      );
      ImageManager.getImage(Game.terrainManager.getBattleTerrain(Game.getProvince(iProvinceID).getTerrainID()))
         .draw(
            oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.battleOver).getHeight() / 2 + iTranslateY
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.35F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.topY, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.bottomY + iTranslateY, this.getWidth(), this.getHeight() - this.bottomY);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.topY - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.bottomY + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(Color.WHITE);
      Renderer.drawLine(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.bottomY + iTranslateY, this.getWidth());
      Renderer.drawLine(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.topY - 2 + iTranslateY, this.getWidth());
      if (enableAnimation) {
         this.drawAnimation();
      }

      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (TURN_ID != Game_Calendar.TURN_ID) {
         TURN_ID = Game_Calendar.TURN_ID;
         Game.addSimpleTask(new Game.SimpleTask("rebuildBattleView") {
            @Override
            public void update() {
               if (Game.menuManager.getInGame() && Game.menuManager.getVisibleInGame_Battle()) {
                  Game.menuManager.rebuildInGame_Battle();
               }
            }
         });
      }
   }

   public final void drawAnimation() {
      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         offsetAttY.set(i, offsetAttY.get(i) + (offsetAttY.get(i) > battlePosY.get(i) ? -1 : 1));
         offsetDefY.set(i, offsetDefY.get(i) + (Math.abs(offsetDefY.get(i)) > getMiddleHeight() - battlePosY.get(i) ? 1 : -1));
         if (this.getMenuElement(this.regimentElementID + i).getCurrent() != battlePosY.get(i)) {
            this.getMenuElement(this.regimentElementID + i).setCurrent(offsetAttY.get(i));
         }

         if (Math.abs(this.getMenuElement(this.regimentElementID + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + i).getCurrent())
            != getMiddleHeight() - battlePosY.get(i)) {
            this.getMenuElement(this.regimentElementID + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + i).setCurrent(offsetDefY.get(i));
         }
      }
   }

   @Override
   public void setVisible(boolean visible) {
      if (!this.getVisible()) {
         lTime = CFG.currentTimeMillis;
      }

      super.setVisible(visible);
   }

   public static int getImageRegimentID(int iLine) {
      switch (iLine) {
         case 1:
            return Images.battleArmy1;
         case 2:
            return Images.battleArmy2;
         default:
            return Images.battleArmy0;
      }
   }

   public static int getMiddleHeight() {
      return ImageManager.getImage(Images.battleArmy0).getHeight();
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameBattle();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_Battle(false);
   }

   public void updateAnimationStatus() {
      enableAnimation = !enableAnimation;
      if (!enableAnimation) {
         for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
            this.getMenuElement(this.regimentElementID + i).setCurrent(0);
            this.getMenuElement(this.regimentElementID + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + i).setCurrent(0);
         }
      } else {
         for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
            offsetDefY.set(i, 0);
            offsetAttY.set(i, 0);
         }
      }
   }

   public void battleFull() {
   }
}
