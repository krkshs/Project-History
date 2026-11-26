package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.events.EventsManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Missions.MissionTree;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_OutlinerBattle;
import aoc.kingdoms.lukasz.menu_element.button.Button_OutlinerEspionageMission;
import aoc.kingdoms.lukasz.menu_element.button.Button_OutlinerRelations;
import aoc.kingdoms.lukasz.menu_element.button.Button_PinnedArmy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Mission_Right;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.menusInGame.InGame_Event;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Right extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static boolean outlinerInView = true;
   public static boolean stateVisible = false;
   public static int posY = 0;

   public InGame_Right() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = 1;
      int paddingRight = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = Game.mapBG.iMinimapWidth + Images.boxTitleBORDERWIDTH;
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = CFG.PADDING;
      int buttonH = Math.max(CFG.BUTTON_HEIGHT / 2, Math.max(ImageManager.getImage(Images.flag_rect).getHeight(), CFG.TEXT_HEIGHT) + CFG.PADDING * 2);

      try {
         for (int i = 0; i < Game.player.iActiveEventsSize; i++) {
            if (Game.player.playerData.activeEvents.get(i).eventType == 999) {
               int var10006 = CFG.PADDING * 2;
               menuElements.add(
                  new Text_StaticBG_Mission_Right(
                     MissionTree.lMissions.get(Game.player.playerData.activeEvents.get(i).id).Name,
                     CFG.FONT_REGULAR_SMALL,
                     var10006,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft - paddingRight,
                     Game.player.playerData.activeEvents.get(i).eventType,
                     Game.player.playerData.activeEvents.get(i).id,
                     MissionTree.lMissions.get(Game.player.playerData.activeEvents.get(i).id).event.mission_image,
                     Game.player.playerData.activeEvents.get(i).iTurnID + GameValues.events.EVENT_TIME_TO_RESPOND
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_Event(false);
                        } else {
                           Game.menuManager.rebuildInGame_Event(MissionTree.lMissions.get(this.getCurrent()).event, this.getValue1(), this.getCurrent());
                        }
                     }

                     @Override
                     public int getSFX() {
                        return Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent() ? super.getSFX() : SoundsManager.EVENT;
                     }
                  }
               );
            } else if (Game.player.playerData.activeEvents.get(i).eventType == 1000) {
               int var29 = CFG.PADDING * 2;
               menuElements.add(
                  new Text_StaticBG_Mission_Right(
                     Game.getCiv(Game.player.iCivID).lMissions.get(Game.player.playerData.activeEvents.get(i).id).Name,
                     CFG.FONT_REGULAR_SMALL,
                     var29,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft - paddingRight,
                     Game.player.playerData.activeEvents.get(i).eventType,
                     Game.player.playerData.activeEvents.get(i).id,
                     Game.getCiv(Game.player.iCivID).lMissions.get(Game.player.playerData.activeEvents.get(i).id).event.mission_image,
                     Game.player.playerData.activeEvents.get(i).iTurnID + GameValues.events.EVENT_TIME_TO_RESPOND
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_Event(false);
                        } else {
                           Game.menuManager
                              .rebuildInGame_Event(Game.getCiv(Game.player.iCivID).lMissions.get(this.getCurrent()).event, this.getValue1(), this.getCurrent());
                        }
                     }

                     @Override
                     public int getSFX() {
                        return Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent() ? super.getSFX() : SoundsManager.EVENT;
                     }
                  }
               );
            } else {
               menuElements.add(
                  new Text_StaticBG_Mission_Right(
                     ""
                        + Game.lang
                           .get(
                              EventsManager.getActiveEvent(Game.player.playerData.activeEvents.get(i).id, Game.player.playerData.activeEvents.get(i).eventType).title
                           ),
                     CFG.FONT_REGULAR_SMALL,
                     CFG.PADDING * 2,
                     paddingLeft,
                     buttonY,
                     menuWidth - paddingLeft - paddingRight,
                     Game.player.playerData.activeEvents.get(i).eventType,
                     Game.player.playerData.activeEvents.get(i).id,
                     EventsManager.getActiveEvent(Game.player.playerData.activeEvents.get(i).id, Game.player.playerData.activeEvents.get(i).eventType).mission_image,
                     Game.player.playerData.activeEvents.get(i).iTurnID + GameValues.events.EVENT_TIME_TO_RESPOND
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_Event(false);
                        } else {
                           Game.menuManager
                              .rebuildInGame_Event(EventsManager.getActiveEvent(this.getCurrent(), this.getValue1()), this.getValue1(), this.getCurrent());
                        }
                     }

                     @Override
                     public int getSFX() {
                        return Game.menuManager.getVisibleInGame_Event() && InGame_Event.eventID == this.getCurrent() ? super.getSFX() : SoundsManager.EVENT;
                     }
                  }
               );
            }

            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      } catch (Exception var17) {
         CFG.exceptionStack(var17);
      }

      if (outlinerInView) {
         try {
            for (int i = 0; i < Game.player.playerData.espionage.iEspionageMissionsSize; i++) {
               if (Game.player.playerData.espionage.espionageMissions.get(i).iReportTurnID > Game_Calendar.TURN_ID) {
                  menuElements.add(
                     new Button_OutlinerEspionageMission(
                        Game.lang.get("Progress"),
                        "%",
                        paddingLeft,
                        buttonY,
                        menuWidth - paddingRight - paddingLeft,
                        buttonH,
                        Images.spy,
                        Game.player.playerData.espionage.espionageMissions.get(i).iCivID,
                        Game.player.playerData.espionage.espionageMissions.get(i).iReportTurnID
                           - DiplomacyManager.sendSpyTime(Game.player.iCivID, Game.player.playerData.espionage.espionageMissions.get(i).iCivID),
                        Game.player.playerData.espionage.espionageMissions.get(i).iReportTurnID,
                        true
                     ) {
                        @Override
                        public void actionElement() {
                           InGame.action1();
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }
         } catch (Exception var16) {
            CFG.exceptionStack(var16);
         }

         for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).diplomacy.iImprovingRelationsSize; ix++) {
            menuElements.add(
               new Button_OutlinerRelations(
                  DiplomacyManager.getOpinion_String(
                     (int)Game.getCiv(Game.getCiv(Game.player.iCivID).diplomacy.improvingRelations.get(ix).iCivID).diplomacy.getRelation(Game.player.iCivID)
                  ),
                  "" + (int)Game.getCiv(Game.getCiv(Game.player.iCivID).diplomacy.improvingRelations.get(ix).iCivID).diplomacy.getRelation(Game.player.iCivID),
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingRight - paddingLeft,
                  buttonH,
                  Images.relationsUp,
                  Game.getCiv(Game.player.iCivID).diplomacy.improvingRelations.get(ix).iCivID,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.menuManager.getVisibleInGame_Civ() && InGame_Civ.iActiveCivID == this.getCurrent()) {
                        Game.menuManager.setVisibleInGame_Civ(false);
                     } else {
                        boolean resetTime = Game.menuManager.getVisibleInGame_Civ();
                        InGame_Civ.iRebuildToCivID = this.getCurrent();
                        Game.menuManager.rebuildInGame_Civ();
                        if (resetTime) {
                           InGame_Civ.lTime = 0L;
                        }
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     Game.getCiv(Game.player.iCivID).diplomacy.removeImproveRelations(this.getCurrent());
                     Game.menuManager.addToastNegative(Game.lang.get("Removed") + ": ", "" + Game.getCiv(this.getCurrent()).getCivName(), Images.relationsDown);
                     Game.menuManager.rebuildInGame_Right();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations_RightMenu(
                        this.iCurrent, Game.player.iCivID, this.improvingMode, !this.improvingMode
                     );
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).diplomacy.iDamagingRelationsSize; ix++) {
            menuElements.add(
               new Button_OutlinerRelations(
                  DiplomacyManager.getOpinion_String(
                     (int)Game.getCiv(Game.getCiv(Game.player.iCivID).diplomacy.damagingRelations.get(ix).iCivID).diplomacy.getRelation(Game.player.iCivID)
                  ),
                  "" + (int)Game.getCiv(Game.getCiv(Game.player.iCivID).diplomacy.damagingRelations.get(ix).iCivID).diplomacy.getRelation(Game.player.iCivID),
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingRight - paddingLeft,
                  buttonH,
                  Images.relationsDown,
                  Game.getCiv(Game.player.iCivID).diplomacy.damagingRelations.get(ix).iCivID,
                  false
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.menuManager.getVisibleInGame_Civ() && InGame_Civ.iActiveCivID == this.getCurrent()) {
                        Game.menuManager.setVisibleInGame_Civ(false);
                     } else {
                        boolean resetTime = Game.menuManager.getVisibleInGame_Civ();
                        InGame_Civ.iRebuildToCivID = this.getCurrent();
                        Game.menuManager.rebuildInGame_Civ();
                        if (resetTime) {
                           InGame_Civ.lTime = 0L;
                        }
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     Game.getCiv(Game.player.iCivID).diplomacy.removeDamageRelations(this.getCurrent());
                     Game.menuManager.addToastNegative(Game.lang.get("Removed") + ": ", "" + Game.getCiv(this.getCurrent()).getCivName(), Images.relationsDown);
                     Game.menuManager.rebuildInGame_Right();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations_RightMenu(
                        this.iCurrent, Game.player.iCivID, this.improvingMode, !this.improvingMode
                     );
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      if (Game.player.iCivID > 0) {
         int ix = 0;

         for (int iSize = Game.getCiv(Game.player.iCivID).inBattles.size(); ix < iSize; ix++) {
            Battle battle = Game.battleManager.getBattle(Game.getCiv(Game.player.iCivID).inBattles.get(ix));
            if (battle != null) {
               menuElements.add(
                  new Button_OutlinerBattle(
                     Game.getCiv(Game.player.iCivID).inBattles.get(ix), paddingLeft, buttonY, menuWidth - paddingRight - paddingLeft, buttonH
                  )
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }
         }
      }

      int buttonX = menuWidth - paddingLeft - paddingRight - ImageManager.getImage(Images.pinnedGeneralFrame).getWidth();

      try {
         int paddingGenerals = (int)((menuWidth - paddingLeft - paddingRight - ImageManager.getImage(Images.pinnedGeneralFrame).getWidth() * 4) / 3.0F);

         for (int ix = 0; ix < Game.player.iPinnedArmiesSize; ix++) {
            menuElements.add(new Button_PinnedArmy(Game.player.playerData.pinnedArmies.get(ix), buttonX, buttonY));
            if (buttonX - ImageManager.getImage(Images.pinnedGeneralFrame).getWidth() + paddingGenerals < 0) {
               buttonX = menuWidth - paddingLeft - paddingRight - ImageManager.getImage(Images.pinnedGeneralFrame).getWidth();
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            } else {
               buttonX -= ImageManager.getImage(Images.pinnedGeneralFrame).getWidth() + paddingGenerals;
            }
         }
      } catch (Exception var15) {
         CFG.exceptionStack(var15);
      }

      buttonY = 0;
      int ixx = 0;

      for (int iSizex = menuElements.size(); ixx < iSizex; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight()) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight();
         }
      }

      buttonY += CFG.PADDING;
      posY = menuY + buttonY;
      ixx = Math.min(
         buttonY,
         Math.max(
            CFG.BUTTON_HEIGHT,
            CFG.GAME_HEIGHT
               - menuY
               - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3)
               - (buttonH + CFG.PADDING) * GameValues.notifications.NUMBER_OF_NOTIFICATIONS
         )
      );
      this.initMenu(null, menuX, menuY, menuWidth, ixx, menuElements, true, false);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      stateVisible = visible;
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut() && !Game.menuManager.getVisibleInGame_CivBonuses();
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_RightQueue.extraY;
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_RightQueue.extraY;
   }
}
