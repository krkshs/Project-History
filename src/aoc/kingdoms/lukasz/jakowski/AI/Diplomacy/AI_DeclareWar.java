package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.List;

public class AI_DeclareWar {
   public static final void declareWar() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_DECLARE_WAR;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DECLARE_WAR
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               declareWar(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DECLARE_WAR;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DECLARE_WAR) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               declareWar(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void declareWar(int civID) {
      if (Game.getCiv(civID).fGold < Game.aiValuesDiplomacy.AI_DECLARE_WAR_ONLY_IF_GOLD_OVER) {
         Game.getCiv(civID).aiCivDiplomacy.clearPrepareForWar();
      } else if (!Game.getCiv(civID).diplomacy.isAtWar() || Game.aiValuesDiplomacy.AI_CAN_DECLARE_WAR_IF_AT_WAR) {
         if (!Game.getCiv(civID).aiCivDiplomacy.w.isEmpty()) {
            for (int i = Game.getCiv(civID).aiCivDiplomacy.w.size() - 1; i >= 0; i--) {
               if (Game.getCiv(Game.getCiv(civID).aiCivDiplomacy.w.get(i).c).getNumOfProvinces() <= 0
                  || AI_PrepareForWar.isCivAlly(civID, Game.getCiv(civID).aiCivDiplomacy.w.get(i).c)) {
                  Game.getCiv(civID).aiCivDiplomacy.w.remove(i);
               }
            }
         }

         if (!(Game.getCiv(civID).civStability_LostFrom100 > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_CIV_STABILITY_IS_BELOW_100)) {
            if (!(Game.getCiv(civID).getAggressiveExpansion() > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_AE_IS_BELOW)) {
               if (!(Game.getCiv(civID).fManpower / Game.getCiv(civID).fManpowerMax < Game.aiValuesDiplomacy.AI_DECLARE_WAR_ONLY_IF_MANPOWER_PERC_OVER)) {
                  if (!Game.getCiv(civID).aiCivDiplomacy.w.isEmpty()) {
                     for (int ix = 0;
                        ix < Math.min(Game.aiValuesDiplomacy.AI_DECLARE_WARS_LIMIT_PER_UPDATE, Game.getCiv(civID).aiCivDiplomacy.w.size())
                           && !Game.getCiv(civID).aiCivDiplomacy.w.isEmpty();
                        ix++
                     ) {
                        if (Game.getCiv(Game.getCiv(civID).aiCivDiplomacy.w.get(ix).c).getNumOfProvinces() == 0) {
                           Game.getCiv(civID).aiCivDiplomacy.w.remove(ix);
                           AI_PrepareForWar.prepareForWar(civID, Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX / 2);
                        } else if (Game_Calendar.TURN_ID >= Game.getCiv(civID).aiCivDiplomacy.w.get(ix).t) {
                           if (Game.getCiv(civID).diplomacy.getRelation(Game.getCiv(civID).aiCivDiplomacy.w.get(ix).c)
                              <= GameValues.war.RELATIONS_TO_DECLARE_WAR) {
                              if (!declareWar(civID, Game.getCiv(civID).aiCivDiplomacy.w.get(ix).c)) {
                                 Game.getCiv(civID).aiCivDiplomacy.w.remove(ix--);
                                 AI_PrepareForWar.prepareForWar(civID, Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX / 2);
                              } else {
                                 Game.getCiv(civID).aiCivDiplomacy.w.remove(ix--);
                              }
                           } else {
                              Game.getCiv(civID).diplomacy.addDamageRelations(civID, Game.getCiv(civID).aiCivDiplomacy.w.get(ix).c);
                              if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_SEND_INSULT_CHANCE) {
                                 DiplomacyManager.sendInsult(civID, Game.getCiv(civID).aiCivDiplomacy.w.get(ix).c);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static final boolean checkRegiments_ForWar(int civID, int onCivID) {
      List<Integer> alliesCiv = DiplomacyManager.declareWar_AlliesAttacker(civID, onCivID);
      List<Integer> alliesDefenders = DiplomacyManager.declareWar_AlliesDefender(onCivID, civID);
      int regimentsA = 0;
      int regimentsB = 0;

      try {
         for (int i = alliesCiv.size() - 1; i >= 0; i--) {
            regimentsA += Game.getCiv(alliesCiv.get(i)).getArmyRegimentSize();
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      try {
         for (int i = alliesDefenders.size() - 1; i >= 0; i--) {
            regimentsB += Game.getCiv(alliesDefenders.get(i)).getArmyRegimentSize();
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      regimentsA = (int)(regimentsA * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER);
      regimentsB = (int)(regimentsB * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER_DEFENDERS);
      regimentsA += Game.getCiv(civID).getArmyRegimentSize();
      regimentsB += (int)(
         Math.max(
               Game.getCiv(onCivID).iRegimentsLimit * Game.aiValuesDiplomacy.AI_DECLARE_WAR_DEFENDER_REGIMENTS_LIMIT_MODIFIER,
               (float)Game.getCiv(onCivID).getArmyRegimentSize()
            )
            * Game.aiValuesDiplomacy.AI_DECLARE_WAR_DEFENDER_REGIMENTS_MODIFIER
      );
      alliesCiv.clear();
      alliesDefenders.clear();
      return regimentsA >= regimentsB;
   }

   public static final boolean declareWar(int civID, int onCivID) {
      List<Integer> alliesCiv = DiplomacyManager.declareWar_AlliesAttacker(civID, onCivID);
      List<Integer> alliesDefenders = DiplomacyManager.declareWar_AlliesDefender(onCivID, civID);
      int regimentsA = 0;
      int regimentsB = 0;

      try {
         for (int i = alliesCiv.size() - 1; i >= 0; i--) {
            if (alliesCiv.get(i) != civID && alliesCiv.get(i) != onCivID) {
               regimentsA += Game.getCiv(alliesCiv.get(i)).getArmyRegimentSize();
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      try {
         for (int ix = alliesDefenders.size() - 1; ix >= 0; ix--) {
            if (alliesDefenders.get(ix) != civID && alliesDefenders.get(ix) != onCivID) {
               regimentsB += Game.getCiv(alliesDefenders.get(ix)).getArmyRegimentSize();
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      regimentsA = (int)(regimentsA * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER);
      regimentsB = (int)(regimentsB * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER_DEFENDERS);
      regimentsA += Game.getCiv(civID).getArmyRegimentSize();
      regimentsB += (int)(Game.getCiv(onCivID).getArmyRegimentSize() * Game.aiValuesDiplomacy.AI_DECLARE_WAR_DEFENDER_REGIMENTS_MODIFIER);
      if (regimentsA >= regimentsB) {
         return DiplomacyManager.declareWar(civID, onCivID, false, alliesCiv);
      } else {
         alliesCiv.clear();
         alliesDefenders.clear();
         return false;
      }
   }

   public static final boolean declareWar_CheckRegiments(int civID, int onCivID) {
      try {
         List<Integer> alliesCiv = DiplomacyManager.declareWar_AlliesAttacker(civID, onCivID);
         List<Integer> alliesDefenders = DiplomacyManager.declareWar_AlliesDefender(onCivID, civID);
         int regimentsA = 0;
         int regimentsB = 0;

         try {
            for (int i = alliesCiv.size() - 1; i >= 0; i--) {
               regimentsA += Game.getCiv(alliesCiv.get(i)).getArmyRegimentSize();
            }
         } catch (Exception var8) {
            CFG.exceptionStack(var8);
         }

         try {
            for (int i = alliesDefenders.size() - 1; i >= 0; i--) {
               regimentsB += Game.getCiv(alliesDefenders.get(i)).getArmyRegimentSize();
            }
         } catch (Exception var7) {
            CFG.exceptionStack(var7);
         }

         regimentsA = (int)(regimentsA * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER);
         regimentsB = (int)(regimentsB * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER_DEFENDERS);
         regimentsA += Game.getCiv(civID).getArmyRegimentSize();
         regimentsB += (int)(Game.getCiv(onCivID).getArmyRegimentSize() * Game.aiValuesDiplomacy.AI_DECLARE_WAR_DEFENDER_REGIMENTS_MODIFIER);
         if (regimentsA >= regimentsB) {
            return DiplomacyManager.declareWar(civID, onCivID, false, alliesCiv);
         }

         alliesCiv.clear();
         alliesDefenders.clear();
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      return false;
   }

   public static final int declareWar_CheckRegiments_Attacker(int civID, int onCivID) {
      try {
         List<Integer> alliesCiv = DiplomacyManager.declareWar_AlliesAttacker(civID, onCivID);
         int regimentsA = 0;

         try {
            for (int i = alliesCiv.size() - 1; i >= 0; i--) {
               regimentsA += Game.getCiv(alliesCiv.get(i)).getArmyRegimentSize();
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }

         regimentsA = (int)(regimentsA * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER);
         return regimentsA + Game.getCiv(civID).getArmyRegimentSize();
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
         return 0;
      }
   }

   public static final int declareWar_CheckRegiments_Defender(int civID, int onCivID) {
      try {
         List<Integer> alliesDefenders = DiplomacyManager.declareWar_AlliesDefender(onCivID, civID);
         int regimentsB = 0;

         try {
            for (int i = alliesDefenders.size() - 1; i >= 0; i--) {
               regimentsB += Game.getCiv(alliesDefenders.get(i)).getArmyRegimentSize();
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }

         regimentsB = (int)(regimentsB * Game.aiValuesDiplomacy.AI_DECLARE_WAR_ALLIES_REGIMENTS_MODIFIER_DEFENDERS);
         return regimentsB + (int)(Game.getCiv(onCivID).getArmyRegimentSize() * Game.aiValuesDiplomacy.AI_DECLARE_WAR_DEFENDER_REGIMENTS_MODIFIER);
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
         return 0;
      }
   }
}
