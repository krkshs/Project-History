package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmy_Invasion extends Menu {
   public InGame_ProvinceArmy_Invasion() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING;
      int buttonY = CFG.PADDING;
      int generalPadding = CFG.PADDING * 3;
      int defaultXArmyPos = ImageManager.getImage(Images.generalFrame).getWidth() + generalPadding * 2;
      if (Game.invasionArmyMode) {
         try {
            int innerH = CFG.GAME_HEIGHT - InGame_ProvinceArmyUnits.mPosY - CFG.PADDING * 2;
            int innerW = InGame_ProvinceArmyUnits.mWidth - CFG.PADDING * 4;
            menuElements.add(new Text_Desc(Game.lang.get("OnlyProvincesBelongingToCivilizationsYouAreAtWarWithCanBeConquered"), buttonX, buttonY, innerW));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            innerH -= menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            int buttonW = (innerW - CFG.PADDING) / 2;
            int buttonH = (innerH - CFG.PADDING) / 2;
            int buttonH2 = (innerH - CFG.PADDING * 2) / 3;
            menuElements.add(
               new ButtonStatsRectIMG_Active_Click(
                  Game.lang.get("Brush"), Images.x, buttonX, buttonY, buttonW, buttonH2, ImageManager.getImage(Images.gold).getWidth(), 0, true
               ) {
                  @Override
                  public void actionElement() {
                     CFG.brushTool = !CFG.brushTool;
                  }

                  @Override
                  public int getImageID() {
                     return CFG.brushTool ? Images.v : Images.x;
                  }
               }
            );
            menuElements.add(
               new ButtonStatsRectIMG_Active_Click(
                  Game.lang.get("Undo"),
                  Images.x,
                  buttonX,
                  buttonY + buttonH2 + CFG.PADDING,
                  buttonW,
                  buttonH2,
                  ImageManager.getImage(Images.gold).getWidth(),
                  0,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.invasionArmy_UndoProvince();
                  }
               }
            );
            menuElements.add(
               new ButtonStatsRectIMG_Active_Click(
                  Game.lang.get("Clear"),
                  Images.x,
                  buttonX,
                  buttonY + buttonH2 * 2 + CFG.PADDING * 2,
                  buttonW,
                  buttonH2,
                  ImageManager.getImage(Images.gold).getWidth(),
                  0,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.invasionArmy_Clear();
                  }
               }
            );
            menuElements.add(new ButtonGame(Game.lang.get("Confirm"), CFG.FONT_REGULAR, -1, buttonX + CFG.PADDING + buttonW, buttonY, buttonW, buttonH, true) {
               @Override
               public void actionElement() {
                  InGame_ProvinceArmy_Invasion.confirm();
               }

               @Override
               public boolean getClickable() {
                  return Game.invasionArmyProvincesSize > 0;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LaunchInvasionOfProvinces"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.invasion, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            });
            menuElements.add(
               new ButtonStatsRectIMG_Active_Click(
                  Game.lang.get("Cancel"),
                  Images.x,
                  buttonX + CFG.PADDING + buttonW,
                  buttonY + CFG.PADDING + buttonH,
                  buttonW,
                  buttonH,
                  ImageManager.getImage(Images.gold).getWidth(),
                  0,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.setInvasionArmyMode(false);
                  }
               }
            );
         } catch (Exception var11) {
            CFG.exceptionStack(var11);
         }
      }

      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.max(CFG.PADDING, buttonY);
      this.initMenu(null, InGame_ProvinceArmyUnits.mPosX, InGame_ProvinceArmyUnits.mPosY, InGame_ProvinceArmyUnits.mWidth, i, menuElements, false);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameProvinceArmy();
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_ProvinceArmy.nTranslateY;
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_ProvinceArmy.nTranslateY + InGame_ProvinceArmy.chooseProvinceExtraY;
   }

   @Override
   public int getPosX() {
      return super.getPosX() + InGame_ProvinceArmy.nTranslateX;
   }

   @Override
   public int getMenuPosX() {
      return super.getMenuPosX() + InGame_ProvinceArmy.nTranslateX;
   }

   public static final void confirm() {
      if (Game.player.playerData.invasion.addInvasion(Game.player.iCivID)) {
         Game.menuManager.addToastGold(Game.lang.get("Done"), Game_Calendar.IMG_MANPOWER);
         Game.setInvasionArmyMode(false);
      } else {
         Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"), Game_Calendar.IMG_MANPOWER);
      }
   }
}
