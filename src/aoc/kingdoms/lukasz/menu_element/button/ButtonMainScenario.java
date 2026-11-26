package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ScenarioPreview;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonMainScenario extends ButtonMain {
   private int iScenarioID;
   private String sDate;
   private String sDesc;

   public ButtonMainScenario(
      int iScenarioID, String sText, String sDate, String sDesc, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
      this.iScenarioID = iScenarioID;
      this.sDate = sDate;
      this.sDesc = sDesc;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.fontMain.get(this.fontID).getData().setScale(0.9F);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + this.getTextPos() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - (int)(CFG.TEXT_HEIGHT * 0.9F / 2.0F) + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.fontMain.get(this.fontID).getData().setScale(0.7F);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sDate,
         this.getPosX() + this.getTextPos() + (int)(this.getTextWidth() * 0.9F) + CFG.PADDING * 4 + iTranslateX,
         this.getPosY()
            + this.getHeight() / 2
            - (int)(CFG.TEXT_HEIGHT + CFG.PADDING + CFG.TEXT_HEIGHT * 0.7F) / 2
            + (int)(CFG.TEXT_HEIGHT - CFG.TEXT_HEIGHT * 0.7F)
            + iTranslateY,
         new Color(0.67F, 0.67F, 0.67F, 1.0F)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sDesc,
         this.getPosX() + this.getTextPos() + (int)(this.getTextWidth() * 0.9F) + CFG.PADDING * 4 + iTranslateX,
         this.getPosY()
            + this.getHeight() / 2
            - (int)(CFG.TEXT_HEIGHT + CFG.PADDING + CFG.TEXT_HEIGHT * 0.7F) / 2
            + CFG.PADDING
            + CFG.PADDING / 2
            + CFG.TEXT_HEIGHT
            + iTranslateY,
         new Color(0.48F, 0.48F, 0.48F, 1.0F)
      );
      Renderer.fontMain.get(this.fontID).getData().setScale(1.0F);
      oSB.setColor(new Color(0.15686275F, 0.15686275F, 0.27450982F, 0.275F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getTextPos() + (int)(this.getTextWidth() * 0.9F) + CFG.PADDING * 2,
            this.getHeight()
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getTextPos() + (int)(this.getTextWidth() * 0.9F) + CFG.PADDING * 2 - 1 + iTranslateX,
            this.getPosY() + iTranslateY,
            1,
            this.getHeight()
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   public int getCurrent() {
      return this.iScenarioID;
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE2;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED2;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT2 : Colors.BUTTON_TEXT_DISABLED;
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_ScenarioPreview(this.getCurrent()));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.mapScenarios.details.get(this.getCurrent()).Day
               + " "
               + Game_Calendar.getMonthName(Game.mapScenarios.details.get(this.getCurrent()).Month)
               + " "
               + Game.mapScenarios.details.get(this.getCurrent()).Year,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_TITLE_2
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text(Game.gameAges.lAges.get(Game.mapScenarios.details.get(this.getCurrent()).Age).Name, CFG.FONT_REGULAR_SMALL)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilizations") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text("" + Game.mapScenarios.details.get(this.getCurrent()).Civs, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Author") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text("" + Game.mapScenarios.details.get(this.getCurrent()).Author, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Tag") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text("" + Game.mapScenarios.lScenarios_TagsList.get(this.getCurrent()), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
