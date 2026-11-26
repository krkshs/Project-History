package aoc.kingdoms.lukasz.map.diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Manager;
import aoc.kingdoms.lukasz.jakowski.AI.AI_PeaceTreaty;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageAlliance;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageCallToWar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageDefensivePact;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageDemandMilitaryAccess;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGift;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGuarantee;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageInsult;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageMilitaryAccessRefused;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageNonAggressionPact;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageWar;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamAchievementsManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class DiplomacyManager {
   public static final Color COLOR_WAR = new Color(0.78431374F, 0.1254902F, 0.14117648F, 1.0F);
   public static final Color COLOR_INSULT = new Color(0.8627451F, 0.39215687F, 0.0F, 1.0F);
   public static final Color COLOR_ALLIANCE = new Color(0.0F, 0.627451F, 1.0F, 1.0F);
   public static final Color COLOR_DEFENSIVE_PACT = new Color(0.41960785F, 0.23529412F, 0.7490196F, 1.0F);
   public static final Color COLOR_NON_AGGRESSION_PACT = new Color(0.78431374F, 0.78431374F, 0.19607843F, 1.0F);
   public static final Color COLOR_MILITARY_ACCESS = new Color(0.08627451F, 0.08627451F, 0.5411765F, 1.0F);
   public static final Color COLOR_GUARANTEE = new Color(0.74509805F, 0.23529412F, 0.6156863F, 1.0F);
   public static final Color COLOR_PEACE = new Color(0.78431374F, 0.92156863F, 0.7254902F, 1.0F);
   public static final Color COLOR_SPY = new Color(0.8235294F, 0.4117647F, 0.13725491F, 1.0F);
   public static final Color COLOR_UNION = new Color(0.25490198F, 0.6862745F, 0.7254902F, 1.0F);
   public static final Color COLOR_SUMMIT = new Color(0.4F, 0.2F, 0.6F, 1.0F);
   public static final Color COLOR_RED = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static final Color COLOR_NEUTRAL = new Color(0.92156863F, 0.9411765F, 0.8627451F, 1.0F);
   public static final Color COLOR_GREEN = new Color(0.27450982F, 0.50980395F, 0.23529412F, 1.0F);
   public static final Color COLOR_BATTLE = new Color(0.64705884F, 0.078431375F, 0.039215688F, 0.75F);
   public static long lTime;
   public static final int ANIMATION_TIME = 500;
   public static boolean inAnimation = false;
   public static float fAnimationPerc = 0.0F;

   public static final void updateInAnimation() {
      if (lTime + 500L >= CFG.currentTimeMillis) {
         inAnimation = true;
         fAnimationPerc = Math.min(1.0F, (float)(CFG.currentTimeMillis - lTime) / 500.0F);
      } else {
         inAnimation = false;
      }
   }

   public static final void updateAnimationTime() {
      lTime = CFG.currentTimeMillis;
      inAnimation = true;
   }

   public static String getOpinion_String(int iOpinion) {
      if (iOpinion <= getRelationAtWar()) {
         return Game.lang.get("AtWar");
      } else if (iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_UNFAVORABLE) {
         return Game.lang.get("Unfavorable");
      } else if (iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_STRAINED) {
         return Game.lang.get("Strained");
      } else if (iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_DETACHED) {
         return Game.lang.get("Detached");
      } else if (iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_NEUTRAL) {
         return Game.lang.get("Neutral");
      } else if (iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_WARM) {
         return Game.lang.get("Warm");
      } else {
         return iOpinion < GameValues.diplomacy.DIPLOMACY_RELATION_COOPERATIVE ? Game.lang.get("Cooperative") : Game.lang.get("Supportive");
      }
   }

   public static Color getOpinion_Color(int iOpinion) {
      if (iOpinion < -15) {
         return COLOR_RED;
      } else {
         return iOpinion < 15 ? COLOR_NEUTRAL : COLOR_GREEN;
      }
   }

   public static final float getRelationImprove(int iFromCivID, int iCivB) {
      return (
            GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_VALUE
               + GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_VALUE_PRESTIGE
                  * Math.min(1.0F, Game.getCiv(iFromCivID).iCivRankScore / Game.getCiv(iCivB).iCivRankScore)
         )
         * (1.0F + Game.getCiv(iFromCivID).civBonuses.ImproveRelationsModifier / 100.0F);
   }

   public static final float getRelationDamage(int iFromCivID, int iCivB) {
      return GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_VALUE;
   }

   public static final String getInsult() {
      switch (Game.oR.nextInt(5)) {
         case 0:
            return Game.lang.get("WeWillClaimBothYourLivesAndYourFreedom");
         case 1:
            return Game.lang.get("YourCivilizationWillCrumbleAndWeShallDanceUponTheRuinsOfYourFormerGreatness");
         case 2:
            return Game.lang.get("WellSnatchAwayYourMightLeavingYourCivilizationInTheShadowsOfItsFormerSelf");
         case 3:
            return Game.lang.get("PrepareForTheDemiseOfYourSovereigntyForWeWillSeizeIt");
         default:
            return Game.lang.get("YourStandingWillBeReducedToRuinsAndWeShallRevelInYourDownfall");
      }
   }

   public static final String getInsult(int id) {
      switch (id % 5) {
         case 0:
            return Game.lang.get("WeWillClaimBothYourLivesAndYourFreedom");
         case 1:
            return Game.lang.get("YourCivilizationWillCrumbleAndWeShallDanceUponTheRuinsOfYourFormerGreatness");
         case 2:
            return Game.lang.get("WellSnatchAwayYourMightLeavingYourCivilizationInTheShadowsOfItsFormerSelf");
         case 3:
            return Game.lang.get("PrepareForTheDemiseOfYourSovereigntyForWeWillSeizeIt");
         default:
            return Game.lang.get("YourStandingWillBeReducedToRuinsAndWeShallRevelInYourDownfall");
      }
   }

   public static final String getWarMessage() {
      switch (Game.oR.nextInt(7)) {
         case 0:
            return Game.lang.get("WitnessTheMightOfMyForcesAsWeDeclareWarUponYouForYourWeaknessCannotWithstandOurStrength");
         case 1:
            return Game.lang.get("BraceYourselfForIDeclareWarUponYouToAssertMyRightfulAuthorityOverYourTerritories");
         case 2:
            return Game.lang.get("ThisWarIsTheDestinyYouCannotEscapeAsWeRiseToCrushYourFeebleResistance");
         case 3:
            return Game.lang.get("YourLandsWillBeReshapedUnderOurBannerAsWarIsDeclaredToSecureOurRightfulDominion");
         case 4:
            return Game.lang.get("PrepareForTheMarchOfOurArmiesAsWeDeclareWarToAssertOurDominanceOverYourLands");
         case 5:
            return Game.lang.get("TheDrumsOfWarBeatLoudlyAnnouncingTheBeginningOfYourDemiseUnderOurRule");
         default:
            return Game.lang.get("TheBannersOfWarUnfurlMarkingTheBeginningOfTheEndForYourInsignificantCivilization");
      }
   }

   public static final String getWarMessage(int id) {
      switch (id % 7) {
         case 0:
            return Game.lang.get("WitnessTheMightOfMyForcesAsWeDeclareWarUponYouForYourWeaknessCannotWithstandOurStrength");
         case 1:
            return Game.lang.get("BraceYourselfForIDeclareWarUponYouToAssertMyRightfulAuthorityOverYourTerritories");
         case 2:
            return Game.lang.get("ThisWarIsTheDestinyYouCannotEscapeAsWeRiseToCrushYourFeebleResistance");
         case 3:
            return Game.lang.get("YourLandsWillBeReshapedUnderOurBannerAsWarIsDeclaredToSecureOurRightfulDominion");
         case 4:
            return Game.lang.get("PrepareForTheMarchOfOurArmiesAsWeDeclareWarToAssertOurDominanceOverYourLands");
         case 5:
            return Game.lang.get("TheDrumsOfWarBeatLoudlyAnnouncingTheBeginningOfYourDemiseUnderOurRule");
         default:
            return Game.lang.get("TheBannersOfWarUnfurlMarkingTheBeginningOfTheEndForYourInsignificantCivilization");
      }
   }

   public static final void updateAfterRemoveCiv(int iCivID) {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         try {
            Game.getCiv(i).diplomacy.updateRelationsAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateTruceAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateDefensivePactAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateNonAggressionPactAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateGuaranteeAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateGuaranteeByCivIDAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateAllianceAfterRemoveCiv(iCivID);
            Game.getCiv(i).diplomacy.updateMilitaryAccessAfterRemoveCiv(iCivID);
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }
      }
   }

   public static boolean willJoinWar(int iCivID, int iToWarOfCivID) {
      return Game.getCiv(iCivID).getPuppetOfCivID() == iToWarOfCivID;
   }

   public static final void declareWar(int iCivA, int iCivB) {
      try {
         declareWar(iCivA, iCivB, false, new ArrayList<>());
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final boolean declareWar(int civA, int civWarDeclaredOn, boolean free, List<Integer> callToWar) {
      return declareWar(civA, civWarDeclaredOn, free, callToWar, false);
   }

   public static final boolean declareWar(int civA, int civWarDeclaredOn, boolean free, List<Integer> callToWar, boolean isCoalition) {
      if (civA == civWarDeclaredOn) {
         return false;
      } else if (Game.getCiv(civA).getNumOfProvinces() > 0 && Game.getCiv(civWarDeclaredOn).getNumOfProvinces() > 0) {
         if (!free) {
            if (Game.getCiv(civA).diplomacy.getRelation(civWarDeclaredOn) > GameValues.war.RELATIONS_TO_DECLARE_WAR) {
               return false;
            }

            if (Game.getCiv(civA).diplomacy.haveNonAggressionPact(civWarDeclaredOn)) {
               return false;
            }

            if (Game.getCiv(civA).fDiplomacy < GameValues.diplomacy.DIPLOMACY_DECLARE_WAR_COST) {
               return false;
            }

            if (Game.getCiv(civA).diplomacy.haveAlliance(civWarDeclaredOn) || Game.getCiv(civWarDeclaredOn).diplomacy.haveAlliance(civA)) {
               return false;
            }

            Civilization var10000 = Game.getCiv(civA);
            var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_DECLARE_WAR_COST;
         }

         if (Game.getCiv(civA).diplomacy.truce.containsKey(civWarDeclaredOn)) {
            if (Game.getCiv(civA).diplomacy.truce.get(civWarDeclaredOn).iTurnID >= Game_Calendar.TURN_ID && !free) {
               return false;
            }

            Game.getCiv(civA).diplomacy.truce.remove(civWarDeclaredOn);
            Game.getCiv(civWarDeclaredOn).diplomacy.truce.remove(civA);
         }

         boolean conquerVassal = Game.getCiv(civWarDeclaredOn).getPuppetOfCivID() == civA;

         try {
            if (Game.getCiv(civA).diplomacy.alliance.containsKey(civWarDeclaredOn)) {
               Game.getCiv(civA).diplomacy.alliance.remove(civWarDeclaredOn);
            }

            if (Game.getCiv(civWarDeclaredOn).diplomacy.alliance.containsKey(civA)) {
               Game.getCiv(civWarDeclaredOn).diplomacy.alliance.remove(civA);
            }

            if (Game.getCiv(civA).diplomacy.defensivePact.containsKey(civWarDeclaredOn)) {
               Game.getCiv(civA).diplomacy.defensivePact.remove(civWarDeclaredOn);
            }

            if (Game.getCiv(civWarDeclaredOn).diplomacy.defensivePact.containsKey(civA)) {
               Game.getCiv(civWarDeclaredOn).diplomacy.defensivePact.remove(civA);
            }

            if (Game.getCiv(civA).diplomacy.nonAggressionPact.containsKey(civWarDeclaredOn)) {
               Game.getCiv(civA).diplomacy.nonAggressionPact.remove(civWarDeclaredOn);
            }

            if (Game.getCiv(civWarDeclaredOn).diplomacy.nonAggressionPact.containsKey(civA)) {
               Game.getCiv(civWarDeclaredOn).diplomacy.nonAggressionPact.remove(civA);
            }

            if (Game.getCiv(civA).diplomacy.guarantee.containsKey(civWarDeclaredOn)) {
               Game.getCiv(civA).diplomacy.guarantee.remove(civWarDeclaredOn);
            }

            if (Game.getCiv(civWarDeclaredOn).diplomacy.guarantee.containsKey(civA)) {
               Game.getCiv(civWarDeclaredOn).diplomacy.guarantee.remove(civA);
            }

            if (Game.getCiv(civA).diplomacy.militaryAccess.containsKey(civWarDeclaredOn)) {
               Game.getCiv(civA).diplomacy.militaryAccess.remove(civWarDeclaredOn);
            }

            if (Game.getCiv(civWarDeclaredOn).diplomacy.militaryAccess.containsKey(civA)) {
               Game.getCiv(civWarDeclaredOn).diplomacy.militaryAccess.remove(civA);
            }
         } catch (Exception var15) {
            CFG.exceptionStack(var15);
         }

         try {
            Game.getCiv(civA).addAggressiveExpansion(GameValues.aggressiveExpansion.AE_DECLARE_WAR);
         } catch (Exception var14) {
            CFG.exceptionStack(var14);
         }

         try {
            declareWar_UpdateRelation(civA, civWarDeclaredOn);
         } catch (Exception var13) {
            CFG.exceptionStack(var13);
         }

         String warKey = WarManager.addWar(civA, civWarDeclaredOn, conquerVassal, isCoalition);
         if (warKey == null) {
            return false;
         } else {
            List<Integer> alliesA = new ArrayList<>();

            try {
               alliesA = declareWar_AlliesDefender(civWarDeclaredOn, civA);
               if (alliesA.size() > 0 && warKey != null) {
                  for (int i = 0; i < alliesA.size(); i++) {
                     WarManager.lWars.get(warKey).addDefender(alliesA.get(i));
                     if (civA == Game.player.iCivID) {
                        Game.getCiv(civWarDeclaredOn).updateArmyImgID();
                        Game.getCiv(alliesA.get(i)).updateArmyImgID();
                     } else if (civWarDeclaredOn == Game.player.iCivID) {
                        Game.getCiv(civA).updateArmyImgID();
                        Game.getCiv(alliesA.get(i)).updateArmyImgID();
                     } else if (alliesA.get(i) == Game.player.iCivID) {
                        for (int a = callToWar.size() - 1; a >= 0; a--) {
                           Game.getCiv(callToWar.get(a)).updateArmyImgID();
                        }

                        Game.getCiv(civA).updateArmyImgID();
                        Game.getCiv(civWarDeclaredOn).updateArmyImgID();
                        Game.player.addMessage(new PMessageWar(civA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                        Game.menuManager
                           .rebuildInGame_Info(Game.lang.get("War"), Game.getCiv(civA).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName());
                        InGame_Info.imgID = Images.infoWar;
                        Game.soundsManager.loadNextMusicWar();
                        if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
                           Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
                              @Override
                              public void update() {
                                 ProvinceBorderManager.updateProvinceBorder();
                              }
                           });
                        }

                        Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                           @Override
                           public void update() {
                              Game.menuManager.rebuildInGame_Wars();
                           }
                        });
                     }
                  }

                  for (int ix = 0; ix < callToWar.size(); ix++) {
                     if (callToWar.get(ix) == Game.player.iCivID) {
                        for (int a = 0; a < alliesA.size(); a++) {
                           Game.getCiv(alliesA.get(a)).updateArmyImgID();
                        }
                        break;
                     }
                  }
               }
            } catch (Exception var20) {
               CFG.exceptionStack(var20);
            }

            try {
               if (callToWar.size() > 0 && warKey != null) {
                  for (int ixx = 0; ixx < callToWar.size(); ixx++) {
                     if (callToWar.get(ixx) == Game.player.iCivID) {
                        Game.player.addMessage(new PMessageCallToWar(civA, warKey, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                     } else {
                        WarManager.lWars.get(warKey).addAggressor(callToWar.get(ixx));
                        if (civA == Game.player.iCivID) {
                           Game.getCiv(civWarDeclaredOn).updateArmyImgID();
                           Game.getCiv(callToWar.get(ixx)).updateArmyImgID();
                        } else if (civWarDeclaredOn == Game.player.iCivID) {
                           Game.getCiv(civA).updateArmyImgID();
                           Game.getCiv(callToWar.get(ixx)).updateArmyImgID();
                        } else if (callToWar.get(ixx) == Game.player.iCivID) {
                           Game.getCiv(civA).updateArmyImgID();
                           Game.getCiv(civWarDeclaredOn).updateArmyImgID();
                           if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
                              Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
                                 @Override
                                 public void update() {
                                    ProvinceBorderManager.updateProvinceBorder();
                                 }
                              });
                           }

                           Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                              @Override
                              public void update() {
                                 Game.menuManager.rebuildInGame_Wars();
                              }
                           });
                        }
                     }
                  }
               }
            } catch (Exception var19) {
               CFG.exceptionStack(var19);
            }

            int regimentsA = Game.getCiv(civA).getArmyRegimentSize();
            int regimentsB = Game.getCiv(civWarDeclaredOn).getArmyRegimentSize();

            try {
               for (int ixxx = WarManager.lWars.get(warKey).lAggressors.size() - 1; ixxx > 0; ixxx--) {
                  regimentsA += Game.getCiv(WarManager.lWars.get(warKey).lAggressors.get(ixxx).iCivID).getArmyRegimentSize();
               }
            } catch (Exception var18) {
               CFG.exceptionStack(var18);
            }

            try {
               for (int ixxx = WarManager.lWars.get(warKey).lDefenders.size() - 1; ixxx > 0; ixxx--) {
                  regimentsB += Game.getCiv(WarManager.lWars.get(warKey).lDefenders.get(ixxx).iCivID).getArmyRegimentSize();
               }
            } catch (Exception var17) {
               CFG.exceptionStack(var17);
            }

            try {
               if (!Game.ideologiesManager.getIdeology(Game.getCiv(WarManager.lWars.get(warKey).lAggressors.get(0).iCivID).getIdeologyID()).TRIBAL
                  && Game.ideologiesManager.getIdeology(Game.getCiv(WarManager.lWars.get(warKey).lDefenders.get(0).iCivID).getIdeologyID()).TRIBAL) {
                  for (int ixxx = WarManager.lWars.get(warKey).lDefenders.size() - 1; ixxx >= 0; ixxx--) {
                     Game.getCiv(WarManager.lWars.get(warKey).lDefenders.get(ixxx).iCivID)
                        .setWarPlayDefensiveUntilTurnID(
                           Game_Calendar.TURN_ID
                              + Game.aiValuesDiplomacy.AI_WAR_DEFENDERS_PLAY_DEFENSIVE_TRIBAL_TURNS_MIN
                              + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_WAR_DEFENDERS_PLAY_DEFENSIVE_TRIBAL_TURNS_RANDOM)
                        );
                  }
               } else {
                  for (int ixxx = WarManager.lWars.get(warKey).lDefenders.size() - 1; ixxx >= 0; ixxx--) {
                     Game.getCiv(WarManager.lWars.get(warKey).lDefenders.get(ixxx).iCivID)
                        .setWarPlayDefensiveUntilTurnID(
                           Game_Calendar.TURN_ID
                              + Game.aiValuesDiplomacy.AI_WAR_DEFENDERS_PLAY_DEFENSIVE_TURNS_MIN
                              + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_WAR_DEFENDERS_PLAY_DEFENSIVE_TURNS_RANDOM)
                        );
                  }
               }
            } catch (Exception var16) {
               CFG.exceptionStack(var16);
            }

            try {
               AI_Manager.aiBudget.updateMilitaryLevel_War(civA, civWarDeclaredOn, regimentsA, regimentsB);
               AI_Manager.aiBudget.updateMilitaryLevel_War(civWarDeclaredOn, civA, regimentsB, regimentsA);
            } catch (Exception var12) {
               CFG.exceptionStack(var12);
            }

            declareWar_NeighborsRelationChange(civA, civWarDeclaredOn);
            if (civA == Game.player.iCivID) {
               Game.getCiv(civWarDeclaredOn).updateArmyImgID();
               if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
                  Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
                     @Override
                     public void update() {
                        ProvinceBorderManager.updateProvinceBorder();
                     }
                  });
               }

               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Wars();
                  }
               });
            } else if (civWarDeclaredOn == Game.player.iCivID) {
               Game.getCiv(civA).updateArmyImgID();
               Game.menuManager.rebuildInGame_Info(Game.lang.get("War"), Game.getCiv(civA).getCivName() + " - " + Game.getCiv(Game.player.iCivID).getCivName());
               InGame_Info.imgID = Images.infoWar;
               Game.soundsManager.loadNextMusicWar();
               Game.player.addMessage(new PMessageWar(civA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
               if (GameValues.provinceBorderWar.ENABLE_WAR_BORDER) {
                  Game.addSimpleTask(new Game.SimpleTask("updateProvinceBorder") {
                     @Override
                     public void update() {
                        ProvinceBorderManager.updateProvinceBorder();
                     }
                  });
               }

               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Wars();
                  }
               });
            }

            if (Game.getCiv(civA).getCapitalProvinceID() >= 0
               && Game.getCiv(civWarDeclaredOn).getCapitalProvinceID() >= 0
               && (GameValues.value.DRAW_DIPLOMACY_LINES_WAR_DECLARED || Game.player.iCivID == civA || Game.player.iCivID == civWarDeclaredOn)) {
               ProvinceDraw.addDiplomacyLines(Game.getCiv(civA).getCapitalProvinceID(), Game.getCiv(civWarDeclaredOn).getCapitalProvinceID(), COLOR_WAR);
            }

            for (int ixxx = 0; ixxx < alliesA.size(); ixxx++) {
               if (alliesA.get(ixxx) == Game.player.iCivID) {
                  for (int a = callToWar.size() - 1; a >= 0; a--) {
                     Game.getCiv(callToWar.get(a)).updateArmyImgID();
                  }
                  break;
               }
            }

            for (int ixxxx = 0; ixxxx < callToWar.size(); ixxxx++) {
               if (callToWar.get(ixxxx) == Game.player.iCivID) {
                  for (int a = alliesA.size() - 1; a >= 0; a--) {
                     Game.getCiv(alliesA.get(a)).updateArmyImgID();
                  }
                  break;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static final void declareWar_NeighborsRelationChange(int civID, int civWarDeclaredOn) {
      try {
         Civilization civ = Game.getCiv(civID);
         int capitalProvinceID = -1;
         if (civ.getCapitalProvinceID() >= 0) {
            capitalProvinceID = civ.getCapitalProvinceID();
         } else if (civ.getNumOfProvinces() > 0) {
            capitalProvinceID = civ.getProvinceID(0);
         }

         if (capitalProvinceID < 0) {
            return;
         }

         List<Integer> civsList = new ArrayList<>();

         for (int i = 0; i < civ.civNeighbors.civsSize; i++) {
            if (!civsList.contains(civ.civNeighbors.civs.get(i).civID)
               && civ.civNeighbors.civs.get(i).civID != civID
               && civ.civNeighbors.civs.get(i).civID != civWarDeclaredOn) {
               civsList.add(civ.civNeighbors.civs.get(i).civID);
            }

            for (int j = 0; j < Game.getCiv(civ.civNeighbors.civs.get(i).civID).civNeighbors.civsSize; j++) {
               if (!civsList.contains(Game.getCiv(civ.civNeighbors.civs.get(i).civID).civNeighbors.civs.get(j).civID)
                  && Game.getCiv(civ.civNeighbors.civs.get(i).civID).civNeighbors.civs.get(j).civID != civID
                  && Game.getCiv(civ.civNeighbors.civs.get(i).civID).civNeighbors.civs.get(j).civID != civWarDeclaredOn) {
                  civsList.add(Game.getCiv(civ.civNeighbors.civs.get(i).civID).civNeighbors.civs.get(j).civID);
               }
            }
         }

         float distance = 0.0F;
         int i = 0;

         for (int iSize = civsList.size(); i < iSize; i++) {
            distance = GameValues.aggressiveExpansion.DECLARE_WAR_RELATION_CHANGE_WITH_NEIGHBORS
               * (1.0F - Game.getDistance_PercOfMax(capitalProvinceID, Game.getCiv(civsList.get(i)).getCapitalProvinceID()));
            Game.getCiv(civID).diplomacy.updateRelation(civID, civsList.get(i), distance);
            Game.getCiv(civsList.get(i)).diplomacy.updateRelation(civsList.get(i), civID, distance);
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void declareWar_UpdateRelation(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         try {
            Game.getCiv(iCivA).diplomacy.removeImproveRelations(iCivB);
            Game.getCiv(iCivB).diplomacy.removeImproveRelations(iCivA);
         } catch (Exception var3) {
         }

         Game.getCiv(iCivA).diplomacy.setRelation_War(iCivB, getRelationAtWar());
         Game.getCiv(iCivB).diplomacy.setRelation_War(iCivA, getRelationAtWar());
         if (Game.getCiv(iCivA).getPuppetOfCivID() == iCivB) {
            Game.getCiv(iCivA).setPuppetOfCivID(iCivA);
         }

         if (Game.getCiv(iCivB).getPuppetOfCivID() == iCivA) {
            Game.getCiv(iCivB).setPuppetOfCivID(iCivB);
         }

         if (Game.getCiv(iCivA).diplomacy.haveAlliance(iCivB)) {
            Game.getCiv(iCivA).diplomacy.removeAlliance(iCivB);
            Game.getCiv(iCivB).diplomacy.removeAlliance(iCivA);
         }

         if (Game.getCiv(iCivB).diplomacy.haveAlliance(iCivA)) {
            Game.getCiv(iCivB).diplomacy.removeAlliance(iCivA);
            Game.getCiv(iCivA).diplomacy.removeAlliance(iCivB);
         }
      }
   }

   public static final void declareWar_UpdateRelation_Peace(int iCivA, int iCivB, String warKey) {
      Game.getCiv(iCivA).diplomacy.removeImproveRelations(iCivB);
      Game.getCiv(iCivB).diplomacy.removeImproveRelations(iCivA);
      int tRandom = 0;
      if (GameValues.peace.PEACE_RELATION_RANDOM * 100 != 0) {
         try {
            tRandom = (int)(-GameValues.peace.PEACE_RELATION_RANDOM + Game.oR.nextInt(Math.abs(GameValues.peace.PEACE_RELATION_RANDOM * 2 * 100)) / 100.0F);
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }
      }

      float var10002 = GameValues.peace.PEACE_RELATION + tRandom;
      Game.getCiv(iCivA).diplomacy.setRelation_Peace(iCivB, var10002, warKey);
      var10002 = GameValues.peace.PEACE_RELATION + tRandom;
      Game.getCiv(iCivB).diplomacy.setRelation_Peace(iCivA, var10002, warKey);
   }

   public static final List<Integer> civAllies(int iCivA) {
      List<Integer> out = new ArrayList<>();
      if (Game.getCiv(iCivA).diplomacy.alliance.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(iCivA).diplomacy.alliance.entrySet()) {
            if (entry.getValue().iCivID != iCivA && !out.contains(entry.getValue().iCivID)) {
               out.add(entry.getValue().iCivID);
            }
         }
      }

      if (Game.ENABLE_CALL_VASSALS) {
         for (int i = 0; i < Game.getCiv(iCivA).diplomacy.iVassalsSize; i++) {
            if (Game.getCiv(iCivA).diplomacy.lVassals.get(i).c != iCivA && !out.contains(Game.getCiv(iCivA).diplomacy.lVassals.get(i).c)) {
               out.add(Game.getCiv(iCivA).diplomacy.lVassals.get(i).c);
            }
         }
      }

      for (int ix = out.size() - 1; ix >= 0; ix--) {
         if (out.get(ix) >= 1 && Game.getCiv(out.get(ix)).getNumOfProvinces() > 0) {
            if (out.get(ix) == iCivA) {
               out.remove(ix);
            }
         } else {
            out.remove(ix);
         }
      }

      return out;
   }

   public static final boolean isAlly(int iCivA, int isAlly) {
      if (iCivA == isAlly) {
         return true;
      } else {
         if (Game.getCiv(iCivA).diplomacy.alliance.size() > 0) {
            for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(iCivA).diplomacy.alliance.entrySet()) {
               if (entry.getValue().iCivID == isAlly) {
                  return true;
               }
            }
         }

         for (int i = 0; i < Game.getCiv(iCivA).diplomacy.iVassalsSize; i++) {
            if (Game.getCiv(iCivA).diplomacy.lVassals.get(i).c == isAlly) {
               return true;
            }
         }

         return Game.getCiv(iCivA).getPuppetOfCivID() == isAlly;
      }
   }

   public static final boolean isAlly_AllianceCheck(int iCivA, int isAlly) {
      if (Game.getCiv(iCivA).diplomacy.alliance.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(iCivA).diplomacy.alliance.entrySet()) {
            if (entry.getValue().iCivID == isAlly) {
               return true;
            }
         }
      }

      return false;
   }

   public static final List<Integer> declareWar_AlliesAttacker(int civAllies, int civEnemy) {
      List<Integer> out = new ArrayList<>();
      if (Game.getCiv(civAllies).diplomacy.alliance.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(civAllies).diplomacy.alliance.entrySet()) {
            if (entry.getValue().iCivID != civAllies && entry.getValue().iCivID != civEnemy && !out.contains(entry.getValue().iCivID)) {
               out.add(entry.getValue().iCivID);
            }
         }
      }

      if (Game.ENABLE_CALL_VASSALS) {
         for (int i = 0; i < Game.getCiv(civAllies).diplomacy.iVassalsSize; i++) {
            if (Game.getCiv(civAllies).diplomacy.lVassals.get(i).c != civAllies
               && Game.getCiv(civAllies).diplomacy.lVassals.get(i).c != civEnemy
               && !out.contains(Game.getCiv(civAllies).diplomacy.lVassals.get(i).c)) {
               out.add(Game.getCiv(civAllies).diplomacy.lVassals.get(i).c);
            }
         }
      }

      for (int ix = out.size() - 1; ix >= 0; ix--) {
         if (out.get(ix) >= 1 && Game.getCiv(out.get(ix)).getNumOfProvinces() > 0) {
            if (out.get(ix) == civAllies || out.get(ix) == civEnemy) {
               out.remove(ix);
            }
         } else {
            out.remove(ix);
         }
      }

      return out;
   }

   public static final List<Integer> declareWar_AlliesDefender(int findCivAllies, int enemyCiv) {
      List<Integer> out = new ArrayList<>();
      Civilization civAllies = Game.getCiv(findCivAllies);
      if (civAllies.inAllianceSize > 0) {
         try {
            for (int i = 0; i < civAllies.inAllianceSize; i++) {
               if (!Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).isInAlliance(enemyCiv)) {
                  if (Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).typeOfAlliance == 0) {
                     if (GameValues.hre.HRE_EMPEROR_WAR_DEFEND_HRE
                        && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID > 0
                        && Game.getCiv(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID).getNumOfProvinces() > 0
                        && !out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID)) {
                        out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID);
                     }

                     if (GameValues.hre.HRE_ELECTORS_WAR_DEFEND_HRE) {
                        for (int a = 0; a < Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.size(); a++) {
                           if (!out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(a))
                              && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(a) != findCivAllies) {
                              out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(a));
                           }
                        }
                     }

                     if (GameValues.hre.HRE_PRINCES_WAR_DEFEND_HRE) {
                        for (int ax = 0; ax < Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.size(); ax++) {
                           if (!out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(ax))
                              && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(ax) != findCivAllies) {
                              out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(ax));
                           }
                        }
                     }
                  } else if (Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).typeOfAlliance == 1) {
                     for (int axx = 0; axx < Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.size(); axx++) {
                        if (!out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(axx))
                           && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(axx) != findCivAllies) {
                           out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).firstTier.get(axx));
                        }
                     }

                     for (int axxx = 0; axxx < Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.size(); axxx++) {
                        if (!out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(axxx))
                           && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(axxx) != findCivAllies) {
                           out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).secondTier.get(axxx));
                        }
                     }

                     if (!out.contains(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID)
                        && Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID != findCivAllies) {
                        out.add(Game.alliancesSpecial.get(civAllies.inAlliance.get(i)).iLeaderCivID);
                     }
                  }
               }
            }
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }
      }

      if (civAllies.diplomacy.alliance.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entry : civAllies.diplomacy.alliance.entrySet()) {
            if (entry.getValue().iCivID != enemyCiv && entry.getValue().iCivID != findCivAllies && !out.contains(entry.getValue().iCivID)) {
               out.add(entry.getValue().iCivID);
            }
         }
      }

      if (civAllies.diplomacy.defensivePact.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entryx : civAllies.diplomacy.defensivePact.entrySet()) {
            if (entryx.getValue().iCivID != enemyCiv && entryx.getValue().iCivID != findCivAllies && !out.contains(entryx.getValue().iCivID)) {
               out.add(entryx.getValue().iCivID);
            }
         }
      }

      if (civAllies.diplomacy.guaranteeByCivID.size() > 0) {
         for (Entry<Integer, Diplomacy.DiplomacyData> entryxx : civAllies.diplomacy.guaranteeByCivID.entrySet()) {
            if (entryxx.getValue().iCivID != enemyCiv && entryxx.getValue().iCivID != findCivAllies && !out.contains(entryxx.getValue().iCivID)) {
               out.add(entryxx.getValue().iCivID);
            }
         }
      }

      if (Game.ENABLE_CALL_VASSALS) {
         for (int ix = 0; ix < civAllies.diplomacy.iVassalsSize; ix++) {
            if (civAllies.diplomacy.lVassals.get(ix).c != enemyCiv
               && civAllies.diplomacy.lVassals.get(ix).c != findCivAllies
               && !out.contains(civAllies.diplomacy.lVassals.get(ix).c)) {
               out.add(civAllies.diplomacy.lVassals.get(ix).c);
            }
         }
      }

      if (GameValues.vassal.LORD_AUTO_JOIN_VASSALS_DEFENSIVE_WAR
         && civAllies.getPuppetOfCivID() != findCivAllies
         && Game.getCiv(enemyCiv).getPuppetOfCivID() != civAllies.getPuppetOfCivID()
         && !out.contains(civAllies.getPuppetOfCivID())) {
         out.add(civAllies.getPuppetOfCivID());
      }

      for (int ixx = out.size() - 1; ixx >= 0; ixx--) {
         if (out.get(ixx) >= 1 && Game.getCiv(out.get(ixx)).getNumOfProvinces() > 0) {
            if (out.get(ixx) == enemyCiv || out.get(ixx) == findCivAllies) {
               out.remove(ixx);
            }
         } else {
            out.remove(ixx);
         }
      }

      return out;
   }

   public static float getRelationAtWar() {
      return GameValues.diplomacy.DIPLOMACY_RELATIONS_MIN - GameValues.diplomacy.DIPLOMACY_RELATIONS_WAR_EXTRA;
   }

   public static boolean isAtWar(int iCivA, int iCivB) {
      return Game.getCiv(iCivA).diplomacy.getRelation(iCivB) <= getRelationAtWar() || iCivA < 0 && iCivB >= 0 || iCivA >= 0 && iCivB < 0;
   }

   public static final boolean sendInsult(int iFromCivID, int iCivB) {
      if (Game.getCiv(iFromCivID).fLegacy < -GameValues.diplomacy.DIPLOMACY_SEND_AN_INSULT_COST_LEGACY) {
         return false;
      } else if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_SEND_INSULT_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iCivB)) {
         return false;
      } else {
         if (iCivB == Game.player.iCivID) {
            Game.player.addMessage(new PMessageInsult(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
         }

         Game.getCiv(iFromCivID).diplomacy.updateRelation(iFromCivID, iCivB, GameValues.diplomacy.DIPLOMACY_SEND_AN_INSULT_DAMAGE);
         Game.getCiv(iCivB).diplomacy.updateRelation(iCivB, iFromCivID, GameValues.diplomacy.DIPLOMACY_SEND_AN_INSULT_DAMAGE);
         Game.getCiv(iFromCivID).addLegacy(GameValues.diplomacy.DIPLOMACY_SEND_AN_INSULT_COST_LEGACY);
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_SEND_INSULT_COST;
         return true;
      }
   }

   public static final boolean startEspionageMission(int spyCivID) {
      if (Game.getCiv(Game.player.iCivID).fGold < sendSpyCost(Game.player.iCivID, spyCivID)) {
         return false;
      } else if (Game.player.playerData.espionage.espionageMission_IsAdded(spyCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fGold = var10000.fGold - sendSpyCost(Game.player.iCivID, spyCivID);
         Game.player.playerData.espionage.addEspionageMission(spyCivID);
         return true;
      }
   }

   public static final int sendSpyCost(int byCivID, int spyCivID) {
      return (int)(
         (GameValues.spy.SEND_SPY_COST + Game.getCiv(spyCivID).getNumOfProvinces() * GameValues.spy.SEND_SPY_COST_PER_PROVINCE)
            * (
               1.0F
                  + (Game.getCiv(byCivID).diplomacy.isRival(spyCivID) ? -GameValues.spy.SEND_SPY_TO_RIVAL_COST_REDUCTION : 0.0F)
                  + Game.getDistance_PercOfMax(Game.getCiv(byCivID).getCapitalProvinceID(), Game.getCiv(spyCivID).getCapitalProvinceID())
                     * GameValues.spy.SEND_SPY_DISTANCE_COST_MODIFIER
            )
      );
   }

   public static final int sendSpyTime(int byCivID, int spyCivID) {
      return (int)(
         (GameValues.spy.SEND_SPY_TIME + Game.getCiv(spyCivID).getNumOfProvinces() * GameValues.spy.SEND_SPY_TIME_PER_PROVINCE)
            * (
               1.0F
                  + (Game.getCiv(byCivID).diplomacy.isRival(spyCivID) ? -GameValues.spy.SEND_SPY_TO_RIVAL_TIME_REDUCTION : 0.0F)
                  + Game.getDistance_PercOfMax(Game.getCiv(byCivID).getCapitalProvinceID(), Game.getCiv(spyCivID).getCapitalProvinceID())
                     * GameValues.spy.SEND_SPY_DISTANCE_TIME_MODIFIER
            )
      );
   }

   public static final boolean giftGold(int iFromCivID, int iToCivID, int iClicks) {
      if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_GIFT_COST) {
         return false;
      } else {
         float goldToGive = iClicks * GameValues.diplomacy.DIPLOMACY_GIFT_GOLD_PER_CLICK;
         if (Game.getCiv(iFromCivID).fGold < goldToGive) {
            return false;
         } else {
            Civilization var10000 = Game.getCiv(iFromCivID);
            var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_GIFT_COST;
            Game.getCiv(iFromCivID).fGold -= goldToGive;
            Game.getCiv(iToCivID).fGold += goldToGive;
            if (!isAtWar(iFromCivID, iToCivID)) {
               Game.getCiv(iFromCivID)
                  .diplomacy
                  .setRelation(
                     iFromCivID,
                     iToCivID,
                     Game.getCiv(iFromCivID).diplomacy.getRelation(iToCivID) + iClicks * GameValues.diplomacy.DIPLOMACY_GIFT_RELATION_PER_CLICK
                  );
               Game.getCiv(iToCivID)
                  .diplomacy
                  .setRelation(
                     iToCivID,
                     iFromCivID,
                     Game.getCiv(iToCivID).diplomacy.getRelation(iFromCivID) + iClicks * GameValues.diplomacy.DIPLOMACY_GIFT_RELATION_PER_CLICK
                  );
            }

            if (iToCivID == Game.player.iCivID) {
               Game.player.addMessage(new PMessageGift(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS, (int)goldToGive));
            }

            return true;
         }
      }
   }

   public static final boolean offerAlliance(int iFromCivID, int iToCivID) {
      if (Game.getCiv(iToCivID).getPuppetOfCivID() != iToCivID && iToCivID != Game.player.iCivID && iFromCivID != Game.player.iCivID) {
         return false;
      } else if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_ALLIANCE_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iToCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_ALLIANCE_COST;
         if (iToCivID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageAlliance(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            return true;
         } else {
            if (getAlliance_Score(iFromCivID, iToCivID) >= 0) {
               acceptAllianceOffer(iFromCivID, iToCivID);
            } else {
               declineAllianceOffer(iFromCivID, iToCivID);
            }

            return true;
         }
      }
   }

   public static int getMaxNumberOfAlliances(int civID) {
      return GameValues.diplomacy.BASE_MAX_NUM_OF_ALLIANCES + Game.getCiv(civID).civBonuses.MaxNumOfAlliances;
   }

   public static boolean getCanJoinNewAlliance(int civID) {
      return getMaxNumberOfAlliances(civID) > Game.getCiv(civID).diplomacy.alliance.size();
   }

   public static final void acceptAllianceOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager
            .rebuildInGame_Info(Game.lang.get("AllianceProposalAccepted"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
         SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.ALLIANCE);
      }

      addAlliance(iCivA, iCivB);
      if (iCivA != Game.player.iCivID
         && GameValues.value.DRAW_DIPLOMACY_LINES_WHEN_ALLIANCE_SIGNED
         && Game.getCiv(iCivA).getCapitalProvinceID() >= 0
         && Game.getCiv(iCivB).getCapitalProvinceID() >= 0) {
         ProvinceDraw.addDiplomacyLines(Game.getCiv(iCivA).getCapitalProvinceID(), Game.getCiv(iCivB).getCapitalProvinceID(), COLOR_ALLIANCE);
      }

      Game.getCiv(iCivA).aiCivDiplomacy.removePreparingForAllianceWithCivID(iCivB);
      Game.getCiv(iCivB).aiCivDiplomacy.removePreparingForAllianceWithCivID(iCivA);
   }

   public static final void declineAllianceOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager
            .rebuildInGame_Info(Game.lang.get("AllianceProposalWasRejected"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }
   }

   public static final void addAlliance(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            if (getCanJoinNewAlliance(iCivA)) {
               if (getCanJoinNewAlliance(iCivB)) {
                  int extraRand = GameValues.diplomacy.DIPLOMACY_ALLIANCE_EXPIRES_EXTRA_RANDOM > 0
                     ? Game.oR.nextInt(GameValues.diplomacy.DIPLOMACY_ALLIANCE_EXPIRES_EXTRA_RANDOM)
                     : 0;
                  int var10002 = Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_ALLIANCE_EXPIRES;
                  Game.getCiv(iCivA).diplomacy.addAlliance(iCivB, var10002 + extraRand);
                  var10002 = Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_ALLIANCE_EXPIRES;
                  Game.getCiv(iCivB).diplomacy.addAlliance(iCivA, var10002 + extraRand);
                  if (iCivA == Game.player.iCivID) {
                     Game.getCiv(iCivB).updateArmyImgID();
                     Game.player.fog.updateFogOfWar_Civ(iCivB);
                  } else if (iCivB == Game.player.iCivID) {
                     Game.getCiv(iCivA).updateArmyImgID();
                     Game.player.fog.updateFogOfWar_Civ(iCivA);
                  }
               }
            }
         }
      }
   }

   public static final void removeAlliance(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeAlliance(iCivB);
      Game.getCiv(iCivB).diplomacy.removeAlliance(iCivA);
      if (iCivA == Game.player.iCivID) {
         Game.getCiv(iCivB).updateArmyImgID();
         Game.player.fog.updateFogOfWar_Civ(iCivB);
      } else if (iCivB == Game.player.iCivID) {
         Game.getCiv(iCivA).updateArmyImgID();
         Game.player.fog.updateFogOfWar_Civ(iCivA);
      }
   }

   public static final float getAlliance_Score_BASE_VALUE(int civFrom, int civTo) {
      return GameValues.diplomacy.ALLIANCE_SCORE_BASE_VALUE
         * Math.max(
            GameValues.diplomacy.ALLIANCE_SCORE_BASE_RANK_SCORE_MIN,
            Math.min(GameValues.diplomacy.ALLIANCE_SCORE_BASE_RANK_SCORE_MAX, Game.getCiv(civTo).iCivRankScore / Game.getCiv(civFrom).iCivRankScore)
         );
   }

   public static final float getAlliance_Score_SameRivals(int civFrom, int civTo) {
      float out = 0.0F;
      if (Game.getCiv(civFrom).diplomacy.rivals.size() > 0) {
         Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = Game.getCiv(civFrom).diplomacy.rivals.entrySet().iterator();

         while (it.hasNext()) {
            Diplomacy.DiplomacyData tData = it.next().getValue();
            if (Game.getCiv(civTo).diplomacy.isRival(tData.iCivID)) {
               out += GameValues.diplomacy.ALLIANCE_SCORE_PER_SAME_RIVAL;
            }
         }
      }

      return out;
   }

   public static final int getAlliance_Score(int civFrom, int civTo) {
      if (!getCanJoinNewAlliance(civFrom) || !getCanJoinNewAlliance(civTo)) {
         return GameValues.diplomacy.ALLIANCE_SCORE_MAX_NUM_OF_ALLIANCES;
      } else {
         return Game.getCiv(civFrom).diplomacy.isAtWar()
            ? (int)GameValues.diplomacy.ALLIANCE_SCORE_AT_WAR
            : (int)(
               getAlliance_Score_BASE_VALUE(civFrom, civTo)
                  + Game.getCiv(civTo).diplomacy.getRelation(civFrom) * GameValues.diplomacy.ALLIANCE_SCORE_PER_RELATION
                  + GameValues.diplomacy.ALLIANCE_SCORE_DISTANCE
                     * Game.getDistance_PercOfMax(Game.getCiv(civFrom).getCapitalProvinceID(), Game.getCiv(civTo).getCapitalProvinceID())
                  + (Game.getCiv(civTo).diplomacy.isRival(civFrom) ? GameValues.diplomacy.ALLIANCE_SCORE_RIVALS : 0.0F)
                  + (Game.getCiv(civFrom).diplomacy.isRival(civTo) ? GameValues.diplomacy.ALLIANCE_SCORE_RIVALS : 0.0F)
                  + getAlliance_Score_SameRivals(civFrom, civTo)
                  + (Game.getCiv(civTo).getPuppetOfCivID() == civFrom ? GameValues.diplomacy.ALLIANCE_SCORE_FOR_VASSAL_OF_CIV : 0.0F)
                  + Game.getCiv(civFrom).getAggressiveExpansion() * GameValues.diplomacy.ALLIANCE_SCORE_PER_AGGRESSIVE_EXPANSION
                  + (
                     Game.getCiv(civFrom).getReligionID() != Game.getCiv(civTo).getReligionID()
                        ? (
                           Game.religionManager.getReligion(Game.getCiv(civFrom).getReligionID()).ReligionGroupID
                                 != Game.religionManager.getReligion(Game.getCiv(civTo).getReligionID()).ReligionGroupID
                              ? GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION_GROUP
                              : GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION
                        )
                        : 0.0F
                  )
                  + (
                     Game.getCiv(civFrom).getIdeologyID() != Game.getCiv(civTo).getIdeologyID()
                        ? (
                           Game.ideologiesManager.getIdeology(Game.getCiv(civFrom).getIdeologyID()).GOV_GROUP_ID
                                 != Game.ideologiesManager.getIdeology(Game.getCiv(civTo).getIdeologyID()).GOV_GROUP_ID
                              ? GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT_GROUP
                              : GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT
                        )
                        : 0.0F
                  )
            );
      }
   }

   public static final float getDefensivePact_Score_BASE_VALUE(int civFrom, int civTo) {
      return GameValues.diplomacy.DEFENSIVE_PACT_SCORE_BASE_VALUE
         * Math.max(
            GameValues.diplomacy.DEFENSIVE_PACT_SCORE_BASE_RANK_SCORE_MIN,
            Math.min(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_BASE_RANK_SCORE_MAX, Game.getCiv(civTo).iCivRankScore / Game.getCiv(civFrom).iCivRankScore)
         );
   }

   public static final float getDefensivePact_Score_SameRivals(int civFrom, int civTo) {
      float out = 0.0F;
      if (Game.getCiv(civFrom).diplomacy.rivals.size() > 0) {
         Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = Game.getCiv(civFrom).diplomacy.rivals.entrySet().iterator();

         while (it.hasNext()) {
            Diplomacy.DiplomacyData tData = it.next().getValue();
            if (Game.getCiv(civTo).diplomacy.isRival(tData.iCivID)) {
               out += GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_SAME_RIVAL;
            }
         }
      }

      return out;
   }

   public static final int getDefensivePact_Score(int civFrom, int civTo) {
      return Game.getCiv(civFrom).diplomacy.isAtWar()
         ? (int)GameValues.diplomacy.DEFENSIVE_PACT_SCORE_AT_WAR
         : (int)(
            getDefensivePact_Score_BASE_VALUE(civFrom, civTo)
               + Game.getCiv(civTo).diplomacy.getRelation(civFrom) * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_RELATION
               + GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DISTANCE
                  * Game.getDistance_PercOfMax(Game.getCiv(civFrom).getCapitalProvinceID(), Game.getCiv(civTo).getCapitalProvinceID())
               + (Game.getCiv(civTo).diplomacy.isRival(civFrom) ? GameValues.diplomacy.DEFENSIVE_PACT_SCORE_RIVALS : 0.0F)
               + (Game.getCiv(civFrom).diplomacy.isRival(civTo) ? GameValues.diplomacy.DEFENSIVE_PACT_SCORE_RIVALS : 0.0F)
               + getDefensivePact_Score_SameRivals(civFrom, civTo)
               + Game.getCiv(civFrom).getAggressiveExpansion() * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_AGGRESSIVE_EXPANSION
               + (Game.getCiv(civFrom).diplomacy.defensivePact.size() + Game.getCiv(civTo).diplomacy.defensivePact.size())
                  * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_DEFENSIVE_PACT
               + (
                  Game.getCiv(civFrom).getReligionID() != Game.getCiv(civTo).getReligionID()
                     ? (
                        Game.religionManager.getReligion(Game.getCiv(civFrom).getReligionID()).ReligionGroupID
                              != Game.religionManager.getReligion(Game.getCiv(civTo).getReligionID()).ReligionGroupID
                           ? GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_RELIGION_GROUP
                           : GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_RELIGION
                     )
                     : 0.0F
               )
               + (
                  Game.getCiv(civFrom).getIdeologyID() != Game.getCiv(civTo).getIdeologyID()
                     ? (
                        Game.ideologiesManager.getIdeology(Game.getCiv(civFrom).getIdeologyID()).GOV_GROUP_ID
                              != Game.ideologiesManager.getIdeology(Game.getCiv(civTo).getIdeologyID()).GOV_GROUP_ID
                           ? GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_GOVERNMENT_GROUP
                           : GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_GOVERNMENT
                     )
                     : 0.0F
               )
         );
   }

   public static final float getNonAggression_Score_BASE_VALUE(int civFrom, int civTo) {
      return GameValues.diplomacy.NON_AGGRESSION_SCORE_BASE_VALUE
         * Math.max(
            GameValues.diplomacy.NON_AGGRESSION_SCORE_BASE_RANK_SCORE_MIN,
            Math.min(GameValues.diplomacy.NON_AGGRESSION_SCORE_BASE_RANK_SCORE_MAX, Game.getCiv(civTo).iCivRankScore / Game.getCiv(civFrom).iCivRankScore)
         );
   }

   public static final float getNonAggression_Score_SameRivals(int civFrom, int civTo) {
      float out = 0.0F;
      if (Game.getCiv(civFrom).diplomacy.rivals.size() > 0) {
         Iterator<Entry<Integer, Diplomacy.DiplomacyData>> it = Game.getCiv(civFrom).diplomacy.rivals.entrySet().iterator();

         while (it.hasNext()) {
            Diplomacy.DiplomacyData tData = it.next().getValue();
            if (Game.getCiv(civTo).diplomacy.isRival(tData.iCivID)) {
               out += GameValues.diplomacy.NON_AGGRESSION_SCORE_PER_SAME_RIVAL;
            }
         }
      }

      return out;
   }

   public static final int getNonAggression_Score(int civFrom, int civTo) {
      return Game.getCiv(civFrom).diplomacy.isAtWar()
         ? (int)GameValues.diplomacy.NON_AGGRESSION_SCORE_AT_WAR
         : (int)(
            getDefensivePact_Score_BASE_VALUE(civFrom, civTo)
               + Game.getCiv(civTo).diplomacy.getRelation(civFrom) * GameValues.diplomacy.NON_AGGRESSION_SCORE_PER_RELATION
               + GameValues.diplomacy.NON_AGGRESSION_SCORE_DISTANCE
                  * Game.getDistance_PercOfMax(Game.getCiv(civFrom).getCapitalProvinceID(), Game.getCiv(civTo).getCapitalProvinceID())
               + (Game.getCiv(civTo).diplomacy.isRival(civFrom) ? GameValues.diplomacy.NON_AGGRESSION_SCORE_RIVALS : 0.0F)
               + (Game.getCiv(civFrom).diplomacy.isRival(civTo) ? GameValues.diplomacy.NON_AGGRESSION_SCORE_RIVALS : 0.0F)
               + (Game.getCiv(civFrom).diplomacy.nonAggressionPact.size() + Game.getCiv(civTo).diplomacy.nonAggressionPact.size())
                  * GameValues.diplomacy.NON_AGGRESSION_SCORE_PER_NON_AGGRESSION_PACTS
               + getNonAggression_Score_SameRivals(civFrom, civTo)
               + (
                  Game.getCiv(civFrom).getReligionID() != Game.getCiv(civTo).getReligionID()
                     ? (
                        Game.religionManager.getReligion(Game.getCiv(civFrom).getReligionID()).ReligionGroupID
                              != Game.religionManager.getReligion(Game.getCiv(civTo).getReligionID()).ReligionGroupID
                           ? GameValues.diplomacy.NON_AGGRESSION_SCORE_DIFFERENT_RELIGION_GROUP
                           : GameValues.diplomacy.NON_AGGRESSION_SCORE_DIFFERENT_RELIGION
                     )
                     : 0.0F
               )
               + (
                  Game.getCiv(civFrom).getIdeologyID() != Game.getCiv(civTo).getIdeologyID()
                     ? (
                        Game.ideologiesManager.getIdeology(Game.getCiv(civFrom).getIdeologyID()).GOV_GROUP_ID
                              != Game.ideologiesManager.getIdeology(Game.getCiv(civTo).getIdeologyID()).GOV_GROUP_ID
                           ? GameValues.diplomacy.NON_AGGRESSION_SCORE_DIFFERENT_GOVERNMENT_GROUP
                           : GameValues.diplomacy.NON_AGGRESSION_SCORE_DIFFERENT_GOVERNMENT
                     )
                     : 0.0F
               )
         );
   }

   public static final void addVassal(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            Game.getCiv(iCivB).setPuppetOfCivID(iCivA);
            if (iCivA == Game.player.iCivID) {
               Game.getCiv(iCivB).updateArmyImgID();
               Game.player.fog.updateFogOfWar_Civ(iCivB);
            } else if (iCivB == Game.player.iCivID) {
               Game.getCiv(iCivA).updateArmyImgID();
               Game.player.fog.updateFogOfWar_Civ(iCivA);
            }
         }
      }
   }

   public static final void removeVassal(int iCivA) {
      if (Game.getCiv(iCivA).getPuppetOfCivID() != iCivA) {
         int updateFog = Game.getCiv(iCivA).getPuppetOfCivID();
         Game.getCiv(iCivA).setPuppetOfCivID(iCivA);
         if (iCivA == Game.player.iCivID) {
            Game.getCiv(updateFog).updateArmyImgID();
            Game.player.fog.updateFogOfWar_Civ(updateFog);
         } else if (updateFog == Game.player.iCivID) {
            Game.getCiv(iCivA).updateArmyImgID();
            Game.player.fog.updateFogOfWar_Civ(iCivA);
         }
      }
   }

   public static final void addTruce(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            Game.getCiv(iCivA).diplomacy.addTruce(iCivB, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_TRUCE_EXPIRES);
            Game.getCiv(iCivB).diplomacy.addTruce(iCivA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_TRUCE_EXPIRES);
         }
      }
   }

   public static final void removeTruce(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeTruce(iCivB);
      Game.getCiv(iCivB).diplomacy.removeTruce(iCivA);
   }

   public static final boolean offerDefensivePact(int iFromCivID, int iToCivID) {
      if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iToCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST;
         if (iToCivID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageDefensivePact(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            return true;
         } else {
            if (getDefensivePact_Score(iFromCivID, iToCivID) >= 0) {
               acceptDefensivePactOffer(iFromCivID, iToCivID);
            } else {
               declineDefensivePactOffer(iFromCivID, iToCivID);
            }

            return true;
         }
      }
   }

   public static final void acceptDefensivePactOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("DefensivePact"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }

      addDefensivePact(iCivA, iCivB);
      if (iCivA != Game.player.iCivID
         && GameValues.value.DRAW_DIPLOMACY_LINES_WHEN_DEFENSIVE_PACT_SIGNED
         && Game.getCiv(iCivA).getCapitalProvinceID() >= 0
         && Game.getCiv(iCivB).getCapitalProvinceID() >= 0) {
         ProvinceDraw.addDiplomacyLines(Game.getCiv(iCivA).getCapitalProvinceID(), Game.getCiv(iCivB).getCapitalProvinceID(), COLOR_DEFENSIVE_PACT);
      }
   }

   public static final void declineDefensivePactOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("ProposalWasRejected"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }
   }

   public static final void addDefensivePact(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            Game.getCiv(iCivA).diplomacy.addDefensivePact(iCivB, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_EXPIRES);
            Game.getCiv(iCivB).diplomacy.addDefensivePact(iCivA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_EXPIRES);
         }
      }
   }

   public static final void removeDefensivePact(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeDefensivePact(iCivB);
      Game.getCiv(iCivB).diplomacy.removeDefensivePact(iCivA);
   }

   public static final boolean offerNonAggressionPact(int iFromCivID, int iToCivID) {
      if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_NON_AGGRESSION_PACT_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iToCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_NON_AGGRESSION_PACT_COST;
         if (iToCivID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageNonAggressionPact(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            return true;
         } else {
            if (getNonAggression_Score(iFromCivID, iToCivID) >= 0) {
               acceptNonAggressionOffer(iFromCivID, iToCivID);
            } else {
               declineNonAggressionOffer(iFromCivID, iToCivID);
            }

            return true;
         }
      }
   }

   public static final void acceptNonAggressionOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("NonAggressionPact"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }

      addNonAggressionPact(iCivA, iCivB);
      if (iCivA != Game.player.iCivID
         && GameValues.value.DRAW_DIPLOMACY_LINES_WHEN_NON_AGGRESSION_SIGNED
         && Game.getCiv(iCivA).getCapitalProvinceID() >= 0
         && Game.getCiv(iCivB).getCapitalProvinceID() >= 0) {
         ProvinceDraw.addDiplomacyLines(Game.getCiv(iCivA).getCapitalProvinceID(), Game.getCiv(iCivB).getCapitalProvinceID(), COLOR_NON_AGGRESSION_PACT);
      }
   }

   public static final void declineNonAggressionOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("ProposalWasRejected"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }
   }

   public static final void addNonAggressionPact(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            Game.getCiv(iCivA).diplomacy.addNonAggressionPact(iCivB, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_NON_AGGRESSION_PACT_EXPIRES);
            Game.getCiv(iCivB).diplomacy.addNonAggressionPact(iCivA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_NON_AGGRESSION_PACT_EXPIRES);
         }
      }
   }

   public static final void removeNonAggressionPact(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeNonAggressionPact(iCivB);
      Game.getCiv(iCivB).diplomacy.removeNonAggressionPact(iCivA);
   }

   public static final boolean offerMilitaryAccess(int iFromCivID, int iToCivID) {
      if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_OFFER_MILITARY_ACCESS_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iToCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_OFFER_MILITARY_ACCESS_COST;
         if (iToCivID == Game.player.iCivID) {
         }

         acceptOfferMilitaryAccess(iFromCivID, iToCivID);
         return true;
      }
   }

   public static final void acceptOfferMilitaryAccess(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivB;
         InGame_Info.iCivID2 = iCivA;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("MilitaryAccess"), Game.getCiv(iCivB).getCivName() + " - " + Game.getCiv(iCivA).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }

      addMilitaryAccess(iCivB, iCivA);
   }

   public static final boolean demandMilitaryAccess(int fromCivID, int toCivID) {
      if (Game.getCiv(fromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_DEMAND_MILITARY_ACCESS_COST) {
         return false;
      } else if (isAtWar(fromCivID, toCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(fromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_DEMAND_MILITARY_ACCESS_COST;
         if (toCivID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageDemandMilitaryAccess(fromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            return true;
         } else if (Game.getCiv(fromCivID).diplomacy.getRelation(toCivID)
            < Game.aiValuesDiplomacy.AI_MILITARY_ACCESS_DECLINE_IF_RELATIONS_BELOW
               + (Game.getCiv(toCivID).diplomacy.isRival(fromCivID) ? Game.aiValuesDiplomacy.AI_MILITARY_ACCESS_DECLINE_IF_RELATIONS_BELOW_EXTRA_RIVAL : 0)
               + (
                  Game.getCiv(fromCivID).getReligionID() != Game.getCiv(toCivID).getReligionID()
                     ? Game.aiValuesDiplomacy.AI_MILITARY_ACCESS_DECLINE_IF_RELATIONS_BELOW_EXTRA_DIFFERENT_RELIGION
                     : 0
               )
               + (
                  Game.getCiv(fromCivID).getIdeologyID() != Game.getCiv(toCivID).getIdeologyID()
                     ? Game.aiValuesDiplomacy.AI_MILITARY_ACCESS_DECLINE_IF_RELATIONS_BELOW_EXTRA_DIFFERENT_GOVERNMENT
                     : 0
               )) {
            if (fromCivID == Game.player.iCivID) {
               Game.player.addMessage(new PMessageMilitaryAccessRefused(toCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            }

            return false;
         } else {
            acceptDemandMilitaryAccess(fromCivID, toCivID);
            return true;
         }
      }
   }

   public static final void acceptDemandMilitaryAccess(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("MilitaryAccess"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }

      addMilitaryAccess(iCivA, iCivB);
   }

   public static final boolean addMilitaryAccess(int iCivA, int iCivB) {
      if (iCivA == iCivB) {
         return false;
      } else if (isAtWar(iCivA, iCivB)) {
         return false;
      } else {
         Game.getCiv(iCivA).diplomacy.addMilitaryAccess(iCivB, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MILITARY_ACCESS_EXPIRES);
         return true;
      }
   }

   public static final void removeMilitaryAccess(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeMilitaryAccess(iCivB);
   }

   public static final boolean offerGuarantee(int iFromCivID, int iToCivID) {
      if (Game.getCiv(iFromCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_GUARANTEE_COST) {
         return false;
      } else if (isAtWar(iFromCivID, iToCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iFromCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - GameValues.diplomacy.DIPLOMACY_GUARANTEE_COST;
         if (iToCivID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageGuarantee(iFromCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
            return true;
         } else {
            acceptGuaranteeOffer(iFromCivID, iToCivID);
            return true;
         }
      }
   }

   public static final void acceptGuaranteeOffer(int iCivA, int iCivB) {
      if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
         InGame_Info.iCivID = iCivA;
         InGame_Info.iCivID2 = iCivB;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("GuaranteeIndependence"), Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
      }

      addGuarantee(iCivA, iCivB);
   }

   public static final void addGuarantee(int iCivA, int iCivB) {
      if (iCivA != iCivB) {
         if (!isAtWar(iCivA, iCivB)) {
            Game.getCiv(iCivA).diplomacy.addGuarantee(iCivB, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_GUARANTEE_EXPIRES);
            Game.getCiv(iCivB).diplomacy.addGuaranteeByCivID(iCivA, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_GUARANTEE_EXPIRES);
         }
      }
   }

   public static final void removeGuarantee(int iCivA, int iCivB) {
      Game.getCiv(iCivA).diplomacy.removeGuarantee(iCivB);
      Game.getCiv(iCivB).diplomacy.removeGuaranteeByCivID(iCivA);
   }

   public static final boolean whitePeace(String warKey) {
      try {
         if (WarManager.lWars.containsKey(warKey)) {
            WarManager.lWars.get(warKey).peaceTreaty();
            WarManager.lWars.remove(warKey);
         }

         return true;
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return false;
      }
   }

   public static final boolean surrenderWar(String warKey, int civID) {
      try {
         if (WarManager.lWars.containsKey(warKey)
            && (WarManager.lWars.get(warKey).lAggressors.get(0).iCivID == civID || WarManager.lWars.get(warKey).lDefenders.get(0).iCivID == civID)) {
            WarManager.lWars.get(warKey).warScore = (WarManager.lWars.get(warKey).isAggressor(civID) ? -1 : 1)
               * GameValues.peace.WAR_MAKE_DEMANDS_MIN_WAR_SCORE
               * 2.0F;
            AI_PeaceTreaty.peaceTreaty(
               warKey,
               WarManager.lWars.get(warKey).isDefender(civID)
                  ? WarManager.lWars.get(warKey).lAggressors.get(0).iCivID
                  : WarManager.lWars.get(warKey).lDefenders.get(0).iCivID,
               civID
            );
         }

         return true;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return false;
      }
   }

   public static boolean respondToUnion(int civA, int toCivID) {
      if (isAtWar(civA, toCivID)) {
         return false;
      } else if (Game.player.playerStats4.unionTags.size() > GameValues.diplomacy.MAX_UNIONS) {
         return false;
      } else if (Game.getCiv(toCivID).diplomacy.getRelation(civA) < 0.0F) {
         return false;
      } else {
         return civA == Game.getCiv(toCivID).getPuppetOfCivID() ? true : getUnion_Score(civA, toCivID) >= 0;
      }
   }

   public static final int getUnion_Score(int civFrom, int civTo) {
      return Game.getCiv(civFrom).diplomacy.isAtWar()
         ? (int)GameValues.diplomacy.ALLIANCE_SCORE_AT_WAR
         : (int)(
            getAlliance_Score_BASE_VALUE(civFrom, civTo)
               + Game.getCiv(civTo).diplomacy.getRelation(civFrom) * GameValues.diplomacy.ALLIANCE_SCORE_PER_RELATION
               + GameValues.diplomacy.ALLIANCE_SCORE_DISTANCE
                  * Game.getDistance_PercOfMax(Game.getCiv(civFrom).getCapitalProvinceID(), Game.getCiv(civTo).getCapitalProvinceID())
               + (Game.getCiv(civTo).diplomacy.isRival(civFrom) ? GameValues.diplomacy.ALLIANCE_SCORE_RIVALS : 0.0F)
               + (Game.getCiv(civFrom).diplomacy.isRival(civTo) ? GameValues.diplomacy.ALLIANCE_SCORE_RIVALS : 0.0F)
               + getAlliance_Score_SameRivals(civFrom, civTo)
               + (Game.getCiv(civTo).getPuppetOfCivID() == civFrom ? GameValues.diplomacy.ALLIANCE_SCORE_FOR_VASSAL_OF_CIV : 0.0F)
               + Game.getCiv(civFrom).getAggressiveExpansion() * GameValues.diplomacy.ALLIANCE_SCORE_PER_AGGRESSIVE_EXPANSION
               + (
                  Game.getCiv(civFrom).getReligionID() != Game.getCiv(civTo).getReligionID()
                     ? (
                        Game.religionManager.getReligion(Game.getCiv(civFrom).getReligionID()).ReligionGroupID
                              != Game.religionManager.getReligion(Game.getCiv(civTo).getReligionID()).ReligionGroupID
                           ? GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION_GROUP
                           : GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION
                     )
                     : 0.0F
               )
               + (
                  Game.getCiv(civFrom).getIdeologyID() != Game.getCiv(civTo).getIdeologyID()
                     ? (
                        Game.ideologiesManager.getIdeology(Game.getCiv(civFrom).getIdeologyID()).GOV_GROUP_ID
                              != Game.ideologiesManager.getIdeology(Game.getCiv(civTo).getIdeologyID()).GOV_GROUP_ID
                           ? GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT_GROUP
                           : GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT
                     )
                     : 0.0F
               )
         );
   }
}
