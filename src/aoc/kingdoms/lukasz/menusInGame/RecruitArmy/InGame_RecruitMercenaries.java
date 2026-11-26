package aoc.kingdoms.lukasz.menusInGame.RecruitArmy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.MercenariesManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyBattle;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RecruitMercenaries extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static List<MercenariesManager.MercenaryArmy> mercenaryArmies;
   public static int activeArmyID = 0;

   public InGame_RecruitMercenaries() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title1Red).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft;
      activeArmyID = -1;
      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      if (mercenaryArmies != null) {
         mercenaryArmies.clear();
      }

      mercenaryArmies = MercenariesManager.getMercenaryArmies(Game.player.iCivID);
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      int leftW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 5;
      int rightW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 2 / 5;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int maxIconWidth = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth();
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("CreateNewArmy"), Game_Calendar.IMG_MANPOWER, paddingLeft + CFG.PADDING + rightW, buttonY, leftW, buttonH, maxIconWidth, 0, true
         ) {
            @Override
            public void actionElement() {
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
               }

               Game.menuManager.setVisibleInGame_Armies(false);
               InGame_RecruitArmy.actionCreateNewArmy();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CreateNewArmy"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (CFG.isDesktop()) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Text("V", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements, !CFG.isDesktop());
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(Game.lang.get("Back"), Game_Calendar.IMG_MANPOWER, paddingLeft, buttonY, rightW, buttonH, maxIconWidth, 0) {
            @Override
            public void actionElement() {
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
               }

               Game.menuManager.setVisibleInGame_Armies(false);
               InGame.action3();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitRegiments"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int z = 0; z < mercenaryArmies.size(); z++) {
         if (mercenaryArmies.get(z).iUnitID.size() > 0) {
            buttonY += CFG.TEXT_HEIGHT + CFG.PADDING * 5;
            int extraX = Images.boxTitleBORDERWIDTH;
            int fromCivID = Game.oR.nextInt(Game.getCivsSize() - 1) + 1;

            for (int a = 0; a < 5 && DiplomacyManager.isAtWar(Game.player.iCivID, fromCivID); a++) {
               fromCivID = Game.oR.nextInt(Game.getCivsSize() - 1) + 1;
            }

            if (DiplomacyManager.isAtWar(Game.player.iCivID, fromCivID)) {
               fromCivID = 0;
            }

            menuElements.add(
               new ButtonArmyNoGeneral2_Armies(Game.lang.get("NoGeneral"), Game.player.iCivID, buttonX + extraX, buttonY, "", fromCivID, -1) {
                  @Override
                  public void actionElement() {
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("NoGeneral"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.iCivID, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }

                  @Override
                  protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                     oSB.setShader(Renderer.shaderAlpha);
                     if (this.iCivID >= 0) {
                        Game.getCiv(this.iCivID).getFlag().getTexture().bind(1);
                     } else {
                        ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
                     }

                     Gdx.gl.glActiveTexture(33984);
                     ImageManager.getImage(Images.flagRect2Mask)
                        .draw(
                           oSB,
                           this.getPosX() + CFG.PADDING + iTranslateX,
                           this.getPosY()
                              + ImageManager.getImage(Images.generalFrameBattle).getHeight()
                              - CFG.PADDING
                              - ImageManager.getImage(Images.flagRect2Mask).getHeight()
                              + iTranslateY,
                           ImageManager.getImage(Images.flagRect2Mask).getWidth(),
                           ImageManager.getImage(Images.flagRect2Mask).getHeight()
                        );
                     oSB.flush();
                     oSB.setShader(Renderer.shaderDefault);
                     ImageManager.getImage(Images.flagRect2)
                        .draw(
                           oSB,
                           this.getPosX() + CFG.PADDING + iTranslateX,
                           this.getPosY()
                              + ImageManager.getImage(Images.generalFrameBattle).getHeight()
                              - CFG.PADDING
                              - ImageManager.getImage(Images.flagRect2Mask).getHeight()
                              + iTranslateY,
                           ImageManager.getImage(Images.flagRect2Mask).getWidth(),
                           ImageManager.getImage(Images.flagRect2Mask).getHeight()
                        );
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            int k = 0;

            for (int kSize = mercenaryArmies.get(z).iUnitID.size(); k < kSize; k++) {
               int tUnits = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
               int numOfRegiments = 1;

               for (int o = k + 1;
                  o < kSize
                     && mercenaryArmies.get(z).iUnitID.get(k) == mercenaryArmies.get(z).iUnitID.get(o)
                     && mercenaryArmies.get(z).iArmyID.get(k) == mercenaryArmies.get(z).iArmyID.get(o);
                  o++
               ) {
                  k++;
                  numOfRegiments++;
                  tUnits += Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
               }

               if (buttonX + ButtonArmyBattle.getButtonWidth() >= menuWidth - paddingLeft) {
                  break;
               }

               menuElements.add(
                  new ButtonArmyBattle(
                     "" + tUnits,
                     numOfRegiments,
                     Game.player.iCivID,
                     buttonX + extraX,
                     buttonY,
                     mercenaryArmies.get(z).iUnitID.get(k),
                     mercenaryArmies.get(z).iArmyID.get(k),
                     false
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            int var26 = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            menuElements.add(
               new Text_Title_v2_TextLR(
                  Game.lang.get("Mercenaries"),
                  CFG.BUTTON_WIDTH / 4,
                  Images.boxTitleBORDERWIDTH,
                  buttonY,
                  menuWidth - Images.boxTitleBORDERWIDTH * 2,
                  CFG.TEXT_HEIGHT + CFG.PADDING * 4,
                  CFG.getNumberWithSpaces("" + mercenaryArmies.get(z).iArmyID.size() * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE)
               )
            );
            buttonX = paddingLeft;
            buttonY = 0;
            k = 0;

            for (int iSize = menuElements.size(); k < iSize; k++) {
               if (buttonY < menuElements.get(k).getPosY() + menuElements.get(k).getHeight() + CFG.PADDING) {
                  buttonY = menuElements.get(k).getPosY() + menuElements.get(k).getHeight() + CFG.PADDING;
               }
            }

            menuElements.add(
               new ButtonStatsRectIMG_Bonuses(
                  Game.lang.get("HireMercenaries"),
                  "",
                  Images.mercenaries,
                  paddingLeft,
                  buttonY,
                  (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
                  CFG.BUTTON_HEIGHT3,
                  maxIconW
               ) {
                  int id;

                  @Override
                  public void setCurrent(int nCurrent) {
                     this.id = nCurrent;
                  }

                  @Override
                  public void actionElement() {
                     if (Game.getCiv(Game.player.iCivID).fGold < InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2((float)InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost, 1),
                              Images.gold
                           );
                     } else {
                        boolean reset = InGame_RecruitMercenaries.activeArmyID == this.id;
                        InGame_RecruitMercenaries.activeArmyID = this.id;
                        if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
                           if (reset) {
                              Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                           }
                        } else {
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE);
                        }
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("HireMercenaries"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.mercenaries, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           CFG.getNumberWithSpaces("" + InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("ArmyWillBeRecruitedInstantly"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(z);
            menuElements.add(
               new ButtonStatsRectIMG_Bonuses(
                  Game.lang.get("Cost") + ": ",
                  CFG.getNumberWithSpaces("" + mercenaryArmies.get(z).iCost),
                  Images.gold,
                  paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
                  buttonY,
                  (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
                  CFG.BUTTON_HEIGHT3,
                  maxIconW
               ) {
                  int id;

                  @Override
                  public void setCurrent(int nCurrent) {
                     this.id = nCurrent;
                  }

                  @Override
                  public void actionElement() {
                     if (Game.getCiv(Game.player.iCivID).fGold < InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2((float)InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost, 1),
                              Images.gold
                           );
                     } else {
                        boolean reset = InGame_RecruitMercenaries.activeArmyID == this.id;
                        InGame_RecruitMercenaries.activeArmyID = this.id;
                        if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
                           if (reset) {
                              Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                           }
                        } else {
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                           Game.mapModes.setActiveViewID(Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE);
                        }
                     }
                  }

                  @Override
                  public Color getColorBonus() {
                     return Colors.HOVER_GOLD;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("HireMercenaries"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.mercenaries, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           CFG.getNumberWithSpaces("" + InGame_RecruitMercenaries.mercenaryArmies.get(this.id).iCost), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                        )
                     );
                     nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("ArmyWillBeRecruitedInstantly"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(z);
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      buttonY = 0;
      int i = 0;

      for (int iSizex = menuElements.size(); i < iSizex; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("RecruitMercenaries"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_RecruitMercenaries.lTime;
         }
      }, menuX, menuY, menuWidth, i, menuElements, false, true);
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      if (Game.getCiv(Game.player.iCivID).diplomacy.isAtWar()) {
         oSB.setColor(InGame_RecruitArmy.sparksColor);
         InGame_RecruitArmy.sparksAnimation
            .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), Images.sparkHeight, false, true);
         oSB.setColor(Color.WHITE);
      }

      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   public static final void confirm(int provinceID) {
      if (MercenariesManager.recruitMercenaries(Game.player.iCivID, provinceID, mercenaryArmies.get(activeArmyID))) {
         Game.menuManager.addToastGold(Game.lang.get("Done"), Images.v);
         Game.menuManager.setVisibleInGame_Armies(false);
      }
   }
}
