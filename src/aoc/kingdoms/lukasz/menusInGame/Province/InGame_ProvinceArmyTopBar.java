package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyID;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyIcon;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.InGame_Armies;
import aoc.kingdoms.lukasz.menusInGame.InGame_Disband;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.InGame_ReorganizeUnits;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmyTopBar extends Menu {
   public static int mPosX = 0;
   public static int mPosY = 0;
   public static int mWidth = 0;

   public InGame_ProvinceArmyTopBar() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING * 2;
      int buttonY = CFG.PADDING * 2;
      int generalPadding = CFG.PADDING * 2;
      int defaultXArmyPos = ImageManager.getImage(Images.generalFrame).getWidth() + generalPadding * 2;
      int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID)
         .getArmyKeyID(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key);
      menuElements.add(new ButtonArmyIcon(Images.center, buttonX, buttonY) {
         @Override
         public int getImageID() {
            return Images.center;
         }

         @Override
         public void actionElement() {
            InGame_ProvinceArmy.centerToArmy();
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
      });
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      if (tDivID >= 0) {
         if (Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).civID == Game.player.iCivID) {
            menuElements.add(new ButtonArmyIcon(Images.pin, buttonX, buttonY, Game.player.isPinned(InGame_ProvinceArmy.key)) {
               @Override
               public int getImageID() {
                  return Images.pin;
               }

               @Override
               public void actionElement() {
                  this.checkbox = !this.checkbox;
                  Game.player.actionPinArmy(InGame_ProvinceArmy.key);
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
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonArmyIcon(Images.frontLine, buttonX, buttonY) {
                  @Override
                  public int getImageID() {
                     return Images.frontLine;
                  }

                  @Override
                  public void actionElement() {
                     Game.setRegroupArmyMode();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("MoveTheArmies"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.frontLine, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SelectProvincesToWhichYouWantToMoveArmies"), CFG.FONT_REGULAR_SMALL));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     if (Game.activeArmySize <= 1) {
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Text_Desc(
                              Game.lang.get("SelectMoreThanOneArmy"), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                     }

                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }

                  @Override
                  public boolean getClickable() {
                     return Game.activeArmySize > 1;
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonArmyIcon(Images.invasion, buttonX, buttonY) {
                  @Override
                  public int getImageID() {
                     return Images.invasion;
                  }

                  @Override
                  public void actionElement() {
                     Game.player.playerData.invasion.removeInvasion(InGame_ProvinceArmy.sActiveKEY);
                     Game.setInvasionArmyMode();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     if (Game.player.playerData.invasion.haveInvasionPlan(InGame_ProvinceArmy.sActiveKEY)) {
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CancelInvasion"), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.invasion, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                        nData.add(
                           new MenuElement_HoverElement_Type_Text(
                              "" + Game.player.playerData.invasion.getInvasionPlan_NumOfProvinces(InGame_ProvinceArmy.sActiveKEY),
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_GOLD
                           )
                        );
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     } else {
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectProvincesToOccupy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.invasion, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements, true);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(new ButtonArmyIcon(Images.movementCancel, buttonX, buttonY) {
               @Override
               public int getImageID() {
                  return Images.movementCancel;
               }

               @Override
               public void actionElement() {
                  for (int i = Game.activeArmySize - 1; i >= 0; i--) {
                     Game.getCiv(Game.activeArmy.get(i).iCivID).cancelMove(Game.activeArmy.get(i).key);
                  }

                  if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
                     Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("StopMovementOfTheArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.movementCancel, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            });
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(new ButtonArmyIcon(Images.reorganizeArmy, buttonX, buttonY) {
               @Override
               public int getImageID() {
                  return Game_Calendar.IMG_MANPOWER_REORGANIZE;
               }

               @Override
               public void actionElement() {
                  int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmyKeyID(InGame_ProvinceArmy.key);
                  if (tDivID >= 0) {
                     if (Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).civID == Game.player.iCivID) {
                        InGame_ReorganizeUnits.setArmyLeft(Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID));
                        Game.menuManager.rebuildInGame_ReorganizeUnits();
                        if (Game.menuManager.getVisibleInGame_DisbandArmy()) {
                           Game.menuManager.setVisibleInGame_DisbandUnits(false);
                        }
                     }
                  } else {
                     Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ReorganizeUnits"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER_REORGANIZE, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            });
            menuElements.get(menuElements.size() - 1)
               .setClickable(Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize > 1);
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(new ButtonArmyIcon(Images.splitArmy, buttonX, buttonY) {
               @Override
               public int getImageID() {
                  return Game_Calendar.IMG_MANPOWER_SPLIT;
               }

               @Override
               public void actionElement() {
                  int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmyKeyID(InGame_ProvinceArmy.key);
                  if (tDivID >= 0) {
                     if (Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).civID == Game.player.iCivID) {
                        Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).splitInHalf(tDivID);
                        Game.menuManager.rebuildInGame_ProvinceArmy();
                        if (Game.menuManager.getVisibleInGame_Armies()) {
                           Game.menuManager.rebuildInGame_Armies(false, false);
                           Game.menuManager.setVisibleInGame_Armies(true);
                           InGame_Armies.lTime = 0L;
                        }

                        if (Game.menuManager.getVisibleInGame_ReorganizeUnits()) {
                           Game.menuManager.setVisibleInGame_ReorganizeUnits(false);
                        }

                        if (Game.menuManager.getVisibleInGame_DisbandArmy()) {
                           Game.menuManager.setVisibleInGame_DisbandUnits(false);
                        }
                     }
                  } else {
                     Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SplitInHalf"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER_SPLIT, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            });
            menuElements.get(menuElements.size() - 1)
               .setClickable(Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).iArmyRegimentSize > 1);
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new ButtonArmyIcon(Images.mergeArmy, buttonX, buttonY) {
                  @Override
                  public int getImageID() {
                     return Game_Calendar.IMG_MANPOWER_MERGE;
                  }

                  @Override
                  public void actionElement() {
                     if (Game.activeArmySize > 1) {
                        List<String> toMerge = new ArrayList<>();

                        for (int i = 0; i < Game.activeArmySize; i++) {
                           if (Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID == Game.activeArmy.get(i).iProvinceID
                              && Game.player.iCivID == Game.activeArmy.get(i).iCivID) {
                              toMerge.add(Game.activeArmy.get(i).key);
                           }
                        }

                        String tID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).mergeUnits(toMerge);
                        if (tID != null) {
                           Game.setActiveArmy(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID, tID);
                           InGame_ProvinceArmy.iActiveID = 0;
                           InGame_ProvinceArmy.sActiveKEY = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key;
                           Game.menuManager.rebuildInGame_ProvinceArmy();
                        } else {
                           Game.clearActiveArmy();
                           Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                        }

                        if (Game.menuManager.getVisibleInGame_Generals()) {
                           Game.menuManager.rebuildInGame_Generals();
                           Game.menuManager.setVisibleInGame_Generals(true);
                           InGame_Generals.lTime = 0L;
                        }

                        if (Game.menuManager.getVisibleInGame_Armies()) {
                           Game.menuManager.rebuildInGame_Armies(false, false);
                           Game.menuManager.setVisibleInGame_Armies(true);
                           InGame_Armies.lTime = 0L;
                        }

                        if (Game.menuManager.getVisibleInGame_ReorganizeUnits()) {
                           Game.menuManager.setVisibleInGame_ReorganizeUnits(false);
                        }

                        if (Game.menuManager.getVisibleInGame_DisbandArmy()) {
                           Game.menuManager.setVisibleInGame_DisbandUnits(false);
                        }
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("MergeUnits"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER_MERGE, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }

                  @Override
                  public boolean getClickable() {
                     return super.getClickable() && Game.activeArmySize > 1;
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(new ButtonArmyIcon(Images.disbandArmy, buttonX, buttonY) {
               @Override
               public int getImageID() {
                  return Game_Calendar.IMG_MANPOWER_DISBAND;
               }

               @Override
               public void actionElement() {
                  int tDivID = Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmyKeyID(InGame_ProvinceArmy.key);
                  if (tDivID >= 0) {
                     if (Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID).civID == Game.player.iCivID) {
                        InGame_Disband.setArmyLeft(Game.getProvince(Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).iProvinceID).getArmy(tDivID));
                        Game.menuManager.rebuildInGame_DisbandUnits();
                        if (Game.menuManager.getVisibleInGame_ReorganizeUnits()) {
                           Game.menuManager.setVisibleInGame_ReorganizeUnits(false);
                        }
                     }
                  } else {
                     Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("DisbandUnits"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER_DISBAND, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            });
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }

         for (int z = 0; z < Game.activeArmySize; z++) {
            int tempDivID = Game.getProvince(Game.activeArmy.get(z).iProvinceID).getArmyKeyID(Game.activeArmy.get(z).key);
            if (tempDivID >= 0) {
               int checkCivID = Game.getProvince(Game.activeArmy.get(z).iProvinceID).getArmy(tempDivID).civID;
               String textUnits = "";
               if (Game.FOG_OF_WAR
                  && GameValues.fog.HIDE_ARMIES
                  && checkCivID != Game.player.iCivID
                  && !DiplomacyManager.isAlly(checkCivID, Game.player.iCivID)) {
                  textUnits = GameValues.fog.TEXT_UNKNOWN_ARMIES;
               } else {
                  textUnits = CFG.getNumberWithSpaces("" + Game.getProvince(Game.activeArmy.get(z).iProvinceID).getArmy(tempDivID).iArmy);
               }

               menuElements.add(
                  new ButtonArmyID(
                     Game.getProvince(Game.activeArmy.get(z).iProvinceID).getArmy(tempDivID).civID,
                     Game.getProvince(Game.activeArmy.get(z).iProvinceID).getArmy(tempDivID).provinceID,
                     textUnits,
                     buttonX,
                     buttonY,
                     z
                  ) {
                     @Override
                     public void actionElement() {
                        if (InGame_ProvinceArmy.iActiveID == this.getCurrent()) {
                           if (Game.activeArmySize > 1) {
                              Game.HoveredArmy tempActive = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID);
                              Game.clearActiveArmy();
                              Game.addActiveArmy(tempActive);
                              InGame_ProvinceArmy.iActiveID = 0;
                              InGame_ProvinceArmy.sActiveKEY = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key;
                              Game.menuManager.rebuildInGame_ProvinceArmy();
                           } else {
                              int nArmyID = Game.getProvince(this.iProvinceID).getArmyKeyID(InGame_ProvinceArmy.key);
                              if (nArmyID < 0) {
                                 for (int i = 0; i < Game.getProvincesSize(); i++) {
                                    int outID = Game.getProvince(i).getArmyKeyID(InGame_ProvinceArmy.key, this.iCivID);
                                    if (outID >= 0) {
                                       this.iProvinceID = i;
                                       nArmyID = outID;
                                       break;
                                    }
                                 }
                              }

                              if (nArmyID >= 0) {
                                 if (Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(Game.getProvince(this.iProvinceID).getArmy(nArmyID).key)) {
                                    Game.mapCoords.centerToProvinceID(this.iProvinceID);
                                 } else {
                                    Game.clearActiveArmy();
                                    Game.HoveredArmy nHA = new Game.HoveredArmy();
                                    nHA.key = Game.getProvince(this.iProvinceID).getArmy(nArmyID).key;
                                    nHA.iCivID = Game.getProvince(this.iProvinceID).getArmy(nArmyID).civID;
                                    nHA.iProvinceID = this.iProvinceID;
                                    nHA.iArmyID = nArmyID;
                                    Game.addActiveArmy(nHA);
                                    ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                                 }
                              }
                           }
                        } else {
                           InGame_ProvinceArmy.iActiveID = this.getCurrent();
                           InGame_ProvinceArmy.sActiveKEY = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key;
                           Game.menuManager.rebuildInGame_ProvinceArmy();
                        }
                     }

                     @Override
                     public void actionElementPPM() {
                        if (InGame_ProvinceArmy.iActiveID == this.getCurrent()) {
                           InGame_ProvinceArmy.iActiveID = 0;
                           InGame_ProvinceArmy.sActiveKEY = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key;
                        } else if (this.getCurrent() < InGame_ProvinceArmy.iActiveID) {
                           InGame_ProvinceArmy.iActiveID = Math.max(0, InGame_ProvinceArmy.iActiveID - 1);
                           InGame_ProvinceArmy.sActiveKEY = Game.activeArmy.get(InGame_ProvinceArmy.iActiveID).key;
                        }

                        Game.removeActiveArmy(this.getCurrent());
                        if (Game.activeArmySize > 0) {
                           Game.menuManager.rebuildInGame_ProvinceArmy();
                        } else {
                           Game.menuManager.setVisibleInGame_ProvinceArmy(false);
                        }
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         }
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.max(CFG.PADDING, buttonY);
      this.initMenu(null, mPosX, mPosY, mWidth, i, menuElements, false);
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
