package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextArmies extends Button {
   public String key;
   public int iProvinceID;
   public int iCivID;
   public String sArmy;
   public int iArmyWidth;
   public int maxArmyPosX;

   public TextArmies(String sText, String sArmy, int nPosX, int nPosY, int nWidth, int nHeight, String key, int iCivID, int iProvinceID, int maxArmyPosX) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, -1, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.sArmy = sArmy;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sArmy);
      this.iArmyWidth = (int)Renderer.glyphLayout.width;
      this.key = key;
      this.iProvinceID = iProvinceID;
      this.iCivID = iCivID;
      this.maxArmyPosX = maxArmyPosX;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.2F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.7F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sArmy,
         this.getPosX() + Math.min(this.maxArmyPosX, this.getWidth()) - this.iArmyWidth - CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT : Colors.BUTTON_TEXT_DISABLED;
      }
   }

   @Override
   public int getCurrent() {
      return this.iProvinceID;
   }

   @Override
   public void actionElement() {
      int nArmyID = Game.getProvince(this.iProvinceID).getArmyKeyID(this.key);
      if (nArmyID < 0) {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            int outID = Game.getProvince(i).getArmyKeyID(this.key, this.iCivID);
            if (outID >= 0) {
               this.iProvinceID = i;
               nArmyID = outID;
               break;
            }
         }
      }

      if (nArmyID >= 0) {
         if (Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(Game.getProvince(this.iProvinceID).getArmy(nArmyID).key)) {
            Game.mapCoords.centerToProvinceID(this.iProvinceID);
         } else {
            Game.clearActiveArmy();
            Game.HoveredArmy nHA = new Game.HoveredArmy();
            nHA.key = Game.getProvince(this.iProvinceID).getArmy(nArmyID).key;
            nHA.iCivID = Game.getProvince(this.iProvinceID).getArmy(nArmyID).civID;
            nHA.iProvinceID = this.iProvinceID;
            nHA.iArmyID = nArmyID;
            Game.addActiveArmy(nHA);
            ProvinceTouchExtraAction.actionUp_SetActiveArmy();
         }

         if (Game.menuManager.getVisibleInGame_Generals()) {
            Game.menuManager.setVisibleInGame_Generals(false);
         }

         if (Game.menuManager.getVisibleInGame_GeneralRecruit()) {
            Game.menuManager.setVisibleInGame_GeneralRecruit(false);
         }
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements, true);
   }
}
