package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGame_AddCivTech extends Menu {
   public NewGame_AddCivTech() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = 0;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT2 : CFG.BUTTON_HEIGHT;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Technologies"),
            -1,
            CFG.FONT_BOLD,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int buttonX = paddingLeft;
      int statW = menuWidth - paddingLeft * 2 - CFG.PADDING - ImageManager.getImage(Images.techGray).getWidth();
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.NEW_GAME_ADD_CIV);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         menuElements.add(
            new TextIcon2(
               "" + CFG.getPrecision2(TechnologyTree.getResearchCost(i, Game.player.iCivID), 10),
               Game_Calendar.IMG_TECHNOLOGY,
               buttonX,
               buttonY,
               statW,
               ImageManager.getImage(Images.techGray).getHeight()
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ResearchCost") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.COLOR_TEXT_RESEARCH));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }

               @Override
               public int getTextH() {
                  return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonTechnology(Images.techGray, i, buttonX, buttonY, false, i - 1) {
            @Override
            public void actionElement() {
               NewGame_AddCiv.addNewCivData.technologyID = this.getCurrent();
               Game.menuManager.setViewID(View.NEW_GAME_ADD_CIV);
            }

            @Override
            protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               if (this.getCurrent() == NewGame_AddCiv.addNewCivData.technologyID) {
                  this.btnIMG = Images.techResearched;
               } else {
                  this.btnIMG = Images.techGray;
               }

               super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         buttonX = paddingLeft;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG("", false, false, Images.title1Red), menuX, menuY, menuWidth, menuHeight, menuElements, true, true);
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
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Technology"));
   }
}
