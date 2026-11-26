package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Formable;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_TitleSpecial_ID;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_FormCiv extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int formCivID = 0;
   public static boolean GO_BACK_TO_COURT = true;

   public InGame_FormCiv(int playerCivID) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      formCivID = playerCivID;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      buttonY += CFG.PADDING;
      menuElements.add(new ButtonFlag_Formable(formCivID, paddingLeft2, buttonY) {
         @Override
         public void actionElement() {
         }
      });
      int buttonX = paddingLeft2 + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      int bHeight = (ButtonFlag_Formable.getButtonHeight() - CFG.PADDING) / 2;
      menuElements.add(
         new Text_StaticBG_RulerTitle(
            Game.lang.getCiv(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(formCivID)), buttonX, buttonY, menuWidth - buttonX - paddingLeft2, bHeight
         ) {
            @Override
            public void actionElement() {
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Provinces") + ": ",
            Game.player.formableCivs.get(formCivID).getControlledProvinces(Game.player.iCivID)
               + " / "
               + Game.player.formableCivs.get(formCivID).getProvincesSize_WithoutNeutral(),
            Images.provinces,
            buttonX,
            buttonY + bHeight + CFG.PADDING,
            menuWidth - buttonX - paddingLeft2,
            bHeight,
            maxIconW
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("ControlsProvinces") + ": ",
                     this.sText2,
                     this.imageID,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += ButtonFlag_Formable.getButtonHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmpty(
            paddingLeft,
            buttonY - (ButtonFlag_Formable.getButtonHeight() + CFG.PADDING * 2),
            menuWidth - paddingLeft * 2,
            ButtonFlag_Formable.getButtonHeight() + CFG.PADDING * 2
         )
      );
      buttonY += CFG.PADDING;
      long population = 0L;
      float economy = 0.0F;

      for (int i = 0; i < Game.player.formableCivs.get(formCivID).getProvincesSize(); i++) {
         if (Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(i)).getCivID() > 0) {
            population += Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(i)).getPopulationTotal();
            economy += Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(i)).getEconomyWithBonuses();
         }
      }

      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Population") + ": ",
            "" + CFG.getNumberWithSpaces("" + population),
            Images.population,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            bHeight,
            maxIconW
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.COLOR_POPULATION;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Economy") + ": ",
            "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(economy, 1)),
            Game_Calendar.IMG_ECONOMY,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            bHeight,
            maxIconW
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.COLOR_TEXT_ECONOMY;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
               if (InGame_FormCiv.GO_BACK_TO_COURT) {
                  if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DEFAULT) {
                     Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                  }

                  Game.menuManager.rebuildInGame_Government();
                  Game.menuManager.setVisibleInGame_Court(true);
               } else {
                  InGame_Civ.iRebuildToCivID = Game.player.iCivID;
                  Game.menuManager.rebuildInGame_Civ(true);
                  if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DIPLOMACY) {
                     Game.mapModes.setActiveViewID(Game.mapModes.MODE_DIPLOMACY);
                  }
               }
            }
         }
      );
      menuElements.add(
         new ButtonGame_ImageSparks(
            Game.lang.get("FormCivilization"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            FormableCivManager.canFormACiv(Game.player.iCivID, Game.player.formableCivs.get(formCivID).FormableCivTag),
            Images.v
         ) {
            @Override
            public void actionElement() {
               InGame_FormCiv.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = FormableCivManager.getHover(InGame_FormCiv.formCivID);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> civs = new ArrayList<>();

      for (int ix = 0; ix < Game.player.formableCivs.get(formCivID).getProvincesSize(); ix++) {
         if (Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(ix)).getCivID() > 0
            && !civs.contains(Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(ix)).getCivID())) {
            civs.add(Game.getProvince(Game.player.formableCivs.get(formCivID).Provinces.get(ix)).getCivID());
         }
      }

      if (!civs.isEmpty()) {
         int buttonYBefore = buttonY;
         buttonY += CFG.PADDING;
         int topTitleH = CFG.BUTTON_HEIGHT3 - CFG.PADDING * 2;
         menuElements.add(
            new Text_StaticBG_TitleSpecial_ID(
               Game.lang.get("Civilizations"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, topTitleH, civs.size()
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        this.getText() + ": ",
                        CFG.getNumberWithSpaces("" + this.id),
                        Images.provinces,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         buttonX = paddingLeft;

         for (int ixx = 0; ixx < civs.size(); ixx++) {
            if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth - paddingLeft) {
               buttonY += ButtonFlag_Diplomacy.getButtonHeight() + CFG.PADDING;
               buttonX = paddingLeft;
            }

            if (Game.getCiv(civs.get(ixx)).getNumOfProvinces() > 0) {
               menuElements.add(
                  new ButtonFlag_Diplomacy(civs.get(ixx), buttonX, buttonY, true) {
                     @Override
                     public void actionElement() {
                        if (Game.getCiv(this.iCivID).getCapitalProvinceID() >= 0
                           && Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getCivID() == this.iCivID) {
                           Game.mapCoords.centerToProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                        }
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_FlagCiv_Title(
                              this.iCivID,
                              Game.getCiv(this.iCivID).iCivRankPosition >= 0
                                 ? Game.lang.get("Ranking") + ": " + Game.getCiv(this.iCivID).iCivRankPosition
                                 : Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getProvinceName()
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        int numOfProvinces = 0;

                        for (int i = 0; i < Game.player.formableCivs.get(InGame_FormCiv.formCivID).getProvincesSize(); i++) {
                           if (Game.getProvince(Game.player.formableCivs.get(InGame_FormCiv.formCivID).Provinces.get(i)).getCivID() == this.iCivID) {
                              numOfProvinces++;
                           }
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Provinces") + ": ",
                              CFG.getNumberWithSpaces("" + numOfProvinces),
                              Images.provinces,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         }

         for (int ixx = menuElements.size() - 1; ixx >= 0; ixx--) {
            buttonY = Math.max(buttonY, menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING);
         }

         menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonYBefore, menuWidth - paddingLeft2 * 2, buttonY - buttonYBefore));
         buttonY += CFG.PADDING;
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("FormCivilization"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_FormCiv.lTime;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
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
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      DiplomacyManager.updateAnimationTime();
   }

   public static void confirm() {
      if (FormableCivManager.canFormACiv(Game.player.iCivID, Game.player.formableCivs.get(formCivID).FormableCivTag)
         && FormableCivManager.formCiv(Game.player.iCivID, Game.player.formableCivs.get(formCivID).FormableCivTag)) {
         Game.soundsManager.playSound(SoundsManager.SOUND_FORMABLE);
         Game.menuManager.addToastPositive(Game.lang.get("Civilization") + ": ", Game.getCiv(Game.player.iCivID).getCivName(), Images.v);
      }

      Game.menuManager.setVisibleInGame_PopUp(false);
      if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DEFAULT) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }
}
