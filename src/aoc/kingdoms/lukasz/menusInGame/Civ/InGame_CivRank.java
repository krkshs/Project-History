package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Horizontal;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_CivRank extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_CivRank() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonH = ImageManager.getImage(Images.flagOverDefault).getHeight();
      int buttonW = (int)((menuWidth - paddingLeft * 2 - ImageManager.getImage(Images.flagOverDefault).getWidth() - CFG.PADDING * 2) * 0.65F);
      int buttonW2 = (int)((menuWidth - paddingLeft * 2 - ImageManager.getImage(Images.flagOverDefault).getWidth() - CFG.PADDING * 2) * 0.35F);
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int c0W = menuWidth - paddingLeft2 * 2;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("Civilizations") + ": " + CFG.getNumberWithSpaces("" + Game.getCivilizationsInGame()),
            Images.council,
            paddingLeft2,
            buttonY,
            c0W,
            buttonH,
            ImageManager.getImage(Images.council).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int maxIconWidth = ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 2;
      int buttonW_Rank = (menuWidth - paddingLeft2 * 2 - CFG.PADDING) / 2;
      int i = 0;

      while (i < GameValues.civRank.CIV_RANK_MANPOWER_MAX.length) {
         int buttonX;
         if (i % 2 == 0) {
            buttonX = paddingLeft;
         } else {
            buttonX = paddingLeft + CFG.PADDING + buttonW_Rank;
         }

         menuElements.add(
            new TextIcon2_Horizontal(
               CivilizationRanking.getCivilizationRank_Name(i),
               CivilizationRanking.getCivilizationRank_IMG(i),
               buttonX,
               buttonY,
               buttonW_Rank,
               CFG.BUTTON_HEIGHT3,
               maxIconWidth
            ) {
               int id;

               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.buildElementHover(this.id, this.id);
               }

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         if (++i % 2 == 0) {
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      List<Integer> tCivs = new ArrayList<>();

      for (int ix = 1; ix < Game.getCivsSize(); ix++) {
         if (Game.getCiv(ix).getNumOfProvinces() > 0) {
            tCivs.add(ix);
         }
      }

      int currentRankID = 5;

      while (tCivs.size() > 0) {
         int bestID = 0;
         int ixx = 0;

         for (int iSizex = tCivs.size(); ixx < iSizex; ixx++) {
            if (Game.getCiv(tCivs.get(bestID)).getNumOfProvinces() < Game.getCiv(tCivs.get(ixx)).getNumOfProvinces()) {
               bestID = ixx;
            }
         }

         if (Game.getCiv(tCivs.get(bestID)).iCivRankID != currentRankID) {
            currentRankID = Game.getCiv(tCivs.get(bestID)).iCivRankID;
            menuElements.add(
               new Text_Title_v2Center(
                  Game.lang.get("CivilizationRank") + ": " + CivilizationRanking.getCivilizationRank_Name(currentRankID),
                  -1,
                  Images.boxTitleBORDERWIDTH,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 6
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight();
            int var20 = Images.boxTitleBORDERWIDTH;
            menuElements.add(
               new Text_Title_v2Center(
                  Game.lang.get("Civilization"),
                  -1,
                  CFG.FONT_REGULAR_SMALL,
                  var20,
                  buttonY,
                  menuWidth - buttonW2 - Images.boxTitleBORDERWIDTH * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4
               )
            );
            var20 += menuElements.get(menuElements.size() - 1).getWidth();
            menuElements.add(
               new Text_Title_v2Center(
                  Game.lang.get("Provinces"),
                  -1,
                  CFG.FONT_REGULAR_SMALL,
                  var20,
                  buttonY,
                  menuWidth - var20 - Images.boxTitleBORDERWIDTH,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         menuElements.add(new ButtonFlag2(tCivs.get(bestID), paddingLeft, buttonY, true) {
            @Override
            public void actionElement() {
               if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                  if (!Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getDrawProvince()) {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  }

                  Game.setActiveProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  Game.menuManager.rebuildInGame_Civ();
               }
            }
         });
         int var22 = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(Game.getCiv(tCivs.get(bestID)).getCivName(), CFG.FONT_REGULAR_SMALL, -1, var22, buttonY, buttonW, buttonH, tCivs.get(bestID)) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  }
               }
            }
         );
         var22 += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG("" + Game.getCiv(tCivs.get(bestID)).getNumOfProvinces(), CFG.FONT_REGULAR_SMALL, -1, var22, buttonY, buttonW2, buttonH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NumberOfProvinces") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tCivs.remove(bestID);
      }

      buttonY = 0;
      int ixx = 0;

      for (int iSizexx = menuElements.size(); ixx < iSizexx; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING;
         }
      }

      ixx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixx)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("CivilizationRank"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_CivRank.lTime;
         }
      }, menuX, menuY, menuWidth, ixx, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

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
         this.getWidth() + Images.boxTitleBORDERWIDTH,
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Civilizations"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
