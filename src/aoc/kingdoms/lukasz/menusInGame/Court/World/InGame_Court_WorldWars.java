package aoc.kingdoms.lukasz.menusInGame.Court.World;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MessageWar;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStats_WarScore;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.InGame_War;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_WorldWars extends Menu {
   public InGame_Court_WorldWars() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.PADDING * 2 + InGame_CourtOptions2.getMenuWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = paddingLeft;
      int buttonY = CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get(GameValues.court.COUNCIL_NAME),
            Images.council,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Court();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(GameValues.court.COUNCIL_NAME), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return SoundsManager.getClickSound_CivOptions();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int maxWidth = ImageManager.getImage(Images.war).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int warscoreW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 2;
      int casualtiesW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 4;
      int statsH = CFG.TEXT_HEIGHT + CFG.PADDING * 6;
      int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("CurrentWars"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (WarManager.iWarsSize > 0) {
         for (War nWar : WarManager.lWars.values()) {
            try {
               menuElements.add(
                  new ButtonFlag_Diplomacy(
                     WarManager.lWars.get(nWar.key).lAggressors.get(0).iCivID,
                     menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
                     buttonY + tempTitlePaddingY,
                     true
                  )
               );
               int var10004 = menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2;
               menuElements.add(new ButtonFlag_Diplomacy(WarManager.lWars.get(nWar.key).lDefenders.get(0).iCivID, var10004, buttonY + tempTitlePaddingY, true));
               menuElements.add(
                  new Text_Static_ID(
                     WarManager.lWars.get(nWar.key).lDefenders.get(0).iCivID,
                     Game.getCiv(WarManager.lWars.get(nWar.key).lDefenders.get(0).iCivID).getCivName(),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
                     buttonY,
                     tempTextW,
                     tempTitleH
                  )
               );
               menuElements.add(
                  new Text_Static_ID(
                     WarManager.lWars.get(nWar.key).lAggressors.get(0).iCivID,
                     Game.getCiv(WarManager.lWars.get(nWar.key).lAggressors.get(0).iCivID).getCivName(),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     paddingLeft,
                     buttonY,
                     tempTextW,
                     tempTitleH
                  )
               );
               menuElements.add(new TextIcon_Diplomacy(Images.war, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
                  public String warKey;

                  @Override
                  public void setText2(String sText) {
                     this.warKey = sText;
                  }

                  @Override
                  public void actionElement() {
                     if (this.warKey != null) {
                        if (Game.menuManager.getVisibleInGame_War() && InGame_War.key.equals(this.warKey)) {
                           Game.menuManager.setVisibleInGame_War(false);
                        } else {
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.clearActiveArmy();
                           Game.menuManager.showInGame_Battle_HideMenus();
                           InGame_War.key = this.warKey;
                           Game.menuManager.rebuildInGame_War();
                           if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_WAR_VIEW) {
                              Game.mapModes.setActiveViewID(Game.mapModes.MODE_WAR_VIEW);
                           } else {
                              Game.mapModes.updateWarView(InGame_War.key);
                           }
                        }
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = MessageWar.getHoverWar(this.warKey, WarManager.lWars.get(this.warKey).lDefenders.get(0).iCivID);
                  }
               });
               menuElements.get(menuElements.size() - 1).setText2(nWar.key);
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG_Diplomacy(
                     "" + CFG.getShortNumber(WarManager.lWars.get(nWar.key).getCasualties_Aggressors()),
                     Images.skull,
                     paddingLeft,
                     buttonY,
                     casualtiesW,
                     statsH,
                     maxIconW,
                     0
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Casualties") + ": ", CFG.FONT_BOLD));
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.skull, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               menuElements.add(
                  new ButtonStatsRectIMG_Diplomacy(
                     "" + CFG.getShortNumber(WarManager.lWars.get(nWar.key).getCasualties_Aggressors()),
                     Images.skull,
                     menuWidth - paddingLeft - casualtiesW,
                     buttonY,
                     casualtiesW,
                     statsH,
                     maxIconW,
                     0
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Casualties") + ": ", CFG.FONT_BOLD));
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE));
                        nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.skull, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               menuElements.add(
                  new ButtonStats_WarScore(
                     Game.lang.get("WarScore") + ": ",
                     paddingLeft + casualtiesW + CFG.PADDING,
                     buttonY,
                     warscoreW,
                     statsH,
                     WarManager.lWars.get(nWar.key).lAggressors.get(0).iCivID,
                     WarManager.lWars.get(nWar.key).lDefenders.get(0).iCivID,
                     nWar.key
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();

                        try {
                           float lastValue2 = WarManager.lWars.get(this.key).getWarScore_Side(this.getCurrent());
                           nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("WarScore") + ": ", CFG.FONT_BOLD));
                           nData.add(
                              new MenuElement_HoverElement_Type_TextTitle(
                                 CFG.getPrecision2(Math.min(Math.max(WarManager.lWars.get(this.key).warScore * lastValue2, -100.0F), 100.0F), 1) + "%",
                                 CFG.FONT_BOLD,
                                 Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                              )
                           );
                           nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.victoryPoints, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           if (Math.abs(WarManager.lWars.get(this.key).warScore) >= 1.0F) {
                              nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("InFavorOf") + ": ", CFG.FONT_REGULAR));
                              nData.add(
                                 new MenuElement_HoverElement_Type_TextTitle(
                                    Game.getCiv(
                                          WarManager.lWars.get(this.key).warScore > 0.0F
                                             ? WarManager.lWars.get(this.key).lAggressors.get(0).iCivID
                                             : WarManager.lWars.get(this.key).lDefenders.get(0).iCivID
                                       )
                                       .getCivName(),
                                    CFG.FONT_BOLD,
                                    Colors.HOVER_POSITIVE
                                 )
                              );
                              nData.add(
                                 new MenuElement_HoverElement_Type_FlagTitle(
                                    WarManager.lWars.get(this.key).warScore > 0.0F
                                       ? WarManager.lWars.get(this.key).lAggressors.get(0).iCivID
                                       : WarManager.lWars.get(this.key).lDefenders.get(0).iCivID,
                                    CFG.PADDING,
                                    0
                                 )
                              );
                              nElements.add(new MenuElement_HoverElement(nData));
                              nData.clear();
                           }

                           nData.add(new MenuElement_HoverElement_Type_Line());
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("OccupiedProvinces") + ": ", CFG.FONT_REGULAR_SMALL));
                           nData.add(
                              new MenuElement_HoverElement_Type_Text(
                                 CFG.getPrecision2(WarManager.lWars.get(this.key).warScoreFromOccupiedProvinces * lastValue2, 10) + "%",
                                 CFG.FONT_BOLD_SMALL,
                                 Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                              )
                           );
                           nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("WarScoreFromBattles") + ": ", CFG.FONT_REGULAR_SMALL));
                           nData.add(
                              new MenuElement_HoverElement_Type_Text(
                                 CFG.getPrecision2(WarManager.lWars.get(this.key).warScoreFromBattles * lastValue2, 10) + "%",
                                 CFG.FONT_BOLD_SMALL,
                                 Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                              )
                           );
                           nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TickingWarScore") + ": ", CFG.FONT_REGULAR_SMALL));
                           nData.add(
                              new MenuElement_HoverElement_Type_Text(
                                 CFG.getPrecision2(WarManager.lWars.get(this.key).tickingWarScore * lastValue2, 10) + "%",
                                 CFG.FONT_BOLD_SMALL,
                                 Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                              )
                           );
                           nData.add(new MenuElement_HoverElement_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(new MenuElement_HoverElement_Type_Line());
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                           nData.add(
                              new MenuElement_HoverElement_Type_Text_Desc(
                                 Game.lang
                                    .get(
                                       "EachMonthTheWinningSideGainsXOfTheTickingWarScoreBasedOnTheCurrentOverallWarScoreFromBattlesAndOccupiedProvinces",
                                       CFG.getPrecision2(GameValues.war.TICKING_WAR_SCORE_EACH_MONTH * 100.0F, 100)
                                    ),
                                 CFG.FONT_REGULAR_SMALL,
                                 Colors.HOVER_RIGHT2
                              )
                           );
                           nElements.add(new MenuElement_HoverElement(nData));
                           nData.clear();
                        } catch (Exception var4) {
                        }

                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }

                     @Override
                     public void actionElement() {
                        if (this.key != null) {
                           if (Game.menuManager.getVisibleInGame_War() && InGame_War.key.equals(this.key)) {
                              Game.menuManager.setVisibleInGame_War(false);
                           } else {
                              Game.gameActiveProvince.resetLastActiveProvince();
                              Game.setActiveProvinceID(-1);
                              Game.clearActiveArmy();
                              Game.menuManager.showInGame_Battle_HideMenus();
                              InGame_War.key = this.key;
                              Game.menuManager.rebuildInGame_War();
                              if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_WAR_VIEW) {
                                 Game.mapModes.setActiveViewID(Game.mapModes.MODE_WAR_VIEW);
                              } else {
                                 Game.mapModes.updateWarView(InGame_War.key);
                              }
                           }
                        }
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            } catch (Exception var20) {
               CFG.exceptionStack(var20);
            }
         }
      } else {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("None") + ".",
               CFG.FONT_REGULAR_SMALL,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get(Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.Name));
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }
}
