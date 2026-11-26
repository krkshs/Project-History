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

public class Menu_Load_Workshop extends Menu {
   public int iStepID = 0;
   public int iNumOfSteps = 3600;
   public static View goToMenu = View.WORKSHOP;
   public static boolean uploaded = false;
   public String loadingName = "";

   public Menu_Load_Workshop() {
      this.iStepID = 0;
      List<MenuElement> menuElements = new ArrayList<>();
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.setLoadText(Game.lang.get("Progress") + " #0");
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
      Renderer.drawLoading(oSB, iTranslateX, iTranslateY, (float)(this.iStepID % 3600) / this.iNumOfSteps);
      Renderer.drawTextWithShadow(
         oSB, CFG.FONT_REGULAR_SMALL, this.loadingName, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, new Color(1.0F, 1.0F, 1.0F, 0.15F)
      );
   }

   public void setLoadText(String sText) {
      this.loadingName = sText;
   }

   public final void loadAction() {
      try {
         this.setLoadText(Game.lang.get("Progress") + " #" + this.iStepID);
         if (uploaded) {
            Game.menuManager.setViewIDWithoutAnimation(goToMenu);
         }
      } catch (Exception var2) {
         CFG.LOG(Game.lang.get("Workshop") + ": " + this.iStepID);
         CFG.exceptionStack(var2);
      }

      this.iStepID++;
   }
}
