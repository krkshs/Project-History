package aoc.kingdoms.lukasz.menusInGame.Province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class InGame_ProvinceArmy_Regroup extends Menu {
   public InGame_ProvinceArmy_Regroup() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING;
      int buttonY = CFG.PADDING;
      int generalPadding = CFG.PADDING * 3;
      int defaultXArmyPos = ImageManager.getImage(Images.generalFrame).getWidth() + generalPadding * 2;
      if (Game.regroupArmyMode) {
         try {
            int innerH = CFG.GAME_HEIGHT - InGame_ProvinceArmyUnits.mPosY - CFG.PADDING * 2;
            int innerW = InGame_ProvinceArmyUnits.mWidth - CFG.PADDING * 4;
            menuElements.add(new Text_Desc(Game.lang.get("SelectProvincesToWhichYouWantToMoveArmies"), buttonX, buttonY, innerW));
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
                     Game.regroupArmy_UndoProvince();
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
                     Game.regroupArmy_Clear();
                  }
               }
            );
            menuElements.add(new ButtonGame(Game.lang.get("Confirm"), CFG.FONT_REGULAR, -1, buttonX + CFG.PADDING + buttonW, buttonY, buttonW, buttonH, true) {
               @Override
               public void actionElement() {
                  InGame_ProvinceArmy_Regroup.confirm();
               }

               @Override
               public boolean getClickable() {
                  return Game.iRegroupArmyProvincesSize > 0;
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
                     Game.setRegroupArmyMode(false);
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
      Game.regroupArmy_Move();
   }
}
