package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.AddNewCivData;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGame_AddCiv extends Menu {
   protected static final int ANIMATION_TIME = 0;
   public static long lTime = 0L;
   public static AddNewCivData addNewCivData = new AddNewCivData();

   public NewGame_AddCiv() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int tTitleH = CFG.TEXT_HEIGHT + CFG.PADDING * 6;
      int tTitleH_Inner = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int tMaxInconWidth = ImageManager.getImage(Images.gold).getWidth();
      menuElements.add(new ButtonGame2(Game.lang.get("Back"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2) {
         @Override
         public void actionElement() {
            CFG.brushTool = false;
            CFG.selectMode = true;
            Game.menuManager.setViewID(View.NEW_GAME);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("Civilization")
               + (addNewCivData.sCivTag != null && addNewCivData.sCivTag.length() > 0 ? ": " + Game.lang.getCiv(addNewCivData.sCivTag) : ""),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            tTitleH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (addNewCivData.sCivTag != null && addNewCivData.sCivTag.length() > 0) {
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.getCiv(addNewCivData.sCivTag),
               Images.invasion,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  CFG.brushTool = false;
                  CFG.selectMode = true;
                  NewGame_SelectCivilization_Add_List.selectedCivTAG = "";
                  Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME_ADD_CIV_SELECT);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectCivilization"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.invasion, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilization") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.getCiv(NewGame_AddCiv.addNewCivData.sCivTag), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.get(Game.ideologiesManager.getIdeology(Game.ideologiesManager.getIdeologyID(addNewCivData.sCivTag)).Name)
                  + ": "
                  + Game.lang.get("ChangeTypeOfGovernment"),
               Images.government,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME_ADD_CIV_GOV);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ChangeTypeOfGovernment"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.government, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Government") + ": "));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.lang.get(Game.ideologiesManager.getIdeology(Game.ideologiesManager.getIdeologyID(NewGame_AddCiv.addNewCivData.sCivTag)).Name),
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.government, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.get("AddNewCivilization"),
               Images.v,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  CFG.brushTool = false;
                  CFG.selectMode = true;
                  if (NewGame_AddCiv.addNewCivData.lProvinces.size() > 0) {
                     if (Game.addCivilization(NewGame_AddCiv.addNewCivData.sCivTag, -1, false, false, false, false, false)) {
                        int civID = Game.getCivID(NewGame_AddCiv.addNewCivData.sCivTag);
                        if (civID > 0) {
                           for (int i = NewGame_AddCiv.addNewCivData.lProvinces.size() - 1; i >= 0; i--) {
                              Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(i)).setCivID(civID);
                              Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(i)).addCore(civID);
                              Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(i)).setReligion(Game.getCiv(civID).getReligionID());
                           }

                           Game.getCiv(civID).setCapitalProvinceID(NewGame_AddCiv.addNewCivData.lProvinces.get(0));
                           if (Game.getCiv(civID).fGold < Math.max(100.0F, Game.getCiv(1).fGold)) {
                              Game.getCiv(civID).fGold = Math.max(100.0F, Game.getCiv(1).fGold);
                           }

                           if (Game.getCiv(civID).fLegacy < Game.getCiv(1).fLegacy) {
                              Game.getCiv(civID).fLegacy = Game.getCiv(1).fLegacy;
                           }

                           if (Game.getCiv(civID).fDiplomacy < Game.getCiv(1).fDiplomacy) {
                              Game.getCiv(civID).fDiplomacy = Game.getCiv(1).fDiplomacy;
                           }

                           if (Game.getCiv(civID).fManpower < Game.getCiv(1).fManpower) {
                              Game.getCiv(civID).fManpower = Game.getCiv(1).fManpower;
                           }

                           try {
                              for (int i = 0; i < NewGame_AddCiv.addNewCivData.technologyID; i++) {
                                 if (!Game.getCiv(civID).getTechResearched(i)) {
                                    Game.getCiv(civID).addTechnology(i, true);
                                 }
                              }
                           } catch (Exception var3) {
                           }

                           Game.getCiv(civID).buildLaws();
                           Game.getCiv(civID).initMaxLaws();
                           Game.getCiv(civID).initGoodsProduced();
                           Game.getCiv(civID).updateBestUnits();
                           Game.getCiv(civID).updateRegimentsLimit();
                           Game.getCiv(civID).updateDiplomacyPerMonth();
                           if (Game.getCiv(civID).getCapitalProvinceID() < 0 || Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getCivID() != civID
                              )
                            {
                              Game.getCiv(civID).moveCapital_ToLargestProvince();
                           } else if (Game.getCiv(civID).getCapitalProvinceID() >= 0
                              && Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getCivID() == civID) {
                              Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).setIsCapital(true);
                           }

                           CivilizationRanking.buildCivilizationRanking();
                           Game.mapScenarios.buildStartingArmy(civID);
                           Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME);
                        } else {
                           Game.menuManager.addToast_Error(Game.lang.get("Civilization") + ": " + Game.lang.get("NotFound"), Images.provinces);
                        }
                     } else {
                        Game.menuManager.addToast_Error(Game.lang.get("CivilizationExist"), Images.provinces);
                     }
                  } else {
                     Game.menuManager.addToast_Error(Game.lang.get("Provinces") + ": " + NewGame_AddCiv.addNewCivData.lProvinces.size(), Images.provinces);
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("AddNewCivilization"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.v, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilization") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.getCiv(NewGame_AddCiv.addNewCivData.sCivTag), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Government") + ": "));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.lang.get(Game.ideologiesManager.getIdeology(Game.ideologiesManager.getIdeologyID(NewGame_AddCiv.addNewCivData.sCivTag)).Name),
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.government, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new Text_Title_v2(
               Game.lang.get("Technology"),
               CFG.FONT_BOLD,
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               tTitleH
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         try {
            menuElements.add(
               new ButtonTechnology(
                  Images.techGray, addNewCivData.technologyID, menuWidth / 2 - TechnologyTree.iTechnologyWidth / 2, buttonY, false, addNewCivData.technologyID
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setViewID(View.NEW_GAME_ADD_CIV_TECH);
                  }

                  @Override
                  protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     if (this.getCurrent() == NewGame_AddCiv.addNewCivData.technologyID) {
                        this.btnIMG = Images.techResearched;
                     } else {
                        this.btnIMG = Images.techGray;
                     }

                     super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } catch (Exception var13) {
         }
      } else {
         menuElements.add(
            new ButtonCurrentSituation(
               Game.lang.get("SelectCivilization")
                  + (addNewCivData.sCivTag != null && addNewCivData.sCivTag.length() > 0 ? ": " + Game.lang.getCiv(addNewCivData.sCivTag) : ""),
               Images.invasion,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  NewGame_SelectCivilization_Add_List.selectedCivTAG = "";
                  Game.menuManager.setViewIDWithoutAnimation(View.NEW_GAME_ADD_CIV_SELECT);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectCivilization"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.invasion, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("CapitalCity"),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            tTitleH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.getProvince(addNewCivData.lProvinces.get(0)).getProvinceName(),
            Images.capital,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               if (Game.iActiveProvince >= 0
                  && !Game.getProvince(Game.iActiveProvince).getSeaProvince()
                  && Game.getProvince(Game.iActiveProvince).getWasteland() < 0
                  && !Game.getProvince(Game.iActiveProvince).isCapital
                  && NewGame_AddCiv.addNewCivData.lProvinces.contains(Game.iActiveProvince)
                  && NewGame_AddCiv.addNewCivData.lProvinces.get(0) != Game.iActiveProvince) {
                  List<Integer> tProvs = new ArrayList<>();

                  for (int i = 0; i < NewGame_AddCiv.addNewCivData.lProvinces.size(); i++) {
                     if (NewGame_AddCiv.addNewCivData.lProvinces.get(i) != Game.iActiveProvince) {
                        tProvs.add(NewGame_AddCiv.addNewCivData.lProvinces.get(i));
                     }
                  }

                  NewGame_AddCiv.addNewCivData.lProvinces.clear();
                  NewGame_AddCiv.addNewCivData.lProvinces.add(Game.iActiveProvince);

                  for (int ix = 0; ix < tProvs.size(); ix++) {
                     NewGame_AddCiv.addNewCivData.lProvinces.add(tProvs.get(ix));
                  }

                  Game.menuManager.rebuildNewGameAddCiv();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CapitalCity") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                     Game.getProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(0)).getProvinceName(), Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("Map") + ": " + Game.lang.get("Provinces") + ": " + CFG.getNumberWithSpaces("" + addNewCivData.lProvinces.size()),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            tTitleH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get("Brush") + ": " + (CFG.brushTool ? Game.lang.get("Enabled") : Game.lang.get("Disabled")),
            Images.brush,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               if (!CFG.brushTool) {
                  CFG.brushTool = true;
                  this.setText(Game.lang.get("Brush") + ": " + Game.lang.get("Enabled"));
               } else {
                  CFG.brushTool = false;
                  this.setText(Game.lang.get("Brush") + ": " + Game.lang.get("Disabled"));
               }
            }

            @Override
            public boolean getIsHovered() {
               return super.getIsHovered() || CFG.brushTool;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Brush"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.brush, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get("Select") + ": " + Game.lang.get("Province") + ": " + (CFG.selectMode ? Game.lang.get("Add") : Game.lang.get("Remove")),
            Images.brush,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.SOUND_CLICK_TOP;
            }

            @Override
            public void actionElement() {
               if (!CFG.selectMode) {
                  CFG.selectMode = true;
                  this.setText(Game.lang.get("Select") + ": " + Game.lang.get("Province") + ": " + Game.lang.get("Add"));
               } else {
                  CFG.selectMode = false;
                  this.setText(Game.lang.get("Select") + ": " + Game.lang.get("Province") + ": " + Game.lang.get("Remove"));
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2(
            Game.lang.get("Provinces") + ": " + CFG.getNumberWithSpaces("" + addNewCivData.lProvinces.size()),
            CFG.FONT_BOLD,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            tTitleH
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < addNewCivData.lProvinces.size(); i++) {
         menuElements.add(
            new ButtonCurrentSituation(
               Game.getProvince(addNewCivData.lProvinces.get(i)).getProvinceName(),
               Images.provinces,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT4,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true
            ) {
               int id = 0;

               @Override
               public int getCurrent() {
                  return this.id;
               }

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               public int getSFX() {
                  return SoundsManager.SOUND_CLICK_TOP;
               }

               @Override
               public void actionElement() {
                  NewGame_AddCiv.addNewCivData.removeProvince(NewGame_AddCiv.addNewCivData.lProvinces.get(this.getCurrent()));
                  Game.menuManager.rebuildNewGameAddCiv();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Remove") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      buttonY += CFG.PADDING;
      int menuHeight = Math.min(
         buttonY,
         CFG.GAME_HEIGHT
            - menuY
            - (
               Game.menuManager.getInGame()
                  ? CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3
                  : ImageManager.getImage(Images.buttonPlay).getHeight() + CFG.PADDING * 3
            )
      );
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("AddNewCivilization"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return NewGame_AddCiv.lTime;
         }
      }, Game.menuManager.getInGame() ? CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 2 : CFG.PADDING * 2, menuY, menuWidth, menuHeight, menuElements, true, false);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 0L >= CFG.currentTimeMillis) {
         if (Game.menuManager.getInGame()) {
            iTranslateX = iTranslateX + CFG.BUTTON_WIDTH - (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 0.0F));
         } else {
            iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 0.0F));
         }
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
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.keyboard.hideKeyboard();
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible) {
         Game.keyboard.hideKeyboard();
      }
   }
}
