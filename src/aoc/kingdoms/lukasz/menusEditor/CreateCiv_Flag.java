package aoc.kingdoms.lukasz.menusEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.ColorPicker;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class CreateCiv_Flag extends Menu {
   public CreateCiv_Flag() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int menuW = CFG.LEFT_MENU_WIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int textPosX = CFG.PADDING * 4;
      int buttonH = 100 + CFG.PADDING * 2;
      menuElements.add(new ButtonMain(Game.lang.get("Back"), 1, -1, paddingLeft, buttonYPadding, menuW - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Game.menuManager.createCivFlag().setVisible(false);
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame("<<", 1, -1, paddingLeft, buttonY, CFG.BUTTON_WIDTH, buttonH, true) {
         @Override
         public void actionElement() {
            if (Game.menuManager.getColorPicker().getVisible()) {
               Game.menuManager.getColorPicker().hideColorPicker();
            }

            Game.flagManager.updateDivision(false);
            Game.menuManager.rebuildCreateCivFlag();
            Game.menuManager.addToastGold("ID: [" + Game.flagManager.flagEdit.iDivisionID + "/" + (Game.flagManager.lDivisions.size() - 1) + "]", Images.brush);
         }
      });
      menuElements.add(
         new ButtonGame("", 1, textPosX, paddingLeft + CFG.BUTTON_WIDTH, buttonY, menuW - paddingLeft * 2 - CFG.BUTTON_WIDTH * 2, buttonH, true) {
            @Override
            public void actionElement() {
               if (Game.menuManager.getColorPicker().getVisible() && Game.flagManager.activeColorID == 0) {
                  Game.menuManager.getColorPicker().hideColorPicker();
               } else {
                  Game.flagManager.activeColorID = 0;
                  Game.menuManager
                     .getColorPicker()
                     .setActiveRGBColor(
                        Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).r,
                        Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).g,
                        Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).b
                     );
                  Game.menuManager.getColorPicker().setVisible(true, ColorPicker.PickerAction.CREATE_CIV_DIVISION);
                  Game.menuManager.getColorPicker().setPosX(CreateCiv_Flag.this.getPosX() + CreateCiv_Flag.this.getWidth() + CFG.PADDING * 4);
                  Game.menuManager.getColorPicker().setPosY(this.getPosY());
               }
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               Game.flagManager
                  .drawDivision_FlagFrameSize(
                     oSB, this.getPosX() + this.getWidth() / 2 - 77 + iTranslateX, this.getPosY() + this.getHeight() / 2 - 50 + iTranslateY
                  );
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "", "" + Game.lang.get("PickColor"), Images.pickerIcon, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(new ButtonGame(">>", 1, -1, menuW - paddingLeft - CFG.BUTTON_WIDTH, buttonY, CFG.BUTTON_WIDTH, buttonH, true) {
         @Override
         public void actionElement() {
            if (Game.menuManager.getColorPicker().getVisible()) {
               Game.menuManager.getColorPicker().hideColorPicker();
            }

            Game.flagManager.updateDivision(true);
            Game.menuManager.rebuildCreateCivFlag();
            Game.menuManager.addToastGold("ID: [" + Game.flagManager.flagEdit.iDivisionID + "/" + (Game.flagManager.lDivisions.size() - 1) + "]", Images.brush);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 1; i < Game.flagManager.lDivisions.get(Game.flagManager.flagEdit.iDivisionID).iLayers; i++) {
         menuElements.add(
            new ButtonGame("", 1, textPosX, paddingLeft, buttonY, menuW - paddingLeft * 2, buttonH, true) {
               int iCurrent;

               @Override
               public void setCurrent(int nCurrent) {
                  this.iCurrent = nCurrent;
               }

               @Override
               public int getCurrent() {
                  return this.iCurrent;
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  Game.flagManager
                     .drawDivision_FlagFrameSize(
                        oSB, this.getPosX() + this.getWidth() / 2 - 77 + iTranslateX, this.getPosY() + this.getHeight() / 2 - 50 + iTranslateY, this.iCurrent
                     );
               }

               @Override
               public void actionElement() {
                  if (Game.menuManager.getColorPicker().getVisible() && Game.flagManager.activeColorID == this.getCurrent()) {
                     Game.menuManager.getColorPicker().hideColorPicker();
                  } else {
                     Game.flagManager.activeColorID = this.getCurrent();
                     Game.menuManager
                        .getColorPicker()
                        .setActiveRGBColor(
                           Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).r,
                           Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).g,
                           Game.flagManager.flagEdit.lDivisionColors.get(Game.flagManager.activeColorID).b
                        );
                     Game.menuManager.getColorPicker().setVisible(true, ColorPicker.PickerAction.CREATE_CIV_DIVISION);
                     Game.menuManager.getColorPicker().setPosX(CreateCiv_Flag.this.getPosX() + CreateCiv_Flag.this.getWidth() + CFG.PADDING * 4);
                     Game.menuManager.getColorPicker().setPosY(this.getPosY());
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        "", "" + Game.lang.get("PickColor"), Images.pickerIcon, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         menuElements.get(menuElements.size() - 1).setCurrent(i);
      }

      for (int i = 0; i < Game.flagManager.flagEdit.lOverlays.size(); i++) {
         menuElements.add(
            new ButtonGame("", 1, textPosX, paddingLeft, buttonY, CFG.BUTTON_WIDTH, buttonH, true) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildCreateCivFlag();
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  ImageManager.getImage(Images.pickerIcon)
                     .draw(
                        oSB,
                        this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.pickerIcon).getWidth() / 2 + iTranslateX,
                        this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.pickerIcon).getHeight() / 2 + iTranslateY
                     );
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        "", "" + Game.lang.get("PickColor"), Images.pickerIcon, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonGame("", 1, textPosX, paddingLeft + CFG.BUTTON_WIDTH, buttonY, menuW - paddingLeft * 2 - CFG.BUTTON_WIDTH * 3, buttonH, true) {
               @Override
               public void actionElement() {
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  Game.flagManager
                     .drawDivision_FlagFrameSize(
                        oSB, this.getPosX() + this.getWidth() / 2 - 77 + iTranslateX, this.getPosY() + this.getHeight() / 2 - 50 + iTranslateY
                     );
                  Game.flagManager
                     .drawOverlay_FlagFrameSize(
                        oSB, this.getPosX() + this.getWidth() / 2 - 77 + iTranslateX, this.getPosY() + this.getHeight() / 2 - 50 + iTranslateY, this.iCurrent
                     );
               }
            }
         );
         menuElements.add(
            new ButtonGame("UP", 1, textPosX, menuW - paddingLeft - CFG.BUTTON_WIDTH * 2, buttonY, CFG.BUTTON_WIDTH, buttonH, true) {
               int iCurrent;

               @Override
               public void setCurrent(int nCurrent) {
                  this.iCurrent = nCurrent;
               }

               @Override
               public int getCurrent() {
                  return this.iCurrent;
               }

               @Override
               public void actionElement() {
                  Game.flagManager.moveOverlayUp(this.getCurrent());
                  Game.menuManager.rebuildCreateCivFlag();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        "", "" + Game.lang.get("Up"), Images.upgrade, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         menuElements.add(
            new ButtonGame("X", 1, textPosX, menuW - paddingLeft - CFG.BUTTON_WIDTH, buttonY, CFG.BUTTON_WIDTH, buttonH, true) {
               int iCurrent;

               @Override
               public void setCurrent(int nCurrent) {
                  this.iCurrent = nCurrent;
               }

               @Override
               public int getCurrent() {
                  return this.iCurrent;
               }

               @Override
               public void actionElement() {
                  Game.flagManager.removeOverlay(this.getCurrent());
                  Game.menuManager.rebuildCreateCivFlag();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        "", "" + Game.lang.get("Remove"), Images.x, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("CustomizeFlag"), 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         CFG.GAME_HEIGHT - titleHeight - menuY * 2,
         menuElements,
         false
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
