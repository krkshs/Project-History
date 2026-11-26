package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonRecruitingArmy extends Button {
   public int unitTypeID;
   public int armyID;
   public int iCivID;

   public ButtonRecruitingArmy(int iPosX, int iPosY, int unitTypeID, int armyID, int iCivID) {
      this.unitTypeID = unitTypeID;
      this.armyID = armyID;
      this.iCivID = iCivID;
      this.init(null, CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         int tRecruitArmyID = Game.getCiv(this.iCivID).getRecruitArmyInProvinceID(InGame_ProvinceInfo.iProvinceID);
         float fProgress = 0.0F;
         if (tRecruitArmyID >= 0) {
            this.unitTypeID = Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).unitID;
            this.armyID = Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).armyID;
            fProgress = (float)Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).timeLeft
               / ArmyManager.lArmy
                     .get(Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).unitID)
                     .get(Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).armyID)
                  .RecruitmentTime;
         } else {
            Game.addSimpleTask(new Game.SimpleTask("rebuildProvinceInfo") {
               @Override
               public void update() {
                  if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                     Game.menuManager.rebuildInGame_ProvinceInfo(false);
                  }
               }
            });
         }

         ArmyManager.armyImages
            .get(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).ImageID)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.buildingsFrameSmall).getWidth(),
               ImageManager.getImage(Images.buildingsFrameSmall).getHeight()
            );
         ImageManager.getImage(Images.buildingsFrameSmall).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         oSB.setColor(new Color(ProvinceDraw.progressBarBG));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2 + iTranslateX,
               this.getPosY()
                  - CFG.PADDING
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY
            );
         oSB.setColor(new Color(ProvinceDraw.progressBar));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2 + iTranslateX,
               this.getPosY()
                  - CFG.PADDING
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY,
               (int)(ImageManager.getImage(Images.progressBarFrameMask).getWidth() * (1.0F - fProgress)),
               ImageManager.getImage(Images.progressBarFrameMask).getHeight()
            );
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.progressBarFrame)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrame).getWidth() / 2 + iTranslateX,
               this.getPosY() - CFG.PADDING + this.getHeight() - ImageManager.getImage(Images.progressBarFrame).getHeight() + iTranslateY
            );
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.buildingsFrameSmall).getHeight();
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.buildingsFrameSmall).getWidth();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      int tRecruitArmyID = Game.getCiv(this.iCivID).getRecruitArmyInProvinceID(InGame_ProvinceInfo.iProvinceID);
      if (this.iCivID == Game.player.iCivID) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ClickToCancel"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TheArmyWillBeDeployedToThisLocation") + ": "));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + Game.getCiv(this.iCivID).lArmyRecruit.get(tRecruitArmyID).get(0).timeLeft),
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements) {
         @Override
         public int getMinPosX() {
            return InGame_ProvinceInfo.HOVER_POSX;
         }

         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY) {
            nPosY = InGame_ProvinceInfo.HOVER_POSY + (CFG.GAME_HEIGHT - InGame_ProvinceInfo.HOVER_POSY) / 2 - this.iHeight / 2;
            super.draw(oSB, nPosX, nPosY);
         }
      };
   }

   @Override
   public void actionElement() {
      if (this.iCivID == Game.player.iCivID) {
         Game.getCiv(Game.player.iCivID).cancelRecruitArmy(InGame_ProvinceInfo.iProvinceID);
      }
   }

   @Override
   public int getSFX() {
      return this.iCivID == Game.player.iCivID ? SoundsManager.SOUND_RECRUIT_CANCEL : super.getSFX();
   }
}
