package aoc.kingdoms.lukasz.menus.LoadSave;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
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

public class Menu_LoadSaveScenario extends Menu {
   public int iStepID = 0;
   public int iNumOfSteps = 26;
   public static View goToMenu = View.NEW_GAME;
   public String loadingName = "";

   public Menu_LoadSaveScenario() {
      this.iStepID = 0;
      List<MenuElement> menuElements = new ArrayList<>();
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.setLoadText("Saving Scenario #1");
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
         if (this.iStepID == 0) {
            Game.mapScenarios.saveScenario_1();
            this.setLoadText("Saving Scenario #2");
         } else if (this.iStepID == 1) {
            Game.mapScenarios.saveScenario_2();
            this.setLoadText("Saving Scenario #3");
         } else if (this.iStepID == 2) {
            Game.mapScenarios.saveScenario_3();
            this.setLoadText("Saving Scenario #4");
         } else if (this.iStepID == 3) {
            Game.mapScenarios.saveScenario_4();
            this.setLoadText("Saving Scenario #5");
         } else if (this.iStepID == 4) {
            Game.mapScenarios.saveScenario_5();
            this.setLoadText("Saving Scenario #6");
         } else if (this.iStepID == 5) {
            Game.mapScenarios.saveScenario_6();
            this.setLoadText("Saving Scenario #7");
         } else if (this.iStepID == 6) {
            Game.mapScenarios.saveScenario_7();
            this.setLoadText("Saving Scenario #8");
         } else if (this.iStepID == 7) {
            Game.mapScenarios.saveScenario_8();
            this.setLoadText("Saving Scenario #9");
         } else if (this.iStepID == 8) {
            Game.mapScenarios.saveScenario_9();
            this.setLoadText("Saving Scenario #10");
         } else if (this.iStepID == 9) {
            Game.mapScenarios.saveScenario_10();
            this.setLoadText("Saving Scenario #11");
         } else if (this.iStepID == 10) {
            Game.mapScenarios.saveScenario_11();
            this.setLoadText("Saving Scenario #12");
         } else if (this.iStepID == 11) {
            Game.mapScenarios.saveScenario_12();
            this.setLoadText("Saving Scenario #13");
         } else if (this.iStepID == 12) {
            Game.mapScenarios.saveScenario_13();
            this.setLoadText("Saving Scenario #14");
         } else if (this.iStepID == 13) {
            Game.mapScenarios.saveScenario_14();
            this.setLoadText("Saving Scenario #15");
         } else if (this.iStepID == 14) {
            Game.mapScenarios.saveScenario_15();
            this.setLoadText("Saving Scenario #16");
         } else if (this.iStepID == 15) {
            Game.mapScenarios.saveScenario_16();
            this.setLoadText("Saving Scenario #17");
         } else if (this.iStepID == 16) {
            Game.mapScenarios.saveScenario_17();
            this.setLoadText("Saving Scenario #18");
         } else if (this.iStepID == 17) {
            Game.mapScenarios.saveScenario_18();
            this.setLoadText("Saving Scenario #19");
         } else if (this.iStepID == 18) {
            Game.mapScenarios.saveScenario_19();
            this.setLoadText("Saving Scenario #20");
         } else if (this.iStepID == 19) {
            Game.mapScenarios.saveScenario_20();
            this.setLoadText("Saving Scenario #21");
         } else if (this.iStepID == 20) {
            Game.mapScenarios.saveScenario_21();
            this.setLoadText("Saving Scenario #22");
         } else if (this.iStepID == 21) {
            Game.mapScenarios.saveScenario_22();
            this.setLoadText("Saving Scenario #23");
         } else if (this.iStepID == 22) {
            Game.mapScenarios.saveScenario_23();
            this.setLoadText("Saving Scenario #24");
         } else if (this.iStepID == 23) {
            Game.mapScenarios.saveScenario_24();
            this.setLoadText("Saving Scenario #25");
         } else if (this.iStepID == 24) {
            Game.mapScenarios.saveScenario_25();
            this.setLoadText("Saving Scenario #26");
         } else if (this.iStepID == 25) {
            this.setLoadText("Saving Scenario #27");
         } else if (this.iStepID == 26) {
            Game.menuManager.setViewIDWithoutAnimation(goToMenu);
         }
      } catch (Exception var2) {
         CFG.LOG("Saving Scenario: " + this.iStepID);
         CFG.exceptionStack(var2);
      }

      this.iStepID++;
   }
}
