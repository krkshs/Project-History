package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement {
   protected MenuElement_Type typeOfElement;
   private int iPosX;
   private int iPosY;
   private int iWidth;
   private int iHeight;
   private boolean isClickable = true;
   private boolean isVisible = true;
   private boolean isInView = false;
   private boolean isHovered = false;
   protected int iCurrent = 0;
   protected int fontID = 0;
   public MenuElement_Hover menuElementHover;

   public void buildElementHover() {
   }

   public void resetElementHover() {
      this.menuElementHover = null;
   }

   public void drawMenuElementHover(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.menuElementHover != null) {
         if (CFG.isAndroid) {
            this.menuElementHover.drawAlwaysOver_Mobile(oSB, Touch.getMousePosX(), Touch.getMousePosY() - Renderer.getHover_ExtraPosY());
         } else {
            this.menuElementHover.draw(oSB, Touch.getMousePosX() + Renderer.getHover_ExtraPosX(), Touch.getMousePosY() + Renderer.getHover_ExtraPosY());
         }
      }
   }

   public boolean getMenuElement_Hover_IsNull() {
      return this.menuElementHover == null;
   }

   public void updateHover(int nPosX, int nPosY, int menuPosX, int menuPosY) {
   }

   protected MenuElement() {
   }

   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
   }

   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
   }

   public void setText(String sText) {
   }

   public void setText2(String sText) {
   }

   public boolean getCheckboxState() {
      return false;
   }

   public void setCheckboxState(boolean checkboxState) {
   }

   public void setCurrent(int nCurrent) {
      this.iCurrent = nCurrent;
   }

   public int getCurrent() {
      return this.iCurrent;
   }

   public void setMin(int iMin) {
   }

   public void setMax(int iMax) {
   }

   public int getValue1() {
      return 0;
   }

   public int getValue2() {
      return 0;
   }

   public boolean getDescription() {
      return false;
   }

   public void setDescription(boolean isDescriptionActive) {
   }

   public boolean getMoveable() {
      return false;
   }

   public boolean getInStatisticsMode() {
      return false;
   }

   public void setInStatisticsMode(boolean inStatisticsMode) {
   }

   public boolean getScrollable() {
      return false;
   }

   public void setScrollable(boolean scrollable) {
   }

   public int getScrollPosX() {
      return 0;
   }

   public void setScrollPosX(int scrollPosX) {
   }

   public int getScrollPosY() {
      return 0;
   }

   public void setScrollPosY(int scrollPosY) {
   }

   public void scrollTheMenu() {
   }

   public void scrollByWheel(int nScoll) {
   }

   public void stopScrolling() {
   }

   public void updateLanguage() {
   }

   public void actionElement() {
   }

   public void actionElementPPM() {
   }

   public void actionElement_ExtraAction() {
   }

   public void updateSlider(int nPosX) {
   }

   public final MenuElement_Type getTypeOfElement() {
      return this.typeOfElement;
   }

   public void setTypeOfElement(MenuElement_Type typeOfElement) {
   }

   public boolean getClickable() {
      return this.isClickable;
   }

   public final void setClickable(boolean isClickable) {
      this.isClickable = isClickable;
   }

   public boolean getVisible() {
      return this.isVisible;
   }

   public void setVisible(boolean isVisible) {
      this.isVisible = isVisible;
   }

   public int getPosX() {
      return this.iPosX;
   }

   public final void setPosX(int iPosX) {
      this.iPosX = iPosX;
   }

   public int getPosY() {
      return this.iPosY;
   }

   public final void setPosY(int iPosY) {
      this.iPosY = iPosY;
   }

   public int getWidth() {
      return this.iWidth;
   }

   public void setWidth(int iWidth) {
      this.iWidth = iWidth;
   }

   public int getHeight() {
      return this.iHeight;
   }

   public final void setHeight(int iHeight) {
      this.iHeight = iHeight;
   }

   public String getText() {
      return "";
   }

   public int getTextWidth() {
      return 0;
   }

   public int getTextHeight() {
      return 0;
   }

   public int getTextPos() {
      return 0;
   }

   public final boolean getIsInView() {
      return this.isInView;
   }

   public final void setIsInView(boolean isInView) {
      this.isInView = isInView;
   }

   public boolean getIsHovered() {
      return this.isHovered;
   }

   public void setIsHovered(boolean isHovered) {
      this.isHovered = isHovered;
   }

   public void updateHovered() {
   }

   public int getSFX() {
      return Game.soundsManager.getClickMain();
   }

   public boolean playSFX_Hovered() {
      return true;
   }

   public int getSFX_Hovered() {
      return Game.soundsManager.getClickMain();
   }

   public String getTextToDraw() {
      return "";
   }

   public void dispose() {
   }

   public boolean canBeHovered() {
      return true;
   }
}
