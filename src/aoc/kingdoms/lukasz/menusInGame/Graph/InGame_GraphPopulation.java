package aoc.kingdoms.lukasz.menusInGame.Graph;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIcon;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_Goods;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_GraphPopulation extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int activeModeID = 0;

   public InGame_GraphPopulation() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.title928).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int menuHeight = Math.min(CFG.GAME_HEIGHT - menuY * 2, menuWidth);
      InGame_Goods.inGoodsView = false;
      int statsH = ImageManager.getImage(Images.provinces).getHeight() + CFG.PADDING * 4;
      int typeW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 5) / 6;
      int typeX = paddingLeft;
      menuElements.add(new ButtonIcon("", Images.population, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 0;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Population"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.population, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 0;
         }
      });
      typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new ButtonIcon("", Game_Calendar.IMG_ECONOMY, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 1;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Economy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 1;
         }
      });
      typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new ButtonIcon("", Images.buildings, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 2;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ConstructedBuildings"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.buildings, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 2;
         }
      });
      typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new ButtonIcon("", Images.infrastructure, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 3;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Infrastructure"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.infrastructure, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 3;
         }
      });
      typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new ButtonIcon("", Images.provinces, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 4;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Provinces"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 4;
         }
      });
      typeX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new ButtonIcon("", Game_Calendar.IMG_TECHNOLOGY, typeX, buttonYPadding, typeW, statsH) {
         @Override
         public void actionElement() {
            InGame_GraphPopulation.activeModeID = 5;
            Game.menuManager.rebuildInGame_GraphPopulation();
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UnlockedTechnologies"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }

         @Override
         public boolean isActive() {
            return InGame_GraphPopulation.activeModeID == 5;
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      if (activeModeID == 0) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_POPULATION,
            Game.lang.get("Civilizations"),
            Game.lang.get("Population"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 1) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_ECONOMY,
            Game.lang.get("Civilizations"),
            Game.lang.get("Economy"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 2) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_CONSTRUCTED_BUILDINGS,
            Game.lang.get("Civilizations"),
            Game.lang.get("ConstructedBuildings"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 3) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_INFRASTRUCTURE,
            Game.lang.get("Civilizations"),
            Game.lang.get("Population"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 4) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.NUM_OF_PROVINCES_BY_CONTINENT,
            Game.lang.get("Civilizations"),
            Game.lang.get("Provinces"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 5) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_UNLOCKED_TECHS,
            Game.lang.get("Civilizations"),
            Game.lang.get("Civilizations"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 6) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_PRESTIGE,
            Game.lang.get("Civilizations"),
            Game.lang.get("Civilizations"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      } else if (activeModeID == 7) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_REGIMENTS_LIMIT,
            Game.lang.get("Civilizations"),
            Game.lang.get("Civilizations"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuHeight - buttonY - buttonYPadding,
            true
         ) {};
         menuElements.add(graphVertical);
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(
            activeModeID == 0
               ? Game.lang.get("Population")
               : (
                  activeModeID == 1
                     ? Game.lang.get("Economy")
                     : (
                        activeModeID == 2
                           ? Game.lang.get("ConstructedBuildings")
                           : (
                              activeModeID == 3
                                 ? Game.lang.get("Infrastructure")
                                 : (
                                    activeModeID == 4
                                       ? Game.lang.get("Provinces")
                                       : (
                                          activeModeID == 5
                                             ? Game.lang.get("UnlockedTechnologies")
                                             : (activeModeID == 6 ? Game.lang.get("Prestige") : Game.lang.get("RegimentsLimit"))
                                       )
                                 )
                           )
                     )
               ),
            Game.lang.get("Year") + ": " + Game_Calendar.currentYear,
            false,
            false,
            Images.title928
         ) {
            @Override
            public long getTime() {
               return InGame_GraphPopulation.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         menuY,
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      float fA = 1.0F;
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.2F * fA));
      Images.pix.draw2(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.65F * fA));
      Images.gradientFull.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(Color.WHITE);
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop928,
         Images.insideBot928
      );
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameGoods();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_Goods(false);
   }
}
