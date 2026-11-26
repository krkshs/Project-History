package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_Active;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Revolutions extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_Revolutions() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      List<Integer> revolution = Game.revolutionManager.getRevolutionaryProvinces_Sorted(Game.player.iCivID);
      if (revolution.size() > 0) {
         int var13 = 0;
         int nx0 = menuWidth - paddingLeft * 2 - CFG.PADDING * 2 - CFG.BUTTON_WIDTH * 2 + (paddingLeft - Images.boxTitleBORDERWIDTH) + CFG.PADDING / 2;
         int nx1 = CFG.BUTTON_WIDTH + CFG.PADDING;
         menuElements.add(
            new Text_TitleBlueSort(
               false, false, Game.lang.get("Province"), -1, Images.boxTitleBORDERWIDTH, var13, nx0, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_BOLD_SMALL
            )
         );
         menuElements.add(
            new Text_TitleBlueSort(false, false, Game.lang.get("Population"), -1, nx0, var13, nx1, CFG.TEXT_HEIGHT + CFG.PADDING * 6, CFG.FONT_BOLD_SMALL)
         );
         menuElements.add(
            new Text_TitleBlueSort(
               true,
               false,
               Game.lang.get("Unrest"),
               -1,
               nx0 + nx1,
               var13,
               menuWidth - (nx0 + nx1) - Images.boxTitleBORDERWIDTH,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6,
               CFG.FONT_BOLD_SMALL
            )
         );
         buttonY = var13 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         for (int i = 0; i < revolution.size(); i++) {
            menuElements.add(
               new ButtonCurrentSituation(
                  Game.getProvince(revolution.get(i)).getProvinceName(),
                  Images.revolutionRisk,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2 - CFG.PADDING * 2 - CFG.BUTTON_WIDTH * 2,
                  CFG.BUTTON_HEIGHT3,
                  ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
                  true
               ) {
                  int id;

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverUnrest(this.id, false, true);
                  }

                  @Override
                  public void setCurrent(int nCurrent) {
                     this.id = nCurrent;
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionUnrest(this.id)) {
                        Game.menuManager.rebuildInGame_Revolutions_SavePos();
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     Game.mapCoords.centerToProvinceID(this.id);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(revolution.get(i));
            menuElements.add(
               new ButtonStatsRect_Active(
                  "" + CFG.getShortNumber(Game.getProvince(revolution.get(i)).getPopulationTotal()),
                  menuWidth - paddingLeft - CFG.BUTTON_WIDTH * 2 - CFG.PADDING,
                  buttonY,
                  CFG.BUTTON_WIDTH,
                  CFG.BUTTON_HEIGHT3,
                  CFG.FONT_BOLD_SMALL
               ) {
                  int id;

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverPopulation(this.id, false);
                  }

                  @Override
                  public void setCurrent(int nCurrent) {
                     this.id = nCurrent;
                  }

                  @Override
                  protected Color getColor(boolean isActive) {
                     return Colors.getColorPopulation(isActive, this.getIsHovered());
                  }

                  @Override
                  public void actionElementPPM() {
                     Game.mapCoords.centerToProvinceID(this.id);
                  }
               }
            );
            menuElements.add(
               new ButtonStatsRect_Active(
                  "" + CFG.getPrecision2(Game.getProvince(revolution.get(i)).getRevulutionaryRisk(), 100) + "%",
                  menuWidth - paddingLeft - CFG.BUTTON_WIDTH,
                  buttonY,
                  CFG.BUTTON_WIDTH,
                  CFG.BUTTON_HEIGHT3,
                  CFG.FONT_BOLD_SMALL
               ) {
                  int id;
                  float lastValue = 0.0F;

                  @Override
                  public String getTextToDraw() {
                     if (Game.getProvince(this.id).getRevulutionaryRisk() != this.lastValue) {
                        this.lastValue = Game.getProvince(this.id).getRevulutionaryRisk();
                        this.setText("" + CFG.getPrecision2(this.lastValue, 100) + "%");
                     }

                     return super.getTextToDraw();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverUnrest(this.id, false, true);
                  }

                  @Override
                  public void setCurrent(int nCurrent) {
                     this.id = nCurrent;
                  }

                  @Override
                  protected Color getColor(boolean isActive) {
                     return Colors.getColorNegative(isActive, this.getIsHovered());
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionUnrest(this.id)) {
                        Game.menuManager.rebuildInGame_Revolutions_SavePos();
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     Game.mapCoords.centerToProvinceID(this.id);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(revolution.get(i));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      } else {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT) {
               @Override
               public void actionElement() {
                  Game.menuManager.setVisibleInGame_PopUp(false);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, tMenuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("RevolutionaryMovements"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_Revolutions.lTime;
         }
      }, menuX, menuY, menuWidth, tMenuHeight, menuElements, false, true);
      this.drawScrollPositionAlways = false;
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
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop500,
         Images.insideBot500
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
