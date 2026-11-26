package aoc.kingdoms.lukasz.menusInGame.Court.World;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_AllianceSpecial;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_TitleSpecial_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.AllianceSpecial.InGame_AllianceSpecial;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class InGame_Court_WorldAlliances extends Menu {
   public InGame_Court_WorldAlliances() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.PADDING * 2 + InGame_CourtOptions2.getMenuWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = paddingLeft;
      int buttonY = CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get(GameValues.court.COUNCIL_NAME),
            Images.council,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Court();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(GameValues.court.COUNCIL_NAME), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return SoundsManager.getClickSound_CivOptions();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int maxWidth = ImageManager.getImage(Images.alliance).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Alliances"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int tElements = menuElements.size();
      int allianceTitle = ButtonFlag_AllianceSpecial.getButtonHeight() - ButtonFlag_Diplomacy.getButtonHeight() - CFG.PADDING;

      for (int i = 0; i < Game.alliancesSpecialSize; i++) {
         buttonY += CFG.PADDING * 2;
         menuElements.add(
            new ButtonFlag_AllianceSpecial(i, paddingLeft2, buttonY) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp()
                     && MenuManager.IN_GAME_POP_UP_MENU_ID == 35
                     && InGame_AllianceSpecial.allianceID == this.getCurrent()) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.rebuildInGame_AllianceSpecial(this.getCurrent());
                  }
               }
            }
         );
         menuElements.add(
            new Text_StaticBG_TitleSpecial_ID(
               Game.lang.get(Game.alliancesSpecial.get(i).Name_Alliance),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft2 + ButtonFlag_AllianceSpecial.getButtonWidth() + CFG.PADDING * 2,
               buttonY,
               menuWidth - (paddingLeft2 * 2 + ButtonFlag_AllianceSpecial.getButtonWidth() + CFG.PADDING * 2),
               allianceTitle,
               i
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_PopUp()
                     && MenuManager.IN_GAME_POP_UP_MENU_ID == 35
                     && InGame_AllianceSpecial.allianceID == this.getCurrent()) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.rebuildInGame_AllianceSpecial(this.getCurrent());
                  }
               }
            }
         );
         menuElements.add(
            new ButtonFlag_Diplomacy(
               Game.alliancesSpecial.get(i).iLeaderCivID,
               paddingLeft2 + CFG.PADDING + ButtonFlag_AllianceSpecial.getButtonWidth() + CFG.PADDING,
               buttonY + allianceTitle + CFG.PADDING,
               true
            )
         );
         menuElements.add(
            new Text_Static_ID(
               Game.alliancesSpecial.get(i).iLeaderCivID,
               Game.getCiv(Game.alliancesSpecial.get(i).iLeaderCivID).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               paddingLeft2 + ButtonFlag_AllianceSpecial.getButtonWidth() + CFG.PADDING * 2 + ButtonFlag_Diplomacy.getButtonWidth(),
               buttonY + allianceTitle + CFG.PADDING,
               menuWidth - (paddingLeft2 * 2 + ButtonFlag_AllianceSpecial.getButtonWidth() + CFG.PADDING * 2 + ButtonFlag_Diplomacy.getButtonWidth()),
               ButtonFlag_Diplomacy.getButtonHeight()
            )
         );
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
               paddingLeft, buttonY - CFG.PADDING * 2, menuWidth - paddingLeft * 2, ButtonFlag_AllianceSpecial.getButtonHeight() + CFG.PADDING * 4
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() - CFG.PADDING;
      }

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.alliance.size() > 0) {
            for (Entry<Integer, Diplomacy.DiplomacyData> entry : Game.getCiv(i).diplomacy.alliance.entrySet()) {
               if (entry.getKey() > i) {
                  menuElements.add(
                     new ButtonFlag_Diplomacy(
                        i,
                        menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
                        buttonY + tempTitlePaddingY,
                        true
                     )
                  );
                  int var10004 = menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2;
                  menuElements.add(new ButtonFlag_Diplomacy(entry.getValue().iCivID, var10004, buttonY + tempTitlePaddingY, true));
                  menuElements.add(
                     new Text_Static_ID(
                        entry.getValue().iCivID,
                        Game.getCiv(entry.getValue().iCivID).getCivName(),
                        CFG.FONT_REGULAR_SMALL,
                        -1,
                        menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
                        buttonY,
                        tempTextW,
                        tempTitleH
                     )
                  );
                  menuElements.add(new Text_Static_ID(i, Game.getCiv(i).getCivName(), CFG.FONT_REGULAR_SMALL, -1, paddingLeft, buttonY, tempTextW, tempTitleH));
                  menuElements.add(new TextIcon_Diplomacy(Images.alliance, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
                     @Override
                     public Color getColorBar() {
                        return Colors.COLOR_GRADIENT_OVER_BLUE;
                     }
                  });
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }
         }
      }

      if (tElements == menuElements.size()) {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("None") + ".",
               CFG.FONT_REGULAR_SMALL,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get(Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.Name));
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
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
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }
}
