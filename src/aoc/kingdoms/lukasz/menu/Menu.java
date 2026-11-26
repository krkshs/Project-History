package aoc.kingdoms.lukasz.menu;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Menu {
   private List<MenuElement> menuElements = new ArrayList<>();
   private int iMenuElementsSize;
   private int iPosX;
   private int iPosY;
   private int iWidth;
   private int iHeight;
   private boolean visible = true;
   private MenuTitle menuTitle = null;
   private boolean lockHoverOverMenuBackground = false;
   public Menu.MenuClose menuClose;
   private boolean closeable = false;
   private int iMenuPosX;
   private int iNewMenuPositionX;
   private int iMaxSliderPositionX;
   private boolean scrollableX = false;
   private int iMenuPosY;
   private int iNewMenuPositionY;
   protected int iMaxSliderPositionY;
   private boolean scrollableY = false;
   private boolean scrollModeY = false;
   private int iScrollPosY = -1;
   private int iScrollPosY2 = -1;
   private float fScrollNewMenuPosY = 0.0F;
   private boolean scrollModeX = false;
   private int iScrollPosX = -1;
   private int iScrollPosX2 = -1;
   private float fScrollNewMenuPosX = 0.0F;
   public final float SCROLL_MENU_UPDATE = 0.97F;
   public boolean drawScrollPositionAlways = true;
   public boolean drawScrollPositionAlways2 = true;
   public int scrollExtraPosX = 0;
   private final Color COLOR_SCROLL_POSITION = new Color(0.039215688F, 0.039215688F, 0.078431375F, 0.4F);
   private final Color COLOR_SCROLL_POSITION_INVIEW = new Color(0.50980395F, 0.42352942F, 0.23529412F, 0.2F);
   private final Color COLOR_SCROLL_POSITION_INVIEW_ACTIVE = new Color(0.50980395F, 0.47058824F, 0.27450982F, 0.6F);

   public void initCloseMenu() {
      this.menuClose = new Menu.MenuClose() {
         @Override
         public int getCloseMenu_PosX() {
            return Menu.this.getPosX() + Menu.this.getWidth() - this.getCloseMenu_Width();
         }

         @Override
         public int getCloseMenu_PosY() {
            return Menu.this.menuTitle != null ? Menu.this.getPosY() - Menu.this.menuTitle.getHeight() : Menu.this.getPosY();
         }

         @Override
         public int getCloseMenu_Width() {
            return ImageManager.getImage(Images.btn_close).getWidth();
         }

         @Override
         public int getCloseMenu_Height() {
            return ImageManager.getImage(Images.btn_close).getHeight();
         }
      };
   }

   protected final void initMenu(MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, false, false, false);
   }

   protected final void initMenu(MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements, boolean visible) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, false, false, false);
   }

   protected final void initMenu(
      MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements, boolean visible, boolean closeable
   ) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, false, closeable, false);
   }

   protected final void initMenuWithBackButton(MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, true, false, false);
   }

   protected final void initMenuWithBackButton(
      MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements, boolean closeable
   ) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, true, closeable, false);
   }

   protected final void initMenuWithBackButton(
      MenuTitle menuTitle, int iPosX, int iPosY, int iWidth, int iHeight, List<MenuElement> menuElements, boolean visible, boolean closeable
   ) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, true, closeable, false);
   }

   protected final void initMenuWithBackButton(
      MenuTitle menuTitle,
      int iPosX,
      int iPosY,
      int iWidth,
      int iHeight,
      List<MenuElement> menuElements,
      boolean visible,
      boolean closeable,
      boolean lockHoverOverMenuBackground
   ) {
      this.initMenu(menuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, true, closeable, lockHoverOverMenuBackground);
   }

   protected final void initMenu(
      MenuTitle menuTitle,
      int iPosX,
      int iPosY,
      int iWidth,
      int iHeight,
      List<MenuElement> menuElements,
      boolean visible,
      boolean initWithBackButton,
      boolean closeable,
      boolean lockHoverOverMenuBackground
   ) {
      this.iPosX = this.iMenuPosX = this.iNewMenuPositionX = iPosX;
      this.iPosY = this.iMenuPosY = this.iNewMenuPositionY = iPosY;
      this.iWidth = iWidth;
      this.iHeight = iHeight;
      this.closeable = closeable;
      this.visible = visible;
      this.lockHoverOverMenuBackground = lockHoverOverMenuBackground;
      this.menuTitle = menuTitle;
      this.iMenuElementsSize = menuElements.size();
      if (initWithBackButton) {
         int tempMaxY = 0;

         for (int i = 0; i < this.iMenuElementsSize; i++) {
            if (menuElements.get(i).getPosY() + menuElements.get(i).getHeight() > tempMaxY) {
               tempMaxY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight();
            }
         }

         menuElements.get(0).setPosY(tempMaxY + CFG.PADDING);
         if (tempMaxY > iHeight - CFG.PADDING - menuElements.get(0).getHeight()) {
            menuElements.get(0).setPosY(tempMaxY + CFG.PADDING);
         } else {
            menuElements.get(0).setPosY(iHeight - menuElements.get(0).getHeight());
         }
      }

      this.menuElements = menuElements;
      if (closeable) {
         this.initCloseMenu();
      }

      this.updateScrollable();
      this.updateMenuElements_IsInView();
      this.updateLanguage();
   }

   public final void updateScrollable() {
      this.iMaxSliderPositionY = this.iMaxSliderPositionX = 0;

      for (int i = 0; i < this.iMenuElementsSize; i++) {
         if (this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() > this.iMaxSliderPositionY) {
            this.iMaxSliderPositionY = this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight();
         }

         if (this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() > this.iMaxSliderPositionX) {
            this.iMaxSliderPositionX = this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth();
         }
      }

      this.scrollableX = this.iMaxSliderPositionX > this.getWidth();
      this.scrollableY = this.iMaxSliderPositionY > this.iHeight;
      if (this.scrollableY) {
         this.updateMenuPosY(this.iPosY);
      }

      if (this.scrollableX) {
         this.updateMenuPosX(this.iPosX);
      }
   }

   public void updateLanguage() {
      try {
         for (int i = 0; i < this.getMenuElementsSize(); i++) {
            this.getMenuElement(i).updateLanguage();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public void update() {
      if (this.scrollModeY) {
         if (Math.abs(this.fScrollNewMenuPosY) > 1.0F) {
            this.updateMenuPosY(this.iMenuPosY + (int)this.fScrollNewMenuPosY);
            this.fScrollNewMenuPosY *= 0.97F;
         } else {
            this.scrollModeY = false;
         }
      }

      if (this.scrollModeX) {
         if (Math.abs(this.fScrollNewMenuPosX) > 1.0F) {
            this.updateMenuPosX(this.iMenuPosX + (int)this.fScrollNewMenuPosX);
            this.fScrollNewMenuPosX *= 0.97F;
         } else {
            this.scrollModeX = false;
         }
      }

      boolean updateMenuElementsInView = false;
      if (this.scrollableX && this.iNewMenuPositionX != this.iMenuPosX) {
         this.iMenuPosX = this.iNewMenuPositionX;
         updateMenuElementsInView = true;
      }

      if (this.iNewMenuPositionY != this.iMenuPosY) {
         this.iMenuPosY = this.iNewMenuPositionY;
         updateMenuElementsInView = true;
      }

      if (updateMenuElementsInView) {
         this.updateMenuElements_IsInView();
      }
   }

   public void extraAction() {
   }

   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.drawView(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   private void drawView(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
      this.drawMenu(oSB, iTranslateX, iTranslateY, menuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public void beginClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      this.drawBackgroundMode(oSB, menuIsActive);
      Renderer.clipView_Start(oSB, this.getPosX() + iTranslateX, CFG.GAME_HEIGHT - this.getPosY() - iTranslateY, this.getWidth(), -this.getHeight());
   }

   public void drawMenu(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      this.drawMenuElements(oSB, iTranslateX, iTranslateY, menuIsActive);
      this.drawMenu_Scroll(oSB, iTranslateX, iTranslateY, menuIsActive);
   }

   public void drawMenu_Scroll(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      this.drawScrollPositionX(oSB, iTranslateX, iTranslateY, menuIsActive);
      this.drawScrollPositionY(oSB, iTranslateX, iTranslateY, menuIsActive);
   }

   public void endClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.clipView_End(oSB);
      this.drawTitle(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (this.getCloseable()) {
         this.drawCloseButton(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus == Status.CLOSE_HOVERED);
      }
   }

   public void drawHover(SpriteBatch oSB, int iTranslateX, int iTranslateY, int nMenuElementID) {
      try {
         this.getMenuElement(nMenuElementID)
            .drawMenuElementHover(
               oSB,
               this.getMenuPosX() + iTranslateX,
               this.getMenuPosY() + iTranslateY,
               this.getMenuElementIsActive(true, Game.menuManager.getActiveMenuElementID())
            );
      } catch (Exception var6) {
      }
   }

   public void drawScrollPositionY(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      try {
         if (this.drawScrollPositionAlways2 && this.scrollableY && this.getHeight() < this.iMaxSliderPositionY) {
            if (Game.menuManager.getMenu_MoveInnerElements()) {
               oSB.setColor(this.COLOR_SCROLL_POSITION_INVIEW_ACTIVE);
            } else {
               oSB.setColor(this.COLOR_SCROLL_POSITION_INVIEW);
            }

            ImageManager.getImage(Images.scroll_position_active)
               .draw2(
                  oSB,
                  this.getPosX() + this.scrollExtraPosX + this.getWidth() - Images.boxTitleBORDERWIDTH - Images.boxTitleBORDERWIDTH - 1 + iTranslateX,
                  this.getPosY()
                     + (this.getHeight() - 100 * this.getHeight() / this.iMaxSliderPositionY * this.getHeight() / 100)
                        * (this.getPosY() - this.getMenuPosY())
                        / (this.iMaxSliderPositionY - this.getHeight())
                     + iTranslateY,
                  Images.boxTitleBORDERWIDTH,
                  this.getHeight() * 100 / this.iMaxSliderPositionY * this.getHeight() / 100 - ImageManager.getImage(Images.scroll_position_active).getHeight()
               );
            ImageManager.getImage(Images.scroll_position_active)
               .draw(
                  oSB,
                  this.getPosX() + this.scrollExtraPosX + this.getWidth() - Images.boxTitleBORDERWIDTH - Images.boxTitleBORDERWIDTH - 1 + iTranslateX,
                  this.getPosY()
                     + (this.getHeight() - 100 * this.getHeight() / this.iMaxSliderPositionY * this.getHeight() / 100)
                        * (this.getPosY() - this.getMenuPosY())
                        / (this.iMaxSliderPositionY - this.getHeight())
                     + this.getHeight() * 100 / this.iMaxSliderPositionY * this.getHeight() / 100
                     - ImageManager.getImage(Images.scroll_position_active).getHeight()
                     + iTranslateY,
                  Images.boxTitleBORDERWIDTH,
                  ImageManager.getImage(Images.scroll_position_active).getHeight(),
                  false,
                  true
               );
            ImageManager.getImage(Images.gradientXYVertical)
               .draw2(
                  oSB,
                  this.getPosX() + this.scrollExtraPosX + this.getWidth() - Images.boxTitleBORDERWIDTH - Images.boxTitleBORDERWIDTH - 1 + iTranslateX,
                  this.getPosY()
                     + (this.getHeight() - 100 * this.getHeight() / this.iMaxSliderPositionY * this.getHeight() / 100)
                        * (this.getPosY() - this.getMenuPosY())
                        / (this.iMaxSliderPositionY - this.getHeight())
                     + iTranslateY,
                  Images.boxTitleBORDERWIDTH,
                  this.getHeight() * 100 / this.iMaxSliderPositionY * this.getHeight() / 100,
                  true,
                  false
               );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public void drawScrollPositionX(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      try {
         if ((this.drawScrollPositionAlways || menuIsActive) && this.scrollableX && this.getWidth() < this.iMaxSliderPositionX) {
            if (Game.menuManager.getMenu_MoveInnerElements()) {
               oSB.setColor(this.COLOR_SCROLL_POSITION_INVIEW_ACTIVE);
            } else {
               oSB.setColor(this.COLOR_SCROLL_POSITION_INVIEW);
            }

            ImageManager.getImage(Images.scroll_position_active)
               .draw2(
                  oSB,
                  this.getPosX()
                     + (this.getWidth() - 100 * this.getWidth() / this.iMaxSliderPositionX * this.getWidth() / 100)
                        * (this.getPosX() - this.getMenuPosX())
                        / (this.iMaxSliderPositionX - this.getWidth())
                     + iTranslateX,
                  this.getPosY()
                     + this.getHeight()
                     - CFG.PADDING * 2
                     + 1
                     - (
                        this.getWidth() * 100 / this.iMaxSliderPositionX * this.getWidth() / 100
                           - ImageManager.getImage(Images.scroll_position_active).getHeight()
                     )
                     + iTranslateY,
                  ImageManager.getImage(Images.scroll_position_active).getWidth(),
                  this.getWidth() * 100 / this.iMaxSliderPositionX * this.getWidth() / 100 - ImageManager.getImage(Images.scroll_position_active).getHeight(),
                  -90.0F,
                  false,
                  true
               );
            ImageManager.getImage(Images.scroll_position_active)
               .draw2(
                  oSB,
                  this.getPosX()
                     + (this.getWidth() - 100 * this.getWidth() / this.iMaxSliderPositionX * this.getWidth() / 100)
                        * (this.getPosX() - this.getMenuPosX())
                        / (this.iMaxSliderPositionX - this.getWidth())
                     + this.getWidth() * 100 / this.iMaxSliderPositionX * this.getWidth() / 100
                     - ImageManager.getImage(Images.scroll_position_active).getHeight()
                     + iTranslateX,
                  this.getPosY() + this.getHeight() - CFG.PADDING * 2 + 1 - ImageManager.getImage(Images.scroll_position_active).getHeight() + iTranslateY,
                  ImageManager.getImage(Images.scroll_position_active).getWidth(),
                  ImageManager.getImage(Images.scroll_position_active).getHeight(),
                  -90.0F
               );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var6) {
      }
   }

   public final void drawMenuElements(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive) {
      try {
         for (int i = this.iMenuElementsSize - 1; i >= 0; i--) {
            try {
               if (this.menuElements.get(i).getVisible() && this.menuElements.get(i).getIsInView()) {
                  this.menuElements
                     .get(i)
                     .draw(
                        oSB, this.getMenuPosX() + iTranslateX, this.getMenuPosY() + iTranslateY, this.getMenuElementIsActive(menuIsActive, i), this.scrollableY
                     );
               }
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public void updateMenuElements_IsInView() {
      for (int i = 0; i < this.iMenuElementsSize; i++) {
         this.menuElements.get(i).setIsInView(this.getMenuElementIsInView_Y(i) && this.getMenuElementIsInView_X(i));
      }
   }

   public void updateMenuElements_IsInView_X() {
      for (int i = 0; i < this.iMenuElementsSize; i++) {
         this.menuElements.get(i).setIsInView(this.getMenuElementIsInView_X(i));
      }
   }

   private final boolean getMenuElementIsInView_Y(int i) {
      return this.menuElements.get(i).getPosY() + this.getMenuPosY() > this.getPosY()
            && this.menuElements.get(i).getPosY() + this.getMenuPosY() < this.getPosY() + this.getHeight()
         || this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() + this.getMenuPosY() > this.getPosY()
            && this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() + this.getMenuPosY() < this.getPosY() + this.getHeight()
         || this.menuElements.get(i).getPosY() + this.getMenuPosY() <= this.getPosY()
            && this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() + this.getMenuPosY() >= this.getPosY() + this.getHeight();
   }

   private final boolean getMenuElementIsInView_X(int i) {
      return this.menuElements.get(i).getPosX() + this.getMenuPosX() >= this.getPosX()
            && this.menuElements.get(i).getPosX() + this.getMenuPosX() < this.getPosX() + this.getWidth()
         || this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() + this.getMenuPosX() > this.getPosX()
            && this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() + this.getMenuPosX() <= this.getPosX() + this.getWidth()
         || this.getPosX() > this.menuElements.get(i).getPosX() + this.getMenuPosX()
            && this.getPosX() < this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() + this.getMenuPosX();
   }

   public boolean getMenuElementIsActive(boolean menuIsActive, int i) {
      return menuIsActive ? i == Game.menuManager.getActiveMenuElementID() : false;
   }

   public void drawTitle(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (this.menuTitle != null) {
         this.menuTitle.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), titleStatus);
      }

      if (menuIsActive) {
         if (Game.menuManager.getMenuResizeMode()) {
            this.drawMenuBorder(oSB);
            this.drawMenuResizeRect(oSB);
         } else if (Game.menuManager.getMoveMenu_ByTitleMode()) {
            this.drawMenuBorder(oSB);
         }
      }
   }

   public final void drawMenuBorder(SpriteBatch oSB) {
      oSB.setColor(0.196F, 0.196F, 0.196F, 1.0F);
      Images.pix.draw(oSB, this.getPosX(), this.getPosY(), 1, this.getHeight());
      Images.pix.draw(oSB, this.getPosX() + this.getWidth() - 1, this.getPosY(), 1, this.getHeight());
      Images.pix.draw(oSB, this.getPosX(), this.getPosY(), this.getWidth(), -1);
      Images.pix.draw(oSB, this.getPosX(), this.getPosY() + this.getHeight() - 1, this.getWidth(), -1);
      oSB.setColor(Color.WHITE);
   }

   public final void drawMenuResizeRect(SpriteBatch oSB) {
      oSB.setColor(0.196F, 0.196F, 0.196F, 0.95F);
      if (Game.menuManager.getMenuResizeLEFT()) {
         Images.pix
            .draw(oSB, this.getPosX(), this.getPosY() + this.getHeight() - CFG.PADDING * CFG.PADDING, CFG.PADDING * CFG.PADDING, CFG.PADDING * CFG.PADDING);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.35F));
         ImageManager.getImage(Images.pickerEdge)
            .draw(
               oSB,
               this.getPosX(),
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pickerEdge).getHeight(),
               ImageManager.getImage(Images.pickerEdge).getWidth(),
               ImageManager.getImage(Images.pickerEdge).getHeight(),
               true,
               false
            );
      } else {
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - 1 - CFG.PADDING * CFG.PADDING,
               this.getPosY() + this.getHeight() - CFG.PADDING * CFG.PADDING,
               CFG.PADDING * CFG.PADDING,
               CFG.PADDING * CFG.PADDING
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.35F));
         ImageManager.getImage(Images.pickerEdge)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - ImageManager.getImage(Images.pickerEdge).getWidth(),
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pickerEdge).getHeight(),
               ImageManager.getImage(Images.pickerEdge).getWidth(),
               ImageManager.getImage(Images.pickerEdge).getHeight(),
               false,
               false
            );
      }

      oSB.setColor(Color.WHITE);
   }

   public final void drawBackgroundMode(SpriteBatch oSB, boolean menuIsActive) {
      if (menuIsActive && (Game.menuManager.getMenuResizeMode() || Game.menuManager.getMoveMenu_ByTitleMode())) {
         oSB.setColor(new Color(0.1F, 0.1F, 0.1F, 0.05F));
         ImageManager.getImage(Images.patt).draw2(oSB, 0, -ImageManager.getImage(Images.patt).getHeight(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(Color.WHITE);
      }
   }

   public void drawCloseButton(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, boolean isHovered) {
      if (Game.menuManager.getCloseMenuMode() && menuIsActive) {
         ImageManager.getImage(Images.btnh_close).draw(oSB, this.menuClose.getCloseMenu_PosX() + iTranslateX, this.menuClose.getCloseMenu_PosY() + iTranslateY);
      } else if (isHovered) {
         oSB.setColor(new Color(1.0F, 0.0F, 0.0F, 0.425F));
         ImageManager.getImage(Images.btnh_close).draw(oSB, this.menuClose.getCloseMenu_PosX() + iTranslateX, this.menuClose.getCloseMenu_PosY() + iTranslateY);
         oSB.setColor(Color.WHITE);
      } else {
         ImageManager.getImage(Images.btn_close).draw(oSB, this.menuClose.getCloseMenu_PosX() + iTranslateX, this.menuClose.getCloseMenu_PosY() + iTranslateY);
      }
   }

   public void actionElement(int nMenuElementID) {
      try {
         this.getMenuElement(nMenuElementID).actionElement();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public void actionElementPPM(int nMenuElementID) {
      try {
         this.getMenuElement(nMenuElementID).actionElementPPM();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public void onBackPressed() {
   }

   public void onMenuPressed() {
   }

   public void actionCloseMenu() {
      this.setVisible(false);
      this.actionCloseMenu_Sound();
   }

   public void actionCloseMenu_Sound() {
      Game.soundsManager.playSound(Game.soundsManager.getClickMain());
   }

   public void onHovered() {
   }

   public final void updateMenuPosX(int nMenuPosX) {
      try {
         if (nMenuPosX > this.getPosX()) {
            this.iNewMenuPositionX = this.getPosX();
            Game.menuManager.setUpdateSliderMenuPosX(true);
         } else if (nMenuPosX < this.getWidth() + this.getPosX() - this.iMaxSliderPositionX) {
            this.iNewMenuPositionX = this.getWidth() + this.getPosX() - this.iMaxSliderPositionX;
            Game.menuManager.setUpdateSliderMenuPosX(true);
         } else {
            this.iNewMenuPositionX = nMenuPosX;
         }
      } catch (Exception var3) {
      }
   }

   public final void updateMenuPosY(int nMenuPosY) {
      try {
         if (!this.scrollableY) {
            this.iNewMenuPositionY = this.getPosY();
            Game.menuManager.setUpdateSliderMenuPosY(false);
            this.scrollModeY = false;
            return;
         }

         if (nMenuPosY > this.getPosY()) {
            this.iNewMenuPositionY = this.getPosY();
            Game.menuManager.setUpdateSliderMenuPosY(true);
            this.scrollModeY = false;
         } else if (nMenuPosY < this.getHeight() + this.getPosY() - this.iMaxSliderPositionY) {
            this.iNewMenuPositionY = this.getHeight() + this.getPosY() - this.iMaxSliderPositionY;
            Game.menuManager.setUpdateSliderMenuPosY(true);
            this.scrollModeY = false;
         } else {
            this.iNewMenuPositionY = nMenuPosY;
         }
      } catch (Exception var3) {
      }
   }

   public final void scrollTheMenu() {
      if (this.scrollableY && this.iScrollPosY > 0 && this.iScrollPosY2 > 0 && Math.abs(this.iScrollPosY - this.iScrollPosY2) > 3.0F * CFG.DENSITY) {
         this.fScrollNewMenuPosY = (this.iScrollPosY - this.iScrollPosY2) * 1.45F;
         this.scrollModeY = true;
      }

      if (this.scrollableX && this.iScrollPosX > 0 && this.iScrollPosX2 > 0 && Math.abs(this.iScrollPosX - this.iScrollPosX2) > 3) {
         this.fScrollNewMenuPosX = (this.iScrollPosX - this.iScrollPosX2) * 1.45F;
         this.scrollModeX = true;
      }

      this.resetScrollINFO();
   }

   private final void resetScrollINFO() {
      this.iScrollPosY = this.iScrollPosY2 = this.iScrollPosX = this.iScrollPosX2 = -1;
   }

   public final void stopScrolling() {
      this.resetScrollINFO();
      this.scrollModeY = this.scrollModeX = false;
   }

   public final void updatedButtonsWidth(int iStartPosX, int iMinWidth) {
      for (int i = 0; i < this.getMenuElementsSize(); i++) {
         iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + CFG.PADDING;
      }

      this.updateScrollable();
   }

   public final void updatedButtonsWidth_Padding(int iStartPosX, int iMinWidth, int iPadding) {
      for (int i = 0; i < this.getMenuElementsSize(); i++) {
         iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + iPadding;
      }

      this.updateScrollable();
   }

   public final void updatedButtonsWidthFromToID(int iStartButtonID, int iEndButtonID, int iStartPosX, int iMinWidth) {
      for (int i = iStartButtonID; i < iEndButtonID; i++) {
         iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + CFG.PADDING;
      }

      this.updateScrollable();
   }

   public final int updateButtonWidth(int iButtonID, int iStartPosX, int iMinWidth) {
      if (this.getMenuElement(iButtonID).getTextWidth() + CFG.PADDING * 4 > iMinWidth) {
         this.getMenuElement(iButtonID).setWidth(this.getMenuElement(iButtonID).getTextWidth() + CFG.PADDING * 4);
      } else {
         this.getMenuElement(iButtonID).setWidth(iMinWidth);
      }

      this.getMenuElement(iButtonID).setPosX(iStartPosX);
      this.updateScrollable();
      return this.getMenuElement(iButtonID).getWidth();
   }

   public final int getMenuElementsSize() {
      return this.iMenuElementsSize;
   }

   public final MenuElement getMenuElement(int iID) {
      return this.menuElements.get(iID);
   }

   public final void setMenuElement(int iID, MenuElement nMenuElement) {
      this.menuElements.set(iID, null);
      this.menuElements.set(iID, nMenuElement);
   }

   public int getPosX() {
      return this.iPosX;
   }

   public void setPosX(int iPosX) {
      this.iPosX = iPosX;
      this.iMenuPosX = iPosX;
      this.updateMenuPosX(this.iMenuPosX);
   }

   public void setPosX_Just(int iPosX) {
      this.iPosX = iPosX;
   }

   public final void setPosX_Force(int iPosX) {
      this.iPosX = iPosX;
      this.iMenuPosX = iPosX;
      this.iNewMenuPositionX = iPosX;
      Game.menuManager.setUpdateSliderMenuPosX(false);
   }

   public int getPosY() {
      return this.iPosY;
   }

   public void setPosY(int iPosY) {
      this.iPosY = iPosY;
      this.iMenuPosY = iPosY;
      this.updateMenuPosY(this.iMenuPosY);
   }

   public void setPosY_Just(int iPosY) {
      this.iPosY = iPosY;
   }

   public int getWidth() {
      return this.iWidth;
   }

   public boolean setWidth(int iWidth) {
      if (iWidth < CFG.GAME_WIDTH) {
         if (iWidth < this.getMinWidth()) {
            this.iWidth = this.getMinWidth();
            return false;
         } else {
            this.iWidth = iWidth;
            return true;
         }
      } else {
         this.iWidth = CFG.GAME_WIDTH;
         return true;
      }
   }

   public boolean setWidth_Resize(int iWidth) {
      if (iWidth < CFG.GAME_WIDTH) {
         if (iWidth < this.getMinWidth()) {
            iWidth = this.getMinWidth();
            this.setPosX(this.getPosX() + (this.iWidth - iWidth));
            this.iWidth = iWidth;
            return false;
         } else {
            this.setPosX(this.getPosX() + (this.iWidth - iWidth));
            this.iWidth = iWidth;
            return true;
         }
      } else {
         this.setPosX(this.getPosX() + (this.iWidth - iWidth));
         this.iWidth = CFG.GAME_WIDTH;
         return true;
      }
   }

   public final int getMinWidth() {
      try {
         return CFG.BUTTON_HEIGHT;
      } catch (Exception var2) {
         return CFG.BUTTON_HEIGHT;
      }
   }

   public int getHeight() {
      return this.iHeight;
   }

   public void setHeight(int iHeight) {
      this.iHeight = iHeight;
      if (iHeight < this.getMinHeight()) {
         this.iHeight = this.getMinHeight();
      }

      if (iHeight + this.getPosY() + (this.menuTitle != null ? this.menuTitle.getHeight() : 0) >= CFG.GAME_HEIGHT) {
         this.iHeight = CFG.GAME_HEIGHT - (this.getPosY() + (this.menuTitle != null ? this.menuTitle.getHeight() : 0));
      }

      this.updateScrollable();
   }

   public final int getMinHeight() {
      return CFG.PADDING + CFG.BUTTON_HEIGHT;
   }

   public final MenuTitle getTitle() {
      return this.menuTitle;
   }

   public final boolean getScrollableY() {
      return this.scrollableY;
   }

   public final void setMenuPosY(int iMenuPosY) {
      this.updateMenuPosY(iMenuPosY);
   }

   public int getMenuPosY() {
      return this.iMenuPosY;
   }

   public final int getNewMenuPosY() {
      return this.iNewMenuPositionY;
   }

   public final int getNewMenuPosX() {
      return this.iNewMenuPositionX;
   }

   public final boolean getScrollableX() {
      return this.scrollableX;
   }

   public final void setMenuPosX(int iMenuPosX) {
      this.updateMenuPosX(iMenuPosX);
   }

   public int getMenuPosX() {
      return this.iMenuPosX;
   }

   public boolean getVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public final boolean getCloseable() {
      return this.closeable;
   }

   public final boolean getMoveable() {
      return this.menuTitle == null ? false : this.menuTitle.getMoveable();
   }

   public final boolean getResizable() {
      return this.menuTitle == null ? false : this.menuTitle.getResizable();
   }

   public final void setScrollPosY(int iScrollPosY) {
      this.iScrollPosY2 = this.iScrollPosY;
      this.iScrollPosY = iScrollPosY;
   }

   public final int getScrollPosY() {
      return this.iScrollPosY;
   }

   public final void setScrollPosX(int iScrollPosX) {
      this.iScrollPosX2 = this.iScrollPosX;
      this.iScrollPosX = iScrollPosX;
   }

   public final boolean getScrollModeY() {
      return this.scrollModeY;
   }

   public final boolean getLockHoverOverMenuBackground() {
      return this.lockHoverOverMenuBackground;
   }

   public void disableButtons() {
   }

   public void closeMenu() {
   }

   public void dispose() {
      for (int i = 0; i < this.getMenuElementsSize(); i++) {
         this.getMenuElement(i).dispose();
      }
   }

   interface MenuClose {
      int getCloseMenu_PosX();

      int getCloseMenu_PosY();

      int getCloseMenu_Width();

      int getCloseMenu_Height();
   }
}
