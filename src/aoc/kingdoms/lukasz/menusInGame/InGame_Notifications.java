package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button32Padd;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification;
import aoc.kingdoms.lukasz.menu_element.button.Button_NotificationResource;
import aoc.kingdoms.lukasz.menu_element.button.Button_NotificationResource_Green;
import aoc.kingdoms.lukasz.menu_element.button.Button_NotificationResource_Red;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification_Flag;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification_Flag_Green;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification_Flag_Red;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification_Green;
import aoc.kingdoms.lukasz.menu_element.button.Button_Notification_Red;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Notifications extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static boolean stateVisible = false;

   public InGame_Notifications() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = 1;
      int paddingRight = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = Game.mapBG.iMinimapWidth + Images.boxTitleBORDERWIDTH;
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2;
      int buttonYPadding = CFG.PADDING;
      int buttonY = 0;
      int buttonH = Math.max(CFG.BUTTON_HEIGHT / 2, Math.max(ImageManager.getImage(Images.gold).getHeight(), CFG.TEXT_HEIGHT) + CFG.PADDING);
      if (Game.player.iNotificationsSize < GameValues.notifications.NUMBER_OF_NOTIFICATIONS) {
         buttonY += (buttonH + CFG.PADDING) * (GameValues.notifications.NUMBER_OF_NOTIFICATIONS - Game.player.iNotificationsSize);
      }

      try {
         for (int i = 0; i < Game.player.iNotificationsSize; i++) {
            if (Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.PRICE_CHANGE
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.LARGEST_PRODUCER
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.NO_LONGER_LARGEST_PRODUCER) {
               try {
                  switch (Game.player.lNotifications.get(i).notificationBG) {
                     case RED:
                        menuElements.add(
                           new Button_NotificationResource_Red(
                              Game.player.lNotifications.get(i).sText,
                              Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                              paddingLeft,
                              buttonY,
                              menuWidth - paddingRight - paddingLeft,
                              buttonH,
                              Game.player.lNotifications.get(i).imageID,
                              i,
                              Game.player.lNotifications.get(i).lTime
                           ) {
                              @Override
                              public void actionElement() {
                                 super.actionElement();
                                 MenuManager.addClickAnimation(
                                    new ClickAnimation(
                                       this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                       this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                       this.getWidth(),
                                       this.getHeight()
                                    ) {
                                       @Override
                                       public Color getColor() {
                                          return Colors.COLOR_NOTIFICATION_OVER_RED;
                                       }
                                    }
                                 );
                              }
                           }
                        );
                        buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                        break;
                     case GREEN:
                        menuElements.add(
                           new Button_NotificationResource_Green(
                              Game.player.lNotifications.get(i).sText,
                              Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                              paddingLeft,
                              buttonY,
                              menuWidth - paddingRight - paddingLeft,
                              buttonH,
                              Game.player.lNotifications.get(i).imageID,
                              i,
                              Game.player.lNotifications.get(i).lTime
                           ) {
                              @Override
                              public void actionElement() {
                                 super.actionElement();
                                 MenuManager.addClickAnimation(
                                    new ClickAnimation(
                                       this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                       this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                       this.getWidth(),
                                       this.getHeight()
                                    ) {
                                       @Override
                                       public Color getColor() {
                                          return Colors.COLOR_NOTIFICATION_OVER_GREEN;
                                       }
                                    }
                                 );
                              }
                           }
                        );
                        buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                        break;
                     default:
                        menuElements.add(
                           new Button_NotificationResource(
                              Game.player.lNotifications.get(i).sText,
                              Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                              paddingLeft,
                              buttonY,
                              menuWidth - paddingRight - paddingLeft,
                              buttonH,
                              Game.player.lNotifications.get(i).imageID,
                              i,
                              Game.player.lNotifications.get(i).lTime
                           ) {
                              @Override
                              public void actionElement() {
                                 super.actionElement();
                                 MenuManager.addClickAnimation(
                                    new ClickAnimation(
                                       this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                       this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                       this.getWidth(),
                                       this.getHeight()
                                    ) {
                                       @Override
                                       public Color getColor() {
                                          return Colors.COLOR_NOTIFICATION_OVER;
                                       }
                                    }
                                 );
                              }
                           }
                        );
                        buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                  }
               } catch (Exception var14) {
                  CFG.exceptionStack(var14);
               }
            } else if (Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.RELATIONS_IMPROVING
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.RELATIONS_DAMAGING
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.NEIGHBOR_OR_RIVAL_AT_WAR
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.RELATIONS
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.RELATIONS_COMPLETED
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.ALLIANCE_EXPIRED
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.LEADER_DIED
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.ADVISOR_DIED
               || Game.player.lNotifications.get(i).notificationType == Notification.Notification_Type.GENERAL_DIED) {
               switch (Game.player.lNotifications.get(i).notificationBG) {
                  case RED:
                     menuElements.add(
                        new Button_Notification_Flag_Red(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.player.lNotifications.get(i).id
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_RED;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  case GREEN:
                     menuElements.add(
                        new Button_Notification_Flag_Green(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.player.lNotifications.get(i).id
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_GREEN;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  default:
                     menuElements.add(
                        new Button_Notification_Flag(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.player.lNotifications.get(i).id
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            } else if (Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.SIEGE
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.BATTLE_REPORT
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.HIGH_UNREST
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.SETTLEMENT_ESTABLISHED
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.ARMY_DESTROYED
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.DISEASE
               && Game.player.lNotifications.get(i).notificationType != Notification.Notification_Type.REVOLT) {
               switch (Game.player.lNotifications.get(i).notificationBG) {
                  case RED:
                     menuElements.add(
                        new Button_Notification_Red(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_RED;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  case GREEN:
                     menuElements.add(
                        new Button_Notification_Green(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_GREEN;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  default:
                     menuElements.add(
                        new Button_Notification(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            } else {
               switch (Game.player.lNotifications.get(i).notificationBG) {
                  case RED:
                     menuElements.add(
                        new Button_Notification_Flag_Red(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.getProvince(Game.player.lNotifications.get(i).id).getCivID()
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_RED;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  case GREEN:
                     menuElements.add(
                        new Button_Notification_Flag_Green(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.getProvince(Game.player.lNotifications.get(i).id).getCivID()
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER_GREEN;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                     break;
                  default:
                     menuElements.add(
                        new Button_Notification_Flag(
                           Game.player.lNotifications.get(i).sText,
                           Game_Calendar.getDate_ByTurnID_MessageShort(Game.player.lNotifications.get(i).iTurnID),
                           paddingLeft,
                           buttonY,
                           menuWidth - paddingRight - paddingLeft,
                           buttonH,
                           Game.player.lNotifications.get(i).imageID,
                           i,
                           Game.player.lNotifications.get(i).lTime,
                           Game.getProvince(Game.player.lNotifications.get(i).id).getCivID()
                        ) {
                           @Override
                           public void actionElement() {
                              super.actionElement();
                              MenuManager.addClickAnimation(
                                 new ClickAnimation(
                                    this.getPosX() + this.getWidth() / 2 + InGame_Notifications.this.getMenuPosX(),
                                    this.getPosY() + this.getHeight() / 2 + InGame_Notifications.this.getMenuPosY(),
                                    this.getWidth(),
                                    this.getHeight()
                                 ) {
                                    @Override
                                    public Color getColor() {
                                       return Colors.COLOR_NOTIFICATION_OVER;
                                    }
                                 }
                              );
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }
         }
      } catch (Exception var15) {
         CFG.exceptionStack(var15);
      }

      buttonY = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (buttonY < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING;
         }
      }

      ix = (buttonH + CFG.PADDING) * GameValues.notifications.NUMBER_OF_NOTIFICATIONS;
      this.initMenu(null, menuX, menuY, menuWidth, ix, menuElements, true, false);
      this.drawScrollPositionAlways2 = false;
      this.setMenuPosY(this.getPosY() - 990000);
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
   public int getMenuPosY() {
      return this.getScrollableY()
         ? super.getMenuPosY()
         : CFG.GAME_HEIGHT
            - Game.mapBG.minimapOfCivilizations.getHeight()
            - Minimap.getPadding() * 2
            - this.getHeight()
            + InGame.iMinimapPosY
            - Button32Padd.getButtonHeight()
            - CFG.PADDING * 4;
   }

   @Override
   public int getPosY() {
      return CFG.GAME_HEIGHT
         - Game.mapBG.minimapOfCivilizations.getHeight()
         - Minimap.getPadding() * 2
         - this.getHeight()
         + InGame.iMinimapPosY
         - Button32Padd.getButtonHeight()
         - CFG.PADDING * 4;
   }
}
