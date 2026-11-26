package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.RomanNumber;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.battles.BattleRegiment;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyBattle;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyIcon_Special;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegiment;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegimentEmpty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Army;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Battle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Battlefield extends Menu {
   public static ArmyDivision armyDivision = new ArmyDivision();
   public static List<BattleRegiment> firstLine = new ArrayList<>();
   public static List<BattleRegiment> secondLineReg = new ArrayList<>();
   public static int boxH;

   public InGame_Battlefield() {
      List<MenuElement> menuElements = new ArrayList<>();
      firstLine.clear();
      secondLineReg.clear();
      int menuWidth = (ImageManager.getImage(Images.battleArmy0).getWidth() + 2) * GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + CFG.PADDING * 4;
      int menuHeight = (ImageManager.getImage(Images.battleArmy0).getHeight() + 2) * 2 + CFG.PADDING * 4;
      int menuX = CFG.GAME_WIDTH / 2 - menuWidth / 2;
      int menuY = CFG.GAME_HEIGHT / 5 - menuHeight / 2;
      int paddingLeft = CFG.PADDING * 2;
      int buttonYPadding = CFG.PADDING;
      int buttonX = paddingLeft;
      int buttonY = 0;

      for (int i = 0; i < GameValues.battle.BATTLE_MAX_BATTLE_WIDTH; i++) {
         firstLine.add(null);
         secondLineReg.add(null);
      }

      List<BattleRegiment> tempLine0 = new ArrayList<>();
      List<BattleRegiment> tempLine1 = new ArrayList<>();
      List<BattleRegiment> tempLine2 = new ArrayList<>();

      for (int i = 0; i < armyDivision.iArmyRegimentSize; i++) {
         switch (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(i).uID).Line) {
            case 0:
               tempLine0.add(new BattleRegiment(armyDivision.civID, armyDivision.lArmyRegiment.get(i), armyDivision.key));
               break;
            case 1:
               tempLine1.add(new BattleRegiment(armyDivision.civID, armyDivision.lArmyRegiment.get(i), armyDivision.key));
               break;
            default:
               tempLine2.add(new BattleRegiment(armyDivision.civID, armyDivision.lArmyRegiment.get(i), armyDivision.key));
         }
      }

      int maxWidth = BattleManager.getBattleWidth(armyDivision.civID);
      List<ArmyDivision> sideArmyList = new ArrayList<>();
      sideArmyList.add(armyDivision);
      int sideArmyMin = Battle.getArmyInFirstLine(sideArmyList);
      sideArmyList.clear();
      int firstAdded = 0;
      int sideArmyWidth = Math.min(maxWidth - (int)(maxWidth * GameValues.battle.BATTLE_SIDES_RATIO), sideArmyMin);

      while (firstAdded < maxWidth && (tempLine0.size() > 0 || tempLine1.size() > 0)) {
         boolean sideArmy = firstAdded >= sideArmyWidth;
         if (!sideArmy && tempLine0.size() > 0) {
            firstLine.set(BattleManager.FILL_ORDER[firstAdded++], tempLine0.get(0));
            tempLine0.remove(0);
         } else if (tempLine1.size() > 0) {
            firstLine.set(BattleManager.FILL_ORDER[firstAdded++], tempLine1.get(0));
            tempLine1.remove(0);
         } else if (tempLine0.size() > 0) {
            firstLine.set(BattleManager.FILL_ORDER[firstAdded++], tempLine0.get(0));
            tempLine0.remove(0);
         }
      }

      int secondAdded = 0;

      while (secondAdded < maxWidth && tempLine2.size() > 0) {
         if (tempLine2.size() > 0) {
            if (firstLine.get(BattleManager.FILL_ORDER[secondAdded]) == null) {
               firstLine.set(BattleManager.FILL_ORDER[secondAdded], tempLine2.get(0));
            } else {
               secondLineReg.set(BattleManager.FILL_ORDER[secondAdded++], tempLine2.get(0));
            }

            tempLine2.remove(0);
         }
      }

      int maxIconW = ImageManager.getImage(Images.battleWidth).getWidth();
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("ArmyDeployment"),
            CFG.BUTTON_WIDTH / 4,
            0,
            buttonY,
            menuWidth,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            "" + Game.lang.get("BattleWidth") + ": " + BattleManager.getBattleWidth(armyDivision.civID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title(
                     InGame_Battlefield.armyDivision.civID,
                     Game.lang.get("BattleWidth")
                        + ": "
                        + BattleManager.getBattleWidth(InGame_Battlefield.armyDivision.civID)
                        + " / "
                        + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("BattleWidthRefersToTheNumberOfUnitsThatCanBeDeployedInAFormationOnTheBattlefield"),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Empty());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Close"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      buttonY += CFG.PADDING;

      try {
         int extraX = 0;
         int pinH = (ButtonArmyBattle.getButtonHeight() - CFG.PADDING) / 2;
         buttonX += CFG.PADDING;
         if (armyDivision.armyGeneral == null) {
            menuElements.add(
               new ButtonArmyNoGeneral2_Armies(
                  Game.lang.get("NoGeneral"), Game.player.iCivID, buttonX + extraX, buttonY, armyDivision.key, armyDivision.civID, armyDivision.provinceID
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                     super.actionElement();
                  }
               }
            );
         } else {
            menuElements.add(
               new ButtonArmyGeneral2_Armies(
                  armyDivision.armyGeneral.n,
                  armyDivision.civID,
                  armyDivision.armyGeneral.getAttack(),
                  armyDivision.armyGeneral.getDefense(),
                  buttonX + extraX,
                  buttonY,
                  armyDivision.armyGeneral.g,
                  armyDivision.armyGeneral.d,
                  armyDivision.armyGeneral.m,
                  armyDivision.armyGeneral.y,
                  armyDivision.key,
                  armyDivision.civID,
                  armyDivision.provinceID,
                  armyDivision.armyGeneral.sI,
                  armyDivision.armyGeneral.getCombatExperience()
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                     super.actionElement();
                  }
               }
            );
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         if (armyDivision.civID == Game.player.iCivID) {
            menuElements.add(new ButtonArmyIcon_Special(Images.pin, buttonX, buttonY, Game.player.isPinned(armyDivision.key), armyDivision.key, pinH) {
               @Override
               public int getImageID() {
                  return Images.pin;
               }

               @Override
               public void actionElement() {
                  this.checkbox = !this.checkbox;
                  Game.player.actionPinArmy(this.armyKey);
                  Game.menuManager.rebuildInGame_Right();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Pin"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.pin, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  if (!this.checkbox) {
                     if (this.getIsHovered()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
                     } else {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
                     }
                  }

                  super.drawText(oSB, iTranslateX, iTranslateY, isActive);
                  oSB.setColor(Color.WHITE);
               }
            });
            menuElements.add(
               new ButtonArmyIcon_Special(Images.center, buttonX, buttonY + CFG.PADDING + pinH, Game.player.isPinned(armyDivision.key), armyDivision.key, pinH) {
                  @Override
                  public int getImageID() {
                     return Images.center;
                  }

                  @Override
                  public void actionElement() {
                     Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(Game.player.iCivID, this.armyKey);
                     if (nArmyPos != null) {
                        Game.mapCoords.centerToProvinceID(nArmyPos.iProvinceID);
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CenterToProvince"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.center, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }

         for (int k = 0; k < armyDivision.iArmyRegimentSize; k++) {
            int tUnits = armyDivision.lArmyRegiment.get(k).num;
            int numOfRegiments = 1;

            for (int o = k + 1;
               o < armyDivision.iArmyRegimentSize
                  && armyDivision.lArmyRegiment.get(k).uID == armyDivision.lArmyRegiment.get(o).uID
                  && armyDivision.lArmyRegiment.get(k).aID == armyDivision.lArmyRegiment.get(o).aID;
               o++
            ) {
               k++;
               numOfRegiments++;
               tUnits += armyDivision.lArmyRegiment.get(k).num;
            }

            menuElements.add(
               new ButtonArmyBattle(
                  "" + tUnits,
                  numOfRegiments,
                  armyDivision.civID,
                  buttonX + extraX,
                  buttonY,
                  armyDivision.lArmyRegiment.get(k).uID,
                  armyDivision.lArmyRegiment.get(k).aID,
                  false
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }

         buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         buttonX = paddingLeft;
      } catch (Exception var27) {
         CFG.exceptionStack(var27);
      }

      boxH = buttonY;
      buttonY += CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Battle(
            Game.lang.get("Enemies") + ": " + Game.lang.get("SecondLine"),
            CFG.FONT_REGULAR_SMALL,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Close"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Battle(
            Game.lang.get("Enemies") + ": " + Game.lang.get("FirstLine"),
            CFG.FONT_REGULAR_SMALL,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Close"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      buttonY += CFG.PADDING;
      int startBattleWidth = GameValues.battle.BATTLE_MAX_BATTLE_WIDTH / 2 - maxWidth / 2;

      for (int i = 0; i < firstLine.size(); i++) {
         if (firstLine.get(i) == null) {
            if (i >= startBattleWidth && i < startBattleWidth + maxWidth) {
               menuElements.add(
                  new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                     @Override
                     public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        oSB.setColor(Game.getCiv(InGame_Battlefield.armyDivision.civID).getRGB(0.25F));
                        Images.pix
                           .draw(
                              oSB,
                              this.getPosX() + 1 + iTranslateX,
                              this.getPosY() + 1 + iTranslateY,
                              ImageManager.getImage(this.imageID).getWidth(),
                              ImageManager.getImage(this.imageID).getHeight()
                           );
                        oSB.setColor(Color.WHITE);
                        oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.getIsHovered() ? 0.4F : 0.25F));
                        ImageManager.getImage(Images.gradientVertical)
                           .draw(
                              oSB,
                              this.getPosX() + 1 + iTranslateX,
                              this.getPosY() + 1 + iTranslateY,
                              ImageManager.getImage(this.imageID).getWidth(),
                              ImageManager.getImage(this.imageID).getHeight()
                           );
                        oSB.setColor(Color.WHITE);
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                     }

                     @Override
                     public float getImageAlpha(boolean isActive) {
                        return !this.getIsHovered() && !isActive ? 0.4F : 0.8F;
                     }

                     @Override
                     public void actionElement() {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NoUnits"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
            } else {
               menuElements.add(
                  new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                     @Override
                     public void actionElement() {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NotAvailable"), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Flag(InGame_Battlefield.armyDivision.civID, 0, CFG.PADDING));
                        nData.add(
                           new MenuElement_HoverElement_Type_Text(
                              Game.getCiv(InGame_Battlefield.armyDivision.civID).getCivName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                        nData.add(
                           new MenuElement_HoverElement_Type_Text(
                              "" + BattleManager.getBattleWidth(InGame_Battlefield.armyDivision.civID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_GOLD
                           )
                        );
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("DeploymentPhase2"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
            }
         } else {
            menuElements.add(
               new ButtonBattleRegiment(
                  armyDivision.civID,
                  InGame_Battle.getImageRegimentID(ArmyManager.lUnitsTypes.get(firstLine.get(i).aR.uID).Line),
                  buttonX,
                  buttonY,
                  i,
                  0,
                  ArmyManager.lArmy.get(firstLine.get(i).aR.uID).get(firstLine.get(i).aR.aID).AttackRange,
                  false,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_Battlefield.this.getHover(InGame_Battlefield.firstLine.get(this.id));
                  }
               }
            );
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      buttonX = paddingLeft;

      for (int i = 0; i < secondLineReg.size(); i++) {
         if (secondLineReg.get(i) == null) {
            if (i >= startBattleWidth && i < startBattleWidth + maxWidth) {
               menuElements.add(
                  new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                     @Override
                     public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        oSB.setColor(Game.getCiv(InGame_Battlefield.armyDivision.civID).getRGB(0.25F));
                        Images.pix
                           .draw(
                              oSB,
                              this.getPosX() + 1 + iTranslateX,
                              this.getPosY() + 1 + iTranslateY,
                              ImageManager.getImage(this.imageID).getWidth(),
                              ImageManager.getImage(this.imageID).getHeight()
                           );
                        oSB.setColor(Color.WHITE);
                        oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.getIsHovered() ? 0.4F : 0.25F));
                        ImageManager.getImage(Images.gradientVertical)
                           .draw(
                              oSB,
                              this.getPosX() + 1 + iTranslateX,
                              this.getPosY() + 1 + iTranslateY,
                              ImageManager.getImage(this.imageID).getWidth(),
                              ImageManager.getImage(this.imageID).getHeight()
                           );
                        oSB.setColor(Color.WHITE);
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                     }

                     @Override
                     public float getImageAlpha(boolean isActive) {
                        return !this.getIsHovered() && !isActive ? 0.4F : 0.8F;
                     }

                     @Override
                     public void actionElement() {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NoUnits"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
            } else {
               menuElements.add(
                  new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {
                     @Override
                     public void actionElement() {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NotAvailable"), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Flag(InGame_Battlefield.armyDivision.civID, 0, CFG.PADDING));
                        nData.add(
                           new MenuElement_HoverElement_Type_Text(
                              Game.getCiv(InGame_Battlefield.armyDivision.civID).getCivName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                        nData.add(
                           new MenuElement_HoverElement_Type_Text(
                              "" + BattleManager.getBattleWidth(InGame_Battlefield.armyDivision.civID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_GOLD
                           )
                        );
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("DeploymentPhase2"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
            }
         } else {
            menuElements.add(
               new ButtonBattleRegiment(
                  armyDivision.civID,
                  InGame_Battle.getImageRegimentID(ArmyManager.lUnitsTypes.get(secondLineReg.get(i).aR.uID).Line),
                  buttonX,
                  buttonY,
                  i,
                  0,
                  ArmyManager.lArmy.get(secondLineReg.get(i).aR.uID).get(secondLineReg.get(i).aR.aID).AttackRange,
                  true,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_Battlefield.this.getHover(InGame_Battlefield.secondLineReg.get(this.id));
                  }
               }
            );
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      tempLine0.clear();
      tempLine1.clear();
      tempLine2.clear();
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 3;
         }
      }

      menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)) {
         @Override
         public void actionElement() {
            Game.menuManager.setVisibleInGame_PopUp(false);
         }
      });
      menuElements.get(menuElements.size() - 1).setClickable(true);
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.85F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      this.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.battleOver)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.battleOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.battleOver).getHeight() / 2 + iTranslateY
         );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.7F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), boxH, 1.0F);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.7F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), boxH, false, true);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + boxH - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
            this.getWidth() - CFG.PADDING * 2,
            CFG.PADDING,
            false,
            true
         );
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING);
      oSB.setColor(Color.WHITE);
      this.drawMenu(oSB, iTranslateX, iTranslateY, menuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public MenuElement_Hover getHover(BattleRegiment battleRegiment) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(battleRegiment.c, RomanNumber.getRoman(battleRegiment.rn) + "" + Game.lang.get("Regiment")));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Army("" + battleRegiment.aR.num, battleRegiment.aR.uID, battleRegiment.aR.aID, battleRegiment.c));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AttackRange") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + ArmyManager.lArmy.get(battleRegiment.aR.uID).get(battleRegiment.aR.aID).AttackRange, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("SiegeAbility") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2(ArmyManager.lArmy.get(battleRegiment.aR.uID).get(battleRegiment.aR.aID).SiegeProgress, 100),
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      return new MenuElement_Hover(nElements);
   }
}
