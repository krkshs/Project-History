package aoc.kingdoms.lukasz.jakowski.Player.Notification;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivBonuses;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class Notification {
   public int imageID;
   public String sText;
   public int iTurnID;
   public Notification.Notification_Type notificationType;
   public Notification.Notification_BG notificationBG;
   public String key;
   public int id;
   public long lTime;

   public Notification(Notification.Notification_Type notificationType, String sText, int imageID, int iTurnID, Notification.Notification_BG notificationBG) {
      this.init(sText, imageID, iTurnID, notificationBG);
      this.notificationType = notificationType;
   }

   public Notification(
      Notification.Notification_Type notificationType, String sText, int imageID, int iTurnID, Notification.Notification_BG notificationBG, String key
   ) {
      this.init(sText, imageID, iTurnID, notificationBG);
      this.notificationType = notificationType;
      this.key = key;
   }

   public Notification(
      Notification.Notification_Type notificationType, String sText, int imageID, int iTurnID, Notification.Notification_BG notificationBG, String key, int id
   ) {
      this.init(sText, imageID, iTurnID, notificationBG);
      this.notificationType = notificationType;
      this.key = key;
      this.id = id;
   }

   public Notification(
      Notification.Notification_Type notificationType, String sText, int imageID, int iTurnID, Notification.Notification_BG notificationBG, int id
   ) {
      this.init(sText, imageID, iTurnID, notificationBG);
      this.notificationType = notificationType;
      this.id = id;
   }

   public void init(String sText, int imageID, int iTurnID, Notification.Notification_BG notificationBG) {
      this.sText = sText;
      this.imageID = imageID;
      this.iTurnID = iTurnID;
      this.notificationBG = notificationBG;
      this.lTime = CFG.currentTimeMillis;
   }

   public void onAction() {
      switch (this.notificationType) {
         case BATTLE_REPORT:
            Game.menuManager.rebuildInGame_BattleReport(this.key);
            break;
         case DISEASE:
         case HIGH_UNREST:
            Game.mapCoords.centerToProvinceID(this.id);
            if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
               Game.setActiveProvinceID(this.id);
               ProvinceTouchExtraAction.actionSetActiveProvinceID();
            }
            break;
         case RELATIONS_COMPLETED:
         case RELATIONS:
         case NEIGHBOR_OR_RIVAL_AT_WAR:
         case RELATIONS_IMPROVING:
         case RELATIONS_DAMAGING:
         case ALLIANCE_EXPIRED:
            if (this.id >= 0) {
               InGame_Civ.iRebuildToCivID = this.id;
               Game.menuManager.rebuildInGame_Civ();
               InGame_Civ.lTime = 0L;
               if (Game.menuManager.getVisibleInGame_CivBonuses() && InGame_Civ.iActiveCivID != 0 && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
                  InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
                  Game.menuManager.rebuildInGame_CivBonuses();
                  Game.menuManager.setVisibleInGame_CivBonuses(true);
                  InGame_CivBonuses.lTime = 0L;
               }
            }
      }
   }

   public MenuElement_Hover buildMenuElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (this.notificationType == Notification.Notification_Type.RELATIONS_IMPROVING) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusFlag(
               Game.getCiv(this.id).getCivName(), "", this.id, CFG.FONT_BOLD_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD, Colors.HOVER_LEFT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ImprovingRelations"),
               "",
               Images.relationsUp,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_POSITIVE,
               Colors.HOVER_LEFT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else if (this.notificationType == Notification.Notification_Type.RELATIONS_DAMAGING) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusFlag(
               Game.getCiv(this.id).getCivName(), "", this.id, CFG.FONT_BOLD_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD, Colors.HOVER_LEFT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("DamagingRelations"),
               "",
               Images.relationsDown,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_NEGATIVE,
               Colors.HOVER_LEFT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         if (this.notificationType == Notification.Notification_Type.HIGH_UNREST) {
            return InGame_ProvinceInfo.getHoverUnrest(this.id, false, false);
         }

         if (this.notificationType == Notification.Notification_Type.PRICE_CHANGE) {
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusResource(
                  this.sText, "", this.imageID, CFG.FONT_REGULAR_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT, Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonus(
                  Game.lang.get("Price") + ": ",
                  "" + CFG.getPrecision2(ResourcesManager.getPrice(this.imageID), 100),
                  Images.gold,
                  CFG.FONT_REGULAR_SMALL,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_GOLD
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else if (this.notificationType != Notification.Notification_Type.NO_LONGER_LARGEST_PRODUCER
            && this.notificationType != Notification.Notification_Type.LARGEST_PRODUCER) {
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonus(
                  this.sText, "", this.imageID, CFG.FONT_REGULAR_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT, Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else {
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonusResource(
                  this.sText, "", this.imageID, CFG.FONT_REGULAR_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT, Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      if (this.notificationType == Notification.Notification_Type.RELATIONS_COMPLETED) {
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonusFlag(
               Game.getCiv(this.id).getCivName(), "", this.id, CFG.FONT_REGULAR_SMALL, CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT, Colors.HOVER_LEFT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (this.notificationType == Notification.Notification_Type.ARMY_DESTROYED) {
         nData.add(
            new MenuElement_HoverElement_Type_Text_Desc(
               Game.lang.get("DestructionOrSurvival0", GameValues.battle.BATTLE_ARMY_DESTROYED_ROUND_ID), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game_Calendar.getDate_ByTurnID(this.iTurnID),
            "",
            Images.time,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT,
            Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      return new MenuElement_Hover(nElements);
   }

   public static enum Notification_BG {
      NEUTRAL_BG,
      RED,
      GREEN;
   }

   public static enum Notification_Type {
      DEFAULT,
      SIEGE,
      BATTLE_REPORT,
      LEADER_DIED,
      ADVISOR_DIED,
      GENERAL_DIED,
      REVOLT,
      DISEASE,
      ARMY_DESTROYED,
      RELATIONS_COMPLETED,
      RELATIONS,
      RELATIONS_IMPROVING,
      RELATIONS_DAMAGING,
      ALLIANCE_EXPIRED,
      NEIGHBOR_OR_RIVAL_AT_WAR,
      PRICE_CHANGE,
      PRICE_CHANGE_GROUP,
      HIGH_UNREST,
      REINFORCE_ARMY_COST,
      LARGEST_PRODUCER,
      NO_LONGER_LARGEST_PRODUCER,
      SETTLEMENT_ESTABLISHED;
   }
}
