package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style_NewArmy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonUnit_02;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy;
import aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy.ScenarioDiplomacy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioArmies_List extends Menu {
   public ScenarioArmies_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int textTitleH = Math.max(ImageManager.getImage(Images.battleArmy0).getHeight() * 2 + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6);
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int c0W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 3.0F);
      int buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(new Text_StaticBG(Game.lang.get("Clear"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2) {
         @Override
         public void actionElement() {
            Game.getProvince(Game.iActiveProvince).clearArmies_ScenarioEditor();
            if (ScenarioDiplomacy.goBackTo == View.IN_GAME && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
               Game.gameThread.addCivUpdateArmyMaintenance(Game.getProvince(Game.iActiveProvince).getCivID());
               Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(Game.iActiveProvince).getCivID());
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(InGame_RecruitArmy.getTitleFirstLine(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int tempAdded = 0;
      int nCivID = 0;
      if (Game.iActiveProvince >= 0) {
         nCivID = Game.getProvince(Game.iActiveProvince).getCivID();
      }

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int j = 0; j < ArmyManager.lArmySize.get(i); j++) {
            if (ArmyManager.lUnitsTypes.get(i).Line == 0 && Game.getCiv(nCivID).isUnitBest(i, j)) {
               tempAdded++;
               menuElements.add(
                  new ButtonUnit_02(i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true) {
                     @Override
                     public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);

                        try {
                           int tNum = 0;
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              for (int ix = Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize - 1; ix >= 0; ix--) {
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(ix).uID == this.getValue1()
                                    && Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(ix).aID == this.getValue2()) {
                                    tNum++;
                                 }
                              }
                           }

                           if (tNum > 0) {
                              Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "" + tNum);
                              int tTextWidth = (int)Renderer.glyphLayout.width;
                              oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75F));
                              Renderer.drawBox(
                                 oSB,
                                 Images.statsRectBG,
                                 this.getPosX() + CFG.PADDING + iTranslateX,
                                 this.getPosY() + CFG.PADDING + iTranslateY,
                                 tTextWidth + CFG.PADDING * 4,
                                 CFG.TEXT_HEIGHT + CFG.PADDING * 2,
                                 1.0F
                              );
                              oSB.setColor(Color.WHITE);
                              Renderer.drawText(
                                 oSB,
                                 CFG.FONT_BOLD,
                                 "" + tNum,
                                 this.getPosX() + CFG.PADDING * 3 + iTranslateX,
                                 this.getPosY() + CFG.PADDING * 2 + iTranslateY,
                                 Colors.HOVER_GOLD
                              );
                           }
                        } catch (Exception var8) {
                           CFG.exceptionStack(var8);
                        }
                     }

                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }

                        if (ScenarioDiplomacy.goBackTo == View.IN_GAME && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                           Game.gameThread.addCivUpdateArmyMaintenance(Game.getProvince(Game.iActiveProvince).getCivID());
                           Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(Game.iActiveProvince).getCivID());
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int ix = 0; ix < 5; ix++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }

                        if (ScenarioDiplomacy.goBackTo == View.IN_GAME && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                           Game.gameThread.addCivUpdateArmyMaintenance(Game.getProvince(Game.iActiveProvince).getCivID());
                           Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(Game.iActiveProvince).getCivID());
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     j,
                     true,
                     "+",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }

                        if (ScenarioDiplomacy.goBackTo == View.IN_GAME && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                           Game.gameThread.addCivUpdateArmyMaintenance(Game.getProvince(Game.iActiveProvince).getCivID());
                           Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(Game.iActiveProvince).getCivID());
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     j,
                     false,
                     "-",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY + (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2 + CFG.PADDING,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }

                        if (ScenarioDiplomacy.goBackTo == View.IN_GAME && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                           Game.gameThread.addCivUpdateArmyMaintenance(Game.getProvince(Game.iActiveProvince).getCivID());
                           Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(Game.iActiveProvince).getCivID());
                        }
                     }
                  }
               );
               buttonY += ImageManager.getImage(Images.unitsFrame).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      menuElements.add(InGame_RecruitArmy.getTitleFirstLineSide(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jx = 0; jx < ArmyManager.lArmySize.get(i); jx++) {
            if (ArmyManager.lUnitsTypes.get(i).Line == 1 && Game.getCiv(nCivID).isUnitBest(i, jx)) {
               tempAdded++;
               menuElements.add(
                  new ButtonUnit_02(i, jx, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true) {
                     @Override
                     public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);

                        try {
                           int tNum = 0;
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              for (int i = Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize - 1; i >= 0; i--) {
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(i).uID == this.getValue1()
                                    && Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(i).aID == this.getValue2()) {
                                    tNum++;
                                 }
                              }
                           }

                           if (tNum > 0) {
                              Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "" + tNum);
                              int tTextWidth = (int)Renderer.glyphLayout.width;
                              oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75F));
                              Renderer.drawBox(
                                 oSB,
                                 Images.statsRectBG,
                                 this.getPosX() + CFG.PADDING + iTranslateX,
                                 this.getPosY() + CFG.PADDING + iTranslateY,
                                 tTextWidth + CFG.PADDING * 4,
                                 CFG.TEXT_HEIGHT + CFG.PADDING * 2,
                                 1.0F
                              );
                              oSB.setColor(Color.WHITE);
                              Renderer.drawText(
                                 oSB,
                                 CFG.FONT_BOLD,
                                 "" + tNum,
                                 this.getPosX() + CFG.PADDING * 3 + iTranslateX,
                                 this.getPosY() + CFG.PADDING * 2 + iTranslateY,
                                 Colors.HOVER_GOLD
                              );
                           }
                        } catch (Exception var8) {
                           CFG.exceptionStack(var8);
                        }
                     }

                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     jx,
                     true,
                     "+",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     jx,
                     false,
                     "-",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY + (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2 + CFG.PADDING,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }
                     }
                  }
               );
               buttonY += ImageManager.getImage(Images.unitsFrame).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      menuElements.add(InGame_RecruitArmy.getTitleSecondLine(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jxx = 0; jxx < ArmyManager.lArmySize.get(i); jxx++) {
            if (ArmyManager.lUnitsTypes.get(i).Line > 1 && Game.getCiv(nCivID).isUnitBest(i, jxx)) {
               tempAdded++;
               menuElements.add(
                  new ButtonUnit_02(i, jxx, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true) {
                     @Override
                     public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);

                        try {
                           int tNum = 0;
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              for (int i = Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize - 1; i >= 0; i--) {
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(i).uID == this.getValue1()
                                    && Game.getProvince(Game.iActiveProvince).getArmy(0).lArmyRegiment.get(i).aID == this.getValue2()) {
                                    tNum++;
                                 }
                              }
                           }

                           if (tNum > 0) {
                              Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "" + tNum);
                              int tTextWidth = (int)Renderer.glyphLayout.width;
                              oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75F));
                              Renderer.drawBox(
                                 oSB,
                                 Images.statsRectBG,
                                 this.getPosX() + CFG.PADDING + iTranslateX,
                                 this.getPosY() + CFG.PADDING + iTranslateY,
                                 tTextWidth + CFG.PADDING * 4,
                                 CFG.TEXT_HEIGHT + CFG.PADDING * 2,
                                 1.0F
                              );
                              oSB.setColor(Color.WHITE);
                              Renderer.drawText(
                                 oSB,
                                 CFG.FONT_BOLD,
                                 "" + tNum,
                                 this.getPosX() + CFG.PADDING * 3 + iTranslateX,
                                 this.getPosY() + CFG.PADDING * 2 + iTranslateY,
                                 Colors.HOVER_GOLD
                              );
                           }
                        } catch (Exception var8) {
                           CFG.exceptionStack(var8);
                        }
                     }

                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     jxx,
                     true,
                     "+",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                              List<ArmyRegiment> nArmies = new ArrayList<>();
                              nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              Game.getProvince(Game.iActiveProvince)
                                 .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                           } else {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() == 0) {
                                 List<ArmyRegiment> nArmies = new ArrayList<>();
                                 nArmies.add(new ArmyRegiment(this.getValue1(), this.getValue2()));
                                 Game.getProvince(Game.iActiveProvince)
                                    .addArmy(new ArmyDivision(Game.getProvince(Game.iActiveProvince).getCivID(), Game.iActiveProvince, nArmies));
                              } else {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).addRegiment(new ArmyRegiment(this.getValue1(), this.getValue2()));
                              }
                           }
                        }
                     }
                  }
               );
               menuElements.add(
                  new ButtonGame_Style_NewArmy(
                     i,
                     jxx,
                     false,
                     "-",
                     CFG.FONT_REGULAR,
                     -1,
                     paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING,
                     buttonY + (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2 + CFG.PADDING,
                     CFG.BUTTON_HEIGHT - CFG.PADDING,
                     (ImageManager.getImage(Images.unitsFrame).getHeight() - CFG.PADDING) / 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (!AA_KeyManager.CTRL_HOLD && !AA_KeyManager.SHIFT_HOLD) {
                           if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                              Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                              if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                 Game.getProvince(Game.iActiveProvince).removeArmy(0);
                              }
                           }
                        } else {
                           for (int i = 0; i < 5; i++) {
                              if (Game.getProvince(Game.iActiveProvince).getArmySize() > 0) {
                                 Game.getProvince(Game.iActiveProvince).getArmy(0).removeRegiment_ScenarioEditor(this.getValue1(), this.getValue2());
                                 if (Game.getProvince(Game.iActiveProvince).getArmy(0).iArmyRegimentSize == 0) {
                                    Game.getProvince(Game.iActiveProvince).removeArmy(0);
                                 }
                              }
                           }
                        }
                     }
                  }
               );
               buttonY += ImageManager.getImage(Images.unitsFrame).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int var23 = 0;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG("", false, false, Images.title1Red), menuX, menuY, menuWidth, menuHeight, menuElements, true, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle()
         .setText(
            Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0
               ? Game.getProvince(Game.iActiveProvince).getProvinceName()
               : Game.lang.get("LandUnits")
         );
   }

   @Override
   public boolean getVisible() {
      return Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0;
   }
}
