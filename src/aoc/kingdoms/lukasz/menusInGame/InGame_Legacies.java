package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamAchievementsManager;
import aoc.kingdoms.lukasz.map.LegacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.Toast;
import aoc.kingdoms.lukasz.menu_element.button.ButtonLegacy;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleLegacy;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Legacies extends Menu {
   public static int UNLOCK_LEGACY_ID = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iActiveCivID = 0;
   public static float bgAlpha = 0.0F;
   public static long bgTIME;
   public static long bgTIME_CHANGE;

   public InGame_Legacies() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.title928).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title928).getHeight();
      int buttonY = 0;
      int buttonX = Images.boxTitleBORDERWIDTH;
      List<Integer> tMaxLvlLegacies_Group = new ArrayList<>();
      List<Integer> tCivLvlLegacies_Group = new ArrayList<>();

      for (int i = 0; i < LegacyManager.iLegacyGroupsSize; i++) {
         tMaxLvlLegacies_Group.add(0);
         tCivLvlLegacies_Group.add(0);
      }

      for (int i = 0; i < LegacyManager.iLegaciesSize; i++) {
         tMaxLvlLegacies_Group.set(
            LegacyManager.legacies.get(i).GroupID,
            tMaxLvlLegacies_Group.get(LegacyManager.legacies.get(i).GroupID) + LegacyManager.legacies.get(i).CostLegacy.length
         );
      }

      for (int i = 0; i < Game.getCiv(iActiveCivID).getLegaciesSize(); i++) {
         tCivLvlLegacies_Group.set(
            LegacyManager.legacies.get(Game.getCiv(iActiveCivID).getCivLegacy(i).id).GroupID,
            tCivLvlLegacies_Group.get(LegacyManager.legacies.get(Game.getCiv(iActiveCivID).getCivLegacy(i).id).GroupID)
               + Game.getCiv(iActiveCivID).getCivLegacy(i).lvl
               + 1
         );
      }

      menuElements.add(
         new Text_TitleLegacy(
            LegacyManager.legacyGroups.get(0),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            "[" + Game.lang.get("Unlocked") + ": " + tCivLvlLegacies_Group.get(0) + "/" + tMaxLvlLegacies_Group.get(0) + "]"
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      int i = 0;

      for (int currentGroupID = LegacyManager.legacies.get(0).GroupID; i < LegacyManager.iLegaciesSize; i++) {
         if (LegacyManager.legacies.get(i).GroupID != currentGroupID) {
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
            buttonX = Images.boxTitleBORDERWIDTH;
            currentGroupID = LegacyManager.legacies.get(i).GroupID;
            menuElements.add(
               new Text_TitleLegacy(
                  LegacyManager.legacyGroups.get(currentGroupID),
                  -1,
                  Images.boxTitleBORDERWIDTH,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 6,
                  "[" + Game.lang.get("Unlocked") + ": " + tCivLvlLegacies_Group.get(currentGroupID) + "/" + tMaxLvlLegacies_Group.get(currentGroupID) + "]"
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight();
         }

         menuElements.add(new ButtonLegacy(buttonX, buttonY, i, Game.getCiv(iActiveCivID).getLegacyLevel(i)) {
            @Override
            public void actionElement() {
               if (InGame_Legacies.iActiveCivID == Game.player.iCivID) {
                  if (this.currentLvl + 1 < LegacyManager.legacies.get(this.legacyID).CostLegacy.length) {
                     InGame_Legacies.UNLOCK_LEGACY_ID = this.getCurrent();
                     InGame_Legacies.unlockLegacy();
                  } else {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Max") + ": "));
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get(LegacyManager.legacies.get(this.legacyID).Name), Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_Image(Images.legacy, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     Game.menuManager.addToast(new Toast(nElements, 0, 6000));
                  }
               }
            }
         });
         buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      }

      buttonY = 0;
      i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      if (iActiveCivID == Game.player.iCivID) {
         this.initMenu(
            new MenuTitleIMG_DoubleText(
               Game.lang.get("CivilizationLegacy"),
               Game.lang.get("Legacy") + ": " + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fLegacy, 1),
               false,
               false,
               Images.title928
            ) {
               @Override
               public long getTime() {
                  return InGame_Legacies.lTime;
               }
            },
            CFG.GAME_WIDTH / 2 - menuWidth / 2,
            menuY,
            menuWidth,
            i,
            menuElements,
            true,
            true
         );
      } else {
         this.initMenu(
            new MenuTitleIMG_FlagCenter(Game.lang.get("CivilizationLegacy"), Game.getCiv(iActiveCivID).getCivName(), false, false, Images.title928) {
               @Override
               public long getTime() {
                  return InGame_Legacies.lTime;
               }

               @Override
               public int getFlagCivID() {
                  return InGame_Legacies.iActiveCivID;
               }
            }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, i, menuElements, true, true
         );
      }

      this.drawScrollPositionAlways = false;
      lTime = CFG.currentTimeMillis;
      bgTIME = System.currentTimeMillis();
      bgTIME_CHANGE = System.currentTimeMillis();
      bgAlpha = 0.0F;
   }

   public int getInnerTitleHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 6;
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
         Images.insideTop928,
         Images.insideBot928
      );
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
      Game.menuManager.setOrderOfMenu_InGameLegacies();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setViewIDWithoutAnimation(View.IN_GAME);
   }

   public static final void unlockLegacy() {
      if (Game.getCiv(Game.player.iCivID).unlockLegacy(UNLOCK_LEGACY_ID)) {
         Game.menuManager.rebuildInGame_Legacies();
         Game.menuManager.setOrderOfMenu_InGameLegacies();
         lTime = 0L;
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.soundsManager.stopLegacySound();
         switch (Game.getCiv(Game.player.iCivID).getLegacyLevel(UNLOCK_LEGACY_ID)) {
            case 0:
               Game.soundsManager.playSound(SoundsManager.LEGACY_0);
               break;
            case 1:
               Game.soundsManager.playSound(SoundsManager.LEGACY_1);
               break;
            default:
               Game.soundsManager.playSound(SoundsManager.LEGACY_2);
         }

         Game.menuManager
            .rebuildInGameLegacies_Info(
               Game.lang.get(LegacyManager.legacies.get(UNLOCK_LEGACY_ID).Name),
               Game.lang.get("Unlocked")
                  + " "
                  + (Game.getCiv(Game.player.iCivID).getLegacyLevel(UNLOCK_LEGACY_ID) + 1)
                  + "/"
                  + LegacyManager.legacies.get(UNLOCK_LEGACY_ID).CostLegacy.length
            );
         SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.UNLOCK_LEGACY);
         Game.player.currSituation.buildPlayerLegaciesLVL();
         if (Game.menuManager.getVisibleInGame_CivBonuses()) {
            Game.menuManager.rebuildInGame_CivBonuses();
            Game.menuManager.setVisibleInGame_CivBonuses(true);
            InGame_CivBonuses.lTime = 0L;
         }
      } else {
         Game.menuManager.addToastInsufficient(Game.lang.get("InsufficientLegacy"), "", Images.legacy);
      }
   }
}
