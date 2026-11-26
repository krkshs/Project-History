package aoc.kingdoms.lukasz.menus.LoadSave;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menus.InitGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Menu_LoadSavingGame extends Menu {
   public int iStepID = 0;
   public int iNumOfSteps = 200;
   public static int SAVE_FILE_ID = 0;
   public static View goToMenu = View.IN_GAME;
   public String loadingName = "";

   public Menu_LoadSavingGame() {
      this.iStepID = 0;
      List<MenuElement> menuElements = new ArrayList<>();
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.setLoadText(Game.lang.get("Saving") + " #1");
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
         this.setLoadText(Game.lang.get("Saving") + " #" + (this.iStepID + 2));
         switch (this.iStepID) {
            case 0:
               SAVE_FILE_ID = 0;
               SaveGameManager.Save_1();
            case 1:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 45:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 60:
            case 62:
            case 64:
            case 66:
            case 67:
            case 68:
            case 70:
            case 71:
            case 72:
            case 74:
            case 75:
            case 77:
            case 78:
            case 80:
            case 81:
            case 83:
            case 84:
            case 86:
            case 87:
            case 89:
            case 90:
            case 92:
            case 94:
            case 96:
            case 98:
            case 99:
            case 100:
            case 102:
            case 104:
            case 105:
            case 107:
            case 108:
            case 110:
            case 113:
            case 114:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130:
            case 131:
            case 133:
            case 135:
            case 137:
            case 139:
            case 140:
            case 142:
            case 144:
            case 146:
            case 148:
            case 150:
            case 151:
            case 153:
            case 154:
            case 156:
            case 157:
            case 159:
            case 161:
            case 163:
            case 166:
            case 167:
            case 169:
            case 171:
            case 173:
            case 174:
            case 176:
            case 177:
            case 179:
            case 180:
            case 182:
            case 184:
            case 185:
            case 187:
            case 189:
            case 190:
            case 192:
            case 194:
            case 196:
            case 197:
            case 199:
            case 200:
            case 201:
            case 203:
            case 204:
            case 206:
            case 207:
            case 209:
            case 210:
            case 212:
            case 214:
            case 216:
            case 217:
            case 219:
            case 220:
            case 222:
            default:
               break;
            case 2:
               SaveGameManager.Save_Provinces_Data();
               break;
            case 5:
               SaveGameManager.Save_Provinces_Data2();
               break;
            case 9:
               SaveGameManager.Save_Provinces_Data3();
               break;
            case 12:
               SaveGameManager.Save_Provinces_Data4();
               break;
            case 16:
               SaveGameManager.Save_Provinces_Data5();
               break;
            case 20:
               SaveGameManager.Save_Provinces_Data6();
               break;
            case 24:
               SaveGameManager.Save_Provinces_Data7();
               break;
            case 28:
               SaveGameManager.Save_Provinces_Data8();
               break;
            case 32:
               SaveGameManager.Save_Provinces_Data9();
               break;
            case 35:
               SaveGameManager.Save_Provinces_Data_Population();
               break;
            case 40:
               SaveGameManager.Save_Provinces_Data_ConstructionBuilding();
               break;
            case 42:
               SAVE_FILE_ID = 0;
               break;
            case 43:
               if (SAVE_FILE_ID * GameValues.value.SAVE_PROVINCES_BUILDINGS_PER_FILE < Game.getProvincesSize()) {
                  SaveGameManager.Save_Provinces_Data_Buildings(SAVE_FILE_ID++);
                  return;
               }
               break;
            case 44:
               SAVE_FILE_ID = 0;
               break;
            case 46:
               SaveGameManager.saveGuarantee();
               break;
            case 48:
               SaveGameManager.saveAlliances();
               break;
            case 50:
               SaveGameManager.saveRivals();
               break;
            case 53:
               SaveGameManager.saveRelationsImprove();
               break;
            case 55:
               SaveGameManager.saveRelationsDamage();
               break;
            case 57:
               SaveGameManager.saveDefensive();
               break;
            case 59:
               SaveGameManager.saveMilitaryAccess();
               break;
            case 61:
               SaveGameManager.saveNonAggression();
               break;
            case 63:
               SaveGameManager.saveTruces();
               break;
            case 65:
               SaveGameManager.saveRelations();
               break;
            case 69:
               SaveGameManager.saveRelations2();
               break;
            case 73:
               SaveGameManager.saveWars();
               break;
            case 76:
               SaveGameManager.Save_Provinces_Data_InvestDaysLeft();
               break;
            case 79:
               SaveGameManager.Save_Provinces_Data_InvestTax();
               break;
            case 82:
               SaveGameManager.Save_Provinces_Data_InvestManpower();
               break;
            case 85:
               SaveGameManager.Save_Provinces_Data_InvestGrowthRate();
               break;
            case 88:
               SaveGameManager.Save_Provinces_Data_Infrastructure();
               break;
            case 91:
               SaveGameManager.Save_Provinces_Data_CoreConstruction();
               break;
            case 93:
               SaveGameManager.Save_Provinces_Data_ReligionConversion();
               break;
            case 95:
               SaveGameManager.Save_Provinces_Data_WondersConstruction();
               break;
            case 97:
               SaveGameManager.Save_Civ_Data_UnlockedTechnologies();
               break;
            case 101:
               SaveGameManager.Save_Civ_Data_NukeProduction();
               break;
            case 103:
               SaveGameManager.Save_Civ_Data_ResearchProgress();
               break;
            case 106:
               SaveGameManager.Save_Civ_Data_UnlockedAdvantages();
               break;
            case 109:
               SaveGameManager.Save_Civ_Data_Laws();
               break;
            case 111:
               SaveGameManager.Save_Civ_Data_RecruitingArmy();
               break;
            case 112:
               SaveGameManager.Save_Civ_Data_RecruitArmyCreate();
               break;
            case 115:
               if (SAVE_FILE_ID * GameValues.value.SAVE_PROVINCES_ARMIES_PER_FILE < Game.getProvincesSize()) {
                  SaveGameManager.Save_Provinces_Data_Armies_MoreFiles(SAVE_FILE_ID++);
                  return;
               }
               break;
            case 121:
               SaveGameManager.Save_Map_Data_Plagues();
               break;
            case 123:
               SaveGameManager.Save_Provinces_Data10();
               break;
            case 125:
               SaveGameManager.Save_Provinces_Data_Plagues();
               break;
            case 127:
               SaveGameManager.Save_Civs_Data_Rulers();
               break;
            case 129:
               SaveGameManager.Save_Civs_Data_RulersBonuses();
               break;
            case 132:
               SaveGameManager.Save_Civs_Data_AdvisorsAdm();
               break;
            case 134:
               SaveGameManager.Save_Civs_Data_AdvisorsAdmBonuses();
               break;
            case 136:
               SaveGameManager.Save_Civs_Data_AdvisorsEconomy();
               break;
            case 138:
               SaveGameManager.Save_Civs_Data_AdvisorsEconomyBonuses();
               break;
            case 141:
               SaveGameManager.Save_Civs_Data_AdvisorsInnovation();
               break;
            case 143:
               SaveGameManager.Save_Civs_Data_AdvisorsInnovationBonuses();
               break;
            case 145:
               SaveGameManager.Save_Civs_Data_AdvisorsMilitary();
               break;
            case 147:
               SaveGameManager.Save_Civs_Data_AdvisorsMilitaryBonuses();
               break;
            case 149:
               SaveGameManager.Save_Civs_Data_GeneralsNotAssigned();
               break;
            case 152:
               SaveGameManager.Save_Civs_Data_TemporaryBonuses();
               break;
            case 155:
               SaveGameManager.Save_Civs_Data_EventsVariables();
               break;
            case 158:
               SaveGameManager.Save_Civs_Data_EventsVariables2();
               break;
            case 160:
               SaveGameManager.Save_Civs_Data_GoldenAges();
               break;
            case 162:
               SaveGameManager.Save_Civs_Data_Loans();
               break;
            case 164:
               SaveGameManager.Save_Civs_Data_Legacies();
               break;
            case 165:
               SaveGameManager.Save_Civs_Data_MoveUnits();
               break;
            case 168:
               SaveGameManager.Save_Civs_Data_EventsData();
               break;
            case 170:
               SaveGameManager.Save_Civs_Data_EventsData2();
               break;
            case 172:
               SaveGameManager.Save_Civs_Data_EventsData3();
               break;
            case 175:
               SaveGameManager.Save_Civs_Data();
               break;
            case 178:
               SaveGameManager.Save_Civs_Data2();
               break;
            case 181:
               SaveGameManager.Save_Civs_Data3();
               break;
            case 183:
               SaveGameManager.Save_Civs_Data4();
               break;
            case 186:
               SaveGameManager.Save_Player_Data();
               break;
            case 188:
               SaveGameManager.Save_Player_Stats();
               break;
            case 191:
               SaveGameManager.Save_Player_Stats2();
               break;
            case 193:
               SaveGameManager.Save_Player_Stats3();
               break;
            case 195:
               SaveGameManager.Save_Map_AlliancesSpecial();
               break;
            case 198:
               SaveGameManager.Save_Map_Data_Battles();
               break;
            case 202:
               SaveGameManager.Save_Rebels_Data_MoveUnits();
               break;
            case 205:
               SaveGameManager.Save_Rebels_Data();
               break;
            case 208:
               SaveGameManager.Save_Civs_Data_AI_Merge();
               break;
            case 211:
               SaveGameManager.Save_Civs_Data_AI_CreateNewArmy();
               break;
            case 213:
               SaveGameManager.Save_Civs_Data_AI_Diplomacy();
               break;
            case 215:
               SaveGameManager.Save_Civs_Data_AI_Budget();
               break;
            case 218:
               Game.stats.saveStats();
               break;
            case 221:
               SaveGameManager.Save_Provinces_Data_Vassals();
               break;
            case 223:
               Game.menuManager.setViewIDWithoutAnimation(goToMenu);
         }
      } catch (Exception var2) {
         CFG.LOG(Game.lang.get("Saving") + ": " + this.iStepID);
         CFG.exceptionStack(var2);
      }

      this.iStepID++;
   }
}
