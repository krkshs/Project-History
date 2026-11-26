package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.map.MapScale;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menus.LoadSave.Menu_LoadScenario;
import aoc.kingdoms.lukasz.menus.NewGame.NewGame;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_Preview extends Button {
   public int scenarioID;
   public String sYear;

   public Button_Preview(int iPosX, int iPosY, int scenarioID) {
      this.scenarioID = scenarioID;
      if (Game.mapScenarios.details.get(scenarioID).Name.indexOf("" + Game.mapScenarios.details.get(scenarioID).Year) >= 0) {
         this.sYear = "";
      } else {
         this.sYear = Game.gameAges.getYear(Game.mapScenarios.details.get(scenarioID).Year);
      }

      this.init(
         Game.lang.get(Game.mapScenarios.details.get(scenarioID).Name),
         CFG.FONT_REGULAR,
         -1,
         iPosX,
         iPosY,
         Game.mapBG.scenarioOver.getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
   }

   public int getPaddingHover() {
      return CFG.PADDING * 2;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);
      if (this.getIsHovered()) {
         try {
            Game.mapScenarios.lScenarios_Preview.get(this.scenarioID).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            Game.mapBG
               .scenarioMask
               .draw(
                  oSB,
                  this.getPosX() - this.getPaddingHover() + iTranslateX,
                  this.getPosY() - this.getPaddingHover() + iTranslateY,
                  Game.mapBG.scenarioMask.getWidth() + this.getPaddingHover() * 2,
                  Game.mapBG.scenarioMask.getHeight() + this.getPaddingHover() * 2,
                  false,
                  false
               );
         } catch (Exception var7) {
            CFG.exceptionStack(var7);
         }

         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.35F));
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getPaddingHover() * 2 + this.getHeight() - CFG.PADDING * 6 - CFG.TEXT_HEIGHT + iTranslateY,
               this.getWidth(),
               CFG.PADDING * 6 + CFG.TEXT_HEIGHT
            );
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - this.getPaddingHover() + iTranslateY,
               this.getWidth(),
               CFG.PADDING * 6 + CFG.TEXT_HEIGHT,
               false,
               true
            );
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.25F));
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING * 6 - CFG.TEXT_HEIGHT - 2 + iTranslateY,
               this.getWidth(),
               CFG.PADDING * 6 + CFG.TEXT_HEIGHT + this.getPaddingHover()
            );
         oSB.setColor(Color.WHITE);
         Game.mapBG
            .scenarioOver
            .draw(
               oSB,
               this.getPosX() - this.getPaddingHover() + iTranslateX,
               this.getPosY() - this.getPaddingHover() + iTranslateY,
               Game.mapBG.scenarioOver.getWidth() + this.getPaddingHover() * 2,
               Game.mapBG.scenarioOver.getHeight() + this.getPaddingHover() * 2
            );
         Renderer.drawText(oSB, CFG.FONT_REGULAR_SMALL, this.sYear, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, Colors.HOVER_RIGHT);
         Renderer.drawText(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY() + this.getHeight() + this.getPaddingHover() - CFG.PADDING * 2 - this.iTextHeight + iTranslateY,
            this.getColor(isActive)
         );
      } else {
         try {
            Game.mapScenarios.lScenarios_Preview.get(this.scenarioID).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            Game.mapBG
               .scenarioMask
               .draw(
                  oSB,
                  this.getPosX() + iTranslateX,
                  this.getPosY() + iTranslateY,
                  Game.mapBG.scenarioMask.getWidth(),
                  Game.mapBG.scenarioMask.getHeight(),
                  false,
                  false
               );
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }

         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING * 6 - CFG.TEXT_HEIGHT + iTranslateY,
               this.getWidth(),
               CFG.PADDING * 6 + CFG.TEXT_HEIGHT
            );
         Images.gradientXY
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 6 + CFG.TEXT_HEIGHT, false, true);
         oSB.setColor(Color.WHITE);
         Game.mapBG.scenarioOver.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         Renderer.drawText(
            oSB,
            CFG.FONT_REGULAR_SMALL,
            this.sYear,
            this.getPosX() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + CFG.PADDING * 2 + iTranslateY,
            Colors.HOVER_RIGHT
         );
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING * 2 - this.iTextHeight + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.scenarioID == Game.scenarioID ? Colors.HOVER_GOLD : Colors.getColorTopStats3(this.getIsHovered(), isActive);
   }

   public static int getButtonWidth() {
      return Game.mapBG.scenarioMask.getHeight();
   }

   public static int getButtonHeight() {
      return Game.mapBG.scenarioMask.getHeight();
   }

   @Override
   public void actionElement() {
      if (Game.scenarioID == this.scenarioID && Game_Calendar.TURN_ID == 1 && !Game.reloadScenario && !MainMenu.canContinue) {
         Game.mapScale.definedScale = MapScale.defScales.definedScale_Default;
         Game.mapScale.setCurrentScale(1.0F);
         NewGame.setRandomCiv();
         Game.menuManager.setViewIDWithoutAnimation(View.CLOUDS_MENU);
      } else {
         Game.scenarioID = this.scenarioID;
         Game.reloadScenario = false;
         Menu_LoadScenario.editorMode = false;
         Menu_LoadScenario.goToMenu = View.CLOUDS_MENU;
         Game.menuManager.setViewIDWithoutAnimation(View.LOAD_SCENARIO);
      }
   }

   @Override
   public int getCurrent() {
      return this.scenarioID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            "",
            ""
               + Game.mapScenarios.details.get(this.scenarioID).Day
               + " "
               + Game_Calendar.getMonthName(Game.mapScenarios.details.get(this.scenarioID).Month)
               + " "
               + Game.gameAges.getYear(Game.mapScenarios.details.get(this.scenarioID).Year),
            Images.time,
            CFG.FONT_BOLD_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Colors.HOVER_LEFT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            "",
            "" + Game.gameAges.lAges.get(Game.gameAges.getAgeOfYear(Game.mapScenarios.details.get(this.scenarioID).Year)).Name,
            Images.time,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_LEFT,
            Colors.HOVER_LEFT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilizations") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            CFG.getNumberWithSpaces("" + Game.mapScenarios.details.get(this.scenarioID).Civs), CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Author") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(new MenuElement_HoverElement_Type_Text(Game.mapScenarios.details.get(this.scenarioID).Author, CFG.FONT_BOLD_SMALL, Colors.HOVER_RIGHT));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
