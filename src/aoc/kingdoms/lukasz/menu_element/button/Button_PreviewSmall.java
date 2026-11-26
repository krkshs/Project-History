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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ScenarioPreview;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menus.LoadSave.Menu_LoadScenario;
import aoc.kingdoms.lukasz.menus.NewGame.NewGame;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_PreviewSmall extends Button {
   public String sText2;
   public int iTextBonusW;
   public int maxIconWidth;
   public int scenarioID;
   public static int previewH;
   public static int previewW;

   public Button_PreviewSmall(String sText, String sText2, int iPosX, int iPosY, int nWidth, int nHeight, int scenarioID) {
      previewH = CFG.TEXT_HEIGHT * 2 + CFG.PADDING * 3;
      previewW = (int)(Game.mapBG.scenarioOver.getWidth() * ((float)previewH / Game.mapBG.scenarioOver.getHeight()));
      this.maxIconWidth = previewW + CFG.PADDING * 2;
      this.init(sText, CFG.FONT_BOLD, 0, iPosX, iPosY, nWidth, Math.max(previewH + CFG.PADDING * 5, nHeight), true, true, false, false);
      this.scenarioID = scenarioID;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sText2);
      this.iTextBonusW = (int)Renderer.glyphLayout.width;
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - this.maxIconWidth - CFG.PADDING * 2 && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.35F : 0.65F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth, this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth, this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.maxIconWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.55F)) : 0.4F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);

      try {
         Game.mapScenarios.lScenarios_Preview.get(this.scenarioID).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         Game.mapBG
            .scenarioMask
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - previewH / 2 + iTranslateY,
               previewW,
               previewH,
               false,
               false
            );
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      Game.mapBG
         .scenarioOver
         .draw(oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() / 2 - previewH / 2 + iTranslateY, previewW, previewH);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.maxIconWidth + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - CFG.PADDING - this.iTextHeight + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText2,
         this.getPosX() + this.maxIconWidth + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getHeight() / 2 + CFG.PADDING + iTranslateY,
         this.getColorBonus()
      );
   }

   public Color getColorBonus() {
      return Colors.HOVER_RIGHT3;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.scenarioID == Game.scenarioID ? Colors.HOVER_GOLD : super.getColor(isActive);
   }

   public final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_ScenarioPreview(this.scenarioID));
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
   public void setCurrent(int nCurrent) {
      this.scenarioID = nCurrent;
   }

   @Override
   public int getCurrent() {
      return this.scenarioID;
   }
}
