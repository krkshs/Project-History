package aoc.kingdoms.lukasz.menusInGame.Buildings;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.Toast;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding2_NotAvailable;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceBonuses;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_BuildingsGroupID extends Menu {
   public int groupID;

   public InGame_BuildingsGroupID(int groupID) {
      List<MenuElement> menuElements = new ArrayList<>();
      this.groupID = groupID;
      int paddingLeft = CFG.PADDING * 2;
      int buttonY = CFG.PADDING * 2;
      int menuWidth = (ImageManager.getImage(Images.title928).getWidth() - Images.boxTitleBORDERWIDTH * 2) / 4;
      List<Point_XY> notAvailable = new ArrayList<>();
      List<Point_XY> notAvailableTech = new ArrayList<>();
      if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID() >= 0
         && ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID >= 0
         && BuildingsManager.buildings
                  .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
               .GroupID
            == groupID) {
         for (int j = 0;
            j
               < BuildingsManager.buildingSize
                  .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID);
            j++
         ) {
            boolean addBuilding = true;
            if (!Game.getCiv(Game.player.iCivID)
               .isBuildingResearched(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID, j)) {
               addBuilding = false;
               if (BuildingsManager.buildings
                     .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                  .ShowUpgrades) {
                  notAvailableTech.add(
                     new Point_XY(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID, j)
                  );
               }
            } else if (j > 0
               && !Game.getProvince(InGame_BuildingsGroup.iProvinceID)
                  .buildingBuilt(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID, j - 1)) {
               addBuilding = false;
               if (BuildingsManager.buildings
                     .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                  .ShowUpgrades) {
                  notAvailable.add(
                     new Point_XY(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID, j)
                  );
               }
            } else if (BuildingsManager.buildings
                     .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                  .SeaAccessRequired
               && Game.getProvince(InGame_BuildingsGroup.iProvinceID).getLevelOfPort() < 0) {
               addBuilding = false;
            } else if (BuildingsManager.buildings
                        .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                     .RequiredGovernmentID
                  >= 0
               && BuildingsManager.buildings
                        .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                     .RequiredGovernmentID
                  != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getIdeologyID()) {
               addBuilding = false;
            } else if (BuildingsManager.buildings
                        .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                     .RequiredReligionID
                  >= 0
               && BuildingsManager.buildings
                        .get(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID)
                     .RequiredReligionID
                  != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getReligionID()) {
               addBuilding = false;
            }

            if (addBuilding) {
               menuElements.add(
                  new ButtonBuilding2(
                     InGame_BuildingsGroup.iProvinceID,
                     Game.getProvince(InGame_BuildingsGroup.iProvinceID)
                        .buildingBuilt(ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID, j),
                     ResourcesManager.lResources.get(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getResourceID()).uniqueBuildingID,
                     j,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft * 2,
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID() == Game.player.iCivID) {
                           if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).buildingBuilt(this.getValue1(), this.getValue2())) {
                              InGame_Destroy.iProvinceID = InGame_BuildingsGroup.iProvinceID;
                              InGame_Destroy.building = this.getValue1();
                              InGame_Destroy.buildingID = this.getValue2();
                              Game.menuManager.rebuildInGame_Destroy();
                           } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).getUsedBuildingsSlots()
                              >= Game.getProvince(InGame_BuildingsGroup.iProvinceID).iBuildingsLimit) {
                              List<MenuElement_HoverElement> nElements = new ArrayList<>();
                              List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                              nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BuildingSlots") + ": "));
                              nData.add(
                                 new MenuElement_HoverElement_Type_Text(
                                    Game.getProvince(InGame_BuildingsGroup.iProvinceID).getUsedBuildingsSlots()
                                       + " / "
                                       + Game.getProvince(InGame_BuildingsGroup.iProvinceID).iBuildingsLimit,
                                    Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                                 )
                              );
                              nData.add(new MenuElement_HoverElement_Type_Image(Images.build, CFG.PADDING, 0));
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                              nData.add(
                                 new MenuElement_HoverElement_Type_Text(Game.lang.get("IncreaseEconomyInAProvinceToUnlockMoreBuildingSlots"), Colors.HOVER_GOLD)
                              );
                              nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                              Game.menuManager.addToast(new Toast(nElements, 0, 6000));
                           } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).isOccupied()) {
                              Game.menuManager.addToast_Error(Game.lang.get("OccupiedProvince"), Images.war);
                           } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).isUnderConstruction(this.getValue1(), this.getValue2())) {
                              Game.menuManager.addToastGold(Game.lang.get("UnderConstruction"), Images.build);
                           } else if (BuildingsManager.buildings.get(this.getValue1()).RequiredReligionID >= 0
                              && BuildingsManager.buildings.get(this.getValue1()).RequiredReligionID
                                 != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getReligionID()) {
                              Game.menuManager
                                 .addToastGold(
                                    Game.lang.get("Religion")
                                       + ": "
                                       + Game.religionManager.getReligion(BuildingsManager.buildings.get(this.getValue1()).RequiredReligionID).Name,
                                    Images.religion
                                 );
                           } else if (BuildingsManager.buildings.get(this.getValue1()).RequiredGovernmentID >= 0
                              && BuildingsManager.buildings.get(this.getValue1()).RequiredGovernmentID
                                 != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getIdeologyID()) {
                              Game.menuManager
                                 .addToastGold(
                                    Game.lang.get("Government")
                                       + ": "
                                       + Game.ideologiesManager.getIdeology(BuildingsManager.buildings.get(this.getValue1()).RequiredGovernmentID).Name,
                                    Images.government
                                 );
                           } else if (!Game.getProvince(InGame_BuildingsGroup.iProvinceID).addBuildingConstruction(this.getValue1(), this.getValue2())) {
                              Game.menuManager
                                 .addToastInsufficient(
                                    Game.lang.get("InsufficientGold") + ": ",
                                    CFG.getPrecision2(
                                       (float)Game.getBuildingConstructionCost(
                                          Game.player.iCivID, InGame_BuildingsGroup.iProvinceID, this.getValue1(), this.getValue2()
                                       ),
                                       100
                                    ),
                                    Images.gold
                                 );
                           } else if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                              Game.menuManager.rebuildInGame_ProvinceInfo(false);
                              Game.menuManager.setOrderInGame_Buildings();
                              if (Game.menuManager.getVisibleInGame_ProvinceBonuses()) {
                                 Game.menuManager.rebuildInGame_ProvinceBonuses();
                                 Game.menuManager.setVisibleInGame_ProvinceBonuses(true, false);
                                 InGame_ProvinceBonuses.lTime = 0L;
                              }
                           }
                        }
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
            }
         }
      }

      for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
         if (BuildingsManager.buildings.get(i).GroupID == groupID) {
            for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
               boolean addBuildingx = true;
               if (!Game.getProvince(InGame_BuildingsGroup.iProvinceID).buildingBuilt(i, j)) {
                  if (!Game.getCiv(Game.player.iCivID).isBuildingResearched(i, j)) {
                     addBuildingx = false;
                     if (BuildingsManager.buildings.get(i).ShowUpgrades) {
                        notAvailableTech.add(new Point_XY(i, j));
                     }
                  } else if (BuildingsManager.buildings.get(i).SeaAccessRequired && Game.getProvince(InGame_BuildingsGroup.iProvinceID).getLevelOfPort() < 0) {
                     addBuildingx = false;
                  } else if (BuildingsManager.buildings.get(i).RequiredGovernmentID >= 0
                     && BuildingsManager.buildings.get(i).RequiredGovernmentID
                        != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getIdeologyID()) {
                     addBuildingx = false;
                  } else if (BuildingsManager.buildings.get(i).RequiredReligionID >= 0
                     && BuildingsManager.buildings.get(i).RequiredReligionID
                        != Game.getCiv(Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID()).getReligionID()) {
                     addBuildingx = false;
                  }
               }

               if (addBuildingx) {
                  menuElements.add(
                     new ButtonBuilding2(
                        InGame_BuildingsGroup.iProvinceID,
                        Game.getProvince(InGame_BuildingsGroup.iProvinceID).buildingBuilt(i, j),
                        i,
                        j,
                        paddingLeft,
                        buttonY,
                        menuWidth - paddingLeft * 2,
                        true
                     ) {
                        @Override
                        public void actionElement() {
                           if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).getCivID() == Game.player.iCivID) {
                              if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).buildingBuilt(this.getValue1(), this.getValue2())) {
                                 InGame_Destroy.iProvinceID = InGame_BuildingsGroup.iProvinceID;
                                 InGame_Destroy.building = this.getValue1();
                                 InGame_Destroy.buildingID = this.getValue2();
                                 Game.menuManager.rebuildInGame_Destroy();
                              } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).getUsedBuildingsSlots()
                                 >= Game.getProvince(InGame_BuildingsGroup.iProvinceID).iBuildingsLimit) {
                                 List<MenuElement_HoverElement> nElements = new ArrayList<>();
                                 List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                                 nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BuildingSlots") + ": "));
                                 nData.add(
                                    new MenuElement_HoverElement_Type_Text(
                                       Game.getProvince(InGame_BuildingsGroup.iProvinceID).getUsedBuildingsSlots()
                                          + " / "
                                          + Game.getProvince(InGame_BuildingsGroup.iProvinceID).iBuildingsLimit,
                                       Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                                    )
                                 );
                                 nData.add(new MenuElement_HoverElement_Type_Image(Images.build, CFG.PADDING, 0));
                                 nElements.add(new MenuElement_HoverElement(nData));
                                 nData.clear();
                                 nData.add(
                                    new MenuElement_HoverElement_Type_Text(
                                       Game.lang.get("IncreaseEconomyInAProvinceToUnlockMoreBuildingSlots"), Colors.HOVER_GOLD
                                    )
                                 );
                                 nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
                                 nElements.add(new MenuElement_HoverElement(nData));
                                 nData.clear();
                                 Game.menuManager.addToast(new Toast(nElements, 0, 6000));
                              } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).isOccupied()) {
                                 Game.menuManager.addToast_Error(Game.lang.get("OccupiedProvince"), Images.war);
                              } else if (Game.getProvince(InGame_BuildingsGroup.iProvinceID).isUnderConstruction(this.getValue1(), this.getValue2())) {
                                 Game.menuManager.addToastGold(Game.lang.get("UnderConstruction"), Images.build);
                              } else if (!Game.getProvince(InGame_BuildingsGroup.iProvinceID).addBuildingConstruction(this.getValue1(), this.getValue2())) {
                                 Game.menuManager
                                    .addToastInsufficient(
                                       Game.lang.get("InsufficientGold") + ": ",
                                       CFG.getPrecision2(
                                          (float)Game.getBuildingConstructionCost(
                                             Game.player.iCivID, InGame_BuildingsGroup.iProvinceID, this.getValue1(), this.getValue2()
                                          ),
                                          100
                                       ),
                                       Images.gold
                                    );
                              } else if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                                 Game.menuManager.rebuildInGame_ProvinceInfo(false);
                                 Game.menuManager.setOrderInGame_Buildings();
                                 if (Game.menuManager.getVisibleInGame_ProvinceBonuses()) {
                                    Game.menuManager.rebuildInGame_ProvinceBonuses();
                                    Game.menuManager.setVisibleInGame_ProvinceBonuses(true, false);
                                    InGame_ProvinceBonuses.lTime = 0L;
                                 }
                              }
                           }
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
               }
            }
         }
      }

      if (notAvailable.size() > 0) {
         menuElements.add(new Text_Title(Game.lang.get("NotAvailable"), -1, 0, buttonY, menuWidth, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_REGULAR_SMALL) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorTopStats3(isActive, this.getIsHovered());
            }

            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         int ix = 0;

         for (int iSize = notAvailable.size(); ix < iSize; ix++) {
            menuElements.add(
               new ButtonBuilding2_NotAvailable(
                  InGame_BuildingsGroup.iProvinceID,
                  false,
                  notAvailable.get(ix).getPosX(),
                  notAvailable.get(ix).getPosY(),
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  true,
                  false,
                  false,
                  false
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         }
      }

      if (GameValues.inGame.SHOW_TO_BE_RESEARCHED_BUILDINGS && notAvailableTech.size() > 0) {
         menuElements.add(
            new Text_Title(Game.lang.get("ToBeResearched"), -1, 0, buttonY, menuWidth, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_REGULAR_SMALL) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.getColorTopStats3(isActive, this.getIsHovered());
               }

               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         int ix = 0;

         for (int iSize = notAvailableTech.size(); ix < iSize; ix++) {
            menuElements.add(
               new ButtonBuilding2_NotAvailable(
                  InGame_BuildingsGroup.iProvinceID,
                  false,
                  notAvailableTech.get(ix).getPosX(),
                  notAvailableTech.get(ix).getPosY(),
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  true,
                  true,
                  false,
                  false
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                        Game.menuManager.setVisibleInGame_TechnologyTree(false);
                     } else {
                        Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                        InGame_TechnologyTree.centerToTechID = BuildingsManager.buildings.get(this.getValue1()).RequiredTechID[this.getValue2()];
                        Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                     }
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         }
      }

      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, InGame_BuildingsGroup.menuHeight)));
      this.initMenu(
         null,
         InGame_BuildingsGroup.menuX + menuWidth * groupID,
         InGame_BuildingsGroup.menuY,
         menuWidth,
         InGame_BuildingsGroup.menuHeight,
         menuElements,
         false,
         false
      );
      this.drawScrollPositionAlways2 = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      iTranslateX = InGame_BuildingsGroup.mTranslateX;
      if (this.groupID % 2 == 1) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.325F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight());
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight(), true, false
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameBuildings();
   }
}
