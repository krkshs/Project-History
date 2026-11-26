package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonBuild extends Button {
   public ButtonBuild(int iPosX, int iPosY, boolean isClickable) {
      this.init(
         null,
         0,
         0,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.buildButton).getWidth(),
         ImageManager.getImage(Images.buildButton).getHeight(),
         isClickable,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(Images.buildButton).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(Colors.COLOR_GRAY);
         Images.gradientXY.draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + 2 + iTranslateY, this.getWidth() - 4, CFG.PADDING * 2, false, true);
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + 2 + iTranslateX,
               this.getPosY() + this.getHeight() - 2 - CFG.PADDING * 2 + iTranslateY,
               this.getWidth() - 4,
               CFG.PADDING * 2
            );
         oSB.setColor(Color.WHITE);
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ConstructNewBuilding"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
