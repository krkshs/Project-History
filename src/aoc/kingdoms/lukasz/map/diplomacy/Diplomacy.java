package aoc.kingdoms.lukasz.map.diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_Rivals;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageAllianceExpired;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageDamagingRelations;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageDefensivePactExpired;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGuaranteeExpired_OurInd;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGuaranteeExpired_We;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageImprovingRelations;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageMilitaryAccessExpired;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageNonAggressionPactExpired;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageRivalryExpired;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageRivals;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageTruceExpired;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.map.RivalsManager;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Diplomacy {
   public ConcurrentHashMap<Integer, Float> relation = new ConcurrentHashMap<>();
   public List<String> lWars = new ArrayList<>();
   public int iWarsSize = 0;
   public List<Integer> atWar = new ArrayList<>();
   public int iAtWarSize = 0;
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> truce = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> rivals = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> alliance = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nonAggressionPact = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> defensivePact = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> guarantee = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> guaranteeByCivID = new ConcurrentHashMap<>();
   public ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> militaryAccess = new ConcurrentHashMap<>();
   public List<Vassal> lVassals = new ArrayList<>();
   public int iVassalsSize = 0;
   public List<Diplomacy_RelationsAction> improvingRelations = new ArrayList<>();
   public int iImprovingRelationsSize = 0;
   public List<Diplomacy_RelationsAction> damagingRelations = new ArrayList<>();
   public int iDamagingRelationsSize = 0;

   public int getVassal_TributeLevel(int iVassalCivID) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               return this.lVassals.get(i).tL;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return 0;
   }

   public void setVassal_TributeLevel(int iVassalCivID, int iLevel) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               this.lVassals.get(i).setTribute(iLevel);
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public int getVassal_ManpowerLevel(int iVassalCivID) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               return this.lVassals.get(i).mL;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return 0;
   }

   public void setVassal_ManpowerLevel(int iVassalCivID, int iLevel) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               this.lVassals.get(i).setManpower(iLevel);
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public void setVassal_CanDeclareWar(int iVassalCivID, boolean canDeclareWar) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               this.lVassals.get(i).cW = canDeclareWar;
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public boolean getVassal_CanDeclareWar(int iVassalCivID) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               return this.lVassals.get(i).cW;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return true;
   }

   public float getVassal_LibertyDesire(int iVassalCivID) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               return this.lVassals.get(i).lD;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return 0.0F;
   }

   public void setLibertyDesire_Change(int iVassalCivID, float value) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               this.lVassals.get(i).setLibertyDesire_Change(value);
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public void setVassal_LoadData(int iVassalCivID, int tL, int mL, boolean cW, float lD) {
      try {
         for (int i = this.iVassalsSize - 1; i >= 0; i--) {
            if (this.lVassals.get(i).c == iVassalCivID) {
               this.lVassals.get(i).tL = tL;
               this.lVassals.get(i).mL = mL;
               this.lVassals.get(i).cW = cW;
               this.lVassals.get(i).lD = lD;
               return;
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public final void updateRelationsAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Float> nRelation = new ConcurrentHashMap<>();

         for (Entry<Integer, Float> entry : this.relation.entrySet()) {
            if (entry.getKey() > iCivID) {
               nRelation.put(entry.getKey() - 1, entry.getValue());
            } else if (entry.getKey() != iCivID) {
               nRelation.put(entry.getKey(), entry.getValue());
            }
         }

         this.relation = nRelation;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void updateRelations_ToNeutral(int civID) {
      try {
         ConcurrentHashMap<Integer, Float> nRelation = new ConcurrentHashMap<>();

         for (Entry<Integer, Float> entry : this.relation.entrySet()) {
            if (entry.getValue() > 0.0F) {
               if (entry.getValue() - GameValues.diplomacy.RELATIONS_CHANGE_PER_UPDATE_TO_NEUTRAL > 0.0F) {
                  nRelation.put(entry.getKey(), Math.max(0.0F, entry.getValue() - GameValues.diplomacy.RELATIONS_CHANGE_PER_UPDATE_TO_NEUTRAL));
               }
            } else if (entry.getValue() < 0.0F) {
               if (DiplomacyManager.isAtWar(civID, entry.getKey())) {
                  nRelation.put(entry.getKey(), entry.getValue());
               } else if (entry.getValue() + GameValues.diplomacy.RELATIONS_CHANGE_PER_UPDATE_TO_NEUTRAL < 0.0F) {
                  nRelation.put(entry.getKey(), Math.min(0.0F, entry.getValue() + GameValues.diplomacy.RELATIONS_CHANGE_PER_UPDATE_TO_NEUTRAL));
               }
            }
         }

         this.relation = nRelation;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void updateRelation(int theCivID, int withCivID, float nRelation) {
      try {
         if (!DiplomacyManager.isAtWar(theCivID, withCivID)) {
            this.relation
               .put(
                  withCivID,
                  Math.min(
                     GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX,
                     Math.max(GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN, this.getRelation(withCivID) + nRelation)
                  )
               );
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void setRelation(int theCivID, int withCivID, float nRelation) {
      try {
         if (!DiplomacyManager.isAtWar(theCivID, withCivID)) {
            this.relation
               .put(withCivID, Math.min(GameValues.diplomacy.DIPLOMACY_RELATIONS_MAX, Math.max(GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN, nRelation)));
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void setRelation_Load(int iCivID, float nRelation) {
      try {
         this.relation.put(iCivID, nRelation);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void setRelation_War(int iCivID, float nRelation) {
      try {
         this.relation.put(iCivID, nRelation);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      try {
         this.addAtWar(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final float getRelation(int iCivID) {
      try {
         if (this.relation.containsKey(iCivID)) {
            return this.relation.get(iCivID);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return 0.0F;
   }

   public final void setRelation_Peace(int iCivID, float nRelation, String warKey) {
      try {
         this.relation.put(iCivID, nRelation);
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      try {
         this.removeAtWar(iCivID);
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      try {
         this.removeWar(warKey, iCivID);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      if (!this.isAtWar() && iCivID != Game.player.iCivID) {
         Game.getCiv(iCivID).updateMilitaryLevel(1);
      }
   }

   public final void updateImproveRelations(int iFromCivID) {
      for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
         try {
            if (this.improvingRelations.get(i).iTurnID <= Game_Calendar.TURN_ID) {
               if (!DiplomacyManager.isAtWar(iFromCivID, this.improvingRelations.get(i).iCivID)
                  && Game.getCiv(this.improvingRelations.get(i).iCivID).getNumOfProvinces() > 0) {
                  float fRelationsUpdate = DiplomacyManager.getRelationImprove(iFromCivID, this.improvingRelations.get(i).iCivID);
                  this.updateRelation(iFromCivID, this.improvingRelations.get(i).iCivID, fRelationsUpdate);
                  Game.getCiv(this.improvingRelations.get(i).iCivID)
                     .diplomacy
                     .updateRelation(this.improvingRelations.get(i).iCivID, iFromCivID, fRelationsUpdate);
                  this.improvingRelations.get(i).iTurnID = Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_TIME;
                  if (Game.getCiv(this.improvingRelations.get(i).iCivID).diplomacy.getRelation(iFromCivID)
                     > GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_MAX) {
                     if (iFromCivID == Game.player.iCivID) {
                        Game.player
                           .addNotification(
                              new Notification(
                                 Notification.Notification_Type.RELATIONS_COMPLETED,
                                 Game.lang.get("ImprovingRelationsCompleted"),
                                 Images.relationsUp,
                                 Game_Calendar.TURN_ID,
                                 Notification.Notification_BG.GREEN,
                                 this.improvingRelations.get(i).iCivID
                              )
                           );
                     }

                     this.improvingRelations.remove(i);
                     this.iImprovingRelationsSize = this.improvingRelations.size();
                  }
               } else {
                  this.removeImproveRelations(this.improvingRelations.get(i).iCivID);
               }
            }
         } catch (Exception var4) {
         }
      }
   }

   public final void addImproveRelations_Load(int iCivID, int turnID) {
      this.improvingRelations.add(new Diplomacy_RelationsAction(iCivID, turnID));
   }

   public final boolean addImproveRelations(int iFromCivID, int iCivID) {
      if (DiplomacyManager.isAtWar(iFromCivID, iCivID)) {
         return false;
      } else if (!Game.getCiv(iFromCivID).diplomacy.isRival(iCivID) && !Game.getCiv(iCivID).diplomacy.isRival(iFromCivID)) {
         if (Game.getCiv(iCivID).diplomacy.getRelation(iFromCivID) >= GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_MAX) {
            return false;
         } else {
            for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
               if (this.improvingRelations.get(i).iCivID == iCivID) {
                  return false;
               }
            }

            this.improvingRelations.add(new Diplomacy_RelationsAction(iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_TIME));
            this.iImprovingRelationsSize = this.improvingRelations.size();
            this.removeDamageRelations(iCivID);
            if (iCivID == Game.player.iCivID) {
               if (GameValues.notifications.RELATIONS_FROM_AS_NOTIFICATION) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.RELATIONS_IMPROVING,
                           Game.getCiv(iFromCivID).getCivName() + ": " + Game.lang.get("ImprovingRelations"),
                           Images.relationsUp,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.GREEN,
                           iFromCivID
                        )
                     );
               } else {
                  Game.player
                     .addMessage(new PMessageImprovingRelations(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_RELATIONS_DAYS));
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public final void removeImproveRelations(int iCivID) {
      try {
         for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
            if (this.improvingRelations.get(i).iCivID == iCivID) {
               this.improvingRelations.remove(i);
               this.iImprovingRelationsSize = this.improvingRelations.size();
               return;
            }
         }
      } catch (Exception var3) {
      }
   }

   public final void clearImproveRelations() {
      this.improvingRelations.clear();
      this.iImprovingRelationsSize = 0;
   }

   public final void clearImproveRelations_AI(int civID) {
      try {
         for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
            if (!Game.getCiv(civID).aiCivDiplomacy.isPreparingForAllianceWithCivID(this.improvingRelations.get(i).iCivID)) {
               this.improvingRelations.remove(i);
            }
         }
      } catch (Exception var3) {
      }

      this.iImprovingRelationsSize = this.improvingRelations.size();
   }

   public final boolean isImprovingRelations(int iCivID) {
      try {
         for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
            if (this.improvingRelations.get(i).iCivID == iCivID) {
               return true;
            }
         }
      } catch (Exception var3) {
      }

      return false;
   }

   public final float getImprovingRelations_Perc(int iCivID) {
      for (int i = this.iImprovingRelationsSize - 1; i >= 0; i--) {
         if (this.improvingRelations.get(i).iCivID == iCivID) {
            if (this.improvingRelations.get(i).iTurnID - Game_Calendar.TURN_ID == GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_TIME) {
               return 99.9F;
            }

            return (float)(this.improvingRelations.get(i).iTurnID - Game_Calendar.TURN_ID) / GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_TIME * 100.0F;
         }
      }

      return -1.0F;
   }

   public final void updateDamageRelations(int iFromCivID) {
      for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
         try {
            if (this.damagingRelations.get(i).iTurnID <= Game_Calendar.TURN_ID) {
               if (!DiplomacyManager.isAtWar(iFromCivID, this.damagingRelations.get(i).iCivID)
                  && Game.getCiv(this.damagingRelations.get(i).iCivID).getNumOfProvinces() > 0) {
                  float fRelationsUpdate = DiplomacyManager.getRelationDamage(iFromCivID, this.damagingRelations.get(i).iCivID);
                  this.updateRelation(iFromCivID, this.damagingRelations.get(i).iCivID, fRelationsUpdate);
                  Game.getCiv(this.damagingRelations.get(i).iCivID)
                     .diplomacy
                     .updateRelation(this.damagingRelations.get(i).iCivID, iFromCivID, fRelationsUpdate);
                  this.damagingRelations.get(i).iTurnID = Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_TIME;
                  if (Game.getCiv(this.damagingRelations.get(i).iCivID).diplomacy.getRelation(iFromCivID) < GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX
                     )
                   {
                     if (iFromCivID == Game.player.iCivID) {
                        Game.player
                           .addNotification(
                              new Notification(
                                 Notification.Notification_Type.RELATIONS_COMPLETED,
                                 Game.lang.get("DamagingRelationsCompleted"),
                                 Images.relationsDown,
                                 Game_Calendar.TURN_ID,
                                 Notification.Notification_BG.GREEN,
                                 this.damagingRelations.get(i).iCivID
                              )
                           );
                     }

                     this.damagingRelations.remove(i);
                     this.iDamagingRelationsSize = this.damagingRelations.size();
                  }
               } else {
                  this.removeDamageRelations(this.damagingRelations.get(i).iCivID);
               }
            }
         } catch (Exception var4) {
         }
      }
   }

   public final void addDamageRelations_Load(int iCivID, int turnID) {
      this.damagingRelations.add(new Diplomacy_RelationsAction(iCivID, turnID));
   }

   public final boolean addDamageRelations(int iFromCivID, int iCivID) {
      if (DiplomacyManager.isAtWar(iFromCivID, iCivID)) {
         return false;
      } else if (Game.getCiv(iCivID).diplomacy.getRelation(iFromCivID) <= GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_MAX) {
         return false;
      } else {
         for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
            if (this.damagingRelations.get(i).iCivID == iCivID) {
               return false;
            }
         }

         this.damagingRelations.add(new Diplomacy_RelationsAction(iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_TIME));
         this.iDamagingRelationsSize = this.damagingRelations.size();
         this.removeImproveRelations(iCivID);
         if (iCivID == Game.player.iCivID) {
            if (GameValues.notifications.RELATIONS_FROM_AS_NOTIFICATION) {
               Game.player
                  .addNotification(
                     new Notification(
                        Notification.Notification_Type.RELATIONS_DAMAGING,
                        Game.getCiv(iFromCivID).getCivName() + ": " + Game.lang.get("DamagingRelations"),
                        Images.relationsDown,
                        Game_Calendar.TURN_ID,
                        Notification.Notification_BG.RED,
                        iFromCivID
                     )
                  );
            } else {
               Game.player.addMessage(new PMessageDamagingRelations(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_RELATIONS_DAYS));
            }
         }

         return true;
      }
   }

   public final void removeDamageRelations(int iCivID) {
      for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
         if (this.damagingRelations.get(i).iCivID == iCivID) {
            this.damagingRelations.remove(i);
            this.iDamagingRelationsSize = this.damagingRelations.size();
            return;
         }
      }
   }

   public final void clearDamageRelations() {
      this.damagingRelations.clear();
      this.iDamagingRelationsSize = 0;
   }

   public final void clearDamageRelations_AI(int civID) {
      try {
         for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
            if (!Game.getCiv(civID).aiCivDiplomacy.isPreparingForWarWithCivID(this.damagingRelations.get(i).iCivID)) {
               this.damagingRelations.remove(i);
            }
         }
      } catch (Exception var3) {
      }

      this.iDamagingRelationsSize = this.damagingRelations.size();
   }

   public final boolean isDamagingRelations(int iCivID) {
      try {
         for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
            if (this.damagingRelations.get(i).iCivID == iCivID) {
               return true;
            }
         }
      } catch (Exception var3) {
      }

      return false;
   }

   public final float getDamagingRelations_Perc(int iCivID) {
      for (int i = this.iDamagingRelationsSize - 1; i >= 0; i--) {
         if (this.damagingRelations.get(i).iCivID == iCivID) {
            if (this.damagingRelations.get(i).iTurnID - Game_Calendar.TURN_ID == GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_TIME) {
               return 99.9F;
            }

            return (float)(this.damagingRelations.get(i).iTurnID - Game_Calendar.TURN_ID) / GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_TIME * 100.0F;
         }
      }

      return -1.0F;
   }

   public final void addAtWar(int iCivID) {
      for (int i = this.iAtWarSize - 1; i >= 0; i--) {
         if (this.atWar.get(i) == iCivID) {
            return;
         }
      }

      this.atWar.add(iCivID);
      this.iAtWarSize = this.atWar.size();
   }

   public final void removeAtWar(int iCivID) {
      for (int i = this.iAtWarSize - 1; i >= 0; i--) {
         if (this.atWar.get(i) == iCivID) {
            this.atWar.remove(i);
            this.iAtWarSize = this.atWar.size();
            return;
         }
      }
   }

   public final void addWar(String key, int civID) {
      if (civID != Game.player.iCivID) {
         Game.getCiv(civID).updateMilitaryLevel(Math.max(GameValues.war.AI_AT_WAR_MIN_MILITARY_LEVEL, Game.getCiv(civID).getMilitaryLevel()));
         if (GameValues.notifications.NOTIFICATION_NEIGHBOR_OR_RIVAL_IS_AT_WAR
            && (Game.getCiv(Game.player.iCivID).civNeighbors.isNeighbor(civID) || Game.getCiv(Game.player.iCivID).diplomacy.isRival(civID))) {
            Game.player
               .addNotification(
                  new Notification(
                     Notification.Notification_Type.NEIGHBOR_OR_RIVAL_AT_WAR,
                     Game.getCiv(civID).getCivName() + ": " + Game.lang.get("IaAtWar"),
                     Images.war,
                     Game_Calendar.TURN_ID,
                     Notification.Notification_BG.NEUTRAL_BG,
                     civID
                  )
               );
         }
      }

      for (int i = 0; i < this.iWarsSize; i++) {
         if (this.lWars.get(i).equals(key)) {
            return;
         }
      }

      this.lWars.add(key);
      this.iWarsSize = this.lWars.size();
   }

   public final void removeWar(String key, int civID) {
      for (int i = 0; i < this.iWarsSize; i++) {
         if (this.lWars.get(i).equals(key)) {
            this.lWars.remove(i);
            this.iWarsSize = this.lWars.size();
            if (this.iWarsSize == 0) {
               Game.getCiv(civID).setWarPlayDefensiveUntilTurnID(0);
            }

            return;
         }
      }
   }

   public final String getWarKey(int iCivA, int iCivB) {
      try {
         for (int i = 0; i < this.iWarsSize; i++) {
            if (WarManager.lWars.get(this.lWars.get(i)).isAggressor(iCivA) && WarManager.lWars.get(this.lWars.get(i)).isDefender(iCivB)) {
               return this.lWars.get(i);
            }

            if (WarManager.lWars.get(this.lWars.get(i)).isAggressor(iCivB) && WarManager.lWars.get(this.lWars.get(i)).isDefender(iCivA)) {
               return this.lWars.get(i);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return null;
   }

   public boolean isAtWar() {
      return this.iAtWarSize > 0;
   }

   public final void updateTruceAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.truce.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.truce = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addTruce(int iCivID, int iTurnID) {
      if (!this.truce.containsKey(iCivID)) {
         this.truce.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public boolean haveTruce(int iCivID) {
      return this.truce.containsKey(iCivID);
   }

   public final void updateTruces(int civID) {
      try {
         if (this.truce.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.truce.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID) {
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageTruceExpired(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  } else if (civID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageTruceExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeTruce(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void removeTruce(int iCivID) {
      this.truce.remove(iCivID);
   }

   public final void updateDefensivePactAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.defensivePact.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.defensivePact = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addDefensivePact(int iCivID, int iTurnID) {
      if (!this.defensivePact.containsKey(iCivID)) {
         this.defensivePact.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeDefensivePact(int iCivID) {
      this.defensivePact.remove(iCivID);
   }

   public boolean haveDefensivePact(int iCivID) {
      return this.defensivePact.containsKey(iCivID);
   }

   public final void updateDefensivePact(int civID) {
      try {
         if (this.defensivePact.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.defensivePact.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID) {
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageDefensivePactExpired(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  } else if (civID == Game.player.iCivID) {
                     Game.player
                        .addMessage(new PMessageDefensivePactExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeDefensivePact(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateNonAggressionPactAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.nonAggressionPact.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.nonAggressionPact = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addNonAggressionPact(int iCivID, int iTurnID) {
      if (!this.nonAggressionPact.containsKey(iCivID)) {
         this.nonAggressionPact.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeNonAggressionPact(int iCivID) {
      this.nonAggressionPact.remove(iCivID);
   }

   public boolean haveNonAggressionPact(int iCivID) {
      return this.nonAggressionPact.containsKey(iCivID);
   }

   public final void updateNonAggressionPact(int civID) {
      try {
         if (this.nonAggressionPact.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.nonAggressionPact.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID) {
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageNonAggressionPactExpired(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  } else if (civID == Game.player.iCivID) {
                     Game.player
                        .addMessage(new PMessageNonAggressionPactExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeNonAggressionPact(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateGuaranteeAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.guarantee.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.guarantee = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addGuarantee(int iCivID, int iTurnID) {
      if (!this.guarantee.containsKey(iCivID)) {
         this.guarantee.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeGuarantee(int iCivID) {
      this.guarantee.remove(iCivID);
   }

   public boolean haveGuarantee(int iCivID) {
      return this.guarantee.containsKey(iCivID);
   }

   public final void updateGuarantee(int civID) {
      try {
         if (this.guarantee.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.guarantee.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID) {
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageGuaranteeExpired_OurInd(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  } else if (civID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageGuaranteeExpired_We(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeGuaranteeByCivID(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateGuaranteeByCivIDAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.guaranteeByCivID.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.guaranteeByCivID = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addGuaranteeByCivID(int iCivID, int iTurnID) {
      if (!this.guaranteeByCivID.containsKey(iCivID)) {
         this.guaranteeByCivID.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeGuaranteeByCivID(int iCivID) {
      this.guaranteeByCivID.remove(iCivID);
   }

   public boolean haveGuaranteeByCivID(int iCivID) {
      return this.guaranteeByCivID.containsKey(iCivID);
   }

   public final void updateGuaranteeByCivID() {
   }

   public final void updateAllianceAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.alliance.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.alliance = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addAlliance(int iCivID, int iTurnID) {
      if (!this.alliance.containsKey(iCivID)) {
         this.alliance.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeAlliance(int iCivID) {
      this.alliance.remove(iCivID);
   }

   public boolean haveAlliance(int iCivID) {
      return this.alliance.containsKey(iCivID);
   }

   public final void updateAlliance(int civID) {
      try {
         if (this.alliance.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.alliance.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID || Game.getCiv(tData.iCivID).getNumOfProvinces() <= 0 || Game.getCiv(civID).getNumOfProvinces() <= 0) {
                  int updateFog = -1;
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageAllianceExpired(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                     Game.player
                        .addNotification(
                           new Notification(
                              Notification.Notification_Type.ALLIANCE_EXPIRED,
                              Game.getCiv(civID).getCivName() + ": " + Game.lang.get("AllianceExpired"),
                              Images.alliance,
                              Game_Calendar.TURN_ID,
                              Notification.Notification_BG.RED,
                              civID
                           )
                        );
                     updateFog = civID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get("AllianceExpired"), Game.getCiv(civID).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName()
                        );
                     InGame_Info.imgID = Images.infoDiplomacy;
                  } else if (civID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageAllianceExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                     Game.player
                        .addNotification(
                           new Notification(
                              Notification.Notification_Type.ALLIANCE_EXPIRED,
                              Game.getCiv(tData.iCivID).getCivName() + ": " + Game.lang.get("AllianceExpired"),
                              Images.alliance,
                              Game_Calendar.TURN_ID,
                              Notification.Notification_BG.RED,
                              tData.iCivID
                           )
                        );
                     updateFog = tData.iCivID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get("AllianceExpired"), Game.getCiv(tData.iCivID).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName()
                        );
                     InGame_Info.imgID = Images.infoDiplomacy;
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeAlliance(civID);
                  it.remove();
                  if (updateFog > 0) {
                     Game.getCiv(updateFog).updateArmyImgID();
                     Game.player.fog.updateFogOfWar_Civ(updateFog);
                  }
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void updateAlliance_ConqueredProvinces(int civID) {
      try {
         if (this.alliance.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.alliance.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (Game.getCiv(tData.iCivID).getNumOfProvinces() <= 0 || Game.getCiv(civID).getNumOfProvinces() <= 0) {
                  int updateFog = -1;
                  if (tData.iCivID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageAllianceExpired(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                     Game.player
                        .addNotification(
                           new Notification(
                              Notification.Notification_Type.ALLIANCE_EXPIRED,
                              Game.getCiv(civID).getCivName() + ": " + Game.lang.get("AllianceExpired"),
                              Images.alliance,
                              Game_Calendar.TURN_ID,
                              Notification.Notification_BG.RED,
                              civID
                           )
                        );
                     updateFog = civID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get("AllianceExpired"), Game.getCiv(civID).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName()
                        );
                     InGame_Info.imgID = Images.infoDiplomacy;
                  } else if (civID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageAllianceExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                     Game.player
                        .addNotification(
                           new Notification(
                              Notification.Notification_Type.ALLIANCE_EXPIRED,
                              Game.getCiv(tData.iCivID).getCivName() + ": " + Game.lang.get("AllianceExpired"),
                              Images.alliance,
                              Game_Calendar.TURN_ID,
                              Notification.Notification_BG.RED,
                              tData.iCivID
                           )
                        );
                     updateFog = tData.iCivID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get("AllianceExpired"), Game.getCiv(tData.iCivID).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName()
                        );
                     InGame_Info.imgID = Images.infoDiplomacy;
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeAlliance(civID);
                  it.remove();
                  if (updateFog > 0) {
                     Game.getCiv(updateFog).updateArmyImgID();
                     Game.player.fog.updateFogOfWar_Civ(updateFog);
                  }
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void updateMilitaryAccessAfterRemoveCiv(int iCivID) {
      try {
         ConcurrentHashMap<Integer, Diplomacy.DiplomacyData> nList = new ConcurrentHashMap<>();

         for (Entry<Integer, Diplomacy.DiplomacyData> entry : this.militaryAccess.entrySet()) {
            if (entry.getKey() > iCivID) {
               nList.put(entry.getKey() - 1, new Diplomacy.DiplomacyData(entry.getValue().iCivID - 1, entry.getValue().iTurnID));
            } else if (entry.getKey() != iCivID) {
               nList.put(entry.getKey(), entry.getValue());
            }
         }

         this.militaryAccess = nList;
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void addMilitaryAccess(int iCivID, int iTurnID) {
      if (!this.militaryAccess.containsKey(iCivID)) {
         this.militaryAccess.put(iCivID, new Diplomacy.DiplomacyData(iCivID, iTurnID));
      }
   }

   public final void removeMilitaryAccess(int iCivID) {
      this.militaryAccess.remove(iCivID);
   }

   public boolean haveMilitaryAccess(int iCivID) {
      return this.militaryAccess.containsKey(iCivID);
   }

   public final void updateMilitaryAccess(int civID) {
      try {
         if (this.militaryAccess.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.militaryAccess.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iTurnID < Game_Calendar.TURN_ID) {
                  if (civID == Game.player.iCivID) {
                     Game.player
                        .addMessage(new PMessageMilitaryAccessExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  }

                  Game.getCiv(tData.iCivID).diplomacy.removeMilitaryAccess(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void addRival_load(int rivalCivID, int endRivalryTurnID) {
      this.rivals.put(rivalCivID, new Diplomacy.DiplomacyData(rivalCivID, endRivalryTurnID));
   }

   public final boolean addRival(int byCivID, int rivalCivID) {
      return this.addRival(
         byCivID,
         rivalCivID,
         Game_Calendar.TURN_ID + 365 * GameValues.rivals.END_OF_RIVALRY_AFTER_YEARS + Game.oR.nextInt(GameValues.rivals.END_OF_RIVALRY_AFTER_EXTRA_DAYS_RANDOM)
      );
   }

   public final boolean addRival(int byCivID, int rivalCivID, int endRivalryTurnID) {
      if (!this.rivals.containsKey(rivalCivID)) {
         if (this.rivals.size() >= GameValues.rivals.RIVALS_LIMIT) {
            return false;
         } else {
            this.rivals.put(rivalCivID, new Diplomacy.DiplomacyData(rivalCivID, endRivalryTurnID));
            if (!DiplomacyManager.isAtWar(byCivID, rivalCivID)) {
               this.updateRelation(byCivID, rivalCivID, GameValues.rivals.RIVALS_OPINION_CHANGE);
               Game.getCiv(rivalCivID).diplomacy.updateRelation(rivalCivID, byCivID, GameValues.rivals.RIVALS_OPINION_CHANGE);
            }

            Game.getCiv(rivalCivID).diplomacy.removeImproveRelations(byCivID);
            Game.getCiv(byCivID).diplomacy.removeImproveRelations(rivalCivID);
            Game.gameThread.addCivUpdateLegacyPerMonth(byCivID);
            Game.gameThreadTurns.addCivUpdateMaxManpower(byCivID);
            if (rivalCivID == Game.player.iCivID) {
               Game.player.addMessage(new PMessageRivals(byCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public final void removeRival(int iCivID) {
      try {
         if (this.rivals.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.rivals.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (tData.iCivID == iCivID) {
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
      }
   }

   public boolean isRival(int iCivID) {
      return this.rivals.containsKey(iCivID);
   }

   public final void updateRivals(int civID) {
      try {
         if (this.rivals.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.rivals.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (Game.getCiv(tData.iCivID).getNumOfProvinces() <= 0 || Game_Calendar.TURN_ID >= tData.iTurnID) {
                  if (civID == Game.player.iCivID) {
                     Game.player.addMessage(new PMessageRivalryExpired(tData.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  } else {
                     Game.gameThread.addAI_SimpleTask(new Game.SimpleTask("chooseRivals" + civID, civID) {
                        @Override
                        public void update() {
                           AI_Rivals.chooseRivals(this.id);
                        }
                     });
                  }

                  Game.gameThread.addCivUpdateLegacyPerMonth(civID);
                  Game.gameThreadTurns.addCivUpdateMaxManpower(civID);
                  it.remove();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final boolean haveTheSameRival(int civID, int withCivID) {
      try {
         if (this.rivals.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.rivals.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (Game.getCiv(withCivID).diplomacy.rivals.containsKey(tData.iCivID)) {
                  return true;
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return false;
   }

   public final int getRivalsManpower(int civID) {
      int out = 0;

      try {
         if (this.rivals.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.rivals.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (Game.getCiv(tData.iCivID).getNumOfProvinces() <= 0) {
                  it.remove();
               } else {
                  out += RivalsManager.getManpower(civID, tData.iCivID);
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return out;
   }

   public final float getRivalsLegacy(int civID) {
      float out = 0.0F;

      try {
         if (this.rivals.size() > 0) {
            Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = this.rivals.entrySet().iterator();

            while (it.hasNext()) {
               Diplomacy.DiplomacyData tData = it.next().getValue();
               if (Game.getCiv(tData.iCivID).getNumOfProvinces() <= 0) {
                  it.remove();
               } else {
                  out += RivalsManager.getLegacy(civID, tData.iCivID);
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return out;
   }

   public class DiplomacyData {
      public int iCivID;
      public int iTurnID;

      public DiplomacyData(int iCivID, int iTurnID) {
         this.iCivID = iCivID;
         this.iTurnID = iTurnID;
      }
   }
}
