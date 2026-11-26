package aoc.kingdoms.lukasz.menus.LoadSave;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.AI.Colonization.AI_Colonization;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadSavedGameManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.map.map.MapScale;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menus.InitGame;
import aoc.kingdoms.lukasz.menus.NewGame.NewGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Menu_LoadSavedGame extends Menu {
   public int iStepID = 0;
   public int iNumOfSteps = 250;
   public String loadingName = "";

   public Menu_LoadSavedGame() {
      this.iStepID = 0;
      List<MenuElement> menuElements = new ArrayList<>();
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.setLoadText(Game.lang.get("Loading") + " #1");
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.loadAction();
      if (InitGame.background != null) {
         oSB.setColor(new Color(0.047058824F, 0.047058824F, 0.047058824F, 1.0F));
         Images.pix.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, (float)this.iStepID / this.iNumOfSteps * 0.25F));
         InitGame.background
            .draw(
               oSB,
               iTranslateX + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2,
               iTranslateY + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.95F + (float)this.iStepID / this.iNumOfSteps * 0.05F));
         oSB.setShader(Renderer.shaderAlpha);
         InitGame.background.getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2 + iTranslateX,
               this.getPosY() + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2 + iTranslateY,
               InitGame.backgroundWidth,
               InitGame.backgroundHeight
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, iTranslateX, CFG.GAME_HEIGHT - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      Renderer.drawLoading(oSB, iTranslateX, iTranslateY, 0.12F + 0.88F * this.iStepID / this.iNumOfSteps);
      Renderer.drawTextWithShadow(
         oSB, CFG.FONT_REGULAR_SMALL, this.loadingName, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, new Color(1.0F, 1.0F, 1.0F, 0.15F)
      );
   }

   public void setLoadText(String sText) {
      this.loadingName = sText;
   }

   public final void loadAction() {
      try {
         this.setLoadText(Game.lang.get("Loading") + " #" + (this.iStepID + 2));
         switch (this.iStepID) {
            case 0:
               Game.mapScenarios.clearData();
               Menu_LoadSavingGame.SAVE_FILE_ID = 0;
               break;
            case 1:
               LoadSavedGameManager.loadSave_Details();
               break;
            case 2:
               LoadSavedGameManager.loadSave_Civs();
               break;
            case 3:
               Game.mapScenarios.loadScenario_3_A();
               break;
            case 4:
               Game.mapScenarios.loadScenario_3_B();
               break;
            case 5:
               LoadSavedGameManager.loadSave_Civs2();
               break;
            case 6:
               LoadSavedGameManager.loadSave_Civs3();
               break;
            case 7:
               LoadSavedGameManager.loadSave_Civs4();
               break;
            case 8:
               LoadSavedGameManager.loadSave_InitCivsData();
               break;
            case 9:
               LoadSavedGameManager.loadSave_CivsUnlockedTechnologies();
               break;
            case 10:
               LoadSavedGameManager.loadSave_CivsLaws();
               break;
            case 11:
               LoadSavedGameManager.loadSave_CivsUnlockedAdvantages();
               break;
            case 12:
               LoadSavedGameManager.loadSave_BuildTechTree();
               break;
            case 13:
               Game.initProvinceData();
            case 14:
            case 15:
            case 16:
            case 32:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 56:
            case 57:
            case 58:
            case 61:
            case 66:
            case 74:
            case 76:
            case 79:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 92:
            case 93:
            case 94:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 109:
            case 112:
            case 113:
            case 114:
            case 128:
            case 131:
            case 132:
            case 133:
            case 134:
            case 136:
            case 137:
            case 138:
            case 139:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 151:
            case 152:
            case 153:
            case 155:
            case 156:
            case 157:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 177:
            case 178:
            case 179:
            case 181:
            case 182:
            case 183:
            case 184:
            case 187:
            case 189:
            case 202:
            case 203:
            case 204:
            case 206:
            case 210:
            case 211:
            case 213:
            case 214:
            case 216:
            case 217:
            case 219:
            case 221:
            case 223:
            case 225:
            case 227:
            case 229:
            case 230:
            case 232:
            case 233:
            case 235:
            case 236:
            case 237:
            case 238:
            case 239:
            case 240:
            case 241:
            case 242:
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 248:
            default:
               break;
            case 17:
               LoadSavedGameManager.loadSave_ProvinceData();
               break;
            case 18:
               LoadSavedGameManager.loadSave_ProvinceData2();
               break;
            case 19:
               LoadSavedGameManager.loadSave_ProvinceData3();
               break;
            case 20:
               LoadSavedGameManager.loadSave_ProvinceData4();
               break;
            case 21:
               LoadSavedGameManager.loadSave_ProvinceData5();
               break;
            case 22:
               LoadSavedGameManager.loadSave_ProvinceData6();
               break;
            case 23:
               LoadSavedGameManager.loadSave_ProvinceData7();
               break;
            case 24:
               LoadSavedGameManager.loadSave_ProvinceData8();
               break;
            case 25:
               LoadSavedGameManager.loadSave_ProvinceData9();
               break;
            case 26:
               LoadSavedGameManager.loadSave_ProvinceData10();
               break;
            case 27:
               LoadSavedGameManager.buildProvinceCores();
               break;
            case 28:
               SiegeManager.buildProvinceUnderSiege_Load();
               break;
            case 29:
               Game.buildProvinceIsCapital();
               break;
            case 30:
               Game.mapScenarios.loadScenario_7();
               break;
            case 31:
               Game.buildWastelandLevels();
               break;
            case 33:
               LoadSavedGameManager.loadSave_Population();
               break;
            case 34:
               LoadSavedGameManager.buildProvincePopulationData();
               break;
            case 35:
               Game.addSimpleTask(new Game.SimpleTask("buildCivilizationsRegions") {
                  @Override
                  public void update() {
                     CivilizationRegionsManager.buildCivilizationsRegions();
                  }
               });
               break;
            case 42:
               LoadSavedGameManager.loadSave_CivsResearchProgress();
               break;
            case 43:
               LoadSavedGameManager.loadSave_CivsLegacies();
               break;
            case 44:
               LoadSavedGameManager.loadSave_CivsLoans();
               break;
            case 45:
               Menu_LoadSavingGame.SAVE_FILE_ID = 0;
               break;
            case 46:
               if (LoadSavedGameManager.loadSave_ProvincesBuildings(Menu_LoadSavingGame.SAVE_FILE_ID++)) {
                  return;
               }
               break;
            case 47:
               Menu_LoadSavingGame.SAVE_FILE_ID = 0;
               LoadSavedGameManager.loadSave_ProvinceConstructionBuilding();
               break;
            case 48:
               LoadSavedGameManager.loadSave_ProvinceInvestDaysLeft();
               break;
            case 49:
               LoadSavedGameManager.loadSave_ProvinceInvestTax();
               break;
            case 50:
               LoadSavedGameManager.loadSave_ProvinceInvestManpower();
               break;
            case 51:
               LoadSavedGameManager.loadSave_ProvinceInvestGrowthRate();
               break;
            case 52:
               LoadSavedGameManager.loadSave_ProvinceInvestInfrastructure();
               break;
            case 53:
               LoadSavedGameManager.loadSave_ProvinceCoreCreation();
               break;
            case 54:
               LoadSavedGameManager.loadSave_ProvinceReligionConversion();
               break;
            case 55:
               LoadSavedGameManager.loadSave_ProvinceWonderConstruction();
               break;
            case 59:
               LoadSavedGameManager.loadSave_CivsTemporaryBonuses();
               break;
            case 60:
               LoadSavedGameManager.loadSave_CivsGoldenAges();
               break;
            case 62:
               LoadSavedGameManager.loadSave_CivsRulers();
               break;
            case 63:
               LoadSavedGameManager.loadSave_CivsRulers_Bonuses();
               break;
            case 64:
               LoadSavedGameManager.loadSave_CivsAdvisorsAdmBonuses();
               break;
            case 65:
               LoadSavedGameManager.loadSave_CivsAdvisorsAdm();
               break;
            case 67:
               LoadSavedGameManager.loadSave_CivsAdvisorsEconomyBonuses();
               break;
            case 68:
               LoadSavedGameManager.loadSave_CivsAdvisorsEconomy();
               break;
            case 69:
               LoadSavedGameManager.loadSave_CivsAdvisorsInnovationBonuses();
               break;
            case 70:
               LoadSavedGameManager.loadSave_CivsAdvisorsInnovation();
               break;
            case 71:
               LoadSavedGameManager.loadSave_CivsAdvisorsMilitaryBonuses();
               break;
            case 72:
               LoadSavedGameManager.loadSave_CivsAdvisorsMilitary();
               break;
            case 73:
               LoadSavedGameManager.loadSave_CivsGeneralsNotAssigned();
               break;
            case 75:
               if (LoadSavedGameManager.loadSave_ProvincesArmy_MoreFiles(Menu_LoadSavingGame.SAVE_FILE_ID++)) {
                  return;
               }
               break;
            case 77:
               LoadSavedGameManager.loadSave_CivsRecruitArmyCreate();
               break;
            case 78:
               LoadSavedGameManager.loadSave_CivsRecruitArmy();
               break;
            case 80:
               LoadSavedGameManager.loadSave_MapPlagues();
               break;
            case 81:
               LoadSavedGameManager.loadSave_ProvincesPlagues();
               break;
            case 90:
               LoadSavedGameManager.loadSave_CivsEventsVariables();
               break;
            case 91:
               LoadSavedGameManager.loadSave_CivsEventsVariables2();
               break;
            case 95:
               LoadSavedGameManager.loadSave_CivsEventsData();
               break;
            case 96:
               LoadSavedGameManager.loadSave_CivsEventsData2();
               break;
            case 97:
               LoadSavedGameManager.loadSave_CivsEventsData3();
               break;
            case 105:
               LoadSavedGameManager.loadSaveWars();
               break;
            case 106:
               LoadSavedGameManager.loadSaveWars_BuildData();
               break;
            case 110:
               LoadSavedGameManager.loadSaveRelations();
               break;
            case 111:
               LoadSavedGameManager.loadSaveRelations2();
               break;
            case 115:
               LoadSavedGameManager.loadSaveAlliances();
               break;
            case 116:
               LoadSavedGameManager.loadSaveDefensive();
               break;
            case 117:
               LoadSavedGameManager.loadSaveTruce();
               break;
            case 118:
               LoadSavedGameManager.loadSaveNonAggression();
               break;
            case 119:
               LoadSavedGameManager.loadSaveMilitaryAccess();
               break;
            case 120:
               LoadSavedGameManager.loadSaveGuarantee();
               break;
            case 121:
               LoadSavedGameManager.loadSaveRivals();
               break;
            case 122:
               LoadSavedGameManager.loadSaveRelationsImprove();
               break;
            case 123:
               LoadSavedGameManager.loadSaveRelationsDamage();
               break;
            case 124:
               LoadSavedGameManager.loadSave_AllianceSpecial();
               break;
            case 125:
               Game.loadAlliancesSpecial_Images();
               break;
            case 126:
               MapScenarios.buildAlliancesSpecial();
               break;
            case 127:
               LoadSavedGameManager.loadSave_CivsNukesProduction();
               break;
            case 129:
               MapScenarios.buildCivData_Load();
               break;
            case 130:
               MapScenarios.buildVassals();
               break;
            case 135:
               LoadSavedGameManager.loadSave_Vassals();
               break;
            case 140:
               LoadSavedGameManager.loadSave_UpdatePlayersCivID();
               break;
            case 141:
               LoadSavedGameManager.loadPlayer_Data();
               break;
            case 142:
               LoadSavedGameManager.loadPlayer_Stats();
               break;
            case 143:
               LoadSavedGameManager.loadPlayer_Stats2();
               break;
            case 144:
               LoadSavedGameManager.loadPlayer_Stats3();
               break;
            case 150:
               Game.mapScenarios.loadScenario_60();
               break;
            case 154:
               Game.mapScenarios.loadScenario_62(false);
               break;
            case 158:
               Game.mapScenarios.loadScenario_64();
               break;
            case 175:
               LoadSavedGameManager.loadSave_MapBattles();
               break;
            case 176:
               LoadSavedGameManager.loadSave_MapBattles_Update();
               break;
            case 180:
               LoadSavedGameManager.loadSave_CivsMoveUnits();
               break;
            case 185:
               ResourcesManager.updateWorldResourcesProduced(true);
               break;
            case 186:
               ResourcesManager.initUniqueCivsGoods();
               Game.buildDistanceToCapital();
               break;
            case 188:
               Game.mapScenarios.buildCivsGovernmentBuildings_LoadSavedGame();
               break;
            case 190:
               MapScenarios.buildManpower();
               break;
            case 191:
               CivilizationRanking.buildCivilizationRanking();
               break;
            case 192:
               Game.mapCities.updateCities();
               break;
            case 193:
               Game.mapScenarios.loadScenario_48();
               break;
            case 194:
               MapScenarios.buildArmyPosition();
               break;
            case 195:
               MapScenarios.buildCivsStability();
               break;
            case 196:
               MapScenarios.buildIncome();
               break;
            case 197:
               LoadSavedGameManager.loadSave_UpdateDiplomacyPerMonth();
               break;
            case 198:
               LoadSavedGameManager.buildProvinceData();
               break;
            case 199:
               LoadSavedGameManager.loadSave_InitCivsData_CoresReligionGenerals();
               break;
            case 200:
               Game.mapScenarios.loadScenario_56();
               break;
            case 201:
               Game.mapScenarios.loadScenario_57();
               break;
            case 205:
               Game.mapScenarios.loadScenario_58();
               break;
            case 207:
               Game.mapScenarios.loadScenario_59();
               break;
            case 208:
               MapScenarios.buildCivData_Load2();
               break;
            case 209:
               LoadSavedGameManager.loadSave_Rebels();
               break;
            case 212:
               LoadSavedGameManager.loadSave_RebelsMoveUnits();
               break;
            case 215:
               LoadSavedGameManager.loadSave_AI_Merge();
               break;
            case 218:
               LoadSavedGameManager.loadSave_AI_CreateNewArmy();
               break;
            case 220:
               LoadSavedGameManager.loadSave_AI_Diplomacy();
               break;
            case 222:
               LoadSavedGameManager.loadBuildColonization();
               break;
            case 224:
               LoadSavedGameManager.loadBuild_ProvincesUnderSiege();
               break;
            case 226:
               LoadSavedGameManager.loadBuild_ProvincesOccupied();
               break;
            case 228:
               AI_Colonization.initData();
               break;
            case 231:
               LoadSavedGameManager.loadSave_AI_Budget();
               break;
            case 234:
               Game.stats.loadStats(Game.getCiv(Game.player.iCivID).getCivTag(), false);
               break;
            case 249:
               NewGame.initNewGame();
               break;
            case 250:
               Game.mapScale.definedScale = MapScale.defScales.definedScale_Default;
               Game.mapScale.setCurrentScale(1.0F);
               Game.mapBG.requestToDisposeMinimap = true;
               Game.menuManager.setViewIDWithoutAnimation(View.IN_GAME);
               Game.addSimpleTask(new Game.SimpleTask("LoadGame_RebuildMenus") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Messages();
                     Game.menuManager.rebuildInGame_Wars();
                     Game.menuManager.rebuildInGame_Right();
                     Game.menuManager.setOrderOfMenu_InGame();
                     ProvinceDraw.updateDrawExtraDetails();
                  }
               });
         }
      } catch (Exception var2) {
         CFG.LOG(Game.lang.get("Loading") + ": " + this.iStepID);
         CFG.exceptionStack(var2);
      }

      this.iStepID++;
   }
}
