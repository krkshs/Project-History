package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral_Assign;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_GeneralRecruit extends Menu {
   protected static final int ANIMATION_TIME = 60;
   private long lTime = 0L;

   public InGame_GeneralRecruit() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int buttonX = paddingLeft;
      int buttonGeneralWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 3;
      Game.player.civilizationGeneralsPool.updatePoolOfGenerals(Game.player.iCivID);

      for (int i = 0; i < Game.player.civilizationGeneralsPool.lGenerals.size(); i++) {
         menuElements.add(
            new ButtonArmyGeneral_Assign(
               buttonX,
               buttonY,
               buttonGeneralWidth,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).n,
               Game.player.iCivID,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).getAttack(),
               Game.player.civilizationGeneralsPool.lGenerals.get(i).getDefense(),
               Game.player.civilizationGeneralsPool.lGenerals.get(i).g,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).d,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).m,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).y,
               true,
               null,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).sI,
               Game.player.civilizationGeneralsPool.lGenerals.get(i).getCombatExperience()
            )
         );
         menuElements.add(
            new ButtonGame(
               Game.lang.get("Recruit"),
               CFG.FONT_REGULAR,
               -1,
               buttonX,
               buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING,
               buttonGeneralWidth,
               true
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).fGold < GeneralManager.getRecruitGoldCost(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ",
                           CFG.getPrecision2((float)GeneralManager.getRecruitGoldCost(Game.player.iCivID), 100),
                           Images.gold
                        );
                  } else if (Game.getCiv(Game.player.iCivID).fLegacy < GeneralManager.getRecruitLegacyCost(Game.player.iCivID)) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientLegacy") + ": ",
                           CFG.getPrecision2((float)GeneralManager.getRecruitLegacyCost(Game.player.iCivID), 100),
                           Images.legacy
                        );
                  } else {
                     String tName = Game.player.civilizationGeneralsPool.lGenerals.get(this.getCurrent()).n;
                     if (Game.player.civilizationGeneralsPool.recruitGeneralID(Game.player.iCivID, this.getCurrent(), true)) {
                        InGame_Info.iCivID = Game.player.iCivID;
                        InGame_Info.iCivID2 = 0;
                        Game.menuManager.rebuildInGame_Info(Game.lang.get("General"), Game.lang.get("Hired") + ": " + tName);
                        InGame_Info.imgID = Images.infoGeneral;
                     }

                     Game.menuManager.setVisibleInGame_GeneralRecruit(false);
                     if (Game.menuManager.getVisibleInGame_Generals()) {
                        Game.menuManager.rebuildInGame_Generals();
                        Game.menuManager.setVisibleInGame_Generals(true);
                        InGame_Generals.lTime = 0L;
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitGeneral"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + GeneralManager.getRecruitGoldCost(Game.player.iCivID), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LegacyPoints") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + GeneralManager.getRecruitLegacyCost(Game.player.iCivID), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.legacy, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         if (i % 3 == 2) {
            buttonX = paddingLeft;
            buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }
      }

      if (menuElements.size() > 0) {
         buttonY = menuElements.get(menuElements.size() - 1).getPosY() + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      }

      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      int costW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + GeneralManager.getRecruitGoldCost(Game.player.iCivID),
            Images.gold,
            paddingLeft,
            buttonY,
            costW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         )
      );
      menuElements.add(
         new TextBonus(
            Game.lang.get("LegacyPoints") + ": ",
            "" + GeneralManager.getRecruitLegacyCost(Game.player.iCivID),
            Images.legacy,
            paddingLeft + costW + CFG.PADDING,
            buttonY,
            costW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG("", true, false, Images.title600) {
         @Override
         public long getTime() {
            return InGame_GeneralRecruit.this.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (this.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT * 2 / 5 + (int)(CFG.BUTTON_HEIGHT * 2 / 5 * ((float)(CFG.currentTimeMillis - this.lTime) / 60.0F));
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
         Images.insideTop600,
         Images.insideBot600
      );
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("SelectGeneralToRecruit"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      this.lTime = CFG.currentTimeMillis;
   }
}
