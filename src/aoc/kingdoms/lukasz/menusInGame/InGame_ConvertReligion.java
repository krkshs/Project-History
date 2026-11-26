package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ReligionTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ConvertReligion extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iProvinceID = 0;

   public InGame_ConvertReligion() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int tIconMaxW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      menuElements.add(
         new ButtonReligion2(
            Game.getProvince(iProvinceID).getReligion(),
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 4
         )
      );
      menuElements.add(
         new ButtonReligion2(
            Game.getCiv(Game.player.iCivID).getReligionID(),
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsBudget(
            Game.lang.get("Cost") + ": " + Game.religionManager.getReligionConversionCost(iProvinceID),
            Images.gold,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            tButtonH,
            tIconMaxW,
            false
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsBudget(
            Game.lang.get("ReligionConversionTime") + ": " + Game.lang.get("XDays", Game.religionManager.getReligionConversionTime(iProvinceID)),
            Images.time,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            tButtonH,
            tIconMaxW,
            false
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_ConvertReligion.confirm();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ConvertReligion"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Religion") + ": ", CFG.FONT_BOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + Game.religionManager.getReligion(Game.getCiv(Game.player.iCivID).getReligionID()).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ReligionTitle(Game.getCiv(Game.player.iCivID).getReligionID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(Game.lang.get("ConvertReligion"), Game.getProvince(iProvinceID).getProvinceName(), true, false, Images.title500) {
            @Override
            public long getTime() {
               return InGame_ConvertReligion.lTime;
            }
         }, CFG.GAME_WIDTH / 2 - menuWidth / 2, CFG.GAME_HEIGHT / 4, menuWidth, i, menuElements, false, true
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
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

   public static final void confirm() {
      if (Game.getCiv(Game.player.iCivID).fGold < Game.religionManager.getReligionConversionCost(iProvinceID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientGold") + ": ",
               "" + CFG.getPrecision2((float)Game.religionManager.getReligionConversionCost(iProvinceID), 10),
               Images.gold
            );
      } else {
         Game.getProvince(iProvinceID).addReligionConversion();
         Game.menuManager.setVisibleInGame_PopUp(false);
      }
   }
}
