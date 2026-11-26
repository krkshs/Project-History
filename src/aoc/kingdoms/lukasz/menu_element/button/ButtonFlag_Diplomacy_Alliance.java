package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menusInGame.AllianceSpecial.InGame_AllianceSpecial;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonFlag_Diplomacy_Alliance extends Button {
   public int allianceID;

   public ButtonFlag_Diplomacy_Alliance(int allianceID, int iPosX, int iPosY, boolean isClickable) {
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
         ImageManager.getImage(Images.flagDiplomacyOver).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.allianceID = allianceID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);
      if (this.getFlagCivID() >= 0) {
         Game.alliancesSpecial_Flag.get(this.allianceID).getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagDiplomacyMask)
         .draw(
            oSB,
            this.getPosX()
               + iTranslateX
               + (ImageManager.getImage(Images.flagDiplomacyOver).getWidth() - ImageManager.getImage(Images.flagDiplomacyMask).getWidth()) / 2,
            this.getPosY()
               + iTranslateY
               + (ImageManager.getImage(Images.flagDiplomacyOver).getHeight() - ImageManager.getImage(Images.flagDiplomacyMask).getHeight()) / 2,
            ImageManager.getImage(Images.flagDiplomacyMask).getWidth(),
            ImageManager.getImage(Images.flagDiplomacyMask).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagDiplomacyOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   public int getFlagCivID() {
      return this.allianceID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial(
            this.allianceID, Game.getCiv(Game.alliancesSpecial.get(this.allianceID).iLeaderCivID).getCivName()
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Civilizations") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(new MenuElement_HoverElement_Type_Text("" + Game.getAllianceSpecialNumOfCivs(this.allianceID), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getCurrent() {
      return this.allianceID;
   }

   @Override
   public void actionElement() {
      if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 35 && InGame_AllianceSpecial.allianceID == this.getCurrent()) {
         Game.menuManager.setVisibleInGame_PopUp(false);
      } else {
         Game.menuManager.hideCourtCiv();
         Game.menuManager.rebuildInGame_AllianceSpecial(this.getCurrent());
      }
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.flagDiplomacyOver).getHeight();
   }

   @Override
   public void actionElementPPM() {
      try {
         if (this.allianceID >= 0
            && Game.alliancesSpecial.get(this.allianceID).iLeaderCivID >= 0
            && Game.getCiv(Game.alliancesSpecial.get(this.allianceID).iLeaderCivID).getCapitalProvinceID() >= 0
            && Game.alliancesSpecial.get(this.allianceID).iLeaderCivID
               == Game.getProvince(Game.getCiv(Game.alliancesSpecial.get(this.allianceID).iLeaderCivID).getCapitalProvinceID()).getCivID()) {
            Game.mapCoords.centerToProvinceID(Game.getCiv(Game.alliancesSpecial.get(this.allianceID).iLeaderCivID).getCapitalProvinceID());
         }
      } catch (Exception var2) {
      }
   }
}
