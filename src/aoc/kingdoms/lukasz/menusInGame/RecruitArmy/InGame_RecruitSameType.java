package aoc.kingdoms.lukasz.menusInGame.RecruitArmy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyBattle;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextArmies;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battlefield;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RecruitSameType extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int firstLineElemID = 0;
   public static int flankLineElemID = 0;
   public static int supportLineElemID = 0;
   public static int firstLineUnits = 0;
   public static int flankLineUnits = 0;
   public static int supportLineUnits = 0;

   public InGame_RecruitSameType() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title1Red).getHeight();
      int menuWidth = ImageManager.getImage(Images.title1Red).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int textTitleH = Math.max(ImageManager.getImage(Images.battleArmy0).getHeight() * 2 + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6);
      firstLineUnits = 0;
      flankLineUnits = 0;
      supportLineUnits = 0;
      firstLineElemID = 0;
      flankLineUnits = 0;
      supportLineUnits = 0;
      Game.ArmyPos tArmy = Game.findArmy(Game.player.iCivID, InGame_RecruitArmy.key);
      if (tArmy != null) {
         buttonY += CFG.TEXT_HEIGHT + CFG.PADDING * 6;
         int extraX = Images.boxTitleBORDERWIDTH;
         if (Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral == null) {
            menuElements.add(
               new ButtonArmyNoGeneral2_Armies(
                  Game.lang.get("NoGeneral"),
                  Game.player.iCivID,
                  paddingLeft + extraX,
                  buttonY,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).key,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).civID,
                  tArmy.iProvinceID
               )
            );
         } else {
            menuElements.add(
               new ButtonArmyGeneral2_Armies(
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.n,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).civID,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.getAttack(),
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.getDefense(),
                  paddingLeft + extraX,
                  buttonY,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.g,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.d,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.m,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.y,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).key,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).civID,
                  tArmy.iProvinceID,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.sI,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).armyGeneral.getCombatExperience()
               )
            );
         }

         int buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;

         for (int k = 0; k < Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).iArmyRegimentSize; k++) {
            int tUnits = Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).num;
            int numOfRegiments = 1;

            for (int o = k + 1;
               o < Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).iArmyRegimentSize
                  && Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).uID
                     == Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(o).uID
                  && Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).aID
                     == Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(o).aID;
               o++
            ) {
               k++;
               numOfRegiments++;
               tUnits += Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).num;
            }

            if (buttonX + ButtonArmyBattle.getButtonWidth() >= menuWidth - paddingLeft) {
               break;
            }

            menuElements.add(
               new ButtonArmyBattle(
                  "" + tUnits,
                  numOfRegiments,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).civID,
                  buttonX + extraX,
                  buttonY,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).uID,
                  Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).lArmyRegiment.get(k).aID,
                  false
               )
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }

         int var19 = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding + CFG.PADDING;
         menuElements.add(
            new TextArmies(
               Game.getProvince(Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).iArmyOfProvinceID).getProvinceName(),
               CFG.getNumberWithSpaces("" + Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).iArmy),
               paddingLeft + CFG.PADDING + menuWidth * 3 / 10,
               buttonY,
               Math.max(buttonX, menuWidth - paddingLeft * 2) - menuWidth * 3 / 10 - CFG.PADDING,
               CFG.TEXT_HEIGHT + CFG.PADDING * 5,
               Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).key,
               Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).civID,
               tArmy.iProvinceID,
               menuWidth - paddingLeft * 2
            )
         );
         menuElements.add(
            new ButtonStatsRectIMG_Active_Click(
               Game.lang.get("ArmyDeployment"),
               Images.battleWidth,
               paddingLeft + extraX,
               buttonY,
               menuWidth * 3 / 10,
               CFG.TEXT_HEIGHT + CFG.PADDING * 5,
               ImageManager.getImage(Images.battleWidth).getWidth(),
               tArmy.iProvinceID,
               true
            ) {
               String key;

               @Override
               public void setText2(String sText) {
                  this.key = sText;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("BattleWidth") + ": "));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(
                        "" + BattleManager.getBattleWidth(Game.player.iCivID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.battleWidth, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Empty());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ArmyDeployment"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battleWidth, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }

               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).iArmyPositionSize > 0) {
                     try {
                        if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        } else {
                           int nArmyID = Game.getProvince(this.id).getArmyKeyID(this.key);
                           if (nArmyID < 0) {
                              for (int i = 0; i < Game.getProvincesSize(); i++) {
                                 int outID = Game.getProvince(i).getArmyKeyID(this.key, Game.player.iCivID);
                                 if (outID >= 0) {
                                    this.id = i;
                                    nArmyID = outID;
                                    break;
                                 }
                              }
                           }

                           if (nArmyID >= 0) {
                              InGame_Battlefield.armyDivision = Game.getProvince(this.id).getArmy(nArmyID);
                              if (InGame_Battlefield.armyDivision != null) {
                                 Game.menuManager.rebuildInGame_Battlefield();
                              } else {
                                 Game.menuManager.setVisibleInGame_PopUp(false);
                              }
                           }
                        }
                     } catch (Exception var4) {
                        CFG.exceptionStack(var4);
                     }
                  }
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setText2(Game.getProvince(tArmy.iProvinceID).getArmy(tArmy.iID).key);
         buttonY = 0;
         int i = 0;

         for (int iSize = menuElements.size(); i < iSize; i++) {
            if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
               buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
            }
         }
      }

      int extraElements = menuElements.size();
      List<MenuElement> tElements = InGame_RecruitArmy.getRecruitMenuElements(buttonY, menuWidth, paddingLeft, CFG.PADDING, textTitleH);
      int i = 0;

      for (int iSizex = tElements.size(); i < iSizex; i++) {
         menuElements.add(tElements.get(i));
         buttonY = Math.max(buttonY, tElements.get(i).getPosY() + tElements.get(i).getHeight() + CFG.PADDING);
      }

      firstLineElemID += extraElements;
      flankLineElemID += extraElements;
      supportLineElemID += extraElements;
      buttonY = 0;
      i = 0;

      for (int iSizex = menuElements.size(); i < iSizex; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(
            Game.lang.get("RecruitAndAssignToTheArmy"),
            Game.lang.get("BattleWidth") + ": " + BattleManager.getBattleWidth(Game.player.iCivID),
            false,
            false,
            Images.title1Red
         ) {
            @Override
            public long getTime() {
               return InGame_RecruitSameType.lTime;
            }
         },
         menuX,
         menuY,
         menuWidth,
         i,
         menuElements,
         false,
         true
      );
      this.drawScrollPositionAlways = false;
      this.updateLanguage();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop,
         Images.insideBot
      );
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      if (Game.getCiv(Game.player.iCivID).diplomacy.isAtWar()) {
         oSB.setColor(InGame_RecruitArmy.sparksColor);
         InGame_RecruitArmy.sparksAnimation
            .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), Images.sparkHeight, false, true);
         oSB.setColor(Color.WHITE);
      }

      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   @Override
   public void updateLanguage() {
      try {
         firstLineUnits = 0;
         flankLineUnits = 0;
         supportLineUnits = 0;
         Game.ArmyPos tArmy = Game.findArmy(Game.player.iCivID, InGame_RecruitArmy.key);
         if (tArmy != null) {
            ArmyDivision armyDivision = Game.getProvince(tArmy.iProvinceID).getArmy(InGame_RecruitArmy.key);

            for (int i = 0; i < armyDivision.iArmyRegimentSize; i++) {
               if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(i).uID).Line == 0) {
                  firstLineUnits++;
               } else if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(i).uID).Line == 1) {
                  flankLineUnits++;
               } else {
                  supportLineUnits++;
               }
            }
         }

         for (int a = 0; a < Game.getCiv(Game.player.iCivID).getArmyRecruitSize(); a++) {
            for (int j = 0; j < Game.getCiv(Game.player.iCivID).lArmyRecruit.get(a).size(); j++) {
               if (InGame_RecruitArmy.key.equals(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(a).get(j).toArmyKey)) {
                  if (ArmyManager.lUnitsTypes.get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(a).get(j).unitID).Line == 0) {
                     firstLineUnits++;
                  } else if (ArmyManager.lUnitsTypes.get(Game.getCiv(Game.player.iCivID).lArmyRecruit.get(a).get(j).unitID).Line == 1) {
                     flankLineUnits++;
                  } else {
                     supportLineUnits++;
                  }
               }
            }
         }

         this.getMenuElement(firstLineElemID).setText(Game.lang.get("FirstLine") + ": " + firstLineUnits);
         this.getMenuElement(flankLineElemID).setText(Game.lang.get("Flank") + ": " + flankLineUnits);
         this.getMenuElement(supportLineElemID).setText(Game.lang.get("Support") + ": " + supportLineUnits);
      } catch (Exception var4) {
      }
   }
}
